/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
// $ANTLR 3.5.2 Corrective.g3 2015-01-02 14:10:35

package tinyequationscoringengine.antlr; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class CorrectiveLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int FLOAT=4;
	public static final int ID=5;
	public static final int INT=6;
	public static final int MUL=7;
	public static final int NEWLINE=8;
	public static final int WS=9;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public CorrectiveLexer() {} 
	public CorrectiveLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public CorrectiveLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "Corrective.g3"; }

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:10:7: ( '(' )
			// Corrective.g3:10:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:11:7: ( ')' )
			// Corrective.g3:11:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:12:7: ( '*' )
			// Corrective.g3:12:9: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:13:7: ( '+' )
			// Corrective.g3:13:9: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:14:7: ( ',' )
			// Corrective.g3:14:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__14"

	// $ANTLR start "T__15"
	public final void mT__15() throws RecognitionException {
		try {
			int _type = T__15;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:15:7: ( '-' )
			// Corrective.g3:15:9: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__15"

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:16:7: ( '/' )
			// Corrective.g3:16:9: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:17:7: ( 'Eq' )
			// Corrective.g3:17:9: 'Eq'
			{
			match("Eq"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:18:7: ( 'Ge' )
			// Corrective.g3:18:9: 'Ge'
			{
			match("Ge"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:19:7: ( 'Gt' )
			// Corrective.g3:19:9: 'Gt'
			{
			match("Gt"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:20:7: ( 'Le' )
			// Corrective.g3:20:9: 'Le'
			{
			match("Le"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:21:7: ( 'Lt' )
			// Corrective.g3:21:9: 'Lt'
			{
			match("Lt"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:22:7: ( 'Ne' )
			// Corrective.g3:22:9: 'Ne'
			{
			match("Ne"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:23:7: ( '_' )
			// Corrective.g3:23:9: '_'
			{
			match('_'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:129:5: ( ( 'a' .. 'z' | 'A' .. 'Z' )+ )
			// Corrective.g3:129:7: ( 'a' .. 'z' | 'A' .. 'Z' )+
			{
			// Corrective.g3:129:7: ( 'a' .. 'z' | 'A' .. 'Z' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= 'A' && LA1_0 <= 'Z')||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// Corrective.g3:
					{
					if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "FLOAT"
	public final void mFLOAT() throws RecognitionException {
		try {
			int _type = FLOAT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:131:5: ( INT '.' ( INT )* | '.' ( INT )+ )
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
				alt4=1;
			}
			else if ( (LA4_0=='.') ) {
				alt4=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// Corrective.g3:131:9: INT '.' ( INT )*
					{
					mINT(); 

					match('.'); 
					// Corrective.g3:131:17: ( INT )*
					loop2:
					while (true) {
						int alt2=2;
						int LA2_0 = input.LA(1);
						if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
							alt2=1;
						}

						switch (alt2) {
						case 1 :
							// Corrective.g3:131:17: INT
							{
							mINT(); 

							}
							break;

						default :
							break loop2;
						}
					}

					}
					break;
				case 2 :
					// Corrective.g3:132:9: '.' ( INT )+
					{
					match('.'); 
					// Corrective.g3:132:13: ( INT )+
					int cnt3=0;
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// Corrective.g3:132:13: INT
							{
							mINT(); 

							}
							break;

						default :
							if ( cnt3 >= 1 ) break loop3;
							EarlyExitException eee = new EarlyExitException(3, input);
							throw eee;
						}
						cnt3++;
					}

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FLOAT"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:134:5: ( ( '0' .. '9' )+ )
			// Corrective.g3:134:7: ( '0' .. '9' )+
			{
			// Corrective.g3:134:7: ( '0' .. '9' )+
			int cnt5=0;
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// Corrective.g3:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt5 >= 1 ) break loop5;
					EarlyExitException eee = new EarlyExitException(5, input);
					throw eee;
				}
				cnt5++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "NEWLINE"
	public final void mNEWLINE() throws RecognitionException {
		try {
			int _type = NEWLINE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:135:9: ( ( '\\r' )? '\\n' )
			// Corrective.g3:135:11: ( '\\r' )? '\\n'
			{
			// Corrective.g3:135:11: ( '\\r' )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0=='\r') ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// Corrective.g3:135:11: '\\r'
					{
					match('\r'); 
					}
					break;

			}

			match('\n'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NEWLINE"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Corrective.g3:136:4: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
			// Corrective.g3:136:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
			{
			// Corrective.g3:136:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
			int cnt7=0;
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( ((LA7_0 >= '\t' && LA7_0 <= '\n')||LA7_0=='\r'||LA7_0==' ') ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// Corrective.g3:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt7 >= 1 ) break loop7;
					EarlyExitException eee = new EarlyExitException(7, input);
					throw eee;
				}
				cnt7++;
			}

			skip();
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// Corrective.g3:1:8: ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | ID | FLOAT | INT | NEWLINE | WS )
		int alt8=19;
		alt8 = dfa8.predict(input);
		switch (alt8) {
			case 1 :
				// Corrective.g3:1:10: T__10
				{
				mT__10(); 

				}
				break;
			case 2 :
				// Corrective.g3:1:16: T__11
				{
				mT__11(); 

				}
				break;
			case 3 :
				// Corrective.g3:1:22: T__12
				{
				mT__12(); 

				}
				break;
			case 4 :
				// Corrective.g3:1:28: T__13
				{
				mT__13(); 

				}
				break;
			case 5 :
				// Corrective.g3:1:34: T__14
				{
				mT__14(); 

				}
				break;
			case 6 :
				// Corrective.g3:1:40: T__15
				{
				mT__15(); 

				}
				break;
			case 7 :
				// Corrective.g3:1:46: T__16
				{
				mT__16(); 

				}
				break;
			case 8 :
				// Corrective.g3:1:52: T__17
				{
				mT__17(); 

				}
				break;
			case 9 :
				// Corrective.g3:1:58: T__18
				{
				mT__18(); 

				}
				break;
			case 10 :
				// Corrective.g3:1:64: T__19
				{
				mT__19(); 

				}
				break;
			case 11 :
				// Corrective.g3:1:70: T__20
				{
				mT__20(); 

				}
				break;
			case 12 :
				// Corrective.g3:1:76: T__21
				{
				mT__21(); 

				}
				break;
			case 13 :
				// Corrective.g3:1:82: T__22
				{
				mT__22(); 

				}
				break;
			case 14 :
				// Corrective.g3:1:88: T__23
				{
				mT__23(); 

				}
				break;
			case 15 :
				// Corrective.g3:1:94: ID
				{
				mID(); 

				}
				break;
			case 16 :
				// Corrective.g3:1:97: FLOAT
				{
				mFLOAT(); 

				}
				break;
			case 17 :
				// Corrective.g3:1:103: INT
				{
				mINT(); 

				}
				break;
			case 18 :
				// Corrective.g3:1:107: NEWLINE
				{
				mNEWLINE(); 

				}
				break;
			case 19 :
				// Corrective.g3:1:115: WS
				{
				mWS(); 

				}
				break;

		}
	}


	protected DFA8 dfa8 = new DFA8(this);
	static final String DFA8_eotS =
		"\10\uffff\4\15\2\uffff\1\31\1\uffff\1\22\1\32\1\uffff\1\33\1\34\1\35\1"+
		"\36\1\37\1\40\10\uffff";
	static final String DFA8_eofS =
		"\41\uffff";
	static final String DFA8_minS =
		"\1\11\7\uffff\1\161\3\145\2\uffff\1\56\1\uffff\1\12\1\11\1\uffff\6\101"+
		"\10\uffff";
	static final String DFA8_maxS =
		"\1\172\7\uffff\1\161\2\164\1\145\2\uffff\1\71\1\uffff\1\12\1\40\1\uffff"+
		"\6\172\10\uffff";
	static final String DFA8_acceptS =
		"\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\4\uffff\1\16\1\17\1\uffff\1\20\2"+
		"\uffff\1\23\6\uffff\1\21\1\22\1\10\1\11\1\12\1\13\1\14\1\15";
	static final String DFA8_specialS =
		"\41\uffff}>";
	static final String[] DFA8_transitionS = {
			"\1\22\1\21\2\uffff\1\20\22\uffff\1\22\7\uffff\1\1\1\2\1\3\1\4\1\5\1\6"+
			"\1\17\1\7\12\16\7\uffff\4\15\1\10\1\15\1\11\4\15\1\12\1\15\1\13\14\15"+
			"\4\uffff\1\14\1\uffff\32\15",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\23",
			"\1\24\16\uffff\1\25",
			"\1\26\16\uffff\1\27",
			"\1\30",
			"",
			"",
			"\1\17\1\uffff\12\16",
			"",
			"\1\21",
			"\2\22\2\uffff\1\22\22\uffff\1\22",
			"",
			"\32\15\6\uffff\32\15",
			"\32\15\6\uffff\32\15",
			"\32\15\6\uffff\32\15",
			"\32\15\6\uffff\32\15",
			"\32\15\6\uffff\32\15",
			"\32\15\6\uffff\32\15",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			""
	};

	static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
	static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
	static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
	static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
	static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
	static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
	static final short[][] DFA8_transition;

	static {
		int numStates = DFA8_transitionS.length;
		DFA8_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
		}
	}

	protected class DFA8 extends DFA {

		public DFA8(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 8;
			this.eot = DFA8_eot;
			this.eof = DFA8_eof;
			this.min = DFA8_min;
			this.max = DFA8_max;
			this.accept = DFA8_accept;
			this.special = DFA8_special;
			this.transition = DFA8_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | ID | FLOAT | INT | NEWLINE | WS );";
		}
	}

}
