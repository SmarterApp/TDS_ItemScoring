/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ItemScorerManagerImplTest
{

  private ItemScorerManagerImpl OUT;
  private IItemScorer           MOCK_ITEM_SCORER;
  private ScorerInfo            SCORER_INFO;

  private static final int      THREAD_COUNT             = 2;
  private static final int      HIGH_WATER               = 10;
  private static final int      LOW_WATER                = 5;
  private static final String   ITEM_FORMAT_1            = "AA";
  private static final String   ITEM_FORMAT_2            = "BB";

  private static final Logger   _logger                  = LoggerFactory.getLogger (ItemScorerManagerImplTest.class);

  private ItemScore             asynchronousScore        = null;
  private ResponseInfo          asynchronousResponseInfo = null;
  private Object                score_monitor            = new Object ();

  @Before
  public void setUp () {
    OUT = new ItemScorerManagerImpl (THREAD_COUNT, HIGH_WATER, LOW_WATER);
    MOCK_ITEM_SCORER = mock (IItemScorer.class);
    OUT.RegisterItemScorer (ITEM_FORMAT_1, MOCK_ITEM_SCORER);
    SCORER_INFO = new ScorerInfo ("VER1", false, false, null);
    when (MOCK_ITEM_SCORER.GetScorerInfo (ITEM_FORMAT_1)).thenReturn (SCORER_INFO);
  }

  @After
  public void shutdown () {
    OUT.shutdown ();
  }

  @Test
  public void registerItemScorerTest () throws Exception {

    // Set up test--see setUp()

    // Do action--see setUp()

    // Check results
    Map<String, IItemScorer> map = getScorerMap ();
    assertTrue (map.containsKey (ITEM_FORMAT_1));
    assertSame (map.get (ITEM_FORMAT_1), MOCK_ITEM_SCORER);
  }

  @Test
  public void getScorereInfoTest () throws Exception {

    // Set up test

    // Do action
    ScorerInfo returned_info = OUT.GetScorerInfo (ITEM_FORMAT_1);

    // Check results
    assertSame (SCORER_INFO, returned_info);
    assertNull (OUT.GetScorerInfo (ITEM_FORMAT_2));
  }

  @Test
  public void scoreSynchronousTest () throws Exception {
    // Set up test
    ResponseInfo studentResponse = new ResponseInfo (ITEM_FORMAT_1, "1234-5678", "", null, RubricContentType.ContentString, "1245", false);
    ItemScore score = new ItemScore (1, 1, ScoringStatus.Scored, "AAAA", new ScoreRationale ()
    {
      {
        setMsg ("BBBB");
      }
    }, new ArrayList<ItemScoreInfo> (), "1245");
    when (MOCK_ITEM_SCORER.ScoreItem ((ResponseInfo) any (), (IItemScorerCallback) any ())).thenReturn (score);

    // Do action
    ItemScore synchronous_score = OUT.ScoreItem (studentResponse, null);

    // Check results
    assertSame (score, synchronous_score);

  }

  @Test
  public void scoreAsynchronousTest () throws Exception {
    // Set up test
    ResponseInfo studentResponse = new ResponseInfo (ITEM_FORMAT_1, "1234-5678", "", null, RubricContentType.ContentString, "1245", false);
    ItemScore score = new ItemScore (1, 1, ScoringStatus.Scored, "AAAA", new ScoreRationale ()
    {
      {
        setMsg ("BBBB");
      }
    }, new ArrayList<ItemScoreInfo> (), "1245");
    IItemScorerCallback callback = new IItemScorerCallback ()
    {

      @Override
      public void ScoreAvailable (ItemScore score, ResponseInfo responseInfo) {
        synchronized (score_monitor) {
          asynchronousScore = score;
          asynchronousResponseInfo = responseInfo;
          score_monitor.notifyAll ();
        }
      }
    };
    when (MOCK_ITEM_SCORER.ScoreItem ((ResponseInfo) any (), (IItemScorerCallback) any ())).thenReturn (score);

    // Do action
    ItemScore synchronous_score = OUT.ScoreItem (studentResponse, callback);

    // Check results
    assertEquals (ScoringStatus.WaitingForMachineScore, synchronous_score.getScoreInfo ().getStatus ());
    synchronized (score_monitor) {
      if (asynchronousScore == null) {
        score_monitor.wait (1000L);
      }
      assertSame (score, asynchronousScore);
      assertSame (studentResponse, asynchronousResponseInfo);
    }
  }

  @SuppressWarnings ("unchecked")
  @Test
  public void scoreErrorTest () throws Exception {
    // Set up test
    ResponseInfo studentResponse = new ResponseInfo (ITEM_FORMAT_1, "1234-5678", "", null, RubricContentType.ContentString, "1245", false);
    IItemScorerCallback callback = new IItemScorerCallback ()
    {

      @Override
      public void ScoreAvailable (ItemScore score, ResponseInfo responseInfo) {
        synchronized (score_monitor) {
          asynchronousScore = score;
          asynchronousResponseInfo = responseInfo;
          score_monitor.notifyAll ();
        }
      }
    };
    when (MOCK_ITEM_SCORER.ScoreItem ((ResponseInfo) any (), (IItemScorerCallback) any ())).thenThrow (RuntimeException.class);

    // Do action
    ItemScore synchronous_score = OUT.ScoreItem (studentResponse, callback);

    // Check results
    assertEquals (ScoringStatus.WaitingForMachineScore, synchronous_score.getScoreInfo ().getStatus ());
    synchronized (score_monitor) {
      if (asynchronousScore == null) {
        score_monitor.wait (1000L);
      }
      assertSame (studentResponse, asynchronousResponseInfo);
      assertEquals (ScoringStatus.ScoringError, asynchronousScore.getScoreInfo ().getStatus ());
    }
  }

  @Test
  public void proxiedAsynchronousScoreTest () throws Exception {

    // Set up test
    SCORER_INFO = new ScorerInfo ("VER2", true, false, null);
    when (MOCK_ITEM_SCORER.GetScorerInfo (ITEM_FORMAT_1)).thenReturn (SCORER_INFO);
    final ResponseInfo studentResponse = new ResponseInfo (ITEM_FORMAT_1, "1234-5678", "", null, RubricContentType.ContentString, "1245", false);
    final ItemScore final_score = new ItemScore (1, 1, ScoringStatus.Scored, "AAAA", new ScoreRationale ()
    {
      {
        setMsg ("BBBB");
      }
    }, new ArrayList<ItemScoreInfo> (), "1245");
    final ItemScore first_score = new ItemScore (-1, -1, ScoringStatus.WaitingForMachineScore, "AAAA", new ScoreRationale ()
    {
      {
        setMsg ("BBBB");
      }
    }, null, "1245");
    IItemScorerCallback callback = new IItemScorerCallback ()
    {

      @Override
      public void ScoreAvailable (ItemScore score, ResponseInfo responseInfo) {
        synchronized (score_monitor) {
          asynchronousScore = score;
          asynchronousResponseInfo = responseInfo;
          score_monitor.notifyAll ();
        }
      }
    };
    when (MOCK_ITEM_SCORER.ScoreItem ((ResponseInfo) any (), (IItemScorerCallback) any ())).then (new Answer<ItemScore> ()
    {

      @Override
      public ItemScore answer (InvocationOnMock invocation) throws Throwable {
        IItemScorerCallback cb = ((IItemScorerCallback) invocation.getArguments ()[1]);
        cb.ScoreAvailable (final_score, studentResponse);
        return first_score;
      }

    });

    synchronized (score_monitor) {
      // Do action
      ItemScore synchronous_score = OUT.ScoreItem (studentResponse, callback);

      // Check results
      assertEquals (ScoringStatus.WaitingForMachineScore, synchronous_score.getScoreInfo ().getStatus ());
      if (asynchronousScore == null) {
        score_monitor.wait (1000L);
      }
      _logger.debug (asynchronousScore.toString ());
      _logger.debug (final_score.toString ());
      _logger.debug (synchronous_score.toString ());
      _logger.debug (first_score.toString ());

      assertSame (studentResponse, asynchronousResponseInfo);
      assertSame (final_score, asynchronousScore);
      assertSame (first_score, synchronous_score);
    }
  }

  @Test
  public void noScorerTest () throws Exception {
    // Set up test
    ResponseInfo studentResponse = new ResponseInfo (ITEM_FORMAT_2, "1234-5678", "", null, RubricContentType.ContentString, "1245", false);
    ItemScore score = new ItemScore (1, 1, ScoringStatus.Scored, "AAAA", new ScoreRationale ()
    {
      {
        setMsg ("BBBB");
      }
    }, new ArrayList<ItemScoreInfo> (), "1245");
    when (MOCK_ITEM_SCORER.ScoreItem ((ResponseInfo) any (), (IItemScorerCallback) any ())).thenReturn (score);

    // Do action
    ItemScore synchronous_score = OUT.ScoreItem (studentResponse, null);

    // Check results
    assertEquals (synchronous_score.getScoreInfo ().getStatus (), ScoringStatus.NoScoringEngine);

  }

  @SuppressWarnings ("unchecked")
  private Map<String, IItemScorer> getScorerMap () throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Field scorerMapField = ItemScorerManagerImpl.class.getDeclaredField ("_scoringEngines");
    scorerMapField.setAccessible (true);
    return (Map<String, IItemScorer>) scorerMapField.get (OUT);
  }

}
