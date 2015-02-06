package qtiscoringengineTester;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import AIR.Common.Configuration.AppSettingsHelper;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Utilities.MapUtils;
import AIR.Common.Utilities.SpringApplicationContext;
import AIR.Common.Utilities.TDSStringUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlReader;
import qtiscoringengine.CustomOperatorRegistry;
import qtiscoringengine.ISECustomOperator;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.QTIScoringException;
import qtiscoringengine.QTIScoringRuntimeException;
import qtiscoringengine.ResponseDeclaration;
import qtiscoringengine.ValidationLog;
import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.RubricContentType;
import tds.itemscoringengine.web.server.ScoringMaster;
import tinyctrlscoringengine.CtrlCustomOpFactory;
import tinyequationscoringengine.TinyEqCustomOpFactory;
import tinygrscoringengine.TinyGRCustomOpFactory;
import tinytablescoringengine.TinyTableCustomOpFactory;

public class FormQtiTester
{
  private static final Logger _logger = LoggerFactory.getLogger (FormQtiTester.class);
  private ItemSpecification   _itemSpec;
  private ScoreCounter        _counter;

  public FormQtiTester (ItemSpecification itemSpec, ScoreCounter scoreCounter) throws QTIScoringException, URISyntaxException {
    _counter = scoreCounter;
    _itemSpec = itemSpec;
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

        try {
          ResponseInfo res = new ResponseInfo (_itemSpec._format, itemId, wrapResponse (response), new URI ("file:///" + _itemSpec._rubricFilePath), RubricContentType.Uri, "NA", true);
          ItemScore score = getScoringMaster ().ScoreItem (res, null);

          if (score.getScoreInfo ().getPoints () == originalScore) {
            _counter.incrementCorrect (originalScore);
            _logger.info (TDSStringUtils.format ("Correct, item={0}, line={2}, score={1}", itemId, score.getScoreInfo ().getPoints (), lineCounter));
          } else {
            _counter.incrementIncorrect (originalScore);
            _logger.error (TDSStringUtils.format ("Wrong, item={0}, line={3}, score={1}, realScore={2}", itemId, score.getScoreInfo ().getPoints (), originalScore, lineCounter));
          }
        } catch (Exception exp) {
          exp.printStackTrace ();
          _counter.incrementErrors (originalScore);
          _logger.error (TDSStringUtils.format ("Exception, item={0}, line={2}, message={1}", itemId, exp.getMessage (), lineCounter));
        }
      }
    }
  }

  private String wrapResponse (String response) {
    /*
     * if (response.startsWith ("<itemResponse>")) return response; else return
     * "<itemResponse><response id=\"RESPONSE\"><value><![CDATA[" + response +
     * "]]></value></response></itemResponse>";
     */
    return response;
  }

  private static IItemScorer getScoringMaster () {
    return SpringApplicationContext.getBean ("scoringMaster", IItemScorer.class);
  }
}
