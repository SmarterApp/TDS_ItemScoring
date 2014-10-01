/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathFactory;


public class JDomTest
{
  public static void main (String[] args) throws JDOMException, IOException {
    SAXBuilder builder = new SAXBuilder();
    File xmlFile = new File("C:\\Users\\qyang\\Desktop\\QTIScoringEngineTesting\\choice.xml");
    
    Document document = (Document) builder.build(xmlFile);
    Element rootNode = document.getRootElement();
    System.out.println(rootNode.getName ());
    Namespace ns = Namespace.getNamespace("qti", "http://www.imsglobal.org/xsd/imsqti_v2p1");
    List<Element> nodes = XPathFactory.instance ().compile ("qti:responseDeclaration", Filters.element (), null, ns).evaluate(rootNode);
  
    for (Element n : nodes) {
      System.out.println(n.getName ());
    }
  }
}
