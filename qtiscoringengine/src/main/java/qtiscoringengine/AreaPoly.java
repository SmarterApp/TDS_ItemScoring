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
import java.util.List;

import qtiscoringengine.cs2java.NotImplementedException;

class AreaPoly extends Area
{
  private List<DEPoint> _points = new ArrayList<DEPoint> ();

  AreaPoly (String coords) {
    super (Shape.Poly);
    DEContainer container = (DEContainer) DataElement.createContainer (coords, BaseType.Point, Cardinality.Ordered);
    for (int i = 0; i < container.getMemberCount (); i++) {
      DEPoint point = (DEPoint) container.getMember (i);
      _points.add (point);
    }

    if (!_points.get (0).equals (_points.get (container.getMemberCount () - 1)))
      _points.add (new DEPoint (_points.get (0).getX (), _points.get (0).getY ()));
  }

  @Override
  DEBoolean getIsInside (DEPoint point) {
    throw new NotImplementedException ();
  }

}
