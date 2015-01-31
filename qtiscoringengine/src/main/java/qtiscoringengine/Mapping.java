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
import java.util.List;

import org.jdom2.Element;

abstract class Mapping
{
  protected DEFloat _defaultValue = null;
  protected DEFloat _upperBound   = null;
  protected DEFloat _lowerBound   = null;
  protected Element _node;

  protected Mapping (double fDefault, double fUpper, double fLower, Element node) {
    _defaultValue = new DEFloat (fDefault);
    _upperBound = new DEFloat (fUpper);
    _lowerBound = new DEFloat (fLower);
    _node = node;
  }

  double mapResponse (DataElement de) {

    if (de == null)
      return Double.NaN;

    if (de.getIsContainer ()) {
      return mapContainerResponse (de);
    } else {
      MapEntry entry = findEntry (de);
      if (entry != null) {
        return applyLimits (entry.getValue ().getValue ().doubleValue ());
      } else {
        return applyLimits (_defaultValue.getValue ());
      }
    }
  }

  protected double mapContainerResponse (DataElement de) {
    double rtnVal = 0.0;
    DEContainer container = (DEContainer) de;
    List<MapEntry> used = new ArrayList<MapEntry> ();
    for (int i = 0; i < container.getMemberCount (); i++) {
      DataElement value = container.getMember (i);
      MapEntry entry = findEntry (value);
      if (entry != null) {
        if (used.contains (entry))
          continue;
        else {
          rtnVal += entry.getValue ().getValue ().doubleValue ();
          used.add (entry);
        }
      }
    }
    if (used.size () == 0) {
      return applyLimits (_defaultValue.getValue ());
    }

    return applyLimits (rtnVal);
  }

  private double applyLimits (double rtnVal) {
    double _final = rtnVal;
    if ((_lowerBound != null) && (_lowerBound.getType () == BaseType.Float))
      _final = Math.max (_lowerBound.getValue (), rtnVal);
    if ((_upperBound != null) && (_upperBound.getType () == BaseType.Float))
      _final = Math.min (_upperBound.getValue (), _final);
    return _final;
  }

  abstract MapEntry findEntry (DataElement value);

  abstract boolean validate (QTIRubric rubric, ValidationLog log);

  DataElement getDefaultValue () {
    return _defaultValue;
  }
}
