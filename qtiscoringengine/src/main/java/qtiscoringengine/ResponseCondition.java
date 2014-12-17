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

import org.jdom2.Element;

import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class ResponseCondition extends ResponseRule
{
  private ResponseIf       _if         = null;
  private ResponseIf       _else       = null;
  private List<ResponseIf> _elseIf     = null;

  private Expression       _expression = null;

  private ResponseCondition (ResponseIf rcIf, ResponseIf rcElse, List<ResponseIf> rcElseIf, Element node)
  {
    super (node);
    _ruleType = RuleType.Condition;
    _if = rcIf;
    _else = rcElse;
    _elseIf = rcElseIf;
  }

  private ResponseCondition (Expression ex, Element node)
  {
    super (node);
    _ruleType = ex.getRuleTypeOf ();
    _expression = ex;
  }

  static/* new */ResponseCondition fromXml (Element node, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    // XmlNode n = node.SelectSingleNode(QTIXmlConstants.ResponseIf, nsmgr);
    // XmlElement responseIf = (XmlElement)n;
    XmlElement e = new XmlElement (node);
    Element responseIf = e.selectSingleNode (QTIXmlConstants.ResponseIf, nsmgr);

    // XmlNode n2 = node.SelectSingleNode(QTIXmlConstants.ResponseElse, nsmgr);
    // XmlElement responseElse = (XmlElement)n2;
    Element responseElse = e.selectSingleNode (QTIXmlConstants.ResponseElse, nsmgr);

    // XmlNodeList responseElseIf =
    // node.SelectNodes(QTIXmlConstants.ResponseElseIf, nsmgr);
    List<Element> responseElseIf = e.selectNodes (QTIXmlConstants.ResponseElseIf, nsmgr);

    ResponseIf respIf = ResponseIf.fromXml (responseIf, nsmgr, log);
    ResponseIf respElse = ResponseIf.fromXml (responseElse, nsmgr, log);
    List<ResponseIf> elseIfList = new ArrayList<ResponseIf> ();

    for (Element el : responseElseIf)
    {
      ResponseIf rif = ResponseIf.fromXml (el, nsmgr, log);
      if (rif != null)
        elseIfList.add (rif);
    }

    return new ResponseCondition (respIf, respElse, elseIfList, node);
  }

  @Override
 public boolean validate (ValidationLog log, QTIRubric rubric)
  {
    boolean ok = true;
    if (_if != null)
    {
      if (!_if.validate (log, rubric))
        ok = false;
    }
    else
    {
      log.addMessage (_node, "ResponseCondition does not contain an IF statement");
      ok = false;
    }

    if (_else != null)
      if (!_else.validate (log, rubric))
        ok = false;
    for (ResponseIf elseIf : _elseIf)
    {
      if (!elseIf.validate (log, rubric))
        ok = false;
    }
    return ok;
  }

  @Override
 public DataElement evaluate (VariableBindings vb, QTIRubric rubric) throws Exception
  {
    DEBoolean resp = null;
    if (_if != null)
    {
      resp = (DEBoolean) _if.evaluate (vb, rubric);
      if ((resp != null) && (resp.getValue () == true))
        return resp;
    }
    if ((resp == null) || resp.getValue () == false)
    {
      for (ResponseIf rif : _elseIf)
      {
        resp = (DEBoolean) rif.evaluate (vb, rubric);
        if ((resp != null) && (resp.getValue () == true))
          return resp;
      }
    }
    if (_else != null)
    {
      return _else.evaluate (vb, rubric);
    }

    return new DEBoolean (false);
  }
}
