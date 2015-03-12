/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.output.XmlStreamWriter;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AIR.Common.xml.IXmlSerializable;
import AIR.Common.xml.TdsXmlOutputFactory;
import AIR.Common.xml.XmlReader;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

;

/**
 * @author temp_rreddy
 * 
 */
@SuppressWarnings ("restriction")
/*
 * <ItemScoreResponse> <Score> <ScoreInfo scorePoint="1" scoreMaxPoint="4"
 * scoreDimension="Default" scoreStatus="Scored" />
 * <ScoreRationale><![CDATA[Output of Scoring Engine]]></ScoreRationale>
 * <SubScoreList/> </ScoreInfo> <ContextToken><![CDATA[xxxxxxx]]></ContextToken>
 * </Score> </ItemScoreResponse>
 */
@XmlRootElement (name = "ItemScoreResponse")
public class ItemScoreResponse implements IXmlSerializable
{
  private ItemScore _score  = null;
  @SuppressWarnings ("unused")
  private Logger    _logger = LoggerFactory.getLogger (ItemScoreResponse.class);

  public ItemScoreResponse () {
  }

  public ItemScoreResponse (ItemScore score, String contextToken) {
    score.setContextToken (contextToken);
    setScore (score);
  }

  /**
   * @return the _score
   */
  @XmlElement (name = "Score")
  public ItemScore getScore () {
    return _score;
  }

  /**
   * @param _score
   *          the _score to set
   */
  public void setScore (ItemScore value) {
    _score = value;
  }

  @Override
  public void readXML (XmlReader reader) {
    throw new NotImplementedException ();
  }

  @Override
  @Deprecated
  public void writeXML (XMLStreamWriter out) throws XMLStreamException {
    writeXmlInternal (out);
  }

  public void writeXML (OutputStream out) throws XMLStreamException {
    writeXmlInternal (out);
  }

  public static ItemScoreResponse getInstanceFromXml (String xml) throws JAXBException {
  
    int indexOfBegin = xml.indexOf ('<');
    if (indexOfBegin > 0)
      xml = xml.substring (indexOfBegin);
    
    Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance (ItemScoreResponse.class).createUnmarshaller ();
    return (ItemScoreResponse) jaxbUnmarshaller.unmarshal (new StringReader (xml));
  }

  // TODO Shiva: this is not used right now. The problem is that we are now
  // using JAxB. The original
  // HttpWebHelper from shared-web uses IXMLSerializable. We right now have
  // copied over HttpWebHelper and
  // changed it to just accept ItemScoreResponse. We need to reconcile with
  // what is there in
  // shared-web. One reason why I decided not to move the following code to
  // shared-web is because of the use
  // of the possibly "access restricted" class as commented below.
  private void writeXmlInternal (Object out) throws XMLStreamException {
    try {
      Marshaller jaxbMarshaller = JAXBContext.newInstance (ItemScoreResponse.class).createMarshaller ();
      // Shiva: We do not want to escape CDATA. JaxB does not internally
      // support
      // it.
      // Here is an alternate way to achieve the same thing:
      // http://odedpeer.blogspot.com/2010/07/jaxb-sun-and-how-to-marshal-cdata.html
      // However this is a sun internal class and we probably should not
      // be
      // using. TODO. There may be "access restriction" issues:
      // http://stackoverflow.com/questions/860187/access-restriction-on-class-due-to-restriction-on-required-library-rt-jar
      // http://stackoverflow.com/questions/16653519/jaxb-marshal-setproperty-com-sun-xml-bind-characterescapehandler

      jaxbMarshaller.setProperty ("com.sun.xml.bind.characterEscapeHandler", new CharacterEscapeHandler ()
      {
        @Override
        public void escape (char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
          if (ac != null && i < ac.length && (i + j) <= ac.length) {
            StringBuilder incomingStringBuilder = new StringBuilder ();
            for (int counter1 = i; counter1 < i + j; ++counter1) {
              incomingStringBuilder.append (ac[counter1]);
            }

            String incomingString = incomingStringBuilder.toString ();
            final String BEGIN_CDATA = "<![CDATA[";
            final String END_CDATA = "]]>";

            StringBuilder outgoingString = new StringBuilder ();
            // there may be multiple CDATA sections. first lets find the index
            // of "<![CDATA[".
            // Shiva: Assumptions 1) the CDATA section is whole i.e. we will not
            // see something like "abc <!CDATA[ x y z". instead we will see
            // "abc <!CDATA[ x y z ]]> m n o p"
            // 2) CDATA may be anywhere. 3) there may be multiple CDATA segments
            // but each will be whole.
            int currentIndex = 0;
            while (currentIndex < incomingString.length ()) {
              int indexOfBeginCdata = incomingString.indexOf (BEGIN_CDATA, currentIndex);
              if (indexOfBeginCdata >= 0) {
                if (indexOfBeginCdata != currentIndex) {
                  // we will copy everything upto the begining of CDATA and
                  // escape it.
                  String substr = incomingString.substring (currentIndex, indexOfBeginCdata);
                  outgoingString.append (StringEscapeUtils.escapeXml (substr));
                }
                // lets move the current index to at least after the match.
                currentIndex = indexOfBeginCdata + BEGIN_CDATA.length ();
                // do we have a end cdata
                int indexOfEndCData = incomingString.indexOf (END_CDATA, currentIndex);
                // because of our assumptions above we are not going to check if
                // this is < 0.
                outgoingString.append (incomingString.subSequence (indexOfBeginCdata, indexOfEndCData + END_CDATA.length ()));
                // move currentIndex again.
                currentIndex = indexOfEndCData + END_CDATA.length ();
              } else {
                // no more CDATA sections left.
                outgoingString.append (StringEscapeUtils.escapeXml (incomingString.substring (currentIndex, incomingString.length ())));
                break;
              }
            }
            writer.write (outgoingString.toString ());
            // do not escape
          } else {
            // TODO Shiva: if they are not within the range then I do not know
            // what to do. i will just let it go.
            writer.write (ac, i, j);
          }
        }
      });

      if (out instanceof OutputStream)
        jaxbMarshaller.marshal (this, (OutputStream) out);
      else if (out instanceof XMLStreamWriter)
        jaxbMarshaller.marshal (this, (XMLStreamWriter) out);
    } catch (JAXBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace ();
      _logger.error ("Exception writing ItemScoreResponse", e);
      throw new XMLStreamException (e);
    }
  }

