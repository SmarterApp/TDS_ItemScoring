/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinyequationscoringengine;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qtiscoringengine.QTIScoringException;
import qtiscoringengine.cs2java.StringHelper;
import AIR.Common.Configuration.AppSettingsHelper;
import AIR.Common.Helpers._Ref;
import AIR.Common.Json.JsonHelper;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Utilities.TDSStringUtils;
import AIR.Common.Web.HttpWebHelper;
import AIR.Common.Web.UrlHelper;

class WebProxy {
  private static final Logger        _logger       = LoggerFactory.getLogger (WebProxy.class);
  private static final HttpWebHelper HttpWebHelper = new HttpWebHelper ();
  private int                        _maxRetries;
  private URI                        _serverUri;

  private int getMaxTries () {
    return _maxRetries;
  }

  private URI getServerUri () {
    return _serverUri;
  }

  WebProxy(final URI serverUri, final int _maxRetries, int timeoutInMillis) {
    this._serverUri = serverUri;
    this._maxRetries = _maxRetries;
    HttpWebHelper.setTimeoutInMillis (timeoutInMillis);
  }

  boolean isEquivalent (String studentResponseStr, String exemplar, boolean simplify, boolean trigIdenties, boolean logIdenties, boolean force) throws QTIScoringException {
    String studentResponse = StringHelper.trim (studentResponseStr, new char[] { '[', ' ', ']' }); // remove
                                                                                                   // the
                                                                                                   // []
                                                                                                   // wrapper
                                                                                                   // around
                                                                                                   // the
                                                                                                   // math
                                                                                                   // expression

    URI serviceUri = null;
    try {
      serviceUri = new URI (UrlHelper.buildUrl (getServerUri ().toString (), "isequivalent"));
    } catch (URISyntaxException exp) {
    	String url = (serviceUri != null)? serviceUri.toString (): "null";
       	_logger.error (String.format ("Failed to invoke @isEquivalent method: URL = %s; %s", url, exp.getMessage()), exp);
       	throw new QTIScoringException ("REST endpoint URI cannot be constructed. Service URI specified as " 
       	+ url + ": " + exp);
    }

    Map<String, Object> requestForm = new HashMap<String, Object> ();
    requestForm.put ("response", studentResponse);
    requestForm.put ("exemplar", exemplar);
    requestForm.put ("simplify", simplify);
    requestForm.put ("trig", trigIdenties);
    requestForm.put ("log", logIdenties);
    requestForm.put ("force", force);

    _Ref<Integer> httpStatusCode = new _Ref<Integer> (HttpServletResponse.SC_OK);
    String httpResponse = "";
    try {
      httpResponse = submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
    } catch (IOException e) {
      // Review Shiva: In revision set 112 of .NET code there is a
      // "response == null" check here. .NET semantics are different.
      // so hence no instead of throwing an exception we will be returning false
      // here.

      // Shiva Removed below based on comments above.
      // SB-1269 (Alex): I uncommented exception. See SB-1269 description	    	
      _logger.error (String.format ("Failed to invoke @isEquivalent methodWeb. Proxy returned an exception: %s ", e.getMessage()), e);
      throw new QTIScoringException ("Web Proxy returned an exception", e);      
      // return false;
    }

    if (httpStatusCode.get () == HttpServletResponse.SC_OK) {
      ScoreResponse scoreResponse = null;
      try {
        scoreResponse = JsonHelper.deserialize (httpResponse, ScoreResponse.class);
      } catch (IOException exp) {
        throw new QTIScoringException (exp);
      }

      // Shiva: We will never return null from deserialize above for
      // scoreResponse.
      // Make sure we have ported this faithfull from .NET.
      if (scoreResponse != null)
      {
        return scoreResponse.getResult () != null && StringUtils.equalsIgnoreCase (scoreResponse.getResult (), "TRUE");
      }
      throw new QTIScoringException("JsonHelper return null from deserialize for scoreResponse.");
    }
    // SB-1269 (Alex): We need to return false only in the case if student answer is not equivalent expected answer.
    throw new QTIScoringException("Web Proxy returned a failure status code: " + httpStatusCode.get ().toString());
  }

