/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

public class DEBoolean extends DEInteger
{
  private boolean _value;

  public DEBoolean (boolean id) {
    super (id ? 1 : 0);
    _baseType = BaseType.Boolean;
    _value = id;
  }

  public Integer getValue () {
    return super.getValue ();
  }

  public boolean getBooleanValue () {
    return _value;
  }

  @Override
  public boolean equals (DataElement d) {
    if (d.getType () == this.getType ()) {
      return (_value == ((DEBoolean) d).getBooleanValue ());
    }
    return super.equals (d);
  }

  @Override
  public String getStringValue () {
    return ((Boolean) _value).toString ();
  }
}
