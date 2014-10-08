/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

public class ResponseBinding
{
  private String      _identifier;
  private String      _value;
  private DataElement _dataValue   = null;
  private BaseType    _baseType    = BaseType.Null;
  private Cardinality _cardinality = Cardinality.None;

  public String getIdentifier ()
  {
    return _identifier;
  }

  DataElement getValue ()
  {
    return _dataValue;
  }

  public ResponseBinding (String id, String val)
  {
    _identifier = id;
    _value = val;
  }

  boolean bind (BaseType type, Cardinality card)
  {
    try
    {
      _baseType = type;
      _cardinality = card;
      if (card == Cardinality.Single)
      {
        _dataValue = DataElement.create (_value, type);
      }
      else
        _dataValue = DataElement.createContainer (_value, type, card);

      return true;
    } catch (Exception e)
    {
      _dataValue = DataElement.create (e.getMessage (), BaseType.Null);
      _baseType = BaseType.Null;
      _cardinality = Cardinality.None;
      return false;
    }
  }
}
