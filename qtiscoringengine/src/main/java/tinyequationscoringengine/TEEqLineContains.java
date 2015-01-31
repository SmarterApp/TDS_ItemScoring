package tinyequationscoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.DEBoolean;
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

public class TEEqLineContains extends TinyEqExpression
{
  public TEEqLineContains (Element node) {
    super (node, 0, 0, BaseType.Boolean, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier, false));
    addAttribute (new ExpressionAttributeSpec ("exemplar", BaseType.String, false));
    addAttribute (new ExpressionAttributeSpec ("simplify", BaseType.Boolean, false));

    addEqParameterConstraint ("object", TinyEquation.TEType.Expression);
    addEqParameterConstraint ("exemplar", TinyEquation.TEType.Expression);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws QTIScoringException {
    DEString obj = (DEString) vb.getVariable ((DEIdentifier) getAttributeValue ("object"));
    if (obj == null)
      obj = new DEString ("");
    DEString expr = (DEString) vb.getVariable (getAttributeValue ("exemplar").toString ());
    if (expr == null)
      expr = (DEString) getAttributeValue ("exemplar");
    DEBoolean simplify = (DEBoolean) getAttributeValue ("simplify");
    return new DEBoolean (TinyEquation.lineContainsEquivalent (obj.toString (), expr.toString (), simplify.getBooleanValue (), false, false, false));
  }
}
