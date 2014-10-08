/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AIR.Common.Web.EncryptionHelper;
import AIR.Common.xml.IXmlSerializable;
import AIR.Common.xml.XmlHelper;
import AIR.Common.xml.XmlReader;

public class ItemScoreRequest implements IXmlSerializable
{
  /*
   * <ItemScoreRequest
   * callbackUrl="http://localhost/ItemScoreClient/Scored.axd"> <ResponseInfo
   * itemIdentifier="I-100-1001" itemFormat="GI"> <StudentResponse
   * encrypted="true"><![CDATA[<Question></Question>]]></StudentResponse>
   * <Rubric type="Uri" cancache="true"
   * encrypted="true">c:\rubrics\rubric.xml</Rubric>
   * <ContextToken><![CDATA[xxxxxxx]]></ContextToken> </ResponseInfo>
   * </ItemScoreRequest>
   */
  private ResponseInfo        _responseInfo;
  private String              _callbackUrl;
  private static final Logger _logger = LoggerFactory.getLogger (ItemScoreRequest.class);

  public ItemScoreRequest () {
  }

  public ItemScoreRequest (ResponseInfo responseInfo) {
    this._responseInfo = responseInfo;
  }

  @Override
  public void readXML (XmlReader reader) {
    try {
      Document doc = reader.getDocument ();
      Element itemScoreRequestElement = XmlHelper.getElement (doc, "ItemScoreRequest");
      _callbackUrl = itemScoreRequestElement.getAttributeValue ("callbackUrl");
      if (_callbackUrl != null)
        setCallbackUrl (itemScoreRequestElement.getAttributeValue ("callbackUrl"));

      String itemIdentifier = null;
      Element responseInfoElement = itemScoreRequestElement.getChild ("ResponseInfo");
      // "itemIdentifier"
      if (responseInfoElement.getAttributeValue ("itemIdentifier") != null)
        itemIdentifier = responseInfoElement.getAttributeValue ("itemIdentifier");

      // "itemFormat"
      String itemFormat = null;
      if (responseInfoElement.getAttributeValue ("itemFormat") != null)
        itemFormat = responseInfoElement.getAttributeValue ("itemFormat");

      // <StudentResponse>
      String studentResponse = null;
      boolean studentResponseIsEncrypted = false;

      Element studentResponseElement = responseInfoElement.getChild ("StudentResponse");
      if (studentResponseElement != null) {
        if (studentResponseElement.getAttributeValue ("encrypted") != null) {
          studentResponseIsEncrypted = Boolean.parseBoolean (studentResponseElement.getAttributeValue ("encrypted"));
        }
        studentResponse = studentResponseElement.getText ();
      }
      // <Rubric>
      Object rubric = null;
      RubricContentType rubricContentType = RubricContentType.Uri;
      boolean canCache = true;
      boolean rubricEncrypted = false;

      Element rubricElement = responseInfoElement.getChild ("Rubric");
      if (rubricElement != null) {
        String rubricType = null;
        if (rubricElement.getAttributeValue ("type") != null) {
          rubricType = rubricElement.getAttributeValue ("type");
        }
        if (rubricElement.getAttributeValue ("cancache") != null) {
          canCache = Boolean.parseBoolean (rubricElement.getAttributeValue ("cancache"));
        }
        if (rubricElement.getAttributeValue ("encrypted") != null) {
          rubricEncrypted = Boolean.parseBoolean (rubricElement.getAttributeValue ("encrypted"));
        }
        if (rubricType.equalsIgnoreCase ("Data"))
          rubricContentType = RubricContentType.ContentString;
        else
          rubricContentType = RubricContentType.Uri;
        rubric = rubricElement.getText ();

        if (!rubricEncrypted && rubricContentType == RubricContentType.Uri) {
          rubric = (String) rubric;
        }
      }

      // <ContextToken>
      String contextToken = null;
      Element contextTokenElement = responseInfoElement.getChild ("ContextToken");
      if (contextTokenElement != null) {
        contextToken = contextTokenElement.getText ();
      }

      // <IncomingBindings>
      List<VarBinding> incomingBindings = new ArrayList<VarBinding> ();
      Element incomingBindingElements = XmlHelper.getElement (doc, "IncomingBindings");
      if (incomingBindingElements != null) {
        List<Element> bindings = XmlHelper.getElements (incomingBindingElements, "Binding");
        for (Element binding : bindings) {
          final String name = binding.getAttributeValue ("name");
          final String type = binding.getAttributeValue ("type");
          final String value = binding.getAttributeValue ("value");

          if (StringUtils.isNotEmpty (name) && StringUtils.isNotEmpty (type) && StringUtils.isNotEmpty (value))
            incomingBindings.add (new VarBinding ()
            {
              {
                setName (name);
                setType (type);
                setValue (value);
              }
            });
        }
        // move past the IncomingBindings
      }

      // <OutgoingBindings>
      List<VarBinding> outgoingBindings = new ArrayList<VarBinding> ();
      Element outgoingBindingElements = XmlHelper.getElement (doc, "OutgoingBindings");
      if (outgoingBindingElements != null) {
        List<Element> bindings = XmlHelper.getElements (outgoingBindingElements, "Binding");
        for (Element binding : bindings) {
          final String name = binding.getAttributeValue ("name");

          if (StringUtils.isNotEmpty (name))
            outgoingBindings.add (new VarBinding ()
            {
              {
                setName (name);
                setType ("");
                setValue ("");
              }
            });
        }
        // move past the OutgoingBindings
      }

      _responseInfo = new ResponseInfo (itemFormat, itemIdentifier, studentResponse, rubric, rubricContentType, contextToken, canCache);
      _responseInfo.setStudentResponseEncrypted (studentResponseIsEncrypted);
      _responseInfo.setRubricEncrypted (rubricEncrypted);
      _responseInfo.setIncomingBindings (incomingBindings);
      _responseInfo.setOutgoingBindings (outgoingBindings);

    } catch (Exception exp) {
      exp.printStackTrace ();
    }
  }

