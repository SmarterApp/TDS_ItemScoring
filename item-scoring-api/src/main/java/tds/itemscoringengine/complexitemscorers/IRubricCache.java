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

public interface IRubricCache
{
  
  public abstract int getMaxRubricsCacheable();
  public abstract void setMaxRubricsCacheable( int value );
  
  public abstract int getNumberOfRubricsCached();
  
  public abstract IRubric lookupRubric( String itemId );
  
  public abstract void storeRubric( IRubric rubric );
  
  public abstract Collection<IRubric> getCachedRubrics();
  
  public abstract void clear();
}
