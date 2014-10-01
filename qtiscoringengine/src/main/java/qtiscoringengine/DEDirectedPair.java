/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;


class DEDirectedPair extends DEPair {
	DEDirectedPair(String v1, String v2) {
		super(v1, v2);
		_baseType = BaseType.DirectedPair;
	}

	@Override
	public boolean equals(DataElement d) {
		if (d.getType() == this.getType()) {
			DEPair dep = (DEPair) d;
			return (getValue1().equals(dep.getValue1()) && (getValue2()
					.equals(dep.getValue2())));
		}
		return false;
	}
}
