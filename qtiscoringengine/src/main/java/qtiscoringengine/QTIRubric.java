/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;

import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;
import AIR.Common.xml.XmlReader;

public class QTIRubric
{
  private List<ResponseDeclaration> _responseDeclarations = null;
  private List<OutcomeDeclaration>  _outcomeDeclarations  = null;
  private ResponseProcessing        _responseProcessor    = null;
  private String                    _source               = "";

  private Set<Object>               _responseProcessingState;

  public Set<Object> getResponseProcessingState () {
    return _responseProcessingState;
  }

  @SuppressWarnings ("unchecked")
  public List<String> getOutcomeVariables () {
    return (List<String>) CollectionUtils.collect (_outcomeDeclarations, new Transformer ()
    {
      @Override
      public Object transform (Object arg0) {
        return ((OutcomeDeclaration) arg0).getIdentifier ();
      }
    }, new ArrayList<String> ());
  }

  private QTIRubric (String source, List<ResponseDeclaration> rdList, List<OutcomeDeclaration> odList, ResponseProcessing responseProcessor) {
    _source = source;
    _responseDeclarations = rdList;
    _outcomeDeclarations = odList;
    _responseProcessor = responseProcessor;
    _responseProcessingState = new HashSet<Object> ();

    _responseDeclarations.addAll (ResponseDeclaration.getBuiltInResponseDeclarations ());
    _outcomeDeclarations.addAll (OutcomeDeclaration.getBuiltInOutcomeDeclarations ());
  }

  public static QTIRubric fromXML (String source, XmlReader reader, ValidationLog log) {
    Document doc = null;
    try {
      doc = reader.getDocument ();
    } catch (Exception e) {
      log.addMessage (null, "Failed to load document. Message: " + e.getMessage ());
      return null;
    }
    // this should never be null at this point, but just in case i guess...
    if (doc == null) {
      log.addMessage (null, "Failed to load document. The XmlDocument is null. Sorry for lack of diagnostics.");
      return null;
    }

    Element foo = doc.getRootElement ();
    if (foo == null) {
      log.addMessage (null, "Failed to load document. There is no DocumentElement.");
      return null;
    }
    // Create an XmlNamespaceManager for resolving namespaces.
    XmlNamespaceManager nsmgr = new XmlNamespaceManager (/* doc.NameTable */);
    boolean foundNamespace = false;
    // add a default value for the namespace
    nsmgr.addNamespace ("qti", QTIXmlConstants.NameSpaces[0]);
    // String xmlns = foo.getAttributeValue ("xmlns");
    String xmlns = foo.getNamespace ().getURI ();
    // if (QTIXmlConstants.NameSpaces.Contains(xmlns))
    if (QTIXmlConstants.containsSchemaLocation (xmlns)) {/* contains */
      nsmgr.addNamespace ("qti", xmlns);
      foundNamespace = true;
    } else {
      // XmlNodeList namespaceNodes = foo.SelectNodes("//*[@xmlns]");
      List<Element> namespaceNodes = new XmlElement (foo).selectNodes ("//*[@xmlns]");
      for (Element elem : namespaceNodes)
        // if
        // (QTIXmlConstants.NameSpaces.Contains(elem.getAttributeValue("xmlns")))
        if (QTIXmlConstants.containsSchemaLocation (elem.getAttributeValue ("xmlns"))) {/* contains */
          nsmgr.addNamespace ("qti", elem.getAttributeValue ("xmlns"));
          foundNamespace = true;
          break;
        }
    }

    List<Element> responseDeclarations = new XmlElement (foo).selectNodes (QTIXmlConstants.ResponseDeclaration, nsmgr);
    List<Element> outcomeDeclarations = new XmlElement (foo).selectNodes (QTIXmlConstants.OutcomeDeclaration, nsmgr);

    Element responseProcessing = new XmlElement (foo).selectSingleNode (QTIXmlConstants.ResponseProcessing, nsmgr);
    if (responseProcessing != null) {
      String url = responseProcessing.getAttributeValue ("template");
      if (!StringUtils.isEmpty (url)) {
        try {
          responseProcessing = QTIUtility.getXMLFromURL (url, nsmgr);
        } catch (Exception e) {
          // responseProcessing can be the root node, so to keep us from
          // printing the entire
          // xml file we just add the error message without a node
          log.addMessage (null, "Error getting responseProcessing node. Url='" + url + "', message: " + e.getMessage ());
        }
      }
    }

    List<ResponseDeclaration> rdList = new ArrayList<ResponseDeclaration> ();
    for (Element node : responseDeclarations) {
      try {
        ResponseDeclaration rd = ResponseDeclaration.fromXML (node, nsmgr, log);
        if (rd != null)
          rdList.add (rd);
      } catch (Exception e) {
        log.addMessage (node, e.getMessage ());
      }
    }

    List<OutcomeDeclaration> odList = new ArrayList<OutcomeDeclaration> ();
    for (Element node : outcomeDeclarations) {
      try {
        OutcomeDeclaration od = OutcomeDeclaration.fromXML (node, nsmgr, log);
        if (od != null)
          odList.add (od);
      } catch (Exception e) {
        log.addMessage (node, e.getMessage ());
      }
    }

    ResponseProcessing responseProcessor = null;
    try {
      responseProcessor = ResponseProcessing.fromXml (responseProcessing, nsmgr, log);
    } catch (Exception e) {
      log.addMessage (responseProcessing, e.getMessage ());
    }

    // if the rubric is empty then it might be because the namespace wasn't
    // recognized
    // as an allowed value. Let the user know.
    if (rdList.size () == 0 && odList.size () == 0 && responseProcessor == null && !foundNamespace)
      log.addMessage (null, "No valid namespace was found in this XML. Maybe you need to add it to the allowed values in QTIXmlConstants?");

    QTIRubric rubric = new QTIRubric (source, rdList, odList, responseProcessor);
    return rubric;
  }

