/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinyequationscoringengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEBoolean;
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
public class TEqIsMatch extends TinyEqExpression
{
  public TEqIsMatch (Element node) {
    super (node, 0, 0, BaseType.Boolean, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier, false));
    addAttribute (new ExpressionAttributeSpec ("pattern", BaseType.String, false));
    addAttribute (new ExpressionAttributeSpec ("constraints", BaseType.String, true));
    addAttribute (new ExpressionAttributeSpec ("parameters", BaseType.String, true));
    addAttribute (new ExpressionAttributeSpec ("variables", BaseType.String, true));
    addAttribute (new ExpressionAttributeSpec ("simplify", BaseType.Boolean, false));
    addEqParameterConstraint ("object", TinyEquation.TEType.Expression);
    // may want to add validation constraints on the other parameters
  }

  public DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws QTIScoringException {
    DEString obj = (DEString) vb.getVariable ((DEIdentifier) getAttributeValue ("object"));
    if (obj == null)
      obj = new DEString ("");
    DEString pattern = (DEString) getAttributeValue ("pattern");
    DEString constraints = (DEString) getAttributeValue ("constraints");
    if (constraints == null)
      constraints = new DEString ("");
    DEString parameters = (DEString) getAttributeValue ("parameters");
    if (parameters == null)
      parameters = new DEString ("");
    DEString variables = (DEString) getAttributeValue ("variables");
    if (variables == null)
      variables = new DEString ("");
    DEBoolean simplify = (DEBoolean) getAttributeValue ("simplify");

    return new DEBoolean (TinyEquation.isMatch (obj.toString (), pattern.toString (), parseCSVAttribute (parameters.toString (), vb), parseCSVAttribute (constraints.toString (), vb),
        parseCSVAttribute (variables.toString (), vb), simplify.getBooleanValue ()));

  }
}
