/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import org.apache.commons.lang3.StringUtils;

public class DEIdentifier extends DataElement
{
  private String _value;

  public DEIdentifier (String id) {
    _baseType = BaseType.Identifier;
    _value = id;
  }

  public String getValue () {
    return _value;
  }

  @Override
  public boolean equals (DataElement d) {
    if (d.getType () == this.getType ()) {
      return StringUtils.equals (_value, ((DEIdentifier) d).getValue ());
    }
    return false;
  }

  @Override
  public String getStringValue () {
    return _value;
  }

  public static DEIdentifier fromDEString (DEString d) {
    return new DEIdentifier (d.getValue ());
  }
}