  public boolean validate (ValidationLog log) {
    boolean ok = true;
    if (_responseProcessor == null) {
      log.addMessage (null, "Response processing node missing or invalid. No rubric to validate");
      ok = false;
    } else
      ok = _responseProcessor.validate (this, log);

    for (ResponseDeclaration rd : _responseDeclarations) {
      if (!rd.validate (this, log))
        ok = false;
    }

    // If the Rigor is set to None always return true. Otherwise return the
    // result of the validation
    switch (log.getValidationRigor ()) {
    case None:
      return true;
    default:
      return ok;
    }
  }

  boolean declaresOutcomeVariable (String identifier) {
    if (identifier == null)
      return false;
    for (OutcomeDeclaration od : _outcomeDeclarations) {
      if (od.getIdentifier ().compareTo (identifier.trim ()) == 0)
        return true;
    }
    return false;
  }

  public BaseType getOutcomeVariableBaseType (String identifier) {
    if (identifier == null)
      return BaseType.Null;
    for (OutcomeDeclaration od : _outcomeDeclarations) {
      if (od.getIdentifier ().compareTo (identifier.trim ()) == 0)
        return od.getType ();
    }
    return BaseType.Null;
  }

  public ResponseDeclaration getResponseDeclaration (String identifier) {
    if (identifier == null)
      return null;
    for (ResponseDeclaration rd : _responseDeclarations) {
      if (rd.getIdentifier ().compareTo (identifier.trim ()) == 0)
        return rd;
    }
    return null;
  }

  OutcomeDeclaration getOutcomeDeclaration (String identifier) {
    if (identifier == null)
      return null;
    for (OutcomeDeclaration od : _outcomeDeclarations) {
      if (od.getIdentifier ().compareTo (identifier.trim ()) == 0)
        return od;
    }
    return null;
  }

  VariableDeclaration getVariableDeclaration (String identifier) {
    if (identifier == null)
      return null;
    ResponseDeclaration rd = getResponseDeclaration (identifier);
    if (rd == null) {
      return getOutcomeDeclaration (identifier);
    } else
      return rd;
  }

  List<ResponseDeclaration> getResponseDeclarations () {
    return _responseDeclarations;
  }

  DataElement getDefaultValue (DEIdentifier identifier) {
    if (identifier == null)
      return null;
    VariableDeclaration vd = getVariableDeclaration (identifier.getValue ());
    if (vd != null)
      return vd.getDefaultValue ();
    return null;
  }

  public String getDefaultValue (String identifier) {
    if (identifier == null)
      return null;
    VariableDeclaration vd = getVariableDeclaration (identifier);
    DataElement de = null;
    if (vd != null)
      de = vd.getDefaultValue ();
    return de == null ? null : de.getStringValue ();
  }

  public String getResponseDeclarationMappingDefaultValue (String identifier) {
    if (identifier == null)
      return null;
    ResponseDeclaration rd = getResponseDeclaration (identifier);
    DataElement de = null;
    if (rd == null)
      return null;
    if (rd.getMapping () != null)
      de = rd.getMapping ().getDefaultValue ();
    return de == null ? null : de.getStringValue ();
  }

  DataElement getCorrectValue (DEIdentifier identifier) {
    if (identifier == null)
      return null;
    ResponseDeclaration rd = getResponseDeclaration (identifier.getValue ());
    return rd.getCorrectValue ();
  }

