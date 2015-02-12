/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinyctrlscoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEBoolean;
import qtiscoringengine.DEInteger;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.ValidationLog;
import qtiscoringengine.VariableBindings;

public class CtrlCountBoolean extends CtrlExpression
{
  public CtrlCountBoolean (Element node) {
    super (node, 0, 0, BaseType.Integer, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("list", BaseType.String, false));
    addAttribute (new ExpressionAttributeSpec ("booleanValue", BaseType.Boolean, false));
  }

  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    boolean status = super.validate (log, rubric);
    boolean viable = validateResolvabilityOfBoolList (getAttributeValue ("list").toString (), log, rubric);
    return (status & viable);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    List<Boolean> bList = resolveBoolList (vb, getAttributeValue ("list").toString ());
    boolean toMatch = ((DEBoolean) getAttributeValue ("booleanValue")).getBooleanValue ();

    int cnt = 0;
    for (boolean b : bList) {
      if (b == toMatch)
        cnt++;
    }

    return new DEInteger (cnt);
  }
}
