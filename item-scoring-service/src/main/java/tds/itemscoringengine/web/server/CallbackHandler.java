/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.IItemScorerCallback;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.ItemScoreRequest;
import tds.itemscoringengine.ItemScoreResponse;

public class CallbackHandler implements IItemScorerCallback
{
  
  Logger _logger = LoggerFactory.getLogger( CallbackHandler.class );
  
  private AsyncResponseCallbackDemon _responseDemon;
  private ItemScoreRequest _scoreRequest;

  public CallbackHandler ( AsyncResponseCallbackDemon responseDemon, ItemScoreRequest scoreRequest) {
    _responseDemon = responseDemon;
    _scoreRequest = scoreRequest;
  }
  
  public AsyncResponseCallbackDemon getResponseDemon() { return _responseDemon; }
  public void setResponseDemon( AsyncResponseCallbackDemon value ) { _responseDemon = value; }
  
  public ItemScoreRequest getScoreRequest() { return _scoreRequest; }
  public void setScoreRequest( ItemScoreRequest value ) { _scoreRequest = value; }

  @Override
  public void ScoreAvailable (ItemScore score, ResponseInfo responseInfo) {
    try {
      ItemScoreResponse scoreResponse = new ItemScoreResponse( score, responseInfo.getContextToken() );
      ByteArrayOutputStream acknowledgement = new ByteArrayOutputStream();
      _responseDemon.sendResponse( _scoreRequest.getCallbackUrl (), scoreResponse, acknowledgement );
      if (acknowledgement.size() > 0 ) {
        // Callback failed. We are not supposed to get back any data. If we do, either we got an
        // exception sent back to us as an HTML file or something else.
        String ackString = acknowledgement.toString();
        _logger.info ( String.format( "Found unexpected message returned from callback URL: %s", ackString ) );
        CallbackStats.RecordStat( _scoreRequest.getCallbackUrl(), false, ackString );
      }
      else {
        CallbackStats.RecordStat( _scoreRequest.getCallbackUrl(), true, null );
      }
    }
    catch ( Throwable t ) {
      CallbackStats.RecordStat( _scoreRequest.getCallbackUrl(), false, t.toString() );
      _logger.error ( String.format( "Error returning score to client (%s)", t.getMessage () ), t );
      throw t;
    }
  }

}
