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

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Utilities.TDSStringUtils;

/**
 * @author temp_mbikkina
 *
 */
public class Region extends GRObject
{
  public String  _name;
  public boolean _isSelected;

  public Region (String name, boolean selected) {
    super (ObjectType.Region);
    _name = name;
    _isSelected = selected;
  }

  public static Region fromXml (Element node) {
    String name = node.getAttributeValue ("name");
    String selectString = node.getAttributeValue ("isselected");
    _Ref<Boolean> selected = new _Ref<Boolean> ();
    boolean status = JavaPrimitiveUtils.boolTryParse (selectString, selected);
    if (!status) {
      selected = new _Ref<Boolean> (false);
    }
    return new Region (name, selected.get ());
  }

  @Override
  public String getXmlString () {
    return TDSStringUtils.format ("<RegionObject name=\"{0}\" isselected=\"{1}\"/>", _name, _isSelected);
  }

  // #region Graphic Response Functions
  @Override
  public boolean isRegionSelected () {
    return _isSelected;
  }

  @Override
  public String getName () {
    return _name;
  }

  // #endregion
}
