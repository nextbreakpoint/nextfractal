// Generated from Mandelbrot.g4 by ANTLR 4.2.2
package com.nextbreakpoint.nextfractal.mandelbrot.grammar;


import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MandelbrotParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__51=1, T__50=2, T__49=3, T__48=4, T__47=5, T__46=6, T__45=7, T__44=8, 
		T__43=9, T__42=10, T__41=11, T__40=12, T__39=13, T__38=14, T__37=15, T__36=16, 
		T__35=17, T__34=18, T__33=19, T__32=20, T__31=21, T__30=22, T__29=23, 
		T__28=24, T__27=25, T__26=26, T__25=27, T__24=28, T__23=29, T__22=30, 
		T__21=31, T__20=32, T__19=33, T__18=34, T__17=35, T__16=36, T__15=37, 
		T__14=38, T__13=39, T__12=40, T__11=41, T__10=42, T__9=43, T__8=44, T__7=45, 
		T__6=46, T__5=47, T__4=48, T__3=49, T__2=50, T__1=51, T__0=52, FRACTAL=53, 
		ORBIT=54, TRAP=55, BEGIN=56, LOOP=57, END=58, INIT=59, IF=60, STOP=61, 
		JULIA=62, COLOR=63, PALETTE=64, RULE=65, ARGB=66, RATIONAL=67, INTEGER=68, 
		PATHOP_1POINTS=69, PATHOP_2POINTS=70, PATHOP_3POINTS=71, VARIABLE=72, 
		COMMENT=73, WHITESPACE=74;
	public static final String[] tokenNames = {
		"<INVALID>", "'cos'", "'{'", "'='", "'asin'", "'^'", "'im'", "'('", "'min'", 
		"','", "'pha'", "'re'", "'atan'", "'sqrt'", "'pi'", "'ceil'", "'mod'", 
		"'>='", "'log'", "'<'", "']'", "'~'", "'abs'", "'<>'", "'#'", "'e'", "'floor'", 
		"'i'", "'+'", "'/'", "'2pi'", "';'", "'max'", "'}'", "'mod2'", "'?'", 
		"'sin'", "'<='", "'pow'", "'~?'", "'&'", "'*'", "'tan'", "'atan2'", "'['", 
		"'|'", "'>'", "'acos'", "'else'", "')'", "'exp'", "'hypot'", "'-'", "'fractal'", 
		"'orbit'", "'trap'", "'begin'", "'loop'", "'end'", "'init'", "'if'", "'stop'", 
		"'julia'", "'color'", "'palette'", "'rule'", "ARGB", "RATIONAL", "INTEGER", 
		"PATHOP_1POINTS", "PATHOP_2POINTS", "PATHOP_3POINTS", "VARIABLE", "COMMENT", 
		"WHITESPACE"
	};
	public static final int
		RULE_fractal = 0, RULE_orbit = 1, RULE_color = 2, RULE_begin = 3, RULE_loop = 4, 
		RULE_end = 5, RULE_trap = 6, RULE_pathop = 7, RULE_beginstatement = 8, 
		RULE_loopstatement = 9, RULE_endstatement = 10, RULE_statement = 11, RULE_variablelist = 12, 
		RULE_simpleconditionexp = 13, RULE_conditionexp = 14, RULE_conditionexp2 = 15, 
		RULE_conditionexp3 = 16, RULE_conditionexp4 = 17, RULE_simpleexpression = 18, 
		RULE_expression = 19, RULE_expression2 = 20, RULE_expression3 = 21, RULE_expression4 = 22, 
		RULE_function = 23, RULE_constant = 24, RULE_variable = 25, RULE_real = 26, 
		RULE_complex = 27, RULE_palette = 28, RULE_paletteelement = 29, RULE_colorinit = 30, 
		RULE_colorstatement = 31, RULE_colorrule = 32, RULE_ruleexp = 33, RULE_colorexp = 34, 
		RULE_colorargb = 35, RULE_eof = 36;
	public static final String[] ruleNames = {
		"fractal", "orbit", "color", "begin", "loop", "end", "trap", "pathop", 
		"beginstatement", "loopstatement", "endstatement", "statement", "variablelist", 
		"simpleconditionexp", "conditionexp", "conditionexp2", "conditionexp3", 
		"conditionexp4", "simpleexpression", "expression", "expression2", "expression3", 
		"expression4", "function", "constant", "variable", "real", "complex", 
		"palette", "paletteelement", "colorinit", "colorstatement", "colorrule", 
		"ruleexp", "colorexp", "colorargb", "eof"
	};

	@Override
	public String getGrammarFileName() { return "Mandelbrot.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


		private ASTBuilder builder = new ASTBuilder();
		
		public ASTBuilder getBuilder() { return builder; }

	public MandelbrotParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FractalContext extends ParserRuleContext {
		public Token f;
		public OrbitContext orbit() {
			return getRuleContext(OrbitContext.class,0);
		}
		public ColorContext color() {
			return getRuleContext(ColorContext.class,0);
		}
		public TerminalNode FRACTAL() { return getToken(MandelbrotParser.FRACTAL, 0); }
		public EofContext eof() {
			return getRuleContext(EofContext.class,0);
		}
		public FractalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fractal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterFractal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitFractal(this);
		}
	}

	public final FractalContext fractal() throws RecognitionException {
		FractalContext _localctx = new FractalContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_fractal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74); ((FractalContext)_localctx).f = match(FRACTAL);

					builder.setFractal(new ASTFractal(((FractalContext)_localctx).f));
				
			setState(76); match(2);
			setState(77); orbit();
			setState(78); color();
			setState(79); match(33);
			setState(80); eof();
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

	public static class OrbitContext extends ParserRuleContext {
		public Token o;
		public ComplexContext ra;
		public ComplexContext rb;
		public VariablelistContext v;
		public TerminalNode ORBIT() { return getToken(MandelbrotParser.ORBIT, 0); }
		public ComplexContext complex(int i) {
			return getRuleContext(ComplexContext.class,i);
		}
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public VariablelistContext variablelist() {
			return getRuleContext(VariablelistContext.class,0);
		}
		public BeginContext begin() {
			return getRuleContext(BeginContext.class,0);
		}
		public TrapContext trap(int i) {
			return getRuleContext(TrapContext.class,i);
		}
		public List<TrapContext> trap() {
			return getRuleContexts(TrapContext.class);
		}
		public List<ComplexContext> complex() {
			return getRuleContexts(ComplexContext.class);
		}
		public OrbitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orbit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterOrbit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitOrbit(this);
		}
	}

	public final OrbitContext orbit() throws RecognitionException {
		OrbitContext _localctx = new OrbitContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_orbit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82); ((OrbitContext)_localctx).o = match(ORBIT);
			setState(83); match(44);
			setState(84); ((OrbitContext)_localctx).ra = complex();
			setState(85); match(9);
			setState(86); ((OrbitContext)_localctx).rb = complex();
			setState(87); match(20);

					builder.setOrbit(new ASTOrbit(((OrbitContext)_localctx).o, new ASTRegion(((OrbitContext)_localctx).ra.result, ((OrbitContext)_localctx).rb.result)));
				
			setState(89); match(44);
			setState(90); ((OrbitContext)_localctx).v = variablelist(0);
			setState(91); match(20);
			setState(92); match(2);
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TRAP) {
				{
				{
				setState(93); trap();
				}
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(100);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(99); begin();
				}
			}

			setState(102); loop();
			setState(104);
			_la = _input.LA(1);
			if (_la==END) {
				{
				setState(103); end();
				}
			}

			setState(106); match(33);
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

	public static class ColorContext extends ParserRuleContext {
		public Token c;
		public ColorargbContext argb;
		public TerminalNode COLOR() { return getToken(MandelbrotParser.COLOR, 0); }
		public List<PaletteContext> palette() {
			return getRuleContexts(PaletteContext.class);
		}
		public ColorinitContext colorinit() {
			return getRuleContext(ColorinitContext.class,0);
		}
		public List<ColorruleContext> colorrule() {
			return getRuleContexts(ColorruleContext.class);
		}
		public ColorargbContext colorargb() {
			return getRuleContext(ColorargbContext.class,0);
		}
		public PaletteContext palette(int i) {
			return getRuleContext(PaletteContext.class,i);
		}
		public ColorruleContext colorrule(int i) {
			return getRuleContext(ColorruleContext.class,i);
		}
		public ColorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_color; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterColor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitColor(this);
		}
	}

	public final ColorContext color() throws RecognitionException {
		ColorContext _localctx = new ColorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_color);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108); ((ColorContext)_localctx).c = match(COLOR);
			setState(109); match(44);
			setState(110); ((ColorContext)_localctx).argb = colorargb();
			setState(111); match(20);
			 
					builder.setColor(new ASTColor(((ColorContext)_localctx).c, ((ColorContext)_localctx).argb.result));
				
			setState(113); match(2);
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PALETTE) {
				{
				{
				setState(114); palette();
				}
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121);
			_la = _input.LA(1);
			if (_la==INIT) {
				{
				setState(120); colorinit();
				}
			}

			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RULE) {
				{
				{
				setState(123); colorrule();
				}
				}
				setState(128);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(129); match(33);
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

	public static class BeginContext extends ParserRuleContext {
		public Token b;
		public List<BeginstatementContext> beginstatement() {
			return getRuleContexts(BeginstatementContext.class);
		}
		public TerminalNode BEGIN() { return getToken(MandelbrotParser.BEGIN, 0); }
		public BeginstatementContext beginstatement(int i) {
			return getRuleContext(BeginstatementContext.class,i);
		}
		public BeginContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_begin; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterBegin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitBegin(this);
		}
	}

	public final BeginContext begin() throws RecognitionException {
		BeginContext _localctx = new BeginContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_begin);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131); ((BeginContext)_localctx).b = match(BEGIN);
			 
					builder.setOrbitBegin(new ASTOrbitBegin(((BeginContext)_localctx).b));
				
			setState(133); match(2);
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
				{
				{
				setState(134); beginstatement();
				}
				}
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(140); match(33);
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

	public static class LoopContext extends ParserRuleContext {
		public Token l;
		public Token lb;
		public Token le;
		public ConditionexpContext e;
		public LoopstatementContext loopstatement(int i) {
			return getRuleContext(LoopstatementContext.class,i);
		}
		public List<TerminalNode> INTEGER() { return getTokens(MandelbrotParser.INTEGER); }
		public TerminalNode LOOP() { return getToken(MandelbrotParser.LOOP, 0); }
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public List<LoopstatementContext> loopstatement() {
			return getRuleContexts(LoopstatementContext.class);
		}
		public TerminalNode INTEGER(int i) {
			return getToken(MandelbrotParser.INTEGER, i);
		}
		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitLoop(this);
		}
	}

	public final LoopContext loop() throws RecognitionException {
		LoopContext _localctx = new LoopContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_loop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142); ((LoopContext)_localctx).l = match(LOOP);
			setState(143); match(44);
			setState(144); ((LoopContext)_localctx).lb = match(INTEGER);
			setState(145); match(9);
			setState(146); ((LoopContext)_localctx).le = match(INTEGER);
			setState(147); match(20);
			setState(148); match(7);
			setState(149); ((LoopContext)_localctx).e = conditionexp(0);
			setState(150); match(49);

					builder.setOrbitLoop(new ASTOrbitLoop(((LoopContext)_localctx).l, builder.parseInt((((LoopContext)_localctx).lb!=null?((LoopContext)_localctx).lb.getText():null)), builder.parseInt((((LoopContext)_localctx).le!=null?((LoopContext)_localctx).le.getText():null)), ((LoopContext)_localctx).e.result));
				
			setState(152); match(2);
			setState(156);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
				{
				{
				setState(153); loopstatement();
				}
				}
				setState(158);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(159); match(33);
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

	public static class EndContext extends ParserRuleContext {
		public Token e;
		public List<EndstatementContext> endstatement() {
			return getRuleContexts(EndstatementContext.class);
		}
		public TerminalNode END() { return getToken(MandelbrotParser.END, 0); }
		public EndstatementContext endstatement(int i) {
			return getRuleContext(EndstatementContext.class,i);
		}
		public EndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_end; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterEnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitEnd(this);
		}
	}

	public final EndContext end() throws RecognitionException {
		EndContext _localctx = new EndContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_end);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161); ((EndContext)_localctx).e = match(END);

					builder.setOrbitEnd(new ASTOrbitEnd(((EndContext)_localctx).e));		
				
			setState(163); match(2);
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
				{
				{
				setState(164); endstatement();
				}
				}
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(170); match(33);
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

	public static class TrapContext extends ParserRuleContext {
		public Token t;
		public Token n;
		public ComplexContext c;
		public TerminalNode TRAP() { return getToken(MandelbrotParser.TRAP, 0); }
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public PathopContext pathop(int i) {
			return getRuleContext(PathopContext.class,i);
		}
		public ComplexContext complex() {
			return getRuleContext(ComplexContext.class,0);
		}
		public List<PathopContext> pathop() {
			return getRuleContexts(PathopContext.class);
		}
		public TrapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trap; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterTrap(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitTrap(this);
		}
	}

	public final TrapContext trap() throws RecognitionException {
		TrapContext _localctx = new TrapContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_trap);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172); ((TrapContext)_localctx).t = match(TRAP);
			setState(173); ((TrapContext)_localctx).n = match(VARIABLE);
			setState(174); match(44);
			setState(175); ((TrapContext)_localctx).c = complex();
			setState(176); match(20);

					builder.addOrbitTrap(new ASTOrbitTrap(((TrapContext)_localctx).t, (((TrapContext)_localctx).n!=null?((TrapContext)_localctx).n.getText():null), ((TrapContext)_localctx).c.result));
				
			setState(178); match(2);
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 69)) & ~0x3f) == 0 && ((1L << (_la - 69)) & ((1L << (PATHOP_1POINTS - 69)) | (1L << (PATHOP_2POINTS - 69)) | (1L << (PATHOP_3POINTS - 69)))) != 0)) {
				{
				{
				setState(179); pathop();
				}
				}
				setState(184);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(185); match(33);
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

	public static class PathopContext extends ParserRuleContext {
		public Token o;
		public ComplexContext c;
		public ComplexContext c1;
		public ComplexContext c2;
		public ComplexContext c3;
		public ComplexContext complex(int i) {
			return getRuleContext(ComplexContext.class,i);
		}
		public TerminalNode PATHOP_3POINTS() { return getToken(MandelbrotParser.PATHOP_3POINTS, 0); }
		public TerminalNode PATHOP_2POINTS() { return getToken(MandelbrotParser.PATHOP_2POINTS, 0); }
		public TerminalNode PATHOP_1POINTS() { return getToken(MandelbrotParser.PATHOP_1POINTS, 0); }
		public List<ComplexContext> complex() {
			return getRuleContexts(ComplexContext.class);
		}
		public PathopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterPathop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitPathop(this);
		}
	}

	public final PathopContext pathop() throws RecognitionException {
		PathopContext _localctx = new PathopContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_pathop);
		try {
			setState(214);
			switch (_input.LA(1)) {
			case PATHOP_1POINTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(187); ((PathopContext)_localctx).o = match(PATHOP_1POINTS);
				setState(188); match(7);
				setState(189); ((PathopContext)_localctx).c = complex();
				setState(190); match(49);
				setState(191); match(31);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c.result));
					
				}
				break;
			case PATHOP_2POINTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(194); ((PathopContext)_localctx).o = match(PATHOP_2POINTS);
				setState(195); match(7);
				setState(196); ((PathopContext)_localctx).c1 = complex();
				setState(197); match(9);
				setState(198); ((PathopContext)_localctx).c2 = complex();
				setState(199); match(49);
				setState(200); match(31);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c1.result, ((PathopContext)_localctx).c2.result));
					
				}
				break;
			case PATHOP_3POINTS:
				enterOuterAlt(_localctx, 3);
				{
				setState(203); ((PathopContext)_localctx).o = match(PATHOP_3POINTS);
				setState(204); match(7);
				setState(205); ((PathopContext)_localctx).c1 = complex();
				setState(206); match(9);
				setState(207); ((PathopContext)_localctx).c2 = complex();
				setState(208); match(9);
				setState(209); ((PathopContext)_localctx).c3 = complex();
				setState(210); match(49);
				setState(211); match(31);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c1.result, ((PathopContext)_localctx).c2.result, ((PathopContext)_localctx).c3.result));
					
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

	public static class BeginstatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public BeginstatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_beginstatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterBeginstatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitBeginstatement(this);
		}
	}

	public final BeginstatementContext beginstatement() throws RecognitionException {
		BeginstatementContext _localctx = new BeginstatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_beginstatement);
		try {
			enterOuterAlt(_localctx, 1);
			{

					builder.pushStatementList();	
				
			setState(217); statement();

					builder.addBeginStatements(builder.getStatementList());
					builder.popStatementList();	
				
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

	public static class LoopstatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public LoopstatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopstatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterLoopstatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitLoopstatement(this);
		}
	}

	public final LoopstatementContext loopstatement() throws RecognitionException {
		LoopstatementContext _localctx = new LoopstatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_loopstatement);
		try {
			enterOuterAlt(_localctx, 1);
			{

					builder.pushStatementList();	
				
			setState(221); statement();

					builder.addLoopStatements(builder.getStatementList());
					builder.popStatementList();	
				
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

	public static class EndstatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public EndstatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endstatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterEndstatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitEndstatement(this);
		}
	}

	public final EndstatementContext endstatement() throws RecognitionException {
		EndstatementContext _localctx = new EndstatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_endstatement);
		try {
			enterOuterAlt(_localctx, 1);
			{

					builder.pushStatementList();	
				
			setState(225); statement();

					builder.addEndStatements(builder.getStatementList());
					builder.popStatementList();	
				
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

	public static class StatementContext extends ParserRuleContext {
		public Token v;
		public ExpressionContext e;
		public Token f;
		public ConditionexpContext c;
		public Token t;
		public TerminalNode IF() { return getToken(MandelbrotParser.IF, 0); }
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public TerminalNode STOP() { return getToken(MandelbrotParser.STOP, 0); }
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_statement);
		int _la;
		try {
			setState(282);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(228); ((StatementContext)_localctx).v = match(VARIABLE);
				setState(229); match(3);
				setState(230); ((StatementContext)_localctx).e = expression(0);
				setState(232);
				_la = _input.LA(1);
				if (_la==31) {
					{
					setState(231); match(31);
					}
				}


						builder.registerVariable((((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result.isReal(), ((StatementContext)_localctx).v);
						builder.appendStatement(new ASTAssignStatement(((StatementContext)_localctx).v, (((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(236); ((StatementContext)_localctx).f = match(IF);
				setState(237); match(7);
				setState(238); ((StatementContext)_localctx).c = conditionexp(0);
				setState(239); match(49);
				setState(240); match(2);

						builder.pushStatementList();
					
				setState(245);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
					{
					{
					setState(242); statement();
					}
					}
					setState(247);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(248); match(33);

						ASTStatementList thenList = builder.getStatementList();
						builder.popStatementList();
					
				setState(250); match(48);
				setState(251); match(2);

						builder.pushStatementList();
					
				setState(256);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
					{
					{
					setState(253); statement();
					}
					}
					setState(258);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(259); match(33);

						ASTStatementList elseList = builder.getStatementList();
						builder.popStatementList();
						builder.appendStatement(new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, thenList, elseList));
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(262); ((StatementContext)_localctx).f = match(IF);
				setState(263); match(7);
				setState(264); ((StatementContext)_localctx).c = conditionexp(0);
				setState(265); match(49);
				setState(266); match(2);

						builder.pushStatementList();
					
				setState(271);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
					{
					{
					setState(268); statement();
					}
					}
					setState(273);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(274); match(33);

						ASTStatementList thenList = builder.getStatementList();
						builder.popStatementList();
						builder.appendStatement(new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, thenList));
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(277); ((StatementContext)_localctx).t = match(STOP);
				setState(279);
				_la = _input.LA(1);
				if (_la==31) {
					{
					setState(278); match(31);
					}
				}


						builder.appendStatement(new ASTStopStatement(((StatementContext)_localctx).t));
					
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

	public static class VariablelistContext extends ParserRuleContext {
		public VariablelistContext vl;
		public Token v;
		public VariablelistContext variablelist() {
			return getRuleContext(VariablelistContext.class,0);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public VariablelistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variablelist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterVariablelist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitVariablelist(this);
		}
	}

	public final VariablelistContext variablelist() throws RecognitionException {
		return variablelist(0);
	}

	private VariablelistContext variablelist(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		VariablelistContext _localctx = new VariablelistContext(_ctx, _parentState);
		VariablelistContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_variablelist, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(285); ((VariablelistContext)_localctx).v = match(VARIABLE);

					builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(294);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VariablelistContext(_parentctx, _parentState);
					_localctx.vl = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_variablelist);
					setState(288);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(289); match(9);
					setState(290); ((VariablelistContext)_localctx).v = match(VARIABLE);

					          		builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
					          	
					}
					} 
				}
				setState(296);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
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

	public static class SimpleconditionexpContext extends ParserRuleContext {
		public ASTConditionExpression result;
		public ExpressionContext e1;
		public Token o;
		public ExpressionContext e2;
		public Token v;
		public ExpressionContext e;
		public Token t;
		public Token s;
		public ConditionexpContext c;
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public TerminalNode JULIA() { return getToken(MandelbrotParser.JULIA, 0); }
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public SimpleconditionexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleconditionexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterSimpleconditionexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitSimpleconditionexp(this);
		}
	}

	public final SimpleconditionexpContext simpleconditionexp() throws RecognitionException {
		SimpleconditionexpContext _localctx = new SimpleconditionexpContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_simpleconditionexp);
		int _la;
		try {
			setState(319);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(297); ((SimpleconditionexpContext)_localctx).e1 = expression(0);
				setState(298);
				((SimpleconditionexpContext)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 23) | (1L << 37) | (1L << 46))) != 0)) ) {
					((SimpleconditionexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(299); ((SimpleconditionexpContext)_localctx).e2 = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionCompareOp(((SimpleconditionexpContext)_localctx).e1.result.getLocation(), (((SimpleconditionexpContext)_localctx).o!=null?((SimpleconditionexpContext)_localctx).o.getText():null), ((SimpleconditionexpContext)_localctx).e1.result, ((SimpleconditionexpContext)_localctx).e2.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(302); ((SimpleconditionexpContext)_localctx).v = match(VARIABLE);
				setState(303); match(35);
				setState(304); ((SimpleconditionexpContext)_localctx).e = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionTrap(((SimpleconditionexpContext)_localctx).v, (((SimpleconditionexpContext)_localctx).v!=null?((SimpleconditionexpContext)_localctx).v.getText():null), ((SimpleconditionexpContext)_localctx).e.result, true);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(307); ((SimpleconditionexpContext)_localctx).v = match(VARIABLE);
				setState(308); match(39);
				setState(309); ((SimpleconditionexpContext)_localctx).e = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionTrap(((SimpleconditionexpContext)_localctx).v, (((SimpleconditionexpContext)_localctx).v!=null?((SimpleconditionexpContext)_localctx).v.getText():null), ((SimpleconditionexpContext)_localctx).e.result, false);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(312); ((SimpleconditionexpContext)_localctx).t = match(JULIA);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionJulia(((SimpleconditionexpContext)_localctx).t);
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(314); ((SimpleconditionexpContext)_localctx).s = match(7);
				setState(315); ((SimpleconditionexpContext)_localctx).c = conditionexp(0);
				setState(316); match(49);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionParen(((SimpleconditionexpContext)_localctx).s, ((SimpleconditionexpContext)_localctx).c.result);
					
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

	public static class ConditionexpContext extends ParserRuleContext {
		public ASTConditionExpression result;
		public ConditionexpContext c1;
		public SimpleconditionexpContext c;
		public Conditionexp2Context c2;
		public Token l;
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public SimpleconditionexpContext simpleconditionexp() {
			return getRuleContext(SimpleconditionexpContext.class,0);
		}
		public Conditionexp2Context conditionexp2() {
			return getRuleContext(Conditionexp2Context.class,0);
		}
		public ConditionexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterConditionexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitConditionexp(this);
		}
	}

	public final ConditionexpContext conditionexp() throws RecognitionException {
		return conditionexp(0);
	}

	private ConditionexpContext conditionexp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConditionexpContext _localctx = new ConditionexpContext(_ctx, _parentState);
		ConditionexpContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_conditionexp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(328);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(322); ((ConditionexpContext)_localctx).c = simpleconditionexp();

						((ConditionexpContext)_localctx).result =  ((ConditionexpContext)_localctx).c.result;
					
				}
				break;

			case 2:
				{
				setState(325); ((ConditionexpContext)_localctx).c2 = conditionexp2(0);

						((ConditionexpContext)_localctx).result =  ((ConditionexpContext)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(337);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConditionexpContext(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp);
					setState(330);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(331); ((ConditionexpContext)_localctx).l = match(45);
					setState(332); ((ConditionexpContext)_localctx).c2 = conditionexp2(0);

					          		((ConditionexpContext)_localctx).result =  new ASTConditionLogicOp(((ConditionexpContext)_localctx).c1.result.getLocation(), (((ConditionexpContext)_localctx).l!=null?((ConditionexpContext)_localctx).l.getText():null), ((ConditionexpContext)_localctx).c1.result, ((ConditionexpContext)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(339);
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

	public static class Conditionexp2Context extends ParserRuleContext {
		public ASTConditionExpression result;
		public Conditionexp2Context c1;
		public SimpleconditionexpContext c;
		public Conditionexp3Context c2;
		public Token l;
		public Conditionexp3Context conditionexp3() {
			return getRuleContext(Conditionexp3Context.class,0);
		}
		public SimpleconditionexpContext simpleconditionexp() {
			return getRuleContext(SimpleconditionexpContext.class,0);
		}
		public Conditionexp2Context conditionexp2() {
			return getRuleContext(Conditionexp2Context.class,0);
		}
		public Conditionexp2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionexp2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterConditionexp2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitConditionexp2(this);
		}
	}

	public final Conditionexp2Context conditionexp2() throws RecognitionException {
		return conditionexp2(0);
	}

	private Conditionexp2Context conditionexp2(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Conditionexp2Context _localctx = new Conditionexp2Context(_ctx, _parentState);
		Conditionexp2Context _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_conditionexp2, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(341); ((Conditionexp2Context)_localctx).c = simpleconditionexp();

						((Conditionexp2Context)_localctx).result =  ((Conditionexp2Context)_localctx).c.result;
					
				}
				break;

			case 2:
				{
				setState(344); ((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

						((Conditionexp2Context)_localctx).result =  ((Conditionexp2Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(356);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Conditionexp2Context(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp2);
					setState(349);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(350); ((Conditionexp2Context)_localctx).l = match(5);
					setState(351); ((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

					          		((Conditionexp2Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp2Context)_localctx).c1.result.getLocation(), (((Conditionexp2Context)_localctx).l!=null?((Conditionexp2Context)_localctx).l.getText():null), ((Conditionexp2Context)_localctx).c1.result, ((Conditionexp2Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(358);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
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

	public static class Conditionexp3Context extends ParserRuleContext {
		public ASTConditionExpression result;
		public Conditionexp3Context c1;
		public SimpleconditionexpContext c;
		public Conditionexp4Context c2;
		public Token l;
		public Conditionexp3Context conditionexp3() {
			return getRuleContext(Conditionexp3Context.class,0);
		}
		public SimpleconditionexpContext simpleconditionexp() {
			return getRuleContext(SimpleconditionexpContext.class,0);
		}
		public Conditionexp4Context conditionexp4() {
			return getRuleContext(Conditionexp4Context.class,0);
		}
		public Conditionexp3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionexp3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterConditionexp3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitConditionexp3(this);
		}
	}

	public final Conditionexp3Context conditionexp3() throws RecognitionException {
		return conditionexp3(0);
	}

	private Conditionexp3Context conditionexp3(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Conditionexp3Context _localctx = new Conditionexp3Context(_ctx, _parentState);
		Conditionexp3Context _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_conditionexp3, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(360); ((Conditionexp3Context)_localctx).c = simpleconditionexp();

						((Conditionexp3Context)_localctx).result =  ((Conditionexp3Context)_localctx).c.result;
					
				}
				break;

			case 2:
				{
				setState(363); ((Conditionexp3Context)_localctx).c2 = conditionexp4();

						((Conditionexp3Context)_localctx).result =  ((Conditionexp3Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(375);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Conditionexp3Context(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp3);
					setState(368);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(369); ((Conditionexp3Context)_localctx).l = match(40);
					setState(370); ((Conditionexp3Context)_localctx).c2 = conditionexp4();

					          		((Conditionexp3Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp3Context)_localctx).c1.result.getLocation(), (((Conditionexp3Context)_localctx).l!=null?((Conditionexp3Context)_localctx).l.getText():null), ((Conditionexp3Context)_localctx).c1.result, ((Conditionexp3Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(377);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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

	public static class Conditionexp4Context extends ParserRuleContext {
		public ASTConditionExpression result;
		public SimpleconditionexpContext c1;
		public Token n;
		public Conditionexp4Context c2;
		public SimpleconditionexpContext simpleconditionexp() {
			return getRuleContext(SimpleconditionexpContext.class,0);
		}
		public Conditionexp4Context conditionexp4() {
			return getRuleContext(Conditionexp4Context.class,0);
		}
		public Conditionexp4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionexp4; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterConditionexp4(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitConditionexp4(this);
		}
	}

	public final Conditionexp4Context conditionexp4() throws RecognitionException {
		Conditionexp4Context _localctx = new Conditionexp4Context(_ctx, getState());
		enterRule(_localctx, 34, RULE_conditionexp4);
		try {
			setState(385);
			switch (_input.LA(1)) {
			case 1:
			case 4:
			case 6:
			case 7:
			case 8:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 18:
			case 19:
			case 22:
			case 25:
			case 26:
			case 27:
			case 28:
			case 30:
			case 32:
			case 34:
			case 36:
			case 38:
			case 42:
			case 43:
			case 45:
			case 47:
			case 50:
			case 51:
			case 52:
			case JULIA:
			case RATIONAL:
			case INTEGER:
			case VARIABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(378); ((Conditionexp4Context)_localctx).c1 = simpleconditionexp();

						((Conditionexp4Context)_localctx).result =  ((Conditionexp4Context)_localctx).c1.result;
					
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 2);
				{
				setState(381); ((Conditionexp4Context)_localctx).n = match(21);
				setState(382); ((Conditionexp4Context)_localctx).c2 = conditionexp4();

						((Conditionexp4Context)_localctx).result =  new ASTConditionNeg(((Conditionexp4Context)_localctx).n, ((Conditionexp4Context)_localctx).c2.result);
					
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

	public static class SimpleexpressionContext extends ParserRuleContext {
		public ASTExpression result;
		public ConstantContext p;
		public VariableContext v;
		public RealContext r;
		public FunctionContext f;
		public Token t;
		public ExpressionContext e;
		public Token m;
		public Token a;
		public ExpressionContext er;
		public ExpressionContext ei;
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public RealContext real() {
			return getRuleContext(RealContext.class,0);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public SimpleexpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleexpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterSimpleexpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitSimpleexpression(this);
		}
	}

	public final SimpleexpressionContext simpleexpression() throws RecognitionException {
		SimpleexpressionContext _localctx = new SimpleexpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_simpleexpression);
		try {
			setState(421);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(387); ((SimpleexpressionContext)_localctx).p = constant();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).p.result;
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(390); ((SimpleexpressionContext)_localctx).v = variable();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).v.result;
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(393); ((SimpleexpressionContext)_localctx).r = real();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).r.result;
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(396); ((SimpleexpressionContext)_localctx).f = function();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).f.result;
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(399); ((SimpleexpressionContext)_localctx).t = match(7);
				setState(400); ((SimpleexpressionContext)_localctx).e = expression(0);
				setState(401); match(49);

						((SimpleexpressionContext)_localctx).result =  new ASTParen(((SimpleexpressionContext)_localctx).t, ((SimpleexpressionContext)_localctx).e.result);
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(404); ((SimpleexpressionContext)_localctx).m = match(45);
				setState(405); ((SimpleexpressionContext)_localctx).e = expression(0);
				setState(406); match(45);

						((SimpleexpressionContext)_localctx).result =  new ASTFunction(((SimpleexpressionContext)_localctx).m, "mod", ((SimpleexpressionContext)_localctx).e.result);	
					
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(409); ((SimpleexpressionContext)_localctx).a = match(19);
				setState(410); ((SimpleexpressionContext)_localctx).e = expression(0);
				setState(411); match(46);

						((SimpleexpressionContext)_localctx).result =  new ASTFunction(((SimpleexpressionContext)_localctx).a, "pha", ((SimpleexpressionContext)_localctx).e.result);	
					
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(414); ((SimpleexpressionContext)_localctx).a = match(19);
				setState(415); ((SimpleexpressionContext)_localctx).er = expression(0);
				setState(416); match(9);
				setState(417); ((SimpleexpressionContext)_localctx).ei = expression(0);
				setState(418); match(46);

						((SimpleexpressionContext)_localctx).result =  new ASTOperator(((SimpleexpressionContext)_localctx).a, "<>", ((SimpleexpressionContext)_localctx).er.result, ((SimpleexpressionContext)_localctx).ei.result);	
					
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

	public static class ExpressionContext extends ParserRuleContext {
		public ASTExpression result;
		public ExpressionContext e1;
		public Expression2Context e2;
		public Token s;
		public SimpleexpressionContext e;
		public ComplexContext c;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Expression2Context expression2() {
			return getRuleContext(Expression2Context.class,0);
		}
		public SimpleexpressionContext simpleexpression() {
			return getRuleContext(SimpleexpressionContext.class,0);
		}
		public ComplexContext complex() {
			return getRuleContext(ComplexContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(438);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(424); ((ExpressionContext)_localctx).e2 = expression2(0);
				setState(425); ((ExpressionContext)_localctx).s = match(28);
				setState(426); ((ExpressionContext)_localctx).e1 = expression(2);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e2.result, ((ExpressionContext)_localctx).e1.result);		
					
				}
				break;

			case 2:
				{
				setState(429); ((ExpressionContext)_localctx).e = simpleexpression();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e.result;	
					
				}
				break;

			case 3:
				{
				setState(432); ((ExpressionContext)_localctx).c = complex();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).c.result;
					
				}
				break;

			case 4:
				{
				setState(435); ((ExpressionContext)_localctx).e2 = expression2(0);

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e2.result;	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(452);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(450);
					switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(440);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(441); ((ExpressionContext)_localctx).s = match(28);
						setState(442); ((ExpressionContext)_localctx).e2 = expression2(0);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;

					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(445);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(446); ((ExpressionContext)_localctx).s = match(52);
						setState(447); ((ExpressionContext)_localctx).e2 = expression2(0);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					}
					} 
				}
				setState(454);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
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

	public static class Expression2Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression2Context e1;
		public Expression2Context e2;
		public Token s;
		public Token i;
		public SimpleexpressionContext e;
		public Expression3Context e3;
		public Expression3Context expression3() {
			return getRuleContext(Expression3Context.class,0);
		}
		public List<Expression2Context> expression2() {
			return getRuleContexts(Expression2Context.class);
		}
		public Expression2Context expression2(int i) {
			return getRuleContext(Expression2Context.class,i);
		}
		public SimpleexpressionContext simpleexpression() {
			return getRuleContext(SimpleexpressionContext.class,0);
		}
		public Expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterExpression2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitExpression2(this);
		}
	}

	public final Expression2Context expression2() throws RecognitionException {
		return expression2(0);
	}

	private Expression2Context expression2(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Expression2Context _localctx = new Expression2Context(_ctx, _parentState);
		Expression2Context _prevctx = _localctx;
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_expression2, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(477);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(456); ((Expression2Context)_localctx).s = match(52);
				setState(457); ((Expression2Context)_localctx).e2 = expression2(4);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "-", ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(460); ((Expression2Context)_localctx).s = match(28);
				setState(461); ((Expression2Context)_localctx).e2 = expression2(3);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "+", ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 3:
				{
				setState(464); ((Expression2Context)_localctx).i = match(27);
				setState(466);
				_la = _input.LA(1);
				if (_la==41) {
					{
					setState(465); match(41);
					}
				}

				setState(468); ((Expression2Context)_localctx).e2 = expression2(2);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 4:
				{
				setState(471); ((Expression2Context)_localctx).e = simpleexpression();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e.result;	
					
				}
				break;

			case 5:
				{
				setState(474); ((Expression2Context)_localctx).e3 = expression3(0);

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(492);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(490);
					switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
					case 1:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(479);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(480); ((Expression2Context)_localctx).s = match(41);
						setState(481); ((Expression2Context)_localctx).e2 = expression2(6);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "*", ((Expression2Context)_localctx).e1.result, ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;

					case 2:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e2 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(484);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(486);
						_la = _input.LA(1);
						if (_la==41) {
							{
							setState(485); match(41);
							}
						}

						setState(488); ((Expression2Context)_localctx).i = match(27);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					}
					} 
				}
				setState(494);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
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

	public static class Expression3Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression3Context e1;
		public SimpleexpressionContext e;
		public Expression4Context e3;
		public Token s;
		public Expression3Context e2;
		public Expression3Context expression3(int i) {
			return getRuleContext(Expression3Context.class,i);
		}
		public Expression4Context expression4() {
			return getRuleContext(Expression4Context.class,0);
		}
		public List<Expression3Context> expression3() {
			return getRuleContexts(Expression3Context.class);
		}
		public SimpleexpressionContext simpleexpression() {
			return getRuleContext(SimpleexpressionContext.class,0);
		}
		public Expression3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterExpression3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitExpression3(this);
		}
	}

	public final Expression3Context expression3() throws RecognitionException {
		return expression3(0);
	}

	private Expression3Context expression3(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Expression3Context _localctx = new Expression3Context(_ctx, _parentState);
		Expression3Context _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_expression3, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(502);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				{
				setState(496); ((Expression3Context)_localctx).e = simpleexpression();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e.result;	
					
				}
				break;

			case 2:
				{
				setState(499); ((Expression3Context)_localctx).e3 = expression4(0);

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(511);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression3Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression3);
					setState(504);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(505); ((Expression3Context)_localctx).s = match(29);
					setState(506); ((Expression3Context)_localctx).e2 = expression3(2);

					          		((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).s, "/", ((Expression3Context)_localctx).e1.result, ((Expression3Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(513);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
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

	public static class Expression4Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression4Context e1;
		public SimpleexpressionContext e;
		public Token s;
		public Expression4Context e2;
		public List<Expression4Context> expression4() {
			return getRuleContexts(Expression4Context.class);
		}
		public SimpleexpressionContext simpleexpression() {
			return getRuleContext(SimpleexpressionContext.class,0);
		}
		public Expression4Context expression4(int i) {
			return getRuleContext(Expression4Context.class,i);
		}
		public Expression4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression4; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterExpression4(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitExpression4(this);
		}
	}

	public final Expression4Context expression4() throws RecognitionException {
		return expression4(0);
	}

	private Expression4Context expression4(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Expression4Context _localctx = new Expression4Context(_ctx, _parentState);
		Expression4Context _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_expression4, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(515); ((Expression4Context)_localctx).e = simpleexpression();

					((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).e.result;	
				
			}
			_ctx.stop = _input.LT(-1);
			setState(525);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression4Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression4);
					setState(518);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(519); ((Expression4Context)_localctx).s = match(5);
					setState(520); ((Expression4Context)_localctx).e2 = expression4(2);

					          		((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).s, "^", ((Expression4Context)_localctx).e1.result, ((Expression4Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(527);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
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

	public static class FunctionContext extends ParserRuleContext {
		public ASTFunction result;
		public Token f;
		public ExpressionContext e;
		public ExpressionContext e1;
		public ExpressionContext e2;
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitFunction(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_function);
		int _la;
		try {
			setState(554);
			switch (_input.LA(1)) {
			case 6:
			case 10:
			case 11:
			case 16:
			case 34:
				enterOuterAlt(_localctx, 1);
				{
				setState(528);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 10) | (1L << 11) | (1L << 16) | (1L << 34))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(529); match(7);
				setState(530); ((FunctionContext)_localctx).e = expression(0);
				setState(531); match(49);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), ((FunctionContext)_localctx).e.result);		
					
				}
				break;
			case 1:
			case 4:
			case 12:
			case 36:
			case 42:
			case 47:
				enterOuterAlt(_localctx, 2);
				{
				setState(534);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 4) | (1L << 12) | (1L << 36) | (1L << 42) | (1L << 47))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(535); match(7);
				setState(536); ((FunctionContext)_localctx).e = expression(0);
				setState(537); match(49);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 13:
			case 15:
			case 18:
			case 22:
			case 26:
			case 50:
				enterOuterAlt(_localctx, 3);
				{
				setState(540);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 13) | (1L << 15) | (1L << 18) | (1L << 22) | (1L << 26) | (1L << 50))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(541); match(7);
				setState(542); ((FunctionContext)_localctx).e = expression(0);
				setState(543); match(49);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 8:
			case 32:
			case 38:
			case 43:
			case 51:
				enterOuterAlt(_localctx, 4);
				{
				setState(546);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 8) | (1L << 32) | (1L << 38) | (1L << 43) | (1L << 51))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(547); match(7);
				setState(548); ((FunctionContext)_localctx).e1 = expression(0);
				setState(549); match(9);
				setState(550); ((FunctionContext)_localctx).e2 = expression(0);
				setState(551); match(49);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e1.result, ((FunctionContext)_localctx).e2.result });		
					
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

	public static class ConstantContext extends ParserRuleContext {
		public ASTNumber result;
		public Token p;
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitConstant(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_constant);
		try {
			setState(562);
			switch (_input.LA(1)) {
			case 25:
				enterOuterAlt(_localctx, 1);
				{
				setState(556); ((ConstantContext)_localctx).p = match(25);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.E);
					
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 2);
				{
				setState(558); ((ConstantContext)_localctx).p = match(14);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.PI);
					
				}
				break;
			case 30:
				enterOuterAlt(_localctx, 3);
				{
				setState(560); ((ConstantContext)_localctx).p = match(30);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, 2 * Math.PI);
					
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

	public static class VariableContext extends ParserRuleContext {
		public ASTVariable result;
		public Token v;
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(564); ((VariableContext)_localctx).v = match(VARIABLE);

					((VariableContext)_localctx).result =  new ASTVariable(((VariableContext)_localctx).v, builder.getVariable((((VariableContext)_localctx).v!=null?((VariableContext)_localctx).v.getText():null), ((VariableContext)_localctx).v));
				
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

	public static class RealContext extends ParserRuleContext {
		public ASTNumber result;
		public Token r;
		public TerminalNode INTEGER() { return getToken(MandelbrotParser.INTEGER, 0); }
		public TerminalNode RATIONAL() { return getToken(MandelbrotParser.RATIONAL, 0); }
		public RealContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_real; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterReal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitReal(this);
		}
	}

	public final RealContext real() throws RecognitionException {
		RealContext _localctx = new RealContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_real);
		int _la;
		try {
			setState(575);
			switch (_input.LA(1)) {
			case 28:
			case RATIONAL:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(568);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(567); match(28);
					}
				}

				setState(570);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((RealContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();

						((RealContext)_localctx).result =  new ASTNumber(((RealContext)_localctx).r, builder.parseDouble((((RealContext)_localctx).r!=null?((RealContext)_localctx).r.getText():null)));
					
				}
				break;
			case 52:
				enterOuterAlt(_localctx, 2);
				{
				setState(572); match(52);
				setState(573);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((RealContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();

						((RealContext)_localctx).result =  new ASTNumber(((RealContext)_localctx).r, builder.parseDouble("-" + (((RealContext)_localctx).r!=null?((RealContext)_localctx).r.getText():null)));
					
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

	public static class ComplexContext extends ParserRuleContext {
		public ASTNumber result;
		public Token r;
		public Token i;
		public RealContext rn;
		public List<TerminalNode> INTEGER() { return getTokens(MandelbrotParser.INTEGER); }
		public RealContext real() {
			return getRuleContext(RealContext.class,0);
		}
		public TerminalNode INTEGER(int i) {
			return getToken(MandelbrotParser.INTEGER, i);
		}
		public TerminalNode RATIONAL(int i) {
			return getToken(MandelbrotParser.RATIONAL, i);
		}
		public List<TerminalNode> RATIONAL() { return getTokens(MandelbrotParser.RATIONAL); }
		public ComplexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_complex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterComplex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitComplex(this);
		}
	}

	public final ComplexContext complex() throws RecognitionException {
		ComplexContext _localctx = new ComplexContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_complex);
		int _la;
		try {
			setState(658);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(577); match(19);
				setState(579);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(578); match(28);
					}
				}

				setState(581);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(582); match(9);
				setState(584);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(583); match(28);
					}
				}

				setState(586);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(587); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(589); match(19);
				setState(591);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(590); match(28);
					}
				}

				setState(593);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(594); match(9);
				setState(595); match(52);
				setState(596);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(597); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(599); match(19);
				setState(600); match(52);
				setState(601);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(602); match(9);
				setState(604);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(603); match(28);
					}
				}

				setState(606);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(607); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(609); match(19);
				setState(610); match(52);
				setState(611);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(612); match(9);
				setState(613); match(52);
				setState(614);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(615); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(618);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(617); match(28);
					}
				}

				setState(620);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(621); match(28);
				setState(622);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(623); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(626);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(625); match(28);
					}
				}

				setState(628);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(629); match(52);
				setState(630);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(631); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(634);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(633); match(28);
					}
				}

				setState(636);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(637); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble((((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(639); match(52);
				setState(640);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(641); match(28);
				setState(642);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(643); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(645); match(52);
				setState(646);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(647); match(52);
				setState(648);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(649); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(651); match(52);
				setState(652);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(653); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(655); ((ComplexContext)_localctx).rn = real();

						((ComplexContext)_localctx).result =  ((ComplexContext)_localctx).rn.result;
					
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

	public static class PaletteContext extends ParserRuleContext {
		public Token p;
		public Token v;
		public TerminalNode PALETTE() { return getToken(MandelbrotParser.PALETTE, 0); }
		public List<PaletteelementContext> paletteelement() {
			return getRuleContexts(PaletteelementContext.class);
		}
		public PaletteelementContext paletteelement(int i) {
			return getRuleContext(PaletteelementContext.class,i);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public PaletteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_palette; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterPalette(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitPalette(this);
		}
	}

	public final PaletteContext palette() throws RecognitionException {
		PaletteContext _localctx = new PaletteContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_palette);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(660); ((PaletteContext)_localctx).p = match(PALETTE);
			setState(661); ((PaletteContext)_localctx).v = match(VARIABLE);

					builder.addPalette(new ASTPalette(((PaletteContext)_localctx).p, (((PaletteContext)_localctx).v!=null?((PaletteContext)_localctx).v.getText():null))); 
				
			setState(663); match(2);
			setState(665); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(664); paletteelement();
				}
				}
				setState(667); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==44 );
			setState(669); match(33);
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

	public static class PaletteelementContext extends ParserRuleContext {
		public Token t;
		public ColorargbContext bc;
		public ColorargbContext ec;
		public Token s;
		public ExpressionContext e;
		public TerminalNode INTEGER() { return getToken(MandelbrotParser.INTEGER, 0); }
		public ColorargbContext colorargb(int i) {
			return getRuleContext(ColorargbContext.class,i);
		}
		public List<ColorargbContext> colorargb() {
			return getRuleContexts(ColorargbContext.class);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PaletteelementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paletteelement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterPaletteelement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitPaletteelement(this);
		}
	}

	public final PaletteelementContext paletteelement() throws RecognitionException {
		PaletteelementContext _localctx = new PaletteelementContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_paletteelement);
		try {
			setState(693);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(671); ((PaletteelementContext)_localctx).t = match(44);
				setState(672); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(673); match(46);
				setState(674); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(675); match(9);
				setState(676); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(677); match(9);
				setState(678); ((PaletteelementContext)_localctx).e = expression(0);
				setState(679); match(20);
				setState(680); match(31);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), ((PaletteelementContext)_localctx).e.result));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(683); ((PaletteelementContext)_localctx).t = match(44);
				setState(684); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(685); match(46);
				setState(686); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(687); match(9);
				setState(688); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(689); match(20);
				setState(690); match(31);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), null));
					
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

	public static class ColorinitContext extends ParserRuleContext {
		public Token i;
		public ColorstatementContext colorstatement(int i) {
			return getRuleContext(ColorstatementContext.class,i);
		}
		public TerminalNode INIT() { return getToken(MandelbrotParser.INIT, 0); }
		public List<ColorstatementContext> colorstatement() {
			return getRuleContexts(ColorstatementContext.class);
		}
		public ColorinitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colorinit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterColorinit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitColorinit(this);
		}
	}

	public final ColorinitContext colorinit() throws RecognitionException {
		ColorinitContext _localctx = new ColorinitContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_colorinit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(695); ((ColorinitContext)_localctx).i = match(INIT);

					builder.setColorContext(true);
					builder.setColorInit(new ASTColorInit(((ColorinitContext)_localctx).i));
				
			setState(697); match(2);
			setState(701);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
				{
				{
				setState(698); colorstatement();
				}
				}
				setState(703);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(704); match(33);
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

	public static class ColorstatementContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ColorstatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colorstatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterColorstatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitColorstatement(this);
		}
	}

	public final ColorstatementContext colorstatement() throws RecognitionException {
		ColorstatementContext _localctx = new ColorstatementContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_colorstatement);
		try {
			enterOuterAlt(_localctx, 1);
			{

					builder.pushStatementList();	
				
			setState(707); statement();

					builder.addColorStatements(builder.getStatementList());
					builder.popStatementList();	
				
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

	public static class ColorruleContext extends ParserRuleContext {
		public Token t;
		public RuleexpContext r;
		public Token o;
		public ColorexpContext c;
		public TerminalNode RULE() { return getToken(MandelbrotParser.RULE, 0); }
		public TerminalNode INTEGER() { return getToken(MandelbrotParser.INTEGER, 0); }
		public ColorexpContext colorexp() {
			return getRuleContext(ColorexpContext.class,0);
		}
		public TerminalNode RATIONAL() { return getToken(MandelbrotParser.RATIONAL, 0); }
		public RuleexpContext ruleexp() {
			return getRuleContext(RuleexpContext.class,0);
		}
		public ColorruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colorrule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterColorrule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitColorrule(this);
		}
	}

	public final ColorruleContext colorrule() throws RecognitionException {
		ColorruleContext _localctx = new ColorruleContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_colorrule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(710); ((ColorruleContext)_localctx).t = match(RULE);
			setState(711); match(7);
			setState(712); ((ColorruleContext)_localctx).r = ruleexp(0);
			setState(713); match(49);
			setState(714); match(44);
			setState(715);
			((ColorruleContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==RATIONAL || _la==INTEGER) ) {
				((ColorruleContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(716); match(20);
			setState(717); match(2);
			setState(718); ((ColorruleContext)_localctx).c = colorexp();
			setState(719); match(33);

					builder.addRule(new ASTRule(((ColorruleContext)_localctx).t, builder.parseFloat((((ColorruleContext)_localctx).o!=null?((ColorruleContext)_localctx).o.getText():null)), ((ColorruleContext)_localctx).r.result, ((ColorruleContext)_localctx).c.result));
				
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

	public static class RuleexpContext extends ParserRuleContext {
		public ASTRuleExpression result;
		public RuleexpContext r1;
		public ExpressionContext e1;
		public Token o;
		public ExpressionContext e2;
		public RuleexpContext r2;
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public RuleexpContext ruleexp(int i) {
			return getRuleContext(RuleexpContext.class,i);
		}
		public List<RuleexpContext> ruleexp() {
			return getRuleContexts(RuleexpContext.class);
		}
		public RuleexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterRuleexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitRuleexp(this);
		}
	}

	public final RuleexpContext ruleexp() throws RecognitionException {
		return ruleexp(0);
	}

	private RuleexpContext ruleexp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RuleexpContext _localctx = new RuleexpContext(_ctx, _parentState);
		RuleexpContext _prevctx = _localctx;
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_ruleexp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(723); ((RuleexpContext)_localctx).e1 = expression(0);
			setState(724);
			((RuleexpContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 23) | (1L << 37) | (1L << 46))) != 0)) ) {
				((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(725); ((RuleexpContext)_localctx).e2 = expression(0);

					((RuleexpContext)_localctx).result =  new ASTRuleCompareOp(((RuleexpContext)_localctx).e1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).e1.result, ((RuleexpContext)_localctx).e2.result);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(735);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RuleexpContext(_parentctx, _parentState);
					_localctx.r1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_ruleexp);
					setState(728);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(729);
					((RuleexpContext)_localctx).o = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 40) | (1L << 45))) != 0)) ) {
						((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(730); ((RuleexpContext)_localctx).r2 = ruleexp(2);

					          		((RuleexpContext)_localctx).result =  new ASTRuleLogicOp(((RuleexpContext)_localctx).r1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).r1.result, ((RuleexpContext)_localctx).r2.result);
					          	
					}
					} 
				}
				setState(737);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,53,_ctx);
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

	public static class ColorexpContext extends ParserRuleContext {
		public ASTColorExpression result;
		public ExpressionContext e1;
		public ExpressionContext e2;
		public ExpressionContext e3;
		public ExpressionContext e4;
		public Token v;
		public ExpressionContext e;
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ColorexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colorexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterColorexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitColorexp(this);
		}
	}

	public final ColorexpContext colorexp() throws RecognitionException {
		ColorexpContext _localctx = new ColorexpContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_colorexp);
		try {
			setState(763);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(738); ((ColorexpContext)_localctx).e1 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(741); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(742); match(9);
				setState(743); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(744); match(9);
				setState(745); ((ColorexpContext)_localctx).e3 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(748); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(749); match(9);
				setState(750); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(751); match(9);
				setState(752); ((ColorexpContext)_localctx).e3 = expression(0);
				setState(753); match(9);
				setState(754); ((ColorexpContext)_localctx).e4 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result, ((ColorexpContext)_localctx).e4.result);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(757); ((ColorexpContext)_localctx).v = match(VARIABLE);
				setState(758); match(44);
				setState(759); ((ColorexpContext)_localctx).e = expression(0);
				setState(760); match(20);

						((ColorexpContext)_localctx).result =  new ASTColorPalette(((ColorexpContext)_localctx).v, (((ColorexpContext)_localctx).v!=null?((ColorexpContext)_localctx).v.getText():null), ((ColorexpContext)_localctx).e.result);
					
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

	public static class ColorargbContext extends ParserRuleContext {
		public ASTColorARGB result;
		public Token a;
		public Token r;
		public Token g;
		public Token b;
		public Token argb;
		public List<TerminalNode> INTEGER() { return getTokens(MandelbrotParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(MandelbrotParser.INTEGER, i);
		}
		public TerminalNode ARGB() { return getToken(MandelbrotParser.ARGB, 0); }
		public TerminalNode RATIONAL(int i) {
			return getToken(MandelbrotParser.RATIONAL, i);
		}
		public List<TerminalNode> RATIONAL() { return getTokens(MandelbrotParser.RATIONAL); }
		public ColorargbContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colorargb; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterColorargb(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitColorargb(this);
		}
	}

	public final ColorargbContext colorargb() throws RecognitionException {
		ColorargbContext _localctx = new ColorargbContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_colorargb);
		int _la;
		try {
			setState(778);
			switch (_input.LA(1)) {
			case 7:
				enterOuterAlt(_localctx, 1);
				{
				setState(765); match(7);
				setState(766);
				((ColorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(767); match(9);
				setState(768);
				((ColorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(769); match(9);
				setState(770);
				((ColorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(771); match(9);
				setState(772);
				((ColorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(773); match(49);

						((ColorargbContext)_localctx).result =  new ASTColorARGB(builder.parseFloat((((ColorargbContext)_localctx).a!=null?((ColorargbContext)_localctx).a.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).r!=null?((ColorargbContext)_localctx).r.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).g!=null?((ColorargbContext)_localctx).g.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).b!=null?((ColorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 2);
				{
				setState(775); match(24);
				setState(776); ((ColorargbContext)_localctx).argb = match(ARGB);

						((ColorargbContext)_localctx).result =  new ASTColorARGB((int)(0xFFFFFFFF & builder.parseLong((((ColorargbContext)_localctx).argb!=null?((ColorargbContext)_localctx).argb.getText():null), 16)));
					
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

	public static class EofContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MandelbrotParser.EOF, 0); }
		public EofContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eof; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterEof(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitEof(this);
		}
	}

	public final EofContext eof() throws RecognitionException {
		EofContext _localctx = new EofContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_eof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(780); match(EOF);
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
		case 12: return variablelist_sempred((VariablelistContext)_localctx, predIndex);

		case 14: return conditionexp_sempred((ConditionexpContext)_localctx, predIndex);

		case 15: return conditionexp2_sempred((Conditionexp2Context)_localctx, predIndex);

		case 16: return conditionexp3_sempred((Conditionexp3Context)_localctx, predIndex);

		case 19: return expression_sempred((ExpressionContext)_localctx, predIndex);

		case 20: return expression2_sempred((Expression2Context)_localctx, predIndex);

		case 21: return expression3_sempred((Expression3Context)_localctx, predIndex);

		case 22: return expression4_sempred((Expression4Context)_localctx, predIndex);

		case 33: return ruleexp_sempred((RuleexpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean conditionexp_sempred(ConditionexpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4: return precpred(_ctx, 3);

		case 5: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean conditionexp3_sempred(Conditionexp3Context _localctx, int predIndex) {
		switch (predIndex) {
		case 3: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean conditionexp2_sempred(Conditionexp2Context _localctx, int predIndex) {
		switch (predIndex) {
		case 2: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression4_sempred(Expression4Context _localctx, int predIndex) {
		switch (predIndex) {
		case 9: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean variablelist_sempred(VariablelistContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression2_sempred(Expression2Context _localctx, int predIndex) {
		switch (predIndex) {
		case 6: return precpred(_ctx, 5);

		case 7: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression3_sempred(Expression3Context _localctx, int predIndex) {
		switch (predIndex) {
		case 8: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean ruleexp_sempred(RuleexpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10: return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3L\u0311\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3a\n\3\f\3\16\3d\13\3"+
		"\3\3\5\3g\n\3\3\3\3\3\5\3k\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4"+
		"v\n\4\f\4\16\4y\13\4\3\4\5\4|\n\4\3\4\7\4\177\n\4\f\4\16\4\u0082\13\4"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\7\5\u008a\n\5\f\5\16\5\u008d\13\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u009d\n\6\f\6\16\6\u00a0"+
		"\13\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7\u00a8\n\7\f\7\16\7\u00ab\13\7\3\7\3"+
		"\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00b7\n\b\f\b\16\b\u00ba\13\b\3"+
		"\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00d9\n\t\3\n\3\n\3\n"+
		"\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\5\r\u00eb\n\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00f6\n\r\f\r\16\r\u00f9\13\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0101\n\r\f\r\16\r\u0104\13\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0110\n\r\f\r\16\r\u0113\13\r\3\r\3\r"+
		"\3\r\3\r\3\r\5\r\u011a\n\r\3\r\5\r\u011d\n\r\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\7\16\u0127\n\16\f\16\16\16\u012a\13\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\5\17\u0142\n\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\5\20\u014b\n\20\3\20\3\20\3\20\3\20\3\20\7\20\u0152\n\20\f\20\16"+
		"\20\u0155\13\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u015e\n\21\3\21"+
		"\3\21\3\21\3\21\3\21\7\21\u0165\n\21\f\21\16\21\u0168\13\21\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\5\22\u0171\n\22\3\22\3\22\3\22\3\22\3\22\7\22"+
		"\u0178\n\22\f\22\16\22\u017b\13\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\5\23\u0184\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u01a8\n\24\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25"+
		"\u01b9\n\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\7\25\u01c5"+
		"\n\25\f\25\16\25\u01c8\13\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3"+
		"\26\3\26\3\26\5\26\u01d5\n\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\5\26\u01e0\n\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u01e9\n"+
		"\26\3\26\3\26\7\26\u01ed\n\26\f\26\16\26\u01f0\13\26\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\5\27\u01f9\n\27\3\27\3\27\3\27\3\27\3\27\7\27\u0200"+
		"\n\27\f\27\16\27\u0203\13\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3"+
		"\30\7\30\u020e\n\30\f\30\16\30\u0211\13\30\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u022d\n\31\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\5\32\u0235\n\32\3\33\3\33\3\33\3\34\5\34\u023b\n\34\3\34\3"+
		"\34\3\34\3\34\3\34\5\34\u0242\n\34\3\35\3\35\5\35\u0246\n\35\3\35\3\35"+
		"\3\35\5\35\u024b\n\35\3\35\3\35\3\35\3\35\3\35\5\35\u0252\n\35\3\35\3"+
		"\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u025f\n\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u026d\n\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u0275\n\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\5\35\u027d\n\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u0295"+
		"\n\35\3\36\3\36\3\36\3\36\3\36\6\36\u029c\n\36\r\36\16\36\u029d\3\36\3"+
		"\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3"+
		"\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u02b8\n\37\3 \3 \3 \3"+
		" \7 \u02be\n \f \16 \u02c1\13 \3 \3 \3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\7#\u02e0\n"+
		"#\f#\16#\u02e3\13#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\3$\5$\u02fe\n$\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%"+
		"\3%\3%\5%\u030d\n%\3&\3&\3&\2\13\32\36 \"(*,.D\'\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJ\2\t\b\2\5\5\23\23\25"+
		"\25\31\31\'\'\60\60\6\2\b\b\f\r\22\22$$\b\2\3\3\6\6\16\16&&,,\61\61\b"+
		"\2\17\17\21\21\24\24\30\30\34\34\64\64\7\2\n\n\"\"((--\65\65\3\2EF\5\2"+
		"\7\7**//\u0342\2L\3\2\2\2\4T\3\2\2\2\6n\3\2\2\2\b\u0085\3\2\2\2\n\u0090"+
		"\3\2\2\2\f\u00a3\3\2\2\2\16\u00ae\3\2\2\2\20\u00d8\3\2\2\2\22\u00da\3"+
		"\2\2\2\24\u00de\3\2\2\2\26\u00e2\3\2\2\2\30\u011c\3\2\2\2\32\u011e\3\2"+
		"\2\2\34\u0141\3\2\2\2\36\u014a\3\2\2\2 \u015d\3\2\2\2\"\u0170\3\2\2\2"+
		"$\u0183\3\2\2\2&\u01a7\3\2\2\2(\u01b8\3\2\2\2*\u01df\3\2\2\2,\u01f8\3"+
		"\2\2\2.\u0204\3\2\2\2\60\u022c\3\2\2\2\62\u0234\3\2\2\2\64\u0236\3\2\2"+
		"\2\66\u0241\3\2\2\28\u0294\3\2\2\2:\u0296\3\2\2\2<\u02b7\3\2\2\2>\u02b9"+
		"\3\2\2\2@\u02c4\3\2\2\2B\u02c8\3\2\2\2D\u02d4\3\2\2\2F\u02fd\3\2\2\2H"+
		"\u030c\3\2\2\2J\u030e\3\2\2\2LM\7\67\2\2MN\b\2\1\2NO\7\4\2\2OP\5\4\3\2"+
		"PQ\5\6\4\2QR\7#\2\2RS\5J&\2S\3\3\2\2\2TU\78\2\2UV\7.\2\2VW\58\35\2WX\7"+
		"\13\2\2XY\58\35\2YZ\7\26\2\2Z[\b\3\1\2[\\\7.\2\2\\]\5\32\16\2]^\7\26\2"+
		"\2^b\7\4\2\2_a\5\16\b\2`_\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2cf\3\2"+
		"\2\2db\3\2\2\2eg\5\b\5\2fe\3\2\2\2fg\3\2\2\2gh\3\2\2\2hj\5\n\6\2ik\5\f"+
		"\7\2ji\3\2\2\2jk\3\2\2\2kl\3\2\2\2lm\7#\2\2m\5\3\2\2\2no\7A\2\2op\7.\2"+
		"\2pq\5H%\2qr\7\26\2\2rs\b\4\1\2sw\7\4\2\2tv\5:\36\2ut\3\2\2\2vy\3\2\2"+
		"\2wu\3\2\2\2wx\3\2\2\2x{\3\2\2\2yw\3\2\2\2z|\5> \2{z\3\2\2\2{|\3\2\2\2"+
		"|\u0080\3\2\2\2}\177\5B\"\2~}\3\2\2\2\177\u0082\3\2\2\2\u0080~\3\2\2\2"+
		"\u0080\u0081\3\2\2\2\u0081\u0083\3\2\2\2\u0082\u0080\3\2\2\2\u0083\u0084"+
		"\7#\2\2\u0084\7\3\2\2\2\u0085\u0086\7:\2\2\u0086\u0087\b\5\1\2\u0087\u008b"+
		"\7\4\2\2\u0088\u008a\5\22\n\2\u0089\u0088\3\2\2\2\u008a\u008d\3\2\2\2"+
		"\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008e\3\2\2\2\u008d\u008b"+
		"\3\2\2\2\u008e\u008f\7#\2\2\u008f\t\3\2\2\2\u0090\u0091\7;\2\2\u0091\u0092"+
		"\7.\2\2\u0092\u0093\7F\2\2\u0093\u0094\7\13\2\2\u0094\u0095\7F\2\2\u0095"+
		"\u0096\7\26\2\2\u0096\u0097\7\t\2\2\u0097\u0098\5\36\20\2\u0098\u0099"+
		"\7\63\2\2\u0099\u009a\b\6\1\2\u009a\u009e\7\4\2\2\u009b\u009d\5\24\13"+
		"\2\u009c\u009b\3\2\2\2\u009d\u00a0\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f"+
		"\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a2\7#\2\2\u00a2"+
		"\13\3\2\2\2\u00a3\u00a4\7<\2\2\u00a4\u00a5\b\7\1\2\u00a5\u00a9\7\4\2\2"+
		"\u00a6\u00a8\5\26\f\2\u00a7\u00a6\3\2\2\2\u00a8\u00ab\3\2\2\2\u00a9\u00a7"+
		"\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ac"+
		"\u00ad\7#\2\2\u00ad\r\3\2\2\2\u00ae\u00af\79\2\2\u00af\u00b0\7J\2\2\u00b0"+
		"\u00b1\7.\2\2\u00b1\u00b2\58\35\2\u00b2\u00b3\7\26\2\2\u00b3\u00b4\b\b"+
		"\1\2\u00b4\u00b8\7\4\2\2\u00b5\u00b7\5\20\t\2\u00b6\u00b5\3\2\2\2\u00b7"+
		"\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bb\3\2"+
		"\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bc\7#\2\2\u00bc\17\3\2\2\2\u00bd\u00be"+
		"\7G\2\2\u00be\u00bf\7\t\2\2\u00bf\u00c0\58\35\2\u00c0\u00c1\7\63\2\2\u00c1"+
		"\u00c2\7!\2\2\u00c2\u00c3\b\t\1\2\u00c3\u00d9\3\2\2\2\u00c4\u00c5\7H\2"+
		"\2\u00c5\u00c6\7\t\2\2\u00c6\u00c7\58\35\2\u00c7\u00c8\7\13\2\2\u00c8"+
		"\u00c9\58\35\2\u00c9\u00ca\7\63\2\2\u00ca\u00cb\7!\2\2\u00cb\u00cc\b\t"+
		"\1\2\u00cc\u00d9\3\2\2\2\u00cd\u00ce\7I\2\2\u00ce\u00cf\7\t\2\2\u00cf"+
		"\u00d0\58\35\2\u00d0\u00d1\7\13\2\2\u00d1\u00d2\58\35\2\u00d2\u00d3\7"+
		"\13\2\2\u00d3\u00d4\58\35\2\u00d4\u00d5\7\63\2\2\u00d5\u00d6\7!\2\2\u00d6"+
		"\u00d7\b\t\1\2\u00d7\u00d9\3\2\2\2\u00d8\u00bd\3\2\2\2\u00d8\u00c4\3\2"+
		"\2\2\u00d8\u00cd\3\2\2\2\u00d9\21\3\2\2\2\u00da\u00db\b\n\1\2\u00db\u00dc"+
		"\5\30\r\2\u00dc\u00dd\b\n\1\2\u00dd\23\3\2\2\2\u00de\u00df\b\13\1\2\u00df"+
		"\u00e0\5\30\r\2\u00e0\u00e1\b\13\1\2\u00e1\25\3\2\2\2\u00e2\u00e3\b\f"+
		"\1\2\u00e3\u00e4\5\30\r\2\u00e4\u00e5\b\f\1\2\u00e5\27\3\2\2\2\u00e6\u00e7"+
		"\7J\2\2\u00e7\u00e8\7\5\2\2\u00e8\u00ea\5(\25\2\u00e9\u00eb\7!\2\2\u00ea"+
		"\u00e9\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\b\r"+
		"\1\2\u00ed\u011d\3\2\2\2\u00ee\u00ef\7>\2\2\u00ef\u00f0\7\t\2\2\u00f0"+
		"\u00f1\5\36\20\2\u00f1\u00f2\7\63\2\2\u00f2\u00f3\7\4\2\2\u00f3\u00f7"+
		"\b\r\1\2\u00f4\u00f6\5\30\r\2\u00f5\u00f4\3\2\2\2\u00f6\u00f9\3\2\2\2"+
		"\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00fa\3\2\2\2\u00f9\u00f7"+
		"\3\2\2\2\u00fa\u00fb\7#\2\2\u00fb\u00fc\b\r\1\2\u00fc\u00fd\7\62\2\2\u00fd"+
		"\u00fe\7\4\2\2\u00fe\u0102\b\r\1\2\u00ff\u0101\5\30\r\2\u0100\u00ff\3"+
		"\2\2\2\u0101\u0104\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103"+
		"\u0105\3\2\2\2\u0104\u0102\3\2\2\2\u0105\u0106\7#\2\2\u0106\u0107\b\r"+
		"\1\2\u0107\u011d\3\2\2\2\u0108\u0109\7>\2\2\u0109\u010a\7\t\2\2\u010a"+
		"\u010b\5\36\20\2\u010b\u010c\7\63\2\2\u010c\u010d\7\4\2\2\u010d\u0111"+
		"\b\r\1\2\u010e\u0110\5\30\r\2\u010f\u010e\3\2\2\2\u0110\u0113\3\2\2\2"+
		"\u0111\u010f\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0114\3\2\2\2\u0113\u0111"+
		"\3\2\2\2\u0114\u0115\7#\2\2\u0115\u0116\b\r\1\2\u0116\u011d\3\2\2\2\u0117"+
		"\u0119\7?\2\2\u0118\u011a\7!\2\2\u0119\u0118\3\2\2\2\u0119\u011a\3\2\2"+
		"\2\u011a\u011b\3\2\2\2\u011b\u011d\b\r\1\2\u011c\u00e6\3\2\2\2\u011c\u00ee"+
		"\3\2\2\2\u011c\u0108\3\2\2\2\u011c\u0117\3\2\2\2\u011d\31\3\2\2\2\u011e"+
		"\u011f\b\16\1\2\u011f\u0120\7J\2\2\u0120\u0121\b\16\1\2\u0121\u0128\3"+
		"\2\2\2\u0122\u0123\f\3\2\2\u0123\u0124\7\13\2\2\u0124\u0125\7J\2\2\u0125"+
		"\u0127\b\16\1\2\u0126\u0122\3\2\2\2\u0127\u012a\3\2\2\2\u0128\u0126\3"+
		"\2\2\2\u0128\u0129\3\2\2\2\u0129\33\3\2\2\2\u012a\u0128\3\2\2\2\u012b"+
		"\u012c\5(\25\2\u012c\u012d\t\2\2\2\u012d\u012e\5(\25\2\u012e\u012f\b\17"+
		"\1\2\u012f\u0142\3\2\2\2\u0130\u0131\7J\2\2\u0131\u0132\7%\2\2\u0132\u0133"+
		"\5(\25\2\u0133\u0134\b\17\1\2\u0134\u0142\3\2\2\2\u0135\u0136\7J\2\2\u0136"+
		"\u0137\7)\2\2\u0137\u0138\5(\25\2\u0138\u0139\b\17\1\2\u0139\u0142\3\2"+
		"\2\2\u013a\u013b\7@\2\2\u013b\u0142\b\17\1\2\u013c\u013d\7\t\2\2\u013d"+
		"\u013e\5\36\20\2\u013e\u013f\7\63\2\2\u013f\u0140\b\17\1\2\u0140\u0142"+
		"\3\2\2\2\u0141\u012b\3\2\2\2\u0141\u0130\3\2\2\2\u0141\u0135\3\2\2\2\u0141"+
		"\u013a\3\2\2\2\u0141\u013c\3\2\2\2\u0142\35\3\2\2\2\u0143\u0144\b\20\1"+
		"\2\u0144\u0145\5\34\17\2\u0145\u0146\b\20\1\2\u0146\u014b\3\2\2\2\u0147"+
		"\u0148\5 \21\2\u0148\u0149\b\20\1\2\u0149\u014b\3\2\2\2\u014a\u0143\3"+
		"\2\2\2\u014a\u0147\3\2\2\2\u014b\u0153\3\2\2\2\u014c\u014d\f\3\2\2\u014d"+
		"\u014e\7/\2\2\u014e\u014f\5 \21\2\u014f\u0150\b\20\1\2\u0150\u0152\3\2"+
		"\2\2\u0151\u014c\3\2\2\2\u0152\u0155\3\2\2\2\u0153\u0151\3\2\2\2\u0153"+
		"\u0154\3\2\2\2\u0154\37\3\2\2\2\u0155\u0153\3\2\2\2\u0156\u0157\b\21\1"+
		"\2\u0157\u0158\5\34\17\2\u0158\u0159\b\21\1\2\u0159\u015e\3\2\2\2\u015a"+
		"\u015b\5\"\22\2\u015b\u015c\b\21\1\2\u015c\u015e\3\2\2\2\u015d\u0156\3"+
		"\2\2\2\u015d\u015a\3\2\2\2\u015e\u0166\3\2\2\2\u015f\u0160\f\3\2\2\u0160"+
		"\u0161\7\7\2\2\u0161\u0162\5\"\22\2\u0162\u0163\b\21\1\2\u0163\u0165\3"+
		"\2\2\2\u0164\u015f\3\2\2\2\u0165\u0168\3\2\2\2\u0166\u0164\3\2\2\2\u0166"+
		"\u0167\3\2\2\2\u0167!\3\2\2\2\u0168\u0166\3\2\2\2\u0169\u016a\b\22\1\2"+
		"\u016a\u016b\5\34\17\2\u016b\u016c\b\22\1\2\u016c\u0171\3\2\2\2\u016d"+
		"\u016e\5$\23\2\u016e\u016f\b\22\1\2\u016f\u0171\3\2\2\2\u0170\u0169\3"+
		"\2\2\2\u0170\u016d\3\2\2\2\u0171\u0179\3\2\2\2\u0172\u0173\f\3\2\2\u0173"+
		"\u0174\7*\2\2\u0174\u0175\5$\23\2\u0175\u0176\b\22\1\2\u0176\u0178\3\2"+
		"\2\2\u0177\u0172\3\2\2\2\u0178\u017b\3\2\2\2\u0179\u0177\3\2\2\2\u0179"+
		"\u017a\3\2\2\2\u017a#\3\2\2\2\u017b\u0179\3\2\2\2\u017c\u017d\5\34\17"+
		"\2\u017d\u017e\b\23\1\2\u017e\u0184\3\2\2\2\u017f\u0180\7\27\2\2\u0180"+
		"\u0181\5$\23\2\u0181\u0182\b\23\1\2\u0182\u0184\3\2\2\2\u0183\u017c\3"+
		"\2\2\2\u0183\u017f\3\2\2\2\u0184%\3\2\2\2\u0185\u0186\5\62\32\2\u0186"+
		"\u0187\b\24\1\2\u0187\u01a8\3\2\2\2\u0188\u0189\5\64\33\2\u0189\u018a"+
		"\b\24\1\2\u018a\u01a8\3\2\2\2\u018b\u018c\5\66\34\2\u018c\u018d\b\24\1"+
		"\2\u018d\u01a8\3\2\2\2\u018e\u018f\5\60\31\2\u018f\u0190\b\24\1\2\u0190"+
		"\u01a8\3\2\2\2\u0191\u0192\7\t\2\2\u0192\u0193\5(\25\2\u0193\u0194\7\63"+
		"\2\2\u0194\u0195\b\24\1\2\u0195\u01a8\3\2\2\2\u0196\u0197\7/\2\2\u0197"+
		"\u0198\5(\25\2\u0198\u0199\7/\2\2\u0199\u019a\b\24\1\2\u019a\u01a8\3\2"+
		"\2\2\u019b\u019c\7\25\2\2\u019c\u019d\5(\25\2\u019d\u019e\7\60\2\2\u019e"+
		"\u019f\b\24\1\2\u019f\u01a8\3\2\2\2\u01a0\u01a1\7\25\2\2\u01a1\u01a2\5"+
		"(\25\2\u01a2\u01a3\7\13\2\2\u01a3\u01a4\5(\25\2\u01a4\u01a5\7\60\2\2\u01a5"+
		"\u01a6\b\24\1\2\u01a6\u01a8\3\2\2\2\u01a7\u0185\3\2\2\2\u01a7\u0188\3"+
		"\2\2\2\u01a7\u018b\3\2\2\2\u01a7\u018e\3\2\2\2\u01a7\u0191\3\2\2\2\u01a7"+
		"\u0196\3\2\2\2\u01a7\u019b\3\2\2\2\u01a7\u01a0\3\2\2\2\u01a8\'\3\2\2\2"+
		"\u01a9\u01aa\b\25\1\2\u01aa\u01ab\5*\26\2\u01ab\u01ac\7\36\2\2\u01ac\u01ad"+
		"\5(\25\4\u01ad\u01ae\b\25\1\2\u01ae\u01b9\3\2\2\2\u01af\u01b0\5&\24\2"+
		"\u01b0\u01b1\b\25\1\2\u01b1\u01b9\3\2\2\2\u01b2\u01b3\58\35\2\u01b3\u01b4"+
		"\b\25\1\2\u01b4\u01b9\3\2\2\2\u01b5\u01b6\5*\26\2\u01b6\u01b7\b\25\1\2"+
		"\u01b7\u01b9\3\2\2\2\u01b8\u01a9\3\2\2\2\u01b8\u01af\3\2\2\2\u01b8\u01b2"+
		"\3\2\2\2\u01b8\u01b5\3\2\2\2\u01b9\u01c6\3\2\2\2\u01ba\u01bb\f\5\2\2\u01bb"+
		"\u01bc\7\36\2\2\u01bc\u01bd\5*\26\2\u01bd\u01be\b\25\1\2\u01be\u01c5\3"+
		"\2\2\2\u01bf\u01c0\f\3\2\2\u01c0\u01c1\7\66\2\2\u01c1\u01c2\5*\26\2\u01c2"+
		"\u01c3\b\25\1\2\u01c3\u01c5\3\2\2\2\u01c4\u01ba\3\2\2\2\u01c4\u01bf\3"+
		"\2\2\2\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7"+
		")\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c9\u01ca\b\26\1\2\u01ca\u01cb\7\66\2"+
		"\2\u01cb\u01cc\5*\26\6\u01cc\u01cd\b\26\1\2\u01cd\u01e0\3\2\2\2\u01ce"+
		"\u01cf\7\36\2\2\u01cf\u01d0\5*\26\5\u01d0\u01d1\b\26\1\2\u01d1\u01e0\3"+
		"\2\2\2\u01d2\u01d4\7\35\2\2\u01d3\u01d5\7+\2\2\u01d4\u01d3\3\2\2\2\u01d4"+
		"\u01d5\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d7\5*\26\4\u01d7\u01d8\b\26"+
		"\1\2\u01d8\u01e0\3\2\2\2\u01d9\u01da\5&\24\2\u01da\u01db\b\26\1\2\u01db"+
		"\u01e0\3\2\2\2\u01dc\u01dd\5,\27\2\u01dd\u01de\b\26\1\2\u01de\u01e0\3"+
		"\2\2\2\u01df\u01c9\3\2\2\2\u01df\u01ce\3\2\2\2\u01df\u01d2\3\2\2\2\u01df"+
		"\u01d9\3\2\2\2\u01df\u01dc\3\2\2\2\u01e0\u01ee\3\2\2\2\u01e1\u01e2\f\7"+
		"\2\2\u01e2\u01e3\7+\2\2\u01e3\u01e4\5*\26\b\u01e4\u01e5\b\26\1\2\u01e5"+
		"\u01ed\3\2\2\2\u01e6\u01e8\f\3\2\2\u01e7\u01e9\7+\2\2\u01e8\u01e7\3\2"+
		"\2\2\u01e8\u01e9\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01eb\7\35\2\2\u01eb"+
		"\u01ed\b\26\1\2\u01ec\u01e1\3\2\2\2\u01ec\u01e6\3\2\2\2\u01ed\u01f0\3"+
		"\2\2\2\u01ee\u01ec\3\2\2\2\u01ee\u01ef\3\2\2\2\u01ef+\3\2\2\2\u01f0\u01ee"+
		"\3\2\2\2\u01f1\u01f2\b\27\1\2\u01f2\u01f3\5&\24\2\u01f3\u01f4\b\27\1\2"+
		"\u01f4\u01f9\3\2\2\2\u01f5\u01f6\5.\30\2\u01f6\u01f7\b\27\1\2\u01f7\u01f9"+
		"\3\2\2\2\u01f8\u01f1\3\2\2\2\u01f8\u01f5\3\2\2\2\u01f9\u0201\3\2\2\2\u01fa"+
		"\u01fb\f\3\2\2\u01fb\u01fc\7\37\2\2\u01fc\u01fd\5,\27\4\u01fd\u01fe\b"+
		"\27\1\2\u01fe\u0200\3\2\2\2\u01ff\u01fa\3\2\2\2\u0200\u0203\3\2\2\2\u0201"+
		"\u01ff\3\2\2\2\u0201\u0202\3\2\2\2\u0202-\3\2\2\2\u0203\u0201\3\2\2\2"+
		"\u0204\u0205\b\30\1\2\u0205\u0206\5&\24\2\u0206\u0207\b\30\1\2\u0207\u020f"+
		"\3\2\2\2\u0208\u0209\f\3\2\2\u0209\u020a\7\7\2\2\u020a\u020b\5.\30\4\u020b"+
		"\u020c\b\30\1\2\u020c\u020e\3\2\2\2\u020d\u0208\3\2\2\2\u020e\u0211\3"+
		"\2\2\2\u020f\u020d\3\2\2\2\u020f\u0210\3\2\2\2\u0210/\3\2\2\2\u0211\u020f"+
		"\3\2\2\2\u0212\u0213\t\3\2\2\u0213\u0214\7\t\2\2\u0214\u0215\5(\25\2\u0215"+
		"\u0216\7\63\2\2\u0216\u0217\b\31\1\2\u0217\u022d\3\2\2\2\u0218\u0219\t"+
		"\4\2\2\u0219\u021a\7\t\2\2\u021a\u021b\5(\25\2\u021b\u021c\7\63\2\2\u021c"+
		"\u021d\b\31\1\2\u021d\u022d\3\2\2\2\u021e\u021f\t\5\2\2\u021f\u0220\7"+
		"\t\2\2\u0220\u0221\5(\25\2\u0221\u0222\7\63\2\2\u0222\u0223\b\31\1\2\u0223"+
		"\u022d\3\2\2\2\u0224\u0225\t\6\2\2\u0225\u0226\7\t\2\2\u0226\u0227\5("+
		"\25\2\u0227\u0228\7\13\2\2\u0228\u0229\5(\25\2\u0229\u022a\7\63\2\2\u022a"+
		"\u022b\b\31\1\2\u022b\u022d\3\2\2\2\u022c\u0212\3\2\2\2\u022c\u0218\3"+
		"\2\2\2\u022c\u021e\3\2\2\2\u022c\u0224\3\2\2\2\u022d\61\3\2\2\2\u022e"+
		"\u022f\7\33\2\2\u022f\u0235\b\32\1\2\u0230\u0231\7\20\2\2\u0231\u0235"+
		"\b\32\1\2\u0232\u0233\7 \2\2\u0233\u0235\b\32\1\2\u0234\u022e\3\2\2\2"+
		"\u0234\u0230\3\2\2\2\u0234\u0232\3\2\2\2\u0235\63\3\2\2\2\u0236\u0237"+
		"\7J\2\2\u0237\u0238\b\33\1\2\u0238\65\3\2\2\2\u0239\u023b\7\36\2\2\u023a"+
		"\u0239\3\2\2\2\u023a\u023b\3\2\2\2\u023b\u023c\3\2\2\2\u023c\u023d\t\7"+
		"\2\2\u023d\u0242\b\34\1\2\u023e\u023f\7\66\2\2\u023f\u0240\t\7\2\2\u0240"+
		"\u0242\b\34\1\2\u0241\u023a\3\2\2\2\u0241\u023e\3\2\2\2\u0242\67\3\2\2"+
		"\2\u0243\u0245\7\25\2\2\u0244\u0246\7\36\2\2\u0245\u0244\3\2\2\2\u0245"+
		"\u0246\3\2\2\2\u0246\u0247\3\2\2\2\u0247\u0248\t\7\2\2\u0248\u024a\7\13"+
		"\2\2\u0249\u024b\7\36\2\2\u024a\u0249\3\2\2\2\u024a\u024b\3\2\2\2\u024b"+
		"\u024c\3\2\2\2\u024c\u024d\t\7\2\2\u024d\u024e\7\60\2\2\u024e\u0295\b"+
		"\35\1\2\u024f\u0251\7\25\2\2\u0250\u0252\7\36\2\2\u0251\u0250\3\2\2\2"+
		"\u0251\u0252\3\2\2\2\u0252\u0253\3\2\2\2\u0253\u0254\t\7\2\2\u0254\u0255"+
		"\7\13\2\2\u0255\u0256\7\66\2\2\u0256\u0257\t\7\2\2\u0257\u0258\7\60\2"+
		"\2\u0258\u0295\b\35\1\2\u0259\u025a\7\25\2\2\u025a\u025b\7\66\2\2\u025b"+
		"\u025c\t\7\2\2\u025c\u025e\7\13\2\2\u025d\u025f\7\36\2\2\u025e\u025d\3"+
		"\2\2\2\u025e\u025f\3\2\2\2\u025f\u0260\3\2\2\2\u0260\u0261\t\7\2\2\u0261"+
		"\u0262\7\60\2\2\u0262\u0295\b\35\1\2\u0263\u0264\7\25\2\2\u0264\u0265"+
		"\7\66\2\2\u0265\u0266\t\7\2\2\u0266\u0267\7\13\2\2\u0267\u0268\7\66\2"+
		"\2\u0268\u0269\t\7\2\2\u0269\u026a\7\60\2\2\u026a\u0295\b\35\1\2\u026b"+
		"\u026d\7\36\2\2\u026c\u026b\3\2\2\2\u026c\u026d\3\2\2\2\u026d\u026e\3"+
		"\2\2\2\u026e\u026f\t\7\2\2\u026f\u0270\7\36\2\2\u0270\u0271\t\7\2\2\u0271"+
		"\u0272\7\35\2\2\u0272\u0295\b\35\1\2\u0273\u0275\7\36\2\2\u0274\u0273"+
		"\3\2\2\2\u0274\u0275\3\2\2\2\u0275\u0276\3\2\2\2\u0276\u0277\t\7\2\2\u0277"+
		"\u0278\7\66\2\2\u0278\u0279\t\7\2\2\u0279\u027a\7\35\2\2\u027a\u0295\b"+
		"\35\1\2\u027b\u027d\7\36\2\2\u027c\u027b\3\2\2\2\u027c\u027d\3\2\2\2\u027d"+
		"\u027e\3\2\2\2\u027e\u027f\t\7\2\2\u027f\u0280\7\35\2\2\u0280\u0295\b"+
		"\35\1\2\u0281\u0282\7\66\2\2\u0282\u0283\t\7\2\2\u0283\u0284\7\36\2\2"+
		"\u0284\u0285\t\7\2\2\u0285\u0286\7\35\2\2\u0286\u0295\b\35\1\2\u0287\u0288"+
		"\7\66\2\2\u0288\u0289\t\7\2\2\u0289\u028a\7\66\2\2\u028a\u028b\t\7\2\2"+
		"\u028b\u028c\7\35\2\2\u028c\u0295\b\35\1\2\u028d\u028e\7\66\2\2\u028e"+
		"\u028f\t\7\2\2\u028f\u0290\7\35\2\2\u0290\u0295\b\35\1\2\u0291\u0292\5"+
		"\66\34\2\u0292\u0293\b\35\1\2\u0293\u0295\3\2\2\2\u0294\u0243\3\2\2\2"+
		"\u0294\u024f\3\2\2\2\u0294\u0259\3\2\2\2\u0294\u0263\3\2\2\2\u0294\u026c"+
		"\3\2\2\2\u0294\u0274\3\2\2\2\u0294\u027c\3\2\2\2\u0294\u0281\3\2\2\2\u0294"+
		"\u0287\3\2\2\2\u0294\u028d\3\2\2\2\u0294\u0291\3\2\2\2\u02959\3\2\2\2"+
		"\u0296\u0297\7B\2\2\u0297\u0298\7J\2\2\u0298\u0299\b\36\1\2\u0299\u029b"+
		"\7\4\2\2\u029a\u029c\5<\37\2\u029b\u029a\3\2\2\2\u029c\u029d\3\2\2\2\u029d"+
		"\u029b\3\2\2\2\u029d\u029e\3\2\2\2\u029e\u029f\3\2\2\2\u029f\u02a0\7#"+
		"\2\2\u02a0;\3\2\2\2\u02a1\u02a2\7.\2\2\u02a2\u02a3\5H%\2\u02a3\u02a4\7"+
		"\60\2\2\u02a4\u02a5\5H%\2\u02a5\u02a6\7\13\2\2\u02a6\u02a7\7F\2\2\u02a7"+
		"\u02a8\7\13\2\2\u02a8\u02a9\5(\25\2\u02a9\u02aa\7\26\2\2\u02aa\u02ab\7"+
		"!\2\2\u02ab\u02ac\b\37\1\2\u02ac\u02b8\3\2\2\2\u02ad\u02ae\7.\2\2\u02ae"+
		"\u02af\5H%\2\u02af\u02b0\7\60\2\2\u02b0\u02b1\5H%\2\u02b1\u02b2\7\13\2"+
		"\2\u02b2\u02b3\7F\2\2\u02b3\u02b4\7\26\2\2\u02b4\u02b5\7!\2\2\u02b5\u02b6"+
		"\b\37\1\2\u02b6\u02b8\3\2\2\2\u02b7\u02a1\3\2\2\2\u02b7\u02ad\3\2\2\2"+
		"\u02b8=\3\2\2\2\u02b9\u02ba\7=\2\2\u02ba\u02bb\b \1\2\u02bb\u02bf\7\4"+
		"\2\2\u02bc\u02be\5@!\2\u02bd\u02bc\3\2\2\2\u02be\u02c1\3\2\2\2\u02bf\u02bd"+
		"\3\2\2\2\u02bf\u02c0\3\2\2\2\u02c0\u02c2\3\2\2\2\u02c1\u02bf\3\2\2\2\u02c2"+
		"\u02c3\7#\2\2\u02c3?\3\2\2\2\u02c4\u02c5\b!\1\2\u02c5\u02c6\5\30\r\2\u02c6"+
		"\u02c7\b!\1\2\u02c7A\3\2\2\2\u02c8\u02c9\7C\2\2\u02c9\u02ca\7\t\2\2\u02ca"+
		"\u02cb\5D#\2\u02cb\u02cc\7\63\2\2\u02cc\u02cd\7.\2\2\u02cd\u02ce\t\7\2"+
		"\2\u02ce\u02cf\7\26\2\2\u02cf\u02d0\7\4\2\2\u02d0\u02d1\5F$\2\u02d1\u02d2"+
		"\7#\2\2\u02d2\u02d3\b\"\1\2\u02d3C\3\2\2\2\u02d4\u02d5\b#\1\2\u02d5\u02d6"+
		"\5(\25\2\u02d6\u02d7\t\2\2\2\u02d7\u02d8\5(\25\2\u02d8\u02d9\b#\1\2\u02d9"+
		"\u02e1\3\2\2\2\u02da\u02db\f\3\2\2\u02db\u02dc\t\b\2\2\u02dc\u02dd\5D"+
		"#\4\u02dd\u02de\b#\1\2\u02de\u02e0\3\2\2\2\u02df\u02da\3\2\2\2\u02e0\u02e3"+
		"\3\2\2\2\u02e1\u02df\3\2\2\2\u02e1\u02e2\3\2\2\2\u02e2E\3\2\2\2\u02e3"+
		"\u02e1\3\2\2\2\u02e4\u02e5\5(\25\2\u02e5\u02e6\b$\1\2\u02e6\u02fe\3\2"+
		"\2\2\u02e7\u02e8\5(\25\2\u02e8\u02e9\7\13\2\2\u02e9\u02ea\5(\25\2\u02ea"+
		"\u02eb\7\13\2\2\u02eb\u02ec\5(\25\2\u02ec\u02ed\b$\1\2\u02ed\u02fe\3\2"+
		"\2\2\u02ee\u02ef\5(\25\2\u02ef\u02f0\7\13\2\2\u02f0\u02f1\5(\25\2\u02f1"+
		"\u02f2\7\13\2\2\u02f2\u02f3\5(\25\2\u02f3\u02f4\7\13\2\2\u02f4\u02f5\5"+
		"(\25\2\u02f5\u02f6\b$\1\2\u02f6\u02fe\3\2\2\2\u02f7\u02f8\7J\2\2\u02f8"+
		"\u02f9\7.\2\2\u02f9\u02fa\5(\25\2\u02fa\u02fb\7\26\2\2\u02fb\u02fc\b$"+
		"\1\2\u02fc\u02fe\3\2\2\2\u02fd\u02e4\3\2\2\2\u02fd\u02e7\3\2\2\2\u02fd"+
		"\u02ee\3\2\2\2\u02fd\u02f7\3\2\2\2\u02feG\3\2\2\2\u02ff\u0300\7\t\2\2"+
		"\u0300\u0301\t\7\2\2\u0301\u0302\7\13\2\2\u0302\u0303\t\7\2\2\u0303\u0304"+
		"\7\13\2\2\u0304\u0305\t\7\2\2\u0305\u0306\7\13\2\2\u0306\u0307\t\7\2\2"+
		"\u0307\u0308\7\63\2\2\u0308\u030d\b%\1\2\u0309\u030a\7\32\2\2\u030a\u030b"+
		"\7D\2\2\u030b\u030d\b%\1\2\u030c\u02ff\3\2\2\2\u030c\u0309\3\2\2\2\u030d"+
		"I\3\2\2\2\u030e\u030f\7\2\2\3\u030fK\3\2\2\2:bfjw{\u0080\u008b\u009e\u00a9"+
		"\u00b8\u00d8\u00ea\u00f7\u0102\u0111\u0119\u011c\u0128\u0141\u014a\u0153"+
		"\u015d\u0166\u0170\u0179\u0183\u01a7\u01b8\u01c4\u01c6\u01d4\u01df\u01e8"+
		"\u01ec\u01ee\u01f8\u0201\u020f\u022c\u0234\u023a\u0241\u0245\u024a\u0251"+
		"\u025e\u026c\u0274\u027c\u0294\u029d\u02b7\u02bf\u02e1\u02fd\u030c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}