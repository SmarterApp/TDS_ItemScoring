/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine.complexitemscorers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jdom2.JDOMException;

import TDS.Shared.Data.ReturnStatus;
import TDS.Shared.Exceptions.FailedReturnStatusException;
import tds.itemscoringengine.RubricContentType;
import tds.itemscoringengine.RubricStats;
import AIR.Common.xml.XmlReader;

public abstract class URLLoadableRubric implements IRubric
{

  private String _itemId;        
  private IRubricStats _rubricStats = new RubricStats();

  @Override
  public String getItemId () {
    return _itemId;
  }
  
  protected XmlReader loadAsXML(RubricContentType rubricContentType, Object rubric) throws FailedReturnStatusException {
    InputStream stream = null;
    try {
      if ( rubricContentType == RubricContentType.Uri ) {
        URL url;
        if ( rubric instanceof URL ) {
          url = (URL) rubric;
        }
        else {
          url = new URL( (String) rubric );
        }
        stream = url.openStream ();
      }
      else {
        stream = new ByteArrayInputStream ( ( (String) rubric).getBytes() );
      }
      return new XmlReader( stream );
    } catch (IOException | JDOMException e) {
      throw new FailedReturnStatusException( new ReturnStatus( "400 Bad Request", "Unable to find or parse rubric" ));
    }
  }

  @Override
  public IRubricStats getStats () {
    return _rubricStats;
  }

}
