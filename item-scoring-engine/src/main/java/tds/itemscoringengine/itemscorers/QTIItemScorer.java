/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.itemscorers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.TransformerClosure;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qtiscoringengine.BaseType;
import qtiscoringengine.Cardinality;
import qtiscoringengine.CustomOperatorRegistry;
import qtiscoringengine.DataElement;
import qtiscoringengine.Expression;
import qtiscoringengine.ICustomOperatorFactory;
import qtiscoringengine.ISECustomOperator;
import qtiscoringengine.QTIRubric;
import qtiscoringengine.ValidationLog;
import qtiscoringengine.VariableBindings;
import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.IItemScorerCallback;
import tds.itemscoringengine.ItemScoreInfo;
import tds.itemscoringengine.ItemScorerManagerImpl;
import tds.itemscoringengine.Proposition;
import tds.itemscoringengine.PropositionState;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.RubricContentSource;
import tds.itemscoringengine.RubricContentType;
import tds.itemscoringengine.ScorerInfo;
import tds.itemscoringengine.ScoreRationale;
import tds.itemscoringengine.ScoringStatus;
import tds.itemscoringengine.VarBinding;
import AIR.Common.Helpers._Ref;
import AIR.Common.Utilities.JavaPrimitiveUtils;
import AIR.Common.xml.XmlElement;
import AIR.Common.xml.XmlNamespaceManager;
import AIR.Common.xml.XmlReader;
import AIR.Common.xml.XmlReaderException;

public class QTIItemScorer implements IItemScorer
{
  private static final Logger _logger = LoggerFactory.getLogger (QTIItemScorer.class);

  public QTIItemScorer () {

  }

  // / <summary>
  // / Scorer meta information
  // / </summary>
  // / <param name="itemFormat"></param>
  // / <returns></returns>
  public ScorerInfo GetScorerInfo (String itemFormat) {
    return new ScorerInfo ("1.0", false, false, RubricContentSource.ItemXML);
  }

  @Override
  public void shutdown () {
  }

  // / <summary>
  // / The rubric for a QTI item is an Xml file.
  // /
  // / Note: Does not support caching or encryption currently
  // / </summary>
  // / <param name="responseInfo"></param>
  // / <param name="callback"></param>
  // / <returns></returns>
  public ItemScore ScoreItem (ResponseInfo responseInfo, IItemScorerCallback callback) {
    QTIRubricScorer scorer = new QTIRubricScorer ("");
    scorer.load (responseInfo.getContentType (), responseInfo.getRubric ());
    return scorer.scoreItem (responseInfo);
  }

}

/*
 * Shiva: the following two classes have now been moved to QTIScoringEngine
 * project as public classes. Please read the comments in the corresponding
 * class files to see the rationale behind this.
 */
// class ISECustomOperator implements ICustomOperatorFactory
// class ISECustomExpression extends Expression
