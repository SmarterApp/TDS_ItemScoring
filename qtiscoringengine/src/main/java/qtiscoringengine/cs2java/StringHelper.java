/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine.cs2java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringHelper
{
  static public String trim (String str, char[] trimChars) {

    String trimCharsString = new String (trimChars);
    int begin = -1, end = str.length () - 1;

    for (int i = 0; i < str.length (); ++i) {
      if (trimCharsString.indexOf (str.charAt (i)) < 0)
      {
        begin = i;
        break;
      }
    }

    if (begin == -1) {
      return "";
    }

    for (int i = str.length () - 1; i >= begin; --i) {
      if (trimCharsString.indexOf (str.charAt (i)) < 0) {
        end = i;
        break;
      }
    }

    return str.substring (begin, end + 1);
  }

  public StringHelper (String str) {
    this._str = str;
  }

  public String trim (char[] trimChars) {
    return StringHelper.trim (_str, trimChars);
  }

  private String _str;

  public static void main (String[] args)
  {
    try {

      System.err.println (StringHelper.trim ("   223", new char[] { ' ', '\n', '\t', '\r', ',', '{', '}' }));
    } catch (Exception exp)
    {
      exp.printStackTrace ();
    }
  }

  
  static public enum StringSplitOptions {
    None, RemoveEmptyEntries
  }

  static public String[] split (String str, char[] separator,
      StringSplitOptions options /* Only support RemoveEmptyEntries */) {
    List<String> list = new ArrayList<String> ();
    list.add (str);

    for (char ch : separator) {
      List<String> l = new ArrayList<String> ();
      for (String s : list) {
        String[] strs = StringUtils.split (s, ch);
        l.addAll (Arrays.asList (strs));
      }
      list = l;
    }
    return list.toArray (new String[0]);
  }
}
