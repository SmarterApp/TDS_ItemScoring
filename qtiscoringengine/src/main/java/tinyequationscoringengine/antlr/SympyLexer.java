/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
// $ANTLR 3.5.2 Sympy.g3 2015-01-02 14:17:12

package tinyequationscoringengine.antlr;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class SympyLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int ASSIGN=4;
	public static final int CALL=5;
	public static final int DIV=6;
	public static final int ELIST=7;
	public static final int EXPR=8;
	public static final int FLOAT=9;
	public static final int ID=10;
	public static final int INT=11;
	public static final int MULT=12;
	public static final int NEWLINE=13;
	public static final int PLUS=14;
	public static final int POW=15;
	public static final int REL=16;
	public static final int SUB=17;
	public static final int UNARY_MINUS=18;
	public static final int WS=19;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public SympyLexer() {} 
	public SympyLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public SympyLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "Sympy.g3"; }

	// $ANTLR start "ASSIGN"
	public final void mASSIGN() throws RecognitionException {
		try {
			int _type = ASSIGN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:10:8: ( '=' )
			// Sympy.g3:10:10: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ASSIGN"

	// $ANTLR start "MULT"
	public final void mMULT() throws RecognitionException {
		try {
			int _type = MULT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:11:6: ( '*' )
			// Sympy.g3:11:8: '*'
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
	// $ANTLR end "MULT"

	// $ANTLR start "PLUS"
	public final void mPLUS() throws RecognitionException {
		try {
			int _type = PLUS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:12:6: ( '+' )
			// Sympy.g3:12:8: '+'
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
	// $ANTLR end "PLUS"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:13:7: ( '(' )
			// Sympy.g3:13:9: '('
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
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:14:7: ( ')' )
			// Sympy.g3:14:9: ')'
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
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:15:7: ( ',' )
			// Sympy.g3:15:9: ','
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
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:16:7: ( '-' )
			// Sympy.g3:16:9: '-'
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
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:17:7: ( '/' )
			// Sympy.g3:17:9: '/'
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
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:18:7: ( 'Eq' )
			// Sympy.g3:18:9: 'Eq'
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
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:19:7: ( 'Ge' )
			// Sympy.g3:19:9: 'Ge'
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
	// $ANTLR end "T__26"

	// $ANTLR start "T__27"
	public final void mT__27() throws RecognitionException {
		try {
			int _type = T__27;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:20:7: ( 'Gt' )
			// Sympy.g3:20:9: 'Gt'
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
	// $ANTLR end "T__27"

	// $ANTLR start "T__28"
	public final void mT__28() throws RecognitionException {
		try {
			int _type = T__28;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:21:7: ( 'Le' )
			// Sympy.g3:21:9: 'Le'
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
	// $ANTLR end "T__28"

	// $ANTLR start "T__29"
	public final void mT__29() throws RecognitionException {
		try {
			int _type = T__29;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:22:7: ( 'Lt' )
			// Sympy.g3:22:9: 'Lt'
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
	// $ANTLR end "T__29"

	// $ANTLR start "T__30"
	public final void mT__30() throws RecognitionException {
		try {
			int _type = T__30;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:23:7: ( 'Ne' )
			// Sympy.g3:23:9: 'Ne'
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
	// $ANTLR end "T__30"

	// $ANTLR start "T__31"
	public final void mT__31() throws RecognitionException {
		try {
			int _type = T__31;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:24:7: ( '_' )
			// Sympy.g3:24:9: '_'
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
	// $ANTLR end "T__31"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// Sympy.g3:103:5: ( ( 'a' .. 'z' | 'A' .. 'Z' )+ )
			// Sympy.g3:103:7: ( 'a' .. 'z' | 'A' .. 'Z' )+
			{
			// Sympy.g3:103:7: ( 'a' .. 'z' | 'A' .. 'Z' )+
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
					// Sympy.g3:
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
			// Sympy.g3:105:5: ( INT '.' ( INT )* | '.' ( INT )+ )
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
					// Sympy.g3:105:9: INT '.' ( INT )*
					{
					mINT(); 

					match('.'); 
					// Sympy.g3:105:17: ( INT )*
					loop2:
					while (true) {
						int alt2=2;
						int LA2_0 = input.LA(1);
						if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
							alt2=1;
						}

						switch (alt2) {
						case 1 :
							// Sympy.g3:105:17: INT
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
					// Sympy.g3:106:9: '.' ( INT )+
					{
					match('.'); 
					// Sympy.g3:106:13: ( INT )+
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
							// Sympy.g3:106:13: INT
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
			// Sympy.g3:108:5: ( ( '0' .. '9' )+ )
			// Sympy.g3:108:7: ( '0' .. '9' )+
			{
			// Sympy.g3:108:7: ( '0' .. '9' )+
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
					// Sympy.g3:
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
			// Sympy.g3:109:9: ( ( '\\r' )? '\\n' )
			// Sympy.g3:109:11: ( '\\r' )? '\\n'
			{
			// Sympy.g3:109:11: ( '\\r' )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0=='\r') ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// Sympy.g3:109:11: '\\r'
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
			// Sympy.g3:110:4: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
			// Sympy.g3:110:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
			{
			// Sympy.g3:110:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
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
					// Sympy.g3:
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
		// Sympy.g3:1:8: ( ASSIGN | MULT | PLUS | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | ID | FLOAT | INT | NEWLINE | WS )
		int alt8=20;
		alt8 = dfa8.predict(input);
		switch (alt8) {
			case 1 :
				// Sympy.g3:1:10: ASSIGN
				{
				mASSIGN(); 

				}
				break;
			case 2 :
				// Sympy.g3:1:17: MULT
				{
				mMULT(); 

				}
				break;
			case 3 :
				// Sympy.g3:1:22: PLUS
				{
				mPLUS(); 

				}
				break;
			case 4 :
				// Sympy.g3:1:27: T__20
				{
				mT__20(); 

				}
				break;
			case 5 :
				// Sympy.g3:1:33: T__21
				{
				mT__21(); 

				}
				break;
			case 6 :
				// Sympy.g3:1:39: T__22
				{
				mT__22(); 

				}
				break;
			case 7 :
				// Sympy.g3:1:45: T__23
				{
				mT__23(); 

				}
				break;
			case 8 :
				// Sympy.g3:1:51: T__24
				{
				mT__24(); 

				}
				break;
			case 9 :
				// Sympy.g3:1:57: T__25
				{
				mT__25(); 

				}
				break;
			case 10 :
				// Sympy.g3:1:63: T__26
				{
				mT__26(); 

				}
				break;
			case 11 :
				// Sympy.g3:1:69: T__27
				{
				mT__27(); 

				}
				break;
			case 12 :
				// Sympy.g3:1:75: T__28
				{
				mT__28(); 

				}
				break;
			case 13 :
				// Sympy.g3:1:81: T__29
				{
				mT__29(); 

				}
				break;
			case 14 :
				// Sympy.g3:1:87: T__30
				{
				mT__30(); 

				}
				break;
			case 15 :
				// Sympy.g3:1:93: T__31
				{
				mT__31(); 

				}
				break;
			case 16 :
				// Sympy.g3:1:99: ID
				{
				mID(); 

				}
				break;
			case 17 :
				// Sympy.g3:1:102: FLOAT
				{
				mFLOAT(); 

				}
				break;
			case 18 :
				// Sympy.g3:1:108: INT
				{
				mINT(); 

				}
				break;
			case 19 :
				// Sympy.g3:1:112: NEWLINE
				{
				mNEWLINE(); 

				}
				break;
			case 20 :
				// Sympy.g3:1:120: WS
				{
				mWS(); 

				}
				break;

		}
	}


	protected DFA8 dfa8 = new DFA8(this);
	static final String DFA8_eotS =
		"\11\uffff\4\16\2\uffff\1\32\1\uffff\1\23\1\33\1\uffff\1\34\1\35\1\36\1"+
		"\37\1\40\1\41\10\uffff";
	static final String DFA8_eofS =
		"\42\uffff";
	static final String DFA8_minS =
		"\1\11\10\uffff\1\161\3\145\2\uffff\1\56\1\uffff\1\12\1\11\1\uffff\6\101"+
		"\10\uffff";
	static final String DFA8_maxS =
		"\1\172\10\uffff\1\161\2\164\1\145\2\uffff\1\71\1\uffff\1\12\1\40\1\uffff"+
		"\6\172\10\uffff";
	static final String DFA8_acceptS =
		"\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\4\uffff\1\17\1\20\1\uffff\1"+
		"\21\2\uffff\1\24\6\uffff\1\22\1\23\1\11\1\12\1\13\1\14\1\15\1\16";
	static final String DFA8_specialS =
		"\42\uffff}>";
	static final String[] DFA8_transitionS = {
			"\1\23\1\22\2\uffff\1\21\22\uffff\1\23\7\uffff\1\4\1\5\1\2\1\3\1\6\1\7"+
			"\1\20\1\10\12\17\3\uffff\1\1\3\uffff\4\16\1\11\1\16\1\12\4\16\1\13\1"+
			"\16\1\14\14\16\4\uffff\1\15\1\uffff\32\16",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\24",
			"\1\25\16\uffff\1\26",
			"\1\27\16\uffff\1\30",
			"\1\31",
			"",
			"",
			"\1\20\1\uffff\12\17",
			"",
			"\1\22",
			"\2\23\2\uffff\1\23\22\uffff\1\23",
			"",
			"\32\16\6\uffff\32\16",
			"\32\16\6\uffff\32\16",
			"\32\16\6\uffff\32\16",
			"\32\16\6\uffff\32\16",
			"\32\16\6\uffff\32\16",
			"\32\16\6\uffff\32\16",
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
			return "1:1: Tokens : ( ASSIGN | MULT | PLUS | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | ID | FLOAT | INT | NEWLINE | WS );";
		}
	}

}