  public static void main (String[] argv) {

    try {
      String strn = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ItemScoreResponse><Score><ContextToken><![CDATA[loGWu4Flp10lv2NusBnXcX4eDyT0DRGx2ZIznrvS98UbZr7kFaS5Z9gqNC-iZTo3WfXsEwAZkpQ8AmCZXhRdsHLuDAwPz2zt4rb_P36kgG0Q_7GZgp4Elgf7Trl5ahCr3FuzOc4lHBZFkkAFBdtnUGIErw3jd4-v8YaXZPQATK-WNYXiLAv3KE0to6hUetTsvp8FC4FjK97iKX5ANoUGG5V1ubW5WI1CYzz75Y3WOf5lmLMy1pkUA4UG3X7A7TmvDP12WD988FvPaLU65IItYkJUIqPAAEsZasRL2uZSsEimVAYdNV2pLTfL2WyPg5xVGZe0GijcblpXseeZgOjKDQvBF4suUayuYwV0Vge3ady0Dfl4FePh7wMIA-w96L_AoLNIkzGRDshebBC7YLqERQNoC8_ajPcdMuDfgtI3nMMU-HDuHg69btYIk2MfqdWD5HBGyyIM9A8O4qq9sx-TwjUkw1dznIdFl0XauT_ejyfMiD7o7bXDlb0UP2R4TRvtRcAcTHskhIFIsA_lhVv6iA2]]></ContextToken><ScoreInfo confLevel=\"0.0\" scoreDimension=\"overall\" maxScore=\"30\" scorePoint=\"2\" scoreStatus=\"Scored\"><ScoreRationale><StackTrace><![CDATA[]]></StackTrace><Message><![CDATA[]]></Message></ScoreRationale><SubScoreList><ScoreInfo confLevel=\"0.0\" scoreDimension=\"STATEMENT OF PURPOSE/FOCUS &amp; ORGANIZATION\" maxScore=\"10\" scorePoint=\"1\" scoreStatus=\"Scored\"><SubScoreList/></ScoreInfo><ScoreInfo confLevel=\"0.0\" scoreDimension=\"EVIDENCE/ELABORATION\" maxScore=\"10\" scorePoint=\"1\" scoreStatus=\"Scored\"><SubScoreList/></ScoreInfo><ScoreInfo confLevel=\"0.0\" scoreDimension=\"EDITING/CONVENTIONS\" maxScore=\"10\" scorePoint=\"0\" scoreStatus=\"Scored\"><SubScoreList/></ScoreInfo></SubScoreList></ScoreInfo><scoreLatency>0</scoreLatency></Score></ItemScoreResponse>";

      Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance (ItemScoreResponse.class).createUnmarshaller ();

      ItemScoreResponse response = ItemScoreResponse.getInstanceFromXml (strn);
      System.err.println ("test");
      response.writeXML (System.err);
    } catch (Exception exp) {
      exp.printStackTrace ();
    }

  }
}
