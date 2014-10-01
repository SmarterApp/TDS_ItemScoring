/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import org.apache.commons.lang.NotImplementedException;

import tds.itemscoringengine.IItemScorerCallback;

public class WebProxyItemScorerCallback implements IItemScorerCallback
{
  /**
   * This url is used for the scoring server.
   */
  private String _serverURL;

  /**
   * This url is used for the scoring server to report the score back to this
   * application.
   */
  private String _callbackURL;

  public WebProxyItemScorerCallback (String serverUrl) {
    _serverURL = serverUrl;
  }

  public WebProxyItemScorerCallback (String serverUrl, String callbackUrl) {
    _serverURL = serverUrl;
    _callbackURL = callbackUrl;
  }

  public String getServerURL () {
    return _serverURL;
  }

  private void setServerURL (String serverURL) {
    this._serverURL = serverURL;
  }

  public String getCallbackURL () {
    return _callbackURL;
  }

  private void setCallbackURL (String callbackURL) {
    this._callbackURL = callbackURL;
  }

  @Override
  public void ScoreAvailable (ItemScore score, ResponseInfo responseInfo) {
    throw new NotImplementedException ();
  }
}
