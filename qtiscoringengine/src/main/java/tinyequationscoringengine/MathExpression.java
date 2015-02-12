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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;

import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import qtiscoringengine.QTIScoringException;
import qtiscoringengine.QTIScoringRuntimeException;
import qtiscoringengine.cs2java.StringHelper;
import tinyequationscoringengine.antlr.CorrectiveLexer;
import tinyequationscoringengine.antlr.CorrectiveOptimisticLexer;
import tinyequationscoringengine.antlr.CorrectiveOptimisticParser;
import tinyequationscoringengine.antlr.CorrectiveParser;

public class MathExpression
{
  // Not a Math Expression object
  public static MathExpression       NaME                   = new MathExpression ();

  // Safeguard for not processing input exceeding certain length threshold
  public static int                  max_expr_len           = 200;

  // need to add more here, maybe in the constructor too if it is item specific
  public static Set<String>          commutative            = new HashSet<String> (Arrays.asList ("+", "*", "Eq", "Ne"));

  public static Set<String>          functions              = new HashSet<String> (Arrays.asList ("sin", "cos", "tan", "asin", "acos", "atan", "log", "ln", "exp", "f", "g"));

  public static Map<String, String>  open_brackets          = new HashMap<String, String> ()
                                                            {
                                                              private static final long serialVersionUID = 1L;
                                                              {
                                                                put ("(", ")");
                                                                put ("|", "|");
                                                              }
                                                            };
  public static Set<String>          closed_brackets        = new HashSet<String> (Arrays.asList (")", "|"));

  // functions that take evaluate keyword argument to disable trivial
  // simplifications (e.g, Add(1,1)=1+1)
  public static Map<String, String>  simplificationsArity1  = new HashMap<String, String> ()
                                                            {
                                                              private static final long serialVersionUID = 1L;
                                                              {
                                                                put ("cos", "cos");
                                                                put ("sin", "sin");
                                                                put ("tan", "tan");
                                                                put ("acos", "acos");
                                                                put ("asin", "asin");
                                                                put ("atan", "atan");
                                                              }
                                                            };

  public static Map<String, String>  simplificationsArity2  = new HashMap<> ();
  public static Set<String>          simplificationsSpecial = new HashSet<String> ();
  public static Set<String>          infixFunctions         = new HashSet<String> (Arrays.asList ("+", "*", "-", "/", "**", "^", "."));

  public static Map<String, Integer> functionPrecedence     = new HashMap<String, Integer> ()
                                                            {
                                                              private static final long serialVersionUID = 1L;
                                                              {
                                                                put ("+", 1);
                                                                put ("-", 1);
                                                                put ("*", 2);
                                                                put ("/", 2);
                                                                put ("**", 3);
                                                                put ("^", 3);
                                                                put (")", 4);
                                                                put (".", 5);
                                                              }
                                                            };

  public static char[]               doubleCharsToTrim      = { '(', ' ', ')' };

  private int                        _leafCounter           = 0;

  public String getLeafCounter () {
    ++_leafCounter;
    return ((Integer) _leafCounter).toString ();
  }

