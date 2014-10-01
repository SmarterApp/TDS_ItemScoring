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
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ItemScoreRequest;
import tds.itemscoringengine.ItemScoreResponse;
import AIR.Common.Web.HttpHandlerBase;
import AIR.Common.xml.TdsXmlOutputFactory;
import AIR.Common.xml.XmlReader;
import TDS.Shared.Exceptions.FailedReturnStatusException;

/**
 * @author temp_rreddy
 * 
 */
@Controller
@Scope ("singleton")
public class ItemScoringHandler extends HttpHandlerBase
{

  private static final Logger logger = LoggerFactory.getLogger (ItemScoringHandler.class);

  // We have eliminated static request counts in favor of a Spring-managed bean
  @Autowired
  private AppStatsRecorder    appStats;

  // Configuration for this item is done in servlet-context.xml
  @Autowired
  private ScoringMaster       scoringMaster;

  @Autowired
  AsyncResponseCallbackDemon  asyncResponseCallbackDemon;

  @RequestMapping (value = "/ItemScoring", method = RequestMethod.POST)
  public void ProcessRequest (HttpServletRequest request, ServletResponse response) throws FailedReturnStatusException {
    // HttpContext context = HttpContext.getCurrentContext ();
    appStats.incrementScoreRequests ();

    if (request.getContentLength () == 0)
      return;

    // Parse the score Request
    ItemScoreRequest scoreRequest = new ItemScoreRequest ();
    InputStream inputstream = null;
    XmlReader xmlReader = null;
    try {
      inputstream = request.getInputStream ();
      xmlReader = new XmlReader (inputstream);
    } catch (IOException | JDOMException e) {
      logger.error ("Unreadable XML in request", e);
      throw new FailedReturnStatusException ("400 Invalid request");
    }
    scoreRequest.readXML (xmlReader);

    // Decrypt student response if needed
    scoreRequest.decrypt (true, true);

    // If it is valid and complete, update stats
    if (scoreRequest.getResponseInfo () != null && scoreRequest.getResponseInfo ().isComplete ())
      appStats.incrementValidScoreRequests ();

    // Score the response asynchronously or synchronously based on whether a
    // callback URL is provided
    ItemScore score = (scoreRequest.getCallbackUrl () != null) ?

    scoringMaster.ScoreItem (scoreRequest.getResponseInfo (), new CallbackHandler (asyncResponseCallbackDemon, scoreRequest)) : scoringMaster.ScoreItem (scoreRequest.getResponseInfo (), null);

    // Send interim (in case of async scoring) or final score back to caller
    ItemScoreResponse scoreResponse = new ItemScoreResponse (score, scoreRequest.getResponseInfo ().getContextToken ());
    SendResponse (response, scoreResponse);

    // If we got some score (all ScoreStatus are considered the same), then the
    // attemp to score was successful
    appStats.incrementScoreResponses ();
    if (score != null)
      appStats.incrementSuccessfulScoreResponses ();
  }

  // Shiva: I am not sure why this was written like this instead of using JAXB.
  /*
   * // this is the response to the initial request private void SendResponse
   * (ServletResponse response, ItemScoreResponse itemScoreResponse) throws
   * FailedReturnStatusException { response.setContentType ("text/xml");
   * OutputStream outputstream = null; try { outputstream =
   * response.getOutputStream (); XMLOutputFactory factory =
   * TdsXmlOutputFactory.newInstance (); XMLStreamWriter writer =
   * factory.createXMLStreamWriter (outputstream); itemScoreResponse.writeXML
   * (writer); } catch (IOException | XMLStreamException e) { logger.error
   * ("Failure writing xml to response", e); throw new
   * FailedReturnStatusException ("500 Internal Server Error"); } }
   */
  private void SendResponse (ServletResponse response, ItemScoreResponse itemScoreResponse) throws FailedReturnStatusException {
    response.setContentType ("text/xml");
    OutputStream outputstream = null;
    try {
      outputstream = response.getOutputStream ();

      itemScoreResponse.writeXML (outputstream);

    } catch (IOException | XMLStreamException e) {
      logger.error ("Failure writing xml to response", e);
      throw new FailedReturnStatusException ("500 Internal Server Error");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see AIR.Common.Web.HttpHandlerBase#onBeanFactoryInitialized()
   */
  @Override
  protected void onBeanFactoryInitialized () {
    // Nothing to do...
  }

}
