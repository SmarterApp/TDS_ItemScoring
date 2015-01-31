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
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

class PointInteractionObject
{
    int width;
    int height;
    PointInteractionObject(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
}

class SelectPointInteraction extends Interaction
{
      private PointInteractionObject _object;
      private int _maxChoices;

      private SelectPointInteraction(Element elem, PointInteractionObject obj, String responseIdentifier, int maxChoices)
      {
        super(elem, responseIdentifier, ItemType.selectPointInteraction);
          _object = obj;
          _maxChoices = maxChoices;
      }

      static SelectPointInteraction fromXml(Element element, XmlNamespaceManager nsmgr, ValidationLog log)
      {
          String respIdentifier = element.getAttributeValue(ItemBodyConstants.ResponseIdentifier);
          String maxChoices = element.getAttributeValue(ItemBodyConstants.MaxChoices);

          Element objectNode = new XmlElement(element).selectSingleNode(ItemBodyConstants.Object, nsmgr);
          if (objectNode == null)
          {
              log.addMessage(element, "The SelectPointInteraction node did not contain an object node, exactly 1 is required");
              return null;
          }

          String width = objectNode.getAttributeValue("width");
          String height = objectNode.getAttributeValue("height");

          _Ref<Integer> w = new _Ref<>(Integer.MIN_VALUE);
          if (!JavaPrimitiveUtils.intTryParse(width, w))
          {
              log.addMessage(element, "Could not parse value '" + width + "' to an int");
              return null; // required attribute, so return null
          }
          _Ref<Integer> h = new _Ref<>(Integer.MIN_VALUE);
          if (!JavaPrimitiveUtils.intTryParse(height, h))
          {
              log.addMessage(element, "Could not parse value '" + height + "' to an int");
              return null; // required attribute, so return null
          }
          PointInteractionObject poi = new PointInteractionObject(w.get(), h.get());

          _Ref<Integer> mc = new _Ref<>(Integer.MIN_VALUE);
          if (!JavaPrimitiveUtils.intTryParse(maxChoices, mc))
          {
              log.addMessage(element, "Could not parse value '" + maxChoices + "' to an int");
              return null; // required attribute, so return null
          }

          return new SelectPointInteraction(element, poi, respIdentifier, mc.get());
      }

      @Override
      public  List<String> getAnswers()
      {
          return Arrays.asList (new String[] { Integer.toString (_object.width), Integer.toString (_object.height) });
      }
}
