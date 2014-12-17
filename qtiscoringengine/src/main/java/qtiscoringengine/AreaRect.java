/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

public class AreaRect extends Area {
	private DEPoint _topLeft;
    private DEPoint _bottomRight;

    AreaRect(String coords) throws Exception
    {
    	super(Shape.Rect);
        //to do error check
        DataElement dec = DataElement.createContainer(coords, BaseType.Point, Cardinality.Ordered);
        if (dec.getIsError())
            throw new Exception(dec.getErrorMessage());
        DEContainer container = (DEContainer)dec;
        _topLeft = (DEPoint)container.getMember(0);
        _bottomRight = (DEPoint)container.getMember(1); //December 10 2014. Fixed bug. Changed getMember(0) to getMember(1)
    }

	@Override
	DEBoolean getIsInside(DEPoint point) {
		if (point == null) return new DEBoolean(false);
        return new DEBoolean((point.getX() >= _topLeft.getX())
                              && (point.getX() <= _bottomRight.getX())
                              && (point.getY() <= _topLeft.getY())
                              && (point.getY() >= _bottomRight.getY()));
	}

}
