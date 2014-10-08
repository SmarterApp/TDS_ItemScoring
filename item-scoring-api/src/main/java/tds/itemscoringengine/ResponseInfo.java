/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import java.util.List;

/**
 * @author temp_rreddy
 * 
 */
public class ResponseInfo
{
  // / <summary>
  // / Item format
  // / </summary>
  private String            _itemFormat;

  // / <summary>
  // / Unique ID for the item (likely the ITS id)
  // / </summary>
  private String            _itemIdentifier;

  // / <summary>
  // / Student response
  // / </summary>
  private String            _studentResponse;

  // / <summary>
  // / Specifies whether the student's response is encrypted or not
  // / </summary>
  private boolean           _isStudentResponseEncrypted;

  // / <summary>
  // / Rubric information for the scorer
  // / </summary>
  private Object            _rubric;

  // / <summary>
  // / Placeholder to associate add'l info related to this student response
  // (such as testeeid, position etc)
  // / </summary>
  private String            _contextToken;

  // / <summary>
  // / Specify if the rubric associated with this responseInfo can be cached by
  // the item scoring server
  // / </summary>
  private boolean           _canCacheRubric;

  // / <summary>
  // / Specifies if the rubric is encrypted or not
  // / </summary>
  private boolean           _isRubricEncrypted;

  // / <summary>
  // / Specifies whether the rubric is specified as a Uri or is the rubric
  // content inline
  // / </summary>
  private RubricContentType _contentType;

  private List<VarBinding>  _incomingBindings;
  private List<VarBinding>  _outgoingBindings;

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#getItemFormat()
   */
  public String getItemFormat () {
    return _itemFormat;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#getItemIdentifier()
   */
  public String getItemIdentifier () {
    return _itemIdentifier;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#getStudentResponse()
   */
  public String getStudentResponse () {
    return _studentResponse;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.itemscoringengine.IResponseInfo#setStudentResponse(java.lang.String)
   */
  public void setStudentResponse (String _studentResponse) {
    this._studentResponse = _studentResponse;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#isStudentResponseEncrypted()
   */
  public boolean isStudentResponseEncrypted () {
    return _isStudentResponseEncrypted;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.itemscoringengine.IResponseInfo#setIsStudentResponseEncrypted(boolean)
   */
  public void setStudentResponseEncrypted (boolean _isStudentResponseEncrypted) {
    this._isStudentResponseEncrypted = _isStudentResponseEncrypted;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#getRubric()
   */
  public Object getRubric () {
    return _rubric;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#setRubric(java.lang.Object)
   */
  public void setRubric (Object _rubric) {
    this._rubric = _rubric;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#getContextToken()
   */
  public String getContextToken () {
    return _contextToken;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#setContextToken(java.lang.String)
   */
  public void setContextToken (String _contextToken) {
    this._contextToken = _contextToken;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#isCanCacheRubric()
   */
  public boolean isCanCacheRubric () {
    return _canCacheRubric;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#setCanCacheRubric(boolean)
   */
  public void setCanCacheRubric (boolean _canCacheRubric) {
    this._canCacheRubric = _canCacheRubric;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#isRubricEncrypted()
   */
  public boolean isRubricEncrypted () {
    return _isRubricEncrypted;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#setIsRubricEncrypted(boolean)
   */
  public void setRubricEncrypted (boolean _isRubricEncrypted) {
    this._isRubricEncrypted = _isRubricEncrypted;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#getContentType()
   */
  public RubricContentType getContentType () {
    return _contentType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.itemscoringengine.IResponseInfo#setContentType(tds.itemscoringengine
   * .RubricContentType)
   */
  public void setContentType (RubricContentType _contentType) {
    this._contentType = _contentType;
  }

  public List<VarBinding> getIncomingBindings () {
    return _incomingBindings;
  }

  public void setIncomingBindings (List<VarBinding> value) {
    _incomingBindings = value;
  }

  public List<VarBinding> getOutgoingBindings () {
    return _outgoingBindings;
  }

  public void setOutgoingBindings (List<VarBinding> value) {
    _outgoingBindings = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.itemscoringengine.IResponseInfo#isComplete()
   */
  public boolean isComplete () {
    // all fields EXCEPT context token are mandatory
    return (_itemFormat != null && _itemIdentifier != null && _studentResponse != null && _rubric != null);
  }

  public ResponseInfo (String itemFormat, String itemID, String studentResponse, Object rubric, RubricContentType contentType, String contextToken, boolean allowRubricCaching) {
    this._itemFormat = itemFormat;
    this._itemIdentifier = itemID;
    this._studentResponse = studentResponse;
    this._rubric = rubric;
    this._contentType = contentType;
    this._contextToken = contextToken;
    this._canCacheRubric = allowRubricCaching;
  }
}
