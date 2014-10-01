/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.complexitemscorers;

import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.RubricContentSource;
import tds.itemscoringengine.RubricContentType;
import tds.itemscoringengine.RubricStats;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ScoreRationale;
import tds.itemscoringengine.ScorerInfo;
import tds.itemscoringengine.ScoringStatus;

public class DummyRubricFactory implements IRubricFactory
{

  @Override
  public IRubric create (final String instanceId) {
    return new IRubric ()
    {

      @Override
      public String getItemId () {
        return instanceId;
      }

      @Override
      public String getType () {
        return "DUMMY";
      }

      @Override
      public void load (RubricContentType type, Object rubric) {
      }

      @Override
      public ItemScore scoreItem (ResponseInfo studentResponseInfo) {
        return new ItemScore (1, 1, ScoringStatus.Scored, "Fifth", new ScoreRationale ()
        {
          {
            setMsg ("Just 'cause");
          }
        }, null, studentResponseInfo.getContextToken ());
      }

      @Override
      public IRubricStats getStats () {
        return new RubricStats ();
      }

    };
  }

  @Override
  public ScorerInfo GetScorerInfo () {
    return new ScorerInfo ("1.0", false, false, RubricContentSource.ItemXML);
  }

}
