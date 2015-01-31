/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathFactory;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

class AreaMapping extends Mapping
{
  private List<AreaMapEntry> _entries;

  private AreaMapping (List<AreaMapEntry> entryList, double fDefault, double fUpper, double fLower, Element node) {
    super (fDefault, fUpper, fLower, node);
    _entries = entryList;
  }

  static AreaMapping fromXML (Element node, XmlNamespaceManager nsmgr, ValidationLog log) throws Exception {
    if (node == null)
      return null;

    String defVal = node.getAttributeValue ("defaultValue"); // required!
    String upper = node.getAttributeValue ("upperBound");
    String lower = node.getAttributeValue ("lowerBound");

    _Ref<Float> fDefault = new _Ref<> (Float.MIN_VALUE);
    _Ref<Float> fUpper = new _Ref<> (Float.MAX_VALUE);
    _Ref<Float> fLower = new _Ref<> (Float.MIN_VALUE);

    if (!JavaPrimitiveUtils.floatTryParse (defVal, fDefault)) { // this is
                                                                // required
      log.addMessage (node, "Could not parse float value for defaultValue. Value attempted: '" + defVal + "'");
      fDefault.set (Float.MIN_VALUE);
    }

    if (!StringUtils.isEmpty (upper))
      if (!JavaPrimitiveUtils.floatTryParse (upper, fUpper))
        log.addMessage (node, "Could not parse float value for upperBound. Value attempted: '" + upper + "'");
    if (!StringUtils.isEmpty (lower))
      if (!JavaPrimitiveUtils.floatTryParse (lower, fLower))
        log.addMessage (node, "Could not parse float value for lowerBound. Value attempted: '" + lower + "'");

    List<Element> entries = new XmlElement (node).selectNodes (QTIXmlConstants.AreaMapEntry, nsmgr);
    List<AreaMapEntry> entryList = new ArrayList<AreaMapEntry> ();

    for (Element me : entries) {
      AreaMapEntry e = AreaMapEntry.fromXML (me, nsmgr, log);
      if (e != null)
        entryList.add (e);
    }

    return new AreaMapping (entryList, fDefault.get (), fUpper.get (), fLower.get (), node);
  }

  @Override
  boolean validate (QTIRubric rubric, ValidationLog log) {
    boolean ok = true;
    if (_entries.size () == 0) {
      log.addMessage (_node, "Area Mapping did not contain any entries, at least 1 is required");
      switch (log.getValidationRigor ()) {
      case None:
        break;
      default:
        ok = false;
        break;
      }
    }
    if (_defaultValue == null) {
      log.addMessage (_node, "Required node defaultValue was not specified");
      switch (log.getValidationRigor ()) {
      case None:
        break;
      default:
        ok = false;
        break;
      }
    } else if (_defaultValue.getIsError ()) {
      log.addMessage (_node, _defaultValue.getErrorMessage ());
      switch (log.getValidationRigor ()) {
      case None:
        break;
      default:
        ok = false;
        break;
      }
    } else if (_defaultValue.getValue () == -Float.MAX_VALUE) {
      log.addMessage (_node, "Could not parse defaultValue");
      switch (log.getValidationRigor ()) {
      case None:
        break;
      default:
        ok = false;
        break;
      }
    }
    return ok;
  }

  @Override
  MapEntry findEntry (DataElement value) {
    DEPoint point = (DEPoint) value;

    for (AreaMapEntry entry : _entries) {
      if ((entry.getArea ().getIsInside (point)).getBooleanValue ())
        return entry;
    }
    return null;
  }
}
