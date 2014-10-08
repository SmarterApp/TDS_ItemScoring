/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine.itembody;

class ItemBodyConstants
{
  static final String ItemBody = "qti:itemBody";

  //interaction types namespaces
  static final String ChoiceInteraction = "qti:choiceInteraction";
  static final String AssociateInteraction = "qti:associateInteraction";
  static final String GapMatchInteraction = "qti:gapMatchInteraction";
  static final String GraphicGapMatchInteraction = "qti:graphicGapMatchInteraction";
  static final String GraphicAssociateInteraction = "qti:graphicAssociateInteraction";
  static final String GraphicOrderInteraction = "qti:graphicOrderInteraction";
  static final String HotspotInteraction = "qti:hotspotInteraction";
  static final String HottextInteraction = "qti:hottextInteraction";
  static final String MatchInteraction = "qti:matchInteraction";
  static final String OrderInteraction = "qti:orderInteraction";
  static final String SelectPointInteraction = "qti:selectPointInteraction";
  static final String SliderInteraction = "qti:sliderInteraction";
  
  //answer types namespaces
  static final String SimpleChoice = "qti:simpleChoice";
  static final String SimpleAssociableChoice = "qti:simpleAssociableChoice";
  static final String GapText = "qti:gapText";
  static final String GapImg = "qti:gapImg";
  static final String Gap = "gap"; // not in namespace qti
  static final String AssociableHotspot = "qti:associableHotspot";
  static final String HotspotChoice = "qti:hotspotChoice";
  static final String Hottext = "qti:hottext";
  static final String SimpleMatchSet = "qti:simpleMatchSet";
  static final String Object = "qti:object";

  //attributes
  static final String ResponseIdentifier = "responseIdentifier";
  static final String MaxChoices = "maxChoices";
  static final String Shuffle = "shuffle";
  static final String MaxAssociations = "maxAssociations";
  static final String Identifier = "identifier";
  static final String MatchMax = "matchMax";
  static final String Shape = "shape";
  static final String Coords = "coords";
  static final String LowerBound = "lowerBound";
  static final String UpperBound = "upperBound";
}
