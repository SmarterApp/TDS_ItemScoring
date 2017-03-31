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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.RewriteEmptyStreamException;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.lang3.StringUtils;
import org.antlr.runtime.tree.Tree;

import org.jdom2.Element;

import qtiscoringengine.QTIScoringException;
import qtiscoringengine.cs2java.StringHelper;
import tinyequationscoringengine.antlr.SympyEqWalker;
import tinyequationscoringengine.antlr.SympyLexer;
import tinyequationscoringengine.antlr.SympyParser;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class MathScoringService
{
  private static MathScoringService _singleton     = null;
  private static final Object       _singletonLock = new Object ();
  private WebProxy                  proxy;

  private MathScoringService () {

  }

  public static MathScoringService getInstance () {
    if (_singleton != null)
      return _singleton;

    synchronized (_singletonLock) {
      if (_singleton != null)
        return _singleton;

      _singleton = new MathScoringService ();
      return _singleton;
    }
  }

  boolean isInitialized () {
    return proxy != null;
  }

  void initialize (URI eqScoringServer, int maxRetries, int timeoutInMillis) {
    proxy = new WebProxy (eqScoringServer, maxRetries, timeoutInMillis);
  }

  public boolean lineContainsEquivalent (MathExpression mathexp, String rubric, boolean allowSimplify, boolean trig, boolean log, boolean force) throws QTIScoringException {
    // if (!IsInitialized()) throw new Exception("Not initialized");

    if (mathexp == null || mathexp.getSympyResponse () == null)
      return false;

    for (int expInd = 0; expInd < mathexp.getSympyResponse ().size (); expInd++) {
      if (isEquivalent (mathexp, rubric, allowSimplify, trig, log, force, expInd))
        return true;
    }

    return false;
  }

  public double evaluate (MathExpression mathexp) throws QTIScoringException {
    // if (!IsInitialized()) throw new Exception("Not initialized");

    if (mathexp == null || mathexp.getSympyResponse () == null || mathexp.getSympyResponse ().size () != 1)
      return Double.NaN;

    boolean parsable = sympify (mathexp.getSympyResponse ().get (0));
    if (parsable) {
      try {
        return proxy.evaluateExpression (mathexp.getSympyResponse ().get (0));
      } catch (Exception exp) {
        // do nothing
        // TODO Shiva
      }
    }
    return Double.NaN;
  }

  public boolean isEquivalent (MathExpression mathexp, String exemplar, boolean allowSimplify, boolean trig, boolean log, boolean force) throws QTIScoringException {
    return isEquivalent (mathexp, exemplar, allowSimplify, trig, log, force, -1);
  }

  public boolean isEquivalent (MathExpression mathexp, String exemplar, boolean allowSimplify, boolean trig, boolean log, boolean force, int expInd) throws QTIScoringException {
    if (mathexp == null || mathexp.getSympyResponse () == null || expInd == -1 && mathexp.getSympyResponse ().size () != 1 || expInd > -1 && mathexp.getSympyResponse ().size () <= expInd)
      return false;

    String rubric = exemplar;
    if (StringUtils.startsWith (rubric, "<?xml")) {
      MathExpressionInfo info = MathExpressionInfo.getMathExpressionInfoFromXml (rubric);
      rubric = info.getSympyResponse ().size () > 0 ? info.getSympyResponse ().get (0) : "";
    }

    if (expInd < 0)
      expInd = 0;

    boolean parsable;
    if (allowSimplify) {
      parsable = sympify (mathexp.getSympyResponse ().get (expInd));
      // first try to correct syntax of the response expressions
      if (!parsable) {
        if (mathexp.correct ())
          parsable = sympify (mathexp.getSympyResponse ().get (expInd));
      }

      // correction worked
      if (parsable) {
        if (proxy.isEquivalent (mathexp.getSympyResponse ().get (expInd), rubric, allowSimplify, trig, log, force))
          return true;
      }

      // continue to over-corrected the response hoping that it would score
      // higher
      if (mathexp.correct () && mathexp.getOvercorrectedSympyResponse () != null && mathexp.getOvercorrectedSympyResponse ().size () > expInd) {
        parsable = sympify (mathexp.getOvercorrectedSympyResponse ().get (expInd));
        if (parsable)
          return proxy.isEquivalent (mathexp.getOvercorrectedSympyResponse ().get (expInd), rubric, allowSimplify, trig, log, force);
      }
    } else {
      // building a full AST of sympy String to later do traversal and apply
      // combinatorial rules for comparison
      Tree rubricAST, responseAST;

      try {
        rubricAST = antlrize (rubric);
        responseAST = antlrize (mathexp.getSympyResponse ().get (expInd));
      } catch (RecognitionException exp) {
        return false;
      } catch (RewriteEmptyStreamException exp) {
        return false;
      } catch (NullPointerException exp) {
        // happens within ANTLR during recovery in the expression rule:
        // Antlr.Runtime.Parser.GetMissingSymbol(IIntStream input,
        // RecognitionException e, Int32 expectedTokenType, BitSet follow)
        // since we do not try to recover on the top level of expression, it is
        // safe to return false here
        return false;
      }

      return isEquivalent (responseAST, rubricAST);
    }
    return false;
  }

  public Tree antlrize (String sympy) throws RecognitionException {
    // TODO Shiva: The caching logic below has not been implemented.
    /*
     * WeakReference treeRef = null; if (antlrizedStrCache.TryGetValue(sympy,
     * out treeRef)) { if (treeRef.IsAlive) return (ITree)treeRef.Target;
     * antlrizedStrCache.Remove(sympy); }
     */

    SympyLexer lex = new SympyLexer (new ANTLRStringStream (sympy + "\n"));
    CommonTokenStream tokens = new CommonTokenStream (lex);
    SympyParser parser = new SympyParser (tokens);
    CommonTree tree = parser.expression ().getTree ();
    CommonTreeNodeStream nodes = new CommonTreeNodeStream (tree);
    SympyEqWalker walker = new SympyEqWalker (nodes);
    CommonTree simplified_tree = (CommonTree) walker.downup (tree, true); // walk
                                                                          // t,
                                                                          // trace
                                                                          // transforms

    // TODO Shiva: The caching logic below has not been implemented.
    /*
     * // cache for re-use later antlrizedStrCache[sympy] = new
     * WeakReference(simplified_tree);
     */
    return simplified_tree;
  }

  private boolean isEquivalent (Tree t1, Tree t2) {
    if (t1 == null && t2 == null)
      return true;
    if (t1 != null && t2 != null && MathExpression.commutative.contains (t1.getText ())) {
      if (StringUtils.equals (t1.getText (), t2.getText ()) && t1.getChildCount () == t2.getChildCount ()) {
        List<Integer> visited = new ArrayList<Integer> ();
        for (int i = 0; i < t1.getChildCount (); i++) {
          for (int j = 0; j < t2.getChildCount (); j++) {
            if (visited.contains (j))
              continue;
            if (isEquivalent (t1.getChild (i), t2.getChild (j))) {
              visited.add (j);
              break;
            }
          }
        }
        if (visited.size () == t1.getChildCount ())
          return true;
        return false;
      }
      return false;
    }
    if (t1 != null && t2 != null && StringUtils.equals (t1.getText (), t2.getText ())) {
      if (t1.getChildCount () == t2.getChildCount ()) {
        for (int i = 0; i < t1.getChildCount (); i++) {
          if (!isEquivalent (t1.getChild (i), t2.getChild (i)))
            return false;
        }
        return true;
      }
      return false;
    }
    return (t1 != null && t2 != null && StringUtils.equals (t1.toStringTree (), t2.toStringTree ()));
  }

  // do not attempt to score responses that are likely to crush scoring engine
  private Boolean garbageResponseFilter (String response) {
    if (response.length () > MathExpression.max_expr_len) {
      try {
        Double.parseDouble (response);
      }
      // TODO shiva: if number overflows. there was a catch block here.
      catch (NumberFormatException exp) {
        return false;
      }
    }
    return true;
  }

  public boolean sympify (String response) throws QTIScoringException {
    if (response == null)
      return false;

    boolean parsable = garbageResponseFilter (response) && !proxy.parsable (response);

    return parsable;
  }

  public boolean sympify (MathExpression response) throws QTIScoringException {

    if (response == null || response.getSympyResponse () == null || response.getSympyResponse ().size () < 1)
      return false;

    for (String sympy : response.getSympyResponse ()) {
      if (!sympify (sympy))
        return false;
    }

    return true;
  }

  public boolean expressionContains (MathExpression mathexp, String subResponse) {
    if (mathexp == null)
      return false;

    return StringUtils.contains (mathexp.toComparableString (), subResponse);
  }

  public List<Double> matchDouble (MathExpression mathexp, String pattern, List<String> parameters, List<String> constraints, List<String> variables, boolean allowSimplify) throws QTIScoringException {
    if (!isInitialized ())
      throw new QTIScoringException ("Not initialized");

    List<Double> retset = new ArrayList<Double> ();
    // sizing according to parameter length and filling with double placeholders
    for (String pr : parameters) {
      retset.add (Double.NaN);
    }

    if (mathexp == null || mathexp.getSympyResponse () == null || mathexp.getSympyResponse ().size () != 1)
      return retset;

    boolean parsable;

    if (allowSimplify) {
      parsable = sympify (mathexp.getSympyResponse ().get (0));
      if (!parsable)
        return retset;

      List<Double> dblretset = proxy.matchDouble (mathexp.getSympyResponse ().get (0), pattern, parameters, constraints, variables);
      for (int i = 0; i < retset.size (); i++) {
        try {
          retset.set (i, dblretset.get (i));
        } catch (Exception e) {
          // keep NaN;
        }
      }

    } else {
      parsable = sympify (mathexp.getSympyResponseNotSimplified ().get (0));
      if (!parsable) {
        parsable = sympify (mathexp.getSympyResponse ().get (0));
        if (parsable)
          mathexp.notSimplifiedParsingFailed = true;
        else
          return retset;
      }

      List<Double> dblretset = proxy.matchDouble (mathexp.getSympyResponseNotSimplified ().get (0), pattern, parameters, constraints, variables);
      for (int i = 0; i < retset.size (); i++) {
        try {
          retset.set (i, dblretset.get (i));
        } catch (Exception e) {
          // keep NaN;
        }
      }

    }

    return retset;
  }

  public MathExpressionSet matchExpression (MathExpression mathexp, String pattern, List<String> parameters, List<String> constraints, List<String> variables, boolean allowSimplify)
      throws QTIScoringException {
    if (!isInitialized ())
      throw new QTIScoringException ("Not initialized");

    MathExpressionSet retset = new MathExpressionSet ();
    // sizing according to parameter length and filling with expression
    // placeholders
    for (String pr : parameters) {
      retset.add (MathExpression.NaME);
    }

    if (mathexp == null || mathexp.getSympyResponse () == null || mathexp.getSympyResponse ().size () != 1)
      return retset;

    List<String> strretset = new ArrayList<String> ();
    boolean parsable;

    if (allowSimplify) {
      parsable = sympify (mathexp.getSympyResponse ().get (0));
      // first try to correct syntax of the response expressions
      if (!parsable)
        if (mathexp.correct ())
          parsable = sympify (mathexp.getSympyResponse ().get (0));

      // correction worked
      List<String> runtimeset = null;
      if (parsable) {
        runtimeset = proxy.matchExpression (mathexp.getSympyResponse ().get (0), pattern, parameters, constraints, variables);
        for (int i = 0; i < runtimeset.size (); i++) {
          try {
            strretset.add (runtimeset.get (i).toString ());
          } catch (Exception e) {
            strretset.add ("");
          }
        }
      }

      // check if match seems to have been unsuccessful
      if ((runtimeset == null || runtimeset.size () < parameters.size ()) && mathexp.correct () && mathexp.getOvercorrectedSympyResponse () != null
          && mathexp.getOvercorrectedSympyResponse ().size () > 0) {
        parsable = sympify (mathexp.getOvercorrectedSympyResponse ().get (0));
        if (parsable) {
          runtimeset = proxy.matchExpression (mathexp.getOvercorrectedSympyResponse ().get (0), pattern, parameters, constraints, variables);
          for (int i = 0; i < runtimeset.size (); i++) {
            try {
              strretset.add (runtimeset.get (i).toString ());
            } catch (Exception e) {
              strretset.add ("");
            }
          }
        }
      }
    } else {
      parsable = sympify (mathexp.getSympyResponseNotSimplified ().get (0));
      if (!parsable) {
        parsable = sympify (mathexp.getSympyResponse ().get (0));
        // first try to correct syntax of the response expressions
        if (!parsable)
          if (mathexp.correct ())
            parsable = sympify (mathexp.getSympyResponse ().get (0));

        // give up
        if (!parsable)
          return retset;
        else
          mathexp.notSimplifiedParsingFailed = true;
      }

      List<String> runtimeset = proxy.matchExpression (mathexp.getSympyResponseNotSimplified ().get (0), pattern, parameters, constraints, variables);
      for (int i = 0; i < runtimeset.size (); i++) {
        try {
          strretset.add (runtimeset.get (i).toString ());
        } catch (Exception e) {
          strretset.add ("");
        }
      }

    }

    // converting a list of strings into a set of math expression objects
    for (int i = 0; i < retset.size (); i++) {
      retset.set (i, ((i < strretset.size ()) ? new MathExpression (strretset.get (i)) : MathExpression.NaME));
    }

    return retset;
  }

  public int countEquations (MathExpression mathexp) {
    int count = 0;

    if (mathexp == null || mathexp.getSympyResponse () == null)
      return count;

    for (String r : mathexp.getSympyResponse ())
      if (StringUtils.startsWith (r, "Eq("))
        count += 1;

    return count;
  }

  public int countInequalities (MathExpression mathexp) {
    int count = 0;

    if (mathexp == null || mathexp.getSympyResponse () == null)
      return count;

    for (String r : mathexp.getSympyResponse ())
      if (r.startsWith ("Gt(") || r.startsWith ("Ge(") || r.startsWith ("Lt(") || r.startsWith ("Le("))
        count += 1;

    return count;
  }

  public int countResponses (MathExpression mathexp) {
    if (mathexp == null || mathexp.getSympyResponse () == null)
      return 0;
    return mathexp.getSympyResponse ().size ();
  }

  // equations with just a single variable on either the left or the right.
  public int countAnswerEquations (MathExpression mathexp) {
    int count = 0;

    if (mathexp == null || mathexp.getSympyResponse () == null)
      return count;

    for (String r : mathexp.getSympyResponse ())
      if (Pattern.matches ("^Eq\\([A-z],", r) || Pattern.matches ("^Eq\\(.*,[A-z]\\)$", r))
        count += 1;

    return count;
  }

  // inequalities with just a single variable on either the left or the right.
  public int countAnswerInequalities (MathExpression mathexp) {
    int count = 0;

    if (mathexp == null || mathexp.getSympyResponse () == null)
      return count;

    for (String r : mathexp.getSympyResponse ())
      if (Pattern.matches ("^(Gt|Ge|Lt|Le)\\([A-z],", r) || Pattern.matches ("^(Gt|Ge|Lt|Le)\\(.*,[A-z]\\)$", r))
        count += 1;

    return count;
  }

  // equations with just a single variable on either the left or the right.
  public int countAnswerEquations (MathExpression mathexp, String var) {
    int count = 0;

    if (mathexp == null || mathexp.getSympyResponse () == null)
      return count;

    for (String r : mathexp.getSympyResponse ())
      if (Pattern.matches ("^Eq\\(" + var + ",", r) || Pattern.matches ("^Eq\\(.*," + var + "\\)$", r))
        count += 1;

    return count;
  }

  // inequalities with just a single variable on either the left or the right.
  public int countAnswerInequalities (MathExpression mathexp, String var) {
    int count = 0;

    if (mathexp == null || mathexp.getSympyResponse () == null)
      return count;

    for (String r : mathexp.getSympyResponse ()) {
      if (Pattern.matches ("^(Gt|Ge|Lt|Le)\\(" + var + ",", r) || Pattern.matches ("^(Gt|Ge|Lt|Le)\\(.*," + var + "\\)$", r))
        count += 1;
    }
    return count;
  }

  // TODO Shiva: review.
  public boolean isEmpty (MathExpression mathexp) {
    if (mathexp == null) {
      return true;
    } else if (mathexp.getMathMLNodeList () == null) {
      // One of the MathExpression object constructors is defined to bypass
      // MathML processing and therefore
      // isEmpty function has to account for the case when MathML is left empty
      // but Sympy representation exists
      if (mathexp.getSympyResponse () == null || mathexp.getSympyResponse ().size () == 0)
        return true;

      char[] charsToTrim = { ' ', '(', ')' };
      for (int expInd = 0; expInd < mathexp.getSympyResponse ().size (); expInd++) {
        if (!StringUtils.isEmpty (StringHelper.trim (mathexp.getSympyResponse ().get (expInd), charsToTrim)))
          return false;
      }
      return true;
    }

    XmlNamespaceManager nsmgr = new XmlNamespaceManager ();
    nsmgr.addNamespace ("math", "http://www.w3.org/1998/Math/MathML");
    for (Element node_i : mathexp.getMathMLNodeList ()) {
      List<Element> text_nodes = new XmlElement (node_i).selectNodes ("..//math:mo/text()|..//math:mi/text()|..//math:mn/text()", nsmgr);
      for (Element node_j : text_nodes) {
        if (!StringUtils.isEmpty (node_j.getValue ()) && !StringUtils.isWhitespace (node_j.getValue ()))
          return false;
      }
    }
    return true;
  }
}
