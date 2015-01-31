/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinyequationscoringengine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DataElement;
import qtiscoringengine.Expression;
import qtiscoringengine.VariableBindings;

/**
 * @author temp_mbikkina
 *
 */
public abstract class TinyEqExpression extends Expression
{
  private static final Pattern        StringTokenPattern      = Pattern.compile ("([a-zA-Z0-9_<=> .]+)|(\\$)([a-zA-Z0-9_.]+)");
  private TinyEquation.TEType         _eqReturnType           = TinyEquation.TEType.Unknown;
  private List<TEParameterConstraint> _teParameterConstraints = new ArrayList<TEParameterConstraint> ();

  public TinyEqExpression (Element node, int minparm, int maxparm, BaseType returnType, Cardinality returnCardinality, TinyEquation.TEType eqType) {
    super (node, minparm, maxparm, returnType, returnCardinality);
    _eqReturnType = eqType;
  }

  public TinyEqExpression (Element node, int minparm, int maxparm, BaseType returnType, Cardinality returnCardinality) {
    super (node, minparm, maxparm, returnType, returnCardinality);
    _eqReturnType = TinyEquation.TEType.None;
  }

  public void addEqParameterConstraint (String name, TinyEquation.TEType type) {
    _teParameterConstraints.add (new TEParameterConstraint (name, type));
  }

  public void addEqParameterConstraint (String name, List<TinyEquation.TEType> types) {
    _teParameterConstraints.add (new TEParameterConstraint (name, types));
  }

  public void setReturnType (TinyEquation.TEType type) {
    _eqReturnType = type;
  }

  // / <summary>
  // / Converts a csv String into a list by mimicing the BindStringSetParameters
  // in InferenceEngine
  // / </summary>
  // / <param name="value"></param>
  // / <param name="vb"></param>
  // / <returns></returns>
  public List<String> parseCSVAttribute (String value, VariableBindings vb) {
    Matcher m = StringTokenPattern.matcher (value);
    List<String> list = new ArrayList<String> ();
    while (m.find ()) {
      String token = m.group ();
      if (token.charAt (0) == '$' && vb.getVariable (token.substring (1)) != null) {
        DataElement de = vb.getVariable (token.substring (1));
        list.add (de.toString ());
      } else {
        list.add (token);
      }
    }
    return list;
  }

  // end TinyEqExpression
}
