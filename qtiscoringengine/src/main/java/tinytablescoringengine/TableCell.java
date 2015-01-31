package tinytablescoringengine;

import org.jdom2.Document;
import org.jdom2.Element;

public class TableCell extends TableObject
{
  public boolean   _isHeader = false;
  protected String _content  = "";

  public TableCell (String content) {
    _content = content;
  }

  public boolean getIsHeader () {
    return _isHeader;
  }

  public String getName () {
    return "";
  }

  @Override
  public Element toXml (Document doc) {
    Element td = new Element ("td");
    td.addContent (_content);
    return td;
  }
}
