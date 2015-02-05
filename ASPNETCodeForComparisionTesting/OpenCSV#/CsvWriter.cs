//------------------------------------------------------------------------------
// <copyright company="Ascertaint.org">
//     Copyright (c) Ascertaint.org.  All rights reserved.
// </copyright> 
//------------------------------------------------------------------------------
/**
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

using System;
using System.Data;
using System.IO;

namespace Ast.OpenCsv {
	/// <summary>
	/// 
	/// </summary>
	public class CsvWriter {
		/** The character used for escaping quotes. */
		public static char ESCAPE_CHARACTER = '"';

		/** The default separator to use if none is supplied to the constructor. */
		public static char DEFAULT_SEPARATOR = ',';

		/**
         * The default quote character to use if none is supplied to the
         * constructor.
         */
		public static char DEFAULT_QUOTE_CHARACTER = '"';

		/** The quote constant to use when you wish to suppress all quoting. */
		public static char NO_QUOTE_CHARACTER = '\u0000';

		/** Default line terminator uses platform encoding. */
		public static String DEFAULT_LINE_END = "\n";

		/// <summary>
		/// 
		/// </summary>
		/// <param name="table"></param>
		/// <param name="header"></param>
		/// <param name="quoteall"></param>
		/// <returns></returns>
		public static string DataTableToCsv(DataTable table, bool header, bool quoteall) {
			StringWriter writer = new StringWriter();

			DataTableToTextStream(writer, table, header, quoteall);

			return writer.ToString();
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="stream"></param>
		/// <param name="table"></param>
		/// <param name="header"></param>
		/// <param name="quoteall"></param>
		public static void DataTableToTextStream(TextWriter stream, DataTable table, bool header, bool quoteall) {
			if (header) {
				for (int i = 0; i < table.Columns.Count; i++) {
					WriteItem(stream, table.Columns[i].Caption, quoteall);
					if (i < table.Columns.Count - 1)
						stream.Write(DEFAULT_SEPARATOR);
					else
						stream.Write(DEFAULT_LINE_END);
				}
			}

			foreach (DataRow row in table.Rows) {
				for (int i = 0; i < table.Columns.Count; i++) {
					WriteItem(stream, row[i], quoteall);
					if (i < table.Columns.Count - 1)
						stream.Write(DEFAULT_SEPARATOR);
					else
						stream.Write(DEFAULT_LINE_END);
				}
			}
		}

		static void WriteItem(TextWriter stream, object item, bool quoteall) {
			if (item == null)
				return;
			string s = item.ToString();
			if (quoteall || s.IndexOfAny("\",\x0A\x0D".ToCharArray()) > -1)
				stream.Write("\"" + s.Replace("\"", "\"\"") + "\"");
			else
				stream.Write(s);
		}
	}
}