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

using System.Collections;
using System.IO;
using System.Text;

namespace Ast.OpenCsv {
	public class CsvStream {
		TextReader stream;
		bool EOS = false;
		bool EOL = false;
        private char _separator = ',';

		public CsvStream(TextReader s, char separator) {
			stream = s;
            this._separator = separator;
        }

        public CsvStream(TextReader s): this(s, ',')
        {
            stream = s;
        }

		public string[] GetNextRow() {
			ArrayList row = new ArrayList();
			while (true) {
				string item = GetNextItem();
				if (item == null)
					return row.Count == 0 ? null : (string[]) row.ToArray(typeof (string));
				row.Add(item);
			}
		}

		string GetNextItem() {
			if (EOL) {
				// previous item was last in line, start new line
				EOL = false;
				return null;
			}

			bool quoted = false;
			bool predata = true;
			bool postdata = false;
			StringBuilder sb = new StringBuilder();

			while (true) {
				char c = GetNextChar(true);
				if (EOS)
					return sb.Length > 0 ? sb.ToString() : null;

				if ((postdata || !quoted) && c == _separator)
					// end of item, return
					return sb.ToString();

				if ((predata || postdata || !quoted) && (c == '\x0A' || c == '\x0D')) {
					// we are at the end of the line, eat newline characters and exit
					EOL = true;
					if (c == '\x0D' && GetNextChar(false) == '\x0A')
						// new line sequence is 0D0A
						GetNextChar(true);
					return sb.ToString();
				}

				if (predata && c == ' ')
					// whitespace preceeding data, discard
					continue;

				if (predata && c == '"') {
					// quoted data is starting
					quoted = true;
					predata = false;
					continue;
				}

				if (predata) {
					// data is starting without quotes
					predata = false;
					sb.Append(c);
					continue;
				}

				if (c == '"' && quoted) {
					if (GetNextChar(false) == '"')
						// double quotes within quoted string means add a quote       
						sb.Append(GetNextChar(true));
					else
						// end-quote reached
						postdata = true;
					continue;
				}

				// all cases covered, character must be data
				sb.Append(c);
			}
		}

		char[] buffer = new char[4096];
		int pos = 0;
		int length = 0;

		char GetNextChar(bool eat) {
			if (pos >= length) {
				length = stream.ReadBlock(buffer, 0, buffer.Length);
				if (length == 0) {
					EOS = true;
					return '\0';
				}
				pos = 0;
			}
			if (eat)
				return buffer[pos++];
			else
				return buffer[pos];
		}
	}
}