/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AIR.Common.Threading.BoundedThreadPool;

/**
 * A base class for AsyncResponseCallbackDemon and ItemScoringClient
 */
public class ItemScoringOnline
{
  private final BoundedThreadPool _threadPool;
  private static final Logger _logger = LoggerFactory.getLogger( ItemScoringOnline.class );
  
  protected ItemScoringOnline () {
    _threadPool = new BoundedThreadPool(10, "OnlineScoring", 500, 400);
  }
  
  protected BoundedThreadPool getThreadPool() { return _threadPool; }
  
  protected void Log( String message ) {
    _logger.info (message);
  }
  
  public void shutdown() {
	  _threadPool.shutdown(true);
  }


}
