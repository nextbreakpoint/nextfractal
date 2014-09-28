// Generated from CFDG.g4 by ANTLR 4.2.2
package com.nextbreakpoint.nextfractal.contextfree.parser;

	import java.util.Map;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CFDGParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__16=1, T__15=2, T__14=3, T__13=4, T__12=5, T__11=6, T__10=7, T__9=8, 
		T__8=9, T__7=10, T__6=11, T__5=12, T__4=13, T__3=14, T__2=15, T__1=16, 
		T__0=17, CFDG2=18, CFDG3=19, USER_RATIONAL=20, STARTSHAPE=21, BACKGROUND=22, 
		INCLUDE=23, IMPORT=24, TILE=25, RULE=26, PATH=27, SHAPE=28, LOOP=29, FINALLY=30, 
		IF=31, ELSE=32, SWITCH=33, CASE=34, RANGE=35, PLUSMINUS=36, TIME=37, TIMESCALE=38, 
		X=39, Y=40, Z=41, ROTATE=42, SIZE=43, SKEW=44, FLIP=45, HUE=46, SATURATION=47, 
		BRIGHTNESS=48, ALPHA=49, TARGETHUE=50, TARGETSATURATION=51, TARGETBRIGHTNESS=52, 
		TARGETALPHA=53, X1=54, X2=55, Y1=56, Y2=57, RX=58, RY=59, WIDTH=60, TRANSFORM=61, 
		PARAM=62, BECOMES=63, LT=64, GT=65, LE=66, GE=67, EQ=68, NEQ=69, NOT=70, 
		AND=71, OR=72, XOR=73, CF_INFINITY=74, USER_PATHOP=75, CLONE=76, LET=77, 
		MODTYPE=78, USER_STRING=79, USER_QSTRING=80, USER_FILENAME=81, USER_ARRAYNAME=82, 
		COMMENT=83, WHITESPACE=84;
	public static final String[] tokenNames = {
		"<INVALID>", "']'", "'^'", "')'", "','", "'_'", "'+'", "'*'", "'['", "'-'", 
		"':'", "'@'", "'('", "';'", "'{'", "'/'", "'}'", "'|'", "'CFDG2'", "'CFDG3'", 
		"USER_RATIONAL", "'startshape'", "'background'", "'include'", "'import'", 
		"'tile'", "'rule'", "'path'", "'shape'", "'loop'", "'finally'", "'if'", 
		"'else'", "'switch'", "'case'", "'..'", "PLUSMINUS", "'time'", "'timescale'", 
		"'x'", "'y'", "'z'", "ROTATE", "SIZE", "'skew'", "FLIP", "HUE", "SATURATION", 
		"BRIGHTNESS", "ALPHA", "TARGETHUE", "TARGETSATURATION", "TARGETBRIGHTNESS", 
		"TARGETALPHA", "'x1'", "'x2'", "'y1'", "'y2'", "'rx'", "'ry'", "'width'", 
		"TRANSFORM", "PARAM", "'='", "'<'", "'>'", "LE", "GE", "'=='", "NEQ", 
		"'!'", "'&&'", "'||'", "'^^'", "'CF_INFINITY'", "USER_PATHOP", "'CLONE'", 
		"'LET'", "MODTYPE", "USER_STRING", "USER_QSTRING", "USER_FILENAME", "USER_ARRAYNAME", 
		"COMMENT", "WHITESPACE"
	};
	public static final int
		RULE_choose = 0, RULE_cfdg2 = 1, RULE_cfdg3 = 2, RULE_statement_v2 = 3, 
		RULE_statement_v3 = 4, RULE_v3clues = 5, RULE_v2stuff = 6, RULE_inclusion_v2 = 7, 
		RULE_import_v3 = 8, RULE_eof = 9, RULE_fileString = 10, RULE_fileNameSpace = 11, 
		RULE_initialization_v3 = 12, RULE_initialization_v2 = 13, RULE_directive_v2 = 14, 
		RULE_directive_string = 15, RULE_shape = 16, RULE_shape_singleton_header = 17, 
		RULE_shape_singleton = 18, RULE_rule_header_v2 = 19, RULE_rule_v2 = 20, 
		RULE_rule_header = 21, RULE_path_header = 22, RULE_rule_v3 = 23, RULE_path = 24, 
		RULE_path_header_v2 = 25, RULE_path_v2 = 26, RULE_parameter = 27, RULE_buncha_parameters = 28, 
		RULE_parameter_list = 29, RULE_function_parameter_list = 30, RULE_parameter_spec = 31, 
		RULE_buncha_elements = 32, RULE_buncha_pathOps_v2 = 33, RULE_pathOp_simple_v2 = 34, 
		RULE_element_simple = 35, RULE_one_or_more_elements = 36, RULE_one_or_more_pathOp_v2 = 37, 
		RULE_caseBody = 38, RULE_caseBody_element = 39, RULE_element = 40, RULE_element_v2clue = 41, 
		RULE_pathOp_v2 = 42, RULE_pathOp_v3clues = 43, RULE_element_loop = 44, 
		RULE_buncha_replacements_v2 = 45, RULE_one_or_more_replacements_v2 = 46, 
		RULE_replacement_simple_v2 = 47, RULE_replacement_v2 = 48, RULE_loopHeader_v2 = 49, 
		RULE_loopHeader = 50, RULE_ifHeader = 51, RULE_ifElseHeader = 52, RULE_transHeader = 53, 
		RULE_switchHeader = 54, RULE_caseHeader = 55, RULE_modification_v2 = 56, 
		RULE_modification = 57, RULE_buncha_adjustments = 58, RULE_adjustment = 59, 
		RULE_letHeader = 60, RULE_letBody = 61, RULE_letVariables = 62, RULE_letVariable = 63, 
		RULE_explist = 64, RULE_arglist = 65, RULE_exp = 66, RULE_exp2 = 67, RULE_exp3 = 68, 
		RULE_expfunc = 69, RULE_exp2func = 70, RULE_shapeName = 71, RULE_global_definition = 72, 
		RULE_function_definition_header = 73, RULE_global_definition_header = 74, 
		RULE_definition_header = 75, RULE_definition = 76;
	public static final String[] ruleNames = {
		"choose", "cfdg2", "cfdg3", "statement_v2", "statement_v3", "v3clues", 
		"v2stuff", "inclusion_v2", "import_v3", "eof", "fileString", "fileNameSpace", 
		"initialization_v3", "initialization_v2", "directive_v2", "directive_string", 
		"shape", "shape_singleton_header", "shape_singleton", "rule_header_v2", 
		"rule_v2", "rule_header", "path_header", "rule_v3", "path", "path_header_v2", 
		"path_v2", "parameter", "buncha_parameters", "parameter_list", "function_parameter_list", 
		"parameter_spec", "buncha_elements", "buncha_pathOps_v2", "pathOp_simple_v2", 
		"element_simple", "one_or_more_elements", "one_or_more_pathOp_v2", "caseBody", 
		"caseBody_element", "element", "element_v2clue", "pathOp_v2", "pathOp_v3clues", 
		"element_loop", "buncha_replacements_v2", "one_or_more_replacements_v2", 
		"replacement_simple_v2", "replacement_v2", "loopHeader_v2", "loopHeader", 
		"ifHeader", "ifElseHeader", "transHeader", "switchHeader", "caseHeader", 
		"modification_v2", "modification", "buncha_adjustments", "adjustment", 
		"letHeader", "letBody", "letVariables", "letVariable", "explist", "arglist", 
		"exp", "exp2", "exp3", "expfunc", "exp2func", "shapeName", "global_definition", 
		"function_definition_header", "global_definition_header", "definition_header", 
		"definition"
	};

	@Override
	public String getGrammarFileName() { return "CFDG.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


		Builder driver = new Builder();

	public CFDGParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ChooseContext extends ParserRuleContext {
		public TerminalNode CFDG2() { return getToken(CFDGParser.CFDG2, 0); }
		public TerminalNode CFDG3() { return getToken(CFDGParser.CFDG3, 0); }
		public Cfdg2Context cfdg2() {
			return getRuleContext(Cfdg2Context.class,0);
		}
		public Cfdg3Context cfdg3() {
			return getRuleContext(Cfdg3Context.class,0);
		}
		public ChooseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choose; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterChoose(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitChoose(this);
		}
	}

	public final ChooseContext choose() throws RecognitionException {
		ChooseContext _localctx = new ChooseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_choose);
		try {
			setState(158);
			switch (_input.LA(1)) {
			case CFDG2:
				enterOuterAlt(_localctx, 1);
				{
				setState(154); match(CFDG2);
				setState(155); cfdg2();
				}
				break;
			case CFDG3:
				enterOuterAlt(_localctx, 2);
				{
				setState(156); match(CFDG3);
				setState(157); cfdg3();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Cfdg2Context extends ParserRuleContext {
		public Statement_v2Context r;
		public Cfdg2Context cfdg2() {
			return getRuleContext(Cfdg2Context.class,0);
		}
		public Statement_v2Context statement_v2() {
			return getRuleContext(Statement_v2Context.class,0);
		}
		public Cfdg2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cfdg2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterCfdg2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitCfdg2(this);
		}
	}

	public final Cfdg2Context cfdg2() throws RecognitionException {
		Cfdg2Context _localctx = new Cfdg2Context(_ctx, getState());
		enterRule(_localctx, 2, RULE_cfdg2);
		try {
			setState(167);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(160); ((Cfdg2Context)_localctx).r = statement_v2();
				setState(161); cfdg2();

					        if (((Cfdg2Context)_localctx).r.result != null) {
					          	driver.pushRep(((Cfdg2Context)_localctx).r.result, true);
					        }
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(164); ((Cfdg2Context)_localctx).r = statement_v2();

					        if (((Cfdg2Context)_localctx).r.result != null) {
					          	driver.pushRep(((Cfdg2Context)_localctx).r.result, true);
					        }
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Cfdg3Context extends ParserRuleContext {
		public Statement_v3Context r;
		public Cfdg3Context cfdg3() {
			return getRuleContext(Cfdg3Context.class,0);
		}
		public Statement_v3Context statement_v3() {
			return getRuleContext(Statement_v3Context.class,0);
		}
		public Cfdg3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cfdg3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterCfdg3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitCfdg3(this);
		}
	}

	public final Cfdg3Context cfdg3() throws RecognitionException {
		Cfdg3Context _localctx = new Cfdg3Context(_ctx, getState());
		enterRule(_localctx, 4, RULE_cfdg3);
		try {
			setState(176);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(169); ((Cfdg3Context)_localctx).r = statement_v3();
				setState(170); cfdg3();

					        if (((Cfdg3Context)_localctx).r.result != null) {
					          	driver.pushRep(((Cfdg3Context)_localctx).r.result, true);
					        }
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(173); ((Cfdg3Context)_localctx).r = statement_v3();

					        if (((Cfdg3Context)_localctx).r.result != null) {
					          	driver.pushRep(((Cfdg3Context)_localctx).r.result, true);
					        }
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Statement_v2Context extends ParserRuleContext {
		public ASTReplacement result;
		public V3cluesContext v3clues;
		public Inclusion_v2Context inclusion_v2() {
			return getRuleContext(Inclusion_v2Context.class,0);
		}
		public Rule_v2Context rule_v2() {
			return getRuleContext(Rule_v2Context.class,0);
		}
		public Directive_v2Context directive_v2() {
			return getRuleContext(Directive_v2Context.class,0);
		}
		public V3cluesContext v3clues() {
			return getRuleContext(V3cluesContext.class,0);
		}
		public Path_v2Context path_v2() {
			return getRuleContext(Path_v2Context.class,0);
		}
		public EofContext eof() {
			return getRuleContext(EofContext.class,0);
		}
		public Initialization_v2Context initialization_v2() {
			return getRuleContext(Initialization_v2Context.class,0);
		}
		public Statement_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterStatement_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitStatement_v2(this);
		}
	}

	public final Statement_v2Context statement_v2() throws RecognitionException {
		Statement_v2Context _localctx = new Statement_v2Context(_ctx, getState());
		enterRule(_localctx, 6, RULE_statement_v2);
		try {
			setState(191);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(178); initialization_v2();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(179); directive_v2();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(180); inclusion_v2();
				 
				        	((Statement_v2Context)_localctx).result =  null;
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(183); eof();

				        	((Statement_v2Context)_localctx).result =  null;        	
				        
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(186); rule_v2();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(187); path_v2();
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(188); ((Statement_v2Context)_localctx).v3clues = v3clues();
				 
				        	if (driver.getMaybeVersion().equals("CFDG2")) {
				        		driver.error("Illegal mixture of old and new elements", (((Statement_v2Context)_localctx).v3clues!=null?(((Statement_v2Context)_localctx).v3clues.start):null));	        		
				        	} else {
				        		driver.setMaybeVersion("CFDG3");
				        	}
				        	((Statement_v2Context)_localctx).result =  null;
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Statement_v3Context extends ParserRuleContext {
		public ASTReplacement result;
		public ShapeContext r;
		public Global_definitionContext d;
		public V2stuffContext v2stuff;
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public V2stuffContext v2stuff() {
			return getRuleContext(V2stuffContext.class,0);
		}
		public Global_definitionContext global_definition() {
			return getRuleContext(Global_definitionContext.class,0);
		}
		public Shape_singletonContext shape_singleton() {
			return getRuleContext(Shape_singletonContext.class,0);
		}
		public Import_v3Context import_v3() {
			return getRuleContext(Import_v3Context.class,0);
		}
		public Initialization_v3Context initialization_v3() {
			return getRuleContext(Initialization_v3Context.class,0);
		}
		public EofContext eof() {
			return getRuleContext(EofContext.class,0);
		}
		public Rule_v3Context rule_v3() {
			return getRuleContext(Rule_v3Context.class,0);
		}
		public ShapeContext shape() {
			return getRuleContext(ShapeContext.class,0);
		}
		public Statement_v3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_v3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterStatement_v3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitStatement_v3(this);
		}
	}

	public final Statement_v3Context statement_v3() throws RecognitionException {
		Statement_v3Context _localctx = new Statement_v3Context(_ctx, getState());
		enterRule(_localctx, 8, RULE_statement_v3);
		try {
			setState(212);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(193); initialization_v3();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(194); import_v3();
				 
				        	((Statement_v3Context)_localctx).result =  null;
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(197); eof();

				        	((Statement_v3Context)_localctx).result =  null;        	
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(200); rule_v3();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(201); path();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(202); ((Statement_v3Context)_localctx).r = shape();
				 
				        	((Statement_v3Context)_localctx).result =  null;
				        
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(205); shape_singleton();
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(206); ((Statement_v3Context)_localctx).d = global_definition();
				 
				        	((Statement_v3Context)_localctx).result =  ((Statement_v3Context)_localctx).d.result;
				        
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(209); ((Statement_v3Context)_localctx).v2stuff = v2stuff();

				        	driver.error("Illegal mixture of old and new elements", (((Statement_v3Context)_localctx).v2stuff!=null?(((Statement_v3Context)_localctx).v2stuff.start):null));	
				        	((Statement_v3Context)_localctx).result =  null;
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class V3cluesContext extends ParserRuleContext {
		public TerminalNode PARAM() { return getToken(CFDGParser.PARAM, 0); }
		public TerminalNode STARTSHAPE() { return getToken(CFDGParser.STARTSHAPE, 0); }
		public TerminalNode IMPORT() { return getToken(CFDGParser.IMPORT, 0); }
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public TerminalNode SHAPE() { return getToken(CFDGParser.SHAPE, 0); }
		public TerminalNode USER_STRING(int i) {
			return getToken(CFDGParser.USER_STRING, i);
		}
		public List<TerminalNode> USER_STRING() { return getTokens(CFDGParser.USER_STRING); }
		public TerminalNode PATH() { return getToken(CFDGParser.PATH, 0); }
		public TerminalNode BECOMES() { return getToken(CFDGParser.BECOMES, 0); }
		public TerminalNode USER_ARRAYNAME() { return getToken(CFDGParser.USER_ARRAYNAME, 0); }
		public V3cluesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_v3clues; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterV3clues(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitV3clues(this);
		}
	}

	public final V3cluesContext v3clues() throws RecognitionException {
		V3cluesContext _localctx = new V3cluesContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_v3clues);
		try {
			setState(239);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(214); match(USER_STRING);
				setState(215); match(BECOMES);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(216); match(MODTYPE);
				setState(217); match(BECOMES);
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(218); match(PARAM);
				setState(219); match(BECOMES);
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(220); match(USER_STRING);
				setState(221); match(12);
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(222); match(USER_STRING);
				setState(223); match(USER_STRING);
				setState(224); match(12);
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(225); match(IMPORT);
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(226); match(SHAPE);
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(227); match(PATH);
				setState(228); match(USER_STRING);
				setState(229); match(12);
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(230); match(STARTSHAPE);
				setState(231); match(USER_STRING);
				setState(232); match(12);
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(233); match(STARTSHAPE);
				setState(234); match(USER_STRING);
				setState(235); match(8);
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(236); match(STARTSHAPE);
				setState(237); match(USER_ARRAYNAME);
				setState(238); match(8);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class V2stuffContext extends ParserRuleContext {
		public Rule_header_v2Context rule_header_v2() {
			return getRuleContext(Rule_header_v2Context.class,0);
		}
		public TerminalNode INCLUDE() { return getToken(CFDGParser.INCLUDE, 0); }
		public Modification_v2Context modification_v2() {
			return getRuleContext(Modification_v2Context.class,0);
		}
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public TerminalNode TILE() { return getToken(CFDGParser.TILE, 0); }
		public FileStringContext fileString() {
			return getRuleContext(FileStringContext.class,0);
		}
		public TerminalNode BACKGROUND() { return getToken(CFDGParser.BACKGROUND, 0); }
		public V2stuffContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_v2stuff; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterV2stuff(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitV2stuff(this);
		}
	}

	public final V2stuffContext v2stuff() throws RecognitionException {
		V2stuffContext _localctx = new V2stuffContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_v2stuff);
		try {
			setState(250);
			switch (_input.LA(1)) {
			case BACKGROUND:
				enterOuterAlt(_localctx, 1);
				{
				setState(241); match(BACKGROUND);
				setState(242); modification_v2();
				}
				break;
			case TILE:
				enterOuterAlt(_localctx, 2);
				{
				setState(243); match(TILE);
				setState(244); modification_v2();
				}
				break;
			case MODTYPE:
				enterOuterAlt(_localctx, 3);
				{
				setState(245); match(MODTYPE);
				setState(246); modification_v2();
				}
				break;
			case INCLUDE:
				enterOuterAlt(_localctx, 4);
				{
				setState(247); match(INCLUDE);
				setState(248); fileString();
				}
				break;
			case RULE:
				enterOuterAlt(_localctx, 5);
				{
				setState(249); rule_header_v2();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Inclusion_v2Context extends ParserRuleContext {
		public Token INCLUDE;
		public Token f;
		public TerminalNode INCLUDE() { return getToken(CFDGParser.INCLUDE, 0); }
		public TerminalNode USER_FILENAME() { return getToken(CFDGParser.USER_FILENAME, 0); }
		public TerminalNode USER_QSTRING() { return getToken(CFDGParser.USER_QSTRING, 0); }
		public Inclusion_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusion_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterInclusion_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitInclusion_v2(this);
		}
	}

	public final Inclusion_v2Context inclusion_v2() throws RecognitionException {
		Inclusion_v2Context _localctx = new Inclusion_v2Context(_ctx, getState());
		enterRule(_localctx, 14, RULE_inclusion_v2);
		try {
			setState(258);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(252); ((Inclusion_v2Context)_localctx).INCLUDE = match(INCLUDE);
				setState(253); ((Inclusion_v2Context)_localctx).f = match(USER_QSTRING);

				        	driver.setShape(null, ((Inclusion_v2Context)_localctx).INCLUDE);
				        	driver.includeFile(((Inclusion_v2Context)_localctx).f.getText(), ((Inclusion_v2Context)_localctx).INCLUDE);
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(255); ((Inclusion_v2Context)_localctx).INCLUDE = match(INCLUDE);
				setState(256); ((Inclusion_v2Context)_localctx).f = match(USER_FILENAME);

				        	driver.setShape(null, ((Inclusion_v2Context)_localctx).INCLUDE);
				        	driver.includeFile(((Inclusion_v2Context)_localctx).f.getText(), ((Inclusion_v2Context)_localctx).INCLUDE);
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Import_v3Context extends ParserRuleContext {
		public Token IMPORT;
		public FileNameSpaceContext n;
		public FileStringContext f;
		public FileNameSpaceContext fileNameSpace() {
			return getRuleContext(FileNameSpaceContext.class,0);
		}
		public TerminalNode IMPORT() { return getToken(CFDGParser.IMPORT, 0); }
		public FileStringContext fileString() {
			return getRuleContext(FileStringContext.class,0);
		}
		public Import_v3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_import_v3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterImport_v3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitImport_v3(this);
		}
	}

	public final Import_v3Context import_v3() throws RecognitionException {
		Import_v3Context _localctx = new Import_v3Context(_ctx, getState());
		enterRule(_localctx, 16, RULE_import_v3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260); ((Import_v3Context)_localctx).IMPORT = match(IMPORT);
			setState(261); ((Import_v3Context)_localctx).n = fileNameSpace();
			setState(262); ((Import_v3Context)_localctx).f = fileString();

			            driver.setShape(null, ((Import_v3Context)_localctx).IMPORT);
			            driver.includeFile(((Import_v3Context)_localctx).f.result, ((Import_v3Context)_localctx).IMPORT);
			            if (((Import_v3Context)_localctx).n.result != null) {
			                driver.pushNameSpace(((Import_v3Context)_localctx).n.result, ((Import_v3Context)_localctx).IMPORT);
			            }
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EofContext extends ParserRuleContext {
		public Token t;
		public TerminalNode EOF() { return getToken(CFDGParser.EOF, 0); }
		public EofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eof; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterEof(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitEof(this);
		}
	}

	public final EofContext eof() throws RecognitionException {
		EofContext _localctx = new EofContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_eof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265); ((EofContext)_localctx).t = match(EOF);

						if (driver.endInclude(((EofContext)_localctx).t)) {
						}
					
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FileStringContext extends ParserRuleContext {
		public String result;
		public Token f;
		public TerminalNode USER_FILENAME() { return getToken(CFDGParser.USER_FILENAME, 0); }
		public TerminalNode USER_QSTRING() { return getToken(CFDGParser.USER_QSTRING, 0); }
		public FileStringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileString; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterFileString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitFileString(this);
		}
	}

	public final FileStringContext fileString() throws RecognitionException {
		FileStringContext _localctx = new FileStringContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_fileString);
		try {
			setState(272);
			switch (_input.LA(1)) {
			case USER_FILENAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(268); ((FileStringContext)_localctx).f = match(USER_FILENAME);

				        	((FileStringContext)_localctx).result =  ((FileStringContext)_localctx).f.getText();
				        
				}
				break;
			case USER_QSTRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(270); ((FileStringContext)_localctx).f = match(USER_QSTRING);

				        	((FileStringContext)_localctx).result =  ((FileStringContext)_localctx).f.getText();
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FileNameSpaceContext extends ParserRuleContext {
		public String result;
		public Token r;
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public FileNameSpaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fileNameSpace; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterFileNameSpace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitFileNameSpace(this);
		}
	}

	public final FileNameSpaceContext fileNameSpace() throws RecognitionException {
		FileNameSpaceContext _localctx = new FileNameSpaceContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_fileNameSpace);
		try {
			setState(278);
			switch (_input.LA(1)) {
			case 11:
				enterOuterAlt(_localctx, 1);
				{
				setState(274); match(11);
				setState(275); ((FileNameSpaceContext)_localctx).r = match(USER_STRING);
				 
				        	((FileNameSpaceContext)_localctx).result =  ((FileNameSpaceContext)_localctx).r.getText();
				        
				}
				break;
			case USER_QSTRING:
			case USER_FILENAME:
				enterOuterAlt(_localctx, 2);
				{
				 
				        	((FileNameSpaceContext)_localctx).result =  null;
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Initialization_v3Context extends ParserRuleContext {
		public ASTDefine result;
		public Token STARTSHAPE;
		public Token s;
		public Parameter_specContext p;
		public ModificationContext m;
		public TerminalNode STARTSHAPE() { return getToken(CFDGParser.STARTSHAPE, 0); }
		public Parameter_specContext parameter_spec() {
			return getRuleContext(Parameter_specContext.class,0);
		}
		public ModificationContext modification() {
			return getRuleContext(ModificationContext.class,0);
		}
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public TerminalNode USER_ARRAYNAME() { return getToken(CFDGParser.USER_ARRAYNAME, 0); }
		public Initialization_v3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initialization_v3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterInitialization_v3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitInitialization_v3(this);
		}
	}

	public final Initialization_v3Context initialization_v3() throws RecognitionException {
		Initialization_v3Context _localctx = new Initialization_v3Context(_ctx, getState());
		enterRule(_localctx, 24, RULE_initialization_v3);
		try {
			setState(296);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(280); ((Initialization_v3Context)_localctx).STARTSHAPE = match(STARTSHAPE);
				setState(281); ((Initialization_v3Context)_localctx).s = match(USER_STRING);
				setState(282); ((Initialization_v3Context)_localctx).p = parameter_spec();
				setState(283); ((Initialization_v3Context)_localctx).m = modification();

				        	String name = ((Initialization_v3Context)_localctx).s.getText();
				        	ASTExpression p = ((Initialization_v3Context)_localctx).p.result;
				        	ASTModification mod = ((Initialization_v3Context)_localctx).m.result;
				        	driver.setShape(null, ((Initialization_v3Context)_localctx).STARTSHAPE);
				        	ASTDefine cfg = driver.makeDefinition(ECFGParam.StartShape.getName(), false, ((Initialization_v3Context)_localctx).STARTSHAPE);
				        	if (cfg != null) {
				        		cfg.setExp(driver.makeRuleSpec(name, p, mod, true, ((Initialization_v3Context)_localctx).s));
				        	}
				        	((Initialization_v3Context)_localctx).result =  cfg;
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(286); ((Initialization_v3Context)_localctx).STARTSHAPE = match(STARTSHAPE);
				setState(287); ((Initialization_v3Context)_localctx).s = match(USER_ARRAYNAME);
				setState(288); ((Initialization_v3Context)_localctx).m = modification();

				        	String name = ((Initialization_v3Context)_localctx).s.getText();
				        	ASTModification mod = ((Initialization_v3Context)_localctx).m.result;
				        	driver.setShape(null, ((Initialization_v3Context)_localctx).STARTSHAPE);
				        	ASTDefine cfg = driver.makeDefinition(ECFGParam.StartShape.getName(), false, ((Initialization_v3Context)_localctx).STARTSHAPE);
				        	if (cfg != null) {
				        		cfg.setExp(driver.makeRuleSpec(name, null, mod, true, ((Initialization_v3Context)_localctx).s));
				        	}
				        	((Initialization_v3Context)_localctx).result =  cfg;
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(291); ((Initialization_v3Context)_localctx).STARTSHAPE = match(STARTSHAPE);
				setState(292); ((Initialization_v3Context)_localctx).s = match(USER_STRING);
				setState(293); ((Initialization_v3Context)_localctx).p = parameter_spec();

				        	String name = ((Initialization_v3Context)_localctx).s.getText();
				        	ASTExpression p = ((Initialization_v3Context)_localctx).p.result;
				        	driver.setShape(null, ((Initialization_v3Context)_localctx).STARTSHAPE);
				        	ASTDefine cfg = driver.makeDefinition(ECFGParam.StartShape.getName(), false, ((Initialization_v3Context)_localctx).STARTSHAPE);
				        	if (cfg != null) {
				        		cfg.setExp(driver.makeRuleSpec(name, p, null, true, ((Initialization_v3Context)_localctx).s));
				        	}
				        	((Initialization_v3Context)_localctx).result =  cfg;
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Initialization_v2Context extends ParserRuleContext {
		public ASTDefine result;
		public Token STARTSHAPE;
		public Token s;
		public TerminalNode STARTSHAPE() { return getToken(CFDGParser.STARTSHAPE, 0); }
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public Initialization_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initialization_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterInitialization_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitInitialization_v2(this);
		}
	}

	public final Initialization_v2Context initialization_v2() throws RecognitionException {
		Initialization_v2Context _localctx = new Initialization_v2Context(_ctx, getState());
		enterRule(_localctx, 26, RULE_initialization_v2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298); ((Initialization_v2Context)_localctx).STARTSHAPE = match(STARTSHAPE);
			setState(299); ((Initialization_v2Context)_localctx).s = match(USER_STRING);

			        	String name = ((Initialization_v2Context)_localctx).s.getText();
			        	driver.setShape(null, ((Initialization_v2Context)_localctx).STARTSHAPE);
			        	ASTDefine cfg = driver.makeDefinition(ECFGParam.StartShape.getName(), false, ((Initialization_v2Context)_localctx).STARTSHAPE);
			        	if (cfg != null) {
			        		cfg.setExp(driver.makeRuleSpec(name, null, null, true, ((Initialization_v2Context)_localctx).s));
			        	}
			        	((Initialization_v2Context)_localctx).result =  cfg;
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Directive_v2Context extends ParserRuleContext {
		public ASTDefine result;
		public Directive_stringContext s;
		public Modification_v2Context m;
		public Modification_v2Context modification_v2() {
			return getRuleContext(Modification_v2Context.class,0);
		}
		public Directive_stringContext directive_string() {
			return getRuleContext(Directive_stringContext.class,0);
		}
		public Directive_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directive_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterDirective_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitDirective_v2(this);
		}
	}

	public final Directive_v2Context directive_v2() throws RecognitionException {
		Directive_v2Context _localctx = new Directive_v2Context(_ctx, getState());
		enterRule(_localctx, 28, RULE_directive_v2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(302); ((Directive_v2Context)_localctx).s = directive_string();
			setState(303); ((Directive_v2Context)_localctx).m = modification_v2();

			        	ASTModification mod = ((Directive_v2Context)_localctx).m.result; 
			            ASTDefine cfg = driver.makeDefinition(((Directive_v2Context)_localctx).s.result, false, (((Directive_v2Context)_localctx).s!=null?(((Directive_v2Context)_localctx).s.start):null));
			            if (cfg != null) {
			                cfg.setExp(mod);
			            }
			            driver.setMaybeVersion("CFDG2");
			        	((Directive_v2Context)_localctx).result =  cfg;
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Directive_stringContext extends ParserRuleContext {
		public String result;
		public Token t;
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public TerminalNode TILE() { return getToken(CFDGParser.TILE, 0); }
		public TerminalNode BACKGROUND() { return getToken(CFDGParser.BACKGROUND, 0); }
		public Directive_stringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_directive_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterDirective_string(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitDirective_string(this);
		}
	}

	public final Directive_stringContext directive_string() throws RecognitionException {
		Directive_stringContext _localctx = new Directive_stringContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_directive_string);
		try {
			setState(312);
			switch (_input.LA(1)) {
			case BACKGROUND:
				enterOuterAlt(_localctx, 1);
				{
				setState(306); match(BACKGROUND);
				 
				        	((Directive_stringContext)_localctx).result =  ECFGParam.Background.getName();
				        
				}
				break;
			case TILE:
				enterOuterAlt(_localctx, 2);
				{
				setState(308); match(TILE);
				 
				        	((Directive_stringContext)_localctx).result =  ECFGParam.Tile.getName();
				        
				}
				break;
			case MODTYPE:
				enterOuterAlt(_localctx, 3);
				{
				setState(310); ((Directive_stringContext)_localctx).t = match(MODTYPE);

				        	if (EModType.size.name().equals(((Directive_stringContext)_localctx).t.getText())) {
				                ((Directive_stringContext)_localctx).result =  ECFGParam.Size.getName();
				        	} else if (EModType.time.name().equals(((Directive_stringContext)_localctx).t.getText())) {
				                ((Directive_stringContext)_localctx).result =  ECFGParam.Time.getName();
				        	} else {
				                ((Directive_stringContext)_localctx).result =  ECFGParam.Size.getName();
				                driver.error("Syntax error", ((Directive_stringContext)_localctx).t);
				        	} 
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShapeContext extends ParserRuleContext {
		public Token SHAPE;
		public Token s;
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public TerminalNode SHAPE() { return getToken(CFDGParser.SHAPE, 0); }
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public ShapeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shape; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterShape(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitShape(this);
		}
	}

	public final ShapeContext shape() throws RecognitionException {
		ShapeContext _localctx = new ShapeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_shape);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314); ((ShapeContext)_localctx).SHAPE = match(SHAPE);
			setState(315); ((ShapeContext)_localctx).s = match(USER_STRING);
			setState(316); parameter_list();

			        	String name = ((ShapeContext)_localctx).s.getText(); 
						driver.setShape(name, ((ShapeContext)_localctx).SHAPE);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Shape_singleton_headerContext extends ParserRuleContext {
		public ASTRule result;
		public ShapeContext s;
		public Token t;
		public ShapeContext shape() {
			return getRuleContext(ShapeContext.class,0);
		}
		public Shape_singleton_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shape_singleton_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterShape_singleton_header(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitShape_singleton_header(this);
		}
	}

	public final Shape_singleton_headerContext shape_singleton_header() throws RecognitionException {
		Shape_singleton_headerContext _localctx = new Shape_singleton_headerContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_shape_singleton_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319); ((Shape_singleton_headerContext)_localctx).s = shape();
			setState(320); ((Shape_singleton_headerContext)_localctx).t = match(14);

			        	driver.setInPathContainer(false);
			        	((Shape_singleton_headerContext)_localctx).result =  new ASTRule(-1, (((Shape_singleton_headerContext)_localctx).s!=null?(((Shape_singleton_headerContext)_localctx).s.start):null));
			        	driver.addRule(_localctx.result);
			        	driver.pushRepContainer(_localctx.result.getRuleBody());
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Shape_singletonContext extends ParserRuleContext {
		public ASTRule result;
		public Shape_singleton_headerContext s;
		public Buncha_elementsContext buncha_elements() {
			return getRuleContext(Buncha_elementsContext.class,0);
		}
		public Shape_singleton_headerContext shape_singleton_header() {
			return getRuleContext(Shape_singleton_headerContext.class,0);
		}
		public Shape_singletonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shape_singleton; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterShape_singleton(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitShape_singleton(this);
		}
	}

	public final Shape_singletonContext shape_singleton() throws RecognitionException {
		Shape_singletonContext _localctx = new Shape_singletonContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_shape_singleton);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(323); ((Shape_singletonContext)_localctx).s = shape_singleton_header();
			setState(324); buncha_elements(0);
			setState(325); match(16);

			        	((Shape_singletonContext)_localctx).result =  ((Shape_singletonContext)_localctx).s.result;
			        	driver.popRepContainer(_localctx.result);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rule_header_v2Context extends ParserRuleContext {
		public ASTRule result;
		public Token RULE;
		public Token s;
		public Token w;
		public TerminalNode RULE() { return getToken(CFDGParser.RULE, 0); }
		public TerminalNode USER_RATIONAL() { return getToken(CFDGParser.USER_RATIONAL, 0); }
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public Rule_header_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rule_header_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterRule_header_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitRule_header_v2(this);
		}
	}

	public final Rule_header_v2Context rule_header_v2() throws RecognitionException {
		Rule_header_v2Context _localctx = new Rule_header_v2Context(_ctx, getState());
		enterRule(_localctx, 38, RULE_rule_header_v2);
		try {
			setState(335);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(328); ((Rule_header_v2Context)_localctx).RULE = match(RULE);
				setState(329); ((Rule_header_v2Context)_localctx).s = match(USER_STRING);

				        	String name = ((Rule_header_v2Context)_localctx).s.getText();
				        	driver.setShape(null, ((Rule_header_v2Context)_localctx).RULE);
				        	((Rule_header_v2Context)_localctx).result =  new ASTRule(driver.stringToShape(name, false, ((Rule_header_v2Context)_localctx).RULE), ((Rule_header_v2Context)_localctx).RULE);
				        	driver.addRule(_localctx.result);
				        	driver.pushRepContainer(_localctx.result.getRuleBody());
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(331); ((Rule_header_v2Context)_localctx).RULE = match(RULE);
				setState(332); ((Rule_header_v2Context)_localctx).s = match(USER_STRING);
				setState(333); ((Rule_header_v2Context)_localctx).w = match(USER_RATIONAL);

				        	String name = ((Rule_header_v2Context)_localctx).s.getText();
				        	String weight = ((Rule_header_v2Context)_localctx).w.getText();
				        	driver.setShape(null, ((Rule_header_v2Context)_localctx).RULE);
				        	((Rule_header_v2Context)_localctx).result =  new ASTRule(driver.stringToShape(name, false, ((Rule_header_v2Context)_localctx).RULE), Float.parseFloat(weight), weight.indexOf("\u0025") != -1, ((Rule_header_v2Context)_localctx).RULE);
				        	driver.addRule(_localctx.result);
				        	driver.pushRepContainer(_localctx.result.getRuleBody());
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rule_v2Context extends ParserRuleContext {
		public ASTRule result;
		public Rule_header_v2Context h;
		public Rule_header_v2Context rule_header_v2() {
			return getRuleContext(Rule_header_v2Context.class,0);
		}
		public Buncha_replacements_v2Context buncha_replacements_v2() {
			return getRuleContext(Buncha_replacements_v2Context.class,0);
		}
		public Rule_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rule_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterRule_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitRule_v2(this);
		}
	}

	public final Rule_v2Context rule_v2() throws RecognitionException {
		Rule_v2Context _localctx = new Rule_v2Context(_ctx, getState());
		enterRule(_localctx, 40, RULE_rule_v2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337); ((Rule_v2Context)_localctx).h = rule_header_v2();
			setState(338); match(14);
			setState(339); buncha_replacements_v2();
			setState(340); match(16);

			            driver.setMaybeVersion("CFDG2");
			        	((Rule_v2Context)_localctx).result =  ((Rule_v2Context)_localctx).h.result;
			        	driver.popRepContainer(((Rule_v2Context)_localctx).h.result);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rule_headerContext extends ParserRuleContext {
		public ASTRule result;
		public Token RULE;
		public Token w;
		public TerminalNode RULE() { return getToken(CFDGParser.RULE, 0); }
		public TerminalNode USER_RATIONAL() { return getToken(CFDGParser.USER_RATIONAL, 0); }
		public Rule_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rule_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterRule_header(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitRule_header(this);
		}
	}

	public final Rule_headerContext rule_header() throws RecognitionException {
		Rule_headerContext _localctx = new Rule_headerContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_rule_header);
		try {
			setState(348);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(343); ((Rule_headerContext)_localctx).RULE = match(RULE);

				        	driver.setInPathContainer(false);
				        	((Rule_headerContext)_localctx).result =  new ASTRule(-1, ((Rule_headerContext)_localctx).RULE);
				        	driver.addRule(_localctx.result);
				        	driver.pushRepContainer(_localctx.result.getRuleBody());
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(345); ((Rule_headerContext)_localctx).RULE = match(RULE);
				setState(346); ((Rule_headerContext)_localctx).w = match(USER_RATIONAL);

				        	driver.setInPathContainer(false);
				        	String weight = ((Rule_headerContext)_localctx).w.getText();
				        	((Rule_headerContext)_localctx).result =  new ASTRule(-1, Float.parseFloat(weight), weight.indexOf("\u0025") != -1, ((Rule_headerContext)_localctx).RULE);
				        	driver.addRule(_localctx.result);
				        	driver.pushRepContainer(_localctx.result.getRuleBody());
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Path_headerContext extends ParserRuleContext {
		public ASTRule result;
		public Token PATH;
		public Token s;
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public TerminalNode PATH() { return getToken(CFDGParser.PATH, 0); }
		public Path_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterPath_header(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitPath_header(this);
		}
	}

	public final Path_headerContext path_header() throws RecognitionException {
		Path_headerContext _localctx = new Path_headerContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_path_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(350); ((Path_headerContext)_localctx).PATH = match(PATH);
			setState(351); ((Path_headerContext)_localctx).s = match(USER_STRING);
			setState(352); parameter_list();

			        	String name = ((Path_headerContext)_localctx).s.getText();
			        	driver.setShape(null, ((Path_headerContext)_localctx).PATH);
			        	driver.setInPathContainer(true);
			        	((Path_headerContext)_localctx).result =  new ASTRule(-1, ((Path_headerContext)_localctx).PATH);
			        	_localctx.result.setPath(true);
			        	driver.addRule(_localctx.result);
			        	driver.pushRepContainer(_localctx.result.getRuleBody());
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rule_v3Context extends ParserRuleContext {
		public ASTRule result;
		public Rule_headerContext h;
		public Buncha_elementsContext buncha_elements() {
			return getRuleContext(Buncha_elementsContext.class,0);
		}
		public Rule_headerContext rule_header() {
			return getRuleContext(Rule_headerContext.class,0);
		}
		public Rule_v3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rule_v3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterRule_v3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitRule_v3(this);
		}
	}

	public final Rule_v3Context rule_v3() throws RecognitionException {
		Rule_v3Context _localctx = new Rule_v3Context(_ctx, getState());
		enterRule(_localctx, 46, RULE_rule_v3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355); ((Rule_v3Context)_localctx).h = rule_header();
			setState(356); match(14);
			setState(357); buncha_elements(0);
			setState(358); match(16);

			        	((Rule_v3Context)_localctx).result =  ((Rule_v3Context)_localctx).h.result;
			        	driver.popRepContainer(_localctx.result);
			        	driver.setInPathContainer(false);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathContext extends ParserRuleContext {
		public ASTRule result;
		public Path_headerContext h;
		public Buncha_elementsContext buncha_elements() {
			return getRuleContext(Buncha_elementsContext.class,0);
		}
		public Path_headerContext path_header() {
			return getRuleContext(Path_headerContext.class,0);
		}
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitPath(this);
		}
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_path);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361); ((PathContext)_localctx).h = path_header();
			setState(362); match(14);
			setState(363); buncha_elements(0);
			setState(364); match(16);

			        	((PathContext)_localctx).result =  ((PathContext)_localctx).h.result;
			        	driver.popRepContainer(_localctx.result);
			        	driver.setInPathContainer(false);
			        	driver.setShape(null, (((PathContext)_localctx).h!=null?(((PathContext)_localctx).h.start):null));
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Path_header_v2Context extends ParserRuleContext {
		public ASTRule result;
		public Token PATH;
		public Token s;
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public TerminalNode PATH() { return getToken(CFDGParser.PATH, 0); }
		public Path_header_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path_header_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterPath_header_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitPath_header_v2(this);
		}
	}

	public final Path_header_v2Context path_header_v2() throws RecognitionException {
		Path_header_v2Context _localctx = new Path_header_v2Context(_ctx, getState());
		enterRule(_localctx, 50, RULE_path_header_v2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(367); ((Path_header_v2Context)_localctx).PATH = match(PATH);
			setState(368); ((Path_header_v2Context)_localctx).s = match(USER_STRING);

			        	String name = ((Path_header_v2Context)_localctx).s.getText();
			        	driver.setShape(null, ((Path_header_v2Context)_localctx).PATH);
			        	((Path_header_v2Context)_localctx).result =  new ASTRule(driver.stringToShape(name, false, ((Path_header_v2Context)_localctx).PATH), ((Path_header_v2Context)_localctx).PATH);
			        	_localctx.result.setPath(true);
			        	driver.addRule(_localctx.result);
			        	driver.pushRepContainer(_localctx.result.getRuleBody());
			        	driver.setInPathContainer(true);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Path_v2Context extends ParserRuleContext {
		public ASTRule result;
		public Path_header_v2Context r;
		public Buncha_pathOps_v2Context buncha_pathOps_v2() {
			return getRuleContext(Buncha_pathOps_v2Context.class,0);
		}
		public Path_header_v2Context path_header_v2() {
			return getRuleContext(Path_header_v2Context.class,0);
		}
		public Path_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterPath_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitPath_v2(this);
		}
	}

	public final Path_v2Context path_v2() throws RecognitionException {
		Path_v2Context _localctx = new Path_v2Context(_ctx, getState());
		enterRule(_localctx, 52, RULE_path_v2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371); ((Path_v2Context)_localctx).r = path_header_v2();
			setState(372); match(14);
			setState(373); buncha_pathOps_v2(0);
			setState(374); match(16);

			            ((Path_v2Context)_localctx).result =  ((Path_v2Context)_localctx).r.result;
			            driver.popRepContainer(_localctx.result);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public Token t;
		public Token v;
		public Token SHAPE;
		public Token MODTYPE;
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public TerminalNode SHAPE() { return getToken(CFDGParser.SHAPE, 0); }
		public TerminalNode USER_STRING(int i) {
			return getToken(CFDGParser.USER_STRING, i);
		}
		public List<TerminalNode> USER_STRING() { return getTokens(CFDGParser.USER_STRING); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitParameter(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_parameter);
		try {
			setState(393);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(377); ((ParameterContext)_localctx).t = match(USER_STRING);
				setState(378); ((ParameterContext)_localctx).v = match(USER_STRING);

							String type = ((ParameterContext)_localctx).t.getText();
							String var = ((ParameterContext)_localctx).v.getText();
							driver.nextParameterDecl(type, var, ((ParameterContext)_localctx).t);
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(380); ((ParameterContext)_localctx).SHAPE = match(SHAPE);
				setState(381); ((ParameterContext)_localctx).v = match(USER_STRING);

							String var = ((ParameterContext)_localctx).v.getText();
							driver.nextParameterDecl("shape", var, ((ParameterContext)_localctx).SHAPE);
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(383); ((ParameterContext)_localctx).v = match(USER_STRING);
				setState(384); match(MODTYPE);

				        	driver.error("Reserved keyword: adjustment", ((ParameterContext)_localctx).v);
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(386); ((ParameterContext)_localctx).SHAPE = match(SHAPE);
				setState(387); match(MODTYPE);

				        	driver.error("Reserved keyword: adjustment", ((ParameterContext)_localctx).SHAPE);
				        
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(389); ((ParameterContext)_localctx).v = match(USER_STRING);

							String var = ((ParameterContext)_localctx).v.getText();
							driver.nextParameterDecl("number", var, ((ParameterContext)_localctx).v);
				        
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(391); ((ParameterContext)_localctx).MODTYPE = match(MODTYPE);

				        	driver.error("Reserved keyword: adjustment", ((ParameterContext)_localctx).MODTYPE);
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Buncha_parametersContext extends ParserRuleContext {
		public Buncha_parametersContext buncha_parameters() {
			return getRuleContext(Buncha_parametersContext.class,0);
		}
		public ParameterContext parameter() {
			return getRuleContext(ParameterContext.class,0);
		}
		public Buncha_parametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_buncha_parameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterBuncha_parameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitBuncha_parameters(this);
		}
	}

	public final Buncha_parametersContext buncha_parameters() throws RecognitionException {
		return buncha_parameters(0);
	}

	private Buncha_parametersContext buncha_parameters(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Buncha_parametersContext _localctx = new Buncha_parametersContext(_ctx, _parentState);
		Buncha_parametersContext _prevctx = _localctx;
		int _startState = 56;
		enterRecursionRule(_localctx, 56, RULE_buncha_parameters, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(396); parameter();
			}
			_ctx.stop = _input.LT(-1);
			setState(403);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Buncha_parametersContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_buncha_parameters);
					setState(398);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(399); match(4);
					setState(400); parameter();
					}
					} 
				}
				setState(405);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Parameter_listContext extends ParserRuleContext {
		public Buncha_parametersContext buncha_parameters() {
			return getRuleContext(Buncha_parametersContext.class,0);
		}
		public Parameter_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterParameter_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitParameter_list(this);
		}
	}

	public final Parameter_listContext parameter_list() throws RecognitionException {
		Parameter_listContext _localctx = new Parameter_listContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_parameter_list);
		try {
			setState(412);
			switch (_input.LA(1)) {
			case 12:
				enterOuterAlt(_localctx, 1);
				{
				setState(406); match(12);
				setState(407); buncha_parameters(0);
				setState(408); match(3);

				        
				}
				break;
			case EOF:
			case 14:
			case STARTSHAPE:
			case BACKGROUND:
			case INCLUDE:
			case IMPORT:
			case TILE:
			case RULE:
			case PATH:
			case SHAPE:
			case MODTYPE:
			case USER_STRING:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Function_parameter_listContext extends ParserRuleContext {
		public Buncha_parametersContext buncha_parameters() {
			return getRuleContext(Buncha_parametersContext.class,0);
		}
		public Function_parameter_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_parameter_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterFunction_parameter_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitFunction_parameter_list(this);
		}
	}

	public final Function_parameter_listContext function_parameter_list() throws RecognitionException {
		Function_parameter_listContext _localctx = new Function_parameter_listContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_function_parameter_list);
		try {
			setState(420);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(414); match(12);
				setState(415); buncha_parameters(0);
				setState(416); match(3);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(418); match(12);
				setState(419); match(3);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Parameter_specContext extends ParserRuleContext {
		public ASTExpression result;
		public ArglistContext a;
		public Token t;
		public TerminalNode BECOMES() { return getToken(CFDGParser.BECOMES, 0); }
		public ArglistContext arglist() {
			return getRuleContext(ArglistContext.class,0);
		}
		public Parameter_specContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_spec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterParameter_spec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitParameter_spec(this);
		}
	}

	public final Parameter_specContext parameter_spec() throws RecognitionException {
		Parameter_specContext _localctx = new Parameter_specContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_parameter_spec);
		try {
			setState(435);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(422); match(12);
				setState(423); ((Parameter_specContext)_localctx).a = arglist();
				setState(424); match(3);
				 
				        	((Parameter_specContext)_localctx).result =  ((Parameter_specContext)_localctx).a.result;
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(427); ((Parameter_specContext)_localctx).t = match(12);
				setState(428); match(BECOMES);
				setState(429); match(3);
				 
				        	((Parameter_specContext)_localctx).result =  new ASTExpression(false, false, EExpType.ReuseType, ((Parameter_specContext)_localctx).t);
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(431); match(12);
				setState(432); match(3);
				 
				        	((Parameter_specContext)_localctx).result =  null; 
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{

				        	((Parameter_specContext)_localctx).result =  null;
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Buncha_elementsContext extends ParserRuleContext {
		public ElementContext r;
		public Buncha_elementsContext buncha_elements() {
			return getRuleContext(Buncha_elementsContext.class,0);
		}
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public Buncha_elementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_buncha_elements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterBuncha_elements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitBuncha_elements(this);
		}
	}

	public final Buncha_elementsContext buncha_elements() throws RecognitionException {
		return buncha_elements(0);
	}

	private Buncha_elementsContext buncha_elements(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Buncha_elementsContext _localctx = new Buncha_elementsContext(_ctx, _parentState);
		Buncha_elementsContext _prevctx = _localctx;
		int _startState = 64;
		enterRecursionRule(_localctx, 64, RULE_buncha_elements, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			}
			_ctx.stop = _input.LT(-1);
			setState(444);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Buncha_elementsContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_buncha_elements);
					setState(438);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(439); ((Buncha_elementsContext)_localctx).r = element();

					                  	driver.pushRep(((Buncha_elementsContext)_localctx).r.result, false);
					                  
					}
					} 
				}
				setState(446);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Buncha_pathOps_v2Context extends ParserRuleContext {
		public PathOp_v2Context r;
		public Buncha_pathOps_v2Context buncha_pathOps_v2() {
			return getRuleContext(Buncha_pathOps_v2Context.class,0);
		}
		public PathOp_v2Context pathOp_v2() {
			return getRuleContext(PathOp_v2Context.class,0);
		}
		public Buncha_pathOps_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_buncha_pathOps_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterBuncha_pathOps_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitBuncha_pathOps_v2(this);
		}
	}

	public final Buncha_pathOps_v2Context buncha_pathOps_v2() throws RecognitionException {
		return buncha_pathOps_v2(0);
	}

	private Buncha_pathOps_v2Context buncha_pathOps_v2(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Buncha_pathOps_v2Context _localctx = new Buncha_pathOps_v2Context(_ctx, _parentState);
		Buncha_pathOps_v2Context _prevctx = _localctx;
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_buncha_pathOps_v2, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			}
			_ctx.stop = _input.LT(-1);
			setState(454);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Buncha_pathOps_v2Context(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_buncha_pathOps_v2);
					setState(448);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(449); ((Buncha_pathOps_v2Context)_localctx).r = pathOp_v2();

					                  	driver.pushRep(((Buncha_pathOps_v2Context)_localctx).r.result, false);
					                  
					}
					} 
				}
				setState(456);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PathOp_simple_v2Context extends ParserRuleContext {
		public ASTReplacement result;
		public Token o;
		public Buncha_adjustmentsContext a;
		public ShapeNameContext s;
		public Modification_v2Context m;
		public Modification_v2Context modification_v2() {
			return getRuleContext(Modification_v2Context.class,0);
		}
		public ShapeNameContext shapeName() {
			return getRuleContext(ShapeNameContext.class,0);
		}
		public Buncha_adjustmentsContext buncha_adjustments() {
			return getRuleContext(Buncha_adjustmentsContext.class,0);
		}
		public TerminalNode USER_PATHOP() { return getToken(CFDGParser.USER_PATHOP, 0); }
		public PathOp_simple_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathOp_simple_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterPathOp_simple_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitPathOp_simple_v2(this);
		}
	}

	public final PathOp_simple_v2Context pathOp_simple_v2() throws RecognitionException {
		PathOp_simple_v2Context _localctx = new PathOp_simple_v2Context(_ctx, getState());
		enterRule(_localctx, 68, RULE_pathOp_simple_v2);
		try {
			setState(467);
			switch (_input.LA(1)) {
			case USER_PATHOP:
				enterOuterAlt(_localctx, 1);
				{
				setState(457); ((PathOp_simple_v2Context)_localctx).o = match(USER_PATHOP);
				setState(458); match(14);
				setState(459); ((PathOp_simple_v2Context)_localctx).a = buncha_adjustments(0);
				setState(460); match(16);

				        	String pop = ((PathOp_simple_v2Context)_localctx).o.getText();
				        	ASTModification mod = ((PathOp_simple_v2Context)_localctx).a.result;
				            driver.setMaybeVersion("CFDG2");
				        	((PathOp_simple_v2Context)_localctx).result =  new ASTPathOp(pop, mod, ((PathOp_simple_v2Context)_localctx).o);
				        
				}
				break;
			case USER_STRING:
			case USER_ARRAYNAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(463); ((PathOp_simple_v2Context)_localctx).s = shapeName();
				setState(464); ((PathOp_simple_v2Context)_localctx).m = modification_v2();

				        	String cmd = ((PathOp_simple_v2Context)_localctx).s.result;
				        	ASTModification mod = ((PathOp_simple_v2Context)_localctx).m.result;
				            driver.setMaybeVersion("CFDG2");
				        	((PathOp_simple_v2Context)_localctx).result =  new ASTPathCommand(cmd, mod, null, (((PathOp_simple_v2Context)_localctx).s!=null?(((PathOp_simple_v2Context)_localctx).s.start):null));
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Element_simpleContext extends ParserRuleContext {
		public ASTReplacement result;
		public Token o;
		public Exp2Context e;
		public ShapeNameContext s;
		public Parameter_specContext p;
		public ModificationContext m;
		public Token IF;
		public LetHeaderContext h;
		public LetBodyContext b;
		public Token PATH;
		public Token n;
		public LetHeaderContext letHeader() {
			return getRuleContext(LetHeaderContext.class,0);
		}
		public TerminalNode IF() { return getToken(CFDGParser.IF, 0); }
		public LetBodyContext letBody() {
			return getRuleContext(LetBodyContext.class,0);
		}
		public ShapeNameContext shapeName() {
			return getRuleContext(ShapeNameContext.class,0);
		}
		public Parameter_specContext parameter_spec() {
			return getRuleContext(Parameter_specContext.class,0);
		}
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public ModificationContext modification() {
			return getRuleContext(ModificationContext.class,0);
		}
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public TerminalNode USER_PATHOP() { return getToken(CFDGParser.USER_PATHOP, 0); }
		public TerminalNode PATH() { return getToken(CFDGParser.PATH, 0); }
		public Element_simpleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element_simple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterElement_simple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitElement_simple(this);
		}
	}

	public final Element_simpleContext element_simple() throws RecognitionException {
		Element_simpleContext _localctx = new Element_simpleContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_element_simple);
		try {
			setState(502);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(469); ((Element_simpleContext)_localctx).o = match(USER_PATHOP);
				setState(470); match(12);
				setState(471); ((Element_simpleContext)_localctx).e = exp2();
				setState(472); match(3);

				        	String pop = ((Element_simpleContext)_localctx).o.getText();
				        	ASTExpression exp = ((Element_simpleContext)_localctx).e.result;
				        	((Element_simpleContext)_localctx).result =  new ASTPathOp(pop, exp, ((Element_simpleContext)_localctx).o);
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(475); ((Element_simpleContext)_localctx).o = match(USER_PATHOP);
				setState(476); match(12);
				setState(477); match(3);

				        	String operator = ((Element_simpleContext)_localctx).o.getText();
				        	((Element_simpleContext)_localctx).result =  new ASTPathOp(operator, null, ((Element_simpleContext)_localctx).o);
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(479); ((Element_simpleContext)_localctx).s = shapeName();
				setState(480); ((Element_simpleContext)_localctx).p = parameter_spec();
				setState(481); ((Element_simpleContext)_localctx).m = modification();

				        	String cmd = ((Element_simpleContext)_localctx).s.result;
				        	ASTExpression p = ((Element_simpleContext)_localctx).p.result;
				        	ASTModification mod = ((Element_simpleContext)_localctx).m.result;
				        	((Element_simpleContext)_localctx).result =  driver.makeElement(cmd, mod, p, false, (((Element_simpleContext)_localctx).s!=null?(((Element_simpleContext)_localctx).s.start):null));
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(484); ((Element_simpleContext)_localctx).IF = match(IF);
				setState(485); match(12);
				setState(486); ((Element_simpleContext)_localctx).e = exp2();
				setState(487); match(3);
				setState(488); ((Element_simpleContext)_localctx).m = modification();

				        	ASTExpression args = ((Element_simpleContext)_localctx).e.result;
				        	ASTModification mod = ((Element_simpleContext)_localctx).m.result;
				        	args = driver.makeFunction("if", args, false, ((Element_simpleContext)_localctx).IF);
				        	((Element_simpleContext)_localctx).result =  driver.makeElement("if", mod, args, false, ((Element_simpleContext)_localctx).IF);
				        
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(491); ((Element_simpleContext)_localctx).h = letHeader();
				setState(492); ((Element_simpleContext)_localctx).b = letBody();
				setState(493); ((Element_simpleContext)_localctx).m = modification();

				        	ASTRepContainer vars = ((Element_simpleContext)_localctx).h.result;
				        	ASTExpression exp = ((Element_simpleContext)_localctx).b.result;
				        	ASTModification mod = ((Element_simpleContext)_localctx).m.result;
				        	exp = driver.makeLet(vars, exp, (((Element_simpleContext)_localctx).h!=null?(((Element_simpleContext)_localctx).h.start):null));
				        	((Element_simpleContext)_localctx).result =  driver.makeElement("let", mod, exp, false, (((Element_simpleContext)_localctx).m!=null?(((Element_simpleContext)_localctx).m.start):null));
				        
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(496); ((Element_simpleContext)_localctx).PATH = match(PATH);
				setState(497); ((Element_simpleContext)_localctx).n = match(USER_STRING);
				setState(498); ((Element_simpleContext)_localctx).p = parameter_spec();
				setState(499); ((Element_simpleContext)_localctx).m = modification();

				        	String cmd = ((Element_simpleContext)_localctx).n.getText();
				        	ASTExpression p = ((Element_simpleContext)_localctx).p.result;
				        	ASTModification mod = ((Element_simpleContext)_localctx).m.result;
				        	((Element_simpleContext)_localctx).result =  driver.makeElement(cmd, mod, p, true, ((Element_simpleContext)_localctx).PATH);
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class One_or_more_elementsContext extends ParserRuleContext {
		public Element_simpleContext r;
		public Buncha_elementsContext buncha_elements() {
			return getRuleContext(Buncha_elementsContext.class,0);
		}
		public Element_simpleContext element_simple() {
			return getRuleContext(Element_simpleContext.class,0);
		}
		public One_or_more_elementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_one_or_more_elements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterOne_or_more_elements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitOne_or_more_elements(this);
		}
	}

	public final One_or_more_elementsContext one_or_more_elements() throws RecognitionException {
		One_or_more_elementsContext _localctx = new One_or_more_elementsContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_one_or_more_elements);
		try {
			setState(512);
			switch (_input.LA(1)) {
			case 14:
				enterOuterAlt(_localctx, 1);
				{
				setState(504); match(14);
				setState(505); buncha_elements(0);
				setState(506); match(16);
				 
				}
				break;
			case PATH:
			case IF:
			case USER_PATHOP:
			case LET:
			case USER_STRING:
			case USER_ARRAYNAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(509); ((One_or_more_elementsContext)_localctx).r = element_simple();

				        	driver.pushRep(((One_or_more_elementsContext)_localctx).r.result, false);
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class One_or_more_pathOp_v2Context extends ParserRuleContext {
		public PathOp_simple_v2Context r;
		public Buncha_pathOps_v2Context buncha_pathOps_v2() {
			return getRuleContext(Buncha_pathOps_v2Context.class,0);
		}
		public PathOp_simple_v2Context pathOp_simple_v2() {
			return getRuleContext(PathOp_simple_v2Context.class,0);
		}
		public One_or_more_pathOp_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_one_or_more_pathOp_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterOne_or_more_pathOp_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitOne_or_more_pathOp_v2(this);
		}
	}

	public final One_or_more_pathOp_v2Context one_or_more_pathOp_v2() throws RecognitionException {
		One_or_more_pathOp_v2Context _localctx = new One_or_more_pathOp_v2Context(_ctx, getState());
		enterRule(_localctx, 74, RULE_one_or_more_pathOp_v2);
		try {
			setState(522);
			switch (_input.LA(1)) {
			case 14:
				enterOuterAlt(_localctx, 1);
				{
				setState(514); match(14);
				setState(515); buncha_pathOps_v2(0);
				setState(516); match(16);
				 
				}
				break;
			case USER_PATHOP:
			case USER_STRING:
			case USER_ARRAYNAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(519); ((One_or_more_pathOp_v2Context)_localctx).r = pathOp_simple_v2();

				        	driver.pushRep(((One_or_more_pathOp_v2Context)_localctx).r.result, false);
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseBodyContext extends ParserRuleContext {
		public CaseBody_elementContext caseBody_element() {
			return getRuleContext(CaseBody_elementContext.class,0);
		}
		public CaseBodyContext caseBody() {
			return getRuleContext(CaseBodyContext.class,0);
		}
		public CaseBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterCaseBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitCaseBody(this);
		}
	}

	public final CaseBodyContext caseBody() throws RecognitionException {
		CaseBodyContext _localctx = new CaseBodyContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_caseBody);
		try {
			setState(528);
			switch (_input.LA(1)) {
			case ELSE:
			case CASE:
				enterOuterAlt(_localctx, 1);
				{
				setState(524); caseBody_element();
				setState(525); caseBody();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseBody_elementContext extends ParserRuleContext {
		public CaseHeaderContext h;
		public One_or_more_elementsContext one_or_more_elements() {
			return getRuleContext(One_or_more_elementsContext.class,0);
		}
		public CaseHeaderContext caseHeader() {
			return getRuleContext(CaseHeaderContext.class,0);
		}
		public CaseBody_elementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseBody_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterCaseBody_element(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitCaseBody_element(this);
		}
	}

	public final CaseBody_elementContext caseBody_element() throws RecognitionException {
		CaseBody_elementContext _localctx = new CaseBody_elementContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_caseBody_element);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(530); ((CaseBody_elementContext)_localctx).h = caseHeader();
			setState(531); one_or_more_elements();

			        	driver.popRepContainer(driver.getSwitchStack().lastElement());
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementContext extends ParserRuleContext {
		public ASTReplacement result;
		public Element_simpleContext r;
		public Element_loopContext rl;
		public IfHeaderContext ri;
		public IfElseHeaderContext rei;
		public TransHeaderContext rt;
		public SwitchHeaderContext rs;
		public Element_v2clueContext element_v2clue;
		public SwitchHeaderContext switchHeader() {
			return getRuleContext(SwitchHeaderContext.class,0);
		}
		public TransHeaderContext transHeader() {
			return getRuleContext(TransHeaderContext.class,0);
		}
		public DefinitionContext definition() {
			return getRuleContext(DefinitionContext.class,0);
		}
		public IfElseHeaderContext ifElseHeader() {
			return getRuleContext(IfElseHeaderContext.class,0);
		}
		public One_or_more_elementsContext one_or_more_elements() {
			return getRuleContext(One_or_more_elementsContext.class,0);
		}
		public TerminalNode FINALLY() { return getToken(CFDGParser.FINALLY, 0); }
		public CaseBodyContext caseBody() {
			return getRuleContext(CaseBodyContext.class,0);
		}
		public Element_v2clueContext element_v2clue() {
			return getRuleContext(Element_v2clueContext.class,0);
		}
		public Element_loopContext element_loop() {
			return getRuleContext(Element_loopContext.class,0);
		}
		public Element_simpleContext element_simple() {
			return getRuleContext(Element_simpleContext.class,0);
		}
		public IfHeaderContext ifHeader() {
			return getRuleContext(IfHeaderContext.class,0);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitElement(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_element);
		try {
			setState(570);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(534); ((ElementContext)_localctx).r = element_simple();
				 
				        	((ElementContext)_localctx).result =  ((ElementContext)_localctx).r.result; 
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(537); definition();
				 
				        	((ElementContext)_localctx).result =  null;
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(540); ((ElementContext)_localctx).rl = element_loop();
				 
				        	((ElementContext)_localctx).result =  ((ElementContext)_localctx).rl.result; 
				        	driver.popRepContainer(_localctx.result);
				        	if (_localctx.result.getRepType().getType() == 0) {
					        	((ElementContext)_localctx).result =  null; 
				        	}
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(543); ((ElementContext)_localctx).rl = element_loop();
				setState(544); match(FINALLY);

				        	driver.popRepContainer(((ElementContext)_localctx).rl.result);
				        	driver.pushRepContainer(((ASTLoop) ((ElementContext)_localctx).rl.result).getFinallyBody());
				        
				setState(546); one_or_more_elements();

				        	driver.popRepContainer(_localctx.result);
				        	((ElementContext)_localctx).result =  ((ElementContext)_localctx).rl.result; 
				        	if (_localctx.result.getRepType().getType() == 0) {
					        	((ElementContext)_localctx).result =  null; 
				        	}
				        
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(549); ((ElementContext)_localctx).ri = ifHeader();
				setState(550); one_or_more_elements();

				        	((ElementContext)_localctx).result =  ((ElementContext)_localctx).ri.result; 
				        	driver.popRepContainer(_localctx.result);
				        	if (_localctx.result.getRepType().getType() == 0) {
					        	((ElementContext)_localctx).result =  null; 
				        	}
				        
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(553); ((ElementContext)_localctx).rei = ifElseHeader();
				setState(554); one_or_more_elements();

				        	((ElementContext)_localctx).result =  ((ElementContext)_localctx).rei.result; 
				        	driver.popRepContainer(_localctx.result);
				        	if (_localctx.result.getRepType().getType() == 0) {
					        	((ElementContext)_localctx).result =  null; 
				        	}
				        
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(557); ((ElementContext)_localctx).rt = transHeader();
				setState(558); one_or_more_elements();

				        	((ElementContext)_localctx).result =  ((ElementContext)_localctx).rt.result; 
				        	driver.popRepContainer(_localctx.result);
				        	if (_localctx.result.getRepType().getType() == 0) {
					        	((ElementContext)_localctx).result =  null; 
				        	}
				        
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(561); ((ElementContext)_localctx).rs = switchHeader();
				setState(562); match(14);
				setState(563); caseBody();
				setState(564); match(16);

				        	((ElementContext)_localctx).result =  ((ElementContext)_localctx).rs.result; 
							((ElementContext)_localctx).rs.result.unify();
				        	driver.getSwitchStack().pop();
				        
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(567); ((ElementContext)_localctx).element_v2clue = element_v2clue();

				            driver.error("Illegal mixture of old and new elements", (((ElementContext)_localctx).element_v2clue!=null?(((ElementContext)_localctx).element_v2clue.start):null));
				            ((ElementContext)_localctx).result =  null;
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Element_v2clueContext extends ParserRuleContext {
		public TerminalNode USER_RATIONAL() { return getToken(CFDGParser.USER_RATIONAL, 0); }
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public TerminalNode USER_PATHOP() { return getToken(CFDGParser.USER_PATHOP, 0); }
		public Element_v2clueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element_v2clue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterElement_v2clue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitElement_v2clue(this);
		}
	}

	public final Element_v2clueContext element_v2clue() throws RecognitionException {
		Element_v2clueContext _localctx = new Element_v2clueContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_element_v2clue);
		try {
			setState(578);
			switch (_input.LA(1)) {
			case USER_RATIONAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(572); match(USER_RATIONAL);
				setState(573); match(7);
				}
				break;
			case USER_STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(574); match(USER_STRING);
				setState(575); match(14);
				}
				break;
			case USER_PATHOP:
				enterOuterAlt(_localctx, 3);
				{
				setState(576); match(USER_PATHOP);
				setState(577); match(14);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathOp_v2Context extends ParserRuleContext {
		public ASTReplacement result;
		public PathOp_simple_v2Context rp;
		public LoopHeader_v2Context rl;
		public PathOp_v3cluesContext pathOp_v3clues;
		public LoopHeader_v2Context loopHeader_v2() {
			return getRuleContext(LoopHeader_v2Context.class,0);
		}
		public PathOp_simple_v2Context pathOp_simple_v2() {
			return getRuleContext(PathOp_simple_v2Context.class,0);
		}
		public PathOp_v3cluesContext pathOp_v3clues() {
			return getRuleContext(PathOp_v3cluesContext.class,0);
		}
		public One_or_more_pathOp_v2Context one_or_more_pathOp_v2() {
			return getRuleContext(One_or_more_pathOp_v2Context.class,0);
		}
		public PathOp_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathOp_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterPathOp_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitPathOp_v2(this);
		}
	}

	public final PathOp_v2Context pathOp_v2() throws RecognitionException {
		PathOp_v2Context _localctx = new PathOp_v2Context(_ctx, getState());
		enterRule(_localctx, 84, RULE_pathOp_v2);
		try {
			setState(590);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(580); ((PathOp_v2Context)_localctx).rp = pathOp_simple_v2();
				 
				        	((PathOp_v2Context)_localctx).result =  ((PathOp_v2Context)_localctx).rp.result;
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(583); ((PathOp_v2Context)_localctx).rl = loopHeader_v2();
				setState(584); one_or_more_pathOp_v2();
				 
				        	((PathOp_v2Context)_localctx).result =  ((PathOp_v2Context)_localctx).rl.result;
							driver.popRepContainer(_localctx.result);
							if (_localctx.result.getRepType().getType() == 0) {
								((PathOp_v2Context)_localctx).result =  null;			
							}
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(587); ((PathOp_v2Context)_localctx).pathOp_v3clues = pathOp_v3clues();

				            if (driver.getMaybeVersion().equals("CFDG2")) {
				                driver.error("Illegal mixture of old and new elements", (((PathOp_v2Context)_localctx).pathOp_v3clues!=null?(((PathOp_v2Context)_localctx).pathOp_v3clues.start):null));
				            } else {
				                driver.setMaybeVersion("CFDG3");
				            }
				            ((PathOp_v2Context)_localctx).result =  null;
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathOp_v3cluesContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(CFDGParser.IF, 0); }
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public TerminalNode LOOP() { return getToken(CFDGParser.LOOP, 0); }
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public TerminalNode SWITCH() { return getToken(CFDGParser.SWITCH, 0); }
		public TerminalNode USER_PATHOP() { return getToken(CFDGParser.USER_PATHOP, 0); }
		public TerminalNode BECOMES() { return getToken(CFDGParser.BECOMES, 0); }
		public TerminalNode PATH() { return getToken(CFDGParser.PATH, 0); }
		public PathOp_v3cluesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathOp_v3clues; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterPathOp_v3clues(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitPathOp_v3clues(this);
		}
	}

	public final PathOp_v3cluesContext pathOp_v3clues() throws RecognitionException {
		PathOp_v3cluesContext _localctx = new PathOp_v3cluesContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_pathOp_v3clues);
		try {
			setState(605);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(592); match(USER_PATHOP);
				setState(593); match(12);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(594); match(USER_STRING);
				setState(595); match(12);
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(596); match(PATH);
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(597); match(LOOP);
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(598); match(USER_STRING);
				setState(599); match(BECOMES);
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(600); match(MODTYPE);
				setState(601); match(BECOMES);
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(602); match(IF);
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(603); match(MODTYPE);
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(604); match(SWITCH);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Element_loopContext extends ParserRuleContext {
		public ASTLoop result;
		public LoopHeaderContext h;
		public ModificationContext m;
		public One_or_more_elementsContext one_or_more_elements() {
			return getRuleContext(One_or_more_elementsContext.class,0);
		}
		public ModificationContext modification() {
			return getRuleContext(ModificationContext.class,0);
		}
		public LoopHeaderContext loopHeader() {
			return getRuleContext(LoopHeaderContext.class,0);
		}
		public Element_loopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element_loop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterElement_loop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitElement_loop(this);
		}
	}

	public final Element_loopContext element_loop() throws RecognitionException {
		Element_loopContext _localctx = new Element_loopContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_element_loop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(607); ((Element_loopContext)_localctx).h = loopHeader();
			setState(608); ((Element_loopContext)_localctx).m = modification();
			setState(609); one_or_more_elements();

			        	((Element_loopContext)_localctx).result =  ((Element_loopContext)_localctx).h.result;
			        	_localctx.result.setLoopHolder(((Element_loopContext)_localctx).m.result);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Buncha_replacements_v2Context extends ParserRuleContext {
		public Replacement_v2Context r;
		public Buncha_replacements_v2Context buncha_replacements_v2() {
			return getRuleContext(Buncha_replacements_v2Context.class,0);
		}
		public Replacement_v2Context replacement_v2() {
			return getRuleContext(Replacement_v2Context.class,0);
		}
		public Buncha_replacements_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_buncha_replacements_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterBuncha_replacements_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitBuncha_replacements_v2(this);
		}
	}

	public final Buncha_replacements_v2Context buncha_replacements_v2() throws RecognitionException {
		Buncha_replacements_v2Context _localctx = new Buncha_replacements_v2Context(_ctx, getState());
		enterRule(_localctx, 90, RULE_buncha_replacements_v2);
		try {
			setState(617);
			switch (_input.LA(1)) {
			case USER_RATIONAL:
			case USER_STRING:
			case USER_ARRAYNAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(612); ((Buncha_replacements_v2Context)_localctx).r = replacement_v2();
				setState(613); buncha_replacements_v2();

				        	driver.pushRep(((Buncha_replacements_v2Context)_localctx).r.result, false);
				        
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class One_or_more_replacements_v2Context extends ParserRuleContext {
		public Replacement_simple_v2Context r;
		public Buncha_replacements_v2Context buncha_replacements_v2() {
			return getRuleContext(Buncha_replacements_v2Context.class,0);
		}
		public Replacement_simple_v2Context replacement_simple_v2() {
			return getRuleContext(Replacement_simple_v2Context.class,0);
		}
		public One_or_more_replacements_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_one_or_more_replacements_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterOne_or_more_replacements_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitOne_or_more_replacements_v2(this);
		}
	}

	public final One_or_more_replacements_v2Context one_or_more_replacements_v2() throws RecognitionException {
		One_or_more_replacements_v2Context _localctx = new One_or_more_replacements_v2Context(_ctx, getState());
		enterRule(_localctx, 92, RULE_one_or_more_replacements_v2);
		try {
			setState(627);
			switch (_input.LA(1)) {
			case 14:
				enterOuterAlt(_localctx, 1);
				{
				setState(619); match(14);
				setState(620); buncha_replacements_v2();
				setState(621); match(16);
				 
				        
				}
				break;
			case USER_STRING:
			case USER_ARRAYNAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(624); ((One_or_more_replacements_v2Context)_localctx).r = replacement_simple_v2();

				        	driver.pushRep(((One_or_more_replacements_v2Context)_localctx).r.result, false);
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Replacement_simple_v2Context extends ParserRuleContext {
		public ASTReplacement result;
		public ShapeNameContext s;
		public Modification_v2Context m;
		public Modification_v2Context modification_v2() {
			return getRuleContext(Modification_v2Context.class,0);
		}
		public ShapeNameContext shapeName() {
			return getRuleContext(ShapeNameContext.class,0);
		}
		public Replacement_simple_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_replacement_simple_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterReplacement_simple_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitReplacement_simple_v2(this);
		}
	}

	public final Replacement_simple_v2Context replacement_simple_v2() throws RecognitionException {
		Replacement_simple_v2Context _localctx = new Replacement_simple_v2Context(_ctx, getState());
		enterRule(_localctx, 94, RULE_replacement_simple_v2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(629); ((Replacement_simple_v2Context)_localctx).s = shapeName();
			setState(630); ((Replacement_simple_v2Context)_localctx).m = modification_v2();

			        	String name = ((Replacement_simple_v2Context)_localctx).s.result;
			        	ASTModification mod = ((Replacement_simple_v2Context)_localctx).m.result;
			        	ASTRuleSpecifier r = driver.makeRuleSpec(name, null, (((Replacement_simple_v2Context)_localctx).s!=null?(((Replacement_simple_v2Context)_localctx).s.start):null));
			        	((Replacement_simple_v2Context)_localctx).result =  new ASTReplacement(r, mod, (((Replacement_simple_v2Context)_localctx).s!=null?(((Replacement_simple_v2Context)_localctx).s.start):null));
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Replacement_v2Context extends ParserRuleContext {
		public ASTReplacement result;
		public Replacement_simple_v2Context r;
		public LoopHeader_v2Context rl;
		public LoopHeader_v2Context loopHeader_v2() {
			return getRuleContext(LoopHeader_v2Context.class,0);
		}
		public One_or_more_replacements_v2Context one_or_more_replacements_v2() {
			return getRuleContext(One_or_more_replacements_v2Context.class,0);
		}
		public Replacement_simple_v2Context replacement_simple_v2() {
			return getRuleContext(Replacement_simple_v2Context.class,0);
		}
		public Replacement_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_replacement_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterReplacement_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitReplacement_v2(this);
		}
	}

	public final Replacement_v2Context replacement_v2() throws RecognitionException {
		Replacement_v2Context _localctx = new Replacement_v2Context(_ctx, getState());
		enterRule(_localctx, 96, RULE_replacement_v2);
		try {
			setState(640);
			switch (_input.LA(1)) {
			case USER_STRING:
			case USER_ARRAYNAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(633); ((Replacement_v2Context)_localctx).r = replacement_simple_v2();
				 
				        	((Replacement_v2Context)_localctx).result =  ((Replacement_v2Context)_localctx).r.result;
				        
				}
				break;
			case USER_RATIONAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(636); ((Replacement_v2Context)_localctx).rl = loopHeader_v2();
				setState(637); one_or_more_replacements_v2();

				        	((Replacement_v2Context)_localctx).result =  ((Replacement_v2Context)_localctx).rl.result;
							driver.popRepContainer(_localctx.result);
							if (_localctx.result.getRepType().getType() == 0) {
					        	((Replacement_v2Context)_localctx).result =  null;			
							}
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopHeader_v2Context extends ParserRuleContext {
		public ASTLoop result;
		public Token r;
		public Modification_v2Context m;
		public TerminalNode USER_RATIONAL() { return getToken(CFDGParser.USER_RATIONAL, 0); }
		public Modification_v2Context modification_v2() {
			return getRuleContext(Modification_v2Context.class,0);
		}
		public LoopHeader_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopHeader_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterLoopHeader_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitLoopHeader_v2(this);
		}
	}

	public final LoopHeader_v2Context loopHeader_v2() throws RecognitionException {
		LoopHeader_v2Context _localctx = new LoopHeader_v2Context(_ctx, getState());
		enterRule(_localctx, 98, RULE_loopHeader_v2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(642); ((LoopHeader_v2Context)_localctx).r = match(USER_RATIONAL);
			setState(643); match(7);
			 
			        	driver.incSwitchStack();
			        
			setState(645); ((LoopHeader_v2Context)_localctx).m = modification_v2();

			        	ASTExpression count = new ASTReal(Float.parseFloat(((LoopHeader_v2Context)_localctx).r.getText()), ((LoopHeader_v2Context)_localctx).r);
			        	ASTModification mod = ((LoopHeader_v2Context)_localctx).m.result;
			        	driver.decSwitchStack();
			            driver.setMaybeVersion("CFDG2");
			            String dummyvar = "~~inaccessiblevar~~";
			        	((LoopHeader_v2Context)_localctx).result =  new ASTLoop(driver.stringToShape(dummyvar, false, (((LoopHeader_v2Context)_localctx).m!=null?(((LoopHeader_v2Context)_localctx).m.start):null)), dummyvar, count, mod, (((LoopHeader_v2Context)_localctx).m!=null?(((LoopHeader_v2Context)_localctx).m.start):null));
			        	driver.pushRepContainer(_localctx.result.getLoopBody());
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopHeaderContext extends ParserRuleContext {
		public ASTLoop result;
		public Token LOOP;
		public Token v;
		public Exp2Context i;
		public Exp2Context c;
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public TerminalNode LOOP() { return getToken(CFDGParser.LOOP, 0); }
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public TerminalNode BECOMES() { return getToken(CFDGParser.BECOMES, 0); }
		public LoopHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterLoopHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitLoopHeader(this);
		}
	}

	public final LoopHeaderContext loopHeader() throws RecognitionException {
		LoopHeaderContext _localctx = new LoopHeaderContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_loopHeader);
		try {
			setState(664);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(648); ((LoopHeaderContext)_localctx).LOOP = match(LOOP);
				setState(649); ((LoopHeaderContext)_localctx).v = match(USER_STRING);
				setState(650); match(BECOMES);
				setState(651); ((LoopHeaderContext)_localctx).i = exp2();

				        	String var = ((LoopHeaderContext)_localctx).v.getText();
				        	ASTExpression index = ((LoopHeaderContext)_localctx).i.result;
				        	((LoopHeaderContext)_localctx).result =  new ASTLoop(driver.stringToShape(var, false, ((LoopHeaderContext)_localctx).LOOP), var, index, null, ((LoopHeaderContext)_localctx).LOOP);
				        	driver.pushRepContainer(_localctx.result.getLoopBody());
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(654); ((LoopHeaderContext)_localctx).LOOP = match(LOOP);
				setState(655); match(MODTYPE);
				setState(656); match(BECOMES);
				setState(657); ((LoopHeaderContext)_localctx).c = exp2();

				        	ASTExpression index = ((LoopHeaderContext)_localctx).c.result;
				            String dummyvar = "~~inaccessiblevar~~";
				        	((LoopHeaderContext)_localctx).result =  new ASTLoop(driver.stringToShape(dummyvar, false, ((LoopHeaderContext)_localctx).LOOP), dummyvar, index, null, ((LoopHeaderContext)_localctx).LOOP);
				        	driver.pushRepContainer(_localctx.result.getLoopBody());
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(660); ((LoopHeaderContext)_localctx).LOOP = match(LOOP);
				setState(661); ((LoopHeaderContext)_localctx).c = exp2();

				        	ASTExpression count = ((LoopHeaderContext)_localctx).c.result;
				            String dummyvar = "~~inaccessiblevar~~";
				        	((LoopHeaderContext)_localctx).result =  new ASTLoop(driver.stringToShape(dummyvar, false, ((LoopHeaderContext)_localctx).LOOP), dummyvar, count, null, ((LoopHeaderContext)_localctx).LOOP);
				        	driver.pushRepContainer(_localctx.result.getLoopBody());
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfHeaderContext extends ParserRuleContext {
		public ASTIf result;
		public Token IF;
		public Exp2Context e;
		public TerminalNode IF() { return getToken(CFDGParser.IF, 0); }
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public IfHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterIfHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitIfHeader(this);
		}
	}

	public final IfHeaderContext ifHeader() throws RecognitionException {
		IfHeaderContext _localctx = new IfHeaderContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_ifHeader);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(666); ((IfHeaderContext)_localctx).IF = match(IF);
			setState(667); match(12);
			setState(668); ((IfHeaderContext)_localctx).e = exp2();
			setState(669); match(3);

			        	ASTExpression cond = ((IfHeaderContext)_localctx).e.result;
			        	((IfHeaderContext)_localctx).result =  new ASTIf(cond, ((IfHeaderContext)_localctx).IF);
			        	driver.pushRepContainer(_localctx.result.getThenBody());
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfElseHeaderContext extends ParserRuleContext {
		public ASTIf result;
		public IfHeaderContext h;
		public One_or_more_elementsContext one_or_more_elements() {
			return getRuleContext(One_or_more_elementsContext.class,0);
		}
		public TerminalNode ELSE() { return getToken(CFDGParser.ELSE, 0); }
		public IfHeaderContext ifHeader() {
			return getRuleContext(IfHeaderContext.class,0);
		}
		public IfElseHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifElseHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterIfElseHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitIfElseHeader(this);
		}
	}

	public final IfElseHeaderContext ifElseHeader() throws RecognitionException {
		IfElseHeaderContext _localctx = new IfElseHeaderContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_ifElseHeader);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(672); ((IfElseHeaderContext)_localctx).h = ifHeader();
			setState(673); one_or_more_elements();
			setState(674); match(ELSE);

			        	((IfElseHeaderContext)_localctx).result =  ((IfElseHeaderContext)_localctx).h.result;
			        	driver.popRepContainer(_localctx.result);
			        	driver.pushRepContainer(_localctx.result.getElseBody());
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TransHeaderContext extends ParserRuleContext {
		public ASTTransform result;
		public Token t;
		public Exp2Context e;
		public Token CLONE;
		public TerminalNode CLONE() { return getToken(CFDGParser.CLONE, 0); }
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public TransHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterTransHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitTransHeader(this);
		}
	}

	public final TransHeaderContext transHeader() throws RecognitionException {
		TransHeaderContext _localctx = new TransHeaderContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_transHeader);
		try {
			setState(685);
			switch (_input.LA(1)) {
			case MODTYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(677); ((TransHeaderContext)_localctx).t = match(MODTYPE);
				setState(678); ((TransHeaderContext)_localctx).e = exp2();

				        	ASTExpression exp = ((TransHeaderContext)_localctx).e.result;
				        	if (!((TransHeaderContext)_localctx).t.getText().equals(EModType.transform.name())) {
				        		driver.error("Syntax error", ((TransHeaderContext)_localctx).t);
				        	} 
				        	((TransHeaderContext)_localctx).result =  new ASTTransform(exp, ((TransHeaderContext)_localctx).t);
				        	driver.pushRepContainer(_localctx.result.getBody());
				        
				}
				break;
			case CLONE:
				enterOuterAlt(_localctx, 2);
				{
				setState(681); ((TransHeaderContext)_localctx).CLONE = match(CLONE);
				setState(682); ((TransHeaderContext)_localctx).e = exp2();

				        	ASTExpression exp = ((TransHeaderContext)_localctx).e.result;
				        	((TransHeaderContext)_localctx).result =  new ASTTransform(exp, ((TransHeaderContext)_localctx).CLONE);
				        	driver.pushRepContainer(_localctx.result.getBody());
				        	_localctx.result.setClone(true);
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwitchHeaderContext extends ParserRuleContext {
		public ASTSwitch result;
		public Token SWITCH;
		public Exp2Context e;
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public TerminalNode SWITCH() { return getToken(CFDGParser.SWITCH, 0); }
		public SwitchHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterSwitchHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitSwitchHeader(this);
		}
	}

	public final SwitchHeaderContext switchHeader() throws RecognitionException {
		SwitchHeaderContext _localctx = new SwitchHeaderContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_switchHeader);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(687); ((SwitchHeaderContext)_localctx).SWITCH = match(SWITCH);
			setState(688); match(12);
			setState(689); ((SwitchHeaderContext)_localctx).e = exp2();
			setState(690); match(3);

			        	ASTExpression caseVal = ((SwitchHeaderContext)_localctx).e.result;
			            ((SwitchHeaderContext)_localctx).result =  new ASTSwitch(caseVal, ((SwitchHeaderContext)_localctx).SWITCH);
			            driver.getSwitchStack().push(_localctx.result);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CaseHeaderContext extends ParserRuleContext {
		public Integer result;
		public Token CASE;
		public Exp2Context e;
		public Token ELSE;
		public TerminalNode ELSE() { return getToken(CFDGParser.ELSE, 0); }
		public TerminalNode CASE() { return getToken(CFDGParser.CASE, 0); }
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public CaseHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterCaseHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitCaseHeader(this);
		}
	}

	public final CaseHeaderContext caseHeader() throws RecognitionException {
		CaseHeaderContext _localctx = new CaseHeaderContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_caseHeader);
		try {
			setState(701);
			switch (_input.LA(1)) {
			case CASE:
				enterOuterAlt(_localctx, 1);
				{
				setState(693); ((CaseHeaderContext)_localctx).CASE = match(CASE);
				setState(694); ((CaseHeaderContext)_localctx).e = exp2();
				setState(695); match(10);

				        	ASTExpression valExp = ((CaseHeaderContext)_localctx).e.result;
				            double[] val = new double[] { 0.0 };
				            try {
				                if (valExp.evaluate(val, 1) != 1) {
				                    driver.error("Case expression is not a single, numeric expression", ((CaseHeaderContext)_localctx).CASE);
				                } else {
				                	int intval = (int) Math.floor(val[0]);
				                	Map<Integer, ASTRepContainer> caseMap = driver.getSwitchStack().peek().getCaseStatements();
				                	if (caseMap.get(intval) != null) {
				                		driver.error("Case value already in use", ((CaseHeaderContext)_localctx).CASE);
				                		driver.pushRepContainer(caseMap.get(intval));
				                	} else {
				                		ASTRepContainer caseBody = new ASTRepContainer();
				                		driver.pushRepContainer(caseBody);
				                		caseMap.put(intval, caseBody);
				                	}
				                }
				            }
				            catch (DeferUntilRuntimeException e) {
				                driver.error("Case expression is not constant", ((CaseHeaderContext)_localctx).CASE);
				            }
				            ((CaseHeaderContext)_localctx).result =  0;
				        
				}
				break;
			case ELSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(698); ((CaseHeaderContext)_localctx).ELSE = match(ELSE);
				setState(699); match(10);

				            if (!driver.getSwitchStack().peek().getElseBody().getBody().isEmpty()) {
				                driver.error("There can only be one 'else:' clause", ((CaseHeaderContext)_localctx).ELSE);
				            } else {
				                driver.pushRepContainer(driver.getSwitchStack().peek().getElseBody());
				            }
				            ((CaseHeaderContext)_localctx).result =  0;
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Modification_v2Context extends ParserRuleContext {
		public ASTModification result;
		public Token t;
		public Buncha_adjustmentsContext m;
		public Buncha_adjustmentsContext buncha_adjustments() {
			return getRuleContext(Buncha_adjustmentsContext.class,0);
		}
		public Modification_v2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modification_v2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterModification_v2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitModification_v2(this);
		}
	}

	public final Modification_v2Context modification_v2() throws RecognitionException {
		Modification_v2Context _localctx = new Modification_v2Context(_ctx, getState());
		enterRule(_localctx, 112, RULE_modification_v2);
		try {
			setState(713);
			switch (_input.LA(1)) {
			case 14:
				enterOuterAlt(_localctx, 1);
				{
				setState(703); ((Modification_v2Context)_localctx).t = match(14);
				setState(704); ((Modification_v2Context)_localctx).m = buncha_adjustments(0);
				setState(705); match(16);

				        	((Modification_v2Context)_localctx).result =  driver.makeModification(((Modification_v2Context)_localctx).m.result, true, ((Modification_v2Context)_localctx).t);
				        
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 2);
				{
				setState(708); ((Modification_v2Context)_localctx).t = match(8);
				setState(709); ((Modification_v2Context)_localctx).m = buncha_adjustments(0);
				setState(710); match(1);

				        	((Modification_v2Context)_localctx).result =  driver.makeModification(((Modification_v2Context)_localctx).m.result, false, ((Modification_v2Context)_localctx).t);
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModificationContext extends ParserRuleContext {
		public ASTModification result;
		public Token t;
		public Buncha_adjustmentsContext m;
		public Buncha_adjustmentsContext buncha_adjustments() {
			return getRuleContext(Buncha_adjustmentsContext.class,0);
		}
		public ModificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterModification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitModification(this);
		}
	}

	public final ModificationContext modification() throws RecognitionException {
		ModificationContext _localctx = new ModificationContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_modification);
		try {
			setState(727);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(715); ((ModificationContext)_localctx).t = match(8);
				setState(716); ((ModificationContext)_localctx).m = buncha_adjustments(0);
				setState(717); match(1);

				        	((ModificationContext)_localctx).result =  driver.makeModification(((ModificationContext)_localctx).m.result, true, ((ModificationContext)_localctx).t);
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(720); ((ModificationContext)_localctx).t = match(8);
				setState(721); match(8);
				setState(722); ((ModificationContext)_localctx).m = buncha_adjustments(0);
				setState(723); match(1);
				setState(724); match(1);

				        	((ModificationContext)_localctx).result =  driver.makeModification(((ModificationContext)_localctx).m.result, false, ((ModificationContext)_localctx).t);
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Buncha_adjustmentsContext extends ParserRuleContext {
		public ASTModification result;
		public Buncha_adjustmentsContext a2;
		public AdjustmentContext a1;
		public AdjustmentContext adjustment() {
			return getRuleContext(AdjustmentContext.class,0);
		}
		public Buncha_adjustmentsContext buncha_adjustments() {
			return getRuleContext(Buncha_adjustmentsContext.class,0);
		}
		public Buncha_adjustmentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_buncha_adjustments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterBuncha_adjustments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitBuncha_adjustments(this);
		}
	}

	public final Buncha_adjustmentsContext buncha_adjustments() throws RecognitionException {
		return buncha_adjustments(0);
	}

	private Buncha_adjustmentsContext buncha_adjustments(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Buncha_adjustmentsContext _localctx = new Buncha_adjustmentsContext(_ctx, _parentState);
		Buncha_adjustmentsContext _prevctx = _localctx;
		int _startState = 116;
		enterRecursionRule(_localctx, 116, RULE_buncha_adjustments, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{

						((Buncha_adjustmentsContext)_localctx).result =  new ASTModification((Token)null);
			        
			}
			_ctx.stop = _input.LT(-1);
			setState(738);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Buncha_adjustmentsContext(_parentctx, _parentState);
					_localctx.a2 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_buncha_adjustments);
					setState(732);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(733); ((Buncha_adjustmentsContext)_localctx).a1 = adjustment();

					                  	driver.makeModTerm(((Buncha_adjustmentsContext)_localctx).a2.result, ((Buncha_adjustmentsContext)_localctx).a1.result, (((Buncha_adjustmentsContext)_localctx).a1!=null?(((Buncha_adjustmentsContext)_localctx).a1.start):null));
					                  	((Buncha_adjustmentsContext)_localctx).result =  ((Buncha_adjustmentsContext)_localctx).a2.result;
					                  
					}
					} 
				}
				setState(740);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AdjustmentContext extends ParserRuleContext {
		public ASTModTerm result;
		public Token t;
		public ExplistContext el;
		public ExpContext e;
		public Token PARAM;
		public Token p;
		public TerminalNode PARAM() { return getToken(CFDGParser.PARAM, 0); }
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public TerminalNode USER_QSTRING() { return getToken(CFDGParser.USER_QSTRING, 0); }
		public AdjustmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_adjustment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterAdjustment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitAdjustment(this);
		}
	}

	public final AdjustmentContext adjustment() throws RecognitionException {
		AdjustmentContext _localctx = new AdjustmentContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_adjustment);
		try {
			setState(756);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(741); ((AdjustmentContext)_localctx).t = match(MODTYPE);
				setState(742); ((AdjustmentContext)_localctx).el = explist(0);

				        	((AdjustmentContext)_localctx).result =  new ASTModTerm(EModType.modTypeByName(((AdjustmentContext)_localctx).t.getText()), ((AdjustmentContext)_localctx).el.result, ((AdjustmentContext)_localctx).t);
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(745); ((AdjustmentContext)_localctx).t = match(MODTYPE);
				setState(746); ((AdjustmentContext)_localctx).e = exp();
				setState(747); match(17);

				        	EModType type = EModType.modTypeByName(((AdjustmentContext)_localctx).t.getText());
				        	if (type.ordinal() < EModType.hue.ordinal() || type.ordinal() > EModType.alpha.ordinal()) {
				        		driver.error("The target operator can only be applied to color adjustments", ((AdjustmentContext)_localctx).t);
				        		((AdjustmentContext)_localctx).result =  null;
				        	} else {
					        	((AdjustmentContext)_localctx).result =  new ASTModTerm(EModType.modTypeByOrdinal(type.ordinal() + 4), ((AdjustmentContext)_localctx).e.result, ((AdjustmentContext)_localctx).t);
				        	}
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(750); ((AdjustmentContext)_localctx).PARAM = match(PARAM);
				setState(751); ((AdjustmentContext)_localctx).p = match(USER_STRING);

				        	((AdjustmentContext)_localctx).result =  new ASTModTerm(EModType.param, ((AdjustmentContext)_localctx).p.getText(), ((AdjustmentContext)_localctx).PARAM);
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(753); ((AdjustmentContext)_localctx).PARAM = match(PARAM);
				setState(754); ((AdjustmentContext)_localctx).p = match(USER_QSTRING);

				        	((AdjustmentContext)_localctx).result =  new ASTModTerm(EModType.param, ((AdjustmentContext)_localctx).p.getText(), ((AdjustmentContext)_localctx).PARAM);
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LetHeaderContext extends ParserRuleContext {
		public ASTRepContainer result;
		public TerminalNode LET() { return getToken(CFDGParser.LET, 0); }
		public LetHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterLetHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitLetHeader(this);
		}
	}

	public final LetHeaderContext letHeader() throws RecognitionException {
		LetHeaderContext _localctx = new LetHeaderContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_letHeader);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(758); match(LET);

			            ((LetHeaderContext)_localctx).result =  new ASTRepContainer();
			            driver.pushRepContainer(_localctx.result);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LetBodyContext extends ParserRuleContext {
		public ASTExpression result;
		public Exp2Context e;
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public LetVariablesContext letVariables() {
			return getRuleContext(LetVariablesContext.class,0);
		}
		public LetBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterLetBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitLetBody(this);
		}
	}

	public final LetBodyContext letBody() throws RecognitionException {
		LetBodyContext _localctx = new LetBodyContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_letBody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(761); match(12);
			setState(762); letVariables(0);
			setState(763); match(13);
			setState(764); ((LetBodyContext)_localctx).e = exp2();
			setState(765); match(3);

			            ((LetBodyContext)_localctx).result =  ((LetBodyContext)_localctx).e.result;
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LetVariablesContext extends ParserRuleContext {
		public LetVariableContext letVariable() {
			return getRuleContext(LetVariableContext.class,0);
		}
		public LetVariablesContext letVariables() {
			return getRuleContext(LetVariablesContext.class,0);
		}
		public LetVariablesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letVariables; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterLetVariables(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitLetVariables(this);
		}
	}

	public final LetVariablesContext letVariables() throws RecognitionException {
		return letVariables(0);
	}

	private LetVariablesContext letVariables(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LetVariablesContext _localctx = new LetVariablesContext(_ctx, _parentState);
		LetVariablesContext _prevctx = _localctx;
		int _startState = 124;
		enterRecursionRule(_localctx, 124, RULE_letVariables, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(769); letVariable();
			}
			_ctx.stop = _input.LT(-1);
			setState(776);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LetVariablesContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_letVariables);
					setState(771);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(772); match(13);
					setState(773); letVariable();
					}
					} 
				}
				setState(778);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LetVariableContext extends ParserRuleContext {
		public ASTDefine result;
		public DefinitionContext r;
		public DefinitionContext definition() {
			return getRuleContext(DefinitionContext.class,0);
		}
		public LetVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterLetVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitLetVariable(this);
		}
	}

	public final LetVariableContext letVariable() throws RecognitionException {
		LetVariableContext _localctx = new LetVariableContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_letVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(779); ((LetVariableContext)_localctx).r = definition();

			            driver.pushRep(((LetVariableContext)_localctx).r.result, false);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExplistContext extends ParserRuleContext {
		public ASTExpression result;
		public ExplistContext e2;
		public ExpContext e;
		public ExpContext e1;
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ExplistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterExplist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitExplist(this);
		}
	}

	public final ExplistContext explist() throws RecognitionException {
		return explist(0);
	}

	private ExplistContext explist(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExplistContext _localctx = new ExplistContext(_ctx, _parentState);
		ExplistContext _prevctx = _localctx;
		int _startState = 128;
		enterRecursionRule(_localctx, 128, RULE_explist, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(783); ((ExplistContext)_localctx).e = exp();
			 
			        	((ExplistContext)_localctx).result =  ((ExplistContext)_localctx).e.result;
			        
			}
			_ctx.stop = _input.LT(-1);
			setState(792);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExplistContext(_parentctx, _parentState);
					_localctx.e2 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_explist);
					setState(786);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(787); ((ExplistContext)_localctx).e1 = exp();

					                  	((ExplistContext)_localctx).result =  ((ExplistContext)_localctx).e2.result.append(((ExplistContext)_localctx).e1.result);
					                  
					}
					} 
				}
				setState(794);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ArglistContext extends ParserRuleContext {
		public ASTExpression result;
		public ExplistContext e2;
		public Exp3Context e1;
		public Exp3Context e;
		public Exp3Context exp3() {
			return getRuleContext(Exp3Context.class,0);
		}
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public ArglistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arglist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterArglist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitArglist(this);
		}
	}

	public final ArglistContext arglist() throws RecognitionException {
		ArglistContext _localctx = new ArglistContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_arglist);
		try {
			setState(802);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(795); ((ArglistContext)_localctx).e2 = explist(0);
				setState(796); ((ArglistContext)_localctx).e1 = exp3();

				        	((ArglistContext)_localctx).result =  ((ArglistContext)_localctx).e2.result.append(((ArglistContext)_localctx).e1.result);
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(799); ((ArglistContext)_localctx).e = exp3();
				 
				        	((ArglistContext)_localctx).result =  new ASTCons((((ArglistContext)_localctx).e!=null?(((ArglistContext)_localctx).e.start):null), new ASTParen(((ArglistContext)_localctx).e.result, (((ArglistContext)_localctx).e!=null?(((ArglistContext)_localctx).e.start):null)));
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public ASTExpression result;
		public Token n;
		public Token CF_INFINITY;
		public Token t;
		public Exp2Context x;
		public ExpfuncContext f;
		public ExpContext e;
		public ExpContext r;
		public TerminalNode USER_RATIONAL() { return getToken(CFDGParser.USER_RATIONAL, 0); }
		public TerminalNode RANGE() { return getToken(CFDGParser.RANGE, 0); }
		public TerminalNode PLUSMINUS() { return getToken(CFDGParser.PLUSMINUS, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpfuncContext expfunc() {
			return getRuleContext(ExpfuncContext.class,0);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public TerminalNode CF_INFINITY() { return getToken(CFDGParser.CF_INFINITY, 0); }
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitExp(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_exp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(824);
			switch (_input.LA(1)) {
			case USER_RATIONAL:
				{
				setState(804); ((ExpContext)_localctx).n = match(USER_RATIONAL);
				 
							((ExpContext)_localctx).result =  new ASTReal(Float.parseFloat(((ExpContext)_localctx).n.getText()), ((ExpContext)_localctx).n); 
				        
				}
				break;
			case CF_INFINITY:
				{
				setState(806); ((ExpContext)_localctx).CF_INFINITY = match(CF_INFINITY);
				 
							((ExpContext)_localctx).result =  new ASTReal(Float.MAX_VALUE, ((ExpContext)_localctx).CF_INFINITY); 
				        
				}
				break;
			case 12:
				{
				setState(808); ((ExpContext)_localctx).t = match(12);
				setState(809); ((ExpContext)_localctx).x = exp2();
				setState(810); match(3);
				 
							((ExpContext)_localctx).result =  new ASTParen(((ExpContext)_localctx).x.result, ((ExpContext)_localctx).t); 
				        
				}
				break;
			case IF:
			case LET:
			case USER_STRING:
			case USER_ARRAYNAME:
				{
				setState(813); ((ExpContext)_localctx).f = expfunc();
				 
							((ExpContext)_localctx).result =  ((ExpContext)_localctx).f.result; 
				        
				}
				break;
			case 9:
				{
				setState(816); ((ExpContext)_localctx).t = match(9);
				setState(817); ((ExpContext)_localctx).e = exp();
				 
							((ExpContext)_localctx).result =  new ASTOperator('N', ((ExpContext)_localctx).e.result, ((ExpContext)_localctx).t); 
				        
				}
				break;
			case 6:
				{
				setState(820); ((ExpContext)_localctx).t = match(6);
				setState(821); ((ExpContext)_localctx).e = exp();
				 
							((ExpContext)_localctx).result =  new ASTOperator('P', ((ExpContext)_localctx).e.result, ((ExpContext)_localctx).t); 
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(834);
			switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
			case 1:
				{
				setState(826); match(RANGE);
				setState(827); ((ExpContext)_localctx).r = exp();

				        	ASTExpression pair = _localctx.result.append(((ExpContext)_localctx).r.result);
				        	((ExpContext)_localctx).result =  new ASTFunction("rand", pair, driver.getSeed(), _localctx.result.getLocation());
				        
				}
				break;

			case 2:
				{
				setState(830); match(PLUSMINUS);
				setState(831); ((ExpContext)_localctx).r = exp();

				        	ASTExpression pair = _localctx.result.append(((ExpContext)_localctx).r.result);
				        	((ExpContext)_localctx).result =  new ASTFunction("rand+/-", pair, driver.getSeed(), _localctx.result.getLocation());
				        
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Exp2Context extends ParserRuleContext {
		public ASTExpression result;
		public Token n;
		public Token CF_INFINITY;
		public Exp2funcContext f;
		public Token t;
		public Exp2Context e;
		public ModificationContext m;
		public Exp2Context r;
		public TerminalNode USER_RATIONAL() { return getToken(CFDGParser.USER_RATIONAL, 0); }
		public TerminalNode RANGE() { return getToken(CFDGParser.RANGE, 0); }
		public TerminalNode XOR() { return getToken(CFDGParser.XOR, 0); }
		public TerminalNode AND() { return getToken(CFDGParser.AND, 0); }
		public TerminalNode OR() { return getToken(CFDGParser.OR, 0); }
		public ModificationContext modification() {
			return getRuleContext(ModificationContext.class,0);
		}
		public TerminalNode LE() { return getToken(CFDGParser.LE, 0); }
		public Exp2Context exp2(int i) {
			return getRuleContext(Exp2Context.class,i);
		}
		public TerminalNode GE() { return getToken(CFDGParser.GE, 0); }
		public TerminalNode EQ() { return getToken(CFDGParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(CFDGParser.NEQ, 0); }
		public TerminalNode PLUSMINUS() { return getToken(CFDGParser.PLUSMINUS, 0); }
		public Exp2funcContext exp2func() {
			return getRuleContext(Exp2funcContext.class,0);
		}
		public TerminalNode LT() { return getToken(CFDGParser.LT, 0); }
		public TerminalNode NOT() { return getToken(CFDGParser.NOT, 0); }
		public List<Exp2Context> exp2() {
			return getRuleContexts(Exp2Context.class);
		}
		public TerminalNode GT() { return getToken(CFDGParser.GT, 0); }
		public TerminalNode CF_INFINITY() { return getToken(CFDGParser.CF_INFINITY, 0); }
		public Exp2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterExp2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitExp2(this);
		}
	}

	public final Exp2Context exp2() throws RecognitionException {
		Exp2Context _localctx = new Exp2Context(_ctx, getState());
		enterRule(_localctx, 134, RULE_exp2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(863);
			switch (_input.LA(1)) {
			case USER_RATIONAL:
				{
				setState(836); ((Exp2Context)_localctx).n = match(USER_RATIONAL);
				 
				        	((Exp2Context)_localctx).result =  new ASTReal(Float.parseFloat(((Exp2Context)_localctx).n.getText()), ((Exp2Context)_localctx).n); 
				        
				}
				break;
			case CF_INFINITY:
				{
				setState(838); ((Exp2Context)_localctx).CF_INFINITY = match(CF_INFINITY);
				 
							((Exp2Context)_localctx).result =  new ASTReal(Float.MAX_VALUE, ((Exp2Context)_localctx).CF_INFINITY); 
				        
				}
				break;
			case IF:
			case LET:
			case USER_STRING:
			case USER_ARRAYNAME:
				{
				setState(840); ((Exp2Context)_localctx).f = exp2func();
				 
				        	((Exp2Context)_localctx).result =  ((Exp2Context)_localctx).f.result; 
				        
				}
				break;
			case 9:
				{
				setState(843); ((Exp2Context)_localctx).t = match(9);
				setState(844); ((Exp2Context)_localctx).e = exp2();
				 
							((Exp2Context)_localctx).result =  new ASTOperator('N', ((Exp2Context)_localctx).e.result, ((Exp2Context)_localctx).t); 
				        
				}
				break;
			case 6:
				{
				setState(847); ((Exp2Context)_localctx).t = match(6);
				setState(848); ((Exp2Context)_localctx).e = exp2();
				 
							((Exp2Context)_localctx).result =  new ASTOperator('P', ((Exp2Context)_localctx).e.result, ((Exp2Context)_localctx).t); 
				        
				}
				break;
			case NOT:
				{
				setState(851); ((Exp2Context)_localctx).t = match(NOT);
				setState(852); ((Exp2Context)_localctx).e = exp2();
				 
							((Exp2Context)_localctx).result =  new ASTOperator('!', ((Exp2Context)_localctx).e.result, ((Exp2Context)_localctx).t); 
				        
				}
				break;
			case 12:
				{
				setState(855); ((Exp2Context)_localctx).t = match(12);
				setState(856); ((Exp2Context)_localctx).e = exp2();
				setState(857); match(3);
				 
							((Exp2Context)_localctx).result =  new ASTParen(((Exp2Context)_localctx).e.result, ((Exp2Context)_localctx).t); 
				        
				}
				break;
			case 8:
				{
				setState(860); ((Exp2Context)_localctx).m = modification();

				        	((Exp2Context)_localctx).result =  ((Exp2Context)_localctx).m.result;
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(937);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				{
				setState(865); match(4);
				setState(866); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTCons(_localctx.result.getLocation(), _localctx.result, ((Exp2Context)_localctx).r.result);
				        
				}
				break;

			case 2:
				{
				setState(869); match(6);
				setState(870); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('+', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 3:
				{
				setState(873); match(9);
				setState(874); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('-', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 4:
				{
				setState(877); match(5);
				setState(878); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('_', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 5:
				{
				setState(881); match(7);
				setState(882); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('*', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 6:
				{
				setState(885); match(15);
				setState(886); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('/', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 7:
				{
				setState(889); match(2);
				setState(890); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('^', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 8:
				{
				setState(893); match(LT);
				setState(894); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('<', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 9:
				{
				setState(897); match(GT);
				setState(898); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('>', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 10:
				{
				setState(901); match(LE);
				setState(902); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('L', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 11:
				{
				setState(905); match(GE);
				setState(906); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('G', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 12:
				{
				setState(909); match(EQ);
				setState(910); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('=', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 13:
				{
				setState(913); match(NEQ);
				setState(914); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('n', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 14:
				{
				setState(917); match(AND);
				setState(918); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('&', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 15:
				{
				setState(921); match(OR);
				setState(922); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('|', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 16:
				{
				setState(925); match(XOR);
				setState(926); ((Exp2Context)_localctx).r = exp2();

				        	((Exp2Context)_localctx).result =  new ASTOperator('X', _localctx.result, ((Exp2Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 17:
				{
				setState(929); match(RANGE);
				setState(930); ((Exp2Context)_localctx).r = exp2();

				        	ASTExpression pair = _localctx.result.append(((Exp2Context)_localctx).r.result);
				        	((Exp2Context)_localctx).result =  new ASTFunction("rand", pair, driver.getSeed(), _localctx.result.getLocation());
				        
				}
				break;

			case 18:
				{
				setState(933); match(PLUSMINUS);
				setState(934); ((Exp2Context)_localctx).r = exp2();

				        	ASTExpression pair = _localctx.result.append(((Exp2Context)_localctx).r.result);
				        	((Exp2Context)_localctx).result =  new ASTFunction("rand+/-", pair, driver.getSeed(), _localctx.result.getLocation());
				        
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Exp3Context extends ParserRuleContext {
		public ASTExpression result;
		public Token n;
		public Token CF_INFINITY;
		public Exp2funcContext f;
		public Token t;
		public Exp3Context e;
		public Exp2Context x;
		public ModificationContext m;
		public Exp3Context r;
		public TerminalNode USER_RATIONAL() { return getToken(CFDGParser.USER_RATIONAL, 0); }
		public TerminalNode RANGE() { return getToken(CFDGParser.RANGE, 0); }
		public TerminalNode XOR() { return getToken(CFDGParser.XOR, 0); }
		public TerminalNode AND() { return getToken(CFDGParser.AND, 0); }
		public TerminalNode OR() { return getToken(CFDGParser.OR, 0); }
		public ModificationContext modification() {
			return getRuleContext(ModificationContext.class,0);
		}
		public TerminalNode LE() { return getToken(CFDGParser.LE, 0); }
		public TerminalNode EQ() { return getToken(CFDGParser.EQ, 0); }
		public TerminalNode GE() { return getToken(CFDGParser.GE, 0); }
		public TerminalNode NEQ() { return getToken(CFDGParser.NEQ, 0); }
		public List<Exp3Context> exp3() {
			return getRuleContexts(Exp3Context.class);
		}
		public TerminalNode PLUSMINUS() { return getToken(CFDGParser.PLUSMINUS, 0); }
		public Exp2funcContext exp2func() {
			return getRuleContext(Exp2funcContext.class,0);
		}
		public TerminalNode LT() { return getToken(CFDGParser.LT, 0); }
		public TerminalNode NOT() { return getToken(CFDGParser.NOT, 0); }
		public Exp3Context exp3(int i) {
			return getRuleContext(Exp3Context.class,i);
		}
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public TerminalNode GT() { return getToken(CFDGParser.GT, 0); }
		public TerminalNode CF_INFINITY() { return getToken(CFDGParser.CF_INFINITY, 0); }
		public Exp3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterExp3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitExp3(this);
		}
	}

	public final Exp3Context exp3() throws RecognitionException {
		Exp3Context _localctx = new Exp3Context(_ctx, getState());
		enterRule(_localctx, 136, RULE_exp3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(966);
			switch (_input.LA(1)) {
			case USER_RATIONAL:
				{
				setState(939); ((Exp3Context)_localctx).n = match(USER_RATIONAL);
				 
				        	((Exp3Context)_localctx).result =  new ASTReal(Float.parseFloat(((Exp3Context)_localctx).n.getText()), ((Exp3Context)_localctx).n); 
				        
				}
				break;
			case CF_INFINITY:
				{
				setState(941); ((Exp3Context)_localctx).CF_INFINITY = match(CF_INFINITY);
				 
							((Exp3Context)_localctx).result =  new ASTReal(Float.MAX_VALUE, ((Exp3Context)_localctx).CF_INFINITY); 
				        
				}
				break;
			case IF:
			case LET:
			case USER_STRING:
			case USER_ARRAYNAME:
				{
				setState(943); ((Exp3Context)_localctx).f = exp2func();
				 
				        	((Exp3Context)_localctx).result =  ((Exp3Context)_localctx).f.result;
				        
				}
				break;
			case 9:
				{
				setState(946); ((Exp3Context)_localctx).t = match(9);
				setState(947); ((Exp3Context)_localctx).e = exp3();
				 
							((Exp3Context)_localctx).result =  new ASTOperator('N', ((Exp3Context)_localctx).e.result, ((Exp3Context)_localctx).t); 
				        
				}
				break;
			case 6:
				{
				setState(950); ((Exp3Context)_localctx).t = match(6);
				setState(951); ((Exp3Context)_localctx).e = exp3();
				 
							((Exp3Context)_localctx).result =  new ASTOperator('P', ((Exp3Context)_localctx).e.result, ((Exp3Context)_localctx).t); 
				        
				}
				break;
			case NOT:
				{
				setState(954); ((Exp3Context)_localctx).t = match(NOT);
				setState(955); ((Exp3Context)_localctx).e = exp3();
				 
							((Exp3Context)_localctx).result =  new ASTOperator('!', ((Exp3Context)_localctx).e.result, ((Exp3Context)_localctx).t); 
				        
				}
				break;
			case 12:
				{
				setState(958); ((Exp3Context)_localctx).t = match(12);
				setState(959); ((Exp3Context)_localctx).x = exp2();
				setState(960); match(3);
				 
							((Exp3Context)_localctx).result =  new ASTParen(((Exp3Context)_localctx).x.result, ((Exp3Context)_localctx).t); 
				        
				}
				break;
			case 8:
				{
				setState(963); ((Exp3Context)_localctx).m = modification();

				        	((Exp3Context)_localctx).result =  ((Exp3Context)_localctx).m.result;
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1036);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				setState(968); match(6);
				setState(969); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('+', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 2:
				{
				setState(972); match(9);
				setState(973); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('-', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 3:
				{
				setState(976); match(5);
				setState(977); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('_', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 4:
				{
				setState(980); match(7);
				setState(981); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('*', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 5:
				{
				setState(984); match(15);
				setState(985); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('/', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 6:
				{
				setState(988); match(2);
				setState(989); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('^', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 7:
				{
				setState(992); match(LT);
				setState(993); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('<', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 8:
				{
				setState(996); match(GT);
				setState(997); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('>', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 9:
				{
				setState(1000); match(LE);
				setState(1001); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('L', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 10:
				{
				setState(1004); match(GE);
				setState(1005); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('G', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 11:
				{
				setState(1008); match(EQ);
				setState(1009); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('=', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 12:
				{
				setState(1012); match(NEQ);
				setState(1013); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('n', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 13:
				{
				setState(1016); match(AND);
				setState(1017); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('&', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 14:
				{
				setState(1020); match(OR);
				setState(1021); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('|', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 15:
				{
				setState(1024); match(XOR);
				setState(1025); ((Exp3Context)_localctx).r = exp3();

				        	((Exp3Context)_localctx).result =  new ASTOperator('X', _localctx.result, ((Exp3Context)_localctx).r.result, _localctx.result.getLocation());
				        
				}
				break;

			case 16:
				{
				setState(1028); match(RANGE);
				setState(1029); ((Exp3Context)_localctx).r = exp3();

				        	ASTExpression pair = _localctx.result.append(((Exp3Context)_localctx).r.result);
				        	((Exp3Context)_localctx).result =  new ASTFunction("rand", pair, driver.getSeed(), _localctx.result.getLocation());
				        
				}
				break;

			case 17:
				{
				setState(1032); match(PLUSMINUS);
				setState(1033); ((Exp3Context)_localctx).r = exp3();

				        	ASTExpression pair = _localctx.result.append(((Exp3Context)_localctx).r.result);
				        	((Exp3Context)_localctx).result =  new ASTFunction("rand+/-", pair, driver.getSeed(), _localctx.result.getLocation());
				        
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpfuncContext extends ParserRuleContext {
		public ASTExpression result;
		public Token f;
		public ArglistContext a;
		public Exp2Context e;
		public Token IF;
		public LetHeaderContext h;
		public LetBodyContext b;
		public Token v;
		public LetHeaderContext letHeader() {
			return getRuleContext(LetHeaderContext.class,0);
		}
		public TerminalNode IF() { return getToken(CFDGParser.IF, 0); }
		public LetBodyContext letBody() {
			return getRuleContext(LetBodyContext.class,0);
		}
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public TerminalNode USER_ARRAYNAME() { return getToken(CFDGParser.USER_ARRAYNAME, 0); }
		public ArglistContext arglist() {
			return getRuleContext(ArglistContext.class,0);
		}
		public ExpfuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expfunc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterExpfunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitExpfunc(this);
		}
	}

	public final ExpfuncContext expfunc() throws RecognitionException {
		ExpfuncContext _localctx = new ExpfuncContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_expfunc);
		try {
			setState(1066);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1038); ((ExpfuncContext)_localctx).f = match(USER_STRING);
				setState(1039); match(12);
				setState(1040); match(3);
				 
				        	String func = ((ExpfuncContext)_localctx).f.getText();
				        	((ExpfuncContext)_localctx).result =  driver.makeFunction(func, null, ((ExpfuncContext)_localctx).f);
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1042); ((ExpfuncContext)_localctx).f = match(USER_STRING);
				setState(1043); match(12);
				setState(1044); ((ExpfuncContext)_localctx).a = arglist();
				setState(1045); match(3);
				 
				        	String func = ((ExpfuncContext)_localctx).f.getText();
				        	ASTExpression args = ((ExpfuncContext)_localctx).a.result;
				        	((ExpfuncContext)_localctx).result =  driver.makeFunction(func, args, ((ExpfuncContext)_localctx).f);
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1048); ((ExpfuncContext)_localctx).f = match(USER_ARRAYNAME);
				setState(1049); match(12);
				setState(1050); ((ExpfuncContext)_localctx).e = exp2();
				setState(1051); match(3);
				 
				        	String func = ((ExpfuncContext)_localctx).f.getText();
				        	ASTExpression args = ((ExpfuncContext)_localctx).e.result;
				        	((ExpfuncContext)_localctx).result =  driver.makeArray(func, args, ((ExpfuncContext)_localctx).f);
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1054); ((ExpfuncContext)_localctx).IF = match(IF);
				setState(1055); match(12);
				setState(1056); ((ExpfuncContext)_localctx).e = exp2();
				setState(1057); match(3);
				 
				        	ASTExpression args = ((ExpfuncContext)_localctx).e.result;
				        	((ExpfuncContext)_localctx).result =  driver.makeFunction("if", args, ((ExpfuncContext)_localctx).IF);
				        
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1060); ((ExpfuncContext)_localctx).h = letHeader();
				setState(1061); ((ExpfuncContext)_localctx).b = letBody();

				        	driver.popRepContainer(null);
				        	((ExpfuncContext)_localctx).result =  driver.makeLet(((ExpfuncContext)_localctx).h.result, ((ExpfuncContext)_localctx).b.result, (((ExpfuncContext)_localctx).h!=null?(((ExpfuncContext)_localctx).h.start):null));
				        
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1064); ((ExpfuncContext)_localctx).v = match(USER_STRING);
				 
				        	String var = ((ExpfuncContext)_localctx).v.getText();
				        	((ExpfuncContext)_localctx).result =  driver.makeVariable(var, ((ExpfuncContext)_localctx).v);
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Exp2funcContext extends ParserRuleContext {
		public ASTExpression result;
		public Token f;
		public ArglistContext a;
		public Exp2Context e;
		public Token IF;
		public LetHeaderContext h;
		public LetBodyContext b;
		public Token v;
		public LetHeaderContext letHeader() {
			return getRuleContext(LetHeaderContext.class,0);
		}
		public TerminalNode IF() { return getToken(CFDGParser.IF, 0); }
		public LetBodyContext letBody() {
			return getRuleContext(LetBodyContext.class,0);
		}
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public TerminalNode BECOMES() { return getToken(CFDGParser.BECOMES, 0); }
		public TerminalNode USER_ARRAYNAME() { return getToken(CFDGParser.USER_ARRAYNAME, 0); }
		public ArglistContext arglist() {
			return getRuleContext(ArglistContext.class,0);
		}
		public Exp2funcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp2func; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterExp2func(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitExp2func(this);
		}
	}

	public final Exp2funcContext exp2func() throws RecognitionException {
		Exp2funcContext _localctx = new Exp2funcContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_exp2func);
		try {
			setState(1101);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1068); ((Exp2funcContext)_localctx).f = match(USER_STRING);
				setState(1069); match(12);
				setState(1070); match(3);
				 
				        	String func = ((Exp2funcContext)_localctx).f.getText();
				        	((Exp2funcContext)_localctx).result =  driver.makeFunction(func, null, ((Exp2funcContext)_localctx).f);
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1072); ((Exp2funcContext)_localctx).f = match(USER_STRING);
				setState(1073); match(12);
				setState(1074); ((Exp2funcContext)_localctx).a = arglist();
				setState(1075); match(3);
				 
				        	String func = ((Exp2funcContext)_localctx).f.getText();
				        	ASTExpression args = ((Exp2funcContext)_localctx).a.result;
				        	((Exp2funcContext)_localctx).result =  driver.makeFunction(func, args, ((Exp2funcContext)_localctx).f);
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1078); ((Exp2funcContext)_localctx).f = match(USER_ARRAYNAME);
				setState(1079); match(12);
				setState(1080); ((Exp2funcContext)_localctx).e = exp2();
				setState(1081); match(3);
				 
				        	String func = ((Exp2funcContext)_localctx).f.getText();
				        	ASTExpression args = ((Exp2funcContext)_localctx).e.result;
				        	((Exp2funcContext)_localctx).result =  driver.makeArray(func, args, ((Exp2funcContext)_localctx).f);
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1084); ((Exp2funcContext)_localctx).IF = match(IF);
				setState(1085); match(12);
				setState(1086); ((Exp2funcContext)_localctx).e = exp2();
				setState(1087); match(3);
				 
				        	ASTExpression args = ((Exp2funcContext)_localctx).e.result;
				        	((Exp2funcContext)_localctx).result =  driver.makeFunction("if", args, ((Exp2funcContext)_localctx).IF);
				        
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1090); ((Exp2funcContext)_localctx).f = match(USER_STRING);
				setState(1091); match(12);
				setState(1092); match(BECOMES);
				setState(1093); match(3);
				 
				        	String func = ((Exp2funcContext)_localctx).f.getText();
				        	ASTExpression args = new ASTExpression(false, false, EExpType.ReuseType, ((Exp2funcContext)_localctx).f);
				        	((Exp2funcContext)_localctx).result =  driver.makeArray(func, args, ((Exp2funcContext)_localctx).f);
				        
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1095); ((Exp2funcContext)_localctx).h = letHeader();
				setState(1096); ((Exp2funcContext)_localctx).b = letBody();

				        	driver.popRepContainer(null);
				        	((Exp2funcContext)_localctx).result =  driver.makeLet(((Exp2funcContext)_localctx).h.result, ((Exp2funcContext)_localctx).b.result, (((Exp2funcContext)_localctx).h!=null?(((Exp2funcContext)_localctx).h.start):null));
				        
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1099); ((Exp2funcContext)_localctx).v = match(USER_STRING);
				 
				        	String var = ((Exp2funcContext)_localctx).v.getText();
				        	((Exp2funcContext)_localctx).result =  driver.makeVariable(var, ((Exp2funcContext)_localctx).v);
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShapeNameContext extends ParserRuleContext {
		public String result;
		public Token r;
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public TerminalNode USER_ARRAYNAME() { return getToken(CFDGParser.USER_ARRAYNAME, 0); }
		public ShapeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shapeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterShapeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitShapeName(this);
		}
	}

	public final ShapeNameContext shapeName() throws RecognitionException {
		ShapeNameContext _localctx = new ShapeNameContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_shapeName);
		try {
			setState(1107);
			switch (_input.LA(1)) {
			case USER_STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(1103); ((ShapeNameContext)_localctx).r = match(USER_STRING);
				 
				        	((ShapeNameContext)_localctx).result =  ((ShapeNameContext)_localctx).r.getText();
				        
				}
				break;
			case USER_ARRAYNAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(1105); ((ShapeNameContext)_localctx).r = match(USER_ARRAYNAME);
				 
				        	((ShapeNameContext)_localctx).result =  ((ShapeNameContext)_localctx).r.getText();
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Global_definitionContext extends ParserRuleContext {
		public ASTDefine result;
		public Global_definition_headerContext r;
		public Exp2Context e;
		public Global_definition_headerContext global_definition_header() {
			return getRuleContext(Global_definition_headerContext.class,0);
		}
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public Global_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_global_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterGlobal_definition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitGlobal_definition(this);
		}
	}

	public final Global_definitionContext global_definition() throws RecognitionException {
		Global_definitionContext _localctx = new Global_definitionContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_global_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1109); ((Global_definitionContext)_localctx).r = global_definition_header();
			setState(1110); ((Global_definitionContext)_localctx).e = exp2();

			            ASTDefine var = ((Global_definitionContext)_localctx).r.result;
			            ASTExpression exp = ((Global_definitionContext)_localctx).e.result;
			            if (var != null) {
			                switch (var.getDefineType()) {
			                    case StackDefine:
			                        if (exp instanceof ASTModification) {
			                        	ASTModification mod = (ASTModification)exp;
			                            var.getChildChange().grab(mod); // emptied ASTmod gets deleted
			                        } else {
			                            var.setExp(exp);
			                        }
			                        break;
			                    case LetDefine:
			                        assert(false);
			                        break;
			                    case FunctionDefine:
			                        driver.popRepContainer(null);
			                        driver.getParamDecls().getParameters().clear();
			                        driver.getParamDecls().setStackCount(0);
			                        // fall through
			                    default:
			                        var.setExp(exp);
			                        break;
			                }
			                ((Global_definitionContext)_localctx).result =  var;
			            } else {
			                ((Global_definitionContext)_localctx).result =  null;
			            }
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Function_definition_headerContext extends ParserRuleContext {
		public ASTDefine result;
		public Token SHAPE;
		public Token f;
		public Function_parameter_listContext p;
		public Token t;
		public Token MODTYPE;
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public Function_parameter_listContext function_parameter_list() {
			return getRuleContext(Function_parameter_listContext.class,0);
		}
		public TerminalNode SHAPE() { return getToken(CFDGParser.SHAPE, 0); }
		public TerminalNode USER_STRING(int i) {
			return getToken(CFDGParser.USER_STRING, i);
		}
		public List<TerminalNode> USER_STRING() { return getTokens(CFDGParser.USER_STRING); }
		public TerminalNode BECOMES() { return getToken(CFDGParser.BECOMES, 0); }
		public Function_definition_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_definition_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterFunction_definition_header(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitFunction_definition_header(this);
		}
	}

	public final Function_definition_headerContext function_definition_header() throws RecognitionException {
		Function_definition_headerContext _localctx = new Function_definition_headerContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_function_definition_header);
		try {
			setState(1147);
			switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1113); ((Function_definition_headerContext)_localctx).SHAPE = match(SHAPE);
				setState(1114); ((Function_definition_headerContext)_localctx).f = match(USER_STRING);
				setState(1115); ((Function_definition_headerContext)_localctx).p = function_parameter_list();
				setState(1116); match(BECOMES);

				        	String name = ((Function_definition_headerContext)_localctx).f.getText();
				            ((Function_definition_headerContext)_localctx).result =  driver.makeDefinition(name, true, ((Function_definition_headerContext)_localctx).SHAPE);
				            if (_localctx.result != null) {
				                _localctx.result.setExpType(EExpType.RuleType);
				                _localctx.result.setTupleSize(1);
				            }
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1119); ((Function_definition_headerContext)_localctx).f = match(USER_STRING);
				setState(1120); ((Function_definition_headerContext)_localctx).p = function_parameter_list();
				setState(1121); match(BECOMES);

				        	String name = ((Function_definition_headerContext)_localctx).f.getText();
				            ((Function_definition_headerContext)_localctx).result =  driver.makeDefinition(name, true, ((Function_definition_headerContext)_localctx).f);
				            if (_localctx.result != null) {
				                _localctx.result.setExpType(EExpType.NumericType);
				                _localctx.result.setTupleSize(1);
				            }
				        
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1124); ((Function_definition_headerContext)_localctx).t = match(USER_STRING);
				setState(1125); ((Function_definition_headerContext)_localctx).f = match(USER_STRING);
				setState(1126); ((Function_definition_headerContext)_localctx).p = function_parameter_list();
				setState(1127); match(BECOMES);

				        	String name = ((Function_definition_headerContext)_localctx).t.getText();
				        	String type = ((Function_definition_headerContext)_localctx).f.getText();
				            ((Function_definition_headerContext)_localctx).result =  driver.makeDefinition(name, true, ((Function_definition_headerContext)_localctx).t);
				            if (_localctx.result != null) {
				            	int[] tupleSize = new int[1];
				            	boolean[] isNatural = new boolean[1];
				                _localctx.result.setExpType(driver.decodeType(type, tupleSize, isNatural, ((Function_definition_headerContext)_localctx).t));
				                _localctx.result.setTupleSize(tupleSize[0]);
				                _localctx.result.setIsNatural(isNatural[0]); 
				            }
				        
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1130); ((Function_definition_headerContext)_localctx).SHAPE = match(SHAPE);
				setState(1131); match(MODTYPE);
				setState(1132); ((Function_definition_headerContext)_localctx).p = function_parameter_list();
				setState(1133); match(BECOMES);

				            driver.error("Reserved keyword: adjustment", ((Function_definition_headerContext)_localctx).SHAPE);
				            ((Function_definition_headerContext)_localctx).result =  null;
				        
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1136); ((Function_definition_headerContext)_localctx).MODTYPE = match(MODTYPE);
				setState(1137); ((Function_definition_headerContext)_localctx).p = function_parameter_list();
				setState(1138); match(BECOMES);

				            driver.error("Reserved keyword: adjustment", ((Function_definition_headerContext)_localctx).MODTYPE);
				            ((Function_definition_headerContext)_localctx).result =  null;
				        
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1141); ((Function_definition_headerContext)_localctx).t = match(USER_STRING);
				setState(1142); match(MODTYPE);
				setState(1143); ((Function_definition_headerContext)_localctx).p = function_parameter_list();
				setState(1144); match(BECOMES);

				            driver.error("Reserved keyword: adjustment", ((Function_definition_headerContext)_localctx).t);
				            ((Function_definition_headerContext)_localctx).result =  null;
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Global_definition_headerContext extends ParserRuleContext {
		public ASTDefine result;
		public Function_definition_headerContext fd;
		public Definition_headerContext d;
		public Definition_headerContext definition_header() {
			return getRuleContext(Definition_headerContext.class,0);
		}
		public Function_definition_headerContext function_definition_header() {
			return getRuleContext(Function_definition_headerContext.class,0);
		}
		public Global_definition_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_global_definition_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterGlobal_definition_header(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitGlobal_definition_header(this);
		}
	}

	public final Global_definition_headerContext global_definition_header() throws RecognitionException {
		Global_definition_headerContext _localctx = new Global_definition_headerContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_global_definition_header);
		try {
			setState(1155);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1149); ((Global_definition_headerContext)_localctx).fd = function_definition_header();

				            if (((Global_definition_headerContext)_localctx).fd.result != null) {
				                assert(((Global_definition_headerContext)_localctx).fd.result.getDefineType() == EDefineType.FunctionDefine);
				                driver.pushRepContainer(driver.getParamDecls());
				            } else {
				                // An error occurred
				                driver.getParamDecls().getParameters().clear();
				                driver.getParamDecls().setStackCount(0);
				            }
				            ((Global_definition_headerContext)_localctx).result =  ((Global_definition_headerContext)_localctx).fd.result;
				        
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1152); ((Global_definition_headerContext)_localctx).d = definition_header();

				            ((Global_definition_headerContext)_localctx).result =  ((Global_definition_headerContext)_localctx).d.result;
				        
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Definition_headerContext extends ParserRuleContext {
		public ASTDefine result;
		public Token n;
		public Token MODTYPE;
		public TerminalNode MODTYPE() { return getToken(CFDGParser.MODTYPE, 0); }
		public TerminalNode USER_STRING() { return getToken(CFDGParser.USER_STRING, 0); }
		public TerminalNode BECOMES() { return getToken(CFDGParser.BECOMES, 0); }
		public Definition_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterDefinition_header(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitDefinition_header(this);
		}
	}

	public final Definition_headerContext definition_header() throws RecognitionException {
		Definition_headerContext _localctx = new Definition_headerContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_definition_header);
		try {
			setState(1163);
			switch (_input.LA(1)) {
			case USER_STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(1157); ((Definition_headerContext)_localctx).n = match(USER_STRING);
				setState(1158); match(BECOMES);

				        	String name = ((Definition_headerContext)_localctx).n.getText();
				            ((Definition_headerContext)_localctx).result =  driver.makeDefinition(name, false, ((Definition_headerContext)_localctx).n);
				        
				}
				break;
			case MODTYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1160); ((Definition_headerContext)_localctx).MODTYPE = match(MODTYPE);
				setState(1161); match(BECOMES);

				            driver.error("Reserved keyword: adjustment", ((Definition_headerContext)_localctx).MODTYPE);
				            ((Definition_headerContext)_localctx).result =  null;
				        
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefinitionContext extends ParserRuleContext {
		public ASTDefine result;
		public Definition_headerContext d;
		public Exp2Context e;
		public Definition_headerContext definition_header() {
			return getRuleContext(Definition_headerContext.class,0);
		}
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public DefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).enterDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFDGListener ) ((CFDGListener)listener).exitDefinition(this);
		}
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1165); ((DefinitionContext)_localctx).d = definition_header();
			setState(1166); ((DefinitionContext)_localctx).e = exp2();
			 
			        	ASTDefine var = ((DefinitionContext)_localctx).d.result;
			        	ASTExpression exp = ((DefinitionContext)_localctx).e.result;
			        	if (var != null) {
			        		if (exp instanceof ASTModification) {
			        			ASTModification mod = (ASTModification)exp;
			        			mod.getModData().setSeed(0); 
			        			var.getChildChange().grab(mod);
			        		} else {
			        			var.setExp(exp);
			        		}
			        		((DefinitionContext)_localctx).result =  var;
			        	} else {
			        		((DefinitionContext)_localctx).result =  null;        		
			        	}
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 28: return buncha_parameters_sempred((Buncha_parametersContext)_localctx, predIndex);

		case 32: return buncha_elements_sempred((Buncha_elementsContext)_localctx, predIndex);

		case 33: return buncha_pathOps_v2_sempred((Buncha_pathOps_v2Context)_localctx, predIndex);

		case 58: return buncha_adjustments_sempred((Buncha_adjustmentsContext)_localctx, predIndex);

		case 62: return letVariables_sempred((LetVariablesContext)_localctx, predIndex);

		case 64: return explist_sempred((ExplistContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean buncha_adjustments_sempred(Buncha_adjustmentsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean buncha_pathOps_v2_sempred(Buncha_pathOps_v2Context _localctx, int predIndex) {
		switch (predIndex) {
		case 2: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean buncha_elements_sempred(Buncha_elementsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean buncha_parameters_sempred(Buncha_parametersContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean letVariables_sempred(LetVariablesContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean explist_sempred(ExplistContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5: return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3V\u0494\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\3\2\3\2\3\2\3\2\5\2\u00a1\n\2\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\5\3\u00aa\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u00b3"+
		"\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u00c2\n\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\5\6\u00d7\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u00f2\n\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00fd\n\b\3\t\3\t\3\t\3\t\3\t\3\t\5"+
		"\t\u0105\n\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\5\f\u0113"+
		"\n\f\3\r\3\r\3\r\3\r\5\r\u0119\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u012b\n\16\3\17\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u013b"+
		"\n\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u0152\n\25\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\5\27\u015f\n\27\3\30\3\30\3\30"+
		"\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u018c"+
		"\n\35\3\36\3\36\3\36\3\36\3\36\3\36\7\36\u0194\n\36\f\36\16\36\u0197\13"+
		"\36\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u019f\n\37\3 \3 \3 \3 \3 \3 \5"+
		" \u01a7\n \3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\5!\u01b6\n!\3\"\3\""+
		"\3\"\3\"\3\"\7\"\u01bd\n\"\f\"\16\"\u01c0\13\"\3#\3#\3#\3#\3#\7#\u01c7"+
		"\n#\f#\16#\u01ca\13#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\5$\u01d6\n$\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\5%\u01f9\n%\3&\3&\3&\3&\3&\3&\3&\3&\5&\u0203\n"+
		"&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u020d\n\'\3(\3(\3(\3(\5(\u0213\n"+
		"(\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\3*\5*\u023d\n*\3+\3"+
		"+\3+\3+\3+\3+\5+\u0245\n+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3,\5,\u0251\n,\3"+
		"-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\5-\u0260\n-\3.\3.\3.\3.\3.\3/\3"+
		"/\3/\3/\3/\5/\u026c\n/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\5\60\u0276"+
		"\n\60\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\5\62\u0283"+
		"\n\62\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\5\64\u029b\n\64\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67"+
		"\3\67\3\67\3\67\5\67\u02b0\n\67\38\38\38\38\38\38\39\39\39\39\39\39\3"+
		"9\39\59\u02c0\n9\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\5:\u02cc\n:\3;\3;\3;\3"+
		";\3;\3;\3;\3;\3;\3;\3;\3;\5;\u02da\n;\3<\3<\3<\3<\3<\3<\3<\7<\u02e3\n"+
		"<\f<\16<\u02e6\13<\3=\3=\3=\3=\3=\3=\3=\3=\3=\3=\3=\3=\3=\3=\3=\5=\u02f7"+
		"\n=\3>\3>\3>\3?\3?\3?\3?\3?\3?\3?\3@\3@\3@\3@\3@\3@\7@\u0309\n@\f@\16"+
		"@\u030c\13@\3A\3A\3A\3B\3B\3B\3B\3B\3B\3B\3B\7B\u0319\nB\fB\16B\u031c"+
		"\13B\3C\3C\3C\3C\3C\3C\3C\5C\u0325\nC\3D\3D\3D\3D\3D\3D\3D\3D\3D\3D\3"+
		"D\3D\3D\3D\3D\3D\3D\3D\3D\3D\5D\u033b\nD\3D\3D\3D\3D\3D\3D\3D\3D\5D\u0345"+
		"\nD\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E"+
		"\3E\3E\3E\3E\3E\5E\u0362\nE\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E"+
		"\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E"+
		"\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E"+
		"\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\3E\5E\u03ac\nE\3F\3F\3F\3F\3F\3F\3F"+
		"\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\5F\u03c9"+
		"\nF\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F"+
		"\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F"+
		"\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F"+
		"\5F\u040f\nF\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G\3G"+
		"\3G\3G\3G\3G\3G\3G\3G\3G\3G\5G\u042d\nG\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H"+
		"\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H\3H"+
		"\5H\u0450\nH\3I\3I\3I\3I\5I\u0456\nI\3J\3J\3J\3J\3K\3K\3K\3K\3K\3K\3K"+
		"\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K"+
		"\3K\3K\3K\3K\5K\u047e\nK\3L\3L\3L\3L\3L\3L\5L\u0486\nL\3M\3M\3M\3M\3M"+
		"\3M\5M\u048e\nM\3N\3N\3N\3N\3N\2\b:BDv~\u0082O\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtv"+
		"xz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094"+
		"\u0096\u0098\u009a\2\2\u04f3\2\u00a0\3\2\2\2\4\u00a9\3\2\2\2\6\u00b2\3"+
		"\2\2\2\b\u00c1\3\2\2\2\n\u00d6\3\2\2\2\f\u00f1\3\2\2\2\16\u00fc\3\2\2"+
		"\2\20\u0104\3\2\2\2\22\u0106\3\2\2\2\24\u010b\3\2\2\2\26\u0112\3\2\2\2"+
		"\30\u0118\3\2\2\2\32\u012a\3\2\2\2\34\u012c\3\2\2\2\36\u0130\3\2\2\2 "+
		"\u013a\3\2\2\2\"\u013c\3\2\2\2$\u0141\3\2\2\2&\u0145\3\2\2\2(\u0151\3"+
		"\2\2\2*\u0153\3\2\2\2,\u015e\3\2\2\2.\u0160\3\2\2\2\60\u0165\3\2\2\2\62"+
		"\u016b\3\2\2\2\64\u0171\3\2\2\2\66\u0175\3\2\2\28\u018b\3\2\2\2:\u018d"+
		"\3\2\2\2<\u019e\3\2\2\2>\u01a6\3\2\2\2@\u01b5\3\2\2\2B\u01b7\3\2\2\2D"+
		"\u01c1\3\2\2\2F\u01d5\3\2\2\2H\u01f8\3\2\2\2J\u0202\3\2\2\2L\u020c\3\2"+
		"\2\2N\u0212\3\2\2\2P\u0214\3\2\2\2R\u023c\3\2\2\2T\u0244\3\2\2\2V\u0250"+
		"\3\2\2\2X\u025f\3\2\2\2Z\u0261\3\2\2\2\\\u026b\3\2\2\2^\u0275\3\2\2\2"+
		"`\u0277\3\2\2\2b\u0282\3\2\2\2d\u0284\3\2\2\2f\u029a\3\2\2\2h\u029c\3"+
		"\2\2\2j\u02a2\3\2\2\2l\u02af\3\2\2\2n\u02b1\3\2\2\2p\u02bf\3\2\2\2r\u02cb"+
		"\3\2\2\2t\u02d9\3\2\2\2v\u02db\3\2\2\2x\u02f6\3\2\2\2z\u02f8\3\2\2\2|"+
		"\u02fb\3\2\2\2~\u0302\3\2\2\2\u0080\u030d\3\2\2\2\u0082\u0310\3\2\2\2"+
		"\u0084\u0324\3\2\2\2\u0086\u033a\3\2\2\2\u0088\u0361\3\2\2\2\u008a\u03c8"+
		"\3\2\2\2\u008c\u042c\3\2\2\2\u008e\u044f\3\2\2\2\u0090\u0455\3\2\2\2\u0092"+
		"\u0457\3\2\2\2\u0094\u047d\3\2\2\2\u0096\u0485\3\2\2\2\u0098\u048d\3\2"+
		"\2\2\u009a\u048f\3\2\2\2\u009c\u009d\7\24\2\2\u009d\u00a1\5\4\3\2\u009e"+
		"\u009f\7\25\2\2\u009f\u00a1\5\6\4\2\u00a0\u009c\3\2\2\2\u00a0\u009e\3"+
		"\2\2\2\u00a1\3\3\2\2\2\u00a2\u00a3\5\b\5\2\u00a3\u00a4\5\4\3\2\u00a4\u00a5"+
		"\b\3\1\2\u00a5\u00aa\3\2\2\2\u00a6\u00a7\5\b\5\2\u00a7\u00a8\b\3\1\2\u00a8"+
		"\u00aa\3\2\2\2\u00a9\u00a2\3\2\2\2\u00a9\u00a6\3\2\2\2\u00aa\5\3\2\2\2"+
		"\u00ab\u00ac\5\n\6\2\u00ac\u00ad\5\6\4\2\u00ad\u00ae\b\4\1\2\u00ae\u00b3"+
		"\3\2\2\2\u00af\u00b0\5\n\6\2\u00b0\u00b1\b\4\1\2\u00b1\u00b3\3\2\2\2\u00b2"+
		"\u00ab\3\2\2\2\u00b2\u00af\3\2\2\2\u00b3\7\3\2\2\2\u00b4\u00c2\5\34\17"+
		"\2\u00b5\u00c2\5\36\20\2\u00b6\u00b7\5\20\t\2\u00b7\u00b8\b\5\1\2\u00b8"+
		"\u00c2\3\2\2\2\u00b9\u00ba\5\24\13\2\u00ba\u00bb\b\5\1\2\u00bb\u00c2\3"+
		"\2\2\2\u00bc\u00c2\5*\26\2\u00bd\u00c2\5\66\34\2\u00be\u00bf\5\f\7\2\u00bf"+
		"\u00c0\b\5\1\2\u00c0\u00c2\3\2\2\2\u00c1\u00b4\3\2\2\2\u00c1\u00b5\3\2"+
		"\2\2\u00c1\u00b6\3\2\2\2\u00c1\u00b9\3\2\2\2\u00c1\u00bc\3\2\2\2\u00c1"+
		"\u00bd\3\2\2\2\u00c1\u00be\3\2\2\2\u00c2\t\3\2\2\2\u00c3\u00d7\5\32\16"+
		"\2\u00c4\u00c5\5\22\n\2\u00c5\u00c6\b\6\1\2\u00c6\u00d7\3\2\2\2\u00c7"+
		"\u00c8\5\24\13\2\u00c8\u00c9\b\6\1\2\u00c9\u00d7\3\2\2\2\u00ca\u00d7\5"+
		"\60\31\2\u00cb\u00d7\5\62\32\2\u00cc\u00cd\5\"\22\2\u00cd\u00ce\b\6\1"+
		"\2\u00ce\u00d7\3\2\2\2\u00cf\u00d7\5&\24\2\u00d0\u00d1\5\u0092J\2\u00d1"+
		"\u00d2\b\6\1\2\u00d2\u00d7\3\2\2\2\u00d3\u00d4\5\16\b\2\u00d4\u00d5\b"+
		"\6\1\2\u00d5\u00d7\3\2\2\2\u00d6\u00c3\3\2\2\2\u00d6\u00c4\3\2\2\2\u00d6"+
		"\u00c7\3\2\2\2\u00d6\u00ca\3\2\2\2\u00d6\u00cb\3\2\2\2\u00d6\u00cc\3\2"+
		"\2\2\u00d6\u00cf\3\2\2\2\u00d6\u00d0\3\2\2\2\u00d6\u00d3\3\2\2\2\u00d7"+
		"\13\3\2\2\2\u00d8\u00d9\7Q\2\2\u00d9\u00f2\7A\2\2\u00da\u00db\7P\2\2\u00db"+
		"\u00f2\7A\2\2\u00dc\u00dd\7@\2\2\u00dd\u00f2\7A\2\2\u00de\u00df\7Q\2\2"+
		"\u00df\u00f2\7\16\2\2\u00e0\u00e1\7Q\2\2\u00e1\u00e2\7Q\2\2\u00e2\u00f2"+
		"\7\16\2\2\u00e3\u00f2\7\32\2\2\u00e4\u00f2\7\36\2\2\u00e5\u00e6\7\35\2"+
		"\2\u00e6\u00e7\7Q\2\2\u00e7\u00f2\7\16\2\2\u00e8\u00e9\7\27\2\2\u00e9"+
		"\u00ea\7Q\2\2\u00ea\u00f2\7\16\2\2\u00eb\u00ec\7\27\2\2\u00ec\u00ed\7"+
		"Q\2\2\u00ed\u00f2\7\n\2\2\u00ee\u00ef\7\27\2\2\u00ef\u00f0\7T\2\2\u00f0"+
		"\u00f2\7\n\2\2\u00f1\u00d8\3\2\2\2\u00f1\u00da\3\2\2\2\u00f1\u00dc\3\2"+
		"\2\2\u00f1\u00de\3\2\2\2\u00f1\u00e0\3\2\2\2\u00f1\u00e3\3\2\2\2\u00f1"+
		"\u00e4\3\2\2\2\u00f1\u00e5\3\2\2\2\u00f1\u00e8\3\2\2\2\u00f1\u00eb\3\2"+
		"\2\2\u00f1\u00ee\3\2\2\2\u00f2\r\3\2\2\2\u00f3\u00f4\7\30\2\2\u00f4\u00fd"+
		"\5r:\2\u00f5\u00f6\7\33\2\2\u00f6\u00fd\5r:\2\u00f7\u00f8\7P\2\2\u00f8"+
		"\u00fd\5r:\2\u00f9\u00fa\7\31\2\2\u00fa\u00fd\5\26\f\2\u00fb\u00fd\5("+
		"\25\2\u00fc\u00f3\3\2\2\2\u00fc\u00f5\3\2\2\2\u00fc\u00f7\3\2\2\2\u00fc"+
		"\u00f9\3\2\2\2\u00fc\u00fb\3\2\2\2\u00fd\17\3\2\2\2\u00fe\u00ff\7\31\2"+
		"\2\u00ff\u0100\7R\2\2\u0100\u0105\b\t\1\2\u0101\u0102\7\31\2\2\u0102\u0103"+
		"\7S\2\2\u0103\u0105\b\t\1\2\u0104\u00fe\3\2\2\2\u0104\u0101\3\2\2\2\u0105"+
		"\21\3\2\2\2\u0106\u0107\7\32\2\2\u0107\u0108\5\30\r\2\u0108\u0109\5\26"+
		"\f\2\u0109\u010a\b\n\1\2\u010a\23\3\2\2\2\u010b\u010c\7\2\2\3\u010c\u010d"+
		"\b\13\1\2\u010d\25\3\2\2\2\u010e\u010f\7S\2\2\u010f\u0113\b\f\1\2\u0110"+
		"\u0111\7R\2\2\u0111\u0113\b\f\1\2\u0112\u010e\3\2\2\2\u0112\u0110\3\2"+
		"\2\2\u0113\27\3\2\2\2\u0114\u0115\7\r\2\2\u0115\u0116\7Q\2\2\u0116\u0119"+
		"\b\r\1\2\u0117\u0119\b\r\1\2\u0118\u0114\3\2\2\2\u0118\u0117\3\2\2\2\u0119"+
		"\31\3\2\2\2\u011a\u011b\7\27\2\2\u011b\u011c\7Q\2\2\u011c\u011d\5@!\2"+
		"\u011d\u011e\5t;\2\u011e\u011f\b\16\1\2\u011f\u012b\3\2\2\2\u0120\u0121"+
		"\7\27\2\2\u0121\u0122\7T\2\2\u0122\u0123\5t;\2\u0123\u0124\b\16\1\2\u0124"+
		"\u012b\3\2\2\2\u0125\u0126\7\27\2\2\u0126\u0127\7Q\2\2\u0127\u0128\5@"+
		"!\2\u0128\u0129\b\16\1\2\u0129\u012b\3\2\2\2\u012a\u011a\3\2\2\2\u012a"+
		"\u0120\3\2\2\2\u012a\u0125\3\2\2\2\u012b\33\3\2\2\2\u012c\u012d\7\27\2"+
		"\2\u012d\u012e\7Q\2\2\u012e\u012f\b\17\1\2\u012f\35\3\2\2\2\u0130\u0131"+
		"\5 \21\2\u0131\u0132\5r:\2\u0132\u0133\b\20\1\2\u0133\37\3\2\2\2\u0134"+
		"\u0135\7\30\2\2\u0135\u013b\b\21\1\2\u0136\u0137\7\33\2\2\u0137\u013b"+
		"\b\21\1\2\u0138\u0139\7P\2\2\u0139\u013b\b\21\1\2\u013a\u0134\3\2\2\2"+
		"\u013a\u0136\3\2\2\2\u013a\u0138\3\2\2\2\u013b!\3\2\2\2\u013c\u013d\7"+
		"\36\2\2\u013d\u013e\7Q\2\2\u013e\u013f\5<\37\2\u013f\u0140\b\22\1\2\u0140"+
		"#\3\2\2\2\u0141\u0142\5\"\22\2\u0142\u0143\7\20\2\2\u0143\u0144\b\23\1"+
		"\2\u0144%\3\2\2\2\u0145\u0146\5$\23\2\u0146\u0147\5B\"\2\u0147\u0148\7"+
		"\22\2\2\u0148\u0149\b\24\1\2\u0149\'\3\2\2\2\u014a\u014b\7\34\2\2\u014b"+
		"\u014c\7Q\2\2\u014c\u0152\b\25\1\2\u014d\u014e\7\34\2\2\u014e\u014f\7"+
		"Q\2\2\u014f\u0150\7\26\2\2\u0150\u0152\b\25\1\2\u0151\u014a\3\2\2\2\u0151"+
		"\u014d\3\2\2\2\u0152)\3\2\2\2\u0153\u0154\5(\25\2\u0154\u0155\7\20\2\2"+
		"\u0155\u0156\5\\/\2\u0156\u0157\7\22\2\2\u0157\u0158\b\26\1\2\u0158+\3"+
		"\2\2\2\u0159\u015a\7\34\2\2\u015a\u015f\b\27\1\2\u015b\u015c\7\34\2\2"+
		"\u015c\u015d\7\26\2\2\u015d\u015f\b\27\1\2\u015e\u0159\3\2\2\2\u015e\u015b"+
		"\3\2\2\2\u015f-\3\2\2\2\u0160\u0161\7\35\2\2\u0161\u0162\7Q\2\2\u0162"+
		"\u0163\5<\37\2\u0163\u0164\b\30\1\2\u0164/\3\2\2\2\u0165\u0166\5,\27\2"+
		"\u0166\u0167\7\20\2\2\u0167\u0168\5B\"\2\u0168\u0169\7\22\2\2\u0169\u016a"+
		"\b\31\1\2\u016a\61\3\2\2\2\u016b\u016c\5.\30\2\u016c\u016d\7\20\2\2\u016d"+
		"\u016e\5B\"\2\u016e\u016f\7\22\2\2\u016f\u0170\b\32\1\2\u0170\63\3\2\2"+
		"\2\u0171\u0172\7\35\2\2\u0172\u0173\7Q\2\2\u0173\u0174\b\33\1\2\u0174"+
		"\65\3\2\2\2\u0175\u0176\5\64\33\2\u0176\u0177\7\20\2\2\u0177\u0178\5D"+
		"#\2\u0178\u0179\7\22\2\2\u0179\u017a\b\34\1\2\u017a\67\3\2\2\2\u017b\u017c"+
		"\7Q\2\2\u017c\u017d\7Q\2\2\u017d\u018c\b\35\1\2\u017e\u017f\7\36\2\2\u017f"+
		"\u0180\7Q\2\2\u0180\u018c\b\35\1\2\u0181\u0182\7Q\2\2\u0182\u0183\7P\2"+
		"\2\u0183\u018c\b\35\1\2\u0184\u0185\7\36\2\2\u0185\u0186\7P\2\2\u0186"+
		"\u018c\b\35\1\2\u0187\u0188\7Q\2\2\u0188\u018c\b\35\1\2\u0189\u018a\7"+
		"P\2\2\u018a\u018c\b\35\1\2\u018b\u017b\3\2\2\2\u018b\u017e\3\2\2\2\u018b"+
		"\u0181\3\2\2\2\u018b\u0184\3\2\2\2\u018b\u0187\3\2\2\2\u018b\u0189\3\2"+
		"\2\2\u018c9\3\2\2\2\u018d\u018e\b\36\1\2\u018e\u018f\58\35\2\u018f\u0195"+
		"\3\2\2\2\u0190\u0191\f\4\2\2\u0191\u0192\7\6\2\2\u0192\u0194\58\35\2\u0193"+
		"\u0190\3\2\2\2\u0194\u0197\3\2\2\2\u0195\u0193\3\2\2\2\u0195\u0196\3\2"+
		"\2\2\u0196;\3\2\2\2\u0197\u0195\3\2\2\2\u0198\u0199\7\16\2\2\u0199\u019a"+
		"\5:\36\2\u019a\u019b\7\5\2\2\u019b\u019c\b\37\1\2\u019c\u019f\3\2\2\2"+
		"\u019d\u019f\3\2\2\2\u019e\u0198\3\2\2\2\u019e\u019d\3\2\2\2\u019f=\3"+
		"\2\2\2\u01a0\u01a1\7\16\2\2\u01a1\u01a2\5:\36\2\u01a2\u01a3\7\5\2\2\u01a3"+
		"\u01a7\3\2\2\2\u01a4\u01a5\7\16\2\2\u01a5\u01a7\7\5\2\2\u01a6\u01a0\3"+
		"\2\2\2\u01a6\u01a4\3\2\2\2\u01a7?\3\2\2\2\u01a8\u01a9\7\16\2\2\u01a9\u01aa"+
		"\5\u0084C\2\u01aa\u01ab\7\5\2\2\u01ab\u01ac\b!\1\2\u01ac\u01b6\3\2\2\2"+
		"\u01ad\u01ae\7\16\2\2\u01ae\u01af\7A\2\2\u01af\u01b0\7\5\2\2\u01b0\u01b6"+
		"\b!\1\2\u01b1\u01b2\7\16\2\2\u01b2\u01b3\7\5\2\2\u01b3\u01b6\b!\1\2\u01b4"+
		"\u01b6\b!\1\2\u01b5\u01a8\3\2\2\2\u01b5\u01ad\3\2\2\2\u01b5\u01b1\3\2"+
		"\2\2\u01b5\u01b4\3\2\2\2\u01b6A\3\2\2\2\u01b7\u01be\b\"\1\2\u01b8\u01b9"+
		"\f\4\2\2\u01b9\u01ba\5R*\2\u01ba\u01bb\b\"\1\2\u01bb\u01bd\3\2\2\2\u01bc"+
		"\u01b8\3\2\2\2\u01bd\u01c0\3\2\2\2\u01be\u01bc\3\2\2\2\u01be\u01bf\3\2"+
		"\2\2\u01bfC\3\2\2\2\u01c0\u01be\3\2\2\2\u01c1\u01c8\b#\1\2\u01c2\u01c3"+
		"\f\4\2\2\u01c3\u01c4\5V,\2\u01c4\u01c5\b#\1\2\u01c5\u01c7\3\2\2\2\u01c6"+
		"\u01c2\3\2\2\2\u01c7\u01ca\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c8\u01c9\3\2"+
		"\2\2\u01c9E\3\2\2\2\u01ca\u01c8\3\2\2\2\u01cb\u01cc\7M\2\2\u01cc\u01cd"+
		"\7\20\2\2\u01cd\u01ce\5v<\2\u01ce\u01cf\7\22\2\2\u01cf\u01d0\b$\1\2\u01d0"+
		"\u01d6\3\2\2\2\u01d1\u01d2\5\u0090I\2\u01d2\u01d3\5r:\2\u01d3\u01d4\b"+
		"$\1\2\u01d4\u01d6\3\2\2\2\u01d5\u01cb\3\2\2\2\u01d5\u01d1\3\2\2\2\u01d6"+
		"G\3\2\2\2\u01d7\u01d8\7M\2\2\u01d8\u01d9\7\16\2\2\u01d9\u01da\5\u0088"+
		"E\2\u01da\u01db\7\5\2\2\u01db\u01dc\b%\1\2\u01dc\u01f9\3\2\2\2\u01dd\u01de"+
		"\7M\2\2\u01de\u01df\7\16\2\2\u01df\u01e0\7\5\2\2\u01e0\u01f9\b%\1\2\u01e1"+
		"\u01e2\5\u0090I\2\u01e2\u01e3\5@!\2\u01e3\u01e4\5t;\2\u01e4\u01e5\b%\1"+
		"\2\u01e5\u01f9\3\2\2\2\u01e6\u01e7\7!\2\2\u01e7\u01e8\7\16\2\2\u01e8\u01e9"+
		"\5\u0088E\2\u01e9\u01ea\7\5\2\2\u01ea\u01eb\5t;\2\u01eb\u01ec\b%\1\2\u01ec"+
		"\u01f9\3\2\2\2\u01ed\u01ee\5z>\2\u01ee\u01ef\5|?\2\u01ef\u01f0\5t;\2\u01f0"+
		"\u01f1\b%\1\2\u01f1\u01f9\3\2\2\2\u01f2\u01f3\7\35\2\2\u01f3\u01f4\7Q"+
		"\2\2\u01f4\u01f5\5@!\2\u01f5\u01f6\5t;\2\u01f6\u01f7\b%\1\2\u01f7\u01f9"+
		"\3\2\2\2\u01f8\u01d7\3\2\2\2\u01f8\u01dd\3\2\2\2\u01f8\u01e1\3\2\2\2\u01f8"+
		"\u01e6\3\2\2\2\u01f8\u01ed\3\2\2\2\u01f8\u01f2\3\2\2\2\u01f9I\3\2\2\2"+
		"\u01fa\u01fb\7\20\2\2\u01fb\u01fc\5B\"\2\u01fc\u01fd\7\22\2\2\u01fd\u01fe"+
		"\b&\1\2\u01fe\u0203\3\2\2\2\u01ff\u0200\5H%\2\u0200\u0201\b&\1\2\u0201"+
		"\u0203\3\2\2\2\u0202\u01fa\3\2\2\2\u0202\u01ff\3\2\2\2\u0203K\3\2\2\2"+
		"\u0204\u0205\7\20\2\2\u0205\u0206\5D#\2\u0206\u0207\7\22\2\2\u0207\u0208"+
		"\b\'\1\2\u0208\u020d\3\2\2\2\u0209\u020a\5F$\2\u020a\u020b\b\'\1\2\u020b"+
		"\u020d\3\2\2\2\u020c\u0204\3\2\2\2\u020c\u0209\3\2\2\2\u020dM\3\2\2\2"+
		"\u020e\u020f\5P)\2\u020f\u0210\5N(\2\u0210\u0213\3\2\2\2\u0211\u0213\3"+
		"\2\2\2\u0212\u020e\3\2\2\2\u0212\u0211\3\2\2\2\u0213O\3\2\2\2\u0214\u0215"+
		"\5p9\2\u0215\u0216\5J&\2\u0216\u0217\b)\1\2\u0217Q\3\2\2\2\u0218\u0219"+
		"\5H%\2\u0219\u021a\b*\1\2\u021a\u023d\3\2\2\2\u021b\u021c\5\u009aN\2\u021c"+
		"\u021d\b*\1\2\u021d\u023d\3\2\2\2\u021e\u021f\5Z.\2\u021f\u0220\b*\1\2"+
		"\u0220\u023d\3\2\2\2\u0221\u0222\5Z.\2\u0222\u0223\7 \2\2\u0223\u0224"+
		"\b*\1\2\u0224\u0225\5J&\2\u0225\u0226\b*\1\2\u0226\u023d\3\2\2\2\u0227"+
		"\u0228\5h\65\2\u0228\u0229\5J&\2\u0229\u022a\b*\1\2\u022a\u023d\3\2\2"+
		"\2\u022b\u022c\5j\66\2\u022c\u022d\5J&\2\u022d\u022e\b*\1\2\u022e\u023d"+
		"\3\2\2\2\u022f\u0230\5l\67\2\u0230\u0231\5J&\2\u0231\u0232\b*\1\2\u0232"+
		"\u023d\3\2\2\2\u0233\u0234\5n8\2\u0234\u0235\7\20\2\2\u0235\u0236\5N("+
		"\2\u0236\u0237\7\22\2\2\u0237\u0238\b*\1\2\u0238\u023d\3\2\2\2\u0239\u023a"+
		"\5T+\2\u023a\u023b\b*\1\2\u023b\u023d\3\2\2\2\u023c\u0218\3\2\2\2\u023c"+
		"\u021b\3\2\2\2\u023c\u021e\3\2\2\2\u023c\u0221\3\2\2\2\u023c\u0227\3\2"+
		"\2\2\u023c\u022b\3\2\2\2\u023c\u022f\3\2\2\2\u023c\u0233\3\2\2\2\u023c"+
		"\u0239\3\2\2\2\u023dS\3\2\2\2\u023e\u023f\7\26\2\2\u023f\u0245\7\t\2\2"+
		"\u0240\u0241\7Q\2\2\u0241\u0245\7\20\2\2\u0242\u0243\7M\2\2\u0243\u0245"+
		"\7\20\2\2\u0244\u023e\3\2\2\2\u0244\u0240\3\2\2\2\u0244\u0242\3\2\2\2"+
		"\u0245U\3\2\2\2\u0246\u0247\5F$\2\u0247\u0248\b,\1\2\u0248\u0251\3\2\2"+
		"\2\u0249\u024a\5d\63\2\u024a\u024b\5L\'\2\u024b\u024c\b,\1\2\u024c\u0251"+
		"\3\2\2\2\u024d\u024e\5X-\2\u024e\u024f\b,\1\2\u024f\u0251\3\2\2\2\u0250"+
		"\u0246\3\2\2\2\u0250\u0249\3\2\2\2\u0250\u024d\3\2\2\2\u0251W\3\2\2\2"+
		"\u0252\u0253\7M\2\2\u0253\u0260\7\16\2\2\u0254\u0255\7Q\2\2\u0255\u0260"+
		"\7\16\2\2\u0256\u0260\7\35\2\2\u0257\u0260\7\37\2\2\u0258\u0259\7Q\2\2"+
		"\u0259\u0260\7A\2\2\u025a\u025b\7P\2\2\u025b\u0260\7A\2\2\u025c\u0260"+
		"\7!\2\2\u025d\u0260\7P\2\2\u025e\u0260\7#\2\2\u025f\u0252\3\2\2\2\u025f"+
		"\u0254\3\2\2\2\u025f\u0256\3\2\2\2\u025f\u0257\3\2\2\2\u025f\u0258\3\2"+
		"\2\2\u025f\u025a\3\2\2\2\u025f\u025c\3\2\2\2\u025f\u025d\3\2\2\2\u025f"+
		"\u025e\3\2\2\2\u0260Y\3\2\2\2\u0261\u0262\5f\64\2\u0262\u0263\5t;\2\u0263"+
		"\u0264\5J&\2\u0264\u0265\b.\1\2\u0265[\3\2\2\2\u0266\u0267\5b\62\2\u0267"+
		"\u0268\5\\/\2\u0268\u0269\b/\1\2\u0269\u026c\3\2\2\2\u026a\u026c\3\2\2"+
		"\2\u026b\u0266\3\2\2\2\u026b\u026a\3\2\2\2\u026c]\3\2\2\2\u026d\u026e"+
		"\7\20\2\2\u026e\u026f\5\\/\2\u026f\u0270\7\22\2\2\u0270\u0271\b\60\1\2"+
		"\u0271\u0276\3\2\2\2\u0272\u0273\5`\61\2\u0273\u0274\b\60\1\2\u0274\u0276"+
		"\3\2\2\2\u0275\u026d\3\2\2\2\u0275\u0272\3\2\2\2\u0276_\3\2\2\2\u0277"+
		"\u0278\5\u0090I\2\u0278\u0279\5r:\2\u0279\u027a\b\61\1\2\u027aa\3\2\2"+
		"\2\u027b\u027c\5`\61\2\u027c\u027d\b\62\1\2\u027d\u0283\3\2\2\2\u027e"+
		"\u027f\5d\63\2\u027f\u0280\5^\60\2\u0280\u0281\b\62\1\2\u0281\u0283\3"+
		"\2\2\2\u0282\u027b\3\2\2\2\u0282\u027e\3\2\2\2\u0283c\3\2\2\2\u0284\u0285"+
		"\7\26\2\2\u0285\u0286\7\t\2\2\u0286\u0287\b\63\1\2\u0287\u0288\5r:\2\u0288"+
		"\u0289\b\63\1\2\u0289e\3\2\2\2\u028a\u028b\7\37\2\2\u028b\u028c\7Q\2\2"+
		"\u028c\u028d\7A\2\2\u028d\u028e\5\u0088E\2\u028e\u028f\b\64\1\2\u028f"+
		"\u029b\3\2\2\2\u0290\u0291\7\37\2\2\u0291\u0292\7P\2\2\u0292\u0293\7A"+
		"\2\2\u0293\u0294\5\u0088E\2\u0294\u0295\b\64\1\2\u0295\u029b\3\2\2\2\u0296"+
		"\u0297\7\37\2\2\u0297\u0298\5\u0088E\2\u0298\u0299\b\64\1\2\u0299\u029b"+
		"\3\2\2\2\u029a\u028a\3\2\2\2\u029a\u0290\3\2\2\2\u029a\u0296\3\2\2\2\u029b"+
		"g\3\2\2\2\u029c\u029d\7!\2\2\u029d\u029e\7\16\2\2\u029e\u029f\5\u0088"+
		"E\2\u029f\u02a0\7\5\2\2\u02a0\u02a1\b\65\1\2\u02a1i\3\2\2\2\u02a2\u02a3"+
		"\5h\65\2\u02a3\u02a4\5J&\2\u02a4\u02a5\7\"\2\2\u02a5\u02a6\b\66\1\2\u02a6"+
		"k\3\2\2\2\u02a7\u02a8\7P\2\2\u02a8\u02a9\5\u0088E\2\u02a9\u02aa\b\67\1"+
		"\2\u02aa\u02b0\3\2\2\2\u02ab\u02ac\7N\2\2\u02ac\u02ad\5\u0088E\2\u02ad"+
		"\u02ae\b\67\1\2\u02ae\u02b0\3\2\2\2\u02af\u02a7\3\2\2\2\u02af\u02ab\3"+
		"\2\2\2\u02b0m\3\2\2\2\u02b1\u02b2\7#\2\2\u02b2\u02b3\7\16\2\2\u02b3\u02b4"+
		"\5\u0088E\2\u02b4\u02b5\7\5\2\2\u02b5\u02b6\b8\1\2\u02b6o\3\2\2\2\u02b7"+
		"\u02b8\7$\2\2\u02b8\u02b9\5\u0088E\2\u02b9\u02ba\7\f\2\2\u02ba\u02bb\b"+
		"9\1\2\u02bb\u02c0\3\2\2\2\u02bc\u02bd\7\"\2\2\u02bd\u02be\7\f\2\2\u02be"+
		"\u02c0\b9\1\2\u02bf\u02b7\3\2\2\2\u02bf\u02bc\3\2\2\2\u02c0q\3\2\2\2\u02c1"+
		"\u02c2\7\20\2\2\u02c2\u02c3\5v<\2\u02c3\u02c4\7\22\2\2\u02c4\u02c5\b:"+
		"\1\2\u02c5\u02cc\3\2\2\2\u02c6\u02c7\7\n\2\2\u02c7\u02c8\5v<\2\u02c8\u02c9"+
		"\7\3\2\2\u02c9\u02ca\b:\1\2\u02ca\u02cc\3\2\2\2\u02cb\u02c1\3\2\2\2\u02cb"+
		"\u02c6\3\2\2\2\u02ccs\3\2\2\2\u02cd\u02ce\7\n\2\2\u02ce\u02cf\5v<\2\u02cf"+
		"\u02d0\7\3\2\2\u02d0\u02d1\b;\1\2\u02d1\u02da\3\2\2\2\u02d2\u02d3\7\n"+
		"\2\2\u02d3\u02d4\7\n\2\2\u02d4\u02d5\5v<\2\u02d5\u02d6\7\3\2\2\u02d6\u02d7"+
		"\7\3\2\2\u02d7\u02d8\b;\1\2\u02d8\u02da\3\2\2\2\u02d9\u02cd\3\2\2\2\u02d9"+
		"\u02d2\3\2\2\2\u02dau\3\2\2\2\u02db\u02dc\b<\1\2\u02dc\u02dd\b<\1\2\u02dd"+
		"\u02e4\3\2\2\2\u02de\u02df\f\4\2\2\u02df\u02e0\5x=\2\u02e0\u02e1\b<\1"+
		"\2\u02e1\u02e3\3\2\2\2\u02e2\u02de\3\2\2\2\u02e3\u02e6\3\2\2\2\u02e4\u02e2"+
		"\3\2\2\2\u02e4\u02e5\3\2\2\2\u02e5w\3\2\2\2\u02e6\u02e4\3\2\2\2\u02e7"+
		"\u02e8\7P\2\2\u02e8\u02e9\5\u0082B\2\u02e9\u02ea\b=\1\2\u02ea\u02f7\3"+
		"\2\2\2\u02eb\u02ec\7P\2\2\u02ec\u02ed\5\u0086D\2\u02ed\u02ee\7\23\2\2"+
		"\u02ee\u02ef\b=\1\2\u02ef\u02f7\3\2\2\2\u02f0\u02f1\7@\2\2\u02f1\u02f2"+
		"\7Q\2\2\u02f2\u02f7\b=\1\2\u02f3\u02f4\7@\2\2\u02f4\u02f5\7R\2\2\u02f5"+
		"\u02f7\b=\1\2\u02f6\u02e7\3\2\2\2\u02f6\u02eb\3\2\2\2\u02f6\u02f0\3\2"+
		"\2\2\u02f6\u02f3\3\2\2\2\u02f7y\3\2\2\2\u02f8\u02f9\7O\2\2\u02f9\u02fa"+
		"\b>\1\2\u02fa{\3\2\2\2\u02fb\u02fc\7\16\2\2\u02fc\u02fd\5~@\2\u02fd\u02fe"+
		"\7\17\2\2\u02fe\u02ff\5\u0088E\2\u02ff\u0300\7\5\2\2\u0300\u0301\b?\1"+
		"\2\u0301}\3\2\2\2\u0302\u0303\b@\1\2\u0303\u0304\5\u0080A\2\u0304\u030a"+
		"\3\2\2\2\u0305\u0306\f\4\2\2\u0306\u0307\7\17\2\2\u0307\u0309\5\u0080"+
		"A\2\u0308\u0305\3\2\2\2\u0309\u030c\3\2\2\2\u030a\u0308\3\2\2\2\u030a"+
		"\u030b\3\2\2\2\u030b\177\3\2\2\2\u030c\u030a\3\2\2\2\u030d\u030e\5\u009a"+
		"N\2\u030e\u030f\bA\1\2\u030f\u0081\3\2\2\2\u0310\u0311\bB\1\2\u0311\u0312"+
		"\5\u0086D\2\u0312\u0313\bB\1\2\u0313\u031a\3\2\2\2\u0314\u0315\f\4\2\2"+
		"\u0315\u0316\5\u0086D\2\u0316\u0317\bB\1\2\u0317\u0319\3\2\2\2\u0318\u0314"+
		"\3\2\2\2\u0319\u031c\3\2\2\2\u031a\u0318\3\2\2\2\u031a\u031b\3\2\2\2\u031b"+
		"\u0083\3\2\2\2\u031c\u031a\3\2\2\2\u031d\u031e\5\u0082B\2\u031e\u031f"+
		"\5\u008aF\2\u031f\u0320\bC\1\2\u0320\u0325\3\2\2\2\u0321\u0322\5\u008a"+
		"F\2\u0322\u0323\bC\1\2\u0323\u0325\3\2\2\2\u0324\u031d\3\2\2\2\u0324\u0321"+
		"\3\2\2\2\u0325\u0085\3\2\2\2\u0326\u0327\7\26\2\2\u0327\u033b\bD\1\2\u0328"+
		"\u0329\7L\2\2\u0329\u033b\bD\1\2\u032a\u032b\7\16\2\2\u032b\u032c\5\u0088"+
		"E\2\u032c\u032d\7\5\2\2\u032d\u032e\bD\1\2\u032e\u033b\3\2\2\2\u032f\u0330"+
		"\5\u008cG\2\u0330\u0331\bD\1\2\u0331\u033b\3\2\2\2\u0332\u0333\7\13\2"+
		"\2\u0333\u0334\5\u0086D\2\u0334\u0335\bD\1\2\u0335\u033b\3\2\2\2\u0336"+
		"\u0337\7\b\2\2\u0337\u0338\5\u0086D\2\u0338\u0339\bD\1\2\u0339\u033b\3"+
		"\2\2\2\u033a\u0326\3\2\2\2\u033a\u0328\3\2\2\2\u033a\u032a\3\2\2\2\u033a"+
		"\u032f\3\2\2\2\u033a\u0332\3\2\2\2\u033a\u0336\3\2\2\2\u033b\u0344\3\2"+
		"\2\2\u033c\u033d\7%\2\2\u033d\u033e\5\u0086D\2\u033e\u033f\bD\1\2\u033f"+
		"\u0345\3\2\2\2\u0340\u0341\7&\2\2\u0341\u0342\5\u0086D\2\u0342\u0343\b"+
		"D\1\2\u0343\u0345\3\2\2\2\u0344\u033c\3\2\2\2\u0344\u0340\3\2\2\2\u0344"+
		"\u0345\3\2\2\2\u0345\u0087\3\2\2\2\u0346\u0347\7\26\2\2\u0347\u0362\b"+
		"E\1\2\u0348\u0349\7L\2\2\u0349\u0362\bE\1\2\u034a\u034b\5\u008eH\2\u034b"+
		"\u034c\bE\1\2\u034c\u0362\3\2\2\2\u034d\u034e\7\13\2\2\u034e\u034f\5\u0088"+
		"E\2\u034f\u0350\bE\1\2\u0350\u0362\3\2\2\2\u0351\u0352\7\b\2\2\u0352\u0353"+
		"\5\u0088E\2\u0353\u0354\bE\1\2\u0354\u0362\3\2\2\2\u0355\u0356\7H\2\2"+
		"\u0356\u0357\5\u0088E\2\u0357\u0358\bE\1\2\u0358\u0362\3\2\2\2\u0359\u035a"+
		"\7\16\2\2\u035a\u035b\5\u0088E\2\u035b\u035c\7\5\2\2\u035c\u035d\bE\1"+
		"\2\u035d\u0362\3\2\2\2\u035e\u035f\5t;\2\u035f\u0360\bE\1\2\u0360\u0362"+
		"\3\2\2\2\u0361\u0346\3\2\2\2\u0361\u0348\3\2\2\2\u0361\u034a\3\2\2\2\u0361"+
		"\u034d\3\2\2\2\u0361\u0351\3\2\2\2\u0361\u0355\3\2\2\2\u0361\u0359\3\2"+
		"\2\2\u0361\u035e\3\2\2\2\u0362\u03ab\3\2\2\2\u0363\u0364\7\6\2\2\u0364"+
		"\u0365\5\u0088E\2\u0365\u0366\bE\1\2\u0366\u03ac\3\2\2\2\u0367\u0368\7"+
		"\b\2\2\u0368\u0369\5\u0088E\2\u0369\u036a\bE\1\2\u036a\u03ac\3\2\2\2\u036b"+
		"\u036c\7\13\2\2\u036c\u036d\5\u0088E\2\u036d\u036e\bE\1\2\u036e\u03ac"+
		"\3\2\2\2\u036f\u0370\7\7\2\2\u0370\u0371\5\u0088E\2\u0371\u0372\bE\1\2"+
		"\u0372\u03ac\3\2\2\2\u0373\u0374\7\t\2\2\u0374\u0375\5\u0088E\2\u0375"+
		"\u0376\bE\1\2\u0376\u03ac\3\2\2\2\u0377\u0378\7\21\2\2\u0378\u0379\5\u0088"+
		"E\2\u0379\u037a\bE\1\2\u037a\u03ac\3\2\2\2\u037b\u037c\7\4\2\2\u037c\u037d"+
		"\5\u0088E\2\u037d\u037e\bE\1\2\u037e\u03ac\3\2\2\2\u037f\u0380\7B\2\2"+
		"\u0380\u0381\5\u0088E\2\u0381\u0382\bE\1\2\u0382\u03ac\3\2\2\2\u0383\u0384"+
		"\7C\2\2\u0384\u0385\5\u0088E\2\u0385\u0386\bE\1\2\u0386\u03ac\3\2\2\2"+
		"\u0387\u0388\7D\2\2\u0388\u0389\5\u0088E\2\u0389\u038a\bE\1\2\u038a\u03ac"+
		"\3\2\2\2\u038b\u038c\7E\2\2\u038c\u038d\5\u0088E\2\u038d\u038e\bE\1\2"+
		"\u038e\u03ac\3\2\2\2\u038f\u0390\7F\2\2\u0390\u0391\5\u0088E\2\u0391\u0392"+
		"\bE\1\2\u0392\u03ac\3\2\2\2\u0393\u0394\7G\2\2\u0394\u0395\5\u0088E\2"+
		"\u0395\u0396\bE\1\2\u0396\u03ac\3\2\2\2\u0397\u0398\7I\2\2\u0398\u0399"+
		"\5\u0088E\2\u0399\u039a\bE\1\2\u039a\u03ac\3\2\2\2\u039b\u039c\7J\2\2"+
		"\u039c\u039d\5\u0088E\2\u039d\u039e\bE\1\2\u039e\u03ac\3\2\2\2\u039f\u03a0"+
		"\7K\2\2\u03a0\u03a1\5\u0088E\2\u03a1\u03a2\bE\1\2\u03a2\u03ac\3\2\2\2"+
		"\u03a3\u03a4\7%\2\2\u03a4\u03a5\5\u0088E\2\u03a5\u03a6\bE\1\2\u03a6\u03ac"+
		"\3\2\2\2\u03a7\u03a8\7&\2\2\u03a8\u03a9\5\u0088E\2\u03a9\u03aa\bE\1\2"+
		"\u03aa\u03ac\3\2\2\2\u03ab\u0363\3\2\2\2\u03ab\u0367\3\2\2\2\u03ab\u036b"+
		"\3\2\2\2\u03ab\u036f\3\2\2\2\u03ab\u0373\3\2\2\2\u03ab\u0377\3\2\2\2\u03ab"+
		"\u037b\3\2\2\2\u03ab\u037f\3\2\2\2\u03ab\u0383\3\2\2\2\u03ab\u0387\3\2"+
		"\2\2\u03ab\u038b\3\2\2\2\u03ab\u038f\3\2\2\2\u03ab\u0393\3\2\2\2\u03ab"+
		"\u0397\3\2\2\2\u03ab\u039b\3\2\2\2\u03ab\u039f\3\2\2\2\u03ab\u03a3\3\2"+
		"\2\2\u03ab\u03a7\3\2\2\2\u03ab\u03ac\3\2\2\2\u03ac\u0089\3\2\2\2\u03ad"+
		"\u03ae\7\26\2\2\u03ae\u03c9\bF\1\2\u03af\u03b0\7L\2\2\u03b0\u03c9\bF\1"+
		"\2\u03b1\u03b2\5\u008eH\2\u03b2\u03b3\bF\1\2\u03b3\u03c9\3\2\2\2\u03b4"+
		"\u03b5\7\13\2\2\u03b5\u03b6\5\u008aF\2\u03b6\u03b7\bF\1\2\u03b7\u03c9"+
		"\3\2\2\2\u03b8\u03b9\7\b\2\2\u03b9\u03ba\5\u008aF\2\u03ba\u03bb\bF\1\2"+
		"\u03bb\u03c9\3\2\2\2\u03bc\u03bd\7H\2\2\u03bd\u03be\5\u008aF\2\u03be\u03bf"+
		"\bF\1\2\u03bf\u03c9\3\2\2\2\u03c0\u03c1\7\16\2\2\u03c1\u03c2\5\u0088E"+
		"\2\u03c2\u03c3\7\5\2\2\u03c3\u03c4\bF\1\2\u03c4\u03c9\3\2\2\2\u03c5\u03c6"+
		"\5t;\2\u03c6\u03c7\bF\1\2\u03c7\u03c9\3\2\2\2\u03c8\u03ad\3\2\2\2\u03c8"+
		"\u03af\3\2\2\2\u03c8\u03b1\3\2\2\2\u03c8\u03b4\3\2\2\2\u03c8\u03b8\3\2"+
		"\2\2\u03c8\u03bc\3\2\2\2\u03c8\u03c0\3\2\2\2\u03c8\u03c5\3\2\2\2\u03c9"+
		"\u040e\3\2\2\2\u03ca\u03cb\7\b\2\2\u03cb\u03cc\5\u008aF\2\u03cc\u03cd"+
		"\bF\1\2\u03cd\u040f\3\2\2\2\u03ce\u03cf\7\13\2\2\u03cf\u03d0\5\u008aF"+
		"\2\u03d0\u03d1\bF\1\2\u03d1\u040f\3\2\2\2\u03d2\u03d3\7\7\2\2\u03d3\u03d4"+
		"\5\u008aF\2\u03d4\u03d5\bF\1\2\u03d5\u040f\3\2\2\2\u03d6\u03d7\7\t\2\2"+
		"\u03d7\u03d8\5\u008aF\2\u03d8\u03d9\bF\1\2\u03d9\u040f\3\2\2\2\u03da\u03db"+
		"\7\21\2\2\u03db\u03dc\5\u008aF\2\u03dc\u03dd\bF\1\2\u03dd\u040f\3\2\2"+
		"\2\u03de\u03df\7\4\2\2\u03df\u03e0\5\u008aF\2\u03e0\u03e1\bF\1\2\u03e1"+
		"\u040f\3\2\2\2\u03e2\u03e3\7B\2\2\u03e3\u03e4\5\u008aF\2\u03e4\u03e5\b"+
		"F\1\2\u03e5\u040f\3\2\2\2\u03e6\u03e7\7C\2\2\u03e7\u03e8\5\u008aF\2\u03e8"+
		"\u03e9\bF\1\2\u03e9\u040f\3\2\2\2\u03ea\u03eb\7D\2\2\u03eb\u03ec\5\u008a"+
		"F\2\u03ec\u03ed\bF\1\2\u03ed\u040f\3\2\2\2\u03ee\u03ef\7E\2\2\u03ef\u03f0"+
		"\5\u008aF\2\u03f0\u03f1\bF\1\2\u03f1\u040f\3\2\2\2\u03f2\u03f3\7F\2\2"+
		"\u03f3\u03f4\5\u008aF\2\u03f4\u03f5\bF\1\2\u03f5\u040f\3\2\2\2\u03f6\u03f7"+
		"\7G\2\2\u03f7\u03f8\5\u008aF\2\u03f8\u03f9\bF\1\2\u03f9\u040f\3\2\2\2"+
		"\u03fa\u03fb\7I\2\2\u03fb\u03fc\5\u008aF\2\u03fc\u03fd\bF\1\2\u03fd\u040f"+
		"\3\2\2\2\u03fe\u03ff\7J\2\2\u03ff\u0400\5\u008aF\2\u0400\u0401\bF\1\2"+
		"\u0401\u040f\3\2\2\2\u0402\u0403\7K\2\2\u0403\u0404\5\u008aF\2\u0404\u0405"+
		"\bF\1\2\u0405\u040f\3\2\2\2\u0406\u0407\7%\2\2\u0407\u0408\5\u008aF\2"+
		"\u0408\u0409\bF\1\2\u0409\u040f\3\2\2\2\u040a\u040b\7&\2\2\u040b\u040c"+
		"\5\u008aF\2\u040c\u040d\bF\1\2\u040d\u040f\3\2\2\2\u040e\u03ca\3\2\2\2"+
		"\u040e\u03ce\3\2\2\2\u040e\u03d2\3\2\2\2\u040e\u03d6\3\2\2\2\u040e\u03da"+
		"\3\2\2\2\u040e\u03de\3\2\2\2\u040e\u03e2\3\2\2\2\u040e\u03e6\3\2\2\2\u040e"+
		"\u03ea\3\2\2\2\u040e\u03ee\3\2\2\2\u040e\u03f2\3\2\2\2\u040e\u03f6\3\2"+
		"\2\2\u040e\u03fa\3\2\2\2\u040e\u03fe\3\2\2\2\u040e\u0402\3\2\2\2\u040e"+
		"\u0406\3\2\2\2\u040e\u040a\3\2\2\2\u040e\u040f\3\2\2\2\u040f\u008b\3\2"+
		"\2\2\u0410\u0411\7Q\2\2\u0411\u0412\7\16\2\2\u0412\u0413\7\5\2\2\u0413"+
		"\u042d\bG\1\2\u0414\u0415\7Q\2\2\u0415\u0416\7\16\2\2\u0416\u0417\5\u0084"+
		"C\2\u0417\u0418\7\5\2\2\u0418\u0419\bG\1\2\u0419\u042d\3\2\2\2\u041a\u041b"+
		"\7T\2\2\u041b\u041c\7\16\2\2\u041c\u041d\5\u0088E\2\u041d\u041e\7\5\2"+
		"\2\u041e\u041f\bG\1\2\u041f\u042d\3\2\2\2\u0420\u0421\7!\2\2\u0421\u0422"+
		"\7\16\2\2\u0422\u0423\5\u0088E\2\u0423\u0424\7\5\2\2\u0424\u0425\bG\1"+
		"\2\u0425\u042d\3\2\2\2\u0426\u0427\5z>\2\u0427\u0428\5|?\2\u0428\u0429"+
		"\bG\1\2\u0429\u042d\3\2\2\2\u042a\u042b\7Q\2\2\u042b\u042d\bG\1\2\u042c"+
		"\u0410\3\2\2\2\u042c\u0414\3\2\2\2\u042c\u041a\3\2\2\2\u042c\u0420\3\2"+
		"\2\2\u042c\u0426\3\2\2\2\u042c\u042a\3\2\2\2\u042d\u008d\3\2\2\2\u042e"+
		"\u042f\7Q\2\2\u042f\u0430\7\16\2\2\u0430\u0431\7\5\2\2\u0431\u0450\bH"+
		"\1\2\u0432\u0433\7Q\2\2\u0433\u0434\7\16\2\2\u0434\u0435\5\u0084C\2\u0435"+
		"\u0436\7\5\2\2\u0436\u0437\bH\1\2\u0437\u0450\3\2\2\2\u0438\u0439\7T\2"+
		"\2\u0439\u043a\7\16\2\2\u043a\u043b\5\u0088E\2\u043b\u043c\7\5\2\2\u043c"+
		"\u043d\bH\1\2\u043d\u0450\3\2\2\2\u043e\u043f\7!\2\2\u043f\u0440\7\16"+
		"\2\2\u0440\u0441\5\u0088E\2\u0441\u0442\7\5\2\2\u0442\u0443\bH\1\2\u0443"+
		"\u0450\3\2\2\2\u0444\u0445\7Q\2\2\u0445\u0446\7\16\2\2\u0446\u0447\7A"+
		"\2\2\u0447\u0448\7\5\2\2\u0448\u0450\bH\1\2\u0449\u044a\5z>\2\u044a\u044b"+
		"\5|?\2\u044b\u044c\bH\1\2\u044c\u0450\3\2\2\2\u044d\u044e\7Q\2\2\u044e"+
		"\u0450\bH\1\2\u044f\u042e\3\2\2\2\u044f\u0432\3\2\2\2\u044f\u0438\3\2"+
		"\2\2\u044f\u043e\3\2\2\2\u044f\u0444\3\2\2\2\u044f\u0449\3\2\2\2\u044f"+
		"\u044d\3\2\2\2\u0450\u008f\3\2\2\2\u0451\u0452\7Q\2\2\u0452\u0456\bI\1"+
		"\2\u0453\u0454\7T\2\2\u0454\u0456\bI\1\2\u0455\u0451\3\2\2\2\u0455\u0453"+
		"\3\2\2\2\u0456\u0091\3\2\2\2\u0457\u0458\5\u0096L\2\u0458\u0459\5\u0088"+
		"E\2\u0459\u045a\bJ\1\2\u045a\u0093\3\2\2\2\u045b\u045c\7\36\2\2\u045c"+
		"\u045d\7Q\2\2\u045d\u045e\5> \2\u045e\u045f\7A\2\2\u045f\u0460\bK\1\2"+
		"\u0460\u047e\3\2\2\2\u0461\u0462\7Q\2\2\u0462\u0463\5> \2\u0463\u0464"+
		"\7A\2\2\u0464\u0465\bK\1\2\u0465\u047e\3\2\2\2\u0466\u0467\7Q\2\2\u0467"+
		"\u0468\7Q\2\2\u0468\u0469\5> \2\u0469\u046a\7A\2\2\u046a\u046b\bK\1\2"+
		"\u046b\u047e\3\2\2\2\u046c\u046d\7\36\2\2\u046d\u046e\7P\2\2\u046e\u046f"+
		"\5> \2\u046f\u0470\7A\2\2\u0470\u0471\bK\1\2\u0471\u047e\3\2\2\2\u0472"+
		"\u0473\7P\2\2\u0473\u0474\5> \2\u0474\u0475\7A\2\2\u0475\u0476\bK\1\2"+
		"\u0476\u047e\3\2\2\2\u0477\u0478\7Q\2\2\u0478\u0479\7P\2\2\u0479\u047a"+
		"\5> \2\u047a\u047b\7A\2\2\u047b\u047c\bK\1\2\u047c\u047e\3\2\2\2\u047d"+
		"\u045b\3\2\2\2\u047d\u0461\3\2\2\2\u047d\u0466\3\2\2\2\u047d\u046c\3\2"+
		"\2\2\u047d\u0472\3\2\2\2\u047d\u0477\3\2\2\2\u047e\u0095\3\2\2\2\u047f"+
		"\u0480\5\u0094K\2\u0480\u0481\bL\1\2\u0481\u0486\3\2\2\2\u0482\u0483\5"+
		"\u0098M\2\u0483\u0484\bL\1\2\u0484\u0486\3\2\2\2\u0485\u047f\3\2\2\2\u0485"+
		"\u0482\3\2\2\2\u0486\u0097\3\2\2\2\u0487\u0488\7Q\2\2\u0488\u0489\7A\2"+
		"\2\u0489\u048e\bM\1\2\u048a\u048b\7P\2\2\u048b\u048c\7A\2\2\u048c\u048e"+
		"\bM\1\2\u048d\u0487\3\2\2\2\u048d\u048a\3\2\2\2\u048e\u0099\3\2\2\2\u048f"+
		"\u0490\5\u0098M\2\u0490\u0491\5\u0088E\2\u0491\u0492\bN\1\2\u0492\u009b"+
		"\3\2\2\29\u00a0\u00a9\u00b2\u00c1\u00d6\u00f1\u00fc\u0104\u0112\u0118"+
		"\u012a\u013a\u0151\u015e\u018b\u0195\u019e\u01a6\u01b5\u01be\u01c8\u01d5"+
		"\u01f8\u0202\u020c\u0212\u023c\u0244\u0250\u025f\u026b\u0275\u0282\u029a"+
		"\u02af\u02bf\u02cb\u02d9\u02e4\u02f6\u030a\u031a\u0324\u033a\u0344\u0361"+
		"\u03ab\u03c8\u040e\u042c\u044f\u0455\u047d\u0485\u048d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}