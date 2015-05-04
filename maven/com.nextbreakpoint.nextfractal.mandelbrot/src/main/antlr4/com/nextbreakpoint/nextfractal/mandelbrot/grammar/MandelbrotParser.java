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
		PATHOP_1POINTS=69, PATHOP_2POINTS=70, VARIABLE=71, COMMENT=72, WHITESPACE=73;
	public static final String[] tokenNames = {
		"<INVALID>", "'cos'", "'{'", "'='", "'asin'", "'^'", "'im'", "'('", "'min'", 
		"','", "'pha'", "'re'", "'atan'", "'sqrt'", "'pi'", "'ceil'", "'mod'", 
		"'>='", "'log'", "'<'", "']'", "'~'", "'abs'", "'<>'", "'#'", "'e'", "'floor'", 
		"'i'", "'+'", "'/'", "'2pi'", "';'", "'max'", "'}'", "'mod2'", "'?'", 
		"'sin'", "'<='", "'pow'", "'~?'", "'&'", "'*'", "'tan'", "'atan2'", "'['", 
		"'|'", "'>'", "'acos'", "'else'", "')'", "'exp'", "'hypot'", "'-'", "'fractal'", 
		"'orbit'", "'trap'", "'begin'", "'loop'", "'end'", "'init'", "'if'", "'stop'", 
		"'julia'", "'color'", "'palette'", "'rule'", "ARGB", "RATIONAL", "INTEGER", 
		"PATHOP_1POINTS", "PATHOP_2POINTS", "VARIABLE", "COMMENT", "WHITESPACE"
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
			while (_la==PATHOP_1POINTS || _la==PATHOP_2POINTS) {
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
		public ComplexContext complex(int i) {
			return getRuleContext(ComplexContext.class,i);
		}
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
			setState(203);
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
				
			setState(206); statement();

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
				
			setState(210); statement();

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
				
			setState(214); statement();

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
			setState(271);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(217); ((StatementContext)_localctx).v = match(VARIABLE);
				setState(218); match(3);
				setState(219); ((StatementContext)_localctx).e = expression(0);
				setState(221);
				_la = _input.LA(1);
				if (_la==31) {
					{
					setState(220); match(31);
					}
				}


						builder.registerVariable((((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result.isReal(), ((StatementContext)_localctx).v);
						builder.appendStatement(new ASTAssignStatement(((StatementContext)_localctx).v, (((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(225); ((StatementContext)_localctx).f = match(IF);
				setState(226); match(7);
				setState(227); ((StatementContext)_localctx).c = conditionexp(0);
				setState(228); match(49);
				setState(229); match(2);

						builder.pushStatementList();
					
				setState(234);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
					{
					{
					setState(231); statement();
					}
					}
					setState(236);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(237); match(33);

						ASTStatementList thenList = builder.getStatementList();
						builder.popStatementList();
					
				setState(239); match(48);
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

						ASTStatementList elseList = builder.getStatementList();
						builder.popStatementList();
						builder.appendStatement(new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, thenList, elseList));
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(251); ((StatementContext)_localctx).f = match(IF);
				setState(252); match(7);
				setState(253); ((StatementContext)_localctx).c = conditionexp(0);
				setState(254); match(49);
				setState(255); match(2);

						builder.pushStatementList();
					
				setState(260);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
					{
					{
					setState(257); statement();
					}
					}
					setState(262);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(263); match(33);

						ASTStatementList thenList = builder.getStatementList();
						builder.popStatementList();
						builder.appendStatement(new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, thenList));
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(266); ((StatementContext)_localctx).t = match(STOP);
				setState(268);
				_la = _input.LA(1);
				if (_la==31) {
					{
					setState(267); match(31);
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
			setState(274); ((VariablelistContext)_localctx).v = match(VARIABLE);

					builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(283);
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
					setState(277);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(278); match(9);
					setState(279); ((VariablelistContext)_localctx).v = match(VARIABLE);

					          		builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
					          	
					}
					} 
				}
				setState(285);
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
			setState(308);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(286); ((SimpleconditionexpContext)_localctx).e1 = expression(0);
				setState(287);
				((SimpleconditionexpContext)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 23) | (1L << 37) | (1L << 46))) != 0)) ) {
					((SimpleconditionexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(288); ((SimpleconditionexpContext)_localctx).e2 = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionCompareOp(((SimpleconditionexpContext)_localctx).e1.result.getLocation(), (((SimpleconditionexpContext)_localctx).o!=null?((SimpleconditionexpContext)_localctx).o.getText():null), ((SimpleconditionexpContext)_localctx).e1.result, ((SimpleconditionexpContext)_localctx).e2.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(291); ((SimpleconditionexpContext)_localctx).v = match(VARIABLE);
				setState(292); match(35);
				setState(293); ((SimpleconditionexpContext)_localctx).e = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionTrap(((SimpleconditionexpContext)_localctx).v, (((SimpleconditionexpContext)_localctx).v!=null?((SimpleconditionexpContext)_localctx).v.getText():null), ((SimpleconditionexpContext)_localctx).e.result, true);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(296); ((SimpleconditionexpContext)_localctx).v = match(VARIABLE);
				setState(297); match(39);
				setState(298); ((SimpleconditionexpContext)_localctx).e = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionTrap(((SimpleconditionexpContext)_localctx).v, (((SimpleconditionexpContext)_localctx).v!=null?((SimpleconditionexpContext)_localctx).v.getText():null), ((SimpleconditionexpContext)_localctx).e.result, false);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(301); ((SimpleconditionexpContext)_localctx).t = match(JULIA);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionJulia(((SimpleconditionexpContext)_localctx).t);
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(303); ((SimpleconditionexpContext)_localctx).s = match(7);
				setState(304); ((SimpleconditionexpContext)_localctx).c = conditionexp(0);
				setState(305); match(49);

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
			setState(317);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(311); ((ConditionexpContext)_localctx).c = simpleconditionexp();

						((ConditionexpContext)_localctx).result =  ((ConditionexpContext)_localctx).c.result;
					
				}
				break;

			case 2:
				{
				setState(314); ((ConditionexpContext)_localctx).c2 = conditionexp2(0);

						((ConditionexpContext)_localctx).result =  ((ConditionexpContext)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(326);
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
					setState(319);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(320); ((ConditionexpContext)_localctx).l = match(45);
					setState(321); ((ConditionexpContext)_localctx).c2 = conditionexp2(0);

					          		((ConditionexpContext)_localctx).result =  new ASTConditionLogicOp(((ConditionexpContext)_localctx).c1.result.getLocation(), (((ConditionexpContext)_localctx).l!=null?((ConditionexpContext)_localctx).l.getText():null), ((ConditionexpContext)_localctx).c1.result, ((ConditionexpContext)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(328);
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
			setState(336);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(330); ((Conditionexp2Context)_localctx).c = simpleconditionexp();

						((Conditionexp2Context)_localctx).result =  ((Conditionexp2Context)_localctx).c.result;
					
				}
				break;

			case 2:
				{
				setState(333); ((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

						((Conditionexp2Context)_localctx).result =  ((Conditionexp2Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(345);
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
					setState(338);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(339); ((Conditionexp2Context)_localctx).l = match(5);
					setState(340); ((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

					          		((Conditionexp2Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp2Context)_localctx).c1.result.getLocation(), (((Conditionexp2Context)_localctx).l!=null?((Conditionexp2Context)_localctx).l.getText():null), ((Conditionexp2Context)_localctx).c1.result, ((Conditionexp2Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(347);
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
			setState(355);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(349); ((Conditionexp3Context)_localctx).c = simpleconditionexp();

						((Conditionexp3Context)_localctx).result =  ((Conditionexp3Context)_localctx).c.result;
					
				}
				break;

			case 2:
				{
				setState(352); ((Conditionexp3Context)_localctx).c2 = conditionexp4();

						((Conditionexp3Context)_localctx).result =  ((Conditionexp3Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(364);
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
					setState(357);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(358); ((Conditionexp3Context)_localctx).l = match(40);
					setState(359); ((Conditionexp3Context)_localctx).c2 = conditionexp4();

					          		((Conditionexp3Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp3Context)_localctx).c1.result.getLocation(), (((Conditionexp3Context)_localctx).l!=null?((Conditionexp3Context)_localctx).l.getText():null), ((Conditionexp3Context)_localctx).c1.result, ((Conditionexp3Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(366);
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
			setState(374);
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
				setState(367); ((Conditionexp4Context)_localctx).c1 = simpleconditionexp();

						((Conditionexp4Context)_localctx).result =  ((Conditionexp4Context)_localctx).c1.result;
					
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 2);
				{
				setState(370); ((Conditionexp4Context)_localctx).n = match(21);
				setState(371); ((Conditionexp4Context)_localctx).c2 = conditionexp4();

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
			setState(410);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(376); ((SimpleexpressionContext)_localctx).p = constant();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).p.result;
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(379); ((SimpleexpressionContext)_localctx).v = variable();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).v.result;
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(382); ((SimpleexpressionContext)_localctx).r = real();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).r.result;
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(385); ((SimpleexpressionContext)_localctx).f = function();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).f.result;
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(388); ((SimpleexpressionContext)_localctx).t = match(7);
				setState(389); ((SimpleexpressionContext)_localctx).e = expression(0);
				setState(390); match(49);

						((SimpleexpressionContext)_localctx).result =  new ASTParen(((SimpleexpressionContext)_localctx).t, ((SimpleexpressionContext)_localctx).e.result);
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(393); ((SimpleexpressionContext)_localctx).m = match(45);
				setState(394); ((SimpleexpressionContext)_localctx).e = expression(0);
				setState(395); match(45);

						((SimpleexpressionContext)_localctx).result =  new ASTFunction(((SimpleexpressionContext)_localctx).m, "mod", ((SimpleexpressionContext)_localctx).e.result);	
					
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(398); ((SimpleexpressionContext)_localctx).a = match(19);
				setState(399); ((SimpleexpressionContext)_localctx).e = expression(0);
				setState(400); match(46);

						((SimpleexpressionContext)_localctx).result =  new ASTFunction(((SimpleexpressionContext)_localctx).a, "pha", ((SimpleexpressionContext)_localctx).e.result);	
					
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(403); ((SimpleexpressionContext)_localctx).a = match(19);
				setState(404); ((SimpleexpressionContext)_localctx).er = expression(0);
				setState(405); match(9);
				setState(406); ((SimpleexpressionContext)_localctx).ei = expression(0);
				setState(407); match(46);

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
			setState(427);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(413); ((ExpressionContext)_localctx).e2 = expression2(0);
				setState(414); ((ExpressionContext)_localctx).s = match(28);
				setState(415); ((ExpressionContext)_localctx).e1 = expression(2);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e2.result, ((ExpressionContext)_localctx).e1.result);		
					
				}
				break;

			case 2:
				{
				setState(418); ((ExpressionContext)_localctx).e = simpleexpression();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e.result;	
					
				}
				break;

			case 3:
				{
				setState(421); ((ExpressionContext)_localctx).c = complex();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).c.result;
					
				}
				break;

			case 4:
				{
				setState(424); ((ExpressionContext)_localctx).e2 = expression2(0);

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e2.result;	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(441);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(439);
					switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(429);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(430); ((ExpressionContext)_localctx).s = match(28);
						setState(431); ((ExpressionContext)_localctx).e2 = expression2(0);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;

					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(434);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(435); ((ExpressionContext)_localctx).s = match(52);
						setState(436); ((ExpressionContext)_localctx).e2 = expression2(0);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					}
					} 
				}
				setState(443);
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
			setState(466);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(445); ((Expression2Context)_localctx).s = match(52);
				setState(446); ((Expression2Context)_localctx).e2 = expression2(4);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "-", ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(449); ((Expression2Context)_localctx).s = match(28);
				setState(450); ((Expression2Context)_localctx).e2 = expression2(3);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "+", ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 3:
				{
				setState(453); ((Expression2Context)_localctx).i = match(27);
				setState(455);
				_la = _input.LA(1);
				if (_la==41) {
					{
					setState(454); match(41);
					}
				}

				setState(457); ((Expression2Context)_localctx).e2 = expression2(2);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 4:
				{
				setState(460); ((Expression2Context)_localctx).e = simpleexpression();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e.result;	
					
				}
				break;

			case 5:
				{
				setState(463); ((Expression2Context)_localctx).e3 = expression3(0);

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(481);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(479);
					switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
					case 1:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(468);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(469); ((Expression2Context)_localctx).s = match(41);
						setState(470); ((Expression2Context)_localctx).e2 = expression2(6);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "*", ((Expression2Context)_localctx).e1.result, ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;

					case 2:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e2 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(473);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(475);
						_la = _input.LA(1);
						if (_la==41) {
							{
							setState(474); match(41);
							}
						}

						setState(477); ((Expression2Context)_localctx).i = match(27);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					}
					} 
				}
				setState(483);
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
			setState(491);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				{
				setState(485); ((Expression3Context)_localctx).e = simpleexpression();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e.result;	
					
				}
				break;

			case 2:
				{
				setState(488); ((Expression3Context)_localctx).e3 = expression4(0);

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(500);
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
					setState(493);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(494); ((Expression3Context)_localctx).s = match(29);
					setState(495); ((Expression3Context)_localctx).e2 = expression3(2);

					          		((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).s, "/", ((Expression3Context)_localctx).e1.result, ((Expression3Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(502);
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
			setState(504); ((Expression4Context)_localctx).e = simpleexpression();

					((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).e.result;	
				
			}
			_ctx.stop = _input.LT(-1);
			setState(514);
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
					setState(507);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(508); ((Expression4Context)_localctx).s = match(5);
					setState(509); ((Expression4Context)_localctx).e2 = expression4(2);

					          		((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).s, "^", ((Expression4Context)_localctx).e1.result, ((Expression4Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(516);
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
			setState(543);
			switch (_input.LA(1)) {
			case 6:
			case 10:
			case 11:
			case 16:
			case 34:
				enterOuterAlt(_localctx, 1);
				{
				setState(517);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 10) | (1L << 11) | (1L << 16) | (1L << 34))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(518); match(7);
				setState(519); ((FunctionContext)_localctx).e = expression(0);
				setState(520); match(49);

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
				setState(523);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 4) | (1L << 12) | (1L << 36) | (1L << 42) | (1L << 47))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(524); match(7);
				setState(525); ((FunctionContext)_localctx).e = expression(0);
				setState(526); match(49);

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
				setState(529);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 13) | (1L << 15) | (1L << 18) | (1L << 22) | (1L << 26) | (1L << 50))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(530); match(7);
				setState(531); ((FunctionContext)_localctx).e = expression(0);
				setState(532); match(49);

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
				setState(535);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 8) | (1L << 32) | (1L << 38) | (1L << 43) | (1L << 51))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(536); match(7);
				setState(537); ((FunctionContext)_localctx).e1 = expression(0);
				setState(538); match(9);
				setState(539); ((FunctionContext)_localctx).e2 = expression(0);
				setState(540); match(49);

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
			setState(551);
			switch (_input.LA(1)) {
			case 25:
				enterOuterAlt(_localctx, 1);
				{
				setState(545); ((ConstantContext)_localctx).p = match(25);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.E);
					
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 2);
				{
				setState(547); ((ConstantContext)_localctx).p = match(14);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.PI);
					
				}
				break;
			case 30:
				enterOuterAlt(_localctx, 3);
				{
				setState(549); ((ConstantContext)_localctx).p = match(30);

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
			setState(553); ((VariableContext)_localctx).v = match(VARIABLE);

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
			setState(564);
			switch (_input.LA(1)) {
			case 28:
			case RATIONAL:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(557);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(556); match(28);
					}
				}

				setState(559);
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
				setState(561); match(52);
				setState(562);
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
			setState(647);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(566); match(19);
				setState(568);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(567); match(28);
					}
				}

				setState(570);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(571); match(9);
				setState(573);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(572); match(28);
					}
				}

				setState(575);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(576); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(578); match(19);
				setState(580);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(579); match(28);
					}
				}

				setState(582);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(583); match(9);
				setState(584); match(52);
				setState(585);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(586); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(588); match(19);
				setState(589); match(52);
				setState(590);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(591); match(9);
				setState(593);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(592); match(28);
					}
				}

				setState(595);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(596); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(598); match(19);
				setState(599); match(52);
				setState(600);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(601); match(9);
				setState(602); match(52);
				setState(603);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(604); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(607);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(606); match(28);
					}
				}

				setState(609);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(610); match(28);
				setState(611);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(612); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(615);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(614); match(28);
					}
				}

				setState(617);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(618); match(52);
				setState(619);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(620); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(623);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(622); match(28);
					}
				}

				setState(625);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(626); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble((((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(628); match(52);
				setState(629);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(630); match(28);
				setState(631);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(632); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(634); match(52);
				setState(635);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(636); match(52);
				setState(637);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(638); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(640); match(52);
				setState(641);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(642); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(644); ((ComplexContext)_localctx).rn = real();

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
			setState(649); ((PaletteContext)_localctx).p = match(PALETTE);
			setState(650); ((PaletteContext)_localctx).v = match(VARIABLE);

					builder.addPalette(new ASTPalette(((PaletteContext)_localctx).p, (((PaletteContext)_localctx).v!=null?((PaletteContext)_localctx).v.getText():null))); 
				
			setState(652); match(2);
			setState(654); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(653); paletteelement();
				}
				}
				setState(656); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==44 );
			setState(658); match(33);
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
			setState(682);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(660); ((PaletteelementContext)_localctx).t = match(44);
				setState(661); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(662); match(46);
				setState(663); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(664); match(9);
				setState(665); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(666); match(9);
				setState(667); ((PaletteelementContext)_localctx).e = expression(0);
				setState(668); match(20);
				setState(669); match(31);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), ((PaletteelementContext)_localctx).e.result));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(672); ((PaletteelementContext)_localctx).t = match(44);
				setState(673); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(674); match(46);
				setState(675); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(676); match(9);
				setState(677); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(678); match(20);
				setState(679); match(31);

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
			setState(684); ((ColorinitContext)_localctx).i = match(INIT);

					builder.setColorContext(true);
					builder.setColorInit(new ASTColorInit(((ColorinitContext)_localctx).i));
				
			setState(686); match(2);
			setState(690);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
				{
				{
				setState(687); colorstatement();
				}
				}
				setState(692);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(693); match(33);
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
				
			setState(696); statement();

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
			setState(699); ((ColorruleContext)_localctx).t = match(RULE);
			setState(700); match(7);
			setState(701); ((ColorruleContext)_localctx).r = ruleexp(0);
			setState(702); match(49);
			setState(703); match(44);
			setState(704);
			((ColorruleContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==RATIONAL || _la==INTEGER) ) {
				((ColorruleContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(705); match(20);
			setState(706); match(2);
			setState(707); ((ColorruleContext)_localctx).c = colorexp();
			setState(708); match(33);

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
			setState(712); ((RuleexpContext)_localctx).e1 = expression(0);
			setState(713);
			((RuleexpContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 23) | (1L << 37) | (1L << 46))) != 0)) ) {
				((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(714); ((RuleexpContext)_localctx).e2 = expression(0);

					((RuleexpContext)_localctx).result =  new ASTRuleCompareOp(((RuleexpContext)_localctx).e1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).e1.result, ((RuleexpContext)_localctx).e2.result);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(724);
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
					setState(717);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(718);
					((RuleexpContext)_localctx).o = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 40) | (1L << 45))) != 0)) ) {
						((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(719); ((RuleexpContext)_localctx).r2 = ruleexp(2);

					          		((RuleexpContext)_localctx).result =  new ASTRuleLogicOp(((RuleexpContext)_localctx).r1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).r1.result, ((RuleexpContext)_localctx).r2.result);
					          	
					}
					} 
				}
				setState(726);
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
			setState(752);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(727); ((ColorexpContext)_localctx).e1 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(730); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(731); match(9);
				setState(732); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(733); match(9);
				setState(734); ((ColorexpContext)_localctx).e3 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(737); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(738); match(9);
				setState(739); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(740); match(9);
				setState(741); ((ColorexpContext)_localctx).e3 = expression(0);
				setState(742); match(9);
				setState(743); ((ColorexpContext)_localctx).e4 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result, ((ColorexpContext)_localctx).e4.result);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(746); ((ColorexpContext)_localctx).v = match(VARIABLE);
				setState(747); match(44);
				setState(748); ((ColorexpContext)_localctx).e = expression(0);
				setState(749); match(20);

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
			setState(767);
			switch (_input.LA(1)) {
			case 7:
				enterOuterAlt(_localctx, 1);
				{
				setState(754); match(7);
				setState(755);
				((ColorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(756); match(9);
				setState(757);
				((ColorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(758); match(9);
				setState(759);
				((ColorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(760); match(9);
				setState(761);
				((ColorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(762); match(49);

						((ColorargbContext)_localctx).result =  new ASTColorARGB(builder.parseFloat((((ColorargbContext)_localctx).a!=null?((ColorargbContext)_localctx).a.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).r!=null?((ColorargbContext)_localctx).r.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).g!=null?((ColorargbContext)_localctx).g.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).b!=null?((ColorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 2);
				{
				setState(764); match(24);
				setState(765); ((ColorargbContext)_localctx).argb = match(ARGB);

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
			setState(769); match(EOF);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3K\u0306\4\2\t\2\4"+
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
		"\5\t\u00ce\n\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\5\r\u00e0\n\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00eb"+
		"\n\r\f\r\16\r\u00ee\13\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00f6\n\r\f\r\16"+
		"\r\u00f9\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0105\n\r\f"+
		"\r\16\r\u0108\13\r\3\r\3\r\3\r\3\r\3\r\5\r\u010f\n\r\3\r\5\r\u0112\n\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u011c\n\16\f\16\16\16\u011f"+
		"\13\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u0137\n\17\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\5\20\u0140\n\20\3\20\3\20\3\20\3\20\3\20\7\20"+
		"\u0147\n\20\f\20\16\20\u014a\13\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\5\21\u0153\n\21\3\21\3\21\3\21\3\21\3\21\7\21\u015a\n\21\f\21\16\21\u015d"+
		"\13\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u0166\n\22\3\22\3\22\3"+
		"\22\3\22\3\22\7\22\u016d\n\22\f\22\16\22\u0170\13\22\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\5\23\u0179\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24"+
		"\u019d\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\5\25\u01ae\n\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\7\25\u01ba\n\25\f\25\16\25\u01bd\13\25\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u01ca\n\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\5\26\u01d5\n\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\5\26\u01de\n\26\3\26\3\26\7\26\u01e2\n\26\f\26\16\26\u01e5\13\26"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u01ee\n\27\3\27\3\27\3\27\3\27"+
		"\3\27\7\27\u01f5\n\27\f\27\16\27\u01f8\13\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\7\30\u0203\n\30\f\30\16\30\u0206\13\30\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u0222\n\31\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\5\32\u022a\n\32\3\33\3\33\3\33\3\34\5\34\u0230"+
		"\n\34\3\34\3\34\3\34\3\34\3\34\5\34\u0237\n\34\3\35\3\35\5\35\u023b\n"+
		"\35\3\35\3\35\3\35\5\35\u0240\n\35\3\35\3\35\3\35\3\35\3\35\5\35\u0247"+
		"\n\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u0254"+
		"\n\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35"+
		"\u0262\n\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u026a\n\35\3\35\3\35\3"+
		"\35\3\35\3\35\3\35\5\35\u0272\n\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\5\35\u028a\n\35\3\36\3\36\3\36\3\36\3\36\6\36\u0291\n\36\r\36\16"+
		"\36\u0292\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u02ad"+
		"\n\37\3 \3 \3 \3 \7 \u02b3\n \f \16 \u02b6\13 \3 \3 \3!\3!\3!\3!\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3#"+
		"\3#\3#\7#\u02d5\n#\f#\16#\u02d8\13#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3"+
		"$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\5$\u02f3\n$\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\5%\u0302\n%\3&\3&\3&\2\13\32\36 \"(*,.D\'\2\4\6"+
		"\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJ\2\t\b"+
		"\2\5\5\23\23\25\25\31\31\'\'\60\60\6\2\b\b\f\r\22\22$$\b\2\3\3\6\6\16"+
		"\16&&,,\61\61\b\2\17\17\21\21\24\24\30\30\34\34\64\64\7\2\n\n\"\"((--"+
		"\65\65\3\2EF\5\2\7\7**//\u0336\2L\3\2\2\2\4T\3\2\2\2\6n\3\2\2\2\b\u0085"+
		"\3\2\2\2\n\u0090\3\2\2\2\f\u00a3\3\2\2\2\16\u00ae\3\2\2\2\20\u00cd\3\2"+
		"\2\2\22\u00cf\3\2\2\2\24\u00d3\3\2\2\2\26\u00d7\3\2\2\2\30\u0111\3\2\2"+
		"\2\32\u0113\3\2\2\2\34\u0136\3\2\2\2\36\u013f\3\2\2\2 \u0152\3\2\2\2\""+
		"\u0165\3\2\2\2$\u0178\3\2\2\2&\u019c\3\2\2\2(\u01ad\3\2\2\2*\u01d4\3\2"+
		"\2\2,\u01ed\3\2\2\2.\u01f9\3\2\2\2\60\u0221\3\2\2\2\62\u0229\3\2\2\2\64"+
		"\u022b\3\2\2\2\66\u0236\3\2\2\28\u0289\3\2\2\2:\u028b\3\2\2\2<\u02ac\3"+
		"\2\2\2>\u02ae\3\2\2\2@\u02b9\3\2\2\2B\u02bd\3\2\2\2D\u02c9\3\2\2\2F\u02f2"+
		"\3\2\2\2H\u0301\3\2\2\2J\u0303\3\2\2\2LM\7\67\2\2MN\b\2\1\2NO\7\4\2\2"+
		"OP\5\4\3\2PQ\5\6\4\2QR\7#\2\2RS\5J&\2S\3\3\2\2\2TU\78\2\2UV\7.\2\2VW\5"+
		"8\35\2WX\7\13\2\2XY\58\35\2YZ\7\26\2\2Z[\b\3\1\2[\\\7.\2\2\\]\5\32\16"+
		"\2]^\7\26\2\2^b\7\4\2\2_a\5\16\b\2`_\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2"+
		"\2\2cf\3\2\2\2db\3\2\2\2eg\5\b\5\2fe\3\2\2\2fg\3\2\2\2gh\3\2\2\2hj\5\n"+
		"\6\2ik\5\f\7\2ji\3\2\2\2jk\3\2\2\2kl\3\2\2\2lm\7#\2\2m\5\3\2\2\2no\7A"+
		"\2\2op\7.\2\2pq\5H%\2qr\7\26\2\2rs\b\4\1\2sw\7\4\2\2tv\5:\36\2ut\3\2\2"+
		"\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x{\3\2\2\2yw\3\2\2\2z|\5> \2{z\3\2\2\2"+
		"{|\3\2\2\2|\u0080\3\2\2\2}\177\5B\"\2~}\3\2\2\2\177\u0082\3\2\2\2\u0080"+
		"~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0083\3\2\2\2\u0082\u0080\3\2\2\2"+
		"\u0083\u0084\7#\2\2\u0084\7\3\2\2\2\u0085\u0086\7:\2\2\u0086\u0087\b\5"+
		"\1\2\u0087\u008b\7\4\2\2\u0088\u008a\5\22\n\2\u0089\u0088\3\2\2\2\u008a"+
		"\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008e\3\2"+
		"\2\2\u008d\u008b\3\2\2\2\u008e\u008f\7#\2\2\u008f\t\3\2\2\2\u0090\u0091"+
		"\7;\2\2\u0091\u0092\7.\2\2\u0092\u0093\7F\2\2\u0093\u0094\7\13\2\2\u0094"+
		"\u0095\7F\2\2\u0095\u0096\7\26\2\2\u0096\u0097\7\t\2\2\u0097\u0098\5\36"+
		"\20\2\u0098\u0099\7\63\2\2\u0099\u009a\b\6\1\2\u009a\u009e\7\4\2\2\u009b"+
		"\u009d\5\24\13\2\u009c\u009b\3\2\2\2\u009d\u00a0\3\2\2\2\u009e\u009c\3"+
		"\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1"+
		"\u00a2\7#\2\2\u00a2\13\3\2\2\2\u00a3\u00a4\7<\2\2\u00a4\u00a5\b\7\1\2"+
		"\u00a5\u00a9\7\4\2\2\u00a6\u00a8\5\26\f\2\u00a7\u00a6\3\2\2\2\u00a8\u00ab"+
		"\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac\3\2\2\2\u00ab"+
		"\u00a9\3\2\2\2\u00ac\u00ad\7#\2\2\u00ad\r\3\2\2\2\u00ae\u00af\79\2\2\u00af"+
		"\u00b0\7I\2\2\u00b0\u00b1\7.\2\2\u00b1\u00b2\58\35\2\u00b2\u00b3\7\26"+
		"\2\2\u00b3\u00b4\b\b\1\2\u00b4\u00b8\7\4\2\2\u00b5\u00b7\5\20\t\2\u00b6"+
		"\u00b5\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2"+
		"\2\2\u00b9\u00bb\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bc\7#\2\2\u00bc"+
		"\17\3\2\2\2\u00bd\u00be\7G\2\2\u00be\u00bf\7\t\2\2\u00bf\u00c0\58\35\2"+
		"\u00c0\u00c1\7\63\2\2\u00c1\u00c2\7!\2\2\u00c2\u00c3\b\t\1\2\u00c3\u00ce"+
		"\3\2\2\2\u00c4\u00c5\7H\2\2\u00c5\u00c6\7\t\2\2\u00c6\u00c7\58\35\2\u00c7"+
		"\u00c8\7\13\2\2\u00c8\u00c9\58\35\2\u00c9\u00ca\7\63\2\2\u00ca\u00cb\7"+
		"!\2\2\u00cb\u00cc\b\t\1\2\u00cc\u00ce\3\2\2\2\u00cd\u00bd\3\2\2\2\u00cd"+
		"\u00c4\3\2\2\2\u00ce\21\3\2\2\2\u00cf\u00d0\b\n\1\2\u00d0\u00d1\5\30\r"+
		"\2\u00d1\u00d2\b\n\1\2\u00d2\23\3\2\2\2\u00d3\u00d4\b\13\1\2\u00d4\u00d5"+
		"\5\30\r\2\u00d5\u00d6\b\13\1\2\u00d6\25\3\2\2\2\u00d7\u00d8\b\f\1\2\u00d8"+
		"\u00d9\5\30\r\2\u00d9\u00da\b\f\1\2\u00da\27\3\2\2\2\u00db\u00dc\7I\2"+
		"\2\u00dc\u00dd\7\5\2\2\u00dd\u00df\5(\25\2\u00de\u00e0\7!\2\2\u00df\u00de"+
		"\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e2\b\r\1\2\u00e2"+
		"\u0112\3\2\2\2\u00e3\u00e4\7>\2\2\u00e4\u00e5\7\t\2\2\u00e5\u00e6\5\36"+
		"\20\2\u00e6\u00e7\7\63\2\2\u00e7\u00e8\7\4\2\2\u00e8\u00ec\b\r\1\2\u00e9"+
		"\u00eb\5\30\r\2\u00ea\u00e9\3\2\2\2\u00eb\u00ee\3\2\2\2\u00ec\u00ea\3"+
		"\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ef\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ef"+
		"\u00f0\7#\2\2\u00f0\u00f1\b\r\1\2\u00f1\u00f2\7\62\2\2\u00f2\u00f3\7\4"+
		"\2\2\u00f3\u00f7\b\r\1\2\u00f4\u00f6\5\30\r\2\u00f5\u00f4\3\2\2\2\u00f6"+
		"\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00fa\3\2"+
		"\2\2\u00f9\u00f7\3\2\2\2\u00fa\u00fb\7#\2\2\u00fb\u00fc\b\r\1\2\u00fc"+
		"\u0112\3\2\2\2\u00fd\u00fe\7>\2\2\u00fe\u00ff\7\t\2\2\u00ff\u0100\5\36"+
		"\20\2\u0100\u0101\7\63\2\2\u0101\u0102\7\4\2\2\u0102\u0106\b\r\1\2\u0103"+
		"\u0105\5\30\r\2\u0104\u0103\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3"+
		"\2\2\2\u0106\u0107\3\2\2\2\u0107\u0109\3\2\2\2\u0108\u0106\3\2\2\2\u0109"+
		"\u010a\7#\2\2\u010a\u010b\b\r\1\2\u010b\u0112\3\2\2\2\u010c\u010e\7?\2"+
		"\2\u010d\u010f\7!\2\2\u010e\u010d\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0110"+
		"\3\2\2\2\u0110\u0112\b\r\1\2\u0111\u00db\3\2\2\2\u0111\u00e3\3\2\2\2\u0111"+
		"\u00fd\3\2\2\2\u0111\u010c\3\2\2\2\u0112\31\3\2\2\2\u0113\u0114\b\16\1"+
		"\2\u0114\u0115\7I\2\2\u0115\u0116\b\16\1\2\u0116\u011d\3\2\2\2\u0117\u0118"+
		"\f\3\2\2\u0118\u0119\7\13\2\2\u0119\u011a\7I\2\2\u011a\u011c\b\16\1\2"+
		"\u011b\u0117\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2\2\2\u011d\u011e"+
		"\3\2\2\2\u011e\33\3\2\2\2\u011f\u011d\3\2\2\2\u0120\u0121\5(\25\2\u0121"+
		"\u0122\t\2\2\2\u0122\u0123\5(\25\2\u0123\u0124\b\17\1\2\u0124\u0137\3"+
		"\2\2\2\u0125\u0126\7I\2\2\u0126\u0127\7%\2\2\u0127\u0128\5(\25\2\u0128"+
		"\u0129\b\17\1\2\u0129\u0137\3\2\2\2\u012a\u012b\7I\2\2\u012b\u012c\7)"+
		"\2\2\u012c\u012d\5(\25\2\u012d\u012e\b\17\1\2\u012e\u0137\3\2\2\2\u012f"+
		"\u0130\7@\2\2\u0130\u0137\b\17\1\2\u0131\u0132\7\t\2\2\u0132\u0133\5\36"+
		"\20\2\u0133\u0134\7\63\2\2\u0134\u0135\b\17\1\2\u0135\u0137\3\2\2\2\u0136"+
		"\u0120\3\2\2\2\u0136\u0125\3\2\2\2\u0136\u012a\3\2\2\2\u0136\u012f\3\2"+
		"\2\2\u0136\u0131\3\2\2\2\u0137\35\3\2\2\2\u0138\u0139\b\20\1\2\u0139\u013a"+
		"\5\34\17\2\u013a\u013b\b\20\1\2\u013b\u0140\3\2\2\2\u013c\u013d\5 \21"+
		"\2\u013d\u013e\b\20\1\2\u013e\u0140\3\2\2\2\u013f\u0138\3\2\2\2\u013f"+
		"\u013c\3\2\2\2\u0140\u0148\3\2\2\2\u0141\u0142\f\3\2\2\u0142\u0143\7/"+
		"\2\2\u0143\u0144\5 \21\2\u0144\u0145\b\20\1\2\u0145\u0147\3\2\2\2\u0146"+
		"\u0141\3\2\2\2\u0147\u014a\3\2\2\2\u0148\u0146\3\2\2\2\u0148\u0149\3\2"+
		"\2\2\u0149\37\3\2\2\2\u014a\u0148\3\2\2\2\u014b\u014c\b\21\1\2\u014c\u014d"+
		"\5\34\17\2\u014d\u014e\b\21\1\2\u014e\u0153\3\2\2\2\u014f\u0150\5\"\22"+
		"\2\u0150\u0151\b\21\1\2\u0151\u0153\3\2\2\2\u0152\u014b\3\2\2\2\u0152"+
		"\u014f\3\2\2\2\u0153\u015b\3\2\2\2\u0154\u0155\f\3\2\2\u0155\u0156\7\7"+
		"\2\2\u0156\u0157\5\"\22\2\u0157\u0158\b\21\1\2\u0158\u015a\3\2\2\2\u0159"+
		"\u0154\3\2\2\2\u015a\u015d\3\2\2\2\u015b\u0159\3\2\2\2\u015b\u015c\3\2"+
		"\2\2\u015c!\3\2\2\2\u015d\u015b\3\2\2\2\u015e\u015f\b\22\1\2\u015f\u0160"+
		"\5\34\17\2\u0160\u0161\b\22\1\2\u0161\u0166\3\2\2\2\u0162\u0163\5$\23"+
		"\2\u0163\u0164\b\22\1\2\u0164\u0166\3\2\2\2\u0165\u015e\3\2\2\2\u0165"+
		"\u0162\3\2\2\2\u0166\u016e\3\2\2\2\u0167\u0168\f\3\2\2\u0168\u0169\7*"+
		"\2\2\u0169\u016a\5$\23\2\u016a\u016b\b\22\1\2\u016b\u016d\3\2\2\2\u016c"+
		"\u0167\3\2\2\2\u016d\u0170\3\2\2\2\u016e\u016c\3\2\2\2\u016e\u016f\3\2"+
		"\2\2\u016f#\3\2\2\2\u0170\u016e\3\2\2\2\u0171\u0172\5\34\17\2\u0172\u0173"+
		"\b\23\1\2\u0173\u0179\3\2\2\2\u0174\u0175\7\27\2\2\u0175\u0176\5$\23\2"+
		"\u0176\u0177\b\23\1\2\u0177\u0179\3\2\2\2\u0178\u0171\3\2\2\2\u0178\u0174"+
		"\3\2\2\2\u0179%\3\2\2\2\u017a\u017b\5\62\32\2\u017b\u017c\b\24\1\2\u017c"+
		"\u019d\3\2\2\2\u017d\u017e\5\64\33\2\u017e\u017f\b\24\1\2\u017f\u019d"+
		"\3\2\2\2\u0180\u0181\5\66\34\2\u0181\u0182\b\24\1\2\u0182\u019d\3\2\2"+
		"\2\u0183\u0184\5\60\31\2\u0184\u0185\b\24\1\2\u0185\u019d\3\2\2\2\u0186"+
		"\u0187\7\t\2\2\u0187\u0188\5(\25\2\u0188\u0189\7\63\2\2\u0189\u018a\b"+
		"\24\1\2\u018a\u019d\3\2\2\2\u018b\u018c\7/\2\2\u018c\u018d\5(\25\2\u018d"+
		"\u018e\7/\2\2\u018e\u018f\b\24\1\2\u018f\u019d\3\2\2\2\u0190\u0191\7\25"+
		"\2\2\u0191\u0192\5(\25\2\u0192\u0193\7\60\2\2\u0193\u0194\b\24\1\2\u0194"+
		"\u019d\3\2\2\2\u0195\u0196\7\25\2\2\u0196\u0197\5(\25\2\u0197\u0198\7"+
		"\13\2\2\u0198\u0199\5(\25\2\u0199\u019a\7\60\2\2\u019a\u019b\b\24\1\2"+
		"\u019b\u019d\3\2\2\2\u019c\u017a\3\2\2\2\u019c\u017d\3\2\2\2\u019c\u0180"+
		"\3\2\2\2\u019c\u0183\3\2\2\2\u019c\u0186\3\2\2\2\u019c\u018b\3\2\2\2\u019c"+
		"\u0190\3\2\2\2\u019c\u0195\3\2\2\2\u019d\'\3\2\2\2\u019e\u019f\b\25\1"+
		"\2\u019f\u01a0\5*\26\2\u01a0\u01a1\7\36\2\2\u01a1\u01a2\5(\25\4\u01a2"+
		"\u01a3\b\25\1\2\u01a3\u01ae\3\2\2\2\u01a4\u01a5\5&\24\2\u01a5\u01a6\b"+
		"\25\1\2\u01a6\u01ae\3\2\2\2\u01a7\u01a8\58\35\2\u01a8\u01a9\b\25\1\2\u01a9"+
		"\u01ae\3\2\2\2\u01aa\u01ab\5*\26\2\u01ab\u01ac\b\25\1\2\u01ac\u01ae\3"+
		"\2\2\2\u01ad\u019e\3\2\2\2\u01ad\u01a4\3\2\2\2\u01ad\u01a7\3\2\2\2\u01ad"+
		"\u01aa\3\2\2\2\u01ae\u01bb\3\2\2\2\u01af\u01b0\f\5\2\2\u01b0\u01b1\7\36"+
		"\2\2\u01b1\u01b2\5*\26\2\u01b2\u01b3\b\25\1\2\u01b3\u01ba\3\2\2\2\u01b4"+
		"\u01b5\f\3\2\2\u01b5\u01b6\7\66\2\2\u01b6\u01b7\5*\26\2\u01b7\u01b8\b"+
		"\25\1\2\u01b8\u01ba\3\2\2\2\u01b9\u01af\3\2\2\2\u01b9\u01b4\3\2\2\2\u01ba"+
		"\u01bd\3\2\2\2\u01bb\u01b9\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc)\3\2\2\2"+
		"\u01bd\u01bb\3\2\2\2\u01be\u01bf\b\26\1\2\u01bf\u01c0\7\66\2\2\u01c0\u01c1"+
		"\5*\26\6\u01c1\u01c2\b\26\1\2\u01c2\u01d5\3\2\2\2\u01c3\u01c4\7\36\2\2"+
		"\u01c4\u01c5\5*\26\5\u01c5\u01c6\b\26\1\2\u01c6\u01d5\3\2\2\2\u01c7\u01c9"+
		"\7\35\2\2\u01c8\u01ca\7+\2\2\u01c9\u01c8\3\2\2\2\u01c9\u01ca\3\2\2\2\u01ca"+
		"\u01cb\3\2\2\2\u01cb\u01cc\5*\26\4\u01cc\u01cd\b\26\1\2\u01cd\u01d5\3"+
		"\2\2\2\u01ce\u01cf\5&\24\2\u01cf\u01d0\b\26\1\2\u01d0\u01d5\3\2\2\2\u01d1"+
		"\u01d2\5,\27\2\u01d2\u01d3\b\26\1\2\u01d3\u01d5\3\2\2\2\u01d4\u01be\3"+
		"\2\2\2\u01d4\u01c3\3\2\2\2\u01d4\u01c7\3\2\2\2\u01d4\u01ce\3\2\2\2\u01d4"+
		"\u01d1\3\2\2\2\u01d5\u01e3\3\2\2\2\u01d6\u01d7\f\7\2\2\u01d7\u01d8\7+"+
		"\2\2\u01d8\u01d9\5*\26\b\u01d9\u01da\b\26\1\2\u01da\u01e2\3\2\2\2\u01db"+
		"\u01dd\f\3\2\2\u01dc\u01de\7+\2\2\u01dd\u01dc\3\2\2\2\u01dd\u01de\3\2"+
		"\2\2\u01de\u01df\3\2\2\2\u01df\u01e0\7\35\2\2\u01e0\u01e2\b\26\1\2\u01e1"+
		"\u01d6\3\2\2\2\u01e1\u01db\3\2\2\2\u01e2\u01e5\3\2\2\2\u01e3\u01e1\3\2"+
		"\2\2\u01e3\u01e4\3\2\2\2\u01e4+\3\2\2\2\u01e5\u01e3\3\2\2\2\u01e6\u01e7"+
		"\b\27\1\2\u01e7\u01e8\5&\24\2\u01e8\u01e9\b\27\1\2\u01e9\u01ee\3\2\2\2"+
		"\u01ea\u01eb\5.\30\2\u01eb\u01ec\b\27\1\2\u01ec\u01ee\3\2\2\2\u01ed\u01e6"+
		"\3\2\2\2\u01ed\u01ea\3\2\2\2\u01ee\u01f6\3\2\2\2\u01ef\u01f0\f\3\2\2\u01f0"+
		"\u01f1\7\37\2\2\u01f1\u01f2\5,\27\4\u01f2\u01f3\b\27\1\2\u01f3\u01f5\3"+
		"\2\2\2\u01f4\u01ef\3\2\2\2\u01f5\u01f8\3\2\2\2\u01f6\u01f4\3\2\2\2\u01f6"+
		"\u01f7\3\2\2\2\u01f7-\3\2\2\2\u01f8\u01f6\3\2\2\2\u01f9\u01fa\b\30\1\2"+
		"\u01fa\u01fb\5&\24\2\u01fb\u01fc\b\30\1\2\u01fc\u0204\3\2\2\2\u01fd\u01fe"+
		"\f\3\2\2\u01fe\u01ff\7\7\2\2\u01ff\u0200\5.\30\4\u0200\u0201\b\30\1\2"+
		"\u0201\u0203\3\2\2\2\u0202\u01fd\3\2\2\2\u0203\u0206\3\2\2\2\u0204\u0202"+
		"\3\2\2\2\u0204\u0205\3\2\2\2\u0205/\3\2\2\2\u0206\u0204\3\2\2\2\u0207"+
		"\u0208\t\3\2\2\u0208\u0209\7\t\2\2\u0209\u020a\5(\25\2\u020a\u020b\7\63"+
		"\2\2\u020b\u020c\b\31\1\2\u020c\u0222\3\2\2\2\u020d\u020e\t\4\2\2\u020e"+
		"\u020f\7\t\2\2\u020f\u0210\5(\25\2\u0210\u0211\7\63\2\2\u0211\u0212\b"+
		"\31\1\2\u0212\u0222\3\2\2\2\u0213\u0214\t\5\2\2\u0214\u0215\7\t\2\2\u0215"+
		"\u0216\5(\25\2\u0216\u0217\7\63\2\2\u0217\u0218\b\31\1\2\u0218\u0222\3"+
		"\2\2\2\u0219\u021a\t\6\2\2\u021a\u021b\7\t\2\2\u021b\u021c\5(\25\2\u021c"+
		"\u021d\7\13\2\2\u021d\u021e\5(\25\2\u021e\u021f\7\63\2\2\u021f\u0220\b"+
		"\31\1\2\u0220\u0222\3\2\2\2\u0221\u0207\3\2\2\2\u0221\u020d\3\2\2\2\u0221"+
		"\u0213\3\2\2\2\u0221\u0219\3\2\2\2\u0222\61\3\2\2\2\u0223\u0224\7\33\2"+
		"\2\u0224\u022a\b\32\1\2\u0225\u0226\7\20\2\2\u0226\u022a\b\32\1\2\u0227"+
		"\u0228\7 \2\2\u0228\u022a\b\32\1\2\u0229\u0223\3\2\2\2\u0229\u0225\3\2"+
		"\2\2\u0229\u0227\3\2\2\2\u022a\63\3\2\2\2\u022b\u022c\7I\2\2\u022c\u022d"+
		"\b\33\1\2\u022d\65\3\2\2\2\u022e\u0230\7\36\2\2\u022f\u022e\3\2\2\2\u022f"+
		"\u0230\3\2\2\2\u0230\u0231\3\2\2\2\u0231\u0232\t\7\2\2\u0232\u0237\b\34"+
		"\1\2\u0233\u0234\7\66\2\2\u0234\u0235\t\7\2\2\u0235\u0237\b\34\1\2\u0236"+
		"\u022f\3\2\2\2\u0236\u0233\3\2\2\2\u0237\67\3\2\2\2\u0238\u023a\7\25\2"+
		"\2\u0239\u023b\7\36\2\2\u023a\u0239\3\2\2\2\u023a\u023b\3\2\2\2\u023b"+
		"\u023c\3\2\2\2\u023c\u023d\t\7\2\2\u023d\u023f\7\13\2\2\u023e\u0240\7"+
		"\36\2\2\u023f\u023e\3\2\2\2\u023f\u0240\3\2\2\2\u0240\u0241\3\2\2\2\u0241"+
		"\u0242\t\7\2\2\u0242\u0243\7\60\2\2\u0243\u028a\b\35\1\2\u0244\u0246\7"+
		"\25\2\2\u0245\u0247\7\36\2\2\u0246\u0245\3\2\2\2\u0246\u0247\3\2\2\2\u0247"+
		"\u0248\3\2\2\2\u0248\u0249\t\7\2\2\u0249\u024a\7\13\2\2\u024a\u024b\7"+
		"\66\2\2\u024b\u024c\t\7\2\2\u024c\u024d\7\60\2\2\u024d\u028a\b\35\1\2"+
		"\u024e\u024f\7\25\2\2\u024f\u0250\7\66\2\2\u0250\u0251\t\7\2\2\u0251\u0253"+
		"\7\13\2\2\u0252\u0254\7\36\2\2\u0253\u0252\3\2\2\2\u0253\u0254\3\2\2\2"+
		"\u0254\u0255\3\2\2\2\u0255\u0256\t\7\2\2\u0256\u0257\7\60\2\2\u0257\u028a"+
		"\b\35\1\2\u0258\u0259\7\25\2\2\u0259\u025a\7\66\2\2\u025a\u025b\t\7\2"+
		"\2\u025b\u025c\7\13\2\2\u025c\u025d\7\66\2\2\u025d\u025e\t\7\2\2\u025e"+
		"\u025f\7\60\2\2\u025f\u028a\b\35\1\2\u0260\u0262\7\36\2\2\u0261\u0260"+
		"\3\2\2\2\u0261\u0262\3\2\2\2\u0262\u0263\3\2\2\2\u0263\u0264\t\7\2\2\u0264"+
		"\u0265\7\36\2\2\u0265\u0266\t\7\2\2\u0266\u0267\7\35\2\2\u0267\u028a\b"+
		"\35\1\2\u0268\u026a\7\36\2\2\u0269\u0268\3\2\2\2\u0269\u026a\3\2\2\2\u026a"+
		"\u026b\3\2\2\2\u026b\u026c\t\7\2\2\u026c\u026d\7\66\2\2\u026d\u026e\t"+
		"\7\2\2\u026e\u026f\7\35\2\2\u026f\u028a\b\35\1\2\u0270\u0272\7\36\2\2"+
		"\u0271\u0270\3\2\2\2\u0271\u0272\3\2\2\2\u0272\u0273\3\2\2\2\u0273\u0274"+
		"\t\7\2\2\u0274\u0275\7\35\2\2\u0275\u028a\b\35\1\2\u0276\u0277\7\66\2"+
		"\2\u0277\u0278\t\7\2\2\u0278\u0279\7\36\2\2\u0279\u027a\t\7\2\2\u027a"+
		"\u027b\7\35\2\2\u027b\u028a\b\35\1\2\u027c\u027d\7\66\2\2\u027d\u027e"+
		"\t\7\2\2\u027e\u027f\7\66\2\2\u027f\u0280\t\7\2\2\u0280\u0281\7\35\2\2"+
		"\u0281\u028a\b\35\1\2\u0282\u0283\7\66\2\2\u0283\u0284\t\7\2\2\u0284\u0285"+
		"\7\35\2\2\u0285\u028a\b\35\1\2\u0286\u0287\5\66\34\2\u0287\u0288\b\35"+
		"\1\2\u0288\u028a\3\2\2\2\u0289\u0238\3\2\2\2\u0289\u0244\3\2\2\2\u0289"+
		"\u024e\3\2\2\2\u0289\u0258\3\2\2\2\u0289\u0261\3\2\2\2\u0289\u0269\3\2"+
		"\2\2\u0289\u0271\3\2\2\2\u0289\u0276\3\2\2\2\u0289\u027c\3\2\2\2\u0289"+
		"\u0282\3\2\2\2\u0289\u0286\3\2\2\2\u028a9\3\2\2\2\u028b\u028c\7B\2\2\u028c"+
		"\u028d\7I\2\2\u028d\u028e\b\36\1\2\u028e\u0290\7\4\2\2\u028f\u0291\5<"+
		"\37\2\u0290\u028f\3\2\2\2\u0291\u0292\3\2\2\2\u0292\u0290\3\2\2\2\u0292"+
		"\u0293\3\2\2\2\u0293\u0294\3\2\2\2\u0294\u0295\7#\2\2\u0295;\3\2\2\2\u0296"+
		"\u0297\7.\2\2\u0297\u0298\5H%\2\u0298\u0299\7\60\2\2\u0299\u029a\5H%\2"+
		"\u029a\u029b\7\13\2\2\u029b\u029c\7F\2\2\u029c\u029d\7\13\2\2\u029d\u029e"+
		"\5(\25\2\u029e\u029f\7\26\2\2\u029f\u02a0\7!\2\2\u02a0\u02a1\b\37\1\2"+
		"\u02a1\u02ad\3\2\2\2\u02a2\u02a3\7.\2\2\u02a3\u02a4\5H%\2\u02a4\u02a5"+
		"\7\60\2\2\u02a5\u02a6\5H%\2\u02a6\u02a7\7\13\2\2\u02a7\u02a8\7F\2\2\u02a8"+
		"\u02a9\7\26\2\2\u02a9\u02aa\7!\2\2\u02aa\u02ab\b\37\1\2\u02ab\u02ad\3"+
		"\2\2\2\u02ac\u0296\3\2\2\2\u02ac\u02a2\3\2\2\2\u02ad=\3\2\2\2\u02ae\u02af"+
		"\7=\2\2\u02af\u02b0\b \1\2\u02b0\u02b4\7\4\2\2\u02b1\u02b3\5@!\2\u02b2"+
		"\u02b1\3\2\2\2\u02b3\u02b6\3\2\2\2\u02b4\u02b2\3\2\2\2\u02b4\u02b5\3\2"+
		"\2\2\u02b5\u02b7\3\2\2\2\u02b6\u02b4\3\2\2\2\u02b7\u02b8\7#\2\2\u02b8"+
		"?\3\2\2\2\u02b9\u02ba\b!\1\2\u02ba\u02bb\5\30\r\2\u02bb\u02bc\b!\1\2\u02bc"+
		"A\3\2\2\2\u02bd\u02be\7C\2\2\u02be\u02bf\7\t\2\2\u02bf\u02c0\5D#\2\u02c0"+
		"\u02c1\7\63\2\2\u02c1\u02c2\7.\2\2\u02c2\u02c3\t\7\2\2\u02c3\u02c4\7\26"+
		"\2\2\u02c4\u02c5\7\4\2\2\u02c5\u02c6\5F$\2\u02c6\u02c7\7#\2\2\u02c7\u02c8"+
		"\b\"\1\2\u02c8C\3\2\2\2\u02c9\u02ca\b#\1\2\u02ca\u02cb\5(\25\2\u02cb\u02cc"+
		"\t\2\2\2\u02cc\u02cd\5(\25\2\u02cd\u02ce\b#\1\2\u02ce\u02d6\3\2\2\2\u02cf"+
		"\u02d0\f\3\2\2\u02d0\u02d1\t\b\2\2\u02d1\u02d2\5D#\4\u02d2\u02d3\b#\1"+
		"\2\u02d3\u02d5\3\2\2\2\u02d4\u02cf\3\2\2\2\u02d5\u02d8\3\2\2\2\u02d6\u02d4"+
		"\3\2\2\2\u02d6\u02d7\3\2\2\2\u02d7E\3\2\2\2\u02d8\u02d6\3\2\2\2\u02d9"+
		"\u02da\5(\25\2\u02da\u02db\b$\1\2\u02db\u02f3\3\2\2\2\u02dc\u02dd\5(\25"+
		"\2\u02dd\u02de\7\13\2\2\u02de\u02df\5(\25\2\u02df\u02e0\7\13\2\2\u02e0"+
		"\u02e1\5(\25\2\u02e1\u02e2\b$\1\2\u02e2\u02f3\3\2\2\2\u02e3\u02e4\5(\25"+
		"\2\u02e4\u02e5\7\13\2\2\u02e5\u02e6\5(\25\2\u02e6\u02e7\7\13\2\2\u02e7"+
		"\u02e8\5(\25\2\u02e8\u02e9\7\13\2\2\u02e9\u02ea\5(\25\2\u02ea\u02eb\b"+
		"$\1\2\u02eb\u02f3\3\2\2\2\u02ec\u02ed\7I\2\2\u02ed\u02ee\7.\2\2\u02ee"+
		"\u02ef\5(\25\2\u02ef\u02f0\7\26\2\2\u02f0\u02f1\b$\1\2\u02f1\u02f3\3\2"+
		"\2\2\u02f2\u02d9\3\2\2\2\u02f2\u02dc\3\2\2\2\u02f2\u02e3\3\2\2\2\u02f2"+
		"\u02ec\3\2\2\2\u02f3G\3\2\2\2\u02f4\u02f5\7\t\2\2\u02f5\u02f6\t\7\2\2"+
		"\u02f6\u02f7\7\13\2\2\u02f7\u02f8\t\7\2\2\u02f8\u02f9\7\13\2\2\u02f9\u02fa"+
		"\t\7\2\2\u02fa\u02fb\7\13\2\2\u02fb\u02fc\t\7\2\2\u02fc\u02fd\7\63\2\2"+
		"\u02fd\u0302\b%\1\2\u02fe\u02ff\7\32\2\2\u02ff\u0300\7D\2\2\u0300\u0302"+
		"\b%\1\2\u0301\u02f4\3\2\2\2\u0301\u02fe\3\2\2\2\u0302I\3\2\2\2\u0303\u0304"+
		"\7\2\2\3\u0304K\3\2\2\2:bfjw{\u0080\u008b\u009e\u00a9\u00b8\u00cd\u00df"+
		"\u00ec\u00f7\u0106\u010e\u0111\u011d\u0136\u013f\u0148\u0152\u015b\u0165"+
		"\u016e\u0178\u019c\u01ad\u01b9\u01bb\u01c9\u01d4\u01dd\u01e1\u01e3\u01ed"+
		"\u01f6\u0204\u0221\u0229\u022f\u0236\u023a\u023f\u0246\u0253\u0261\u0269"+
		"\u0271\u0289\u0292\u02ac\u02b4\u02d6\u02f2\u0301";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}