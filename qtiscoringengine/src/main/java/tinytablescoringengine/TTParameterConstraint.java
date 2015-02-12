/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinytablescoringengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TTParameterConstraint
{
  public List<TableType> AcceptableTypes = new ArrayList<TableType> ();
  public String          Name;

  public TTParameterConstraint (String name, TableType type) {
    Name = name;
    AcceptableTypes.add (type);
  }

  public TTParameterConstraint (String name, List<TableType> types) {
    Name = name;
    AcceptableTypes.addAll (types);
  }
}
