package qtiscoringengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CountLines
{
  private static PrintWriter Writer = null;

  public static void main (String[] args) {

    try (PrintWriter writer = new PrintWriter (new FileWriter ("C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/logs/filelines"))) {
      Writer = writer;
      String[] folderNames = { "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva" };
      int lineCounter = 0;
      for (String folderName : folderNames)
        lineCounter = lineCounter + scanFolder (folderName);
      System.err.println (lineCounter);
      Writer.println ("\n\nTotal lines: " + lineCounter);
    } catch (Exception e) {
      e.printStackTrace ();
    }
  }

  private static int scanFolder (String folderPathToScan) throws IOException {
    if (folderPathToScan.startsWith ("."))
      return 0;
    if (folderPathToScan.endsWith ("target") || folderPathToScan.endsWith ("settings"))
      return 0;
    int lineCounter = 0;
    File folder = new File (folderPathToScan);
    if (folder.isFile ()) {
      lineCounter = lineCounter + scanFile (folder.getAbsolutePath ());
    } else {
      for (String subFile : folder.list ()) {
        File subFilePath = new File (folder.getAbsolutePath () + File.separator + subFile);
        if (subFilePath.isDirectory ())
          lineCounter = lineCounter + scanFolder (subFilePath.getAbsolutePath ());
        else if (subFilePath.isFile ())
          lineCounter = lineCounter + scanFile (subFilePath.getAbsolutePath ());
      }
    }
    return lineCounter;
  }

  private static int scanFile (String file) throws IOException {
    int lineCounter = 0;
    file = file.toLowerCase ();
    if (file.endsWith (".cs") || file.endsWith (".java") || file.endsWith (".js") || file.endsWith (".xhtml") || file.endsWith (".aspx") || file.endsWith (".ascx") || file.endsWith (".asmx")
        || file.endsWith (".tsv")) {
      Writer.println (file);
      try (BufferedReader bfr = new BufferedReader (new FileReader (file))) {
        String line = null;
        while ((line = bfr.readLine ()) != null)
          ++lineCounter;
      }
    }
    return lineCounter;
  }
}
