/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class QTIUtility
{
  // / <summary>
  // / Returns BaseType.Null if there are any problems
  // / </summary>
  // / <param name="basetype"></param>
  // / <returns></returns>
  static BaseType basetypeFromXML (String basetype)
  {
    _Ref<BaseType> out = new _Ref<> (BaseType.Null);
    if (basetype == null)
      basetype = ""; // so we can call trim below

    if (!StringUtils.isEmpty (basetype.trim ()))
    {
      // if (!Enum.TryParse(basetype, true, out bt))
      // bt = BaseType.Null;
      if (!JavaPrimitiveUtils.enumTryParse (BaseType.class, basetype.trim (), true, out))
        out.set (BaseType.Null);
    }
    return out.get ();
  }

  // / <summary>
  // / parse a string value for a Cardinality
  // / </summary>
  // / <param name="cardinality">value to parse</param>
  // / <returns></returns>
  static Cardinality cardinalityFromXML (String cardinality)
  {
    _Ref<Cardinality> card = new _Ref<> (Cardinality.None);
    if (cardinality == null)
      cardinality = ""; // so we can call trim below

    if (!StringUtils.isEmpty (cardinality.trim ()))
    {
      if (!JavaPrimitiveUtils.enumTryParse (Cardinality.class, cardinality.trim (), true, card))
        return Cardinality.None;
    }
    return card.get ();
  }

  static List<DataElement> getValueListFromXML (Element defaultVal, BaseType bt, XmlNamespaceManager nsmgr)
  {
    List<DataElement> values = new ArrayList<DataElement> ();
    if (defaultVal == null)
      return values;
    // XmlNodeList defaultValues = defaultVal.SelectNodes(QTIXmlConstants.Value,
    // nsmgr);
    List<Element> defaultValues = new XmlElement (defaultVal).selectNodes (QTIXmlConstants.Value, nsmgr);
    for (Element node : defaultValues)
    {
      DataElement de = DataElement.create (node, bt);
      // todo: add error checking for if de.IsError
      // Zach: adding checks where this is called instead
      values.add (de);
    }
    return values;
  }

  static DataElement getSingleValueFromXML (Element defaultVal, BaseType bt, XmlNamespaceManager nsmgr)
  {
    DataElement de = null;
    if (defaultVal == null)
      return null;
    // XmlElement node =
    // (XmlElement)defaultVal.SelectSingleNode(QTIXmlConstants.Value, nsmgr);
    Element node = new XmlElement (defaultVal).selectSingleNode (QTIXmlConstants.Value, nsmgr);
    if (node != null)
    {
      de = DataElement.create (node, bt);
    }
    return de;
  }

  // todo: obviously, a more robust approach is needed
  // internal static XmlElement GetXMLFromURL(string url, XmlNamespaceManager
  // nsmgr)
  // {

  // HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
  // HttpWebResponse response = (HttpWebResponse)request.GetResponse();
  // if (response.StatusCode == HttpStatusCode.OK && response.ContentLength > 0)
  // {
  // XmlReader reader = XmlReader.Create(url);
  // XmlDocument doc = new XmlDocument();
  // doc.Load(reader);
  // return (XmlElement)doc.SelectSingleNode("qti:responseProcessing",nsmgr);
  // }
  // return null;
  // }

  static Element getXMLFromURL (String url, XmlNamespaceManager nsmgr) throws MalformedURLException
  {
    // C:\Projects\QTISpec\qtiv2p1pd2\rptemplates
    // Uri uri = new Uri(url);
    // string[] chunks = uri.
    // int count = uri.Segments.Length;

    // string filename = uri.Segments[count-1];

    // string place =
    // string.Format("file:///C:\\Projects\\QTISpec\\qtiv2p1pd2\\rptemplates\\{0}.xml",
    // filename);
    Document doc = new Document ();
    
    URL _url = new URL(url);
    try (InputStream is = _url.openStream ()) {
      doc = new SAXBuilder ().build (is);
    } catch (JDOMException | IOException e) {
      e.printStackTrace ();
    }
    // return XPathFactory.instance ().compile
    // (QTIXmlConstants.ResponseProcessing, Filters.element (), null,
    // nsmgr).evaluateFirst (doc);
    //return new XmlElement (doc.getRootElement ()).selectSingleNode (QTIXmlConstants.ResponseProcessing, nsmgr);
    return doc.getRootElement ();
  }

  // / <summary>
  // / Get the current node and all child nodes of the current node in a string,
  // preserving the formatting
  // / and indentation
  // / </summary>
  // / <param name="node"></param>
  // / <returns></returns>
  static String nodeToString (Element node)
  {
    if (node == null)
      return "";
    XMLOutputter out = new XMLOutputter (Format.getPrettyFormat ());
    return out.outputString (node);
  }

  // / <summary>
  // / parses any space-delimited or comma-delimited string into a list of
  // strings, removing empty entries
  // / </summary>
  // / <param name="delimitedString"></param>
  // / <returns></returns>
  public static List<String> ParseDelimitedString (String delimitedString)
  {
    // return delimitedString.Split(new char[] { ',', ' ' },
    // StringSplitOptions.RemoveEmptyEntries).ToList<string>();
    return Arrays.asList (delimitedString.split ("[, ]+"));
  }

  // / <summary>
  // / get a Response object
  // / </summary>
  // / <param name="rubric"></param>
  // / <param name="IdentifiersAndResponses">key is identifier, value is
  // response</param>
  // / <returns></returns>

  // / <summary>
  // / get a Response object
  // / </summary>
  // / <param name="rubric"></param>
  // / <param name="IdentifiersAndResponses">key is identifier, value is
  // response</param>
  // / <returns></returns>
  static Response getResponse (QTIRubric rubric, Map<String, String> IdentifiersAndResponses)
  {
    Map<String, String> _responseValues = new HashMap<String, String> ();
    for (String[] info : rubric.getPublicVariableDeclarations ())
    {
      _responseValues.put (info[0], _responseValues.containsKey (info[0]) ? _responseValues.get (info[0]) : null);
    }
    // get the key for the response and set the value to the response parameter
    // foreach (KeyValuePair<string, string> kvp in IdentifiersAndResponses)
    // {
    // if (_responseValues.ContainsKey(kvp.Key))
    // _responseValues[kvp.Key] = kvp.Value;
    // //throw error if the identifier doesn't exist??
    // }

    for (String key : IdentifiersAndResponses.keySet ()) {
      if (_responseValues.containsKey (key))
        _responseValues.put (key, IdentifiersAndResponses.get (key));
    }

    List<ResponseBinding> responses = new ArrayList<ResponseBinding> ();
    for (String varname : _responseValues.keySet ())
    {
      String val = _responseValues.get (varname);
      ResponseBinding binding = new ResponseBinding (varname, val);
      responses.add (binding);
    }
    return new Response (responses);
  }

  // / <summary>
  // / Get a file name for the validation log, made unique by the current date
  // and time
  // / </summary>
  // / <returns></returns>
  public static String getUniqueFileName (String fileNamePrefix, String fileExtension)
  {
    // DateTime now = DateTime.Now;
    long now = System.currentTimeMillis ();
    // return "QTIScoringEngine_" + fileNamePrefix + "_" +
    // now.ToShortDateString().Replace("/", "-") + "_" +
    // now.ToLongTimeString().Replace(" ", "").Replace(":", "_") +
    // fileExtension;
    return "QTIScoringEngine_" + fileNamePrefix + "_" + DateFormat.getDateInstance (DateFormat.SHORT).format (new Date (now)).replace ("/", "-") + "_"
        + DateFormat.getTimeInstance (DateFormat.LONG).format (new Date (now)).replace (" ", "").replace (":", "_") + fileExtension;
  }
}
