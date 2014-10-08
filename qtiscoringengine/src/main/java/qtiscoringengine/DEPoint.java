/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import AIR.Common.Utilities.TDSStringUtils;

class DEPoint extends DataElement {
	private int _x;
	private int _y;

	DEPoint(int x, int y) {
		_baseType = BaseType.Point;
		_x = x;
		_y = y;
	}

	String getValue() {
		return TDSStringUtils.format("{0} {1}", _x, _y);
	}

	int getX() {
		return _x;
	}

	int getY() {
		return _y;
	}

	@Override
	public boolean equals(DataElement d) {
		if (d.getType() == this.getType()) {
			DEPoint dep = (DEPoint) d;
			return (getX() == dep.getX() && getY() == dep.getY());
		}
		return false;
	}

	@Override
	public String getStringValue() {
		return getValue();
	}
}
