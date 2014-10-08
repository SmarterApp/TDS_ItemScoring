/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine.itembody;

import java.util.Arrays;
import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.ValidationLog;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlNamespaceManager;

class SliderInteraction extends Interaction
{
  private int _lowerBound;
  private int _upperBound;

  private SliderInteraction (Element elem, String responseIdentifier, int lowerBound, int upperBound)
  {
    super (elem, responseIdentifier, ItemType.sliderInteraction);
    _lowerBound = lowerBound;
    _upperBound = upperBound;
  }

  static SliderInteraction fromXml (Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    String respIdentifier = element.getAttributeValue (ItemBodyConstants.ResponseIdentifier);
    String lowerBound = element.getAttributeValue (ItemBodyConstants.LowerBound);
    String upperBound = element.getAttributeValue (ItemBodyConstants.UpperBound);

    _Ref<Integer> l = new _Ref<> (Integer.MIN_VALUE);
    if (!JavaPrimitiveUtils.intTryParse (lowerBound, l))
    {
      log.addMessage (element, "Could not parse value '" + lowerBound + "' to an int");
      return null; // required attribute, so return null
    }

    _Ref<Integer> u = new _Ref<> (Integer.MIN_VALUE);
    if (!JavaPrimitiveUtils.intTryParse (upperBound, u))
    {
      log.addMessage (element, "Could not parse value '" + upperBound + "' to an int");
      return null; // required attribute, so return null
    }

    return new SliderInteraction (element, respIdentifier, l.get (), u.get ());
  }

  @Override
  public List<String> getAnswers ()
  {
    return Arrays.asList (new String[] { Integer.toString (_lowerBound), Integer.toString (_upperBound) });
  }
}
