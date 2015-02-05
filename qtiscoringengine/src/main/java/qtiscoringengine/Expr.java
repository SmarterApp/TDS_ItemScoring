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
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Utilities.MathUtils;
import AIR.Common.Utilities.TDSStringUtils;

class ExprNot extends Expression
{
  ExprNot (Element node) {
    super (node, 1, 1, BaseType.Boolean, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.Boolean, true, true));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    // Shiva: What if it is not instance of DEBoolean ?
    DEBoolean result = (DEBoolean) paramValues.get (0);
    return new DEBoolean (!result.getBooleanValue ());
  }
}// end class ExprNot

class ExprAnd extends Expression
{
  ExprAnd (Element node) {
    super (node, 1, Integer.MAX_VALUE, BaseType.Boolean, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.Boolean, true, true));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    boolean hadNull = false;
    for (DataElement param : paramValues) {
      if (param == null) {
        hadNull = true;
        continue;
      }
      // Shiva: What if it is not instance of DEBoolean ?
      if (((DEBoolean) param).getBooleanValue () == false)
        return new DEBoolean (false);
    }
    return hadNull ? null : new DEBoolean (true);
  }
}// end class ExprAnd

class ExprOr extends Expression
{
  ExprOr (Element node) {
    super (node, 1, Integer.MAX_VALUE, BaseType.Boolean, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.Boolean, true, true));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    for (DataElement param : paramValues) {
      // Shiva: What if it is not instance of DEBoolean?
      if (((DEBoolean) param).getBooleanValue ())
        return new DEBoolean (true);
    }
    return new DEBoolean (false);
  }

}// end class ExprOR

class ExprAnyN extends Expression
{
  ExprAnyN (Element node) {
    super (node, 1, Integer.MAX_VALUE, BaseType.Boolean, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.Boolean, true, true));
    addAttribute (new ExpressionAttributeSpec ("min", BaseType.Integer));
    addAttribute (new ExpressionAttributeSpec ("max", BaseType.Integer));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    int count = 0;
    int min = ((DEInteger) getAttributeValue ("min")).getValue ();
    int max = ((DEInteger) getAttributeValue ("max")).getValue ();

    for (DataElement param : paramValues) {
      if (param instanceof DEBoolean) {
        if (((DEBoolean) param).getBooleanValue ())
          ++count;
      } else {
        // Shiva: What if not instance of DEBoolean ?
      }
    }

    if ((count >= min) && (count <= max))
      return new DEBoolean (true);
    else
      return new DEBoolean (false);
  }
}// end class ExprAny

class ExprMatch extends Expression
{
  ExprMatch (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.File, BaseType.Identifier, BaseType.Integer, BaseType.String, BaseType.Pair, BaseType.Point, BaseType.URI);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.None, types, true, true));
  }

  @Override
  public DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if ((paramValues.get (0) == null) || (paramValues.get (1) == null))
      return null;
    return new DEBoolean (paramValues.get (0).equals (paramValues.get (1)));
  }
}// end class ExprMatch

class ExprStringMatch extends Expression
{
  ExprStringMatch (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("caseSensitive", BaseType.Boolean);
    addAttribute (eac);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.String, true, true));
  }

  @Override
  public DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEBoolean cs = (DEBoolean) getAttributeValue ("caseSensitive");
    boolean caseSensitive = false;
    if (cs != null)
      caseSensitive = cs.getBooleanValue ();
    // handle when paramValues.count == 0?
    String string1 = ((DEString) paramValues.get (0)).getValue ();
    // jdc. Sept 6, 2014. This was paramValues[0]. fixed to paramValues[1]
    String string2 = ((DEString) paramValues.get (1)).getValue ();
    if (caseSensitive) {
      string1 = string1.toLowerCase ();
      string2 = string2.toLowerCase ();
    }
    return new DEBoolean (StringUtils.equals (string1, string2));
  }
}// end class ExprStringMatch

class ExprPatternMatch extends Expression
{
  ExprPatternMatch (Element node) {
    super (node, 1, 1, BaseType.Boolean, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.Single, BaseType.String, true, true));
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("pattern", BaseType.String);
    addAttribute (eac);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEString val = (DEString) getAttributeValue ("pattern");
    Pattern pattern = Pattern.compile (val.getValue ());
    return new DEBoolean (Pattern.matches (val.getValue (), ((DEString) paramValues.get (0)).getValue ()));
  }
}// end class ExprPatternMatch

class ExprEqual extends Expression
{
  private enum ToleranceMode {
    Exact, Absolute, Relative
  };

