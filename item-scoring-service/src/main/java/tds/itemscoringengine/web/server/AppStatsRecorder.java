/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import AIR.Common.Threading.ThreadPoolStatsRecorder;
import AIR.Common.time.TimeSpan;

@Component
@Scope("singleton")
public class AppStatsRecorder extends ThreadPoolStatsRecorder
{

  private Object        totalScoreRequestsLock            = new Object ();
  private volatile long totalScoreRequests                = 0;

  private Object        totalValidScoreRequestsLock       = new Object ();
  private volatile long totalValidScoreRequests           = 0;

  private Object        totalScoreResponsesLock           = new Object ();
  private volatile long totalScoreResponses               = 0;

  private Object        totalSuccessfulScoreResponsesLock = new Object ();
  private volatile long totalSuccessfulScoreResponses     = 0;

  private volatile Date creationTime                      = new Date();

  public void incrementScoreRequests () {
    synchronized (totalScoreRequestsLock) {
      totalScoreRequests++;
    }
  }

  public long getTotalScoreRequests () {
    return totalScoreRequests;
  }

  public synchronized void incrementValidScoreRequests () {
    synchronized (totalValidScoreRequestsLock) {
      totalValidScoreRequests++;
    }
  }

  public long getTotalValidScoreRequests () {
    return totalValidScoreRequests;
  }

  public synchronized void incrementScoreResponses () {
    synchronized (totalScoreResponsesLock) {
      totalScoreResponses++;
    }
  }

  public long getTotalScoreResponses () {
    return totalScoreResponses;
  }

  public synchronized void incrementSuccessfulScoreResponses () {
    synchronized (totalSuccessfulScoreResponsesLock) {
      totalSuccessfulScoreResponses++;
    }
  }

  public long getTotalSuccessfulScoreResponses () {
    return totalSuccessfulScoreResponses;
  }

  public Date getCreationTime () {
    synchronized( creationTime ) {
      return (Date) creationTime.clone ();
    }
  }

  public void setCreationTime () {
    synchronized( creationTime ) {
      creationTime.setTime ( System.currentTimeMillis () );
    }
  }

  public String getUpTime () {
    synchronized( creationTime ) {
      return new TimeSpan (creationTime, new Date ()).toString ();
    }
  }

}
