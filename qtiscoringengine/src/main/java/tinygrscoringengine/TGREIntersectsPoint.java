/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import java.io.IOException;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEBoolean;
import qtiscoringengine.DEFloat;
import qtiscoringengine.DEInteger;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.VariableBindings;
import qtiscoringengine._DEFloat;

/**
 * @author temp_mbikkina
 *
 */
public class TGREIntersectsPoint extends TinyGRExpression
{
  public TGREIntersectsPoint (Element node) {
    super (node, 0, 0, BaseType.Boolean, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier));
    addAttribute (new ExpressionAttributeSpec ("x", BaseType.Integer));
    addAttribute (new ExpressionAttributeSpec ("y", BaseType.Integer));
    addAttribute (new ExpressionAttributeSpec ("tolerance", BaseType.Float));
    addGraphicParameterConstraint ("object", TinyGraphicType.GRObject);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws  TinyGRException {
    DEString obj = (DEString) vb.getVariable (getAttributeValue ("object").toString ());
    int x = ((DEInteger) getAttributeValue ("x")).getValue ();
    int y = ((DEInteger) getAttributeValue ("y")).getValue ();
    double tolerance = ((_DEFloat) getAttributeValue ("tolerance")).getValue ().doubleValue ();
    boolean result = TinyGR.intersectsPoint (obj.toString (), x, y, tolerance);
    return new DEBoolean (result);
  }
}
