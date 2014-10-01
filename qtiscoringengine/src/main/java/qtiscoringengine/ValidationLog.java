/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import AIR.Common.xml.XmlElement;

public class ValidationLog
{
  public enum Rigor {
    None, Some, Strict
  };

  private List<ValidationLogEntry> _log             = new ArrayList<ValidationLogEntry> ();
  private Rigor                    _validationRigor = Rigor.Some;
  private String                   _sourceFile      = "";

  public ValidationLog (String fileName)
  {
    this._sourceFile = fileName;
  }

  public void addMessage (Element node, String msg)
  {
    _log.add (new ValidationLogEntry (node, msg));
  }

  public Rigor getValidationRigor ()
  {
    return _validationRigor;
  }

  public void setValidationRigor (Rigor value) {
    _validationRigor = value;
  }

  public int getCount ()
  {
    return _log.size ();
  }

  public String OuterXML (int i)
  {
    if (_log.get (i).getNode () == null)
      return "Missing?";
    return new XmlElement(_log.get (i).getNode ()).getOuterXml();
  }

  public String Message (int i)
  {
    return _log.get (i).getMessage ();
  }

  public String GetLogHTML ()
  {
    String returnString = "";
    try (StringWriter writer = new StringWriter ())
    {
      writer.append (GetHTMLHeader ()).append ("\n");
      for (ValidationLogEntry entry : _log)
        writer.append (entry.writeHtmlTableRow ()).append ("\n");
      writer.append (GetHTMLClosing ()).append ("\n");
      returnString = writer.toString ();
    } catch (IOException e) {
      e.printStackTrace ();
    }
    return returnString;
  }

  private String GetHTMLHeader ()
  {
    // This has room for future improvements for formatting
    StringBuilder sb = new StringBuilder ();
    sb.append ("<!DOCTYPE html>").append ("\n");
    sb.append ("<html>").append ("\n");
    sb.append ("<body>").append ("\n");
    sb.append ("<p><h1> Validation Log</h1>").append ("\n");
    sb.append ("Source File: " + this._sourceFile + "</p>").append ("\n");
    sb.append ("<p>Rigor: " + _validationRigor.toString () + "</p>").append ("\n");
    sb.append ("<table border=\"1\">").append ("\n");
    // add the table headers
    sb.append ("<tr><th>Outer XML</th>").append ("\n");
    sb.append ("<th>Message</th></tr>").append ("\n");
    return sb.toString ();
  }

  private String GetHTMLClosing ()
  {
    // This has room for future improvements for formatting
    StringBuilder sb = new StringBuilder ();
    sb.append ("</table>").append ("\n");
    sb.append ("</html>").append ("\n");
    sb.append ("</body>").append ("\n");
    return sb.toString ();
  }
}
