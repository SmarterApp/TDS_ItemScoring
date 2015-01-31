package tinytablescoringengine;

import qtiscoringengine.QTIScoringException;

public class TinyTableScoringException extends QTIScoringException
{
  public TinyTableScoringException (String msg) {
    super (msg);
  }

  public TinyTableScoringException (String message, Throwable th) {
    super (message, th);
  }

  public TinyTableScoringException (Throwable th) {
    super (th);
  }
}
