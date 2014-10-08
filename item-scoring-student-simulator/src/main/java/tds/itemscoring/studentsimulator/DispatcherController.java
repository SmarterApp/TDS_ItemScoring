/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoring.studentsimulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import tds.itemscoringengine.ItemScoreResponse;

@Controller
public class DispatcherController
{
  @RequestMapping (value = "rubric/{rubricFile}", method = RequestMethod.GET, produces = "text/xml")
  @ResponseBody
  public String fetchRubric (@PathVariable String rubricFile, HttpServletResponse response) throws FileNotFoundException {
    ClassLoader cl = getClass ().getClassLoader ();
    URL rubricUrl = cl.getResource ("rubrics/" + rubricFile + ".xml");
    String rubricPath = (rubricUrl == null ? "FileNotFound" : rubricUrl.getPath ());
    return readWholeFile (rubricPath);
  }

  @RequestMapping (value = "scoreResponse", method = RequestMethod.POST, consumes = "text/xml")
  @ResponseStatus (value = HttpStatus.OK)
  public void scoreResponse (@RequestBody String xml) {
    ItemScoreResponse isr = null;

    try {
      JAXBContext context = JAXBContext.newInstance (ItemScoreResponse.class);
      Unmarshaller um = context.createUnmarshaller ();
      isr = (ItemScoreResponse) um.unmarshal (new StringReader (xml));
    } catch (JAXBException e) {
      isr = null;
      e.printStackTrace ();
    }

    if (isr == null) {
      /* Deserialize failed. */
      return;
    }

    ClassLoader cl = getClass ().getClassLoader ();
    URL resourceFolder = cl.getResource ("response/");
    String contextToken = isr.getScore ().getContextToken ().trim ();
    //System.out.println (contextToken);

    try {
      JAXBContext context = JAXBContext.newInstance (ItemScoreResponse.class);
      String filePath = String.format ("%s/%s.xml", resourceFolder.getPath ().trim (), contextToken);
      Marshaller m = context.createMarshaller ();
      m.marshal (isr, new File (filePath));
    } catch (JAXBException e) {
      e.printStackTrace ();
    }
  }

  @RequestMapping (value = "fetchResponse", method = RequestMethod.GET)
  public void fetchResponse (@RequestParam ("ct") String contextToken, HttpServletResponse response) {
    ClassLoader cl = getClass ().getClassLoader ();
    URL resourceFolder = cl.getResource ("response/");
    String filePath = String.format ("%s/%s.xml", resourceFolder.getPath ().trim (), contextToken);

    try {
      String content = readWholeFile (filePath);
      PrintWriter out = response.getWriter();
      out.print(content);
      response.setStatus (200);
    } catch (FileNotFoundException e) {
      response.setStatus (404);
    } catch (IOException e) {
      e.printStackTrace ();
    }
  }

  private String readWholeFile (String filePath) throws FileNotFoundException {
    StringBuilder sb = new StringBuilder ();
    String line;

    try (BufferedReader reader = new BufferedReader (new FileReader (filePath))) {
      while ((line = reader.readLine ()) != null) {
        sb.append (line);
      }
      reader.close ();
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IOException e) {
      e.printStackTrace ();
    }

    return sb.toString ();
  }
}
