/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class VariableMapping extends Mapping
{
  private List<VariableMapEntry> _entries = new ArrayList<VariableMapEntry> ();

  private VariableMapping (List<VariableMapEntry> entryList, double fDefault, double fUpper, double fLower, Element node)
  {
    super (fDefault, fUpper, fLower, node);
    _entries = entryList;
  }

  @Override
  MapEntry findEntry (DataElement value)
  {
    for (VariableMapEntry entry : _entries)
    {
      if (entry.getKey ().equals (value))
        return entry;
    }
    return null;
  }

  // / <summary>
  // / find an entry based on the string value, with the option to ignore case
  // / </summary>
  // / <param name="identifier"></param>
  // / <param name="ignoreCase"></param>
  // / <returns></returns>
  MapEntry findEntry (String identifier, boolean ignoreCase)
  {
    for (VariableMapEntry entry : _entries)
    {
      if (ignoreCase)
      {
        if (identifier.equalsIgnoreCase ((entry.getKey ().getStringValue ())))
          return entry;
      }
      else if (identifier.equals (entry.getKey ().getStringValue ()))
        return entry;
    }
    return null;
  }

  static VariableMapping fromXML (Element node, BaseType bt, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    if (node == null)
      return null;

    String defVal = node.getAttributeValue ("defaultValue"); // required!
    String upper = node.getAttributeValue ("upperBound");
    String lower = node.getAttributeValue ("lowerBound");

    _Ref<Float> fDefault = new _Ref<> (-Float.MAX_VALUE);
    _Ref<Float> fUpper = new _Ref<> (Float.MAX_VALUE);
    _Ref<Float> fLower = new _Ref<> (-Float.MAX_VALUE);

    if (!JavaPrimitiveUtils.floatTryParse (defVal, fDefault))// this is required
                                                             // so return null
                                                             // if it fails
    {
      log.addMessage (node, "Could not parse float value for defaultValue. Value attempted: '" + defVal + "'");
      fDefault.set (-Float.MAX_VALUE);
    }
    if (!StringUtils.isEmpty (upper))
      if (!JavaPrimitiveUtils.floatTryParse (upper, fUpper))
        log.addMessage (node, "Could not parse float value for upperBound. Value attempted: '" + upper + "'");
    if (!StringUtils.isEmpty (lower))
      if (!JavaPrimitiveUtils.floatTryParse (lower, fLower))
        log.addMessage (node, "Could not parse float value for lowerBound. Value attempted: '" + lower + "'");

    // XmlNodeList entries = node.SelectNodes("qti:mapEntry",nsmgr);
    List<Element> entries = new XmlElement (node).selectNodes ("qti:mapEntry", nsmgr);
    List<VariableMapEntry> entryList = new ArrayList<VariableMapEntry> ();

    for (Element me : entries)
    {
      VariableMapEntry e = VariableMapEntry.FromXML (me, bt, log);
      if (e != null)
        entryList.add (e);
    }

    return new VariableMapping (entryList, fDefault.get (), fUpper.get (), fLower.get (), node);
  }

  @Override
  boolean validate (QTIRubric rubric, ValidationLog log)
  {
    boolean ok = true;
    if (_entries.size () == 0)
    {
      log.addMessage (_node, "Area Mapping did not contain any entries, at least 1 is required");
      switch (log.getValidationRigor ())
      {
      case None:
        break;
      default:
        ok = false;
        break;
      }
    }
    if (_defaultValue == null)
    {
      log.addMessage (_node, "Required node defaultValue was not specified");
      switch (log.getValidationRigor ())
      {
      case None:
        break;
      default:
        ok = false;
        break;
      }
    }
    else if (_defaultValue.getIsError ())
    {
      log.addMessage (_node, _defaultValue.getErrorMessage ());
      switch (log.getValidationRigor ())
      {
      case None:
        break;
      default:
        ok = false;
        break;
      }
    }
    else if (_defaultValue.getValue () == -Float.MAX_VALUE)
    {
      log.addMessage (_node, "Could not parse defaultValue");
      switch (log.getValidationRigor ())
      {
      case None:
        break;
      default:
        ok = false;
        break;
      }
    }
    return ok;
  }

}
