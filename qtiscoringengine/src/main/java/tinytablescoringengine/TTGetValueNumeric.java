/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinytablescoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEFloat;
import qtiscoringengine.DEIdentifier;
import qtiscoringengine.DEInteger;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.QTIScoringException;
import qtiscoringengine.VariableBindings;

public class TTGetValueNumeric extends TinyTableExpression
{
  public TTGetValueNumeric (Element node) {
    super (node, 0, 0, BaseType.Float, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("tableVector", BaseType.Identifier, false));
    addAttribute (new ExpressionAttributeSpec ("index", BaseType.Integer, false));
    addTableParameterConstraint ("tableVector", TableType.TableVector);
  }

  @Override
  public DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws QTIScoringException {
    DataElement obj = vb.getVariable ((DEIdentifier) getAttributeValue ("tableVector"));
    if (obj == null) {
      return new DEString ("");
    }
    return new DEFloat (TinyTable.getValueNumeric (obj.toString (), ((DEInteger) getAttributeValue ("index")).getValue ()));
  }
}
