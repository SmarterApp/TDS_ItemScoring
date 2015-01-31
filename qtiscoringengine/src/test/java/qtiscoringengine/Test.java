/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.List;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math.util.MathUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.XMLOutputter;

import AIR.Common.xml.XmlReader;

public class Test
{

  public static void main (String[] args) {

    try {
      URI uri = new URI ("http://38.118.82.183:8080/abc");
      System.err.println(uri.toString ());

    } catch (Exception e) {
      e.printStackTrace ();
    }
  }

  public static void mainz (String[] args) {

    XmlReader reader;
    try {
      reader = new XmlReader (new StringReader ("<a><b>x</b></a>"));
      Document doc = reader.getDocument ();
      Element a = doc.getRootElement ();

      XMLOutputter output = new XMLOutputter ();

      StringWriter strn = new StringWriter ();
      output.outputElementContent (a, strn);

      System.err.println (strn.toString ());
    } catch (IOException | JDOMException e) {
      e.printStackTrace ();
    }
  }

  public static void mainy (String[] args) {
    System.err.println ("a".compareTo (new String ("a")));
  }

  public static void mainm (String[] args) {
    String rationale = "";
    try {
      String source = "file:///C:/tmp/Bank-187/Items/Item-187-2564/Item_2564_v1Copy.xml";
      // String source =
      // "file:///C:/WorkSpace/QTIScoringEngine/QTISpec/qtiv2p1pd2/examples/items/choice_fixed.xml";
      XmlReader reader = XmlReader.create (new URI (source));

      ValidationLog log = new ValidationLog ("htq");
      QTIRubric rubric = QTIRubric.fromXML ("htq", reader, log);

      // check if there were any problems with the rubric
      if (rubric == null || !rubric.validate (log)) {
        for (int i = 0; i < log.getCount (); i++)
          rationale += log.Message (i) + "\n";
        throw new Exception ("problem with validation.");
      }

      List<String[]> score = rubric.evaluate (new HashMap<String, String> ()
      {
        {
          this.put ("1", "4,1,2,5,3");
        }
      });

      for (String[] a : score) {
        System.err.println (StringUtils.join (a, ','));
      }

      System.err.println ("");
    } catch (Exception exp) {
      System.err.println (rationale);
      exp.printStackTrace ();
    }
    System.err.println ("Everything is fine");
  }
}
