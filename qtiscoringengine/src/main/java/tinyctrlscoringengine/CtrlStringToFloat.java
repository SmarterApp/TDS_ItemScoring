package tinyctrlscoringengine;

import java.util.List;

import org.jdom2.Element;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.DEFloat;
import qtiscoringengine.DEString;
import qtiscoringengine.DataElement;
import qtiscoringengine.ExpressionParameterConstraint;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.VariableBindings;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;

public class CtrlStringToFloat extends CtrlExpression
{
  public CtrlStringToFloat (Element node) {
    super (node, 1, 1, BaseType.Float, Cardinality.Single);
    addParameterConstraint (new ExpressionParameterConstraint (0, Cardinality.Single, BaseType.String));
  }

  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    DEString obj = (DEString) paramValues.get (0);
    if (obj == null)
      return new DEFloat (Double.NaN);

    String name = obj.toString ().trim ();
    _Ref<Double> d = new _Ref<> (Double.NaN);

    boolean success = JavaPrimitiveUtils.doubleTryParse (name, d);
    if (success)
      return new DEFloat (d.get ());
    else
      return new DEFloat (Double.NaN);
  }
}
