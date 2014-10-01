/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.air.scoring.loadtest.automation.AbstractLoadTest;
import org.air.scoring.loadtest.automation.DefineResponse;
import org.air.scoring.loadtest.automation.DefineRubric;
import org.air.scoring.loadtest.automation.ItemScoreParser;
import org.air.scoring.loadtest.core.data.ResponseInfo;
import org.air.scoring.loadtest.core.data.Rubric.RubricType;
import org.air.scoring.loadtest.core.data.SentRequestRecord;
import org.air.scoring.loadtest.core.data.SentRequestRecord.RequestStatus;
import org.junit.Test;

import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.complexitemscorers.IRubric;

/**
 * Integration test for Item scoring Service Need to pass
 * "org.air.scoring.loadtest.serverUrl" parameter
 * 
 */
public class IntegrationTest extends AbstractLoadTest
{

  @DefineRubric (refId = "RUBRIC 1", value = "A")
  private IRubric      rubric;

  @DefineResponse (rubricRef = "RUBRIC 1", value = "B", itemFormat = "MC")
  private ResponseInfo wrongResponse;

  @DefineResponse (rubricRef = "RUBRIC 1", value = "A", itemFormat = "MC")
  private ResponseInfo correctResponse;

  @DefineRubric (refId = "ITEM 22484", resource = "Item_22484_v5_rubric.xml", type = RubricType.Ext)
  private IRubric      essayRubric;

  @DefineResponse (rubricRef = "ITEM 22484", resource = "essay_22484_01.txt", itemFormat = "ER")
  private ResponseInfo essayResponse1;

  @Test
  public void loadTest () throws InterruptedException {

    // Use a manual scoring request as a bookmark to indicate where the
    // automatic scoring requests begin.
    final long previousId = this.sendNow (correctResponse);

    // Send 1,000 requests, using the "correct response" answer, and targeting
    // a load of 100 pending requests until all 1,000 have been sent.
    getRunState ().setSelectedRequests (new Long[] { correctResponse.getId () });
    getRunState ().setTargetNumPending (100);
    getRequestGenerator ().runUntil (1000);

    // Wait until all 1,000 requests have been sent (but asynchronous scores not
    // necessarily received)
    final long t_f = System.currentTimeMillis () + 30000;
    getRequestGenerator ().waitUntilPaused (30000);

    // Confirm that all 1,000 asynchronous scoring replies are received within
    // 30 seconds of the load test start.
    int i = 0;
    for (SentRequestRecord record_i : getSentRequestRepository ().list ()) {
      long sentRequestId = record_i.getId ();
      if (sentRequestId <= previousId) {
        continue;
      }
      long t_wait = t_f - System.currentTimeMillis ();
      if (t_wait <= 0) {
        throw new AssertionError ("Time exceeded: unable to process 1000 requests in 30 seconds.");
      }
      this.waitForCallback (sentRequestId, t_wait);
      SentRequestRecord record_i_updated = getSentRequestRepository ().find (sentRequestId);
      assertEquals (String.format ("Response was not received for request %d", i + 1), RequestStatus.REPLY_RECEIVED, record_i_updated.getStatus ());
      i++;
    }

    // Note: there is a lack of synchronization between the load tests threads
    // that can cause them not to stop on a dime, so there might be one extra
    // request processed.
    assertTrue ("Wrong number of requests was processed, expected about 1000", i >= 1000 && i <= 1001);

  }

  @Test
  public void incorrectAnswerTest () throws Exception {
    long sentRequestId = this.sendNow (wrongResponse);
    this.waitForCallback (sentRequestId, 5000);
    SentRequestRecord rec = this.getSentRequestRepository ().find (sentRequestId);
    assertEquals ("Response was not received", RequestStatus.REPLY_RECEIVED, rec.getStatus ());
    ItemScore itemScore = ItemScoreParser.getScorePoint (rec.getCallbackData ());
    int scorePoint = itemScore.getScorePoint ();
    assertEquals ("Answer is Correct for incorrectAnswerTest ", 0, scorePoint);
  }

  @Test
  public void correctAnswerTest () throws Exception {
    long sentRequestId = this.sendNow (correctResponse);
    this.waitForCallback (sentRequestId, 5000);
    SentRequestRecord rec = this.getSentRequestRepository ().find (sentRequestId);
    assertEquals ("Response was not received", RequestStatus.REPLY_RECEIVED, rec.getStatus ());
    ItemScore itemScore = ItemScoreParser.getScorePoint (rec.getCallbackData ());
    int scorePoint = itemScore.getScorePoint ();
    assertEquals ("Answer is not Correct for correctAnswerTest ", 1, scorePoint);
  }

  @Test
  public void essayAnswerTest () throws Exception {
    long sentRequestId = this.sendNow (essayResponse1);
    this.waitForCallback (sentRequestId, 60000);
    SentRequestRecord rec = this.getSentRequestRepository ().find (sentRequestId);
    assertEquals ("Response was not received", RequestStatus.REPLY_RECEIVED, rec.getStatus ());
    ItemScore itemScore = ItemScoreParser.getScorePoint (rec.getCallbackData ());
    System.out.println (rec.getCallbackData ());
    int scorePoint = itemScore.getScorePoint ();
    assertEquals ("Answer is not Correct for essayAnswerTest ", 2, scorePoint);
  }

}