  @Override
  public void writeXML (XMLStreamWriter out) throws XMLStreamException {

    out.writeStartDocument ();
    out.writeStartElement ("ItemScoreRequest");
    out.writeAttribute ("callbackUrl", getCallbackUrl ());

    out.writeStartElement ("ResponseInfo");
    out.writeAttribute ("itemIdentifier", _responseInfo.getItemIdentifier ());
    out.writeAttribute ("itemFormat", _responseInfo.getItemFormat ());

    out.writeStartElement ("StudentResponse");
    out.writeAttribute ("encrypted", Boolean.toString (_responseInfo.isStudentResponseEncrypted ()));
    out.writeCData (_responseInfo.getStudentResponse ());
    out.writeEndElement (); // </StudentResponse>

    out.writeStartElement ("Rubric");
    out.writeAttribute ("type", _responseInfo.getContentType () == RubricContentType.ContentString ? "Data" : "Uri");
    out.writeAttribute ("cancache", Boolean.toString (_responseInfo.isCanCacheRubric ()));
    out.writeAttribute ("encrypted", Boolean.toString (_responseInfo.isRubricEncrypted ()));
    if (_responseInfo.getRubric () != null) {
      out.writeCData (_responseInfo.getRubric ().toString ());
    }
    out.writeEndElement (); // </Rubric>

    out.writeStartElement ("ContextToken");
    out.writeCData (_responseInfo.getContextToken ().toString ());
    out.writeEndElement (); // </ContextToken>

    // <IncomingBindings>
    if (_responseInfo.getIncomingBindings () != null && _responseInfo.getIncomingBindings ().size () > 0) {
      out.writeStartElement ("IncomingBindings");
      for (VarBinding varBinding : _responseInfo.getIncomingBindings ()) {
        out.writeStartElement ("Binding");
        out.writeAttribute ("name", varBinding.getName ());
        out.writeAttribute ("type", varBinding.getType ());
        out.writeAttribute ("value", varBinding.getValue ());
        out.writeEndElement ();
      }
      out.writeEndElement ();
    }

    // <OutgoingBindings>
    if (_responseInfo.getOutgoingBindings () != null && _responseInfo.getOutgoingBindings ().size () > 0) {
      out.writeStartElement ("OutgoingBindings");
      for (VarBinding varBinding : _responseInfo.getOutgoingBindings ()) {
        out.writeStartElement ("Binding");
        out.writeAttribute ("name", varBinding.getName ());
        out.writeAttribute ("type", varBinding.getType ());
        out.writeAttribute ("value", varBinding.getValue ());
        out.writeEndElement ();
      }
      out.writeEndElement ();
    }

    out.writeEndElement (); // </ResponseInfo>
    out.writeEndElement (); // </ItemScoreRequest>
    out.writeEndDocument ();
    out.close ();
  }

  // / <summary>
  // / Encrypt response and rubric info
  // / </summary>
  // / <param name="encryptStudentResponse"></param>
  // / <param name="encryptRubric"></param>
  public void encrypt (boolean encryptStudentResponse, boolean encryptRubric) {
    if (encryptStudentResponse && !_responseInfo.isStudentResponseEncrypted ()) {
      _responseInfo.setStudentResponse (EncryptionHelper.EncryptToBase64 (_responseInfo.getStudentResponse ()));
      _responseInfo.setStudentResponseEncrypted (true);
    }

    if (encryptRubric && !_responseInfo.isRubricEncrypted ()) {
      _responseInfo.setRubric (EncryptionHelper.EncryptToBase64 ((String) _responseInfo.getRubric ()));
      _responseInfo.setRubricEncrypted (true);
    }
  }

  // <summary>
  // </summary>
  // <param name="decryptStudentResponse"></param>
  // <param name="decryptRubric"></param>
  public void decrypt (boolean decryptStudentResponse, boolean decryptRubric) {
    if (decryptStudentResponse && _responseInfo.isStudentResponseEncrypted ()) {
      _responseInfo.setStudentResponse (EncryptionHelper.DecryptFromBase64 (_responseInfo.getStudentResponse ()));
      _responseInfo.setStudentResponseEncrypted (false);
    }
    if (decryptRubric && _responseInfo.isRubricEncrypted ()) {
      _responseInfo.setRubric (EncryptionHelper.DecryptFromBase64 ((String) _responseInfo.getRubric ()));
      _responseInfo.setRubricEncrypted (false);
      // We can now safely convert it to a URI if needed
      if (_responseInfo.getContentType () == RubricContentType.Uri)
        try {
          _responseInfo.setRubric (new URI ((String) _responseInfo.getRubric ()));
        } catch (URISyntaxException e) {
          e.printStackTrace ();
          _logger.error (e.getMessage ());
        }
    }
  }

  public ResponseInfo getResponseInfo () {
    return _responseInfo;
  }

  public void setResponseInfo (ResponseInfo value) {
    _responseInfo = value;
  }

  public String getCallbackUrl () {
    return _callbackUrl;
  }

  public void setCallbackUrl (String value) {
    _callbackUrl = value;
  }
}
