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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEFloat;
import qtiscoringengine.DEIdentifier;
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
public class TGREGetLength extends TinyGRExpression
{
  public TGREGetLength (Element node) {
    super (node, 0, 0, BaseType.Float, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("vector", BaseType.Identifier));
    List<TinyGraphicType> types = Arrays.asList (TinyGraphicType.Vector, TinyGraphicType.Arrow);
    addGraphicParameterConstraint ("vector", types);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws TinyGRException {
    DEString vector = (DEString) vb.getVariable ((DEIdentifier) getAttributeValue ("vector"));
    if (vector == null) {
      return new DEFloat (Double.NaN);
    }
    double length = TinyGR.getLength (vector.toString ());
    return new DEFloat (length);
  }
}
