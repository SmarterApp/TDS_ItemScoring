package tinytablescoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.QTIScoringException;
import qtiscoringengine.VariableBindings;

public class TTGetHeaderRow extends TinyTableExpression
{
  public TTGetHeaderRow (Element node) {
    super (node, 0, 0, BaseType.String, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("table", BaseType.Identifier, false));
    addTableParameterConstraint ("table", TableType.Table);
  }

  @Override
  public DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws QTIScoringException {
    String varName = getAttributeValue ("table").toString ().trim ();
    if ("#".equals (varName)) {
      varName = "RESPONSE";
    }
    DataElement obj = vb.getVariable (varName);
    if (obj == null) {
      return new DEString ("");
    }
    return new DEString (TinyTable.getHeaderRow (obj.toString ()));
  }
}