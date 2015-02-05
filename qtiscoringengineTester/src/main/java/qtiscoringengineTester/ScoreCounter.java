package qtiscoringengineTester;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

enum LogType {
  correct, incorrect, error
};

public class ScoreCounter
{
  private static final Logger                 _logger         = LoggerFactory.getLogger (ScoreCounter.class);
  private int                                 _incorrect      = 0;
  private int                                 _correct        = 0;
  private int                                 _exception      = 0;
  private int                                 _missingRubrics = 0;

  private Map<LogType, Map<Integer, Integer>> _histogram      = new HashMap<LogType, Map<Integer, Integer>> ();

  public synchronized void incrementIncorrect (int realScore) {
    ++_incorrect;
    addToMapForRealScore (realScore, LogType.incorrect);
    logTotal ();
  }

  public synchronized void incrementCorrect (int realScore) {
    ++_correct;
    addToMapForRealScore (realScore, LogType.correct);
    logTotal ();
  }

  public synchronized void incrementErrors (int realScore) {
    ++_exception;
    addToMapForRealScore (realScore, LogType.error);
    logTotal ();
  }

  public synchronized void incrementMissingRubrics () {
    ++_missingRubrics;
    logTotal ();
  }

  public int getIncorrect () {
    return _incorrect;
  }

  public int getCorrect () {
    return _correct;
  }

  public int getErrors () {
    return _exception;
  }

  private void logTotal () {
    // TODO Shiva: calculating everytime is inefficient.
    int total = getIncorrect () + getCorrect () + getErrors ();
    if (total % 2000 == 0) {
      _logger.error (this.toString ());
    }
  }

  @Override
  public String toString () {
    StringBuilder outputBuilder = new StringBuilder ();
    outputBuilder.append ("======================Stats====================\n");
    outputBuilder.append (String.format ("Correct: %d ; Incorrect: %d ; Errors : %d\n", this.getCorrect (), this.getIncorrect (), this.getErrors ()));
    outputBuilder.append (String.format ("Missing rubrics: %d\n", _missingRubrics));
    for (Map.Entry<LogType, Map<Integer, Integer>> entries : _histogram.entrySet ()) {
      LogType key = entries.getKey ();
      for (Map.Entry<Integer, Integer> record : entries.getValue ().entrySet ()) {
        outputBuilder.append (String.format ("Type: %s ; Score : %d ; Counter : %d\n", key.name (), record.getKey (), record.getValue ()));
      }
    }

    return outputBuilder.toString ();
  }

  /*
   * for type : correct, incorrect or error we want to keep track of the
   * distribution of scores.
   */
  private void addToMapForRealScore (int realScore, LogType type) {
    if (!_histogram.containsKey (type))
      _histogram.put (type, new HashMap<Integer, Integer> ());

    Map<Integer, Integer> scoreHistogram = _histogram.get (type);

    int scoreTracker = 0;
    if (scoreHistogram.containsKey (realScore))
      scoreTracker = scoreHistogram.get (realScore);

    scoreTracker++;
    scoreHistogram.put (realScore, scoreTracker);
  }
}
