package tinyctrlscoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEInteger;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionAttributeSpec;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.ValidationLog;
import qtiscoringengine.VariableBindings;

public class CtrlMinInt extends CtrlExpression
{
  public CtrlMinInt (Element node)
  {
    super (node, 0, 0, BaseType.Integer, Cardinality.Single);
    addAttribute (new ExpressionAttributeSpec ("list", BaseType.String));
  }

  protected boolean validate (ValidationLog log, QTIRubric rubric)
  {
    boolean status = super.validate (log, rubric);
    boolean viable = validateResolvabilityOfDoubleList (getAttributeValue ("list").toString (), log, rubric);
    return (status & viable);
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues)
  {
    List<Double> dList = resolveDoubleList (vb, getAttributeValue ("list").toString ());

    double min = Integer.MAX_VALUE;
    for (double d : dList)
    {
      if (d <= min)
        min = d;
    }

    if ((min < Integer.MIN_VALUE) || (min > Integer.MAX_VALUE))
      return new DEInteger (Integer.MIN_VALUE);
    //Shiva: Is this equivalent to what is there in .NET?
    return new DEInteger (new Double(min).intValue ());
  }
}
