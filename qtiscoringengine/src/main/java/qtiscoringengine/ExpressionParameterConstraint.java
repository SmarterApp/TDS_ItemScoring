/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

public class ExpressionParameterConstraint
{
  private static List<Cardinality> STATIC_CARDLIST         = Arrays.asList (Cardinality.None);
  private static List<BaseType>    STATIC_BASETYPE         = Arrays.asList (BaseType.Null);

  private int                      _appyToParameter        = -1;                              // interpret
                                                                                               // -1
                                                                                               // as
                                                                                               // "all"
  private List<Cardinality>        _cardinaltyConstraint   = STATIC_CARDLIST;
  private List<BaseType>           _typeConstraint         = STATIC_BASETYPE;
  private boolean                  _requireSameCardinality = false;
  private boolean                  _requireSameType        = false;

  ExpressionParameterConstraint (int applyTo, List<Cardinality> cardConstraint, List<BaseType> typeConstraint, boolean requireSameCard, boolean requireSameType) {
    _appyToParameter = applyTo;
    _cardinaltyConstraint = cardConstraint;
    _typeConstraint = typeConstraint;
    _requireSameCardinality = requireSameCard;
    _requireSameType = requireSameType;
  }

  ExpressionParameterConstraint (int applyTo, Cardinality cardConstraint, BaseType typeConstraint, boolean requireSameCard, boolean requireSameType) {
    // this(applyTo,new List<Cardinality>() { cardConstraint },new
    // List<BaseType>() {typeConstraint},requireSameCard,requireSameType);
    this (applyTo, Arrays.asList (cardConstraint), Arrays.asList (typeConstraint), requireSameCard, requireSameType);
  }

  ExpressionParameterConstraint (int applyTo, Cardinality cardConstraint, List<BaseType> typeConstraint, boolean requireSameCard, boolean requireSameType)
  // :this(applyTo,new List<Cardinality>() { cardConstraint
  // },typeConstraint,requireSameCard,requireSameType)
  {
    this (applyTo, Arrays.asList (cardConstraint), typeConstraint, requireSameCard, requireSameType);
  }

  public ExpressionParameterConstraint (int applyTo, Cardinality cardConstraint, BaseType typeConstraint)
  // :this(applyTo,new List<Cardinality>() { cardConstraint },new
  // List<BaseType>() { typeConstraint },false,false)
  {
    this (applyTo, Arrays.asList (cardConstraint), Arrays.asList (typeConstraint), false, false);
  }

  boolean validateExpressions (List<Expression> parameters, QTIRubric rubric, Element node, ValidationLog log) {
    String message = "";
    boolean ok = true;
    if ((_appyToParameter == -1) && (parameters.size () > 0)) {
      Cardinality requiredCardinality = Cardinality.None;
      BaseType requiredType = BaseType.Null;

      if (_requireSameCardinality)
        requiredCardinality = parameters.get (0).getReturnCardinality ();
      if (_requireSameType)
        requiredType = parameters.get (0).getReturnType ();

      for (Expression exp : parameters) {
        ok = checkCardinalityConstraints (requiredCardinality, exp, ok, node, log);
        ok = checkTypeConstraints (requiredType, exp, ok, node, log);
      }
    } else {
      if (parameters.size () <= _appyToParameter) {
        message += "This expression does not have enough parameters (subexpressions)\n";
        ok = false;
      } else {
        ok = checkCardinalityConstraints (Cardinality.None, parameters.get (_appyToParameter), ok, node, log);
        ok = checkTypeConstraints (BaseType.Null, parameters.get (_appyToParameter), ok, node, log);
      }
    }
    return ok;
  }

  private boolean checkCardinalityConstraints (Cardinality requiredCardinality, Expression exp, boolean ok, Element node, ValidationLog log) {
    String message = "";
    if (_cardinaltyConstraint.size () > 0) {
      if (!matchCardinality (exp, log)) {
        message += "The cardinality of the parameters is not the type required by the expression\n";
        ok = false;
      }
    }
    if (_requireSameCardinality) {
      if ((exp.getReturnCardinality () != requiredCardinality) && (exp.getReturnCardinality () != Cardinality.None) && (requiredCardinality != Cardinality.None)) {
        message += "The parameters of this expression must all have the same cardinality and they do not\n";
        ok = false;
      }
    }
    if (!ok && !StringUtils.isEmpty (message))
      log.addMessage (node, message);
    return ok;
  }

  private boolean checkTypeConstraints (BaseType requiredType, Expression exp, boolean ok, Element node, ValidationLog log) {
    String message = "";

    if (_typeConstraint.size () > 0) {
      if (!matchType (exp, log)) {
        message += "The BaseType of the parameters is not the type required by the expression\n";
        ok = false;
      }
    }

    if (_requireSameType) {
      if (!matchType (exp.getReturnType (), requiredType, log.getValidationRigor ())) // jdc.
                                                                                      // sept
                                                                                      // 13
                                                                                      // 2014
                                                                                      // Changed
                                                                                      // from
                                                                                      // (exp.ReturnType
                                                                                      // !=
                                                                                      // requiredType).
                                                                                      // Allowing
                                                                                      // conformable
                                                                                      // types
                                                                                      // under
                                                                                      // less
                                                                                      // validation
                                                                                      // rigor
      {
        message += "The parameters of this expression must all have the same BaseType and they do not\n";
        ok = false;
      }
    }
    if (!ok && !StringUtils.isEmpty (message))
      log.addMessage (node, message);
    return ok;
  }

  private boolean matchType (BaseType bt, BaseType requiredType, ValidationLog.Rigor rigor) {
    switch (rigor) {
    case None:
      return true;
    case Some:
      return DataElement.isConformable (bt, requiredType);
    case Strict:
      return (bt == requiredType);
    }
    return false;
  }

  private boolean matchType (Expression exp, ValidationLog log) {
    switch (log.getValidationRigor ()) {
    case None:
      return true;
    case Some:
      if (checkTypeConformability (exp.getReturnType ()))
        return true;
      break;
    case Strict:
      if (_typeConstraint.contains (exp.getReturnType ()))
        return true;
      break;
    }
    return false;
  }

  private boolean checkTypeConformability (BaseType baseType) {
    if (_typeConstraint.size () == 0)
      return true;
    for (BaseType bt : _typeConstraint) {
      if (DataElement.isConformable (bt, baseType))
        return true;
    }
    return false;
  }

  private boolean matchCardinality (Expression exp, ValidationLog log) {
    switch (log.getValidationRigor ()) {
    case None:
      return true;
    case Some:
      return checkCardinalityConformability (exp.getReturnCardinality ());
    case Strict:
      if (_cardinaltyConstraint.size () > 0) {
        return (!_cardinaltyConstraint.contains (exp.getReturnCardinality ()));
      }
      return true;
    }
    return true;
  }

  private boolean checkCardinalityConformability (Cardinality cardinality) {
    if (_cardinaltyConstraint.size () == 0)
      return true;
    for (Cardinality card : _cardinaltyConstraint) {
      if (DataElement.isConformableCardinality (card, cardinality))
        return true;
    }
    return false;
  }
}
