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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.cs2java.StringHelper;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.TDSStringUtils;
import AIR.Common.xml.XmlElement;

/**
 * @author temp_mbikkina
 *
 */

public class GeoObject extends GRObject
{
  private List<Point>  _pointList;
  private List<Vector> _vectorList;

  public GeoObject (List<Point> pointList, List<Vector> vectorList) {
    super (ObjectType.Geometric);
    _pointList = pointList;
    _vectorList = vectorList;

    List<Point> meetingPoints = GetUniqueMeetingPoints (vectorList);

    // if it is a useable closed form shape, sort the vectors
    if ((vectorList != null) && ((vectorList.size () == 3 && meetingPoints.size () == 3) || // closed
        // form
        // triangle
        (vectorList.size () == 4 && meetingPoints.size () == 4) || // closed
                                                                   // form
        // quadrilateral
        (vectorList.size () == 5 && meetingPoints.size () == 5) // closed form
        // pentagram
        )) {
      sortVectors ();
    }

  }

  public List<Point> getPointList () {
    return _pointList;
  }

  public List<Vector> getVectorList () {
    return _vectorList;
  }

  public static GRObject fromXml (Element node) throws TinyGRException {
    final char[] toTrim = { ' ', '{', '}' };
    Element vectorNode = node.getChild ("EdgeVector");
    Element pointNode = node.getChild ("PointVector");
    String pointText = StringHelper.trim (pointNode.getText (), toTrim);
    String vectorText = vectorNode.getText ();

    List<Point> points = getPointObjects (pointText);
    int start = vectorText.indexOf ('{');
    int end = vectorText.lastIndexOf ('}');

    List<Vector> vectors = null;

    if ((start >= 0) && (end > 0)) {
      vectorText = vectorText.substring (start + 1, end);
      vectors = getVectorObjects (vectorText);
    }

    switch (vectors.size ()) {
    case 0:
      if (points.size () == 1)
        return points.get (0);
      else {
        throw new TinyGRException (3, "What kind of object has multiple points but no vectors?");
      }
    case 1:
      return vectors.get (0);
    default:
      return new GeoObject (points, vectors);
    }
  }

  public static List<Point> getPointObjects (String pointText) {
    int endOfPoint = 0;
    List<Point> points = new ArrayList<Point> ();
    while (pointText.contains ("(")) {
      points.add (Point.getPointObj (pointText));
      endOfPoint = pointText.indexOf (")");
      pointText = pointText.substring (endOfPoint + 1);
    }
    return points;
  }

  public static List<Vector> getVectorObjects (String vectorText) {
    int endOfPoint = 0;
    List<Vector> vectors = new ArrayList<Vector> ();
    while (vectorText.contains ("{")) // wrong thing to check...
    {
      vectors.add (Vector.getVectorObj (vectorText));
      endOfPoint = vectorText.indexOf ("}");
      vectorText = vectorText.substring (endOfPoint + 1);
    }
    return vectors;
  }

  @Override
  public String getXmlString () {
    StringBuilder pointVector = new StringBuilder ();
    StringBuilder edgeVector = new StringBuilder ();

    for (Point p : getPointList ()) {
      pointVector.append (p.getTextRendering ());
    }

    for (Vector v : getVectorList ()) {
      edgeVector.append (v.getTextRendering ());
    }

    return String.format ("<Object><PointVector>{%s}</PointVector><EdgeVector>{%s}</EdgeVector></Object>", pointVector.toString (), edgeVector.toString ());
  }

  // 1. top left vector.
  // Each vector's position is first defined by its maxY, then among those with
  // the maxY,
  // --this is weird. The X position is defined as the sum of the x positions of
  // the endpoints. So, a short line could be considered "more left" than a
  // longer line
  // --that originates to its left. Why is this?
  // Maybe we never hit it? This is only used for closed objects, so only two
  // lines meet at any point. I guess you could have two lines with the same top
  // left point, then the shorter one wins, right?

  public void sortVectors () {
    List<Vector> vList = new ArrayList<Vector> ();
    vList.addAll (_vectorList);
    _vectorList.clear ();
    Collections.sort (vList, sortTopLeft);
    Point pivotPoint = vList.get (0).getTopLeftMost ();

    _Ref<Double> d = new _Ref<Double> (0d);
    while (vList.size () > 0) {
      Vector v = vList.get (0);
      _vectorList.add (v);
      vList.remove (0);
      pivotPoint = v.points[1 - v.closestEndPoint (pivotPoint, d)];
      sortNearestPoint (vList, pivotPoint);
    }
  }

