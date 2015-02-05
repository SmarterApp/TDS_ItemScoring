package qtiscoringengine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.cassandra.thrift.Cassandra.AsyncProcessor.system_add_column_family;

public class RegexTest {
	public static void main3(String[] args) {

		try {
			String value = "b { } {{{ a }} ";
			final String StringTokenPattern = "[{} \t\n\r]{1,}";
			Pattern p = Pattern.compile(StringTokenPattern);
			Matcher m = p.matcher(value);
			System.err.println(m.replaceAll(" "));
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public static void main2(String[] args) {

		try {
			String value = "$a";
			final String StringTokenPattern = "([a-zA-Z0-9_<=> .]+)|(\\$)([a-zA-Z0-9_.]+)";
			Pattern p = Pattern.compile(StringTokenPattern);
			Matcher m = p.matcher(value);
			while (m.find()) {
				String token = m.group();
				System.err.println(token);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String value = "Lt(A,A)";
			// final String stringTokenPattern =
			// "(^[\\(Eq\\)]+)([a-z]+) | ((^[\\(Eq\\)]+)\\(.*,[a-z]\\)+)";
			// final String stringTokenPattern = "^Eq\\(.*,[A-z]\\)$";
			// final String stringTokenPattern = "^(Gt|Ge|Lt|Le)\\([A-z],";
			final String stringTokenPattern = "^(Gt|Ge|Lt|Le)\\(.*,[A-z]\\)$";
			boolean b = Pattern.matches(stringTokenPattern, value);
			System.out.println(b);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public static void main5(String[] args) {

		try {

			String var = "variable";
			String value = "Le(n," + var + ")";
			// final String stringTokenPattern = "^Eq\\(" + var + "," ;
			// final String stringTokenPattern = "^Eq\\(.*," + var + "\\)$";
			//final String stringTokenPattern = "^(Gt|Ge|Lt|Le)\\(" + var + ",";
			final String stringTokenPattern = "^(Gt|Ge|Lt|Le)\\(.*," + var + "\\)$";
			boolean b = Pattern.matches(stringTokenPattern, value);
			System.err.println(b);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

}
