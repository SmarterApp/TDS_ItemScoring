/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tinyequationscoringengine;

import java.util.List;

import org.antlr.runtime.RecognitionException;
import org.apache.commons.lang3.StringUtils;

import qtiscoringengine.QTIScoringException;
import qtiscoringengine.cs2java.StringHelper;

public class TinyEquation
{
  public enum TEType {
    Unknown, None, Expression, ExpressionSet, Boolean
  };

  public static List<String> parseMathML (String studentResponse) throws QTIScoringException {
    return MathMLParser.processMathMLData (studentResponse).toStringList ();
  }

  public static boolean getIsEquivalent (String parsedStudentResponse, String exemplar, boolean simplify, boolean trigIdenties, boolean logIdenties, boolean force) throws QTIScoringException {
    return getIsEquivalent (parsedStudentResponse, exemplar, simplify, trigIdenties, logIdenties, force, -1);
  }

  public static boolean getIsEquivalent (String parsedStudentResponse, String exemplar, boolean simplify, boolean trigIdenties, boolean logIdenties, boolean force, int expId)
      throws QTIScoringException {
    MathScoringService scoringService = MathScoringService.getInstance ();

    boolean isEq = scoringService.isEquivalent (new MathExpression (parsedStudentResponse), exemplar, simplify, trigIdenties, logIdenties, force, expId);

    return isEq;
  }

  public static boolean isEmpty (String parsedStudentResponse) throws QTIScoringException {
    MathScoringService scoringService = MathScoringService.getInstance ();
    return scoringService.isEmpty (new MathExpression (parsedStudentResponse));
  }

  public static double numberFromExpression (String parsedStudentResponse) throws QTIScoringException {
    String studentResponse = StringHelper.trim (parsedStudentResponse, new char[] { '[', ' ', ']' }); // remove
                                                                                                      // the
                                                                                                      // []
                                                                                                      // wrapper
                                                                                                      // around
                                                                                                      // the
                                                                                                      // math
                                                                                                      // expression
    return (new MathExpression (studentResponse)).toDouble ();
  }

  public static int countInEqualities (String parsedStudentResponse) throws QTIScoringException {
    MathScoringService scoringService = MathScoringService.getInstance ();
    return scoringService.countInequalities (new MathExpression (parsedStudentResponse));
  }

  public static List<String> matchExpression (String studentResponse, String pattern, List<String> parameters, List<String> constraints, List<String> variables, boolean simplify)
      throws QTIScoringException {
    MathScoringService scoringService = MathScoringService.getInstance ();
    MathExpressionSet matchedValue = scoringService.matchExpression (new MathExpression (studentResponse), pattern, parameters, constraints, variables, simplify);
    return matchedValue.toStringList ();
  }

  public static boolean isMatch (String studentResponse, String pattern, List<String> parameters, List<String> constraints, List<String> variables, boolean simplify) throws QTIScoringException {
    MathScoringService scoringService = MathScoringService.getInstance ();
    boolean ismatch = true;
    if (simplify) {
      MathExpressionSet matchedValue = scoringService.matchExpression (new MathExpression (studentResponse), pattern, parameters, constraints, variables, simplify);
      if (matchedValue.contains (MathExpression.NaME))
        ismatch = false;
    } else {
      List<Double> matchedValue = scoringService.matchDouble (new MathExpression (studentResponse), pattern, parameters, constraints, variables, simplify);
      if (matchedValue.contains (Double.NaN))
        ismatch = false;
    }

    return ismatch;
  }

  public static boolean lineContainsEquivalent (String response, String rubric, boolean allowSimplify, boolean trig, boolean log, boolean force) throws QTIScoringException {
    if (StringUtils.isEmpty (response)) {
      return false;
    }

    MathExpression mathexp = new MathExpression (response);

    if (mathexp.getSympyResponse () == null)
      return false;

    for (int expInd = 0; expInd < mathexp.getSympyResponse ().size (); expInd++) {
      if (getIsEquivalent (response, rubric, allowSimplify, trig, log, force, expInd))
        return true;
    }

    return false;
  }

  public static boolean expressionContains (String response, String substr) throws QTIScoringException {
    if (StringUtils.isEmpty (response)) {
      return false;
    }
    MathScoringService scoringService = MathScoringService.getInstance ();
    MathExpression exp = new MathExpression (response);
    return scoringService.expressionContains (exp, substr);
  }

  public static double evaluate (String response) throws QTIScoringException {
    MathExpression obj = new MathExpression (response);
    if (obj == MathExpression.NaME)
      return Double.NaN;

    MathScoringService scoringService = MathScoringService.getInstance ();
    return scoringService.evaluate (obj);
  }

  public static int equationsCount (String response) throws QTIScoringException {
    MathExpression mathexp = new MathExpression (response);
    MathScoringService scoringService = MathScoringService.getInstance ();
    return scoringService.countEquations (mathexp);
  }
}
