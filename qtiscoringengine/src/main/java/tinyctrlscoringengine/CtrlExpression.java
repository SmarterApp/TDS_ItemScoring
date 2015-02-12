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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Utilities.TDSStringUtils;
import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEBoolean;
import qtiscoringengine.DEContainer;
import qtiscoringengine.DEFloat;
import qtiscoringengine.DataElement;
import qtiscoringengine.Expression;
import qtiscoringengine.ExpressionParameterConstraint;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.ValidationLog;
import qtiscoringengine.VariableBindings;
import qtiscoringengine._DEFloat;

public abstract class CtrlExpression extends Expression
{
  protected Map<String, ExpressionParameterConstraint> _namedParameterConstraints = new HashMap<String, ExpressionParameterConstraint> ();

  public CtrlExpression (Element node, int minparm, int maxparm, BaseType returnType, Cardinality returnCardinality) {
    super (node, minparm, maxparm, returnType, returnCardinality);
  }

  public double resolveDouble (VariableBindings vb, String input) {
    DataElement resolve = vb.getVariable (input);
    if (resolve != null && resolve.getIsNumber ()) {
      _DEFloat resolvedVal = (_DEFloat) resolve;
      if (resolvedVal != null)
        return resolvedVal.getValue ().doubleValue ();

    }
    if (input.charAt (0) == '$') {
      _DEFloat de = (_DEFloat) vb.getVariable (input.substring (1));
      return de.getValue ().doubleValue ();
    }

    _Ref<Double> dd = new _Ref<Double> (new Double (0));
    if (JavaPrimitiveUtils.doubleTryParse (input, dd)) {
      return dd.get ();
    }
    return Double.NaN;
  }

  protected static List<Double> resolveDoubleList (VariableBindings vb, String input) {
    List<Double> dList = new ArrayList<Double> ();
    DataElement resolve = vb.getVariable (input);
    if (resolve != null) {
      if (resolve.getIsContainer ()) {
        DEContainer deList = (DEContainer) resolve;
        for (int i = 0; i < deList.getMemberCount (); i++) {
          double d = ((_DEFloat) deList.getMember (i)).getValue ().doubleValue ();
          dList.add (d);
        }
      } else
        dList.add (((_DEFloat) resolve).getValue ().doubleValue ());
    } else {
      String[] inputs = StringUtils.split (input, ",");

      for (String s : inputs) {
        String token = s.trim ();
        if (token.charAt (0) == '$') {
          _DEFloat de = (_DEFloat) vb.getVariable (token.substring (1));
          dList.add (de.getValue ().doubleValue ());
        } else {
          _Ref<Double> dd = new _Ref<Double> (Double.NaN);
          JavaPrimitiveUtils.doubleTryParse (token, dd);
          dList.add (dd.get ());
        }
      }
    }
    return dList;
  }

  protected boolean validateResolvabilityOfDoubleList (String input, ValidationLog log, QTIRubric rubric) {
    if (rubric.getVariableDeclaration (input) != null)
      return true;
    String[] inputs = StringUtils.split (input, ",");

    boolean status = true;
    _Ref<Double> d = new _Ref<> ();
    for (String s : inputs) {
      String token = s.trim ();
      if (token.charAt (0) == '$') {
        if (rubric.getVariableDeclaration (s.substring (1)) != null)
          continue;
        else {
          log.addMessage (_node, "Unknown identifier referenced with '$': " + token);
          status = false;
        }
      } else if (!JavaPrimitiveUtils.doubleTryParse (token, d)) {
        log.addMessage (_node, TDSStringUtils.format ("What should be a list of doubles contains: {0}. If this is a variable identifier, it prepend it with '$'", token));
        status = false;
      }
    }
    return status;
  }

  protected static List<Boolean> resolveBoolList (VariableBindings vb, String input) {
    List<Boolean> bList = new ArrayList<Boolean> ();
    DataElement resolve = vb.getVariable (input);

    if (resolve != null) {
      if (resolve.getIsContainer ()) {
        DEContainer deList = (DEContainer) resolve;
        for (int i = 0; i < deList.getMemberCount (); i++) {
          boolean b = ((DEBoolean) deList.getMember (i)).getBooleanValue ();
          bList.add (b);
        }
      } else {
        bList.add (((DEBoolean) resolve).getBooleanValue ());
      }
    } else {
      String[] inputs = StringUtils.split (input, ",");

      for (String s : inputs) {
        String token = s.trim ();
        if (token.charAt (0) == '$') {
          DEBoolean de = (DEBoolean) vb.getVariable (token.substring (1));
          bList.add (de.getBooleanValue ());
        } else {
          _Ref<Boolean> dd = new _Ref<> (false);
          JavaPrimitiveUtils.boolTryParse (token, dd);
          bList.add (dd.get ());
        }
      }
    }
    return bList;
  }

  protected boolean validateResolvabilityOfBoolList (String input, ValidationLog log, QTIRubric rubric) {
    if (rubric.getVariableDeclaration (input) != null)
      return true;
    String[] inputs = StringUtils.split (input, ",");

    boolean status = true;
    // boolean d;
    _Ref<Boolean> d = new _Ref<> ();
    for (String s : inputs) {
      String token = s.trim ();
      if (token.charAt (0) == '$') {
        if (rubric.getVariableDeclaration (s.substring (1)) != null)
          continue;
        else {
          log.addMessage (_node, "Unknown identifier referenced with '$': " + token);
          status = false;
        }
      } else if (!JavaPrimitiveUtils.boolTryParse (token, d)) {
        log.addMessage (_node, TDSStringUtils.format ("What should be a list of booleans contains: {0}. If this is a variable identifier, it prepend it with '$'", token));
        status = false;
      }
    }
    return status;

  }
}
