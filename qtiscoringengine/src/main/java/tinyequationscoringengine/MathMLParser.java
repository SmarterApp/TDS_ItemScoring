package tinyequationscoringengine;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;

import qtiscoringengine.QTIScoringException;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;
import AIR.Common.xml.XmlReader;

public class MathMLParser
{

  public static MathExpressionSet processMathMLData (String MathMLstring) throws QTIScoringException {
    MathExpressionSet expressions = new MathExpressionSet ();

    String responseXMLString;
    if (MathMLstring.startsWith ("<response>"))
      responseXMLString = MathMLstring;
    else
      responseXMLString = "<response>" + MathMLstring + "</response>";

    Document mathMLDoc = null;
    try {
      mathMLDoc = (new XmlReader (new StringReader (responseXMLString))).getDocument ();
    } catch (Exception exp) {
      throw new QTIScoringException (exp);
    }

    XmlNamespaceManager nsMgr = new XmlNamespaceManager ();
    nsMgr.addNamespace ("m", "http://www.w3.org/1998/Math/MathML");

    // //Apply hack to oldmathjax responses only; New desmos widget always adds
    // a title attribute to the math node and old one didn't

    if (mathMLDoc.getRootElement ().getChildren ().size () > 0 && "math".equals (mathMLDoc.getRootElement ().getChildren ().get (0).getName ())
        && mathMLDoc.getRootElement ().getChildren ().get (0).getAttribute ("title") == null) {
      // / HACK BEGIN: FIX UP BUGS IN THE RESPONSE
      // Msup Issue
      while (applyMFRACFix (mathMLDoc, nsMgr)) {
      }
      // negation vs subtraction
      while (applySubtractionFix (mathMLDoc, nsMgr)) {
      }
      // Mfrac Issue
      while (applyMFRACFix (mathMLDoc, nsMgr)) {
      }
      // Delete mrows with no operators or children
      while (applyMROWwithNoOperatorsFix (mathMLDoc, nsMgr)) {
      }
      // / HACK END

    }

    List<Element> mathMLNodeList = mathMLDoc.getRootElement ().getChildren ();

    if (mathMLNodeList.size () > 0) {
      for (Element mathml : mathMLNodeList) {
        if (!"math".equals (mathml.getName ()))
          throw new QTIScoringException ("MathML2SymPyString should be called with a top level node with name 'math', this node has name '" + mathml.getName () + "'");

        List<Element> nodeList;

        // Case <math><mstyle>...</mstyle></math> Need to check this

        if (new XmlElement (mathml).getChildNodes ().size () == 1 && new XmlElement (mathml).getChildNodes ().get (0).getName ().equals ("mstyle")) {
          nodeList = new XmlElement (mathml).getChildNodes ().get (0).getChildren ();
        }
        // Case <math>...</math>, no <mstyle>

        else {
          nodeList = new XmlElement (mathml).getChildNodes ();

        }

        MathExpression mathExpObj = new MathExpression (nodeList);
        expressions.add (mathExpObj);
      }
    }
    return expressions;
  }

  // / <summary>
  // / Bug in Renderer can cause msup to contain bogus mrows in it. These mrows
  // are not visible to the user
  // / but change the base node for the exponent.
  // / </summary>
  // / <param name="xmlDocument"></param>

