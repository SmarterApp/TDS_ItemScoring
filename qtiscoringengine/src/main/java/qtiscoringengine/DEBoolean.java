/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;


class DEBoolean extends DataElement {
	private boolean _value;

	DEBoolean(boolean id) {
		_baseType = BaseType.Boolean;
		_value = id;
	}

	boolean getValue() {

		return _value;
	}

	@Override
	public boolean equals(DataElement d) {
		if (d.getType() == this.getType()) {
			return (_value == ((DEBoolean) d).getValue());
		}
		return false;
	}

	@Override
	public String getStringValue() {
		return ((Boolean) _value).toString();
	}
}
