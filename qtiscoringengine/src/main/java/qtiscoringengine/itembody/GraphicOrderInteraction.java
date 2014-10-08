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

public class GraphicOrderInteraction extends Interaction
{
  private List<HotspotChoice> _hotspotChoices;
  private Integer             _maxChoices = null;

  private GraphicOrderInteraction (Element elem, List<HotspotChoice> hotspots, String responseIdentifier, Integer maxChoices)
  {
    super (elem, responseIdentifier, ItemType.graphicOrderInteraction);
    _hotspotChoices = hotspots;
    _maxChoices = maxChoices;
  }

  static GraphicOrderInteraction fromXml (Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    String respIdentifier = element.getAttributeValue (ItemBodyConstants.ResponseIdentifier);
    String maxChoices = element.getAttributeValue (ItemBodyConstants.MaxChoices);

    // XmlNodeList hotspotChoiceNodes =
    // element.SelectNodes(ItemBodyConstants.HotspotChoice, nsmgr);
    List<Element> hotspotChoiceNodes = new XmlElement (element).selectNodes (ItemBodyConstants.HotspotChoice, nsmgr);
    List<HotspotChoice> hscList = new ArrayList<HotspotChoice> ();
    for (Element elem : hotspotChoiceNodes)
    {
      HotspotChoice hsc = HotspotChoice.fromXml (elem, nsmgr, log);
      if (hsc != null)
        hscList.add (hsc);
    }
    if (hscList.size () == 0)
    {
      log.addMessage (element, "The GraphicOrderInteraction node did not contain any hotspotChoice nodes, at least 1 is required");
      return null;
    }

    _Ref<Integer> mc = new _Ref<> (Integer.MIN_VALUE);
    Integer mcVal = null;
    if (JavaPrimitiveUtils.intTryParse (maxChoices, mc))
    {
      mcVal = mc.get ();
    }

    return new GraphicOrderInteraction (element, hscList, respIdentifier, mcVal);
  }

  @Override
  public List<String> getAnswers ()
  {
    List<String> answers = new ArrayList<String> ();
    for (HotspotChoice hsc : _hotspotChoices)
      answers.add (hsc.getAnswer ());

    return answers;
  }

  @Override
  public int getNumberOfAnswersInResponse () {
    // return _maxChoices ?? _hotspotChoices.size();
    return null != _maxChoices ? _maxChoices : _hotspotChoices.size ();
  }
}