  private static Map<String, String> unicodes = new HashMap<String, String> ()
                                              {
                                                private static final long serialVersionUID = 1L;

                                                {
                                                  put ("\u003C", "<");
                                                  put ("\u003D", "=");
                                                  put ("\u003E", ">");
                                                  put ("\u00D7", "*");
                                                  put ("\u00F7", "/");
                                                  put ("\u2061", "");
                                                  put ("\u2212", "-");
                                                  put ("\u2215", "/");
                                                  put ("\u2217", "*");
                                                  put ("\u2219", "*"); // fat
                                                                       // dot
                                                                       // for
                                                                       // multiplication
                                                  put ("\u221E", "oo");
                                                  put ("\u2223", "|");
                                                  put ("\u2260", "!=");
                                                  put ("\u2264", "<=");
                                                  put ("\u2265", ">=");
                                                  put ("\u02C2", "<");
                                                  put ("\u02C3", ">");
                                                  put ("\u03B1", "alpha");
                                                  put ("\u03B2", "beta");
                                                  put ("\u03B3", "gamma");
                                                  put ("\u03B4", "delta");
                                                  put ("\u03B5", "epsilon");
                                                  put ("\u03F5", "epsilon");
                                                  put ("\u03B6", "zeta");
                                                  put ("\u03B7", "eta");
                                                  put ("\u03B8", "theta");
                                                  put ("\u03B9", "iota");
                                                  put ("\u03BA", "kappa");
                                                  put ("\u03BB", "lambda");
                                                  put ("\u03BC", "mu");
                                                  put ("\u03BD", "nu");
                                                  put ("\u03BE", "xi");
                                                  put ("\u03BF", "omicron");
                                                  put ("\u03C0", "pi");
                                                  put ("\u03C1", "rho");
                                                  put ("\u03C3", "sigma");
                                                  put ("\u03C4", "tau");
                                                  put ("\u03C5", "upsilon");
                                                  put ("\u03C6", "phi");
                                                  put ("\u03C7", "chi");
                                                  put ("\u03C8", "psi");
                                                  put ("\u03C9", "omega");
                                                  put ("\u0393", "Gamma");
                                                  put ("\u0394", "Delta");
                                                  put ("\u0398", "Theta");
                                                  put ("\u039B", "Lambda");
                                                  put ("\u039E", "Xi");
                                                  put ("\u03A0", "Pi");
                                                  put ("\u03A3", "Sigma");
                                                  put ("\u03A5", "Upsilon");
                                                  put ("\u03A6", "Phi");
                                                  put ("\u03A8", "Psi");
                                                  put ("\u03A9", "Omega");
                                                  put ("arcsin", "asin");
                                                  put ("arccos", "acos");
                                                  put ("arctan", "atan");
                                                  put ("i", "I");
                                                  // substitute something more
                                                  // meaningful
                                                  put ("\u25FB", "box");
                                                  put ("\\Box", "box");
                                                }
                                              };

  // MathML XML node list
  private List<Element>              mathMLNodeList;

  public List<Element> getMathMLNodeList () {
    return mathMLNodeList;
  }

  @SuppressWarnings ("unused")
  private void setMathMLNodeList (List<Element> elements) {
    mathMLNodeList = elements;
  }

  // List of sympy format strings. One entry for each "line" of the response.
  private List<String> sympyResponse;

  public List<String> getSympyResponse () {
    return sympyResponse;
  }

  @SuppressWarnings ("unused")
  private void setSympyResponse (List<String> sympyResponse) {
    this.sympyResponse = sympyResponse;
  }

  // List of sympy string with inserted evaluate=False that would disable
  // automatic simplifications by sympy engine
  private List<String> _sympyResponseNotSimplified;

  public List<String> getSympyResponseNotSimplified () {
    if (notSimplifiedParsingFailed)
      _sympyResponseNotSimplified = sympyResponse;
    return _sympyResponseNotSimplified;
  }

  @SuppressWarnings ("unused")
  private void setSympyResponseNotSimplified (List<String> value) {
    _sympyResponseNotSimplified = value;
  }

  public boolean notSimplifiedParsingFailed = false;

  // List of sympy strings overcorrected for * vs X
  public List<String> getOvercorrectedSympyResponse () {
    return _overcorrectedSympyResponse;
  }

  private List<String> _overcorrectedSympyResponse = null;

  // make sure we are not trying to correct it multiple times
  private boolean      _triedToApplyCorrection     = true;
  private boolean      _appliedCorrection          = false;

