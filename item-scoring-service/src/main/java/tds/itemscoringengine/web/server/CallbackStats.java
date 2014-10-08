/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CallbackStats
{
  private static final Map<String, SuccessStats> _callbackStats = new HashMap<String,SuccessStats>();
  private static final Object lockObject = new Object();

  // No constructable objects here.
  private CallbackStats () {}
  
  public static void RecordStat( String url, Boolean success, String msg ) {
    synchronized( lockObject ) {
      SuccessStats successStats = _callbackStats.get (url);
      if ( successStats == null ) {
        successStats = new SuccessStats(url);
        _callbackStats.put (url, successStats);
      }
      if (success) {
        successStats.incrementSuccessCount();
      }
      else {
        successStats.incrementFailureCount();
      }
      if (msg != null) {
        successStats.setLastMsg( msg );
      }
    }
  }
  
  public static Collection<SuccessStats> getData() {
    return new ArrayList<SuccessStats>( _callbackStats.values() );
  }

}
