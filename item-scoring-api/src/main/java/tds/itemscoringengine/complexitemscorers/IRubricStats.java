/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.complexitemscorers;

import AIR.Common.time.TimeSpan;

public interface IRubricStats
{
  public TimeSpan getLoadTime();
  
  public int getNumUses();
  
  public int getNumSuccessfulScores();
  
  public int getNumUnsuccessfulScores();
  
  public TimeSpan getMinScoreTime();
  
  public long getAveScoreTimeNanos();
  
  public TimeSpan getMaxScoreTime();
  
  public void trackAccess();    // Number of times this rubric has been accessed
  
  public void trackLoadTime(TimeSpan loadTime);  // time to load this rubric
  
  public void trackScoreTimes( TimeSpan scoreTime );  // Number of times this rubric has been called upon to score (timespan == null implies failure to score)
}
