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

using System.Data;
using System.IO;

namespace Ast.OpenCsv {
	/// <summary>
	/// 
	/// </summary>
	public class CsvReader {
		/// <summary>
		/// 
		/// </summary>
		/// <param name="data"></param>
		/// <param name="headers"></param>
		/// <returns></returns>
		public static DataTable Parse(string data, bool headers) {
			return Parse(new StringReader(data), headers);
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="data"></param>
		/// <returns></returns>
		public static DataTable Parse(string data) {
			return Parse(new StringReader(data));
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="stream"></param>
		/// <returns></returns>
		public static DataTable Parse(TextReader stream) {
			return Parse(stream, false);
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="stream"></param>
		/// <param name="headers"></param>
		/// <returns></returns>
		public static DataTable Parse(TextReader stream, bool headers) {
            return Parse(stream, headers, ',');
        }

        public static DataTable Parse(TextReader stream, bool headers, char separatorChar)
        {
            DataTable table = new DataTable();

            CsvStream csv = new CsvStream(stream, separatorChar);
            string[] row = csv.GetNextRow();
            if (row == null) return null;

            if (headers)
            {
                foreach (string header in row)
                {
                    if (header != null && header.Length > 0 && !table.Columns.Contains(header))
                        table.Columns.Add(header, typeof(string));
                    else
                        table.Columns.Add(GetNextColumnHeader(table), typeof(string));
                }
                row = csv.GetNextRow();
            }

            while (row != null)
            {
                while (row.Length > table.Columns.Count)
                    table.Columns.Add(GetNextColumnHeader(table), typeof(string));
                table.Rows.Add(row);
                row = csv.GetNextRow();
            }
            return table;
        }

		static string GetNextColumnHeader(DataTable table) {
			int c = 1;
			while (true) {
				string h = "Column" + c++;
				if (!table.Columns.Contains(h))
					return h;
			}
		}
	}
}