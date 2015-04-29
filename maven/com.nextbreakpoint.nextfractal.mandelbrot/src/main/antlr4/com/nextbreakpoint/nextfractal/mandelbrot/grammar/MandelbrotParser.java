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
		T__50=1, T__49=2, T__48=3, T__47=4, T__46=5, T__45=6, T__44=7, T__43=8, 
		T__42=9, T__41=10, T__40=11, T__39=12, T__38=13, T__37=14, T__36=15, T__35=16, 
		T__34=17, T__33=18, T__32=19, T__31=20, T__30=21, T__29=22, T__28=23, 
		T__27=24, T__26=25, T__25=26, T__24=27, T__23=28, T__22=29, T__21=30, 
		T__20=31, T__19=32, T__18=33, T__17=34, T__16=35, T__15=36, T__14=37, 
		T__13=38, T__12=39, T__11=40, T__10=41, T__9=42, T__8=43, T__7=44, T__6=45, 
		T__5=46, T__4=47, T__3=48, T__2=49, T__1=50, T__0=51, FRACTAL=52, ORBIT=53, 
		TRAP=54, BEGIN=55, LOOP=56, END=57, INIT=58, IF=59, STOP=60, JULIA=61, 
		COLOR=62, PALETTE=63, RULE=64, ARGB=65, RATIONAL=66, INTEGER=67, PATHOP_1POINTS=68, 
		PATHOP_2POINTS=69, VARIABLE=70, COMMENT=71, WHITESPACE=72;
	public static final String[] tokenNames = {
		"<INVALID>", "'cos'", "'{'", "'='", "'asin'", "'^'", "'im'", "'('", "'min'", 
		"','", "'pha'", "'re'", "'atan'", "'sqrt'", "'pi'", "'ceil'", "'mod'", 
		"'>='", "'log'", "'<'", "']'", "'~'", "'abs'", "'<>'", "'#'", "'e'", "'floor'", 
		"'i'", "'+'", "'/'", "'2pi'", "';'", "'max'", "'}'", "'mod2'", "'?'", 
		"'sin'", "'<='", "'pow'", "'~?'", "'&'", "'*'", "'tan'", "'atan2'", "'['", 
		"'|'", "'>'", "'acos'", "')'", "'exp'", "'hypot'", "'-'", "'fractal'", 
		"'orbit'", "'trap'", "'begin'", "'loop'", "'end'", "'init'", "'if'", "'stop'", 
		"'julia'", "'color'", "'palette'", "'rule'", "ARGB", "RATIONAL", "INTEGER", 
		"PATHOP_1POINTS", "PATHOP_2POINTS", "VARIABLE", "COMMENT", "WHITESPACE"
	};
	public static final int
		RULE_fractal = 0, RULE_orbit = 1, RULE_color = 2, RULE_begin = 3, RULE_loop = 4, 
		RULE_end = 5, RULE_trap = 6, RULE_pathop = 7, RULE_beginstatement = 8, 
		RULE_loopstatement = 9, RULE_endstatement = 10, RULE_statement = 11, RULE_variablelist = 12, 
		RULE_conditionexp = 13, RULE_conditionexp2 = 14, RULE_conditionexp3 = 15, 
		RULE_conditionexp4 = 16, RULE_expression = 17, RULE_expression2 = 18, 
		RULE_expression3 = 19, RULE_expression4 = 20, RULE_function = 21, RULE_constant = 22, 
		RULE_variable = 23, RULE_real = 24, RULE_complex = 25, RULE_palette = 26, 
		RULE_paletteelement = 27, RULE_colorinit = 28, RULE_colorstatement = 29, 
		RULE_colorrule = 30, RULE_ruleexp = 31, RULE_colorexp = 32, RULE_colorargb = 33, 
		RULE_eof = 34;
	public static final String[] ruleNames = {
		"fractal", "orbit", "color", "begin", "loop", "end", "trap", "pathop", 
		"beginstatement", "loopstatement", "endstatement", "statement", "variablelist", 
		"conditionexp", "conditionexp2", "conditionexp3", "conditionexp4", "expression", 
		"expression2", "expression3", "expression4", "function", "constant", "variable", 
		"real", "complex", "palette", "paletteelement", "colorinit", "colorstatement", 
		"colorrule", "ruleexp", "colorexp", "colorargb", "eof"
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
			setState(70); ((FractalContext)_localctx).f = match(FRACTAL);

					builder.setFractal(new ASTFractal(((FractalContext)_localctx).f));
				
			setState(72); match(2);
			setState(73); orbit();
			setState(74); color();
			setState(75); match(33);
			setState(76); eof();
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
			setState(78); ((OrbitContext)_localctx).o = match(ORBIT);
			setState(79); match(44);
			setState(80); ((OrbitContext)_localctx).ra = complex();
			setState(81); match(9);
			setState(82); ((OrbitContext)_localctx).rb = complex();
			setState(83); match(20);

					builder.setOrbit(new ASTOrbit(((OrbitContext)_localctx).o, new ASTRegion(((OrbitContext)_localctx).ra.result, ((OrbitContext)_localctx).rb.result)));
				
			setState(85); match(44);
			setState(86); ((OrbitContext)_localctx).v = variablelist(0);
			setState(87); match(20);
			setState(88); match(2);
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TRAP) {
				{
				{
				setState(89); trap();
				}
				}
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(96);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(95); begin();
				}
			}

			setState(98); loop();
			setState(100);
			_la = _input.LA(1);
			if (_la==END) {
				{
				setState(99); end();
				}
			}

			setState(102); match(33);
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
			setState(104); ((ColorContext)_localctx).c = match(COLOR);
			setState(105); match(44);
			setState(106); ((ColorContext)_localctx).argb = colorargb();
			setState(107); match(20);
			 
					builder.setColor(new ASTColor(((ColorContext)_localctx).c, ((ColorContext)_localctx).argb.result));
				
			setState(109); match(2);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PALETTE) {
				{
				{
				setState(110); palette();
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(117);
			_la = _input.LA(1);
			if (_la==INIT) {
				{
				setState(116); colorinit();
				}
			}

			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RULE) {
				{
				{
				setState(119); colorrule();
				}
				}
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(125); match(33);
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
			setState(127); ((BeginContext)_localctx).b = match(BEGIN);
			 
					builder.setOrbitBegin(new ASTOrbitBegin(((BeginContext)_localctx).b));
				
			setState(129); match(2);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 59)) & ~0x3f) == 0 && ((1L << (_la - 59)) & ((1L << (IF - 59)) | (1L << (STOP - 59)) | (1L << (VARIABLE - 59)))) != 0)) {
				{
				{
				setState(130); beginstatement();
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(136); match(33);
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
			setState(138); ((LoopContext)_localctx).l = match(LOOP);
			setState(139); match(44);
			setState(140); ((LoopContext)_localctx).lb = match(INTEGER);
			setState(141); match(9);
			setState(142); ((LoopContext)_localctx).le = match(INTEGER);
			setState(143); match(20);
			setState(144); match(7);
			setState(145); ((LoopContext)_localctx).e = conditionexp(0);
			setState(146); match(48);

					builder.setOrbitLoop(new ASTOrbitLoop(((LoopContext)_localctx).l, builder.parseInt((((LoopContext)_localctx).lb!=null?((LoopContext)_localctx).lb.getText():null)), builder.parseInt((((LoopContext)_localctx).le!=null?((LoopContext)_localctx).le.getText():null)), ((LoopContext)_localctx).e.result));
				
			setState(148); match(2);
			setState(152);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 59)) & ~0x3f) == 0 && ((1L << (_la - 59)) & ((1L << (IF - 59)) | (1L << (STOP - 59)) | (1L << (VARIABLE - 59)))) != 0)) {
				{
				{
				setState(149); loopstatement();
				}
				}
				setState(154);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(155); match(33);
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
			setState(157); ((EndContext)_localctx).e = match(END);

					builder.setOrbitEnd(new ASTOrbitEnd(((EndContext)_localctx).e));		
				
			setState(159); match(2);
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 59)) & ~0x3f) == 0 && ((1L << (_la - 59)) & ((1L << (IF - 59)) | (1L << (STOP - 59)) | (1L << (VARIABLE - 59)))) != 0)) {
				{
				{
				setState(160); endstatement();
				}
				}
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(166); match(33);
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
			setState(168); ((TrapContext)_localctx).t = match(TRAP);
			setState(169); ((TrapContext)_localctx).n = match(VARIABLE);
			setState(170); match(44);
			setState(171); ((TrapContext)_localctx).c = complex();
			setState(172); match(20);

					builder.addOrbitTrap(new ASTOrbitTrap(((TrapContext)_localctx).t, (((TrapContext)_localctx).n!=null?((TrapContext)_localctx).n.getText():null), ((TrapContext)_localctx).c.result));
				
			setState(174); match(2);
			setState(178);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PATHOP_1POINTS || _la==PATHOP_2POINTS) {
				{
				{
				setState(175); pathop();
				}
				}
				setState(180);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(181); match(33);
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
			setState(199);
			switch (_input.LA(1)) {
			case PATHOP_1POINTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(183); ((PathopContext)_localctx).o = match(PATHOP_1POINTS);
				setState(184); match(7);
				setState(185); ((PathopContext)_localctx).c = complex();
				setState(186); match(48);
				setState(187); match(31);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c.result));
					
				}
				break;
			case PATHOP_2POINTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(190); ((PathopContext)_localctx).o = match(PATHOP_2POINTS);
				setState(191); match(7);
				setState(192); ((PathopContext)_localctx).c1 = complex();
				setState(193); match(9);
				setState(194); ((PathopContext)_localctx).c2 = complex();
				setState(195); match(48);
				setState(196); match(31);

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
				
			setState(202); statement();

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
				
			setState(206); statement();

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
				
			setState(210); statement();

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
			setState(241);
			switch (_input.LA(1)) {
			case VARIABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(213); ((StatementContext)_localctx).v = match(VARIABLE);
				setState(214); match(3);
				setState(215); ((StatementContext)_localctx).e = expression(0);
				setState(217);
				_la = _input.LA(1);
				if (_la==31) {
					{
					setState(216); match(31);
					}
				}


						builder.registerVariable((((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result.isReal(), ((StatementContext)_localctx).v);
						builder.appendStatement(new ASTAssignStatement(((StatementContext)_localctx).v, (((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result));
					
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(221); ((StatementContext)_localctx).f = match(IF);
				setState(222); match(7);
				setState(223); ((StatementContext)_localctx).c = conditionexp(0);
				setState(224); match(48);
				setState(225); match(2);

						builder.pushStatementList();
					
				setState(230);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 59)) & ~0x3f) == 0 && ((1L << (_la - 59)) & ((1L << (IF - 59)) | (1L << (STOP - 59)) | (1L << (VARIABLE - 59)))) != 0)) {
					{
					{
					setState(227); statement();
					}
					}
					setState(232);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(233); match(33);

						ASTStatementList list = builder.getStatementList();
						builder.popStatementList();
						builder.appendStatement(new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, list));
					
				}
				break;
			case STOP:
				enterOuterAlt(_localctx, 3);
				{
				setState(236); ((StatementContext)_localctx).t = match(STOP);
				setState(238);
				_la = _input.LA(1);
				if (_la==31) {
					{
					setState(237); match(31);
					}
				}


						builder.appendStatement(new ASTStopStatement(((StatementContext)_localctx).t));
					
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
			setState(244); ((VariablelistContext)_localctx).v = match(VARIABLE);

					builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(253);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VariablelistContext(_parentctx, _parentState);
					_localctx.vl = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_variablelist);
					setState(247);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(248); match(9);
					setState(249); ((VariablelistContext)_localctx).v = match(VARIABLE);

					          		builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
					          	
					}
					} 
				}
				setState(255);
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

	public static class ConditionexpContext extends ParserRuleContext {
		public ASTConditionExpression result;
		public ConditionexpContext c1;
		public ExpressionContext e1;
		public Token o;
		public ExpressionContext e2;
		public Token v;
		public ExpressionContext e;
		public Token t;
		public Token s;
		public ConditionexpContext c;
		public Conditionexp2Context c2;
		public Token l;
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public Conditionexp2Context conditionexp2() {
			return getRuleContext(Conditionexp2Context.class,0);
		}
		public TerminalNode JULIA() { return getToken(MandelbrotParser.JULIA, 0); }
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
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
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_conditionexp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(257); ((ConditionexpContext)_localctx).e1 = expression(0);
				setState(258);
				((ConditionexpContext)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 23) | (1L << 37) | (1L << 46))) != 0)) ) {
					((ConditionexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(259); ((ConditionexpContext)_localctx).e2 = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionCompareOp(((ConditionexpContext)_localctx).e1.result.getLocation(), (((ConditionexpContext)_localctx).o!=null?((ConditionexpContext)_localctx).o.getText():null), ((ConditionexpContext)_localctx).e1.result, ((ConditionexpContext)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(262); ((ConditionexpContext)_localctx).v = match(VARIABLE);
				setState(263); match(35);
				setState(264); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, true);
					
				}
				break;

			case 3:
				{
				setState(267); ((ConditionexpContext)_localctx).v = match(VARIABLE);
				setState(268); match(39);
				setState(269); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, false);
					
				}
				break;

			case 4:
				{
				setState(272); ((ConditionexpContext)_localctx).t = match(JULIA);

						((ConditionexpContext)_localctx).result =  new ASTConditionJulia(((ConditionexpContext)_localctx).t);
					
				}
				break;

			case 5:
				{
				setState(274); ((ConditionexpContext)_localctx).s = match(7);
				setState(275); ((ConditionexpContext)_localctx).c = conditionexp(0);
				setState(276); match(48);

						((ConditionexpContext)_localctx).result =  new ASTConditionParen(((ConditionexpContext)_localctx).s, ((ConditionexpContext)_localctx).c.result);
					
				}
				break;

			case 6:
				{
				setState(279); ((ConditionexpContext)_localctx).c2 = conditionexp2(0);

						((ConditionexpContext)_localctx).result =  ((ConditionexpContext)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(291);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConditionexpContext(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp);
					setState(284);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(285); ((ConditionexpContext)_localctx).l = match(45);
					setState(286); ((ConditionexpContext)_localctx).c2 = conditionexp2(0);

					          		((ConditionexpContext)_localctx).result =  new ASTConditionLogicOp(((ConditionexpContext)_localctx).c1.result.getLocation(), (((ConditionexpContext)_localctx).l!=null?((ConditionexpContext)_localctx).l.getText():null), ((ConditionexpContext)_localctx).c1.result, ((ConditionexpContext)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(293);
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

	public static class Conditionexp2Context extends ParserRuleContext {
		public ASTConditionExpression result;
		public Conditionexp2Context c1;
		public ExpressionContext e1;
		public Token o;
		public ExpressionContext e2;
		public Token v;
		public ExpressionContext e;
		public Token t;
		public Token s;
		public ConditionexpContext c;
		public Conditionexp3Context c2;
		public Token l;
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public Conditionexp3Context conditionexp3() {
			return getRuleContext(Conditionexp3Context.class,0);
		}
		public Conditionexp2Context conditionexp2() {
			return getRuleContext(Conditionexp2Context.class,0);
		}
		public TerminalNode JULIA() { return getToken(MandelbrotParser.JULIA, 0); }
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
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
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_conditionexp2, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(295); ((Conditionexp2Context)_localctx).e1 = expression(0);
				setState(296);
				((Conditionexp2Context)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 23) | (1L << 37) | (1L << 46))) != 0)) ) {
					((Conditionexp2Context)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(297); ((Conditionexp2Context)_localctx).e2 = expression(0);

						((Conditionexp2Context)_localctx).result =  new ASTConditionCompareOp(((Conditionexp2Context)_localctx).e1.result.getLocation(), (((Conditionexp2Context)_localctx).o!=null?((Conditionexp2Context)_localctx).o.getText():null), ((Conditionexp2Context)_localctx).e1.result, ((Conditionexp2Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(300); ((Conditionexp2Context)_localctx).v = match(VARIABLE);
				setState(301); match(35);
				setState(302); ((Conditionexp2Context)_localctx).e = expression(0);

						((Conditionexp2Context)_localctx).result =  new ASTConditionTrap(((Conditionexp2Context)_localctx).v, (((Conditionexp2Context)_localctx).v!=null?((Conditionexp2Context)_localctx).v.getText():null), ((Conditionexp2Context)_localctx).e.result, true);
					
				}
				break;

			case 3:
				{
				setState(305); ((Conditionexp2Context)_localctx).v = match(VARIABLE);
				setState(306); match(39);
				setState(307); ((Conditionexp2Context)_localctx).e = expression(0);

						((Conditionexp2Context)_localctx).result =  new ASTConditionTrap(((Conditionexp2Context)_localctx).v, (((Conditionexp2Context)_localctx).v!=null?((Conditionexp2Context)_localctx).v.getText():null), ((Conditionexp2Context)_localctx).e.result, false);
					
				}
				break;

			case 4:
				{
				setState(310); ((Conditionexp2Context)_localctx).t = match(JULIA);

						((Conditionexp2Context)_localctx).result =  new ASTConditionJulia(((Conditionexp2Context)_localctx).t);
					
				}
				break;

			case 5:
				{
				setState(312); ((Conditionexp2Context)_localctx).s = match(7);
				setState(313); ((Conditionexp2Context)_localctx).c = conditionexp(0);
				setState(314); match(48);

						((Conditionexp2Context)_localctx).result =  new ASTConditionParen(((Conditionexp2Context)_localctx).s, ((Conditionexp2Context)_localctx).c.result);
					
				}
				break;

			case 6:
				{
				setState(317); ((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

						((Conditionexp2Context)_localctx).result =  ((Conditionexp2Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(329);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Conditionexp2Context(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp2);
					setState(322);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(323); ((Conditionexp2Context)_localctx).l = match(5);
					setState(324); ((Conditionexp2Context)_localctx).c2 = conditionexp3(0);

					          		((Conditionexp2Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp2Context)_localctx).c1.result.getLocation(), (((Conditionexp2Context)_localctx).l!=null?((Conditionexp2Context)_localctx).l.getText():null), ((Conditionexp2Context)_localctx).c1.result, ((Conditionexp2Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(331);
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

	public static class Conditionexp3Context extends ParserRuleContext {
		public ASTConditionExpression result;
		public Conditionexp3Context c1;
		public ExpressionContext e1;
		public Token o;
		public ExpressionContext e2;
		public Token v;
		public ExpressionContext e;
		public Token t;
		public Token s;
		public ConditionexpContext c;
		public Conditionexp4Context c2;
		public Token l;
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public Conditionexp3Context conditionexp3() {
			return getRuleContext(Conditionexp3Context.class,0);
		}
		public TerminalNode JULIA() { return getToken(MandelbrotParser.JULIA, 0); }
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Conditionexp4Context conditionexp4() {
			return getRuleContext(Conditionexp4Context.class,0);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
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
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_conditionexp3, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(358);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(333); ((Conditionexp3Context)_localctx).e1 = expression(0);
				setState(334);
				((Conditionexp3Context)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 23) | (1L << 37) | (1L << 46))) != 0)) ) {
					((Conditionexp3Context)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(335); ((Conditionexp3Context)_localctx).e2 = expression(0);

						((Conditionexp3Context)_localctx).result =  new ASTConditionCompareOp(((Conditionexp3Context)_localctx).e1.result.getLocation(), (((Conditionexp3Context)_localctx).o!=null?((Conditionexp3Context)_localctx).o.getText():null), ((Conditionexp3Context)_localctx).e1.result, ((Conditionexp3Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(338); ((Conditionexp3Context)_localctx).v = match(VARIABLE);
				setState(339); match(35);
				setState(340); ((Conditionexp3Context)_localctx).e = expression(0);

						((Conditionexp3Context)_localctx).result =  new ASTConditionTrap(((Conditionexp3Context)_localctx).v, (((Conditionexp3Context)_localctx).v!=null?((Conditionexp3Context)_localctx).v.getText():null), ((Conditionexp3Context)_localctx).e.result, true);
					
				}
				break;

			case 3:
				{
				setState(343); ((Conditionexp3Context)_localctx).v = match(VARIABLE);
				setState(344); match(39);
				setState(345); ((Conditionexp3Context)_localctx).e = expression(0);

						((Conditionexp3Context)_localctx).result =  new ASTConditionTrap(((Conditionexp3Context)_localctx).v, (((Conditionexp3Context)_localctx).v!=null?((Conditionexp3Context)_localctx).v.getText():null), ((Conditionexp3Context)_localctx).e.result, false);
					
				}
				break;

			case 4:
				{
				setState(348); ((Conditionexp3Context)_localctx).t = match(JULIA);

						((Conditionexp3Context)_localctx).result =  new ASTConditionJulia(((Conditionexp3Context)_localctx).t);
					
				}
				break;

			case 5:
				{
				setState(350); ((Conditionexp3Context)_localctx).s = match(7);
				setState(351); ((Conditionexp3Context)_localctx).c = conditionexp(0);
				setState(352); match(48);

						((Conditionexp3Context)_localctx).result =  new ASTConditionParen(((Conditionexp3Context)_localctx).s, ((Conditionexp3Context)_localctx).c.result);
					
				}
				break;

			case 6:
				{
				setState(355); ((Conditionexp3Context)_localctx).c2 = conditionexp4();

						((Conditionexp3Context)_localctx).result =  ((Conditionexp3Context)_localctx).c2.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(367);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Conditionexp3Context(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp3);
					setState(360);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(361); ((Conditionexp3Context)_localctx).l = match(40);
					setState(362); ((Conditionexp3Context)_localctx).c2 = conditionexp4();

					          		((Conditionexp3Context)_localctx).result =  new ASTConditionLogicOp(((Conditionexp3Context)_localctx).c1.result.getLocation(), (((Conditionexp3Context)_localctx).l!=null?((Conditionexp3Context)_localctx).l.getText():null), ((Conditionexp3Context)_localctx).c1.result, ((Conditionexp3Context)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(369);
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

	public static class Conditionexp4Context extends ParserRuleContext {
		public ASTConditionExpression result;
		public ExpressionContext e1;
		public Token o;
		public ExpressionContext e2;
		public Token v;
		public ExpressionContext e;
		public Token t;
		public Token s;
		public ConditionexpContext c;
		public Token n;
		public Conditionexp4Context c2;
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public TerminalNode JULIA() { return getToken(MandelbrotParser.JULIA, 0); }
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Conditionexp4Context conditionexp4() {
			return getRuleContext(Conditionexp4Context.class,0);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
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
		enterRule(_localctx, 32, RULE_conditionexp4);
		int _la;
		try {
			setState(396);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(370); ((Conditionexp4Context)_localctx).e1 = expression(0);
				setState(371);
				((Conditionexp4Context)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 23) | (1L << 37) | (1L << 46))) != 0)) ) {
					((Conditionexp4Context)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(372); ((Conditionexp4Context)_localctx).e2 = expression(0);

						((Conditionexp4Context)_localctx).result =  new ASTConditionCompareOp(((Conditionexp4Context)_localctx).e1.result.getLocation(), (((Conditionexp4Context)_localctx).o!=null?((Conditionexp4Context)_localctx).o.getText():null), ((Conditionexp4Context)_localctx).e1.result, ((Conditionexp4Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(375); ((Conditionexp4Context)_localctx).v = match(VARIABLE);
				setState(376); match(35);
				setState(377); ((Conditionexp4Context)_localctx).e = expression(0);

						((Conditionexp4Context)_localctx).result =  new ASTConditionTrap(((Conditionexp4Context)_localctx).v, (((Conditionexp4Context)_localctx).v!=null?((Conditionexp4Context)_localctx).v.getText():null), ((Conditionexp4Context)_localctx).e.result, true);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(380); ((Conditionexp4Context)_localctx).v = match(VARIABLE);
				setState(381); match(39);
				setState(382); ((Conditionexp4Context)_localctx).e = expression(0);

						((Conditionexp4Context)_localctx).result =  new ASTConditionTrap(((Conditionexp4Context)_localctx).v, (((Conditionexp4Context)_localctx).v!=null?((Conditionexp4Context)_localctx).v.getText():null), ((Conditionexp4Context)_localctx).e.result, false);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(385); ((Conditionexp4Context)_localctx).t = match(JULIA);

						((Conditionexp4Context)_localctx).result =  new ASTConditionJulia(((Conditionexp4Context)_localctx).t);
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(387); ((Conditionexp4Context)_localctx).s = match(7);
				setState(388); ((Conditionexp4Context)_localctx).c = conditionexp(0);
				setState(389); match(48);

						((Conditionexp4Context)_localctx).result =  new ASTConditionParen(((Conditionexp4Context)_localctx).s, ((Conditionexp4Context)_localctx).c.result);
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(392); ((Conditionexp4Context)_localctx).n = match(21);
				setState(393); ((Conditionexp4Context)_localctx).c2 = conditionexp4();

						((Conditionexp4Context)_localctx).result =  new ASTConditionNeg(((Conditionexp4Context)_localctx).n, ((Conditionexp4Context)_localctx).c2.result);
					
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
		public ConstantContext p;
		public VariableContext v;
		public ComplexContext c;
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
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public Expression2Context expression2() {
			return getRuleContext(Expression2Context.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
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
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(441);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(399); ((ExpressionContext)_localctx).e2 = expression2(0);
				setState(400); ((ExpressionContext)_localctx).s = match(28);
				setState(401); ((ExpressionContext)_localctx).e1 = expression(2);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e2.result, ((ExpressionContext)_localctx).e1.result);		
					
				}
				break;

			case 2:
				{
				setState(404); ((ExpressionContext)_localctx).p = constant();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).p.result;
					
				}
				break;

			case 3:
				{
				setState(407); ((ExpressionContext)_localctx).v = variable();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).v.result;
					
				}
				break;

			case 4:
				{
				setState(410); ((ExpressionContext)_localctx).c = complex();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).c.result;
					
				}
				break;

			case 5:
				{
				setState(413); ((ExpressionContext)_localctx).f = function();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).f.result;
					
				}
				break;

			case 6:
				{
				setState(416); ((ExpressionContext)_localctx).t = match(7);
				setState(417); ((ExpressionContext)_localctx).e = expression(0);
				setState(418); match(48);

						((ExpressionContext)_localctx).result =  new ASTParen(((ExpressionContext)_localctx).t, ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 7:
				{
				setState(421); ((ExpressionContext)_localctx).m = match(45);
				setState(422); ((ExpressionContext)_localctx).e = expression(0);
				setState(423); match(45);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).m, "mod", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(426); ((ExpressionContext)_localctx).a = match(19);
				setState(427); ((ExpressionContext)_localctx).e = expression(0);
				setState(428); match(46);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).a, "pha", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 9:
				{
				setState(431); ((ExpressionContext)_localctx).a = match(19);
				setState(432); ((ExpressionContext)_localctx).er = expression(0);
				setState(433); match(9);
				setState(434); ((ExpressionContext)_localctx).ei = expression(0);
				setState(435); match(46);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).a, "<>", ((ExpressionContext)_localctx).er.result, ((ExpressionContext)_localctx).ei.result);	
					
				}
				break;

			case 10:
				{
				setState(438); ((ExpressionContext)_localctx).e2 = expression2(0);

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e2.result;	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(455);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(453);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(443);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(444); ((ExpressionContext)_localctx).s = match(28);
						setState(445); ((ExpressionContext)_localctx).e2 = expression2(0);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;

					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(448);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(449); ((ExpressionContext)_localctx).s = match(51);
						setState(450); ((ExpressionContext)_localctx).e2 = expression2(0);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					}
					} 
				}
				setState(457);
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

	public static class Expression2Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression2Context e1;
		public Expression2Context e2;
		public Token s;
		public Token i;
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
		public Expression3Context e3;
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
		public Expression3Context expression3() {
			return getRuleContext(Expression3Context.class,0);
		}
		public List<Expression2Context> expression2() {
			return getRuleContexts(Expression2Context.class);
		}
		public Expression2Context expression2(int i) {
			return getRuleContext(Expression2Context.class,i);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
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
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_expression2, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(511);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(459); ((Expression2Context)_localctx).s = match(51);
				setState(460); ((Expression2Context)_localctx).e2 = expression2(4);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "-", ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(463); ((Expression2Context)_localctx).s = match(28);
				setState(464); ((Expression2Context)_localctx).e2 = expression2(3);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "+", ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 3:
				{
				setState(467); ((Expression2Context)_localctx).i = match(27);
				setState(469);
				_la = _input.LA(1);
				if (_la==41) {
					{
					setState(468); match(41);
					}
				}

				setState(471); ((Expression2Context)_localctx).e2 = expression2(2);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 4:
				{
				setState(474); ((Expression2Context)_localctx).p = constant();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).p.result;
					
				}
				break;

			case 5:
				{
				setState(477); ((Expression2Context)_localctx).v = variable();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).v.result;
					
				}
				break;

			case 6:
				{
				setState(480); ((Expression2Context)_localctx).r = real();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).r.result;
					
				}
				break;

			case 7:
				{
				setState(483); ((Expression2Context)_localctx).f = function();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).f.result;
					
				}
				break;

			case 8:
				{
				setState(486); ((Expression2Context)_localctx).t = match(7);
				setState(487); ((Expression2Context)_localctx).e = expression(0);
				setState(488); match(48);

						((Expression2Context)_localctx).result =  new ASTParen(((Expression2Context)_localctx).t, ((Expression2Context)_localctx).e.result);
					
				}
				break;

			case 9:
				{
				setState(491); ((Expression2Context)_localctx).m = match(45);
				setState(492); ((Expression2Context)_localctx).e = expression(0);
				setState(493); match(45);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).m, "mod", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 10:
				{
				setState(496); ((Expression2Context)_localctx).a = match(19);
				setState(497); ((Expression2Context)_localctx).e = expression(0);
				setState(498); match(46);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).a, "pha", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 11:
				{
				setState(501); ((Expression2Context)_localctx).a = match(19);
				setState(502); ((Expression2Context)_localctx).er = expression(0);
				setState(503); match(9);
				setState(504); ((Expression2Context)_localctx).ei = expression(0);
				setState(505); match(46);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).a, "<>", ((Expression2Context)_localctx).er.result, ((Expression2Context)_localctx).ei.result);	
					
				}
				break;

			case 12:
				{
				setState(508); ((Expression2Context)_localctx).e3 = expression3(0);

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(526);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(524);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(513);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(514); ((Expression2Context)_localctx).s = match(41);
						setState(515); ((Expression2Context)_localctx).e2 = expression2(6);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "*", ((Expression2Context)_localctx).e1.result, ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;

					case 2:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e2 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(518);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(520);
						_la = _input.LA(1);
						if (_la==41) {
							{
							setState(519); match(41);
							}
						}

						setState(522); ((Expression2Context)_localctx).i = match(27);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					}
					} 
				}
				setState(528);
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

	public static class Expression3Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression3Context e1;
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
		public Expression4Context e3;
		public Token s;
		public Expression3Context e2;
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public Expression3Context expression3(int i) {
			return getRuleContext(Expression3Context.class,i);
		}
		public RealContext real() {
			return getRuleContext(RealContext.class,0);
		}
		public Expression4Context expression4() {
			return getRuleContext(Expression4Context.class,0);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public List<Expression3Context> expression3() {
			return getRuleContexts(Expression3Context.class);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
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
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expression3, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(567);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(530); ((Expression3Context)_localctx).p = constant();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).p.result;
					
				}
				break;

			case 2:
				{
				setState(533); ((Expression3Context)_localctx).v = variable();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).v.result;
					
				}
				break;

			case 3:
				{
				setState(536); ((Expression3Context)_localctx).r = real();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).r.result;
					
				}
				break;

			case 4:
				{
				setState(539); ((Expression3Context)_localctx).f = function();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).f.result;
					
				}
				break;

			case 5:
				{
				setState(542); ((Expression3Context)_localctx).t = match(7);
				setState(543); ((Expression3Context)_localctx).e = expression(0);
				setState(544); match(48);

						((Expression3Context)_localctx).result =  new ASTParen(((Expression3Context)_localctx).t, ((Expression3Context)_localctx).e.result);
					
				}
				break;

			case 6:
				{
				setState(547); ((Expression3Context)_localctx).m = match(45);
				setState(548); ((Expression3Context)_localctx).e = expression(0);
				setState(549); match(45);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).m, "mod", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 7:
				{
				setState(552); ((Expression3Context)_localctx).a = match(19);
				setState(553); ((Expression3Context)_localctx).e = expression(0);
				setState(554); match(46);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).a, "pha", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(557); ((Expression3Context)_localctx).a = match(19);
				setState(558); ((Expression3Context)_localctx).er = expression(0);
				setState(559); match(9);
				setState(560); ((Expression3Context)_localctx).ei = expression(0);
				setState(561); match(46);

						((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).a, "<>", ((Expression3Context)_localctx).er.result, ((Expression3Context)_localctx).ei.result);	
					
				}
				break;

			case 9:
				{
				setState(564); ((Expression3Context)_localctx).e3 = expression4(0);

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(576);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression3Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression3);
					setState(569);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(570); ((Expression3Context)_localctx).s = match(29);
					setState(571); ((Expression3Context)_localctx).e2 = expression3(2);

					          		((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).s, "/", ((Expression3Context)_localctx).e1.result, ((Expression3Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(578);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
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
		public Token s;
		public Expression4Context e2;
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public RealContext real() {
			return getRuleContext(RealContext.class,0);
		}
		public List<Expression4Context> expression4() {
			return getRuleContexts(Expression4Context.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public Expression4Context expression4(int i) {
			return getRuleContext(Expression4Context.class,i);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
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
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_expression4, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(614);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(580); ((Expression4Context)_localctx).p = constant();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).p.result;
					
				}
				break;

			case 2:
				{
				setState(583); ((Expression4Context)_localctx).v = variable();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).v.result;
					
				}
				break;

			case 3:
				{
				setState(586); ((Expression4Context)_localctx).r = real();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).r.result;
					
				}
				break;

			case 4:
				{
				setState(589); ((Expression4Context)_localctx).f = function();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).f.result;
					
				}
				break;

			case 5:
				{
				setState(592); ((Expression4Context)_localctx).t = match(7);
				setState(593); ((Expression4Context)_localctx).e = expression(0);
				setState(594); match(48);

						((Expression4Context)_localctx).result =  new ASTParen(((Expression4Context)_localctx).t, ((Expression4Context)_localctx).e.result);
					
				}
				break;

			case 6:
				{
				setState(597); ((Expression4Context)_localctx).m = match(45);
				setState(598); ((Expression4Context)_localctx).e = expression(0);
				setState(599); match(45);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).m, "mod", ((Expression4Context)_localctx).e.result);	
					
				}
				break;

			case 7:
				{
				setState(602); ((Expression4Context)_localctx).a = match(19);
				setState(603); ((Expression4Context)_localctx).e = expression(0);
				setState(604); match(46);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).a, "pha", ((Expression4Context)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(607); ((Expression4Context)_localctx).a = match(19);
				setState(608); ((Expression4Context)_localctx).er = expression(0);
				setState(609); match(9);
				setState(610); ((Expression4Context)_localctx).ei = expression(0);
				setState(611); match(46);

						((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).a, "<>", ((Expression4Context)_localctx).er.result, ((Expression4Context)_localctx).ei.result);	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(623);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression4Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression4);
					setState(616);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(617); ((Expression4Context)_localctx).s = match(5);
					setState(618); ((Expression4Context)_localctx).e2 = expression4(2);

					          		((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).s, "^", ((Expression4Context)_localctx).e1.result, ((Expression4Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(625);
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
		enterRule(_localctx, 42, RULE_function);
		int _la;
		try {
			setState(652);
			switch (_input.LA(1)) {
			case 6:
			case 10:
			case 11:
			case 16:
			case 34:
				enterOuterAlt(_localctx, 1);
				{
				setState(626);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 10) | (1L << 11) | (1L << 16) | (1L << 34))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(627); match(7);
				setState(628); ((FunctionContext)_localctx).e = expression(0);
				setState(629); match(48);

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
				setState(632);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 4) | (1L << 12) | (1L << 36) | (1L << 42) | (1L << 47))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(633); match(7);
				setState(634); ((FunctionContext)_localctx).e = expression(0);
				setState(635); match(48);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 13:
			case 15:
			case 18:
			case 22:
			case 26:
			case 49:
				enterOuterAlt(_localctx, 3);
				{
				setState(638);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 13) | (1L << 15) | (1L << 18) | (1L << 22) | (1L << 26) | (1L << 49))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(639); match(7);
				setState(640); ((FunctionContext)_localctx).e = expression(0);
				setState(641); match(48);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 8:
			case 32:
			case 38:
			case 43:
			case 50:
				enterOuterAlt(_localctx, 4);
				{
				setState(644);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 8) | (1L << 32) | (1L << 38) | (1L << 43) | (1L << 50))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(645); match(7);
				setState(646); ((FunctionContext)_localctx).e1 = expression(0);
				setState(647); match(9);
				setState(648); ((FunctionContext)_localctx).e2 = expression(0);
				setState(649); match(48);

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
		enterRule(_localctx, 44, RULE_constant);
		try {
			setState(660);
			switch (_input.LA(1)) {
			case 25:
				enterOuterAlt(_localctx, 1);
				{
				setState(654); ((ConstantContext)_localctx).p = match(25);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.E);
					
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 2);
				{
				setState(656); ((ConstantContext)_localctx).p = match(14);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.PI);
					
				}
				break;
			case 30:
				enterOuterAlt(_localctx, 3);
				{
				setState(658); ((ConstantContext)_localctx).p = match(30);

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
		enterRule(_localctx, 46, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(662); ((VariableContext)_localctx).v = match(VARIABLE);

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
		enterRule(_localctx, 48, RULE_real);
		int _la;
		try {
			setState(673);
			switch (_input.LA(1)) {
			case 28:
			case RATIONAL:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(666);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(665); match(28);
					}
				}

				setState(668);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((RealContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();

						((RealContext)_localctx).result =  new ASTNumber(((RealContext)_localctx).r, builder.parseDouble((((RealContext)_localctx).r!=null?((RealContext)_localctx).r.getText():null)));
					
				}
				break;
			case 51:
				enterOuterAlt(_localctx, 2);
				{
				setState(670); match(51);
				setState(671);
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
		enterRule(_localctx, 50, RULE_complex);
		int _la;
		try {
			setState(756);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(675); match(19);
				setState(677);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(676); match(28);
					}
				}

				setState(679);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(680); match(9);
				setState(682);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(681); match(28);
					}
				}

				setState(684);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(685); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(687); match(19);
				setState(689);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(688); match(28);
					}
				}

				setState(691);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(692); match(9);
				setState(693); match(51);
				setState(694);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(695); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(697); match(19);
				setState(698); match(51);
				setState(699);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(700); match(9);
				setState(702);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(701); match(28);
					}
				}

				setState(704);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(705); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(707); match(19);
				setState(708); match(51);
				setState(709);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(710); match(9);
				setState(711); match(51);
				setState(712);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(713); match(46);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(716);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(715); match(28);
					}
				}

				setState(718);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(719); match(28);
				setState(720);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(721); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(724);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(723); match(28);
					}
				}

				setState(726);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(727); match(51);
				setState(728);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(729); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(732);
				_la = _input.LA(1);
				if (_la==28) {
					{
					setState(731); match(28);
					}
				}

				setState(734);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(735); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble((((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(737); match(51);
				setState(738);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(739); match(28);
				setState(740);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(741); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(743); match(51);
				setState(744);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(745); match(51);
				setState(746);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(747); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(749); match(51);
				setState(750);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(751); match(27);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(753); ((ComplexContext)_localctx).rn = real();

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
		enterRule(_localctx, 52, RULE_palette);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(758); ((PaletteContext)_localctx).p = match(PALETTE);
			setState(759); ((PaletteContext)_localctx).v = match(VARIABLE);

					builder.addPalette(new ASTPalette(((PaletteContext)_localctx).p, (((PaletteContext)_localctx).v!=null?((PaletteContext)_localctx).v.getText():null))); 
				
			setState(761); match(2);
			setState(763); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(762); paletteelement();
				}
				}
				setState(765); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==44 );
			setState(767); match(33);
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
		enterRule(_localctx, 54, RULE_paletteelement);
		try {
			setState(791);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(769); ((PaletteelementContext)_localctx).t = match(44);
				setState(770); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(771); match(46);
				setState(772); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(773); match(9);
				setState(774); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(775); match(9);
				setState(776); ((PaletteelementContext)_localctx).e = expression(0);
				setState(777); match(20);
				setState(778); match(31);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), ((PaletteelementContext)_localctx).e.result));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(781); ((PaletteelementContext)_localctx).t = match(44);
				setState(782); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(783); match(46);
				setState(784); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(785); match(9);
				setState(786); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(787); match(20);
				setState(788); match(31);

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
		enterRule(_localctx, 56, RULE_colorinit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(793); ((ColorinitContext)_localctx).i = match(INIT);

					builder.setColorContext(true);
					builder.setColorInit(new ASTColorInit(((ColorinitContext)_localctx).i));
				
			setState(795); match(2);
			setState(799);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 59)) & ~0x3f) == 0 && ((1L << (_la - 59)) & ((1L << (IF - 59)) | (1L << (STOP - 59)) | (1L << (VARIABLE - 59)))) != 0)) {
				{
				{
				setState(796); colorstatement();
				}
				}
				setState(801);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(802); match(33);
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
		enterRule(_localctx, 58, RULE_colorstatement);
		try {
			enterOuterAlt(_localctx, 1);
			{

					builder.pushStatementList();	
				
			setState(805); statement();

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
		enterRule(_localctx, 60, RULE_colorrule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(808); ((ColorruleContext)_localctx).t = match(RULE);
			setState(809); match(7);
			setState(810); ((ColorruleContext)_localctx).r = ruleexp(0);
			setState(811); match(48);
			setState(812); match(44);
			setState(813);
			((ColorruleContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==RATIONAL || _la==INTEGER) ) {
				((ColorruleContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(814); match(20);
			setState(815); match(2);
			setState(816); ((ColorruleContext)_localctx).c = colorexp();
			setState(817); match(33);

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
		int _startState = 62;
		enterRecursionRule(_localctx, 62, RULE_ruleexp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(821); ((RuleexpContext)_localctx).e1 = expression(0);
			setState(822);
			((RuleexpContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 23) | (1L << 37) | (1L << 46))) != 0)) ) {
				((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(823); ((RuleexpContext)_localctx).e2 = expression(0);

					((RuleexpContext)_localctx).result =  new ASTRuleCompareOp(((RuleexpContext)_localctx).e1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).e1.result, ((RuleexpContext)_localctx).e2.result);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(833);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RuleexpContext(_parentctx, _parentState);
					_localctx.r1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_ruleexp);
					setState(826);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(827);
					((RuleexpContext)_localctx).o = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 40) | (1L << 45))) != 0)) ) {
						((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(828); ((RuleexpContext)_localctx).r2 = ruleexp(2);

					          		((RuleexpContext)_localctx).result =  new ASTRuleLogicOp(((RuleexpContext)_localctx).r1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).r1.result, ((RuleexpContext)_localctx).r2.result);
					          	
					}
					} 
				}
				setState(835);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,50,_ctx);
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
		enterRule(_localctx, 64, RULE_colorexp);
		try {
			setState(861);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(836); ((ColorexpContext)_localctx).e1 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(839); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(840); match(9);
				setState(841); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(842); match(9);
				setState(843); ((ColorexpContext)_localctx).e3 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(846); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(847); match(9);
				setState(848); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(849); match(9);
				setState(850); ((ColorexpContext)_localctx).e3 = expression(0);
				setState(851); match(9);
				setState(852); ((ColorexpContext)_localctx).e4 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result, ((ColorexpContext)_localctx).e4.result);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(855); ((ColorexpContext)_localctx).v = match(VARIABLE);
				setState(856); match(44);
				setState(857); ((ColorexpContext)_localctx).e = expression(0);
				setState(858); match(20);

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
		enterRule(_localctx, 66, RULE_colorargb);
		int _la;
		try {
			setState(876);
			switch (_input.LA(1)) {
			case 7:
				enterOuterAlt(_localctx, 1);
				{
				setState(863); match(7);
				setState(864);
				((ColorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(865); match(9);
				setState(866);
				((ColorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(867); match(9);
				setState(868);
				((ColorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(869); match(9);
				setState(870);
				((ColorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(871); match(48);

						((ColorargbContext)_localctx).result =  new ASTColorARGB(builder.parseFloat((((ColorargbContext)_localctx).a!=null?((ColorargbContext)_localctx).a.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).r!=null?((ColorargbContext)_localctx).r.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).g!=null?((ColorargbContext)_localctx).g.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).b!=null?((ColorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 2);
				{
				setState(873); match(24);
				setState(874); ((ColorargbContext)_localctx).argb = match(ARGB);

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
		enterRule(_localctx, 68, RULE_eof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(878); match(EOF);
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

		case 13: return conditionexp_sempred((ConditionexpContext)_localctx, predIndex);

		case 14: return conditionexp2_sempred((Conditionexp2Context)_localctx, predIndex);

		case 15: return conditionexp3_sempred((Conditionexp3Context)_localctx, predIndex);

		case 17: return expression_sempred((ExpressionContext)_localctx, predIndex);

		case 18: return expression2_sempred((Expression2Context)_localctx, predIndex);

		case 19: return expression3_sempred((Expression3Context)_localctx, predIndex);

		case 20: return expression4_sempred((Expression4Context)_localctx, predIndex);

		case 31: return ruleexp_sempred((RuleexpContext)_localctx, predIndex);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3J\u0373\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3]\n\3\f\3\16\3`\13\3\3\3\5\3c\n"+
		"\3\3\3\3\3\5\3g\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4r\n\4\f\4\16"+
		"\4u\13\4\3\4\5\4x\n\4\3\4\7\4{\n\4\f\4\16\4~\13\4\3\4\3\4\3\5\3\5\3\5"+
		"\3\5\7\5\u0086\n\5\f\5\16\5\u0089\13\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u0099\n\6\f\6\16\6\u009c\13\6\3\6\3\6\3\7"+
		"\3\7\3\7\3\7\7\7\u00a4\n\7\f\7\16\7\u00a7\13\7\3\7\3\7\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\7\b\u00b3\n\b\f\b\16\b\u00b6\13\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00ca\n\t\3\n"+
		"\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\5\r\u00dc"+
		"\n\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00e7\n\r\f\r\16\r\u00ea"+
		"\13\r\3\r\3\r\3\r\3\r\3\r\5\r\u00f1\n\r\3\r\5\r\u00f4\n\r\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\7\16\u00fe\n\16\f\16\16\16\u0101\13\16\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u011d"+
		"\n\17\3\17\3\17\3\17\3\17\3\17\7\17\u0124\n\17\f\17\16\17\u0127\13\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0143"+
		"\n\20\3\20\3\20\3\20\3\20\3\20\7\20\u014a\n\20\f\20\16\20\u014d\13\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u0169"+
		"\n\21\3\21\3\21\3\21\3\21\3\21\7\21\u0170\n\21\f\21\16\21\u0173\13\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u018f"+
		"\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\5\23\u01bc\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\7\23\u01c8\n\23\f\23\16\23\u01cb\13\23\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u01d8\n\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u0202\n\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\5\24\u020b\n\24\3\24\3\24\7\24\u020f\n\24\f\24\16\24\u0212"+
		"\13\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u023a\n\25"+
		"\3\25\3\25\3\25\3\25\3\25\7\25\u0241\n\25\f\25\16\25\u0244\13\25\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0269\n\26\3\26\3\26\3\26\3\26\3\26"+
		"\7\26\u0270\n\26\f\26\16\26\u0273\13\26\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u028f\n\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\5\30\u0297\n\30\3\31\3\31\3\31\3\32\5\32\u029d\n\32\3\32\3\32\3"+
		"\32\3\32\3\32\5\32\u02a4\n\32\3\33\3\33\5\33\u02a8\n\33\3\33\3\33\3\33"+
		"\5\33\u02ad\n\33\3\33\3\33\3\33\3\33\3\33\5\33\u02b4\n\33\3\33\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u02c1\n\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u02cf\n\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\5\33\u02d7\n\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\5\33\u02df\n\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u02f7\n\33"+
		"\3\34\3\34\3\34\3\34\3\34\6\34\u02fe\n\34\r\34\16\34\u02ff\3\34\3\34\3"+
		"\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3"+
		"\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u031a\n\35\3\36\3\36\3\36"+
		"\3\36\7\36\u0320\n\36\f\36\16\36\u0323\13\36\3\36\3\36\3\37\3\37\3\37"+
		"\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3!\3!\3"+
		"!\3!\7!\u0342\n!\f!\16!\u0345\13!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u0360"+
		"\n\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#\u036f\n#\3$\3$\3$\2\13"+
		"\32\34\36 $&(*@%\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62"+
		"\64\668:<>@BDF\2\t\b\2\5\5\23\23\25\25\31\31\'\'\60\60\6\2\b\b\f\r\22"+
		"\22$$\b\2\3\3\6\6\16\16&&,,\61\61\b\2\17\17\21\21\24\24\30\30\34\34\63"+
		"\63\7\2\n\n\"\"((--\64\64\3\2DE\5\2\7\7**//\u03c2\2H\3\2\2\2\4P\3\2\2"+
		"\2\6j\3\2\2\2\b\u0081\3\2\2\2\n\u008c\3\2\2\2\f\u009f\3\2\2\2\16\u00aa"+
		"\3\2\2\2\20\u00c9\3\2\2\2\22\u00cb\3\2\2\2\24\u00cf\3\2\2\2\26\u00d3\3"+
		"\2\2\2\30\u00f3\3\2\2\2\32\u00f5\3\2\2\2\34\u011c\3\2\2\2\36\u0142\3\2"+
		"\2\2 \u0168\3\2\2\2\"\u018e\3\2\2\2$\u01bb\3\2\2\2&\u0201\3\2\2\2(\u0239"+
		"\3\2\2\2*\u0268\3\2\2\2,\u028e\3\2\2\2.\u0296\3\2\2\2\60\u0298\3\2\2\2"+
		"\62\u02a3\3\2\2\2\64\u02f6\3\2\2\2\66\u02f8\3\2\2\28\u0319\3\2\2\2:\u031b"+
		"\3\2\2\2<\u0326\3\2\2\2>\u032a\3\2\2\2@\u0336\3\2\2\2B\u035f\3\2\2\2D"+
		"\u036e\3\2\2\2F\u0370\3\2\2\2HI\7\66\2\2IJ\b\2\1\2JK\7\4\2\2KL\5\4\3\2"+
		"LM\5\6\4\2MN\7#\2\2NO\5F$\2O\3\3\2\2\2PQ\7\67\2\2QR\7.\2\2RS\5\64\33\2"+
		"ST\7\13\2\2TU\5\64\33\2UV\7\26\2\2VW\b\3\1\2WX\7.\2\2XY\5\32\16\2YZ\7"+
		"\26\2\2Z^\7\4\2\2[]\5\16\b\2\\[\3\2\2\2]`\3\2\2\2^\\\3\2\2\2^_\3\2\2\2"+
		"_b\3\2\2\2`^\3\2\2\2ac\5\b\5\2ba\3\2\2\2bc\3\2\2\2cd\3\2\2\2df\5\n\6\2"+
		"eg\5\f\7\2fe\3\2\2\2fg\3\2\2\2gh\3\2\2\2hi\7#\2\2i\5\3\2\2\2jk\7@\2\2"+
		"kl\7.\2\2lm\5D#\2mn\7\26\2\2no\b\4\1\2os\7\4\2\2pr\5\66\34\2qp\3\2\2\2"+
		"ru\3\2\2\2sq\3\2\2\2st\3\2\2\2tw\3\2\2\2us\3\2\2\2vx\5:\36\2wv\3\2\2\2"+
		"wx\3\2\2\2x|\3\2\2\2y{\5> \2zy\3\2\2\2{~\3\2\2\2|z\3\2\2\2|}\3\2\2\2}"+
		"\177\3\2\2\2~|\3\2\2\2\177\u0080\7#\2\2\u0080\7\3\2\2\2\u0081\u0082\7"+
		"9\2\2\u0082\u0083\b\5\1\2\u0083\u0087\7\4\2\2\u0084\u0086\5\22\n\2\u0085"+
		"\u0084\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2"+
		"\2\2\u0088\u008a\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008b\7#\2\2\u008b"+
		"\t\3\2\2\2\u008c\u008d\7:\2\2\u008d\u008e\7.\2\2\u008e\u008f\7E\2\2\u008f"+
		"\u0090\7\13\2\2\u0090\u0091\7E\2\2\u0091\u0092\7\26\2\2\u0092\u0093\7"+
		"\t\2\2\u0093\u0094\5\34\17\2\u0094\u0095\7\62\2\2\u0095\u0096\b\6\1\2"+
		"\u0096\u009a\7\4\2\2\u0097\u0099\5\24\13\2\u0098\u0097\3\2\2\2\u0099\u009c"+
		"\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009d\3\2\2\2\u009c"+
		"\u009a\3\2\2\2\u009d\u009e\7#\2\2\u009e\13\3\2\2\2\u009f\u00a0\7;\2\2"+
		"\u00a0\u00a1\b\7\1\2\u00a1\u00a5\7\4\2\2\u00a2\u00a4\5\26\f\2\u00a3\u00a2"+
		"\3\2\2\2\u00a4\u00a7\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6"+
		"\u00a8\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a8\u00a9\7#\2\2\u00a9\r\3\2\2\2"+
		"\u00aa\u00ab\78\2\2\u00ab\u00ac\7H\2\2\u00ac\u00ad\7.\2\2\u00ad\u00ae"+
		"\5\64\33\2\u00ae\u00af\7\26\2\2\u00af\u00b0\b\b\1\2\u00b0\u00b4\7\4\2"+
		"\2\u00b1\u00b3\5\20\t\2\u00b2\u00b1\3\2\2\2\u00b3\u00b6\3\2\2\2\u00b4"+
		"\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b7\3\2\2\2\u00b6\u00b4\3\2"+
		"\2\2\u00b7\u00b8\7#\2\2\u00b8\17\3\2\2\2\u00b9\u00ba\7F\2\2\u00ba\u00bb"+
		"\7\t\2\2\u00bb\u00bc\5\64\33\2\u00bc\u00bd\7\62\2\2\u00bd\u00be\7!\2\2"+
		"\u00be\u00bf\b\t\1\2\u00bf\u00ca\3\2\2\2\u00c0\u00c1\7G\2\2\u00c1\u00c2"+
		"\7\t\2\2\u00c2\u00c3\5\64\33\2\u00c3\u00c4\7\13\2\2\u00c4\u00c5\5\64\33"+
		"\2\u00c5\u00c6\7\62\2\2\u00c6\u00c7\7!\2\2\u00c7\u00c8\b\t\1\2\u00c8\u00ca"+
		"\3\2\2\2\u00c9\u00b9\3\2\2\2\u00c9\u00c0\3\2\2\2\u00ca\21\3\2\2\2\u00cb"+
		"\u00cc\b\n\1\2\u00cc\u00cd\5\30\r\2\u00cd\u00ce\b\n\1\2\u00ce\23\3\2\2"+
		"\2\u00cf\u00d0\b\13\1\2\u00d0\u00d1\5\30\r\2\u00d1\u00d2\b\13\1\2\u00d2"+
		"\25\3\2\2\2\u00d3\u00d4\b\f\1\2\u00d4\u00d5\5\30\r\2\u00d5\u00d6\b\f\1"+
		"\2\u00d6\27\3\2\2\2\u00d7\u00d8\7H\2\2\u00d8\u00d9\7\5\2\2\u00d9\u00db"+
		"\5$\23\2\u00da\u00dc\7!\2\2\u00db\u00da\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc"+
		"\u00dd\3\2\2\2\u00dd\u00de\b\r\1\2\u00de\u00f4\3\2\2\2\u00df\u00e0\7="+
		"\2\2\u00e0\u00e1\7\t\2\2\u00e1\u00e2\5\34\17\2\u00e2\u00e3\7\62\2\2\u00e3"+
		"\u00e4\7\4\2\2\u00e4\u00e8\b\r\1\2\u00e5\u00e7\5\30\r\2\u00e6\u00e5\3"+
		"\2\2\2\u00e7\u00ea\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9"+
		"\u00eb\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb\u00ec\7#\2\2\u00ec\u00ed\b\r"+
		"\1\2\u00ed\u00f4\3\2\2\2\u00ee\u00f0\7>\2\2\u00ef\u00f1\7!\2\2\u00f0\u00ef"+
		"\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f4\b\r\1\2\u00f3"+
		"\u00d7\3\2\2\2\u00f3\u00df\3\2\2\2\u00f3\u00ee\3\2\2\2\u00f4\31\3\2\2"+
		"\2\u00f5\u00f6\b\16\1\2\u00f6\u00f7\7H\2\2\u00f7\u00f8\b\16\1\2\u00f8"+
		"\u00ff\3\2\2\2\u00f9\u00fa\f\3\2\2\u00fa\u00fb\7\13\2\2\u00fb\u00fc\7"+
		"H\2\2\u00fc\u00fe\b\16\1\2\u00fd\u00f9\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff"+
		"\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\33\3\2\2\2\u0101\u00ff\3\2\2"+
		"\2\u0102\u0103\b\17\1\2\u0103\u0104\5$\23\2\u0104\u0105\t\2\2\2\u0105"+
		"\u0106\5$\23\2\u0106\u0107\b\17\1\2\u0107\u011d\3\2\2\2\u0108\u0109\7"+
		"H\2\2\u0109\u010a\7%\2\2\u010a\u010b\5$\23\2\u010b\u010c\b\17\1\2\u010c"+
		"\u011d\3\2\2\2\u010d\u010e\7H\2\2\u010e\u010f\7)\2\2\u010f\u0110\5$\23"+
		"\2\u0110\u0111\b\17\1\2\u0111\u011d\3\2\2\2\u0112\u0113\7?\2\2\u0113\u011d"+
		"\b\17\1\2\u0114\u0115\7\t\2\2\u0115\u0116\5\34\17\2\u0116\u0117\7\62\2"+
		"\2\u0117\u0118\b\17\1\2\u0118\u011d\3\2\2\2\u0119\u011a\5\36\20\2\u011a"+
		"\u011b\b\17\1\2\u011b\u011d\3\2\2\2\u011c\u0102\3\2\2\2\u011c\u0108\3"+
		"\2\2\2\u011c\u010d\3\2\2\2\u011c\u0112\3\2\2\2\u011c\u0114\3\2\2\2\u011c"+
		"\u0119\3\2\2\2\u011d\u0125\3\2\2\2\u011e\u011f\f\3\2\2\u011f\u0120\7/"+
		"\2\2\u0120\u0121\5\36\20\2\u0121\u0122\b\17\1\2\u0122\u0124\3\2\2\2\u0123"+
		"\u011e\3\2\2\2\u0124\u0127\3\2\2\2\u0125\u0123\3\2\2\2\u0125\u0126\3\2"+
		"\2\2\u0126\35\3\2\2\2\u0127\u0125\3\2\2\2\u0128\u0129\b\20\1\2\u0129\u012a"+
		"\5$\23\2\u012a\u012b\t\2\2\2\u012b\u012c\5$\23\2\u012c\u012d\b\20\1\2"+
		"\u012d\u0143\3\2\2\2\u012e\u012f\7H\2\2\u012f\u0130\7%\2\2\u0130\u0131"+
		"\5$\23\2\u0131\u0132\b\20\1\2\u0132\u0143\3\2\2\2\u0133\u0134\7H\2\2\u0134"+
		"\u0135\7)\2\2\u0135\u0136\5$\23\2\u0136\u0137\b\20\1\2\u0137\u0143\3\2"+
		"\2\2\u0138\u0139\7?\2\2\u0139\u0143\b\20\1\2\u013a\u013b\7\t\2\2\u013b"+
		"\u013c\5\34\17\2\u013c\u013d\7\62\2\2\u013d\u013e\b\20\1\2\u013e\u0143"+
		"\3\2\2\2\u013f\u0140\5 \21\2\u0140\u0141\b\20\1\2\u0141\u0143\3\2\2\2"+
		"\u0142\u0128\3\2\2\2\u0142\u012e\3\2\2\2\u0142\u0133\3\2\2\2\u0142\u0138"+
		"\3\2\2\2\u0142\u013a\3\2\2\2\u0142\u013f\3\2\2\2\u0143\u014b\3\2\2\2\u0144"+
		"\u0145\f\3\2\2\u0145\u0146\7\7\2\2\u0146\u0147\5 \21\2\u0147\u0148\b\20"+
		"\1\2\u0148\u014a\3\2\2\2\u0149\u0144\3\2\2\2\u014a\u014d\3\2\2\2\u014b"+
		"\u0149\3\2\2\2\u014b\u014c\3\2\2\2\u014c\37\3\2\2\2\u014d\u014b\3\2\2"+
		"\2\u014e\u014f\b\21\1\2\u014f\u0150\5$\23\2\u0150\u0151\t\2\2\2\u0151"+
		"\u0152\5$\23\2\u0152\u0153\b\21\1\2\u0153\u0169\3\2\2\2\u0154\u0155\7"+
		"H\2\2\u0155\u0156\7%\2\2\u0156\u0157\5$\23\2\u0157\u0158\b\21\1\2\u0158"+
		"\u0169\3\2\2\2\u0159\u015a\7H\2\2\u015a\u015b\7)\2\2\u015b\u015c\5$\23"+
		"\2\u015c\u015d\b\21\1\2\u015d\u0169\3\2\2\2\u015e\u015f\7?\2\2\u015f\u0169"+
		"\b\21\1\2\u0160\u0161\7\t\2\2\u0161\u0162\5\34\17\2\u0162\u0163\7\62\2"+
		"\2\u0163\u0164\b\21\1\2\u0164\u0169\3\2\2\2\u0165\u0166\5\"\22\2\u0166"+
		"\u0167\b\21\1\2\u0167\u0169\3\2\2\2\u0168\u014e\3\2\2\2\u0168\u0154\3"+
		"\2\2\2\u0168\u0159\3\2\2\2\u0168\u015e\3\2\2\2\u0168\u0160\3\2\2\2\u0168"+
		"\u0165\3\2\2\2\u0169\u0171\3\2\2\2\u016a\u016b\f\3\2\2\u016b\u016c\7*"+
		"\2\2\u016c\u016d\5\"\22\2\u016d\u016e\b\21\1\2\u016e\u0170\3\2\2\2\u016f"+
		"\u016a\3\2\2\2\u0170\u0173\3\2\2\2\u0171\u016f\3\2\2\2\u0171\u0172\3\2"+
		"\2\2\u0172!\3\2\2\2\u0173\u0171\3\2\2\2\u0174\u0175\5$\23\2\u0175\u0176"+
		"\t\2\2\2\u0176\u0177\5$\23\2\u0177\u0178\b\22\1\2\u0178\u018f\3\2\2\2"+
		"\u0179\u017a\7H\2\2\u017a\u017b\7%\2\2\u017b\u017c\5$\23\2\u017c\u017d"+
		"\b\22\1\2\u017d\u018f\3\2\2\2\u017e\u017f\7H\2\2\u017f\u0180\7)\2\2\u0180"+
		"\u0181\5$\23\2\u0181\u0182\b\22\1\2\u0182\u018f\3\2\2\2\u0183\u0184\7"+
		"?\2\2\u0184\u018f\b\22\1\2\u0185\u0186\7\t\2\2\u0186\u0187\5\34\17\2\u0187"+
		"\u0188\7\62\2\2\u0188\u0189\b\22\1\2\u0189\u018f\3\2\2\2\u018a\u018b\7"+
		"\27\2\2\u018b\u018c\5\"\22\2\u018c\u018d\b\22\1\2\u018d\u018f\3\2\2\2"+
		"\u018e\u0174\3\2\2\2\u018e\u0179\3\2\2\2\u018e\u017e\3\2\2\2\u018e\u0183"+
		"\3\2\2\2\u018e\u0185\3\2\2\2\u018e\u018a\3\2\2\2\u018f#\3\2\2\2\u0190"+
		"\u0191\b\23\1\2\u0191\u0192\5&\24\2\u0192\u0193\7\36\2\2\u0193\u0194\5"+
		"$\23\4\u0194\u0195\b\23\1\2\u0195\u01bc\3\2\2\2\u0196\u0197\5.\30\2\u0197"+
		"\u0198\b\23\1\2\u0198\u01bc\3\2\2\2\u0199\u019a\5\60\31\2\u019a\u019b"+
		"\b\23\1\2\u019b\u01bc\3\2\2\2\u019c\u019d\5\64\33\2\u019d\u019e\b\23\1"+
		"\2\u019e\u01bc\3\2\2\2\u019f\u01a0\5,\27\2\u01a0\u01a1\b\23\1\2\u01a1"+
		"\u01bc\3\2\2\2\u01a2\u01a3\7\t\2\2\u01a3\u01a4\5$\23\2\u01a4\u01a5\7\62"+
		"\2\2\u01a5\u01a6\b\23\1\2\u01a6\u01bc\3\2\2\2\u01a7\u01a8\7/\2\2\u01a8"+
		"\u01a9\5$\23\2\u01a9\u01aa\7/\2\2\u01aa\u01ab\b\23\1\2\u01ab\u01bc\3\2"+
		"\2\2\u01ac\u01ad\7\25\2\2\u01ad\u01ae\5$\23\2\u01ae\u01af\7\60\2\2\u01af"+
		"\u01b0\b\23\1\2\u01b0\u01bc\3\2\2\2\u01b1\u01b2\7\25\2\2\u01b2\u01b3\5"+
		"$\23\2\u01b3\u01b4\7\13\2\2\u01b4\u01b5\5$\23\2\u01b5\u01b6\7\60\2\2\u01b6"+
		"\u01b7\b\23\1\2\u01b7\u01bc\3\2\2\2\u01b8\u01b9\5&\24\2\u01b9\u01ba\b"+
		"\23\1\2\u01ba\u01bc\3\2\2\2\u01bb\u0190\3\2\2\2\u01bb\u0196\3\2\2\2\u01bb"+
		"\u0199\3\2\2\2\u01bb\u019c\3\2\2\2\u01bb\u019f\3\2\2\2\u01bb\u01a2\3\2"+
		"\2\2\u01bb\u01a7\3\2\2\2\u01bb\u01ac\3\2\2\2\u01bb\u01b1\3\2\2\2\u01bb"+
		"\u01b8\3\2\2\2\u01bc\u01c9\3\2\2\2\u01bd\u01be\f\5\2\2\u01be\u01bf\7\36"+
		"\2\2\u01bf\u01c0\5&\24\2\u01c0\u01c1\b\23\1\2\u01c1\u01c8\3\2\2\2\u01c2"+
		"\u01c3\f\3\2\2\u01c3\u01c4\7\65\2\2\u01c4\u01c5\5&\24\2\u01c5\u01c6\b"+
		"\23\1\2\u01c6\u01c8\3\2\2\2\u01c7\u01bd\3\2\2\2\u01c7\u01c2\3\2\2\2\u01c8"+
		"\u01cb\3\2\2\2\u01c9\u01c7\3\2\2\2\u01c9\u01ca\3\2\2\2\u01ca%\3\2\2\2"+
		"\u01cb\u01c9\3\2\2\2\u01cc\u01cd\b\24\1\2\u01cd\u01ce\7\65\2\2\u01ce\u01cf"+
		"\5&\24\6\u01cf\u01d0\b\24\1\2\u01d0\u0202\3\2\2\2\u01d1\u01d2\7\36\2\2"+
		"\u01d2\u01d3\5&\24\5\u01d3\u01d4\b\24\1\2\u01d4\u0202\3\2\2\2\u01d5\u01d7"+
		"\7\35\2\2\u01d6\u01d8\7+\2\2\u01d7\u01d6\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8"+
		"\u01d9\3\2\2\2\u01d9\u01da\5&\24\4\u01da\u01db\b\24\1\2\u01db\u0202\3"+
		"\2\2\2\u01dc\u01dd\5.\30\2\u01dd\u01de\b\24\1\2\u01de\u0202\3\2\2\2\u01df"+
		"\u01e0\5\60\31\2\u01e0\u01e1\b\24\1\2\u01e1\u0202\3\2\2\2\u01e2\u01e3"+
		"\5\62\32\2\u01e3\u01e4\b\24\1\2\u01e4\u0202\3\2\2\2\u01e5\u01e6\5,\27"+
		"\2\u01e6\u01e7\b\24\1\2\u01e7\u0202\3\2\2\2\u01e8\u01e9\7\t\2\2\u01e9"+
		"\u01ea\5$\23\2\u01ea\u01eb\7\62\2\2\u01eb\u01ec\b\24\1\2\u01ec\u0202\3"+
		"\2\2\2\u01ed\u01ee\7/\2\2\u01ee\u01ef\5$\23\2\u01ef\u01f0\7/\2\2\u01f0"+
		"\u01f1\b\24\1\2\u01f1\u0202\3\2\2\2\u01f2\u01f3\7\25\2\2\u01f3\u01f4\5"+
		"$\23\2\u01f4\u01f5\7\60\2\2\u01f5\u01f6\b\24\1\2\u01f6\u0202\3\2\2\2\u01f7"+
		"\u01f8\7\25\2\2\u01f8\u01f9\5$\23\2\u01f9\u01fa\7\13\2\2\u01fa\u01fb\5"+
		"$\23\2\u01fb\u01fc\7\60\2\2\u01fc\u01fd\b\24\1\2\u01fd\u0202\3\2\2\2\u01fe"+
		"\u01ff\5(\25\2\u01ff\u0200\b\24\1\2\u0200\u0202\3\2\2\2\u0201\u01cc\3"+
		"\2\2\2\u0201\u01d1\3\2\2\2\u0201\u01d5\3\2\2\2\u0201\u01dc\3\2\2\2\u0201"+
		"\u01df\3\2\2\2\u0201\u01e2\3\2\2\2\u0201\u01e5\3\2\2\2\u0201\u01e8\3\2"+
		"\2\2\u0201\u01ed\3\2\2\2\u0201\u01f2\3\2\2\2\u0201\u01f7\3\2\2\2\u0201"+
		"\u01fe\3\2\2\2\u0202\u0210\3\2\2\2\u0203\u0204\f\7\2\2\u0204\u0205\7+"+
		"\2\2\u0205\u0206\5&\24\b\u0206\u0207\b\24\1\2\u0207\u020f\3\2\2\2\u0208"+
		"\u020a\f\3\2\2\u0209\u020b\7+\2\2\u020a\u0209\3\2\2\2\u020a\u020b\3\2"+
		"\2\2\u020b\u020c\3\2\2\2\u020c\u020d\7\35\2\2\u020d\u020f\b\24\1\2\u020e"+
		"\u0203\3\2\2\2\u020e\u0208\3\2\2\2\u020f\u0212\3\2\2\2\u0210\u020e\3\2"+
		"\2\2\u0210\u0211\3\2\2\2\u0211\'\3\2\2\2\u0212\u0210\3\2\2\2\u0213\u0214"+
		"\b\25\1\2\u0214\u0215\5.\30\2\u0215\u0216\b\25\1\2\u0216\u023a\3\2\2\2"+
		"\u0217\u0218\5\60\31\2\u0218\u0219\b\25\1\2\u0219\u023a\3\2\2\2\u021a"+
		"\u021b\5\62\32\2\u021b\u021c\b\25\1\2\u021c\u023a\3\2\2\2\u021d\u021e"+
		"\5,\27\2\u021e\u021f\b\25\1\2\u021f\u023a\3\2\2\2\u0220\u0221\7\t\2\2"+
		"\u0221\u0222\5$\23\2\u0222\u0223\7\62\2\2\u0223\u0224\b\25\1\2\u0224\u023a"+
		"\3\2\2\2\u0225\u0226\7/\2\2\u0226\u0227\5$\23\2\u0227\u0228\7/\2\2\u0228"+
		"\u0229\b\25\1\2\u0229\u023a\3\2\2\2\u022a\u022b\7\25\2\2\u022b\u022c\5"+
		"$\23\2\u022c\u022d\7\60\2\2\u022d\u022e\b\25\1\2\u022e\u023a\3\2\2\2\u022f"+
		"\u0230\7\25\2\2\u0230\u0231\5$\23\2\u0231\u0232\7\13\2\2\u0232\u0233\5"+
		"$\23\2\u0233\u0234\7\60\2\2\u0234\u0235\b\25\1\2\u0235\u023a\3\2\2\2\u0236"+
		"\u0237\5*\26\2\u0237\u0238\b\25\1\2\u0238\u023a\3\2\2\2\u0239\u0213\3"+
		"\2\2\2\u0239\u0217\3\2\2\2\u0239\u021a\3\2\2\2\u0239\u021d\3\2\2\2\u0239"+
		"\u0220\3\2\2\2\u0239\u0225\3\2\2\2\u0239\u022a\3\2\2\2\u0239\u022f\3\2"+
		"\2\2\u0239\u0236\3\2\2\2\u023a\u0242\3\2\2\2\u023b\u023c\f\3\2\2\u023c"+
		"\u023d\7\37\2\2\u023d\u023e\5(\25\4\u023e\u023f\b\25\1\2\u023f\u0241\3"+
		"\2\2\2\u0240\u023b\3\2\2\2\u0241\u0244\3\2\2\2\u0242\u0240\3\2\2\2\u0242"+
		"\u0243\3\2\2\2\u0243)\3\2\2\2\u0244\u0242\3\2\2\2\u0245\u0246\b\26\1\2"+
		"\u0246\u0247\5.\30\2\u0247\u0248\b\26\1\2\u0248\u0269\3\2\2\2\u0249\u024a"+
		"\5\60\31\2\u024a\u024b\b\26\1\2\u024b\u0269\3\2\2\2\u024c\u024d\5\62\32"+
		"\2\u024d\u024e\b\26\1\2\u024e\u0269\3\2\2\2\u024f\u0250\5,\27\2\u0250"+
		"\u0251\b\26\1\2\u0251\u0269\3\2\2\2\u0252\u0253\7\t\2\2\u0253\u0254\5"+
		"$\23\2\u0254\u0255\7\62\2\2\u0255\u0256\b\26\1\2\u0256\u0269\3\2\2\2\u0257"+
		"\u0258\7/\2\2\u0258\u0259\5$\23\2\u0259\u025a\7/\2\2\u025a\u025b\b\26"+
		"\1\2\u025b\u0269\3\2\2\2\u025c\u025d\7\25\2\2\u025d\u025e\5$\23\2\u025e"+
		"\u025f\7\60\2\2\u025f\u0260\b\26\1\2\u0260\u0269\3\2\2\2\u0261\u0262\7"+
		"\25\2\2\u0262\u0263\5$\23\2\u0263\u0264\7\13\2\2\u0264\u0265\5$\23\2\u0265"+
		"\u0266\7\60\2\2\u0266\u0267\b\26\1\2\u0267\u0269\3\2\2\2\u0268\u0245\3"+
		"\2\2\2\u0268\u0249\3\2\2\2\u0268\u024c\3\2\2\2\u0268\u024f\3\2\2\2\u0268"+
		"\u0252\3\2\2\2\u0268\u0257\3\2\2\2\u0268\u025c\3\2\2\2\u0268\u0261\3\2"+
		"\2\2\u0269\u0271\3\2\2\2\u026a\u026b\f\3\2\2\u026b\u026c\7\7\2\2\u026c"+
		"\u026d\5*\26\4\u026d\u026e\b\26\1\2\u026e\u0270\3\2\2\2\u026f\u026a\3"+
		"\2\2\2\u0270\u0273\3\2\2\2\u0271\u026f\3\2\2\2\u0271\u0272\3\2\2\2\u0272"+
		"+\3\2\2\2\u0273\u0271\3\2\2\2\u0274\u0275\t\3\2\2\u0275\u0276\7\t\2\2"+
		"\u0276\u0277\5$\23\2\u0277\u0278\7\62\2\2\u0278\u0279\b\27\1\2\u0279\u028f"+
		"\3\2\2\2\u027a\u027b\t\4\2\2\u027b\u027c\7\t\2\2\u027c\u027d\5$\23\2\u027d"+
		"\u027e\7\62\2\2\u027e\u027f\b\27\1\2\u027f\u028f\3\2\2\2\u0280\u0281\t"+
		"\5\2\2\u0281\u0282\7\t\2\2\u0282\u0283\5$\23\2\u0283\u0284\7\62\2\2\u0284"+
		"\u0285\b\27\1\2\u0285\u028f\3\2\2\2\u0286\u0287\t\6\2\2\u0287\u0288\7"+
		"\t\2\2\u0288\u0289\5$\23\2\u0289\u028a\7\13\2\2\u028a\u028b\5$\23\2\u028b"+
		"\u028c\7\62\2\2\u028c\u028d\b\27\1\2\u028d\u028f\3\2\2\2\u028e\u0274\3"+
		"\2\2\2\u028e\u027a\3\2\2\2\u028e\u0280\3\2\2\2\u028e\u0286\3\2\2\2\u028f"+
		"-\3\2\2\2\u0290\u0291\7\33\2\2\u0291\u0297\b\30\1\2\u0292\u0293\7\20\2"+
		"\2\u0293\u0297\b\30\1\2\u0294\u0295\7 \2\2\u0295\u0297\b\30\1\2\u0296"+
		"\u0290\3\2\2\2\u0296\u0292\3\2\2\2\u0296\u0294\3\2\2\2\u0297/\3\2\2\2"+
		"\u0298\u0299\7H\2\2\u0299\u029a\b\31\1\2\u029a\61\3\2\2\2\u029b\u029d"+
		"\7\36\2\2\u029c\u029b\3\2\2\2\u029c\u029d\3\2\2\2\u029d\u029e\3\2\2\2"+
		"\u029e\u029f\t\7\2\2\u029f\u02a4\b\32\1\2\u02a0\u02a1\7\65\2\2\u02a1\u02a2"+
		"\t\7\2\2\u02a2\u02a4\b\32\1\2\u02a3\u029c\3\2\2\2\u02a3\u02a0\3\2\2\2"+
		"\u02a4\63\3\2\2\2\u02a5\u02a7\7\25\2\2\u02a6\u02a8\7\36\2\2\u02a7\u02a6"+
		"\3\2\2\2\u02a7\u02a8\3\2\2\2\u02a8\u02a9\3\2\2\2\u02a9\u02aa\t\7\2\2\u02aa"+
		"\u02ac\7\13\2\2\u02ab\u02ad\7\36\2\2\u02ac\u02ab\3\2\2\2\u02ac\u02ad\3"+
		"\2\2\2\u02ad\u02ae\3\2\2\2\u02ae\u02af\t\7\2\2\u02af\u02b0\7\60\2\2\u02b0"+
		"\u02f7\b\33\1\2\u02b1\u02b3\7\25\2\2\u02b2\u02b4\7\36\2\2\u02b3\u02b2"+
		"\3\2\2\2\u02b3\u02b4\3\2\2\2\u02b4\u02b5\3\2\2\2\u02b5\u02b6\t\7\2\2\u02b6"+
		"\u02b7\7\13\2\2\u02b7\u02b8\7\65\2\2\u02b8\u02b9\t\7\2\2\u02b9\u02ba\7"+
		"\60\2\2\u02ba\u02f7\b\33\1\2\u02bb\u02bc\7\25\2\2\u02bc\u02bd\7\65\2\2"+
		"\u02bd\u02be\t\7\2\2\u02be\u02c0\7\13\2\2\u02bf\u02c1\7\36\2\2\u02c0\u02bf"+
		"\3\2\2\2\u02c0\u02c1\3\2\2\2\u02c1\u02c2\3\2\2\2\u02c2\u02c3\t\7\2\2\u02c3"+
		"\u02c4\7\60\2\2\u02c4\u02f7\b\33\1\2\u02c5\u02c6\7\25\2\2\u02c6\u02c7"+
		"\7\65\2\2\u02c7\u02c8\t\7\2\2\u02c8\u02c9\7\13\2\2\u02c9\u02ca\7\65\2"+
		"\2\u02ca\u02cb\t\7\2\2\u02cb\u02cc\7\60\2\2\u02cc\u02f7\b\33\1\2\u02cd"+
		"\u02cf\7\36\2\2\u02ce\u02cd\3\2\2\2\u02ce\u02cf\3\2\2\2\u02cf\u02d0\3"+
		"\2\2\2\u02d0\u02d1\t\7\2\2\u02d1\u02d2\7\36\2\2\u02d2\u02d3\t\7\2\2\u02d3"+
		"\u02d4\7\35\2\2\u02d4\u02f7\b\33\1\2\u02d5\u02d7\7\36\2\2\u02d6\u02d5"+
		"\3\2\2\2\u02d6\u02d7\3\2\2\2\u02d7\u02d8\3\2\2\2\u02d8\u02d9\t\7\2\2\u02d9"+
		"\u02da\7\65\2\2\u02da\u02db\t\7\2\2\u02db\u02dc\7\35\2\2\u02dc\u02f7\b"+
		"\33\1\2\u02dd\u02df\7\36\2\2\u02de\u02dd\3\2\2\2\u02de\u02df\3\2\2\2\u02df"+
		"\u02e0\3\2\2\2\u02e0\u02e1\t\7\2\2\u02e1\u02e2\7\35\2\2\u02e2\u02f7\b"+
		"\33\1\2\u02e3\u02e4\7\65\2\2\u02e4\u02e5\t\7\2\2\u02e5\u02e6\7\36\2\2"+
		"\u02e6\u02e7\t\7\2\2\u02e7\u02e8\7\35\2\2\u02e8\u02f7\b\33\1\2\u02e9\u02ea"+
		"\7\65\2\2\u02ea\u02eb\t\7\2\2\u02eb\u02ec\7\65\2\2\u02ec\u02ed\t\7\2\2"+
		"\u02ed\u02ee\7\35\2\2\u02ee\u02f7\b\33\1\2\u02ef\u02f0\7\65\2\2\u02f0"+
		"\u02f1\t\7\2\2\u02f1\u02f2\7\35\2\2\u02f2\u02f7\b\33\1\2\u02f3\u02f4\5"+
		"\62\32\2\u02f4\u02f5\b\33\1\2\u02f5\u02f7\3\2\2\2\u02f6\u02a5\3\2\2\2"+
		"\u02f6\u02b1\3\2\2\2\u02f6\u02bb\3\2\2\2\u02f6\u02c5\3\2\2\2\u02f6\u02ce"+
		"\3\2\2\2\u02f6\u02d6\3\2\2\2\u02f6\u02de\3\2\2\2\u02f6\u02e3\3\2\2\2\u02f6"+
		"\u02e9\3\2\2\2\u02f6\u02ef\3\2\2\2\u02f6\u02f3\3\2\2\2\u02f7\65\3\2\2"+
		"\2\u02f8\u02f9\7A\2\2\u02f9\u02fa\7H\2\2\u02fa\u02fb\b\34\1\2\u02fb\u02fd"+
		"\7\4\2\2\u02fc\u02fe\58\35\2\u02fd\u02fc\3\2\2\2\u02fe\u02ff\3\2\2\2\u02ff"+
		"\u02fd\3\2\2\2\u02ff\u0300\3\2\2\2\u0300\u0301\3\2\2\2\u0301\u0302\7#"+
		"\2\2\u0302\67\3\2\2\2\u0303\u0304\7.\2\2\u0304\u0305\5D#\2\u0305\u0306"+
		"\7\60\2\2\u0306\u0307\5D#\2\u0307\u0308\7\13\2\2\u0308\u0309\7E\2\2\u0309"+
		"\u030a\7\13\2\2\u030a\u030b\5$\23\2\u030b\u030c\7\26\2\2\u030c\u030d\7"+
		"!\2\2\u030d\u030e\b\35\1\2\u030e\u031a\3\2\2\2\u030f\u0310\7.\2\2\u0310"+
		"\u0311\5D#\2\u0311\u0312\7\60\2\2\u0312\u0313\5D#\2\u0313\u0314\7\13\2"+
		"\2\u0314\u0315\7E\2\2\u0315\u0316\7\26\2\2\u0316\u0317\7!\2\2\u0317\u0318"+
		"\b\35\1\2\u0318\u031a\3\2\2\2\u0319\u0303\3\2\2\2\u0319\u030f\3\2\2\2"+
		"\u031a9\3\2\2\2\u031b\u031c\7<\2\2\u031c\u031d\b\36\1\2\u031d\u0321\7"+
		"\4\2\2\u031e\u0320\5<\37\2\u031f\u031e\3\2\2\2\u0320\u0323\3\2\2\2\u0321"+
		"\u031f\3\2\2\2\u0321\u0322\3\2\2\2\u0322\u0324\3\2\2\2\u0323\u0321\3\2"+
		"\2\2\u0324\u0325\7#\2\2\u0325;\3\2\2\2\u0326\u0327\b\37\1\2\u0327\u0328"+
		"\5\30\r\2\u0328\u0329\b\37\1\2\u0329=\3\2\2\2\u032a\u032b\7B\2\2\u032b"+
		"\u032c\7\t\2\2\u032c\u032d\5@!\2\u032d\u032e\7\62\2\2\u032e\u032f\7.\2"+
		"\2\u032f\u0330\t\7\2\2\u0330\u0331\7\26\2\2\u0331\u0332\7\4\2\2\u0332"+
		"\u0333\5B\"\2\u0333\u0334\7#\2\2\u0334\u0335\b \1\2\u0335?\3\2\2\2\u0336"+
		"\u0337\b!\1\2\u0337\u0338\5$\23\2\u0338\u0339\t\2\2\2\u0339\u033a\5$\23"+
		"\2\u033a\u033b\b!\1\2\u033b\u0343\3\2\2\2\u033c\u033d\f\3\2\2\u033d\u033e"+
		"\t\b\2\2\u033e\u033f\5@!\4\u033f\u0340\b!\1\2\u0340\u0342\3\2\2\2\u0341"+
		"\u033c\3\2\2\2\u0342\u0345\3\2\2\2\u0343\u0341\3\2\2\2\u0343\u0344\3\2"+
		"\2\2\u0344A\3\2\2\2\u0345\u0343\3\2\2\2\u0346\u0347\5$\23\2\u0347\u0348"+
		"\b\"\1\2\u0348\u0360\3\2\2\2\u0349\u034a\5$\23\2\u034a\u034b\7\13\2\2"+
		"\u034b\u034c\5$\23\2\u034c\u034d\7\13\2\2\u034d\u034e\5$\23\2\u034e\u034f"+
		"\b\"\1\2\u034f\u0360\3\2\2\2\u0350\u0351\5$\23\2\u0351\u0352\7\13\2\2"+
		"\u0352\u0353\5$\23\2\u0353\u0354\7\13\2\2\u0354\u0355\5$\23\2\u0355\u0356"+
		"\7\13\2\2\u0356\u0357\5$\23\2\u0357\u0358\b\"\1\2\u0358\u0360\3\2\2\2"+
		"\u0359\u035a\7H\2\2\u035a\u035b\7.\2\2\u035b\u035c\5$\23\2\u035c\u035d"+
		"\7\26\2\2\u035d\u035e\b\"\1\2\u035e\u0360\3\2\2\2\u035f\u0346\3\2\2\2"+
		"\u035f\u0349\3\2\2\2\u035f\u0350\3\2\2\2\u035f\u0359\3\2\2\2\u0360C\3"+
		"\2\2\2\u0361\u0362\7\t\2\2\u0362\u0363\t\7\2\2\u0363\u0364\7\13\2\2\u0364"+
		"\u0365\t\7\2\2\u0365\u0366\7\13\2\2\u0366\u0367\t\7\2\2\u0367\u0368\7"+
		"\13\2\2\u0368\u0369\t\7\2\2\u0369\u036a\7\62\2\2\u036a\u036f\b#\1\2\u036b"+
		"\u036c\7\32\2\2\u036c\u036d\7C\2\2\u036d\u036f\b#\1\2\u036e\u0361\3\2"+
		"\2\2\u036e\u036b\3\2\2\2\u036fE\3\2\2\2\u0370\u0371\7\2\2\3\u0371G\3\2"+
		"\2\2\67^bfsw|\u0087\u009a\u00a5\u00b4\u00c9\u00db\u00e8\u00f0\u00f3\u00ff"+
		"\u011c\u0125\u0142\u014b\u0168\u0171\u018e\u01bb\u01c7\u01c9\u01d7\u0201"+
		"\u020a\u020e\u0210\u0239\u0242\u0268\u0271\u028e\u0296\u029c\u02a3\u02a7"+
		"\u02ac\u02b3\u02c0\u02ce\u02d6\u02de\u02f6\u02ff\u0319\u0321\u0343\u035f"+
		"\u036e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}