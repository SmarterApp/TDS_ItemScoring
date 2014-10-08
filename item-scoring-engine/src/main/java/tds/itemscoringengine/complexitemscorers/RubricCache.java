/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.complexitemscorers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class RubricCache implements IRubricCache
{
  private final Map<String,IRubric> _cachedRubrics = new HashMap<String,IRubric>();
  private volatile int _maxRubricsCacheable = 150;
  private final Object _lockObject = new Object();
  
  // Private constructor can still be accessed by Spring
  private RubricCache () {
    
  }

  @Override
  public int getMaxRubricsCacheable () {
    // Reading an int is atomic, so I don't see any reason to lock this.
    return _maxRubricsCacheable;
  }

  @Override
  public void setMaxRubricsCacheable (int value) {
    synchronized( _lockObject ) {
      _maxRubricsCacheable = value;
    }
  }

  @Override
  public int getNumberOfRubricsCached () {
    // Reading an int is atomic, so I don't see any reason to lock this.
    return _cachedRubrics.size ();
  }

  @Override
  public IRubric lookupRubric (String itemId) {
    synchronized( _lockObject ) {
      return _cachedRubrics.get ( itemId );
    }
  }

  @Override
  public void storeRubric (IRubric rubric) {
    synchronized( _lockObject ) {
      if ( _maxRubricsCacheable > 0 ) {
        for ( IRubric rubric_i : getCachedRubrics() ) {
          if ( _cachedRubrics.size() < _maxRubricsCacheable ) {
            break;
          }
          _cachedRubrics.remove ( rubric_i.getItemId () );
        }
      }
      _cachedRubrics.put ( rubric.getItemId (), rubric );
    }
  }

  @Override
  public Collection<IRubric> getCachedRubrics () {
    synchronized( _lockObject ) {
      return new HashSet<IRubric>( _cachedRubrics.values() );
    }
  }
  
  @Override
  public void clear() {
    _cachedRubrics.clear();
  }

}
