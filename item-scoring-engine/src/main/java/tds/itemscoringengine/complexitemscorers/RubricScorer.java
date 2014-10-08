/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.complexitemscorers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.IItemScorerCallback;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.ScoreRationale;
import tds.itemscoringengine.ScorerInfo;
import tds.itemscoringengine.ScoringStatus;
import AIR.Common.time.TimeSpan;

public class RubricScorer implements IItemScorer
{

  private final IRubricFactory _rubricFactory;

  @Autowired
  private RubricCache          _rubricCache = null;

  public RubricScorer (IRubricFactory factory) {
    _rubricFactory = factory;
  }

  @Override
  public ScorerInfo GetScorerInfo (String itemFormat) {
    return _rubricFactory.GetScorerInfo ();
  }

  @Override
  public ItemScore ScoreItem (ResponseInfo responseInfo, IItemScorerCallback callback) {
    IRubricCache cache = getRubricCache ();
    IRubric rubric = cache.lookupRubric (responseInfo.getItemIdentifier ());
    if (rubric == null) {
      rubric = _rubricFactory.create (responseInfo.getItemIdentifier ());
      try {
        Date start = new Date ();
        rubric.load (responseInfo.getContentType (), responseInfo.getRubric ());
        rubric.getStats ().trackLoadTime (new TimeSpan (start, new Date ()));
        // If we are allowed to cache this rubric, lets do so to prevent having
        // to re-read it again
        if (responseInfo.isCanCacheRubric ()) {
          cache.storeRubric (rubric);
        }
      } catch (Exception exp) {
        // something bad happened here. Nothing we can do other than return an
        // error
        final IRubric currentRubric = rubric;
        final StringWriter sw = new StringWriter ();
        exp.printStackTrace (new PrintWriter (sw));
        return new ItemScore (-1, -1, ScoringStatus.ScoringError, null, new ScoreRationale ()
        {
          {
            setMsg ("Exception loading rubric for item " + currentRubric.getItemId () + "\n" + sw.toString ());
          }
        }, null, responseInfo.getContextToken ());
      }
    }

    rubric.getStats ().trackAccess ();

    try {
      Date d = new Date ();
      ItemScore score = rubric.scoreItem (responseInfo);
      rubric.getStats ().trackScoreTimes (new TimeSpan (d, new Date ()));
      return score;
    } catch (Exception exp) {
      // something bad happened here. Nothing we can do other than return an
      // error
      rubric.getStats ().trackScoreTimes (null);
      final IRubric currentRubric = rubric;

      final StringWriter sw = new StringWriter ();
      exp.printStackTrace (new PrintWriter (sw));
      return new ItemScore (-1, -1, ScoringStatus.ScoringError, null, new ScoreRationale ()
      {
        {
          setMsg ("Exception scoring item " + currentRubric.getItemId () + "\n" + sw.toString ());
        }
      }, null, responseInfo.getContextToken ());
    }

    // Unreachable code.
    // return new Score (-1, ScoringStatus.NoScoringEngine, null, null, null,
    // responseInfo.getContextToken());
  }

  @Override
  public void shutdown () {
  }

  protected IRubricCache getRubricCache () {
    return _rubricCache;
  }

}
