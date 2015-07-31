// Generated from com/nextbreakpoint/nextfractal/mandelbrot/grammar/Mandelbrot.g4 by ANTLR 4.5.1
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
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		FRACTAL=53, ORBIT=54, TRAP=55, BEGIN=56, LOOP=57, END=58, INIT=59, IF=60, 
		ELSE=61, STOP=62, JULIA=63, COLOR=64, PALETTE=65, RULE=66, ARGB32=67, 
		ARGB24=68, RATIONAL=69, INTEGER=70, PATHOP_1POINTS=71, PATHOP_2POINTS=72, 
		PATHOP_3POINTS=73, VARIABLE=74, COMMENT=75, WHITESPACE=76;
	public static final int
		RULE_fractal = 0, RULE_orbit = 1, RULE_color = 2, RULE_begin = 3, RULE_loop = 4, 
		RULE_end = 5, RULE_trap = 6, RULE_pathop = 7, RULE_beginstatement = 8, 
		RULE_loopstatement = 9, RULE_endstatement = 10, RULE_statement = 11, RULE_statevariable = 12, 
		RULE_statevariablelist = 13, RULE_simpleconditionexp = 14, RULE_conditionexp = 15, 
		RULE_conditionexp2 = 16, RULE_conditionexp3 = 17, RULE_conditionexp4 = 18, 
		RULE_simpleexpression = 19, RULE_expression = 20, RULE_expression2 = 21, 
		RULE_expression3 = 22, RULE_expression4 = 23, RULE_function = 24, RULE_constant = 25, 
		RULE_variable = 26, RULE_real = 27, RULE_complex = 28, RULE_palette = 29, 
		RULE_paletteelement = 30, RULE_colorinit = 31, RULE_colorstatement = 32, 
		RULE_colorrule = 33, RULE_ruleexp = 34, RULE_colorexp = 35, RULE_colorargb = 36, 
		RULE_backgroundcolorargb = 37, RULE_eof = 38;
	public static final String[] ruleNames = {
		"fractal", "orbit", "color", "begin", "loop", "end", "trap", "pathop", 
		"beginstatement", "loopstatement", "endstatement", "statement", "statevariable", 
		"statevariablelist", "simpleconditionexp", "conditionexp", "conditionexp2", 
		"conditionexp3", "conditionexp4", "simpleexpression", "expression", "expression2", 
		"expression3", "expression4", "function", "constant", "variable", "real", 
		"complex", "palette", "paletteelement", "colorinit", "colorstatement", 
		"colorrule", "ruleexp", "colorexp", "colorargb", "backgroundcolorargb", 
		"eof"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'['", "','", "']'", "'('", "')'", "';'", "'='", "'real'", 
		"'complex'", "'<'", "'>'", "'<='", "'>='", "'<>'", "'?'", "'~?'", "'|'", 
		"'^'", "'&'", "'~'", "'+'", "'-'", "'*'", "'i'", "'/'", "'mod'", "'mod2'", 
		"'pha'", "'re'", "'im'", "'cos'", "'sin'", "'tan'", "'acos'", "'asin'", 
		"'atan'", "'log'", "'exp'", "'sqrt'", "'abs'", "'ceil'", "'floor'", "'pow'", 
		"'atan2'", "'hypot'", "'max'", "'min'", "'e'", "'pi'", "'2pi'", "'fractal'", 
		"'orbit'", "'trap'", "'begin'", "'loop'", "'end'", "'init'", "'if'", "'else'", 
		"'stop'", "'julia'", "'color'", "'palette'", "'rule'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, "FRACTAL", "ORBIT", "TRAP", "BEGIN", "LOOP", 
		"END", "INIT", "IF", "ELSE", "STOP", "JULIA", "COLOR", "PALETTE", "RULE", 
		"ARGB32", "ARGB24", "RATIONAL", "INTEGER", "PATHOP_1POINTS", "PATHOP_2POINTS", 
		"PATHOP_3POINTS", "VARIABLE", "COMMENT", "WHITESPACE"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Mandelbrot.g4"; }

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
		public EofContext eof() {
			return getRuleContext(EofContext.class,0);
		}
		public TerminalNode FRACTAL() { return getToken(MandelbrotParser.FRACTAL, 0); }
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
			setState(78);
			((FractalContext)_localctx).f = match(FRACTAL);

					builder.setFractal(new ASTFractal(((FractalContext)_localctx).f));
				
			setState(80);
			match(T__0);
			setState(81);
			orbit();
			setState(82);
			color();
			setState(83);
			match(T__1);
			setState(84);
			eof();
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
		public StatevariablelistContext statevariablelist() {
			return getRuleContext(StatevariablelistContext.class,0);
		}
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public TerminalNode ORBIT() { return getToken(MandelbrotParser.ORBIT, 0); }
		public List<ComplexContext> complex() {
			return getRuleContexts(ComplexContext.class);
		}
		public ComplexContext complex(int i) {
			return getRuleContext(ComplexContext.class,i);
		}
		public List<TrapContext> trap() {
			return getRuleContexts(TrapContext.class);
		}
		public TrapContext trap(int i) {
			return getRuleContext(TrapContext.class,i);
		}
		public BeginContext begin() {
			return getRuleContext(BeginContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
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
			setState(86);
			((OrbitContext)_localctx).o = match(ORBIT);
			setState(87);
			match(T__2);
			setState(88);
			((OrbitContext)_localctx).ra = complex();
			setState(89);
			match(T__3);
			setState(90);
			((OrbitContext)_localctx).rb = complex();
			setState(91);
			match(T__4);

					builder.setOrbit(new ASTOrbit(((OrbitContext)_localctx).o, new ASTRegion(((OrbitContext)_localctx).ra.result, ((OrbitContext)_localctx).rb.result)));
				
			setState(93);
			match(T__2);
			setState(94);
			statevariablelist(0);
			setState(95);
			match(T__4);
			setState(96);
			match(T__0);
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TRAP) {
				{
				{
				setState(97);
				trap();
				}
				}
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(104);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(103);
				begin();
				}
			}

			setState(106);
			loop();
			setState(108);
			_la = _input.LA(1);
			if (_la==END) {
				{
				setState(107);
				end();
				}
			}

			setState(110);
			match(T__1);
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
		public BackgroundcolorargbContext argb;
		public TerminalNode COLOR() { return getToken(MandelbrotParser.COLOR, 0); }
		public BackgroundcolorargbContext backgroundcolorargb() {
			return getRuleContext(BackgroundcolorargbContext.class,0);
		}
		public List<PaletteContext> palette() {
			return getRuleContexts(PaletteContext.class);
		}
		public PaletteContext palette(int i) {
			return getRuleContext(PaletteContext.class,i);
		}
		public ColorinitContext colorinit() {
			return getRuleContext(ColorinitContext.class,0);
		}
		public List<ColorruleContext> colorrule() {
			return getRuleContexts(ColorruleContext.class);
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
			setState(112);
			((ColorContext)_localctx).c = match(COLOR);
			setState(113);
			match(T__2);
			setState(114);
			((ColorContext)_localctx).argb = backgroundcolorargb();
			setState(115);
			match(T__4);
			 
					builder.setColor(new ASTColor(((ColorContext)_localctx).c, ((ColorContext)_localctx).argb.result));
				
			setState(117);
			match(T__0);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PALETTE) {
				{
				{
				setState(118);
				palette();
				}
				}
				setState(123);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(125);
			_la = _input.LA(1);
			if (_la==INIT) {
				{
				setState(124);
				colorinit();
				}
			}

			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RULE) {
				{
				{
				setState(127);
				colorrule();
				}
				}
				setState(132);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(133);
			match(T__1);
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
		public BeginstatementContext beginstatement() {
			return getRuleContext(BeginstatementContext.class,0);
		}
		public TerminalNode BEGIN() { return getToken(MandelbrotParser.BEGIN, 0); }
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			((BeginContext)_localctx).b = match(BEGIN);
			 
					builder.setOrbitBegin(new ASTOrbitBegin(((BeginContext)_localctx).b));
				
			setState(137);
			match(T__0);
			setState(138);
			beginstatement();
			setState(139);
			match(T__1);
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
		public LoopstatementContext loopstatement() {
			return getRuleContext(LoopstatementContext.class,0);
		}
		public TerminalNode LOOP() { return getToken(MandelbrotParser.LOOP, 0); }
		public List<TerminalNode> INTEGER() { return getTokens(MandelbrotParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(MandelbrotParser.INTEGER, i);
		}
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			((LoopContext)_localctx).l = match(LOOP);
			setState(142);
			match(T__2);
			setState(143);
			((LoopContext)_localctx).lb = match(INTEGER);
			setState(144);
			match(T__3);
			setState(145);
			((LoopContext)_localctx).le = match(INTEGER);
			setState(146);
			match(T__4);
			setState(147);
			match(T__5);
			setState(148);
			((LoopContext)_localctx).e = conditionexp(0);
			setState(149);
			match(T__6);

					builder.setOrbitLoop(new ASTOrbitLoop(((LoopContext)_localctx).l, builder.parseInt((((LoopContext)_localctx).lb!=null?((LoopContext)_localctx).lb.getText():null)), builder.parseInt((((LoopContext)_localctx).le!=null?((LoopContext)_localctx).le.getText():null)), ((LoopContext)_localctx).e.result));
				
			setState(151);
			match(T__0);
			setState(152);
			loopstatement();
			setState(153);
			match(T__1);
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
		public EndstatementContext endstatement() {
			return getRuleContext(EndstatementContext.class,0);
		}
		public TerminalNode END() { return getToken(MandelbrotParser.END, 0); }
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			((EndContext)_localctx).e = match(END);

					builder.setOrbitEnd(new ASTOrbitEnd(((EndContext)_localctx).e));		
				
			setState(157);
			match(T__0);
			setState(158);
			endstatement();
			setState(159);
			match(T__1);
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
		public ComplexContext complex() {
			return getRuleContext(ComplexContext.class,0);
		}
		public List<PathopContext> pathop() {
			return getRuleContexts(PathopContext.class);
		}
		public PathopContext pathop(int i) {
			return getRuleContext(PathopContext.class,i);
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
			setState(161);
			((TrapContext)_localctx).t = match(TRAP);
			setState(162);
			((TrapContext)_localctx).n = match(VARIABLE);
			setState(163);
			match(T__2);
			setState(164);
			((TrapContext)_localctx).c = complex();
			setState(165);
			match(T__4);

					builder.addOrbitTrap(new ASTOrbitTrap(((TrapContext)_localctx).t, (((TrapContext)_localctx).n!=null?((TrapContext)_localctx).n.getText():null), ((TrapContext)_localctx).c.result));
				
			setState(167);
			match(T__0);
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (PATHOP_1POINTS - 71)) | (1L << (PATHOP_2POINTS - 71)) | (1L << (PATHOP_3POINTS - 71)))) != 0)) {
				{
				{
				setState(168);
				pathop();
				}
				}
				setState(173);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(174);
			match(T__1);
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
		public TerminalNode PATHOP_1POINTS() { return getToken(MandelbrotParser.PATHOP_1POINTS, 0); }
		public List<ComplexContext> complex() {
			return getRuleContexts(ComplexContext.class);
		}
		public ComplexContext complex(int i) {
			return getRuleContext(ComplexContext.class,i);
		}
		public TerminalNode PATHOP_2POINTS() { return getToken(MandelbrotParser.PATHOP_2POINTS, 0); }
		public TerminalNode PATHOP_3POINTS() { return getToken(MandelbrotParser.PATHOP_3POINTS, 0); }
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
				setState(176);
				((PathopContext)_localctx).o = match(PATHOP_1POINTS);
				setState(177);
				match(T__5);
				setState(178);
				((PathopContext)_localctx).c = complex();
				setState(179);
				match(T__6);
				setState(180);
				match(T__7);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c.result));
					
				}
				break;
			case PATHOP_2POINTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(183);
				((PathopContext)_localctx).o = match(PATHOP_2POINTS);
				setState(184);
				match(T__5);
				setState(185);
				((PathopContext)_localctx).c1 = complex();
				setState(186);
				match(T__3);
				setState(187);
				((PathopContext)_localctx).c2 = complex();
				setState(188);
				match(T__6);
				setState(189);
				match(T__7);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c1.result, ((PathopContext)_localctx).c2.result));
					
				}
				break;
			case PATHOP_3POINTS:
				enterOuterAlt(_localctx, 3);
				{
				setState(192);
				((PathopContext)_localctx).o = match(PATHOP_3POINTS);
				setState(193);
				match(T__5);
				setState(194);
				((PathopContext)_localctx).c1 = complex();
				setState(195);
				match(T__3);
				setState(196);
				((PathopContext)_localctx).c2 = complex();
				setState(197);
				match(T__3);
				setState(198);
				((PathopContext)_localctx).c3 = complex();
				setState(199);
				match(T__6);
				setState(200);
				match(T__7);

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
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{

					builder.pushStatementList();	
				
			setState(209);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
				{
				{
				setState(206);
				statement();
				}
				}
				setState(211);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

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
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{

					builder.pushScope();	
					builder.pushStatementList();	
				
			setState(218);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
				{
				{
				setState(215);
				statement();
				}
				}
				setState(220);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

					builder.addLoopStatements(builder.getStatementList());
					builder.popScope();	
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
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{

					builder.pushScope();	
					builder.pushStatementList();	
				
			setState(227);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
				{
				{
				setState(224);
				statement();
				}
				}
				setState(229);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

					builder.addEndStatements(builder.getStatementList());
					builder.popScope();	
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
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ELSE() { return getToken(MandelbrotParser.ELSE, 0); }
		public TerminalNode IF() { return getToken(MandelbrotParser.IF, 0); }
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode STOP() { return getToken(MandelbrotParser.STOP, 0); }
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
			setState(294);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(232);
				((StatementContext)_localctx).v = match(VARIABLE);
				setState(233);
				match(T__8);
				setState(234);
				((StatementContext)_localctx).e = expression(0);
				setState(236);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(235);
					match(T__7);
					}
				}


						builder.registerVariable((((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result.isReal(), ((StatementContext)_localctx).v);
						builder.appendStatement(new ASTAssignStatement(((StatementContext)_localctx).v, (((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result));
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(240);
				((StatementContext)_localctx).f = match(IF);
				setState(241);
				match(T__5);
				setState(242);
				((StatementContext)_localctx).c = conditionexp(0);
				setState(243);
				match(T__6);
				setState(244);
				match(T__0);

						builder.pushScope();	
						builder.pushStatementList();
					
				setState(249);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
					{
					{
					setState(246);
					statement();
					}
					}
					setState(251);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(252);
				match(T__1);

						ASTStatementList thenList = builder.getStatementList();
						builder.popScope();	
						builder.popStatementList();
					
				setState(254);
				match(ELSE);
				setState(255);
				match(T__0);

						builder.pushScope();	
						builder.pushStatementList();
					
				setState(260);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
					{
					{
					setState(257);
					statement();
					}
					}
					setState(262);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(263);
				match(T__1);

						ASTStatementList elseList = builder.getStatementList();
						builder.popScope();	
						builder.popStatementList();
						builder.appendStatement(new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, thenList, elseList));
					
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(266);
				((StatementContext)_localctx).f = match(IF);
				setState(267);
				match(T__5);
				setState(268);
				((StatementContext)_localctx).c = conditionexp(0);
				setState(269);
				match(T__6);
				setState(270);
				match(T__0);

						builder.pushScope();	
						builder.pushStatementList();
					
				setState(275);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
					{
					{
					setState(272);
					statement();
					}
					}
					setState(277);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(278);
				match(T__1);

						ASTStatementList thenList = builder.getStatementList();
						builder.popScope();	
						builder.popStatementList();
						builder.appendStatement(new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, thenList));
					
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(281);
				((StatementContext)_localctx).f = match(IF);
				setState(282);
				match(T__5);
				setState(283);
				((StatementContext)_localctx).c = conditionexp(0);
				setState(284);
				match(T__6);

						builder.pushScope();	
						builder.pushStatementList();
					
				setState(286);
				statement();

						ASTStatementList thenList = builder.getStatementList();
						builder.popScope();	
						builder.popStatementList();
						builder.appendStatement(new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, thenList));
					
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(289);
				((StatementContext)_localctx).t = match(STOP);
				setState(291);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(290);
					match(T__7);
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

	public static class StatevariableContext extends ParserRuleContext {
		public Token v;
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public StatevariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statevariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterStatevariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitStatevariable(this);
		}
	}

	public final StatevariableContext statevariable() throws RecognitionException {
		StatevariableContext _localctx = new StatevariableContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_statevariable);
		try {
			setState(304);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(296);
				match(T__9);
				setState(297);
				((StatevariableContext)_localctx).v = match(VARIABLE);

						builder.registerStateVariable((((StatevariableContext)_localctx).v!=null?((StatevariableContext)_localctx).v.getText():null), true, ((StatevariableContext)_localctx).v);
					
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				setState(299);
				match(T__10);
				setState(300);
				((StatevariableContext)_localctx).v = match(VARIABLE);

						builder.registerStateVariable((((StatevariableContext)_localctx).v!=null?((StatevariableContext)_localctx).v.getText():null), false, ((StatevariableContext)_localctx).v);
					
				}
				break;
			case VARIABLE:
				enterOuterAlt(_localctx, 3);
				{
				setState(302);
				((StatevariableContext)_localctx).v = match(VARIABLE);

						builder.registerStateVariable((((StatevariableContext)_localctx).v!=null?((StatevariableContext)_localctx).v.getText():null), "n".equals((((StatevariableContext)_localctx).v!=null?((StatevariableContext)_localctx).v.getText():null)), ((StatevariableContext)_localctx).v);
					
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

	public static class StatevariablelistContext extends ParserRuleContext {
		public StatevariableContext statevariable() {
			return getRuleContext(StatevariableContext.class,0);
		}
		public StatevariablelistContext statevariablelist() {
			return getRuleContext(StatevariablelistContext.class,0);
		}
		public StatevariablelistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statevariablelist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterStatevariablelist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitStatevariablelist(this);
		}
	}

	public final StatevariablelistContext statevariablelist() throws RecognitionException {
		return statevariablelist(0);
	}

	private StatevariablelistContext statevariablelist(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StatevariablelistContext _localctx = new StatevariablelistContext(_ctx, _parentState);
		StatevariablelistContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_statevariablelist, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(307);
			statevariable();
			}
			_ctx.stop = _input.LT(-1);
			setState(314);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StatevariablelistContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_statevariablelist);
					setState(309);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(310);
					match(T__3);
					setState(311);
					statevariable();
					}
					} 
				}
				setState(316);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public TerminalNode JULIA() { return getToken(MandelbrotParser.JULIA, 0); }
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
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
		enterRule(_localctx, 28, RULE_simpleconditionexp);
		int _la;
		try {
			setState(339);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(317);
				((SimpleconditionexpContext)_localctx).e1 = expression(0);
				setState(318);
				((SimpleconditionexpContext)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15))) != 0)) ) {
					((SimpleconditionexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(319);
				((SimpleconditionexpContext)_localctx).e2 = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionCompareOp(((SimpleconditionexpContext)_localctx).e1.result.getLocation(), (((SimpleconditionexpContext)_localctx).o!=null?((SimpleconditionexpContext)_localctx).o.getText():null), ((SimpleconditionexpContext)_localctx).e1.result, ((SimpleconditionexpContext)_localctx).e2.result);
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(322);
				((SimpleconditionexpContext)_localctx).v = match(VARIABLE);
				setState(323);
				match(T__16);
				setState(324);
				((SimpleconditionexpContext)_localctx).e = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionTrap(((SimpleconditionexpContext)_localctx).v, (((SimpleconditionexpContext)_localctx).v!=null?((SimpleconditionexpContext)_localctx).v.getText():null), ((SimpleconditionexpContext)_localctx).e.result, true);
					
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(327);
				((SimpleconditionexpContext)_localctx).v = match(VARIABLE);
				setState(328);
				match(T__17);
				setState(329);
				((SimpleconditionexpContext)_localctx).e = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionTrap(((SimpleconditionexpContext)_localctx).v, (((SimpleconditionexpContext)_localctx).v!=null?((SimpleconditionexpContext)_localctx).v.getText():null), ((SimpleconditionexpContext)_localctx).e.result, false);
					
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(332);
				((SimpleconditionexpContext)_localctx).t = match(JULIA);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionJulia(((SimpleconditionexpContext)_localctx).t);
					
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(334);
				((SimpleconditionexpContext)_localctx).s = match(T__5);
				setState(335);
				((SimpleconditionexpContext)_localctx).c = conditionexp(0);
				setState(336);
				match(T__6);

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
		public SimpleconditionexpContext simpleconditionexp() {
			return getRuleContext(SimpleconditionexpContext.class,0);
		}
		public Conditionexp2Context conditionexp2() {
			return getRuleContext(Conditionexp2Context.class,0);
		}
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
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
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_conditionexp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(348);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(342);
				((ConditionexpContext)_localctx).c = simpleconditionexp();

						((ConditionexpContext)_localctx).result =  ((ConditionexpContext)_localctx).c.result;
					
				}
				break;
			case 2:
				{
				setState(345);
				((ConditionexpContext)_localctx).c2 = conditionexp2(0);

						((ConditionexpContext)_localctx).result =  ((ConditionexpContext)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(357);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConditionexpContext(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp);
					setState(350);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(351);
					((ConditionexpContext)_localctx).l = match(T__18);
					setState(352);
					((ConditionexpContext)_localctx).c2 = conditionexp2(0);

					          		((ConditionexpContext)_localctx).result =  new ASTConditionLogicOp(((ConditionexpContext)_localctx).c1.result.getLocation(), (((ConditionexpContext)_localctx).l!=null?((ConditionexpContext)_localctx).l.getText():null), ((ConditionexpContext)_localctx).c1.result, ((ConditionexpContext)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(359);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
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
		public SimpleconditionexpContext simpleconditionexp() {
			return getRuleContext(SimpleconditionexpContext.class,0);
		}
		public Conditionexp3Context conditionexp3() {
			return getRuleContext(Conditionexp3Context.class,0);
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
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_conditionexp2, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(367);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(361);
				((Conditionexp2Context)_localctx).c = simpleconditionexp();

						((Conditionexp2Context)_localctx).result =  ((Conditionexp2Context)_localctx).c.result;
					
				}
				break;
			case 2:
				{
				setState(364);
				((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

						((Conditionexp2Context)_localctx).result =  ((Conditionexp2Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(376);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Conditionexp2Context(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp2);
					setState(369);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(370);
					((Conditionexp2Context)_localctx).l = match(T__19);
					setState(371);
					((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

					          		((Conditionexp2Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp2Context)_localctx).c1.result.getLocation(), (((Conditionexp2Context)_localctx).l!=null?((Conditionexp2Context)_localctx).l.getText():null), ((Conditionexp2Context)_localctx).c1.result, ((Conditionexp2Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(378);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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
		public SimpleconditionexpContext simpleconditionexp() {
			return getRuleContext(SimpleconditionexpContext.class,0);
		}
		public Conditionexp4Context conditionexp4() {
			return getRuleContext(Conditionexp4Context.class,0);
		}
		public Conditionexp3Context conditionexp3() {
			return getRuleContext(Conditionexp3Context.class,0);
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
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_conditionexp3, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(386);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(380);
				((Conditionexp3Context)_localctx).c = simpleconditionexp();

						((Conditionexp3Context)_localctx).result =  ((Conditionexp3Context)_localctx).c.result;
					
				}
				break;
			case 2:
				{
				setState(383);
				((Conditionexp3Context)_localctx).c2 = conditionexp4();

						((Conditionexp3Context)_localctx).result =  ((Conditionexp3Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(395);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Conditionexp3Context(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp3);
					setState(388);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(389);
					((Conditionexp3Context)_localctx).l = match(T__20);
					setState(390);
					((Conditionexp3Context)_localctx).c2 = conditionexp4();

					          		((Conditionexp3Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp3Context)_localctx).c1.result.getLocation(), (((Conditionexp3Context)_localctx).l!=null?((Conditionexp3Context)_localctx).l.getText():null), ((Conditionexp3Context)_localctx).c1.result, ((Conditionexp3Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(397);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
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
		enterRule(_localctx, 36, RULE_conditionexp4);
		try {
			setState(405);
			switch (_input.LA(1)) {
			case T__5:
			case T__11:
			case T__18:
			case T__22:
			case T__23:
			case T__25:
			case T__27:
			case T__28:
			case T__29:
			case T__30:
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
			case T__38:
			case T__39:
			case T__40:
			case T__41:
			case T__42:
			case T__43:
			case T__44:
			case T__45:
			case T__46:
			case T__47:
			case T__48:
			case T__49:
			case T__50:
			case T__51:
			case JULIA:
			case RATIONAL:
			case INTEGER:
			case VARIABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(398);
				((Conditionexp4Context)_localctx).c1 = simpleconditionexp();

						((Conditionexp4Context)_localctx).result =  ((Conditionexp4Context)_localctx).c1.result;
					
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 2);
				{
				setState(401);
				((Conditionexp4Context)_localctx).n = match(T__21);
				setState(402);
				((Conditionexp4Context)_localctx).c2 = conditionexp4();

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
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public RealContext real() {
			return getRuleContext(RealContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		enterRule(_localctx, 38, RULE_simpleexpression);
		try {
			setState(441);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(407);
				((SimpleexpressionContext)_localctx).p = constant();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).p.result;
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(410);
				((SimpleexpressionContext)_localctx).v = variable();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).v.result;
					
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(413);
				((SimpleexpressionContext)_localctx).r = real();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).r.result;
					
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(416);
				((SimpleexpressionContext)_localctx).f = function();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).f.result;
					
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(419);
				((SimpleexpressionContext)_localctx).t = match(T__5);
				setState(420);
				((SimpleexpressionContext)_localctx).e = expression(0);
				setState(421);
				match(T__6);

						((SimpleexpressionContext)_localctx).result =  new ASTParen(((SimpleexpressionContext)_localctx).t, ((SimpleexpressionContext)_localctx).e.result);
					
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(424);
				((SimpleexpressionContext)_localctx).m = match(T__18);
				setState(425);
				((SimpleexpressionContext)_localctx).e = expression(0);
				setState(426);
				match(T__18);

						((SimpleexpressionContext)_localctx).result =  new ASTFunction(((SimpleexpressionContext)_localctx).m, "mod", ((SimpleexpressionContext)_localctx).e.result);	
					
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(429);
				((SimpleexpressionContext)_localctx).a = match(T__11);
				setState(430);
				((SimpleexpressionContext)_localctx).e = expression(0);
				setState(431);
				match(T__12);

						((SimpleexpressionContext)_localctx).result =  new ASTFunction(((SimpleexpressionContext)_localctx).a, "pha", ((SimpleexpressionContext)_localctx).e.result);	
					
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(434);
				((SimpleexpressionContext)_localctx).a = match(T__11);
				setState(435);
				((SimpleexpressionContext)_localctx).er = expression(0);
				setState(436);
				match(T__3);
				setState(437);
				((SimpleexpressionContext)_localctx).ei = expression(0);
				setState(438);
				match(T__12);

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
		public Expression2Context expression2() {
			return getRuleContext(Expression2Context.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(458);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				setState(444);
				((ExpressionContext)_localctx).e2 = expression2(0);
				setState(445);
				((ExpressionContext)_localctx).s = match(T__22);
				setState(446);
				((ExpressionContext)_localctx).e1 = expression(2);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e2.result, ((ExpressionContext)_localctx).e1.result);		
					
				}
				break;
			case 2:
				{
				setState(449);
				((ExpressionContext)_localctx).e = simpleexpression();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e.result;	
					
				}
				break;
			case 3:
				{
				setState(452);
				((ExpressionContext)_localctx).c = complex();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).c.result;
					
				}
				break;
			case 4:
				{
				setState(455);
				((ExpressionContext)_localctx).e2 = expression2(0);

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e2.result;	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(472);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(470);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(460);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(461);
						((ExpressionContext)_localctx).s = match(T__22);
						setState(462);
						((ExpressionContext)_localctx).e2 = expression2(0);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(465);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(466);
						((ExpressionContext)_localctx).s = match(T__23);
						setState(467);
						((ExpressionContext)_localctx).e2 = expression2(0);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					}
					} 
				}
				setState(474);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
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
		public List<Expression2Context> expression2() {
			return getRuleContexts(Expression2Context.class);
		}
		public Expression2Context expression2(int i) {
			return getRuleContext(Expression2Context.class,i);
		}
		public SimpleexpressionContext simpleexpression() {
			return getRuleContext(SimpleexpressionContext.class,0);
		}
		public Expression3Context expression3() {
			return getRuleContext(Expression3Context.class,0);
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
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_expression2, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(497);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(476);
				((Expression2Context)_localctx).s = match(T__23);
				setState(477);
				((Expression2Context)_localctx).e2 = expression2(4);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "-", ((Expression2Context)_localctx).e2.result);
					
				}
				break;
			case 2:
				{
				setState(480);
				((Expression2Context)_localctx).s = match(T__22);
				setState(481);
				((Expression2Context)_localctx).e2 = expression2(3);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "+", ((Expression2Context)_localctx).e2.result);
					
				}
				break;
			case 3:
				{
				setState(484);
				((Expression2Context)_localctx).i = match(T__25);
				setState(486);
				_la = _input.LA(1);
				if (_la==T__24) {
					{
					setState(485);
					match(T__24);
					}
				}

				setState(488);
				((Expression2Context)_localctx).e2 = expression2(2);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
					
				}
				break;
			case 4:
				{
				setState(491);
				((Expression2Context)_localctx).e = simpleexpression();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e.result;	
					
				}
				break;
			case 5:
				{
				setState(494);
				((Expression2Context)_localctx).e3 = expression3(0);

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(512);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(510);
					switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
					case 1:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(499);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(500);
						((Expression2Context)_localctx).s = match(T__24);
						setState(501);
						((Expression2Context)_localctx).e2 = expression2(6);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "*", ((Expression2Context)_localctx).e1.result, ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					case 2:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e2 = _prevctx;
						_localctx.e2 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(504);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(506);
						_la = _input.LA(1);
						if (_la==T__24) {
							{
							setState(505);
							match(T__24);
							}
						}

						setState(508);
						((Expression2Context)_localctx).i = match(T__25);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					}
					} 
				}
				setState(514);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
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
		public SimpleexpressionContext simpleexpression() {
			return getRuleContext(SimpleexpressionContext.class,0);
		}
		public Expression4Context expression4() {
			return getRuleContext(Expression4Context.class,0);
		}
		public List<Expression3Context> expression3() {
			return getRuleContexts(Expression3Context.class);
		}
		public Expression3Context expression3(int i) {
			return getRuleContext(Expression3Context.class,i);
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
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_expression3, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(522);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(516);
				((Expression3Context)_localctx).e = simpleexpression();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e.result;	
					
				}
				break;
			case 2:
				{
				setState(519);
				((Expression3Context)_localctx).e3 = expression4(0);

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(531);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression3Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression3);
					setState(524);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(525);
					((Expression3Context)_localctx).s = match(T__26);
					setState(526);
					((Expression3Context)_localctx).e2 = expression3(2);

					          		((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).s, "/", ((Expression3Context)_localctx).e1.result, ((Expression3Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(533);
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

	public static class Expression4Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression4Context e1;
		public SimpleexpressionContext e;
		public Token s;
		public Expression4Context e2;
		public SimpleexpressionContext simpleexpression() {
			return getRuleContext(SimpleexpressionContext.class,0);
		}
		public List<Expression4Context> expression4() {
			return getRuleContexts(Expression4Context.class);
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
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_expression4, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(535);
			((Expression4Context)_localctx).e = simpleexpression();

					((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).e.result;	
				
			}
			_ctx.stop = _input.LT(-1);
			setState(545);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression4Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression4);
					setState(538);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(539);
					((Expression4Context)_localctx).s = match(T__19);
					setState(540);
					((Expression4Context)_localctx).e2 = expression4(2);

					          		((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).s, "^", ((Expression4Context)_localctx).e1.result, ((Expression4Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(547);
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

	public static class FunctionContext extends ParserRuleContext {
		public ASTFunction result;
		public Token f;
		public ExpressionContext e;
		public ExpressionContext e1;
		public ExpressionContext e2;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		enterRule(_localctx, 48, RULE_function);
		int _la;
		try {
			setState(574);
			switch (_input.LA(1)) {
			case T__27:
			case T__28:
			case T__29:
			case T__30:
			case T__31:
				enterOuterAlt(_localctx, 1);
				{
				setState(548);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(549);
				match(T__5);
				setState(550);
				((FunctionContext)_localctx).e = expression(0);
				setState(551);
				match(T__6);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), ((FunctionContext)_localctx).e.result);		
					
				}
				break;
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
				enterOuterAlt(_localctx, 2);
				{
				setState(554);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(555);
				match(T__5);
				setState(556);
				((FunctionContext)_localctx).e = expression(0);
				setState(557);
				match(T__6);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case T__38:
			case T__39:
			case T__40:
			case T__41:
			case T__42:
			case T__43:
				enterOuterAlt(_localctx, 3);
				{
				setState(560);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(561);
				match(T__5);
				setState(562);
				((FunctionContext)_localctx).e = expression(0);
				setState(563);
				match(T__6);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case T__44:
			case T__45:
			case T__46:
			case T__47:
			case T__48:
				enterOuterAlt(_localctx, 4);
				{
				setState(566);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(567);
				match(T__5);
				setState(568);
				((FunctionContext)_localctx).e1 = expression(0);
				setState(569);
				match(T__3);
				setState(570);
				((FunctionContext)_localctx).e2 = expression(0);
				setState(571);
				match(T__6);

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
		enterRule(_localctx, 50, RULE_constant);
		try {
			setState(582);
			switch (_input.LA(1)) {
			case T__49:
				enterOuterAlt(_localctx, 1);
				{
				setState(576);
				((ConstantContext)_localctx).p = match(T__49);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.E);
					
				}
				break;
			case T__50:
				enterOuterAlt(_localctx, 2);
				{
				setState(578);
				((ConstantContext)_localctx).p = match(T__50);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.PI);
					
				}
				break;
			case T__51:
				enterOuterAlt(_localctx, 3);
				{
				setState(580);
				((ConstantContext)_localctx).p = match(T__51);

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
		enterRule(_localctx, 52, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(584);
			((VariableContext)_localctx).v = match(VARIABLE);

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
		public TerminalNode RATIONAL() { return getToken(MandelbrotParser.RATIONAL, 0); }
		public TerminalNode INTEGER() { return getToken(MandelbrotParser.INTEGER, 0); }
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
		enterRule(_localctx, 54, RULE_real);
		int _la;
		try {
			setState(595);
			switch (_input.LA(1)) {
			case T__22:
			case RATIONAL:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(588);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(587);
					match(T__22);
					}
				}

				setState(590);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((RealContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}

						((RealContext)_localctx).result =  new ASTNumber(((RealContext)_localctx).r, builder.parseDouble((((RealContext)_localctx).r!=null?((RealContext)_localctx).r.getText():null)));
					
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(592);
				match(T__23);
				setState(593);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((RealContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}

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
		public List<TerminalNode> RATIONAL() { return getTokens(MandelbrotParser.RATIONAL); }
		public TerminalNode RATIONAL(int i) {
			return getToken(MandelbrotParser.RATIONAL, i);
		}
		public List<TerminalNode> INTEGER() { return getTokens(MandelbrotParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(MandelbrotParser.INTEGER, i);
		}
		public RealContext real() {
			return getRuleContext(RealContext.class,0);
		}
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
		enterRule(_localctx, 56, RULE_complex);
		int _la;
		try {
			setState(678);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(597);
				match(T__11);
				setState(599);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(598);
					match(T__22);
					}
				}

				setState(601);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(602);
				match(T__3);
				setState(604);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(603);
					match(T__22);
					}
				}

				setState(606);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(607);
				match(T__12);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(609);
				match(T__11);
				setState(611);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(610);
					match(T__22);
					}
				}

				setState(613);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(614);
				match(T__3);
				setState(615);
				match(T__23);
				setState(616);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(617);
				match(T__12);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(619);
				match(T__11);
				setState(620);
				match(T__23);
				setState(621);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(622);
				match(T__3);
				setState(624);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(623);
					match(T__22);
					}
				}

				setState(626);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(627);
				match(T__12);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(629);
				match(T__11);
				setState(630);
				match(T__23);
				setState(631);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(632);
				match(T__3);
				setState(633);
				match(T__23);
				setState(634);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(635);
				match(T__12);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(638);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(637);
					match(T__22);
					}
				}

				setState(640);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(641);
				match(T__22);
				setState(642);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(643);
				match(T__25);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(646);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(645);
					match(T__22);
					}
				}

				setState(648);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(649);
				match(T__23);
				setState(650);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(651);
				match(T__25);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(654);
				_la = _input.LA(1);
				if (_la==T__22) {
					{
					setState(653);
					match(T__22);
					}
				}

				setState(656);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(657);
				match(T__25);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble((((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(659);
				match(T__23);
				setState(660);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(661);
				match(T__22);
				setState(662);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(663);
				match(T__25);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(665);
				match(T__23);
				setState(666);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(667);
				match(T__23);
				setState(668);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(669);
				match(T__25);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(671);
				match(T__23);
				setState(672);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(673);
				match(T__25);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(675);
				((ComplexContext)_localctx).rn = real();

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
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public List<PaletteelementContext> paletteelement() {
			return getRuleContexts(PaletteelementContext.class);
		}
		public PaletteelementContext paletteelement(int i) {
			return getRuleContext(PaletteelementContext.class,i);
		}
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
		enterRule(_localctx, 58, RULE_palette);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(680);
			((PaletteContext)_localctx).p = match(PALETTE);
			setState(681);
			((PaletteContext)_localctx).v = match(VARIABLE);

					builder.addPalette(new ASTPalette(((PaletteContext)_localctx).p, (((PaletteContext)_localctx).v!=null?((PaletteContext)_localctx).v.getText():null))); 
				
			setState(683);
			match(T__0);
			setState(685); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(684);
				paletteelement();
				}
				}
				setState(687); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__2 );
			setState(689);
			match(T__1);
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
		public List<ColorargbContext> colorargb() {
			return getRuleContexts(ColorargbContext.class);
		}
		public ColorargbContext colorargb(int i) {
			return getRuleContext(ColorargbContext.class,i);
		}
		public TerminalNode INTEGER() { return getToken(MandelbrotParser.INTEGER, 0); }
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
		enterRule(_localctx, 60, RULE_paletteelement);
		try {
			setState(713);
			switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(691);
				((PaletteelementContext)_localctx).t = match(T__2);
				setState(692);
				((PaletteelementContext)_localctx).bc = colorargb();
				setState(693);
				match(T__12);
				setState(694);
				((PaletteelementContext)_localctx).ec = colorargb();
				setState(695);
				match(T__3);
				setState(696);
				((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(697);
				match(T__3);
				setState(698);
				((PaletteelementContext)_localctx).e = expression(0);
				setState(699);
				match(T__4);
				setState(700);
				match(T__7);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), ((PaletteelementContext)_localctx).e.result));
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(703);
				((PaletteelementContext)_localctx).t = match(T__2);
				setState(704);
				((PaletteelementContext)_localctx).bc = colorargb();
				setState(705);
				match(T__12);
				setState(706);
				((PaletteelementContext)_localctx).ec = colorargb();
				setState(707);
				match(T__3);
				setState(708);
				((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(709);
				match(T__4);
				setState(710);
				match(T__7);

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
		public ColorstatementContext colorstatement() {
			return getRuleContext(ColorstatementContext.class,0);
		}
		public TerminalNode INIT() { return getToken(MandelbrotParser.INIT, 0); }
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
		enterRule(_localctx, 62, RULE_colorinit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(715);
			((ColorinitContext)_localctx).i = match(INIT);

					builder.setColorContext(true);
					builder.setColorInit(new ASTColorInit(((ColorinitContext)_localctx).i));
				
			setState(717);
			match(T__0);
			setState(718);
			colorstatement();
			setState(719);
			match(T__1);
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
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
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
		enterRule(_localctx, 64, RULE_colorstatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{

					builder.pushStatementList();	
				
			setState(725);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 60)) & ~0x3f) == 0 && ((1L << (_la - 60)) & ((1L << (IF - 60)) | (1L << (STOP - 60)) | (1L << (VARIABLE - 60)))) != 0)) {
				{
				{
				setState(722);
				statement();
				}
				}
				setState(727);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}

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
		public RuleexpContext ruleexp() {
			return getRuleContext(RuleexpContext.class,0);
		}
		public ColorexpContext colorexp() {
			return getRuleContext(ColorexpContext.class,0);
		}
		public TerminalNode RATIONAL() { return getToken(MandelbrotParser.RATIONAL, 0); }
		public TerminalNode INTEGER() { return getToken(MandelbrotParser.INTEGER, 0); }
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
		enterRule(_localctx, 66, RULE_colorrule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(730);
			((ColorruleContext)_localctx).t = match(RULE);
			setState(731);
			match(T__5);
			setState(732);
			((ColorruleContext)_localctx).r = ruleexp(0);
			setState(733);
			match(T__6);
			setState(734);
			match(T__2);
			setState(735);
			((ColorruleContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==RATIONAL || _la==INTEGER) ) {
				((ColorruleContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(736);
			match(T__4);
			setState(737);
			match(T__0);
			setState(738);
			((ColorruleContext)_localctx).c = colorexp();
			setState(739);
			match(T__1);

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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<RuleexpContext> ruleexp() {
			return getRuleContexts(RuleexpContext.class);
		}
		public RuleexpContext ruleexp(int i) {
			return getRuleContext(RuleexpContext.class,i);
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
		int _startState = 68;
		enterRecursionRule(_localctx, 68, RULE_ruleexp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(743);
			((RuleexpContext)_localctx).e1 = expression(0);
			setState(744);
			((RuleexpContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15))) != 0)) ) {
				((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(745);
			((RuleexpContext)_localctx).e2 = expression(0);

					((RuleexpContext)_localctx).result =  new ASTRuleCompareOp(((RuleexpContext)_localctx).e1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).e1.result, ((RuleexpContext)_localctx).e2.result);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(755);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RuleexpContext(_parentctx, _parentState);
					_localctx.r1 = _prevctx;
					_localctx.r1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_ruleexp);
					setState(748);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(749);
					((RuleexpContext)_localctx).o = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__18) | (1L << T__19) | (1L << T__20))) != 0)) ) {
						((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(750);
					((RuleexpContext)_localctx).r2 = ruleexp(2);

					          		((RuleexpContext)_localctx).result =  new ASTRuleLogicOp(((RuleexpContext)_localctx).r1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).r1.result, ((RuleexpContext)_localctx).r2.result);
					          	
					}
					} 
				}
				setState(757);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
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
		enterRule(_localctx, 70, RULE_colorexp);
		try {
			setState(783);
			switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(758);
				((ColorexpContext)_localctx).e1 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result);
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(761);
				((ColorexpContext)_localctx).e1 = expression(0);
				setState(762);
				match(T__3);
				setState(763);
				((ColorexpContext)_localctx).e2 = expression(0);
				setState(764);
				match(T__3);
				setState(765);
				((ColorexpContext)_localctx).e3 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result);
					
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(768);
				((ColorexpContext)_localctx).e1 = expression(0);
				setState(769);
				match(T__3);
				setState(770);
				((ColorexpContext)_localctx).e2 = expression(0);
				setState(771);
				match(T__3);
				setState(772);
				((ColorexpContext)_localctx).e3 = expression(0);
				setState(773);
				match(T__3);
				setState(774);
				((ColorexpContext)_localctx).e4 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result, ((ColorexpContext)_localctx).e4.result);
					
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(777);
				((ColorexpContext)_localctx).v = match(VARIABLE);
				setState(778);
				match(T__2);
				setState(779);
				((ColorexpContext)_localctx).e = expression(0);
				setState(780);
				match(T__4);

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
		public Token argb32;
		public Token argb24;
		public List<TerminalNode> RATIONAL() { return getTokens(MandelbrotParser.RATIONAL); }
		public TerminalNode RATIONAL(int i) {
			return getToken(MandelbrotParser.RATIONAL, i);
		}
		public List<TerminalNode> INTEGER() { return getTokens(MandelbrotParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(MandelbrotParser.INTEGER, i);
		}
		public TerminalNode ARGB32() { return getToken(MandelbrotParser.ARGB32, 0); }
		public TerminalNode ARGB24() { return getToken(MandelbrotParser.ARGB24, 0); }
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
		enterRule(_localctx, 72, RULE_colorargb);
		int _la;
		try {
			setState(799);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(785);
				match(T__5);
				setState(786);
				((ColorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(787);
				match(T__3);
				setState(788);
				((ColorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(789);
				match(T__3);
				setState(790);
				((ColorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(791);
				match(T__3);
				setState(792);
				((ColorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(793);
				match(T__6);

						((ColorargbContext)_localctx).result =  new ASTColorARGB(builder.parseFloat((((ColorargbContext)_localctx).a!=null?((ColorargbContext)_localctx).a.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).r!=null?((ColorargbContext)_localctx).r.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).g!=null?((ColorargbContext)_localctx).g.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).b!=null?((ColorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case ARGB32:
				enterOuterAlt(_localctx, 2);
				{
				setState(795);
				((ColorargbContext)_localctx).argb32 = match(ARGB32);

						((ColorargbContext)_localctx).result =  new ASTColorARGB((int)(0xFFFFFFFF & builder.parseLong((((ColorargbContext)_localctx).argb32!=null?((ColorargbContext)_localctx).argb32.getText():null).substring(1), 16)));
					
				}
				break;
			case ARGB24:
				enterOuterAlt(_localctx, 3);
				{
				setState(797);
				((ColorargbContext)_localctx).argb24 = match(ARGB24);

						((ColorargbContext)_localctx).result =  new ASTColorARGB((int)(0xFF000000 | (0xFFFFFFFF & builder.parseLong((((ColorargbContext)_localctx).argb24!=null?((ColorargbContext)_localctx).argb24.getText():null).substring(1), 16))));
					
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

	public static class BackgroundcolorargbContext extends ParserRuleContext {
		public ASTColorARGB result;
		public Token a;
		public Token r;
		public Token g;
		public Token b;
		public Token argb32;
		public Token argb24;
		public List<TerminalNode> RATIONAL() { return getTokens(MandelbrotParser.RATIONAL); }
		public TerminalNode RATIONAL(int i) {
			return getToken(MandelbrotParser.RATIONAL, i);
		}
		public List<TerminalNode> INTEGER() { return getTokens(MandelbrotParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(MandelbrotParser.INTEGER, i);
		}
		public TerminalNode ARGB32() { return getToken(MandelbrotParser.ARGB32, 0); }
		public TerminalNode ARGB24() { return getToken(MandelbrotParser.ARGB24, 0); }
		public BackgroundcolorargbContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_backgroundcolorargb; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterBackgroundcolorargb(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitBackgroundcolorargb(this);
		}
	}

	public final BackgroundcolorargbContext backgroundcolorargb() throws RecognitionException {
		BackgroundcolorargbContext _localctx = new BackgroundcolorargbContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_backgroundcolorargb);
		int _la;
		try {
			setState(815);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(801);
				match(T__5);
				setState(802);
				((BackgroundcolorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((BackgroundcolorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(803);
				match(T__3);
				setState(804);
				((BackgroundcolorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((BackgroundcolorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(805);
				match(T__3);
				setState(806);
				((BackgroundcolorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((BackgroundcolorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(807);
				match(T__3);
				setState(808);
				((BackgroundcolorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((BackgroundcolorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(809);
				match(T__6);

						((BackgroundcolorargbContext)_localctx).result =  new ASTColorARGB(1f, builder.parseFloat((((BackgroundcolorargbContext)_localctx).r!=null?((BackgroundcolorargbContext)_localctx).r.getText():null)), builder.parseFloat((((BackgroundcolorargbContext)_localctx).g!=null?((BackgroundcolorargbContext)_localctx).g.getText():null)), builder.parseFloat((((BackgroundcolorargbContext)_localctx).b!=null?((BackgroundcolorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case ARGB32:
				enterOuterAlt(_localctx, 2);
				{
				setState(811);
				((BackgroundcolorargbContext)_localctx).argb32 = match(ARGB32);

						((BackgroundcolorargbContext)_localctx).result =  new ASTColorARGB((int)(0xFF000000 | (0xFFFFFFFF & builder.parseLong((((BackgroundcolorargbContext)_localctx).argb32!=null?((BackgroundcolorargbContext)_localctx).argb32.getText():null).substring(1), 16))));
					
				}
				break;
			case ARGB24:
				enterOuterAlt(_localctx, 3);
				{
				setState(813);
				((BackgroundcolorargbContext)_localctx).argb24 = match(ARGB24);

						((BackgroundcolorargbContext)_localctx).result =  new ASTColorARGB((int)(0xFF000000 | (0xFFFFFFFF & builder.parseLong((((BackgroundcolorargbContext)_localctx).argb24!=null?((BackgroundcolorargbContext)_localctx).argb24.getText():null).substring(1), 16))));
					
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
		enterRule(_localctx, 76, RULE_eof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(817);
			match(EOF);
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
		case 13:
			return statevariablelist_sempred((StatevariablelistContext)_localctx, predIndex);
		case 15:
			return conditionexp_sempred((ConditionexpContext)_localctx, predIndex);
		case 16:
			return conditionexp2_sempred((Conditionexp2Context)_localctx, predIndex);
		case 17:
			return conditionexp3_sempred((Conditionexp3Context)_localctx, predIndex);
		case 20:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 21:
			return expression2_sempred((Expression2Context)_localctx, predIndex);
		case 22:
			return expression3_sempred((Expression3Context)_localctx, predIndex);
		case 23:
			return expression4_sempred((Expression4Context)_localctx, predIndex);
		case 34:
			return ruleexp_sempred((RuleexpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean statevariablelist_sempred(StatevariablelistContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean conditionexp_sempred(ConditionexpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean conditionexp2_sempred(Conditionexp2Context _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean conditionexp3_sempred(Conditionexp3Context _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 3);
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression2_sempred(Expression2Context _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 5);
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression3_sempred(Expression3Context _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression4_sempred(Expression4Context _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean ruleexp_sempred(RuleexpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3N\u0336\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3e\n\3"+
		"\f\3\16\3h\13\3\3\3\5\3k\n\3\3\3\3\3\5\3o\n\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\7\4z\n\4\f\4\16\4}\13\4\3\4\5\4\u0080\n\4\3\4\7\4\u0083\n"+
		"\4\f\4\16\4\u0086\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00ac\n\b\f\b\16\b\u00af\13\b\3\b\3\b\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00ce\n\t\3\n\3\n\7\n\u00d2\n"+
		"\n\f\n\16\n\u00d5\13\n\3\n\3\n\3\13\3\13\7\13\u00db\n\13\f\13\16\13\u00de"+
		"\13\13\3\13\3\13\3\f\3\f\7\f\u00e4\n\f\f\f\16\f\u00e7\13\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\5\r\u00ef\n\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00fa"+
		"\n\r\f\r\16\r\u00fd\13\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0105\n\r\f\r\16"+
		"\r\u0108\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0114\n\r\f"+
		"\r\16\r\u0117\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\5\r\u0126\n\r\3\r\5\r\u0129\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\5\16\u0133\n\16\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u013b\n\17\f\17"+
		"\16\17\u013e\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0156"+
		"\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u015f\n\21\3\21\3\21\3\21"+
		"\3\21\3\21\7\21\u0166\n\21\f\21\16\21\u0169\13\21\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\5\22\u0172\n\22\3\22\3\22\3\22\3\22\3\22\7\22\u0179\n"+
		"\22\f\22\16\22\u017c\13\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0185"+
		"\n\23\3\23\3\23\3\23\3\23\3\23\7\23\u018c\n\23\f\23\16\23\u018f\13\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0198\n\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\5\25\u01bc\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u01cd\n\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\7\26\u01d9\n\26\f\26\16\26\u01dc\13\26\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u01e9\n\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u01f4\n\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\5\27\u01fd\n\27\3\27\3\27\7\27\u0201\n\27\f\27\16"+
		"\27\u0204\13\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u020d\n\30\3\30"+
		"\3\30\3\30\3\30\3\30\7\30\u0214\n\30\f\30\16\30\u0217\13\30\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u0222\n\31\f\31\16\31\u0225\13"+
		"\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0241"+
		"\n\32\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0249\n\33\3\34\3\34\3\34\3\35"+
		"\5\35\u024f\n\35\3\35\3\35\3\35\3\35\3\35\5\35\u0256\n\35\3\36\3\36\5"+
		"\36\u025a\n\36\3\36\3\36\3\36\5\36\u025f\n\36\3\36\3\36\3\36\3\36\3\36"+
		"\5\36\u0266\n\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\5\36\u0273\n\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\5\36\u0281\n\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u0289\n\36\3"+
		"\36\3\36\3\36\3\36\3\36\3\36\5\36\u0291\n\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\5\36\u02a9\n\36\3\37\3\37\3\37\3\37\3\37\6\37\u02b0\n"+
		"\37\r\37\16\37\u02b1\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 "+
		"\3 \3 \3 \3 \3 \3 \3 \3 \3 \5 \u02cc\n \3!\3!\3!\3!\3!\3!\3\"\3\"\7\""+
		"\u02d6\n\"\f\"\16\"\u02d9\13\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3"+
		"#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\7$\u02f4\n$\f$\16$\u02f7\13$\3%"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%"+
		"\3%\5%\u0312\n%\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\5&\u0322\n&"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u0332\n\'"+
		"\3(\3(\3(\2\13\34 \"$*,.\60F)\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 "+
		"\"$&(*,.\60\62\64\668:<>@BDFHJLN\2\t\4\2\13\13\16\22\3\2\36\"\3\2#(\3"+
		"\2).\3\2/\63\3\2GH\3\2\25\27\u036b\2P\3\2\2\2\4X\3\2\2\2\6r\3\2\2\2\b"+
		"\u0089\3\2\2\2\n\u008f\3\2\2\2\f\u009d\3\2\2\2\16\u00a3\3\2\2\2\20\u00cd"+
		"\3\2\2\2\22\u00cf\3\2\2\2\24\u00d8\3\2\2\2\26\u00e1\3\2\2\2\30\u0128\3"+
		"\2\2\2\32\u0132\3\2\2\2\34\u0134\3\2\2\2\36\u0155\3\2\2\2 \u015e\3\2\2"+
		"\2\"\u0171\3\2\2\2$\u0184\3\2\2\2&\u0197\3\2\2\2(\u01bb\3\2\2\2*\u01cc"+
		"\3\2\2\2,\u01f3\3\2\2\2.\u020c\3\2\2\2\60\u0218\3\2\2\2\62\u0240\3\2\2"+
		"\2\64\u0248\3\2\2\2\66\u024a\3\2\2\28\u0255\3\2\2\2:\u02a8\3\2\2\2<\u02aa"+
		"\3\2\2\2>\u02cb\3\2\2\2@\u02cd\3\2\2\2B\u02d3\3\2\2\2D\u02dc\3\2\2\2F"+
		"\u02e8\3\2\2\2H\u0311\3\2\2\2J\u0321\3\2\2\2L\u0331\3\2\2\2N\u0333\3\2"+
		"\2\2PQ\7\67\2\2QR\b\2\1\2RS\7\3\2\2ST\5\4\3\2TU\5\6\4\2UV\7\4\2\2VW\5"+
		"N(\2W\3\3\2\2\2XY\78\2\2YZ\7\5\2\2Z[\5:\36\2[\\\7\6\2\2\\]\5:\36\2]^\7"+
		"\7\2\2^_\b\3\1\2_`\7\5\2\2`a\5\34\17\2ab\7\7\2\2bf\7\3\2\2ce\5\16\b\2"+
		"dc\3\2\2\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2gj\3\2\2\2hf\3\2\2\2ik\5\b\5\2"+
		"ji\3\2\2\2jk\3\2\2\2kl\3\2\2\2ln\5\n\6\2mo\5\f\7\2nm\3\2\2\2no\3\2\2\2"+
		"op\3\2\2\2pq\7\4\2\2q\5\3\2\2\2rs\7B\2\2st\7\5\2\2tu\5L\'\2uv\7\7\2\2"+
		"vw\b\4\1\2w{\7\3\2\2xz\5<\37\2yx\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2"+
		"|\177\3\2\2\2}{\3\2\2\2~\u0080\5@!\2\177~\3\2\2\2\177\u0080\3\2\2\2\u0080"+
		"\u0084\3\2\2\2\u0081\u0083\5D#\2\u0082\u0081\3\2\2\2\u0083\u0086\3\2\2"+
		"\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0087\3\2\2\2\u0086\u0084"+
		"\3\2\2\2\u0087\u0088\7\4\2\2\u0088\7\3\2\2\2\u0089\u008a\7:\2\2\u008a"+
		"\u008b\b\5\1\2\u008b\u008c\7\3\2\2\u008c\u008d\5\22\n\2\u008d\u008e\7"+
		"\4\2\2\u008e\t\3\2\2\2\u008f\u0090\7;\2\2\u0090\u0091\7\5\2\2\u0091\u0092"+
		"\7H\2\2\u0092\u0093\7\6\2\2\u0093\u0094\7H\2\2\u0094\u0095\7\7\2\2\u0095"+
		"\u0096\7\b\2\2\u0096\u0097\5 \21\2\u0097\u0098\7\t\2\2\u0098\u0099\b\6"+
		"\1\2\u0099\u009a\7\3\2\2\u009a\u009b\5\24\13\2\u009b\u009c\7\4\2\2\u009c"+
		"\13\3\2\2\2\u009d\u009e\7<\2\2\u009e\u009f\b\7\1\2\u009f\u00a0\7\3\2\2"+
		"\u00a0\u00a1\5\26\f\2\u00a1\u00a2\7\4\2\2\u00a2\r\3\2\2\2\u00a3\u00a4"+
		"\79\2\2\u00a4\u00a5\7L\2\2\u00a5\u00a6\7\5\2\2\u00a6\u00a7\5:\36\2\u00a7"+
		"\u00a8\7\7\2\2\u00a8\u00a9\b\b\1\2\u00a9\u00ad\7\3\2\2\u00aa\u00ac\5\20"+
		"\t\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad"+
		"\u00ae\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b1\7\4"+
		"\2\2\u00b1\17\3\2\2\2\u00b2\u00b3\7I\2\2\u00b3\u00b4\7\b\2\2\u00b4\u00b5"+
		"\5:\36\2\u00b5\u00b6\7\t\2\2\u00b6\u00b7\7\n\2\2\u00b7\u00b8\b\t\1\2\u00b8"+
		"\u00ce\3\2\2\2\u00b9\u00ba\7J\2\2\u00ba\u00bb\7\b\2\2\u00bb\u00bc\5:\36"+
		"\2\u00bc\u00bd\7\6\2\2\u00bd\u00be\5:\36\2\u00be\u00bf\7\t\2\2\u00bf\u00c0"+
		"\7\n\2\2\u00c0\u00c1\b\t\1\2\u00c1\u00ce\3\2\2\2\u00c2\u00c3\7K\2\2\u00c3"+
		"\u00c4\7\b\2\2\u00c4\u00c5\5:\36\2\u00c5\u00c6\7\6\2\2\u00c6\u00c7\5:"+
		"\36\2\u00c7\u00c8\7\6\2\2\u00c8\u00c9\5:\36\2\u00c9\u00ca\7\t\2\2\u00ca"+
		"\u00cb\7\n\2\2\u00cb\u00cc\b\t\1\2\u00cc\u00ce\3\2\2\2\u00cd\u00b2\3\2"+
		"\2\2\u00cd\u00b9\3\2\2\2\u00cd\u00c2\3\2\2\2\u00ce\21\3\2\2\2\u00cf\u00d3"+
		"\b\n\1\2\u00d0\u00d2\5\30\r\2\u00d1\u00d0\3\2\2\2\u00d2\u00d5\3\2\2\2"+
		"\u00d3\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d6\3\2\2\2\u00d5\u00d3"+
		"\3\2\2\2\u00d6\u00d7\b\n\1\2\u00d7\23\3\2\2\2\u00d8\u00dc\b\13\1\2\u00d9"+
		"\u00db\5\30\r\2\u00da\u00d9\3\2\2\2\u00db\u00de\3\2\2\2\u00dc\u00da\3"+
		"\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00df\3\2\2\2\u00de\u00dc\3\2\2\2\u00df"+
		"\u00e0\b\13\1\2\u00e0\25\3\2\2\2\u00e1\u00e5\b\f\1\2\u00e2\u00e4\5\30"+
		"\r\2\u00e3\u00e2\3\2\2\2\u00e4\u00e7\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e5"+
		"\u00e6\3\2\2\2\u00e6\u00e8\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e8\u00e9\b\f"+
		"\1\2\u00e9\27\3\2\2\2\u00ea\u00eb\7L\2\2\u00eb\u00ec\7\13\2\2\u00ec\u00ee"+
		"\5*\26\2\u00ed\u00ef\7\n\2\2\u00ee\u00ed\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef"+
		"\u00f0\3\2\2\2\u00f0\u00f1\b\r\1\2\u00f1\u0129\3\2\2\2\u00f2\u00f3\7>"+
		"\2\2\u00f3\u00f4\7\b\2\2\u00f4\u00f5\5 \21\2\u00f5\u00f6\7\t\2\2\u00f6"+
		"\u00f7\7\3\2\2\u00f7\u00fb\b\r\1\2\u00f8\u00fa\5\30\r\2\u00f9\u00f8\3"+
		"\2\2\2\u00fa\u00fd\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc"+
		"\u00fe\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe\u00ff\7\4\2\2\u00ff\u0100\b\r"+
		"\1\2\u0100\u0101\7?\2\2\u0101\u0102\7\3\2\2\u0102\u0106\b\r\1\2\u0103"+
		"\u0105\5\30\r\2\u0104\u0103\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3"+
		"\2\2\2\u0106\u0107\3\2\2\2\u0107\u0109\3\2\2\2\u0108\u0106\3\2\2\2\u0109"+
		"\u010a\7\4\2\2\u010a\u010b\b\r\1\2\u010b\u0129\3\2\2\2\u010c\u010d\7>"+
		"\2\2\u010d\u010e\7\b\2\2\u010e\u010f\5 \21\2\u010f\u0110\7\t\2\2\u0110"+
		"\u0111\7\3\2\2\u0111\u0115\b\r\1\2\u0112\u0114\5\30\r\2\u0113\u0112\3"+
		"\2\2\2\u0114\u0117\3\2\2\2\u0115\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116"+
		"\u0118\3\2\2\2\u0117\u0115\3\2\2\2\u0118\u0119\7\4\2\2\u0119\u011a\b\r"+
		"\1\2\u011a\u0129\3\2\2\2\u011b\u011c\7>\2\2\u011c\u011d\7\b\2\2\u011d"+
		"\u011e\5 \21\2\u011e\u011f\7\t\2\2\u011f\u0120\b\r\1\2\u0120\u0121\5\30"+
		"\r\2\u0121\u0122\b\r\1\2\u0122\u0129\3\2\2\2\u0123\u0125\7@\2\2\u0124"+
		"\u0126\7\n\2\2\u0125\u0124\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0127\3\2"+
		"\2\2\u0127\u0129\b\r\1\2\u0128\u00ea\3\2\2\2\u0128\u00f2\3\2\2\2\u0128"+
		"\u010c\3\2\2\2\u0128\u011b\3\2\2\2\u0128\u0123\3\2\2\2\u0129\31\3\2\2"+
		"\2\u012a\u012b\7\f\2\2\u012b\u012c\7L\2\2\u012c\u0133\b\16\1\2\u012d\u012e"+
		"\7\r\2\2\u012e\u012f\7L\2\2\u012f\u0133\b\16\1\2\u0130\u0131\7L\2\2\u0131"+
		"\u0133\b\16\1\2\u0132\u012a\3\2\2\2\u0132\u012d\3\2\2\2\u0132\u0130\3"+
		"\2\2\2\u0133\33\3\2\2\2\u0134\u0135\b\17\1\2\u0135\u0136\5\32\16\2\u0136"+
		"\u013c\3\2\2\2\u0137\u0138\f\3\2\2\u0138\u0139\7\6\2\2\u0139\u013b\5\32"+
		"\16\2\u013a\u0137\3\2\2\2\u013b\u013e\3\2\2\2\u013c\u013a\3\2\2\2\u013c"+
		"\u013d\3\2\2\2\u013d\35\3\2\2\2\u013e\u013c\3\2\2\2\u013f\u0140\5*\26"+
		"\2\u0140\u0141\t\2\2\2\u0141\u0142\5*\26\2\u0142\u0143\b\20\1\2\u0143"+
		"\u0156\3\2\2\2\u0144\u0145\7L\2\2\u0145\u0146\7\23\2\2\u0146\u0147\5*"+
		"\26\2\u0147\u0148\b\20\1\2\u0148\u0156\3\2\2\2\u0149\u014a\7L\2\2\u014a"+
		"\u014b\7\24\2\2\u014b\u014c\5*\26\2\u014c\u014d\b\20\1\2\u014d\u0156\3"+
		"\2\2\2\u014e\u014f\7A\2\2\u014f\u0156\b\20\1\2\u0150\u0151\7\b\2\2\u0151"+
		"\u0152\5 \21\2\u0152\u0153\7\t\2\2\u0153\u0154\b\20\1\2\u0154\u0156\3"+
		"\2\2\2\u0155\u013f\3\2\2\2\u0155\u0144\3\2\2\2\u0155\u0149\3\2\2\2\u0155"+
		"\u014e\3\2\2\2\u0155\u0150\3\2\2\2\u0156\37\3\2\2\2\u0157\u0158\b\21\1"+
		"\2\u0158\u0159\5\36\20\2\u0159\u015a\b\21\1\2\u015a\u015f\3\2\2\2\u015b"+
		"\u015c\5\"\22\2\u015c\u015d\b\21\1\2\u015d\u015f\3\2\2\2\u015e\u0157\3"+
		"\2\2\2\u015e\u015b\3\2\2\2\u015f\u0167\3\2\2\2\u0160\u0161\f\3\2\2\u0161"+
		"\u0162\7\25\2\2\u0162\u0163\5\"\22\2\u0163\u0164\b\21\1\2\u0164\u0166"+
		"\3\2\2\2\u0165\u0160\3\2\2\2\u0166\u0169\3\2\2\2\u0167\u0165\3\2\2\2\u0167"+
		"\u0168\3\2\2\2\u0168!\3\2\2\2\u0169\u0167\3\2\2\2\u016a\u016b\b\22\1\2"+
		"\u016b\u016c\5\36\20\2\u016c\u016d\b\22\1\2\u016d\u0172\3\2\2\2\u016e"+
		"\u016f\5$\23\2\u016f\u0170\b\22\1\2\u0170\u0172\3\2\2\2\u0171\u016a\3"+
		"\2\2\2\u0171\u016e\3\2\2\2\u0172\u017a\3\2\2\2\u0173\u0174\f\3\2\2\u0174"+
		"\u0175\7\26\2\2\u0175\u0176\5$\23\2\u0176\u0177\b\22\1\2\u0177\u0179\3"+
		"\2\2\2\u0178\u0173\3\2\2\2\u0179\u017c\3\2\2\2\u017a\u0178\3\2\2\2\u017a"+
		"\u017b\3\2\2\2\u017b#\3\2\2\2\u017c\u017a\3\2\2\2\u017d\u017e\b\23\1\2"+
		"\u017e\u017f\5\36\20\2\u017f\u0180\b\23\1\2\u0180\u0185\3\2\2\2\u0181"+
		"\u0182\5&\24\2\u0182\u0183\b\23\1\2\u0183\u0185\3\2\2\2\u0184\u017d\3"+
		"\2\2\2\u0184\u0181\3\2\2\2\u0185\u018d\3\2\2\2\u0186\u0187\f\3\2\2\u0187"+
		"\u0188\7\27\2\2\u0188\u0189\5&\24\2\u0189\u018a\b\23\1\2\u018a\u018c\3"+
		"\2\2\2\u018b\u0186\3\2\2\2\u018c\u018f\3\2\2\2\u018d\u018b\3\2\2\2\u018d"+
		"\u018e\3\2\2\2\u018e%\3\2\2\2\u018f\u018d\3\2\2\2\u0190\u0191\5\36\20"+
		"\2\u0191\u0192\b\24\1\2\u0192\u0198\3\2\2\2\u0193\u0194\7\30\2\2\u0194"+
		"\u0195\5&\24\2\u0195\u0196\b\24\1\2\u0196\u0198\3\2\2\2\u0197\u0190\3"+
		"\2\2\2\u0197\u0193\3\2\2\2\u0198\'\3\2\2\2\u0199\u019a\5\64\33\2\u019a"+
		"\u019b\b\25\1\2\u019b\u01bc\3\2\2\2\u019c\u019d\5\66\34\2\u019d\u019e"+
		"\b\25\1\2\u019e\u01bc\3\2\2\2\u019f\u01a0\58\35\2\u01a0\u01a1\b\25\1\2"+
		"\u01a1\u01bc\3\2\2\2\u01a2\u01a3\5\62\32\2\u01a3\u01a4\b\25\1\2\u01a4"+
		"\u01bc\3\2\2\2\u01a5\u01a6\7\b\2\2\u01a6\u01a7\5*\26\2\u01a7\u01a8\7\t"+
		"\2\2\u01a8\u01a9\b\25\1\2\u01a9\u01bc\3\2\2\2\u01aa\u01ab\7\25\2\2\u01ab"+
		"\u01ac\5*\26\2\u01ac\u01ad\7\25\2\2\u01ad\u01ae\b\25\1\2\u01ae\u01bc\3"+
		"\2\2\2\u01af\u01b0\7\16\2\2\u01b0\u01b1\5*\26\2\u01b1\u01b2\7\17\2\2\u01b2"+
		"\u01b3\b\25\1\2\u01b3\u01bc\3\2\2\2\u01b4\u01b5\7\16\2\2\u01b5\u01b6\5"+
		"*\26\2\u01b6\u01b7\7\6\2\2\u01b7\u01b8\5*\26\2\u01b8\u01b9\7\17\2\2\u01b9"+
		"\u01ba\b\25\1\2\u01ba\u01bc\3\2\2\2\u01bb\u0199\3\2\2\2\u01bb\u019c\3"+
		"\2\2\2\u01bb\u019f\3\2\2\2\u01bb\u01a2\3\2\2\2\u01bb\u01a5\3\2\2\2\u01bb"+
		"\u01aa\3\2\2\2\u01bb\u01af\3\2\2\2\u01bb\u01b4\3\2\2\2\u01bc)\3\2\2\2"+
		"\u01bd\u01be\b\26\1\2\u01be\u01bf\5,\27\2\u01bf\u01c0\7\31\2\2\u01c0\u01c1"+
		"\5*\26\4\u01c1\u01c2\b\26\1\2\u01c2\u01cd\3\2\2\2\u01c3\u01c4\5(\25\2"+
		"\u01c4\u01c5\b\26\1\2\u01c5\u01cd\3\2\2\2\u01c6\u01c7\5:\36\2\u01c7\u01c8"+
		"\b\26\1\2\u01c8\u01cd\3\2\2\2\u01c9\u01ca\5,\27\2\u01ca\u01cb\b\26\1\2"+
		"\u01cb\u01cd\3\2\2\2\u01cc\u01bd\3\2\2\2\u01cc\u01c3\3\2\2\2\u01cc\u01c6"+
		"\3\2\2\2\u01cc\u01c9\3\2\2\2\u01cd\u01da\3\2\2\2\u01ce\u01cf\f\5\2\2\u01cf"+
		"\u01d0\7\31\2\2\u01d0\u01d1\5,\27\2\u01d1\u01d2\b\26\1\2\u01d2\u01d9\3"+
		"\2\2\2\u01d3\u01d4\f\3\2\2\u01d4\u01d5\7\32\2\2\u01d5\u01d6\5,\27\2\u01d6"+
		"\u01d7\b\26\1\2\u01d7\u01d9\3\2\2\2\u01d8\u01ce\3\2\2\2\u01d8\u01d3\3"+
		"\2\2\2\u01d9\u01dc\3\2\2\2\u01da\u01d8\3\2\2\2\u01da\u01db\3\2\2\2\u01db"+
		"+\3\2\2\2\u01dc\u01da\3\2\2\2\u01dd\u01de\b\27\1\2\u01de\u01df\7\32\2"+
		"\2\u01df\u01e0\5,\27\6\u01e0\u01e1\b\27\1\2\u01e1\u01f4\3\2\2\2\u01e2"+
		"\u01e3\7\31\2\2\u01e3\u01e4\5,\27\5\u01e4\u01e5\b\27\1\2\u01e5\u01f4\3"+
		"\2\2\2\u01e6\u01e8\7\34\2\2\u01e7\u01e9\7\33\2\2\u01e8\u01e7\3\2\2\2\u01e8"+
		"\u01e9\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01eb\5,\27\4\u01eb\u01ec\b\27"+
		"\1\2\u01ec\u01f4\3\2\2\2\u01ed\u01ee\5(\25\2\u01ee\u01ef\b\27\1\2\u01ef"+
		"\u01f4\3\2\2\2\u01f0\u01f1\5.\30\2\u01f1\u01f2\b\27\1\2\u01f2\u01f4\3"+
		"\2\2\2\u01f3\u01dd\3\2\2\2\u01f3\u01e2\3\2\2\2\u01f3\u01e6\3\2\2\2\u01f3"+
		"\u01ed\3\2\2\2\u01f3\u01f0\3\2\2\2\u01f4\u0202\3\2\2\2\u01f5\u01f6\f\7"+
		"\2\2\u01f6\u01f7\7\33\2\2\u01f7\u01f8\5,\27\b\u01f8\u01f9\b\27\1\2\u01f9"+
		"\u0201\3\2\2\2\u01fa\u01fc\f\3\2\2\u01fb\u01fd\7\33\2\2\u01fc\u01fb\3"+
		"\2\2\2\u01fc\u01fd\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe\u01ff\7\34\2\2\u01ff"+
		"\u0201\b\27\1\2\u0200\u01f5\3\2\2\2\u0200\u01fa\3\2\2\2\u0201\u0204\3"+
		"\2\2\2\u0202\u0200\3\2\2\2\u0202\u0203\3\2\2\2\u0203-\3\2\2\2\u0204\u0202"+
		"\3\2\2\2\u0205\u0206\b\30\1\2\u0206\u0207\5(\25\2\u0207\u0208\b\30\1\2"+
		"\u0208\u020d\3\2\2\2\u0209\u020a\5\60\31\2\u020a\u020b\b\30\1\2\u020b"+
		"\u020d\3\2\2\2\u020c\u0205\3\2\2\2\u020c\u0209\3\2\2\2\u020d\u0215\3\2"+
		"\2\2\u020e\u020f\f\3\2\2\u020f\u0210\7\35\2\2\u0210\u0211\5.\30\4\u0211"+
		"\u0212\b\30\1\2\u0212\u0214\3\2\2\2\u0213\u020e\3\2\2\2\u0214\u0217\3"+
		"\2\2\2\u0215\u0213\3\2\2\2\u0215\u0216\3\2\2\2\u0216/\3\2\2\2\u0217\u0215"+
		"\3\2\2\2\u0218\u0219\b\31\1\2\u0219\u021a\5(\25\2\u021a\u021b\b\31\1\2"+
		"\u021b\u0223\3\2\2\2\u021c\u021d\f\3\2\2\u021d\u021e\7\26\2\2\u021e\u021f"+
		"\5\60\31\4\u021f\u0220\b\31\1\2\u0220\u0222\3\2\2\2\u0221\u021c\3\2\2"+
		"\2\u0222\u0225\3\2\2\2\u0223\u0221\3\2\2\2\u0223\u0224\3\2\2\2\u0224\61"+
		"\3\2\2\2\u0225\u0223\3\2\2\2\u0226\u0227\t\3\2\2\u0227\u0228\7\b\2\2\u0228"+
		"\u0229\5*\26\2\u0229\u022a\7\t\2\2\u022a\u022b\b\32\1\2\u022b\u0241\3"+
		"\2\2\2\u022c\u022d\t\4\2\2\u022d\u022e\7\b\2\2\u022e\u022f\5*\26\2\u022f"+
		"\u0230\7\t\2\2\u0230\u0231\b\32\1\2\u0231\u0241\3\2\2\2\u0232\u0233\t"+
		"\5\2\2\u0233\u0234\7\b\2\2\u0234\u0235\5*\26\2\u0235\u0236\7\t\2\2\u0236"+
		"\u0237\b\32\1\2\u0237\u0241\3\2\2\2\u0238\u0239\t\6\2\2\u0239\u023a\7"+
		"\b\2\2\u023a\u023b\5*\26\2\u023b\u023c\7\6\2\2\u023c\u023d\5*\26\2\u023d"+
		"\u023e\7\t\2\2\u023e\u023f\b\32\1\2\u023f\u0241\3\2\2\2\u0240\u0226\3"+
		"\2\2\2\u0240\u022c\3\2\2\2\u0240\u0232\3\2\2\2\u0240\u0238\3\2\2\2\u0241"+
		"\63\3\2\2\2\u0242\u0243\7\64\2\2\u0243\u0249\b\33\1\2\u0244\u0245\7\65"+
		"\2\2\u0245\u0249\b\33\1\2\u0246\u0247\7\66\2\2\u0247\u0249\b\33\1\2\u0248"+
		"\u0242\3\2\2\2\u0248\u0244\3\2\2\2\u0248\u0246\3\2\2\2\u0249\65\3\2\2"+
		"\2\u024a\u024b\7L\2\2\u024b\u024c\b\34\1\2\u024c\67\3\2\2\2\u024d\u024f"+
		"\7\31\2\2\u024e\u024d\3\2\2\2\u024e\u024f\3\2\2\2\u024f\u0250\3\2\2\2"+
		"\u0250\u0251\t\7\2\2\u0251\u0256\b\35\1\2\u0252\u0253\7\32\2\2\u0253\u0254"+
		"\t\7\2\2\u0254\u0256\b\35\1\2\u0255\u024e\3\2\2\2\u0255\u0252\3\2\2\2"+
		"\u02569\3\2\2\2\u0257\u0259\7\16\2\2\u0258\u025a\7\31\2\2\u0259\u0258"+
		"\3\2\2\2\u0259\u025a\3\2\2\2\u025a\u025b\3\2\2\2\u025b\u025c\t\7\2\2\u025c"+
		"\u025e\7\6\2\2\u025d\u025f\7\31\2\2\u025e\u025d\3\2\2\2\u025e\u025f\3"+
		"\2\2\2\u025f\u0260\3\2\2\2\u0260\u0261\t\7\2\2\u0261\u0262\7\17\2\2\u0262"+
		"\u02a9\b\36\1\2\u0263\u0265\7\16\2\2\u0264\u0266\7\31\2\2\u0265\u0264"+
		"\3\2\2\2\u0265\u0266\3\2\2\2\u0266\u0267\3\2\2\2\u0267\u0268\t\7\2\2\u0268"+
		"\u0269\7\6\2\2\u0269\u026a\7\32\2\2\u026a\u026b\t\7\2\2\u026b\u026c\7"+
		"\17\2\2\u026c\u02a9\b\36\1\2\u026d\u026e\7\16\2\2\u026e\u026f\7\32\2\2"+
		"\u026f\u0270\t\7\2\2\u0270\u0272\7\6\2\2\u0271\u0273\7\31\2\2\u0272\u0271"+
		"\3\2\2\2\u0272\u0273\3\2\2\2\u0273\u0274\3\2\2\2\u0274\u0275\t\7\2\2\u0275"+
		"\u0276\7\17\2\2\u0276\u02a9\b\36\1\2\u0277\u0278\7\16\2\2\u0278\u0279"+
		"\7\32\2\2\u0279\u027a\t\7\2\2\u027a\u027b\7\6\2\2\u027b\u027c\7\32\2\2"+
		"\u027c\u027d\t\7\2\2\u027d\u027e\7\17\2\2\u027e\u02a9\b\36\1\2\u027f\u0281"+
		"\7\31\2\2\u0280\u027f\3\2\2\2\u0280\u0281\3\2\2\2\u0281\u0282\3\2\2\2"+
		"\u0282\u0283\t\7\2\2\u0283\u0284\7\31\2\2\u0284\u0285\t\7\2\2\u0285\u0286"+
		"\7\34\2\2\u0286\u02a9\b\36\1\2\u0287\u0289\7\31\2\2\u0288\u0287\3\2\2"+
		"\2\u0288\u0289\3\2\2\2\u0289\u028a\3\2\2\2\u028a\u028b\t\7\2\2\u028b\u028c"+
		"\7\32\2\2\u028c\u028d\t\7\2\2\u028d\u028e\7\34\2\2\u028e\u02a9\b\36\1"+
		"\2\u028f\u0291\7\31\2\2\u0290\u028f\3\2\2\2\u0290\u0291\3\2\2\2\u0291"+
		"\u0292\3\2\2\2\u0292\u0293\t\7\2\2\u0293\u0294\7\34\2\2\u0294\u02a9\b"+
		"\36\1\2\u0295\u0296\7\32\2\2\u0296\u0297\t\7\2\2\u0297\u0298\7\31\2\2"+
		"\u0298\u0299\t\7\2\2\u0299\u029a\7\34\2\2\u029a\u02a9\b\36\1\2\u029b\u029c"+
		"\7\32\2\2\u029c\u029d\t\7\2\2\u029d\u029e\7\32\2\2\u029e\u029f\t\7\2\2"+
		"\u029f\u02a0\7\34\2\2\u02a0\u02a9\b\36\1\2\u02a1\u02a2\7\32\2\2\u02a2"+
		"\u02a3\t\7\2\2\u02a3\u02a4\7\34\2\2\u02a4\u02a9\b\36\1\2\u02a5\u02a6\5"+
		"8\35\2\u02a6\u02a7\b\36\1\2\u02a7\u02a9\3\2\2\2\u02a8\u0257\3\2\2\2\u02a8"+
		"\u0263\3\2\2\2\u02a8\u026d\3\2\2\2\u02a8\u0277\3\2\2\2\u02a8\u0280\3\2"+
		"\2\2\u02a8\u0288\3\2\2\2\u02a8\u0290\3\2\2\2\u02a8\u0295\3\2\2\2\u02a8"+
		"\u029b\3\2\2\2\u02a8\u02a1\3\2\2\2\u02a8\u02a5\3\2\2\2\u02a9;\3\2\2\2"+
		"\u02aa\u02ab\7C\2\2\u02ab\u02ac\7L\2\2\u02ac\u02ad\b\37\1\2\u02ad\u02af"+
		"\7\3\2\2\u02ae\u02b0\5> \2\u02af\u02ae\3\2\2\2\u02b0\u02b1\3\2\2\2\u02b1"+
		"\u02af\3\2\2\2\u02b1\u02b2\3\2\2\2\u02b2\u02b3\3\2\2\2\u02b3\u02b4\7\4"+
		"\2\2\u02b4=\3\2\2\2\u02b5\u02b6\7\5\2\2\u02b6\u02b7\5J&\2\u02b7\u02b8"+
		"\7\17\2\2\u02b8\u02b9\5J&\2\u02b9\u02ba\7\6\2\2\u02ba\u02bb\7H\2\2\u02bb"+
		"\u02bc\7\6\2\2\u02bc\u02bd\5*\26\2\u02bd\u02be\7\7\2\2\u02be\u02bf\7\n"+
		"\2\2\u02bf\u02c0\b \1\2\u02c0\u02cc\3\2\2\2\u02c1\u02c2\7\5\2\2\u02c2"+
		"\u02c3\5J&\2\u02c3\u02c4\7\17\2\2\u02c4\u02c5\5J&\2\u02c5\u02c6\7\6\2"+
		"\2\u02c6\u02c7\7H\2\2\u02c7\u02c8\7\7\2\2\u02c8\u02c9\7\n\2\2\u02c9\u02ca"+
		"\b \1\2\u02ca\u02cc\3\2\2\2\u02cb\u02b5\3\2\2\2\u02cb\u02c1\3\2\2\2\u02cc"+
		"?\3\2\2\2\u02cd\u02ce\7=\2\2\u02ce\u02cf\b!\1\2\u02cf\u02d0\7\3\2\2\u02d0"+
		"\u02d1\5B\"\2\u02d1\u02d2\7\4\2\2\u02d2A\3\2\2\2\u02d3\u02d7\b\"\1\2\u02d4"+
		"\u02d6\5\30\r\2\u02d5\u02d4\3\2\2\2\u02d6\u02d9\3\2\2\2\u02d7\u02d5\3"+
		"\2\2\2\u02d7\u02d8\3\2\2\2\u02d8\u02da\3\2\2\2\u02d9\u02d7\3\2\2\2\u02da"+
		"\u02db\b\"\1\2\u02dbC\3\2\2\2\u02dc\u02dd\7D\2\2\u02dd\u02de\7\b\2\2\u02de"+
		"\u02df\5F$\2\u02df\u02e0\7\t\2\2\u02e0\u02e1\7\5\2\2\u02e1\u02e2\t\7\2"+
		"\2\u02e2\u02e3\7\7\2\2\u02e3\u02e4\7\3\2\2\u02e4\u02e5\5H%\2\u02e5\u02e6"+
		"\7\4\2\2\u02e6\u02e7\b#\1\2\u02e7E\3\2\2\2\u02e8\u02e9\b$\1\2\u02e9\u02ea"+
		"\5*\26\2\u02ea\u02eb\t\2\2\2\u02eb\u02ec\5*\26\2\u02ec\u02ed\b$\1\2\u02ed"+
		"\u02f5\3\2\2\2\u02ee\u02ef\f\3\2\2\u02ef\u02f0\t\b\2\2\u02f0\u02f1\5F"+
		"$\4\u02f1\u02f2\b$\1\2\u02f2\u02f4\3\2\2\2\u02f3\u02ee\3\2\2\2\u02f4\u02f7"+
		"\3\2\2\2\u02f5\u02f3\3\2\2\2\u02f5\u02f6\3\2\2\2\u02f6G\3\2\2\2\u02f7"+
		"\u02f5\3\2\2\2\u02f8\u02f9\5*\26\2\u02f9\u02fa\b%\1\2\u02fa\u0312\3\2"+
		"\2\2\u02fb\u02fc\5*\26\2\u02fc\u02fd\7\6\2\2\u02fd\u02fe\5*\26\2\u02fe"+
		"\u02ff\7\6\2\2\u02ff\u0300\5*\26\2\u0300\u0301\b%\1\2\u0301\u0312\3\2"+
		"\2\2\u0302\u0303\5*\26\2\u0303\u0304\7\6\2\2\u0304\u0305\5*\26\2\u0305"+
		"\u0306\7\6\2\2\u0306\u0307\5*\26\2\u0307\u0308\7\6\2\2\u0308\u0309\5*"+
		"\26\2\u0309\u030a\b%\1\2\u030a\u0312\3\2\2\2\u030b\u030c\7L\2\2\u030c"+
		"\u030d\7\5\2\2\u030d\u030e\5*\26\2\u030e\u030f\7\7\2\2\u030f\u0310\b%"+
		"\1\2\u0310\u0312\3\2\2\2\u0311\u02f8\3\2\2\2\u0311\u02fb\3\2\2\2\u0311"+
		"\u0302\3\2\2\2\u0311\u030b\3\2\2\2\u0312I\3\2\2\2\u0313\u0314\7\b\2\2"+
		"\u0314\u0315\t\7\2\2\u0315\u0316\7\6\2\2\u0316\u0317\t\7\2\2\u0317\u0318"+
		"\7\6\2\2\u0318\u0319\t\7\2\2\u0319\u031a\7\6\2\2\u031a\u031b\t\7\2\2\u031b"+
		"\u031c\7\t\2\2\u031c\u0322\b&\1\2\u031d\u031e\7E\2\2\u031e\u0322\b&\1"+
		"\2\u031f\u0320\7F\2\2\u0320\u0322\b&\1\2\u0321\u0313\3\2\2\2\u0321\u031d"+
		"\3\2\2\2\u0321\u031f\3\2\2\2\u0322K\3\2\2\2\u0323\u0324\7\b\2\2\u0324"+
		"\u0325\t\7\2\2\u0325\u0326\7\6\2\2\u0326\u0327\t\7\2\2\u0327\u0328\7\6"+
		"\2\2\u0328\u0329\t\7\2\2\u0329\u032a\7\6\2\2\u032a\u032b\t\7\2\2\u032b"+
		"\u032c\7\t\2\2\u032c\u0332\b\'\1\2\u032d\u032e\7E\2\2\u032e\u0332\b\'"+
		"\1\2\u032f\u0330\7F\2\2\u0330\u0332\b\'\1\2\u0331\u0323\3\2\2\2\u0331"+
		"\u032d\3\2\2\2\u0331\u032f\3\2\2\2\u0332M\3\2\2\2\u0333\u0334\7\2\2\3"+
		"\u0334O\3\2\2\2<fjn{\177\u0084\u00ad\u00cd\u00d3\u00dc\u00e5\u00ee\u00fb"+
		"\u0106\u0115\u0125\u0128\u0132\u013c\u0155\u015e\u0167\u0171\u017a\u0184"+
		"\u018d\u0197\u01bb\u01cc\u01d8\u01da\u01e8\u01f3\u01fc\u0200\u0202\u020c"+
		"\u0215\u0223\u0240\u0248\u024e\u0255\u0259\u025e\u0265\u0272\u0280\u0288"+
		"\u0290\u02a8\u02b1\u02cb\u02d7\u02f5\u0311\u0321\u0331";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}