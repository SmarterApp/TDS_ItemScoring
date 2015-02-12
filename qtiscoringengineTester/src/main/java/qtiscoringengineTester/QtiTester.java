/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengineTester;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import AIR.Common.Helpers._Ref;

public class QtiTester
{
  private static final Logger _logger = LoggerFactory.getLogger (QtiTester.class);

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
    /*
     * final String itemId = "11787"; final String bankId = "NA"; final String
     * rubricFilePath =
     * "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/Others/Item_11787_v5.qrx"
     * ; final String responseFile =
     * "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/11787_HTQ.tsv"
     * ; final String itemType = "HTQ";
     */
    final String itemId = "12948";
    final String bankId = "NA";
    final String rubricFilePath = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/GRX/Item_12948_v14.qrx";
    final String responseFile = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/12948_GI.tsv";
    final String itemType = "GI";

    // the following are required in case you intend to use testAllFiles.
    final String folder = "C:\\WorkSpace\\JavaWorkSpace\\TinyScoringEngine\\DataFiles\\forShiva\\";
    final Integer MAX_FILES_TO_TEST = 3;

    // TODO Shiva: control logger settings from here.
    // For the time being set them in log4j.xml

    // Do not modify below this line.
    Object[] args = new Object[7];
    args[0] = itemId;
    args[1] = bankId;
    args[2] = rubricFilePath;
    args[3] = responseFile;
    args[4] = itemType;
    args[5] = folder;
    args[6] = MAX_FILES_TO_TEST;

    return args;
  }

  private static void testAllFiles (Object[] args) {
    final ScoreCounter scoreCounter = new ScoreCounter ();
    final String folder = sanitizeFileNameForUri ((String) args[5]);
    final int MAXFILES = (Integer) args[6];
    try {
      final Map<String, String> rubricsMap = mapRubricPathToItemId (folder, new _Ref<Map<String, String>> (new HashMap<String, String> ()));

      int fileCounter = 1;
      List<File> files = (List<File>) Arrays.asList (new File (folder).listFiles ());
      Collections.shuffle (files);
      for (File file : files) {
        String absoluteFilePath = file.getAbsolutePath ();
        if (StringUtils.endsWithIgnoreCase (absoluteFilePath, ".tsv")) {
          final Map<String, String> parameters = getItemIdForResponsefile (file.getName ());
          final String itemId = parameters.get ("itemid");
          final String format = parameters.get ("format");

          if (StringUtils.isEmpty (itemId))
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

  private static void testOneFile (Object[] args) {
    final ScoreCounter scoreCounter = new ScoreCounter ();
    try {
      // From here: http://blog.frankel.ch/thoughts-on-java-logging-and-slf4j
      final String itemId = (String) args[0];
      final String bankId = (String) args[1];
      final String rubricFilePath = sanitizeFileNameForUri ((String) args[2]);
      final String responseFile = sanitizeFileNameForUri ((String) args[3]);
      final String itemType = (String) args[4];

      FormQtiTester qtiTester = new FormQtiTester (new ItemSpecification ()
      {
        {
          this._itemId = itemId;
          this._bankId = bankId;
          this._rubricFilePath = rubricFilePath;
          this._format = itemType;
        }
      }, scoreCounter);

      qtiTester.processResponseFiles (responseFile);

    } catch (Exception exp) {
      exp.printStackTrace ();
      _logger.error (exp.getMessage (), exp);
    }
    _logger.info (scoreCounter.toString ());
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
      map.put (m.group ("itemid"), folder.getAbsolutePath ());
    }
    return map;
  }

  private static String sanitizeFileNameForUri (String name) {
    return StringUtils.replace (name, "\\", "/");
  }
}
