/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscorerunittests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import qtiscoringengine.BaseType;
import qtiscoringengine.DataElement;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.QTIUtility;
import qtiscoringengine.ValidationLog;
import qtiscoringengine.itembody.Interaction;
import qtiscoringengine.itembody.MatchingInteraction;
import qtiscoringengine.itembody.QTIItemBody;
import AIR.Common.xml.XmlReader;

public class UnitTests
{
  // private final String directoryPath =
  // "C:\\Users\\qyang\\Desktop\\QTI_Test\\Current";
  private final String directoryPath = "C:\\Users\\qyang\\Desktop\\QTI_Test\\TestCase";

  @Test
  public void testAllFilesInDirectory () throws IOException
  {
    File logFile = new File (directoryPath, QTIUtility.getUniqueFileName ("Log", ".html"));
    if (!logFile.exists ()) {
      logFile.createNewFile ();
    }

    try (BufferedWriter sw = new BufferedWriter (new FileWriter (logFile)))
    {
      sw.write (getHTMLHeader ());

      /* Search only top directory */
      // Files.walk (Paths.get (directoryPath), 1)
      // .filter (fp -> FilenameUtils.getExtension (fp.toString ()).equals
      // ("xml"))
      // .forEach (fp -> {
      // try {
      // System.out.println (fp.toString ());
      // testFile (fp.toFile (), sw);
      // } catch (Exception e) {
      // e.printStackTrace ();
      // }
      // });

      File folder = new File (directoryPath);
      File[] listOfFiles = folder.listFiles ();

      for (File fp : listOfFiles) {
        if (FilenameUtils.getExtension (fp.getPath ()).equals ("xml")) {
          try {
            System.out.println (fp.getPath ());
            testFile (fp, sw);
          } catch (Exception e) {
            e.printStackTrace ();
          }
        }
      }

      sw.write (getHTMLClosing ());
    }
  }

  private void testFile (File file, BufferedWriter sw) throws Exception
  {
    QTIRubric _rubric = null;
    ValidationLog log = new ValidationLog (file.getPath ());
    // log.ValidationRigor = ValidationLog.Rigor.None;
    String msgSuffix = " for file " + file.getName () + "\n";

    // using (XmlReader reader = XmlReader.Create("file:///" + file.getPath ()))
    XmlReader reader = new XmlReader (new FileInputStream (file.getPath ()));
    QTIRubric rubric = QTIRubric.fromXML (file.getPath (), reader, log);
    _rubric = rubric;
    Assert.assertNotNull (_rubric);

    // check the validation
    boolean ok = _rubric.validate (log);
    String failedMessage = "The rubric failed validation with " + log.getCount () + " errors. Messages:\n";
    for (int i = 0; i < log.getCount (); i++)
      failedMessage += log.Message (i) + "\n";
    Assert.assertTrue (ok);

    QTIItemBody itemBody = null;
    // using (XmlReader reader = XmlReader.Create("file:///" + file.getPath ()))
    reader = new XmlReader (new FileInputStream (file.getPath ()));
    // if it passes validation then get the itemBody and create a test based on
    // the item
    itemBody = QTIItemBody.fromXml (file.getPath (), reader, log);
    String itemBodyFailedMsg = "Error in the ItemBody node" + msgSuffix;
    for (int i = 0; i < log.getCount (); i++)
      itemBodyFailedMsg += log.Message (i) + "\n";
    Assert.assertNotNull (itemBody);

    // itemBody node is not required, and we can't test items that don't have an
    // item body node
    // Zach: should this make the test fail or just skip it?
    if (itemBody.isEmpty ())
      return;

    for (int i = 0; i < itemBody.getNumberOfInteractions (); i++)
    {
      switch (itemBody.getItemType (i))
      {
      case choiceInteraction:
      case associateInteraction:
      case graphicAssociateInteraction:
      case graphicOrderInteraction:
      case hotspotInteraction:
      case hottextInteraction:
      case orderInteraction:
        testAllCombinations (itemBody.getInteraction (i), _rubric, msgSuffix, sw, file.getName ());
        break;
      case gapMatchInteraction:
      case graphicGapMatchInteraction:
      case matchInteraction:
        testGapMatch (itemBody.getInteraction (i), _rubric, msgSuffix, sw, file.getName ());
        break;
      case selectPointInteraction:
      case sliderInteraction:
        testSelectPoint (itemBody.getInteraction (i), _rubric, msgSuffix, sw, file.getName ());
        break;
      default:
        log.addMessage (null, "No unit tests setup for item type " + itemBody.getItemType (i));
        break;

      }
      if (log.getCount () > 0)
      {
        System.out.println ("Validation Log Messages " + msgSuffix);
        for (int j = 0; j < log.getCount (); j++)
          System.out.println (log.Message (j));
        System.out.println ("");
      }
      log = new ValidationLog (file.getPath ());
    }
  }

