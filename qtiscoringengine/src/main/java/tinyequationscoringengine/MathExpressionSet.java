/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinyequationscoringengine;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import qtiscoringengine.QTIScoringRuntimeException;

public class MathExpressionSet extends ArrayList<MathExpression>
{
  // private List<MathExpression> _exprList = new List<MathExpression>();

  // public void Add(MathExpression expr)
  // {
  // _exprList.Add(expr);
  // }
  private static final long serialVersionUID = 1L;

  @Override
  public String toString () {
    String response = "[", mexpstr;
    boolean first = true;
    for (MathExpression mexp : this) {
      if (first)
        first = false;
      else
        response += ", ";
      mexpstr = mexp.toString ();
      response += mexpstr.substring (1, mexpstr.length () - 2);
    }
    response += "]";
    return response;
  }

  public List<String> toStringList () {
    List<String> parsedExpressions = new ArrayList<String> ();

    for (MathExpression expression : this) {
      parsedExpressions.add (MathExpressionInfo.getXmlStringFromMathExpressionInfo (expression.toMathExpressionInfo ()));
    }
    return parsedExpressions;
  }
}
