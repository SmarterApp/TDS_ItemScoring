/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.itemscorers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.TransformerClosure;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.CustomOperatorRegistry;
import qtiscoringengine.DataElement;
import qtiscoringengine.Expression;
import qtiscoringengine.ICustomOperatorFactory;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.ValidationLog;
import qtiscoringengine.VariableBindings;
import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.IItemScorerCallback;
import tds.itemscoringengine.ItemScoreInfo;
import tds.itemscoringengine.ItemScorerManagerImpl;
import tds.itemscoringengine.Proposition;
import tds.itemscoringengine.PropositionState;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.RubricContentSource;
import tds.itemscoringengine.RubricContentType;
import tds.itemscoringengine.ScorerInfo;
import tds.itemscoringengine.ScoreRationale;
import tds.itemscoringengine.ScoringStatus;
import tds.itemscoringengine.VarBinding;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;
import AIR.Common.xml.XmlReader;
import AIR.Common.xml.XmlReaderException;

public class QTIItemScorer implements IItemScorer
{
  private static final Logger            _logger            = LoggerFactory.getLogger (QTIItemScorer.class);

  public QTIItemScorer () {

  }

  public QTIItemScorer (IItemScorer customOperatorItemScorer) {
    CustomOperatorRegistry.getInstance ().addOperatorFactory (new ISECustomOperator (customOperatorItemScorer));
  }

  @Override
  public ScorerInfo GetScorerInfo (String itemFormat) {
    return new ScorerInfo ("1.0", false, false, RubricContentSource.ItemXML);
  }