  // tries to make syntactic corrections in the response expressions
  public boolean correct () {
    if (_triedToApplyCorrection)
      return _appliedCorrection;

    _triedToApplyCorrection = true;
    _appliedCorrection = false; // at least one response has been corrected

    _overcorrectedSympyResponse = new ArrayList<String> (sympyResponse);
    for (int ind = 0; ind < sympyResponse.size (); ind++) {
      String sympyString = sympyResponse.get (ind);

      if (sympyString.length () > max_expr_len)
        return false;

      if (!sympyString.contains ("*"))
        continue;

      CorrectiveLexer lex = new CorrectiveLexer (new ANTLRStringStream (sympyString));
      CommonTokenStream tokens = new CommonTokenStream (lex);
      CorrectiveParser parser = new CorrectiveParser (tokens);
      try {
        CommonTree tree = parser.expression ().getTree ();
        sympyResponse.set (ind, StringUtils.replace (tree.toStringTree (), " ", ""));
        _appliedCorrection = true;
      } catch (RecognitionException e) {
        // do not replace with anything
      }

      CorrectiveOptimisticLexer olex = new CorrectiveOptimisticLexer (new ANTLRStringStream (sympyString));
      CommonTokenStream otokens = new CommonTokenStream (olex);
      CorrectiveOptimisticParser oparser = new CorrectiveOptimisticParser (otokens);
      try {
        CommonTree tree = oparser.expression ().getTree ();
        _overcorrectedSympyResponse.set (ind, StringUtils.replace (tree.toStringTree (), " ", ""));
        _appliedCorrection = true;
      } catch (RecognitionException e) {
        // do not replace with anything
      }
    }
    return _appliedCorrection;
  }

  // Public contructor accepts a list of XML nodes and transforms it into a list
  // of sympy strings
  public MathExpression (List<Element> nodeList) throws QTIScoringException {
    nodeList = stripTopMRow (nodeList);
    mathMLNodeList = nodeList;
    sympyResponse = nodeListToSymPyStringList (nodeList, true);
    _sympyResponseNotSimplified = nodeListToSymPyStringList (nodeList, false);
  }

  // Public contructor accepts a list of sympy strings
  public MathExpression (final String sympy) throws QTIScoringException {
    try {
      mathMLNodeList = null;
      if (StringUtils.startsWith (sympy, "<?xml")) {
        MathExpressionInfo info = MathExpressionInfo.getMathExpressionInfoFromXml (sympy);

        sympyResponse = info.getSympyResponse ();
        _sympyResponseNotSimplified = info.getSympyResponseNotSimplified ();
        _overcorrectedSympyResponse = info.getOvercorrectedSympyResponse ();
        _triedToApplyCorrection = info.getTriedToApplyCorrection ();
        _appliedCorrection = info.getAppliedCorrection ();
      } else {
        sympyResponse = Arrays.asList (sympy);
      }
    } catch (Exception exp) {
      throw new QTIScoringException (exp);
    }
  }

  // Default constructor only used for NaME initialization
  private MathExpression () {
    mathMLNodeList = null;
    sympyResponse = new ArrayList<String> ();
  }

  private static String decodeText (String nodetext) {
    if (unicodes.containsKey (nodetext))
      return unicodes.get (nodetext);
    return nodetext;
  }

  @SuppressWarnings ("unused")
  private String leafToExpression (String text) {
    _leafCounter++;
    return "(" + text + "*_m" + _leafCounter + "+_a" + _leafCounter + ")";
  }