  // / <summary>
  // / TODO: this does not yet support getting the correct value from a
  // responseProcessing node
  // / </summary>
  // / <param name="identifier"></param>
  // / <returns></returns>
  public String getCorrectValue (String identifier) {
    if (identifier == null)
      return null;
    ResponseDeclaration rd = getResponseDeclaration (identifier);
    if (rd == null)
      return null;
    DataElement de = rd.getCorrectValue ();
    if (de == null)
      return null;
    if (de.getIsContainer ()) {
      DEContainer dc = (DEContainer) de;
      if (dc.getMemberCount () > 0) {
        if (rd.getCardinality () == Cardinality.Ordered) {
          return dc.getStringValue ();
        }
        double maxScore = Double.MIN_VALUE;
        DataElement elem = null;
        double sum = 0;
        for (int i = 0; i < dc.getMemberCount (); i++) {
          // if (i == 0) elem = dc.Member(i); // this is so we always get a
          // value. Setting default value to the first one
          Double d = rd.getScore (dc.getMember (i));
          if (d != null) {
            if (d > maxScore) {
              maxScore = (double) d;
              elem = dc.getMember (i);
            }
            sum += (double) d;
          }
        }
        if (sum > maxScore)
          return dc.getStringValue ();
        return elem == null ? null : elem.getStringValue ();
      } else
        return null;
    }
    return de.getStringValue ();
  }

  public String getOneCorrectValue (String identifier) {
    if (identifier == null)
      return null;
    ResponseDeclaration rd = getResponseDeclaration (identifier);
    if (rd == null)
      return null;
    DataElement de = rd.getCorrectValue ();
    if (de == null)
      return null;
    if (de.getIsContainer ()) {
      DEContainer dc = (DEContainer) de;
      if (dc.getMemberCount () > 0) {
        if (rd.getCardinality () == Cardinality.Ordered) {
          return dc.getStringValue ();
        }
        double maxScore = Double.MIN_VALUE;
        DataElement elem = null;
        for (int i = 0; i < dc.getMemberCount (); i++) {
          if (i == 0)
            elem = dc.getMember (i); // this is so we always get a value.
                                     // Setting default value to the first one
          Double d = rd.getScore (dc.getMember (i));
          if (d != null)
            if (d > maxScore) {
              maxScore = (double) d;
              elem = dc.getMember (i);
            }
        }
        return elem == null ? null : elem.getStringValue ();
      } else
        return null;
    }
    return de.getStringValue ();
  }

  private VariableBindings getVariableBindingsFromResponse (Response response) throws Exception {
    VariableBindings vb = new VariableBindings ();
    if (response == null)
      return vb;
    bindResponseVariables (response);
    transferValues (vb);
    response.transferBindings (vb);
    return vb;
  }

  public List<String[]> getPublicVariableDeclarations () {
    List<String[]> list = new ArrayList<String[]> ();
    for (ResponseDeclaration rd : _responseDeclarations) {
      String[] info = new String[3];
      info[0] = rd.getIdentifier ();
      info[1] = rd.getType ().toString ();
      info[2] = rd.getCardinality ().toString ();

      list.add (info);
    }
    return list;
  }

  public List<String[]> evaluate (String response, String identifier) throws Exception {
    Map<String, String> dict = new HashMap<String, String> ();
    dict.put (identifier, response);
    return evaluate (QTIUtility.getResponse (this, dict));
  }

  public List<String[]> evaluate (Map<String, String> IdentifiersAndResponses) throws Exception {
    return evaluate (QTIUtility.getResponse (this, IdentifiersAndResponses));
  }

  public List<String[]> evaluate (Response response) throws Exception {
    VariableBindings vb = new VariableBindings ();
    bindResponseVariables (response);
    transferValues (vb);
    response.transferBindings (vb);
    _responseProcessor.evaluate (vb, this);
    return vb.getPublicVariableDeclarations ();
  }

  // this happens at scoring time, so problems with the item should already be
  // fixed.
  // Only bind those that have been declared. Really don't care about the
  // others.
  void bindResponseVariables (Response response) {
    if (response == null)
      return;
    for (ResponseDeclaration rd : getResponseDeclarations ()) {
      response.bind (rd.getIdentifier (), rd.getType (), rd.getCardinality ());
    }
  }

  void transferValues (VariableBindings bindings) throws Exception {
    if (bindings == null || bindings.getCount () > 0) {
      throw new Exception ("Programmer: you must call QTIRubric.TransferValues before you call Response.TransferBindings");
    }
    for (ResponseDeclaration rd : _responseDeclarations) {
      bindings.setVariable (rd.getIdentifier (), rd.getDefaultValue ());
    }
    for (OutcomeDeclaration od : _outcomeDeclarations) {
      bindings.setVariable (od.getIdentifier (), od.getDefaultValue ());
    }
  }

  VariableMapping getResponseMapping (DEIdentifier identifier) {
    if (identifier == null)
      return null;
    ResponseDeclaration rd = getResponseDeclaration (identifier.getValue ());
    if (rd == null)
      return null;
    return rd.getMapping ();
  }

  AreaMapping getAreaMapping (DEIdentifier identifier) {
    if (identifier == null)
      return null;
    ResponseDeclaration rd = getResponseDeclaration (identifier.getValue ());
    if (rd == null)
      return null;
    return rd.getAreaMapping ();
  }
}
