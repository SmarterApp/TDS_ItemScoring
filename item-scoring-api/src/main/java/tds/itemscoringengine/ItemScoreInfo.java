/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "ScoreInfo")
public class ItemScoreInfo
{

  private String              _confLevel;
  private int                 _points;
  private String              _dimension;
  private ScoringStatus       _status;
  private ScoreRationale      _rationale;
  private List<ItemScoreInfo> _subScores = new ArrayList<ItemScoreInfo> ();
  private int                 _maxScore;

  @XmlAttribute (name = "scorePoint")
  public int getPoints () {
    return _points;
  }

  public void setPoints (int value) {
    _points = value;
  }

  @XmlAttribute (name = "confLevel")
  public String getConfLevel () {
    return _confLevel;
  }

  public void setConfLevel (String value) {
    _confLevel = value;
  }

  // / <summary>
  // / Max score possible for this dimension
  // / </summary>
  @XmlAttribute (name = "maxScore")
  public int getMaxScore () {
    return _maxScore;
  }

  public void setMaxScore (int value) {
    _maxScore = value;
  }

  @XmlAttribute (name = "scoreDimension")
  public String getDimension () {
    return _dimension;
  }

  public void setDimension (String value) {
    _dimension = value;
  }

  @XmlAttribute (name = "scoreStatus")
  public ScoringStatus getStatus () {
    return _status;
  }

  public void setStatus (ScoringStatus value) {
    _status = value;
  }

  @XmlElement (name = "ScoreRationale")
  public ScoreRationale getRationale () {
    return _rationale;
  }

  public void setRationale (ScoreRationale value) {
    _rationale = value;
  }

  @XmlElementWrapper (name = "SubScoreList")
  @XmlElement (name = "ScoreInfo")
  public List<ItemScoreInfo> getSubScores () {
    return _subScores;
  }

  public void setSubScores (List<ItemScoreInfo> value) {
    _subScores = value;
  }

  public ItemScoreInfo () {
  }

  public ItemScoreInfo (int points, int maxPoints, ScoringStatus status, String dimension, ScoreRationale rationale) {
    _points = points;
    _maxScore = maxPoints;
    _status = status;
    _dimension = dimension;
    _rationale = rationale;
  }
}
