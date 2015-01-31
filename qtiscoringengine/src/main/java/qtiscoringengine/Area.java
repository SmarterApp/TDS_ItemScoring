/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import org.jdom2.Element;

import AIR.Common.Helpers.InvalidCastException;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlNamespaceManager;

abstract class Area
{
  private Shape _shape;

  protected Area (Shape shape) {
    _shape = shape;
  }

  abstract DEBoolean getIsInside (DEPoint point);

  static Area fromXML (Element node, XmlNamespaceManager nsmgr) throws InvalidCastException {
    String coords = node.getAttribute ("coords").getValue ();
    String shapex = node.getAttribute ("shape").getValue ();
    try {
      return Area.Create (shapex, coords);
    } catch (Exception e) {
      throw new InvalidCastException ("Error in element " + node.getName () + ": " + e.getMessage ());
    }
  }

  static Area Create (String shapex, String coords) throws InvalidCastException, QTIScoringException {
    Shape shape;
    _Ref<Shape> out = new _Ref<Shape> ();
    if (JavaPrimitiveUtils.enumTryParse (Shape.class, shapex, true, out))
      shape = out.get ();
    else
      throw new InvalidCastException ("Could not parse area shape '" + shapex + "'");
    return create (shape, coords);
  }

  static Area create (Shape shape, String coords) throws InvalidCastException, QTIScoringException {
    switch (shape) {
    case Circle:
      return new AreaCircle (coords);
    case Poly:
      throw new UnsupportedOperationException ("Have to implement AreaPoly.IsInside");
    case Rect:
      return new AreaRect (coords);
    case Default:
      return new AreaDefault ();

    default:
      throw new QTIScoringException ("Unknown or unsupported shape request in xml");
    }
  }
}
