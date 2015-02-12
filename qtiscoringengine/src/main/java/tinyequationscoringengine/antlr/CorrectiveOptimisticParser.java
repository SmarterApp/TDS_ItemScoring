/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *     
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
// $ANTLR 3.5.2 CorrectiveOptimistic.g3 2015-01-02 14:12:09

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
public class CorrectiveOptimisticParser extends Parser {
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


	public CorrectiveOptimisticParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public CorrectiveOptimisticParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return CorrectiveOptimisticParser.tokenNames; }
	@Override public String getGrammarFileName() { return "CorrectiveOptimistic.g3"; }


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
	// CorrectiveOptimistic.g3:46:1: relation : ( 'Eq' | 'Le' | 'Lt' | 'Ge' | 'Gt' | 'Ne' );
	public final CorrectiveOptimisticParser.relation_return relation() throws RecognitionException {
		CorrectiveOptimisticParser.relation_return retval = new CorrectiveOptimisticParser.relation_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken set1=null;

		CommonTree set1_tree=null;

		try {
			// CorrectiveOptimistic.g3:47:2: ( 'Eq' | 'Le' | 'Lt' | 'Ge' | 'Gt' | 'Ne' )
			// CorrectiveOptimistic.g3:
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
	// CorrectiveOptimistic.g3:55:1: expressionList : ( expr ( ',' expr )* |);
	public final CorrectiveOptimisticParser.expressionList_return expressionList() throws RecognitionException {
		CorrectiveOptimisticParser.expressionList_return retval = new CorrectiveOptimisticParser.expressionList_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal3=null;
		ParserRuleReturnScope expr2 =null;
		ParserRuleReturnScope expr4 =null;

		CommonTree char_literal3_tree=null;

		try {
			// CorrectiveOptimistic.g3:56:5: ( expr ( ',' expr )* |)
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
					// CorrectiveOptimistic.g3:56:9: expr ( ',' expr )*
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_expr_in_expressionList151);
					expr2=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expr2.getTree());

					// CorrectiveOptimistic.g3:56:14: ( ',' expr )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( (LA1_0==14) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// CorrectiveOptimistic.g3:56:15: ',' expr
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
					// CorrectiveOptimistic.g3:58:5: 
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
	// CorrectiveOptimistic.g3:61:1: public expression : expr EOF -> expr ;
	public final CorrectiveOptimisticParser.expression_return expression() throws RecognitionException {
		CorrectiveOptimisticParser.expression_return retval = new CorrectiveOptimisticParser.expression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken EOF6=null;
		ParserRuleReturnScope expr5 =null;

		CommonTree EOF6_tree=null;
		RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// CorrectiveOptimistic.g3:62:5: ( expr EOF -> expr )
			// CorrectiveOptimistic.g3:62:9: expr EOF
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
			// 62:18: -> expr
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
	// CorrectiveOptimistic.g3:65:1: expr : relationalExpression ;
	public final CorrectiveOptimisticParser.expr_return expr() throws RecognitionException {
		CorrectiveOptimisticParser.expr_return retval = new CorrectiveOptimisticParser.expr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope relationalExpression7 =null;


		try {
			// CorrectiveOptimistic.g3:65:5: ( relationalExpression )
			// CorrectiveOptimistic.g3:65:9: relationalExpression
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
	// CorrectiveOptimistic.g3:68:1: relationalExpression : ( relation '(' additiveExpression ',' additiveExpression ')' | additiveExpression );
	public final CorrectiveOptimisticParser.relationalExpression_return relationalExpression() throws RecognitionException {
		CorrectiveOptimisticParser.relationalExpression_return retval = new CorrectiveOptimisticParser.relationalExpression_return();
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
			// CorrectiveOptimistic.g3:69:5: ( relation '(' additiveExpression ',' additiveExpression ')' | additiveExpression )
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
					// CorrectiveOptimistic.g3:69:7: relation '(' additiveExpression ',' additiveExpression ')'
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
					// CorrectiveOptimistic.g3:70:4: additiveExpression
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
	// CorrectiveOptimistic.g3:73:1: additiveExpression : multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* ;
	public final CorrectiveOptimisticParser.additiveExpression_return additiveExpression() throws RecognitionException {
		CorrectiveOptimisticParser.additiveExpression_return retval = new CorrectiveOptimisticParser.additiveExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken set16=null;
		ParserRuleReturnScope multiplicativeExpression15 =null;
		ParserRuleReturnScope multiplicativeExpression17 =null;

		CommonTree set16_tree=null;

		try {
			// CorrectiveOptimistic.g3:74:5: ( multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* )
			// CorrectiveOptimistic.g3:74:9: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression258);
			multiplicativeExpression15=multiplicativeExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression15.getTree());

			// CorrectiveOptimistic.g3:74:34: ( ( '+' | '-' ) multiplicativeExpression )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==13||LA4_0==15) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// CorrectiveOptimistic.g3:74:35: ( '+' | '-' ) multiplicativeExpression
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
	// CorrectiveOptimistic.g3:77:1: multiplicativeExpression : ( preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )* | unaryExpression ( interimMultExpression multiplicativeExpression )* | unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* | unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* trailingMultExpression );
	public final CorrectiveOptimisticParser.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
		CorrectiveOptimisticParser.multiplicativeExpression_return retval = new CorrectiveOptimisticParser.multiplicativeExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken set20=null;
		CommonToken set26=null;
		CommonToken set29=null;
		ParserRuleReturnScope preceedingMultExpression18 =null;
		ParserRuleReturnScope superExpression19 =null;
		ParserRuleReturnScope multiplicativeExpression21 =null;
		ParserRuleReturnScope unaryExpression22 =null;
		ParserRuleReturnScope interimMultExpression23 =null;
		ParserRuleReturnScope multiplicativeExpression24 =null;
		ParserRuleReturnScope unaryExpression25 =null;
		ParserRuleReturnScope multiplicativeExpression27 =null;
		ParserRuleReturnScope unaryExpression28 =null;
		ParserRuleReturnScope multiplicativeExpression30 =null;
		ParserRuleReturnScope trailingMultExpression31 =null;

		CommonTree set20_tree=null;
		CommonTree set26_tree=null;
		CommonTree set29_tree=null;

		try {
			// CorrectiveOptimistic.g3:78:5: ( preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )* | unaryExpression ( interimMultExpression multiplicativeExpression )* | unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* | unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* trailingMultExpression )
			int alt9=4;
			switch ( input.LA(1) ) {
			case 12:
				{
				int LA9_1 = input.LA(2);
				if ( (synpred13_CorrectiveOptimistic()) ) {
					alt9=1;
				}
				else if ( (synpred15_CorrectiveOptimistic()) ) {
					alt9=2;
				}
				else if ( (synpred18_CorrectiveOptimistic()) ) {
					alt9=3;
				}
				else if ( (true) ) {
					alt9=4;
				}

				}
				break;
			case 15:
				{
				int LA9_2 = input.LA(2);
				if ( (synpred15_CorrectiveOptimistic()) ) {
					alt9=2;
				}
				else if ( (synpred18_CorrectiveOptimistic()) ) {
					alt9=3;
				}
				else if ( (true) ) {
					alt9=4;
				}

				}
				break;
			case ID:
				{
				int LA9_3 = input.LA(2);
				if ( (synpred15_CorrectiveOptimistic()) ) {
					alt9=2;
				}
				else if ( (synpred18_CorrectiveOptimistic()) ) {
					alt9=3;
				}
				else if ( (true) ) {
					alt9=4;
				}

				}
				break;
			case INT:
				{
				int LA9_4 = input.LA(2);
				if ( (synpred15_CorrectiveOptimistic()) ) {
					alt9=2;
				}
				else if ( (synpred18_CorrectiveOptimistic()) ) {
					alt9=3;
				}
				else if ( (true) ) {
					alt9=4;
				}

				}
				break;
			case FLOAT:
				{
				int LA9_5 = input.LA(2);
				if ( (synpred15_CorrectiveOptimistic()) ) {
					alt9=2;
				}
				else if ( (synpred18_CorrectiveOptimistic()) ) {
					alt9=3;
				}
				else if ( (true) ) {
					alt9=4;
				}

				}
				break;
			case 10:
				{
				int LA9_6 = input.LA(2);
				if ( (synpred15_CorrectiveOptimistic()) ) {
					alt9=2;
				}
				else if ( (synpred18_CorrectiveOptimistic()) ) {
					alt9=3;
				}
				else if ( (true) ) {
					alt9=4;
				}

				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}
			switch (alt9) {
				case 1 :
					// CorrectiveOptimistic.g3:78:7: preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )*
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

					// CorrectiveOptimistic.g3:78:48: ( ( '*' | '/' ) multiplicativeExpression )*
					loop5:
					while (true) {
						int alt5=2;
						int LA5_0 = input.LA(1);
						if ( (LA5_0==12) ) {
							int LA5_2 = input.LA(2);
							if ( (synpred12_CorrectiveOptimistic()) ) {
								alt5=1;
							}

						}
						else if ( (LA5_0==16) ) {
							int LA5_3 = input.LA(2);
							if ( (synpred12_CorrectiveOptimistic()) ) {
								alt5=1;
							}

						}

						switch (alt5) {
						case 1 :
							// CorrectiveOptimistic.g3:78:49: ( '*' | '/' ) multiplicativeExpression
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
					// CorrectiveOptimistic.g3:79:4: unaryExpression ( interimMultExpression multiplicativeExpression )*
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression310);
					unaryExpression22=unaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression22.getTree());

					// CorrectiveOptimistic.g3:79:20: ( interimMultExpression multiplicativeExpression )*
					loop6:
					while (true) {
						int alt6=2;
						int LA6_0 = input.LA(1);
						if ( (LA6_0==12) ) {
							int LA6_2 = input.LA(2);
							if ( (synpred14_CorrectiveOptimistic()) ) {
								alt6=1;
							}

						}
						else if ( (LA6_0==16) ) {
							int LA6_3 = input.LA(2);
							if ( (synpred14_CorrectiveOptimistic()) ) {
								alt6=1;
							}

						}

						switch (alt6) {
						case 1 :
							// CorrectiveOptimistic.g3:79:21: interimMultExpression multiplicativeExpression
							{
							pushFollow(FOLLOW_interimMultExpression_in_multiplicativeExpression313);
							interimMultExpression23=interimMultExpression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) adaptor.addChild(root_0, interimMultExpression23.getTree());

							pushFollow(FOLLOW_multiplicativeExpression_in_multiplicativeExpression315);
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
					// CorrectiveOptimistic.g3:80:7: unaryExpression ( ( '*' | '/' ) multiplicativeExpression )*
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression326);
					unaryExpression25=unaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression25.getTree());

					// CorrectiveOptimistic.g3:80:23: ( ( '*' | '/' ) multiplicativeExpression )*
					loop7:
					while (true) {
						int alt7=2;
						int LA7_0 = input.LA(1);
						if ( (LA7_0==12) ) {
							int LA7_2 = input.LA(2);
							if ( (synpred17_CorrectiveOptimistic()) ) {
								alt7=1;
							}

						}
						else if ( (LA7_0==16) ) {
							int LA7_3 = input.LA(2);
							if ( (synpred17_CorrectiveOptimistic()) ) {
								alt7=1;
							}

						}

						switch (alt7) {
						case 1 :
							// CorrectiveOptimistic.g3:80:24: ( '*' | '/' ) multiplicativeExpression
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
							pushFollow(FOLLOW_multiplicativeExpression_in_multiplicativeExpression337);
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

					}
					break;
				case 4 :
					// CorrectiveOptimistic.g3:81:7: unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* trailingMultExpression
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression348);
					unaryExpression28=unaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression28.getTree());

					// CorrectiveOptimistic.g3:81:23: ( ( '*' | '/' ) multiplicativeExpression )*
					loop8:
					while (true) {
						int alt8=2;
						int LA8_0 = input.LA(1);
						if ( (LA8_0==12) ) {
							int LA8_1 = input.LA(2);
							if ( (synpred20_CorrectiveOptimistic()) ) {
								alt8=1;
							}

						}
						else if ( (LA8_0==16) ) {
							alt8=1;
						}

						switch (alt8) {
						case 1 :
							// CorrectiveOptimistic.g3:81:24: ( '*' | '/' ) multiplicativeExpression
							{
							set29=(CommonToken)input.LT(1);
							if ( input.LA(1)==12||input.LA(1)==16 ) {
								input.consume();
								if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set29));
								state.errorRecovery=false;
								state.failed=false;
							}
							else {
								if (state.backtracking>0) {state.failed=true; return retval;}
								MismatchedSetException mse = new MismatchedSetException(null,input);
								throw mse;
							}
							pushFollow(FOLLOW_multiplicativeExpression_in_multiplicativeExpression359);
							multiplicativeExpression30=multiplicativeExpression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression30.getTree());

							}
							break;

						default :
							break loop8;
						}
					}

					pushFollow(FOLLOW_trailingMultExpression_in_multiplicativeExpression363);
					trailingMultExpression31=trailingMultExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, trailingMultExpression31.getTree());

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
	// CorrectiveOptimistic.g3:84:1: preceedingMultExpression : '*' -> ID[\"x\"] '*' ;
	public final CorrectiveOptimisticParser.preceedingMultExpression_return preceedingMultExpression() throws RecognitionException {
		CorrectiveOptimisticParser.preceedingMultExpression_return retval = new CorrectiveOptimisticParser.preceedingMultExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal32=null;

		CommonTree char_literal32_tree=null;
		RewriteRuleTokenStream stream_12=new RewriteRuleTokenStream(adaptor,"token 12");

		try {
			// CorrectiveOptimistic.g3:85:2: ( '*' -> ID[\"x\"] '*' )
			// CorrectiveOptimistic.g3:85:4: '*'
			{
			char_literal32=(CommonToken)match(input,12,FOLLOW_12_in_preceedingMultExpression378); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_12.add(char_literal32);

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
			// 85:8: -> ID[\"x\"] '*'
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
	// CorrectiveOptimistic.g3:88:1: trailingMultExpression : '*' -> '*' ID[\"x\"] ;
	public final CorrectiveOptimisticParser.trailingMultExpression_return trailingMultExpression() throws RecognitionException {
		CorrectiveOptimisticParser.trailingMultExpression_return retval = new CorrectiveOptimisticParser.trailingMultExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal33=null;

		CommonTree char_literal33_tree=null;
		RewriteRuleTokenStream stream_12=new RewriteRuleTokenStream(adaptor,"token 12");

		try {
			// CorrectiveOptimistic.g3:89:2: ( '*' -> '*' ID[\"x\"] )
			// CorrectiveOptimistic.g3:89:4: '*'
			{
			char_literal33=(CommonToken)match(input,12,FOLLOW_12_in_trailingMultExpression396); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_12.add(char_literal33);

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
			// 89:8: -> '*' ID[\"x\"]
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


	public static class interimMultExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "interimMultExpression"
	// CorrectiveOptimistic.g3:92:1: interimMultExpression : ( '*' '-' -> '*' ID[\"x\"] '-' | '*' -> '*' | '/' -> '/' );
	public final CorrectiveOptimisticParser.interimMultExpression_return interimMultExpression() throws RecognitionException {
		CorrectiveOptimisticParser.interimMultExpression_return retval = new CorrectiveOptimisticParser.interimMultExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal34=null;
		CommonToken char_literal35=null;
		CommonToken char_literal36=null;
		CommonToken char_literal37=null;

		CommonTree char_literal34_tree=null;
		CommonTree char_literal35_tree=null;
		CommonTree char_literal36_tree=null;
		CommonTree char_literal37_tree=null;
		RewriteRuleTokenStream stream_15=new RewriteRuleTokenStream(adaptor,"token 15");
		RewriteRuleTokenStream stream_16=new RewriteRuleTokenStream(adaptor,"token 16");
		RewriteRuleTokenStream stream_12=new RewriteRuleTokenStream(adaptor,"token 12");

		try {
			// CorrectiveOptimistic.g3:93:8: ( '*' '-' -> '*' ID[\"x\"] '-' | '*' -> '*' | '/' -> '/' )
			int alt10=3;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==12) ) {
				int LA10_1 = input.LA(2);
				if ( (synpred21_CorrectiveOptimistic()) ) {
					alt10=1;
				}
				else if ( (synpred22_CorrectiveOptimistic()) ) {
					alt10=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 10, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}
			else if ( (LA10_0==16) ) {
				alt10=3;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}

			switch (alt10) {
				case 1 :
					// CorrectiveOptimistic.g3:93:10: '*' '-'
					{
					char_literal34=(CommonToken)match(input,12,FOLLOW_12_in_interimMultExpression420); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_12.add(char_literal34);

					char_literal35=(CommonToken)match(input,15,FOLLOW_15_in_interimMultExpression422); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_15.add(char_literal35);

					// AST REWRITE
					// elements: 12, 15
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 93:18: -> '*' ID[\"x\"] '-'
					{
						adaptor.addChild(root_0, stream_12.nextNode());
						adaptor.addChild(root_0, (CommonTree)adaptor.create(ID, "x"));
						adaptor.addChild(root_0, stream_15.nextNode());
					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// CorrectiveOptimistic.g3:94:10: '*'
					{
					char_literal36=(CommonToken)match(input,12,FOLLOW_12_in_interimMultExpression442); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_12.add(char_literal36);

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
					// 94:15: -> '*'
					{
						adaptor.addChild(root_0, stream_12.nextNode());
					}


					retval.tree = root_0;
					}

					}
					break;
				case 3 :
					// CorrectiveOptimistic.g3:95:10: '/'
					{
					char_literal37=(CommonToken)match(input,16,FOLLOW_16_in_interimMultExpression459); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_16.add(char_literal37);

					// AST REWRITE
					// elements: 16
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 95:14: -> '/'
					{
						adaptor.addChild(root_0, stream_16.nextNode());
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
	// $ANTLR end "interimMultExpression"


	public static class unaryExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "unaryExpression"
	// CorrectiveOptimistic.g3:98:1: unaryExpression : ( '-' unaryExpression | superExpression );
	public final CorrectiveOptimisticParser.unaryExpression_return unaryExpression() throws RecognitionException {
		CorrectiveOptimisticParser.unaryExpression_return retval = new CorrectiveOptimisticParser.unaryExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal38=null;
		ParserRuleReturnScope unaryExpression39 =null;
		ParserRuleReturnScope superExpression40 =null;

		CommonTree char_literal38_tree=null;

		try {
			// CorrectiveOptimistic.g3:99:5: ( '-' unaryExpression | superExpression )
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==15) ) {
				alt11=1;
			}
			else if ( ((LA11_0 >= FLOAT && LA11_0 <= INT)||LA11_0==10||LA11_0==12) ) {
				alt11=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}

			switch (alt11) {
				case 1 :
					// CorrectiveOptimistic.g3:99:9: '-' unaryExpression
					{
					root_0 = (CommonTree)adaptor.nil();


					char_literal38=(CommonToken)match(input,15,FOLLOW_15_in_unaryExpression485); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal38_tree = (CommonTree)adaptor.create(char_literal38);
					adaptor.addChild(root_0, char_literal38_tree);
					}

					pushFollow(FOLLOW_unaryExpression_in_unaryExpression487);
					unaryExpression39=unaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression39.getTree());

					}
					break;
				case 2 :
					// CorrectiveOptimistic.g3:100:9: superExpression
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_superExpression_in_unaryExpression498);
					superExpression40=superExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, superExpression40.getTree());

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
	// CorrectiveOptimistic.g3:104:1: superExpression : postfixExpression ( ( '*' '*' postfixExpression ) )* ;
	public final CorrectiveOptimisticParser.superExpression_return superExpression() throws RecognitionException {
		CorrectiveOptimisticParser.superExpression_return retval = new CorrectiveOptimisticParser.superExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal42=null;
		CommonToken char_literal43=null;
		ParserRuleReturnScope postfixExpression41 =null;
		ParserRuleReturnScope postfixExpression44 =null;

		CommonTree char_literal42_tree=null;
		CommonTree char_literal43_tree=null;

		try {
			// CorrectiveOptimistic.g3:105:2: ( postfixExpression ( ( '*' '*' postfixExpression ) )* )
			// CorrectiveOptimistic.g3:105:4: postfixExpression ( ( '*' '*' postfixExpression ) )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_postfixExpression_in_superExpression513);
			postfixExpression41=postfixExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, postfixExpression41.getTree());

			// CorrectiveOptimistic.g3:106:3: ( ( '*' '*' postfixExpression ) )*
			loop12:
			while (true) {
				int alt12=2;
				int LA12_0 = input.LA(1);
				if ( (LA12_0==12) ) {
					int LA12_1 = input.LA(2);
					if ( (synpred24_CorrectiveOptimistic()) ) {
						alt12=1;
					}

				}

				switch (alt12) {
				case 1 :
					// CorrectiveOptimistic.g3:107:4: ( '*' '*' postfixExpression )
					{
					// CorrectiveOptimistic.g3:107:4: ( '*' '*' postfixExpression )
					// CorrectiveOptimistic.g3:107:5: '*' '*' postfixExpression
					{
					char_literal42=(CommonToken)match(input,12,FOLLOW_12_in_superExpression523); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal42_tree = (CommonTree)adaptor.create(char_literal42);
					adaptor.addChild(root_0, char_literal42_tree);
					}

					char_literal43=(CommonToken)match(input,12,FOLLOW_12_in_superExpression525); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal43_tree = (CommonTree)adaptor.create(char_literal43);
					adaptor.addChild(root_0, char_literal43_tree);
					}

					pushFollow(FOLLOW_postfixExpression_in_superExpression527);
					postfixExpression44=postfixExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, postfixExpression44.getTree());

					}

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
	// $ANTLR end "superExpression"


	public static class postfixExpression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "postfixExpression"
	// CorrectiveOptimistic.g3:111:1: postfixExpression : subscriptExpression ( '(' expressionList ')' )* ;
	public final CorrectiveOptimisticParser.postfixExpression_return postfixExpression() throws RecognitionException {
		CorrectiveOptimisticParser.postfixExpression_return retval = new CorrectiveOptimisticParser.postfixExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal46=null;
		CommonToken char_literal48=null;
		ParserRuleReturnScope subscriptExpression45 =null;
		ParserRuleReturnScope expressionList47 =null;

		CommonTree char_literal46_tree=null;
		CommonTree char_literal48_tree=null;

		try {
			// CorrectiveOptimistic.g3:112:5: ( subscriptExpression ( '(' expressionList ')' )* )
			// CorrectiveOptimistic.g3:112:9: subscriptExpression ( '(' expressionList ')' )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_subscriptExpression_in_postfixExpression550);
			subscriptExpression45=subscriptExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, subscriptExpression45.getTree());

			// CorrectiveOptimistic.g3:113:9: ( '(' expressionList ')' )*
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( (LA13_0==10) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// CorrectiveOptimistic.g3:114:13: '(' expressionList ')'
					{
					char_literal46=(CommonToken)match(input,10,FOLLOW_10_in_postfixExpression575); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal46_tree = (CommonTree)adaptor.create(char_literal46);
					adaptor.addChild(root_0, char_literal46_tree);
					}

					pushFollow(FOLLOW_expressionList_in_postfixExpression577);
					expressionList47=expressionList();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionList47.getTree());

					char_literal48=(CommonToken)match(input,11,FOLLOW_11_in_postfixExpression579); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal48_tree = (CommonTree)adaptor.create(char_literal48);
					adaptor.addChild(root_0, char_literal48_tree);
					}

					}
					break;

				default :
					break loop13;
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
	// CorrectiveOptimistic.g3:118:1: subscriptExpression : atom ( '_' atom )* ;
	public final CorrectiveOptimisticParser.subscriptExpression_return subscriptExpression() throws RecognitionException {
		CorrectiveOptimisticParser.subscriptExpression_return retval = new CorrectiveOptimisticParser.subscriptExpression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal50=null;
		ParserRuleReturnScope atom49 =null;
		ParserRuleReturnScope atom51 =null;

		CommonTree char_literal50_tree=null;

		try {
			// CorrectiveOptimistic.g3:119:2: ( atom ( '_' atom )* )
			// CorrectiveOptimistic.g3:119:4: atom ( '_' atom )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_atom_in_subscriptExpression601);
			atom49=atom();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, atom49.getTree());

			// CorrectiveOptimistic.g3:119:9: ( '_' atom )*
			loop14:
			while (true) {
				int alt14=2;
				int LA14_0 = input.LA(1);
				if ( (LA14_0==23) ) {
					alt14=1;
				}

				switch (alt14) {
				case 1 :
					// CorrectiveOptimistic.g3:119:10: '_' atom
					{
					char_literal50=(CommonToken)match(input,23,FOLLOW_23_in_subscriptExpression604); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal50_tree = (CommonTree)adaptor.create(char_literal50);
					adaptor.addChild(root_0, char_literal50_tree);
					}

					pushFollow(FOLLOW_atom_in_subscriptExpression606);
					atom51=atom();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, atom51.getTree());

					}
					break;

				default :
					break loop14;
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
	// CorrectiveOptimistic.g3:122:1: atom : ( ID | INT | FLOAT | '(' expr ')' | '*' -> ID[\"x\"] );
	public final CorrectiveOptimisticParser.atom_return atom() throws RecognitionException {
		CorrectiveOptimisticParser.atom_return retval = new CorrectiveOptimisticParser.atom_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken ID52=null;
		CommonToken INT53=null;
		CommonToken FLOAT54=null;
		CommonToken char_literal55=null;
		CommonToken char_literal57=null;
		CommonToken char_literal58=null;
		ParserRuleReturnScope expr56 =null;

		CommonTree ID52_tree=null;
		CommonTree INT53_tree=null;
		CommonTree FLOAT54_tree=null;
		CommonTree char_literal55_tree=null;
		CommonTree char_literal57_tree=null;
		CommonTree char_literal58_tree=null;
		RewriteRuleTokenStream stream_12=new RewriteRuleTokenStream(adaptor,"token 12");

		try {
			// CorrectiveOptimistic.g3:123:5: ( ID | INT | FLOAT | '(' expr ')' | '*' -> ID[\"x\"] )
			int alt15=5;
			switch ( input.LA(1) ) {
			case ID:
				{
				alt15=1;
				}
				break;
			case INT:
				{
				alt15=2;
				}
				break;
			case FLOAT:
				{
				alt15=3;
				}
				break;
			case 10:
				{
				alt15=4;
				}
				break;
			case 12:
				{
				alt15=5;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 15, 0, input);
				throw nvae;
			}
			switch (alt15) {
				case 1 :
					// CorrectiveOptimistic.g3:123:9: ID
					{
					root_0 = (CommonTree)adaptor.nil();


					ID52=(CommonToken)match(input,ID,FOLLOW_ID_in_atom625); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					ID52_tree = (CommonTree)adaptor.create(ID52);
					adaptor.addChild(root_0, ID52_tree);
					}

					}
					break;
				case 2 :
					// CorrectiveOptimistic.g3:124:9: INT
					{
					root_0 = (CommonTree)adaptor.nil();


					INT53=(CommonToken)match(input,INT,FOLLOW_INT_in_atom635); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					INT53_tree = (CommonTree)adaptor.create(INT53);
					adaptor.addChild(root_0, INT53_tree);
					}

					}
					break;
				case 3 :
					// CorrectiveOptimistic.g3:125:9: FLOAT
					{
					root_0 = (CommonTree)adaptor.nil();


					FLOAT54=(CommonToken)match(input,FLOAT,FOLLOW_FLOAT_in_atom645); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					FLOAT54_tree = (CommonTree)adaptor.create(FLOAT54);
					adaptor.addChild(root_0, FLOAT54_tree);
					}

					}
					break;
				case 4 :
					// CorrectiveOptimistic.g3:126:9: '(' expr ')'
					{
					root_0 = (CommonTree)adaptor.nil();


					char_literal55=(CommonToken)match(input,10,FOLLOW_10_in_atom655); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal55_tree = (CommonTree)adaptor.create(char_literal55);
					adaptor.addChild(root_0, char_literal55_tree);
					}

					pushFollow(FOLLOW_expr_in_atom657);
					expr56=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, expr56.getTree());

					char_literal57=(CommonToken)match(input,11,FOLLOW_11_in_atom659); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					char_literal57_tree = (CommonTree)adaptor.create(char_literal57);
					adaptor.addChild(root_0, char_literal57_tree);
					}

					}
					break;
				case 5 :
					// CorrectiveOptimistic.g3:127:4: '*'
					{
					char_literal58=(CommonToken)match(input,12,FOLLOW_12_in_atom665); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_12.add(char_literal58);

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
					// 127:8: -> ID[\"x\"]
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

	// $ANTLR start synpred12_CorrectiveOptimistic
	public final void synpred12_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:78:49: ( ( '*' | '/' ) multiplicativeExpression )
		// CorrectiveOptimistic.g3:78:49: ( '*' | '/' ) multiplicativeExpression
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
		pushFollow(FOLLOW_multiplicativeExpression_in_synpred12_CorrectiveOptimistic301);
		multiplicativeExpression();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred12_CorrectiveOptimistic

	// $ANTLR start synpred13_CorrectiveOptimistic
	public final void synpred13_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:78:7: ( preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )* )
		// CorrectiveOptimistic.g3:78:7: preceedingMultExpression superExpression ( ( '*' | '/' ) multiplicativeExpression )*
		{
		pushFollow(FOLLOW_preceedingMultExpression_in_synpred13_CorrectiveOptimistic288);
		preceedingMultExpression();
		state._fsp--;
		if (state.failed) return;

		pushFollow(FOLLOW_superExpression_in_synpred13_CorrectiveOptimistic290);
		superExpression();
		state._fsp--;
		if (state.failed) return;

		// CorrectiveOptimistic.g3:78:48: ( ( '*' | '/' ) multiplicativeExpression )*
		loop17:
		while (true) {
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==12||LA17_0==16) ) {
				alt17=1;
			}

			switch (alt17) {
			case 1 :
				// CorrectiveOptimistic.g3:78:49: ( '*' | '/' ) multiplicativeExpression
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
				pushFollow(FOLLOW_multiplicativeExpression_in_synpred13_CorrectiveOptimistic301);
				multiplicativeExpression();
				state._fsp--;
				if (state.failed) return;

				}
				break;

			default :
				break loop17;
			}
		}

		}

	}
	// $ANTLR end synpred13_CorrectiveOptimistic

	// $ANTLR start synpred14_CorrectiveOptimistic
	public final void synpred14_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:79:21: ( interimMultExpression multiplicativeExpression )
		// CorrectiveOptimistic.g3:79:21: interimMultExpression multiplicativeExpression
		{
		pushFollow(FOLLOW_interimMultExpression_in_synpred14_CorrectiveOptimistic313);
		interimMultExpression();
		state._fsp--;
		if (state.failed) return;

		pushFollow(FOLLOW_multiplicativeExpression_in_synpred14_CorrectiveOptimistic315);
		multiplicativeExpression();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred14_CorrectiveOptimistic

	// $ANTLR start synpred15_CorrectiveOptimistic
	public final void synpred15_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:79:4: ( unaryExpression ( interimMultExpression multiplicativeExpression )* )
		// CorrectiveOptimistic.g3:79:4: unaryExpression ( interimMultExpression multiplicativeExpression )*
		{
		pushFollow(FOLLOW_unaryExpression_in_synpred15_CorrectiveOptimistic310);
		unaryExpression();
		state._fsp--;
		if (state.failed) return;

		// CorrectiveOptimistic.g3:79:20: ( interimMultExpression multiplicativeExpression )*
		loop18:
		while (true) {
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==12||LA18_0==16) ) {
				alt18=1;
			}

			switch (alt18) {
			case 1 :
				// CorrectiveOptimistic.g3:79:21: interimMultExpression multiplicativeExpression
				{
				pushFollow(FOLLOW_interimMultExpression_in_synpred15_CorrectiveOptimistic313);
				interimMultExpression();
				state._fsp--;
				if (state.failed) return;

				pushFollow(FOLLOW_multiplicativeExpression_in_synpred15_CorrectiveOptimistic315);
				multiplicativeExpression();
				state._fsp--;
				if (state.failed) return;

				}
				break;

			default :
				break loop18;
			}
		}

		}

	}
	// $ANTLR end synpred15_CorrectiveOptimistic

	// $ANTLR start synpred17_CorrectiveOptimistic
	public final void synpred17_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:80:24: ( ( '*' | '/' ) multiplicativeExpression )
		// CorrectiveOptimistic.g3:80:24: ( '*' | '/' ) multiplicativeExpression
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
		pushFollow(FOLLOW_multiplicativeExpression_in_synpred17_CorrectiveOptimistic337);
		multiplicativeExpression();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred17_CorrectiveOptimistic

	// $ANTLR start synpred18_CorrectiveOptimistic
	public final void synpred18_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:80:7: ( unaryExpression ( ( '*' | '/' ) multiplicativeExpression )* )
		// CorrectiveOptimistic.g3:80:7: unaryExpression ( ( '*' | '/' ) multiplicativeExpression )*
		{
		pushFollow(FOLLOW_unaryExpression_in_synpred18_CorrectiveOptimistic326);
		unaryExpression();
		state._fsp--;
		if (state.failed) return;

		// CorrectiveOptimistic.g3:80:23: ( ( '*' | '/' ) multiplicativeExpression )*
		loop19:
		while (true) {
			int alt19=2;
			int LA19_0 = input.LA(1);
			if ( (LA19_0==12||LA19_0==16) ) {
				alt19=1;
			}

			switch (alt19) {
			case 1 :
				// CorrectiveOptimistic.g3:80:24: ( '*' | '/' ) multiplicativeExpression
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
				pushFollow(FOLLOW_multiplicativeExpression_in_synpred18_CorrectiveOptimistic337);
				multiplicativeExpression();
				state._fsp--;
				if (state.failed) return;

				}
				break;

			default :
				break loop19;
			}
		}

		}

	}
	// $ANTLR end synpred18_CorrectiveOptimistic

	// $ANTLR start synpred20_CorrectiveOptimistic
	public final void synpred20_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:81:24: ( ( '*' | '/' ) multiplicativeExpression )
		// CorrectiveOptimistic.g3:81:24: ( '*' | '/' ) multiplicativeExpression
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
		pushFollow(FOLLOW_multiplicativeExpression_in_synpred20_CorrectiveOptimistic359);
		multiplicativeExpression();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred20_CorrectiveOptimistic

	// $ANTLR start synpred21_CorrectiveOptimistic
	public final void synpred21_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:93:10: ( '*' '-' )
		// CorrectiveOptimistic.g3:93:10: '*' '-'
		{
		match(input,12,FOLLOW_12_in_synpred21_CorrectiveOptimistic420); if (state.failed) return;

		match(input,15,FOLLOW_15_in_synpred21_CorrectiveOptimistic422); if (state.failed) return;

		}

	}
	// $ANTLR end synpred21_CorrectiveOptimistic

	// $ANTLR start synpred22_CorrectiveOptimistic
	public final void synpred22_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:94:10: ( '*' )
		// CorrectiveOptimistic.g3:94:10: '*'
		{
		match(input,12,FOLLOW_12_in_synpred22_CorrectiveOptimistic442); if (state.failed) return;

		}

	}
	// $ANTLR end synpred22_CorrectiveOptimistic

	// $ANTLR start synpred24_CorrectiveOptimistic
	public final void synpred24_CorrectiveOptimistic_fragment() throws RecognitionException {
		// CorrectiveOptimistic.g3:107:4: ( ( '*' '*' postfixExpression ) )
		// CorrectiveOptimistic.g3:107:4: ( '*' '*' postfixExpression )
		{
		// CorrectiveOptimistic.g3:107:4: ( '*' '*' postfixExpression )
		// CorrectiveOptimistic.g3:107:5: '*' '*' postfixExpression
		{
		match(input,12,FOLLOW_12_in_synpred24_CorrectiveOptimistic523); if (state.failed) return;

		match(input,12,FOLLOW_12_in_synpred24_CorrectiveOptimistic525); if (state.failed) return;

		pushFollow(FOLLOW_postfixExpression_in_synpred24_CorrectiveOptimistic527);
		postfixExpression();
		state._fsp--;
		if (state.failed) return;

		}

		}

	}
	// $ANTLR end synpred24_CorrectiveOptimistic

	// Delegated rules

	public final boolean synpred13_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred13_CorrectiveOptimistic_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred18_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred18_CorrectiveOptimistic_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred21_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred21_CorrectiveOptimistic_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred20_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred20_CorrectiveOptimistic_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred17_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred17_CorrectiveOptimistic_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred22_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred22_CorrectiveOptimistic_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred15_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred15_CorrectiveOptimistic_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred24_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred24_CorrectiveOptimistic_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred14_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred14_CorrectiveOptimistic_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred12_CorrectiveOptimistic() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred12_CorrectiveOptimistic_fragment(); // can never throw exception
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
	public static final BitSet FOLLOW_interimMultExpression_in_multiplicativeExpression313 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_multiplicativeExpression315 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression326 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_multiplicativeExpression329 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_multiplicativeExpression337 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression348 = new BitSet(new long[]{0x0000000000011000L});
	public static final BitSet FOLLOW_set_in_multiplicativeExpression351 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_multiplicativeExpression359 = new BitSet(new long[]{0x0000000000011000L});
	public static final BitSet FOLLOW_trailingMultExpression_in_multiplicativeExpression363 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_preceedingMultExpression378 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_trailingMultExpression396 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_interimMultExpression420 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_interimMultExpression422 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_interimMultExpression442 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_16_in_interimMultExpression459 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_15_in_unaryExpression485 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_unaryExpression_in_unaryExpression487 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_superExpression_in_unaryExpression498 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_postfixExpression_in_superExpression513 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_12_in_superExpression523 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_superExpression525 = new BitSet(new long[]{0x0000000000001470L});
	public static final BitSet FOLLOW_postfixExpression_in_superExpression527 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_subscriptExpression_in_postfixExpression550 = new BitSet(new long[]{0x0000000000000402L});
	public static final BitSet FOLLOW_10_in_postfixExpression575 = new BitSet(new long[]{0x00000000007E9C70L});
	public static final BitSet FOLLOW_expressionList_in_postfixExpression577 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_postfixExpression579 = new BitSet(new long[]{0x0000000000000402L});
	public static final BitSet FOLLOW_atom_in_subscriptExpression601 = new BitSet(new long[]{0x0000000000800002L});
	public static final BitSet FOLLOW_23_in_subscriptExpression604 = new BitSet(new long[]{0x0000000000001470L});
	public static final BitSet FOLLOW_atom_in_subscriptExpression606 = new BitSet(new long[]{0x0000000000800002L});
	public static final BitSet FOLLOW_ID_in_atom625 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_atom635 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FLOAT_in_atom645 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_10_in_atom655 = new BitSet(new long[]{0x00000000007E9470L});
	public static final BitSet FOLLOW_expr_in_atom657 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_11_in_atom659 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_atom665 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_synpred12_CorrectiveOptimistic293 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred12_CorrectiveOptimistic301 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_preceedingMultExpression_in_synpred13_CorrectiveOptimistic288 = new BitSet(new long[]{0x0000000000001470L});
	public static final BitSet FOLLOW_superExpression_in_synpred13_CorrectiveOptimistic290 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_synpred13_CorrectiveOptimistic293 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred13_CorrectiveOptimistic301 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_interimMultExpression_in_synpred14_CorrectiveOptimistic313 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred14_CorrectiveOptimistic315 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryExpression_in_synpred15_CorrectiveOptimistic310 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_interimMultExpression_in_synpred15_CorrectiveOptimistic313 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred15_CorrectiveOptimistic315 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_synpred17_CorrectiveOptimistic329 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred17_CorrectiveOptimistic337 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryExpression_in_synpred18_CorrectiveOptimistic326 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_synpred18_CorrectiveOptimistic329 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred18_CorrectiveOptimistic337 = new BitSet(new long[]{0x0000000000011002L});
	public static final BitSet FOLLOW_set_in_synpred20_CorrectiveOptimistic351 = new BitSet(new long[]{0x0000000000009470L});
	public static final BitSet FOLLOW_multiplicativeExpression_in_synpred20_CorrectiveOptimistic359 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_synpred21_CorrectiveOptimistic420 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_synpred21_CorrectiveOptimistic422 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_synpred22_CorrectiveOptimistic442 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_synpred24_CorrectiveOptimistic523 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_synpred24_CorrectiveOptimistic525 = new BitSet(new long[]{0x0000000000001470L});
	public static final BitSet FOLLOW_postfixExpression_in_synpred24_CorrectiveOptimistic527 = new BitSet(new long[]{0x0000000000000002L});
}