  // a workhorse for NodeListToSymPyStringList and NodeListToSymPyString methods
  // simplify flag will transform all leaf nodes (e.g. 10, 2, x) into
  // expressions with hidden variables ((10*_m1+_a1), (2*_m2+_a2), (x*_m3+a3))
  private PartialSymPyObject toSymPy (List<Element> nodelist, int start, int end, PartialSymPyObject sympy, boolean simplify) throws QTIScoringException {
    for (int ind = end; ind >= start; ind--) {

      String nodeName = nodelist.get (ind).getName ();
      String decodedText = decodeText (nodelist.get (ind).getText ());
      switch (nodeName) {
      case "mspace": // ignore this. MathQuill based eq editor adds this to
                     // beautify the rendered output. Not semantically
                     // significant for scoring
        break;
      case "mn":
        switch (sympy.getLeftType ()) {
        case Expression:
          if (isMathMLSimpleNumericFraction (nodelist.get (ind + 1))) {
            sympy.reorder (decodedText + "+", LeftMostType.Number, getLeafCounter (), simplify);
            continue;
          } else {
            sympy = new PartialSymPyObject ("", new PartialSymPyObject ("*", sympy, LeftMostType.Operator), LeftMostType.Number);
            sympy.addLeaf (decodedText, getLeafCounter (), simplify);
            continue;
          }
        case Number: // another digit of the same number
          sympy.addText (decodedText);
          continue;
        case Operator:
        case None:
          sympy = new PartialSymPyObject ("", sympy, LeftMostType.Number);
          sympy.addLeaf (decodedText, getLeafCounter (), simplify);
          continue;
        default:
          throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
        }
      case "mo":
        String op = decodedText;
        // parenthesis and dot are listed as operators but need to be treated
        // like an expression
        if (op == ".") {
          switch (sympy.getLeftType ()) {
          case Expression:
          // if (IsMathMLSimpleNumericFraction(nodelist[ind + 1]))
          // {
          // sympy = new PartialSymPyObject("", new PartialSymPyObject("+",
          // sympy, LeftMostType.Operator), LeftMostType.Number);
          // sympy.AddLeaf(op, LeafCounter, simplify);
          // continue;
          // }
          // else
          {
            sympy = new PartialSymPyObject ("", new PartialSymPyObject ("*", sympy, LeftMostType.Operator), LeftMostType.Number);
            sympy.addLeaf (op, getLeafCounter (), simplify);
            continue;
          }
          case Number: // another digit of the same number
            sympy.addText (op);
            continue;
          case Operator:
          case None:
            sympy = new PartialSymPyObject ("", sympy, LeftMostType.Number);
            sympy.addLeaf (op, getLeafCounter (), simplify);
            continue;
          default:
            throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
          }
        } else if (closed_brackets.contains (op) && !sympy.isClosed ()) {
          if (op == "|") {
            op = ")";
            sympy.setClosed (true);
          }

          switch (sympy.getLeftType ()) {
          case Expression:
          case Number:
            sympy = new PartialSymPyObject (op + "*", sympy, LeftMostType.Operator);
            continue;
          case Operator:
          case None:
            sympy = new PartialSymPyObject (op, sympy, LeftMostType.Operator);
            continue;
          default:
            throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
          }
        } else {
          LeftMostType ltype = LeftMostType.Operator;
          if (open_brackets.containsKey (op)) {
            if (op == "|") {
              op = "Abs(";
              sympy.setClosed (false);
            }
            ltype = LeftMostType.Expression;
          }

          switch (sympy.getLeftType ()) {
          case Expression:
          case Operator:
          case Number:
          case None:
            sympy = new PartialSymPyObject (op, sympy, ltype);
            continue;
          default:
            throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
          }
        }
      case "mi":
      case "mtext":
        if (functions.contains (decodedText)) {
          sympy = new PartialSymPyObject (decodedText, sympy, LeftMostType.Expression);
          continue;
        } else {
          switch (sympy.getLeftType ()) {
          case Expression:
          case Number:
            sympy = new PartialSymPyObject (decodedText + "*", sympy, LeftMostType.Expression);
            continue;
          case Operator:
          case None:
            sympy = new PartialSymPyObject (decodedText, sympy, LeftMostType.Expression);
            continue;
          default:
            throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
          }
        }
      case "mfrac":
        List<Element> mfracnodes = nodelist.get (ind).getChildren ();
        if (mfracnodes.size () != 2)
          throw new QTIScoringException ("mfrac with number of children != 2?");
        String frac;
        frac = "(" + toSymPy (mfracnodes, 0, 0, new PartialSymPyObject (), simplify).getSymPyString () + "/" + toSymPy (mfracnodes, 1, 1, new PartialSymPyObject (), simplify).getSymPyString () + ")";

        switch (sympy.getLeftType ()) {
        case Expression:
        case Number:
          sympy = new PartialSymPyObject ("*", sympy, LeftMostType.Operator);
          sympy = new PartialSymPyObject (frac, sympy, LeftMostType.Expression);
          continue;
        case Operator:
        case None:
          sympy = new PartialSymPyObject (frac, sympy, LeftMostType.Expression);
          continue;
        default:
          throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
        }
      case "mfenced":
        String openFence = decodeText (nodelist.get (ind).getAttribute ("open").getValue ());
        String closeFence = decodeText (nodelist.get (ind).getAttribute ("close").getValue ());
        if (StringUtils.equals (openFence, "|") && StringUtils.equals (closeFence, "|")) {
          openFence = "Abs(";
          closeFence = ")";
        }
        String fenced = openFence + nodeListToSymPyString (nodelist.get (ind).getChildren (), simplify) + closeFence;
        switch (sympy.getLeftType ()) {
        case Expression:
        case Number:
          sympy = new PartialSymPyObject (fenced + "*", sympy, LeftMostType.Expression);
          continue;
        case Operator:
        case None:
          sympy = new PartialSymPyObject (fenced, sympy, LeftMostType.Expression);
          continue;
        default:
          throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
        }
      case "mrow":
        String row = "(" + nodeListToSymPyString (nodelist.get (ind).getChildren (), simplify) + ")";
        if (StringUtils.equals (row, "([])")) // a bug on the renderer side;
                                              // just ignore empty mrow
          continue;
        switch (sympy.getLeftType ()) {
        case Expression:
        case Number:
          sympy = new PartialSymPyObject (row + "*", sympy, LeftMostType.Expression);
          continue;
        case Operator:
        case None:
          sympy = new PartialSymPyObject (row, sympy, LeftMostType.Expression);
          continue;
        default:
          throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
        }
      case "msqrt":
        String sqrt = "sqrt(" + nodeListToSymPyString (nodelist.get (ind).getChildren (), simplify) + ")";
        switch (sympy.getLeftType ()) {
        case Expression:
        case Number:
          sympy = new PartialSymPyObject (sqrt + "*", sympy, LeftMostType.Expression);
          continue;
        case Operator:
        case None:
          sympy = new PartialSymPyObject (sqrt, sympy, LeftMostType.Expression);
          continue;
        default:
          throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
        }
      case "mroot":
        List<Element> mrootnodes = nodelist.get (ind).getChildren ();
        if (mrootnodes.size () != 2)
          throw new QTIScoringException ("mroot with number of children != 2?");
        // string root = "(" + ToSymPy(mrootnodes, 0, 0, new
        // PartialSymPyObject(), simplify).SymPyString + ")**(1/(" +
        // ToSymPy(mrootnodes, 1, 1, new PartialSymPyObject(),
        // simplify).SymPyString + "))";
        String root = "nthroot(" + toSymPy (mrootnodes, 0, 0, new PartialSymPyObject (), simplify).getSymPyString () + ","
            + toSymPy (mrootnodes, 1, 1, new PartialSymPyObject (), simplify).getSymPyString () + ")";
        switch (sympy.getLeftType ()) {
        case Expression:
        case Number:
          sympy = new PartialSymPyObject (root + "*", sympy, LeftMostType.Expression);
          continue;
        case Operator:
        case None:
          sympy = new PartialSymPyObject (root, sympy, LeftMostType.Expression);
          continue;
        default:
          throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
        }
      case "msup":
        List<Element> msupnodes = nodelist.get (ind).getChildren ();
        if (msupnodes.size () != 2)
          throw new QTIScoringException ("msup with number of children != 2?");
        String sup = "(" + toSymPy (msupnodes, 0, 0, new PartialSymPyObject (), simplify).getSymPyString () + ")**(" + toSymPy (msupnodes, 1, 1, new PartialSymPyObject (), true).getSymPyString ()
            + ")";
        switch (sympy.getLeftType ()) {
        case Expression:
        case Number:
          sympy = new PartialSymPyObject (sup + "*", sympy, LeftMostType.Expression);
          continue;
        case Operator:
        case None:
          sympy = new PartialSymPyObject (sup, sympy, LeftMostType.Expression);
          continue;
        default:
          throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
        }

      case "msub":
        List<Element> msubnodes = nodelist.get (ind).getChildren ();
        if (msubnodes.size () != 2)
          throw new QTIScoringException ("msub with number of children != 2?");
        String sub = toSymPy (msubnodes, 0, 0, new PartialSymPyObject (), simplify).getSymPyString () + "_" + toSymPy (msubnodes, 1, 1, new PartialSymPyObject (), simplify).getSymPyString ();
        switch (sympy.getLeftType ()) {
        case Expression:
        case Number:
          sympy = new PartialSymPyObject (sub + "*", sympy, LeftMostType.Expression);
          continue;
        case Operator:
        case None:
          sympy = new PartialSymPyObject (sub, sympy, LeftMostType.Expression);
          continue;
        default:
          throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
        }

      case "msubsup":
        List<Element> msubsupnodes = nodelist.get (ind).getChildren ();
        if (msubsupnodes.size () != 3)
          throw new QTIScoringException ("msubsupnodes with number of children != 2?");
        String subsup = "(" + toSymPy (msubsupnodes, 0, 0, new PartialSymPyObject (), simplify).getSymPyString () + "_"
            + toSymPy (msubsupnodes, 1, 1, new PartialSymPyObject (), simplify).getSymPyString () + ")**(" + toSymPy (msubsupnodes, 2, 2, new PartialSymPyObject (), simplify).getSymPyString () + ")";
        switch (sympy.getLeftType ()) {
        case Expression:
        case Number:
          sympy = new PartialSymPyObject (subsup + "*", sympy, LeftMostType.Expression);
          continue;
        case Operator:
        case None:
          sympy = new PartialSymPyObject (subsup, sympy, LeftMostType.Expression);
          continue;
        default:
          throw new QTIScoringException ("Unhandled type: " + sympy.getLeftType ().toString ());
        }

      default:
        throw new QTIScoringException ("Unhandled mathML node type '" + nodeName + "'; content '" + nodelist.get (ind).getText () + "'");
      }
    }
    return sympy;
  }

