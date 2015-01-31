/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinygrscoringengine;

import qtiscoringengine.QTIScoringException;

/**
 * @author temp_mbikkina
 *
 */
public class TinyGRException extends QTIScoringException
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public int                _code            = 0;
  public String             _coderMessage    = "";
  public String             _description     = "undescribed";

  public TinyGRException (int code, String coderMessage) {
    super (code, coderMessage);
    _code = code;
    _coderMessage = coderMessage;
    _description = QTIScoringException.getDescription (_code);
  }

  public TinyGRException (Throwable th) {
    super (th);
  }
}
