package tinytablescoringengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TTParameterConstraint
{
  public List<TableType> AcceptableTypes = new ArrayList<TableType> ();
  public String          Name;

  public TTParameterConstraint (String name, TableType type) {
    Name = name;
    AcceptableTypes.add (type);
  }

  public TTParameterConstraint (String name, List<TableType> types) {
    Name = name;
    AcceptableTypes.addAll (types);
  }
}
