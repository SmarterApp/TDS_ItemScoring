package tinyequationscoringengine;

import java.util.ArrayList;
import java.util.List;

public class TEParameterConstraint
{
  private List<TinyEquation.TEType> _acceptableTypes = new ArrayList<TinyEquation.TEType> ();
  private String                    _name;

  public List<TinyEquation.TEType> getAcceptableTypes () {
    return _acceptableTypes;
  }

  public void setAcceptableTypes (List<TinyEquation.TEType> value) {
    this._acceptableTypes = value;
  }

  public String getName () {
    return _name;
  }

  public void setName (String value) {
    this._name = value;
  }

  public TEParameterConstraint (String name, TinyEquation.TEType type) {
    _name = name;
    _acceptableTypes.add (type);
  }

  public TEParameterConstraint (String name, List<TinyEquation.TEType> types) {
    _name = name;
    _acceptableTypes.addAll (types);
  }
}
