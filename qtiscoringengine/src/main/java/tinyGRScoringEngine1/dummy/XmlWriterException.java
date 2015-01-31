/*******************************************************************************
   * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinyGRScoringEngine1.dummy;

/**
 * @author jmambo
 *
 */
public class XmlWriterException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public XmlWriterException (Exception exp) {
      super (exp);
    }

    public XmlWriterException (String message, Throwable e) {
        super (message, e);
    }

}
