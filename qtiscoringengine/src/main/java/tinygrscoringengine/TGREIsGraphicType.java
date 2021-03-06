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
import qtiscoringengine.DEBoolean;
import qtiscoringengine.DEIdentifier;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.VariableBindings;

/**
 * @author temp_mbikkina
 *
 */
public class TGREIsGraphicType extends TinyGRExpression
{
  public TGREIsGraphicType (Element node) {
    super (node, 0, 0, BaseType.Boolean, Cardinality.Single);
    List<String> types = Arrays.asList ("PALETTEIMAGE", "VECTOR", "ARROW", "POINT");
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier, false));
    addAttribute (new ExpressionAttributeSpec ("graphicType", types, false));
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws TinyGRException {
    DEString ob = (DEString) vb.getVariable ((DEIdentifier) getAttributeValue ("object"));
    String ty = getAttributeValue ("graphicType").toString ();
    if (ob == null) {
      return new DEBoolean (false);
    }
    boolean isIt = TinyGR.isGraphicType (ob.toString (), ty);
    return new DEBoolean (isIt);
  }
}
