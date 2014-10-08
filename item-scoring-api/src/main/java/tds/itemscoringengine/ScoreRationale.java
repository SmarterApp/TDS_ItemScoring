/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import AIR.Common.xml.AdapterXmlCData;

public class ScoreRationale
{
  private Exception         _exception;
  private String            _exceptionStr;
  private List<Proposition> _propositions;
  private List<VarBinding>  _bindings;
  private String            _message;

  // / <summary>
  // / Propositions and their assert states
  // / </summary>
  @XmlElementWrapper (name = "Propositions")
  @XmlElement (name = "Proposition")
  public List<Proposition> getPropositions () {
    return _propositions;
  }

  public void setPropositions (List<Proposition> value) {
    _propositions = value;
  }

  // / <summary>
  // / Variable bindings and their values (if requested)
  // / </summary>
  @XmlElementWrapper (name = "Bindings")
  @XmlElement (name = "Binding")
  public List<VarBinding> getBindings () {
    return _bindings;
  }

  public void setBindings (List<VarBinding> value) {
    _bindings = value;
  }

  // / <summary>
  // / Free form descriptive message for why the score is what it is
  // / </summary>
  @XmlTransient
  public String getMsg () {
    return _message;
  }

  public void setMsg (String value) {
    _message = value;
  }

  // / <summary>
  // / Exception holder in case any occur during scoring
  // / </summary>
  @XmlTransient
  public Exception getException () {
    return _exception;
  }

  public void setException (Exception value) {
    _exception = value;

    StringWriter sw = new StringWriter ();
    try (PrintWriter prn = new PrintWriter (sw)) {
      value.printStackTrace (prn);
    }

    _exceptionStr = sw.toString ();
  }

  // stuff for the Xml Serializer/Deserializer of the specific fields into CDATA
  // elements
  @XmlJavaTypeAdapter (AdapterXmlCData.class)
  @XmlElement (name = "Message")
  public String getMsgCData () {
    return StringUtils.isEmpty (_message) ? "" : _message;
  }

  public void setMsgCData (String value) {
    _message = value;
  }

  @XmlJavaTypeAdapter (AdapterXmlCData.class)
  @XmlElement (name = "StackTrace")
  public String getExpCData () {
    return StringUtils.isEmpty (_exceptionStr) ? "" : _exceptionStr; // keeping
                                                                     // a string
                                                                     // representation
                                                                     // of the
                                                                     // exception
                                                                     // around
                                                                     // is
                                                                     // really
                                                                     // to help
                                                                     // with the
                                                                     // serialization/deserialization
                                                                     // between
                                                                     // ISE and
                                                                     // calling
                                                                     // host.
  }

  public void setExpCData (String value) {
    _exceptionStr = value;
  }

}
