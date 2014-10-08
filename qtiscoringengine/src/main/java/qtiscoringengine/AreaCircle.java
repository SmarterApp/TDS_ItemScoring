/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qtiscoringengine.cs2java.StringHelper;

public class AreaCircle extends Area
{
  private double  _radius;
  private DEPoint _center;

  AreaCircle (String coords) throws Exception {
    super (Shape.Circle);
    // todo: error checking
    // Regex whitespace = new Regex("[{} \t\n\r]{1,}"); //clearing out the curly
    // brackets which are sometimes used to separate pairs, but not always.
    // Kludge.
    // Regex pairs = new Regex("[^ ^,]{1,}[ ,][^ ^,]{1,}");

    // start by replacing all sequences of whitespace, }, or { with a single
    // space
    // coords = whitespace.Replace(coords, " ");
    coords = coords.replace ("[{} \t\n\r]{1,}", " ");

    Pattern pattern = Pattern.compile ("[^ ^,]{1,}[ ,][^ ^,]{1,}");
    Matcher matcher = pattern.matcher (coords);
    if (!matcher.find ())
      // MatchCollection matches = pairs.Matches(coords);
      // if (matches.Count == 0)
      throw new Exception ("No matches for pairs in coords value '" + coords + "' in the AreaCircle constructor");

    // String radiusString = coords.substring(matches[0].Index +
    // matches[0].Length);
    String radiusString = coords.substring (matcher.end () + 1);
    // radiusString = radiusString.trim(QTIXmlConstants.TokenDelimiters);
    radiusString = new StringHelper (radiusString).trim (QTIXmlConstants.TokenDelimiters);

    DataElement dep = DataElement.create (matcher.group (), BaseType.Point);
    if (dep.getIsError ())
      throw new Exception (dep.getErrorMessage ());
    _center = (DEPoint) dep;
    DataElement def = DataElement.create (radiusString, BaseType.Float);
    if (def.getIsError ())
      throw new Exception (def.getErrorMessage ());
    _radius = ((_DEFloat) def).getValue ().doubleValue ();
  }

  @Override
  DEBoolean getIsInside (DEPoint point)
  {
    if (point == null)
      return new DEBoolean (false);
    double distance = Math.sqrt (Math.pow (_center.getX () - point.getX (), 2.0) + Math.pow (_center.getY () - point.getY (), 2.0));
    return new DEBoolean (distance <= _radius);
  }
}
