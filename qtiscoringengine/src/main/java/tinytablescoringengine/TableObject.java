/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinytablescoringengine;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import AIR.Common.xml.XmlElement;

public abstract class TableObject
{

  public Element toXml () {
    return toXml (new Document ());
  }

  public abstract Element toXml (Document doc);

  public static TableObject create (Document doc) {
    Element node = doc.getRootElement ();
    switch (node.getName ()) {
    case "responseSpec":
      node = node.getChild ("responseTable");
      return Table.fromXml (node);
    case "responseTable":
      return Table.fromXml (node);
    case "tr":
      return TableVector.fromXml (node);
    default:
      return null;
    }
  }

  public String getXmlString () {
    return new XmlElement (toXml ()).getOuterXml ();
  }
  
  public TableVector getColumn (String name) {
    return null;
  }

  public TableVector getColumn (int i) {
    return null;
  }

  public double getValueNumeric (int idx) {
    return Double.NaN;
  }

  public TableVector getHeaderRow () {
    return null;
  }
}
