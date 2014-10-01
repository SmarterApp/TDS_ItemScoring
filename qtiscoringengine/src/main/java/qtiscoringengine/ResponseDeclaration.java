/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathFactory;
import org.junit.runner.manipulation.Filter;

import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class ResponseDeclaration extends VariableDeclaration
{
  private List<DataElement> _correctResponse = null;
  private VariableMapping   _mapping         = null;
  private AreaMapping       _areaMapping     = null;

  private ResponseDeclaration (DEIdentifier identifier, Cardinality cardinality, BaseType baseType, List<DataElement> correctResponses, List<DataElement> defaultValues, VariableMapping map,
      AreaMapping areaMapping, Element node) {
    super (identifier, cardinality, baseType, node);
    _identifier = identifier;
    _cardinality = cardinality;
    _baseType = baseType;
    _correctResponse = correctResponses;
    _defaultValues = defaultValues;
    _mapping = map;
    _areaMapping = areaMapping;
    _role = VariableRole.Response;
  }

  static List<ResponseDeclaration> getBuiltInResponseDeclarations () {
    List<ResponseDeclaration> builtIns = new ArrayList<ResponseDeclaration> ();
    ResponseDeclaration rd = new ResponseDeclaration (new DEIdentifier ("numAttempts"), Cardinality.Single, BaseType.Integer, null, Arrays.asList (new DataElement[] { new DEInteger (0) }), null,
        null, null);
    builtIns.add (rd);
    return builtIns;
  }

  static ResponseDeclaration fromXML (Element element, XmlNamespaceManager nsmgr, ValidationLog log) throws Exception {
    // todo: Errorcheck
    String identifier = element.getAttributeValue ("identifier");
    String cardinality = element.getAttributeValue ("cardinality");
    String basetype = element.getAttributeValue ("baseType");

    // XmlNode defaultVal =
    // element.SelectSingleNode(QTIXmlConstants.DefaultValue,nsmgr);
    // XmlNode correctResponse =
    // element.SelectSingleNode(QTIXmlConstants.CorrectResponse, nsmgr);
    // XmlElement mappingNode =
    // (XmlElement)element.SelectSingleNode(QTIXmlConstants.Mapping, nsmgr);
    // XmlElement areaMappingNode =
    // (XmlElement)element.SelectSingleNode(QTIXmlConstants.AreaMapping, nsmgr);
    XmlElement ele = new XmlElement (element);
    Element defaultVal = ele.selectSingleNode (QTIXmlConstants.DefaultValue, nsmgr);
    Element correctResponse = ele.selectSingleNode (QTIXmlConstants.CorrectResponse, nsmgr);
    Element mappingNode = ele.selectSingleNode (QTIXmlConstants.Mapping, nsmgr);
    Element areaMappingNode = ele.selectSingleNode (QTIXmlConstants.AreaMapping, nsmgr);

    BaseType bt = QTIUtility.basetypeFromXML (basetype);
    List<DataElement> de = QTIUtility.getValueListFromXML (defaultVal, bt, nsmgr);
    List<DataElement> correct = QTIUtility.getValueListFromXML (correctResponse, bt, nsmgr);
    VariableMapping mapping = VariableMapping.fromXML (mappingNode, bt, nsmgr, log);
    AreaMapping areaMapping = AreaMapping.fromXML (areaMappingNode, nsmgr, log);

    // check de and correct to see if there were any errors and log them
    for (int i = 0; i < de.size (); i++) {
      if (de.get (i).getIsError ()) {
        log.addMessage (defaultVal, de.get (i).getErrorMessage ());
        de.remove (i);
        i--;
      }
    }
    for (int i = 0; i < correct.size (); i++) {
      if (correct.get (i).getIsError ()) {
        log.addMessage (correctResponse, correct.get (i).getErrorMessage ());
        correct.remove (i);
        i--;
      }
    }

    DataElement deID = DataElement.create (identifier, BaseType.Identifier);
    DEIdentifier id = (DEIdentifier) deID;

    Cardinality card = QTIUtility.cardinalityFromXML (cardinality);
    BaseType b = QTIUtility.basetypeFromXML (basetype);

    return new ResponseDeclaration (id, card, b, correct, de, mapping, areaMapping, element);
  }

  boolean validate (QTIRubric rubric, ValidationLog log) {
    boolean ok = true;
    if (_identifier == null || _identifier.getIsError ()) {
      ok = false;
      log.addMessage (_node, "Error with Identifier: " + _identifier.getErrorMessage ());
    }
    if (_cardinality == Cardinality.None) {
      ok = false;
      log.addMessage (_node, "Cardinality was not specified or could not be parsed");
    }
    if (_areaMapping != null && !_areaMapping.validate (rubric, log))
      ok = false;
    if (_mapping != null && !_mapping.validate (rubric, log))
      ok = false;

    return ok;
  }

  DataElement getCorrectValue () {
    if (_cardinality == Cardinality.Single) {
      if (_correctResponse.size () > 0)
        return _correctResponse.get (0);
    } else {
      DEContainer de = new DEContainer (_baseType, _cardinality);
      for (DataElement entry : _correctResponse) {
        de.add (entry);
      }
      return de;
    }
    return null;
  }

  Double getScore (String answer, boolean ignoreCase) {
    Double returnVal = null;
    if (_mapping == null)
      return returnVal;
    MapEntry entry = _mapping.findEntry (answer, ignoreCase);
    if (entry != null)
      returnVal = entry.getValue ().getValue ();
    return returnVal;
  }

  Double getScore (DataElement de) {
    Double returnVal = null;
    if (_mapping == null)
      return returnVal;
    MapEntry entry = _mapping.findEntry (de);
    if (entry != null)
      returnVal = entry.getValue ().getValue ();
    return returnVal;
  }

  public VariableMapping getMapping () {
    return _mapping;
  }

  public AreaMapping getAreaMapping () {
    return _areaMapping;
  }
}
