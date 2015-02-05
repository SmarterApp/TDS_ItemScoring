package tinytablescoringengine;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import qtiscoringengine.Expression;
import qtiscoringengine.ICustomOperatorFactory;

public class TinyTableCustomOpFactory implements ICustomOperatorFactory
{
  interface ITTConstructor
  {
    TinyTableExpression getTTConstructor (Element node);
  }

  public Map<String, ITTConstructor> _ttConstructors = new HashMap<String, ITTConstructor> ();

  public TinyTableCustomOpFactory () {
    addConstructor ("GETCOLUMN", new TTConGetColumn ());
    addConstructor ("GETHEADERROW", new TTConGetHeaderRow ());
    addConstructor ("GETVALUENUMERIC", new TTConGetValueNumeric ());
  }

  class TTConGetColumn implements ITTConstructor
  {
    public TinyTableExpression getTTConstructor (Element node) {
      return new TTGetColumn (node);
    }
  }

  class TTConGetValueNumeric implements ITTConstructor
  {
    public TinyTableExpression getTTConstructor (Element node) {
      return new TTGetValueNumeric (node);
    }
  }

  class TTConGetHeaderRow implements ITTConstructor
  {
    public TinyTableExpression getTTConstructor (Element node) {
      return new TTGetHeaderRow (node);
    }
  }

  public void addConstructor (String name, ITTConstructor del) {
    if (_ttConstructors.containsKey (name))
      _ttConstructors.remove (name);
    _ttConstructors.put (name, del);
  }

  @Override
  public boolean supportsOperator (Element customOperatorNode) {
    Element coElement = customOperatorNode;
    if (!StringUtils.equals ("TABLE", coElement.getAttribute ("type").getValue ())) {
      return false;
    }
    return _ttConstructors.containsKey (coElement.getAttribute ("functionName").getValue ());
  }

  @Override
  public Expression createExpression (Element customOperatorNode) {
    Element coElement = customOperatorNode;
    return (Expression) _ttConstructors.get (coElement.getAttribute ("functionName").getValue ()).getTTConstructor (coElement);
  }
}