  private ToleranceMode _mode              = ToleranceMode.Exact;
  private double        _allowLow          = 0.0;
  private double        _allowHi           = 0.0;
  private boolean       _includeLowerBound = true;
  private boolean       _includeUpperBound = true;

  ExprEqual (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, false, false));
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("toleranceMode", Arrays.asList ("exact", "absolute", "relative"), true);
    addAttribute (eac);
    eac = new ExpressionAttributeSpec ("tolerance", BaseType.String, true);// they
                                                                           // stuff
                                                                           // to
                                                                           // floats
                                                                           // into
                                                                           // this
                                                                           // attribute.
                                                                           // Does
                                                                           // not
                                                                           // implement
                                                                           // template
                                                                           // processing--this
                                                                           // is
                                                                           // spec'd
                                                                           // as
                                                                           // floatOrTemplateRef
    addAttribute (eac);
    eac = new ExpressionAttributeSpec ("includeUpperBound", BaseType.Boolean, true);
    addAttribute (eac);
    eac = new ExpressionAttributeSpec ("includeLowerBound", BaseType.Boolean, true);
    addAttribute (eac);
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    boolean ok = super.validate (log, rubric);
    DEIdentifier toleranceMode = (DEIdentifier) getAttributeValue ("toleranceMode");
    DEString tolerance = (DEString) getAttributeValue ("tolerance");
    DEBoolean includeUpper = (DEBoolean) getAttributeValue ("includeUpperBound");
    DEBoolean includeLower = (DEBoolean) getAttributeValue ("includeLowerBound");

    if (toleranceMode == null) {
      _mode = ToleranceMode.Exact;
    } else {
      ok = validateTolerance (ok, log, toleranceMode, tolerance, includeLower, includeUpper);
    }

    return ok;
  }

  private boolean validateTolerance (boolean ok, ValidationLog log, DEIdentifier toleranceMode, DEString tolerance, DEBoolean includeLower, DEBoolean includeUpper) {
    _Ref<ToleranceMode> _mode = new _Ref<> ();
    boolean status = JavaPrimitiveUtils.enumTryParse (ToleranceMode.class, toleranceMode.getValue (), true, _mode);
    if (status == false) {
      log.addMessage (_node, TDSStringUtils.format ("Tolerance mode, if provided, must be one of 'exact', 'absolute','relative'. Got {0}", toleranceMode.getValue ()));
      return false;
    }

    DEContainer tolerances = null;
    try {
      tolerances = (DEContainer) DataElement.createContainer (tolerance.getValue (), BaseType.Float, Cardinality.Ordered);
    } catch (Exception e) {
      if (status) {
        if (_mode.get () != ToleranceMode.Exact) {
          log.addMessage (_node, "Tolerances must be specified if the tolerance mode is not 'exact' and must be one or two valid floats");
          return false;
        }
      }
    }
    if ((tolerances == null) || (tolerances.getMemberCount () == 0) || (tolerances.getMember (0) == null)) {
      if (_mode.get () != ToleranceMode.Exact) {
        log.addMessage (_node, "Tolerances must be specified if the tolerance mode is not 'exact' and must be one or two valid floats");
        return false;
      }
    } else {
      _allowLow = ((_DEFloat) tolerances.getMember (0)).getValue ().doubleValue ();

      if (tolerances.getMember (1) == null) {
        _allowHi = _allowLow;
      } else
        _allowHi = ((_DEFloat) tolerances.getMember (1)).getValue ().doubleValue ();
    }

    DEBoolean includeLow = (DEBoolean) getAttributeValue ("includeLowerBound");
    DEBoolean includeHi = (DEBoolean) getAttributeValue ("includeUpperBound");

    // jdc. Sept 9, 2014. Removed this validation because defaults are provided
    // and documented in the spec.
    // if (_mode != ToleranceMode.Exact) //removed this
    /* Start Removed */
    // if (_mode.get () != ToleranceMode.Exact) {
    // if ((includeLow == null) || (includeHi == null)) {
    // log.addMessage (_node,
    // "When the tolerance mode is not 'exact' you must specify includeLowerBound and includeUpperBound");
    // return false;
    // }
    // }
    /* End Removed */
    if (includeLow != null)
      _includeLowerBound = includeLow.getBooleanValue ();
    if (includeHi != null)
      _includeUpperBound = includeHi.getBooleanValue ();
    return ok;
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws QTIScoringException {
    _DEFloat val1 = (_DEFloat) paramValues.get (0);
    _DEFloat val2 = (_DEFloat) paramValues.get (1);
    if ((val1 == null) || (val2 == null))
      return new DEBoolean (false);
    switch (_mode) {
    case Exact:
      return new DEBoolean (val1.equals (val2));
    case Absolute: // In absolute mode the result of the
                   // comparison is true if the value of the
                   // second expression, y is within the following
                   // range defined by the first value, x.
                   // x-t0,x+t1
      return new DEBoolean ((_includeLowerBound ? val2.gte (val1.getValue ().doubleValue () - _allowLow) : val2.gt (val1.getValue ().doubleValue () - _allowLow))
          && (_includeUpperBound ? val2.lte (val1.getValue ().doubleValue () + _allowHi) : val2.lt (val1.getValue ().doubleValue () + _allowHi)));
    case Relative: // In relative mode, t0 and t1 are treated as
                   // percentages and the following range is used
                   // instead. x*(1-t0/100),x*(1+t1/100)
      return new DEBoolean ((_includeLowerBound ? val2.gte (val1.getValue ().doubleValue () * (1.0 - _allowLow / 100.0)) : val2.gt (val1.getValue ().doubleValue () * (1.0 - _allowLow / 100.0)))
          && (_includeUpperBound ? val2.lte (val1.getValue ().doubleValue () * (1.0 + _allowHi / 100.0)) : val2.lt (val1.getValue ().doubleValue () * (1.0 + _allowHi / 100.0))));
    default:
      throw new QTIScoringException ("ExprEquals got an unknown tolerance mode");

    }
  }
}// end class ExprEqual