  @Override
  public ItemScore ScoreItem (ResponseInfo responseInfo, IItemScorerCallback callback) {
    final ItemScore score = new ItemScore (-1, -1, ScoringStatus.NotScored, "overall", new ScoreRationale (), responseInfo.getContextToken ()); // We
                                                                                                                                                // cannot
                                                                                                                                                // tell
                                                                                                                                                // what
                                                                                                                                                // the
                                                                                                                                                // Max
                                                                                                                                                // score
                                                                                                                                                // is

    // DateTime startTime = DateTime.Now;
    long startTime = System.currentTimeMillis ();
    Map<String, String> identifiersAndResponses = new HashMap<> ();
    // first try to retrieve the item response, and the identifier
    try {
      XmlReader reader = new XmlReader (new StringReader (responseInfo.getStudentResponse ()));
      Document doc = reader.getDocument ();
      List<Element> responseNodes = new XmlElement (doc.getRootElement ()).selectNodes ("//itemResponse/response");
      for (Element elem : responseNodes) {
        String identifier = elem.getAttributeValue ("id");
        List<String> responses = new ArrayList<String> ();
        List<Element> valueNodes = new XmlElement (elem).selectNodes ("value");
        for (Element valElem : valueNodes) {
          responses.add (valElem.getText ());
        }

        // if (!identifiersAndResponses.containsKey(identifier)) {
        identifiersAndResponses.put (identifier, StringUtils.join (responses, ','));
        // } else {
        // identifiersAndResponses.put (identifier, StringUtils.join(",",
        // responses));
        // }
      }
    } catch (final Exception e) {
      score.getScoreInfo ().setStatus (ScoringStatus.ScoringError);
      score.getScoreInfo ().getRationale ().setMsg ("Error loading response. Message: " + e.getMessage ());
      score.getScoreInfo ().getRationale ().setException (e);
      return score;
    }

    if (identifiersAndResponses.size () == 0) {
      score.getScoreInfo ().getRationale ().setMsg ("No responses found");
      return score;
    }

    try {
      // now go through and create the rubric and score the response
      XmlReader reader = getReader (responseInfo);
      ValidationLog log = new ValidationLog (responseInfo.getContentType ().toString ());
      QTIRubric rubric = QTIRubric.fromXML (responseInfo.getContentType ().toString (), reader, log);

      // check if there were any problems with the rubric
      if (rubric == null || !rubric.validate (log)) {
        score.getScoreInfo ().setStatus (ScoringStatus.ScoringError);

        StringBuilder rationaleString = new StringBuilder ();
        for (int i = 0; i < log.getCount (); i++)
          rationaleString.append (log.Message (i) + "\n");
        score.getScoreInfo ().getRationale ().setMsg (rationaleString.toString ());

        return score;
      }

      // now score the item
      String[] scoreArr = null;
      List<String[]> bindings = rubric.evaluate (identifiersAndResponses);
      for (String[] binding : bindings) {
        if ("score".equalsIgnoreCase (binding[0]))
          scoreArr = binding;
      }

      // error check the score result
      if (scoreArr == null) {
        score.getScoreInfo ().setStatus (ScoringStatus.ScoringError);
        score.getScoreInfo ().getRationale ().setMsg ("There was no SCORE identifier specified for the identifiers " + StringUtils.join (identifiersAndResponses.keySet (), ','));
        return score;
      }

      if (scoreArr.length < 4 || StringUtils.isEmpty (scoreArr[3])) {
        score.getScoreInfo ().setStatus (ScoringStatus.ScoringError);
        score.getScoreInfo ().getRationale ().setMsg ("There was no score specified for the identifiers " + StringUtils.join (identifiersAndResponses.keySet (), ','));
        return score;
      }

      // try to parse the actual score value -- note the score value is
      // hardcoded to be at index 3
      _Ref<Double> dScore = new _Ref<> ();
      if (JavaPrimitiveUtils.doubleTryParse (scoreArr[3], dScore)) {
        // note: we truncate the double score to be an int
        score.getScoreInfo ().setPoints ((int) Math.round (Math.max (dScore.get (), 0)));
        score.getScoreInfo ().setStatus (ScoringStatus.Scored);
        score.getScoreInfo ().getRationale ().setMsg ("successfully scored");

        // populate the requested outgoing bindings
        if (responseInfo.getOutgoingBindings () != null) {
          if (responseInfo.getOutgoingBindings ().contains (VarBinding.ALL)) // All
                                                                             // bindings
                                                                             // requested
          {
            responseInfo.setOutgoingBindings (new ArrayList<VarBinding> ());
            CollectionUtils.collect (bindings, new Transformer ()
            {

              @Override
              public Object transform (Object arg0) {
                final String[] variable = (String[]) arg0;
                return new VarBinding ()
                {
                  {
                    setName (variable[0]);
                    setType (variable[1]);
                    setValue (variable[3]);
                  }
                };
              }
            }, responseInfo.getOutgoingBindings ());

          } else // Specific bindings requested
          {
            score.getScoreInfo ().getRationale ().setBindings (new ArrayList<VarBinding> ());

            for (final VarBinding outgoingBinding : responseInfo.getOutgoingBindings ()) {

              final String[] binding = (String[]) CollectionUtils.find (bindings, new Predicate ()
              {

                @Override
                public boolean evaluate (Object arg0) {
                  return StringUtils.equals (((String[]) arg0)[0], outgoingBinding.getName ());
                }
              });

              if (binding != null)
                score.getScoreInfo ().getRationale ().getBindings ().add (new VarBinding ()
                {
                  {
                    setName (binding[0]);
                    setType (binding[1]);
                    setValue (binding[3]);
                  }
                });

            }
          }
        }

        // populate any subscores that might have been recorded as internal
        // scoring state
        for (Object stateObj : rubric.getResponseProcessingState ()) {
          ItemScore subScore = (ItemScore) stateObj;
          if (subScore != null) {
            if (score.getScoreInfo ().getSubScores () == null)
              score.getScoreInfo ().setSubScores (new ArrayList<ItemScoreInfo> ());

            // remove any bindings from the subscore that is not requested as an
            // outgoing binding
            if (responseInfo.getOutgoingBindings () == null) {
              // No bindings requested - clear everything
              // TODO: Recursively go through the subscore's children to clear
              // those bindings also.
              subScore.getScoreInfo ().getRationale ().getBindings ().clear ();
            } else if (responseInfo.getOutgoingBindings ().contains (VarBinding.ALL)) {
              // All bindings requested
              // don't remove anything. let them all through
            } else {
              // specific bindings requested. Remove anything not explicitly
              // asked for.
              // TODO: Recursively go through the subscore's children to filter
              // them also
              VarBinding[] subScoreBindings = subScore.getScoreInfo ().getRationale ().getBindings ().toArray (new VarBinding[subScore.getScoreInfo ().getRationale ().getBindings ().size ()]);

              for (int counter1 = 0; counter1 < subScoreBindings.length; ++counter1) {
                final VarBinding subScoreBinding = subScoreBindings[counter1];
                if (!CollectionUtils.exists (responseInfo.getOutgoingBindings (), new Predicate ()
                {

                  @Override
                  public boolean evaluate (Object arg0) {
                    return StringUtils.equals (((VarBinding) arg0).getName (), subScoreBinding.getName ());
                  }
                })) {
                  subScore.getScoreInfo ().getRationale ().getBindings ().remove (counter1);
                  --counter1;
                }
              }

            }
            score.getScoreInfo ().getSubScores ().add (subScore.getScoreInfo ());
          }
        }

        // note: ScoreLatency is miliseconds
        score.setScoreLatency (System.currentTimeMillis () - startTime);
        return score;

      } else {
        score.getScoreInfo ().setStatus (ScoringStatus.ScoringError);
        score.getScoreInfo ().getRationale ().setMsg ("Error changing score to int, score array values='" + StringUtils.join (scoreArr, ',') + "'.");
        return score;
      }
      // reader.close()
    } catch (final Exception e) {
      e.printStackTrace ();
      score.getScoreInfo ().setPoints (-1);
      score.getScoreInfo ().setStatus (ScoringStatus.ScoringError);
      score.getScoreInfo ().getRationale ().setMsg ("Error processing rubric. Message: " + e.getMessage ());
      score.getScoreInfo ().getRationale ().setException (e);
      return score;
    }
  }