  // make sure this works with closed objects. For non-closed objects will
  // return a deterministic sort order, I think, but it is not intuitive.
  // For points that intersect at this point, it will select the one the
  // "most left," in that the sum of the x coordinates are minimum. If they are
  // equal, it goes to length.
  // if it is possible for that to be equal, too, there is a problem.
  public static void sortNearestPoint (List<Vector> vList, final Point startPoint) {
    Collections.sort (vList, new Comparator<Vector> ()
    {
      public int compare (Vector v1, Vector v2) {
        double d1 = v1.distanceToClosestEndPoint (startPoint);
        double d2 = v2.distanceToClosestEndPoint (startPoint);

        // always make the "left-most" come first in the case of a tie?
        if (d1 == d2) {
          double x1 = v1.points[0]._x + v1.points[1]._x;
          double x2 = v2.points[0]._x + v2.points[1]._x;
          if (x1 == x2) // if they have the same x "extent"
          {
            if (v1.getLength () < v2.getLength ()) {
              return -1; // settle it based on length, if all else is equal
            } else
              return 1;
          }
          return (x1 < x2) ? -1 : 1;
        }
        return (d1 == d2) ? 0 : (d1 < d2) ? -1 : 1;
      }
    });
  }

  @Override
  public boolean intersectsRegion (Point topLeft, Point bottomRight, double eps) {
    List<Vector> vecs = new ArrayList<Vector> (10);

    Point topRight = new Point (bottomRight._x, topLeft._y);
    Point botLeft = new Point (topLeft._x, bottomRight._y);

    vecs.add (new Vector (topLeft, topRight));
    vecs.add (new Vector (topRight, bottomRight));
    vecs.add (new Vector (bottomRight, botLeft));
    vecs.add (new Vector (botLeft, topLeft));

    for (Vector v : this.getVectorList ()) {
      for (Vector vec : vecs) {
        if (v.intersectsVector (vec, 1.0))
          return true;
      }
    }

    return getVectorList ().get (0).points[0].intersectsRegion (topLeft, bottomRight, eps); // checks
    // if
    // the
    // whole
    // thing
    // is
    // inside
    // the
    // region

  }

  @Override
  public int countSides () {
    return _vectorList.size ();
  }

  @Override
  public Vector getVector (int index) {
    if ((index >= 0) && (index < _vectorList.size ()))
      return _vectorList.get (index);
    return null;
  }

  @Override
  public boolean hasVertex (Point p, int tolerance) {
    for (Vector v : _vectorList) {
      if (v.hasVertex (p, tolerance))
        return true;
    }
    return false;
  }

  private static List<Point> GetUniqueMeetingPoints (List<Vector> vecs) {
    List<Point> r = new ArrayList<Point> ();
    if (vecs.size () == 0)
      return r;
    for (Vector v1 : vecs) {
      for (Vector v2 : vecs) {
        if ((!v1.equalsVector (v2)) && v1.meetsVector (v2)) {
          Point meetingPoint = v1.meetingPoint (v2);
          if (meetingPoint != null) {
            boolean alreadyIn = false;
            for (Point p : r) {
              if (p.equalsPoint (meetingPoint)) {
                alreadyIn = true;
                // break;
              }
            }
            if (!alreadyIn)
              r.add (meetingPoint);
          }
        }
      }
    }

    return r;
  }

  private static final Comparator<Vector> sortTopLeft = new Comparator<Vector> ()
                                                      {

                                                        @Override
                                                        public int compare (Vector v1, Vector v2) {

                                                          {
                                                            Point top1 = v1.getTopLeftMost ();
                                                            Point top2 = v2.getTopLeftMost ();
                                                            if (top1._y > top2._y)
                                                              return -1;
                                                            if (top2._y > top1._y)
                                                              return 1;
                                                            // otherwise they
                                                            // are equal...
                                                            if (top1._x < top2._x)
                                                              return -1;
                                                            if (top2._x < top1._x)
                                                              return 1;
                                                            // otherwise it is
                                                            // the same
                                                            // point,

                                                            if (v1.getLeftExtent () < v2.getLeftExtent ()) {
                                                              return -1;
                                                            }
                                                            if (v2.getLeftExtent () < v1.getLeftExtent ()) {
                                                              return 1;
                                                            }
                                                            // otherwise this
                                                            // too is equal

                                                            if (v2.getLength () < v1.getLength ()) {
                                                              return 1;
                                                            }
                                                            return -1;
                                                          }

                                                        }

                                                      };

}
