package tds.itemscoringengine.itemscorers;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qtiscoringengine.QTIRubric;
import qtiscoringengine.ValidationLog;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlReader;
import AIR.Common.xml.XmlReaderException;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ItemScoreInfo;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.RubricContentType;
import tds.itemscoringengine.RubricStats;
import tds.itemscoringengine.ScoreRationale;
import tds.itemscoringengine.ScoringStatus;
import tds.itemscoringengine.VarBinding;
import tds.itemscoringengine.complexitemscorers.IRubric;
import tds.itemscoringengine.complexitemscorers.IRubricStats;

public class QTIRubricScorer implements IRubric
{
  private static final Logger _logger               = LoggerFactory.getLogger (QTIRubricScorer.class);
  private String              itemId;
  private IRubricStats        stats                 = new RubricStats ();
  private QTIRubric           _rubric               = null;

  private String              _invalidRubricMessage = null;
  private Exception           _exception            = null;

  public QTIRubricScorer (String itemId) {
    this.itemId = itemId;
  }

  @Override
  public String getType () {
    // TODO Auto-generated method stub
    return null;
  }

  public String getItemId () {
    return this.itemId;
  }

  public IRubricStats getStats () {
    return stats;
  }

  public boolean wasThereErrorWithRubric () {
    if (StringUtils.isEmpty (_invalidRubricMessage) && _exception == null)
      return false;
    return true;
  }

  public String getErrorMessageIfAny () {
    return _invalidRubricMessage;
  }

  public void load (RubricContentType type, Object rubric) {
    try {
      // now go through and create the rubric and score the response
      XmlReader reader = getReader (type, rubric);
      ValidationLog log = new ValidationLog (type.toString ());
      _rubric = QTIRubric.fromXML (type.toString (), reader, log);

      // check if there were any problems with the rubric
      if (_rubric == null || !_rubric.validate (log)) {

        StringBuilder rationaleString = new StringBuilder ();
        for (int i = 0; i < log.getCount (); i++)
          rationaleString.append (log.Message (i) + "\n");
        _invalidRubricMessage = rationaleString.toString ();

        _logger.error (String.format ("Error validating rubric. File %s. Message %s", rubric.toString (), rationaleString.toString ()));
      }
    } catch (Exception e) {
      _exception = e;
      e.printStackTrace ();
      _logger.error ("Error : " + e.getMessage ());
      _invalidRubricMessage = _invalidRubricMessage + "\n" + "Error processing rubric. Message: " + e.getMessage ();
    }
  }

