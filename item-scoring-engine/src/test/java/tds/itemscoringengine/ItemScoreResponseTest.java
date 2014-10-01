/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AIR.Common.xml.TdsXmlOutputFactory;
import AIR.Common.xml.XmlHelper;
import AIR.Common.xml.XmlReader;

/**
 * @author temp_rreddy
 * 
 */
public class ItemScoreResponseTest
{

  ItemScoreResponse           _itemScoreResponse = null;
  private static final Logger _logger            = LoggerFactory.getLogger (ItemScoreResponseTest.class);

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
    _itemScoreResponse = new ItemScoreResponse ();

    _itemScoreResponse.setScore (new ItemScore ());
    ItemScore score = _itemScoreResponse.getScore ();
    score.setScoreInfo (new ItemScoreInfo ());
    ItemScoreInfo scoreInfo = score.getScoreInfo ();

    // set "ScoreInfo" values.
    scoreInfo.setMaxScore (1);
    scoreInfo.setPoints (0);
    scoreInfo.setDimension ("overall");
    scoreInfo.setStatus (ScoringStatus.Scored);

    // set ScoreRationale in ScoreInfo
    ScoreRationale scoreRationale = new ScoreRationale ();
    scoreInfo.setRationale (scoreRationale);
    // first set the propositions
    scoreRationale.setMsg ("Your response earned a score of 0. Full credit requires: Correct");
    scoreRationale.setPropositions (new ArrayList<Proposition> ()
    {
      {
        // add one single proposition;
        add (new Proposition ()
        {
          {
            setName ("Full");
            setDesc ("The student correctly constructed the graph.");
            setState (PropositionState.NotAsserted);
          }
        });
      }
    });

    scoreRationale.setBindings (new ArrayList<VarBinding> ()
    {
      {
        add (new VarBinding ()
        {
          {
            setName ("AllLines");
            setType ("OBJECTSET");
            setValue ("ScoringEngine.GridObjectSet");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("Correct");
            setType ("OBJECTSET");
            setValue ("ScoringEngine.GridObjectSet");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("Alternate");
            setType ("OBJECTSET");
            setValue ("ScoringEngine.GridObjectSet");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("AllLinesCount");
            setType ("INT");
            setValue ("0");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("CorrectCount");
            setType ("INT");
            setValue ("0");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("CorrectFirstObject");
            setType ("OBJECT");
            setValue ("Object: null");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("CorrectSides");
            setType ("BOOL");
            setValue ("False");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("y1");
            setType ("INT");
            setValue ("-2147483648");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("y2");
            setType ("INT");
            setValue ("-2147483648");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("y3");
            setType ("INT");
            setValue ("-2147483648");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("y4");
            setType ("INT");
            setValue ("-2147483648");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("y5");
            setType ("INT");
            setValue ("-2147483648");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("CDRy");
            setType ("INT");
            setValue ("0");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("x4");
            setType ("INT");
            setValue ("-2147483648");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("x5");
            setType ("INT");
            setValue ("-2147483648");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("AlternateCount");
            setType ("INT");
            setValue ("0");
          }
        });

        add (new VarBinding ()
        {
          {
            setName ("AlternateFirstObject");
            setType ("OBJECT");
            setValue ("Object: null");
          }
        });
      }
    });

    score.setScoreLatency (0);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown () throws Exception {
    _itemScoreResponse = null;
  }

