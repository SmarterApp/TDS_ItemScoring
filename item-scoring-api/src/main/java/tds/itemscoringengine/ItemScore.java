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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import AIR.Common.xml.AdapterXmlCData;

/**
 * @author temp_rreddy
 * 
 */
@XmlRootElement (name = "Score")
public class ItemScore
{
  private ItemScoreInfo _scoreInfo;
  private String        _contextToken;
  private long          _scoreLatency;

  // Properties stored here
  @XmlElement (name = "ScoreInfo")
  public ItemScoreInfo getScoreInfo () {
    return _scoreInfo;
  }

  public void setScoreInfo (ItemScoreInfo value) {
    _scoreInfo = value;
  }

  @XmlElement (name = "ContextToken")
  @XmlJavaTypeAdapter (AdapterXmlCData.class)
  public String getContextToken () {
    return _contextToken;
  }

  public void setContextToken (String value) {
    _contextToken = value;
  }

  public long getScoreLatency () {
    return _scoreLatency;
  }

  public void setScoreLatency (long _scoreLatency) {
    this._scoreLatency = _scoreLatency;
  }

  public ItemScore () {
  }

  public ItemScore (ItemScoreInfo scoreInfo, String contextToken) {
    setScoreInfo (scoreInfo);
    setContextToken (contextToken);
  }

  public ItemScore (int scorePoint, int maxScore, ScoringStatus status, String dimension, ScoreRationale rationale, String contextToken) {
    setScoreInfo (new ItemScoreInfo (scorePoint, maxScore, status, dimension, rationale));
    setContextToken (contextToken);
  }

  public ItemScore (int scorePoint, int maxScore, ScoringStatus status, String dimension, ScoreRationale rationale, List<ItemScoreInfo> subScores, String contextToken) {
    this (scorePoint, maxScore, status, dimension, rationale, contextToken);
    getScoreInfo ().setSubScores (subScores);
  }

}
