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
		T__52=53, T__53=54, FRACTAL=55, ORBIT=56, TRAP=57, BEGIN=58, LOOP=59, 
		END=60, INIT=61, IF=62, STOP=63, JULIA=64, COLOR=65, PALETTE=66, RULE=67, 
		ARGB=68, RATIONAL=69, INTEGER=70, PATHOP_1POINTS=71, PATHOP_2POINTS=72, 
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
		RULE_eof = 37;
	public static final String[] ruleNames = {
		"fractal", "orbit", "color", "begin", "loop", "end", "trap", "pathop", 
		"beginstatement", "loopstatement", "endstatement", "statement", "statevariable", 
		"statevariablelist", "simpleconditionexp", "conditionexp", "conditionexp2", 
		"conditionexp3", "conditionexp4", "simpleexpression", "expression", "expression2", 
		"expression3", "expression4", "function", "constant", "variable", "real", 
		"complex", "palette", "paletteelement", "colorinit", "colorstatement", 
		"colorrule", "ruleexp", "colorexp", "colorargb", "eof"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'['", "','", "']'", "'('", "')'", "';'", "'='", "'else'", 
		"'real'", "'complex'", "'<'", "'>'", "'<='", "'>='", "'<>'", "'?'", "'~?'", 
		"'|'", "'^'", "'&'", "'~'", "'+'", "'-'", "'*'", "'i'", "'/'", "'mod'", 
		"'mod2'", "'pha'", "'re'", "'im'", "'cos'", "'sin'", "'tan'", "'acos'", 
		"'asin'", "'atan'", "'log'", "'exp'", "'sqrt'", "'abs'", "'ceil'", "'floor'", 
		"'pow'", "'atan2'", "'hypot'", "'max'", "'min'", "'e'", "'pi'", "'2pi'", 
		"'#'", "'fractal'", "'orbit'", "'trap'", "'begin'", "'loop'", "'end'", 
		"'init'", "'if'", "'stop'", "'julia'", "'color'", "'palette'", "'rule'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, "FRACTAL", "ORBIT", "TRAP", 
		"BEGIN", "LOOP", "END", "INIT", "IF", "STOP", "JULIA", "COLOR", "PALETTE", 
		"RULE", "ARGB", "RATIONAL", "INTEGER", "PATHOP_1POINTS", "PATHOP_2POINTS", 
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
			setState(76);
			((FractalContext)_localctx).f = match(FRACTAL);

					builder.setFractal(new ASTFractal(((FractalContext)_localctx).f));
				
			setState(78);
			match(T__0);
			setState(79);
			orbit();
			setState(80);
			color();
			setState(81);
			match(T__1);
			setState(82);
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
			setState(84);
			((OrbitContext)_localctx).o = match(ORBIT);
			setState(85);
			match(T__2);
			setState(86);
			((OrbitContext)_localctx).ra = complex();
			setState(87);
			match(T__3);
			setState(88);
			((OrbitContext)_localctx).rb = complex();
			setState(89);
			match(T__4);

					builder.setOrbit(new ASTOrbit(((OrbitContext)_localctx).o, new ASTRegion(((OrbitContext)_localctx).ra.result, ((OrbitContext)_localctx).rb.result)));
				
			setState(91);
			match(T__2);
			setState(92);
			statevariablelist(0);
			setState(93);
			match(T__4);
			setState(94);
			match(T__0);
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TRAP) {
				{
				{
				setState(95);
				trap();
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(102);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(101);
				begin();
				}
			}

			setState(104);
			loop();
			setState(106);
			_la = _input.LA(1);
			if (_la==END) {
				{
				setState(105);
				end();
				}
			}

			setState(108);
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
		public ColorargbContext argb;
		public TerminalNode COLOR() { return getToken(MandelbrotParser.COLOR, 0); }
		public ColorargbContext colorargb() {
			return getRuleContext(ColorargbContext.class,0);
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
			setState(110);
			((ColorContext)_localctx).c = match(COLOR);
			setState(111);
			match(T__2);
			setState(112);
			((ColorContext)_localctx).argb = colorargb();
			setState(113);
			match(T__4);
			 
					builder.setColor(new ASTColor(((ColorContext)_localctx).c, ((ColorContext)_localctx).argb.result));
				
			setState(115);
			match(T__0);
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PALETTE) {
				{
				{
				setState(116);
				palette();
				}
				}
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(123);
			_la = _input.LA(1);
			if (_la==INIT) {
				{
				setState(122);
				colorinit();
				}
			}

			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RULE) {
				{
				{
				setState(125);
				colorrule();
				}
				}
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(131);
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
			setState(133);
			((BeginContext)_localctx).b = match(BEGIN);
			 
					builder.setOrbitBegin(new ASTOrbitBegin(((BeginContext)_localctx).b));
				
			setState(135);
			match(T__0);
			setState(136);
			beginstatement();
			setState(137);
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
			setState(139);
			((LoopContext)_localctx).l = match(LOOP);
			setState(140);
			match(T__2);
			setState(141);
			((LoopContext)_localctx).lb = match(INTEGER);
			setState(142);
			match(T__3);
			setState(143);
			((LoopContext)_localctx).le = match(INTEGER);
			setState(144);
			match(T__4);
			setState(145);
			match(T__5);
			setState(146);
			((LoopContext)_localctx).e = conditionexp(0);
			setState(147);
			match(T__6);

					builder.setOrbitLoop(new ASTOrbitLoop(((LoopContext)_localctx).l, builder.parseInt((((LoopContext)_localctx).lb!=null?((LoopContext)_localctx).lb.getText():null)), builder.parseInt((((LoopContext)_localctx).le!=null?((LoopContext)_localctx).le.getText():null)), ((LoopContext)_localctx).e.result));
				
			setState(149);
			match(T__0);
			setState(150);
			loopstatement();
			setState(151);
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
			setState(153);
			((EndContext)_localctx).e = match(END);

					builder.setOrbitEnd(new ASTOrbitEnd(((EndContext)_localctx).e));		
				
			setState(155);
			match(T__0);
			setState(156);
			endstatement();
			setState(157);
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
			setState(159);
			((TrapContext)_localctx).t = match(TRAP);
			setState(160);
			((TrapContext)_localctx).n = match(VARIABLE);
			setState(161);
			match(T__2);
			setState(162);
			((TrapContext)_localctx).c = complex();
			setState(163);
			match(T__4);

					builder.addOrbitTrap(new ASTOrbitTrap(((TrapContext)_localctx).t, (((TrapContext)_localctx).n!=null?((TrapContext)_localctx).n.getText():null), ((TrapContext)_localctx).c.result));
				
			setState(165);
			match(T__0);
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 71)) & ~0x3f) == 0 && ((1L << (_la - 71)) & ((1L << (PATHOP_1POINTS - 71)) | (1L << (PATHOP_2POINTS - 71)) | (1L << (PATHOP_3POINTS - 71)))) != 0)) {
				{
				{
				setState(166);
				pathop();
				}
				}
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(172);
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
			setState(201);
			switch (_input.LA(1)) {
			case PATHOP_1POINTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				((PathopContext)_localctx).o = match(PATHOP_1POINTS);
				setState(175);
				match(T__5);
				setState(176);
				((PathopContext)_localctx).c = complex();
				setState(177);
				match(T__6);
				setState(178);
				match(T__7);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c.result));
					
				}
				break;
			case PATHOP_2POINTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(181);
				((PathopContext)_localctx).o = match(PATHOP_2POINTS);
				setState(182);
				match(T__5);
				setState(183);
				((PathopContext)_localctx).c1 = complex();
				setState(184);
				match(T__3);
				setState(185);
				((PathopContext)_localctx).c2 = complex();
				setState(186);
				match(T__6);
				setState(187);
				match(T__7);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c1.result, ((PathopContext)_localctx).c2.result));
					
				}
				break;
			case PATHOP_3POINTS:
				enterOuterAlt(_localctx, 3);
				{
				setState(190);
				((PathopContext)_localctx).o = match(PATHOP_3POINTS);
				setState(191);
				match(T__5);
				setState(192);
				((PathopContext)_localctx).c1 = complex();
				setState(193);
				match(T__3);
				setState(194);
				((PathopContext)_localctx).c2 = complex();
				setState(195);
				match(T__3);
				setState(196);
				((PathopContext)_localctx).c3 = complex();
				setState(197);
				match(T__6);
				setState(198);
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
				
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 62)) & ~0x3f) == 0 && ((1L << (_la - 62)) & ((1L << (IF - 62)) | (1L << (STOP - 62)) | (1L << (VARIABLE - 62)))) != 0)) {
				{
				{
				setState(204);
				statement();
				}
				}
				setState(209);
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
				
			setState(216);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 62)) & ~0x3f) == 0 && ((1L << (_la - 62)) & ((1L << (IF - 62)) | (1L << (STOP - 62)) | (1L << (VARIABLE - 62)))) != 0)) {
				{
				{
				setState(213);
				statement();
				}
				}
				setState(218);
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
				
			setState(225);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 62)) & ~0x3f) == 0 && ((1L << (_la - 62)) & ((1L << (IF - 62)) | (1L << (STOP - 62)) | (1L << (VARIABLE - 62)))) != 0)) {
				{
				{
				setState(222);
				statement();
				}
				}
				setState(227);
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
			setState(295);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(230);
				((StatementContext)_localctx).v = match(VARIABLE);
				setState(231);
				match(T__8);
				setState(232);
				((StatementContext)_localctx).e = expression(0);
				setState(234);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(233);
					match(T__7);
					}
					break;
				}

						builder.registerVariable((((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result.isReal(), ((StatementContext)_localctx).v);
						builder.appendStatement(new ASTAssignStatement(((StatementContext)_localctx).v, (((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result));
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(238);
				((StatementContext)_localctx).f = match(IF);
				setState(239);
				match(T__5);
				setState(240);
				((StatementContext)_localctx).c = conditionexp(0);
				setState(241);
				match(T__6);
				setState(242);
				match(T__0);

						builder.pushScope();	
						builder.pushStatementList();
					
				setState(247);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 62)) & ~0x3f) == 0 && ((1L << (_la - 62)) & ((1L << (IF - 62)) | (1L << (STOP - 62)) | (1L << (VARIABLE - 62)))) != 0)) {
					{
					{
					setState(244);
					statement();
					}
					}
					setState(249);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(250);
				match(T__1);

						ASTStatementList thenList = builder.getStatementList();
						builder.popScope();	
						builder.popStatementList();
					
				setState(252);
				match(T__9);
				setState(253);
				match(T__0);

						builder.pushScope();	
						builder.pushStatementList();
					
				setState(258);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 62)) & ~0x3f) == 0 && ((1L << (_la - 62)) & ((1L << (IF - 62)) | (1L << (STOP - 62)) | (1L << (VARIABLE - 62)))) != 0)) {
					{
					{
					setState(255);
					statement();
					}
					}
					setState(260);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(261);
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
				setState(264);
				((StatementContext)_localctx).f = match(IF);
				setState(265);
				match(T__5);
				setState(266);
				((StatementContext)_localctx).c = conditionexp(0);
				setState(267);
				match(T__6);
				setState(268);
				match(T__0);

						builder.pushScope();	
						builder.pushStatementList();
					
				setState(273);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 62)) & ~0x3f) == 0 && ((1L << (_la - 62)) & ((1L << (IF - 62)) | (1L << (STOP - 62)) | (1L << (VARIABLE - 62)))) != 0)) {
					{
					{
					setState(270);
					statement();
					}
					}
					setState(275);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(276);
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
				setState(279);
				((StatementContext)_localctx).f = match(IF);
				setState(280);
				match(T__5);
				setState(281);
				((StatementContext)_localctx).c = conditionexp(0);
				setState(282);
				match(T__6);

						builder.pushScope();	
						builder.pushStatementList();
					
				setState(284);
				statement();
				setState(286);
				switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
				case 1:
					{
					setState(285);
					match(T__7);
					}
					break;
				}

						ASTStatementList thenList = builder.getStatementList();
						builder.popScope();	
						builder.popStatementList();
						builder.appendStatement(new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, thenList));
					
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(290);
				((StatementContext)_localctx).t = match(STOP);
				setState(292);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(291);
					match(T__7);
					}
					break;
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
			setState(305);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(297);
				match(T__10);
				setState(298);
				((StatevariableContext)_localctx).v = match(VARIABLE);

						builder.registerStateVariable((((StatevariableContext)_localctx).v!=null?((StatevariableContext)_localctx).v.getText():null), true, ((StatevariableContext)_localctx).v);
					
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(300);
				match(T__11);
				setState(301);
				((StatevariableContext)_localctx).v = match(VARIABLE);

						builder.registerStateVariable((((StatevariableContext)_localctx).v!=null?((StatevariableContext)_localctx).v.getText():null), false, ((StatevariableContext)_localctx).v);
					
				}
				break;
			case VARIABLE:
				enterOuterAlt(_localctx, 3);
				{
				setState(303);
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
			setState(308);
			statevariable();
			}
			_ctx.stop = _input.LT(-1);
			setState(315);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StatevariablelistContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_statevariablelist);
					setState(310);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(311);
					match(T__3);
					setState(312);
					statevariable();
					}
					} 
				}
				setState(317);
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
			setState(340);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(318);
				((SimpleconditionexpContext)_localctx).e1 = expression(0);
				setState(319);
				((SimpleconditionexpContext)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16))) != 0)) ) {
					((SimpleconditionexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(320);
				((SimpleconditionexpContext)_localctx).e2 = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionCompareOp(((SimpleconditionexpContext)_localctx).e1.result.getLocation(), (((SimpleconditionexpContext)_localctx).o!=null?((SimpleconditionexpContext)_localctx).o.getText():null), ((SimpleconditionexpContext)_localctx).e1.result, ((SimpleconditionexpContext)_localctx).e2.result);
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(323);
				((SimpleconditionexpContext)_localctx).v = match(VARIABLE);
				setState(324);
				match(T__17);
				setState(325);
				((SimpleconditionexpContext)_localctx).e = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionTrap(((SimpleconditionexpContext)_localctx).v, (((SimpleconditionexpContext)_localctx).v!=null?((SimpleconditionexpContext)_localctx).v.getText():null), ((SimpleconditionexpContext)_localctx).e.result, true);
					
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(328);
				((SimpleconditionexpContext)_localctx).v = match(VARIABLE);
				setState(329);
				match(T__18);
				setState(330);
				((SimpleconditionexpContext)_localctx).e = expression(0);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionTrap(((SimpleconditionexpContext)_localctx).v, (((SimpleconditionexpContext)_localctx).v!=null?((SimpleconditionexpContext)_localctx).v.getText():null), ((SimpleconditionexpContext)_localctx).e.result, false);
					
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(333);
				((SimpleconditionexpContext)_localctx).t = match(JULIA);

						((SimpleconditionexpContext)_localctx).result =  new ASTConditionJulia(((SimpleconditionexpContext)_localctx).t);
					
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(335);
				((SimpleconditionexpContext)_localctx).s = match(T__5);
				setState(336);
				((SimpleconditionexpContext)_localctx).c = conditionexp(0);
				setState(337);
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
			setState(349);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(343);
				((ConditionexpContext)_localctx).c = simpleconditionexp();

						((ConditionexpContext)_localctx).result =  ((ConditionexpContext)_localctx).c.result;
					
				}
				break;
			case 2:
				{
				setState(346);
				((ConditionexpContext)_localctx).c2 = conditionexp2(0);

						((ConditionexpContext)_localctx).result =  ((ConditionexpContext)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(358);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
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
					setState(351);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(352);
					((ConditionexpContext)_localctx).l = match(T__19);
					setState(353);
					((ConditionexpContext)_localctx).c2 = conditionexp2(0);

					          		((ConditionexpContext)_localctx).result =  new ASTConditionLogicOp(((ConditionexpContext)_localctx).c1.result.getLocation(), (((ConditionexpContext)_localctx).l!=null?((ConditionexpContext)_localctx).l.getText():null), ((ConditionexpContext)_localctx).c1.result, ((ConditionexpContext)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(360);
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
			setState(368);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(362);
				((Conditionexp2Context)_localctx).c = simpleconditionexp();

						((Conditionexp2Context)_localctx).result =  ((Conditionexp2Context)_localctx).c.result;
					
				}
				break;
			case 2:
				{
				setState(365);
				((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

						((Conditionexp2Context)_localctx).result =  ((Conditionexp2Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(377);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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
					setState(370);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(371);
					((Conditionexp2Context)_localctx).l = match(T__20);
					setState(372);
					((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

					          		((Conditionexp2Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp2Context)_localctx).c1.result.getLocation(), (((Conditionexp2Context)_localctx).l!=null?((Conditionexp2Context)_localctx).l.getText():null), ((Conditionexp2Context)_localctx).c1.result, ((Conditionexp2Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(379);
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
			setState(387);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(381);
				((Conditionexp3Context)_localctx).c = simpleconditionexp();

						((Conditionexp3Context)_localctx).result =  ((Conditionexp3Context)_localctx).c.result;
					
				}
				break;
			case 2:
				{
				setState(384);
				((Conditionexp3Context)_localctx).c2 = conditionexp4();

						((Conditionexp3Context)_localctx).result =  ((Conditionexp3Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(396);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
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
					setState(389);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(390);
					((Conditionexp3Context)_localctx).l = match(T__21);
					setState(391);
					((Conditionexp3Context)_localctx).c2 = conditionexp4();

					          		((Conditionexp3Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp3Context)_localctx).c1.result.getLocation(), (((Conditionexp3Context)_localctx).l!=null?((Conditionexp3Context)_localctx).l.getText():null), ((Conditionexp3Context)_localctx).c1.result, ((Conditionexp3Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(398);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
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
			setState(406);
			switch (_input.LA(1)) {
			case T__5:
			case T__12:
			case T__19:
			case T__23:
			case T__24:
			case T__26:
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
			case T__52:
			case JULIA:
			case RATIONAL:
			case INTEGER:
			case VARIABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(399);
				((Conditionexp4Context)_localctx).c1 = simpleconditionexp();

						((Conditionexp4Context)_localctx).result =  ((Conditionexp4Context)_localctx).c1.result;
					
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 2);
				{
				setState(402);
				((Conditionexp4Context)_localctx).n = match(T__22);
				setState(403);
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
			setState(442);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(408);
				((SimpleexpressionContext)_localctx).p = constant();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).p.result;
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(411);
				((SimpleexpressionContext)_localctx).v = variable();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).v.result;
					
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(414);
				((SimpleexpressionContext)_localctx).r = real();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).r.result;
					
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(417);
				((SimpleexpressionContext)_localctx).f = function();

						((SimpleexpressionContext)_localctx).result =  ((SimpleexpressionContext)_localctx).f.result;
					
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(420);
				((SimpleexpressionContext)_localctx).t = match(T__5);
				setState(421);
				((SimpleexpressionContext)_localctx).e = expression(0);
				setState(422);
				match(T__6);

						((SimpleexpressionContext)_localctx).result =  new ASTParen(((SimpleexpressionContext)_localctx).t, ((SimpleexpressionContext)_localctx).e.result);
					
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(425);
				((SimpleexpressionContext)_localctx).m = match(T__19);
				setState(426);
				((SimpleexpressionContext)_localctx).e = expression(0);
				setState(427);
				match(T__19);

						((SimpleexpressionContext)_localctx).result =  new ASTFunction(((SimpleexpressionContext)_localctx).m, "mod", ((SimpleexpressionContext)_localctx).e.result);	
					
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(430);
				((SimpleexpressionContext)_localctx).a = match(T__12);
				setState(431);
				((SimpleexpressionContext)_localctx).e = expression(0);
				setState(432);
				match(T__13);

						((SimpleexpressionContext)_localctx).result =  new ASTFunction(((SimpleexpressionContext)_localctx).a, "pha", ((SimpleexpressionContext)_localctx).e.result);	
					
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(435);
				((SimpleexpressionContext)_localctx).a = match(T__12);
				setState(436);
				((SimpleexpressionContext)_localctx).er = expression(0);
				setState(437);
				match(T__3);
				setState(438);
				((SimpleexpressionContext)_localctx).ei = expression(0);
				setState(439);
				match(T__13);

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
			setState(459);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(445);
				((ExpressionContext)_localctx).e2 = expression2(0);
				setState(446);
				((ExpressionContext)_localctx).s = match(T__23);
				setState(447);
				((ExpressionContext)_localctx).e1 = expression(2);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e2.result, ((ExpressionContext)_localctx).e1.result);		
					
				}
				break;
			case 2:
				{
				setState(450);
				((ExpressionContext)_localctx).e = simpleexpression();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e.result;	
					
				}
				break;
			case 3:
				{
				setState(453);
				((ExpressionContext)_localctx).c = complex();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).c.result;
					
				}
				break;
			case 4:
				{
				setState(456);
				((ExpressionContext)_localctx).e2 = expression2(0);

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e2.result;	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(473);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(471);
					switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(461);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(462);
						((ExpressionContext)_localctx).s = match(T__23);
						setState(463);
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
						setState(466);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(467);
						((ExpressionContext)_localctx).s = match(T__24);
						setState(468);
						((ExpressionContext)_localctx).e2 = expression2(0);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					}
					} 
				}
				setState(475);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
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
			setState(498);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(477);
				((Expression2Context)_localctx).s = match(T__24);
				setState(478);
				((Expression2Context)_localctx).e2 = expression2(4);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "-", ((Expression2Context)_localctx).e2.result);
					
				}
				break;
			case 2:
				{
				setState(481);
				((Expression2Context)_localctx).s = match(T__23);
				setState(482);
				((Expression2Context)_localctx).e2 = expression2(3);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "+", ((Expression2Context)_localctx).e2.result);
					
				}
				break;
			case 3:
				{
				setState(485);
				((Expression2Context)_localctx).i = match(T__26);
				setState(487);
				_la = _input.LA(1);
				if (_la==T__25) {
					{
					setState(486);
					match(T__25);
					}
				}

				setState(489);
				((Expression2Context)_localctx).e2 = expression2(2);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
					
				}
				break;
			case 4:
				{
				setState(492);
				((Expression2Context)_localctx).e = simpleexpression();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e.result;	
					
				}
				break;
			case 5:
				{
				setState(495);
				((Expression2Context)_localctx).e3 = expression3(0);

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(513);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(511);
					switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
					case 1:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(500);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(501);
						((Expression2Context)_localctx).s = match(T__25);
						setState(502);
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
						setState(505);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(507);
						_la = _input.LA(1);
						if (_la==T__25) {
							{
							setState(506);
							match(T__25);
							}
						}

						setState(509);
						((Expression2Context)_localctx).i = match(T__26);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					}
					} 
				}
				setState(515);
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
			setState(523);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(517);
				((Expression3Context)_localctx).e = simpleexpression();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e.result;	
					
				}
				break;
			case 2:
				{
				setState(520);
				((Expression3Context)_localctx).e3 = expression4(0);

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(532);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
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
					setState(525);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(526);
					((Expression3Context)_localctx).s = match(T__27);
					setState(527);
					((Expression3Context)_localctx).e2 = expression3(2);

					          		((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).s, "/", ((Expression3Context)_localctx).e1.result, ((Expression3Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(534);
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
			setState(536);
			((Expression4Context)_localctx).e = simpleexpression();

					((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).e.result;	
				
			}
			_ctx.stop = _input.LT(-1);
			setState(546);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
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
					setState(539);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(540);
					((Expression4Context)_localctx).s = match(T__20);
					setState(541);
					((Expression4Context)_localctx).e2 = expression4(2);

					          		((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).s, "^", ((Expression4Context)_localctx).e1.result, ((Expression4Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(548);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
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
			setState(575);
			switch (_input.LA(1)) {
			case T__28:
			case T__29:
			case T__30:
			case T__31:
			case T__32:
				enterOuterAlt(_localctx, 1);
				{
				setState(549);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(550);
				match(T__5);
				setState(551);
				((FunctionContext)_localctx).e = expression(0);
				setState(552);
				match(T__6);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), ((FunctionContext)_localctx).e.result);		
					
				}
				break;
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
			case T__38:
				enterOuterAlt(_localctx, 2);
				{
				setState(555);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(556);
				match(T__5);
				setState(557);
				((FunctionContext)_localctx).e = expression(0);
				setState(558);
				match(T__6);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case T__39:
			case T__40:
			case T__41:
			case T__42:
			case T__43:
			case T__44:
				enterOuterAlt(_localctx, 3);
				{
				setState(561);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(562);
				match(T__5);
				setState(563);
				((FunctionContext)_localctx).e = expression(0);
				setState(564);
				match(T__6);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case T__45:
			case T__46:
			case T__47:
			case T__48:
			case T__49:
				enterOuterAlt(_localctx, 4);
				{
				setState(567);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(568);
				match(T__5);
				setState(569);
				((FunctionContext)_localctx).e1 = expression(0);
				setState(570);
				match(T__3);
				setState(571);
				((FunctionContext)_localctx).e2 = expression(0);
				setState(572);
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
			setState(583);
			switch (_input.LA(1)) {
			case T__50:
				enterOuterAlt(_localctx, 1);
				{
				setState(577);
				((ConstantContext)_localctx).p = match(T__50);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.E);
					
				}
				break;
			case T__51:
				enterOuterAlt(_localctx, 2);
				{
				setState(579);
				((ConstantContext)_localctx).p = match(T__51);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.PI);
					
				}
				break;
			case T__52:
				enterOuterAlt(_localctx, 3);
				{
				setState(581);
				((ConstantContext)_localctx).p = match(T__52);

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
			setState(585);
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
			setState(596);
			switch (_input.LA(1)) {
			case T__23:
			case RATIONAL:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(589);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(588);
					match(T__23);
					}
				}

				setState(591);
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
			case T__24:
				enterOuterAlt(_localctx, 2);
				{
				setState(593);
				match(T__24);
				setState(594);
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
			setState(679);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(598);
				match(T__12);
				setState(600);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(599);
					match(T__23);
					}
				}

				setState(602);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(603);
				match(T__3);
				setState(605);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(604);
					match(T__23);
					}
				}

				setState(607);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(608);
				match(T__13);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(610);
				match(T__12);
				setState(612);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(611);
					match(T__23);
					}
				}

				setState(614);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(615);
				match(T__3);
				setState(616);
				match(T__24);
				setState(617);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(618);
				match(T__13);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(620);
				match(T__12);
				setState(621);
				match(T__24);
				setState(622);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(623);
				match(T__3);
				setState(625);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(624);
					match(T__23);
					}
				}

				setState(627);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(628);
				match(T__13);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(630);
				match(T__12);
				setState(631);
				match(T__24);
				setState(632);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(633);
				match(T__3);
				setState(634);
				match(T__24);
				setState(635);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(636);
				match(T__13);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(639);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(638);
					match(T__23);
					}
				}

				setState(641);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(642);
				match(T__23);
				setState(643);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(644);
				match(T__26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(647);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(646);
					match(T__23);
					}
				}

				setState(649);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(650);
				match(T__24);
				setState(651);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(652);
				match(T__26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(655);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(654);
					match(T__23);
					}
				}

				setState(657);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(658);
				match(T__26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble((((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(660);
				match(T__24);
				setState(661);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(662);
				match(T__23);
				setState(663);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(664);
				match(T__26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(666);
				match(T__24);
				setState(667);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(668);
				match(T__24);
				setState(669);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(670);
				match(T__26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(672);
				match(T__24);
				setState(673);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(674);
				match(T__26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(676);
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
			setState(681);
			((PaletteContext)_localctx).p = match(PALETTE);
			setState(682);
			((PaletteContext)_localctx).v = match(VARIABLE);

					builder.addPalette(new ASTPalette(((PaletteContext)_localctx).p, (((PaletteContext)_localctx).v!=null?((PaletteContext)_localctx).v.getText():null))); 
				
			setState(684);
			match(T__0);
			setState(686); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(685);
				paletteelement();
				}
				}
				setState(688); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__2 );
			setState(690);
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
			setState(714);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(692);
				((PaletteelementContext)_localctx).t = match(T__2);
				setState(693);
				((PaletteelementContext)_localctx).bc = colorargb();
				setState(694);
				match(T__13);
				setState(695);
				((PaletteelementContext)_localctx).ec = colorargb();
				setState(696);
				match(T__3);
				setState(697);
				((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(698);
				match(T__3);
				setState(699);
				((PaletteelementContext)_localctx).e = expression(0);
				setState(700);
				match(T__4);
				setState(701);
				match(T__7);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), ((PaletteelementContext)_localctx).e.result));
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(704);
				((PaletteelementContext)_localctx).t = match(T__2);
				setState(705);
				((PaletteelementContext)_localctx).bc = colorargb();
				setState(706);
				match(T__13);
				setState(707);
				((PaletteelementContext)_localctx).ec = colorargb();
				setState(708);
				match(T__3);
				setState(709);
				((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(710);
				match(T__4);
				setState(711);
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
			setState(716);
			((ColorinitContext)_localctx).i = match(INIT);

					builder.setColorContext(true);
					builder.setColorInit(new ASTColorInit(((ColorinitContext)_localctx).i));
				
			setState(718);
			match(T__0);
			setState(719);
			colorstatement();
			setState(720);
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
				
			setState(726);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 62)) & ~0x3f) == 0 && ((1L << (_la - 62)) & ((1L << (IF - 62)) | (1L << (STOP - 62)) | (1L << (VARIABLE - 62)))) != 0)) {
				{
				{
				setState(723);
				statement();
				}
				}
				setState(728);
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
			setState(731);
			((ColorruleContext)_localctx).t = match(RULE);
			setState(732);
			match(T__5);
			setState(733);
			((ColorruleContext)_localctx).r = ruleexp(0);
			setState(734);
			match(T__6);
			setState(735);
			match(T__2);
			setState(736);
			((ColorruleContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==RATIONAL || _la==INTEGER) ) {
				((ColorruleContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(737);
			match(T__4);
			setState(738);
			match(T__0);
			setState(739);
			((ColorruleContext)_localctx).c = colorexp();
			setState(740);
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
			setState(744);
			((RuleexpContext)_localctx).e1 = expression(0);
			setState(745);
			((RuleexpContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16))) != 0)) ) {
				((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(746);
			((RuleexpContext)_localctx).e2 = expression(0);

					((RuleexpContext)_localctx).result =  new ASTRuleCompareOp(((RuleexpContext)_localctx).e1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).e1.result, ((RuleexpContext)_localctx).e2.result);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(756);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,55,_ctx);
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
					setState(749);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(750);
					((RuleexpContext)_localctx).o = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__19) | (1L << T__20) | (1L << T__21))) != 0)) ) {
						((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(751);
					((RuleexpContext)_localctx).r2 = ruleexp(2);

					          		((RuleexpContext)_localctx).result =  new ASTRuleLogicOp(((RuleexpContext)_localctx).r1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).r1.result, ((RuleexpContext)_localctx).r2.result);
					          	
					}
					} 
				}
				setState(758);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,55,_ctx);
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
			setState(784);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(759);
				((ColorexpContext)_localctx).e1 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result);
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(762);
				((ColorexpContext)_localctx).e1 = expression(0);
				setState(763);
				match(T__3);
				setState(764);
				((ColorexpContext)_localctx).e2 = expression(0);
				setState(765);
				match(T__3);
				setState(766);
				((ColorexpContext)_localctx).e3 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result);
					
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(769);
				((ColorexpContext)_localctx).e1 = expression(0);
				setState(770);
				match(T__3);
				setState(771);
				((ColorexpContext)_localctx).e2 = expression(0);
				setState(772);
				match(T__3);
				setState(773);
				((ColorexpContext)_localctx).e3 = expression(0);
				setState(774);
				match(T__3);
				setState(775);
				((ColorexpContext)_localctx).e4 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result, ((ColorexpContext)_localctx).e4.result);
					
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(778);
				((ColorexpContext)_localctx).v = match(VARIABLE);
				setState(779);
				match(T__2);
				setState(780);
				((ColorexpContext)_localctx).e = expression(0);
				setState(781);
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
		public Token argb;
		public List<TerminalNode> RATIONAL() { return getTokens(MandelbrotParser.RATIONAL); }
		public TerminalNode RATIONAL(int i) {
			return getToken(MandelbrotParser.RATIONAL, i);
		}
		public List<TerminalNode> INTEGER() { return getTokens(MandelbrotParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(MandelbrotParser.INTEGER, i);
		}
		public TerminalNode ARGB() { return getToken(MandelbrotParser.ARGB, 0); }
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
				setState(786);
				match(T__5);
				setState(787);
				((ColorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(788);
				match(T__3);
				setState(789);
				((ColorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(790);
				match(T__3);
				setState(791);
				((ColorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(792);
				match(T__3);
				setState(793);
				((ColorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(794);
				match(T__6);

						((ColorargbContext)_localctx).result =  new ASTColorARGB(builder.parseFloat((((ColorargbContext)_localctx).a!=null?((ColorargbContext)_localctx).a.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).r!=null?((ColorargbContext)_localctx).r.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).g!=null?((ColorargbContext)_localctx).g.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).b!=null?((ColorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case T__53:
				enterOuterAlt(_localctx, 2);
				{
				setState(796);
				match(T__53);
				setState(797);
				((ColorargbContext)_localctx).argb = match(ARGB);

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
		enterRule(_localctx, 74, RULE_eof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(801);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3N\u0326\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3c\n\3\f\3\16"+
		"\3f\13\3\3\3\5\3i\n\3\3\3\3\3\5\3m\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\7\4x\n\4\f\4\16\4{\13\4\3\4\5\4~\n\4\3\4\7\4\u0081\n\4\f\4\16\4\u0084"+
		"\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\7\b\u00aa\n\b\f\b\16\b\u00ad\13\b\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\5\t\u00cc\n\t\3\n\3\n\7\n\u00d0\n\n\f\n\16\n\u00d3"+
		"\13\n\3\n\3\n\3\13\3\13\7\13\u00d9\n\13\f\13\16\13\u00dc\13\13\3\13\3"+
		"\13\3\f\3\f\7\f\u00e2\n\f\f\f\16\f\u00e5\13\f\3\f\3\f\3\r\3\r\3\r\3\r"+
		"\5\r\u00ed\n\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00f8\n\r\f\r\16"+
		"\r\u00fb\13\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0103\n\r\f\r\16\r\u0106\13"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0112\n\r\f\r\16\r\u0115"+
		"\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0121\n\r\3\r\3\r\3"+
		"\r\3\r\5\r\u0127\n\r\3\r\5\r\u012a\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\5\16\u0134\n\16\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u013c\n\17"+
		"\f\17\16\17\u013f\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0157"+
		"\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u0160\n\21\3\21\3\21\3\21"+
		"\3\21\3\21\7\21\u0167\n\21\f\21\16\21\u016a\13\21\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\5\22\u0173\n\22\3\22\3\22\3\22\3\22\3\22\7\22\u017a\n"+
		"\22\f\22\16\22\u017d\13\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0186"+
		"\n\23\3\23\3\23\3\23\3\23\3\23\7\23\u018d\n\23\f\23\16\23\u0190\13\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0199\n\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\5\25\u01bd\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u01ce\n\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\7\26\u01da\n\26\f\26\16\26\u01dd\13\26\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u01ea\n\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u01f5\n\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\5\27\u01fe\n\27\3\27\3\27\7\27\u0202\n\27\f\27\16"+
		"\27\u0205\13\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u020e\n\30\3\30"+
		"\3\30\3\30\3\30\3\30\7\30\u0215\n\30\f\30\16\30\u0218\13\30\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u0223\n\31\f\31\16\31\u0226\13"+
		"\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0242"+
		"\n\32\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u024a\n\33\3\34\3\34\3\34\3\35"+
		"\5\35\u0250\n\35\3\35\3\35\3\35\3\35\3\35\5\35\u0257\n\35\3\36\3\36\5"+
		"\36\u025b\n\36\3\36\3\36\3\36\5\36\u0260\n\36\3\36\3\36\3\36\3\36\3\36"+
		"\5\36\u0267\n\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\5\36\u0274\n\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\5\36\u0282\n\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u028a\n\36\3"+
		"\36\3\36\3\36\3\36\3\36\3\36\5\36\u0292\n\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\5\36\u02aa\n\36\3\37\3\37\3\37\3\37\3\37\6\37\u02b1\n"+
		"\37\r\37\16\37\u02b2\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 "+
		"\3 \3 \3 \3 \3 \3 \3 \3 \3 \5 \u02cd\n \3!\3!\3!\3!\3!\3!\3\"\3\"\7\""+
		"\u02d7\n\"\f\"\16\"\u02da\13\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3"+
		"#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\7$\u02f5\n$\f$\16$\u02f8\13$\3%"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%"+
		"\3%\5%\u0313\n%\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\5&\u0322\n&\3\'"+
		"\3\'\3\'\2\13\34 \"$*,.\60F(\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \""+
		"$&(*,.\60\62\64\668:<>@BDFHJL\2\t\4\2\13\13\17\23\3\2\37#\3\2$)\3\2*/"+
		"\3\2\60\64\3\2GH\3\2\26\30\u035a\2N\3\2\2\2\4V\3\2\2\2\6p\3\2\2\2\b\u0087"+
		"\3\2\2\2\n\u008d\3\2\2\2\f\u009b\3\2\2\2\16\u00a1\3\2\2\2\20\u00cb\3\2"+
		"\2\2\22\u00cd\3\2\2\2\24\u00d6\3\2\2\2\26\u00df\3\2\2\2\30\u0129\3\2\2"+
		"\2\32\u0133\3\2\2\2\34\u0135\3\2\2\2\36\u0156\3\2\2\2 \u015f\3\2\2\2\""+
		"\u0172\3\2\2\2$\u0185\3\2\2\2&\u0198\3\2\2\2(\u01bc\3\2\2\2*\u01cd\3\2"+
		"\2\2,\u01f4\3\2\2\2.\u020d\3\2\2\2\60\u0219\3\2\2\2\62\u0241\3\2\2\2\64"+
		"\u0249\3\2\2\2\66\u024b\3\2\2\28\u0256\3\2\2\2:\u02a9\3\2\2\2<\u02ab\3"+
		"\2\2\2>\u02cc\3\2\2\2@\u02ce\3\2\2\2B\u02d4\3\2\2\2D\u02dd\3\2\2\2F\u02e9"+
		"\3\2\2\2H\u0312\3\2\2\2J\u0321\3\2\2\2L\u0323\3\2\2\2NO\79\2\2OP\b\2\1"+
		"\2PQ\7\3\2\2QR\5\4\3\2RS\5\6\4\2ST\7\4\2\2TU\5L\'\2U\3\3\2\2\2VW\7:\2"+
		"\2WX\7\5\2\2XY\5:\36\2YZ\7\6\2\2Z[\5:\36\2[\\\7\7\2\2\\]\b\3\1\2]^\7\5"+
		"\2\2^_\5\34\17\2_`\7\7\2\2`d\7\3\2\2ac\5\16\b\2ba\3\2\2\2cf\3\2\2\2db"+
		"\3\2\2\2de\3\2\2\2eh\3\2\2\2fd\3\2\2\2gi\5\b\5\2hg\3\2\2\2hi\3\2\2\2i"+
		"j\3\2\2\2jl\5\n\6\2km\5\f\7\2lk\3\2\2\2lm\3\2\2\2mn\3\2\2\2no\7\4\2\2"+
		"o\5\3\2\2\2pq\7C\2\2qr\7\5\2\2rs\5J&\2st\7\7\2\2tu\b\4\1\2uy\7\3\2\2v"+
		"x\5<\37\2wv\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z}\3\2\2\2{y\3\2\2\2"+
		"|~\5@!\2}|\3\2\2\2}~\3\2\2\2~\u0082\3\2\2\2\177\u0081\5D#\2\u0080\177"+
		"\3\2\2\2\u0081\u0084\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083"+
		"\u0085\3\2\2\2\u0084\u0082\3\2\2\2\u0085\u0086\7\4\2\2\u0086\7\3\2\2\2"+
		"\u0087\u0088\7<\2\2\u0088\u0089\b\5\1\2\u0089\u008a\7\3\2\2\u008a\u008b"+
		"\5\22\n\2\u008b\u008c\7\4\2\2\u008c\t\3\2\2\2\u008d\u008e\7=\2\2\u008e"+
		"\u008f\7\5\2\2\u008f\u0090\7H\2\2\u0090\u0091\7\6\2\2\u0091\u0092\7H\2"+
		"\2\u0092\u0093\7\7\2\2\u0093\u0094\7\b\2\2\u0094\u0095\5 \21\2\u0095\u0096"+
		"\7\t\2\2\u0096\u0097\b\6\1\2\u0097\u0098\7\3\2\2\u0098\u0099\5\24\13\2"+
		"\u0099\u009a\7\4\2\2\u009a\13\3\2\2\2\u009b\u009c\7>\2\2\u009c\u009d\b"+
		"\7\1\2\u009d\u009e\7\3\2\2\u009e\u009f\5\26\f\2\u009f\u00a0\7\4\2\2\u00a0"+
		"\r\3\2\2\2\u00a1\u00a2\7;\2\2\u00a2\u00a3\7L\2\2\u00a3\u00a4\7\5\2\2\u00a4"+
		"\u00a5\5:\36\2\u00a5\u00a6\7\7\2\2\u00a6\u00a7\b\b\1\2\u00a7\u00ab\7\3"+
		"\2\2\u00a8\u00aa\5\20\t\2\u00a9\u00a8\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab"+
		"\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ae\3\2\2\2\u00ad\u00ab\3\2"+
		"\2\2\u00ae\u00af\7\4\2\2\u00af\17\3\2\2\2\u00b0\u00b1\7I\2\2\u00b1\u00b2"+
		"\7\b\2\2\u00b2\u00b3\5:\36\2\u00b3\u00b4\7\t\2\2\u00b4\u00b5\7\n\2\2\u00b5"+
		"\u00b6\b\t\1\2\u00b6\u00cc\3\2\2\2\u00b7\u00b8\7J\2\2\u00b8\u00b9\7\b"+
		"\2\2\u00b9\u00ba\5:\36\2\u00ba\u00bb\7\6\2\2\u00bb\u00bc\5:\36\2\u00bc"+
		"\u00bd\7\t\2\2\u00bd\u00be\7\n\2\2\u00be\u00bf\b\t\1\2\u00bf\u00cc\3\2"+
		"\2\2\u00c0\u00c1\7K\2\2\u00c1\u00c2\7\b\2\2\u00c2\u00c3\5:\36\2\u00c3"+
		"\u00c4\7\6\2\2\u00c4\u00c5\5:\36\2\u00c5\u00c6\7\6\2\2\u00c6\u00c7\5:"+
		"\36\2\u00c7\u00c8\7\t\2\2\u00c8\u00c9\7\n\2\2\u00c9\u00ca\b\t\1\2\u00ca"+
		"\u00cc\3\2\2\2\u00cb\u00b0\3\2\2\2\u00cb\u00b7\3\2\2\2\u00cb\u00c0\3\2"+
		"\2\2\u00cc\21\3\2\2\2\u00cd\u00d1\b\n\1\2\u00ce\u00d0\5\30\r\2\u00cf\u00ce"+
		"\3\2\2\2\u00d0\u00d3\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2"+
		"\u00d4\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d4\u00d5\b\n\1\2\u00d5\23\3\2\2"+
		"\2\u00d6\u00da\b\13\1\2\u00d7\u00d9\5\30\r\2\u00d8\u00d7\3\2\2\2\u00d9"+
		"\u00dc\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dd\3\2"+
		"\2\2\u00dc\u00da\3\2\2\2\u00dd\u00de\b\13\1\2\u00de\25\3\2\2\2\u00df\u00e3"+
		"\b\f\1\2\u00e0\u00e2\5\30\r\2\u00e1\u00e0\3\2\2\2\u00e2\u00e5\3\2\2\2"+
		"\u00e3\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e6\3\2\2\2\u00e5\u00e3"+
		"\3\2\2\2\u00e6\u00e7\b\f\1\2\u00e7\27\3\2\2\2\u00e8\u00e9\7L\2\2\u00e9"+
		"\u00ea\7\13\2\2\u00ea\u00ec\5*\26\2\u00eb\u00ed\7\n\2\2\u00ec\u00eb\3"+
		"\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\b\r\1\2\u00ef"+
		"\u012a\3\2\2\2\u00f0\u00f1\7@\2\2\u00f1\u00f2\7\b\2\2\u00f2\u00f3\5 \21"+
		"\2\u00f3\u00f4\7\t\2\2\u00f4\u00f5\7\3\2\2\u00f5\u00f9\b\r\1\2\u00f6\u00f8"+
		"\5\30\r\2\u00f7\u00f6\3\2\2\2\u00f8\u00fb\3\2\2\2\u00f9\u00f7\3\2\2\2"+
		"\u00f9\u00fa\3\2\2\2\u00fa\u00fc\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fc\u00fd"+
		"\7\4\2\2\u00fd\u00fe\b\r\1\2\u00fe\u00ff\7\f\2\2\u00ff\u0100\7\3\2\2\u0100"+
		"\u0104\b\r\1\2\u0101\u0103\5\30\r\2\u0102\u0101\3\2\2\2\u0103\u0106\3"+
		"\2\2\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0107\3\2\2\2\u0106"+
		"\u0104\3\2\2\2\u0107\u0108\7\4\2\2\u0108\u0109\b\r\1\2\u0109\u012a\3\2"+
		"\2\2\u010a\u010b\7@\2\2\u010b\u010c\7\b\2\2\u010c\u010d\5 \21\2\u010d"+
		"\u010e\7\t\2\2\u010e\u010f\7\3\2\2\u010f\u0113\b\r\1\2\u0110\u0112\5\30"+
		"\r\2\u0111\u0110\3\2\2\2\u0112\u0115\3\2\2\2\u0113\u0111\3\2\2\2\u0113"+
		"\u0114\3\2\2\2\u0114\u0116\3\2\2\2\u0115\u0113\3\2\2\2\u0116\u0117\7\4"+
		"\2\2\u0117\u0118\b\r\1\2\u0118\u012a\3\2\2\2\u0119\u011a\7@\2\2\u011a"+
		"\u011b\7\b\2\2\u011b\u011c\5 \21\2\u011c\u011d\7\t\2\2\u011d\u011e\b\r"+
		"\1\2\u011e\u0120\5\30\r\2\u011f\u0121\7\n\2\2\u0120\u011f\3\2\2\2\u0120"+
		"\u0121\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0123\b\r\1\2\u0123\u012a\3\2"+
		"\2\2\u0124\u0126\7A\2\2\u0125\u0127\7\n\2\2\u0126\u0125\3\2\2\2\u0126"+
		"\u0127\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u012a\b\r\1\2\u0129\u00e8\3\2"+
		"\2\2\u0129\u00f0\3\2\2\2\u0129\u010a\3\2\2\2\u0129\u0119\3\2\2\2\u0129"+
		"\u0124\3\2\2\2\u012a\31\3\2\2\2\u012b\u012c\7\r\2\2\u012c\u012d\7L\2\2"+
		"\u012d\u0134\b\16\1\2\u012e\u012f\7\16\2\2\u012f\u0130\7L\2\2\u0130\u0134"+
		"\b\16\1\2\u0131\u0132\7L\2\2\u0132\u0134\b\16\1\2\u0133\u012b\3\2\2\2"+
		"\u0133\u012e\3\2\2\2\u0133\u0131\3\2\2\2\u0134\33\3\2\2\2\u0135\u0136"+
		"\b\17\1\2\u0136\u0137\5\32\16\2\u0137\u013d\3\2\2\2\u0138\u0139\f\3\2"+
		"\2\u0139\u013a\7\6\2\2\u013a\u013c\5\32\16\2\u013b\u0138\3\2\2\2\u013c"+
		"\u013f\3\2\2\2\u013d\u013b\3\2\2\2\u013d\u013e\3\2\2\2\u013e\35\3\2\2"+
		"\2\u013f\u013d\3\2\2\2\u0140\u0141\5*\26\2\u0141\u0142\t\2\2\2\u0142\u0143"+
		"\5*\26\2\u0143\u0144\b\20\1\2\u0144\u0157\3\2\2\2\u0145\u0146\7L\2\2\u0146"+
		"\u0147\7\24\2\2\u0147\u0148\5*\26\2\u0148\u0149\b\20\1\2\u0149\u0157\3"+
		"\2\2\2\u014a\u014b\7L\2\2\u014b\u014c\7\25\2\2\u014c\u014d\5*\26\2\u014d"+
		"\u014e\b\20\1\2\u014e\u0157\3\2\2\2\u014f\u0150\7B\2\2\u0150\u0157\b\20"+
		"\1\2\u0151\u0152\7\b\2\2\u0152\u0153\5 \21\2\u0153\u0154\7\t\2\2\u0154"+
		"\u0155\b\20\1\2\u0155\u0157\3\2\2\2\u0156\u0140\3\2\2\2\u0156\u0145\3"+
		"\2\2\2\u0156\u014a\3\2\2\2\u0156\u014f\3\2\2\2\u0156\u0151\3\2\2\2\u0157"+
		"\37\3\2\2\2\u0158\u0159\b\21\1\2\u0159\u015a\5\36\20\2\u015a\u015b\b\21"+
		"\1\2\u015b\u0160\3\2\2\2\u015c\u015d\5\"\22\2\u015d\u015e\b\21\1\2\u015e"+
		"\u0160\3\2\2\2\u015f\u0158\3\2\2\2\u015f\u015c\3\2\2\2\u0160\u0168\3\2"+
		"\2\2\u0161\u0162\f\3\2\2\u0162\u0163\7\26\2\2\u0163\u0164\5\"\22\2\u0164"+
		"\u0165\b\21\1\2\u0165\u0167\3\2\2\2\u0166\u0161\3\2\2\2\u0167\u016a\3"+
		"\2\2\2\u0168\u0166\3\2\2\2\u0168\u0169\3\2\2\2\u0169!\3\2\2\2\u016a\u0168"+
		"\3\2\2\2\u016b\u016c\b\22\1\2\u016c\u016d\5\36\20\2\u016d\u016e\b\22\1"+
		"\2\u016e\u0173\3\2\2\2\u016f\u0170\5$\23\2\u0170\u0171\b\22\1\2\u0171"+
		"\u0173\3\2\2\2\u0172\u016b\3\2\2\2\u0172\u016f\3\2\2\2\u0173\u017b\3\2"+
		"\2\2\u0174\u0175\f\3\2\2\u0175\u0176\7\27\2\2\u0176\u0177\5$\23\2\u0177"+
		"\u0178\b\22\1\2\u0178\u017a\3\2\2\2\u0179\u0174\3\2\2\2\u017a\u017d\3"+
		"\2\2\2\u017b\u0179\3\2\2\2\u017b\u017c\3\2\2\2\u017c#\3\2\2\2\u017d\u017b"+
		"\3\2\2\2\u017e\u017f\b\23\1\2\u017f\u0180\5\36\20\2\u0180\u0181\b\23\1"+
		"\2\u0181\u0186\3\2\2\2\u0182\u0183\5&\24\2\u0183\u0184\b\23\1\2\u0184"+
		"\u0186\3\2\2\2\u0185\u017e\3\2\2\2\u0185\u0182\3\2\2\2\u0186\u018e\3\2"+
		"\2\2\u0187\u0188\f\3\2\2\u0188\u0189\7\30\2\2\u0189\u018a\5&\24\2\u018a"+
		"\u018b\b\23\1\2\u018b\u018d\3\2\2\2\u018c\u0187\3\2\2\2\u018d\u0190\3"+
		"\2\2\2\u018e\u018c\3\2\2\2\u018e\u018f\3\2\2\2\u018f%\3\2\2\2\u0190\u018e"+
		"\3\2\2\2\u0191\u0192\5\36\20\2\u0192\u0193\b\24\1\2\u0193\u0199\3\2\2"+
		"\2\u0194\u0195\7\31\2\2\u0195\u0196\5&\24\2\u0196\u0197\b\24\1\2\u0197"+
		"\u0199\3\2\2\2\u0198\u0191\3\2\2\2\u0198\u0194\3\2\2\2\u0199\'\3\2\2\2"+
		"\u019a\u019b\5\64\33\2\u019b\u019c\b\25\1\2\u019c\u01bd\3\2\2\2\u019d"+
		"\u019e\5\66\34\2\u019e\u019f\b\25\1\2\u019f\u01bd\3\2\2\2\u01a0\u01a1"+
		"\58\35\2\u01a1\u01a2\b\25\1\2\u01a2\u01bd\3\2\2\2\u01a3\u01a4\5\62\32"+
		"\2\u01a4\u01a5\b\25\1\2\u01a5\u01bd\3\2\2\2\u01a6\u01a7\7\b\2\2\u01a7"+
		"\u01a8\5*\26\2\u01a8\u01a9\7\t\2\2\u01a9\u01aa\b\25\1\2\u01aa\u01bd\3"+
		"\2\2\2\u01ab\u01ac\7\26\2\2\u01ac\u01ad\5*\26\2\u01ad\u01ae\7\26\2\2\u01ae"+
		"\u01af\b\25\1\2\u01af\u01bd\3\2\2\2\u01b0\u01b1\7\17\2\2\u01b1\u01b2\5"+
		"*\26\2\u01b2\u01b3\7\20\2\2\u01b3\u01b4\b\25\1\2\u01b4\u01bd\3\2\2\2\u01b5"+
		"\u01b6\7\17\2\2\u01b6\u01b7\5*\26\2\u01b7\u01b8\7\6\2\2\u01b8\u01b9\5"+
		"*\26\2\u01b9\u01ba\7\20\2\2\u01ba\u01bb\b\25\1\2\u01bb\u01bd\3\2\2\2\u01bc"+
		"\u019a\3\2\2\2\u01bc\u019d\3\2\2\2\u01bc\u01a0\3\2\2\2\u01bc\u01a3\3\2"+
		"\2\2\u01bc\u01a6\3\2\2\2\u01bc\u01ab\3\2\2\2\u01bc\u01b0\3\2\2\2\u01bc"+
		"\u01b5\3\2\2\2\u01bd)\3\2\2\2\u01be\u01bf\b\26\1\2\u01bf\u01c0\5,\27\2"+
		"\u01c0\u01c1\7\32\2\2\u01c1\u01c2\5*\26\4\u01c2\u01c3\b\26\1\2\u01c3\u01ce"+
		"\3\2\2\2\u01c4\u01c5\5(\25\2\u01c5\u01c6\b\26\1\2\u01c6\u01ce\3\2\2\2"+
		"\u01c7\u01c8\5:\36\2\u01c8\u01c9\b\26\1\2\u01c9\u01ce\3\2\2\2\u01ca\u01cb"+
		"\5,\27\2\u01cb\u01cc\b\26\1\2\u01cc\u01ce\3\2\2\2\u01cd\u01be\3\2\2\2"+
		"\u01cd\u01c4\3\2\2\2\u01cd\u01c7\3\2\2\2\u01cd\u01ca\3\2\2\2\u01ce\u01db"+
		"\3\2\2\2\u01cf\u01d0\f\5\2\2\u01d0\u01d1\7\32\2\2\u01d1\u01d2\5,\27\2"+
		"\u01d2\u01d3\b\26\1\2\u01d3\u01da\3\2\2\2\u01d4\u01d5\f\3\2\2\u01d5\u01d6"+
		"\7\33\2\2\u01d6\u01d7\5,\27\2\u01d7\u01d8\b\26\1\2\u01d8\u01da\3\2\2\2"+
		"\u01d9\u01cf\3\2\2\2\u01d9\u01d4\3\2\2\2\u01da\u01dd\3\2\2\2\u01db\u01d9"+
		"\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc+\3\2\2\2\u01dd\u01db\3\2\2\2\u01de"+
		"\u01df\b\27\1\2\u01df\u01e0\7\33\2\2\u01e0\u01e1\5,\27\6\u01e1\u01e2\b"+
		"\27\1\2\u01e2\u01f5\3\2\2\2\u01e3\u01e4\7\32\2\2\u01e4\u01e5\5,\27\5\u01e5"+
		"\u01e6\b\27\1\2\u01e6\u01f5\3\2\2\2\u01e7\u01e9\7\35\2\2\u01e8\u01ea\7"+
		"\34\2\2\u01e9\u01e8\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01eb\3\2\2\2\u01eb"+
		"\u01ec\5,\27\4\u01ec\u01ed\b\27\1\2\u01ed\u01f5\3\2\2\2\u01ee\u01ef\5"+
		"(\25\2\u01ef\u01f0\b\27\1\2\u01f0\u01f5\3\2\2\2\u01f1\u01f2\5.\30\2\u01f2"+
		"\u01f3\b\27\1\2\u01f3\u01f5\3\2\2\2\u01f4\u01de\3\2\2\2\u01f4\u01e3\3"+
		"\2\2\2\u01f4\u01e7\3\2\2\2\u01f4\u01ee\3\2\2\2\u01f4\u01f1\3\2\2\2\u01f5"+
		"\u0203\3\2\2\2\u01f6\u01f7\f\7\2\2\u01f7\u01f8\7\34\2\2\u01f8\u01f9\5"+
		",\27\b\u01f9\u01fa\b\27\1\2\u01fa\u0202\3\2\2\2\u01fb\u01fd\f\3\2\2\u01fc"+
		"\u01fe\7\34\2\2\u01fd\u01fc\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe\u01ff\3"+
		"\2\2\2\u01ff\u0200\7\35\2\2\u0200\u0202\b\27\1\2\u0201\u01f6\3\2\2\2\u0201"+
		"\u01fb\3\2\2\2\u0202\u0205\3\2\2\2\u0203\u0201\3\2\2\2\u0203\u0204\3\2"+
		"\2\2\u0204-\3\2\2\2\u0205\u0203\3\2\2\2\u0206\u0207\b\30\1\2\u0207\u0208"+
		"\5(\25\2\u0208\u0209\b\30\1\2\u0209\u020e\3\2\2\2\u020a\u020b\5\60\31"+
		"\2\u020b\u020c\b\30\1\2\u020c\u020e\3\2\2\2\u020d\u0206\3\2\2\2\u020d"+
		"\u020a\3\2\2\2\u020e\u0216\3\2\2\2\u020f\u0210\f\3\2\2\u0210\u0211\7\36"+
		"\2\2\u0211\u0212\5.\30\4\u0212\u0213\b\30\1\2\u0213\u0215\3\2\2\2\u0214"+
		"\u020f\3\2\2\2\u0215\u0218\3\2\2\2\u0216\u0214\3\2\2\2\u0216\u0217\3\2"+
		"\2\2\u0217/\3\2\2\2\u0218\u0216\3\2\2\2\u0219\u021a\b\31\1\2\u021a\u021b"+
		"\5(\25\2\u021b\u021c\b\31\1\2\u021c\u0224\3\2\2\2\u021d\u021e\f\3\2\2"+
		"\u021e\u021f\7\27\2\2\u021f\u0220\5\60\31\4\u0220\u0221\b\31\1\2\u0221"+
		"\u0223\3\2\2\2\u0222\u021d\3\2\2\2\u0223\u0226\3\2\2\2\u0224\u0222\3\2"+
		"\2\2\u0224\u0225\3\2\2\2\u0225\61\3\2\2\2\u0226\u0224\3\2\2\2\u0227\u0228"+
		"\t\3\2\2\u0228\u0229\7\b\2\2\u0229\u022a\5*\26\2\u022a\u022b\7\t\2\2\u022b"+
		"\u022c\b\32\1\2\u022c\u0242\3\2\2\2\u022d\u022e\t\4\2\2\u022e\u022f\7"+
		"\b\2\2\u022f\u0230\5*\26\2\u0230\u0231\7\t\2\2\u0231\u0232\b\32\1\2\u0232"+
		"\u0242\3\2\2\2\u0233\u0234\t\5\2\2\u0234\u0235\7\b\2\2\u0235\u0236\5*"+
		"\26\2\u0236\u0237\7\t\2\2\u0237\u0238\b\32\1\2\u0238\u0242\3\2\2\2\u0239"+
		"\u023a\t\6\2\2\u023a\u023b\7\b\2\2\u023b\u023c\5*\26\2\u023c\u023d\7\6"+
		"\2\2\u023d\u023e\5*\26\2\u023e\u023f\7\t\2\2\u023f\u0240\b\32\1\2\u0240"+
		"\u0242\3\2\2\2\u0241\u0227\3\2\2\2\u0241\u022d\3\2\2\2\u0241\u0233\3\2"+
		"\2\2\u0241\u0239\3\2\2\2\u0242\63\3\2\2\2\u0243\u0244\7\65\2\2\u0244\u024a"+
		"\b\33\1\2\u0245\u0246\7\66\2\2\u0246\u024a\b\33\1\2\u0247\u0248\7\67\2"+
		"\2\u0248\u024a\b\33\1\2\u0249\u0243\3\2\2\2\u0249\u0245\3\2\2\2\u0249"+
		"\u0247\3\2\2\2\u024a\65\3\2\2\2\u024b\u024c\7L\2\2\u024c\u024d\b\34\1"+
		"\2\u024d\67\3\2\2\2\u024e\u0250\7\32\2\2\u024f\u024e\3\2\2\2\u024f\u0250"+
		"\3\2\2\2\u0250\u0251\3\2\2\2\u0251\u0252\t\7\2\2\u0252\u0257\b\35\1\2"+
		"\u0253\u0254\7\33\2\2\u0254\u0255\t\7\2\2\u0255\u0257\b\35\1\2\u0256\u024f"+
		"\3\2\2\2\u0256\u0253\3\2\2\2\u02579\3\2\2\2\u0258\u025a\7\17\2\2\u0259"+
		"\u025b\7\32\2\2\u025a\u0259\3\2\2\2\u025a\u025b\3\2\2\2\u025b\u025c\3"+
		"\2\2\2\u025c\u025d\t\7\2\2\u025d\u025f\7\6\2\2\u025e\u0260\7\32\2\2\u025f"+
		"\u025e\3\2\2\2\u025f\u0260\3\2\2\2\u0260\u0261\3\2\2\2\u0261\u0262\t\7"+
		"\2\2\u0262\u0263\7\20\2\2\u0263\u02aa\b\36\1\2\u0264\u0266\7\17\2\2\u0265"+
		"\u0267\7\32\2\2\u0266\u0265\3\2\2\2\u0266\u0267\3\2\2\2\u0267\u0268\3"+
		"\2\2\2\u0268\u0269\t\7\2\2\u0269\u026a\7\6\2\2\u026a\u026b\7\33\2\2\u026b"+
		"\u026c\t\7\2\2\u026c\u026d\7\20\2\2\u026d\u02aa\b\36\1\2\u026e\u026f\7"+
		"\17\2\2\u026f\u0270\7\33\2\2\u0270\u0271\t\7\2\2\u0271\u0273\7\6\2\2\u0272"+
		"\u0274\7\32\2\2\u0273\u0272\3\2\2\2\u0273\u0274\3\2\2\2\u0274\u0275\3"+
		"\2\2\2\u0275\u0276\t\7\2\2\u0276\u0277\7\20\2\2\u0277\u02aa\b\36\1\2\u0278"+
		"\u0279\7\17\2\2\u0279\u027a\7\33\2\2\u027a\u027b\t\7\2\2\u027b\u027c\7"+
		"\6\2\2\u027c\u027d\7\33\2\2\u027d\u027e\t\7\2\2\u027e\u027f\7\20\2\2\u027f"+
		"\u02aa\b\36\1\2\u0280\u0282\7\32\2\2\u0281\u0280\3\2\2\2\u0281\u0282\3"+
		"\2\2\2\u0282\u0283\3\2\2\2\u0283\u0284\t\7\2\2\u0284\u0285\7\32\2\2\u0285"+
		"\u0286\t\7\2\2\u0286\u0287\7\35\2\2\u0287\u02aa\b\36\1\2\u0288\u028a\7"+
		"\32\2\2\u0289\u0288\3\2\2\2\u0289\u028a\3\2\2\2\u028a\u028b\3\2\2\2\u028b"+
		"\u028c\t\7\2\2\u028c\u028d\7\33\2\2\u028d\u028e\t\7\2\2\u028e\u028f\7"+
		"\35\2\2\u028f\u02aa\b\36\1\2\u0290\u0292\7\32\2\2\u0291\u0290\3\2\2\2"+
		"\u0291\u0292\3\2\2\2\u0292\u0293\3\2\2\2\u0293\u0294\t\7\2\2\u0294\u0295"+
		"\7\35\2\2\u0295\u02aa\b\36\1\2\u0296\u0297\7\33\2\2\u0297\u0298\t\7\2"+
		"\2\u0298\u0299\7\32\2\2\u0299\u029a\t\7\2\2\u029a\u029b\7\35\2\2\u029b"+
		"\u02aa\b\36\1\2\u029c\u029d\7\33\2\2\u029d\u029e\t\7\2\2\u029e\u029f\7"+
		"\33\2\2\u029f\u02a0\t\7\2\2\u02a0\u02a1\7\35\2\2\u02a1\u02aa\b\36\1\2"+
		"\u02a2\u02a3\7\33\2\2\u02a3\u02a4\t\7\2\2\u02a4\u02a5\7\35\2\2\u02a5\u02aa"+
		"\b\36\1\2\u02a6\u02a7\58\35\2\u02a7\u02a8\b\36\1\2\u02a8\u02aa\3\2\2\2"+
		"\u02a9\u0258\3\2\2\2\u02a9\u0264\3\2\2\2\u02a9\u026e\3\2\2\2\u02a9\u0278"+
		"\3\2\2\2\u02a9\u0281\3\2\2\2\u02a9\u0289\3\2\2\2\u02a9\u0291\3\2\2\2\u02a9"+
		"\u0296\3\2\2\2\u02a9\u029c\3\2\2\2\u02a9\u02a2\3\2\2\2\u02a9\u02a6\3\2"+
		"\2\2\u02aa;\3\2\2\2\u02ab\u02ac\7D\2\2\u02ac\u02ad\7L\2\2\u02ad\u02ae"+
		"\b\37\1\2\u02ae\u02b0\7\3\2\2\u02af\u02b1\5> \2\u02b0\u02af\3\2\2\2\u02b1"+
		"\u02b2\3\2\2\2\u02b2\u02b0\3\2\2\2\u02b2\u02b3\3\2\2\2\u02b3\u02b4\3\2"+
		"\2\2\u02b4\u02b5\7\4\2\2\u02b5=\3\2\2\2\u02b6\u02b7\7\5\2\2\u02b7\u02b8"+
		"\5J&\2\u02b8\u02b9\7\20\2\2\u02b9\u02ba\5J&\2\u02ba\u02bb\7\6\2\2\u02bb"+
		"\u02bc\7H\2\2\u02bc\u02bd\7\6\2\2\u02bd\u02be\5*\26\2\u02be\u02bf\7\7"+
		"\2\2\u02bf\u02c0\7\n\2\2\u02c0\u02c1\b \1\2\u02c1\u02cd\3\2\2\2\u02c2"+
		"\u02c3\7\5\2\2\u02c3\u02c4\5J&\2\u02c4\u02c5\7\20\2\2\u02c5\u02c6\5J&"+
		"\2\u02c6\u02c7\7\6\2\2\u02c7\u02c8\7H\2\2\u02c8\u02c9\7\7\2\2\u02c9\u02ca"+
		"\7\n\2\2\u02ca\u02cb\b \1\2\u02cb\u02cd\3\2\2\2\u02cc\u02b6\3\2\2\2\u02cc"+
		"\u02c2\3\2\2\2\u02cd?\3\2\2\2\u02ce\u02cf\7?\2\2\u02cf\u02d0\b!\1\2\u02d0"+
		"\u02d1\7\3\2\2\u02d1\u02d2\5B\"\2\u02d2\u02d3\7\4\2\2\u02d3A\3\2\2\2\u02d4"+
		"\u02d8\b\"\1\2\u02d5\u02d7\5\30\r\2\u02d6\u02d5\3\2\2\2\u02d7\u02da\3"+
		"\2\2\2\u02d8\u02d6\3\2\2\2\u02d8\u02d9\3\2\2\2\u02d9\u02db\3\2\2\2\u02da"+
		"\u02d8\3\2\2\2\u02db\u02dc\b\"\1\2\u02dcC\3\2\2\2\u02dd\u02de\7E\2\2\u02de"+
		"\u02df\7\b\2\2\u02df\u02e0\5F$\2\u02e0\u02e1\7\t\2\2\u02e1\u02e2\7\5\2"+
		"\2\u02e2\u02e3\t\7\2\2\u02e3\u02e4\7\7\2\2\u02e4\u02e5\7\3\2\2\u02e5\u02e6"+
		"\5H%\2\u02e6\u02e7\7\4\2\2\u02e7\u02e8\b#\1\2\u02e8E\3\2\2\2\u02e9\u02ea"+
		"\b$\1\2\u02ea\u02eb\5*\26\2\u02eb\u02ec\t\2\2\2\u02ec\u02ed\5*\26\2\u02ed"+
		"\u02ee\b$\1\2\u02ee\u02f6\3\2\2\2\u02ef\u02f0\f\3\2\2\u02f0\u02f1\t\b"+
		"\2\2\u02f1\u02f2\5F$\4\u02f2\u02f3\b$\1\2\u02f3\u02f5\3\2\2\2\u02f4\u02ef"+
		"\3\2\2\2\u02f5\u02f8\3\2\2\2\u02f6\u02f4\3\2\2\2\u02f6\u02f7\3\2\2\2\u02f7"+
		"G\3\2\2\2\u02f8\u02f6\3\2\2\2\u02f9\u02fa\5*\26\2\u02fa\u02fb\b%\1\2\u02fb"+
		"\u0313\3\2\2\2\u02fc\u02fd\5*\26\2\u02fd\u02fe\7\6\2\2\u02fe\u02ff\5*"+
		"\26\2\u02ff\u0300\7\6\2\2\u0300\u0301\5*\26\2\u0301\u0302\b%\1\2\u0302"+
		"\u0313\3\2\2\2\u0303\u0304\5*\26\2\u0304\u0305\7\6\2\2\u0305\u0306\5*"+
		"\26\2\u0306\u0307\7\6\2\2\u0307\u0308\5*\26\2\u0308\u0309\7\6\2\2\u0309"+
		"\u030a\5*\26\2\u030a\u030b\b%\1\2\u030b\u0313\3\2\2\2\u030c\u030d\7L\2"+
		"\2\u030d\u030e\7\5\2\2\u030e\u030f\5*\26\2\u030f\u0310\7\7\2\2\u0310\u0311"+
		"\b%\1\2\u0311\u0313\3\2\2\2\u0312\u02f9\3\2\2\2\u0312\u02fc\3\2\2\2\u0312"+
		"\u0303\3\2\2\2\u0312\u030c\3\2\2\2\u0313I\3\2\2\2\u0314\u0315\7\b\2\2"+
		"\u0315\u0316\t\7\2\2\u0316\u0317\7\6\2\2\u0317\u0318\t\7\2\2\u0318\u0319"+
		"\7\6\2\2\u0319\u031a\t\7\2\2\u031a\u031b\7\6\2\2\u031b\u031c\t\7\2\2\u031c"+
		"\u031d\7\t\2\2\u031d\u0322\b&\1\2\u031e\u031f\78\2\2\u031f\u0320\7F\2"+
		"\2\u0320\u0322\b&\1\2\u0321\u0314\3\2\2\2\u0321\u031e\3\2\2\2\u0322K\3"+
		"\2\2\2\u0323\u0324\7\2\2\3\u0324M\3\2\2\2<dhly}\u0082\u00ab\u00cb\u00d1"+
		"\u00da\u00e3\u00ec\u00f9\u0104\u0113\u0120\u0126\u0129\u0133\u013d\u0156"+
		"\u015f\u0168\u0172\u017b\u0185\u018e\u0198\u01bc\u01cd\u01d9\u01db\u01e9"+
		"\u01f4\u01fd\u0201\u0203\u020d\u0216\u0224\u0241\u0249\u024f\u0256\u025a"+
		"\u025f\u0266\u0273\u0281\u0289\u0291\u02a9\u02b2\u02cc\u02d8\u02f6\u0312"+
		"\u0321";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}