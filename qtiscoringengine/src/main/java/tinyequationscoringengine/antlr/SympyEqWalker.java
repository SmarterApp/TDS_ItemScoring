// $ANTLR 3.5.2 SympyEqWalker.g3 2015-01-02 14:18:24

package tinyequationscoringengine.antlr;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


@SuppressWarnings("all")
public class SympyEqWalker extends TreeRewriter {
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
	public TreeRewriter[] getDelegates() {
		return new TreeRewriter[] {};
	}

	// delegators


	public SympyEqWalker(TreeNodeStream input) {
		this(input, new RecognizerSharedState());
	}
	public SympyEqWalker(TreeNodeStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return SympyEqWalker.tokenNames; }
	@Override public String getGrammarFileName() { return "SympyEqWalker.g3"; }


	public static class bottomup_return extends TreeRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "bottomup"
	// SympyEqWalker.g3:14:1: bottomup : ( divToMult | minusToPlus | combinePluses | combineMults | changeGe );
	@Override
	public final SympyEqWalker.bottomup_return bottomup() throws RecognitionException {
		SympyEqWalker.bottomup_return retval = new SympyEqWalker.bottomup_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonTree _first_0 = null;
		CommonTree _last = null;


		TreeRuleReturnScope divToMult1 =null;
		TreeRuleReturnScope minusToPlus2 =null;
		TreeRuleReturnScope combinePluses3 =null;
		TreeRuleReturnScope combineMults4 =null;
		TreeRuleReturnScope changeGe5 =null;


		try {
			// SympyEqWalker.g3:15:5: ( divToMult | minusToPlus | combinePluses | combineMults | changeGe )
			int alt1=5;
			switch ( input.LA(1) ) {
			case 24:
				{
				alt1=1;
				}
				break;
			case 23:
				{
				alt1=2;
				}
				break;
			case PLUS:
				{
				alt1=3;
				}
				break;
			case MULT:
				{
				alt1=4;
				}
				break;
			case 26:
			case 27:
				{
				alt1=5;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// SympyEqWalker.g3:15:8: divToMult
					{
					_last = (CommonTree)input.LT(1);
					pushFollow(FOLLOW_divToMult_in_bottomup93);
					divToMult1=divToMult();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==1 ) 
					 
					if ( _first_0==null ) _first_0 = (CommonTree)divToMult1.getTree();

					if ( state.backtracking==1 ) {
					retval.tree = _first_0;
					if ( adaptor.getParent(retval.tree)!=null && adaptor.isNil( adaptor.getParent(retval.tree) ) )
						retval.tree = (CommonTree)adaptor.getParent(retval.tree);
					}

					}
					break;
				case 2 :
					// SympyEqWalker.g3:16:5: minusToPlus
					{
					_last = (CommonTree)input.LT(1);
					pushFollow(FOLLOW_minusToPlus_in_bottomup99);
					minusToPlus2=minusToPlus();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==1 ) 
					 
					if ( _first_0==null ) _first_0 = (CommonTree)minusToPlus2.getTree();

					if ( state.backtracking==1 ) {
					retval.tree = _first_0;
					if ( adaptor.getParent(retval.tree)!=null && adaptor.isNil( adaptor.getParent(retval.tree) ) )
						retval.tree = (CommonTree)adaptor.getParent(retval.tree);
					}

					}
					break;
				case 3 :
					// SympyEqWalker.g3:17:8: combinePluses
					{
					_last = (CommonTree)input.LT(1);
					pushFollow(FOLLOW_combinePluses_in_bottomup108);
					combinePluses3=combinePluses();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==1 ) 
					 
					if ( _first_0==null ) _first_0 = (CommonTree)combinePluses3.getTree();

					if ( state.backtracking==1 ) {
					retval.tree = _first_0;
					if ( adaptor.getParent(retval.tree)!=null && adaptor.isNil( adaptor.getParent(retval.tree) ) )
						retval.tree = (CommonTree)adaptor.getParent(retval.tree);
					}

					}
					break;
				case 4 :
					// SympyEqWalker.g3:18:5: combineMults
					{
					_last = (CommonTree)input.LT(1);
					pushFollow(FOLLOW_combineMults_in_bottomup114);
					combineMults4=combineMults();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==1 ) 
					 
					if ( _first_0==null ) _first_0 = (CommonTree)combineMults4.getTree();

					if ( state.backtracking==1 ) {
					retval.tree = _first_0;
					if ( adaptor.getParent(retval.tree)!=null && adaptor.isNil( adaptor.getParent(retval.tree) ) )
						retval.tree = (CommonTree)adaptor.getParent(retval.tree);
					}

					}
					break;
				case 5 :
					// SympyEqWalker.g3:19:5: changeGe
					{
					_last = (CommonTree)input.LT(1);
					pushFollow(FOLLOW_changeGe_in_bottomup120);
					changeGe5=changeGe();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==1 ) 
					 
					if ( _first_0==null ) _first_0 = (CommonTree)changeGe5.getTree();

