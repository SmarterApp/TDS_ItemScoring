package qtiscoringengineTester;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AIR.Common.Helpers._Ref;

public class QtiTester
{
  private static final Logger _logger = LoggerFactory.getLogger (QtiTester.class);

  public static void main (String[] args) {
    testAllFiles (args);
    // testOneFile (args);
  }

  public static void testAllFiles (String[] args) {
    final ScoreCounter scoreCounter = new ScoreCounter ();
    final int MAXFILES = 50;
    try {
      final String folder = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/";
      final Map<String, String> rubricsMap = mapRubricPathToItemId (folder, new _Ref<Map<String, String>> (new HashMap<String, String> ()));

      int fileCounter = 1;
      for (File file : new File (folder).listFiles ()) {
        if (StringUtils.endsWithIgnoreCase (file.getAbsolutePath (), ".tsv")) {

          if (fileCounter == MAXFILES)
            break;
          ++fileCounter;

          final String itemId = getItemIdForResponsefile (file.getName ());
          if (StringUtils.isEmpty (itemId))
            continue;

          final String rubricPath = rubricsMap.get (itemId);
          if (StringUtils.isEmpty (rubricPath)) {
            _logger.info (String.format ("Error: No rubric found for item id %s", itemId));
            scoreCounter.incrementMissingRubrics ();
            continue;
          }

          _logger.info (String.format ("Processing input file %s with item id %s using rubric %s.", file.getAbsolutePath (), itemId, rubricPath));

          FormQtiTester qtiTester = new FormQtiTester (new ItemSpecification ()
          {
            {
              this._itemId = itemId;
              this._bankId = "NA";
              this._rubricFilePath = rubricPath;
            }
          }, scoreCounter);

          qtiTester.processResponseFiles (file.getAbsolutePath ());
        }
      }

    } catch (Exception exp) {
      exp.printStackTrace ();
      _logger.error (exp.getMessage (), exp);
    }

    _logger.info (scoreCounter.toString ());
  }

  public static void testOneFile (String[] args) {
    final ScoreCounter scoreCounter = new ScoreCounter ();
    try {
      // From here: http://blog.frankel.ch/thoughts-on-java-logging-and-slf4j
      final String itemId = "10110";
      final String bankId = "NA";
      final String rubricFilePath = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/ERX/Item_10110_v14.qrx";
      final String responseFile = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/10110_EQ.tsv";

      FormQtiTester qtiTester = new FormQtiTester (new ItemSpecification ()
      {
        {
          this._itemId = itemId;
          this._bankId = bankId;
          this._rubricFilePath = rubricFilePath;
        }
      }, scoreCounter);

      qtiTester.processResponseFiles (responseFile);

    } catch (Exception exp) {
      exp.printStackTrace ();
      _logger.error (exp.getMessage (), exp);
    }
    _logger.info (scoreCounter.toString ());
  }

  final static Pattern FilePattern = Pattern.compile ("(?<itemid>[0-9]*)_[a-zA-Z]*\\.tsv");

  private static String getItemIdForResponsefile (String file) {
    Matcher m = FilePattern.matcher (file.toLowerCase ());
    if (m.matches ()) {
      return m.group ("itemid");
    }
    return null;
  }

  final static Pattern RubricPattern = Pattern.compile ("item_(?<itemid>[0-9]*)_v[0-9]*\\.qrx");

  private static Map<String, String> mapRubricPathToItemId (String folderPathToScan, _Ref<Map<String, String>> outputMap) {
    Map<String, String> map = outputMap.get ();
    if (map == null) {
      outputMap.set (new HashMap<String, String> ());
      map = outputMap.get ();
    }

    // scan files.
    File folder = new File (folderPathToScan);
    if (folder.isFile ())
      mapRubricPathToItemIdForFile (folder.getAbsolutePath (), outputMap);
    else {
      for (String subFile : folder.list ()) {
        File subFilePath = new File (folder.getAbsolutePath () + File.separator + subFile);
        if (subFilePath.isDirectory ())
          mapRubricPathToItemId (subFilePath.getAbsolutePath (), outputMap);
        else if (subFilePath.isFile ())
          mapRubricPathToItemIdForFile (subFilePath.getAbsolutePath (), outputMap);
      }
    }

    return map;
  }

  private static Map<String, String> mapRubricPathToItemIdForFile (String file, _Ref<Map<String, String>> outputMap) {
    File folder = new File (file);
    Map<String, String> map = outputMap.get ();
    Matcher m = RubricPattern.matcher (folder.getName ().toLowerCase ());
    if (m.matches ()) {
      map.put (m.group ("itemid"), folder.getAbsolutePath ());
    }
    return map;
  }
}
