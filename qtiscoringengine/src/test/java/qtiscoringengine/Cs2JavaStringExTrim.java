/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import qtiscoringengine.cs2java.StringHelper;

@RunWith (Parameterized.class)
public class Cs2JavaStringExTrim
{
  @Parameter
  public String str;

  @Parameter (1)
  public String expected;

  @Parameters
  public static Collection<Object[]> primeNumbers () {
    return Arrays.asList (new Object[][] { { "   123", "123" }, { "123 ", "123" },
        { "  123 ", "123" }, { "123", "123" }, { "1 23", "1 23" },
        { "  1 2 3 ", "1 2 3" }, { "{abc}", "abc" }, { "{}{},abc,", "abc" },
        { "{}{}{}", "" }, { ",,,", "" }, { "{,}", "" },
    });
  }

  @Test
  public void Test () {
    String result = new StringHelper (str).trim (new char[] { ' ', '\n', '\t', '\r', ',', '{', '}' });
    System.out.println (String.format ("Orig: %s; Result: %s, Expected: %s", str, result, expected));
    Assert.assertEquals (expected, result);
  }
}
