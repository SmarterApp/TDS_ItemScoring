package tinyequationscoringengine;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang3.StringUtils;

import qtiscoringengine.QTIScoringException;
import qtiscoringengine.cs2java.StringHelper;
import AIR.Common.Helpers._Ref;
import AIR.Common.Json.JsonHelper;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Web.HttpWebHelper;
import AIR.Common.Web.UrlHelper;

public class WebProxy
{
  private static final HttpWebHelper HttpWebHelper = new HttpWebHelper ();
  private int _maxRetries;
  private URI _serverUri;

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
      httpResponse = HttpWebHelper.submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
    } catch (IOException e) {
      throw new QTIScoringException ("Web Proxy returned an exception", e);
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
      httpResponse = HttpWebHelper.submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
    } catch (IOException e) {
      throw new QTIScoringException ("Web Proxy returned an exception", e);
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
      httpResponse = HttpWebHelper.submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
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

  public List<Double> matchExpression (String studentResponseStr, String pattern, List<String> parameters, List<String> constraints, List<String> variables) throws QTIScoringException {
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
      httpResponse = HttpWebHelper.submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
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
        // Lets convert each return into a double list
        List<Double> parsedValues = new ArrayList<Double> ();
        for (String value : values) {
          _Ref<Double> parsedValue = new _Ref<Double> ();
          if (JavaPrimitiveUtils.doubleTryParse (value, parsedValue))
            parsedValues.add (parsedValue.get ());
          else
            parsedValues.add (evaluateExpression (value));
        }
        return parsedValues;
      }
      return new ArrayList<Double> ();
    }
    return new ArrayList<Double> ();

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
      httpResponse = HttpWebHelper.submitForm (serviceUri.toString (), requestForm, getMaxTries (), httpStatusCode);
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
      if (scorerResponse != null) {
        _Ref<Double> value = new _Ref<Double> (Double.NaN);
        JavaPrimitiveUtils.doubleTryParse (scorerResponse.getResult (), value);
      }
    }
    return Double.NaN;
  }
}
