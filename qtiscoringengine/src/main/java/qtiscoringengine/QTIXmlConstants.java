/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class QTIXmlConstants
{
  public static final char[]   TokenDelimiters          = { ' ', '\n', '\t', '\r', ',', '{', '}' };
  public static final String[] NameSpaces               = new String[] { "http://www.imsglobal.org/xsd/imsqti_v2p1", "http://www.imsglobal.org/xsd/apip/apipv1p0/qtiitem/imsqti_v2p2",
      "http://www.imsglobal.org/xsd/apip/apipv1p0/qtiitem/imsqti_v2p1" };

  public static final String   ResponseRuleElementGroup = "qti:exitResponse|qti:lookupOutcomeValue|qti:responseCondition|qti:responseProcessingFragment|qti:setOutcomeValue";
  public static final String   ExpressionElementGroup   = "qti:and|qti:anyN|qti:baseValue|qti:containerSize|qti:contains|qti:correct|qti:customOperator|qti:default|qti:delete|qti:divide|qti:durationGTE|qti:durationLT|qti:equal|qti:equalRounded|qti:fieldValue|qti:gt|qti:gte|qti:index|qti:inside|qti:integerDivide|qti:integerModulus|qti:integerToFloat|qti:isNull|qti:lt|qti:lte|qti:mapResponse|qti:mapResponsePoint|qti:match|qti:member|qti:multiple|qti:not|qti:null|qti:numberCorrect|qti:numberIncorrect|qti:numberPresented|qti:numberResponded|qti:numberSelected|qti:or|qti:ordered|qti:outcomeMaximum|qti:outcomeMinimum|qti:patternMatch|qti:power|qti:product|qti:random|qti:randomFloat|qti:randomInteger|qti:round|qti:stringMatch|qti:substring|qti:subtract|qti:sum|qti:testVariables|qti:truncate|qti:variable";
  public static final String   ResponseIf               = "qti:responseIf";
  public static final String   ResponseElse             = "qti:responseElse";
  public static final String   ResponseElseIf           = "qti:responseElseIf";
  public static final String   ResponseCondition        = "qti:responseCondition";

  public static final String   ResponseExit             = "qti:exitResponse";
  public static final String   ResponseLookup           = "qti:lookupOutcomeValue";
  public static final String   ResponseFragment         = "qti:responseProcessingFragment";
  public static final String   ResponseSetOutcome       = "qti:setOutcomeValue";

  public static final String   AreaMapEntry             = "qti:areaMapEntry";
  public static final String   AreaMapping              = "qti:areaMapping";

  // Zach: added
  public static final String   DefaultValue             = "qti:defaultValue";
  public static final String   CorrectResponse          = "qti:correctResponse";
  public static final String   Mapping                  = "qti:mapping";
  public static final String   Value                    = "qti:value";
  public static final String   ResponseProcessing       = "qti:responseProcessing";
  public static final String   ResponseDeclaration      = "qti:responseDeclaration";
  public static final String   OutcomeDeclaration       = "qti:outcomeDeclaration";
  public static final String   MatchTable               = "qti:matchTable";
  public static final String   InterpolationTable       = "qti:interpolationTable";
  // Zach: end added

  public static final String   ResponseIfName           = "responseIf";
  public static final String   ResponseElseName         = "responseElse";
  public static final String   ResponseElseIfName       = "responseElseIf";
  public static final String   ResponseConditionName    = "responseCondition";

  public static final String   ResponseExitName         = "exitResponse";
  public static final String   ResponseLookupName       = "lookupOutcomeValue";
  public static final String   ResponseFragmentName     = "responseProcessingFragment";
  public static final String   ResponseSetOutcomeName   = "setOutcomeValue";

  public static class Expr
  {
    public static final String And              = "and";
    public static final String AnyN             = "anyN";
    public static final String BaseValue        = "baseValue";
    public static final String ContainerSize    = "containerSize";
    public static final String Contains         = "contains";
    public static final String Correct          = "correct";
    public static final String Custom           = "customOperator";
    public static final String Default          = "default";
    public static final String Delete           = "delete";
    public static final String Divide           = "divide";
    public static final String Equal            = "equal";
    public static final String EqualRounded     = "equalRounded";
    public static final String GT               = "gt";
    public static final String GTE              = "gte";
    public static final String Index            = "index";
    public static final String Inside           = "inside";
    public static final String IntDivide        = "integerDivide";
    public static final String IntMod           = "integerModulus";
    public static final String IntToFloat       = "integerToFloat";
    public static final String IsNull           = "isNull";
    public static final String LT               = "lt";
    public static final String LTE              = "lte";
    public static final String MapResponse      = "mapResponse";
    public static final String MapResponsePoint = "mapResponsePoint";
    public static final String Match            = "match";
    public static final String Member           = "member";
    public static final String Multiple         = "multiple";
    public static final String Not              = "not";
    public static final String Null             = "null";
    public static final String Or               = "or";
    public static final String Ordered          = "ordered";
    public static final String PatternMatch     = "patternMatch";
    public static final String Power            = "power";
    public static final String Product          = "product";
    public static final String Random           = "random";
    public static final String RandomFloat      = "randomFloat";
    public static final String RandomInt        = "randomInteger";
    public static final String Round            = "round";
    public static final String StringMatch      = "stringMatch";
    public static final String Substring        = "substring";
    public static final String Subtract         = "subtract";
    public static final String Sum              = "sum";
    public static final String Truncate         = "truncate";
    public static final String Variable         = "variable";

    public static final String DurationGTE      = "durationGTE";
    public static final String DurationLT       = "durationLT";
    public static final String FieldVal         = "fieldValue";
    public static final String NumCorrect       = "numberCorrect";
    public static final String NumIncorrect     = "numberIncorrect";
    public static final String NumPresented     = "numberPresented";
    public static final String NumResponded     = "numberResponded";
    public static final String NumSelected      = "numberSelected";
    public static final String OutcomeMaximum   = "outcomeMaximum";
    public static final String OutcomeMinimum   = "outcomeMinimum";
    public static final String TestVariables    = "testVariables";

  } // end inside class

  public static boolean containsSchemaLocation (String key) {
    for (String possibleKey : NameSpaces)
      if (StringUtils.equals (key, possibleKey))
        return true;
    return false;
  }

}// end class
