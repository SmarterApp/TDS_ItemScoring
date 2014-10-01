/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscorerunittests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import qtiscoringengine.QTIRubric;

class Utility
{
  /*internal static string GetTestFile(string filename)
  {
      Assembly thisAssembly = Assembly.GetExecutingAssembly();

      return Path.Combine(Path.GetDirectoryName(thisAssembly.Location), filename);
  }*/
//  static Stream GetTestFileStream(String name)
//  {
//      Assembly a = Assembly.GetExecutingAssembly();
//      return a.GetManifestResourceStream(name);
//  }

  static List<String[]> setCorrectResponseAndScore(QTIRubric rubric, String identifier) throws Exception
  {
      return setResponseAndScore(rubric, rubric.getCorrectValue(identifier), identifier);
  }

  public static List<String[]> setResponseAndScore(QTIRubric rubric, String response, String identifier) throws Exception
  {
      return rubric.evaluate(response, identifier);
  }
  

  /// <summary>
  /// Get a List of all possible responses, returned in returnList. It will automatically end when there are
  /// 500 possibilities.
  /// 
  /// Note: Lists are passed by reference
  /// 
  /// todo: room for optimization when ordering doesn't matter
  /// </summary>
  /// <param name="accum"></param>
  /// <param name="depth"></param>
  /// <param name="responseChoices"></param>
  /// <param name="returnList"></param>
  static void getPossibleResponses(List<String> accum, int depth, List<String> responseChoices, List<List<String>> returnList)
  {
      //this is so we don't get too many possibilities. They can go on for a very long time.
      if (returnList.size() >= 500)
          return;

      if (depth == 0)
      {
          returnList.addAll(Arrays.asList (accum));
          return;
      }
      for (String c : responseChoices)
      {
          List<String> newAccum = new ArrayList<String>(accum);
          newAccum.add(c);
          List<String> newChoices = new ArrayList<String>(responseChoices);
          newChoices.remove(c);
          getPossibleResponses(newAccum, depth - 1, newChoices, returnList);
      }
  }

  /// <summary>
  /// Map answers in Choices parameter to answers in ChoicesToMatch parameter. There will be a maximum of
  /// 500 items in the return list.
  /// </summary>
  /// <param name="Choices"></param>
  /// <param name="ChoicesToMatch"></param>
  /// <returns></returns>
  static List<List<String>> getMappingResponses(List<String> Choices, List<String> ChoicesToMatch)
  {
      List<List<String>> AllChoices = new ArrayList<List<String>>();
      for (String choice : Choices)
      {
          for (String match : ChoicesToMatch)
          {
              AllChoices.add(Arrays.asList(new String[] { choice, match }));
              if (AllChoices.size() >= 500)
                  return AllChoices;
          }
      }
      return AllChoices;
  }

  /// <summary>
  /// generate a list of numbers starting at 1 and counting by 10s, there will be a maximum of 500 elements
  /// </summary>
  /// <param name="maxNum"></param>
  /// <returns></returns>
  static List<Integer> getNumbers(int maxNum)
  {
      List<Integer> nums = new ArrayList<Integer>();
      for (int i = 1; i <= maxNum && nums.size() <= 500; i += 10)
          nums.add(i);
      return nums;
  }
}
