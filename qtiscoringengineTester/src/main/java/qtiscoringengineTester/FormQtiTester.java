package qtiscoringengineTester;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Utilities.MapUtils;
import AIR.Common.Utilities.TDSStringUtils;
import AIR.Common.xml.XmlReader;
import qtiscoringengine.CustomOperatorRegistry;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.QTIScoringException;
import qtiscoringengine.QTIScoringRuntimeException;
import qtiscoringengine.ResponseDeclaration;
import qtiscoringengine.ValidationLog;
import tinyctrlscoringengine.CtrlCustomOpFactory;
import tinyequationscoringengine.TinyEqCustomOpFactory;
import tinygrscoringengine.TinyGRCustomOpFactory;
import tinytablescoringengine.TinyTableCustomOpFactory;

public class FormQtiTester
{
  private static final Logger _logger         = LoggerFactory.getLogger (FormQtiTester.class);
  private ItemSpecification   _itemSpec;
  private QTIRubric           _rubric;
  private Map<String, String> _responseValues = new HashMap<String, String> ();
  private ValidationLog       _log            = null;
  private ScoreCounter        _counter;

  public FormQtiTester (ItemSpecification itemSpec, ScoreCounter scoreCounter) throws QTIScoringException, URISyntaxException {
    _counter = scoreCounter;
    _itemSpec = itemSpec;
    _log = new ValidationLog (_itemSpec._rubricFilePath);
    // load the rubric.
    try {
      XmlReader reader = new XmlReader (new FileInputStream (_itemSpec._rubricFilePath));
      _rubric = QTIRubric.fromXML (_itemSpec._rubricFilePath, reader, _log);
      if (!_rubric.validate (_log)) {
        StringBuilder rationale = new StringBuilder ();

        for (int i = 0; i < _log.getCount (); i++)
          rationale.append (_log.Message (i) + "\n");

        String message = "Rubric validation failed : " + rationale.toString ();
        _logger.error (message);
        throw new QTIScoringException (message);
      }
    } catch (final Exception exp) {
      _logger.error (exp.getMessage (), exp);
      exp.printStackTrace ();
      throw new QTIScoringException ("Unable to load rubric", exp);
    }

    _responseValues.clear ();
    for (String[] info : _rubric.getPublicVariableDeclarations ()) {
      String val = getValue (info[0]);
      _responseValues.put (info[0], val);
    }
    displayVariables ();

  }

  public void processResponseFiles (String fileName) throws IOException {
    try (BufferedReader bfr = new BufferedReader (new FileReader (fileName))) {

      int lineCounter = 1;
      // get the header line.
      String line = bfr.readLine ();
      // process from next line onwards.
      while ((line = bfr.readLine ()) != null) {
        ++lineCounter;
        // get the columns.
        String[] columns = qtiscoringengineTester.CSVReader.getColumns (line);
        String itemId = columns[0];
        int originalScore = Integer.parseInt (columns[1]);
        String response = columns[2];
        if (!StringUtils.equals (itemId, _itemSpec._itemId))
          continue;

        Map<String, String> cloneResponseValues = new HashMap<String, String> ();
        // TODO Shiva: I do not think I need to clone _responseValues map as it
        // should be empty at this point but
        // I will just do this.
        for (String key : _responseValues.keySet ()) {
          cloneResponseValues.put (key, _responseValues.get (key));
        }
        cloneResponseValues.put ("RESPONSE", response);
        try {
          List<String[]> bindings = _rubric.evaluate (cloneResponseValues);

          String scorestring = findValue (bindings, "SCORE");
          _Ref<Integer> score = new _Ref<Integer> (-1);
          JavaPrimitiveUtils.intTryParse (scorestring, score);
          if (score.get () == originalScore) {
            _counter.incrementCorrect (originalScore);
            _logger.info (TDSStringUtils.format ("Correct, item={0}, line={2}, score={1}", itemId, score, lineCounter));
          } else {
            _counter.incrementIncorrect (originalScore);
            _logger.error (TDSStringUtils.format ("Wrong, item={0}, line={3}, score={1}, realScore={2}", itemId, score.get (), originalScore, lineCounter));
          }
        } catch (Exception exp) {
          exp.printStackTrace ();
          _counter.incrementErrors (originalScore);
          _logger.error (TDSStringUtils.format ("Exception, item={0}, line={2}, message={1}", itemId, exp.getMessage (), lineCounter));
        }
      }
    }
  }

  private void displayVariables () {
    _logger.info ("==========Start displaying public variable declarations=============");
    List<String[]> variableInfo = _rubric.getPublicVariableDeclarations ();
    for (String[] info : variableInfo) {
      String[] stuff = new String[4];
      stuff[0] = info[0];
      stuff[1] = info[1];
      stuff[2] = info[2];

      String val = getValue (info[0]);
      stuff[3] = (val == null) ? "<null>" : val;
      _logger.info ("[" + StringUtils.join (stuff) + "]");
    }
    _logger.info ("==========End displaying public variable declarations=============");
  }

  private void displayReturnVariables (List<String[]> variableInfo) {
    _logger.info ("==========Start displaying return variable declarations=============");
    for (String[] info : variableInfo) {
      String[] stuff = new String[4];
      stuff[0] = info[0];
      stuff[1] = info[1];
      stuff[2] = info[2];
      stuff[3] = info[3];
      _logger.info ("[" + StringUtils.join (stuff) + "]");
    }
    _logger.info ("==========End displaying return variable declarations=============");
  }

  private String getValue (String p) {
    if (_responseValues.containsKey (p))
      return _responseValues.get (p);
    return null;
  }

  private String findValue (List<String[]> bindings, String p) {
    for (String[] info : bindings) {
      if (StringUtils.equals (info[0], p))
        return info[3];
    }
    return "";
  }

  static {
    CustomOperatorRegistry.getInstance ().addOperatorFactory (new TinyGRCustomOpFactory ());
    CustomOperatorRegistry.getInstance ().addOperatorFactory (new CtrlCustomOpFactory ());
    CustomOperatorRegistry.getInstance ().addOperatorFactory (new TinyTableCustomOpFactory ());
    try {
      CustomOperatorRegistry.getInstance ().addOperatorFactory (new TinyEqCustomOpFactory (new URI ("http://localhost:8080/"), 3));
    } catch (URISyntaxException exp) {
      throw new QTIScoringRuntimeException (exp);
    }
  }
}