  private void testAllCombinations (Interaction interaction, QTIRubric rubric, String msgSuffix, BufferedWriter sw, String fileName) throws Exception
  {
    String responseIdentifier = interaction.getResponseIdentifier (); // usually
                                                                      // "RESPONSE"
    String correctResponse = rubric.getCorrectValue (responseIdentifier);
    int numElemsInMatch = interaction.getNumberOfAnswersInResponse ();
    if (!StringUtils.isEmpty (correctResponse))
      numElemsInMatch = correctResponse.split ("\\s+").length;
    // list of the possible choices for answers
    List<String> responseChoices = interaction.getAnswers ();

    // just in case, so we don't get an inifinite loop
    if (numElemsInMatch == -1)
      numElemsInMatch = 1;

    List<List<String>> possibleAnswers = new ArrayList<List<String>> ();
    Utility.getPossibleResponses (new ArrayList<String> (), numElemsInMatch, responseChoices, possibleAnswers);
    testAnswers (possibleAnswers, rubric, responseIdentifier, msgSuffix, sw, fileName);
  }

  private void testGapMatch (Interaction interaction, QTIRubric rubric, String msgSuffix, BufferedWriter sw, String fileName) throws Exception
  {
    MatchingInteraction mi = (MatchingInteraction) interaction;
    String responseIdentifier = interaction.getResponseIdentifier (); // usually
                                                                      // "RESPONSE"
    List<String> textChoices = interaction.getAnswers ();
    List<String> gapChoices = mi.getMatchValues ();

    List<List<String>> possibleAnswers = Utility.getMappingResponses (textChoices, gapChoices);
    testAnswers (possibleAnswers, rubric, responseIdentifier, msgSuffix, sw, fileName);
  }

  private void testSelectPoint (Interaction interaction, QTIRubric rubric, String msgSuffix, BufferedWriter sw, String fileName) throws Exception
  {
    String responseIdentifier = interaction.getResponseIdentifier (); // usually
                                                                      // "RESPONSE"
    List<String> responseChoices = interaction.getAnswers (); // this will have
                                                              // width and
                                                              // height as
                                                              // strings
    int width = Integer.valueOf (responseChoices.get (0));
    int height = Integer.valueOf (responseChoices.get (1));
    // width and height could be huge, so instead of trying every combination
    // we try every 10 up to the width and height
    // List<String> widths =
    // Utility.getNumbers(width).ConvertAll<string>(Convert.ToString);
    // List<String> heights =
    // Utility.getNumbers(height).ConvertAll<string>(Convert.ToString);

    List<String> widths = Arrays.asList (StringUtils.join (Utility.getNumbers (width), " ").split ("\\s+"));
    List<String> heights = Arrays.asList (StringUtils.join (Utility.getNumbers (height), " ").split ("\\s+"));

    List<List<String>> possibleAnswers = Utility.getMappingResponses (widths, heights);
    testAnswers (possibleAnswers, rubric, responseIdentifier, msgSuffix, sw, fileName);
  }

