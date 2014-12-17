/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import org.jdom2.Element;

import qtiscoringengine.cs2java.NotImplementedException;
import AIR.Common.xml.XmlNamespaceManager;

public class ResponseRule
{
  public enum RuleType {
    None, Condition, Expression, Action
  };

  protected RuleType _ruleType = RuleType.None;
  protected Element  _node     = null;

  ResponseRule (Element node)
  {
    _node = node;
  }

  RuleType getRuleTypeOf ()
  {
    return _ruleType;
  }

  static ResponseRule fromXml (Element node, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    String nodeName = node.getName ();
    switch (nodeName)
    {
    case QTIXmlConstants.ResponseConditionName:
      return ResponseCondition.fromXml (node, nsmgr, log);
    case QTIXmlConstants.ResponseSetOutcomeName:
      return ResponseSetOutcome.fromXml (node, nsmgr, log);
    case QTIXmlConstants.ResponseLookupName:
    case QTIXmlConstants.ResponseExitName:
    case QTIXmlConstants.ResponseFragmentName:
      log.addMessage (node, "Response lookup, response exit, response fragment .FromXml not yet implemented");
      return null;
    default:
      log.addMessage (node, "Unrecognized Response Rule node name: '" + nodeName + "'");
      return null;
    }
  }

  // todo: write more robust validation code
  public boolean validate (ValidationLog log, QTIRubric rubric)
  {
    return true;
  }

  public DataElement evaluate (VariableBindings vb, QTIRubric rubric) throws Exception
  {
    throw new NotImplementedException ();
  }
}
