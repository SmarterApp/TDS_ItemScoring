/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qtiscoringengine.cs2java.StringHelper;

public class AreaCircle extends Area
{
  private double               _radius;
  private DEPoint              _center;
  private static final Pattern Whitespace = Pattern.compile ("[{} \t\n\r]{1,}");
  private static final Pattern Pairs      = Pattern.compile ("[^ ^,]{1,}[ ,][^ ^,]{1,}");

  // TODO shiva: Whitespace regex is checked and fixed but the Pairs regex and
  // its use here is not so clear.
  // It seems the .NET code itself has a bug - in particular the use of
  // radiusString twice in .NET code.
  //
  AreaCircle (String coords) throws QTIScoringException {
    super (Shape.Circle);

    coords = Whitespace.matcher (coords).replaceAll (" ");

    Matcher matcher = Pairs.matcher (coords);

    if (!matcher.find ())
      throw new QTIScoringException ("No matches for pairs in coords value '" + coords + "' in the AreaCircle constructor");

    String radiusString = matcher.group ();

    radiusString = StringHelper.trim (radiusString, QTIXmlConstants.TokenDelimiters);

    DataElement dep = DataElement.create (matcher.group (), BaseType.Point);
    if (dep.getIsError ())
      throw new QTIScoringException (dep.getErrorMessage ());
    _center = (DEPoint) dep;

    DataElement def = DataElement.create (radiusString, BaseType.Float);
    if (def.getIsError ())
      throw new QTIScoringException (def.getErrorMessage ());
    _radius = ((_DEFloat) def).getValue ().doubleValue ();
  }

  // End Review:

  @Override
  DEBoolean getIsInside (DEPoint point) {
    if (point == null)
      return new DEBoolean (false);
    double distance = Math.sqrt (Math.pow (_center.getX () - point.getX (), 2.0) + Math.pow (_center.getY () - point.getY (), 2.0));
    return new DEBoolean (distance <= _radius);
  }
}
