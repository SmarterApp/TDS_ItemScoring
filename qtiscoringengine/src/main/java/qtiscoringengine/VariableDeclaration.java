/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.List;

import org.jdom2.Element;

enum VariableRole {
	None, Response, Outcome
};

class VariableDeclaration {
	protected VariableRole _role = VariableRole.None;
	protected DEIdentifier _identifier;
	protected Cardinality _cardinality;
	protected BaseType _baseType;
	protected List<DataElement> _defaultValues = null;
	protected Element _node;

	protected VariableDeclaration(DEIdentifier identifier,
			Cardinality cardinality, BaseType baseType, Element node) {
		_identifier = identifier;
		_cardinality = cardinality;
		_baseType = baseType;
		_node = node;
	}

	VariableRole getVariableRole() {
		return _role;
	}

	String getIdentifier() {
		return _identifier.getValue();
	}

	public BaseType getType() {
		return _baseType;
	}

	Cardinality getCardinality() {
		return _cardinality;
	}

	DataElement getDefaultValue() {
		if (_defaultValues == null)
			return null;

		if (_cardinality == Cardinality.Single) {
			if (_defaultValues.size() > 0)
				return _defaultValues.get(0);
		} else {
			DEContainer de = new DEContainer(_baseType, _cardinality);
			for (DataElement entry : _defaultValues) {
				de.add(entry);
			}
			return de;
		}
		return null;
	}
}