  @Test
  @Ignore
  /*
   * Ignore this test until the ReadXml() method is implemented
   */
  public void testRWItemScoreResponseXml () throws IOException, JDOMException, XMLStreamException {
    try {
      InputStream input = Thread.currentThread ().getContextClassLoader ().getResourceAsStream ("ItemScoreResponse.xml");
      XmlReader reader = new XmlReader (input);
      ItemScoreResponse reloadedItemScoreResponse = new ItemScoreResponse ();
      reloadedItemScoreResponse.readXML (reader);

      OutputStream out = new ByteArrayOutputStream ();
      XMLOutputFactory factory = TdsXmlOutputFactory.newInstance ();
      XMLStreamWriter writer = factory.createXMLStreamWriter (out);
      reloadedItemScoreResponse.writeXML (writer);

      // /////////////////////////////////////////
      Document doc = reader.getDocument ();

      Element itemScoreResponseElement = XmlHelper.getElement (doc, "ItemScoreResponse");
      Element scoreElement = itemScoreResponseElement.getChild ("Score");
      Element scoreInfoElement = scoreElement.getChild ("ScoreInfo");

      if (scoreInfoElement == null)
        return;
      // "scorePoint"
      int scorePoint = -1;
      if (scoreInfoElement.getAttributeValue ("scorePoint") != null) {
        scorePoint = Integer.parseInt (scoreInfoElement.getAttributeValue ("scorePoint"));
        _logger.info ("score Point value:: " + scorePoint);
      }

      // "scoreStatus"
      // ScoringStatus status = ScoringStatus.ScoringError;
      String statusName = "";
      if (scoreInfoElement.getAttributeValue ("scoreStatus") != null) {
        statusName = scoreInfoElement.getAttributeValue ("scoreStatus");
        _logger.info ("status Name value:: " + statusName);
      }

      // switch (statusName)
      // {
      // case "NotScored":
      // status = ScoringStatus.NotScored;
      // break;
      // case "Scored":
      // status = ScoringStatus.Scored;
      // break;
      // case "WaitingForMachineScore":
      // status = ScoringStatus.WaitingForMachineScore;
      // break;
      // case "NoScoringEngine":
      // status = ScoringStatus.NoScoringEngine;
      // break;
      // case "ScoringError":
      // status = ScoringStatus.ScoringError;
      // break;
      // }
      // "scoreDimension"
      String dimension = null;
      if (scoreInfoElement.getAttributeValue ("scoreDimension") != null) {
        dimension = scoreInfoElement.getAttributeValue ("scoreDimension");
        _logger.info ("dimension value:: " + dimension);
      }

      // <ScoreRationale>
      String rationale = null;
      Element scoreRationaleElement = scoreElement.getChild ("ScoreRationale");
      if (scoreRationaleElement != null) {
        rationale = scoreRationaleElement.getText ();
        _logger.info ("rationale value:: " + rationale);
      }
      // <SubScoreList>
      // Score[] childScores = new Score[0];

      // <ContextToken>
      String contextToken = null;
      Element contextTokenElement = scoreElement.getChild ("ContextToken");
      if (contextTokenElement != null) {
        contextToken = contextTokenElement.getText ();
        _logger.info ("contextToken value:: " + contextToken);
      }
      // /////////////////////////////////////////////////////////////

      InputStream inputStream = new ByteArrayInputStream (out.toString ().getBytes ());
      XmlReader read1 = new XmlReader (inputStream);
      Document doc1 = read1.getDocument ();

      Element childItemScoreResponseElement = XmlHelper.getElement (doc1, "ItemScoreResponse");
      Element childScoreElement = childItemScoreResponseElement.getChild ("Score");
      Element childScoreInfoElement = childScoreElement.getChild ("ScoreInfo");

      if (childScoreInfoElement == null)
        return;
      // "scorePoint"
      int childScorePoint = -1;
      if (childScoreInfoElement.getAttributeValue ("scorePoint") != null) {
        childScorePoint = Integer.parseInt (childScoreInfoElement.getAttributeValue ("scorePoint"));
        _logger.info ("score Point value:: " + scorePoint);
      }
      Assert.assertTrue (scorePoint == childScorePoint);

      // "scoreStatus"
      // ScoringStatus childStatus = ScoringStatus.ScoringError;
      String childStatusName = "";
      if (childScoreInfoElement.getAttributeValue ("scoreStatus") != null) {
        childStatusName = childScoreInfoElement.getAttributeValue ("scoreStatus");
        _logger.info ("status Name value:: " + childStatusName);
      }
      Assert.assertTrue (statusName.equalsIgnoreCase (childStatusName));

      // switch (statusName)
      // {
      // case "NotScored":
      // childStatus = ScoringStatus.NotScored;
      // break;
      // case "Scored":
      // childStatus = ScoringStatus.Scored;
      // break;
      // case "WaitingForMachineScore":
      // childStatus = ScoringStatus.WaitingForMachineScore;
      // break;
      // case "NoScoringEngine":
      // childStatus = ScoringStatus.NoScoringEngine;
      // break;
      // case "ScoringError":
      // childStatus = ScoringStatus.ScoringError;
      // break;
      // }
      // "scoreDimension"
      String childDimension = null;
      if (childScoreInfoElement.getAttributeValue ("scoreDimension") != null) {
        childDimension = childScoreInfoElement.getAttributeValue ("scoreDimension");
        _logger.info ("dimension value:: " + childDimension);
      }
      if (dimension != null && dimension.trim () != "")
        Assert.assertTrue (dimension.equalsIgnoreCase (childDimension));

      // <ScoreRationale>
      String childRationale = null;
      Element childScoreRationaleElement = childScoreElement.getChild ("ScoreRationale");
      if (childScoreRationaleElement != null) {
        childRationale = childScoreRationaleElement.getText ();
        _logger.info ("rationale value:: " + childRationale);
      }

      String childContextToken = null;
      Element childContextTokenElement = childScoreElement.getChild ("ContextToken");
      if (contextTokenElement != null) {
        childContextToken = childContextTokenElement.getText ();
        _logger.info ("contextToken value:: " + childContextToken);
      }

      Assert.assertTrue (contextToken.equalsIgnoreCase (childContextToken));

    } catch (IOException e) {
      _logger.error (e.getMessage ());
      throw e;
    } catch (JDOMException e) {
      _logger.error (e.getMessage ());
      throw e;
    }
  }

  public static void main (String argv[]) {
    try {
      ItemScoreResponseTest test = new ItemScoreResponseTest ();
      test.setUp ();

      XMLOutputFactory output = TdsXmlOutputFactory.newInstance ();
      XMLStreamWriter writer = output.createXMLStreamWriter (System.err);

      test._itemScoreResponse.writeXML (writer);
    } catch (Exception exp) {
      exp.printStackTrace ();
    }
  }
}
