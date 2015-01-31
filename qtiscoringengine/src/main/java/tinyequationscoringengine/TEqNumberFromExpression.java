/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinyequationscoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEFloat;
import qtiscoringengine.DEIdentifier;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.QTIScoringException;
import qtiscoringengine.VariableBindings;

/**
 * @author temp_mbikkina
 *
 */
public class TEqNumberFromExpression extends TinyEqExpression
{

  public TEqNumberFromExpression (Element node) {
    super (node, 0, 0, BaseType.Float, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier, false));
    addEqParameterConstraint ("object", TinyEquation.TEType.Expression);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws QTIScoringException {
    DEString obj = (DEString) vb.getVariable ((DEIdentifier) getAttributeValue ("object"));
    if (obj == null)
      obj = new DEString ("");
    return new DEFloat (TinyEquation.numberFromExpression (obj.toString ()));
  }
}
