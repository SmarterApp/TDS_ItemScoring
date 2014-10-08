/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

public class SuccessStats
{
  private final String _url;
  private int _successCount;
  private int _failureCount;
  private String _lastMsg;

  public SuccessStats ( String url ) {
    _url = url;
    _successCount = 0;
    _failureCount = 0;
    _lastMsg = null;
  }
  
  public String getUrl() { return _url; }
  
  public int getSuccessCount() { return _successCount; }
  public void incrementSuccessCount() { _successCount ++; }
  
  public int getFailureCount() { return _failureCount; }
  public void incrementFailureCount() { _failureCount ++; }
  
  public String getLastMsg() { return _lastMsg; }
  public void setLastMsg( String value ) { _lastMsg = value; }

}
