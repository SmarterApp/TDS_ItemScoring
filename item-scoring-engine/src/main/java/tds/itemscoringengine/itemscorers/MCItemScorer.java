/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.itemscorers;

import org.apache.commons.lang.StringUtils;

import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.IItemScorerCallback;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.RubricContentSource;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ScoreRationale;
import tds.itemscoringengine.ScorerInfo;
import tds.itemscoringengine.ScoringStatus;

public class MCItemScorer implements IItemScorer
{

  @Override
  public ScorerInfo GetScorerInfo (String itemFormat) {
    return new ScorerInfo ("1.0", false, false, RubricContentSource.AnswerKey);
  }

  @Override
  public ItemScore ScoreItem (final ResponseInfo responseInfo, IItemScorerCallback callback) {
    if (responseInfo.getStudentResponse () != null && responseInfo.getRubric () != null) {
      // The rubric is of the format <answerkey>|<maxscore>
      String rubricContent = (String) (responseInfo.getRubric ());
      String[] tokens = StringUtils.split (rubricContent, '|');
      final String answerKey = tokens[0];
      // Legacy support in case "|<maxscore>" is not part of the rubric or if it
      // is 0
      int maxScore = tokens.length > 1 && Integer.parseInt (tokens[1]) > 0 ? Integer.parseInt (tokens[1]) : 1;
      int scorePoint = StringUtils.equals (responseInfo.getStudentResponse ().toUpperCase (), answerKey.toUpperCase ()) ? maxScore : 0;

      return new ItemScore (scorePoint, maxScore, ScoringStatus.Scored, null, new ScoreRationale ()
      {
        {
          setMsg (answerKey);
        }
      }, null, responseInfo.getContextToken ());
    }

    return new ItemScore (-1, -1, ScoringStatus.ScoringError, null, new ScoreRationale ()
    {
      {
        setMsg (String.format ("Error Scoring in TDS %b/%b", responseInfo.getStudentResponse () == null, responseInfo.getRubric () == null));
      }
    }, null, responseInfo.getContextToken ());
  }

  @Override
  public void shutdown () {
  }

}
