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

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEBoolean;
import qtiscoringengine.DEIdentifier;
import qtiscoringengine.DEInteger;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.ExpressionParameterConstraint;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.VariableBindings;

/**
 * @author temp_mbikkina
 *
 */
public class TGREHasVertex extends TinyGRExpression
{
  public TGREHasVertex (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier));
    addAttribute (new ExpressionAttributeSpec ("tolerance", BaseType.Integer));
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.Integer));
    addGraphicParameterConstraint ("object", TinyGraphicType.GRObject);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws TinyGRException {
    DEIdentifier obString = (DEIdentifier) getAttributeValue ("object");
    DEString ob = (DEString) vb.getVariable (obString);
    if (ob.equals (null)) {
      return new DEBoolean (false);
    }
    int x = ((DEInteger) paramValues.get (0)).getValue ();
    int y = ((DEInteger) paramValues.get (1)).getValue ();
    _Ref<Integer> tolerance = new _Ref<Integer> (0);
    JavaPrimitiveUtils.intTryParse (getAttributeValue ("tolerance").toString (), tolerance);
    boolean rslt = TinyGR.hasVertex (ob.toString (), x, y, tolerance.get ());
    return new DEBoolean (rslt);
  }
}
