package tinyequationscoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.DEFloat;
import qtiscoringengine.DEIdentifier;
import qtiscoringengine.DEInteger;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.QTIScoringException;
import qtiscoringengine.VariableBindings;

public class TEEqEvaluate extends TinyEqExpression
{
  public TEEqEvaluate (Element node)

  {
    super (node, 0, 0, BaseType.Float, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier, false));
    addEqParameterConstraint ("object", TinyEquation.TEType.Expression);

  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws QTIScoringException {
    DEString obj = (DEString) vb.getVariable ((DEIdentifier) getAttributeValue ("object"));
    if (obj == null)
      obj = new DEString ("");
    return new DEFloat (TinyEquation.evaluate (obj.toString ()));
  }
}
