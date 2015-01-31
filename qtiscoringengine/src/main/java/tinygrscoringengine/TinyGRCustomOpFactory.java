/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import java.util.HashMap;
import java.util.Map;

import org.jdom2.Element;

import qtiscoringengine.Expression;
import qtiscoringengine.ICustomOperatorFactory;

/**
 * @author temp_mbikkina
 *
 */
public class TinyGRCustomOpFactory implements ICustomOperatorFactory
{
  interface ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node);
  }

  private Map<String, ITGRConstructor> _tgrConstructors = new HashMap<String, ITGRConstructor> ();

  public TinyGRCustomOpFactory () {
    addConstructor ("PREPROCESSRESPONSE", new TGRConPreprocess ());
    addConstructor ("COUNTSIDES", new TGRConCountSides ());
    addConstructor ("GETPOINT", new TGRConGetPoint ());
    addConstructor ("INTERSECTSREGION", new TGRConIntersectsRegion ());
    addConstructor ("ISREGIONSELECTED", new TGRConIsRegionSelected ());
    addConstructor ("GETSELECTEDREGIONCOUNT", new TGRConGetSelectedCount ());
    addConstructor ("GETSLOPE", new TGRConGetSlope ());
    addConstructor ("GETVECTOR", new TGRConGetVector ());
    addConstructor ("ISGRAPHICTYPE", new TGRConIsGraphicType ());
    addConstructor ("GETLENGTH", new TGRConGetLength ());
    addConstructor ("GETNAME", new TGRConGetName ());
    addConstructor ("INTERSECTSPOINT", new TGRConIntersectsPoint ());
    addConstructor ("HASVERTEXT", new TGRConHasVertext ());
  }

  class TGRConHasVertext implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREHasVertex (node);
    }
  }

  class TGRConIntersectsPoint implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREIntersectsPoint (node);
    }
  }

  class TGRConIsRegionSelected implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREIsRegionSelected (node);
    }
  }

  class TGRConGetSelectedCount implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREGetSelectedCount (node);
    }
  }

  class TGRConGetSlope implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREGetSlope (node);
    }
  }

  class TGRConGetVector implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREGetVector (node);
    }
  }

  class TGRConIsGraphicType implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREIsGraphicType (node);
    }
  }

  class TGRConGetLength implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREGetLength (node);
    }
  }

  class TGRConPreprocess implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREPreProcess (node);
    }
  }

  class TGRConIntersectsRegion implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREIntersectsRegion (node);
    }
  }

  class TGRConCountSides implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGRECountSides (node);
    }
  }

  class TGRConGetPoint implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREGetSinglePoint (node);
    }
  }

  class TGRConGetName implements ITGRConstructor
  {
    public TinyGRExpression getTGRConstructor (Element node) {
      return new TGREGetName (node);
    }
  }

  public void addConstructor (String name, ITGRConstructor del) {
    if (_tgrConstructors.containsKey (name))
      _tgrConstructors.remove (name);
    _tgrConstructors.put (name, del);
  }

  public boolean supportsOperator (Element customOperatorNode) {
    Element coElement = (Element) customOperatorNode;

    if (!("GRAPHIC".equals (coElement.getAttribute ("type").getValue ()))) {
      return false;
    }
    return _tgrConstructors.containsKey (coElement.getAttribute ("functionName").getValue ());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * qtiscoringengine.ICustomOperatorFactory#createExpression(org.jdom2.Element)
   */
  @Override
  public Expression createExpression (Element customOperatorNode) {
    Element coElement = (Element) customOperatorNode;
    return (Expression) _tgrConstructors.get (coElement.getAttribute ("functionName").getValue ()).getTGRConstructor (coElement);
  }
}
