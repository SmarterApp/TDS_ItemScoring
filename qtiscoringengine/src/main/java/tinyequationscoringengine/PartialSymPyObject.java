package tinyequationscoringengine;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;

public class PartialSymPyObject
{
  private LeftMostType leftType;

  public LeftMostType getLeftType () {
    return leftType;
  }

  private void setLeftType (LeftMostType value) {
    this.leftType = value;
  }

  private boolean closed;

  public boolean isClosed () {
    return closed;
  }

  public void setClosed (boolean value) {
    this.closed = value;
  }

  private boolean _symPyValid = false;
  private String  _symPyString;

  public String getSymPyString () {
    if (!_symPyValid) {
      _Ref<Long> number1 = new _Ref<Long> (0l);
      if (_text.startsWith ("0") && JavaPrimitiveUtils.longTryParse (_text, number1)) {
        _text = number1.get ().toString ();
      }
      if (_leaf != null) {
        // if (_text != "0") //allow trivial simplifications
        _text = "((" + _text + "+_a" + _leaf + ")" + "*_m" + _leaf + ")";
        _leaf = null;
      }
      if (_reordered) {
        _text = "(" + _text + ")";
        _reordered = false;
      }
      _symPyString = _text;
      if (_children != null) {
        _symPyString += "(";
        boolean isfirst = true;
        for (PartialSymPyObject ch : _children) {
          if (!isfirst)
            _symPyString += ",";
          _symPyString += ch.getSymPyString ();
          isfirst = false;
        }
        _symPyString += ")";
      }
      if (_next != null)
        _symPyString += _next.getSymPyString ();
      _symPyValid = true;
    }
    return _symPyString;
  }

  private String                   _text      = "";
  private PartialSymPyObject       _next      = null;
  private List<PartialSymPyObject> _children  = null;
  private String                   _leaf      = null;
  private boolean                  _reordered = false;

  public PartialSymPyObject (String sympyPrefix, PartialSymPyObject sympy, LeftMostType type) {
    _text = sympyPrefix;
    _next = sympy;
    leftType = type;
    closed = sympy.isClosed ();
  }

  public PartialSymPyObject () {
    leftType = LeftMostType.None;
    closed = false;
  }

  // add a numeric leaf node
  public void addLeaf (String nodeText, String counter, boolean simplify) {
    _text = nodeText + _text;
    if (!simplify)
      _leaf = counter;
    _symPyValid = false;
  }

  public void reorder (String nodeText, LeftMostType type, String counter, boolean simplify) {
    addLeaf (nodeText, counter, simplify);
    _reordered = true;
    leftType = type;
  }

  // adds a prefix to the current node text
  public void addText (String nodeText) {
    _text = nodeText + _text;
    _symPyValid = false;
  }

  // creates a node and pulls a queue of children under it
  public void addParent (String nodeText) {
    removeChildren ();
    addFirstChild (_text);
    _text = nodeText;
    _symPyValid = false;
  }

  // pushes child on the Queue
  public void addChild (String nodeText) {
    if (_children == null)
      _children = new ArrayList<PartialSymPyObject> ();
    _children.add (new PartialSymPyObject (nodeText, null, LeftMostType.None));
    _symPyValid = false;
  }

  // prepends child to the Queue
  public void addFirstChild (String nodeText) {
    if (_children == null)
      _children = new ArrayList<PartialSymPyObject> ();
    _children.add (0, new PartialSymPyObject (nodeText, null, LeftMostType.None));
    _symPyValid = false;
  }

  public void removeChildren () {
    if (_children != null) {
      _text += "(";
      boolean isfirst = true;
      for (PartialSymPyObject ch : _children) {
        if (!isfirst)
          _text += ",";
        _text += ch.getSymPyString ();
        isfirst = false;
      }
      _text += ")";
      _children.clear ();
      _children = null;
    }
  }

  public void RemoveSiblings (String stop) {
    String nexttext;

    removeChildren ();
    while (_next != null) {
      nexttext = _next._text;
      _next.removeChildren ();
      _text += _next._text;
      _next = _next._next;
      if (StringUtils.equals (nexttext, stop))
        break;
    }
  }
}
