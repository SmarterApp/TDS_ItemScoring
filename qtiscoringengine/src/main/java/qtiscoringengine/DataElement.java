/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import qtiscoringengine.cs2java.StringHelper;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;

public abstract class DataElement
{
  private static final Pattern Whitespace    = Pattern.compile ("[{} \t\n\r]{1,}");
  private static final Pattern Pairs         = Pattern.compile ("[^ ^,]{1,}[, ][^ ^,]{1,}");
  protected BaseType           _baseType     = BaseType.Null;
  protected String             _errorMessage = "No message";
  protected boolean            _isContainer  = false;

  public boolean getIsNumber () {
    switch (_baseType) {
    case Integer:
    case Float:
      return true;
    default:
      return false;
    }
  }

  public boolean getIsContainer () {
    return _isContainer;
  }

  public boolean getIsPair () {
    switch (_baseType) {
    case Pair:
    case DirectedPair:
      return true;
    default:
      return false;
    }
  }

  public boolean getIsError () {
    // check if the type is DEError
    return this instanceof DEError;
  }

  public BaseType getType () {
    return _baseType;
  }

  public Cardinality getCardinality () {
    return Cardinality.Single;
  }

  public String getErrorMessage () {
    return _errorMessage;
  }

  public abstract boolean equals (DataElement d);

  public static DataElement create (Element node, BaseType bt) {
    _Ref<BaseType> valType = new _Ref<BaseType> (bt);
    if (node == null)
      return new DEError ("Null node passed"); // maybe this should return null

    String typeString = node.getAttributeValue ("baseType"); // node.GetAttribute("baseType");
    if (!StringUtils.isEmpty (typeString)) {
      if (!JavaPrimitiveUtils.enumTryParse (BaseType.class, typeString, true, valType)) {
        return new DEError ("Could not parse BaseType from string: '" + typeString + "'");
      }
    }

    String val = node.getText (); // node.InnerText;

    return create (val, valType.get ());
  }

  public static DataElement create (String val, BaseType valType) {
    String v1;
    int idx;

    if (val == null || StringUtils.isEmpty (val.trim ()))
      return null;

    switch (valType) {
    case BaseType:
      _Ref<BaseType> bt = new _Ref<> (BaseType.Null);
      JavaPrimitiveUtils.enumTryParse (BaseType.class, val, true, bt);
      return new DEBaseType (bt.get ());
    case String:
      return new DEString (val);
    case Identifier:
      return new DEIdentifier (val);
    case Integer:
      _Ref<Integer> num = new _Ref<Integer> ();

      if (!JavaPrimitiveUtils.intTryParse (val, num))
        return new DEError ("Could not parse int from value '" + val + "'");
      return new DEInteger (num.get ());
    case Float:
      try {
        double f = Double.parseDouble (val);
        return new DEFloat (f);
      } catch (Exception e) {
        return new DEError (e.getMessage ());
      }
    case Pair:
      try {
        v1 = val.trim ();
        idx = v1.indexOf (" ");
        return new DEPair (v1.substring (0, idx), v1.substring (idx + 1));
      } catch (Exception e) {
        return new DEError (e.getMessage ());
      }
    case DirectedPair:
      try {
        v1 = val.trim ();
        idx = v1.indexOf (" ");
        return new DEDirectedPair (v1.substring (0, idx), v1.substring (idx + 1));
      } catch (Exception e) {
        return new DEError (e.getMessage ());
      }
    case Boolean:
      // boolean b;
      // if (!bool.TryParse(val, out b))
      _Ref<Boolean> b = new _Ref<> ();
      if (!JavaPrimitiveUtils.boolTryParse (val, b)) {
        return new DEError ("Could not parse boolean from value '" + val + "'");
      }
      return new DEBoolean (b.get ());
    case Point:
      try {
        v1 = val.trim ();
        v1 = v1.replace (',', ' ');
        idx = v1.indexOf (" ");
        return new DEPoint (Integer.parseInt (v1.substring (0, idx)), Integer.parseInt (v1.substring (idx + 1)));
      } catch (Exception e) {
        return new DEError (e.getMessage ());
      }
    default:
      return new DEError ("Unknown or unimplemented DataElement type " + valType);
    }
  }

  // Shiva: Review this method again. I do not have sample strings to test the
  // Pairs regex
  // and from email from Balaji it seemed a similar code block in AreaCircle is
  // buggy in .NET
  public static DataElement createContainer (String vals, BaseType baseType, Cardinality card) {
    vals = Whitespace.matcher (vals).replaceAll (" ");

    // the use regex.matches to separate the string in singles or pairs
    // (depending on the base type)
    String[] strings = null;

    switch (baseType) {
    case DirectedPair:
    case Pair:
    case Point:

      Matcher matcher = Pairs.matcher (vals);
      ArrayList<String> al = new ArrayList<> ();
      while (matcher.find ()) {
        al.add (matcher.group ());
      }
      strings = al.toArray (new String[al.size ()]);
      break;
    default:
      // strings = vals.Split(QTIXmlConstants.TokenDelimiters,
      // StringSplitOptions.RemoveEmptyEntries);
      strings = StringHelper.split (vals, QTIXmlConstants.TokenDelimiters, StringHelper.StringSplitOptions.RemoveEmptyEntries);
      break;
    }

    // then create the data elements
    DEContainer dec = new DEContainer (baseType, card);

    for (String val : strings) {
      DataElement de = DataElement.create (val, baseType);
      dec.add (de);
    }
    return dec;
  }

  public static boolean isConformable (BaseType bt1, BaseType bt2) {
    List<BaseType> confTypes = null;
    if (bt2 == BaseType.Null)
      return true;

    switch (bt1) {
    case Null:
      return true;
    case Identifier:
      return Arrays.asList (new BaseType[] { BaseType.Identifier, BaseType.String }).contains (bt2);
    case Boolean:
      return (bt2 == BaseType.Boolean);
    case Integer:
      // This is a correction over the .NET code.
      return Arrays.asList (new BaseType[] { BaseType.Integer, BaseType.Float, BaseType.String }).contains (bt2);
    case Float:
      return Arrays.asList (new BaseType[] { BaseType.Integer, BaseType.Float, BaseType.String }).contains (bt2);
    case String:
      return true;
    case Point:
      return (bt2 == BaseType.Point);
    case Pair:
      return Arrays.asList (new BaseType[] { BaseType.Pair, BaseType.DirectedPair, BaseType.Point }).contains (bt2);
    case DirectedPair:
      return Arrays.asList (new BaseType[] { BaseType.Pair, BaseType.DirectedPair, BaseType.Point }).contains (bt2);
    case Duration:
      return (bt2 == BaseType.Duration);
    case File:
      return (bt2 == BaseType.File);
    case URI:
      return Arrays.asList (new BaseType[] { BaseType.URI, BaseType.File }).contains (bt2);
    case BaseType:
      return (bt2 == BaseType.BaseType);
    default:
      return false;
    }
  }

  public static boolean isConformableCardinality (Cardinality c1, Cardinality c2) {
    if (c2 == Cardinality.None)
      return true;
    switch (c1) {
    case None:
      return true;
    case Record:
    case Single:
      return (c2 == c1);
    case Multiple:
      return ((c2 == Cardinality.Multiple) || (c2 == Cardinality.Ordered));
    case Ordered:
      // this is a kludge--should not allow multiple
      return ((c2 == Cardinality.Multiple) || (c2 == Cardinality.Ordered));
    default:
      return true;
    }
  }

  @Override
  public String toString () {
    return getStringValue ();
  }

  public abstract String getStringValue ();
}
