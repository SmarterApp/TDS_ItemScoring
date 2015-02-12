/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinygrscoringengine;

import java.util.ArrayList;
import java.util.List;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.TDSStringUtils;

public class Vector extends GRObject
{
  public Point[] points    = null;
  public double  slope     = Double.NaN;
  public double  intercept = Double.NaN;

  public Vector (Point p1, Point p2) {
    this (p1, p2, ObjectType.Vector);
  }

  public Vector (Point p1, Point p2, ObjectType obType) {
    super (obType);
    setPointsInOrder (p1, p2);
    computeSlope ();
    computeIntercept ();
  }

  public void computeSlope () {
    double term1 = ((double) points[0]._y - (double) points[1]._y);
    double term2 = ((double) points[0]._x - (double) points[1]._x);

    if (term2 == 0) {
      slope = Double.POSITIVE_INFINITY;
    } else
      slope = (double) (term1 / term2);
  }

  public void computeIntercept () {
    if (Double.isInfinite (slope)) {
      intercept = Double.NaN;
    } else {
      if (slope == 0) {
        intercept = getMinY ();
      } else {
        intercept = points[0]._y - (slope * points[0]._x);
      }
    }
  }

  public int getMinY () {
    return Math.min (points[0]._y, points[1]._y);
  }

  public int getMinX () {
    return points[0]._x;
  }

  public int getMaxY () {
    return Math.max (points[0]._y, points[1]._y);
  }

  public int getMaxX () {
    return points[1]._x;
  }

  public void setPointsInOrder (Point p1, Point p2) {
    points = new Point[2];
    if (p1._x == p2._x) {
      if (p1._y > p2._y) {
        points[0] = p1;
        points[1] = p2;
      } else {
        points[0] = p2;
        points[1] = p1;
      }
    } else if (p1._x < p2._x) {
      points[0] = p1;
      points[1] = p2;
    } else {
      points[0] = p2;
      points[1] = p1;
    }
  }

  public static Vector getVectorObj (String text) {
    text = text.trim ();
    ArrayList<Point> points = new ArrayList<Point> ();
    for (int i = 0; i < 2; i++) {
      int locationOfX = text.indexOf ('(') + 1;
      int locationOfXC = text.indexOf (',');
      int endOfPair = text.indexOf (')');

      String xText = text.substring (locationOfX, locationOfXC).trim ();
      String yText = text.substring (locationOfXC + 1, endOfPair).trim ();

      points.add (new Point (Integer.parseInt (xText), Integer.parseInt (yText)));
      if (!text.contains ("),")) {
        text = text.substring (endOfPair + 1);
      } else {
        text = text.substring (endOfPair + 2);
      }
    }

    return new Vector (points.get (0), points.get (1));
  }

  @Override
  public String getXmlString () {
    return TDSStringUtils.format ("<Vector>{0}</Vector>", getTextRendering ());
  }

  public String getTextRendering () {
    return String.format ("{%s,%s}", points[0].getTextRendering (), points[1].getTextRendering ());
  }

  public double distanceToClosestEndPoint (Point p) {
    _Ref<Double> d = new _Ref<Double> (0d);
    closestEndPoint (p, d);
    return d.get ();
  }

  public int closestEndPoint (Point p, _Ref<Double> distance) {
    double d1 = Math.pow (points[0]._x - p._x, 2) + Math.pow (points[0]._y - p._y, 2);
    double d2 = Math.pow (points[1]._x - p._x, 2) + Math.pow (points[1]._y - p._y, 2);
    distance.set (Math.sqrt (Math.min (d1, d2)));
    return (d1 < d2) ? 0 : 1;
  }

  public Point getTopLeftMost () {
    if (points[0]._y > points[1]._y)
      return points[0];
    if (points[1]._y > points[0]._y)
      return points[1];
    // otherwise they are equal...
    if (points[0]._x > points[1]._x)
      return points[1];
    else
      return points[0]; // should never be equal because then it would be a
                        // point, not a vector
  }

  public int getLeftExtent () {
    return points[0]._x + points[1]._x;
  }

  public Point intersectionPoint (Vector v, double tolerance) {
    if (v.slope == this.slope)
      return null;
    double slopeDif = (v.slope - this.slope);
    if (slopeDif == 0)
      return null;
    double xIntersect;
    if (Double.isNaN (v.intercept))
      xIntersect = v.getMinX ();
    else if (Double.isNaN (this.intercept))
      xIntersect = this.getMinX ();
    else
      xIntersect = (this.intercept - v.intercept) / slopeDif;

    if ((xIntersect < this.getMinX () - tolerance) || (xIntersect < v.getMinX () - tolerance) || (xIntersect > this.getMaxX () + tolerance) || (xIntersect > v.getMaxX () + tolerance))
      return null;

    double icept = intercept;
    double slp = slope;
    if (Double.isNaN (icept) || Double.isInfinite (slope)) {
      icept = v.intercept;
      slp = v.slope;
    }
    int yIntersect = (int) Math.round (icept + slp * xIntersect);

    if ((yIntersect < this.getMinY () - tolerance) || (yIntersect < v.getMinY () - tolerance) || (yIntersect > this.getMaxY () + tolerance) || (yIntersect > v.getMaxY () + tolerance))
      return null;

    return new Point ((int) Math.round (xIntersect), yIntersect);
  }

  public boolean intersectsRegion (Point topLeftPt, Point botRightPt, double eps) {
    List<Vector> vecs = new ArrayList<Vector> ();

    Point topRight = new Point (botRightPt._x, topLeftPt._y);
    Point botLeft = new Point (topLeftPt._x, botRightPt._y);

    vecs.add (new Vector (topLeftPt, topRight));
    vecs.add (new Vector (topRight, botRightPt));
    vecs.add (new Vector (botRightPt, botLeft));
    vecs.add (new Vector (botLeft, topLeftPt));

    for (Vector vector : vecs) {
      if (this.intersectsVector (vector, eps))
        return true;
    }
    return points[0].intersectsRegion (topLeftPt, botRightPt, eps); // this
                                                                    // checks if
                                                                    // the whole
                                                                    // thing is
                                                                    // inside
                                                                    // the
                                                                    // region
  }

  public boolean intersectsVector (Vector v, double tolerance) {
    Point intersect = intersectionPoint (v, tolerance);
    return (intersect != null);
  }

  public Vector getVector (int index) {
    if (index == 0) {
      return this;
    }
    return null;
  }

  // #region Graphic response functions

  public double getSlope () {
    double term1 = ((double) points[0]._y - (double) points[1]._y);
    double term2 = ((double) points[0]._x - (double) points[1]._x);

    if (term2 == 0) {
      return Double.POSITIVE_INFINITY;
    }
    return (double) (term1 / term2);
  }

  public double getLength () {
    double term1 = (double) (points[1]._x - points[0]._x);
    double term2 = (double) (points[1]._y - points[0]._y);

    return (double) Math.sqrt ((term1 * term1) + (term2 * term2));
  }

  @Override
  public int countSides () {
    return 1;
  }

  public boolean hasVertex (Point p, int tolerance) {
    for (int i = 0; i < 2; i++)
      if (points[i].equalsPoint (p, tolerance)) {
        return true;
      }
    return false;
    // #endregion
  }
}