  public static boolean applyMSUPFix (Document xml, XmlNamespaceManager nsMgr) {
    boolean docModified = false;
    // MSup issue
    List<Element> matchingNodes = new XmlElement (xml.getRootElement ()).selectNodes ("//m:msup/m:mrow[m:mo]", nsMgr);
    for (Element mRowNode : matchingNodes) {
      XmlElement elementMRowNode = new XmlElement (mRowNode);
      if (!isBogusMROW (elementMRowNode))
        continue; // This is a legit mrow

      // Found a bogus mrow. Remove it and apply the msup to the last child of
      // the mrow.
      // <msup><mrow><mi>x</mi><mo>+</mo><mi>y</mi></mrow><mn>2</mn></msup>

      Element mSupNode = mRowNode.getParentElement ();

      XmlElement elementMSupNode = new XmlElement (mSupNode);

      if ((elementMSupNode.getChildNodes ().size ()) != 2) // If this happens,
                                                           // we should
      // leave. We can't fix this.
      {
        continue;
      }

      if (!(elementMSupNode.getFirstChild ().equals (mRowNode)))
        continue; // This hack only applies if the mrow is the base node and not
                  // the exponent

      // Get the base node for the msup. This is the last child of the mrow

      XmlElement newBaseNode = elementMRowNode.getLastChild ();
      elementMRowNode.removeChild (newBaseNode.getContentNode ());

      // Remove all the remaing mrow children and elevent them to be siblings of
      // the msup
      // *** Need to check this
      while (elementMRowNode.hasChildNodes ()) {
        XmlElement node = elementMRowNode.getFirstChild ();
        elementMRowNode.removeChild (node.getContentNode ());
        elementMSupNode.insertBefore (node.getContentNode (), elementMSupNode.getContentNode ());
      }

      // remove the mrow all together and splice the new base node in as the
      // first child.
      elementMSupNode.removeChild (elementMRowNode.getContentNode ());
      elementMSupNode.insertBefore (newBaseNode.getContentNode (), elementMSupNode.getContentNode ());
      docModified = true;
    }
    return docModified;
  }

  // / <summary>
  // / Bug in Renderer can cause bogus mrow around a subrtraction operator.
  // These mrows are not visible to the user
  // / but change the order of operations since we have 2 mrows now back to back
  // with no operator between them and a multiplier gets added between them by
  // the scoring engine
  // / </summary>
  // / <param name="xmlDocument"></param>
  public static boolean applySubtractionFix (Document xml, XmlNamespaceManager nsMgr) {
    boolean docModified = false;

    List<Element> matchingNodes = new XmlElement (xml.getRootElement ()).selectNodes ("//m:mrow[m:mo]", nsMgr);

    for (Element mRowNode : matchingNodes) {
      XmlElement elementmRowNode = new XmlElement (mRowNode);

      if (!isBogusMROW (elementmRowNode))
        continue; // This is a legit mrow

      if (hasComplexParent (mRowNode))
        continue; // we dont want to touch mrows that are within complex mml
                  // elements

      // Found a bogus mrow. Remove it and elevate all the children up 1 level
      // <mrow><mo>&#x2212;<!-- −
      // --></mo><mrow><mo>(</mo><mn>3</mn><mn>7</mn><mo>&#x00D7;<!-- ×
      // --></mo><mn>3</mn><mo>)</mo></mrow></mrow>

      Element mRowParentNode = mRowNode.getParentElement ();

      // Remove all the remaing mrow children and elevate them to be siblings of
      // the mrow

      while (elementmRowNode.hasChildNodes ()) {
        XmlElement node = elementmRowNode.getFirstChild ();
        elementmRowNode.removeChild (node.getContentNode ());
        new XmlElement (mRowParentNode).insertBefore (node.getContentNode (), elementmRowNode.getContentNode ());
      }

      // remove the mrow all together and splice the new base node in as the
      // first child.

      new XmlElement (mRowParentNode).removeChild (mRowNode);
      docModified = true;
    }

    return docModified;
  }

  // / <summary>
  // / Bug in Renderer can cause bogus mrow around the whole number in a mixed
  // fraction. This mrow is not visible to the user
  // / but since we have this mrow with no operator between it and the mfrac, a
  // multiplier gets added between them by the scoring engine and it is no
  // longer a mixed fraction
  // / </summary>
  // / <param name="xmlDocument"></param>
  public static boolean applyMFRACFix (Document xml, XmlNamespaceManager nsMgr) {
    boolean docModified = false;
    List<Element> matchingNodes = new XmlElement (xml.getRootElement ()).selectNodes ("//m:mfrac", nsMgr);

    for (Element mFracNode : matchingNodes) {
      XmlElement elementMFracNode = new XmlElement (mFracNode);
      if ((!(elementMFracNode.getPreviousSibling ().equals (null))) && "MROW".equalsIgnoreCase (elementMFracNode.getPreviousSibling ().getLocalName ())) {

        XmlElement mRowNode = new XmlElement (mFracNode).getPreviousSibling ();

        // the previous sibling was a mrow. Now, it is bogus ONLY IF it does not
        // have a <mo>(<mo> child
        if (!isBogusMROW (mRowNode))
          continue;

        // Found a bogus mrow. Remove it and elevate all the children up 1 level
        // <mrow><mn>2</mn></mrow><mfrac><mrow><mn>1</mn><mn>9</mn></mrow><mrow><mn>2</mn><mn>1</mn></mrow></mfrac>

        XmlElement mRowParentNode = XmlElement.getXmlElementForParent (mRowNode.getParent ());

        // Remove all the remaing mrow children and elevate them to be siblings
        // of the mrow

        while (mRowNode.hasChildNodes ()) {
          XmlElement node = mRowNode.getFirstChild ();
          mRowNode.removeChild (node.getContentNode ());
          mRowParentNode.insertBefore (node.getContentNode (), mRowNode.getContentNode ());
        }
        // remove the mrow all together and splice the new base node in as the
        // first child.
        mRowParentNode.removeChild (mRowNode.getContentNode ());
        docModified = true;
      }
    }

    return docModified;
  }

