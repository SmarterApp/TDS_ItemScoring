/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;


public class DEError extends DataElement {
	public DEError(String message) {
		_baseType = BaseType.Null;
		_errorMessage = message;
	}

	@Override
	public String getStringValue() {
		return "null";
	}

	@Override
	public boolean equals(DataElement d) {
		return (d.getType() == BaseType.Null);
	}

	public Object getValue() {
		return null;
	}
}
