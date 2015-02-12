/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
// $ANTLR 3.5.2 Corrective.g3 2015-01-02 14:10:34
 
	package tinyequationscoringengine.antlr; 


import org.antlr.runtime.*;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

import AIR.Common.Helpers.StopWatch;


@SuppressWarnings("all")
public class CorrectiveParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "FLOAT", "ID", "INT", "MUL", "NEWLINE", 
		"WS", "'('", "')'", "'*'", "'+'", "','", "'-'", "'/'", "'Eq'", "'Ge'", 
		"'Gt'", "'Le'", "'Lt'", "'Ne'", "'_'"
	};
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
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public CorrectiveParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public CorrectiveParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return CorrectiveParser.tokenNames; }
	@Override public String getGrammarFileName() { return "Corrective.g3"; }


	private StopWatch _stopWatch = null;
	private int _depth = 0;
	// override it to disable recovery
	public Object RecoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException 
	{
		throw e;
	}
	// override it to disable recovery
	protected Object RecoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws MismatchedTokenException
	{ 
	    MismatchedTokenException mte = new MismatchedTokenException(ttype, input);
	    throw mte;
	}


	public static class relation_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "relation"
	// Corrective.g3:48:1: relation : ( 'Eq' | 'Le' | 'Lt' | 'Ge' | 'Gt' | 'Ne' );
	public final CorrectiveParser.relation_return relation() throws RecognitionException {
		CorrectiveParser.relation_return retval = new CorrectiveParser.relation_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken set1=null;

		CommonTree set1_tree=null;

		try {
			// Corrective.g3:49:2: ( 'Eq' | 'Le' | 'Lt' | 'Ge' | 'Gt' | 'Ne' )
			// Corrective.g3:
			{
			root_0 = (CommonTree)adaptor.nil();


			set1=(CommonToken)input.LT(1);
			if ( (input.LA(1) >= 17 && input.LA(1) <= 22) ) {
				input.consume();
				if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set1));
				state.errorRecovery=false;
				state.failed=false;
			}
			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "relation"


	public static class expressionList_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "expressionList"
	// Corrective.g3:57:1: expressionList : ( expr ( ',' expr )* |);
	public final CorrectiveParser.expressionList_return expressionList() throws RecognitionException {
		CorrectiveParser.expressionList_return retval = new CorrectiveParser.expressionList_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal3=null;
		ParserRuleReturnScope expr2 =null;
		ParserRuleReturnScope expr4 =null;

		CommonTree char_literal3_tree=null;

		try {
			// Corrective.g3:58:5: ( expr ( ',' expr )* |)
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( ((LA2_0 >= FLOAT && LA2_0 <= INT)||LA2_0==10||LA2_0==12||LA2_0==15||(LA2_0 >= 17 && LA2_0 <= 22)) ) {
				alt2=1;
			}
			else if ( (LA2_0==11) ) {
				alt2=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// Corrective.g3:58:9: expr ( ',' expr )*
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_expr_in_expressionList151);
					expr2=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expr2.getTree());

					// Corrective.g3:58:14: ( ',' expr )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( (LA1_0==14) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// Corrective.g3:58:15: ',' expr
							{
							char_literal3=(CommonToken)match(input,14,FOLLOW_14_in_expressionList154); if (state.failed) return retval;
							if ( state.backtracking==0 ) {
							char_literal3_tree = (CommonTree)adaptor.create(char_literal3);
							adaptor.addChild(root_0, char_literal3_tree);
							}

							pushFollow(FOLLOW_expr_in_expressionList156);
							expr4=expr();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) adaptor.addChild(root_0, expr4.getTree());

							}
							break;

						default :
							break loop1;
						}
					}

					}
					break;
				case 2 :
					// Corrective.g3:60:5: 
					{
					root_0 = (CommonTree)adaptor.nil();


					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expressionList"


	public static class expression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "expression"
	// Corrective.g3:63:1: public expression : expr EOF -> expr ;
	public final CorrectiveParser.expression_return expression() throws RecognitionException {
		CorrectiveParser.expression_return retval = new CorrectiveParser.expression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken EOF6=null;
		ParserRuleReturnScope expr5 =null;

		CommonTree EOF6_tree=null;
		RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// Corrective.g3:64:5: ( expr EOF -> expr )
			// Corrective.g3:64:9: expr EOF
			{
			pushFollow(FOLLOW_expr_in_expression189);
			expr5=expr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_expr.add(expr5.getTree());
			EOF6=(CommonToken)match(input,EOF,FOLLOW_EOF_in_expression191); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_EOF.add(EOF6);

			// AST REWRITE
			// elements: expr
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 64:18: -> expr
			{
				adaptor.addChild(root_0, stream_expr.nextTree());
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expression"


	public static class expr_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "expr"
	// Corrective.g3:67:1: expr : relationalExpression ;
	public final CorrectiveParser.expr_return expr() throws RecognitionException {
		CorrectiveParser.expr_return retval = new CorrectiveParser.expr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope relationalExpression7 =null;


		try {
			// Corrective.g3:67:5: ( relationalExpression )
			// Corrective.g3:67:9: relationalExpression
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_relationalExpression_in_expr209);
			relationalExpression7=relationalExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression7.getTree());

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expr"


	public static class relationalExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "relationalExpression"
	// Corrective.g3:70:1: relationalExpression : ( relation '(' additiveExpression ',' additiveExpression ')' | additiveExpression );
	public final CorrectiveParser.relationalExpression_return relationalExpression() throws RecognitionException {
		CorrectiveParser.relationalExpression_return retval = new CorrectiveParser.relationalExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal9=null;
		CommonToken char_literal11=null;
		CommonToken char_literal13=null;
		ParserRuleReturnScope relation8 =null;
		ParserRuleReturnScope additiveExpression10 =null;
		ParserRuleReturnScope additiveExpression12 =null;
		ParserRuleReturnScope additiveExpression14 =null;

		CommonTree char_literal9_tree=null;
		CommonTree char_literal11_tree=null;
		CommonTree char_literal13_tree=null;

		try {
			// Corrective.g3:71:5: ( relation '(' additiveExpression ',' additiveExpression ')' | additiveExpression )
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( ((LA3_0 >= 17 && LA3_0 <= 22)) ) {
				alt3=1;
			}
			else if ( ((LA3_0 >= FLOAT && LA3_0 <= INT)||LA3_0==10||LA3_0==12||LA3_0==15) ) {
				alt3=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}

			switch (alt3) {
				case 1 :
					// Corrective.g3:71:7: relation '(' additiveExpression ',' additiveExpression ')'
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_relation_in_relationalExpression226);
					relation8=relation();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, relation8.getTree());

					char_literal9=(CommonToken)match(input,10,FOLLOW_10_in_relationalExpression228); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal9_tree = (CommonTree)adaptor.create(char_literal9);
					adaptor.addChild(root_0, char_literal9_tree);
					}

					pushFollow(FOLLOW_additiveExpression_in_relationalExpression230);
					additiveExpression10=additiveExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression10.getTree());

					char_literal11=(CommonToken)match(input,14,FOLLOW_14_in_relationalExpression232); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal11_tree = (CommonTree)adaptor.create(char_literal11);
					adaptor.addChild(root_0, char_literal11_tree);
					}

					pushFollow(FOLLOW_additiveExpression_in_relationalExpression234);
					additiveExpression12=additiveExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression12.getTree());

					char_literal13=(CommonToken)match(input,11,FOLLOW_11_in_relationalExpression236); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal13_tree = (CommonTree)adaptor.create(char_literal13);
					adaptor.addChild(root_0, char_literal13_tree);
					}

					}
					break;
				case 2 :
					// Corrective.g3:72:4: additiveExpression
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_additiveExpression_in_relationalExpression242);
					additiveExpression14=additiveExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression14.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "relationalExpression"


	public static class additiveExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "additiveExpression"
	// Corrective.g3:75:1: additiveExpression : multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* ;
	public final CorrectiveParser.additiveExpression_return additiveExpression() throws RecognitionException {
		CorrectiveParser.additiveExpression_return retval = new CorrectiveParser.additiveExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken set16=null;
		ParserRuleReturnScope multiplicativeExpression15 =null;
		ParserRuleReturnScope multiplicativeExpression17 =null;

		CommonTree set16_tree=null;

		try {
			// Corrective.g3:76:5: ( multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* )
			// Corrective.g3:76:9: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression258);
			multiplicativeExpression15=multiplicativeExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression15.getTree());

			// Corrective.g3:76:34: ( ( '+' | '-' ) multiplicativeExpression )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==13||LA4_0==15) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// Corrective.g3:76:35: ( '+' | '-' ) multiplicativeExpression
					{
					set16=(CommonToken)input.LT(1);
					if ( input.LA(1)==13||input.LA(1)==15 ) {
						input.consume();
						if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set16));
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression269);
					multiplicativeExpression17=multiplicativeExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression17.getTree());

					}
					break;

				default :
					break loop4;
				}
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "additiveExpression"


	public static class multiplicativeExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "multiplicativeExpression"
	// Corrective.g3:79:1: multiplicativeExpression : ( preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )* | unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* | unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* trailingMultExpression );
	public final CorrectiveParser.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
		CorrectiveParser.multiplicativeExpression_return retval = new CorrectiveParser.multiplicativeExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken set20=null;
		CommonToken set23=null;
		CommonToken set26=null;
		ParserRuleReturnScope preceedingMultExpression18 =null;
		ParserRuleReturnScope superExpression19 =null;
		ParserRuleReturnScope multiplicativeExpression21 =null;
		ParserRuleReturnScope unaryExpression22 =null;
		ParserRuleReturnScope multiplicativeExpression24 =null;
		ParserRuleReturnScope unaryExpression25 =null;
		ParserRuleReturnScope multiplicativeExpression27 =null;
		ParserRuleReturnScope trailingMultExpression28 =null;

		CommonTree set20_tree=null;
		CommonTree set23_tree=null;
		CommonTree set26_tree=null;

		try {
			// Corrective.g3:80:5: ( preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )* | unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* | unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* trailingMultExpression )
			int alt8=3;
			switch ( input.LA(1) ) {
			case 12:
				{
				int LA8_1 = input.LA(2);
				if ( (synpred13_Corrective()) ) {
					alt8=1;
				}
				else if ( (synpred16_Corrective()) ) {
					alt8=2;
				}
				else if ( (true) ) {
					alt8=3;
				}

				}
				break;
			case 15:
				{
				int LA8_2 = input.LA(2);
				if ( (synpred16_Corrective()) ) {
					alt8=2;
				}
				else if ( (true) ) {
					alt8=3;
				}

				}
				break;
			case ID:
				{
				int LA8_3 = input.LA(2);
				if ( (synpred16_Corrective()) ) {
					alt8=2;
				}
				else if ( (true) ) {
					alt8=3;
				}

				}
				break;
			case INT:
				{
				int LA8_4 = input.LA(2);
				if ( (synpred16_Corrective()) ) {
					alt8=2;
				}
				else if ( (true) ) {
					alt8=3;
				}

				}
				break;
			case FLOAT:
				{
				int LA8_5 = input.LA(2);
				if ( (synpred16_Corrective()) ) {
					alt8=2;
				}
				else if ( (true) ) {
					alt8=3;
				}

				}
				break;
			case 10:
				{
				int LA8_6 = input.LA(2);
				if ( (synpred16_Corrective()) ) {
					alt8=2;
				}
				else if ( (true) ) {
					alt8=3;
				}

				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 8, 0, input);
				throw nvae;
			}
			switch (alt8) {
				case 1 :
					// Corrective.g3:80:7: preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )*
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_preceedingMultExpression_in_multiplicativeExpression288);
					preceedingMultExpression18=preceedingMultExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, preceedingMultExpression18.getTree());

					pushFollow(FOLLOW_superExpression_in_multiplicativeExpression290);
					superExpression19=superExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, superExpression19.getTree());

					// Corrective.g3:80:48: ( ( '*' | '/' ) multiplicativeExpression )*
					loop5:
					while (true) {
						int alt5=2;
						int LA5_0 = input.LA(1);
						if ( (LA5_0==12) ) {
							int LA5_2 = input.LA(2);
							if ( (synpred12_Corrective()) ) {
								alt5=1;
							}

						}
						else if ( (LA5_0==16) ) {
							int LA5_3 = input.LA(2);
							if ( (synpred12_Corrective()) ) {
								alt5=1;
							}

						}

						switch (alt5) {
						case 1 :
							// Corrective.g3:80:49: ( '*' | '/' ) multiplicativeExpression
							{
							set20=(CommonToken)input.LT(1);
							if ( input.LA(1)==12||input.LA(1)==16 ) {
								input.consume();
								if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set20));
								state.errorRecovery=false;
								state.failed=false;
							}
							else {
								if (state.backtracking>0) {state.failed=true; return retval;}
								MismatchedSetException mse = new MismatchedSetException(null,input);
								throw mse;
							}
							pushFollow(FOLLOW_multiplicativeExpression_in_multiplicativeExpression301);
							multiplicativeExpression21=multiplicativeExpression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression21.getTree());

							}
							break;

						default :
							break loop5;
						}
					}

					}
					break;
				case 2 :
					// Corrective.g3:81:4: unaryExpression ( ( '*' | '/' ) multiplicativeExpression )*
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression310);
					unaryExpression22=unaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression22.getTree());

					// Corrective.g3:81:20: ( ( '*' | '/' ) multiplicativeExpression )*
					loop6:
					while (true) {
						int alt6=2;
						int LA6_0 = input.LA(1);
						if ( (LA6_0==12) ) {
							int LA6_2 = input.LA(2);
							if ( (synpred15_Corrective()) ) {
								alt6=1;
							}

						}
						else if ( (LA6_0==16) ) {
							int LA6_3 = input.LA(2);
							if ( (synpred15_Corrective()) ) {
								alt6=1;
							}

						}

						switch (alt6) {
						case 1 :
							// Corrective.g3:81:21: ( '*' | '/' ) multiplicativeExpression
							{
							set23=(CommonToken)input.LT(1);
							if ( input.LA(1)==12||input.LA(1)==16 ) {
								input.consume();
								if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set23));
								state.errorRecovery=false;
								state.failed=false;
							}
							else {
								if (state.backtracking>0) {state.failed=true; return retval;}
								MismatchedSetException mse = new MismatchedSetException(null,input);
								throw mse;
							}
							pushFollow(FOLLOW_multiplicativeExpression_in_multiplicativeExpression321);
							multiplicativeExpression24=multiplicativeExpression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression24.getTree());

							}
							break;

						default :
							break loop6;
						}
					}

					}
					break;
				case 3 :
					// Corrective.g3:82:7: unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* trailingMultExpression
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression332);
					unaryExpression25=unaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression25.getTree());

					// Corrective.g3:82:23: ( ( '*' | '/' ) multiplicativeExpression )*
					loop7:
					while (true) {
						int alt7=2;
						int LA7_0 = input.LA(1);
						if ( (LA7_0==12) ) {
							int LA7_1 = input.LA(2);
							if ( (synpred18_Corrective()) ) {
								alt7=1;
							}

						}
						else if ( (LA7_0==16) ) {
							alt7=1;
						}

						switch (alt7) {
						case 1 :
							// Corrective.g3:82:24: ( '*' | '/' ) multiplicativeExpression
							{
							set26=(CommonToken)input.LT(1);
							if ( input.LA(1)==12||input.LA(1)==16 ) {
								input.consume();
								if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set26));
								state.errorRecovery=false;
								state.failed=false;
							}
							else {
								if (state.backtracking>0) {state.failed=true; return retval;}
								MismatchedSetException mse = new MismatchedSetException(null,input);
								throw mse;
							}
							pushFollow(FOLLOW_multiplicativeExpression_in_multiplicativeExpression343);
							multiplicativeExpression27=multiplicativeExpression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression27.getTree());

							}
							break;

						default :
							break loop7;
						}
					}

					pushFollow(FOLLOW_trailingMultExpression_in_multiplicativeExpression347);
					trailingMultExpression28=trailingMultExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, trailingMultExpression28.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "multiplicativeExpression"


	public static class preceedingMultExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "preceedingMultExpression"
	// Corrective.g3:85:1: preceedingMultExpression : '*' -> ID[\"x\"] '*' ;
	public final CorrectiveParser.preceedingMultExpression_return preceedingMultExpression() throws RecognitionException {
		CorrectiveParser.preceedingMultExpression_return retval = new CorrectiveParser.preceedingMultExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal29=null;

		CommonTree char_literal29_tree=null;
		RewriteRuleTokenStream stream_12=new RewriteRuleTokenStream(adaptor,"token 12");

		try {
			// Corrective.g3:86:2: ( '*' -> ID[\"x\"] '*' )
			// Corrective.g3:86:4: '*'
			{
			char_literal29=(CommonToken)match(input,12,FOLLOW_12_in_preceedingMultExpression362); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_12.add(char_literal29);

			// AST REWRITE
			// elements: 12
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 86:8: -> ID[\"x\"] '*'
			{
				adaptor.addChild(root_0, (CommonTree)adaptor.create(ID, "x"));
				adaptor.addChild(root_0, stream_12.nextNode());
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "preceedingMultExpression"


	public static class trailingMultExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "trailingMultExpression"
	// Corrective.g3:89:1: trailingMultExpression : '*' -> '*' ID[\"x\"] ;
	public final CorrectiveParser.trailingMultExpression_return trailingMultExpression() throws RecognitionException {
		CorrectiveParser.trailingMultExpression_return retval = new CorrectiveParser.trailingMultExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal30=null;

		CommonTree char_literal30_tree=null;
		RewriteRuleTokenStream stream_12=new RewriteRuleTokenStream(adaptor,"token 12");

		try {
			// Corrective.g3:90:2: ( '*' -> '*' ID[\"x\"] )
			// Corrective.g3:90:4: '*'
			{
			char_literal30=(CommonToken)match(input,12,FOLLOW_12_in_trailingMultExpression380); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_12.add(char_literal30);

			// AST REWRITE
			// elements: 12
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 90:8: -> '*' ID[\"x\"]
			{
				adaptor.addChild(root_0, stream_12.nextNode());
				adaptor.addChild(root_0, (CommonTree)adaptor.create(ID, "x"));
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "trailingMultExpression"


	public static class unaryExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "unaryExpression"
	// Corrective.g3:93:1: unaryExpression : ( '-' unaryExpression | superExpression );
	public final CorrectiveParser.unaryExpression_return unaryExpression() throws RecognitionException {
		CorrectiveParser.unaryExpression_return retval = new CorrectiveParser.unaryExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal31=null;
		ParserRuleReturnScope unaryExpression32 =null;
		ParserRuleReturnScope superExpression33 =null;

		CommonTree char_literal31_tree=null;

		try {
			// Corrective.g3:94:5: ( '-' unaryExpression | superExpression )
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==15) ) {
				alt9=1;
			}
			else if ( ((LA9_0 >= FLOAT && LA9_0 <= INT)||LA9_0==10||LA9_0==12) ) {
				alt9=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}

			switch (alt9) {
				case 1 :
					// Corrective.g3:94:9: '-' unaryExpression
					{
					root_0 = (CommonTree)adaptor.nil();


					char_literal31=(CommonToken)match(input,15,FOLLOW_15_in_unaryExpression403); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal31_tree = (CommonTree)adaptor.create(char_literal31);
					adaptor.addChild(root_0, char_literal31_tree);
					}

					pushFollow(FOLLOW_unaryExpression_in_unaryExpression405);
					unaryExpression32=unaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression32.getTree());

					}
					break;
				case 2 :
					// Corrective.g3:95:9: superExpression
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_superExpression_in_unaryExpression416);
					superExpression33=superExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, superExpression33.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "unaryExpression"


	public static class superExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "superExpression"
	// Corrective.g3:99:1: superExpression : postfixExpression ( ( '*' '*' postfixExpression ) )* ;
	public final CorrectiveParser.superExpression_return superExpression() throws RecognitionException {
		CorrectiveParser.superExpression_return retval = new CorrectiveParser.superExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal35=null;
		CommonToken char_literal36=null;
		ParserRuleReturnScope postfixExpression34 =null;
		ParserRuleReturnScope postfixExpression37 =null;

		CommonTree char_literal35_tree=null;
		CommonTree char_literal36_tree=null;

		try {
			// Corrective.g3:100:2: ( postfixExpression ( ( '*' '*' postfixExpression ) )* )
			// Corrective.g3:100:4: postfixExpression ( ( '*' '*' postfixExpression ) )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_postfixExpression_in_superExpression431);
			postfixExpression34=postfixExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, postfixExpression34.getTree());

			// Corrective.g3:101:3: ( ( '*' '*' postfixExpression ) )*
			loop10:
			while (true) {
				int alt10=2;
				int LA10_0 = input.LA(1);
				if ( (LA10_0==12) ) {
					int LA10_1 = input.LA(2);
					if ( (synpred20_Corrective()) ) {
						alt10=1;
					}

				}

				switch (alt10) {
				case 1 :
					// Corrective.g3:102:4: ( '*' '*' postfixExpression )
					{
					// Corrective.g3:102:4: ( '*' '*' postfixExpression )
					// Corrective.g3:102:5: '*' '*' postfixExpression
					{
					char_literal35=(CommonToken)match(input,12,FOLLOW_12_in_superExpression441); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal35_tree = (CommonTree)adaptor.create(char_literal35);
					adaptor.addChild(root_0, char_literal35_tree);
					}

					char_literal36=(CommonToken)match(input,12,FOLLOW_12_in_superExpression443); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal36_tree = (CommonTree)adaptor.create(char_literal36);
					adaptor.addChild(root_0, char_literal36_tree);
					}

					pushFollow(FOLLOW_postfixExpression_in_superExpression445);
					postfixExpression37=postfixExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, postfixExpression37.getTree());

					}

					}
					break;

				default :
					break loop10;
				}
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "superExpression"


	public static class postfixExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "postfixExpression"
	// Corrective.g3:106:1: postfixExpression : subscriptExpression ( '(' expressionList ')' )* ;
	public final CorrectiveParser.postfixExpression_return postfixExpression() throws RecognitionException {
		CorrectiveParser.postfixExpression_return retval = new CorrectiveParser.postfixExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal39=null;
		CommonToken char_literal41=null;
		ParserRuleReturnScope subscriptExpression38 =null;
		ParserRuleReturnScope expressionList40 =null;

		CommonTree char_literal39_tree=null;
		CommonTree char_literal41_tree=null;

		try {
			// Corrective.g3:107:5: ( subscriptExpression ( '(' expressionList ')' )* )
			// Corrective.g3:107:9: subscriptExpression ( '(' expressionList ')' )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_subscriptExpression_in_postfixExpression468);
			subscriptExpression38=subscriptExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, subscriptExpression38.getTree());

			// Corrective.g3:108:9: ( '(' expressionList ')' )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==10) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// Corrective.g3:109:13: '(' expressionList ')'
					{
					char_literal39=(CommonToken)match(input,10,FOLLOW_10_in_postfixExpression493); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal39_tree = (CommonTree)adaptor.create(char_literal39);
					adaptor.addChild(root_0, char_literal39_tree);
					}

					pushFollow(FOLLOW_expressionList_in_postfixExpression495);
					expressionList40=expressionList();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionList40.getTree());

					char_literal41=(CommonToken)match(input,11,FOLLOW_11_in_postfixExpression497); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal41_tree = (CommonTree)adaptor.create(char_literal41);
					adaptor.addChild(root_0, char_literal41_tree);
					}

					}
					break;

				default :
					break loop11;
				}
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "postfixExpression"


	public static class subscriptExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "subscriptExpression"
	// Corrective.g3:113:1: subscriptExpression : atom ( '_' atom )* ;
	public final CorrectiveParser.subscriptExpression_return subscriptExpression() throws RecognitionException {
		CorrectiveParser.subscriptExpression_return retval = new CorrectiveParser.subscriptExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal43=null;
		ParserRuleReturnScope atom42 =null;
		ParserRuleReturnScope atom44 =null;

		CommonTree char_literal43_tree=null;

		try {
			// Corrective.g3:114:2: ( atom ( '_' atom )* )
			// Corrective.g3:114:4: atom ( '_' atom )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_atom_in_subscriptExpression519);
			atom42=atom();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, atom42.getTree());

			// Corrective.g3:114:9: ( '_' atom )*
			loop12:
			while (true) {
				int alt12=2;
				int LA12_0 = input.LA(1);
				if ( (LA12_0==23) ) {
					alt12=1;
				}

				switch (alt12) {
				case 1 :
					// Corrective.g3:114:10: '_' atom
					{
					char_literal43=(CommonToken)match(input,23,FOLLOW_23_in_subscriptExpression522); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal43_tree = (CommonTree)adaptor.create(char_literal43);
					adaptor.addChild(root_0, char_literal43_tree);
					}

					pushFollow(FOLLOW_atom_in_subscriptExpression524);
					atom44=atom();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, atom44.getTree());

					}
					break;

				default :
					break loop12;
				}
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "subscriptExpression"


	public static class atom_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "atom"
	// Corrective.g3:117:1: atom : ( ID | INT | FLOAT | '(' expr ')' | '*' -> ID[\"x\"] );
	public final CorrectiveParser.atom_return atom() throws RecognitionException {
		CorrectiveParser.atom_return retval = new CorrectiveParser.atom_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken ID45=null;
		CommonToken INT46=null;
		CommonToken FLOAT47=null;
		CommonToken char_literal48=null;
		CommonToken char_literal50=null;
		CommonToken char_literal51=null;
		ParserRuleReturnScope expr49 =null;

		CommonTree ID45_tree=null;
		CommonTree INT46_tree=null;
		CommonTree FLOAT47_tree=null;
		CommonTree char_literal48_tree=null;
		CommonTree char_literal50_tree=null;
		CommonTree char_literal51_tree=null;
		RewriteRuleTokenStream stream_12=new RewriteRuleTokenStream(adaptor,"token 12");

		try {
			// Corrective.g3:118:5: ( ID | INT | FLOAT | '(' expr ')' | '*' -> ID[\"x\"] )
			int alt13=5;
			switch ( input.LA(1) ) {
			case ID:
				{
				alt13=1;
				}
				break;
			case INT:
				{
				alt13=2;
				}
				break;
			case FLOAT:
				{
				alt13=3;
				}
				break;
			case 10:
				{
				alt13=4;
				}
				break;
			case 12:
				{
				alt13=5;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 13, 0, input);
				throw nvae;
			}
			switch (alt13) {
				case 1 :
					// Corrective.g3:118:9: ID
					{
					root_0 = (CommonTree)adaptor.nil();


					ID45=(CommonToken)match(input,ID,FOLLOW_ID_in_atom543); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					ID45_tree = (CommonTree)adaptor.create(ID45);
					adaptor.addChild(root_0, ID45_tree);
					}

					}
					break;
				case 2 :
					// Corrective.g3:119:9: INT
					{
					root_0 = (CommonTree)adaptor.nil();


					INT46=(CommonToken)match(input,INT,FOLLOW_INT_in_atom553); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					INT46_tree = (CommonTree)adaptor.create(INT46);
					adaptor.addChild(root_0, INT46_tree);
					}

					}
					break;
				case 3 :
					// Corrective.g3:120:9: FLOAT
					{
					root_0 = (CommonTree)adaptor.nil();


					FLOAT47=(CommonToken)match(input,FLOAT,FOLLOW_FLOAT_in_atom563); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					FLOAT47_tree = (CommonTree)adaptor.create(FLOAT47);
					adaptor.addChild(root_0, FLOAT47_tree);
					}

					}
					break;
				case 4 :
					// Corrective.g3:121:9: '(' expr ')'
					{
					root_0 = (CommonTree)adaptor.nil();


					char_literal48=(CommonToken)match(input,10,FOLLOW_10_in_atom573); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal48_tree = (CommonTree)adaptor.create(char_literal48);
					adaptor.addChild(root_0, char_literal48_tree);
					}

					pushFollow(FOLLOW_expr_in_atom575);
					expr49=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expr49.getTree());

					char_literal50=(CommonToken)match(input,11,FOLLOW_11_in_atom577); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal50_tree = (CommonTree)adaptor.create(char_literal50);
					adaptor.addChild(root_0, char_literal50_tree);
					}

					}
					break;
				case 5 :
					// Corrective.g3:122:4: '*'
					{
					char_literal51=(CommonToken)match(input,12,FOLLOW_12_in_atom583); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_12.add(char_literal51);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 122:8: -> ID[\"x\"]
					{
						adaptor.addChild(root_0, (CommonTree)adaptor.create(ID, "x"));
					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "atom"

	// $ANTLR start synpred12_Corrective
	public final void synpred12_Corrective_fragment() throws RecognitionException {
		// Corrective.g3:80:49: ( ( '*' | '/' ) multiplicativeExpression )
		// Corrective.g3:80:49: ( '*' | '/' ) multiplicativeExpression
		{
		if ( input.LA(1)==12||input.LA(1)==16 ) {
			input.consume();
			state.errorRecovery=false;
			state.failed=false;
		}
		else {
			if (state.backtracking>0) {state.failed=true; return;}
			MismatchedSetException mse = new MismatchedSetException(null,input);
			throw mse;
		}
		pushFollow(FOLLOW_multiplicativeExpression_in_synpred12_Corrective301);
		multiplicativeExpression();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred12_Corrective

	// $ANTLR start synpred13_Corrective
	public final void synpred13_Corrective_fragment() throws RecognitionException {
		// Corrective.g3:80:7: ( preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )* )
		// Corrective.g3:80:7: preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )*
		{
		pushFollow(FOLLOW_preceedingMultExpression_in_synpred13_Corrective288);
		preceedingMultExpression();
		state._fsp--;
		if (state.failed) return;

		pushFollow(FOLLOW_superExpression_in_synpred13_Corrective290);
		superExpression();
		state._fsp--;
		if (state.failed) return;

		// Corrective.g3:80:48: ( ( '*' | '/' ) multiplicativeExpression )*
		loop15:
		while (true) {
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==12||LA15_0==16) ) {
				alt15=1;
			}

			switch (alt15) {
			case 1 :
				// Corrective.g3:80:49: ( '*' | '/' ) multiplicativeExpression
				{
				if ( input.LA(1)==12||input.LA(1)==16 ) {
					input.consume();
					state.errorRecovery=false;
					state.failed=false;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return;}
					MismatchedSetException mse = new MismatchedSetException(null,input);
					throw mse;
				}
				pushFollow(FOLLOW_multiplicativeExpression_in_synpred13_Corrective301);
				multiplicativeExpression();
				state._fsp--;
				if (state.failed) return;

				}
				break;

			default :
				break loop15;
			}
		}

		}

	}
	// $ANTLR end synpred13_Corrective

	// $ANTLR start synpred15_Corrective
	public final void synpred15_Corrective_fragment() throws RecognitionException {
		// Corrective.g3:81:21: ( ( '*' | '/' ) multiplicativeExpression )
		// Corrective.g3:81:21: ( '*' | '/' ) multiplicativeExpression
		{
		if ( input.LA(1)==12||input.LA(1)==16 ) {
			input.consume();
			state.errorRecovery=false;
			state.failed=false;
		}
		else {
			if (state.backtracking>0) {state.failed=true; return;}
			MismatchedSetException mse = new MismatchedSetException(null,input);
			throw mse;
		}
		pushFollow(FOLLOW_multiplicativeExpression_in_synpred15_Corrective321);
		multiplicativeExpression();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred15_Corrective

	// $ANTLR start synpred16_Corrective
	public final void synpred16_Corrective_fragment() throws RecognitionException {
		// Corrective.g3:81:4: ( unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* )
		// Corrective.g3:81:4: unaryExpression ( ( '*' | '/' ) multiplicativeExpression )*
		{
		pushFollow(FOLLOW_unaryExpression_in_synpred16_Corrective310);
		unaryExpression();
		state._fsp--;
		if (state.failed) return;

		// Corrective.g3:81:20: ( ( '*' | '/' ) multiplicativeExpression )*
		loop16:
		while (true) {
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==12||LA16_0==16) ) {
				alt16=1;
			}

			switch (alt16) {
			case 1 :
				// Corrective.g3:81:21: ( '*' | '/' ) multiplicativeExpression
				{
				if ( input.LA(1)==12||input.LA(1)==16 ) {
					input.consume();
					state.errorRecovery=false;
					state.failed=false;
				}
				else {
					if (state.backtracking>0) {state.failed=true; return;}
					MismatchedSetException mse = new MismatchedSetException(null,input);
					throw mse;
				}
				pushFollow(FOLLOW_multiplicativeExpression_in_synpred16_Corrective321);
				multiplicativeExpression();
				state._fsp--;
				if (state.failed) return;

				}
				break;

			default :
				break loop16;
			}
		}

		}

	}
	// $ANTLR end synpred16_Corrective

	// $ANTLR start synpred18_Corrective
	public final void synpred18_Corrective_fragment() throws RecognitionException {
		// Corrective.g3:82:24: ( ( '*' | '/' ) multiplicativeExpression )
		// Corrective.g3:82:24: ( '*' | '/' ) multiplicativeExpression
		{
		if ( input.LA(1)==12||input.LA(1)==16 ) {
			input.consume();
			state.errorRecovery=false;
			state.failed=false;
		}
		else {
			if (state.backtracking>0) {state.failed=true; return;}
			MismatchedSetException mse = new MismatchedSetException(null,input);
			throw mse;
		}
		pushFollow(FOLLOW_multiplicativeExpression_in_synpred18_Corrective343);
		multiplicativeExpression();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred18_Corrective

	// $ANTLR start synpred20_Corrective
	public final void synpred20_Corrective_fragment() throws RecognitionException {
		// Corrective.g3:102:4: ( ( '*' '*' postfixExpression ) )
		// Corrective.g3:102:4: ( '*' '*' postfixExpression )
		{
		// Corrective.g3:102:4: ( '*' '*' postfixExpression )
		// Corrective.g3:102:5: '*' '*' postfixExpression
		{
		match(input,12,FOLLOW_12_in_synpred20_Corrective441); if (state.failed) return;

		match(input,12,FOLLOW_12_in_synpred20_Corrective443); if (state.failed) return;

		pushFollow(FOLLOW_postfixExpression_in_synpred20_Corrective445);
		postfixExpression();
		state._fsp--;
		if (state.failed) return;

		}

		}

	}
	// $ANTLR end synpred20_Corrective

	// Delegated rules

	public final boolean synpred13_Corrective() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred13_Corrective_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred18_Corrective() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred18_Corrective_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred20_Corrective() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred20_Corrective_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred12_Corrective() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred12_Corrective_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred15_Corrective() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred15_Corrective_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred16_Corrective() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred16_Corrective_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}



	public static final BitSet FOLLOW_expr_in_expressionList151 = new BitSet(new long[]{0x0000000000004002L});
	public static final BitSet FOLLOW_14_in_expressionList154 = new BitSet(new long[]{0x00000000007E9470L});
	public static final BitSet FOLLOW_expr_in_expressionList156 = new BitSet(new long[]{0x0000000000004002L});
	public static final BitSet FOLLOW_expr_in_expression189 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_EOF_in_expression191 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_relationalExpression_in_expr209 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_relation_in_relationalExpression226 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_10_in_relationalExpression228 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_additiveExpression_in_relationalExpression230 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_relationalExpression232 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_additiveExpression_in_relationalExpression234 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_relationalExpression236 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_additiveExpression_in_relationalExpression242 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression258 = new BitSet(new long[]{0x000000000000A002L});
	public static final BitSet FOLLOW_set_in_additiveExpression261 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression269 = new BitSet(new long[]{0x000000000000A002L});
	public static final BitSet FOLLOW_preceedingMultExpression_in_multiplicativeExpression288 = new BitSet(new long[]{0x0000000000001470L});
	public static final BitSet FOLLOW_superExpression_in_multiplicativeExpression290 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_multiplicativeExpression293 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_multiplicativeExpression301 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression310 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_multiplicativeExpression313 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_multiplicativeExpression321 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression332 = new BitSet(new long[]{0x0000000000011000L});
	public static final BitSet FOLLOW_set_in_multiplicativeExpression335 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_multiplicativeExpression343 = new BitSet(new long[]{0x0000000000011000L});
	public static final BitSet FOLLOW_trailingMultExpression_in_multiplicativeExpression347 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_preceedingMultExpression362 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_trailingMultExpression380 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_15_in_unaryExpression403 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_unaryExpression_in_unaryExpression405 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_superExpression_in_unaryExpression416 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_postfixExpression_in_superExpression431 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_12_in_superExpression441 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_superExpression443 = new BitSet(new long[]{0x0000000000001470L});
	public static final BitSet FOLLOW_postfixExpression_in_superExpression445 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_subscriptExpression_in_postfixExpression468 = new BitSet(new long[]{0x0000000000000402L});
	public static final BitSet FOLLOW_10_in_postfixExpression493 = new BitSet(new long[]{0x00000000007E9C70L});
	public static final BitSet FOLLOW_expressionList_in_postfixExpression495 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_postfixExpression497 = new BitSet(new long[]{0x0000000000000402L});
	public static final BitSet FOLLOW_atom_in_subscriptExpression519 = new BitSet(new long[]{0x0000000000800002L});
	public static final BitSet FOLLOW_23_in_subscriptExpression522 = new BitSet(new long[]{0x0000000000001470L});
	public static final BitSet FOLLOW_atom_in_subscriptExpression524 = new BitSet(new long[]{0x0000000000800002L});
	public static final BitSet FOLLOW_ID_in_atom543 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_atom553 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FLOAT_in_atom563 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_10_in_atom573 = new BitSet(new long[]{0x00000000007E9470L});
	public static final BitSet FOLLOW_expr_in_atom575 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_atom577 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_atom583 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_synpred12_Corrective293 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred12_Corrective301 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_preceedingMultExpression_in_synpred13_Corrective288 = new BitSet(new long[]{0x0000000000001470L});
	public static final BitSet FOLLOW_superExpression_in_synpred13_Corrective290 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_synpred13_Corrective293 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred13_Corrective301 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_synpred15_Corrective313 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred15_Corrective321 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryExpression_in_synpred16_Corrective310 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_synpred16_Corrective313 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred16_Corrective321 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_synpred18_Corrective335 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred18_Corrective343 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_synpred20_Corrective441 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_synpred20_Corrective443 = new BitSet(new long[]{0x0000000000001470L});
	public static final BitSet FOLLOW_postfixExpression_in_synpred20_Corrective445 = new BitSet(new long[]{0x0000000000000002L});
}