  public ItemScore scoreItem (ResponseInfo responseInfo) {
    ItemScore score = new ItemScore (-1, -1, ScoringStatus.NotScored, "overall", new ScoreRationale (), responseInfo.getContextToken ()); // We
    // Shiva: I added the follow null check on the response as it makes it
    // easier to test it on the web
    // interface with a REST client.
    String response = responseInfo.getStudentResponse ();
    if (response != null) {
      response = response.trim ();
      responseInfo.setStudentResponse (response);
    }

    // Max
    // score
    // is
    long startTime = System.currentTimeMillis ();
    Map<String, String> identifiersAndResponses = new HashMap<String, String> ();
    // first try to retrieve the item response, and the identifier
    try {
      XmlReader reader = new XmlReader (new StringReader (responseInfo.getStudentResponse ()));
      Document doc = reader.getDocument ();

      List<Element> responseNodes = new XmlElement (doc.getRootElement ()).selectNodes ("//itemResponse/response");
      for (Element elem : responseNodes) {
        String identifier = elem.getAttribute ("id").getValue ();
        List<String> responses = new ArrayList<String> ();
        List<Element> valueNodes = new XmlElement (elem).selectNodes ("value");
        for (Element valElem : valueNodes) {
          responses.add (new XmlElement (valElem).getInnerText ());
        }

        identifiersAndResponses.put (identifier, StringUtils.join (responses, ","));
      }

    } catch (final Exception e) {
      e.printStackTrace ();
      _logger.error ("Error loading responses " + e.getMessage ());
      score.getScoreInfo ().setStatus (ScoringStatus.ScoringError);
      score.getScoreInfo ().getRationale ().setMsg ("Error loading response. Message: " + e.getMessage ());
      score.getScoreInfo ().getRationale ().setException (e);
      return score;
    }

    if (identifiersAndResponses.size () == 0) {
      // This could be a response from a grid/ti/eq item being scored using a
      // QTI version of our proprietary rubrics
      // Check if this is a TI/GI/SIM/EQ and unfortunately, there is no clean
      // way to figure this out other than resorting to this hack
      response = responseInfo.getStudentResponse ();

      if (!StringUtils.isEmpty (response) && ((response.toUpperCase ().startsWith ("<RESPONSESPEC>") || response.toUpperCase ().startsWith ("<RESPONSETABLE>")) || // TI
          (response.toUpperCase ().startsWith ("<RESPONSE>")) || // EQ
          (response.toUpperCase ().contains ("<ANSWERSET>"))) // GI
      ) {
        identifiersAndResponses.put ("RESPONSE", responseInfo.getStudentResponse ());
      } else {
        _logger.error ("No responses found");
        score.getScoreInfo ().getRationale ().setMsg ("No responses found");
        return score;
      }
    }

    try {
      if (wasThereErrorWithRubric ()) {
        _logger.error (String.format ("Error validating rubric. File %s. Message %s", responseInfo.getRubric ().toString (), getErrorMessageIfAny ()));
        score.getScoreInfo ().getRationale ().setMsg (getErrorMessageIfAny ());
        return score;
      }

      // now score the item
      String[] scoreArr = null;
      List<String[]> bindings = _rubric.evaluate (identifiersAndResponses);
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
        score.getScoreInfo ().setPoints ((int) Math.ceil (Math.max (dScore.get (), 0)));
        score.getScoreInfo ().setStatus (ScoringStatus.Scored);
        score.getScoreInfo ().getRationale ().setMsg ("successfully scored");

        // populate the requested outgoing bindings
        if (responseInfo.getOutgoingBindings () != null) {
          if (responseInfo.getOutgoingBindings ().contains (VarBinding.ALL)) // All
                                                                             // bindings
                                                                             // requested
          {
            score.getScoreInfo ().getRationale ().setBindings (new ArrayList<VarBinding> ());

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
            }, score.getScoreInfo ().getRationale ().getBindings ());

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
        for (Object stateObj : _rubric.getResponseProcessingState ()) {
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

    } catch (final Exception e) {
      e.printStackTrace ();
      _logger.error ("Error : " + e.getMessage ());
      score.getScoreInfo ().setPoints (-1);
      score.getScoreInfo ().setStatus (ScoringStatus.ScoringError);
      score.getScoreInfo ().getRationale ().setMsg ("Error processing rubric. Message: " + e.getMessage ());
      score.getScoreInfo ().getRationale ().setException (e);
      return score;
    }
  }

  // / <summary>
  // / get an XmlReader object for the rubric
  // / </summary>
  // / <param name="ri"></param>
  // / <returns></returns>
  private XmlReader getReader (RubricContentType rubricContentType, Object rubricObject) throws JDOMException, IOException, XmlReaderException, URISyntaxException {
    try {
      if (rubricContentType == RubricContentType.Uri) {
        return XmlReader.create (new URI (rubricObject.toString ()));
      } else {// rubric is a string
        return new XmlReader (new StringReader (rubricObject.toString ()));
      }
    } catch (XmlReaderException xe) {
      String theStr = "<null>";
      if (rubricObject != null)
        theStr = rubricObject.toString ();
      _logger.error (String.format ("Exception: %s, input string: '%s'", xe.getMessage (), theStr), xe);
      throw xe;
    }
  }

}
