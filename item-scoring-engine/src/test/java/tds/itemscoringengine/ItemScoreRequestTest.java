/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import AIR.Common.xml.TdsXmlOutputFactory;
import AIR.Common.xml.XmlHelper;
import AIR.Common.xml.XmlReader;

/**
 * @author temp_rreddy
 * 
 */
public class ItemScoreRequestTest
{
  ItemScoreRequest            itemScoreRequest = null;
  private static final Logger _logger          = LoggerFactory.getLogger (ItemScoreRequestTest.class);

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass () throws Exception {

  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass () throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp () throws Exception {
    itemScoreRequest = new ItemScoreRequest ();
    try (InputStream input = Thread.currentThread ().getContextClassLoader ().getResourceAsStream ("ScoringRequestBindings.xml")) {
      XmlReader reader = null;
      reader = new XmlReader (input);
      itemScoreRequest.readXML (reader);
    }
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown () throws Exception {
    itemScoreRequest = null;
  }

  @Test
  public void testItemScoreRead () {
    ResponseInfo r = itemScoreRequest.getResponseInfo ();

    Assert.assertTrue (r.getOutgoingBindings ().size () == 1);
    
    Assert.assertTrue("Student Response".equals(r.getStudentResponse ()));
    
    Assert.assertTrue ("Rubric Path".equals(r.getRubric ().toString ()));
    
  }
}
