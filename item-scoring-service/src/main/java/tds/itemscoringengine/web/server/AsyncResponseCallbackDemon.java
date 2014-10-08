/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

import java.io.OutputStream;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import AIR.Common.Threading.AbstractTask;
import tds.itemscoringengine.ItemScoreResponse;

@Component
@Scope ("singleton")
public class AsyncResponseCallbackDemon extends ItemScoringOnline
{
  @Autowired
  ItemScoringEngineHttpWebHelper _httpWebHelper;

  public void sendResponse (final String url, final ItemScoreResponse scoreResponse) {
    _httpWebHelper.sendXml (url, scoreResponse);
    Log ("ItemScoringServer SendResponse");
  }

  public void sendResponse (final String url, final ItemScoreResponse scoreResponse, final OutputStream acknowledgement) {
    _httpWebHelper.sendXml (url, scoreResponse, acknowledgement);
    Log ("ItemScoringServer SendResponse");
  }

  public void sendResponseAsync (final String url, final ItemScoreResponse scoreResponse) {
    getThreadPool ().Enqueue (new AbstractTask ()
    {
      @Override
      public void Execute () {
        sendResponse (url, scoreResponse);
      }
    });
  }

  @PreDestroy
  @Override
  public void shutdown () {
    super.shutdown ();
  }

}
