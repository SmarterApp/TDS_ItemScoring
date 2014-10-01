/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

import java.lang.management.ManagementFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import tds.itemscoringengine.complexitemscorers.RubricCache;
import AIR.Common.Threading.BoundedThreadPool;

@Controller
public class IndexHandler
{
  
  @Autowired
  private AppStatsRecorder appStats;
  
  @Autowired
  private RubricCache rubricCache;
  
    @RequestMapping("/index")
    public ModelAndView handleIndex() {
      ModelAndView mav = new ModelAndView( "index" );
      mav.addObject ("appStats", appStats);
      mav.addObject ("rubricCache", rubricCache);
      mav.addObject ("numberOfProcessors", BoundedThreadPool.getNumberOfProcessors() );
      mav.addObject ("successStats", CallbackStats.getData() );
      mav.addObject ("memoryPoolMXBeans", ManagementFactory.getMemoryPoolMXBeans() );
      mav.addObject ("threadInfo", ManagementFactory.getThreadMXBean().dumpAllThreads(false, false) );
      return mav;
    }
    
    @RequestMapping("/")
    public View handleDefault() {
      return new RedirectView ( "index.xhtml", true );
    }

}
