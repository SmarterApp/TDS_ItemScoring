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

public class AssociateInteraction extends Interaction
{
  private List<SimpleAssociableChoice> _simpleAssociableChoices;
  private int                          _maxAssociations;
  private boolean                      _shuffle;

  private AssociateInteraction (Element elem, List<SimpleAssociableChoice> scList, String responseIdentifier, int maxAssociations, boolean shuffle)
  {
    super (elem, responseIdentifier, ItemType.associateInteraction);
    _simpleAssociableChoices = scList;
    _maxAssociations = maxAssociations;
    _shuffle = shuffle;
  }

  static AssociateInteraction fromXml (Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    String respIdentifier = element.getAttributeValue (ItemBodyConstants.ResponseIdentifier);
    String maxAssociations = element.getAttributeValue (ItemBodyConstants.MaxAssociations);
    String shuffle = element.getAttributeValue (ItemBodyConstants.Shuffle);

    // XmlNodeList simpleAssocChoices =
    // element.SelectNodes(ItemBodyConstants.SimpleAssociableChoice, nsmgr);
    List<Element> simpleAssocChoices = new XmlElement (element).selectNodes (ItemBodyConstants.SimpleAssociableChoice, nsmgr);
    List<SimpleAssociableChoice> sacList = new ArrayList<SimpleAssociableChoice> ();
    for (Element scElem : simpleAssocChoices)
    {
      SimpleAssociableChoice sac = SimpleAssociableChoice.fromXml (scElem, nsmgr, log);
      if (sac != null)
        sacList.add (sac);
    }

    _Ref<Integer> ma = new _Ref<> (Integer.MIN_VALUE);
    if (!JavaPrimitiveUtils.intTryParse (maxAssociations, ma))
    {
      log.addMessage (element, "Could not parse value '" + maxAssociations + "' to an int");
      return null; // required attribute, so return null
    }

    _Ref<Boolean> shffle = new _Ref<> (false);
    if (!JavaPrimitiveUtils.boolTryParse (shuffle, shffle))
    {
      log.addMessage (element, "Could not parse value '" + shuffle + "' to a boolean");
      return null; // required attribute, so return null
    }

    return new AssociateInteraction (element, sacList, respIdentifier, ma.get (), shffle.get ());
  }

  @Override
  public List<String> getAnswers ()
  {
    List<String> answers = new ArrayList<String> ();
    for (SimpleAssociableChoice sc : _simpleAssociableChoices)
      answers.add (sc.getAnswer ());
    return answers;
  }

  @Override
  public int getNumberOfAnswersInResponse () {
    return _maxAssociations;
  }
}
