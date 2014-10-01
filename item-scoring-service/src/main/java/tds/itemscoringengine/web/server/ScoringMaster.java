/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.ItemScorerManagerImpl;
import AIR.Common.Configuration.AppSettingsHelper;

// Note: the Spring configuration for this component is in the
// servlet-context.xml
public class ScoringMaster extends ItemScorerManagerImpl
{

  @SuppressWarnings ("unused")
  private static final Logger _logger = LoggerFactory.getLogger (ScoringMaster.class);

  public ScoringMaster (Map<String, IItemScorer> engines, AppStatsRecorder statsRecorder) {
    super (AppSettingsHelper.getInt32 ("QThreadCount", 20),
        AppSettingsHelper.getInt32 ("QHiWaterMark", 500),
        AppSettingsHelper.getInt32 ("QLowWaterMark", 400),
        statsRecorder);

    statsRecorder.setCreationTime ();

    for (Map.Entry<String, IItemScorer> entry_i : engines.entrySet ()) {
      RegisterItemScorer (entry_i.getKey (), entry_i.getValue ());
    }
  }
}