  private String nodeListToSymPyString (List<Element> nodelist, boolean simplify) throws QTIScoringException {
    List<String> symPyStrings = nodeListToSymPyStringList (nodelist, simplify);
    boolean first = true;
    String ret = "";
    for (String s : symPyStrings) {
      if (first)
        first = false;
      else
        ret += ", ";
      ret += s;
    }
    if (symPyStrings.size () == 1)
      return ret;
    else
      return "[" + ret + "]";
  }

  public List<Element> stripTopMRow (List<Element> nodelist) {
    List<Element> toplevel = nodelist;
    while (true) {
      if (toplevel != null && toplevel.size () == 1 && "mrow".equals (toplevel.get (0).getName ())) {
        toplevel = toplevel.get (0).getChildren ();
      } else
        break;
    }
    return toplevel;
  }

  public List<String> nodeListToSymPyStringList (List<Element> nodelist, boolean simplify) throws QTIScoringException {
    List<String> ret = new ArrayList<String> ();
    if (nodelist.size () == 0)
      return ret;
    // First split on equal signs and inequalities
    int start = 0;
    int count = 0;
    String lhs = "", inequality = "", innerTxt;
    for (int i = 0; i < nodelist.size (); i++) {
      Element node = nodelist.get (i);
      innerTxt = node.getText ();
      if (node.getName ().equals ("mo")
          && (innerTxt.equals ("=") || innerTxt.equals ("&lt;") || innerTxt.equals ("<") || innerTxt.equals ("&gt;") || innerTxt.equals (">") || innerTxt.equals ("≤") || innerTxt.equals ("≥")
              || innerTxt.equals ("<=") || innerTxt.equals (">=") || innerTxt.equals ("≠") || innerTxt.equals ("!="))) {
        switch (innerTxt) {
        case "=":
          inequality = "Eq";
          break;
        case "<":
        case "&lt;":
          inequality = "Lt";
          break;
        case "&gt;":
        case ">":
          inequality = "Gt";
          break;
        case "≤":
        case "<=":
          inequality = "Le";
          break;
        case "≥":
        case ">=":
          inequality = "Ge";
          break;
        case "≠":
        case "!=":
          inequality = "Ne";
          break;
        }
        if (count == 0) {
          lhs = inequality + "(" + toSymPy (nodelist, start, i - 1, new PartialSymPyObject (), simplify).getSymPyString () + ",";
        } else {
          String exp = toSymPy (nodelist, start, i - 1, new PartialSymPyObject (), simplify).getSymPyString ();
          ret.add (lhs + exp + ")");
          lhs = inequality + "(" + exp + ",";
        }
        count += 1;
        start = i + 1;
      }
    }
    if (count == 0) {
      ret.add (toSymPy (nodelist, 0, nodelist.size () - 1, new PartialSymPyObject (), simplify).getSymPyString ());
      return ret;
    }
    if (count == 1) {
      ret.add (lhs + toSymPy (nodelist, start, nodelist.size () - 1, new PartialSymPyObject (), simplify).getSymPyString () + ")");
      return ret;
    } else {
      String exp = toSymPy (nodelist, start, nodelist.size () - 1, new PartialSymPyObject (), simplify).getSymPyString ();
      ret.add (lhs + exp + ")");
      return ret;
    }
  }

