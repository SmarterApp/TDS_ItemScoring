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

public class Proposition
{
      
  private String _name;
  private String _description;
  private PropositionState _state;
  
  @XmlAttribute(name="name")
  public String getName()
  {
    return _name;
  }
  
  public void setName(String value)
  {
    _name = value;
  }

  @XmlAttribute(name="description")
  public String getDesc()
  {
    return _description;
  }
  public void setDesc(String value)
  {
    _description = value;
  }
  
  @XmlAttribute(name="state")
  public PropositionState getState() { 
    return _state;
  }
  
  public void setState(PropositionState value)
  {
    _state = value;
  }
}
