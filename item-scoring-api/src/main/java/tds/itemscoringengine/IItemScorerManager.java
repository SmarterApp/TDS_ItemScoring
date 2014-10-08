/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import AIR.Common.Web.IUrlRewriter;

/**
 * @author temp_rreddy
 * 
 */
public interface IItemScorerManager extends IItemScorer
{
  // / <summary>
  // / Register the specific scoring engine to be used
  // / to score responses of the specified format
  // / </summary>
  // / <param name="itemFormat">Item format (MC, WB, GI, RW etc)</param>
  // / <param name="itemScorer">Scoring engine reference</param>
  void RegisterItemScorer (String itemFormat, IItemScorer itemScorer);
  
  /**
   * Sets and retrieves an object that can be used to rewrite rubric URLs
   * @return
   */
  IUrlRewriter getRubricUrlRewriter();
  void setRubricUrlRewriter( IUrlRewriter value );
  
  /**
   * End-of-lifecycle call to release resources
   */
  void shutdown();
}
