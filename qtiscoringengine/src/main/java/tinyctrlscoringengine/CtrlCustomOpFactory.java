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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import qtiscoringengine.Expression;
import qtiscoringengine.ICustomOperatorFactory;

public class CtrlCustomOpFactory implements ICustomOperatorFactory
{
  interface CtrlConstructor
  {
    CtrlExpression getExpression (Element node);
  }

  private Map<String, CtrlConstructor> _ctrlConstructors = new HashMap<> ();

  public CtrlCustomOpFactory () {
    addConstructor ("mapExpression", new CCMapExpression ());
    addConstructor ("stringToFloat", new CCStringToFloat ());
    addConstructor ("COUNTDOUBLEINRANGE", new CCCountDoubleInRange ());
    addConstructor ("COUNTBOOL", new CCCountBoolean ());
    addConstructor ("MAXINT", new CCMaxInt ());
    addConstructor ("MININT", new CCMinInt ());
  }

  class CCMinInt implements CtrlConstructor
  {
    public CtrlExpression getExpression (Element node) {
      return new CtrlMinInt (node);
    }
  }

  class CCMaxInt implements CtrlConstructor
  {
    public CtrlExpression getExpression (Element node) {
      return new CtrlMaxInt (node);
    }
  }

  class CCMapExpression implements CtrlConstructor
  {
    public CtrlExpression getExpression (Element node) {
      return new CtrlMapExpression (node);
    }
  }

  class CCStringToFloat implements CtrlConstructor
  {
    public CtrlExpression getExpression (Element node) {
      return new CtrlStringToFloat (node);
    }
  }

  class CCCountDoubleInRange implements CtrlConstructor
  {
    public CtrlExpression getExpression (Element node) {
      return new CtrlCountDoubleInRange (node);
    }
  }

  class CCCountBoolean implements CtrlConstructor
  {
    public CtrlExpression getExpression (Element node) {
      return new CtrlCountBoolean (node);
    }
  }

  private void addConstructor (String name, CtrlConstructor del) {
    if (_ctrlConstructors.containsKey (name))
      _ctrlConstructors.remove (name);
    _ctrlConstructors.put (name, del);
  }

  @Override
  public boolean supportsOperator (Element customOperatorNode) {
    Element coElement = customOperatorNode;

    if (!StringUtils.equals (coElement.getAttribute ("type").getValue (), "CTRL"))
      return false;

    return _ctrlConstructors.containsKey (coElement.getAttribute ("functionName").getValue ());
  }

  @Override
  public Expression createExpression (Element customOperatorNode) {
    Element coElement = customOperatorNode;
    return (Expression) _ctrlConstructors.get (coElement.getAttribute ("functionName").getValue ()).getExpression (coElement);
  }
}
