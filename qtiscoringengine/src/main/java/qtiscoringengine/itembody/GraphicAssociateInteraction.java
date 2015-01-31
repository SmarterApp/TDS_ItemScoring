/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine.itembody;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.ValidationLog;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class GraphicAssociateInteraction extends Interaction
{
  private List<AssociableHotspot> _associableHotSpots;
  private int _maxAssociations;

  private GraphicAssociateInteraction(Element elem, List<AssociableHotspot> ahsList, String responseIdentifier, int maxAssociations)
      
  {
    super(elem, responseIdentifier, ItemType.graphicAssociateInteraction);
      _associableHotSpots = ahsList;
      _maxAssociations = maxAssociations;
  }

  static GraphicAssociateInteraction fromXml(Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
      String respIdentifier = element.getAttributeValue(ItemBodyConstants.ResponseIdentifier);
      String maxAssociations = element.getAttributeValue(ItemBodyConstants.MaxAssociations);

      List<Element> assocHotspotNodes = new XmlElement(element).selectNodes(ItemBodyConstants.AssociableHotspot, nsmgr);
      List<AssociableHotspot> ahsList = new ArrayList<AssociableHotspot>();
      for (Element scElem : assocHotspotNodes)
      {
          AssociableHotspot ahs = AssociableHotspot.fromXml(scElem, nsmgr, log);
          if (ahs != null) ahsList.add(ahs);
      }
      if (ahsList.size() == 0)
      {
          log.addMessage(element, "No associableHotSpot nodes were present, at least 1 is required");
          return null;
      }

      _Ref<Integer> ma = new _Ref<>(Integer.MIN_VALUE);
      if (!JavaPrimitiveUtils.intTryParse(maxAssociations, ma))
      {
          log.addMessage(element, "Could not parse value '" + maxAssociations + "' to an int");
          return null; // required attribute, so return null
      }

      return new GraphicAssociateInteraction(element, ahsList, respIdentifier, ma.get());
  }

  public  List<String> getAnswers()
  {
      List<String> answers = new ArrayList<String>();
      for (AssociableHotspot ahs : _associableHotSpots)
          answers.add(ahs.getAnswer());
      return answers;
  }
  @Override
  public  int getNumberOfAnswersInResponse() { return _maxAssociations; }
}
