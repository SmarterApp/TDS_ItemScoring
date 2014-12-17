/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

public class DEFloat extends _DEFloat<Double> {
	public enum RoundingMode {
		SignificantFigures, DecimalPlaces
	};

	private double _value;

	public DEFloat(double id) {
		_baseType = BaseType.Float;
		_value = id;
	}

	public boolean Lt(double value) {
		return (getValue() < value);
	}

	public boolean Gt(double value) {
		return (getValue() > value);
	}

	public boolean Lte(double value) {
		return (getValue() <= value);
	}

	public boolean Gte(double value) {
		return (getValue() >= value);
	}

	// public static explicit operator DEFloat(DEInteger d)
	// {
	// return new DEFloat(d.Value);
	// }
	// public static DEFloat fromDEInteger (DEInteger d)
	// {
	// return new DEFloat (d.getValue ());
	// }

	public Double getValue() {
		return _value;
	}

	@Override
	public boolean equals(DataElement d) {
		if (d.getType() == this.getType()) {
			return (_value == ((DEFloat) d).getValue());
		}
		return false;
	}

	// public double Round(RoundingMode _roundingMode, int figures)
	// {
	// if (_roundingMode.equals(RoundingMode.DecimalPlaces))
	// {
	// return 0.0;
	// }
	// return 0.0;
	// }
	// public double RoundToDecimals(int figures)
	// {
	// return Math.round(getValue(), figures);
	// }

	@Override
	public String getStringValue() {
		return Double.toString(_value);
	}
}
