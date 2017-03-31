/*************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tinyequationscoringengine;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import AIR.Common.Configuration.AppSettingsHelper;
import org.jdom2.Element;

import qtiscoringengine.Expression;
import qtiscoringengine.ICustomOperatorFactory;

/**
 * @author temp_mbikkina
 *
 */
public class TinyEqCustomOpFactory implements ICustomOperatorFactory
{
  interface IEQConstructor
  {
    TinyEqExpression geteqConstructors (Element node);
  }

  public Map<String, IEQConstructor> _eqConstructors = new HashMap<String, IEQConstructor> ();

  public TinyEqCustomOpFactory (URI scoringService, int maxRetries, int timeoutInMillis) {
    MathScoringService.getInstance ().initialize (scoringService, maxRetries, timeoutInMillis);
    addConstructor ("PREPROCESSRESPONSE", new TEConPreprocess ());
    addConstructor ("ISEQUIVALENT", new TEConIsEquivalent ());
    addConstructor ("ISEQUIVALENTLOG", new TEConIsEquivalentLog ());
    addConstructor ("ISEQUIVALENTTRIG", new TEConIsEquivalentTrig ());
    addConstructor ("ISEMPTY", new TEConIsEmpty ());
    addConstructor ("NUMBERFROMEXPRESSION", new TEConNumberFromExpression ());
    addConstructor ("GETINEQUALITIESCOUNT", new TEConGetInequalitiesCount ());
    addConstructor ("ISMATCH", new TEConIsMatch ());
    addConstructor ("MATCHEXPRESSION", new TEConMatchExpression ());
    addConstructor ("LINECONTAINS", new TEConLineContains ());
    addConstructor ("EXRESSIONCONTAINS", new TEConExpressionContains ());
    addConstructor ("EVALUATE", new TEConEvaluate ());
    addConstructor ("GETEQUATIONSCOUNT", new TEConEquationsCount ());
  }

  private void addConstructor (String name, IEQConstructor del) {
    if (_eqConstructors.containsKey (name))
      _eqConstructors.remove (name);
    _eqConstructors.put (name, del);
  }

  private class TEConPreprocess implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEqPreProcess (node);
    }
  }

  private class TEConIsEquivalent implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEqIsEquivalent (node);
    }
  }

  private class TEConIsEquivalentLog implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEqIsEquivalentLog (node);
    }
  }

  private class TEConIsEquivalentTrig implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEqIsEquivalentTrig (node);
    }
  }

  private class TEConNumberFromExpression implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEqNumberFromExpression (node);
    }
  }

  private class TEConGetInequalitiesCount implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEqGetInequalitiesCount (node);
    }
  }

  private class TEConIsMatch implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEqIsMatch (node);
    }
  }

  private class TEConMatchExpression implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEqMatchExpression (node);
    }
  }

  private class TEConIsEmpty implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEqIsEmpty (node);
    }
  }

  private class TEConLineContains implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEEqLineContains (node);
    }
  }

  private class TEConExpressionContains implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEEqExpressionContains (node);
    }
  }

  private class TEConEvaluate implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEEqEvaluate (node);
    }
  }

  private class TEConEquationsCount implements IEQConstructor
  {
    public TinyEqExpression geteqConstructors (Element node) {
      return new TEEqEquationsCount (node);
    }
  }

  public boolean supportsOperator (Element customOperatorNode) {
    Element coElement = customOperatorNode;
    if (!"EQ".equals (coElement.getAttribute ("type").getValue ()))
      return false;

    return _eqConstructors.containsKey (coElement.getAttribute ("functionName").getValue ());
  }

  public Expression createExpression (Element customOperatorNode) {
    Element coElement = (Element) customOperatorNode;

    return (Expression) _eqConstructors.get (coElement.getAttribute ("functionName").getValue ()).geteqConstructors (coElement);
  }
}
