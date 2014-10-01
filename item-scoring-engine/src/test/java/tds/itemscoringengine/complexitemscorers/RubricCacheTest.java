/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.complexitemscorers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/item-scoring-engine-test-context.xml")
public class RubricCacheTest
{
  
  @Autowired
  private IRubricCache OUT;
  
  @Before
  public void setUp() {
    // Return the cache to its defaults
    OUT.setMaxRubricsCacheable ( 150 );
    OUT.clear ();
  }
  
  @Test
  public void instantiationTest() {
    assertTrue( OUT instanceof RubricCache );
  }

  @Test
  public void getPutTest() {
    IRubric mockRubric = mock( IRubric.class );
    when ( mockRubric.getItemId () ).thenReturn ("RUBRIC1");
    OUT.storeRubric ( mockRubric );
    assertEquals( 1, OUT.getNumberOfRubricsCached () );
    assertSame( mockRubric, OUT.lookupRubric ("RUBRIC1"));
  }
  
  @Test
  public void maxStoreTest() {
    OUT.setMaxRubricsCacheable ( 2 );
    for ( int i = 0; i < 3; i ++ ) {
      IRubric rubric_i = mock( IRubric.class );
      when ( rubric_i.getItemId () ).thenReturn (String.format( "RUBRIC%d", i ) );
      OUT.storeRubric ( rubric_i );
    }
    assertEquals( 2, OUT.getNumberOfRubricsCached () );
  }

}
