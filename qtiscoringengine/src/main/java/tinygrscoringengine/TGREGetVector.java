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
import qtiscoringengine.DEIdentifier;
import qtiscoringengine.DEInteger;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.VariableBindings;

/**
 * @author temp_mbikkina
 *
 */
public class TGREGetVector extends TinyGRExpression
{
  public TGREGetVector (Element node) {
    super (node, 0, 0, BaseType.String, Cardinality.Single, TinyGraphicType.Vector);

    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier, false));
    addAttribute (new ExpressionAttributeSpec ("index", BaseType.Integer, false));
    List<TinyGraphicType> types = Arrays.asList (TinyGraphicType.GeoObject, TinyGraphicType.Vector, TinyGraphicType.Arrow);
    addGraphicParameterConstraint ("object", types);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws TinyGRException {
    DEString obj = (DEString) vb.getVariable ((DEIdentifier) getAttributeValue ("object"));
    DEInteger index = ((DEInteger) getAttributeValue ("index"));
    if (obj == null) {
      return null;
    }
    String vector = TinyGR.getVector (obj.toString (), index.getValue ());
    if (vector.equals (null))
      return null;
    return new DEString (vector);
  }
}
