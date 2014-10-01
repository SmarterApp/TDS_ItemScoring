/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
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

import org.apache.commons.io.output.XmlStreamWriter;
import org.apache.commons.lang.NotImplementedException;
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
@SuppressWarnings("restriction")
/*
 * <ItemScoreResponse> <Score> <ScoreInfo scorePoint="1" scoreMaxPoint="4"
 * scoreDimension="Default" scoreStatus="Scored" />
 * <ScoreRationale><![CDATA[Output of Scoring Engine]]></ScoreRationale>
 * <SubScoreList/> </ScoreInfo> <ContextToken><![CDATA[xxxxxxx]]></ContextToken>
 * </Score> </ItemScoreResponse>
 */
@XmlRootElement(name = "ItemScoreResponse")
public class ItemScoreResponse implements IXmlSerializable {
	private ItemScore _score = null;
	@SuppressWarnings("unused")
	private Logger _logger = LoggerFactory.getLogger(ItemScoreResponse.class);

	public ItemScoreResponse() {
	}

	public ItemScoreResponse(ItemScore score, String contextToken) {
		score.setContextToken(contextToken);
		setScore(score);
	}

	/**
	 * @return the _score
	 */
	@XmlElement(name = "Score")
	public ItemScore getScore() {
		return _score;
	}

	/**
	 * @param _score
	 *            the _score to set
	 */
	public void setScore(ItemScore value) {
		_score = value;
	}

	@Override
	public void readXML(XmlReader reader) {
		throw new NotImplementedException();
	}

	@Override
	@Deprecated
	// TODO Shiva: this is not used right now. The problem is that we are now
	// using JAxB. The original
	// HttpWebHelper from shared-web uses IXMLSerializable. We right now have
	// copied over HttpWebHelper and
	// changed it to just accept ItemScoreResponse. We need to reconcile with
	// what is there in
	// shared-web. One reason why I decided not to move the following code to
	// shared-web is because of the use
	// of the possibly "access restricted" class as commented below.
	public void writeXML(XMLStreamWriter out) throws XMLStreamException {
		try {
			Marshaller jaxbMarshaller = JAXBContext.newInstance(
					ItemScoreResponse.class).createMarshaller();
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

			jaxbMarshaller.setProperty(
					"com.sun.xml.bind.characterEscapeHandler",
					new CharacterEscapeHandler() {
						@Override
						public void escape(char[] ac, int i, int j,
								boolean flag, Writer writer) throws IOException {
							// do not escape
							writer.write(ac, i, j);
						}
					});

			jaxbMarshaller.marshal(this, out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_logger.error("Exception writing ItemScoreResponse", e);
			throw new XMLStreamException(e);
		}
	}

	public void writeXML(OutputStream out) throws XMLStreamException {
		try {
			Marshaller jaxbMarshaller = JAXBContext.newInstance(
					ItemScoreResponse.class).createMarshaller();
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

			jaxbMarshaller.setProperty(
					"com.sun.xml.bind.characterEscapeHandler",
					new CharacterEscapeHandler() {
						@Override
						public void escape(char[] ac, int i, int j,
								boolean flag, Writer writer) throws IOException {
							// do not escape
							writer.write(ac, i, j);
						}
					});

			jaxbMarshaller.marshal(this, out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_logger.error("Exception writing ItemScoreResponse", e);
			throw new XMLStreamException(e);
		}
	}

	
	public static ItemScoreResponse getInstanceFromXml(String xml) throws JAXBException 
	{
		Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(ItemScoreResponse.class).createUnmarshaller();
		return  (ItemScoreResponse)jaxbUnmarshaller.unmarshal(new StringReader(xml));
	}
	
	public static void main(String[] argv) {
		try {
			BufferedReader bfr = new BufferedReader(
					new FileReader(
							"C:/Install/springsource 3.3/WorkSpace/workspace-sts-3.3.0.RELEASE/itemscoringdev/docs/ScoringResponseBindings.txt"));
			StringBuilder builder = new StringBuilder();
			String line = "";
			while ((line = bfr.readLine()) != null)
				builder.append(line);

			bfr.close();

			Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(
					ItemScoreResponse.class).createUnmarshaller();

			ItemScoreResponse response = ItemScoreResponse.getInstanceFromXml(builder.toString());
			System.err.println("test");
			response.writeXML(System.err);
		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}
}
