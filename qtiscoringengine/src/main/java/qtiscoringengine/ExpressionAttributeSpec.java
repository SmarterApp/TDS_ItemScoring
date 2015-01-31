/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.List;

public class ExpressionAttributeSpec
{
  private String       _name                 = "";
  private BaseType     _valueType;
  private List<String> _allowableIdentifiers = null;
  private boolean      _optional             = false;

  public ExpressionAttributeSpec (String name, BaseType bt) {
    this (name, bt, false);
  }

  ExpressionAttributeSpec (String name, List<String> allowableIDs) {
    this (name, allowableIDs, false);
  }

  public ExpressionAttributeSpec (String name, List<String> allowableIDs, boolean isOptional) {
    _name = name;
    _valueType = BaseType.Identifier;
    _allowableIdentifiers = allowableIDs;
    _optional = isOptional;
  }

  public ExpressionAttributeSpec (String name, BaseType bt, boolean isOptional) {
    _name = name;
    _valueType = bt;
    _optional = isOptional;
  }

  boolean getIsOptional () {
    return _optional;
  }

  String getName () {
    return _name;
  }

  BaseType getValueType () {
    return _valueType;
  }

  List<String> getAllowableIdentifiers () {
    return _allowableIdentifiers;
  }

  boolean validateIdentifierValue (DEIdentifier identifier) {
    if (_allowableIdentifiers == null)
      return true;
    if (_allowableIdentifiers.size () > 0) {
      return (_allowableIdentifiers.contains (identifier.getValue ().trim ()));
    }
    return true;
  }
}
