/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import AIR.Common.Utilities.TDSStringUtils;

public class DEPair extends DataElement
{
  private String _value1;
  private String _value2;

  DEPair (String v1, String v2) {
    _baseType = BaseType.Pair;
    _value1 = v1;
    _value2 = v2;
  }

  public String getValue () {
    return TDSStringUtils.format ("{0} {1}", _value1, _value2);
  }

  @Override
  public boolean equals (DataElement d) {
    if (d.getType () == this.getType ()) {
      DEPair dep = (DEPair) d;
      // might need to add more to the logic here, it looks like something
      // of the form
      // this = "0 0", dep = "0 1" would pass. Is that intended?
      return ((getValue1 ().equals (dep.getValue1 ()) || getValue1 ().equals (dep.getValue2 ())) && (getValue2 ().equals (dep.getValue1 ()) || getValue2 ().equals (dep.getValue2 ())));
    }
    return false;
  }

  public String getValue1 () {
    return _value1;
  }

  public String getValue2 () {
    return _value2;
  }

  @Override
  public String getStringValue () {
    return getValue ();
  }
}
