/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine.itembody;

import org.jdom2.Element;

import qtiscoringengine.ValidationLog;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlNamespaceManager;

public class SimpleAssociableChoice extends SimpleChoice
{
  protected int _matchMax;

  // todo: implement matchMax functionality

  protected SimpleAssociableChoice (String identifier, int matchMax)
  {
    super (identifier);
    _matchMax = matchMax;
  }

  static/* new */SimpleAssociableChoice fromXml (Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    String identifier = element.getAttributeValue (ItemBodyConstants.Identifier);
    String matchMax = element.getAttributeValue (ItemBodyConstants.MatchMax);

    _Ref<Integer> mm = new _Ref<> (Integer.MIN_VALUE);
    if (!JavaPrimitiveUtils.intTryParse (matchMax, mm))
    {
      log.addMessage (element, "Could not parse value '" + matchMax + "' to an int");
      return null; // required attribute, so return null
    }

    return new SimpleAssociableChoice (identifier, mm.get ());
  }
}
