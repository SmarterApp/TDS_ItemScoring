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
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.VariableBindings;

/**
 * @author temp_mbikkina
 *
 */
public class TGREGetSinglePoint extends TinyGRExpression
{

  public TGREGetSinglePoint (Element node) {
    super (node, 0, 0, BaseType.Point, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier, false));
    List<TinyGraphicType> types = Arrays.asList (TinyGraphicType.Point, TinyGraphicType.AtomicObject);
    addGraphicParameterConstraint ("object", types);
  }

  public DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws TinyGRException {
    String obj = vb.getVariable ((DEIdentifier) getAttributeValue ("object")).toString ();
    String pointString = TinyGR.getPoint (obj);
    if (pointString == null)
      return null;
    return pointFromXml (pointString);
  }
}
