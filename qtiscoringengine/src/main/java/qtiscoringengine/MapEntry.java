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

class MapEntry
{
  protected DEFloat _value;
  protected Element _node;

  protected MapEntry (DEFloat value, Element node)
  {
    _value = value;
    _node = node;
  }

  DEFloat getValue ()
  {
    return _value;
  }
}
