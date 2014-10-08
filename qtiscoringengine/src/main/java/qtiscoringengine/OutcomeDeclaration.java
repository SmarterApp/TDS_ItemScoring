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
import java.util.List;

import org.jdom2.Element;

import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class OutcomeDeclaration extends VariableDeclaration
{
  private LookupTable _lookupTable;

  // todo: implement all the other optional stuff

  private OutcomeDeclaration (DEIdentifier id, Cardinality card, BaseType bt, DataElement defaultValue, LookupTable lt, Element node)
  {
    super (id, card, bt, node);
    _identifier = id;
    _cardinality = card;
    _baseType = bt;
    _defaultValues = new ArrayList<DataElement> ();
    _defaultValues.add (defaultValue);
    _lookupTable = lt;
    _role = VariableRole.Outcome;
  }

  static List<OutcomeDeclaration> getBuiltInOutcomeDeclarations ()
  {
    List<OutcomeDeclaration> builtIns = new ArrayList<OutcomeDeclaration> ();
    OutcomeDeclaration od = new OutcomeDeclaration (new DEIdentifier ("completionStatus"), Cardinality.Single, BaseType.Identifier, new DEIdentifier ("not_attempted"), null, null);
    builtIns.add (od);
    od = new OutcomeDeclaration (new DEIdentifier ("completion_status"), Cardinality.Single, BaseType.Identifier, new DEIdentifier ("not_attempted"), null, null);
    builtIns.add (od);

    return builtIns;
  }

  static OutcomeDeclaration fromXML (Element element, XmlNamespaceManager nsmgr, ValidationLog log)
  {
    String identifier = element.getAttributeValue ("identifier");
    String cardinality = element.getAttributeValue ("cardinality");
    String basetype = element.getAttributeValue ("baseType");

    // XmlNode defaultVal =
    // element.SelectSingleNode(QTIXmlConstants.DefaultValue, nsmgr);
    // XmlNode lookupTableNode =
    // element.SelectSingleNode(QTIXmlConstants.MatchTable, nsmgr);
    Element defaultVal = new XmlElement (element).selectSingleNode (QTIXmlConstants.DefaultValue, nsmgr);
    Element lookupTableNode = new XmlElement (element).selectSingleNode (QTIXmlConstants.MatchTable, nsmgr);

    if (lookupTableNode == null)
      lookupTableNode = new XmlElement (element).selectSingleNode (QTIXmlConstants.InterpolationTable, nsmgr);

    LookupTable lt = LookupTable.fromXML (lookupTableNode, nsmgr, log);
    BaseType bt = QTIUtility.basetypeFromXML (basetype);
    DataElement de = QTIUtility.getSingleValueFromXML (defaultVal, bt, nsmgr);

    DataElement deID = DataElement.create (identifier, BaseType.Identifier);
    if (deID == null)
    {
      log.addMessage (element, "Required attribute Identifier not specified in element " + element.getName ());
      return null;
    }
    if (deID.getIsError ())
    {
      log.addMessage (element, "Error in Identifier for element " + element + " " + deID.getErrorMessage ());
      return null;
    }
    DEIdentifier id = (DEIdentifier) deID;

    Cardinality card = QTIUtility.cardinalityFromXML (cardinality);
    if (card == Cardinality.None)
    {
      log.addMessage (element, "Cardinality not specified or not recognized for element " + element.getName ());
      return null;
    }

    return new OutcomeDeclaration (id, card, bt, de, lt, element);
  }
}
