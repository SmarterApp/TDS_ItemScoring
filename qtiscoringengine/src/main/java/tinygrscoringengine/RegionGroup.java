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
import java.util.List;

import org.jdom2.Element;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Utilities.TDSStringUtils;

/**
 * @author temp_mbikkina
 *
 */
public class RegionGroup extends GRObject
{
  public String       _name;
  public int          _numSelected = 0;
  public List<Region> _regions;

  public RegionGroup (String names, int numSelect, List<Region> regions) {
    super (ObjectType.RegionGroup);
    _name = names;
    _numSelected = numSelect;
    _regions = regions;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tinyGRScoringEngine.GRObject#GetXmlString()
   */
  @Override
  public String getXmlString () {
    StringBuilder regionStrings = new StringBuilder ();
    for (Region r : _regions) {
      regionStrings.append (r.getXmlString ());
    }
    return TDSStringUtils.format ("<RegionGroupObject name=\"{0}\" numselected=\"{1}\"/>", _name, _numSelected);
  }

  public static RegionGroup fromXml (Element node) {
    String name = node.getAttributeValue ("name");
    String countString = node.getAttributeValue ("numselected");
    _Ref<Integer> count = new _Ref<Integer> ();

    if (!JavaPrimitiveUtils.intTryParse (countString, count)) {
      count = new _Ref<Integer> (0);
    }
    List<Region> regionList = new ArrayList<Region> ();
    return new RegionGroup (name, count.get (), regionList);
  }

  @Override
  public int getSelectedCount () {
    return _numSelected;
  }

  @Override
  public String getName () {
    return _name;
  }

}
