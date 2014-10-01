/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.web.server;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import AIR.Common.Web.HttpHandlerBase;

/**
 * @author temp_rreddy
 * 
 */
@Controller
@Scope("singleton")
public class AdminHandler extends HttpHandlerBase
{

  private static Logger _logger = LoggerFactory.getLogger (AdminHandler.class);

  @RequestMapping (value = "/Admin", method = RequestMethod.GET)
  public void ProcessRequest ( ServletResponse response )
  {
    try {
      response.setContentType ("text/xml");
      OutputStream output = response.getOutputStream ();

      Element docElement = new Element ("status");
      docElement.addContent ("Item scoring server is running");
      Document doc = new Document (docElement);

      XMLOutputter xmlOutput = new XMLOutputter ();
      xmlOutput.output (doc, output);
    } catch (IOException e) {
      _logger.error ("Exception writing output from AdminHandler", e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see AIR.Common.Web.HttpHandlerBase#onBeanFactoryInitialized()
   */
  @Override
  protected void onBeanFactoryInitialized () {
    // No action needed

  }

}