  private XmlReader getReader (ResponseInfo ri) throws JDOMException, IOException, XmlReaderException, URISyntaxException {
    try {
      if (ri.getContentType () == RubricContentType.Uri) {
        // rubric is a Uri
        // return
        // XmlReader.create(ItemScorerUtil.GetContentStream((Uri)ri.Rubric), new
        // XmlReaderSettings(){CloseInput = true});
        return XmlReader.create (new URI (ri.getRubric ().toString ()));
      } else {// rubric is a string
        return new XmlReader (new StringReader (ri.getRubric ().toString ()));
      }
    } catch (XmlReaderException xe) {
      String theStr = "<null>";
      if (ri.getRubric () != null)
         theStr = ri.getRubric ().toString ();
      _logger.error (String.format("Exception: %s, input string: '%s'", xe.getMessage(), theStr),xe);
      throw xe;
    }
  }

  @Override
  public void shutdown () {
  }
  
  public static void main (String[] args) {
    try {
      // String response =
      // "<itemResponse><response id=\"1\"><value>1</value></response></itemResponse>";
      // URI rubricUri = new URI
      // ("file:///C:/tmp/Bank-187/Items/Item-187-2620/Item_2620_v4.qrx");
      // ResponseInfo responseInfo = new ResponseInfo ("htq", "2620", response,
      // rubricUri, RubricContentType.Uri, "abc", false);

      String response = "<itemResponse><response id=\"1\"><value>4</value><value>1</value><value>2</value><value>5</value><value>3</value></response></itemResponse>";
      URI rubricUri = new URI ("file:///C:/tmp/Bank-187/Items/Item-187-2564/Item_2564_v1.qrx");
      ResponseInfo responseInfo = new ResponseInfo ("htq", "2564", response, rubricUri, RubricContentType.Uri, "abc", false);

      QTIItemScorer qtiScorer = new QTIItemScorer ();
      ItemScore score = qtiScorer.ScoreItem (responseInfo, null);
      
      StringWriter strnWriter = new StringWriter ();
      JAXBContext jaxbContext = JAXBContext.newInstance (ItemScore.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller ();
      // output pretty printed
      jaxbMarshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
      jaxbMarshaller.marshal(score, strnWriter);
      
      System.err.println (strnWriter.toString ());
    } catch (Exception exp) {
      exp.printStackTrace ();
    }
  }

}

class ISECustomOperator implements ICustomOperatorFactory
{
  private IItemScorer _externalScorer;

  public ISECustomOperator (IItemScorer externalScorer) {
    _externalScorer = externalScorer;
  }

