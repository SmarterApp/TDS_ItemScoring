/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinytablescoringengine;

import org.jdom2.Document;
import org.jdom2.Element;

public class TableCell extends TableObject
{
  public boolean   _isHeader = false;
  protected String _content  = "";

  public TableCell (String content) {
    _content = content;
  }

  public boolean getIsHeader () {
    return _isHeader;
  }

  public String getName () {
    return "";
  }

  @Override
  public Element toXml (Document doc) {
    Element td = new Element ("td");
    td.addContent (_content);
    return td;
  }
}
