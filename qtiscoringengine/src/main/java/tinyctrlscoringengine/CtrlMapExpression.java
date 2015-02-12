/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinyctrlscoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEBoolean;
import qtiscoringengine.DEContainer;
import qtiscoringengine.DEIdentifier;
import qtiscoringengine.DataElement;
import qtiscoringengine.Expression;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.ExpressionParameterConstraint;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.QTIScoringException;
import qtiscoringengine.ValidationLog;
import qtiscoringengine.VariableBindings;
import qtiscoringengine.VariableDeclaration;
import qtiscoringengine.cs2java.NotImplementedException;

public class CtrlMapExpression extends CtrlExpression
{
  public CtrlMapExpression (Element node) {
    super (node, 1, 1, BaseType.Null, Cardinality.None);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.Single, BaseType.Boolean));
    addAttribute (new ExpressionAttributeSpec ("container", BaseType.Identifier, false));
    // this must set the return type at validation time. -- has to be the same
    // as the input type.
  }

  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    boolean ok = super.validate (log, rubric);
    DEIdentifier collection = (DEIdentifier) getAttributeValue ("container");
    if (collection != null) {
      String cn = collection.toString ();
      VariableDeclaration vd = rubric.getVariableDeclaration (cn);
      if (vd != null) {
        _returnType = vd.getType ();
        _returnCardinality = vd.getCardinality ();
      } else {
        log.addMessage (_node, "Referencing an undeclared variable: " + cn);
        ok = false;
      }
    }

    return ok;
  }

  public DataElement evaluate (VariableBindings vb, QTIRubric rubric) throws QTIScoringException {
    String collectionName = getAttributeValue ("container").toString ();

    DEContainer container = (DEContainer) vb.getVariable (collectionName);
    DEContainer rtnContainer = new DEContainer (container.getType (), container.getCardinality ());

    for (int i = 0; i < container.getMemberCount (); i++) {
      DataElement d = container.getMember (i);
      vb.setVariable ("@", d);

      boolean isTrue = true;
      for (Expression param : _parameters) {
        DEBoolean result = (DEBoolean) param.evaluate (vb, rubric);
        isTrue = result.getBooleanValue ();
        if (!isTrue)
          break;
      }
      if (isTrue)
        rtnContainer.add (d);
    }
    vb.remove ("@");
    return rtnContainer;
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    throw new NotImplementedException ();
  }
}