  // needed for spotting mixed fractions (2+1/3 written a 2 1/3)
  private boolean isMathMLSimpleNumericFraction (Element xmlNode) {
    if (!StringUtils.equals (xmlNode.getName (), "mfrac"))
      return false;
    List<Element> mfracnodes = xmlNode.getChildren ();
    if (mfracnodes.size () != 2 || !(isMathMLSimpleInteger (mfracnodes.get (0)) || isMathMLLongInteger (mfracnodes.get (0)))
        || !(isMathMLSimpleInteger (mfracnodes.get (1)) || isMathMLLongInteger (mfracnodes.get (1))))
      return false;
    return true;
  }

  // Simple non-negative integer (not -5, not 2**3, not 2. or 2.0, not 4/2)
  private boolean isMathMLSimpleInteger (Element xmlNode) {
    if (!StringUtils.equals (xmlNode.getName (), "mn"))
      return false;
    for (int i = 0; i < xmlNode.getText ().length (); i++)
      if (!Character.isDigit (xmlNode.getText ().charAt (i)))
        return false;
    return true;
  }

  // Multi-digit non-negative integer (123)
  private boolean isMathMLLongInteger (Element xmlNode) {
    if (!StringUtils.equals (xmlNode.getName (), "mrow"))
      return false;
    for (int i = 0; i < xmlNode.getChildren ().size (); i++)
      if (!isMathMLSimpleInteger (xmlNode.getChildren ().get (i)))
        return false;
    return true;
  }

  @Override
  public String toString () {
    return MathExpressionInfo.getXmlStringFromMathExpressionInfo (toMathExpressionInfo ());
  }

  public String toComparableString()
  {
      String response = "[";
      boolean first = true;
      for (String s : getSympyResponse())
      {
          if (first)
              first = false;
          else
              response += ", ";
          response += s;
      }
      response += "]";
      return response;
  }

  public double toDouble () {
    _Ref<Double> response = new _Ref<> (Double.NaN);
    if (sympyResponse.size () == 1) {
      JavaPrimitiveUtils.doubleTryParse (StringHelper.trim (sympyResponse.get (0), doubleCharsToTrim), response);
    }
    return response.get ();
  }

  public MathExpressionInfo toMathExpressionInfo () {
    MathExpressionInfo returnValue = new MathExpressionInfo ();
    returnValue.setAppliedCorrection (_appliedCorrection);
    returnValue.setOvercorrectedSympyResponse (_overcorrectedSympyResponse);
    returnValue.setSympyResponseNotSimplified (_sympyResponseNotSimplified);
    returnValue.setTriedToApplyCorrection (_triedToApplyCorrection);
    returnValue.setSympyResponse (sympyResponse);
    return returnValue;
  }
}
