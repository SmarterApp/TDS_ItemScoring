package tinytablescoringengine;

import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;
import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.Expression;

public abstract class TinyTableExpression extends Expression
{
  public TableType                   _tableReturnType        = TableType.Unknown;
  public List<TTParameterConstraint> _ttParameterConstraints = new ArrayList<TTParameterConstraint> ();

  public TinyTableExpression (Element node, int minparm, int maxparm, BaseType returnType, Cardinality returnCardinality, TableType tableType) {
    super (node, minparm, maxparm, returnType, returnCardinality);
    _tableReturnType = tableType;
  }

  public TinyTableExpression (Element node, int minparm, int maxparm, BaseType returnType, Cardinality returnCardinality) {
    super (node, minparm, maxparm, returnType, returnCardinality);
    _tableReturnType = TableType.None;
  }

  public void addTableParameterConstraint (String name, TableType type) {
    _ttParameterConstraints.add (new TTParameterConstraint (name, type));
  }

  public void addTableParameterConstraint (String name, List<TableType> types) {
    _ttParameterConstraints.add (new TTParameterConstraint (name, types));
  }
}
