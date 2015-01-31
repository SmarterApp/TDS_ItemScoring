package qtiscoringengine;

import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest
{
  public static void main (String[] args) {

    try {
      String value = "b { } {{{ a }} ";
      final String StringTokenPattern = "[{} \t\n\r]{1,}";
      Pattern p = Pattern.compile (StringTokenPattern);
      Matcher m = p.matcher (value);
      System.err.println(m.replaceAll (" "));
    } catch (Exception exp) {
      exp.printStackTrace ();
    }
  }
  
  public static void main2 (String[] args) {

    try {
      String value = "$a";
      final String StringTokenPattern = "([a-zA-Z0-9_<=> .]+)|(\\$)([a-zA-Z0-9_.]+)";
      Pattern p = Pattern.compile (StringTokenPattern);
      Matcher m = p.matcher (value);
      while (m.find())
      {
          String token = m.group ();
          System.err.println(token);
      }
    } catch (Exception exp) {
      exp.printStackTrace ();
    }
  }
}
