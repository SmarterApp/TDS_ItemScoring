/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author temp_mbikkina
 *
 */
public class TGRParameterConstraint
{
  public boolean               isPositional = true;
  public String                name         = "";
  public int                   position     = 0;
  public List<TinyGraphicType> graphicTypes;

  public TGRParameterConstraint (String Name, TinyGraphicType type) {
    isPositional = false;
    name = Name;
    graphicTypes = new ArrayList<TinyGraphicType> ();
    graphicTypes.add (type);
  }

  public TGRParameterConstraint (String Name, List<TinyGraphicType> types) {
    isPositional = false;
    name = Name;
    graphicTypes = types;
  }

  public TGRParameterConstraint (int Position, TinyGraphicType type) {
    isPositional = true;
    position = Position;
    graphicTypes = Arrays.asList (type);
  }
}
