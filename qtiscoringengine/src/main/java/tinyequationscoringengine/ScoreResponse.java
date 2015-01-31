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
