/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.StringUtils;

public class VarBinding
{

  private String                 _name;
  private String                 _type;
  private String                 _value;

  public static final VarBinding ALL = new VarBinding ()
                                     {
                                       {
                                         setName ("*");
                                         setType ("");
                                         setValue ("");
                                       }
                                     };

  @XmlAttribute (name = "name")
  public String getName () {
    return _name;
  }

  public void setName (String value) {
    _name = value;
  }

  @XmlAttribute (name = "type")
  public String getType () {
    return _type;
  }

  public void setType (String value) {
    _type = value;
  }

  @XmlAttribute (name = "value")
  public String getValue () {
    return _value;
  }

  public void setValue (String value) {
    _value = value;
  }

  @Override
  public boolean equals (Object other) {
    if (other == null)
      return false;

    if (other instanceof VarBinding) {
      VarBinding otherBinding = (VarBinding) other;
      return StringUtils.equals (_name, otherBinding.getName ()) && StringUtils.equals (_type, otherBinding.getType ()) && StringUtils.equals (_value, otherBinding.getValue ());
    }
    return false;
  }

  @Override
  public int hashCode () {
    return (_name + ":" + _type + ":" + _value).hashCode ();
  }
}
