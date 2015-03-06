/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinytablescoringengine;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import AIR.Common.xml.XmlElement;

public class Table extends TableObject
{
  private TableVector[] _rows;
  private int           _rowCount    = 0;
  private int           _columnCount = 0;
  private int           _headerRows  = 0;

  public Table (TableVector[] rows) {
    _rowCount = rows.length;
    _rows = rows;
    for (int i = 0; i < _rowCount; i++) {
      if (_rows[i].isHeader) {
        _headerRows++;
      }
      _columnCount = _rows[i].getElementCount ();
    }
  }

  @Override
  public TableVector getColumn (String colName) {
    int colIdx = getColumnIndex (colName);
    if (colIdx == -1) {
      return null;
    }
    return getColumn (colIdx);

  }

  @Override
  public TableVector getColumn (int i) {
    int size = _rowCount - _headerRows;
    TableVector column = new TableVector (size);
    for (int j = 0; j < size; j++) {
      column.setElement (j, _rows[_headerRows + j].getElement (i));
    }

    return column;
  }

  @Override
  public TableVector getHeaderRow () {
    return (_rowCount > 0) ? _rows[0] : null;
  }

  public TableVector getRowIndex (int idx) {
    return _rows[idx];
  }

  public int getColumnIndex (String colName) {
    if (_headerRows > 0) {
      for (int i = 0; i < _columnCount; i++) {
        if (colName.equals (_rows[_headerRows - 1].getElement (i).getName ())) {
          return i;
        }
      }
    }
    return -1;
  }

  public static Table fromXml (Element node) // tales a responseTable node as
                                             // input
  {
    List<Element> rowNodes = new XmlElement (node).selectNodes ("tr");
    int rowCount = rowNodes.size ();
    TableVector[] rows = new TableVector[rowCount];
    for (int i = 0; i < rowCount; i++) {
      rows[i] = TableVector.fromXml (rowNodes.get (i));
    }
    return new Table (rows);
  }

  @Override
  public Element toXml (Document doc) {
    Element table = new Element ("responseTable");
    for (TableVector v : _rows) {
      Element row = v.toXml (doc);
      table.addContent (row);
    }
    return table;
  }
}