  @Override
  public boolean supportsOperator (Element customOperatorNode) {
    return true;
  }

  @Override
  public Expression createExpression (Element customOperatorNode) {
    return new ISECustomExpression (customOperatorNode, _externalScorer);
  }
}

class ISECustomExpression extends Expression
{
  private IItemScorer  _itemScorer         = null;
  private String       _itemFormat         = null;
  private String       _rubric             = null;
  private String       _responseIdentifier = null;
  private List<String> incomingIdentifiers = new ArrayList<String> ();
  private List<String> outgoingIdentifiers = new ArrayList<String> ();

  public ISECustomExpression (Element nodeInput, IItemScorer externalScorer)

  {
    super (nodeInput, 0, Integer.MAX_VALUE, BaseType.Boolean, Cardinality.Single);

    XmlNamespaceManager nsMgr = new XmlNamespaceManager ();
    nsMgr.addNamespace ("air", "http://www.air.org.org/");

    XmlElement node = new XmlElement (nodeInput);

    _itemScorer = externalScorer;
    _itemFormat = getOperatorType (nodeInput);
    _rubric = new XmlElement (node.selectSingleNode ("air:rubric", nsMgr)).getInnerXml ();
    _responseIdentifier = node.selectSingleNode ("air:responseComponent", nsMgr).getAttribute ("identifier").getValue ();
    for (Element incomingBinding : node.selectNodes ("air:bindInput", nsMgr)) {
      incomingIdentifiers.add (incomingBinding.getAttribute ("identifier").getValue ());
    }
    for (Element outgoingBinding : node.selectNodes ("air:bindOutput", nsMgr)) {
      outgoingIdentifiers.add (outgoingBinding.getAttribute ("identifier").getValue ());
    }
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    String response = vb.getVariable (_responseIdentifier).toString ();

    ResponseInfo rinfo = new ResponseInfo (_itemFormat, "", response, _rubric, RubricContentType.ContentString, null, false)
    {
      {
        setOutgoingBindings (new ArrayList<VarBinding> ()
        {
          {
            add (VarBinding.ALL);
          }
        }); // We alwyas ask for ALL from expressions and leave it to the QTI
            // executive to decide which ones to expose to the caller
      }
    };

    ItemScore score = _itemScorer.ScoreItem (rinfo, null);

    rubric.getResponseProcessingState ().add (score);

    if (score.getScoreInfo ().getRationale ().getBindings () != null) {
      for (VarBinding binding : score.getScoreInfo ().getRationale ().getBindings ()) {
        if (vb.getVariable (binding.getName ()) != null) // this is added so
                                                         // that only variables
                                                         // declared in the QTI
                                                         // rubric will be bound
                                                         // and we can exclude
                                                         // all the internal
                                                         // native scoring
                                                         // engine bindings
        {
          vb.setVariable (binding.getName (), DataElement.create (binding.getValue (), rubric.getOutcomeVariableBaseType (binding.getName ())));
        }
      }
    }
    if (score.getScoreInfo ().getRationale ().getPropositions () != null) {
      for (Proposition proposition : score.getScoreInfo ().getRationale ().getPropositions ()) {
        if (vb.getVariable (proposition.getName ()) != null) // this is added so
                                                             // that only
                                                             // variables
                                                             // declared in the
                                                             // QTI rubric will
                                                             // be bound and we
                                                             // can exclude all
                                                             // the internal
                                                             // native scoring
                                                             // engine
                                                             // propositions
        {
          vb.setVariable (proposition.getName (), DataElement.create (new Boolean ((proposition.getState () == PropositionState.Asserted)).toString (), BaseType.Boolean));
        }
      }
    }

    return score.getScoreInfo ().getStatus () == ScoringStatus.Scored ? DataElement.create ("true", BaseType.Boolean) : DataElement.create ("false", BaseType.Boolean);
  }

  private static String getOperatorType (Element customOperatorNode) {
    for (Attribute attribute : customOperatorNode.getAttributes ()) {
      if ("CLASS".equals (attribute.getName ().toUpperCase ())) {
        return attribute.getValue ();
      }
    }
    return "";
  }
}
