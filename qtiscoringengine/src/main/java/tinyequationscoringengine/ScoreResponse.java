/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinyequationscoringengine;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoreResponse
{
  private String _result;
  private String _exception;

  @JsonProperty ("result")
  public String getResult () {
    return _result;
  }

  public void setResult (String _result) {
    this._result = _result;
  }

  @JsonProperty ("exception")
  public String getException () {
    return _exception;
  }

  public void setException (String _exception) {
    this._exception = _exception;
  }
}
