/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import AIR.Common.Utilities.TDSStringUtils;

/**
 * @author temp_mbikkina
 *
 */

public class Point extends GRObject
{
  public int _x;
  public int _y;

  public Point (int x, int y) {
    super (ObjectType.Point);
    this._x = x;
    this._y = y;
  }

  public static Point getPointObj (String text) {
    int locationOfX = text.indexOf ('(');
    int locationOfXC = text.indexOf (',');
    int endOfPair = text.indexOf (')');

    String xText = text.substring (locationOfX + 1, locationOfXC).trim ();
    String yText = text.substring (locationOfXC + 1, endOfPair).trim ();

    return new Point (Integer.parseInt (xText), Integer.parseInt (yText));
  }

  @Override
  public String getXmlString () {
    return TDSStringUtils.format ("<Point>{0}</Point>", getTextRendering ());
  }

  public String getTextRendering () {
    return TDSStringUtils.format ("({0},{1})", _x, _y);
  }

  @Override
  public int countSides () {
    return 0;
  }

  public boolean intersectsRegion (Point topLeft, Point bottomRight, double eps) {
    return ((this._x >= topLeft._x - eps) && (this._x <= bottomRight._x + eps) && (this._y >= bottomRight._y - eps) && (this._y <= topLeft._y + eps));
  }

  public Point getSinglePoint () {
    return this;
  }

  public boolean equalsPoint (Point p, double eps) {
    return ((Math.abs ((double) (_x - p._x)) <= eps) && (Math.abs ((double) (_y - p._y)) <= eps));
  }

}
