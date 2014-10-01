/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import AIR.Common.Utilities.TDSStringUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class ResponseSetOutcome extends ResponseRule
{
  private Expression _expression;
  private String     _identifier;

  private ResponseSetOutcome (String identifier, Expression exp, Element node)
  {
    super (node);
    _identifier = identifier;
    _expression = exp;
    // _ruleType = RuleType.Action; // Zach: Added
  }

  static/* new */ResponseSetOutcome fromXml (Element node, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    String id = node.getAttributeValue ("identifier");

    Element el = new XmlElement (node).selectSingleNode (QTIXmlConstants.ExpressionElementGroup, nsmgr);
    // Element el = (XmlElement) n;

    Expression ex = Expression.fromXml (el, nsmgr, log);
    return new ResponseSetOutcome (id, ex, node);
  }

  @Override
  boolean validate (ValidationLog log, QTIRubric rubric)
  {
    String message = "";
    boolean ok = true;

    if (_expression == null)
    {
      if (_identifier == null)
      {
        message += "setOutcomeValue does not indicate which variable's value to set\n";
        ok = false;
      }
      else
      {
        message += TDSStringUtils.format ("setOutcomeValue for value {0} needs an expression to set it to, and does not have one\n", _identifier);
        ok = false;
      }
    }
    else
    {
      boolean status = _expression.validate (log, rubric);
      if (!status)
        ok = false;
    }

    if (!(rubric.declaresOutcomeVariable (_identifier)))
    {
      if (_identifier == null)
      {
        message += "setOutcomeValue does not indicate which variable's value to set\n";
        ok = false;
      }
      else
      {
        message += TDSStringUtils.format ("setOutcomeVariable references an undeclared outcome variable: {0}\n", _identifier);
        ok = false;
      }
    }
    else
    {
      boolean bad = false;
      BaseType valueType = rubric.getOutcomeVariableBaseType (_identifier);
      switch (log.getValidationRigor ())
      {
      case None:
        break;
      case Some:
        if (!(DataElement.isConformable (valueType, _expression.getReturnType ())))
          bad = true;
        break;
      case Strict:
        if (valueType != _expression.getReturnType ())
          bad = true;
        break;
      }
      if (bad)
      {
        message += TDSStringUtils.format ("Declared outcome variable type -{0}- differs from the type specified in the document for {1}", valueType.toString (), _identifier);
        ok = false;
      }
    }

    if (!ok && !StringUtils.isEmpty (message))
      log.addMessage (_node, message);
    return ok;
  }

  @Override
  DataElement evaluate (VariableBindings vb, QTIRubric rubric) throws Exception
  {
    DataElement value = _expression.evaluate (vb, rubric);
    vb.setVariable (this._identifier, value);
    return value;
  }
}
