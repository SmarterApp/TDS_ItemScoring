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

public class GapText extends SimpleAssociableChoice
{
//todo: implement all optional attributes

  private GapText(String identifier, int matchMax)
  {super(identifier, matchMax);
  }

   static /* new */ GapText fromXml(Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
      String identifier = element.getAttributeValue(ItemBodyConstants.Identifier);
      String matchMax = element.getAttributeValue(ItemBodyConstants.MatchMax);

      _Ref<Integer> mm = new _Ref<>(Integer.MIN_VALUE);
      if (!JavaPrimitiveUtils.intTryParse(matchMax, mm))
      {
          log.addMessage(element, "Could not parse value '" + matchMax + "' to an int");
          return null; // required attribute, so return null
      }

      return new GapText(identifier, mm.get());
  }
}
