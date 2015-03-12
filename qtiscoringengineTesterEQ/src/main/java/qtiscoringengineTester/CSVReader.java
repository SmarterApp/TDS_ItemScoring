/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package qtiscoringengineTester;

public class CSVReader
{
  /*
   * This is a hacky CSV reader. For some reason the public CSVReader source
   * code seems to have problems with the "" in the responses. This one will
   * only be able to parse itemid~score~response strings and nothing else.
   */
  public static String[] getColumns (String input) {
    String[] columns = new String[3];

    // Shiva: I am not going to do a split as "~" may be within the response.
    int end = input.indexOf ('~');
    columns[0] = input.substring (0, end);

    int start = end;
    end = input.indexOf ('~', start + 1);
    columns[1] = input.substring (start + 1, end);

    start = end;
    columns[2] = input.substring (start + 1);

    return columns;
  }
}
