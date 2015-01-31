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
public class TGREIntersectsRegion extends TinyGRExpression
{

  public TGREIntersectsRegion (Element node) {
    super (node, 0, 0, BaseType.Boolean, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier, false));
    addAttribute (new ExpressionAttributeSpec ("leftX", BaseType.Integer, false));
    addAttribute (new ExpressionAttributeSpec ("topY", BaseType.Integer, false));
    addAttribute (new ExpressionAttributeSpec ("bottomY", BaseType.Integer, false));
    addAttribute (new ExpressionAttributeSpec ("rightX", BaseType.Integer, false));
    addGraphicParameterConstraint ("Object", TinyGraphicType.GRObject);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws TinyGRException {
    DEString obj = (DEString) vb.getVariable ((DEIdentifier) getAttributeValue ("object"));
    if (obj == null)
      return new DEBoolean (false);
    boolean intersects = TinyGR.intersectsRegion (obj.toString (), ((DEInteger) getAttributeValue ("leftX")).getValue (), ((DEInteger) getAttributeValue ("topY")).getValue (),
        ((DEInteger) getAttributeValue ("rightX")).getValue (), ((DEInteger) getAttributeValue ("bottomY")).getValue ());
    return new DEBoolean (intersects);
  }
}