class ExprEqualRounded extends Expression
{
  // this is a very stupid default. I am going to require the mode attribute
  private DEFloat.RoundingMode _roundingMode = DEFloat.RoundingMode.SignificantFigures;
  private int                  _figures      = 1;

  ExprEqualRounded (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, false, false));
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("roundingMode", Arrays.asList ("significantFigures", "decimalPlaces"));
    addAttribute (eac);
    eac = new ExpressionAttributeSpec ("figures", BaseType.Integer);// does not
                                                                    // implement
                                                                    // template
                                                                    // ref
    addAttribute (eac);
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    boolean ok = super.validate (log, rubric);
    DEIdentifier roundingMode = (DEIdentifier) getAttributeValue ("roundingMode");
    DEInteger figures = (DEInteger) getAttributeValue ("figures");

    _Ref<DEFloat.RoundingMode> _roundingMode = new _Ref<> ();
    boolean status = JavaPrimitiveUtils.enumTryParse (DEFloat.RoundingMode.class, roundingMode.getValue (), true, _roundingMode);
    if (status == false) {
      log.addMessage (_node, "Invalid rounding mode, must be 'significantFigures' or 'decimalPlaces'");
      ok = false;
    }
    _figures = figures.getValue ();
    return ok;
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if ((paramValues.get (0) == null) || (paramValues.get (1) == null))
      return null;

    _DEFloat val1 = (_DEFloat) paramValues.get (0);
    _DEFloat val2 = (_DEFloat) paramValues.get (1);
    double val1Rounded = val1.round (_roundingMode, _figures);
    double val2Rounded = val2.round (_roundingMode, _figures);

    return new DEBoolean (val1Rounded == val2Rounded);
  }
}// end class ExprEqualRounded

class ExprInside extends Expression
{
  private Area _area = null;

  ExprInside (Element node) {
    super (node, 1, 1, BaseType.Boolean, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.Point));
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("shape", Arrays.asList ("default", "rect", "circle", "poly"));
    addAttribute (eac);
    eac = new ExpressionAttributeSpec ("coords", BaseType.String);// this string
                                                                  // contains a
                                                                  // comma-separated
                                                                  // list of
                                                                  // integers.
                                                                  // This is how
                                                                  // the
                                                                  // examples
                                                                  // read,
                                                                  // despite the
                                                                  // spec that
                                                                  // calls this
                                                                  // out as an
                                                                  // XTML
                                                                  // ordered
                                                                  // list.
    addAttribute (eac);
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    boolean ok = super.validate (log, rubric);
    DEIdentifier shapeName = (DEIdentifier) getAttributeValue ("shape");
    DEString coords = (DEString) getAttributeValue ("coords");

    if (shapeName == null) {
      ok = false;
      log.addMessage (_node, "Expression 'inside' must specify a shape");
    }

    _Ref<Shape> shape = new _Ref<> ();
    boolean status = JavaPrimitiveUtils.enumTryParse (Shape.class, shapeName.getValue (), true, shape);
    if (!status) {
      log.addMessage (_node, TDSStringUtils.format ("expression 'inside' requires a shape to be 'default', 'rect', 'circle' or 'poly', but got {0}", shapeName.getValue ()));
      ok = false;
    }

