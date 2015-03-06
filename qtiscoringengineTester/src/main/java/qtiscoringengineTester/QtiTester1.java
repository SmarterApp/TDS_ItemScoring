package qtiscoringengineTester;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import AIR.Common.Helpers._Ref;
public class QtiTester1
{
  private static final Logger _logger = LoggerFactory.getLogger (QtiTester1.class);

  public static void main (String[] args) {

    Object[] inputArguments = getParameters (args);

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext ("/scoringengine-spring-config.xml");

    testAllFiles (inputArguments);
    // testOneFile (inputArguments);
  }

  // TODO Shiva: make these command line arguments using CLI
  private static Object[] getParameters (String[] inputArguments) {
    // the following 5 are required in case you intend to use invoke
    // testOneFile.
    // final String itemId = "21690";
    final String itemId = "8222";
    final String bankId = "NA";
    final String rubricFilePath = "C:/javaworkspace/DataFiles/QRXrubrics/Item_8222_v7.qrx";
    final String responseFile = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forshiva/8222_EQ.tsv";
    // final String responseFile =
    // "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/16207_TI.tsv";
    final String itemType = "TI";

    // the following are required in case you intend to use testAllFiles.
    //final String folder = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/";
    final String folder = "C:/java_workspace/sts_workspace/TinyScoringEngine/DataFiles";
    final Integer MAX_FILES_TO_TEST = 2000;
    //final String itemIdsToScore = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/SourceCode/itemscoringdevdefault/ScoringResults/MismatchItemIds.txt";
    final String itemIdsToScore = "C:/Users/efurman/Downloads/qtitester_eq.log";

    
    // TODO Shiva: control logger settings from here.
    // For the time being set them in log4j.xml

    // Do not modify below this line.
    Object[] args = new Object[8];
    args[0] = itemId;
    args[1] = bankId;
    args[2] = rubricFilePath;
    args[3] = responseFile;
    args[4] = itemType;
    args[5] = folder;
    args[6] = MAX_FILES_TO_TEST;
    args[7] = itemIdsToScore;

    return args;
  }

  private static void testAllFiles (Object[] args) {
    final ScoreCounter scoreCounter = new ScoreCounter ();
    final String folder = sanitizeFileNameForUri ((String) args[5]);
    final int MAXFILES = (Integer) args[6];
    final String itemIdsToScore = (String) args[7];
    try {
      final Set<String> itemsToScoreSet = buildItemsToScore (itemIdsToScore);
      final Map<String, String> rubricsMap = mapRubricPathToItemId (folder, new _Ref<Map<String, String>> (new HashMap<String, String> ()));

      int fileCounter = 1;
      List<File> files = (List<File>) Arrays.asList (new File (folder).listFiles ());
      Collections.shuffle (files);
      for (File file : files) {
        String absoluteFilePath = file.getAbsolutePath ();
        if (StringUtils.endsWithIgnoreCase (absoluteFilePath, ".tsv")) {
          final Map<String, String> parameters = getItemIdForResponsefile (file.getName ());
          if (parameters == null)
            continue;
          final String itemId = parameters.get ("itemid");
          final String format = parameters.get ("format");

          if (StringUtils.isEmpty (itemId))
            continue;

          // is it in the set? if the set is null then basically we are scoring
          // everything else we are only scoring if it exists in the set.
          if (itemsToScoreSet != null && !itemsToScoreSet.contains (itemId))
            continue;

          if (fileCounter == MAXFILES)
            break;
          ++fileCounter;

          final String rubricPath = sanitizeFileNameForUri (rubricsMap.get (itemId));
          if (StringUtils.isEmpty (rubricPath)) {
            _logger.error (String.format ("Error: No rubric found for item id %s", itemId));
            scoreCounter.incrementMissingRubrics ();
            continue;
          }

          _logger.error (String.format ("Processing input file %s with item id %s using rubric %s.", file.getAbsolutePath (), itemId, rubricPath));

          restartPython ();

          FormQtiTester qtiTester = new FormQtiTester (new ItemSpecification ()
          {
            {
              this._itemId = itemId;
              this._bankId = "NA";
              this._rubricFilePath = rubricPath;
              this._format = format;
            }
          }, scoreCounter);

          qtiTester.processResponseFiles (file.getAbsolutePath ());
        }
      }

    } catch (Exception exp) {
      exp.printStackTrace ();
      _logger.error (exp.getMessage (), exp);
    }

    _logger.error (scoreCounter.toString ());
  }

 
  final static Pattern FilePattern = Pattern.compile ("(?<itemid>[0-9]*)_(?<format>[a-zA-Z0-9]*)\\.tsv");

  private static Map<String, String> getItemIdForResponsefile (String file) {
    Matcher m = FilePattern.matcher (file.toLowerCase ());
    if (m.matches ()) {
      Map<String, String> parameters = new HashMap<String, String> ();
      parameters.put ("itemid", m.group ("itemid"));
      parameters.put ("format", m.group ("format"));
      return parameters;
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
      String itemId = m.group ("itemid");
      String currentMap = map.get (itemId);
      if (currentMap == null || currentMap.toLowerCase ().contains ("others"))
        map.put (itemId, folder.getAbsolutePath ());
    }
    return map;
  }

  private static String sanitizeFileNameForUri (String name) {
    return StringUtils.replace (name, "\\", "/");
  }

  private static Set<String> buildItemsToScore (String fileName) throws IOException {
    if (fileName == null)
      return null;
    Set<String> set = new HashSet<String> ();
    try (BufferedReader bfr = new BufferedReader (new FileReader (fileName))) {
      String line = null;
      while ((line = bfr.readLine ()) != null) {
                
        // Looking for item number in string like this:
        //02:37:17,055 ERROR [FormQtiTester] Wrong, item=3034, line=5, score=0,....
        int start = StringUtils.indexOf (line, "Wrong, item=");
        if  (start != -1) {
          start += 12;
          int end = StringUtils.indexOf (line, ",", start);
          if (end != -1) {
            String a = StringUtils.substring (line, start, end);
            set.add (a);
          }
        }
      }
      if (set.size () > 0)
        return set;
      return null;
    }
  }

  static Thread _pythonProcessThread = null;

  public static void restartPython ()
  {
    try {
      // first kill all python processes.
      Process endPython = Runtime.getRuntime ().exec ("taskkill /F /IM python.exe");
      Process endCmd = Runtime.getRuntime ().exec ("taskkill /F /IM cmd.exe");
      // TODO: only for debugging: remove it
      //Thread.sleep (10000);

      if (_pythonProcessThread != null)
        _pythonProcessThread.join ();

      _pythonProcessThread = new Thread ()
      {
        public void run ()
        {
          try {
            //from 
            // TODO start python http://stackoverflow.com/questions/15199119/runtime-exec-waitfor-doesnt-wait-until-process-is-done
            //Process startPython = Runtime.getRuntime ().exec ("cmd /c start C:/Workspace/JavaWorkspace/TinyScoringEngine/SourceCode/ItemScoringEngineDev/sympy-scripts/start.bat");
            Process startPython = Runtime.getRuntime ().exec ("cmd /c start C:/java_workspace/sts_workspace/TinyScoringEngine/SourceCode/ItemScoringEngineDev/sympy-scripts/start.bat");

          } catch (Exception exp)
          {
            _logger.error (exp.getMessage ());
            exp.printStackTrace ();
          }
        }
      };
      _pythonProcessThread.start ();

      // TODO only for debugging: remove it.
      //Thread.sleep (10000);
    } catch (Exception exp)
    {
      exp.printStackTrace ();
    }
  }
}
