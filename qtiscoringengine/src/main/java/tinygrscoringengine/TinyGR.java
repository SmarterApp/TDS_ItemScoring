/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import tinyGRScoringEngine1.dummy.XmlWriter;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlReader;

/**
 * @author temp_mbikkina
 *
 */
public class TinyGR
{

  public static List<String> getObjectStrings (String answerSet) throws TinyGRException {
    try {
      List<String> objects = new ArrayList<String> ();
      StringWriter sw = new StringWriter ();
      StringBuilder sb = null;
      sb.append (sw);
      XmlWriter xw = new XmlWriter (null);
      StringReader sr = new StringReader (answerSet);
      XmlReader reader = new XmlReader (sr);
      Document doc = new Document ();
      doc = reader.getDocument ();
      List<Element> objectSet = new XmlElement (doc.getRootElement ()).selectNodes ("//AnswerSet//Question//QuestionPart//ObjectSet");
      for (Element child : objectSet) {
        GRObject obj = GRObject.createFromNode (child);
        objects.add (obj.getXmlString ());
        if (child.getName ().equals ("RegionGroupObject")) {
          for (Element region : child.getChildren ()) {
            outputObjectString (region, xw, sw, objects, sb);
          }
        }
      }
      return objects;
    } catch (Exception exp) {
      throw new TinyGRException (exp);
    }
  }

  private static void outputObjectString (Element child, XmlWriter xw, StringWriter sw, List<String> objects, StringBuilder sb) {
    String objString = sw.toString ();
    if (objString != null) {
      objects.add (sw.toString ());
    }
    sb.delete (0, sb.length ());
  }

  public static GRObject create (String obj) throws TinyGRException {
    try {
      StringReader sr = new StringReader (obj);
      XmlReader reader = new XmlReader (sr);
      Document doc = reader.getDocument ();
      return GRObject.create (doc);
    } catch (Exception exp) {
      throw new TinyGRException (exp);
    }
  }

  // #region Graphic Response Functions

  public static String getPoint (String obj) throws TinyGRException {
    GRObject ob = create (obj);
    Point p = ob.getSinglePoint ();
    if (p == null) {
      return null;
    }
    return p.getXmlString ();
  }

  public static boolean intersectsPoint (String obj, int x, int y, double tol) throws TinyGRException {
    GRObject ob = create (obj);
    return ob.intersectsRegion (new Point (x - 1, y + 1), new Point (x + 1, y - 1), tol);
  }

  public static boolean intersectsRegion (String obj, int leftX, int topY, int rightX, int bottomY) throws TinyGRException {
    GRObject ob = create (obj);
    return ob.intersectsRegion (new Point (leftX, topY), new Point (rightX, bottomY), 5.0);
  }

  public static String getName (String obj) throws TinyGRException {
    GRObject ob = create (obj);
    return ob.getName ();
  }

  public static boolean isRegionSelected (String obj) throws TinyGRException {
    GRObject ob = create (obj);
    return ob.isRegionSelected ();
  }

  public static boolean isGraphicType (String obj, String type) throws TinyGRException {
    GRObject ob = create (obj);
    switch (type) {
    case "PALETTEIMAGE":
      return ob.getTypeOfObject () == GRObject.ObjectType.Atomic;
    case "POINT":
      return ob.getTypeOfObject () == GRObject.ObjectType.Point;
    case "ARROW":
      return ob.getTypeOfObject () == GRObject.ObjectType.TerminatedEdge;
    case "VECTOR":
      return ob.getTypeOfObject () == GRObject.ObjectType.Vector;
    default:
      throw new TinyGRException (2, "Unknown type sent to IsGraphic Type: " + type);
    }
  }

  public static int getSelectedRegionCount (String obj) throws TinyGRException {
    GRObject ob = create (obj);
    return ob.getSelectedCount ();
  }

  public static int countSides (String obj) throws TinyGRException {
    GRObject ob = create (obj);
    return ob.countSides ();
  }

  public static double getSlope (String obj) throws TinyGRException {
    GRObject ob = create (obj);
    return ob.getSlope ();
  }

  public static String getVector (String obj, int index) throws TinyGRException {
    GRObject ob = create (obj);
    Vector v = ob.getVector (index);
    if (v == null)
      return "";
    return v.getXmlString ();
  }

  public static double getLength (String obj) throws TinyGRException {
    GRObject ob = create (obj);
    return ob.getLength ();
  }

  public static boolean hasVertex (String obj, int x, int y, int tolerance) throws TinyGRException {
    GRObject ob = create (obj);
    Point p = new Point (x, y);
    if (ob == null) {
      return false;
    }
    if ((x < 0) || (y < 0)) {
      return false;
    }
    Point vertex = new Point (x, y);
    return ob.hasVertex (p, tolerance);
  }

  // #endregion
}
