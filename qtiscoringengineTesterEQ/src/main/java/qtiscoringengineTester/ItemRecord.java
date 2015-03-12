package qtiscoringengineTester;

public class ItemRecord
{
  String _itemID;
  double _duration;
  int    _responseCount;
  int    _scoreMatch;
  int    _scoreMismatch;
  int    _scoringError;
  int[]  _scoringPointFreq;

  public ItemRecord (String itemID)
  {
    this._itemID = itemID;
    _duration = 0;
    _responseCount = 0;
    _scoreMatch = 0;
    _scoreMismatch = 0;
    _scoringError = 0;
    _scoringPointFreq = new int[10];
  }

  public void updateValues (int score, double timetoScore, boolean correct)
  {
    _responseCount++;
    if (correct)
      _scoreMatch++;
    else
    {
      _scoreMismatch++;
      _scoringError++;
    }
    _scoringPointFreq[score]++;
    _duration = +timetoScore;
  }

  public String getItemId ()
  {
    return _itemID;
  }

  public double getDuration ()
  {
    return _duration / _responseCount;
  }

  public int getResponseCount ()
  {
    return _responseCount;
  }

  public int getScoreMatch ()
  {
    return _scoreMatch;
  }

  public int getScoreMismatch ()
  {
    return _scoreMismatch;
  }

  public int getScoringError ()
  {
    return _scoringError;
  }

  public int[] getScoringPointFreq ()
  {
    return _scoringPointFreq;
  }
}
