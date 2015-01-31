/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class DEContainer extends DataElement
{
  private List<DataElement> _members = new ArrayList<DataElement> ();
  private Cardinality       _cardinality;

  public DEContainer (BaseType baseType, Cardinality card) {
    _baseType = baseType;
    _cardinality = card;
    _isContainer = true;
  }

  @Override
  public Cardinality getCardinality () {
    return _cardinality;
  }

  public int getMemberCount () {
    return _members.size ();
  }

  public void add (DataElement de) {
    _members.add (de);
  }

  boolean contains (DataElement de) {
    if (de.getIsContainer ())
      return ContainsContainer ((DEContainer) de);
    for (DataElement member : _members) {
      if (member.equals (de))
        return true;
    }
    return false;
  }

  // todo: never tested
  private boolean ContainsContainer (DEContainer de) {
    switch (_cardinality) {
    case Multiple:
      return ContainsContainerMultiple (de);
    case Ordered:
      return ContainsContainerOrdered (de);
    }
    return false;
  }

  private boolean ContainsContainerOrdered (DEContainer de) {
    DataElement next = de.getMember (0);

    for (int i = 0; i < this.getMemberCount (); i++) {
      if (getMemberCount () < i + de.getMemberCount ())
        return false;

      DataElement member = this.getMember (i);
      if (member.equals (next)) {
        boolean failed = false;
        for (int j = 1; j < de.getMemberCount (); j++) {
          if (!_members.get (i + j).equals (next)) {
            failed = true;
            break;
          }
        }
        if (!failed)
          return true;
      }
    }
    return false;
  }

  private boolean ContainsContainerMultiple (DEContainer de) {
    List<DataElement> deList = new ArrayList<DataElement> ();

    for (int i = 0; i < de.getMemberCount (); i++) {
      DataElement next = de.getMember (i);
      boolean gotit = false;
      for (DataElement member : _members) {
        if (member.equals (next)) {
          if (deList.contains (member))
            continue;
          else {
            deList.add (member);
            gotit = true;
            break;
          }
        }
      }
      if (!gotit)
        return false;
    }
    return true;
  }

  DEContainer delete (DataElement de) {
    DEContainer rslt = new DEContainer (_baseType, _cardinality);
    for (DataElement member : _members) {
      if (!member.equals (de))
        rslt.add (de);
    }
    return rslt;
  }

  @Override
  public boolean equals (DataElement d) {
    if (!d.getIsContainer ())
      return false;
    if (d.getType () != this.getType ())
      return false;

    final DEContainer c = (DEContainer) d;
    if (c.getMemberCount () != this.getMemberCount ())
      return false;

    if (this._cardinality == Cardinality.Ordered) {
      for (int i = 0; i < getMemberCount (); i++) {
        if (!(_members.get (i).equals (c.getMember (i))))
          return false;
      }
    } else {
      for (int i = 0; i < getMemberCount (); i++) {
        // if (_members.Find(x => x.Equals(c.getMember(i))) == null)
        // return false;
        final int idx = i;
        // if (_members.stream().filter(x -> x.equals(c.getMember(idx))).count()
        // == 0)
        // return false;
        if (CollectionUtils.select (_members, new Predicate ()
        {
          @Override
          public boolean evaluate (Object x) {
            return x.equals (c.getMember (idx));
          }
        }).size () == 0) {
          return false;
        }
      }
    }
    return true;
  }

  public DataElement getMember (int i) {
    return _members.get (i);
  }

  @Override
  public String getStringValue () {
    String val = "";
    for (DataElement member : _members) {
      if (member == null)
        continue;
      val += member.getStringValue () + " ";
    }
    return val;
  }
}
