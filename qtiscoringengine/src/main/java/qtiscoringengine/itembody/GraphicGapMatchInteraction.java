/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine.itembody;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.ValidationLog;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class GraphicGapMatchInteraction extends MatchingInteraction
{
  private List<GapImg>            _gapImgs;
  private List<AssociableHotspot> _associableHotspots;

  private GraphicGapMatchInteraction (Element elem, List<AssociableHotspot> hotspots, List<GapImg> giList, String responseIdentifier) {
    super (elem, responseIdentifier, ItemType.graphicGapMatchInteraction);
    _gapImgs = giList;
    _associableHotspots = hotspots;
  }

  static GraphicGapMatchInteraction fromXml (Element element, XmlNamespaceManager nsmgr, ValidationLog log) {
    String respIdentifier = element.getAttributeValue (ItemBodyConstants.ResponseIdentifier);
    XmlElement elementToXml = new XmlElement (element);

    List<Element> assocHotspotNodes = elementToXml.selectNodes (ItemBodyConstants.AssociableHotspot, nsmgr);
    List<AssociableHotspot> hsList = new ArrayList<AssociableHotspot> ();
    for (Element elem : assocHotspotNodes) {
      AssociableHotspot hs = AssociableHotspot.fromXml (elem, nsmgr, log);
      if (hs != null)
        hsList.add (hs);
    }
    if (hsList.size () == 0) {
      log.addMessage (element, "The GraphicGapMatchInteraction node did not contain any associableHotspot nodes, at least 1 is required");
      return null;
    }

    List<Element> gapImgNodes = elementToXml.selectNodes (ItemBodyConstants.GapImg, nsmgr);
    List<GapImg> giList = new ArrayList<GapImg> ();
    for (Element elem : gapImgNodes) {
      GapImg gi = GapImg.fromXml (elem, nsmgr, log);
      if (gi != null)
        giList.add (gi);
    }
    if (giList.size () == 0) {
      log.addMessage (element, "The GraphicGapMatchInteraction node did not contain any GapImg nodes, at least 1 is required");
      return null;
    }

    return new GraphicGapMatchInteraction (element, hsList, giList, respIdentifier);
  }

  @Override
  public List<String> getAnswers () {
    List<String> answers = new ArrayList<String> ();
    for (GapImg gi : _gapImgs)
      answers.add (gi.getAnswer ());

    return answers;
  }

  @Override
  public List<String> getMatchValues () {
    List<String> answers = new ArrayList<String> ();
    for (AssociableHotspot hs : _associableHotspots)
      answers.add (hs.getAnswer ());
    return answers;
  }
}
