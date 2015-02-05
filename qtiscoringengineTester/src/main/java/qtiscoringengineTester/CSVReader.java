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
