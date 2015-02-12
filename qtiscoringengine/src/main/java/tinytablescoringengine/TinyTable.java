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

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;

import AIR.Common.xml.XmlReader;

public class TinyTable
{

  public static TableObject create (String obj) throws TinyTableScoringException {
    if (StringUtils.isEmpty (obj))
      return null;

    try {
      StringReader sr = new StringReader (obj);
      XmlReader reader = new XmlReader (sr);
      Document doc = new Document ();
      doc = reader.getDocument ();
      return TableObject.create (doc);
    } catch (JDOMException | IOException ex) {
      throw new TinyTableScoringException (ex);
    }
  }

  public static String getColumn (String obj, String name) throws TinyTableScoringException {
    TableObject to = create (obj);
    if (to == null) {
      return "";
    }
    TableVector tv = to.getColumn (name);
    if (tv == null) {
      return "";
    }
    return tv.getXmlString ();
  }

  public static double getValueNumeric (String obj, int idx) throws TinyTableScoringException {
    TableObject to = create (obj);
    if (to.equals (null)) {
      return Double.NaN;
    }
    return to.getValueNumeric (idx);
  }

  public static String getHeaderRow (String obj) throws TinyTableScoringException {
    TableObject to = create (obj);
    if (to == null) {
      return "";
    }
    TableVector tv = to.getHeaderRow ();
    if (tv == null) {
      return "";
    }
    return tv.getXmlString ();
  }

}
