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

public class HottextInteraction extends Interaction
{
  private List<Hottext> _hottexts;
  private int _maxChoices;

  private HottextInteraction(Element elem, List<Hottext> htList, String responseIdentifier, int maxChoices)
  {
    super(elem, responseIdentifier, ItemType.hottextInteraction);
      _hottexts = htList;
      _maxChoices = maxChoices;
  }

  static HottextInteraction fromXml(Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
      String respIdentifier = element.getAttributeValue(ItemBodyConstants.ResponseIdentifier);
      String maxChoices = element.getAttributeValue(ItemBodyConstants.MaxChoices);

      //XmlNodeList hotTextNodes = element.SelectNodes(ItemBodyConstants.Hottext, nsmgr);
      List<Element> hotTextNodes = new XmlElement(element).selectNodes(ItemBodyConstants.Hottext, nsmgr);
      List<Hottext> htList = new ArrayList<Hottext>();
      for (Element htElem : hotTextNodes)
      {
          Hottext ht = Hottext.fromXml(htElem, nsmgr, log);
          if (ht != null) htList.add(ht);
      }

      _Ref<Integer> mc = new _Ref<>(Integer.MIN_VALUE);
      if (!JavaPrimitiveUtils.intTryParse(maxChoices, mc))
      {
          log.addMessage(element, "Could not parse value '" + maxChoices + "' to an int");
          return null; // required attribute, so return null
      }

      return new HottextInteraction(element, htList, respIdentifier, mc.get());
  }

  @Override
  public  List<String> getAnswers()
  {
      List<String> answers = new ArrayList<String>();
      for (Hottext ht : _hottexts)
          answers.add(ht.getAnswer());
      return answers;
  }
  @Override
  public  int getNumberOfAnswersInResponse() { return _maxChoices; }
}
