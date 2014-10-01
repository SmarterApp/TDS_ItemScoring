/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.complexitemscorers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.RubricContentType;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = { "/rubric-scorer-test-context.xml" })
public class RubricScorerTest
{
  @Autowired
  @Qualifier ("dummyRubricScorer")
  RubricScorer OUT;

  @Test
  public void testExists () {
    assertNotNull (OUT);
  }

  @Test
  public void testHasFactory () throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Field factoryField = OUT.getClass ().getDeclaredField ("_rubricFactory");
    factoryField.setAccessible (true);
    IRubricFactory factory = (IRubricFactory) factoryField.get (OUT);
    assertNotNull (factory);
    assertTrue (factory instanceof DummyRubricFactory);
  }

  @Test
  public void testHasCache () throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Field cacheField = OUT.getClass ().getDeclaredField ("_rubricCache");
    cacheField.setAccessible (true);
    RubricCache cache = (RubricCache) cacheField.get (OUT);
    assertNotNull (cache);
  }

  @Test
  public void testScore () {
    ResponseInfo ri = new ResponseInfo ("DUMMY", "1234-5678", "I don't care what Teacher says, I can't do this sum.", "2+2=4", RubricContentType.ContentString, "0979843210987432107984321", false);
    ItemScore score = OUT.ScoreItem (ri, null);
    assertNotNull (score);

  }

}
