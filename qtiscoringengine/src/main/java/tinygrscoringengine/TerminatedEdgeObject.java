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
public class TerminatedEdgeObject extends Vector
{
  public String _type;

  public TerminatedEdgeObject (String types, Point p1, Point p2) {
    super (p1, p2, ObjectType.TerminatedEdge);
    _type = types;
  }

  public static TerminatedEdgeObject fromXml (Element node) {
    String text = node.getText ();
    int idx1 = text.indexOf ("),");
    String p1String = text.substring (0, idx1 + 1);
    int idx2 = text.indexOf (",Typ");
    String p2String = text.substring (idx1 + 2, idx2);
    String type = text.substring (idx2 + 1).trim ();
    return new TerminatedEdgeObject (type, Point.getPointObj (p1String), Point.getPointObj (p2String));
  }

  @Override
  public String getXmlString () {
    return TDSStringUtils.format ("<TerminatedEdgeObject>{0},{1},{2}</TerminatedEdgeObject>", points[0].getTextRendering (), points[1].getTextRendering (), _type);
  }

  @Override
  public int countSides () {
    return 1;
  }
}
