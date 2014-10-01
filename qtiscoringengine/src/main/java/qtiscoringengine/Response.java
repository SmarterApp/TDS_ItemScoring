/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response
{
  private Map<String, ResponseBinding> _responseBindings = new HashMap<String, ResponseBinding> ();

  public Response (List<ResponseBinding> bindings)
  {
    for (ResponseBinding rb : bindings)
    {
      if (_responseBindings.containsKey (rb.getIdentifier ()))
        _responseBindings.remove (rb.getIdentifier ());
      _responseBindings.put (rb.getIdentifier (), rb);
    }
  }

  DataElement getValue (String identifier)
  {
    if (_responseBindings.containsKey (identifier))
      return _responseBindings.get (identifier).getValue ();
    return null;
  }

  DataElement getValue (DEIdentifier identifier)
  {
    if (identifier == null)
      return null;
    return getValue (identifier.getValue ());
  }

  void bind (String id, BaseType baseType, Cardinality cardinality)
  {
    if (_responseBindings.containsKey (id))
    {
      ResponseBinding binding = _responseBindings.get (id);
      binding.bind (baseType, cardinality);
    }
  }

  void transferBindings (VariableBindings bindings)
  {
    for (String key : _responseBindings.keySet ())
    {
      bindings.setVariable (key, _responseBindings.get (key).getValue ());
    }
  }
}
