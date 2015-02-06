package qtiscoringengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.Proposition;
import tds.itemscoringengine.PropositionState;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.RubricContentType;
import tds.itemscoringengine.ScoringStatus;
import tds.itemscoringengine.VarBinding;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Element;

import qtiscoringengine.cs2java.StringHelper;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.Utilities.TDSStringUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;

public class ISECustomExpression extends Expression
{
  private IItemScorer _itemScorer         = null;
  private String      _itemFormat         = null;
  private String      _rubric             = null;
  private String      _responseIdentifier = null;

  // A tuple used to store source/target pairs - used for bindInput and
  // bindOuput declaration parsing.
  private class NameMapping
  {
    public String sourceId;
    public String targetId;

    public NameMapping (String src, String tgt) {
      sourceId = src;
      targetId = tgt;
    }
  }

  private List<NameMapping> _incomingIdentifiers = new ArrayList<NameMapping> ();
  private List<NameMapping> _outgoingIdentifiers = new ArrayList<NameMapping> ();

  public ISECustomExpression (Element node, IItemScorer externalScorer) {
    super (node, 0, Integer.MAX_VALUE, BaseType.Boolean, Cardinality.Single);
    XmlNamespaceManager nsMgr = new XmlNamespaceManager ();
    nsMgr.addNamespace ("air", "http://www.air.org");

    XmlElement nodeElement = new XmlElement (node);

    _itemScorer = externalScorer;
    _itemFormat = getOperatorType (node);
    _rubric = new XmlElement (nodeElement.selectSingleNode ("air:rubric", nsMgr)).getInnerXml ();
    _responseIdentifier = nodeElement.selectSingleNode ("air:responseComponent", nsMgr).getAttributeValue ("identifier");

    List<Element> bindInputsList = nodeElement.selectNodes ("air:bindInput", nsMgr);
    if (bindInputsList != null) // These are the bindings going from QTI
                                // executive to the ISE - values evaluated by
                                // QTI and passed into ISE
    {
      for (Element incomingBinding : bindInputsList) {
        _incomingIdentifiers.add (new NameMapping (incomingBinding.getAttributeValue ("sourceIdentifier"), incomingBinding.getAttributeValue ("targetIdentifier")));
      }
    }

    List<Element> bindOutput = nodeElement.selectNodes ("air:bindOutput", nsMgr);
    if (bindOutput != null) // These are the bindings going from ISE to the QTI
                            // executive - values evaluated by ISE and made
                            // available to QTI
    {
      for (Element outgoingBinding : bindOutput) {
        _outgoingIdentifiers.add (new NameMapping (outgoingBinding.getAttributeValue ("sourceIdentifier"), outgoingBinding.getAttributeValue ("targetIdentifier")));
        ;
      }
    }
  }

  @Override
  protected DataElement exprEvaluate (VariableBindings vb, QTIRubric rubric, List<DataElement> paramValues) {
    String response = vb.getVariable (_responseIdentifier).toString ();

    ResponseInfo rinfo = new ResponseInfo (_itemFormat, "", response, _rubric, RubricContentType.ContentString, null, false)
    {
      {
        setOutgoingBindings (Arrays.asList (VarBinding.ALL)); // We alwyas ask
                                                              // for ALL from
                                                              // expressions and
                                                              // leave it to the
                                                              // QTI executive
                                                              // to decide which
                                                              // ones to expose
                                                              // to the caller
        // IncomingBindings = GetIncomingBindings(_incomingIdentifiers) // These
        // are bindings we can pass from QTI to ISE .. **TODO**
      }
    };

    ItemScore score = _itemScorer.ScoreItem (rinfo, null);

    rubric.getResponseProcessingState ().add (score);

    if (score.getScoreInfo ().getRationale ().getBindings () != null) {
      for (final VarBinding binding : score.getScoreInfo ().getRationale ().getBindings ()) {
        // Look through the bindOutput declarations to see whether this binding
        // from ISE needs to be bound to a QTI variable and to which one
        NameMapping bindOutputDirective = (NameMapping) CollectionUtils.find (_outgoingIdentifiers, new Predicate ()
        {

          @Override
          public boolean evaluate (Object object) {
            NameMapping x = (NameMapping) object;
            return StringUtils.equals (x.sourceId, binding.getName ());
          }
        });

        if (bindOutputDirective.targetId != null && vb.getVariable (bindOutputDirective.targetId) != null) // this
                                                                                                           // is
                                                                                                           // added
                                                                                                           // so
                                                                                                           // that
                                                                                                           // only
                                                                                                           // variables
                                                                                                           // declared
                                                                                                           // in
                                                                                                           // the
                                                                                                           // QTI
                                                                                                           // rubric
                                                                                                           // will
                                                                                                           // be
                                                                                                           // bound
                                                                                                           // and
                                                                                                           // we
                                                                                                           // can
                                                                                                           // exclude
                                                                                                           // all
                                                                                                           // the
                                                                                                           // internal
                                                                                                           // native
                                                                                                           // scoring
                                                                                                           // engine
                                                                                                           // bindings
        {
          vb.setVariable (bindOutputDirective.targetId, DataElement.create (binding.getValue (), rubric.getOutcomeVariableBaseType (bindOutputDirective.targetId)));
        }
      }
    }
    if (score.getScoreInfo ().getRationale ().getPropositions () != null) {
      for (final Proposition proposition : score.getScoreInfo ().getRationale ().getPropositions ()) {
        // Look through the bindOutput declarations to see whether this binding
        // from ISE needs to be bound to a QTI variable and to which one
        NameMapping bindOutputDirective = (NameMapping) CollectionUtils.find (_outgoingIdentifiers, new Predicate ()
        {
          @Override
          public boolean evaluate (Object object) {
            NameMapping x = (NameMapping) object;
            return StringUtils.equals (x.sourceId, proposition.getName ());
          }
        });

        if (bindOutputDirective.targetId != null && vb.getVariable (bindOutputDirective.targetId) != null) // this
                                                                                                           // is
                                                                                                           // added
                                                                                                           // so
                                                                                                           // that
                                                                                                           // only
                                                                                                           // variables
                                                                                                           // declared
                                                                                                           // in
                                                                                                           // the
                                                                                                           // QTI
                                                                                                           // rubric
                                                                                                           // will
                                                                                                           // be
                                                                                                           // bound
                                                                                                           // and
                                                                                                           // we
                                                                                                           // can
                                                                                                           // exclude
                                                                                                           // all
                                                                                                           // the
                                                                                                           // internal
                                                                                                           // native
                                                                                                           // scoring
                                                                                                           // engine
                                                                                                           // propositions
        {
          vb.setVariable (bindOutputDirective.targetId, DataElement.create (TDSStringUtils.getCSharpBooleanToString (proposition.getState () == PropositionState.Asserted), BaseType.Boolean));
        }
      }
    }

    return score.getScoreInfo ().getStatus () == ScoringStatus.Scored ? DataElement.create ("true", BaseType.Boolean) : DataElement.create ("false", BaseType.Boolean);
  }

  private static String getOperatorType (Element customOperatorNode) {
    for (Attribute attribute : customOperatorNode.getAttributes ()) {
      if (StringUtils.equalsIgnoreCase (attribute.getName (), "CLASS")) {
        return attribute.getValue ();
      }
    }
    return "";
  }
}