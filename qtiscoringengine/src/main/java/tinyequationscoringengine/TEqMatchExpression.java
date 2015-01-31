package tinyequationscoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.DEBoolean;
import qtiscoringengine.DEContainer;
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

public class TEqMatchExpression extends TinyEqExpression
{
  public TEqMatchExpression (Element node)

  {
    super (node, 0, 0, BaseType.String, Cardinality.Ordered);
    addAttribute (new ExpressionAttributeSpec ("object", BaseType.Identifier, false));
    addAttribute (new ExpressionAttributeSpec ("pattern", BaseType.String, false));
    addAttribute (new ExpressionAttributeSpec ("constraints", BaseType.String, true));
    addAttribute (new ExpressionAttributeSpec ("parameters", BaseType.String, true));
    addAttribute (new ExpressionAttributeSpec ("variables", BaseType.String, true));
    addAttribute (new ExpressionAttributeSpec ("simplify", BaseType.Boolean, false));
    addEqParameterConstraint ("object", TinyEquation.TEType.Expression);
    // may want to add validation constraints on the other parameters
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) throws QTIScoringException {
    DEString obj = (DEString) vb.getVariable ((DEIdentifier) getAttributeValue ("object"));
    DEString pattern = (DEString) getAttributeValue ("pattern");
    DEString constraints = (DEString) getAttributeValue ("constraints");
    if (constraints == null)
      constraints = new DEString ("");
    DEString parameters = (DEString) getAttributeValue ("parameters");
    if (parameters == null)
      parameters = new DEString ("");
    DEString variables = (DEString) getAttributeValue ("variables");
    if (variables == null)
      variables = new DEString ("");
    DEBoolean simplify = (DEBoolean) getAttributeValue ("simplify");

    List<String> expList = TinyEquation.matchExpression (obj.toString (), pattern.toString (), parseCSVAttribute (parameters.toString (), vb), parseCSVAttribute (constraints.toString (), vb),
        parseCSVAttribute (variables.toString (), vb), simplify.getBooleanValue ());

    DEContainer container = new DEContainer (BaseType.String, Cardinality.Ordered);
    for (String ob : expList) {
      container.add (new DEString (ob));
    }
    return container;
  }
}
