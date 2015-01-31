/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEPoint;
import qtiscoringengine.Expression;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlReader;

/**
 * @author temp_mbikkina
 *
 */
public abstract class TinyGRExpression extends Expression
{

  public TinyGraphicType              _graphicReturnType           = TinyGraphicType.Unknown;
  public List<TGRParameterConstraint> _graphicParameterConstraints = new ArrayList<TGRParameterConstraint> ();

  public TinyGRExpression (Element node, int minparm, int maxparm, BaseType returnType, Cardinality returnCardinality, TinyGraphicType tinyGraphicType) {
    super (node, minparm, maxparm, returnType, returnCardinality);
    _graphicReturnType = tinyGraphicType;
  }

  public TinyGRExpression (Element node, int minparm, int maxparm, BaseType returnType, Cardinality returnCardinality) {
    super (node, minparm, maxparm, returnType, returnCardinality);
    _graphicReturnType = TinyGraphicType.None;
  }

  public void addGraphicParameterConstraint (String name, TinyGraphicType type) {
    _graphicParameterConstraints.add (new TGRParameterConstraint (name, type));
  }

  public void addGraphicParameterConstraint (String name, List<TinyGraphicType> types) {
    _graphicParameterConstraints.add (new TGRParameterConstraint (name, types));
  }

  public void addGraphicParameterConstraint (int position, TinyGraphicType type) {
    _graphicParameterConstraints.add (new TGRParameterConstraint (position, type));
  }

  public void setReturnType (TinyGraphicType type) {
    _graphicReturnType = type;
  }

  public static DEPoint pointFromXml (String xml) throws TinyGRException {
    try {
      String text = "";
      StringReader sr = new StringReader (xml);

      XmlReader reader = new XmlReader (sr);
      Document doc = new Document ();
      doc = reader.getDocument ();
      if ("Point".equals (doc.getRootElement ().getName ()))
        text = doc.getRootElement ().getText ();

      if (StringUtils.isEmpty (text)) {
        return null;
      }

      int locationOfX = text.indexOf ('(');
      int locationOfXC = text.indexOf (',');
      int endOfPair = text.indexOf (')');

      String xText = text.substring (locationOfX + 1, locationOfXC).trim ();
      String yText = text.substring (locationOfXC + 1, endOfPair).trim ();

      _Ref<Integer> x = new _Ref<Integer> (0);
      _Ref<Integer> y = new _Ref<Integer> (0);
      boolean status = JavaPrimitiveUtils.intTryParse (xText, x);

      if (status)
        status = JavaPrimitiveUtils.intTryParse (yText, y);

      if (status) {
        return new DEPoint (x.get (), y.get ());
      } else {
        return null;
      }
    } catch (Exception exp) {
      throw new TinyGRException (exp);
    }
  }
}
