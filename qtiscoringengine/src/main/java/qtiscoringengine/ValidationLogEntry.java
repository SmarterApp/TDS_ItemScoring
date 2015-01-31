/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.List;

import org.jdom2.Content;
import org.jdom2.Element;

import AIR.Common.Utilities.TDSStringUtils;
import AIR.Common.xml.XmlElement;

class ValidationLogEntry
{
  private Element _node    = null;
  private String  _message = "";

  ValidationLogEntry (Element node, String msg) {
    _node = node;
    _message = msg;
  }

  public Element getNode () {
    return _node;

  }

  public String getMessage () {
    return _message;
  }

  // / Turn the ValidationLogEntry into a row of an HTML table with the
  // opening and closing tr and td tags included

  String writeHtmlTableRow () {
    int numRows = 1;
    if (_node != null)
      numRows += getChildNodeCount (_node.getChildren ()) + 1;
    // this implementation might change. What if there is an error on the
    // root node? Then
    // it would print the entire XML. Maybe change it to only print the one
    // node and no child
    // nodes.

    // using textarea is the best/easiest way I've found to keep the XML
    // exactly as it appears
    // and keep the indentation/structure the same in the HTML table. Using
    // WebUtility.HtmlEncode
    // removes the formatting and indentation of the XML.

    // Note: We set the number of rows and columns for each textarea. The
    // column number is hardcoded.
    // We also make the border invisible.

    return TDSStringUtils.format ("<tr><td><textarea readonly rows=" + "\"" + numRows + "\" cols=\"125\" style=" + "\"" + "border:none;\"" + ">{0}</textarea></td><td>{1}</td></tr>",
        QTIUtility.nodeToString (_node), _message);
  }

  private int getChildNodeCount (List<Element> childnodes) {
    if (childnodes == null)
      return 0;
    int cnt = 0;
    for (Content node : childnodes)
      cnt += getChildNodeCount (new XmlElement (node).getChildNodes ());
    return cnt + childnodes.size ();
  }
}
