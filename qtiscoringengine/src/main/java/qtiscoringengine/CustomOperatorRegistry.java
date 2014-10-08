/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.List;
import java.util.ArrayList;

import org.jdom2.Attribute;
import org.jdom2.Element;

public class CustomOperatorRegistry
{
  private static final Object                _lock                = new Object ();
  private static CustomOperatorRegistry      _instance            = null;
  private final List<ICustomOperatorFactory> _registeredFactories = new ArrayList<ICustomOperatorFactory> ();

  public static CustomOperatorRegistry getInstance () {
    if (_instance != null)
      return _instance;
    synchronized (_lock) {
      if (_instance != null)
        return _instance;
      _instance = new CustomOperatorRegistry ();
      return _instance;
    }
  }

  private CustomOperatorRegistry () {
  }

  public void addOperatorFactory (ICustomOperatorFactory factory) {
    _registeredFactories.add (factory);
  }

  public Expression createExpression (Element customOperatorNode) {
    for (ICustomOperatorFactory factory : _registeredFactories) {
      if (factory.supportsOperator (customOperatorNode)) {
        return factory.createExpression (customOperatorNode);
      }
    }

    return null;
  }

  private static String getOperatorType (Element customOperatorNode) {
    for (Attribute attribute : customOperatorNode.getAttributes ()) {
      if ("CLASS".equals (attribute.getName ().toUpperCase ())) {
        return attribute.getValue ();
      }
    }
    return "";
  }
}
