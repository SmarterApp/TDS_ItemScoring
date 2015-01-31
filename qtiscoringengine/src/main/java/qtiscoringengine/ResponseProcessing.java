/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class ResponseProcessing
{
  private List<ResponseRule> _responseRules;

  private ResponseProcessing () {
    _responseRules = new ArrayList<ResponseRule> ();
  }

  private void AddRule (ResponseRule rule) {
    _responseRules.add (rule);
  }

  static ResponseProcessing fromXml (Element responseProcessing, XmlNamespaceManager nsmgr, ValidationLog log) {
    if (responseProcessing == null)
      return null;
    ResponseProcessing processor = new ResponseProcessing ();
    // XmlNodeList nodes =
    // responseProcessing.SelectNodes(QTIXmlConstants.ResponseRuleElementGroup,
    // nsmgr);
    List<Element> nodes = new XmlElement (responseProcessing).selectNodes (QTIXmlConstants.ResponseRuleElementGroup, nsmgr);

    for (Element node : nodes) {
      ResponseRule rule = ResponseRule.fromXml (node, nsmgr, log);
      if (rule != null)
        processor.AddRule (rule);
    }
    return processor;
  }

  boolean validate (QTIRubric rubric, ValidationLog log) {
    boolean ok = true;
    if (_responseRules.size () == 0) {
      log.addMessage (null, "ResponseProcessing statement exists but has no valid statements.");
      return false;
    }

    for (ResponseRule rule : _responseRules) {
      if (!rule.validate (log, rubric))
        ok = false;
    }
    return ok;
  }

  DataElement evaluate (VariableBindings vb, QTIRubric rubric) throws QTIScoringException {
    DataElement de = null;
    for (ResponseRule rule : _responseRules) {
      de = rule.evaluate (vb, rubric);
    }
    return de;
  }
}