    if (coords == null) {
      if (shape.get () != Shape.Default) {
        log.addMessage (_node, "expression 'inside' requires coordinates for its shape, unless the shape is default, covering the entire area.");
        ok = false;
      }
    }

    if (ok) {
      try {
        _area = Area.create (shape.get (), coords.getValue ());
      } catch (Exception e) {
        log.addMessage (_node, TDSStringUtils.format ("Unable to create area object: {0}", e.getMessage ()));
        ok = false;
      }
    }
    return ok;
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if (paramValues.get (0) == null)
      return null;
    return _area.getIsInside ((DEPoint) paramValues.get (0));
  }
}// end class ExprInside

class ExprLt extends Expression
{
  ExprLt (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, true, true));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    return new DEBoolean (((_DEFloat) paramValues.get (0)).getValue ().doubleValue () < ((_DEFloat) paramValues.get (0)).getValue ().doubleValue ());
  }

}// end class ExprLt

class ExprGt extends Expression
{
  ExprGt (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, true, true));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    return new DEBoolean (((_DEFloat) paramValues.get (0)).getValue ().doubleValue () > ((_DEFloat) paramValues.get (0)).getValue ().doubleValue ());
  }
}// end class ExprGt

class ExprLte extends Expression
{
  ExprLte (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, true, true));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    return new DEBoolean (((_DEFloat) paramValues.get (0)).getValue ().doubleValue () <= ((_DEFloat) paramValues.get (0)).getValue ().doubleValue ());
  }
}// end class ExprLte

class ExprGte extends Expression
{
  ExprGte (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, true, true));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    return new DEBoolean (((_DEFloat) paramValues.get (0)).getValue ().doubleValue () >= ((_DEFloat) paramValues.get (0)).getValue ().doubleValue ());
  }
}// end class ExprGte

