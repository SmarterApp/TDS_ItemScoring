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

import org.jdom2.Document;
import org.jdom2.Element;

import qtiscoringengine.ValidationLog;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;
import AIR.Common.xml.XmlReader;

public class QTIItemBody
{
  private java.util.List<Interaction> _interactions;
  private String                      _source = "";

  public boolean isEmpty ()
  {
    return _interactions == null || _interactions.size () == 0;
  }

  private QTIItemBody (String source, List<Interaction> interactions)
  {
    _source = source;
    _interactions = interactions;
  }

  public static QTIItemBody fromXml (String source, XmlReader reader, ValidationLog log)
  {
    if (reader == null)
      return null;

    Document doc = new Document ();
    try
    {
      doc = reader.getDocument ();
    } catch (Exception e)
    {
      log.addMessage (null, "Failed to load document. Message: " + e.getMessage ());
      return null;
    }

    // Create an XmlNamespaceManager for resolving namespaces.
    XmlNamespaceManager nsmgr = new XmlNamespaceManager (/* doc.NameTable */);
    nsmgr.addNamespace ("qti", "http://www.imsglobal.org/xsd/imsqti_v2p1");

    Element foo = doc.getRootElement ();
    Element itemBody = new XmlElement (foo).selectSingleNode (ItemBodyConstants.ItemBody, nsmgr);
    if (itemBody == null)
    {
      log.addMessage (null, "No ItemBody node is present in the rubric");
      return new QTIItemBody (source, null);
    }
    List<Interaction> interactionList = new ArrayList<Interaction> ();

    List<Element> choiceInteractions = new XmlElement (itemBody).selectNodes (ItemBodyConstants.ChoiceInteraction, nsmgr);
    for (Element node : choiceInteractions)
    {
      ChoiceInteraction ci = ChoiceInteraction.fromXml (node, nsmgr, log);
      if (ci != null)
        interactionList.add (ci);
    }

    List<Element> associateInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.AssociateInteraction, nsmgr);
    for (Element node : associateInteraction)
    {
      AssociateInteraction ai = AssociateInteraction.fromXml (node, nsmgr, log);
      if (ai != null)
        interactionList.add (ai);
    }

    List<Element> gapMatchInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.GapMatchInteraction, nsmgr);
    for (Element node : gapMatchInteraction)
    {
      GapMatchInteraction gmi = GapMatchInteraction.fromXml (node, nsmgr, log);
      if (gmi != null)
        interactionList.add (gmi);
    }

    List<Element> graphAssocInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.GraphicAssociateInteraction, nsmgr);
    for (Element node : graphAssocInteraction)
    {
      GraphicAssociateInteraction gai = GraphicAssociateInteraction.fromXml (node, nsmgr, log);
      if (gai != null)
        interactionList.add (gai);
    }

    List<Element> graphGapMatchInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.GraphicGapMatchInteraction, nsmgr);
    for (Element node : graphGapMatchInteraction)
    {
      GraphicGapMatchInteraction ggmi = GraphicGapMatchInteraction.fromXml (node, nsmgr, log);
      if (ggmi != null)
        interactionList.add (ggmi);
    }

    List<Element> graphOrderInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.GraphicOrderInteraction, nsmgr);
    for (Element node : graphOrderInteraction)
    {
      GraphicOrderInteraction goi = GraphicOrderInteraction.fromXml (node, nsmgr, log);
      if (goi != null)
        interactionList.add (goi);
    }

    List<Element> hotspotInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.HotspotInteraction, nsmgr);
    for (Element node : hotspotInteraction)
    {
      HotspotInteraction hsi = HotspotInteraction.fromXml (node, nsmgr, log);
      if (hsi != null)
        interactionList.add (hsi);
    }

    List<Element> hottextInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.HottextInteraction, nsmgr);
    for (Element node : hottextInteraction)
    {
      HottextInteraction hti = HottextInteraction.fromXml (node, nsmgr, log);
      if (hti != null)
        interactionList.add (hti);
    }

    List<Element> matchInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.MatchInteraction, nsmgr);
    for (Element node : matchInteraction)
    {
      MatchInteraction mi = MatchInteraction.fromXml (node, nsmgr, log);
      if (mi != null)
        interactionList.add (mi);
    }

    List<Element> orderInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.OrderInteraction, nsmgr);
    for (Element node : orderInteraction)
    {
      OrderInteraction oi = OrderInteraction.fromXml (node, nsmgr, log);
      if (oi != null)
        interactionList.add (oi);
    }

    List<Element> selectPointInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.SelectPointInteraction, nsmgr);
    for (Element node : selectPointInteraction)
    {
      SelectPointInteraction spi = SelectPointInteraction.fromXml (node, nsmgr, log);
      if (spi != null)
        interactionList.add (spi);
    }

    List<Element> sliderInteraction = new XmlElement (itemBody).selectNodes (ItemBodyConstants.SliderInteraction, nsmgr);
    for (Element node : sliderInteraction)
    {
      SliderInteraction si = SliderInteraction.fromXml (node, nsmgr, log);
      if (si != null)
        interactionList.add (si);
    }

    if (interactionList.size () == 0)
    {
      log.addMessage (null, "No valid Interaction nodes found in ItemBody node");
      return new QTIItemBody (source, null);
    }

    return new QTIItemBody (source, interactionList);
  }

  public List<String> getAnswers (int i)
  {
    return _interactions.get (i).getAnswers ();
  }

  public String getResponseIdentifier (int i)
  {
    return _interactions.get (i).getResponseIdentifier ();
  }

  public Interaction.ItemType getItemType (int i)
  {
    return _interactions.get (i).getInteractionType ();
  }

  public int getNumberOfAnswersInResponse (int i)
  {
    return _interactions.get (i).getNumberOfAnswersInResponse ();
  }

  public Interaction getInteraction (int i)
  {
    return _interactions.get (i);
  }

  List<Interaction> getInteractions ()
  {
    return _interactions;
  }

  public int getNumberOfInteractions ()
  {
    return _interactions.size ();
  }
}
