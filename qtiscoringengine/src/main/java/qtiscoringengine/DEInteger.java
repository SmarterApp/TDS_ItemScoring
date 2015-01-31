/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

public class DEInteger extends _DEFloat<Integer>
{
  private int _value;

  public DEInteger (int id) {
    _baseType = BaseType.Integer;
    _value = id;
  }

  public Integer getValue () {
    return _value;
  }

  @Override
  public boolean equals (DataElement d) {
    if (d != null) {
      if (d instanceof _DEFloat) {
        _DEFloat intDE = (_DEFloat) d;
        if (intDE != null) {
          return _value == intDE.getValue ().doubleValue ();
        }
      }
    }
    return super.equals (d);
  }

  @Override
  public String getStringValue () {
    return Integer.toString (_value);
  }
}
