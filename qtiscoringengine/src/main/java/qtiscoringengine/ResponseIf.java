/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class ResponseIf extends ResponseRule
{
  enum IfType {
    If, Else, ElseIf
  };

  private IfType             _ifType;
  private Expression         _condition = null;
  private List<ResponseRule> _rules     = null;

  private ResponseIf (IfType it, Expression condition, List<ResponseRule> rules, Element node)
  {
    super (node);
    _ifType = it;
    _condition = condition;
    _rules = rules;
  }

  static/* new */ResponseIf fromXml (Element node, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    if (node == null)
      return null;
    String name = node.getName ();
    IfType it = IfType.If;
    switch (name)
    {
    case QTIXmlConstants.ResponseElseName:
      it = IfType.Else;
      break;
    case QTIXmlConstants.ResponseElseIfName:
      it = IfType.ElseIf;
      break;
    }
    // XmlNode n = node.SelectSingleNode(QTIXmlConstants.ExpressionElementGroup,
    // nsmgr);
    // XmlElement condNode = (XmlElement)n;
    Element condNode = new XmlElement (node).selectSingleNode (QTIXmlConstants.ExpressionElementGroup, nsmgr);

    // XmlNodeList ruleNodes =
    // node.SelectNodes(QTIXmlConstants.ResponseRuleElementGroup, nsmgr);
    List<Element> ruleNodes = new XmlElement (node).selectNodes (QTIXmlConstants.ResponseRuleElementGroup, nsmgr);

    List<ResponseRule> rules = new ArrayList<ResponseRule> ();
    Expression ex = Expression.fromXml (condNode, nsmgr, log);

    for (Element ruleNode : ruleNodes)
    {
      ResponseRule rule = ResponseRule.fromXml (ruleNode, nsmgr, log);
      if (rule != null)
        rules.add (rule);
    }

    // todo: do you return anything if it is empty?
    return new ResponseIf (it, ex, rules, node);
  }

  // todo: extend the validation
  @Override
  boolean validate (ValidationLog log, QTIRubric rubric)
  {
    boolean ok = true;

    String message = "";

    switch (_ifType)
    {
    case If:
    case ElseIf:
      if (this._condition == null)
      {
        ok = false;
        message += "ResponseIf/ElseIf requires a condition\n";
      }
      else if ((_condition.getReturnType () != BaseType.Boolean) || (_condition.getReturnCardinality () != Cardinality.Single))
      {
        ok = false;
        message += "Condition associated with ResponseIf/ElseIf must be type boolean, cardinality single\n";
      }
      boolean status = _condition.validate (log, rubric);
      if (status == false)
        ok = false;

      if (!ok && !StringUtils.isEmpty (message))
      {
        log.addMessage (_node, message);
      }
      break;
    }

    for (ResponseRule rule : _rules)
    {
      boolean status = rule.validate (log, rubric);
      if (status == false)
        ok = false;
    }

    return ok;
  }

  @Override
  DataElement evaluate (VariableBindings vb, QTIRubric rubric) throws Exception
  {
    switch (_ifType)
    {
    case If:
    case ElseIf:
      DEBoolean resp = (DEBoolean) _condition.evaluate (vb, rubric);
      if ((resp == null) || (resp.getValue () == false))
        return resp;
      EvaluateResponseRules (vb, rubric);
      return resp;
    default:
      DataElement de = EvaluateResponseRules (vb, rubric);
      return de;
    }
  }

  private DataElement EvaluateResponseRules (VariableBindings vb, QTIRubric rubric) throws Exception
  {
    DataElement de = null;
    for (ResponseRule rule : _rules)
    {
      de = rule.evaluate (vb, rubric);
    }
    return de;
  }
}
