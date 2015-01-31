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
import qtiscoringengine.DEContainer;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.VariableBindings;

/**
 * @author temp_mbikkina
 *
 */
public class TGREPreProcess extends TinyGRExpression
{

  public TGREPreProcess (Element node) {
    super (node, 0, 0, BaseType.String, Cardinality.Ordered, TinyGraphicType.Unknown);
    addAttribute (new ExpressionAttributeSpec ("response", BaseType.Identifier, false));
    setReturnType (TinyGraphicType.GRObject);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws TinyGRException {
    DataElement resp = getAttributeValue ("response");
    List<String> objectList = TinyGR.getObjectStrings (vb.getVariable (resp.toString ()).toString ());
    DEContainer container = new DEContainer (BaseType.String, Cardinality.Ordered);
    for (String ob : objectList) {
      container.add (new DEString (ob));
    }
    return container;
  }

}
