package qtiscoringengineTester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class LogProcessor
{
  private static final Logger _logger = Logger.getLogger (LogProcessor.class);
  Map<String, ItemRecord>     map     = new HashMap<String, ItemRecord> ();

  public void processLog (String filePath) throws IOException
  {
    BufferedReader logReader = new BufferedReader (new FileReader (filePath));
    String line = null;
    while ((line = logReader.readLine ()) != null)
    {
      if (!line.contains ("item") || !line.contains ("score") || !line.contains ("timeToScore(ms)"))
        continue;
      Matcher matcher = Pattern.compile ("([a-zA-Z_0-9()]+\\s*)=(\\s*\\d+)").matcher (line);
      String itemid = null;
      String score = null;
      String timeToScore = null;
      boolean correct = line.contains ("Correct") ? true : false;
      while (matcher.find ())
      {
        String keyName = matcher.group (1).trim ();
        String value = matcher.group (2).trim ();
        if (StringUtils.equals ("item", keyName))
          itemid = value;
        if (StringUtils.equals ("score", keyName) || StringUtils.equals ("realScore", keyName))
          score = matcher.group (2);
        if (StringUtils.equals ("timeToScore(ms)", keyName))
          timeToScore = value;
      }
      if (!StringUtils.isEmpty (itemid) && !StringUtils.isEmpty (score) && !StringUtils.isEmpty (timeToScore))
      {
        if (map.containsKey (itemid))
        {
          map.get (itemid).updateValues (Integer.parseInt (score), Double.parseDouble (timeToScore), correct);
        }
        else
        {
          ItemRecord record = new ItemRecord (itemid);
          record.updateValues (Integer.parseInt (score), Double.parseDouble (timeToScore), correct);
          map.put (itemid, record);

        }
      }
    }
  }

  public void writeToExcel (Map<String, String> itemFormat, String filePath) throws IOException, WriteException
  {
    HashSet<String> set = new HashSet<String> ();
    set.addAll (itemFormat.values ());
    
    File file = new File (filePath);
    WorkbookSettings wbSettings = new WorkbookSettings ();

    wbSettings.setLocale (new Locale ("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook (file, wbSettings);
    for (String s : set)
    {
      workbook.createSheet (s, 0);
      addHeader (workbook.getSheet (s));
    }
    for (String key : map.keySet ())
    {
      ItemRecord record = map.get (key);
      // TODO format will come from "Map<String, String> itemFormat", the
      // commented part
      String format = "HTQ";// itemFormat.get (record.getItemId ());
      WritableSheet sheet = workbook.getSheet (format);
      int row = sheet.getRows ();
      addRowToSheet (sheet, record, row);
    }
    workbook.write ();
    workbook.close ();
  }

  public void addHeader (WritableSheet sheet)
  {
    Label[] l = new Label[16];
    l[0] = new Label (0, 0, "Item ID");
    l[1] = new Label (1, 0, "Duration");
    l[2] = new Label (2, 0, "ResponseCount");
    l[3] = new Label (3, 0, "ScoreMatch");
    l[4] = new Label (4, 0, "ScoreMismatch");
    l[5] = new Label (5, 0, "ScoringError");
    for (int i = 6; i < 16; i++)
    {
      l[i] = new Label (i, 0, "score " + (i - 6));
    }
    for (int i = 0; i < l.length; i++)
      try {
        sheet.addCell (l[i]);
      } catch (RowsExceededException e) {
        _logger.error (e.getMessage ());
      } catch (WriteException e) {
        _logger.error (e.getMessage ());
      }
  }

  public void addRowToSheet (WritableSheet sheet, ItemRecord record, int row)
  {
    Label[] l = new Label[16];
    l[0] = new Label (0, row, record.getItemId ());
    l[1] = new Label (1, row, Double.toString (record.getDuration ()));
    l[2] = new Label (2, row, Integer.toString (record.getResponseCount ()));
    l[3] = new Label (3, row, Integer.toString (record.getScoreMatch ()));
    l[4] = new Label (4, row, Integer.toString (record.getScoreMismatch ()));
    l[5] = new Label (5, row, Integer.toString (record.getScoringError ()));
    for (int i = 6; i < l.length; i++)
      l[i] = new Label (i, row, Integer.toString (record.getScoringPointFreq ()[i - 6]));
    for (int i = 0; i < l.length; i++)
      try {
        sheet.addCell (l[i]);
      } catch (RowsExceededException e) {
        _logger.error (e.getMessage ());
      } catch (WriteException e) {
        _logger.error (e.getMessage ());
      }
  }

  public static void main (String[] args) {

    LogProcessor log = new LogProcessor ();
    try {
      log.processLog
          ("C:/Users/mskhan/Desktop/NewClient.log");
    } catch (IOException e) {
      _logger.error (e.getMessage ());
    }
    try {
      log.writeToExcel (null, "C:/Users/mskhan/Desktop/b.xls");
    } catch (WriteException e) {
      _logger.error (e.getMessage ());
    } catch (IOException e) {
      _logger.error (e.getMessage ());
    }

  }
}
