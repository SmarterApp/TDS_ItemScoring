package tinyequationscoringengine;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import qtiscoringengine.QTIScoringException;
import qtiscoringengine.QTIScoringRuntimeException;

/*
 * The XML that needs to be generated is the following:
 */
/*
 * <?xml version="1.0" encoding="utf-16"?>
<MathExpressionInfo xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <SympyResponse>
    <string>1</string>
    <string>2</string>
    <string>3</string>
  </SympyResponse>
  <_sympyResponseNotSimplified>
    <string>4</string>
    <string>5</string>
    <string>6</string>
  </_sympyResponseNotSimplified>
  <_overcorrectedSympyResponse>
    <string>7</string>
    <string>8</string>
    <string>9</string>
  </_overcorrectedSympyResponse>
  <_triedToApplyCorrection>true</_triedToApplyCorrection>
  <_appliedCorrection>true</_appliedCorrection>
</MathExpressionInfo>
 */
@XmlRootElement(name="MathExpressionInfo")
public class MathExpressionInfo
{
  private List<String> _sympyResponse;
  private List<String> _sympyResponseNotSimplified;
  private List<String> _overcorrectedSympyResponse;
  private boolean      _triedToApplyCorrection;
  private boolean      _appliedCorrection;

  @XmlElementWrapper (name = "SympyResponse")
  @XmlElement (name = "string")
  public List<String> getSympyResponse () {
    return _sympyResponse;
  }

  public void setSympyResponse (List<String> value) {
    this._sympyResponse = value;
  }

  @XmlElementWrapper (name = "_sympyResponseNotSimplified")
  @XmlElement (name = "string")
  public List<String> getSympyResponseNotSimplified () {
    return _sympyResponseNotSimplified;
  }

  public void setSympyResponseNotSimplified (List<String> value) {
    this._sympyResponseNotSimplified = value;
  }
  
  @XmlElementWrapper (name = "_overcorrectedSympyResponse")
  @XmlElement (name = "string")
  public List<String> getOvercorrectedSympyResponse () {
    return _overcorrectedSympyResponse;
  }

  public void setOvercorrectedSympyResponse (List<String> value) {
    this._overcorrectedSympyResponse = value;
  }

  @XmlElement (name = "_triedToApplyCorrection")
  public boolean getTriedToApplyCorrection () {
    return _triedToApplyCorrection;
  }

  public void setTriedToApplyCorrection (boolean value) {
    this._triedToApplyCorrection = value;
  }

  @XmlElement (name = "_appliedCorrection")
  public boolean getAppliedCorrection () {
    return _appliedCorrection;
  }

  public void setAppliedCorrection (boolean value) {
    this._appliedCorrection = value;
  }

  public static MathExpressionInfo getMathExpressionInfoFromXml (String sympy) throws QTIScoringRuntimeException {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance (MathExpressionInfo.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller ();
      return (MathExpressionInfo) jaxbUnmarshaller.unmarshal (new StringReader (sympy));
    } catch (JAXBException exp) {
      throw new QTIScoringRuntimeException (exp);
    }
  }

  public static String getXmlStringFromMathExpressionInfo (MathExpressionInfo info) throws QTIScoringRuntimeException {
    try (StringWriter foo = new StringWriter ()) {
      JAXBContext jaxbContext = JAXBContext.newInstance (MathExpressionInfo.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller ();
      jaxbMarshaller.marshal (info, foo);
      return foo.toString ();
    } catch (JAXBException | IOException exp) {
      throw new QTIScoringRuntimeException (exp);
    }
  }
}