class ExprSum extends Expression
{
  ExprSum (Element node) {
    super (node, 1, Integer.MAX_VALUE, BaseType.Float, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    double val = 0.0;
    for (DataElement datum : paramValues) {
      if (datum == null)
        return null;
      val += ((_DEFloat) datum).getValue ().doubleValue ();
    }

    return new DEFloat (val);
  }
}// end class ExprSum

class ExprProduct extends Expression
{
  ExprProduct (Element node) {
    super (node, 1, Integer.MAX_VALUE, BaseType.Float, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if (CollectionUtils.exists (paramValues, new Predicate ()
    {

      @Override
      public boolean evaluate (Object arg0) {
        if (arg0 == null)
          return true;
        return false;
      }
    }))
      return null;

    double val = ((_DEFloat) paramValues.get (0)).getValue ().doubleValue ();

    for (int i = 1; i < paramValues.size (); i++) {
      val *= ((_DEFloat) paramValues.get (i)).getValue ().doubleValue ();
    }
    return new DEFloat (val);
  }
}// end class ExprProduct

class ExprSubtract extends Expression
{
  ExprSubtract (Element node) {
    super (node, 2, 2, BaseType.Float, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {

    if (CollectionUtils.exists (paramValues, new Predicate ()
    {

      @Override
      public boolean evaluate (Object arg0) {
        if (arg0 == null)
          return true;
        return false;
      }
    }))
      return null;

    return new DEFloat (((_DEFloat) paramValues.get (0)).getValue ().doubleValue () - ((_DEFloat) paramValues.get (1)).getValue ().doubleValue ());
  }
}// end class ExprSubtract

class ExprDivide extends Expression
{
  ExprDivide (Element node) {
    super (node, 2, 2, BaseType.Float, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if (CollectionUtils.exists (paramValues, new Predicate ()
    {

      @Override
      public boolean evaluate (Object arg0) {
        if (arg0 == null)
          return true;
        return false;
      }
    }))
      return null;

    return new DEFloat (((_DEFloat) paramValues.get (0)).getValue ().doubleValue () / ((_DEFloat) paramValues.get (1)).getValue ().doubleValue ());
  }
}// end class ExprDivide

class ExprPower extends Expression
{
  ExprPower (Element node) {
    super (node, 2, 2, BaseType.Float, Cardinality.Single);
    List<BaseType> types = Arrays.asList (BaseType.Integer, BaseType.Float);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, types, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    for (DataElement datum : paramValues) {
      if (datum == null)
        return null;
    }

    return new DEFloat (Math.pow (((_DEFloat) paramValues.get (0)).getValue ().doubleValue (), ((_DEFloat) paramValues.get (1)).getValue ().doubleValue ()));
  }

}// end class ExprPower

class ExprIntegerDivide extends Expression
{
  ExprIntegerDivide (Element node) {
    super (node, 2, 2, BaseType.Integer, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.Integer, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    for (DataElement datum : paramValues) {
      if (datum == null)
        return null;
    }

    return new DEInteger (((DEInteger) paramValues.get (0)).getValue ().intValue () / ((DEInteger) paramValues.get (1)).getValue ().intValue ());
  }
}// end class ExprIntegerDivide

class ExprIntegerModulus extends Expression
{
  ExprIntegerModulus (Element node) {
    super (node, 2, 2, BaseType.Integer, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.Integer, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    for (DataElement datum : paramValues) {
      if (datum == null)
        return null;
    }

    return new DEInteger (((DEInteger) paramValues.get (0)).getValue ().intValue () % ((DEInteger) paramValues.get (1)).getValue ().intValue ());
  }

}// end class ExprModulus

class ExprTruncate extends Expression
{
  ExprTruncate (Element node) {
    super (node, 1, 1, BaseType.Integer, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.Single, BaseType.Float, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if ((paramValues.size () == 0) || (paramValues.get (0) == null))
      return null;

    return new DEInteger ((int) MathUtils.truncate (((_DEFloat) paramValues.get (0)).getValue ().doubleValue ()));
  }

}// end class ExprTruncate

class ExprRound extends Expression
{
  ExprRound (Element node) {
    super (node, 1, 1, BaseType.Integer, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.Single, BaseType.Float, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if (paramValues.get (0) == null)
      return null;
    return new DEInteger ((int) ((_DEFloat) paramValues.get (0)).round (DEFloat.RoundingMode.DecimalPlaces, 0));
  }
}// end class ExprRound

class ExprIntegerToFloat extends Expression
{
  ExprIntegerToFloat (Element node) {
    super (node, 1, 1, BaseType.Float, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.Single, BaseType.Integer, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    return (_DEFloat) paramValues.get (0);
  }
}// end class ExprIntegerToFloat

class ExprBaseValue extends Expression
{
  private String      _value;
  private DataElement _dataValue;

  ExprBaseValue (Element node) {
    super (node, 0, 0, BaseType.String, Cardinality.None);
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("baseType", BaseType.BaseType);
    addAttribute (eac);

    _value = node.getText ();// node.InnerText;
  }

  // todo: should probably move processing of the constant value to the
  // validation section
  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    boolean ok = super.validate (log, rubric);

    resolveType (rubric);

    if (ok) {
      DataElement de = DataElement.create (_value, _returnType);
      if (de.getType () == BaseType.Null) {
        log.addMessage (_node, TDSStringUtils.format ("Unable to interpret element content ({0}) as {1}", _value, _returnType.toString ()));
        ok = false;
      }

      if (ok) {
        if (_returnCardinality == Cardinality.Single) {
          _dataValue = DataElement.create (_value, _returnType);
          if (_dataValue.getType () != _returnType) {
            ok = false;
            log.addMessage (_node, "Unable to turn the given string into the requested base type and cardinality");
          }
        } else {
          _dataValue = DataElement.createContainer (_value, _returnType, _returnCardinality);
          if (_dataValue.getType () == BaseType.Null) {
            ok = false;
            log.addMessage (_node, "Unable to turn the given string into the requested base type and cardinality");
          }
        }
      }
    }
    return ok;
  }

  void resolveType (QTIRubric rubric) {
    DEBaseType de = (DEBaseType) getAttributeValue ("baseType");
    if (de != null) {
      _returnType = de.getValue ();
    }

    _returnCardinality = Cardinality.Single;

    if (_returnType != BaseType.String) // this is a kludge, because baseValues
                                        // are always supposed to be
                                        // single-valued, but examples use them
                                        // as
    {
      if (_value.trim ().contains (" "))
        _returnCardinality = Cardinality.None;
    }
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    return _dataValue;
  }

}// end class ExprBaseType

class ExprVariable extends Expression
{
  ExprVariable (Element node) {
    super (node, 0, 0, BaseType.Null, Cardinality.None);
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("identifier", BaseType.Identifier);
    addAttribute (eac);
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    resolveIdentifierCardinality (rubric);
    resolveIdentifierType (rubric);
    return super.validate (log, rubric);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEIdentifier identifier = (DEIdentifier) getAttributeValue ("identifier");
    return vb.getVariable (identifier);
    // if (_result == null) //the spec is unclear. GetVariable will return the
    // default value if another value has not been provided.
    // _result = rubric.GetDefaultValue(identifier);
  }

}// end class ExprVariable

class ExprDefault extends Expression
{
  ExprDefault (Element node) {
    super (node, 0, 0, BaseType.Null, Cardinality.None);
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("identifier", BaseType.Identifier);
    addAttribute (eac);
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    resolveIdentifierCardinality (rubric);
    resolveIdentifierType (rubric);
    return super.validate (log, rubric);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEIdentifier identifier = (DEIdentifier) getAttributeValue ("identifier");
    return rubric.getDefaultValue (identifier);
  }
}// end class ExprDefault

class ExprCorrect extends Expression
{
  ExprCorrect (Element node) {
    super (node, 0, 0, BaseType.Null, Cardinality.None);
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("identifier", BaseType.Identifier);
    addAttribute (eac);
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    resolveIdentifierCardinality (rubric);
    resolveIdentifierType (rubric);
    return super.validate (log, rubric);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEIdentifier identifier = (DEIdentifier) getAttributeValue ("identifier");
    return rubric.getCorrectValue (identifier);
  }
}// end class ExprCorrect

class ExprMapResponse extends Expression
{
  ExprMapResponse (Element node) {
    super (node, 0, 0, BaseType.Float, Cardinality.Single);
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("identifier", BaseType.Identifier);
    addAttribute (eac);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEIdentifier varname = (DEIdentifier) getAttributeValue ("identifier");
    VariableMapping map = rubric.getResponseMapping (varname);
    if (map == null)
      return null;

    double val = map.mapResponse (vb.getVariable (varname));
    return new DEFloat (val);
  }
}// end class ExprMapResponse

class ExprMapResponsePoint extends Expression
{
  ExprMapResponsePoint (Element node) {
    super (node, 0, 0, BaseType.Float, Cardinality.Single);
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("identifier", BaseType.Identifier);
    addAttribute (eac);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEIdentifier varname = (DEIdentifier) getAttributeValue ("identifier");
    AreaMapping map = rubric.getAreaMapping (varname);
    if (map == null)
      return null;

    double val = map.mapResponse (vb.getVariable (varname));
    return new DEFloat (val);
  }
}// end class ExprMapResponsePoint

class ExprNull extends Expression
{
  ExprNull (Element node) {
    super (node, 0, 0, BaseType.Null, Cardinality.None);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    return null;
  }
}// end class ExprNull

class ExprRandomInteger extends Expression
{
  ExprRandomInteger (Element node) {
    super (node, 0, 0, BaseType.Integer, Cardinality.Single);
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("min", BaseType.Integer, true);
    addAttribute (eac);
    eac = new ExpressionAttributeSpec ("max", BaseType.Integer);
    addAttribute (eac);
    eac = new ExpressionAttributeSpec ("step", BaseType.Integer, true);
    addAttribute (eac);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEInteger minx = (DEInteger) getAttributeValue ("min");
    DEInteger maxx = (DEInteger) getAttributeValue ("max");
    DEInteger stepx = (DEInteger) getAttributeValue ("step");

    int min = (minx == null) ? 0 : minx.getValue ();
    int step = (stepx == null) ? 0 : stepx.getValue ();
    int max = maxx.getValue ();

    int valCount = (max - min) / step;
    Random randomizer = new Random ();
    double rand = randomizer.nextDouble () * 1000000;
    int val = (int) (rand % valCount);
    return new DEInteger ((int) val * step + min);
  }
}// end class ExprRandomInteger

class ExprRandomFloat extends Expression
{
  ExprRandomFloat (Element node) {
    super (node, 0, 0, BaseType.Float, Cardinality.Single);
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("min", BaseType.Float);
    addAttribute (eac);
    eac = new ExpressionAttributeSpec ("max", BaseType.Float);
    addAttribute (eac);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    _DEFloat min = (_DEFloat) getAttributeValue ("min");
    _DEFloat max = (_DEFloat) getAttributeValue ("max");

    Random randomizer = new Random ();
    double rand = randomizer.nextDouble ();
    return new DEFloat (min.getValue ().doubleValue () + rand * (max.getValue ().doubleValue () - min.getValue ().doubleValue ()));
  }
}// end class ExprRandomFloat

class ExprMultiple extends Expression
{
  ExprMultiple (Element node) {
    super (node, 0, Integer.MAX_VALUE, BaseType.Null, Cardinality.Multiple);
    List<Cardinality> cards = Arrays.asList (Cardinality.Single, Cardinality.Multiple);
    List<BaseType> types = Arrays.asList (BaseType.Null);
    addParameterConstraint (new ExpressionParameterConstraint (-1, cards, types, false, true));
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    return resolveType (super.validate (log, rubric));
  }

  private boolean resolveType (boolean ok) {
    for (Expression exp : _parameters) {
      if (exp != null) {
        _returnType = exp.getReturnType ();
      }
      return ok;
    }
    return ok;
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    BaseType bt = BaseType.Null;
    DEContainer rslt = null;
    for (DataElement de : paramValues) {
      if (de == null)
        continue;
      if (bt == BaseType.Null) {
        bt = de.getType ();
        rslt = new DEContainer (bt, Cardinality.Multiple);
      }

      if (de.getIsContainer ()) {
        DEContainer container = (DEContainer) de;
        for (int i = 0; i < container.getMemberCount (); i++) {
          if (container.getMember (i) != null)
            rslt.add (container.getMember (i));
        }
      } else
        rslt.add (de);
    }

    return rslt;
  }
}// end class ExprMultiple

class ExprOrdered extends Expression
{
  ExprOrdered (Element node) {
    super (node, 0, Integer.MAX_VALUE, BaseType.Null, Cardinality.Ordered);
    List<Cardinality> cards = Arrays.asList (Cardinality.Single, Cardinality.Ordered);
    List<BaseType> types = Arrays.asList (BaseType.Null);
    addParameterConstraint (new ExpressionParameterConstraint (-1, cards, types, false, true));
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    boolean ok = super.validate (log, rubric);

    return resolveType (ok);
  }

  private boolean resolveType (boolean ok) {
    for (Expression exp : _parameters) {
      if (exp != null) {
        _returnType = exp.getReturnType ();
      }
      return ok;
    }
    return ok;
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    BaseType bt = BaseType.Null;
    DEContainer rslt = null;
    for (DataElement de : paramValues) {
      if (de == null)
        continue;
      if (bt == BaseType.Null) {
        bt = de.getType ();
        rslt = new DEContainer (bt, Cardinality.Ordered);
      }

      if (de.getIsContainer ()) {
        DEContainer container = (DEContainer) de;
        for (int i = 0; i < container.getMemberCount (); i++) {
          if (container.getMember (i) != null)
            rslt.add (container.getMember (i));
        }
      } else
        rslt.add (de);
    }

    return rslt;
  }

}// end class ExprOrdered

class ExprContainerSize extends Expression
{
  ExprContainerSize (Element node) {
    super (node, 1, 1, BaseType.Integer, Cardinality.Single);
    List<Cardinality> cards = Arrays.asList (Cardinality.Multiple, Cardinality.Ordered);
    List<BaseType> types = Arrays.asList (BaseType.Null);
    addParameterConstraint (new ExpressionParameterConstraint (0, cards, types, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if (paramValues.get (0) == null)
      return new DEInteger (0);

    return new DEInteger (((DEContainer) paramValues.get (0)).getMemberCount ());
  }
}// end class ExprContainerSize

class ExprIsNull extends Expression
{
  ExprIsNull (Element node) {
    super (node, 1, 1, BaseType.Boolean, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.None, BaseType.Null, false, false));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if ((paramValues.get (0) == null) || (paramValues.get (0).getType () == BaseType.Null))
      return new DEBoolean (true);
    else
      return new DEBoolean (false);
  }
}// end class ExprIsNull

class ExprIndex extends Expression
{
  ExprIndex (Element node) {
    super (node, 1, 1, BaseType.Null, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.Ordered, BaseType.Null, false, false));
    addAttribute (new ExpressionAttributeSpec ("n", BaseType.Integer, false)); // jdc.
                                                                               // sept
                                                                               // 7
                                                                               // 2014.
                                                                               // Changed
                                                                               // to
                                                                               // "n"
                                                                               // which
                                                                               // matches
                                                                               // spec.
                                                                               // made
                                                                               // it
                                                                               // mandatory.
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEInteger idx = (DEInteger) getAttributeValue ("n");
    if (idx == null)
      return null;
    DataElement param = paramValues.get (0);
    if (param == null)
      return null;

    if (!param.getIsContainer ()) {
      // convert to a single element container
      DEContainer container = new DEContainer (param.getType (), Cardinality.Ordered);
      container.add (param);
      param = container;
    }

    DEContainer vals = (DEContainer) param;
    if (vals.getMemberCount () < idx.getValue ())
      return null;
    if (idx.getValue () < 1)
      return null;

    return vals.getMember (idx.getValue () - 1);
  }
}// end class ExprIndex

class ExprRandom extends Expression
{
  ExprRandom (Element node) {
    super (node, 1, 1, BaseType.Null, Cardinality.Single);
    List<Cardinality> cards = Arrays.asList (Cardinality.Multiple, Cardinality.Ordered);
    List<BaseType> types = Arrays.asList (BaseType.Null);
    addParameterConstraint (new ExpressionParameterConstraint (0, cards, types, false, false));
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    boolean ok = super.validate (log, rubric);
    for (Expression exp : _parameters) {
      if (exp != null) {
        _returnType = exp.getReturnType ();
        return ok;
      }
    }
    return ok;
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    Random randomizer = new Random ();
    int randSet = (int) MathUtils.truncate (randomizer.nextDouble () * 1000000);
    DEContainer param = (DEContainer) paramValues.get (0);
    if (param == null)
      return null;
    if (param.getMemberCount () == 0)
      return null;
    int idx = randSet % param.getMemberCount ();
    return param.getMember (idx);
  }
}// end class ExprRandom

class ExprMember extends Expression
{
  ExprMember (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    List<Cardinality> cards = Arrays.asList (Cardinality.Multiple, Cardinality.Ordered);
    List<BaseType> types = Arrays.asList (BaseType.Null);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.Single, BaseType.Null));
    addParameterConstraint (new ExpressionParameterConstraint (1, cards, types, false, false));
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.None, BaseType.Null, false, true));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if ((paramValues.get (0) == null) || (paramValues.get (1) == null))
      return null;
    return new DEBoolean (((DEContainer) paramValues.get (1)).contains (paramValues.get (0)));
  }
}// end class ExprMember

class ExprDelete extends Expression
{
  ExprDelete (Element node) {
    // this could be a problem--if the original was orderded
    super (node, 2, 2, BaseType.Null, Cardinality.Multiple);
    List<Cardinality> cards = Arrays.asList (Cardinality.Multiple, Cardinality.Ordered);
    List<BaseType> types = Arrays.asList (BaseType.Null);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.Single, BaseType.Null));
    addParameterConstraint (new ExpressionParameterConstraint (1, cards, types, false, false));
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.None, BaseType.Null, false, true));
  }

