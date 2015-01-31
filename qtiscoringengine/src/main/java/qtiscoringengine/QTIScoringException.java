/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

public class QTIScoringException extends Exception
{
  public QTIScoringException (String msg) {
    super (msg);
  }

  public QTIScoringException (String message, Throwable th) {
    super (message, th);
  }

  public QTIScoringException (Throwable th) {
    super (th);
  }

  // Shiva: need to revisit this constructor. I added this constructor so
  // as to unify all exceptions under QTIScoringException.
  // Shiva Jan 27th: I am going to remove the todo from this. Future code
  // reviewers will have to revisit this topic
  public QTIScoringException (int code, String coderMessage) {
    super (getDescription (code) + " ; " + coderMessage);
  }

  public static String getDescription (int code) {
    switch (code) {
    case 0:
      return "unknown";
    case 1:
      return "Unexpected Parameter Type";
    case 2:
      return "Unacceptable parameter value";
    case 3:
      return "maybe there is a gap in the code";
    default:
      return "Uknown error code";
    }
  }
}
