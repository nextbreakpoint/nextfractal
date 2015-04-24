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
		T__49=1, T__48=2, T__47=3, T__46=4, T__45=5, T__44=6, T__43=7, T__42=8, 
		T__41=9, T__40=10, T__39=11, T__38=12, T__37=13, T__36=14, T__35=15, T__34=16, 
		T__33=17, T__32=18, T__31=19, T__30=20, T__29=21, T__28=22, T__27=23, 
		T__26=24, T__25=25, T__24=26, T__23=27, T__22=28, T__21=29, T__20=30, 
		T__19=31, T__18=32, T__17=33, T__16=34, T__15=35, T__14=36, T__13=37, 
		T__12=38, T__11=39, T__10=40, T__9=41, T__8=42, T__7=43, T__6=44, T__5=45, 
		T__4=46, T__3=47, T__2=48, T__1=49, T__0=50, FRACTAL=51, ORBIT=52, TRAP=53, 
		BEGIN=54, LOOP=55, END=56, INIT=57, IF=58, COLOR=59, PALETTE=60, RULE=61, 
		ARGB=62, RATIONAL=63, INTEGER=64, PATHOP_1POINTS=65, PATHOP_2POINTS=66, 
		VARIABLE=67, COMMENT=68, WHITESPACE=69;
	public static final String[] tokenNames = {
		"<INVALID>", "'cos'", "'{'", "'='", "'asin'", "'^'", "'im'", "'('", "'min'", 
		"','", "'pha'", "'re'", "'atan'", "'sqrt'", "'pi'", "'ceil'", "'mod'", 
		"'>='", "'log'", "'<'", "']'", "'abs'", "'<>'", "'#'", "'e'", "'floor'", 
		"'i'", "'+'", "'/'", "'2pi'", "';'", "'max'", "'}'", "'mod2'", "'?'", 
		"'sin'", "'<='", "'pow'", "'~?'", "'&'", "'*'", "'tan'", "'atan2'", "'['", 
		"'|'", "'>'", "'acos'", "')'", "'exp'", "'hypot'", "'-'", "'fractal'", 
		"'orbit'", "'trap'", "'begin'", "'loop'", "'end'", "'init'", "'if'", "'color'", 
		"'palette'", "'rule'", "ARGB", "RATIONAL", "INTEGER", "PATHOP_1POINTS", 
		"PATHOP_2POINTS", "VARIABLE", "COMMENT", "WHITESPACE"
	};
	public static final int
		RULE_fractal = 0, RULE_orbit = 1, RULE_color = 2, RULE_begin = 3, RULE_loop = 4, 
		RULE_end = 5, RULE_trap = 6, RULE_pathop = 7, RULE_beginstatement = 8, 
		RULE_loopstatement = 9, RULE_endstatement = 10, RULE_statement = 11, RULE_variablelist = 12, 
		RULE_conditionexp = 13, RULE_expression = 14, RULE_expression2 = 15, RULE_expression3 = 16, 
		RULE_expression4 = 17, RULE_function = 18, RULE_constant = 19, RULE_variable = 20, 
		RULE_real = 21, RULE_complex = 22, RULE_palette = 23, RULE_paletteelement = 24, 
		RULE_colorinit = 25, RULE_colorstatement = 26, RULE_colorrule = 27, RULE_ruleexp = 28, 
		RULE_colorexp = 29, RULE_colorargb = 30, RULE_eof = 31;
	public static final String[] ruleNames = {
		"fractal", "orbit", "color", "begin", "loop", "end", "trap", "pathop", 
		"beginstatement", "loopstatement", "endstatement", "statement", "variablelist", 
		"conditionexp", "expression", "expression2", "expression3", "expression4", 
		"function", "constant", "variable", "real", "complex", "palette", "paletteelement", 
		"colorinit", "colorstatement", "colorrule", "ruleexp", "colorexp", "colorargb", 
		"eof"
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
			setState(64); ((FractalContext)_localctx).f = match(FRACTAL);

					builder.setFractal(new ASTFractal(((FractalContext)_localctx).f));
				
			setState(66); match(2);
			setState(67); orbit();
			setState(68); color();
			setState(69); match(32);
			setState(70); eof();
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
			setState(72); ((OrbitContext)_localctx).o = match(ORBIT);
			setState(73); match(43);
			setState(74); ((OrbitContext)_localctx).ra = complex();
			setState(75); match(9);
			setState(76); ((OrbitContext)_localctx).rb = complex();
			setState(77); match(20);

					builder.setOrbit(new ASTOrbit(((OrbitContext)_localctx).o, new ASTRegion(((OrbitContext)_localctx).ra.result, ((OrbitContext)_localctx).rb.result)));
				
			setState(79); match(43);
			setState(80); ((OrbitContext)_localctx).v = variablelist(0);
			setState(81); match(20);
			setState(82); match(2);
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TRAP) {
				{
				{
				setState(83); trap();
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(90);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(89); begin();
				}
			}

			setState(92); loop();
			setState(94);
			_la = _input.LA(1);
			if (_la==END) {
				{
				setState(93); end();
				}
			}

			setState(96); match(32);
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
			setState(98); ((ColorContext)_localctx).c = match(COLOR);
			setState(99); match(43);
			setState(100); ((ColorContext)_localctx).argb = colorargb();
			setState(101); match(20);
			 
					builder.setColor(new ASTColor(((ColorContext)_localctx).c, ((ColorContext)_localctx).argb.result));
				
			setState(103); match(2);
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PALETTE) {
				{
				{
				setState(104); palette();
				}
				}
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(111);
			_la = _input.LA(1);
			if (_la==INIT) {
				{
				setState(110); colorinit();
				}
			}

			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RULE) {
				{
				{
				setState(113); colorrule();
				}
				}
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(119); match(32);
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
			setState(121); ((BeginContext)_localctx).b = match(BEGIN);
			 
					builder.setOrbitBegin(new ASTOrbitBegin(((BeginContext)_localctx).b));
				
			setState(123); match(2);
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IF || _la==VARIABLE) {
				{
				{
				setState(124); beginstatement();
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(130); match(32);
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
			setState(132); ((LoopContext)_localctx).l = match(LOOP);
			setState(133); match(43);
			setState(134); ((LoopContext)_localctx).lb = match(INTEGER);
			setState(135); match(9);
			setState(136); ((LoopContext)_localctx).le = match(INTEGER);
			setState(137); match(20);
			setState(138); match(7);
			setState(139); ((LoopContext)_localctx).e = conditionexp(0);
			setState(140); match(47);

					builder.setOrbitLoop(new ASTOrbitLoop(((LoopContext)_localctx).l, builder.parseInt((((LoopContext)_localctx).lb!=null?((LoopContext)_localctx).lb.getText():null)), builder.parseInt((((LoopContext)_localctx).le!=null?((LoopContext)_localctx).le.getText():null)), ((LoopContext)_localctx).e.result));
				
			setState(142); match(2);
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IF || _la==VARIABLE) {
				{
				{
				setState(143); loopstatement();
				}
				}
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(149); match(32);
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
			setState(151); ((EndContext)_localctx).e = match(END);

					builder.setOrbitEnd(new ASTOrbitEnd(((EndContext)_localctx).e));		
				
			setState(153); match(2);
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IF || _la==VARIABLE) {
				{
				{
				setState(154); endstatement();
				}
				}
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(160); match(32);
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
			setState(162); ((TrapContext)_localctx).t = match(TRAP);
			setState(163); ((TrapContext)_localctx).n = match(VARIABLE);
			setState(164); match(43);
			setState(165); ((TrapContext)_localctx).c = complex();
			setState(166); match(20);

					builder.addOrbitTrap(new ASTOrbitTrap(((TrapContext)_localctx).t, (((TrapContext)_localctx).n!=null?((TrapContext)_localctx).n.getText():null), ((TrapContext)_localctx).c.result));
				
			setState(168); match(2);
			setState(172);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PATHOP_1POINTS || _la==PATHOP_2POINTS) {
				{
				{
				setState(169); pathop();
				}
				}
				setState(174);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(175); match(32);
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
			setState(193);
			switch (_input.LA(1)) {
			case PATHOP_1POINTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(177); ((PathopContext)_localctx).o = match(PATHOP_1POINTS);
				setState(178); match(7);
				setState(179); ((PathopContext)_localctx).c = complex();
				setState(180); match(47);
				setState(181); match(30);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c.result));
					
				}
				break;
			case PATHOP_2POINTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(184); ((PathopContext)_localctx).o = match(PATHOP_2POINTS);
				setState(185); match(7);
				setState(186); ((PathopContext)_localctx).c1 = complex();
				setState(187); match(9);
				setState(188); ((PathopContext)_localctx).c2 = complex();
				setState(189); match(47);
				setState(190); match(30);

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
		public StatementContext s;
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
			setState(195); ((BeginstatementContext)_localctx).s = statement();

					builder.addBeginStatement(((BeginstatementContext)_localctx).s.result);
				
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
		public StatementContext s;
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
			setState(198); ((LoopstatementContext)_localctx).s = statement();

					builder.addLoopStatement(((LoopstatementContext)_localctx).s.result);
				
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
		public StatementContext s;
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
			setState(201); ((EndstatementContext)_localctx).s = statement();

					builder.addEndStatement(((EndstatementContext)_localctx).s.result);
				
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
		public ASTStatement result;
		public Token v;
		public ExpressionContext e;
		public Token f;
		public ConditionexpContext c;
		public StatementContext s;
		public TerminalNode IF() { return getToken(MandelbrotParser.IF, 0); }
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
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
		try {
			setState(219);
			switch (_input.LA(1)) {
			case VARIABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(204); ((StatementContext)_localctx).v = match(VARIABLE);
				setState(205); match(3);
				setState(206); ((StatementContext)_localctx).e = expression(0);
				setState(207); match(30);

						((StatementContext)_localctx).result =  new ASTAssignStatement(((StatementContext)_localctx).v, (((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result);
						builder.registerVariable((((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result.isReal(), ((StatementContext)_localctx).v);
					
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(210); ((StatementContext)_localctx).f = match(IF);
				setState(211); match(7);
				setState(212); ((StatementContext)_localctx).c = conditionexp(0);
				setState(213); match(47);
				setState(214); match(2);
				setState(215); ((StatementContext)_localctx).s = statement();
				setState(216); match(32);

						((StatementContext)_localctx).result =  new ASTConditionalStatement(((StatementContext)_localctx).f, ((StatementContext)_localctx).c.result, ((StatementContext)_localctx).s.result);
					
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
			setState(222); ((VariablelistContext)_localctx).v = match(VARIABLE);

					builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(231);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VariablelistContext(_parentctx, _parentState);
					_localctx.vl = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_variablelist);
					setState(225);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(226); match(9);
					setState(227); ((VariablelistContext)_localctx).v = match(VARIABLE);

					          		builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
					          	
					}
					} 
				}
				setState(233);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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
		public Token l;
		public ConditionexpContext c2;
		public List<ConditionexpContext> conditionexp() {
			return getRuleContexts(ConditionexpContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
		public ConditionexpContext conditionexp(int i) {
			return getRuleContext(ConditionexpContext.class,i);
		}
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
			setState(250);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(235); ((ConditionexpContext)_localctx).e1 = expression(0);
				setState(236);
				((ConditionexpContext)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 22) | (1L << 36) | (1L << 45))) != 0)) ) {
					((ConditionexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(237); ((ConditionexpContext)_localctx).e2 = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionCompareOp(((ConditionexpContext)_localctx).e1.result.getLocation(), (((ConditionexpContext)_localctx).o!=null?((ConditionexpContext)_localctx).o.getText():null), ((ConditionexpContext)_localctx).e1.result, ((ConditionexpContext)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(240); ((ConditionexpContext)_localctx).v = match(VARIABLE);
				setState(241); match(34);
				setState(242); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, true);
					
				}
				break;

			case 3:
				{
				setState(245); ((ConditionexpContext)_localctx).v = match(VARIABLE);
				setState(246); match(38);
				setState(247); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, false);
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(259);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConditionexpContext(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp);
					setState(252);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(253);
					((ConditionexpContext)_localctx).l = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 39) | (1L << 44))) != 0)) ) {
						((ConditionexpContext)_localctx).l = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(254); ((ConditionexpContext)_localctx).c2 = conditionexp(2);

					          		((ConditionexpContext)_localctx).result =  new ASTConditionLogicOp(((ConditionexpContext)_localctx).c1.result.getLocation(), (((ConditionexpContext)_localctx).l!=null?((ConditionexpContext)_localctx).l.getText():null), ((ConditionexpContext)_localctx).c1.result, ((ConditionexpContext)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(261);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
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

	public static class ExpressionContext extends ParserRuleContext {
		public ASTExpression result;
		public ExpressionContext e1;
		public Token s;
		public ExpressionContext e;
		public ConstantContext p;
		public VariableContext v;
		public ComplexContext c;
		public FunctionContext f;
		public Token t;
		public Token m;
		public Token a;
		public Expression2Context e3;
		public ExpressionContext e2;
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
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(263); ((ExpressionContext)_localctx).s = match(50);
				setState(264); ((ExpressionContext)_localctx).e = expression(5);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 2:
				{
				setState(267); ((ExpressionContext)_localctx).s = match(27);
				setState(268); ((ExpressionContext)_localctx).e = expression(4);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 3:
				{
				setState(271); ((ExpressionContext)_localctx).p = constant();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).p.result;
					
				}
				break;

			case 4:
				{
				setState(274); ((ExpressionContext)_localctx).v = variable();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).v.result;
					
				}
				break;

			case 5:
				{
				setState(277); ((ExpressionContext)_localctx).c = complex();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).c.result;
					
				}
				break;

			case 6:
				{
				setState(280); ((ExpressionContext)_localctx).f = function();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).f.result;
					
				}
				break;

			case 7:
				{
				setState(283); ((ExpressionContext)_localctx).t = match(7);
				setState(284); ((ExpressionContext)_localctx).e = expression(0);
				setState(285); match(47);

						((ExpressionContext)_localctx).result =  new ASTParen(((ExpressionContext)_localctx).t, ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 8:
				{
				setState(288); ((ExpressionContext)_localctx).m = match(44);
				setState(289); ((ExpressionContext)_localctx).e = expression(0);
				setState(290); match(44);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).m, "mod", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 9:
				{
				setState(293); ((ExpressionContext)_localctx).a = match(19);
				setState(294); ((ExpressionContext)_localctx).e = expression(0);
				setState(295); match(45);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).a, "pha", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 10:
				{
				setState(298); ((ExpressionContext)_localctx).e3 = expression2(0);

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e3.result;	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(315);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(313);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(303);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(304); ((ExpressionContext)_localctx).s = match(27);
						setState(305); ((ExpressionContext)_localctx).e2 = expression(4);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;

					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(308);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(309); ((ExpressionContext)_localctx).s = match(50);
						setState(310); ((ExpressionContext)_localctx).e2 = expression(3);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					}
					} 
				}
				setState(317);
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

	public static class Expression2Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression2Context e1;
		public Expression2Context e2;
		public Token i;
		public ConstantContext p;
		public VariableContext v;
		public RealContext r;
		public FunctionContext f;
		public Token t;
		public ExpressionContext e;
		public Token m;
		public Token a;
		public Expression3Context e3;
		public Token s;
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public RealContext real() {
			return getRuleContext(RealContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_expression2, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(319); ((Expression2Context)_localctx).i = match(26);
				setState(321);
				_la = _input.LA(1);
				if (_la==40) {
					{
					setState(320); match(40);
					}
				}

				setState(323); ((Expression2Context)_localctx).e2 = expression2(3);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(326); ((Expression2Context)_localctx).p = constant();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).p.result;
					
				}
				break;

			case 3:
				{
				setState(329); ((Expression2Context)_localctx).v = variable();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).v.result;
					
				}
				break;

			case 4:
				{
				setState(332); ((Expression2Context)_localctx).r = real();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).r.result;
					
				}
				break;

			case 5:
				{
				setState(335); ((Expression2Context)_localctx).f = function();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).f.result;
					
				}
				break;

			case 6:
				{
				setState(338); ((Expression2Context)_localctx).t = match(7);
				setState(339); ((Expression2Context)_localctx).e = expression(0);
				setState(340); match(47);

						((Expression2Context)_localctx).result =  new ASTParen(((Expression2Context)_localctx).t, ((Expression2Context)_localctx).e.result);
					
				}
				break;

			case 7:
				{
				setState(343); ((Expression2Context)_localctx).m = match(44);
				setState(344); ((Expression2Context)_localctx).e = expression(0);
				setState(345); match(44);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).m, "mod", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(348); ((Expression2Context)_localctx).a = match(19);
				setState(349); ((Expression2Context)_localctx).e = expression(0);
				setState(350); match(45);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).a, "pha", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 9:
				{
				setState(353); ((Expression2Context)_localctx).e3 = expression3(0);

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(371);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(369);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(358);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(359); ((Expression2Context)_localctx).s = match(40);
						setState(360); ((Expression2Context)_localctx).e2 = expression2(5);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "*", ((Expression2Context)_localctx).e1.result, ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;

					case 2:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e2 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(363);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(365);
						_la = _input.LA(1);
						if (_la==40) {
							{
							setState(364); match(40);
							}
						}

						setState(367); ((Expression2Context)_localctx).i = match(26);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					}
					} 
				}
				setState(373);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_expression3, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(405);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(375); ((Expression3Context)_localctx).p = constant();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).p.result;
					
				}
				break;

			case 2:
				{
				setState(378); ((Expression3Context)_localctx).v = variable();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).v.result;
					
				}
				break;

			case 3:
				{
				setState(381); ((Expression3Context)_localctx).r = real();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).r.result;
					
				}
				break;

			case 4:
				{
				setState(384); ((Expression3Context)_localctx).f = function();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).f.result;
					
				}
				break;

			case 5:
				{
				setState(387); ((Expression3Context)_localctx).t = match(7);
				setState(388); ((Expression3Context)_localctx).e = expression(0);
				setState(389); match(47);

						((Expression3Context)_localctx).result =  new ASTParen(((Expression3Context)_localctx).t, ((Expression3Context)_localctx).e.result);
					
				}
				break;

			case 6:
				{
				setState(392); ((Expression3Context)_localctx).m = match(44);
				setState(393); ((Expression3Context)_localctx).e = expression(0);
				setState(394); match(44);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).m, "mod", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 7:
				{
				setState(397); ((Expression3Context)_localctx).a = match(19);
				setState(398); ((Expression3Context)_localctx).e = expression(0);
				setState(399); match(45);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).a, "pha", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(402); ((Expression3Context)_localctx).e3 = expression4(0);

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(414);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression3Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression3);
					setState(407);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(408); ((Expression3Context)_localctx).s = match(28);
					setState(409); ((Expression3Context)_localctx).e2 = expression3(3);

					          		((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).s, "/", ((Expression3Context)_localctx).e1.result, ((Expression3Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(416);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_expression4, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(445);
			switch (_input.LA(1)) {
			case 14:
			case 24:
			case 29:
				{
				setState(418); ((Expression4Context)_localctx).p = constant();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).p.result;
					
				}
				break;
			case VARIABLE:
				{
				setState(421); ((Expression4Context)_localctx).v = variable();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).v.result;
					
				}
				break;
			case 27:
			case 50:
			case RATIONAL:
			case INTEGER:
				{
				setState(424); ((Expression4Context)_localctx).r = real();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).r.result;
					
				}
				break;
			case 1:
			case 4:
			case 6:
			case 8:
			case 10:
			case 11:
			case 12:
			case 13:
			case 15:
			case 16:
			case 18:
			case 21:
			case 25:
			case 31:
			case 33:
			case 35:
			case 37:
			case 41:
			case 42:
			case 46:
			case 48:
			case 49:
				{
				setState(427); ((Expression4Context)_localctx).f = function();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).f.result;
					
				}
				break;
			case 7:
				{
				setState(430); ((Expression4Context)_localctx).t = match(7);
				setState(431); ((Expression4Context)_localctx).e = expression(0);
				setState(432); match(47);

						((Expression4Context)_localctx).result =  new ASTParen(((Expression4Context)_localctx).t, ((Expression4Context)_localctx).e.result);
					
				}
				break;
			case 44:
				{
				setState(435); ((Expression4Context)_localctx).m = match(44);
				setState(436); ((Expression4Context)_localctx).e = expression(0);
				setState(437); match(44);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).m, "mod", ((Expression4Context)_localctx).e.result);	
					
				}
				break;
			case 19:
				{
				setState(440); ((Expression4Context)_localctx).a = match(19);
				setState(441); ((Expression4Context)_localctx).e = expression(0);
				setState(442); match(45);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).a, "pha", ((Expression4Context)_localctx).e.result);	
					
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(454);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression4Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression4);
					setState(447);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(448); ((Expression4Context)_localctx).s = match(5);
					setState(449); ((Expression4Context)_localctx).e2 = expression4(2);

					          		((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).s, "^", ((Expression4Context)_localctx).e1.result, ((Expression4Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(456);
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
		enterRule(_localctx, 36, RULE_function);
		int _la;
		try {
			setState(483);
			switch (_input.LA(1)) {
			case 6:
			case 10:
			case 11:
			case 16:
			case 33:
				enterOuterAlt(_localctx, 1);
				{
				setState(457);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 10) | (1L << 11) | (1L << 16) | (1L << 33))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(458); match(7);
				setState(459); ((FunctionContext)_localctx).e = expression(0);
				setState(460); match(47);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), ((FunctionContext)_localctx).e.result);		
					
				}
				break;
			case 1:
			case 4:
			case 12:
			case 35:
			case 41:
			case 46:
				enterOuterAlt(_localctx, 2);
				{
				setState(463);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 4) | (1L << 12) | (1L << 35) | (1L << 41) | (1L << 46))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(464); match(7);
				setState(465); ((FunctionContext)_localctx).e = expression(0);
				setState(466); match(47);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 13:
			case 15:
			case 18:
			case 21:
			case 25:
			case 48:
				enterOuterAlt(_localctx, 3);
				{
				setState(469);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 13) | (1L << 15) | (1L << 18) | (1L << 21) | (1L << 25) | (1L << 48))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(470); match(7);
				setState(471); ((FunctionContext)_localctx).e = expression(0);
				setState(472); match(47);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 8:
			case 31:
			case 37:
			case 42:
			case 49:
				enterOuterAlt(_localctx, 4);
				{
				setState(475);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 8) | (1L << 31) | (1L << 37) | (1L << 42) | (1L << 49))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(476); match(7);
				setState(477); ((FunctionContext)_localctx).e1 = expression(0);
				setState(478); match(9);
				setState(479); ((FunctionContext)_localctx).e2 = expression(0);
				setState(480); match(47);

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
		enterRule(_localctx, 38, RULE_constant);
		try {
			setState(491);
			switch (_input.LA(1)) {
			case 24:
				enterOuterAlt(_localctx, 1);
				{
				setState(485); ((ConstantContext)_localctx).p = match(24);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.E);
					
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 2);
				{
				setState(487); ((ConstantContext)_localctx).p = match(14);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.PI);
					
				}
				break;
			case 29:
				enterOuterAlt(_localctx, 3);
				{
				setState(489); ((ConstantContext)_localctx).p = match(29);

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
		enterRule(_localctx, 40, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(493); ((VariableContext)_localctx).v = match(VARIABLE);

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
		enterRule(_localctx, 42, RULE_real);
		int _la;
		try {
			setState(504);
			switch (_input.LA(1)) {
			case 27:
			case RATIONAL:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(497);
				_la = _input.LA(1);
				if (_la==27) {
					{
					setState(496); match(27);
					}
				}

				setState(499);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((RealContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();

						((RealContext)_localctx).result =  new ASTNumber(((RealContext)_localctx).r, builder.parseDouble((((RealContext)_localctx).r!=null?((RealContext)_localctx).r.getText():null)));
					
				}
				break;
			case 50:
				enterOuterAlt(_localctx, 2);
				{
				setState(501); match(50);
				setState(502);
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
		enterRule(_localctx, 44, RULE_complex);
		int _la;
		try {
			setState(587);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(506); match(19);
				setState(508);
				_la = _input.LA(1);
				if (_la==27) {
					{
					setState(507); match(27);
					}
				}

				setState(510);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(511); match(9);
				setState(513);
				_la = _input.LA(1);
				if (_la==27) {
					{
					setState(512); match(27);
					}
				}

				setState(515);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(516); match(45);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(518); match(19);
				setState(520);
				_la = _input.LA(1);
				if (_la==27) {
					{
					setState(519); match(27);
					}
				}

				setState(522);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(523); match(9);
				setState(524); match(50);
				setState(525);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(526); match(45);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(528); match(19);
				setState(529); match(50);
				setState(530);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(531); match(9);
				setState(533);
				_la = _input.LA(1);
				if (_la==27) {
					{
					setState(532); match(27);
					}
				}

				setState(535);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(536); match(45);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(538); match(19);
				setState(539); match(50);
				setState(540);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(541); match(9);
				setState(542); match(50);
				setState(543);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(544); match(45);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(547);
				_la = _input.LA(1);
				if (_la==27) {
					{
					setState(546); match(27);
					}
				}

				setState(549);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(550); match(27);
				setState(551);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(552); match(26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(555);
				_la = _input.LA(1);
				if (_la==27) {
					{
					setState(554); match(27);
					}
				}

				setState(557);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(558); match(50);
				setState(559);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(560); match(26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(563);
				_la = _input.LA(1);
				if (_la==27) {
					{
					setState(562); match(27);
					}
				}

				setState(565);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(566); match(26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble((((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(568); match(50);
				setState(569);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(570); match(27);
				setState(571);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(572); match(26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(574); match(50);
				setState(575);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(576); match(50);
				setState(577);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(578); match(26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(580); match(50);
				setState(581);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(582); match(26);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(584); ((ComplexContext)_localctx).rn = real();

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
		enterRule(_localctx, 46, RULE_palette);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(589); ((PaletteContext)_localctx).p = match(PALETTE);
			setState(590); ((PaletteContext)_localctx).v = match(VARIABLE);

					builder.addPalette(new ASTPalette(((PaletteContext)_localctx).p, (((PaletteContext)_localctx).v!=null?((PaletteContext)_localctx).v.getText():null))); 
				
			setState(592); match(2);
			setState(594); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(593); paletteelement();
				}
				}
				setState(596); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==43 );
			setState(598); match(32);
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
		enterRule(_localctx, 48, RULE_paletteelement);
		try {
			setState(622);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(600); ((PaletteelementContext)_localctx).t = match(43);
				setState(601); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(602); match(45);
				setState(603); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(604); match(9);
				setState(605); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(606); match(9);
				setState(607); ((PaletteelementContext)_localctx).e = expression(0);
				setState(608); match(20);
				setState(609); match(30);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), ((PaletteelementContext)_localctx).e.result));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(612); ((PaletteelementContext)_localctx).t = match(43);
				setState(613); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(614); match(45);
				setState(615); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(616); match(9);
				setState(617); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(618); match(20);
				setState(619); match(30);

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
		enterRule(_localctx, 50, RULE_colorinit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(624); ((ColorinitContext)_localctx).i = match(INIT);

					builder.setColorContext(true);
					builder.setColorInit(new ASTColorInit(((ColorinitContext)_localctx).i));
				
			setState(626); match(2);
			setState(630);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IF || _la==VARIABLE) {
				{
				{
				setState(627); colorstatement();
				}
				}
				setState(632);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(633); match(32);
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
		public StatementContext s;
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
		enterRule(_localctx, 52, RULE_colorstatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(635); ((ColorstatementContext)_localctx).s = statement();

					builder.addColorStatement(((ColorstatementContext)_localctx).s.result);
				
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
		enterRule(_localctx, 54, RULE_colorrule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(638); ((ColorruleContext)_localctx).t = match(RULE);
			setState(639); match(7);
			setState(640); ((ColorruleContext)_localctx).r = ruleexp(0);
			setState(641); match(47);
			setState(642); match(43);
			setState(643);
			((ColorruleContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==RATIONAL || _la==INTEGER) ) {
				((ColorruleContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(644); match(20);
			setState(645); match(2);
			setState(646); ((ColorruleContext)_localctx).c = colorexp();
			setState(647); match(32);

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
		int _startState = 56;
		enterRecursionRule(_localctx, 56, RULE_ruleexp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(651); ((RuleexpContext)_localctx).e1 = expression(0);
			setState(652);
			((RuleexpContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 17) | (1L << 19) | (1L << 22) | (1L << 36) | (1L << 45))) != 0)) ) {
				((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(653); ((RuleexpContext)_localctx).e2 = expression(0);

					((RuleexpContext)_localctx).result =  new ASTRuleCompareOp(((RuleexpContext)_localctx).e1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).e1.result, ((RuleexpContext)_localctx).e2.result);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(663);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RuleexpContext(_parentctx, _parentState);
					_localctx.r1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_ruleexp);
					setState(656);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(657);
					((RuleexpContext)_localctx).o = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 39) | (1L << 44))) != 0)) ) {
						((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(658); ((RuleexpContext)_localctx).r2 = ruleexp(2);

					          		((RuleexpContext)_localctx).result =  new ASTRuleLogicOp(((RuleexpContext)_localctx).r1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).r1.result, ((RuleexpContext)_localctx).r2.result);
					          	
					}
					} 
				}
				setState(665);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
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
		enterRule(_localctx, 58, RULE_colorexp);
		try {
			setState(691);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(666); ((ColorexpContext)_localctx).e1 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(669); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(670); match(9);
				setState(671); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(672); match(9);
				setState(673); ((ColorexpContext)_localctx).e3 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(676); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(677); match(9);
				setState(678); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(679); match(9);
				setState(680); ((ColorexpContext)_localctx).e3 = expression(0);
				setState(681); match(9);
				setState(682); ((ColorexpContext)_localctx).e4 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result, ((ColorexpContext)_localctx).e4.result);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(685); ((ColorexpContext)_localctx).v = match(VARIABLE);
				setState(686); match(43);
				setState(687); ((ColorexpContext)_localctx).e = expression(0);
				setState(688); match(20);

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
		enterRule(_localctx, 60, RULE_colorargb);
		int _la;
		try {
			setState(706);
			switch (_input.LA(1)) {
			case 7:
				enterOuterAlt(_localctx, 1);
				{
				setState(693); match(7);
				setState(694);
				((ColorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(695); match(9);
				setState(696);
				((ColorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(697); match(9);
				setState(698);
				((ColorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(699); match(9);
				setState(700);
				((ColorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(701); match(47);

						((ColorargbContext)_localctx).result =  new ASTColorARGB(builder.parseFloat((((ColorargbContext)_localctx).a!=null?((ColorargbContext)_localctx).a.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).r!=null?((ColorargbContext)_localctx).r.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).g!=null?((ColorargbContext)_localctx).g.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).b!=null?((ColorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case 23:
				enterOuterAlt(_localctx, 2);
				{
				setState(703); match(23);
				setState(704); ((ColorargbContext)_localctx).argb = match(ARGB);

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
		enterRule(_localctx, 62, RULE_eof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(708); match(EOF);
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

		case 14: return expression_sempred((ExpressionContext)_localctx, predIndex);

		case 15: return expression2_sempred((Expression2Context)_localctx, predIndex);

		case 16: return expression3_sempred((Expression3Context)_localctx, predIndex);

		case 17: return expression4_sempred((Expression4Context)_localctx, predIndex);

		case 28: return ruleexp_sempred((RuleexpContext)_localctx, predIndex);
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
		case 2: return precpred(_ctx, 3);

		case 3: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean expression4_sempred(Expression4Context _localctx, int predIndex) {
		switch (predIndex) {
		case 7: return precpred(_ctx, 1);
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
		case 4: return precpred(_ctx, 4);

		case 5: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean expression3_sempred(Expression3Context _localctx, int predIndex) {
		switch (predIndex) {
		case 6: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean ruleexp_sempred(RuleexpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8: return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3G\u02c9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\7\3W\n\3\f\3\16\3Z\13\3\3\3\5\3]\n\3\3\3\3\3\5\3a\n\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4l\n\4\f\4\16\4o\13\4\3\4\5\4r\n"+
		"\4\3\4\7\4u\n\4\f\4\16\4x\13\4\3\4\3\4\3\5\3\5\3\5\3\5\7\5\u0080\n\5\f"+
		"\5\16\5\u0083\13\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\7\6\u0093\n\6\f\6\16\6\u0096\13\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7\u009e"+
		"\n\7\f\7\16\7\u00a1\13\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00ad"+
		"\n\b\f\b\16\b\u00b0\13\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00c4\n\t\3\n\3\n\3\n\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\5\r\u00de\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u00e8\n\16"+
		"\f\16\16\16\u00eb\13\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00fd\n\17\3\17\3\17\3\17\3\17"+
		"\3\17\7\17\u0104\n\17\f\17\16\17\u0107\13\17\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0130\n\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\7\20\u013c\n\20\f\20\16\20\u013f\13\20\3\21"+
		"\3\21\3\21\5\21\u0144\n\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u0167\n\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u0170\n\21\3\21\3\21\7\21\u0174\n"+
		"\21\f\21\16\21\u0177\13\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u0198\n\22\3\22\3\22\3\22"+
		"\3\22\3\22\7\22\u019f\n\22\f\22\16\22\u01a2\13\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u01c0\n\23\3\23"+
		"\3\23\3\23\3\23\3\23\7\23\u01c7\n\23\f\23\16\23\u01ca\13\23\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u01e6\n\24\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\5\25\u01ee\n\25\3\26\3\26\3\26\3\27\5\27\u01f4"+
		"\n\27\3\27\3\27\3\27\3\27\3\27\5\27\u01fb\n\27\3\30\3\30\5\30\u01ff\n"+
		"\30\3\30\3\30\3\30\5\30\u0204\n\30\3\30\3\30\3\30\3\30\3\30\5\30\u020b"+
		"\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0218"+
		"\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30"+
		"\u0226\n\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u022e\n\30\3\30\3\30\3"+
		"\30\3\30\3\30\3\30\5\30\u0236\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\5\30\u024e\n\30\3\31\3\31\3\31\3\31\3\31\6\31\u0255\n\31\r\31\16"+
		"\31\u0256\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0271"+
		"\n\32\3\33\3\33\3\33\3\33\7\33\u0277\n\33\f\33\16\33\u027a\13\33\3\33"+
		"\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\7\36"+
		"\u0298\n\36\f\36\16\36\u029b\13\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\5\37\u02b6\n\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 "+
		"\3 \3 \5 \u02c5\n \3!\3!\3!\2\t\32\34\36 \"$:\"\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@\2\t\b\2\5\5\23\23\25\25\30\30"+
		"&&//\5\2\7\7))..\6\2\b\b\f\r\22\22##\b\2\3\3\6\6\16\16%%++\60\60\b\2\17"+
		"\17\21\21\24\24\27\27\33\33\62\62\7\2\n\n!!\'\',,\63\63\3\2AB\u02fe\2"+
		"B\3\2\2\2\4J\3\2\2\2\6d\3\2\2\2\b{\3\2\2\2\n\u0086\3\2\2\2\f\u0099\3\2"+
		"\2\2\16\u00a4\3\2\2\2\20\u00c3\3\2\2\2\22\u00c5\3\2\2\2\24\u00c8\3\2\2"+
		"\2\26\u00cb\3\2\2\2\30\u00dd\3\2\2\2\32\u00df\3\2\2\2\34\u00fc\3\2\2\2"+
		"\36\u012f\3\2\2\2 \u0166\3\2\2\2\"\u0197\3\2\2\2$\u01bf\3\2\2\2&\u01e5"+
		"\3\2\2\2(\u01ed\3\2\2\2*\u01ef\3\2\2\2,\u01fa\3\2\2\2.\u024d\3\2\2\2\60"+
		"\u024f\3\2\2\2\62\u0270\3\2\2\2\64\u0272\3\2\2\2\66\u027d\3\2\2\28\u0280"+
		"\3\2\2\2:\u028c\3\2\2\2<\u02b5\3\2\2\2>\u02c4\3\2\2\2@\u02c6\3\2\2\2B"+
		"C\7\65\2\2CD\b\2\1\2DE\7\4\2\2EF\5\4\3\2FG\5\6\4\2GH\7\"\2\2HI\5@!\2I"+
		"\3\3\2\2\2JK\7\66\2\2KL\7-\2\2LM\5.\30\2MN\7\13\2\2NO\5.\30\2OP\7\26\2"+
		"\2PQ\b\3\1\2QR\7-\2\2RS\5\32\16\2ST\7\26\2\2TX\7\4\2\2UW\5\16\b\2VU\3"+
		"\2\2\2WZ\3\2\2\2XV\3\2\2\2XY\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2[]\5\b\5\2\\"+
		"[\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^`\5\n\6\2_a\5\f\7\2`_\3\2\2\2`a\3\2\2\2"+
		"ab\3\2\2\2bc\7\"\2\2c\5\3\2\2\2de\7=\2\2ef\7-\2\2fg\5> \2gh\7\26\2\2h"+
		"i\b\4\1\2im\7\4\2\2jl\5\60\31\2kj\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2"+
		"\2nq\3\2\2\2om\3\2\2\2pr\5\64\33\2qp\3\2\2\2qr\3\2\2\2rv\3\2\2\2su\58"+
		"\35\2ts\3\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2wy\3\2\2\2xv\3\2\2\2yz\7"+
		"\"\2\2z\7\3\2\2\2{|\78\2\2|}\b\5\1\2}\u0081\7\4\2\2~\u0080\5\22\n\2\177"+
		"~\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082"+
		"\u0084\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0085\7\"\2\2\u0085\t\3\2\2\2"+
		"\u0086\u0087\79\2\2\u0087\u0088\7-\2\2\u0088\u0089\7B\2\2\u0089\u008a"+
		"\7\13\2\2\u008a\u008b\7B\2\2\u008b\u008c\7\26\2\2\u008c\u008d\7\t\2\2"+
		"\u008d\u008e\5\34\17\2\u008e\u008f\7\61\2\2\u008f\u0090\b\6\1\2\u0090"+
		"\u0094\7\4\2\2\u0091\u0093\5\24\13\2\u0092\u0091\3\2\2\2\u0093\u0096\3"+
		"\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0097\3\2\2\2\u0096"+
		"\u0094\3\2\2\2\u0097\u0098\7\"\2\2\u0098\13\3\2\2\2\u0099\u009a\7:\2\2"+
		"\u009a\u009b\b\7\1\2\u009b\u009f\7\4\2\2\u009c\u009e\5\26\f\2\u009d\u009c"+
		"\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0"+
		"\u00a2\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a3\7\"\2\2\u00a3\r\3\2\2\2"+
		"\u00a4\u00a5\7\67\2\2\u00a5\u00a6\7E\2\2\u00a6\u00a7\7-\2\2\u00a7\u00a8"+
		"\5.\30\2\u00a8\u00a9\7\26\2\2\u00a9\u00aa\b\b\1\2\u00aa\u00ae\7\4\2\2"+
		"\u00ab\u00ad\5\20\t\2\u00ac\u00ab\3\2\2\2\u00ad\u00b0\3\2\2\2\u00ae\u00ac"+
		"\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b1\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b1"+
		"\u00b2\7\"\2\2\u00b2\17\3\2\2\2\u00b3\u00b4\7C\2\2\u00b4\u00b5\7\t\2\2"+
		"\u00b5\u00b6\5.\30\2\u00b6\u00b7\7\61\2\2\u00b7\u00b8\7 \2\2\u00b8\u00b9"+
		"\b\t\1\2\u00b9\u00c4\3\2\2\2\u00ba\u00bb\7D\2\2\u00bb\u00bc\7\t\2\2\u00bc"+
		"\u00bd\5.\30\2\u00bd\u00be\7\13\2\2\u00be\u00bf\5.\30\2\u00bf\u00c0\7"+
		"\61\2\2\u00c0\u00c1\7 \2\2\u00c1\u00c2\b\t\1\2\u00c2\u00c4\3\2\2\2\u00c3"+
		"\u00b3\3\2\2\2\u00c3\u00ba\3\2\2\2\u00c4\21\3\2\2\2\u00c5\u00c6\5\30\r"+
		"\2\u00c6\u00c7\b\n\1\2\u00c7\23\3\2\2\2\u00c8\u00c9\5\30\r\2\u00c9\u00ca"+
		"\b\13\1\2\u00ca\25\3\2\2\2\u00cb\u00cc\5\30\r\2\u00cc\u00cd\b\f\1\2\u00cd"+
		"\27\3\2\2\2\u00ce\u00cf\7E\2\2\u00cf\u00d0\7\5\2\2\u00d0\u00d1\5\36\20"+
		"\2\u00d1\u00d2\7 \2\2\u00d2\u00d3\b\r\1\2\u00d3\u00de\3\2\2\2\u00d4\u00d5"+
		"\7<\2\2\u00d5\u00d6\7\t\2\2\u00d6\u00d7\5\34\17\2\u00d7\u00d8\7\61\2\2"+
		"\u00d8\u00d9\7\4\2\2\u00d9\u00da\5\30\r\2\u00da\u00db\7\"\2\2\u00db\u00dc"+
		"\b\r\1\2\u00dc\u00de\3\2\2\2\u00dd\u00ce\3\2\2\2\u00dd\u00d4\3\2\2\2\u00de"+
		"\31\3\2\2\2\u00df\u00e0\b\16\1\2\u00e0\u00e1\7E\2\2\u00e1\u00e2\b\16\1"+
		"\2\u00e2\u00e9\3\2\2\2\u00e3\u00e4\f\3\2\2\u00e4\u00e5\7\13\2\2\u00e5"+
		"\u00e6\7E\2\2\u00e6\u00e8\b\16\1\2\u00e7\u00e3\3\2\2\2\u00e8\u00eb\3\2"+
		"\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\33\3\2\2\2\u00eb\u00e9"+
		"\3\2\2\2\u00ec\u00ed\b\17\1\2\u00ed\u00ee\5\36\20\2\u00ee\u00ef\t\2\2"+
		"\2\u00ef\u00f0\5\36\20\2\u00f0\u00f1\b\17\1\2\u00f1\u00fd\3\2\2\2\u00f2"+
		"\u00f3\7E\2\2\u00f3\u00f4\7$\2\2\u00f4\u00f5\5\36\20\2\u00f5\u00f6\b\17"+
		"\1\2\u00f6\u00fd\3\2\2\2\u00f7\u00f8\7E\2\2\u00f8\u00f9\7(\2\2\u00f9\u00fa"+
		"\5\36\20\2\u00fa\u00fb\b\17\1\2\u00fb\u00fd\3\2\2\2\u00fc\u00ec\3\2\2"+
		"\2\u00fc\u00f2\3\2\2\2\u00fc\u00f7\3\2\2\2\u00fd\u0105\3\2\2\2\u00fe\u00ff"+
		"\f\3\2\2\u00ff\u0100\t\3\2\2\u0100\u0101\5\34\17\4\u0101\u0102\b\17\1"+
		"\2\u0102\u0104\3\2\2\2\u0103\u00fe\3\2\2\2\u0104\u0107\3\2\2\2\u0105\u0103"+
		"\3\2\2\2\u0105\u0106\3\2\2\2\u0106\35\3\2\2\2\u0107\u0105\3\2\2\2\u0108"+
		"\u0109\b\20\1\2\u0109\u010a\7\64\2\2\u010a\u010b\5\36\20\7\u010b\u010c"+
		"\b\20\1\2\u010c\u0130\3\2\2\2\u010d\u010e\7\35\2\2\u010e\u010f\5\36\20"+
		"\6\u010f\u0110\b\20\1\2\u0110\u0130\3\2\2\2\u0111\u0112\5(\25\2\u0112"+
		"\u0113\b\20\1\2\u0113\u0130\3\2\2\2\u0114\u0115\5*\26\2\u0115\u0116\b"+
		"\20\1\2\u0116\u0130\3\2\2\2\u0117\u0118\5.\30\2\u0118\u0119\b\20\1\2\u0119"+
		"\u0130\3\2\2\2\u011a\u011b\5&\24\2\u011b\u011c\b\20\1\2\u011c\u0130\3"+
		"\2\2\2\u011d\u011e\7\t\2\2\u011e\u011f\5\36\20\2\u011f\u0120\7\61\2\2"+
		"\u0120\u0121\b\20\1\2\u0121\u0130\3\2\2\2\u0122\u0123\7.\2\2\u0123\u0124"+
		"\5\36\20\2\u0124\u0125\7.\2\2\u0125\u0126\b\20\1\2\u0126\u0130\3\2\2\2"+
		"\u0127\u0128\7\25\2\2\u0128\u0129\5\36\20\2\u0129\u012a\7/\2\2\u012a\u012b"+
		"\b\20\1\2\u012b\u0130\3\2\2\2\u012c\u012d\5 \21\2\u012d\u012e\b\20\1\2"+
		"\u012e\u0130\3\2\2\2\u012f\u0108\3\2\2\2\u012f\u010d\3\2\2\2\u012f\u0111"+
		"\3\2\2\2\u012f\u0114\3\2\2\2\u012f\u0117\3\2\2\2\u012f\u011a\3\2\2\2\u012f"+
		"\u011d\3\2\2\2\u012f\u0122\3\2\2\2\u012f\u0127\3\2\2\2\u012f\u012c\3\2"+
		"\2\2\u0130\u013d\3\2\2\2\u0131\u0132\f\5\2\2\u0132\u0133\7\35\2\2\u0133"+
		"\u0134\5\36\20\6\u0134\u0135\b\20\1\2\u0135\u013c\3\2\2\2\u0136\u0137"+
		"\f\4\2\2\u0137\u0138\7\64\2\2\u0138\u0139\5\36\20\5\u0139\u013a\b\20\1"+
		"\2\u013a\u013c\3\2\2\2\u013b\u0131\3\2\2\2\u013b\u0136\3\2\2\2\u013c\u013f"+
		"\3\2\2\2\u013d\u013b\3\2\2\2\u013d\u013e\3\2\2\2\u013e\37\3\2\2\2\u013f"+
		"\u013d\3\2\2\2\u0140\u0141\b\21\1\2\u0141\u0143\7\34\2\2\u0142\u0144\7"+
		"*\2\2\u0143\u0142\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0145\3\2\2\2\u0145"+
		"\u0146\5 \21\5\u0146\u0147\b\21\1\2\u0147\u0167\3\2\2\2\u0148\u0149\5"+
		"(\25\2\u0149\u014a\b\21\1\2\u014a\u0167\3\2\2\2\u014b\u014c\5*\26\2\u014c"+
		"\u014d\b\21\1\2\u014d\u0167\3\2\2\2\u014e\u014f\5,\27\2\u014f\u0150\b"+
		"\21\1\2\u0150\u0167\3\2\2\2\u0151\u0152\5&\24\2\u0152\u0153\b\21\1\2\u0153"+
		"\u0167\3\2\2\2\u0154\u0155\7\t\2\2\u0155\u0156\5\36\20\2\u0156\u0157\7"+
		"\61\2\2\u0157\u0158\b\21\1\2\u0158\u0167\3\2\2\2\u0159\u015a\7.\2\2\u015a"+
		"\u015b\5\36\20\2\u015b\u015c\7.\2\2\u015c\u015d\b\21\1\2\u015d\u0167\3"+
		"\2\2\2\u015e\u015f\7\25\2\2\u015f\u0160\5\36\20\2\u0160\u0161\7/\2\2\u0161"+
		"\u0162\b\21\1\2\u0162\u0167\3\2\2\2\u0163\u0164\5\"\22\2\u0164\u0165\b"+
		"\21\1\2\u0165\u0167\3\2\2\2\u0166\u0140\3\2\2\2\u0166\u0148\3\2\2\2\u0166"+
		"\u014b\3\2\2\2\u0166\u014e\3\2\2\2\u0166\u0151\3\2\2\2\u0166\u0154\3\2"+
		"\2\2\u0166\u0159\3\2\2\2\u0166\u015e\3\2\2\2\u0166\u0163\3\2\2\2\u0167"+
		"\u0175\3\2\2\2\u0168\u0169\f\6\2\2\u0169\u016a\7*\2\2\u016a\u016b\5 \21"+
		"\7\u016b\u016c\b\21\1\2\u016c\u0174\3\2\2\2\u016d\u016f\f\4\2\2\u016e"+
		"\u0170\7*\2\2\u016f\u016e\3\2\2\2\u016f\u0170\3\2\2\2\u0170\u0171\3\2"+
		"\2\2\u0171\u0172\7\34\2\2\u0172\u0174\b\21\1\2\u0173\u0168\3\2\2\2\u0173"+
		"\u016d\3\2\2\2\u0174\u0177\3\2\2\2\u0175\u0173\3\2\2\2\u0175\u0176\3\2"+
		"\2\2\u0176!\3\2\2\2\u0177\u0175\3\2\2\2\u0178\u0179\b\22\1\2\u0179\u017a"+
		"\5(\25\2\u017a\u017b\b\22\1\2\u017b\u0198\3\2\2\2\u017c\u017d\5*\26\2"+
		"\u017d\u017e\b\22\1\2\u017e\u0198\3\2\2\2\u017f\u0180\5,\27\2\u0180\u0181"+
		"\b\22\1\2\u0181\u0198\3\2\2\2\u0182\u0183\5&\24\2\u0183\u0184\b\22\1\2"+
		"\u0184\u0198\3\2\2\2\u0185\u0186\7\t\2\2\u0186\u0187\5\36\20\2\u0187\u0188"+
		"\7\61\2\2\u0188\u0189\b\22\1\2\u0189\u0198\3\2\2\2\u018a\u018b\7.\2\2"+
		"\u018b\u018c\5\36\20\2\u018c\u018d\7.\2\2\u018d\u018e\b\22\1\2\u018e\u0198"+
		"\3\2\2\2\u018f\u0190\7\25\2\2\u0190\u0191\5\36\20\2\u0191\u0192\7/\2\2"+
		"\u0192\u0193\b\22\1\2\u0193\u0198\3\2\2\2\u0194\u0195\5$\23\2\u0195\u0196"+
		"\b\22\1\2\u0196\u0198\3\2\2\2\u0197\u0178\3\2\2\2\u0197\u017c\3\2\2\2"+
		"\u0197\u017f\3\2\2\2\u0197\u0182\3\2\2\2\u0197\u0185\3\2\2\2\u0197\u018a"+
		"\3\2\2\2\u0197\u018f\3\2\2\2\u0197\u0194\3\2\2\2\u0198\u01a0\3\2\2\2\u0199"+
		"\u019a\f\4\2\2\u019a\u019b\7\36\2\2\u019b\u019c\5\"\22\5\u019c\u019d\b"+
		"\22\1\2\u019d\u019f\3\2\2\2\u019e\u0199\3\2\2\2\u019f\u01a2\3\2\2\2\u01a0"+
		"\u019e\3\2\2\2\u01a0\u01a1\3\2\2\2\u01a1#\3\2\2\2\u01a2\u01a0\3\2\2\2"+
		"\u01a3\u01a4\b\23\1\2\u01a4\u01a5\5(\25\2\u01a5\u01a6\b\23\1\2\u01a6\u01c0"+
		"\3\2\2\2\u01a7\u01a8\5*\26\2\u01a8\u01a9\b\23\1\2\u01a9\u01c0\3\2\2\2"+
		"\u01aa\u01ab\5,\27\2\u01ab\u01ac\b\23\1\2\u01ac\u01c0\3\2\2\2\u01ad\u01ae"+
		"\5&\24\2\u01ae\u01af\b\23\1\2\u01af\u01c0\3\2\2\2\u01b0\u01b1\7\t\2\2"+
		"\u01b1\u01b2\5\36\20\2\u01b2\u01b3\7\61\2\2\u01b3\u01b4\b\23\1\2\u01b4"+
		"\u01c0\3\2\2\2\u01b5\u01b6\7.\2\2\u01b6\u01b7\5\36\20\2\u01b7\u01b8\7"+
		".\2\2\u01b8\u01b9\b\23\1\2\u01b9\u01c0\3\2\2\2\u01ba\u01bb\7\25\2\2\u01bb"+
		"\u01bc\5\36\20\2\u01bc\u01bd\7/\2\2\u01bd\u01be\b\23\1\2\u01be\u01c0\3"+
		"\2\2\2\u01bf\u01a3\3\2\2\2\u01bf\u01a7\3\2\2\2\u01bf\u01aa\3\2\2\2\u01bf"+
		"\u01ad\3\2\2\2\u01bf\u01b0\3\2\2\2\u01bf\u01b5\3\2\2\2\u01bf\u01ba\3\2"+
		"\2\2\u01c0\u01c8\3\2\2\2\u01c1\u01c2\f\3\2\2\u01c2\u01c3\7\7\2\2\u01c3"+
		"\u01c4\5$\23\4\u01c4\u01c5\b\23\1\2\u01c5\u01c7\3\2\2\2\u01c6\u01c1\3"+
		"\2\2\2\u01c7\u01ca\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c8\u01c9\3\2\2\2\u01c9"+
		"%\3\2\2\2\u01ca\u01c8\3\2\2\2\u01cb\u01cc\t\4\2\2\u01cc\u01cd\7\t\2\2"+
		"\u01cd\u01ce\5\36\20\2\u01ce\u01cf\7\61\2\2\u01cf\u01d0\b\24\1\2\u01d0"+
		"\u01e6\3\2\2\2\u01d1\u01d2\t\5\2\2\u01d2\u01d3\7\t\2\2\u01d3\u01d4\5\36"+
		"\20\2\u01d4\u01d5\7\61\2\2\u01d5\u01d6\b\24\1\2\u01d6\u01e6\3\2\2\2\u01d7"+
		"\u01d8\t\6\2\2\u01d8\u01d9\7\t\2\2\u01d9\u01da\5\36\20\2\u01da\u01db\7"+
		"\61\2\2\u01db\u01dc\b\24\1\2\u01dc\u01e6\3\2\2\2\u01dd\u01de\t\7\2\2\u01de"+
		"\u01df\7\t\2\2\u01df\u01e0\5\36\20\2\u01e0\u01e1\7\13\2\2\u01e1\u01e2"+
		"\5\36\20\2\u01e2\u01e3\7\61\2\2\u01e3\u01e4\b\24\1\2\u01e4\u01e6\3\2\2"+
		"\2\u01e5\u01cb\3\2\2\2\u01e5\u01d1\3\2\2\2\u01e5\u01d7\3\2\2\2\u01e5\u01dd"+
		"\3\2\2\2\u01e6\'\3\2\2\2\u01e7\u01e8\7\32\2\2\u01e8\u01ee\b\25\1\2\u01e9"+
		"\u01ea\7\20\2\2\u01ea\u01ee\b\25\1\2\u01eb\u01ec\7\37\2\2\u01ec\u01ee"+
		"\b\25\1\2\u01ed\u01e7\3\2\2\2\u01ed\u01e9\3\2\2\2\u01ed\u01eb\3\2\2\2"+
		"\u01ee)\3\2\2\2\u01ef\u01f0\7E\2\2\u01f0\u01f1\b\26\1\2\u01f1+\3\2\2\2"+
		"\u01f2\u01f4\7\35\2\2\u01f3\u01f2\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4\u01f5"+
		"\3\2\2\2\u01f5\u01f6\t\b\2\2\u01f6\u01fb\b\27\1\2\u01f7\u01f8\7\64\2\2"+
		"\u01f8\u01f9\t\b\2\2\u01f9\u01fb\b\27\1\2\u01fa\u01f3\3\2\2\2\u01fa\u01f7"+
		"\3\2\2\2\u01fb-\3\2\2\2\u01fc\u01fe\7\25\2\2\u01fd\u01ff\7\35\2\2\u01fe"+
		"\u01fd\3\2\2\2\u01fe\u01ff\3\2\2\2\u01ff\u0200\3\2\2\2\u0200\u0201\t\b"+
		"\2\2\u0201\u0203\7\13\2\2\u0202\u0204\7\35\2\2\u0203\u0202\3\2\2\2\u0203"+
		"\u0204\3\2\2\2\u0204\u0205\3\2\2\2\u0205\u0206\t\b\2\2\u0206\u0207\7/"+
		"\2\2\u0207\u024e\b\30\1\2\u0208\u020a\7\25\2\2\u0209\u020b\7\35\2\2\u020a"+
		"\u0209\3\2\2\2\u020a\u020b\3\2\2\2\u020b\u020c\3\2\2\2\u020c\u020d\t\b"+
		"\2\2\u020d\u020e\7\13\2\2\u020e\u020f\7\64\2\2\u020f\u0210\t\b\2\2\u0210"+
		"\u0211\7/\2\2\u0211\u024e\b\30\1\2\u0212\u0213\7\25\2\2\u0213\u0214\7"+
		"\64\2\2\u0214\u0215\t\b\2\2\u0215\u0217\7\13\2\2\u0216\u0218\7\35\2\2"+
		"\u0217\u0216\3\2\2\2\u0217\u0218\3\2\2\2\u0218\u0219\3\2\2\2\u0219\u021a"+
		"\t\b\2\2\u021a\u021b\7/\2\2\u021b\u024e\b\30\1\2\u021c\u021d\7\25\2\2"+
		"\u021d\u021e\7\64\2\2\u021e\u021f\t\b\2\2\u021f\u0220\7\13\2\2\u0220\u0221"+
		"\7\64\2\2\u0221\u0222\t\b\2\2\u0222\u0223\7/\2\2\u0223\u024e\b\30\1\2"+
		"\u0224\u0226\7\35\2\2\u0225\u0224\3\2\2\2\u0225\u0226\3\2\2\2\u0226\u0227"+
		"\3\2\2\2\u0227\u0228\t\b\2\2\u0228\u0229\7\35\2\2\u0229\u022a\t\b\2\2"+
		"\u022a\u022b\7\34\2\2\u022b\u024e\b\30\1\2\u022c\u022e\7\35\2\2\u022d"+
		"\u022c\3\2\2\2\u022d\u022e\3\2\2\2\u022e\u022f\3\2\2\2\u022f\u0230\t\b"+
		"\2\2\u0230\u0231\7\64\2\2\u0231\u0232\t\b\2\2\u0232\u0233\7\34\2\2\u0233"+
		"\u024e\b\30\1\2\u0234\u0236\7\35\2\2\u0235\u0234\3\2\2\2\u0235\u0236\3"+
		"\2\2\2\u0236\u0237\3\2\2\2\u0237\u0238\t\b\2\2\u0238\u0239\7\34\2\2\u0239"+
		"\u024e\b\30\1\2\u023a\u023b\7\64\2\2\u023b\u023c\t\b\2\2\u023c\u023d\7"+
		"\35\2\2\u023d\u023e\t\b\2\2\u023e\u023f\7\34\2\2\u023f\u024e\b\30\1\2"+
		"\u0240\u0241\7\64\2\2\u0241\u0242\t\b\2\2\u0242\u0243\7\64\2\2\u0243\u0244"+
		"\t\b\2\2\u0244\u0245\7\34\2\2\u0245\u024e\b\30\1\2\u0246\u0247\7\64\2"+
		"\2\u0247\u0248\t\b\2\2\u0248\u0249\7\34\2\2\u0249\u024e\b\30\1\2\u024a"+
		"\u024b\5,\27\2\u024b\u024c\b\30\1\2\u024c\u024e\3\2\2\2\u024d\u01fc\3"+
		"\2\2\2\u024d\u0208\3\2\2\2\u024d\u0212\3\2\2\2\u024d\u021c\3\2\2\2\u024d"+
		"\u0225\3\2\2\2\u024d\u022d\3\2\2\2\u024d\u0235\3\2\2\2\u024d\u023a\3\2"+
		"\2\2\u024d\u0240\3\2\2\2\u024d\u0246\3\2\2\2\u024d\u024a\3\2\2\2\u024e"+
		"/\3\2\2\2\u024f\u0250\7>\2\2\u0250\u0251\7E\2\2\u0251\u0252\b\31\1\2\u0252"+
		"\u0254\7\4\2\2\u0253\u0255\5\62\32\2\u0254\u0253\3\2\2\2\u0255\u0256\3"+
		"\2\2\2\u0256\u0254\3\2\2\2\u0256\u0257\3\2\2\2\u0257\u0258\3\2\2\2\u0258"+
		"\u0259\7\"\2\2\u0259\61\3\2\2\2\u025a\u025b\7-\2\2\u025b\u025c\5> \2\u025c"+
		"\u025d\7/\2\2\u025d\u025e\5> \2\u025e\u025f\7\13\2\2\u025f\u0260\7B\2"+
		"\2\u0260\u0261\7\13\2\2\u0261\u0262\5\36\20\2\u0262\u0263\7\26\2\2\u0263"+
		"\u0264\7 \2\2\u0264\u0265\b\32\1\2\u0265\u0271\3\2\2\2\u0266\u0267\7-"+
		"\2\2\u0267\u0268\5> \2\u0268\u0269\7/\2\2\u0269\u026a\5> \2\u026a\u026b"+
		"\7\13\2\2\u026b\u026c\7B\2\2\u026c\u026d\7\26\2\2\u026d\u026e\7 \2\2\u026e"+
		"\u026f\b\32\1\2\u026f\u0271\3\2\2\2\u0270\u025a\3\2\2\2\u0270\u0266\3"+
		"\2\2\2\u0271\63\3\2\2\2\u0272\u0273\7;\2\2\u0273\u0274\b\33\1\2\u0274"+
		"\u0278\7\4\2\2\u0275\u0277\5\66\34\2\u0276\u0275\3\2\2\2\u0277\u027a\3"+
		"\2\2\2\u0278\u0276\3\2\2\2\u0278\u0279\3\2\2\2\u0279\u027b\3\2\2\2\u027a"+
		"\u0278\3\2\2\2\u027b\u027c\7\"\2\2\u027c\65\3\2\2\2\u027d\u027e\5\30\r"+
		"\2\u027e\u027f\b\34\1\2\u027f\67\3\2\2\2\u0280\u0281\7?\2\2\u0281\u0282"+
		"\7\t\2\2\u0282\u0283\5:\36\2\u0283\u0284\7\61\2\2\u0284\u0285\7-\2\2\u0285"+
		"\u0286\t\b\2\2\u0286\u0287\7\26\2\2\u0287\u0288\7\4\2\2\u0288\u0289\5"+
		"<\37\2\u0289\u028a\7\"\2\2\u028a\u028b\b\35\1\2\u028b9\3\2\2\2\u028c\u028d"+
		"\b\36\1\2\u028d\u028e\5\36\20\2\u028e\u028f\t\2\2\2\u028f\u0290\5\36\20"+
		"\2\u0290\u0291\b\36\1\2\u0291\u0299\3\2\2\2\u0292\u0293\f\3\2\2\u0293"+
		"\u0294\t\3\2\2\u0294\u0295\5:\36\4\u0295\u0296\b\36\1\2\u0296\u0298\3"+
		"\2\2\2\u0297\u0292\3\2\2\2\u0298\u029b\3\2\2\2\u0299\u0297\3\2\2\2\u0299"+
		"\u029a\3\2\2\2\u029a;\3\2\2\2\u029b\u0299\3\2\2\2\u029c\u029d\5\36\20"+
		"\2\u029d\u029e\b\37\1\2\u029e\u02b6\3\2\2\2\u029f\u02a0\5\36\20\2\u02a0"+
		"\u02a1\7\13\2\2\u02a1\u02a2\5\36\20\2\u02a2\u02a3\7\13\2\2\u02a3\u02a4"+
		"\5\36\20\2\u02a4\u02a5\b\37\1\2\u02a5\u02b6\3\2\2\2\u02a6\u02a7\5\36\20"+
		"\2\u02a7\u02a8\7\13\2\2\u02a8\u02a9\5\36\20\2\u02a9\u02aa\7\13\2\2\u02aa"+
		"\u02ab\5\36\20\2\u02ab\u02ac\7\13\2\2\u02ac\u02ad\5\36\20\2\u02ad\u02ae"+
		"\b\37\1\2\u02ae\u02b6\3\2\2\2\u02af\u02b0\7E\2\2\u02b0\u02b1\7-\2\2\u02b1"+
		"\u02b2\5\36\20\2\u02b2\u02b3\7\26\2\2\u02b3\u02b4\b\37\1\2\u02b4\u02b6"+
		"\3\2\2\2\u02b5\u029c\3\2\2\2\u02b5\u029f\3\2\2\2\u02b5\u02a6\3\2\2\2\u02b5"+
		"\u02af\3\2\2\2\u02b6=\3\2\2\2\u02b7\u02b8\7\t\2\2\u02b8\u02b9\t\b\2\2"+
		"\u02b9\u02ba\7\13\2\2\u02ba\u02bb\t\b\2\2\u02bb\u02bc\7\13\2\2\u02bc\u02bd"+
		"\t\b\2\2\u02bd\u02be\7\13\2\2\u02be\u02bf\t\b\2\2\u02bf\u02c0\7\61\2\2"+
		"\u02c0\u02c5\b \1\2\u02c1\u02c2\7\31\2\2\u02c2\u02c3\7@\2\2\u02c3\u02c5"+
		"\b \1\2\u02c4\u02b7\3\2\2\2\u02c4\u02c1\3\2\2\2\u02c5?\3\2\2\2\u02c6\u02c7"+
		"\7\2\2\3\u02c7A\3\2\2\2/X\\`mqv\u0081\u0094\u009f\u00ae\u00c3\u00dd\u00e9"+
		"\u00fc\u0105\u012f\u013b\u013d\u0143\u0166\u016f\u0173\u0175\u0197\u01a0"+
		"\u01bf\u01c8\u01e5\u01ed\u01f3\u01fa\u01fe\u0203\u020a\u0217\u0225\u022d"+
		"\u0235\u024d\u0256\u0270\u0278\u0299\u02b5\u02c4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}