					if ( state.backtracking==1 ) {
					retval.tree = _first_0;
					if ( adaptor.getParent(retval.tree)!=null && adaptor.isNil( adaptor.getParent(retval.tree) ) )
						retval.tree = (CommonTree)adaptor.getParent(retval.tree);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "bottomup"


	public static class minusToPlus_return extends TreeRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "minusToPlus"
	// SympyEqWalker.g3:23:1: minusToPlus : ^( '-' x= . y= . ) -> ^( PLUS[\"+\"] $x ^( UNARY_MINUS[\"-\"] $y) ) ;
	public final SympyEqWalker.minusToPlus_return minusToPlus() throws RecognitionException {
		SympyEqWalker.minusToPlus_return retval = new SympyEqWalker.minusToPlus_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonTree _first_0 = null;
		CommonTree _last = null;


		CommonTree char_literal6=null;
		CommonTree x=null;
		CommonTree y=null;

		CommonTree char_literal6_tree=null;
		CommonTree x_tree=null;
		CommonTree y_tree=null;
		RewriteRuleNodeStream stream_23=new RewriteRuleNodeStream(adaptor,"token 23");

		try {
			// SympyEqWalker.g3:23:12: ( ^( '-' x= . y= . ) -> ^( PLUS[\"+\"] $x ^( UNARY_MINUS[\"-\"] $y) ) )
			// SympyEqWalker.g3:23:14: ^( '-' x= . y= . )
			{
			_last = (CommonTree)input.LT(1);
			{
			CommonTree _save_last_1 = _last;
			CommonTree _first_1 = null;
			_last = (CommonTree)input.LT(1);
			char_literal6=(CommonTree)match(input,23,FOLLOW_23_in_minusToPlus134); if (state.failed) return retval;
			 
			if ( state.backtracking==1 ) stream_23.add(char_literal6);

			if ( state.backtracking==1 )
			if ( _first_0==null ) _first_0 = char_literal6;
			match(input, Token.DOWN, null); if (state.failed) return retval;
			_last = (CommonTree)input.LT(1);
			x=(CommonTree)input.LT(1);
			matchAny(input); if (state.failed) return retval;
			 
			if ( state.backtracking==1 )
			if ( _first_1==null ) _first_1 = x;

			_last = (CommonTree)input.LT(1);
			y=(CommonTree)input.LT(1);
			matchAny(input); if (state.failed) return retval;
			 
			if ( state.backtracking==1 )
			if ( _first_1==null ) _first_1 = y;

			match(input, Token.UP, null); if (state.failed) return retval;
			_last = _save_last_1;
			}


			// AST REWRITE
			// elements: y, x
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: y, x
			if ( state.backtracking==1 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_y=new RewriteRuleSubtreeStream(adaptor,"wildcard y",y);
			RewriteRuleSubtreeStream stream_x=new RewriteRuleSubtreeStream(adaptor,"wildcard x",x);
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 23:29: -> ^( PLUS[\"+\"] $x ^( UNARY_MINUS[\"-\"] $y) )
			{
				// SympyEqWalker.g3:23:32: ^( PLUS[\"+\"] $x ^( UNARY_MINUS[\"-\"] $y) )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PLUS, "+"), root_1);
				adaptor.addChild(root_1, stream_x.nextTree());
				// SympyEqWalker.g3:23:47: ^( UNARY_MINUS[\"-\"] $y)
				{
				CommonTree root_2 = (CommonTree)adaptor.nil();
				root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(UNARY_MINUS, "-"), root_2);
				adaptor.addChild(root_2, stream_y.nextTree());
				adaptor.addChild(root_1, root_2);
				}

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			input.replaceChildren(adaptor.getParent(retval.start),
								  adaptor.getChildIndex(retval.start),
								  adaptor.getChildIndex(_last),
								  retval.tree);
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "minusToPlus"


	public static class divToMult_return extends TreeRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "divToMult"
	// SympyEqWalker.g3:26:1: divToMult : ^( '/' x= . y= . ) -> ^( MULT[\"*\"] $x ^( DIV[\"/\"] INT[\"1\"] $y) ) ;
	public final SympyEqWalker.divToMult_return divToMult() throws RecognitionException {
		SympyEqWalker.divToMult_return retval = new SympyEqWalker.divToMult_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonTree _first_0 = null;
		CommonTree _last = null;


		CommonTree char_literal7=null;
		CommonTree x=null;
		CommonTree y=null;

		CommonTree char_literal7_tree=null;
		CommonTree x_tree=null;
		CommonTree y_tree=null;
		RewriteRuleNodeStream stream_24=new RewriteRuleNodeStream(adaptor,"token 24");

		try {
			// SympyEqWalker.g3:26:10: ( ^( '/' x= . y= . ) -> ^( MULT[\"*\"] $x ^( DIV[\"/\"] INT[\"1\"] $y) ) )
			// SympyEqWalker.g3:26:12: ^( '/' x= . y= . )
			{
			_last = (CommonTree)input.LT(1);
			{
			CommonTree _save_last_1 = _last;
			CommonTree _first_1 = null;
			_last = (CommonTree)input.LT(1);
			char_literal7=(CommonTree)match(input,24,FOLLOW_24_in_divToMult171); if (state.failed) return retval;
			 
			if ( state.backtracking==1 ) stream_24.add(char_literal7);

			if ( state.backtracking==1 )
			if ( _first_0==null ) _first_0 = char_literal7;
			match(input, Token.DOWN, null); if (state.failed) return retval;
			_last = (CommonTree)input.LT(1);
			x=(CommonTree)input.LT(1);
			matchAny(input); if (state.failed) return retval;
			 
			if ( state.backtracking==1 )
			if ( _first_1==null ) _first_1 = x;

			_last = (CommonTree)input.LT(1);
			y=(CommonTree)input.LT(1);
			matchAny(input); if (state.failed) return retval;
			 
			if ( state.backtracking==1 )
			if ( _first_1==null ) _first_1 = y;

			match(input, Token.UP, null); if (state.failed) return retval;
			_last = _save_last_1;
			}


			// AST REWRITE
			// elements: x, y
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: y, x
			if ( state.backtracking==1 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_y=new RewriteRuleSubtreeStream(adaptor,"wildcard y",y);
			RewriteRuleSubtreeStream stream_x=new RewriteRuleSubtreeStream(adaptor,"wildcard x",x);
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 26:27: -> ^( MULT[\"*\"] $x ^( DIV[\"/\"] INT[\"1\"] $y) )
			{
				// SympyEqWalker.g3:26:30: ^( MULT[\"*\"] $x ^( DIV[\"/\"] INT[\"1\"] $y) )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(MULT, "*"), root_1);
				adaptor.addChild(root_1, stream_x.nextTree());
				// SympyEqWalker.g3:26:45: ^( DIV[\"/\"] INT[\"1\"] $y)
				{
				CommonTree root_2 = (CommonTree)adaptor.nil();
				root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(DIV, "/"), root_2);
				adaptor.addChild(root_2, (CommonTree)adaptor.create(INT, "1"));
				adaptor.addChild(root_2, stream_y.nextTree());
				adaptor.addChild(root_1, root_2);
				}

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			input.replaceChildren(adaptor.getParent(retval.start),
								  adaptor.getChildIndex(retval.start),
								  adaptor.getChildIndex(_last),
								  retval.tree);
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "divToMult"


	public static class combinePluses_return extends TreeRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "combinePluses"
	// SympyEqWalker.g3:28:1: combinePluses : ( ^( '+' ^( '+' (e+= . )+ ) z= . ) -> ^( '+' ( $e)+ $z) | ^( '+' x= . ^( '+' (e+= . )+ ) ) -> ^( '+' $x ( $e)+ ) );
	public final SympyEqWalker.combinePluses_return combinePluses() throws RecognitionException {
		SympyEqWalker.combinePluses_return retval = new SympyEqWalker.combinePluses_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonTree _first_0 = null;
		CommonTree _last = null;


		CommonTree char_literal8=null;
		CommonTree char_literal9=null;
		CommonTree char_literal10=null;
		CommonTree char_literal11=null;
		CommonTree z=null;
		CommonTree x=null;
		CommonTree e=null;
		List<Object> list_e=null;

		CommonTree char_literal8_tree=null;
		CommonTree char_literal9_tree=null;
		CommonTree char_literal10_tree=null;
		CommonTree char_literal11_tree=null;
		CommonTree z_tree=null;
		CommonTree x_tree=null;
		CommonTree e_tree=null;
		RewriteRuleNodeStream stream_PLUS=new RewriteRuleNodeStream(adaptor,"token PLUS");

		try {
			// SympyEqWalker.g3:29:2: ( ^( '+' ^( '+' (e+= . )+ ) z= . ) -> ^( '+' ( $e)+ $z) | ^( '+' x= . ^( '+' (e+= . )+ ) ) -> ^( '+' $x ( $e)+ ) )
			int alt4=2;
			alt4 = dfa4.predict(input);
			switch (alt4) {
				case 1 :
					// SympyEqWalker.g3:29:5: ^( '+' ^( '+' (e+= . )+ ) z= . )
					{
					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_1 = _last;
					CommonTree _first_1 = null;
					_last = (CommonTree)input.LT(1);
					char_literal8=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_combinePluses214); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_PLUS.add(char_literal8);

					if ( state.backtracking==1 )
					if ( _first_0==null ) _first_0 = char_literal8;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_2 = _last;
					CommonTree _first_2 = null;
					_last = (CommonTree)input.LT(1);
					char_literal9=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_combinePluses217); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_PLUS.add(char_literal9);

					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = char_literal9;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					// SympyEqWalker.g3:29:17: (e+= . )+
					int cnt2=0;
					loop2:
					while (true) {
						int alt2=2;
						int LA2_0 = input.LA(1);
						if ( ((LA2_0 >= ASSIGN && LA2_0 <= 31)) ) {
							alt2=1;
						}

						switch (alt2) {
						case 1 :
							// SympyEqWalker.g3:29:18: e+= .
							{
							_last = (CommonTree)input.LT(1);
							e=(CommonTree)input.LT(1);
							matchAny(input); if (state.failed) return retval;
							 
							if ( state.backtracking==1 )
							if ( _first_2==null ) _first_2 = e;

							if (list_e==null) list_e=new ArrayList<Object>();
							list_e.add(e);
							if ( state.backtracking==1 ) {
							retval.tree = _first_0;
							if ( adaptor.getParent(retval.tree)!=null && adaptor.isNil( adaptor.getParent(retval.tree) ) )
								retval.tree = (CommonTree)adaptor.getParent(retval.tree);
							}

							}
							break;

						default :
							if ( cnt2 >= 1 ) break loop2;
							if (state.backtracking>0) {state.failed=true; return retval;}
							EarlyExitException eee = new EarlyExitException(2, input);
							throw eee;
						}
						cnt2++;
					}

					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_2;
					}


					_last = (CommonTree)input.LT(1);
					z=(CommonTree)input.LT(1);
					matchAny(input); if (state.failed) return retval;
					 
					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = z;

					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_1;
					}


					// AST REWRITE
					// elements: e, z, PLUS
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: z, e
					if ( state.backtracking==1 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_z=new RewriteRuleSubtreeStream(adaptor,"wildcard z",z);
					RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"wildcard e",list_e);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 29:31: -> ^( '+' ( $e)+ $z)
					{
						// SympyEqWalker.g3:29:34: ^( '+' ( $e)+ $z)
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot(
						stream_PLUS.nextNode()
						, root_1);
						if ( !(stream_e.hasNext()) ) {
							throw new RewriteEarlyExitException();
						}
						while ( stream_e.hasNext() ) {
							adaptor.addChild(root_1, stream_e.nextTree());
						}
						stream_e.reset();

						adaptor.addChild(root_1, stream_z.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
					input.replaceChildren(adaptor.getParent(retval.start),
										  adaptor.getChildIndex(retval.start),
										  adaptor.getChildIndex(_last),
										  retval.tree);
					}

					}
					break;
				case 2 :
					// SympyEqWalker.g3:30:5: ^( '+' x= . ^( '+' (e+= . )+ ) )
					{
					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_1 = _last;
					CommonTree _first_1 = null;
					_last = (CommonTree)input.LT(1);
					char_literal10=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_combinePluses250); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_PLUS.add(char_literal10);

					if ( state.backtracking==1 )
					if ( _first_0==null ) _first_0 = char_literal10;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					_last = (CommonTree)input.LT(1);
					x=(CommonTree)input.LT(1);
					matchAny(input); if (state.failed) return retval;
					 
					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = x;

					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_2 = _last;
					CommonTree _first_2 = null;
					_last = (CommonTree)input.LT(1);
					char_literal11=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_combinePluses257); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_PLUS.add(char_literal11);

					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = char_literal11;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					// SympyEqWalker.g3:30:21: (e+= . )+
					int cnt3=0;
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( ((LA3_0 >= ASSIGN && LA3_0 <= 31)) ) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// SympyEqWalker.g3:30:22: e+= .
							{
							_last = (CommonTree)input.LT(1);
							e=(CommonTree)input.LT(1);
							matchAny(input); if (state.failed) return retval;
							 
							if ( state.backtracking==1 )
							if ( _first_2==null ) _first_2 = e;

							if (list_e==null) list_e=new ArrayList<Object>();
							list_e.add(e);
							if ( state.backtracking==1 ) {
							retval.tree = _first_0;
							if ( adaptor.getParent(retval.tree)!=null && adaptor.isNil( adaptor.getParent(retval.tree) ) )
								retval.tree = (CommonTree)adaptor.getParent(retval.tree);
							}

							}
							break;

						default :
							if ( cnt3 >= 1 ) break loop3;
							if (state.backtracking>0) {state.failed=true; return retval;}
							EarlyExitException eee = new EarlyExitException(3, input);
							throw eee;
						}
						cnt3++;
					}

					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_2;
					}


					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_1;
					}


					// AST REWRITE
					// elements: e, PLUS, x
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: x, e
					if ( state.backtracking==1 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_x=new RewriteRuleSubtreeStream(adaptor,"wildcard x",x);
					RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"wildcard e",list_e);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 30:31: -> ^( '+' $x ( $e)+ )
					{
						// SympyEqWalker.g3:30:34: ^( '+' $x ( $e)+ )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot(
						stream_PLUS.nextNode()
						, root_1);
						adaptor.addChild(root_1, stream_x.nextTree());
						if ( !(stream_e.hasNext()) ) {
							throw new RewriteEarlyExitException();
						}
						while ( stream_e.hasNext() ) {
							adaptor.addChild(root_1, stream_e.nextTree());
						}
						stream_e.reset();

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
					input.replaceChildren(adaptor.getParent(retval.start),
										  adaptor.getChildIndex(retval.start),
										  adaptor.getChildIndex(_last),
										  retval.tree);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "combinePluses"


	public static class combineMults_return extends TreeRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "combineMults"
	// SympyEqWalker.g3:33:1: combineMults : ( ^( '*' ^( '*' (e+= . )+ ) z= . ) -> ^( '*' ( $e)+ $z) | ^( '*' x= . ^( '*' (e+= . )+ ) ) -> ^( '*' $x ( $e)+ ) );
	public final SympyEqWalker.combineMults_return combineMults() throws RecognitionException {
		SympyEqWalker.combineMults_return retval = new SympyEqWalker.combineMults_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonTree _first_0 = null;
		CommonTree _last = null;


		CommonTree char_literal12=null;
		CommonTree char_literal13=null;
		CommonTree char_literal14=null;
		CommonTree char_literal15=null;
		CommonTree z=null;
		CommonTree x=null;
		CommonTree e=null;
		List<Object> list_e=null;

		CommonTree char_literal12_tree=null;
		CommonTree char_literal13_tree=null;
		CommonTree char_literal14_tree=null;
		CommonTree char_literal15_tree=null;
		CommonTree z_tree=null;
		CommonTree x_tree=null;
		CommonTree e_tree=null;
		RewriteRuleNodeStream stream_MULT=new RewriteRuleNodeStream(adaptor,"token MULT");

		try {
			// SympyEqWalker.g3:34:2: ( ^( '*' ^( '*' (e+= . )+ ) z= . ) -> ^( '*' ( $e)+ $z) | ^( '*' x= . ^( '*' (e+= . )+ ) ) -> ^( '*' $x ( $e)+ ) )
			int alt7=2;
			alt7 = dfa7.predict(input);
			switch (alt7) {
				case 1 :
					// SympyEqWalker.g3:34:5: ^( '*' ^( '*' (e+= . )+ ) z= . )
					{
					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_1 = _last;
					CommonTree _first_1 = null;
					_last = (CommonTree)input.LT(1);
					char_literal12=(CommonTree)match(input,MULT,FOLLOW_MULT_in_combineMults293); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_MULT.add(char_literal12);

					if ( state.backtracking==1 )
					if ( _first_0==null ) _first_0 = char_literal12;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_2 = _last;
					CommonTree _first_2 = null;
					_last = (CommonTree)input.LT(1);
					char_literal13=(CommonTree)match(input,MULT,FOLLOW_MULT_in_combineMults296); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_MULT.add(char_literal13);

					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = char_literal13;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					// SympyEqWalker.g3:34:17: (e+= . )+
					int cnt5=0;
					loop5:
					while (true) {
						int alt5=2;
						int LA5_0 = input.LA(1);
						if ( ((LA5_0 >= ASSIGN && LA5_0 <= 31)) ) {
							alt5=1;
						}

						switch (alt5) {
						case 1 :
							// SympyEqWalker.g3:34:18: e+= .
							{
							_last = (CommonTree)input.LT(1);
							e=(CommonTree)input.LT(1);
							matchAny(input); if (state.failed) return retval;
							 
							if ( state.backtracking==1 )
							if ( _first_2==null ) _first_2 = e;

							if (list_e==null) list_e=new ArrayList<Object>();
							list_e.add(e);
							if ( state.backtracking==1 ) {
							retval.tree = _first_0;
							if ( adaptor.getParent(retval.tree)!=null && adaptor.isNil( adaptor.getParent(retval.tree) ) )
								retval.tree = (CommonTree)adaptor.getParent(retval.tree);
							}

							}
							break;

						default :
							if ( cnt5 >= 1 ) break loop5;
							if (state.backtracking>0) {state.failed=true; return retval;}
							EarlyExitException eee = new EarlyExitException(5, input);
							throw eee;
						}
						cnt5++;
					}

					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_2;
					}


					_last = (CommonTree)input.LT(1);
					z=(CommonTree)input.LT(1);
					matchAny(input); if (state.failed) return retval;
					 
					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = z;

					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_1;
					}


					// AST REWRITE
					// elements: MULT, z, e
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: z, e
					if ( state.backtracking==1 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_z=new RewriteRuleSubtreeStream(adaptor,"wildcard z",z);
					RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"wildcard e",list_e);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 34:31: -> ^( '*' ( $e)+ $z)
					{
						// SympyEqWalker.g3:34:34: ^( '*' ( $e)+ $z)
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot(
						stream_MULT.nextNode()
						, root_1);
						if ( !(stream_e.hasNext()) ) {
							throw new RewriteEarlyExitException();
						}
						while ( stream_e.hasNext() ) {
							adaptor.addChild(root_1, stream_e.nextTree());
						}
						stream_e.reset();

						adaptor.addChild(root_1, stream_z.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
					input.replaceChildren(adaptor.getParent(retval.start),
										  adaptor.getChildIndex(retval.start),
										  adaptor.getChildIndex(_last),
										  retval.tree);
					}

					}
					break;
				case 2 :
					// SympyEqWalker.g3:35:5: ^( '*' x= . ^( '*' (e+= . )+ ) )
					{
					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_1 = _last;
					CommonTree _first_1 = null;
					_last = (CommonTree)input.LT(1);
					char_literal14=(CommonTree)match(input,MULT,FOLLOW_MULT_in_combineMults329); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_MULT.add(char_literal14);

					if ( state.backtracking==1 )
					if ( _first_0==null ) _first_0 = char_literal14;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					_last = (CommonTree)input.LT(1);
					x=(CommonTree)input.LT(1);
					matchAny(input); if (state.failed) return retval;
					 
					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = x;

					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_2 = _last;
					CommonTree _first_2 = null;
					_last = (CommonTree)input.LT(1);
					char_literal15=(CommonTree)match(input,MULT,FOLLOW_MULT_in_combineMults336); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_MULT.add(char_literal15);

					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = char_literal15;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					// SympyEqWalker.g3:35:21: (e+= . )+
					int cnt6=0;
					loop6:
					while (true) {
						int alt6=2;
						int LA6_0 = input.LA(1);
						if ( ((LA6_0 >= ASSIGN && LA6_0 <= 31)) ) {
							alt6=1;
						}

						switch (alt6) {
						case 1 :
							// SympyEqWalker.g3:35:22: e+= .
							{
							_last = (CommonTree)input.LT(1);
							e=(CommonTree)input.LT(1);
							matchAny(input); if (state.failed) return retval;
							 
							if ( state.backtracking==1 )
							if ( _first_2==null ) _first_2 = e;

							if (list_e==null) list_e=new ArrayList<Object>();
							list_e.add(e);
							if ( state.backtracking==1 ) {
							retval.tree = _first_0;
							if ( adaptor.getParent(retval.tree)!=null && adaptor.isNil( adaptor.getParent(retval.tree) ) )
								retval.tree = (CommonTree)adaptor.getParent(retval.tree);
							}

							}
							break;

						default :
							if ( cnt6 >= 1 ) break loop6;
							if (state.backtracking>0) {state.failed=true; return retval;}
							EarlyExitException eee = new EarlyExitException(6, input);
							throw eee;
						}
						cnt6++;
					}

					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_2;
					}


					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_1;
					}


					// AST REWRITE
					// elements: e, MULT, x
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: x, e
					if ( state.backtracking==1 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_x=new RewriteRuleSubtreeStream(adaptor,"wildcard x",x);
					RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"wildcard e",list_e);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 35:31: -> ^( '*' $x ( $e)+ )
					{
						// SympyEqWalker.g3:35:34: ^( '*' $x ( $e)+ )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot(
						stream_MULT.nextNode()
						, root_1);
						adaptor.addChild(root_1, stream_x.nextTree());
						if ( !(stream_e.hasNext()) ) {
							throw new RewriteEarlyExitException();
						}
						while ( stream_e.hasNext() ) {
							adaptor.addChild(root_1, stream_e.nextTree());
						}
						stream_e.reset();

						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
					input.replaceChildren(adaptor.getParent(retval.start),
										  adaptor.getChildIndex(retval.start),
										  adaptor.getChildIndex(_last),
										  retval.tree);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "combineMults"


	public static class changeGe_return extends TreeRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "changeGe"
	// SympyEqWalker.g3:38:1: changeGe : ( ^( 'Ge' x= . y= . ) -> ^( REL[\"Le\"] $y $x) | ^( 'Gt' x= . y= . ) -> ^( REL[\"Lt\"] $y $x) );
	public final SympyEqWalker.changeGe_return changeGe() throws RecognitionException {
		SympyEqWalker.changeGe_return retval = new SympyEqWalker.changeGe_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		CommonTree _first_0 = null;
		CommonTree _last = null;


		CommonTree string_literal16=null;
		CommonTree string_literal17=null;
		CommonTree x=null;
		CommonTree y=null;

		CommonTree string_literal16_tree=null;
		CommonTree string_literal17_tree=null;
		CommonTree x_tree=null;
		CommonTree y_tree=null;
		RewriteRuleNodeStream stream_26=new RewriteRuleNodeStream(adaptor,"token 26");
		RewriteRuleNodeStream stream_27=new RewriteRuleNodeStream(adaptor,"token 27");

		try {
			// SympyEqWalker.g3:39:2: ( ^( 'Ge' x= . y= . ) -> ^( REL[\"Le\"] $y $x) | ^( 'Gt' x= . y= . ) -> ^( REL[\"Lt\"] $y $x) )
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==26) ) {
				alt8=1;
			}
			else if ( (LA8_0==27) ) {
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
					// SympyEqWalker.g3:39:4: ^( 'Ge' x= . y= . )
					{
					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_1 = _last;
					CommonTree _first_1 = null;
					_last = (CommonTree)input.LT(1);
					string_literal16=(CommonTree)match(input,26,FOLLOW_26_in_changeGe371); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_26.add(string_literal16);

					if ( state.backtracking==1 )
					if ( _first_0==null ) _first_0 = string_literal16;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					_last = (CommonTree)input.LT(1);
					x=(CommonTree)input.LT(1);
					matchAny(input); if (state.failed) return retval;
					 
					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = x;

					_last = (CommonTree)input.LT(1);
					y=(CommonTree)input.LT(1);
					matchAny(input); if (state.failed) return retval;
					 
					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = y;

					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_1;
					}


					// AST REWRITE
					// elements: x, y
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: y, x
					if ( state.backtracking==1 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_y=new RewriteRuleSubtreeStream(adaptor,"wildcard y",y);
					RewriteRuleSubtreeStream stream_x=new RewriteRuleSubtreeStream(adaptor,"wildcard x",x);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 39:20: -> ^( REL[\"Le\"] $y $x)
					{
						// SympyEqWalker.g3:39:23: ^( REL[\"Le\"] $y $x)
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(REL, "Le"), root_1);
						adaptor.addChild(root_1, stream_y.nextTree());
						adaptor.addChild(root_1, stream_x.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
					input.replaceChildren(adaptor.getParent(retval.start),
										  adaptor.getChildIndex(retval.start),
										  adaptor.getChildIndex(_last),
										  retval.tree);
					}

					}
					break;
				case 2 :
					// SympyEqWalker.g3:40:4: ^( 'Gt' x= . y= . )
					{
					_last = (CommonTree)input.LT(1);
					{
					CommonTree _save_last_1 = _last;
					CommonTree _first_1 = null;
					_last = (CommonTree)input.LT(1);
					string_literal17=(CommonTree)match(input,27,FOLLOW_27_in_changeGe399); if (state.failed) return retval;
					 
					if ( state.backtracking==1 ) stream_27.add(string_literal17);

					if ( state.backtracking==1 )
					if ( _first_0==null ) _first_0 = string_literal17;
					match(input, Token.DOWN, null); if (state.failed) return retval;
					_last = (CommonTree)input.LT(1);
					x=(CommonTree)input.LT(1);
					matchAny(input); if (state.failed) return retval;
					 
					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = x;

					_last = (CommonTree)input.LT(1);
					y=(CommonTree)input.LT(1);
					matchAny(input); if (state.failed) return retval;
					 
					if ( state.backtracking==1 )
					if ( _first_1==null ) _first_1 = y;

					match(input, Token.UP, null); if (state.failed) return retval;
					_last = _save_last_1;
					}


					// AST REWRITE
					// elements: x, y
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: y, x
					if ( state.backtracking==1 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_y=new RewriteRuleSubtreeStream(adaptor,"wildcard y",y);
					RewriteRuleSubtreeStream stream_x=new RewriteRuleSubtreeStream(adaptor,"wildcard x",x);
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 40:20: -> ^( REL[\"Lt\"] $y $x)
					{
						// SympyEqWalker.g3:40:23: ^( REL[\"Lt\"] $y $x)
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(REL, "Lt"), root_1);
						adaptor.addChild(root_1, stream_y.nextTree());
						adaptor.addChild(root_1, stream_x.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
					input.replaceChildren(adaptor.getParent(retval.start),
										  adaptor.getChildIndex(retval.start),
										  adaptor.getChildIndex(_last),
										  retval.tree);
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "changeGe"

	// $ANTLR start synpred6_SympyEqWalker
	public final void synpred6_SympyEqWalker_fragment() throws RecognitionException {
		CommonTree z=null;
		CommonTree e=null;
		List<Object> list_e=null;


		// SympyEqWalker.g3:29:5: ( ^( '+' ^( '+' (e+= . )+ ) z= . ) )
		// SympyEqWalker.g3:29:5: ^( '+' ^( '+' (e+= . )+ ) z= . )
		{
		match(input,PLUS,FOLLOW_PLUS_in_synpred6_SympyEqWalker214); if (state.failed) return;

		match(input, Token.DOWN, null); if (state.failed) return;
		match(input,PLUS,FOLLOW_PLUS_in_synpred6_SympyEqWalker217); if (state.failed) return;

		match(input, Token.DOWN, null); if (state.failed) return;
		// SympyEqWalker.g3:29:17: (e+= . )+
		int cnt9=0;
		loop9:
		while (true) {
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( ((LA9_0 >= ASSIGN && LA9_0 <= 31)) ) {
				alt9=1;
			}

			switch (alt9) {
			case 1 :
				// SympyEqWalker.g3:29:18: e+= .
				{
				e=(CommonTree)input.LT(1);
				matchAny(input); if (state.failed) return;

				if (list_e==null) list_e=new ArrayList<Object>();
				list_e.add(e);
				}
				break;

			default :
				if ( cnt9 >= 1 ) break loop9;
				if (state.backtracking>0) {state.failed=true; return;}
				EarlyExitException eee = new EarlyExitException(9, input);
				throw eee;
			}
			cnt9++;
		}

		match(input, Token.UP, null); if (state.failed) return;


		z=(CommonTree)input.LT(1);
		matchAny(input); if (state.failed) return;

		match(input, Token.UP, null); if (state.failed) return;


		}

	}
	// $ANTLR end synpred6_SympyEqWalker

	// $ANTLR start synpred9_SympyEqWalker
	public final void synpred9_SympyEqWalker_fragment() throws RecognitionException {
		CommonTree z=null;
		CommonTree e=null;
		List<Object> list_e=null;


		// SympyEqWalker.g3:34:5: ( ^( '*' ^( '*' (e+= . )+ ) z= . ) )
		// SympyEqWalker.g3:34:5: ^( '*' ^( '*' (e+= . )+ ) z= . )
		{
		match(input,MULT,FOLLOW_MULT_in_synpred9_SympyEqWalker293); if (state.failed) return;

		match(input, Token.DOWN, null); if (state.failed) return;
		match(input,MULT,FOLLOW_MULT_in_synpred9_SympyEqWalker296); if (state.failed) return;

		match(input, Token.DOWN, null); if (state.failed) return;
		// SympyEqWalker.g3:34:17: (e+= . )+
		int cnt10=0;
		loop10:
		while (true) {
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( ((LA10_0 >= ASSIGN && LA10_0 <= 31)) ) {
				alt10=1;
			}

			switch (alt10) {
			case 1 :
				// SympyEqWalker.g3:34:18: e+= .
				{
				e=(CommonTree)input.LT(1);
				matchAny(input); if (state.failed) return;

				if (list_e==null) list_e=new ArrayList<Object>();
				list_e.add(e);
				}
				break;

			default :
				if ( cnt10 >= 1 ) break loop10;
				if (state.backtracking>0) {state.failed=true; return;}
				EarlyExitException eee = new EarlyExitException(10, input);
				throw eee;
			}
			cnt10++;
		}

		match(input, Token.UP, null); if (state.failed) return;


		z=(CommonTree)input.LT(1);
		matchAny(input); if (state.failed) return;

		match(input, Token.UP, null); if (state.failed) return;


		}

	}
	// $ANTLR end synpred9_SympyEqWalker

	// Delegated rules

	public final boolean synpred6_SympyEqWalker() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred6_SympyEqWalker_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred9_SympyEqWalker() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred9_SympyEqWalker_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}


	protected DFA4 dfa4 = new DFA4(this);
	protected DFA7 dfa7 = new DFA7(this);
	static final String DFA4_eotS =
		"\16\uffff";
	static final String DFA4_eofS =
		"\16\uffff";
	static final String DFA4_minS =
		"\1\16\1\2\1\4\1\2\1\uffff\1\4\1\2\1\uffff\1\4\1\2\1\4\1\2\1\3\1\0";
	static final String DFA4_maxS =
		"\1\16\1\2\1\37\1\16\1\uffff\2\37\1\uffff\1\37\1\3\2\37\1\3\1\0";
	static final String DFA4_acceptS =
		"\4\uffff\1\2\2\uffff\1\1\6\uffff";
	static final String DFA4_specialS =
		"\15\uffff\1\0}>";
	static final String[] DFA4_transitionS = {
			"\1\1",
			"\1\2",
			"\12\4\1\3\21\4",
			"\1\5\13\uffff\1\4",
			"",
			"\34\6",
			"\1\7\1\10\34\6",
			"",
			"\12\7\1\11\21\7",
			"\1\12\1\7",
			"\34\13",
			"\1\4\1\14\34\13",
			"\1\15",
			"\1\uffff"
	};

	static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
	static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
	static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
	static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
	static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
	static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
	static final short[][] DFA4_transition;

	static {
		int numStates = DFA4_transitionS.length;
		DFA4_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
		}
	}

	protected class DFA4 extends DFA {

		public DFA4(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 4;
			this.eot = DFA4_eot;
			this.eof = DFA4_eof;
			this.min = DFA4_min;
			this.max = DFA4_max;
			this.accept = DFA4_accept;
			this.special = DFA4_special;
			this.transition = DFA4_transition;
		}
		@Override
		public String getDescription() {
			return "28:1: combinePluses : ( ^( '+' ^( '+' (e+= . )+ ) z= . ) -> ^( '+' ( $e)+ $z) | ^( '+' x= . ^( '+' (e+= . )+ ) ) -> ^( '+' $x ( $e)+ ) );";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			TreeNodeStream input = (TreeNodeStream)_input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA4_13 = input.LA(1);
						 
						int index4_13 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred6_SympyEqWalker()) ) {s = 7;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index4_13);
						if ( s>=0 ) return s;
						break;
			}
			if (state.backtracking>0) {state.failed=true; return -1;}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 4, _s, input);
			error(nvae);
			throw nvae;
		}
	}

	static final String DFA7_eotS =
		"\16\uffff";
	static final String DFA7_eofS =
		"\16\uffff";
	static final String DFA7_minS =
		"\1\14\1\2\1\4\1\2\1\uffff\1\4\1\2\1\uffff\1\4\1\2\1\4\1\2\1\3\1\0";
	static final String DFA7_maxS =
		"\1\14\1\2\1\37\1\14\1\uffff\2\37\1\uffff\1\37\1\3\2\37\1\3\1\0";
	static final String DFA7_acceptS =
		"\4\uffff\1\2\2\uffff\1\1\6\uffff";
	static final String DFA7_specialS =
		"\15\uffff\1\0}>";
	static final String[] DFA7_transitionS = {
			"\1\1",
			"\1\2",
			"\10\4\1\3\23\4",
			"\1\5\11\uffff\1\4",
			"",
			"\34\6",
			"\1\7\1\10\34\6",
			"",
			"\10\7\1\11\23\7",
			"\1\12\1\7",
			"\34\13",
			"\1\4\1\14\34\13",
			"\1\15",
			"\1\uffff"
	};

	static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
	static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
	static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
	static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
	static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
	static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
	static final short[][] DFA7_transition;

	static {
		int numStates = DFA7_transitionS.length;
		DFA7_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
		}
	}

	protected class DFA7 extends DFA {

		public DFA7(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 7;
			this.eot = DFA7_eot;
			this.eof = DFA7_eof;
			this.min = DFA7_min;
			this.max = DFA7_max;
			this.accept = DFA7_accept;
			this.special = DFA7_special;
			this.transition = DFA7_transition;
		}
		@Override
		public String getDescription() {
			return "33:1: combineMults : ( ^( '*' ^( '*' (e+= . )+ ) z= . ) -> ^( '*' ( $e)+ $z) | ^( '*' x= . ^( '*' (e+= . )+ ) ) -> ^( '*' $x ( $e)+ ) );";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			TreeNodeStream input = (TreeNodeStream)_input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA7_13 = input.LA(1);
						 
						int index7_13 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred9_SympyEqWalker()) ) {s = 7;}
						else if ( (true) ) {s = 4;}
						 
						input.seek(index7_13);
						if ( s>=0 ) return s;
						break;
			}
			if (state.backtracking>0) {state.failed=true; return -1;}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 7, _s, input);
			error(nvae);
			throw nvae;
		}
	}

	public static final BitSet FOLLOW_divToMult_in_bottomup93 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_minusToPlus_in_bottomup99 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_combinePluses_in_bottomup108 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_combineMults_in_bottomup114 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_changeGe_in_bottomup120 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_minusToPlus134 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_24_in_divToMult171 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_PLUS_in_combinePluses214 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_PLUS_in_combinePluses217 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_PLUS_in_combinePluses250 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_PLUS_in_combinePluses257 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_MULT_in_combineMults293 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_MULT_in_combineMults296 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_MULT_in_combineMults329 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_MULT_in_combineMults336 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_26_in_changeGe371 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_27_in_changeGe399 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_PLUS_in_synpred6_SympyEqWalker214 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_PLUS_in_synpred6_SympyEqWalker217 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_MULT_in_synpred9_SympyEqWalker293 = new BitSet(new long[]{0x0000000000000004L});
	public static final BitSet FOLLOW_MULT_in_synpred9_SympyEqWalker296 = new BitSet(new long[]{0x0000000000000004L});
}
