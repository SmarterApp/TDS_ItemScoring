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

class GapMatchInteraction extends MatchingInteraction
{
  enum GapType {
    GapText, GapImg
  };

  private List<GapText> _gapTexts;
  private List<GapImg>  _gapImgs;
  private List<Gap>     _gaps;
  private boolean       _shuffle;
  private GapType       _gaptype;

  public GapType getType () {
    return _gaptype;
  }

  private GapMatchInteraction (Element elem, List<GapText> gtList, List<Gap> gaps, List<GapImg> giList,
      String responseIdentifier, boolean shuffle, GapType type)

  {
    super (elem, responseIdentifier, ItemType.gapMatchInteraction);
    _gapTexts = gtList;
    _gapImgs = giList;
    _gaps = gaps;
    _shuffle = shuffle;
    _gaptype = type;
  }

  static GapMatchInteraction fromXml (Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    String respIdentifier = element.getAttributeValue (ItemBodyConstants.ResponseIdentifier);
    String shuffle = element.getAttributeValue (ItemBodyConstants.Shuffle);

    // XmlNodeList gapTextNodes = element.SelectNodes(ItemBodyConstants.GapText,
    // nsmgr);
    List<Element> gapTextNodes = new XmlElement (element).selectNodes (ItemBodyConstants.GapText, nsmgr);
    List<GapText> gtList = new ArrayList<GapText> ();
    for (Element elem : gapTextNodes)
    {
      GapText gt = GapText.fromXml (elem, nsmgr, log);
      if (gt != null)
        gtList.add (gt);
    }

    // XmlNodeList gapImgNodes = element.SelectNodes(ItemBodyConstants.GapImg,
    // nsmgr);
    List<Element> gapImgNodes = new XmlElement (element).selectNodes (ItemBodyConstants.GapImg, nsmgr);
    List<GapImg> giList = new ArrayList<GapImg> ();
    for (Element elem : gapImgNodes)
    {
      GapImg gi = GapImg.fromXml (elem, nsmgr, log);
      if (gi != null)
        giList.add (gi);
    }

    if (gtList.size () == 0 && giList.size () == 0)
    {
      log.addMessage (element, "The GapMatchInteraction node did not contain any GapText or GapImg nodes, at least 1 is required");
      return null;
    }

    GapType type = GapType.GapImg;
    if (gtList.size () > 0)
      type = GapType.GapText;

    List<Gap> gaps = new ArrayList<Gap> ();
    // XmlNodeList gapNodes =
    // element.GetElementsByTagName(ItemBodyConstants.Gap);
    List<Element> gapNodes = new XmlElement (element).getElementsByTagName (ItemBodyConstants.Gap);
    for (Element gapNode : gapNodes)
    {
      Gap gap = Gap.fromXml (gapNode, nsmgr, log);
      if (gap != null)
        gaps.add (gap);
    }
    // todo: This could be a few other things. Need to add those and check there
    // is at least 1 node of
    // any of those types. Is it even required there is at least 1?
    if (gaps.size () == 0)
    {
      log.addMessage (element, "The GapMatchInteraction node did not contain any Gap nodes, at least 1 is required");
      return null;
    }

    _Ref<Boolean> shffle = new _Ref<Boolean> (false);
    if (!JavaPrimitiveUtils.boolTryParse (shuffle, shffle))
    {
      log.addMessage (element, "Could not parse value '" + shuffle + "' to a boolean");
      return null; // required attribute, so return null
    }

    return new GapMatchInteraction (element, gtList, gaps, giList, respIdentifier, shffle.get (), type);
  }

  @Override
  public List<String> getAnswers ()
  {
    List<String> answers = new ArrayList<String> ();
    if (_gaptype == GapType.GapText)
    {
      for (GapText gt : _gapTexts)
        answers.add (gt.getAnswer ());
    }
    else
    {
      for (GapImg gi : _gapImgs)
        answers.add (gi.getAnswer ());
    }
    return answers;
  }

  @Override
  public List<String> getMatchValues ()
  {
    List<String> answers = new ArrayList<String> ();
    for (Gap g : _gaps)
      answers.add (g.getAnswer ());
    return answers;
  }
}