  public boolean parsable (String studentResponseStr) throws QTIScoringException {
    String studentResponse = StringHelper.trim (studentResponseStr, new char[] { '[', ' ', ']' }); // remove
                                                                                                   // the
                                                                                                   // []
                                                                                                   // wrapper
                                                                                                   // around
                                                                                                   // the
                                                                                                   // math
                                                                                                   // expression

    URI serviceUri = null;
    try {
      serviceUri = new URI (UrlHelper.buildUrl (getServerUri ().toString (), "parsable"));
    } catch (URISyntaxException exp) {
    	String url = (serviceUri != null)? serviceUri.toString (): "null";
    	_logger.error (String.format ("Failed to invoke @parsable method: URL = %s; %s", url, exp.getMessage()), exp);
       	throw new QTIScoringException ("REST endpoint URI cannot be constructed. Service URI specified as " 
       	+ url + ": " + exp);
    }

    Map<String, Object> requestForm = new HashMap<String, Object> ();
    requestForm.put ("response", studentResponse);

    _Ref<Integer> httpStatusCode = new _Ref<Integer> (HttpServletResponse.SC_OK);
    String httpResponse = "";
    try {
      httpResponse = submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
    } catch (IOException e) {
      // Review Shiva: In revision set 112 of .NET code there is a
      // "response == null" check here. .NET semantics are different.
      // so hence no instead of throwing an exception we will be returning false
      // here.

      // Shiva Removed below based on comments above.
      // SB-1269 (Alex): I uncommented exception. See SB-1269 description	    	
       _logger.error (String.format ("Failed to invoke @parsable methodWeb. Proxy returned an exception: %s ", e.getMessage()), e);        
       throw new QTIScoringException ("Web Proxy returned an exception", e);
       // return false;
    }
    if (httpStatusCode.get () == HttpServletResponse.SC_OK) {
      ScoreResponse scoreResponse = null;
      try {
        scoreResponse = JsonHelper.deserialize (httpResponse, ScoreResponse.class);
      } catch (IOException exp) {
        throw new QTIScoringException (exp);
      }
      if (scoreResponse != null)
      {
        return scoreResponse.getResult () != null && StringUtils.equalsIgnoreCase (scoreResponse.getResult (), "TRUE");
      }
      throw new QTIScoringException("JsonHelper return null from deserialize for scoreResponse.");
    }
    // SB-1269 (Alex): We need to return false only in the case if student answer is not equivalent expected answer.
    throw new QTIScoringException("Web Proxy returned a failure status code: " + httpStatusCode.get ().toString());
  }

  List<Double> matchDouble (String studentResponseStr, String pattern, List<String> parameters, List<String> constraints, List<String> variables) throws QTIScoringException {
    String studentResponse = StringHelper.trim (studentResponseStr, new char[] { '[', ' ', ']' }); // remove
                                                                                                   // the
                                                                                                   // []
                                                                                                   // wrapper
                                                                                                   // around
                                                                                                   // the
                                                                                                   // math
                                                                                                   // expression
    URI serviceUri = null;

    try {
      serviceUri = new URI (UrlHelper.buildUrl (getServerUri ().toString (), "matchdouble"));

    } catch (URISyntaxException exp) {
    	String url = (serviceUri != null)? serviceUri.toString (): "null";
       	_logger.error (String.format ("Failed to invoke @matchDouble method: URL = %s; %s", url, exp.getMessage()), exp);
       	throw new QTIScoringException ("REST endpoint URI cannot be constructed. Service URI specified as " 
       	+ url + ": " + exp);
    }

    Map<String, Object> requestForm = new HashMap<String, Object> ();
    requestForm.put ("response", studentResponse);
    requestForm.put ("pattern", pattern);
    requestForm.put ("parameters", StringUtils.join (parameters, '|'));
    requestForm.put ("constraints", StringUtils.join (constraints, "|"));
    requestForm.put ("variables", StringUtils.join (variables, "|"));

    _Ref<Integer> httpStatusCode = new _Ref<Integer> (HttpServletResponse.SC_OK);
    String httpResponse = "";
    try {
      httpResponse = submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
    } catch (IOException e) {
      throw new QTIScoringException ("Web Proxy returned an exception", e);
    }
    if (httpStatusCode.get () == HttpServletResponse.SC_OK) {
      ScoreResponse scorerResponse = null;
      try {
        scorerResponse = JsonHelper.deserialize (httpResponse, ScoreResponse.class);
      } catch (IOException exp) {
        throw new QTIScoringException (exp);
      }
      if (scorerResponse != null && !StringUtils.isEmpty (scorerResponse.getResult ())) {
        String[] values = StringUtils.split (scorerResponse.getResult (), '|');
        List<Double> returnValue = new ArrayList<Double> ();
        for (String value : values)
          returnValue.add (Double.parseDouble (value));
        return returnValue;
      }
      else if(scorerResponse == null)
      {
    	  throw new QTIScoringException("JsonHelper return null from deserialize for scoreResponse."); 
      }
      return new ArrayList<Double> ();
    }
    throw new QTIScoringException("Web Proxy returned a failure status code: " + httpStatusCode.get ().toString());
  }

