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

import java.io.IOException;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.VariableBindings;

public class TTGetColumn extends TinyTableExpression
{
  public TTGetColumn (Element node) {
    super (node, 0, 0, BaseType.String, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("table", BaseType.Identifier, false));
    addAttribute (new ExpressionAttributeSpec ("columnName", BaseType.String, false));
    addTableParameterConstraint ("table", TableType.Table);
  }

  public DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws TinyTableScoringException {
    String varName = getAttributeValue ("table").toString ().trim ();
    if ("#".equals (varName))
      varName = "RESPONSE";
    DataElement obj = vb.getVariable (varName);
    if (obj == null) {
      return new DEString ("");
    }
    return new DEString (TinyTable.getColumn (obj.toString (), getAttributeValue ("columnName").toString ()));
  }
}
