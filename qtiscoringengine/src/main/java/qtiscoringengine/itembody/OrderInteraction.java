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

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import qtiscoringengine.ValidationLog;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class OrderInteraction extends Interaction
{
  private List<SimpleChoice> _simpleChoices;
  private Integer            _maxChoices;
  private boolean            _shuffle;

  // todo: optional attributes

  private OrderInteraction (Element elem, List<SimpleChoice> scList, String responseIdentifier, Integer maxChoices, boolean shuffle) {
    super (elem, responseIdentifier, ItemType.orderInteraction);
    _simpleChoices = scList;
    _maxChoices = maxChoices;
    _shuffle = shuffle;
  }

  static OrderInteraction fromXml (Element element, XmlNamespaceManager nsmgr, ValidationLog log) {
    String respIdentifier = element.getAttributeValue (ItemBodyConstants.ResponseIdentifier);
    String maxChoices = element.getAttributeValue (ItemBodyConstants.MaxChoices);
    String shuffle = element.getAttributeValue (ItemBodyConstants.Shuffle);
    
    List<Element> simpleChoices = new XmlElement (element).selectNodes (ItemBodyConstants.SimpleChoice, nsmgr);
    List<SimpleChoice> scList = new ArrayList<SimpleChoice> ();
    for (Element scElem : simpleChoices) {
      SimpleChoice sc = SimpleChoice.fromXml (scElem, nsmgr, log);
      if (sc != null)
        scList.add (sc);
    }

    _Ref<Integer> mc = new _Ref<> (Integer.MIN_VALUE);
    boolean hasMaxChoices = false;
    if (maxChoices != null && !StringUtils.isEmpty (maxChoices.trim ()))
      hasMaxChoices = true;
    // maxChoices isn't required, so dont try to parse it if it isn't specified
    if (hasMaxChoices && !JavaPrimitiveUtils.intTryParse (maxChoices, mc)) {
      log.addMessage (element, "Could not parse value '" + maxChoices + "' to an int");
    }
    Integer mcVal = null;
    if (hasMaxChoices)
      mcVal = mc.get ();

    _Ref<Boolean> shffle = new _Ref<> (false);
    if (!JavaPrimitiveUtils.boolTryParse (shuffle, shffle)) {
      log.addMessage (element, "Could not parse value '" + shuffle + "' to a boolean");
      return null; // required attribute, so return null
    }

    return new OrderInteraction (element, scList, respIdentifier, mcVal, shffle.get ());
  }

  @Override
  public List<String> getAnswers () {
    List<String> answers = new ArrayList<String> ();
    for (SimpleChoice sc : _simpleChoices)
      answers.add (sc.getAnswer ());
    return answers;
  }

  @Override
  public int getNumberOfAnswersInResponse () {
    return null != _maxChoices ? _maxChoices : _simpleChoices.size ();
  }
}
