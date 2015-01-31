/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import qtiscoringengine.DEFloat.RoundingMode;

/*
 * 
 * DEInteger used to inherit from DataElement but now it inherits from DEFloat.
 * 
 * In the .NET code we have used the following syntax:
 * 
 * internal new int Value { get { return _value; } }
 * 
 * 
 * This basically allows DEInteger to provide its own implementation of Value
 * that returns integer.
 * 
 * In someways it is not strictly polymorphic.
 * 
 * To get around
 */

public abstract class _DEFloat<N extends Number> extends DataElement
{
  public static final double DEFAULT_TOLERANCE = 0.0000001; // a tolerance to

  // use when
  // comparing 2
  // DEFloat instances

  protected _DEFloat () {
  }

  public abstract N getValue ();

  public boolean lt (double value) {
    return (getValue ().doubleValue () < value);
  }

  public boolean gt (double value) {
    return (getValue ().doubleValue () > value);
  }

  public boolean lte (double value) {
    return (getValue ().doubleValue () <= value);
  }

  public boolean gte (double value) {
    return (getValue ().doubleValue () >= value);
  }

  // Shiva: The below code just mirrors the .NET version as much as possible
  // without
  // optimizations.
  @SuppressWarnings ("unused")
  @Override
  public boolean equals (DataElement d) {
    @SuppressWarnings ("rawtypes")
    _DEFloat i = (_DEFloat) d;
    if (i != null) {
      return getValue ().doubleValue () == i.getValue ().doubleValue ();
    }

    @SuppressWarnings ("rawtypes")
    _DEFloat f = i;
    if (f != null) {
      return Math.abs (getValue ().doubleValue () - f.getValue ().doubleValue ()) < DEFAULT_TOLERANCE;
    }
    return false;
  }

  public double round (RoundingMode _roundingMode, int figures) {
    if (_roundingMode == RoundingMode.DecimalPlaces)
      return roundToDecimals (figures);
    return roundToSignficantFigures (figures);
  }

  private double roundToDecimals (int figures) {
    // return Math.Round(_value, figures);
    double m = Math.pow (10, figures);
    return Math.round (getValue ().doubleValue () * m) / m;
  }

  // todo: please test this. I have not.
  // Zach: When rounding up it stores 1.3333444556 as 1.3333444559999998
  // internally, but prints the correct value.
  private double roundToSignficantFigures (int figures) {
    double value = getValue ().doubleValue ();
    if (value == 0.0)
      return 0.0;

    double scale = Math.pow (10.0, Math.floor (Math.log10 (Math.abs (value))) + 1.0);
    // return scale * Math.Round(_value / scale, figures);
    double m = Math.pow (10, figures);
    return scale * Math.round (value / scale * m) / m;
  }
}
