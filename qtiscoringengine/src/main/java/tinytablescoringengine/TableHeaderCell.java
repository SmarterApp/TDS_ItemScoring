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

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;

public class TableHeaderCell extends TableCell
{

  private String _name;

  public TableHeaderCell (String name, String content) {
    super (content);
    _isHeader = true;
    _name = name;
  }

  public String getName () {
    return _name;
  }

  public Element toXml (Document doc) {
    Element th = new Element ("th");
    if (!StringUtils.isEmpty (_name)) {
      th.setAttribute ("id", _name);
    }
    if (!StringUtils.isEmpty (_content))
      th.setText (_content);
    return th;
  }
}
