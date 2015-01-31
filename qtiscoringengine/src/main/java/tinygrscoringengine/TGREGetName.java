/*************************************************************************
 * Educational Online Test Delivery System
 * Copyright (c) 2014 American Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0
 * See accompanying file AIR-License-1_0.txt or at 
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import java.io.IOException;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
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
public class TGREGetName extends TinyGRExpression
{
  
  public TGREGetName(Element node)  
    {
        super(node, 0, 0, BaseType.String, Cardinality.Single);
        addAttribute(new ExpressionAttributeSpec("object",BaseType.Identifier,false));
    }
  
  protected DataElement exprEvaluate(VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws TinyGRException
  {
      DataElement obj = vb.getVariable((DEIdentifier) getAttributeValue("object"));
      if (obj == null) return new DEString("");
      return new DEString(TinyGR.getName(obj.toString()));
  }
}
