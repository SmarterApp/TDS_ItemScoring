/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import org.jdom2.Element;

import AIR.Common.xml.XmlNamespaceManager;

public class AreaMapEntry extends MapEntry
{
  private Area _area;

  private AreaMapEntry (Area area, _DEFloat value, Element node) {
    super (value, node);
    _area = area;
  }

  public Area getArea () {
    return _area;
  }

  public static AreaMapEntry fromXML (Element node, XmlNamespaceManager nsmgr, ValidationLog log) throws QTIScoringException {
    String val = node.getAttribute ("mappedValue").getValue ();

    DataElement de = DataElement.create (val, BaseType.Float);
    if (de.getIsError ())
      throw new QTIScoringException (de.getErrorMessage ());
    _DEFloat floatVal = (_DEFloat) de;

    try {
      // this will throw an exception if anything is wrong
      Area area = Area.fromXML (node, nsmgr); //
      return new AreaMapEntry (area, floatVal, node);
    } catch (Exception e) {
      log.addMessage (node, e.getMessage ());
      return null;
    }
  }

  public boolean validate (QTIRubric rubric, ValidationLog log) {
    boolean ok = true;
    if (_value == null) {
      ok = false;
      log.addMessage (_node, "Required value mappedValue is not specified");
    } else if (_value.getIsError ()) {
      ok = false;
      log.addMessage (_node, "Error with mappedValue: " + _value.getErrorMessage ());
    }
    return ok;
  }
}
