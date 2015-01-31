/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 * @author temp_mbikkina
 *
 */

public abstract class GRObject
{
  public enum ObjectType {
    None, Atomic, Geometric, Vector, Point, Arrow, RegionGroup, Region, TerminatedEdge
  }

  public ObjectType _typeOfObject = ObjectType.None;

  public GRObject (ObjectType type) {
    _typeOfObject = type;
  }

  public abstract String getXmlString ();

  public ObjectType getTypeOfObject () {
    return _typeOfObject;
  }

  public static GRObject create (Document doc) throws TinyGRException {
    Element node = doc.getRootElement ();
    return createFromNode (node);
  }

  public static GRObject createFromNode (Element node) throws TinyGRException {
    switch (node.getName ()) {
    case "Object":
      return GeoObject.fromXml (node);
    case "Point":
      return Point.getPointObj (node.getText ());
    case "Vector":
      return Vector.getVectorObj (node.getText ());
    case "AtomicObject":
      return AtomicObject.fromXml (node);
    case "RegionGroupObject":
      return RegionGroup.fromXml (node);
    case "RegionObject":
      return Region.fromXml (node);
    case "TerminatedEdgeObject":
      return TerminatedEdgeObject.fromXml (node);

    default:
      return null;
    }
  }

  public Point getSinglePoint () {
    // throw new
    // TinyGRException(1,"Trying to get a single point from a multipoint object: "
    // + getTypeOfObject.toString());
    return null;
  }

  public String getName () {
    // throw new TinyGRException(1,
    // "Trying to get a name from an unnamed object of type: " +
    // getTypeOfObject.toString());
    return "";
  }

  public boolean isRegionSelected () {
    return false;// if an object is not selectable, it is clearly not
    // selected.
  }

  public int getSelectedCount () {
    // throw new TinyGRException(1,
    // "Trying to get the region count from non-regionGroup: " +
    // getTypeOfObject.toString());
    return 0; // if it is not selectable, nothing is selected.
  }

  public int countSides () {
    return 0;
  }

  public double getSlope () {
    // throw new TinyGRException(1,
    // "Trying to get a slope of something that is not a line: " +
    // getTypeOfObject.toString());
    return Double.NaN;
  }

  public Vector getVector (int index) {
    // throw new TinyGRException(1,
    // "Trying to get a vector from something that is not a multivector object: "
    // + getTypeOfObject.toString());
    return null;
  }

  public double getLength () throws TinyGRException {
    throw new TinyGRException (1, "Trying to get a the length of something that is not a vector: " + getTypeOfObject ().toString ());
  }

  public boolean intersectsRegion (Point topLeft, Point bottomRight, double eps) {
    // everything should override this. should never hit this.
    // throw new TinyGRException(1,
    // "Checking whether something intersects a region that is not supported "
    // + getTypeOfObject.toString());
    return false; // if it is something that cannot intersect, then return
    // false;
  }

  public boolean hasVertex (Point p, int tolerance) {
    return false;
  }

}