  private void testAnswers (List<List<String>> possibleAnswers, QTIRubric rubric, String responseIdentifier, String msgSuffix,
      BufferedWriter sw, String fileName) throws Exception
  {
    BaseType type = rubric.getResponseDeclaration (responseIdentifier).getType ();

    List<String> answers = new ArrayList<String> ();

    for (int i = 0; i < possibleAnswers.size (); i++)
    {
      String answer = StringUtils.join (possibleAnswers.get (i), " ");
      answers.add (answer);
    }
    // add a blank answer and a null answer
    answers.add ("");
    answers.add (null);
    String correctResponse = rubric.getCorrectValue (responseIdentifier);
    // Assert.IsNotNull(correctResponse,
    // "Correct response was not set for identifier " + responseIdentifier +
    // msgSuffix);
    DataElement correctAnswerDE = DataElement.create (correctResponse, type);
    double correctScore = Double.MAX_VALUE; // double.MaxValue;
    if (!StringUtils.isEmpty (correctResponse))
    {
      // get the score for the correct answer
      String[] score = null; // = Utility.setResponseAndScore(rubric,
                             // correctResponse,
                             // responseIdentifier).FirstOrDefault(x =>
                             // x[0].ToLower() == "score"); // note: score
                             // hardcoded
      for (String[] sa : Utility.setResponseAndScore (rubric, correctResponse, responseIdentifier)) {
        if (sa[0].toLowerCase ().equals ("score")) {
          score = sa;
          break;
        }
      }

      Assert.assertNotNull (score);
      // Assert.IsNotNullOrEmpty(score[3],
      // "There was no score for the correct response of '" + correctResponse +
      // "'" + msgSuffix);
      Assert.assertNotNull (score[3]);
      // correctScore = Convert.ToDouble((score[3] != null ? score[3] : "null"/*
      // score[3] ?? "null"*/).Equals("null",
      // StringComparison.InvariantCultureIgnoreCase) ? "-1" : score[3]);
      score[3] = (score[3] == null ? "null" : score[3]);
      correctScore = score[3].equals ("null") ? -1 : Double.parseDouble (score[3]);
    }
    double maxScore = -Double.MAX_VALUE; // double.MinValue;
    String maxScoreAnswer = "";

    // try all possible responses and check that the scores are <= the score for
    // the correct answer
    for (String answer : answers)
    {
      String strAns = (answer != null ? answer : "null"); // answer ?? "null";
      String[] score = null;
      for (String[] sa : Utility.setResponseAndScore (rubric, answer, responseIdentifier)) {
        if (sa[0].toLowerCase ().equals ("score")) {
          score = sa;
          break;
        }
      }

      Assert.assertNotNull (score);
      // Assert.IsNotNullOrEmpty(score[3],
      // "There was no score for the response of '" + strAns + "'" + msgSuffix);
      Assert.assertNotNull (score[3]);
      // keep track of the maximum score and make sure the correct answer had
      // the highest score
      // double numScore = Convert.ToDouble((score[3] != null ? score[3] :
      // "null"/*score[3] ?? "null"*/).Equals("null",
      // StringComparison.InvariantCultureIgnoreCase) ? "-1" : score[3]);
      score[3] = (score[3] == null ? "null" : score[3]);
      double numScore = score[3].equals ("null") ? -1 : Double.parseDouble (score[3]);

      // print out a row in the log table if the score had a value > 0
      if (numScore > 0)
        sw.write (addHTMLRow (fileName, answer, score[3]));
      if (!StringUtils.isEmpty (correctResponse) && numScore > maxScore && strAns != correctResponse)
      {
        DataElement answerDE = DataElement.create (strAns, type);

        if (answerDE == null || correctAnswerDE == null || !answerDE.equals (correctAnswerDE) || (numScore > correctScore && answerDE.equals (correctAnswerDE)))
        {
          maxScore = numScore;
          maxScoreAnswer = strAns;
        }
      }
    }
    Assert.assertTrue (correctScore >= maxScore);
  }

  private String getHTMLHeader ()
  {
    // This has room for future improvements for formatting
    StringBuilder sb = new StringBuilder ();
    sb.append ("<!DOCTYPE html>").append ("\n");
    sb.append ("<html>").append ("\n");
    sb.append ("<body>").append ("\n");
    sb.append ("<p><h1> Unit Test Log</h1>").append ("\n");
    sb.append ("<table border=\"1\">").append ("\n");
    // add the table headers
    sb.append ("<tr><th>File Name</th>").append ("\n");
    sb.append ("<th>Answer</th>").append ("\n");
    sb.append ("<th>Score</th></tr>").append ("\n");
    return sb.toString ();
  }

  private String addHTMLRow (String fileName, String answer, String score)
  {
    StringBuilder sb = new StringBuilder ();
    // add the table headers
    sb.append ("<tr><td>" + fileName + "</td>").append ("\n");
    sb.append ("<td>" + answer + "</td>").append ("\n");
    sb.append ("<td>" + score + "</td></tr>").append ("\n");
    return sb.toString ();
  }

  private String getHTMLClosing ()
  {
    // This has room for future improvements for formatting
    StringBuilder sb = new StringBuilder ();
    sb.append ("</table>").append ("\n");
    sb.append ("</html>").append ("\n");
    sb.append ("</body>").append ("\n");
    return sb.toString ();
  }
}
