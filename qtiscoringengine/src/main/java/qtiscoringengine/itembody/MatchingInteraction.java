/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengine.itembody;

import java.util.List;

import org.jdom2.Element;

public abstract class MatchingInteraction extends Interaction
{
  protected MatchingInteraction (Element node, String responseIdentifier, ItemType type)
  {
    super (node, responseIdentifier, type);
  }

  public abstract List<String> getMatchValues ();

  @Override
  public int getNumberOfAnswersInResponse () {
    return 2;
  }
}
