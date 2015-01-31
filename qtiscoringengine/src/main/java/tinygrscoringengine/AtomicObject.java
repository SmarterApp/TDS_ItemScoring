/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import org.jdom2.Element;

import AIR.Common.Utilities.TDSStringUtils;

/**
 * @author temp_mbikkina
 *
 */

public class AtomicObject extends GRObject
{
  private Point  _location;
  private String _name;

  public AtomicObject (String name, Point location) {
    super (ObjectType.Atomic);
    _name = name;
    _location = location;
  }

  @Override
  public String getXmlString () {
    return TDSStringUtils.format ("<AtomicObject>{{{0}{1}}}</AtomicObject>", _name, _location.getTextRendering ());
  }

  public static AtomicObject getAtomicObject (String text) {
    text = text.trim ();
    if (!text.equals ("")) {
      int pntStart = text.indexOf ("(");
      String name = text.substring (1, pntStart - 1);
      String pntString = text.substring (pntStart);
      return new AtomicObject (name, Point.getPointObj (pntString));
    }
    return null;
  }

  public static AtomicObject fromXml (Element node) {
    return getAtomicObject (node.getText ());
  }

  // Graphic Response Functions

  public Point getSinglePoint () {
    return _location;
  }

  public String getName () {
    return _name;
  }

  @Override
  public int countSides () {
    return -1;
  }

  // end region
  public boolean intersectsRegion (Point topLeft, Point bottomRight, double eps) {
    return this._location.intersectsRegion (topLeft, bottomRight, eps);
  }
}
