/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine.itembody;

import java.util.List;

import org.jdom2.Element;

public abstract class Interaction
{
  public enum ItemType
  {
    None, choiceInteraction, associateInteraction, drawingInteraction, extendedTextInteraction, gapMatchInteraction,
    graphicAssociateInteraction, graphicGapMatchInteraction, graphicOrderInteraction, hotspotInteraction,
    hottextInteraction, matchInteraction, mediaInteraction, orderInteraction, selectPointInteraction, sliderInteraction,
    uploadInteraction,
  }

  protected Element  _node;
  protected ItemType _type;
  protected String   _responseIdentifier;

  public String getResponseIdentifier ()
  {
    return _responseIdentifier;
  }

  Interaction (Element node, String responseIdentifier, ItemType type)
  {
    _node = node;
    _responseIdentifier = responseIdentifier;
    _type = type;
  }

  // internal static Interaction FromXml(XmlNode node, XmlNamespaceManager
  // nsmgr, ValidationLog log);

  public ItemType getInteractionType ()
  {
    return _type;
  }

  public abstract List<String> getAnswers ();

  public int getNumberOfAnswersInResponse () {
    return -1;
  }
}
