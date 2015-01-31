/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VariableBindings
{
  private HashMap<String, DataElement> _variableBindings = new HashMap<String, DataElement> ();

  VariableBindings () {
  }

  public void setVariable (String identifier, DataElement value) {
    // Shiva: the .NET code here does not make sense.
    _variableBindings.put (identifier, value);
  }

  public void remove (String identifier) {
    if (_variableBindings.containsKey (identifier))
      _variableBindings.remove (identifier);
  }

  public DataElement getVariable (String identifier) {
    return _variableBindings.get (identifier);
  }

  public DataElement getVariable (DEIdentifier identifier) {
    return getVariable (identifier.getValue ());
  }

  int getCount () {
    return _variableBindings.size ();
  }

  public List<String[]> getPublicVariableDeclarations () {
    List<String[]> list = new ArrayList<String[]> ();
    for (String key : _variableBindings.keySet ()) {
      String[] info = new String[4];
      DataElement de = _variableBindings.get (key);
      if (de != null) {
        info[0] = key;
        info[1] = de.getType ().toString ();
        info[2] = de.getCardinality ().toString ();
        info[3] = de.getStringValue ();
      } else {
        info[0] = key;
        info[1] = "null";
        info[2] = "null";
        info[3] = "null";
      }

      list.add (info);
    }
    return list;
  }
}
