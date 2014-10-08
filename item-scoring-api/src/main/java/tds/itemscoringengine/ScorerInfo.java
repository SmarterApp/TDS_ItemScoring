/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

/**
 * @author temp_rreddy
 * 
 */
public class ScorerInfo
{
  private String              _version;
  private boolean             _supportsAsyncMode;
  private boolean             _supportsRubricCaching;
  private RubricContentSource _rubricContentSource;

  public ScorerInfo (String version, boolean supportsAsyncMode, boolean supportsRubricCaching, RubricContentSource rubricContentSource) {
    this._version = version;
    this._supportsAsyncMode = supportsAsyncMode;
    this._supportsRubricCaching = supportsRubricCaching;
    this._rubricContentSource = rubricContentSource;
  }

  /**
   * @return the _version
   */
  public String getVersion () {
    return _version;
  }

  /**
   * @return the _supportsAsyncMode
   */
  public boolean isSupportsAsyncMode () {
    return _supportsAsyncMode;
  }

  /**
   * @param _supportsAsyncMode
   *          the _supportsAsyncMode to set
   */
  /**
   * @return the _supportsRubricCaching
   */
  public boolean isSupportsRubricCaching () {
    return _supportsRubricCaching;
  }

  /**
   * @return the _rubricContentSource
   */
  public RubricContentSource getRubricContentSource () {
    return _rubricContentSource;
  }
  
  //TODO Shiva: should this be abstract
  public String getDetails () {
    return "N/A";
  }

  protected void setVersion (String value) {
    _version = value;
  }
}