  // / <summary>
  // / Looks for and deletes any mrows that are empty or contain only <mn> or
  // <mi> elements
  // / </summary>
  // / <param name="xml"></param>
  // / <param name="nsMgr"></param>
  public static boolean applyMROWwithNoOperatorsFix (Document xml, XmlNamespaceManager nsMgr) {
    boolean docModified = false;
    List<Element> matchingNodes = new XmlElement (xml.getRootElement ()).selectNodes ("//m:mrow", nsMgr);

    for (Element mRowNode : matchingNodes) {
      // We dont want to mess with any mrows that are children of a complex
      // parent
      if (hasComplexParent (mRowNode))
        continue;

      if (new XmlElement (mRowNode).getChildNodes ().size () == 0) {
        new XmlElement (mRowNode.getParentElement ()).removeChild (mRowNode);
        continue;
      }

      // <mn>1</mn><mn>4</mn><mrow><mn>5</mn></mrow>
      boolean containsOnlyMnorMi = true;

      List<Element> filteredList = new ArrayList<Element> ();
      CollectionUtils.select (new XmlElement (mRowNode).getChildNodes (), new Predicate ()
      {

        @Override
        public boolean evaluate (Object object) {
          XmlElement child = new XmlElement ((Element) object);
          return !("MN".equalsIgnoreCase (child.getLocalName ()) || "MI".equalsIgnoreCase (child.getLocalName ()));
        }
      }, filteredList);

      for (Element child : filteredList) {
        containsOnlyMnorMi = false;
      }

      if (containsOnlyMnorMi) {
        Element mRowParentNode = mRowNode.getParentElement ();

        // Remove all the remaing mrow children and elevate them to be siblings
        // of the mrow
        while (new XmlElement (mRowNode).hasChildNodes ()) {

          XmlElement node = new XmlElement (mRowNode).getFirstChild ();
          new XmlElement (mRowNode).removeChild (node.getContentNode ());
          new XmlElement (mRowParentNode).insertBefore (node.getContentNode (), new XmlElement (mRowNode).getContentNode ());
        }
        // remove the mrow all together and splice the new base node in as the
        // first child.

        new XmlElement (mRowParentNode).removeChild (mRowNode);
        docModified = true;
      }
    }

    return docModified;
  }

  private static boolean isBogusMROW (XmlElement mRowNode) {
    // a mrow that only contains MN elements and a MO with a . is not bogus. It
    // just represents a floating point number.

    _Ref<Double> number = new _Ref<Double> ();

    if (JavaPrimitiveUtils.doubleTryParse (mRowNode.getInnerText (), number))
      return false;

    return mRowNode.getChildNodes ().size () > 0 && !("MO".equalsIgnoreCase (mRowNode.getFirstChild ().getLocalName ()) && ("(".equalsIgnoreCase (mRowNode.getFirstChild ().getInnerText ())));
  }

  private static boolean hasComplexParent (Element node) {
    Element parentNode = node.getParentElement ();

    return ("MFRAC".equalsIgnoreCase (parentNode.getName ()) || "MSUP".equalsIgnoreCase (parentNode.getName ()) || "MSUB".equalsIgnoreCase (parentNode.getName ())
        || "MSUPSUB".equalsIgnoreCase (parentNode.getName ()) || "MSQRT".equalsIgnoreCase (parentNode.getName ()) || "MROOT".equalsIgnoreCase (parentNode.getName ()));
  }
}