  List<String> matchExpression (String studentResponseStr, String pattern, List<String> parameters, List<String> constraints, List<String> variables) throws QTIScoringException {
    String studentResponse = StringHelper.trim (studentResponseStr, new char[] { '[', ' ', ']' }); // remove
                                                                                                   // the
                                                                                                   // []
                                                                                                   // wrapper
                                                                                                   // around
                                                                                                   // the
                                                                                                   // math
                                                                                                   // expression

    URI serviceUri = null;
    try {
      serviceUri = new URI (UrlHelper.buildUrl (getServerUri ().toString (), "matchexpression"));
    } catch (URISyntaxException exp) {
    	String url = (serviceUri != null)? serviceUri.toString (): "null";
       	_logger.error (String.format ("Failed to invoke @matchExpression method: URL = %s; %s", url, exp.getMessage()), exp);
       	throw new QTIScoringException ("REST endpoint URI cannot be constructed. Service URI specified as " 
       	+ url + ": " + exp);
    }
    Map<String, Object> requestForm = new HashMap<String, Object> ();
    requestForm.put ("response", studentResponse);
    requestForm.put ("pattern", pattern);
    requestForm.put ("parameters", StringUtils.join (parameters, '|'));
    requestForm.put ("constraints", StringUtils.join (constraints, '|'));
    requestForm.put ("variables", StringUtils.join (variables, '|'));

    _Ref<Integer> httpStatusCode = new _Ref<Integer> (HttpServletResponse.SC_OK);
    String httpResponse = "";
    try {
      httpResponse = submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
    } catch (IOException e) {
      throw new QTIScoringException ("Web Proxy returned an exception", e);
    }
    if (httpStatusCode.get () == HttpServletResponse.SC_OK) {
      ScoreResponse scorerResponse = null;
      try {
        scorerResponse = JsonHelper.deserialize (httpResponse, ScoreResponse.class);
      } catch (IOException exp) {
        throw new QTIScoringException (exp);
      }

      if (scorerResponse != null && !StringUtils.isEmpty (scorerResponse.getResult ())) {
        String[] values = StringUtils.split (scorerResponse.getResult (), '|');
        return Arrays.asList (values);
      }
      else if(scorerResponse == null)
      {
    	  throw new QTIScoringException("JsonHelper return null from deserialize for scoreResponse."); 
      }
      return new ArrayList<String> ();
    }
    throw new QTIScoringException("Web Proxy returned a failure status code: " + httpStatusCode.get ().toString());
    

  }

  double evaluateExpression (String studentResponseStr) throws QTIScoringException {
    String studentResponse = StringHelper.trim (studentResponseStr, new char[] { '[', ' ', ']' }); // remove
                                                                                                   // the
                                                                                                   // []
                                                                                                   // wrapper
                                                                                                   // around
                                                                                                   // the
                                                                                                   // math
                                                                                                   // expressiontry
    if (StringUtils.isEmpty (studentResponse))
      return Double.NaN;

    URI serviceUri = null;
    try {
      serviceUri = new URI (UrlHelper.buildUrl (getServerUri ().toString (), "evaluate"));
    } catch (URISyntaxException exp) {
       	String url = (serviceUri != null)? serviceUri.toString (): "null";
       	_logger.error (String.format ("Failed to invoke @evaluateExpression method: URL = %s; %s", url, exp.getMessage()), exp);
       	throw new QTIScoringException ("REST endpoint URI cannot be constructed. Service URI specified as " 
       	+ url + ": " + exp);
    }

    Map<String, Object> requestForm = new HashMap<String, Object> ();
    requestForm.put ("response", studentResponse);

    _Ref<Integer> httpStatusCode = new _Ref<Integer> (HttpServletResponse.SC_OK);
    String httpResponse = "";
    try {
      httpResponse = submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
    } catch (IOException e) {
      throw new QTIScoringException ("Web Proxy returned an exception", e);
    }

    if (httpStatusCode.get () == HttpServletResponse.SC_OK) {
      ScoreResponse scorerResponse = null;
      try {
        scorerResponse = JsonHelper.deserialize (httpResponse, ScoreResponse.class);
      } catch (IOException exp) {
        throw new QTIScoringException (exp);
      }
      // Shiva: The deserialize above is never going to return null. Check
      // that this logic has been
      // ported faithfull from .NET.
      if (scorerResponse != null) {
        _Ref<Double> value = new _Ref<Double> (Double.NaN);
        JavaPrimitiveUtils.doubleTryParse (scorerResponse.getResult (), value);
        return value.get ();
      }
      throw new QTIScoringException("JsonHelper return null from deserialize for scoreResponse."); 
    }
    throw new QTIScoringException("Web Proxy returned a failure status code: " + httpStatusCode.get ().toString());
  }

  /*
   * TODO Shiva: this is hack. Read the comment below. The to String for boolean
   * in Java comes to as "true" and "false". For .NET it is "True" and "False".
   */
  private String submitForm (String url, Map<String, Object> formParameters, int maxTries, _Ref<Integer> httpStatusCode) throws IOException {
    Map<String, Object> formParametersToString = new HashMap<String, Object> ();
    if (formParameters != null)
      for (Map.Entry<String, Object> entry : formParameters.entrySet ()) {
        Object entryValue = entry.getValue ();
        if (entryValue instanceof Boolean)
          entryValue = TDSStringUtils.getCSharpBooleanToString ((Boolean) entryValue);
        formParametersToString.put (entry.getKey (), entryValue.toString ());
      }
    return HttpWebHelper.submitForm (url, formParametersToString, maxTries, httpStatusCode);
  }
}
