package qtiscoringengineTester;

import java.net.URI;

import qtiscoringengine.CustomOperatorRegistry;
import tinyctrlscoringengine.CtrlCustomOpFactory;
import tinyequationscoringengine.TinyEqCustomOpFactory;
import tinygrscoringengine.TinyGRCustomOpFactory;
import tinytablescoringengine.TinyTableCustomOpFactory;

public class QtiTester
{
  public static void main (String[] args) {
    try {
      final String itemId = "73";
      final String bankId = "NA";
      final String rubricFilePath = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/ERX/Item_73_v8.qrx";
      String responseFile = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/73_EQ.tsv";

      FormQtiTester qtiTester = new FormQtiTester (new ItemSpecification ()
      {
        {
          this._itemId = itemId;
          this._bankId = bankId;
          this._rubricFilePath = rubricFilePath;
        }
      });

      qtiTester.processResponseFiles (responseFile);

    } catch (Exception exp) {
      exp.printStackTrace ();
    }
  }
}
