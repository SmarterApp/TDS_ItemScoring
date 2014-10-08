/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;

import AIR.Common.Utilities.TDSStringUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public abstract class Expression extends ResponseRule
{
  private int                                 _minparameters        = 0;
  private int                                 _maxparameters        = Integer.MAX_VALUE;
  protected BaseType                          _returnType           = BaseType.Null;
  protected Cardinality                       _returnCardinality    = Cardinality.None;

  private List<ExpressionParameterConstraint> _parameterConstraints = new ArrayList<ExpressionParameterConstraint> ();
  private List<ExpressionAttributeSpec>       _attributes           = new ArrayList<ExpressionAttributeSpec> ();

  protected HashMap<String, DataElement>      _attributeValues      = new HashMap<String, DataElement> ();
  protected List<Expression>                  _parameters           = new ArrayList<Expression> ();

  protected Expression (Element node, int minparm, int maxparm, BaseType returnType, Cardinality returnCardinality) {
    super (node);
    _minparameters = minparm;
    _maxparameters = maxparm;
    _returnType = returnType;
    _returnCardinality = returnCardinality;
  }

  protected void addParameterConstraint (ExpressionParameterConstraint constraint) {
    _parameterConstraints.add (constraint);
  }

  protected void addParameter (Expression expression) {
    _parameters.add (expression);
  }

  protected void addAttribute (ExpressionAttributeSpec attname) {
    _attributes.add (attname);
  }

  BaseType getReturnType () {
    return _returnType;
  }

  Cardinality getReturnCardinality () {
    return _returnCardinality;
  }

  /* new */static Expression fromXml (Element node, XmlNamespaceManager nsmgr, ValidationLog log) {
    if (node == null)
      return null;
    Expression exp = null;
    switch (node.getName ()) {
    case QTIXmlConstants.Expr.And:
      exp = (Expression) new ExprAnd (node);
      break;
    case QTIXmlConstants.Expr.AnyN:
      exp = (Expression) new ExprAnyN (node);
      break;
    case QTIXmlConstants.Expr.BaseValue:
      exp = (Expression) new ExprBaseValue (node);
      break;
    case QTIXmlConstants.Expr.ContainerSize:
      exp = (Expression) new ExprContainerSize (node);
      break;
    case QTIXmlConstants.Expr.Contains:
      exp = (Expression) new ExprContains (node);
      break;
    case QTIXmlConstants.Expr.Correct:
      exp = (Expression) new ExprCorrect (node);
      break;
    case QTIXmlConstants.Expr.Default:
      exp = (Expression) new ExprDefault (node);
      break;
    case QTIXmlConstants.Expr.Delete:
      exp = (Expression) new ExprDelete (node);
      break;
    case QTIXmlConstants.Expr.Divide:
      exp = (Expression) new ExprDivide (node);
      break;
    case QTIXmlConstants.Expr.Equal:
      exp = (Expression) new ExprEqual (node);
      break;
    case QTIXmlConstants.Expr.EqualRounded:
      exp = (Expression) new ExprEqualRounded (node);
      break;
    case QTIXmlConstants.Expr.GT:
      exp = (Expression) new ExprGt (node);
      break;
    case QTIXmlConstants.Expr.GTE:
      exp = (Expression) new ExprGte (node);
      break;
    case QTIXmlConstants.Expr.Index:
      exp = (Expression) new ExprIndex (node);
      break;
    case QTIXmlConstants.Expr.Inside:
      exp = (Expression) new ExprInside (node);
      break;
    case QTIXmlConstants.Expr.IntDivide:
      exp = (Expression) new ExprIntegerDivide (node);
      break;
    case QTIXmlConstants.Expr.IntMod:
      exp = (Expression) new ExprIntegerModulus (node);
      break;
    case QTIXmlConstants.Expr.IntToFloat:
      exp = (Expression) new ExprIntegerToFloat (node);
      break;
    case QTIXmlConstants.Expr.IsNull:
      exp = (Expression) new ExprIsNull (node);
      break;
    case QTIXmlConstants.Expr.LT:
      exp = (Expression) new ExprLt (node);
      break;
    case QTIXmlConstants.Expr.LTE:
      exp = (Expression) new ExprLte (node);
      break;
    case QTIXmlConstants.Expr.MapResponse:
      exp = (Expression) new ExprMapResponse (node);
      break;
    case QTIXmlConstants.Expr.MapResponsePoint:
      exp = (Expression) new ExprMapResponsePoint (node);
      break;
    case QTIXmlConstants.Expr.Match:
      exp = (Expression) new ExprMatch (node);
      break;
    case QTIXmlConstants.Expr.Member:
      exp = (Expression) new ExprMember (node);
      break;
    case QTIXmlConstants.Expr.Multiple:
      exp = (Expression) new ExprMultiple (node);
      break;
    case QTIXmlConstants.Expr.Not:
      exp = (Expression) new ExprNot (node);
      break;
    case QTIXmlConstants.Expr.Null:
      exp = (Expression) new ExprNull (node);
      break;
    case QTIXmlConstants.Expr.Or:
      exp = (Expression) new ExprOr (node);
      break;
    case QTIXmlConstants.Expr.Ordered:
      exp = (Expression) new ExprOrdered (node);
      break;
    case QTIXmlConstants.Expr.PatternMatch:
      exp = (Expression) new ExprPatternMatch (node);
      break;
    case QTIXmlConstants.Expr.Power:
      exp = (Expression) new ExprPower (node);
      break;
    case QTIXmlConstants.Expr.Product:
      exp = (Expression) new ExprProduct (node);
      break;
    case QTIXmlConstants.Expr.Random:
      exp = (Expression) new ExprRandom (node);
      break;
    case QTIXmlConstants.Expr.RandomFloat:
      exp = (Expression) new ExprRandomFloat (node);
      break;
    case QTIXmlConstants.Expr.RandomInt:
      exp = (Expression) new ExprRandomInteger (node);
      break;
    case QTIXmlConstants.Expr.Round:
      exp = (Expression) new ExprRound (node);
      break;
    case QTIXmlConstants.Expr.StringMatch:
      exp = (Expression) new ExprStringMatch (node);
      break;
    case QTIXmlConstants.Expr.Substring:
      exp = (Expression) new ExprSubstring (node);
      break;
    case QTIXmlConstants.Expr.Subtract:
      exp = (Expression) new ExprSubtract (node);
      break;
    case QTIXmlConstants.Expr.Sum:
      exp = (Expression) new ExprSum (node);
      break;
    case QTIXmlConstants.Expr.Truncate:
      exp = (Expression) new ExprTruncate (node);
      break;
    case QTIXmlConstants.Expr.Variable:
      exp = (Expression) new ExprVariable (node);
      break;
    case QTIXmlConstants.Expr.Custom:
      exp = (Expression) CustomOperatorRegistry.getInstance ().createExpression (node);
      if (exp == null)
        log.addMessage (node, "QTI custom operator not yet implemented. Value: " + node.getName ());
      break;
    case QTIXmlConstants.Expr.DurationGTE:
    case QTIXmlConstants.Expr.DurationLT:
    case QTIXmlConstants.Expr.FieldVal:
    case QTIXmlConstants.Expr.NumCorrect:
    case QTIXmlConstants.Expr.NumIncorrect:
    case QTIXmlConstants.Expr.NumPresented:
    case QTIXmlConstants.Expr.NumResponded:
    case QTIXmlConstants.Expr.NumSelected:
    case QTIXmlConstants.Expr.OutcomeMaximum:
    case QTIXmlConstants.Expr.OutcomeMinimum:
    case QTIXmlConstants.Expr.TestVariables:
      log.addMessage (node, "QTI Expression.FromXml called for that are not yet implemented. Value: " + node.getName ());
      break;
    default:
      log.addMessage (node, "Unrecognized Expression in Expression.FromXml(). Expression Node Name: '" + node.getName () + "'");
      break;
    }

    if (exp != null) {
      try {
        exp.processAttributes (node, nsmgr);
        exp.processParameters (node, nsmgr, log);
      } catch (Exception e) {
        log.addMessage (node, e.getMessage ());
        return null;
      }
    }
    return exp;
  }

  private void processParameters (Element node, XmlNamespaceManager nsmgr, ValidationLog log) {
    // XmlNodeList paramNodes =
    // node.SelectNodes(QTIXmlConstants.ExpressionElementGroup,nsmgr);
    List<Element> paramNodes = new XmlElement (node).selectNodes (QTIXmlConstants.ExpressionElementGroup, nsmgr);
    for (Element el : paramNodes) {
      Expression ex = Expression.fromXml (el, nsmgr, log);
      if (ex != null) {
        this.addParameter (ex);
      }
    }
  }

  private void processAttributes (Element node, XmlNamespaceManager nsmgr) throws Exception {
    for (ExpressionAttributeSpec eas : _attributes) {
      String val = node.getAttributeValue (eas.getName ());
      if (val != "") // should throw exception if attribute doesn't exist?
      {
        DataElement de = DataElement.create (val, eas.getValueType ());
        if (de.getIsError ())
          throw new Exception (de.getErrorMessage ());
        this.SetAttributeValue (eas.getName (), de);
      }
    }
    return;
  }

  private void SetAttributeValue (String name, DataElement de) {
    _attributeValues.put (name, de);
  }

  @Override
  boolean validate (ValidationLog log, QTIRubric rubric) {
    boolean ok = true;
    ok = validateParameterCount (log, ok);
    if (!validateAttributes (log, ok))
      ok = false;

    for (Expression parameter : _parameters) {
      if (!parameter.validate (log, rubric))
        ok = false;
    }
    for (ExpressionParameterConstraint epc : _parameterConstraints) {
      if (!epc.validateExpressions (_parameters, rubric, _node, log))
        ok = false;
    }
    return ok;
  }

  private boolean validateAttributes (ValidationLog log, boolean ok) {
    for (ExpressionAttributeSpec eas : _attributes) {
      DataElement val = getAttributeValue (eas.getName ());
      if (!eas.getIsOptional ()) {
        if (val == null) {
          log.addMessage (_node, TDSStringUtils.format ("Required attribute {0} is missing", eas.getName ()));
          ok = false;
        }
      }
      if (eas.getValueType () == BaseType.Identifier) {
        if (val != null) {
          if (!eas.ValidateIdentifierValue ((DEIdentifier) val)) {
            log.addMessage (_node, TDSStringUtils.format ("Trying to set attribute {0} to an unallowable value {1}", eas.getName (), ((DEIdentifier) val).getValue ()));
            ok = false;
          }
        }
      }
      if (((val == null) && (!eas.getIsOptional ())) || ((val != null) && (val.getType () == BaseType.Null) && (eas.getValueType () != BaseType.Null))) {
        log.addMessage (_node, TDSStringUtils.format ("Bad value for attribute {0}. Message: {1}", eas.getName (), val == null ? "No value given" : val.getErrorMessage ()));
        ok = false;
      }
    }
    return ok;
  }

  private boolean validateParameterCount (ValidationLog log, boolean ok) {
    int count = _parameters.size ();
    if (count < _minparameters) {
      ok = false;
      log.addMessage (_node, TDSStringUtils.format ("Expression requires a minumum of {0} parameters and received only {1}\n", _minparameters, count));
    }
    if (count > _maxparameters) {
      log.addMessage (_node, TDSStringUtils.format ("Expression allows a maximum of {0} parameters and received {1}\n", _maxparameters, count));
      ok = false;
    }
    return ok;
  }

  DataElement getAttributeValue (String attName) {
    if (_attributeValues.containsKey (attName))
      return _attributeValues.get (attName);
    return null;
  }

  void ResolveIdentifierType (QTIRubric rubric) {
    DEIdentifier id = (DEIdentifier) getAttributeValue ("identifier");
    if (id != null) {
      VariableDeclaration rd = rubric.getVariableDeclaration (id.getValue ());
      if (rd != null) {
        BaseType bt = rd.getType ();
        this._returnType = bt;
      }
    }
  }

  void ResolveIdentifierCardinality (QTIRubric rubric) {
    DEIdentifier id = (DEIdentifier) getAttributeValue ("identifier");
    if (id != null) {
      ResponseDeclaration rd = rubric.getResponseDeclaration (id.getValue ());
      if (rd != null) {
        Cardinality card = rd.getCardinality ();
        this._returnCardinality = card;
      }
    }
  }

  protected abstract DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws Exception;

  // {
  // DataElement de =
  // DataElement.Create("Evaluation fell though to base type--something not implemented",
  // BaseType.Null);
  // return de;
  // }

  @Override
  DataElement evaluate (VariableBindings vb, QTIRubric rubric) throws Exception {
    List<DataElement> paramValues = new ArrayList<DataElement> ();
    for (Expression param : _parameters) {
      DataElement result = param.evaluate (vb, rubric);
      paramValues.add (result);
    }
    return exprEvaluate (vb, rubric, paramValues);
  }

}
