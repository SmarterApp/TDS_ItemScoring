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

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;
import qtiscoringengine.ValidationLog;

public class MatchInteraction extends MatchingInteraction
{
  private List<SimpleAssociableChoice> _simpleAssociableChoices1;
  private List<SimpleAssociableChoice> _simpleAssociableChoices2;
  private boolean _shuffle;
  private int _maxAssociations;

  private MatchInteraction(Element elem, List<SimpleAssociableChoice> sacList1, List<SimpleAssociableChoice> sacList2,
                           String responseIdentifier, boolean shuffle, int maxAssociations)
  {
    super(elem, responseIdentifier, ItemType.matchInteraction);
      _simpleAssociableChoices1 = sacList1;
      _simpleAssociableChoices2 = sacList2;
      _shuffle = shuffle;
      _maxAssociations = maxAssociations;
  }

  static MatchInteraction fromXml(Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
      String respIdentifier = element.getAttributeValue(ItemBodyConstants.ResponseIdentifier);
      String shuffle = element.getAttributeValue(ItemBodyConstants.Shuffle);
      String maxAssociations = element.getAttributeValue(ItemBodyConstants.MaxAssociations);

      //XmlNodeList simpleMatchSetNodes = element.SelectNodes(ItemBodyConstants.SimpleMatchSet, nsmgr);
      List<Element> simpleMatchSetNodes = new XmlElement(element).selectNodes(ItemBodyConstants.SimpleMatchSet, nsmgr);
      List<SimpleAssociableChoice> sacList1 = new ArrayList<SimpleAssociableChoice>();
      List<SimpleAssociableChoice> sacList2 = new ArrayList<SimpleAssociableChoice>();

      if (simpleMatchSetNodes.size() != 2)
      {
          log.addMessage(element, "MatchInteraction requires exactly 2 simpleMatchSet nodes, there are " + simpleMatchSetNodes.size());
          return null;
      }

      //go through the first simpleMatchSet node and get the simpleAssociableChoice nodes
      List<Element> assocChoiceNodes = new XmlElement(simpleMatchSetNodes.get (0)).selectNodes(ItemBodyConstants.SimpleAssociableChoice, nsmgr);
      for (Element elem : assocChoiceNodes)
      {
          SimpleAssociableChoice sac = SimpleAssociableChoice.fromXml(elem, nsmgr, log);
          if (sac != null) sacList1.add(sac);
      }

      //go through the second simpleMatchSet node and get the simpleAssociableChoice nodes
      assocChoiceNodes = new XmlElement(simpleMatchSetNodes.get (1)).selectNodes(ItemBodyConstants.SimpleAssociableChoice, nsmgr);
      for (Element elem : assocChoiceNodes)
      {
          SimpleAssociableChoice sac = SimpleAssociableChoice.fromXml(elem, nsmgr, log);
          if (sac != null) sacList2.add(sac);
      }

      _Ref<Boolean> shffle = new _Ref<>(false);
      if (!JavaPrimitiveUtils.boolTryParse(shuffle, shffle))
      {
          log.addMessage(element, "Could not parse value '" + shuffle + "' to a boolean");
          return null; // required attribute, so return null
      }

      _Ref<Integer> ma = new _Ref<>(Integer.MIN_VALUE);
      if (!JavaPrimitiveUtils.intTryParse(maxAssociations, ma))
      {
          log.addMessage(element, "Could not parse value '" + maxAssociations + "' to an int");
          return null; // required attribute, so return null
      }

      return new MatchInteraction(element, sacList1, sacList2, respIdentifier, shffle.get(), ma.get());
  }

  @Override
  public List<String> getAnswers()
  {
      List<String> answers = new ArrayList<String>();
      for (SimpleAssociableChoice sac : _simpleAssociableChoices1)
          answers.add(sac.getAnswer());
      return answers;
  }
  
  @Override
  public List<String> getMatchValues()
  {
      List<String> answers = new ArrayList<String>();
      for (SimpleAssociableChoice sac : _simpleAssociableChoices2)
          answers.add(sac.getAnswer());
      return answers;
  }
}