  @Override
  protected boolean validate (ValidationLog log, QTIRubric rubric) {
    return (resolveType (super.validate (log, rubric)));
  }

  private boolean resolveType (boolean ok) {
    for (Expression exp : _parameters) {
      if (exp != null) {
        _returnType = exp.getReturnType ();
      }
      return ok;
    }
    return ok;
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if ((paramValues.get (0) == null) || (paramValues.get (1) == null))
      return null;
    return (((DEContainer) paramValues.get (1)).delete (paramValues.get (0)));
  }
}// end class ExprDelete

class ExprContains extends Expression
{
  ExprContains (Element node) {
    super (node, 2, 2, BaseType.Boolean, Cardinality.Single);
    List<Cardinality> cards = Arrays.asList (Cardinality.Multiple, Cardinality.Ordered);
    List<BaseType> types = Arrays.asList (BaseType.Null);
    addParameterConstraint (new ExpressionParameterConstraint (-1, cards, types, true, true));
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    if ((paramValues.get (0) == null) || (paramValues.get (1) == null))
      return null;
    return new DEBoolean (((DEContainer) paramValues.get (1)).contains (paramValues.get (0)));
  }
}// end class ExprContains

class ExprSubstring extends Expression
{
  ExprSubstring (Element node) {
    super (node, 2, 2, BaseType.String, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (-1, Cardinality.Single, BaseType.String, true, true));
    ExpressionAttributeSpec eac = new ExpressionAttributeSpec ("caseSensitive", BaseType.Boolean);
    addAttribute (eac);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    boolean caseSensitive = true;

    if ((paramValues.get (0) == null) || (paramValues.get (1) == null))
      return null;
    DEBoolean cs = (DEBoolean) getAttributeValue ("caseSensitive");
    if (cs != null)
      caseSensitive = cs.getBooleanValue ();

    if (caseSensitive)
      return new DEBoolean (((DEString) paramValues.get (1)).getValue ().contains (((DEString) paramValues.get (0)).getValue ()));
    else
      return new DEBoolean (((DEString) paramValues.get (1)).getValue ().toLowerCase ().contains (((DEString) paramValues.get (0)).getValue ().toLowerCase ()));
  }

}// end class ExprSubstring

