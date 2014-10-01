/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import org.jdom2.Element;

public class VariableMapEntry extends MapEntry
{
  private DataElement _key = null;

  private VariableMapEntry (DataElement key, DEFloat val, Element node)
  {
    super (val, node);
    _key = key;
  }

  static VariableMapEntry FromXML (Element node, BaseType bt, ValidationLog log)
  {
    String key = node.getAttribute ("mapKey").getValue ();
    String val = node.getAttribute ("mappedValue").getValue ();

    DataElement de = DataElement.create (key, bt);
    DataElement def = DataElement.create (val, BaseType.Float);

    if (de == null)
    {
      log.addMessage (node, "mapKey attribute not specified for node " + node.getName ());
      return null;
    }
    if (de.getIsError ())
    {
      log.addMessage (node, "Error getting mapKey for node " + node.getName () + ": " + de.getErrorMessage ());
      return null;
    }
    if (def == null)
    {
      log.addMessage (node, "mappedValue attribute not specified for node " + node.getName ());
      return null;
    }
    if (def.getIsError ())
    {
      log.addMessage (node, "Error getting mappedValue for node " + node.getName () + ": " + def.getErrorMessage ());
      return null;
    }

    return new VariableMapEntry (de, (DEFloat) def, node);
  }

  DataElement getKey ()
  {
    return _key;
  }
}
