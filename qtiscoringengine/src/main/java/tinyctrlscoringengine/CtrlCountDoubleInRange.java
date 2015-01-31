package tinyctrlscoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEFloat;
import qtiscoringengine.DEInteger;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.ValidationLog;
import qtiscoringengine.VariableBindings;
import qtiscoringengine._DEFloat;

public class CtrlCountDoubleInRange extends CtrlExpression
{
  public CtrlCountDoubleInRange (Element node) {
    super (node, 0, 0, BaseType.Integer, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("list", BaseType.String));
    addAttribute (new ExpressionAttributeSpec ("min", BaseType.String));
    addAttribute (new ExpressionAttributeSpec ("max", BaseType.String));
  }

  protected boolean Validate (ValidationLog log, QTIRubric rubric) {
    boolean status = super.validate (log, rubric);
    boolean viable = validateResolvabilityOfDoubleList (getAttributeValue ("list").toString (), log, rubric);
    return (status & viable);
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    List<Double> dList = resolveDoubleList (vb, getAttributeValue ("list").toString ());
    double min = resolveDouble (vb, getAttributeValue ("min").toString ());
    double max = resolveDouble (vb, getAttributeValue ("max").toString ());

    int cnt = 0;
    for (double d : dList) {
      if ((d >= min) && (d <= max))
        cnt++;
    }

    return new DEInteger (cnt);
  }
}
