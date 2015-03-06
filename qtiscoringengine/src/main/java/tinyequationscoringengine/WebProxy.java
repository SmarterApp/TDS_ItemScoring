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

import qtiscoringengine.QTIScoringException;
import qtiscoringengine.cs2java.StringHelper;
import AIR.Common.Configuration.AppSettingsHelper;
import AIR.Common.Helpers._Ref;
import AIR.Common.Json.JsonHelper;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Utilities.TDSStringUtils;
import AIR.Common.Web.HttpWebHelper;
import AIR.Common.Web.UrlHelper;

public class WebProxy
{
  private static final HttpWebHelper HttpWebHelper = new HttpWebHelper ();
  private int                        _maxRetries;
  private URI                        _serverUri;

  public int getMaxTries () {
    return _maxRetries;
  }

  public URI getServerUri () {
    return _serverUri;
  }

  public WebProxy (URI serverUri, int maxRetries) {
    this._serverUri = serverUri;
    this._maxRetries = maxRetries;
  }

  public boolean isEquivalent (String studentResponseStr, String exemplar, boolean simplify, boolean trigIdenties, boolean logIdenties, boolean force) throws QTIScoringException {
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
      throw new QTIScoringException (exp);
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
      // TODO Review Shiva: In revision set 112 of .NET code there is a
      // "response == null" check here. .NET semantics are different.
      // so hence no instead of throwing an exception we will be returning false
      // here.

      // Shiva Removed below based on comments above.
      /*
       * throw new QTIScoringException ("Web Proxy returned an exception", e);
       */
      return false;
    }

    if (httpStatusCode.get () == HttpServletResponse.SC_OK) {
      ScoreResponse scoreResponse = null;
      try {
        scoreResponse = JsonHelper.deserialize (httpResponse, ScoreResponse.class);
      } catch (IOException exp) {
        throw new QTIScoringException (exp);
      }

      // TODO Shiva: We will never return null from deserialize above for
      // scoreResponse.
      // Make sure we have ported this faithfull from .NET.
      if (scoreResponse != null)
        return scoreResponse.getResult () != null && StringUtils.equalsIgnoreCase (scoreResponse.getResult (), "TRUE");
    }
    return false;
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
      throw new QTIScoringException (exp);
    }

    Map<String, Object> requestForm = new HashMap<String, Object> ();
    requestForm.put ("response", studentResponse);

    _Ref<Integer> httpStatusCode = new _Ref<Integer> (HttpServletResponse.SC_OK);
    String httpResponse = "";
    try {
      httpResponse = submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
    } catch (IOException e) {
      // TODO Review Shiva: In revision set 112 of .NET code there is a
      // "response == null" check here. .NET semantics are different.
      // so hence no instead of throwing an exception we will be returning false
      // here.

      // Shiva Removed below based on comments above.
      /*
       * throw new QTIScoringException ("Web Proxy returned an exception", e);
       */
      return false;
    }
    if (httpStatusCode.get () == HttpServletResponse.SC_OK) {
      ScoreResponse scoreResponse = null;
      try {
        scoreResponse = JsonHelper.deserialize (httpResponse, ScoreResponse.class);
      } catch (IOException exp) {
        throw new QTIScoringException (exp);
      }
      return scoreResponse.getResult () != null && StringUtils.equalsIgnoreCase (scoreResponse.getResult (), "TRUE");
    }
    return false;
  }

  public List<Double> matchDouble (String studentResponseStr, String pattern, List<String> parameters, List<String> constraints, List<String> variables) throws QTIScoringException {
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
      throw new QTIScoringException (exp);
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
    }
    return new ArrayList<Double> ();
  }

  public List<String> matchExpression (String studentResponseStr, String pattern, List<String> parameters, List<String> constraints, List<String> variables) throws QTIScoringException {
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
      throw new QTIScoringException (exp);
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
      return new ArrayList<String> ();
    }
    return new ArrayList<String> ();

  }

  public double evaluateExpression (String studentResponseStr) throws QTIScoringException {
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
      throw new QTIScoringException (exp);
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
      // TODO Shiva: The deserialize above is never going to return null. Check
      // that this logic has been
      // ported faithfull from .NET.
      if (scorerResponse != null) {
        _Ref<Double> value = new _Ref<Double> (Double.NaN);
        JavaPrimitiveUtils.doubleTryParse (scorerResponse.getResult (), value);
        return value.get ();
      }
    }
    return Double.NaN;
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

  static {
    HttpWebHelper.setTimeoutInMillis (AppSettingsHelper.getInt32 ("itemscoring.qti.sympyTimeoutMillis", 10000));
  }
}
