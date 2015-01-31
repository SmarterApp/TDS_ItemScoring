// $ANTLR 3.5.2 Sympy.g3 2015-01-02 14:17:11

package tinyequationscoringengine.antlr;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class SympyParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ASSIGN", "CALL", "DIV", "ELIST", 
		"EXPR", "FLOAT", "ID", "INT", "MULT", "NEWLINE", "PLUS", "POW", "REL", 
		"SUB", "UNARY_MINUS", "WS", "'('", "')'", "','", "'-'", "'/'", "'Eq'", 
		"'Ge'", "'Gt'", "'Le'", "'Lt'", "'Ne'", "'_'"
	};
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
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public SympyParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public SympyParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return SympyParser.tokenNames; }
	@Override public String getGrammarFileName() { return "Sympy.g3"; }


	private int _depth = 0;


	public static class rel_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "rel"
	// Sympy.g3:36:1: rel : ( 'Eq' | 'Le' | 'Lt' | 'Ge' | 'Gt' | 'Ne' );
	public final SympyParser.rel_return rel() throws RecognitionException {
		SympyParser.rel_return retval = new SympyParser.rel_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken set1=null;

		CommonTree set1_tree=null;

		try {
			// Sympy.g3:37:2: ( 'Eq' | 'Le' | 'Lt' | 'Ge' | 'Gt' | 'Ne' )
			// Sympy.g3:
			{
			root_0 = (CommonTree)adaptor.nil();


			set1=(CommonToken)input.LT(1);
			if ( (input.LA(1) >= 25 && input.LA(1) <= 30) ) {
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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "rel"


	public static class exprList_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "exprList"
	// Sympy.g3:45:1: exprList : ( expr ( ',' expr )* -> ^( ELIST ( expr )+ ) | -> ELIST );
	public final SympyParser.exprList_return exprList() throws RecognitionException {
		SympyParser.exprList_return retval = new SympyParser.exprList_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal3=null;
		ParserRuleReturnScope expr2 =null;
		ParserRuleReturnScope expr4 =null;

		CommonTree char_literal3_tree=null;
		RewriteRuleTokenStream stream_22=new RewriteRuleTokenStream(adaptor,"token 22");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// Sympy.g3:46:5: ( expr ( ',' expr )* -> ^( ELIST ( expr )+ ) | -> ELIST )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( ((LA2_0 >= FLOAT && LA2_0 <= INT)||LA2_0==20||LA2_0==23||(LA2_0 >= 25 && LA2_0 <= 30)) ) {
				alt2=1;
			}
			else if ( (LA2_0==21) ) {
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
					// Sympy.g3:46:9: expr ( ',' expr )*
					{
					pushFollow(FOLLOW_expr_in_exprList186);
					expr2=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_expr.add(expr2.getTree());
					// Sympy.g3:46:14: ( ',' expr )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( (LA1_0==22) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// Sympy.g3:46:15: ',' expr
							{
							char_literal3=(CommonToken)match(input,22,FOLLOW_22_in_exprList189); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_22.add(char_literal3);

							pushFollow(FOLLOW_expr_in_exprList191);
							expr4=expr();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_expr.add(expr4.getTree());
							}
							break;

						default :
							break loop1;
						}
					}

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
					// 46:26: -> ^( ELIST ( expr )+ )
					{
						// Sympy.g3:46:29: ^( ELIST ( expr )+ )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ELIST, "ELIST"), root_1);
						if ( !(stream_expr.hasNext()) ) {
							throw new RewriteEarlyExitException();
						}
						while ( stream_expr.hasNext() ) {
							adaptor.addChild(root_1, stream_expr.nextTree());
						}
						stream_expr.reset();

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// Sympy.g3:47:9: 
					{
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
					// 47:9: -> ELIST
					{
						adaptor.addChild(root_0, (CommonTree)adaptor.create(ELIST, "ELIST"));
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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "exprList"


	public static class expression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "expression"
	// Sympy.g3:51:1: public expression : expr WS -> ^( EXPR expr ) ;
	public final SympyParser.expression_return expression() throws RecognitionException {
		SympyParser.expression_return retval = new SympyParser.expression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken WS6=null;
		ParserRuleReturnScope expr5 =null;

		CommonTree WS6_tree=null;
		RewriteRuleTokenStream stream_WS=new RewriteRuleTokenStream(adaptor,"token WS");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// Sympy.g3:52:5: ( expr WS -> ^( EXPR expr ) )
			// Sympy.g3:52:9: expr WS
			{
			pushFollow(FOLLOW_expr_in_expression235);
			expr5=expr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_expr.add(expr5.getTree());
			WS6=(CommonToken)match(input,WS,FOLLOW_WS_in_expression237); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_WS.add(WS6);

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
			// 52:17: -> ^( EXPR expr )
			{
				// Sympy.g3:52:20: ^( EXPR expr )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(EXPR, "EXPR"), root_1);
				adaptor.addChild(root_1, stream_expr.nextTree());
				adaptor.addChild(root_0, root_1);
				}

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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
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
	// Sympy.g3:55:1: expr : relExpr ;
	public final SympyParser.expr_return expr() throws RecognitionException {
		SympyParser.expr_return retval = new SympyParser.expr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope relExpr7 =null;


		try {
			// Sympy.g3:55:5: ( relExpr )
			// Sympy.g3:55:9: relExpr
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_relExpr_in_expr259);
			relExpr7=relExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, relExpr7.getTree());

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expr"


	public static class relExpr_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "relExpr"
	// Sympy.g3:57:1: relExpr : ( rel '(' addExpr ',' addExpr ')' -> ^( rel ( addExpr )+ ) | addExpr );
	public final SympyParser.relExpr_return relExpr() throws RecognitionException {
		SympyParser.relExpr_return retval = new SympyParser.relExpr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal9=null;
		CommonToken char_literal11=null;
		CommonToken char_literal13=null;
		ParserRuleReturnScope rel8 =null;
		ParserRuleReturnScope addExpr10 =null;
		ParserRuleReturnScope addExpr12 =null;
		ParserRuleReturnScope addExpr14 =null;

		CommonTree char_literal9_tree=null;
		CommonTree char_literal11_tree=null;
		CommonTree char_literal13_tree=null;
		RewriteRuleTokenStream stream_21=new RewriteRuleTokenStream(adaptor,"token 21");
		RewriteRuleTokenStream stream_20=new RewriteRuleTokenStream(adaptor,"token 20");
		RewriteRuleTokenStream stream_22=new RewriteRuleTokenStream(adaptor,"token 22");
		RewriteRuleSubtreeStream stream_addExpr=new RewriteRuleSubtreeStream(adaptor,"rule addExpr");
		RewriteRuleSubtreeStream stream_rel=new RewriteRuleSubtreeStream(adaptor,"rule rel");

		try {
			// Sympy.g3:57:8: ( rel '(' addExpr ',' addExpr ')' -> ^( rel ( addExpr )+ ) | addExpr )
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( ((LA3_0 >= 25 && LA3_0 <= 30)) ) {
				alt3=1;
			}
			else if ( ((LA3_0 >= FLOAT && LA3_0 <= INT)||LA3_0==20||LA3_0==23) ) {
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
					// Sympy.g3:57:12: rel '(' addExpr ',' addExpr ')'
					{
					pushFollow(FOLLOW_rel_in_relExpr272);
					rel8=rel();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_rel.add(rel8.getTree());
					char_literal9=(CommonToken)match(input,20,FOLLOW_20_in_relExpr274); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_20.add(char_literal9);

					pushFollow(FOLLOW_addExpr_in_relExpr276);
					addExpr10=addExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_addExpr.add(addExpr10.getTree());
					char_literal11=(CommonToken)match(input,22,FOLLOW_22_in_relExpr278); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_22.add(char_literal11);

					pushFollow(FOLLOW_addExpr_in_relExpr280);
					addExpr12=addExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_addExpr.add(addExpr12.getTree());
					char_literal13=(CommonToken)match(input,21,FOLLOW_21_in_relExpr282); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_21.add(char_literal13);

					// AST REWRITE
					// elements: rel, addExpr
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 57:44: -> ^( rel ( addExpr )+ )
					{
						// Sympy.g3:57:47: ^( rel ( addExpr )+ )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot(stream_rel.nextNode(), root_1);
						if ( !(stream_addExpr.hasNext()) ) {
							throw new RewriteEarlyExitException();
						}
						while ( stream_addExpr.hasNext() ) {
							adaptor.addChild(root_1, stream_addExpr.nextTree());
						}
						stream_addExpr.reset();

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// Sympy.g3:58:4: addExpr
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_addExpr_in_relExpr296);
					addExpr14=addExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, addExpr14.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "relExpr"


	public static class addExpr_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "addExpr"
	// Sympy.g3:61:1: addExpr : mulExpr ( ( '+' ^| '-' ^) mulExpr )* ;
	public final SympyParser.addExpr_return addExpr() throws RecognitionException {
		SympyParser.addExpr_return retval = new SympyParser.addExpr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal16=null;
		CommonToken char_literal17=null;
		ParserRuleReturnScope mulExpr15 =null;
		ParserRuleReturnScope mulExpr18 =null;

		CommonTree char_literal16_tree=null;
		CommonTree char_literal17_tree=null;

		try {
			// Sympy.g3:62:5: ( mulExpr ( ( '+' ^| '-' ^) mulExpr )* )
			// Sympy.g3:62:9: mulExpr ( ( '+' ^| '-' ^) mulExpr )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_mulExpr_in_addExpr312);
			mulExpr15=mulExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, mulExpr15.getTree());

			// Sympy.g3:62:17: ( ( '+' ^| '-' ^) mulExpr )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==PLUS||LA5_0==23) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// Sympy.g3:62:18: ( '+' ^| '-' ^) mulExpr
					{
					// Sympy.g3:62:18: ( '+' ^| '-' ^)
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0==PLUS) ) {
						alt4=1;
					}
					else if ( (LA4_0==23) ) {
						alt4=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 4, 0, input);
						throw nvae;
					}

					switch (alt4) {
						case 1 :
							// Sympy.g3:62:19: '+' ^
							{
							char_literal16=(CommonToken)match(input,PLUS,FOLLOW_PLUS_in_addExpr316); if (state.failed) return retval;
							if ( state.backtracking==0 ) {
							char_literal16_tree = (CommonTree)adaptor.create(char_literal16);
							root_0 = (CommonTree)adaptor.becomeRoot(char_literal16_tree, root_0);
							}

							}
							break;
						case 2 :
							// Sympy.g3:62:26: '-' ^
							{
							char_literal17=(CommonToken)match(input,23,FOLLOW_23_in_addExpr321); if (state.failed) return retval;
							if ( state.backtracking==0 ) {
							char_literal17_tree = (CommonTree)adaptor.create(char_literal17);
							root_0 = (CommonTree)adaptor.becomeRoot(char_literal17_tree, root_0);
							}

							}
							break;

					}

					pushFollow(FOLLOW_mulExpr_in_addExpr325);
					mulExpr18=mulExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, mulExpr18.getTree());

					}
					break;

				default :
					break loop5;
				}
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "addExpr"


	public static class mulExpr_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "mulExpr"
	// Sympy.g3:65:1: mulExpr : unrExpr ( ( '*' ^| '/' ^) unrExpr )* ;
	public final SympyParser.mulExpr_return mulExpr() throws RecognitionException {
		SympyParser.mulExpr_return retval = new SympyParser.mulExpr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal20=null;
		CommonToken char_literal21=null;
		ParserRuleReturnScope unrExpr19 =null;
		ParserRuleReturnScope unrExpr22 =null;

		CommonTree char_literal20_tree=null;
		CommonTree char_literal21_tree=null;

		try {
			// Sympy.g3:66:5: ( unrExpr ( ( '*' ^| '/' ^) unrExpr )* )
			// Sympy.g3:66:9: unrExpr ( ( '*' ^| '/' ^) unrExpr )*
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_unrExpr_in_mulExpr346);
			unrExpr19=unrExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, unrExpr19.getTree());

			// Sympy.g3:66:17: ( ( '*' ^| '/' ^) unrExpr )*
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==MULT||LA7_0==24) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// Sympy.g3:66:18: ( '*' ^| '/' ^) unrExpr
					{
					// Sympy.g3:66:18: ( '*' ^| '/' ^)
					int alt6=2;
					int LA6_0 = input.LA(1);
					if ( (LA6_0==MULT) ) {
						alt6=1;
					}
					else if ( (LA6_0==24) ) {
						alt6=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 6, 0, input);
						throw nvae;
					}

					switch (alt6) {
						case 1 :
							// Sympy.g3:66:19: '*' ^
							{
							char_literal20=(CommonToken)match(input,MULT,FOLLOW_MULT_in_mulExpr350); if (state.failed) return retval;
							if ( state.backtracking==0 ) {
							char_literal20_tree = (CommonTree)adaptor.create(char_literal20);
							root_0 = (CommonTree)adaptor.becomeRoot(char_literal20_tree, root_0);
							}

							}
							break;
						case 2 :
							// Sympy.g3:66:26: '/' ^
							{
							char_literal21=(CommonToken)match(input,24,FOLLOW_24_in_mulExpr355); if (state.failed) return retval;
							if ( state.backtracking==0 ) {
							char_literal21_tree = (CommonTree)adaptor.create(char_literal21);
							root_0 = (CommonTree)adaptor.becomeRoot(char_literal21_tree, root_0);
							}

							}
							break;

					}

					pushFollow(FOLLOW_unrExpr_in_mulExpr359);
					unrExpr22=unrExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, unrExpr22.getTree());

					}
					break;

				default :
					break loop7;
				}
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "mulExpr"


	public static class unrExpr_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "unrExpr"
	// Sympy.g3:69:1: unrExpr : (op= '-' unrExpr -> ^( UNARY_MINUS[$op] unrExpr ) | supExpr );
	public final SympyParser.unrExpr_return unrExpr() throws RecognitionException {
		SympyParser.unrExpr_return retval = new SympyParser.unrExpr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken op=null;
		ParserRuleReturnScope unrExpr23 =null;
		ParserRuleReturnScope supExpr24 =null;

		CommonTree op_tree=null;
		RewriteRuleTokenStream stream_23=new RewriteRuleTokenStream(adaptor,"token 23");
		RewriteRuleSubtreeStream stream_unrExpr=new RewriteRuleSubtreeStream(adaptor,"rule unrExpr");

		try {
			// Sympy.g3:70:5: (op= '-' unrExpr -> ^( UNARY_MINUS[$op] unrExpr ) | supExpr )
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==23) ) {
				alt8=1;
			}
			else if ( ((LA8_0 >= FLOAT && LA8_0 <= INT)||LA8_0==20) ) {
				alt8=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 8, 0, input);
				throw nvae;
			}

			switch (alt8) {
				case 1 :
					// Sympy.g3:70:9: op= '-' unrExpr
					{
					op=(CommonToken)match(input,23,FOLLOW_23_in_unrExpr382); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_23.add(op);

					pushFollow(FOLLOW_unrExpr_in_unrExpr384);
					unrExpr23=unrExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_unrExpr.add(unrExpr23.getTree());
					// AST REWRITE
					// elements: unrExpr
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 70:24: -> ^( UNARY_MINUS[$op] unrExpr )
					{
						// Sympy.g3:70:27: ^( UNARY_MINUS[$op] unrExpr )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(UNARY_MINUS, op), root_1);
						adaptor.addChild(root_1, stream_unrExpr.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// Sympy.g3:71:9: supExpr
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_supExpr_in_unrExpr404);
					supExpr24=supExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, supExpr24.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "unrExpr"


	public static class supExpr_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "supExpr"
	// Sympy.g3:74:1: supExpr : ( pstExpr -> pstExpr ) ( ( '*' '*' pstExpr -> ^( POW[\"**\"] $supExpr pstExpr ) ) )* ;
	public final SympyParser.supExpr_return supExpr() throws RecognitionException {
		SympyParser.supExpr_return retval = new SympyParser.supExpr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal26=null;
		CommonToken char_literal27=null;
		ParserRuleReturnScope pstExpr25 =null;
		ParserRuleReturnScope pstExpr28 =null;

		CommonTree char_literal26_tree=null;
		CommonTree char_literal27_tree=null;
		RewriteRuleTokenStream stream_MULT=new RewriteRuleTokenStream(adaptor,"token MULT");
		RewriteRuleSubtreeStream stream_pstExpr=new RewriteRuleSubtreeStream(adaptor,"rule pstExpr");

		try {
			// Sympy.g3:75:2: ( ( pstExpr -> pstExpr ) ( ( '*' '*' pstExpr -> ^( POW[\"**\"] $supExpr pstExpr ) ) )* )
			// Sympy.g3:75:4: ( pstExpr -> pstExpr ) ( ( '*' '*' pstExpr -> ^( POW[\"**\"] $supExpr pstExpr ) ) )*
			{
			// Sympy.g3:75:4: ( pstExpr -> pstExpr )
			// Sympy.g3:75:5: pstExpr
			{
			pushFollow(FOLLOW_pstExpr_in_supExpr419);
			pstExpr25=pstExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_pstExpr.add(pstExpr25.getTree());
			// AST REWRITE
			// elements: pstExpr
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 75:12: -> pstExpr
			{
				adaptor.addChild(root_0, stream_pstExpr.nextTree());
			}


			retval.tree = root_0;
			}

			}

			// Sympy.g3:76:3: ( ( '*' '*' pstExpr -> ^( POW[\"**\"] $supExpr pstExpr ) ) )*
			loop9:
			while (true) {
				int alt9=2;
				int LA9_0 = input.LA(1);
				if ( (LA9_0==MULT) ) {
					int LA9_1 = input.LA(2);
					if ( (LA9_1==MULT) ) {
						alt9=1;
					}

				}

				switch (alt9) {
				case 1 :
					// Sympy.g3:77:4: ( '*' '*' pstExpr -> ^( POW[\"**\"] $supExpr pstExpr ) )
					{
					// Sympy.g3:77:4: ( '*' '*' pstExpr -> ^( POW[\"**\"] $supExpr pstExpr ) )
					// Sympy.g3:77:5: '*' '*' pstExpr
					{
					char_literal26=(CommonToken)match(input,MULT,FOLLOW_MULT_in_supExpr433); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_MULT.add(char_literal26);

					char_literal27=(CommonToken)match(input,MULT,FOLLOW_MULT_in_supExpr435); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_MULT.add(char_literal27);

					pushFollow(FOLLOW_pstExpr_in_supExpr438);
					pstExpr28=pstExpr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_pstExpr.add(pstExpr28.getTree());
					// AST REWRITE
					// elements: supExpr, pstExpr
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 77:22: -> ^( POW[\"**\"] $supExpr pstExpr )
					{
						// Sympy.g3:77:25: ^( POW[\"**\"] $supExpr pstExpr )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(POW, "**"), root_1);
						adaptor.addChild(root_1, stream_retval.nextTree());
						adaptor.addChild(root_1, stream_pstExpr.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}

					}
					break;

				default :
					break loop9;
				}
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "supExpr"


	public static class pstExpr_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "pstExpr"
	// Sympy.g3:81:1: pstExpr : ( subExpr -> subExpr ) ( ( '(' exprList ')' -> ^( CALL $pstExpr exprList ) ) )* ;
	public final SympyParser.pstExpr_return pstExpr() throws RecognitionException {
		SympyParser.pstExpr_return retval = new SympyParser.pstExpr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal30=null;
		CommonToken char_literal32=null;
		ParserRuleReturnScope subExpr29 =null;
		ParserRuleReturnScope exprList31 =null;

		CommonTree char_literal30_tree=null;
		CommonTree char_literal32_tree=null;
		RewriteRuleTokenStream stream_21=new RewriteRuleTokenStream(adaptor,"token 21");
		RewriteRuleTokenStream stream_20=new RewriteRuleTokenStream(adaptor,"token 20");
		RewriteRuleSubtreeStream stream_subExpr=new RewriteRuleSubtreeStream(adaptor,"rule subExpr");
		RewriteRuleSubtreeStream stream_exprList=new RewriteRuleSubtreeStream(adaptor,"rule exprList");

		try {
			// Sympy.g3:82:5: ( ( subExpr -> subExpr ) ( ( '(' exprList ')' -> ^( CALL $pstExpr exprList ) ) )* )
			// Sympy.g3:82:9: ( subExpr -> subExpr ) ( ( '(' exprList ')' -> ^( CALL $pstExpr exprList ) ) )*
			{
			// Sympy.g3:82:9: ( subExpr -> subExpr )
			// Sympy.g3:82:10: subExpr
			{
			pushFollow(FOLLOW_subExpr_in_pstExpr473);
			subExpr29=subExpr();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_subExpr.add(subExpr29.getTree());
			// AST REWRITE
			// elements: subExpr
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 82:17: -> subExpr
			{
				adaptor.addChild(root_0, stream_subExpr.nextTree());
			}


			retval.tree = root_0;
			}

			}

			// Sympy.g3:83:9: ( ( '(' exprList ')' -> ^( CALL $pstExpr exprList ) ) )*
			loop10:
			while (true) {
				int alt10=2;
				int LA10_0 = input.LA(1);
				if ( (LA10_0==20) ) {
					alt10=1;
				}

				switch (alt10) {
				case 1 :
					// Sympy.g3:84:13: ( '(' exprList ')' -> ^( CALL $pstExpr exprList ) )
					{
					// Sympy.g3:84:13: ( '(' exprList ')' -> ^( CALL $pstExpr exprList ) )
					// Sympy.g3:84:17: '(' exprList ')'
					{
					char_literal30=(CommonToken)match(input,20,FOLLOW_20_in_pstExpr504); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_20.add(char_literal30);

					pushFollow(FOLLOW_exprList_in_pstExpr506);
					exprList31=exprList();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_exprList.add(exprList31.getTree());
					char_literal32=(CommonToken)match(input,21,FOLLOW_21_in_pstExpr508); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_21.add(char_literal32);

					// AST REWRITE
					// elements: exprList, pstExpr
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 84:34: -> ^( CALL $pstExpr exprList )
					{
						// Sympy.g3:84:37: ^( CALL $pstExpr exprList )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(CALL, "CALL"), root_1);
						adaptor.addChild(root_1, stream_retval.nextTree());
						adaptor.addChild(root_1, stream_exprList.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "pstExpr"


	public static class subExpr_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "subExpr"
	// Sympy.g3:88:1: subExpr : ( atom -> atom ) ( ( '_' atom ) -> ^( SUB $subExpr atom ) )* ;
	public final SympyParser.subExpr_return subExpr() throws RecognitionException {
		SympyParser.subExpr_return retval = new SympyParser.subExpr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken char_literal34=null;
		ParserRuleReturnScope atom33 =null;
		ParserRuleReturnScope atom35 =null;

		CommonTree char_literal34_tree=null;
		RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
		RewriteRuleSubtreeStream stream_atom=new RewriteRuleSubtreeStream(adaptor,"rule atom");

		try {
			// Sympy.g3:89:2: ( ( atom -> atom ) ( ( '_' atom ) -> ^( SUB $subExpr atom ) )* )
			// Sympy.g3:89:4: ( atom -> atom ) ( ( '_' atom ) -> ^( SUB $subExpr atom ) )*
			{
			// Sympy.g3:89:4: ( atom -> atom )
			// Sympy.g3:89:5: atom
			{
			pushFollow(FOLLOW_atom_in_subExpr545);
			atom33=atom();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_atom.add(atom33.getTree());
			// AST REWRITE
			// elements: atom
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 89:9: -> atom
			{
				adaptor.addChild(root_0, stream_atom.nextTree());
			}


			retval.tree = root_0;
			}

			}

			// Sympy.g3:89:17: ( ( '_' atom ) -> ^( SUB $subExpr atom ) )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==31) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// Sympy.g3:89:18: ( '_' atom )
					{
					// Sympy.g3:89:18: ( '_' atom )
					// Sympy.g3:89:19: '_' atom
					{
					char_literal34=(CommonToken)match(input,31,FOLLOW_31_in_subExpr552); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_31.add(char_literal34);

					pushFollow(FOLLOW_atom_in_subExpr554);
					atom35=atom();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_atom.add(atom35.getTree());
					}

					// AST REWRITE
					// elements: subExpr, atom
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 89:29: -> ^( SUB $subExpr atom )
					{
						// Sympy.g3:89:32: ^( SUB $subExpr atom )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SUB, "SUB"), root_1);
						adaptor.addChild(root_1, stream_retval.nextTree());
						adaptor.addChild(root_1, stream_atom.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "subExpr"


	public static class atom_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "atom"
	// Sympy.g3:92:1: atom : ( ID | INT | FLOAT | '(' expr ')' -> expr );
	public final SympyParser.atom_return atom() throws RecognitionException {
		SympyParser.atom_return retval = new SympyParser.atom_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonToken ID36=null;
		CommonToken INT37=null;
		CommonToken FLOAT38=null;
		CommonToken char_literal39=null;
		CommonToken char_literal41=null;
		ParserRuleReturnScope expr40 =null;

		CommonTree ID36_tree=null;
		CommonTree INT37_tree=null;
		CommonTree FLOAT38_tree=null;
		CommonTree char_literal39_tree=null;
		CommonTree char_literal41_tree=null;
		RewriteRuleTokenStream stream_21=new RewriteRuleTokenStream(adaptor,"token 21");
		RewriteRuleTokenStream stream_20=new RewriteRuleTokenStream(adaptor,"token 20");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// Sympy.g3:93:5: ( ID | INT | FLOAT | '(' expr ')' -> expr )
			int alt12=4;
			switch ( input.LA(1) ) {
			case ID:
				{
				alt12=1;
				}
				break;
			case INT:
				{
				alt12=2;
				}
				break;
			case FLOAT:
				{
				alt12=3;
				}
				break;
			case 20:
				{
				alt12=4;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 12, 0, input);
				throw nvae;
			}
			switch (alt12) {
				case 1 :
					// Sympy.g3:93:9: ID
					{
					root_0 = (CommonTree)adaptor.nil();


					ID36=(CommonToken)match(input,ID,FOLLOW_ID_in_atom585); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					ID36_tree = (CommonTree)adaptor.create(ID36);
					adaptor.addChild(root_0, ID36_tree);
					}

					}
					break;
				case 2 :
					// Sympy.g3:94:9: INT
					{
					root_0 = (CommonTree)adaptor.nil();


					INT37=(CommonToken)match(input,INT,FOLLOW_INT_in_atom595); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					INT37_tree = (CommonTree)adaptor.create(INT37);
					adaptor.addChild(root_0, INT37_tree);
					}

					}
					break;
				case 3 :
					// Sympy.g3:95:9: FLOAT
					{
					root_0 = (CommonTree)adaptor.nil();


					FLOAT38=(CommonToken)match(input,FLOAT,FOLLOW_FLOAT_in_atom605); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					FLOAT38_tree = (CommonTree)adaptor.create(FLOAT38);
					adaptor.addChild(root_0, FLOAT38_tree);
					}

					}
					break;
				case 4 :
					// Sympy.g3:96:9: '(' expr ')'
					{
					char_literal39=(CommonToken)match(input,20,FOLLOW_20_in_atom615); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_20.add(char_literal39);

					pushFollow(FOLLOW_expr_in_atom617);
					expr40=expr();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_expr.add(expr40.getTree());
					char_literal41=(CommonToken)match(input,21,FOLLOW_21_in_atom619); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_21.add(char_literal41);

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
					// 96:22: -> expr
					{
						adaptor.addChild(root_0, stream_expr.nextTree());
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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "atom"

	// Delegated rules



	public static final BitSet FOLLOW_expr_in_exprList186 = new BitSet(new long[]{0x0000000000400002L});
	public static final BitSet FOLLOW_22_in_exprList189 = new BitSet(new long[]{0x000000007E900E00L});
	public static final BitSet FOLLOW_expr_in_exprList191 = new BitSet(new long[]{0x0000000000400002L});
	public static final BitSet FOLLOW_expr_in_expression235 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_WS_in_expression237 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_relExpr_in_expr259 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rel_in_relExpr272 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_20_in_relExpr274 = new BitSet(new long[]{0x0000000000900E00L});
	public static final BitSet FOLLOW_addExpr_in_relExpr276 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_relExpr278 = new BitSet(new long[]{0x0000000000900E00L});
	public static final BitSet FOLLOW_addExpr_in_relExpr280 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_relExpr282 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_addExpr_in_relExpr296 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_mulExpr_in_addExpr312 = new BitSet(new long[]{0x0000000000804002L});
	public static final BitSet FOLLOW_PLUS_in_addExpr316 = new BitSet(new long[]{0x0000000000900E00L});
	public static final BitSet FOLLOW_23_in_addExpr321 = new BitSet(new long[]{0x0000000000900E00L});
	public static final BitSet FOLLOW_mulExpr_in_addExpr325 = new BitSet(new long[]{0x0000000000804002L});
	public static final BitSet FOLLOW_unrExpr_in_mulExpr346 = new BitSet(new long[]{0x0000000001001002L});
	public static final BitSet FOLLOW_MULT_in_mulExpr350 = new BitSet(new long[]{0x0000000000900E00L});
	public static final BitSet FOLLOW_24_in_mulExpr355 = new BitSet(new long[]{0x0000000000900E00L});
	public static final BitSet FOLLOW_unrExpr_in_mulExpr359 = new BitSet(new long[]{0x0000000001001002L});
	public static final BitSet FOLLOW_23_in_unrExpr382 = new BitSet(new long[]{0x0000000000900E00L});
	public static final BitSet FOLLOW_unrExpr_in_unrExpr384 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_supExpr_in_unrExpr404 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pstExpr_in_supExpr419 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_MULT_in_supExpr433 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_MULT_in_supExpr435 = new BitSet(new long[]{0x0000000000100E00L});
	public static final BitSet FOLLOW_pstExpr_in_supExpr438 = new BitSet(new long[]{0x0000000000001002L});
	public static final BitSet FOLLOW_subExpr_in_pstExpr473 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_20_in_pstExpr504 = new BitSet(new long[]{0x000000007EB00E00L});
	public static final BitSet FOLLOW_exprList_in_pstExpr506 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_pstExpr508 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_atom_in_subExpr545 = new BitSet(new long[]{0x0000000080000002L});
	public static final BitSet FOLLOW_31_in_subExpr552 = new BitSet(new long[]{0x0000000000100E00L});
	public static final BitSet FOLLOW_atom_in_subExpr554 = new BitSet(new long[]{0x0000000080000002L});
	public static final BitSet FOLLOW_ID_in_atom585 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_atom595 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FLOAT_in_atom605 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_20_in_atom615 = new BitSet(new long[]{0x000000007E900E00L});
	public static final BitSet FOLLOW_expr_in_atom617 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_atom619 = new BitSet(new long[]{0x0000000000000002L});
}
