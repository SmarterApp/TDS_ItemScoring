/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qtiscoringengine.CustomOperatorRegistry;
import qtiscoringengine.ISECustomOperator;
import qtiscoringengine.QTIScoringRuntimeException;
import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.ItemScorerManagerImpl;
import tinyctrlscoringengine.CtrlCustomOpFactory;
import tinyequationscoringengine.TinyEqCustomOpFactory;
import tinygrscoringengine.TinyGRCustomOpFactory;
import tinytablescoringengine.TinyTableCustomOpFactory;
import AIR.Common.Configuration.AppSettingsHelper;
import AIR.Common.Utilities.SpringApplicationContext;

// Note: the Spring configuration for this component is in the
// servlet-context.xml
public class ScoringMaster extends ItemScorerManagerImpl
{

  @SuppressWarnings ("unused")
  private static final Logger _logger = LoggerFactory.getLogger (ScoringMaster.class);

  public ScoringMaster (Map<String, IItemScorer> engines, AppStatsRecorder statsRecorder) {
    super (AppSettingsHelper.getInt32 ("QThreadCount", 20), AppSettingsHelper.getInt32 ("QHiWaterMark", 500), AppSettingsHelper.getInt32 ("QLowWaterMark", 400), statsRecorder);

    statsRecorder.setCreationTime ();

    for (Map.Entry<String, IItemScorer> entry_i : engines.entrySet ()) {
      RegisterItemScorer (entry_i.getKey (), entry_i.getValue ());
    }

    initOperators ();
  }

  private static String getPythonScoringUrl () {
    return AppSettingsHelper.get ("itemscoring.qti.sympyServiceUrl");
  }

  private static int getMaxTries () {
    return AppSettingsHelper.getInt32 ("itemscoring.qti.sympyMaxTries", 3);
  }

  // TODO Shiva: We need to ensure that initOperators may be invoked only ones.
  private void initOperators () {
    CustomOperatorRegistry.getInstance ().addOperatorFactory (new ISECustomOperator (this));
    CustomOperatorRegistry.getInstance ().addOperatorFactory (new TinyGRCustomOpFactory ());
    CustomOperatorRegistry.getInstance ().addOperatorFactory (new CtrlCustomOpFactory ());
    CustomOperatorRegistry.getInstance ().addOperatorFactory (new TinyTableCustomOpFactory ());
    try {
      CustomOperatorRegistry.getInstance ().addOperatorFactory (new TinyEqCustomOpFactory (new URI (getPythonScoringUrl ()), getMaxTries ()));
    } catch (URISyntaxException exp) {
      throw new QTIScoringRuntimeException (exp);
    }
  }
}
