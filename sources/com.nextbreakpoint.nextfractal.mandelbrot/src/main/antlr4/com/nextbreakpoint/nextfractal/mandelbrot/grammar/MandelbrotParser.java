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
		T__41=1, T__40=2, T__39=3, T__38=4, T__37=5, T__36=6, T__35=7, T__34=8, 
		T__33=9, T__32=10, T__31=11, T__30=12, T__29=13, T__28=14, T__27=15, T__26=16, 
		T__25=17, T__24=18, T__23=19, T__22=20, T__21=21, T__20=22, T__19=23, 
		T__18=24, T__17=25, T__16=26, T__15=27, T__14=28, T__13=29, T__12=30, 
		T__11=31, T__10=32, T__9=33, T__8=34, T__7=35, T__6=36, T__5=37, T__4=38, 
		T__3=39, T__2=40, T__1=41, T__0=42, FRACTAL=43, ORBIT=44, TRAP=45, CONDITION=46, 
		BEGIN=47, LOOP=48, END=49, COLOR=50, PALETTE=51, RULE=52, USER_ARGB=53, 
		USER_RATIONAL=54, USER_INTEGER=55, USER_PATHOP_1POINTS=56, USER_PATHOP_2POINTS=57, 
		USER_VARIABLE=58, COMMENT=59, WHITESPACE=60;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'cos'", "';'", "'{'", "'='", "'}'", "'asin'", "'mod2'", 
		"'^'", "'im'", "'?'", "'sin'", "'<='", "'pow'", "'~?'", "'&'", "'('", 
		"'*'", "','", "'pha'", "'re'", "'atan'", "'sqrt'", "'tan'", "'atan2'", 
		"'mod'", "'>='", "'['", "'log'", "'|'", "'<'", "']'", "'>'", "'<>'", "'#'", 
		"'acos'", "'i'", "')'", "'exp'", "'+'", "'hypot'", "'-'", "'fractal'", 
		"'orbit'", "'trap'", "'condition'", "'begin'", "'loop'", "'end'", "'color'", 
		"'palette'", "'rule'", "USER_ARGB", "USER_RATIONAL", "USER_INTEGER", "USER_PATHOP_1POINTS", 
		"USER_PATHOP_2POINTS", "USER_VARIABLE", "COMMENT", "WHITESPACE"
	};
	public static final int
		RULE_fractal = 0, RULE_orbit = 1, RULE_color = 2, RULE_begin = 3, RULE_loop = 4, 
		RULE_end = 5, RULE_trap = 6, RULE_pathop = 7, RULE_beginstatements = 8, 
		RULE_loopstatements = 9, RULE_endstatements = 10, RULE_statement = 11, 
		RULE_variablelist = 12, RULE_conditionexp = 13, RULE_expression = 14, 
		RULE_expression2 = 15, RULE_expression3 = 16, RULE_expression4 = 17, RULE_function = 18, 
		RULE_variable = 19, RULE_real = 20, RULE_complex = 21, RULE_palette = 22, 
		RULE_paletteelement = 23, RULE_colorrule = 24, RULE_ruleexp = 25, RULE_colorexp = 26, 
		RULE_colorargb = 27, RULE_eof = 28;
	public static final String[] ruleNames = {
		"fractal", "orbit", "color", "begin", "loop", "end", "trap", "pathop", 
		"beginstatements", "loopstatements", "endstatements", "statement", "variablelist", 
		"conditionexp", "expression", "expression2", "expression3", "expression4", 
		"function", "variable", "real", "complex", "palette", "paletteelement", 
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
			setState(58); ((FractalContext)_localctx).f = match(FRACTAL);

					builder.setFractal(new ASTFractal(((FractalContext)_localctx).f));
				
			setState(60); match(4);
			setState(61); orbit();
			setState(62); color();
			setState(63); match(6);
			setState(64); eof();
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
			setState(66); ((OrbitContext)_localctx).o = match(ORBIT);
			setState(67); match(28);
			setState(68); ((OrbitContext)_localctx).ra = complex();
			setState(69); match(19);
			setState(70); ((OrbitContext)_localctx).rb = complex();
			setState(71); match(32);

					builder.setOrbit(new ASTOrbit(((OrbitContext)_localctx).o, new ASTRegion(((OrbitContext)_localctx).ra.result, ((OrbitContext)_localctx).rb.result)));
				
			setState(73); match(28);
			setState(74); ((OrbitContext)_localctx).v = variablelist(0);
			setState(75); match(32);
			setState(76); match(4);
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TRAP) {
				{
				{
				setState(77); trap();
				}
				}
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(84);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(83); begin();
				}
			}

			setState(86); loop();
			setState(88);
			_la = _input.LA(1);
			if (_la==END) {
				{
				setState(87); end();
				}
			}

			setState(90); match(6);
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
			setState(92); ((ColorContext)_localctx).c = match(COLOR);
			setState(93); match(28);
			setState(94); ((ColorContext)_localctx).argb = colorargb();
			setState(95); match(32);
			 
					builder.setColor(new ASTColor(((ColorContext)_localctx).c, ((ColorContext)_localctx).argb.result));
				
			setState(97); match(4);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PALETTE) {
				{
				{
				setState(98); palette();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(107);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RULE) {
				{
				{
				setState(104); colorrule();
				}
				}
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(110); match(6);
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
		public BeginstatementsContext beginstatements(int i) {
			return getRuleContext(BeginstatementsContext.class,i);
		}
		public TerminalNode BEGIN() { return getToken(MandelbrotParser.BEGIN, 0); }
		public List<BeginstatementsContext> beginstatements() {
			return getRuleContexts(BeginstatementsContext.class);
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
			setState(112); ((BeginContext)_localctx).b = match(BEGIN);
			 
					builder.setOrbitBegin(new ASTOrbitBegin(((BeginContext)_localctx).b));
				
			setState(114); match(4);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==USER_VARIABLE) {
				{
				{
				setState(115); beginstatements();
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121); match(6);
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
		public List<TerminalNode> USER_INTEGER() { return getTokens(MandelbrotParser.USER_INTEGER); }
		public TerminalNode USER_INTEGER(int i) {
			return getToken(MandelbrotParser.USER_INTEGER, i);
		}
		public TerminalNode LOOP() { return getToken(MandelbrotParser.LOOP, 0); }
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public List<LoopstatementsContext> loopstatements() {
			return getRuleContexts(LoopstatementsContext.class);
		}
		public LoopstatementsContext loopstatements(int i) {
			return getRuleContext(LoopstatementsContext.class,i);
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
			setState(123); ((LoopContext)_localctx).l = match(LOOP);
			setState(124); match(28);
			setState(125); ((LoopContext)_localctx).lb = match(USER_INTEGER);
			setState(126); match(19);
			setState(127); ((LoopContext)_localctx).le = match(USER_INTEGER);
			setState(128); match(32);
			setState(129); match(17);
			setState(130); ((LoopContext)_localctx).e = conditionexp(0);
			setState(131); match(38);

					builder.setOrbitLoop(new ASTOrbitLoop(((LoopContext)_localctx).l, builder.parseInt((((LoopContext)_localctx).lb!=null?((LoopContext)_localctx).lb.getText():null)), builder.parseInt((((LoopContext)_localctx).le!=null?((LoopContext)_localctx).le.getText():null)), ((LoopContext)_localctx).e.result));
				
			setState(133); match(4);
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==USER_VARIABLE) {
				{
				{
				setState(134); loopstatements();
				}
				}
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(140); match(6);
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
		public TerminalNode END() { return getToken(MandelbrotParser.END, 0); }
		public EndstatementsContext endstatements(int i) {
			return getRuleContext(EndstatementsContext.class,i);
		}
		public List<EndstatementsContext> endstatements() {
			return getRuleContexts(EndstatementsContext.class);
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
			setState(142); ((EndContext)_localctx).e = match(END);

					builder.setOrbitEnd(new ASTOrbitEnd(((EndContext)_localctx).e));		
				
			setState(144); match(4);
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==USER_VARIABLE) {
				{
				{
				setState(145); endstatements();
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(151); match(6);
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
		public TerminalNode USER_VARIABLE() { return getToken(MandelbrotParser.USER_VARIABLE, 0); }
		public TerminalNode TRAP() { return getToken(MandelbrotParser.TRAP, 0); }
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
			setState(153); ((TrapContext)_localctx).t = match(TRAP);
			setState(154); ((TrapContext)_localctx).n = match(USER_VARIABLE);
			setState(155); match(28);
			setState(156); ((TrapContext)_localctx).c = complex();
			setState(157); match(32);

					builder.addOrbitTrap(new ASTOrbitTrap(((TrapContext)_localctx).t, (((TrapContext)_localctx).n!=null?((TrapContext)_localctx).n.getText():null), ((TrapContext)_localctx).c.result));
				
			setState(159); match(4);
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==USER_PATHOP_1POINTS || _la==USER_PATHOP_2POINTS) {
				{
				{
				setState(160); pathop();
				}
				}
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(166); match(6);
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
		public TerminalNode USER_PATHOP_2POINTS() { return getToken(MandelbrotParser.USER_PATHOP_2POINTS, 0); }
		public ComplexContext complex(int i) {
			return getRuleContext(ComplexContext.class,i);
		}
		public TerminalNode USER_PATHOP_1POINTS() { return getToken(MandelbrotParser.USER_PATHOP_1POINTS, 0); }
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
			setState(184);
			switch (_input.LA(1)) {
			case USER_PATHOP_1POINTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(168); ((PathopContext)_localctx).o = match(USER_PATHOP_1POINTS);
				setState(169); match(17);
				setState(170); ((PathopContext)_localctx).c = complex();
				setState(171); match(38);
				setState(172); match(3);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c.result));
					
				}
				break;
			case USER_PATHOP_2POINTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(175); ((PathopContext)_localctx).o = match(USER_PATHOP_2POINTS);
				setState(176); match(17);
				setState(177); ((PathopContext)_localctx).c1 = complex();
				setState(178); match(19);
				setState(179); ((PathopContext)_localctx).c2 = complex();
				setState(180); match(38);
				setState(181); match(3);

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

	public static class BeginstatementsContext extends ParserRuleContext {
		public StatementContext s;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public BeginstatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_beginstatements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterBeginstatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitBeginstatements(this);
		}
	}

	public final BeginstatementsContext beginstatements() throws RecognitionException {
		BeginstatementsContext _localctx = new BeginstatementsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_beginstatements);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186); ((BeginstatementsContext)_localctx).s = statement();

					builder.addBeginStatement(((BeginstatementsContext)_localctx).s.result);
				
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

	public static class LoopstatementsContext extends ParserRuleContext {
		public StatementContext s;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public LoopstatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopstatements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterLoopstatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitLoopstatements(this);
		}
	}

	public final LoopstatementsContext loopstatements() throws RecognitionException {
		LoopstatementsContext _localctx = new LoopstatementsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_loopstatements);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189); ((LoopstatementsContext)_localctx).s = statement();

					builder.addLoopStatement(((LoopstatementsContext)_localctx).s.result);
				
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

	public static class EndstatementsContext extends ParserRuleContext {
		public StatementContext s;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public EndstatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endstatements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).enterEndstatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MandelbrotListener ) ((MandelbrotListener)listener).exitEndstatements(this);
		}
	}

	public final EndstatementsContext endstatements() throws RecognitionException {
		EndstatementsContext _localctx = new EndstatementsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_endstatements);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192); ((EndstatementsContext)_localctx).s = statement();

					builder.addEndStatement(((EndstatementsContext)_localctx).s.result);
				
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
		public TerminalNode USER_VARIABLE() { return getToken(MandelbrotParser.USER_VARIABLE, 0); }
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
			enterOuterAlt(_localctx, 1);
			{
			setState(195); ((StatementContext)_localctx).v = match(USER_VARIABLE);
			setState(196); match(5);
			setState(197); ((StatementContext)_localctx).e = expression(0);
			setState(198); match(3);

					((StatementContext)_localctx).result =  new ASTStatement(((StatementContext)_localctx).v, (((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result);
					builder.registerVariable((((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result.isReal(), ((StatementContext)_localctx).v);
				
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
		public TerminalNode USER_VARIABLE() { return getToken(MandelbrotParser.USER_VARIABLE, 0); }
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
			setState(202); ((VariablelistContext)_localctx).v = match(USER_VARIABLE);

					builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(211);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VariablelistContext(_parentctx, _parentState);
					_localctx.vl = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_variablelist);
					setState(205);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(206); match(19);
					setState(207); ((VariablelistContext)_localctx).v = match(USER_VARIABLE);

					          		builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
					          	
					}
					} 
				}
				setState(213);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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
		public TerminalNode USER_VARIABLE() { return getToken(MandelbrotParser.USER_VARIABLE, 0); }
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
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
			setState(230);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(215); ((ConditionexpContext)_localctx).e1 = expression(0);
				setState(216);
				((ConditionexpContext)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 13) | (1L << 27) | (1L << 31) | (1L << 33) | (1L << 34))) != 0)) ) {
					((ConditionexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(217); ((ConditionexpContext)_localctx).e2 = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionCompareOp(((ConditionexpContext)_localctx).e1.result.getLocation(), (((ConditionexpContext)_localctx).o!=null?((ConditionexpContext)_localctx).o.getText():null), ((ConditionexpContext)_localctx).e1.result, ((ConditionexpContext)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(220); ((ConditionexpContext)_localctx).v = match(USER_VARIABLE);
				setState(221); match(11);
				setState(222); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, true);
					
				}
				break;

			case 3:
				{
				setState(225); ((ConditionexpContext)_localctx).v = match(USER_VARIABLE);
				setState(226); match(15);
				setState(227); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, false);
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(239);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConditionexpContext(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp);
					setState(232);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(233);
					((ConditionexpContext)_localctx).l = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 9) | (1L << 16) | (1L << 30))) != 0)) ) {
						((ConditionexpContext)_localctx).l = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(234); ((ConditionexpContext)_localctx).c2 = conditionexp(2);

					          		((ConditionexpContext)_localctx).result =  new ASTConditionLogicOp(((ConditionexpContext)_localctx).c1.result.getLocation(), (((ConditionexpContext)_localctx).l!=null?((ConditionexpContext)_localctx).l.getText():null), ((ConditionexpContext)_localctx).c1.result, ((ConditionexpContext)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(241);
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

	public static class ExpressionContext extends ParserRuleContext {
		public ASTExpression result;
		public ExpressionContext e1;
		public Token s;
		public ExpressionContext e;
		public VariableContext v;
		public ComplexContext c;
		public FunctionContext f;
		public Token t;
		public Token m;
		public Token a;
		public Expression2Context e3;
		public ExpressionContext e2;
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
			setState(278);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(243); ((ExpressionContext)_localctx).s = match(42);
				setState(244); ((ExpressionContext)_localctx).e = expression(5);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 2:
				{
				setState(247); ((ExpressionContext)_localctx).s = match(40);
				setState(248); ((ExpressionContext)_localctx).e = expression(4);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 3:
				{
				setState(251); ((ExpressionContext)_localctx).v = variable();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).v.result;
					
				}
				break;

			case 4:
				{
				setState(254); ((ExpressionContext)_localctx).c = complex();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).c.result;
					
				}
				break;

			case 5:
				{
				setState(257); ((ExpressionContext)_localctx).f = function();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).f.result;
					
				}
				break;

			case 6:
				{
				setState(260); ((ExpressionContext)_localctx).t = match(17);
				setState(261); ((ExpressionContext)_localctx).e = expression(0);
				setState(262); match(38);

						((ExpressionContext)_localctx).result =  new ASTParen(((ExpressionContext)_localctx).t, ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 7:
				{
				setState(265); ((ExpressionContext)_localctx).m = match(30);
				setState(266); ((ExpressionContext)_localctx).e = expression(0);
				setState(267); match(30);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).m, "mod", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(270); ((ExpressionContext)_localctx).a = match(31);
				setState(271); ((ExpressionContext)_localctx).e = expression(0);
				setState(272); match(33);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).a, "pha", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 9:
				{
				setState(275); ((ExpressionContext)_localctx).e3 = expression2(0);

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e3.result;	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(292);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(290);
					switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(280);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(281); ((ExpressionContext)_localctx).s = match(40);
						setState(282); ((ExpressionContext)_localctx).e2 = expression(4);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;

					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(285);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(286); ((ExpressionContext)_localctx).s = match(42);
						setState(287); ((ExpressionContext)_localctx).e2 = expression(3);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					}
					} 
				}
				setState(294);
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

	public static class Expression2Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression2Context e1;
		public Expression2Context e2;
		public Token i;
		public VariableContext v;
		public RealContext r;
		public FunctionContext f;
		public Token t;
		public ExpressionContext e;
		public Token m;
		public Token a;
		public Expression3Context e3;
		public Token s;
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
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(296); ((Expression2Context)_localctx).i = match(37);
				setState(297); ((Expression2Context)_localctx).e2 = expression2(3);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(300); ((Expression2Context)_localctx).v = variable();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).v.result;
					
				}
				break;

			case 3:
				{
				setState(303); ((Expression2Context)_localctx).r = real();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).r.result;
					
				}
				break;

			case 4:
				{
				setState(306); ((Expression2Context)_localctx).f = function();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).f.result;
					
				}
				break;

			case 5:
				{
				setState(309); ((Expression2Context)_localctx).t = match(17);
				setState(310); ((Expression2Context)_localctx).e = expression(0);
				setState(311); match(38);

						((Expression2Context)_localctx).result =  new ASTParen(((Expression2Context)_localctx).t, ((Expression2Context)_localctx).e.result);
					
				}
				break;

			case 6:
				{
				setState(314); ((Expression2Context)_localctx).m = match(30);
				setState(315); ((Expression2Context)_localctx).e = expression(0);
				setState(316); match(30);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).m, "mod", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 7:
				{
				setState(319); ((Expression2Context)_localctx).a = match(31);
				setState(320); ((Expression2Context)_localctx).e = expression(0);
				setState(321); match(33);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).a, "pha", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(324); ((Expression2Context)_localctx).e3 = expression3(0);

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(339);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(337);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(329);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(330); ((Expression2Context)_localctx).s = match(18);
						setState(331); ((Expression2Context)_localctx).e2 = expression2(5);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "*", ((Expression2Context)_localctx).e1.result, ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;

					case 2:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e2 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(334);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(335); ((Expression2Context)_localctx).i = match(37);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					}
					} 
				}
				setState(341);
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

	public static class Expression3Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression3Context e1;
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
			setState(370);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(343); ((Expression3Context)_localctx).v = variable();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).v.result;
					
				}
				break;

			case 2:
				{
				setState(346); ((Expression3Context)_localctx).r = real();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).r.result;
					
				}
				break;

			case 3:
				{
				setState(349); ((Expression3Context)_localctx).f = function();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).f.result;
					
				}
				break;

			case 4:
				{
				setState(352); ((Expression3Context)_localctx).t = match(17);
				setState(353); ((Expression3Context)_localctx).e = expression(0);
				setState(354); match(38);

						((Expression3Context)_localctx).result =  new ASTParen(((Expression3Context)_localctx).t, ((Expression3Context)_localctx).e.result);
					
				}
				break;

			case 5:
				{
				setState(357); ((Expression3Context)_localctx).m = match(30);
				setState(358); ((Expression3Context)_localctx).e = expression(0);
				setState(359); match(30);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).m, "mod", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 6:
				{
				setState(362); ((Expression3Context)_localctx).a = match(31);
				setState(363); ((Expression3Context)_localctx).e = expression(0);
				setState(364); match(33);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).a, "pha", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 7:
				{
				setState(367); ((Expression3Context)_localctx).e3 = expression4(0);

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(379);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression3Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression3);
					setState(372);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(373); ((Expression3Context)_localctx).s = match(1);
					setState(374); ((Expression3Context)_localctx).e2 = expression3(3);

					          		((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).s, "/", ((Expression3Context)_localctx).e1.result, ((Expression3Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(381);
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

	public static class Expression4Context extends ParserRuleContext {
		public ASTExpression result;
		public Expression4Context e1;
		public VariableContext v;
		public RealContext r;
		public FunctionContext f;
		public Token t;
		public ExpressionContext e;
		public Token m;
		public Token a;
		public Token s;
		public Expression4Context e2;
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
			setState(407);
			switch (_input.LA(1)) {
			case USER_VARIABLE:
				{
				setState(383); ((Expression4Context)_localctx).v = variable();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).v.result;
					
				}
				break;
			case 40:
			case 42:
			case USER_RATIONAL:
			case USER_INTEGER:
				{
				setState(386); ((Expression4Context)_localctx).r = real();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).r.result;
					
				}
				break;
			case 2:
			case 7:
			case 8:
			case 10:
			case 12:
			case 14:
			case 20:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 29:
			case 36:
			case 39:
			case 41:
				{
				setState(389); ((Expression4Context)_localctx).f = function();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).f.result;
					
				}
				break;
			case 17:
				{
				setState(392); ((Expression4Context)_localctx).t = match(17);
				setState(393); ((Expression4Context)_localctx).e = expression(0);
				setState(394); match(38);

						((Expression4Context)_localctx).result =  new ASTParen(((Expression4Context)_localctx).t, ((Expression4Context)_localctx).e.result);
					
				}
				break;
			case 30:
				{
				setState(397); ((Expression4Context)_localctx).m = match(30);
				setState(398); ((Expression4Context)_localctx).e = expression(0);
				setState(399); match(30);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).m, "mod", ((Expression4Context)_localctx).e.result);	
					
				}
				break;
			case 31:
				{
				setState(402); ((Expression4Context)_localctx).a = match(31);
				setState(403); ((Expression4Context)_localctx).e = expression(0);
				setState(404); match(33);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).a, "pha", ((Expression4Context)_localctx).e.result);	
					
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(416);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression4Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression4);
					setState(409);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(410); ((Expression4Context)_localctx).s = match(9);
					setState(411); ((Expression4Context)_localctx).e2 = expression4(2);

					          		((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).s, "^", ((Expression4Context)_localctx).e1.result, ((Expression4Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(418);
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
			setState(445);
			switch (_input.LA(1)) {
			case 8:
			case 10:
			case 20:
			case 21:
			case 26:
				enterOuterAlt(_localctx, 1);
				{
				setState(419);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 8) | (1L << 10) | (1L << 20) | (1L << 21) | (1L << 26))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(420); match(17);
				setState(421); ((FunctionContext)_localctx).e = expression(0);
				setState(422); match(38);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), ((FunctionContext)_localctx).e.result);		
					
				}
				break;
			case 2:
			case 7:
			case 12:
			case 22:
			case 24:
			case 36:
				enterOuterAlt(_localctx, 2);
				{
				setState(425);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 2) | (1L << 7) | (1L << 12) | (1L << 22) | (1L << 24) | (1L << 36))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(426); match(17);
				setState(427); ((FunctionContext)_localctx).e = expression(0);
				setState(428); match(38);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 23:
			case 29:
			case 39:
				enterOuterAlt(_localctx, 3);
				{
				setState(431);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 23) | (1L << 29) | (1L << 39))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(432); match(17);
				setState(433); ((FunctionContext)_localctx).e = expression(0);
				setState(434); match(38);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 14:
			case 25:
			case 41:
				enterOuterAlt(_localctx, 4);
				{
				setState(437);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 14) | (1L << 25) | (1L << 41))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(438); match(17);
				setState(439); ((FunctionContext)_localctx).e1 = expression(0);
				setState(440); match(19);
				setState(441); ((FunctionContext)_localctx).e2 = expression(0);
				setState(442); match(38);

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

	public static class VariableContext extends ParserRuleContext {
		public ASTVariable result;
		public Token v;
		public TerminalNode USER_VARIABLE() { return getToken(MandelbrotParser.USER_VARIABLE, 0); }
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
		enterRule(_localctx, 38, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(447); ((VariableContext)_localctx).v = match(USER_VARIABLE);

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
		public TerminalNode USER_INTEGER() { return getToken(MandelbrotParser.USER_INTEGER, 0); }
		public TerminalNode USER_RATIONAL() { return getToken(MandelbrotParser.USER_RATIONAL, 0); }
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
		enterRule(_localctx, 40, RULE_real);
		int _la;
		try {
			setState(458);
			switch (_input.LA(1)) {
			case 40:
			case USER_RATIONAL:
			case USER_INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(451);
				_la = _input.LA(1);
				if (_la==40) {
					{
					setState(450); match(40);
					}
				}

				setState(453);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((RealContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();

						((RealContext)_localctx).result =  new ASTNumber(((RealContext)_localctx).r, builder.parseDouble((((RealContext)_localctx).r!=null?((RealContext)_localctx).r.getText():null)));
					
				}
				break;
			case 42:
				enterOuterAlt(_localctx, 2);
				{
				setState(455); match(42);
				setState(456);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
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
		public List<TerminalNode> USER_INTEGER() { return getTokens(MandelbrotParser.USER_INTEGER); }
		public TerminalNode USER_RATIONAL(int i) {
			return getToken(MandelbrotParser.USER_RATIONAL, i);
		}
		public TerminalNode USER_INTEGER(int i) {
			return getToken(MandelbrotParser.USER_INTEGER, i);
		}
		public RealContext real() {
			return getRuleContext(RealContext.class,0);
		}
		public List<TerminalNode> USER_RATIONAL() { return getTokens(MandelbrotParser.USER_RATIONAL); }
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
		enterRule(_localctx, 42, RULE_complex);
		int _la;
		try {
			setState(541);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(460); match(31);
				setState(462);
				_la = _input.LA(1);
				if (_la==40) {
					{
					setState(461); match(40);
					}
				}

				setState(464);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(465); match(19);
				setState(467);
				_la = _input.LA(1);
				if (_la==40) {
					{
					setState(466); match(40);
					}
				}

				setState(469);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(470); match(33);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(472); match(31);
				setState(474);
				_la = _input.LA(1);
				if (_la==40) {
					{
					setState(473); match(40);
					}
				}

				setState(476);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(477); match(19);
				setState(478); match(42);
				setState(479);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(480); match(33);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(482); match(31);
				setState(483); match(42);
				setState(484);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(485); match(19);
				setState(487);
				_la = _input.LA(1);
				if (_la==40) {
					{
					setState(486); match(40);
					}
				}

				setState(489);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(490); match(33);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(492); match(31);
				setState(493); match(42);
				setState(494);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(495); match(19);
				setState(496); match(42);
				setState(497);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(498); match(33);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(501);
				_la = _input.LA(1);
				if (_la==40) {
					{
					setState(500); match(40);
					}
				}

				setState(503);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(504); match(40);
				setState(505);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(506); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(509);
				_la = _input.LA(1);
				if (_la==40) {
					{
					setState(508); match(40);
					}
				}

				setState(511);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(512); match(42);
				setState(513);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(514); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(517);
				_la = _input.LA(1);
				if (_la==40) {
					{
					setState(516); match(40);
					}
				}

				setState(519);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(520); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble((((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(522); match(42);
				setState(523);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(524); match(40);
				setState(525);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(526); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(528); match(42);
				setState(529);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(530); match(42);
				setState(531);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(532); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(534); match(42);
				setState(535);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(536); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(538); ((ComplexContext)_localctx).rn = real();

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
		public TerminalNode USER_VARIABLE() { return getToken(MandelbrotParser.USER_VARIABLE, 0); }
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
		enterRule(_localctx, 44, RULE_palette);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(543); ((PaletteContext)_localctx).p = match(PALETTE);
			setState(544); ((PaletteContext)_localctx).v = match(USER_VARIABLE);

					builder.addPalette(new ASTPalette(((PaletteContext)_localctx).p, (((PaletteContext)_localctx).v!=null?((PaletteContext)_localctx).v.getText():null))); 
				
			setState(546); match(4);
			setState(548); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(547); paletteelement();
				}
				}
				setState(550); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==28 );
			setState(552); match(6);
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
		public TerminalNode USER_INTEGER() { return getToken(MandelbrotParser.USER_INTEGER, 0); }
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
		enterRule(_localctx, 46, RULE_paletteelement);
		try {
			setState(576);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(554); ((PaletteelementContext)_localctx).t = match(28);
				setState(555); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(556); match(33);
				setState(557); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(558); match(19);
				setState(559); ((PaletteelementContext)_localctx).s = match(USER_INTEGER);
				setState(560); match(19);
				setState(561); ((PaletteelementContext)_localctx).e = expression(0);
				setState(562); match(32);
				setState(563); match(3);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), ((PaletteelementContext)_localctx).e.result));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(566); ((PaletteelementContext)_localctx).t = match(28);
				setState(567); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(568); match(33);
				setState(569); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(570); match(19);
				setState(571); ((PaletteelementContext)_localctx).s = match(USER_INTEGER);
				setState(572); match(32);
				setState(573); match(3);

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

	public static class ColorruleContext extends ParserRuleContext {
		public Token t;
		public RuleexpContext r;
		public Token o;
		public ColorexpContext c;
		public TerminalNode RULE() { return getToken(MandelbrotParser.RULE, 0); }
		public TerminalNode USER_INTEGER() { return getToken(MandelbrotParser.USER_INTEGER, 0); }
		public ColorexpContext colorexp() {
			return getRuleContext(ColorexpContext.class,0);
		}
		public TerminalNode USER_RATIONAL() { return getToken(MandelbrotParser.USER_RATIONAL, 0); }
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
		enterRule(_localctx, 48, RULE_colorrule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(578); ((ColorruleContext)_localctx).t = match(RULE);
			setState(579); match(17);
			setState(580); ((ColorruleContext)_localctx).r = ruleexp(0);
			setState(581); match(38);
			setState(582); match(28);
			setState(583);
			((ColorruleContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
				((ColorruleContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(584); match(32);
			setState(585); match(4);
			setState(586); ((ColorruleContext)_localctx).c = colorexp();
			setState(587); match(6);

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
		int _startState = 50;
		enterRecursionRule(_localctx, 50, RULE_ruleexp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(591); ((RuleexpContext)_localctx).e1 = expression(0);
			setState(592);
			((RuleexpContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 5) | (1L << 13) | (1L << 27) | (1L << 31) | (1L << 33) | (1L << 34))) != 0)) ) {
				((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(593); ((RuleexpContext)_localctx).e2 = expression(0);

					((RuleexpContext)_localctx).result =  new ASTRuleCompareOpExpression(((RuleexpContext)_localctx).e1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).e1.result, ((RuleexpContext)_localctx).e2.result);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(603);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RuleexpContext(_parentctx, _parentState);
					_localctx.r1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_ruleexp);
					setState(596);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(597);
					((RuleexpContext)_localctx).o = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 9) | (1L << 16) | (1L << 30))) != 0)) ) {
						((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(598); ((RuleexpContext)_localctx).r2 = ruleexp(2);

					          		((RuleexpContext)_localctx).result =  new ASTRuleLogicOpExpression(((RuleexpContext)_localctx).r1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).r1.result, ((RuleexpContext)_localctx).r2.result);
					          	
					}
					} 
				}
				setState(605);
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

	public static class ColorexpContext extends ParserRuleContext {
		public ASTColorExpression result;
		public ExpressionContext e1;
		public ExpressionContext e2;
		public ExpressionContext e3;
		public ExpressionContext e4;
		public Token v;
		public ExpressionContext e;
		public TerminalNode USER_VARIABLE() { return getToken(MandelbrotParser.USER_VARIABLE, 0); }
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
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
		enterRule(_localctx, 52, RULE_colorexp);
		try {
			setState(631);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(606); ((ColorexpContext)_localctx).e1 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(609); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(610); match(19);
				setState(611); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(612); match(19);
				setState(613); ((ColorexpContext)_localctx).e3 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(616); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(617); match(19);
				setState(618); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(619); match(19);
				setState(620); ((ColorexpContext)_localctx).e3 = expression(0);
				setState(621); match(19);
				setState(622); ((ColorexpContext)_localctx).e4 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result, ((ColorexpContext)_localctx).e4.result);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(625); ((ColorexpContext)_localctx).v = match(USER_VARIABLE);
				setState(626); match(28);
				setState(627); ((ColorexpContext)_localctx).e = expression(0);
				setState(628); match(32);

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
		public List<TerminalNode> USER_INTEGER() { return getTokens(MandelbrotParser.USER_INTEGER); }
		public TerminalNode USER_RATIONAL(int i) {
			return getToken(MandelbrotParser.USER_RATIONAL, i);
		}
		public TerminalNode USER_INTEGER(int i) {
			return getToken(MandelbrotParser.USER_INTEGER, i);
		}
		public TerminalNode USER_ARGB() { return getToken(MandelbrotParser.USER_ARGB, 0); }
		public List<TerminalNode> USER_RATIONAL() { return getTokens(MandelbrotParser.USER_RATIONAL); }
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
		enterRule(_localctx, 54, RULE_colorargb);
		int _la;
		try {
			setState(646);
			switch (_input.LA(1)) {
			case 17:
				enterOuterAlt(_localctx, 1);
				{
				setState(633); match(17);
				setState(634);
				((ColorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ColorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(635); match(19);
				setState(636);
				((ColorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ColorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(637); match(19);
				setState(638);
				((ColorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ColorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(639); match(19);
				setState(640);
				((ColorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==USER_RATIONAL || _la==USER_INTEGER) ) {
					((ColorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(641); match(38);

						((ColorargbContext)_localctx).result =  new ASTColorARGB(builder.parseFloat((((ColorargbContext)_localctx).a!=null?((ColorargbContext)_localctx).a.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).r!=null?((ColorargbContext)_localctx).r.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).g!=null?((ColorargbContext)_localctx).g.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).b!=null?((ColorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case 35:
				enterOuterAlt(_localctx, 2);
				{
				setState(643); match(35);
				setState(644); ((ColorargbContext)_localctx).argb = match(USER_ARGB);

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
		enterRule(_localctx, 56, RULE_eof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(648); match(EOF);
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

		case 25: return ruleexp_sempred((RuleexpContext)_localctx, predIndex);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3>\u028d\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3Q\n\3"+
		"\f\3\16\3T\13\3\3\3\5\3W\n\3\3\3\3\3\5\3[\n\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\7\4f\n\4\f\4\16\4i\13\4\3\4\7\4l\n\4\f\4\16\4o\13\4\3\4\3"+
		"\4\3\5\3\5\3\5\3\5\7\5w\n\5\f\5\16\5z\13\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u008a\n\6\f\6\16\6\u008d\13\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\7\7\u0095\n\7\f\7\16\7\u0098\13\7\3\7\3\7\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\7\b\u00a4\n\b\f\b\16\b\u00a7\13\b\3\b\3\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00bb\n\t"+
		"\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u00d4\n\16\f\16\16\16\u00d7\13"+
		"\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\5\17\u00e9\n\17\3\17\3\17\3\17\3\17\3\17\7\17\u00f0\n\17"+
		"\f\17\16\17\u00f3\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0119"+
		"\n\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u0125\n\20"+
		"\f\20\16\20\u0128\13\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u014a\n\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\7\21\u0154\n\21\f\21\16\21\u0157\13\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\5\22\u0175\n\22\3\22\3\22\3\22\3\22\3\22\7\22\u017c\n\22\f\22\16\22\u017f"+
		"\13\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u019a"+
		"\n\23\3\23\3\23\3\23\3\23\3\23\7\23\u01a1\n\23\f\23\16\23\u01a4\13\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u01c0"+
		"\n\24\3\25\3\25\3\25\3\26\5\26\u01c6\n\26\3\26\3\26\3\26\3\26\3\26\5\26"+
		"\u01cd\n\26\3\27\3\27\5\27\u01d1\n\27\3\27\3\27\3\27\5\27\u01d6\n\27\3"+
		"\27\3\27\3\27\3\27\3\27\5\27\u01dd\n\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\5\27\u01ea\n\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u01f8\n\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\5\27\u0200\n\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u0208\n\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u0220\n\27\3\30\3\30\3\30"+
		"\3\30\3\30\6\30\u0227\n\30\r\30\16\30\u0228\3\30\3\30\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\5\31\u0243\n\31\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\7\33\u025c\n\33\f\33\16\33\u025f\13\33\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u027a\n\34\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u0289\n\35\3\36"+
		"\3\36\3\36\2\t\32\34\36 \"$\64\37\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$&(*,.\60\62\64\668:\2\t\7\2\7\7\17\17\35\35!!#$\5\2\13\13\22\22"+
		"  \6\2\n\n\f\f\26\27\34\34\b\2\4\4\t\t\16\16\30\30\32\32&&\5\2\31\31\37"+
		"\37))\5\2\20\20\33\33++\3\289\u02ba\2<\3\2\2\2\4D\3\2\2\2\6^\3\2\2\2\b"+
		"r\3\2\2\2\n}\3\2\2\2\f\u0090\3\2\2\2\16\u009b\3\2\2\2\20\u00ba\3\2\2\2"+
		"\22\u00bc\3\2\2\2\24\u00bf\3\2\2\2\26\u00c2\3\2\2\2\30\u00c5\3\2\2\2\32"+
		"\u00cb\3\2\2\2\34\u00e8\3\2\2\2\36\u0118\3\2\2\2 \u0149\3\2\2\2\"\u0174"+
		"\3\2\2\2$\u0199\3\2\2\2&\u01bf\3\2\2\2(\u01c1\3\2\2\2*\u01cc\3\2\2\2,"+
		"\u021f\3\2\2\2.\u0221\3\2\2\2\60\u0242\3\2\2\2\62\u0244\3\2\2\2\64\u0250"+
		"\3\2\2\2\66\u0279\3\2\2\28\u0288\3\2\2\2:\u028a\3\2\2\2<=\7-\2\2=>\b\2"+
		"\1\2>?\7\6\2\2?@\5\4\3\2@A\5\6\4\2AB\7\b\2\2BC\5:\36\2C\3\3\2\2\2DE\7"+
		".\2\2EF\7\36\2\2FG\5,\27\2GH\7\25\2\2HI\5,\27\2IJ\7\"\2\2JK\b\3\1\2KL"+
		"\7\36\2\2LM\5\32\16\2MN\7\"\2\2NR\7\6\2\2OQ\5\16\b\2PO\3\2\2\2QT\3\2\2"+
		"\2RP\3\2\2\2RS\3\2\2\2SV\3\2\2\2TR\3\2\2\2UW\5\b\5\2VU\3\2\2\2VW\3\2\2"+
		"\2WX\3\2\2\2XZ\5\n\6\2Y[\5\f\7\2ZY\3\2\2\2Z[\3\2\2\2[\\\3\2\2\2\\]\7\b"+
		"\2\2]\5\3\2\2\2^_\7\64\2\2_`\7\36\2\2`a\58\35\2ab\7\"\2\2bc\b\4\1\2cg"+
		"\7\6\2\2df\5.\30\2ed\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2hm\3\2\2\2i"+
		"g\3\2\2\2jl\5\62\32\2kj\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2np\3\2\2"+
		"\2om\3\2\2\2pq\7\b\2\2q\7\3\2\2\2rs\7\61\2\2st\b\5\1\2tx\7\6\2\2uw\5\22"+
		"\n\2vu\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y{\3\2\2\2zx\3\2\2\2{|\7\b"+
		"\2\2|\t\3\2\2\2}~\7\62\2\2~\177\7\36\2\2\177\u0080\79\2\2\u0080\u0081"+
		"\7\25\2\2\u0081\u0082\79\2\2\u0082\u0083\7\"\2\2\u0083\u0084\7\23\2\2"+
		"\u0084\u0085\5\34\17\2\u0085\u0086\7(\2\2\u0086\u0087\b\6\1\2\u0087\u008b"+
		"\7\6\2\2\u0088\u008a\5\24\13\2\u0089\u0088\3\2\2\2\u008a\u008d\3\2\2\2"+
		"\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008e\3\2\2\2\u008d\u008b"+
		"\3\2\2\2\u008e\u008f\7\b\2\2\u008f\13\3\2\2\2\u0090\u0091\7\63\2\2\u0091"+
		"\u0092\b\7\1\2\u0092\u0096\7\6\2\2\u0093\u0095\5\26\f\2\u0094\u0093\3"+
		"\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097"+
		"\u0099\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009a\7\b\2\2\u009a\r\3\2\2\2"+
		"\u009b\u009c\7/\2\2\u009c\u009d\7<\2\2\u009d\u009e\7\36\2\2\u009e\u009f"+
		"\5,\27\2\u009f\u00a0\7\"\2\2\u00a0\u00a1\b\b\1\2\u00a1\u00a5\7\6\2\2\u00a2"+
		"\u00a4\5\20\t\2\u00a3\u00a2\3\2\2\2\u00a4\u00a7\3\2\2\2\u00a5\u00a3\3"+
		"\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a8\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a8"+
		"\u00a9\7\b\2\2\u00a9\17\3\2\2\2\u00aa\u00ab\7:\2\2\u00ab\u00ac\7\23\2"+
		"\2\u00ac\u00ad\5,\27\2\u00ad\u00ae\7(\2\2\u00ae\u00af\7\5\2\2\u00af\u00b0"+
		"\b\t\1\2\u00b0\u00bb\3\2\2\2\u00b1\u00b2\7;\2\2\u00b2\u00b3\7\23\2\2\u00b3"+
		"\u00b4\5,\27\2\u00b4\u00b5\7\25\2\2\u00b5\u00b6\5,\27\2\u00b6\u00b7\7"+
		"(\2\2\u00b7\u00b8\7\5\2\2\u00b8\u00b9\b\t\1\2\u00b9\u00bb\3\2\2\2\u00ba"+
		"\u00aa\3\2\2\2\u00ba\u00b1\3\2\2\2\u00bb\21\3\2\2\2\u00bc\u00bd\5\30\r"+
		"\2\u00bd\u00be\b\n\1\2\u00be\23\3\2\2\2\u00bf\u00c0\5\30\r\2\u00c0\u00c1"+
		"\b\13\1\2\u00c1\25\3\2\2\2\u00c2\u00c3\5\30\r\2\u00c3\u00c4\b\f\1\2\u00c4"+
		"\27\3\2\2\2\u00c5\u00c6\7<\2\2\u00c6\u00c7\7\7\2\2\u00c7\u00c8\5\36\20"+
		"\2\u00c8\u00c9\7\5\2\2\u00c9\u00ca\b\r\1\2\u00ca\31\3\2\2\2\u00cb\u00cc"+
		"\b\16\1\2\u00cc\u00cd\7<\2\2\u00cd\u00ce\b\16\1\2\u00ce\u00d5\3\2\2\2"+
		"\u00cf\u00d0\f\3\2\2\u00d0\u00d1\7\25\2\2\u00d1\u00d2\7<\2\2\u00d2\u00d4"+
		"\b\16\1\2\u00d3\u00cf\3\2\2\2\u00d4\u00d7\3\2\2\2\u00d5\u00d3\3\2\2\2"+
		"\u00d5\u00d6\3\2\2\2\u00d6\33\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d8\u00d9"+
		"\b\17\1\2\u00d9\u00da\5\36\20\2\u00da\u00db\t\2\2\2\u00db\u00dc\5\36\20"+
		"\2\u00dc\u00dd\b\17\1\2\u00dd\u00e9\3\2\2\2\u00de\u00df\7<\2\2\u00df\u00e0"+
		"\7\r\2\2\u00e0\u00e1\5\36\20\2\u00e1\u00e2\b\17\1\2\u00e2\u00e9\3\2\2"+
		"\2\u00e3\u00e4\7<\2\2\u00e4\u00e5\7\21\2\2\u00e5\u00e6\5\36\20\2\u00e6"+
		"\u00e7\b\17\1\2\u00e7\u00e9\3\2\2\2\u00e8\u00d8\3\2\2\2\u00e8\u00de\3"+
		"\2\2\2\u00e8\u00e3\3\2\2\2\u00e9\u00f1\3\2\2\2\u00ea\u00eb\f\3\2\2\u00eb"+
		"\u00ec\t\3\2\2\u00ec\u00ed\5\34\17\4\u00ed\u00ee\b\17\1\2\u00ee\u00f0"+
		"\3\2\2\2\u00ef\u00ea\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1"+
		"\u00f2\3\2\2\2\u00f2\35\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f4\u00f5\b\20\1"+
		"\2\u00f5\u00f6\7,\2\2\u00f6\u00f7\5\36\20\7\u00f7\u00f8\b\20\1\2\u00f8"+
		"\u0119\3\2\2\2\u00f9\u00fa\7*\2\2\u00fa\u00fb\5\36\20\6\u00fb\u00fc\b"+
		"\20\1\2\u00fc\u0119\3\2\2\2\u00fd\u00fe\5(\25\2\u00fe\u00ff\b\20\1\2\u00ff"+
		"\u0119\3\2\2\2\u0100\u0101\5,\27\2\u0101\u0102\b\20\1\2\u0102\u0119\3"+
		"\2\2\2\u0103\u0104\5&\24\2\u0104\u0105\b\20\1\2\u0105\u0119\3\2\2\2\u0106"+
		"\u0107\7\23\2\2\u0107\u0108\5\36\20\2\u0108\u0109\7(\2\2\u0109\u010a\b"+
		"\20\1\2\u010a\u0119\3\2\2\2\u010b\u010c\7 \2\2\u010c\u010d\5\36\20\2\u010d"+
		"\u010e\7 \2\2\u010e\u010f\b\20\1\2\u010f\u0119\3\2\2\2\u0110\u0111\7!"+
		"\2\2\u0111\u0112\5\36\20\2\u0112\u0113\7#\2\2\u0113\u0114\b\20\1\2\u0114"+
		"\u0119\3\2\2\2\u0115\u0116\5 \21\2\u0116\u0117\b\20\1\2\u0117\u0119\3"+
		"\2\2\2\u0118\u00f4\3\2\2\2\u0118\u00f9\3\2\2\2\u0118\u00fd\3\2\2\2\u0118"+
		"\u0100\3\2\2\2\u0118\u0103\3\2\2\2\u0118\u0106\3\2\2\2\u0118\u010b\3\2"+
		"\2\2\u0118\u0110\3\2\2\2\u0118\u0115\3\2\2\2\u0119\u0126\3\2\2\2\u011a"+
		"\u011b\f\5\2\2\u011b\u011c\7*\2\2\u011c\u011d\5\36\20\6\u011d\u011e\b"+
		"\20\1\2\u011e\u0125\3\2\2\2\u011f\u0120\f\4\2\2\u0120\u0121\7,\2\2\u0121"+
		"\u0122\5\36\20\5\u0122\u0123\b\20\1\2\u0123\u0125\3\2\2\2\u0124\u011a"+
		"\3\2\2\2\u0124\u011f\3\2\2\2\u0125\u0128\3\2\2\2\u0126\u0124\3\2\2\2\u0126"+
		"\u0127\3\2\2\2\u0127\37\3\2\2\2\u0128\u0126\3\2\2\2\u0129\u012a\b\21\1"+
		"\2\u012a\u012b\7\'\2\2\u012b\u012c\5 \21\5\u012c\u012d\b\21\1\2\u012d"+
		"\u014a\3\2\2\2\u012e\u012f\5(\25\2\u012f\u0130\b\21\1\2\u0130\u014a\3"+
		"\2\2\2\u0131\u0132\5*\26\2\u0132\u0133\b\21\1\2\u0133\u014a\3\2\2\2\u0134"+
		"\u0135\5&\24\2\u0135\u0136\b\21\1\2\u0136\u014a\3\2\2\2\u0137\u0138\7"+
		"\23\2\2\u0138\u0139\5\36\20\2\u0139\u013a\7(\2\2\u013a\u013b\b\21\1\2"+
		"\u013b\u014a\3\2\2\2\u013c\u013d\7 \2\2\u013d\u013e\5\36\20\2\u013e\u013f"+
		"\7 \2\2\u013f\u0140\b\21\1\2\u0140\u014a\3\2\2\2\u0141\u0142\7!\2\2\u0142"+
		"\u0143\5\36\20\2\u0143\u0144\7#\2\2\u0144\u0145\b\21\1\2\u0145\u014a\3"+
		"\2\2\2\u0146\u0147\5\"\22\2\u0147\u0148\b\21\1\2\u0148\u014a\3\2\2\2\u0149"+
		"\u0129\3\2\2\2\u0149\u012e\3\2\2\2\u0149\u0131\3\2\2\2\u0149\u0134\3\2"+
		"\2\2\u0149\u0137\3\2\2\2\u0149\u013c\3\2\2\2\u0149\u0141\3\2\2\2\u0149"+
		"\u0146\3\2\2\2\u014a\u0155\3\2\2\2\u014b\u014c\f\6\2\2\u014c\u014d\7\24"+
		"\2\2\u014d\u014e\5 \21\7\u014e\u014f\b\21\1\2\u014f\u0154\3\2\2\2\u0150"+
		"\u0151\f\4\2\2\u0151\u0152\7\'\2\2\u0152\u0154\b\21\1\2\u0153\u014b\3"+
		"\2\2\2\u0153\u0150\3\2\2\2\u0154\u0157\3\2\2\2\u0155\u0153\3\2\2\2\u0155"+
		"\u0156\3\2\2\2\u0156!\3\2\2\2\u0157\u0155\3\2\2\2\u0158\u0159\b\22\1\2"+
		"\u0159\u015a\5(\25\2\u015a\u015b\b\22\1\2\u015b\u0175\3\2\2\2\u015c\u015d"+
		"\5*\26\2\u015d\u015e\b\22\1\2\u015e\u0175\3\2\2\2\u015f\u0160\5&\24\2"+
		"\u0160\u0161\b\22\1\2\u0161\u0175\3\2\2\2\u0162\u0163\7\23\2\2\u0163\u0164"+
		"\5\36\20\2\u0164\u0165\7(\2\2\u0165\u0166\b\22\1\2\u0166\u0175\3\2\2\2"+
		"\u0167\u0168\7 \2\2\u0168\u0169\5\36\20\2\u0169\u016a\7 \2\2\u016a\u016b"+
		"\b\22\1\2\u016b\u0175\3\2\2\2\u016c\u016d\7!\2\2\u016d\u016e\5\36\20\2"+
		"\u016e\u016f\7#\2\2\u016f\u0170\b\22\1\2\u0170\u0175\3\2\2\2\u0171\u0172"+
		"\5$\23\2\u0172\u0173\b\22\1\2\u0173\u0175\3\2\2\2\u0174\u0158\3\2\2\2"+
		"\u0174\u015c\3\2\2\2\u0174\u015f\3\2\2\2\u0174\u0162\3\2\2\2\u0174\u0167"+
		"\3\2\2\2\u0174\u016c\3\2\2\2\u0174\u0171\3\2\2\2\u0175\u017d\3\2\2\2\u0176"+
		"\u0177\f\4\2\2\u0177\u0178\7\3\2\2\u0178\u0179\5\"\22\5\u0179\u017a\b"+
		"\22\1\2\u017a\u017c\3\2\2\2\u017b\u0176\3\2\2\2\u017c\u017f\3\2\2\2\u017d"+
		"\u017b\3\2\2\2\u017d\u017e\3\2\2\2\u017e#\3\2\2\2\u017f\u017d\3\2\2\2"+
		"\u0180\u0181\b\23\1\2\u0181\u0182\5(\25\2\u0182\u0183\b\23\1\2\u0183\u019a"+
		"\3\2\2\2\u0184\u0185\5*\26\2\u0185\u0186\b\23\1\2\u0186\u019a\3\2\2\2"+
		"\u0187\u0188\5&\24\2\u0188\u0189\b\23\1\2\u0189\u019a\3\2\2\2\u018a\u018b"+
		"\7\23\2\2\u018b\u018c\5\36\20\2\u018c\u018d\7(\2\2\u018d\u018e\b\23\1"+
		"\2\u018e\u019a\3\2\2\2\u018f\u0190\7 \2\2\u0190\u0191\5\36\20\2\u0191"+
		"\u0192\7 \2\2\u0192\u0193\b\23\1\2\u0193\u019a\3\2\2\2\u0194\u0195\7!"+
		"\2\2\u0195\u0196\5\36\20\2\u0196\u0197\7#\2\2\u0197\u0198\b\23\1\2\u0198"+
		"\u019a\3\2\2\2\u0199\u0180\3\2\2\2\u0199\u0184\3\2\2\2\u0199\u0187\3\2"+
		"\2\2\u0199\u018a\3\2\2\2\u0199\u018f\3\2\2\2\u0199\u0194\3\2\2\2\u019a"+
		"\u01a2\3\2\2\2\u019b\u019c\f\3\2\2\u019c\u019d\7\13\2\2\u019d\u019e\5"+
		"$\23\4\u019e\u019f\b\23\1\2\u019f\u01a1\3\2\2\2\u01a0\u019b\3\2\2\2\u01a1"+
		"\u01a4\3\2\2\2\u01a2\u01a0\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3%\3\2\2\2"+
		"\u01a4\u01a2\3\2\2\2\u01a5\u01a6\t\4\2\2\u01a6\u01a7\7\23\2\2\u01a7\u01a8"+
		"\5\36\20\2\u01a8\u01a9\7(\2\2\u01a9\u01aa\b\24\1\2\u01aa\u01c0\3\2\2\2"+
		"\u01ab\u01ac\t\5\2\2\u01ac\u01ad\7\23\2\2\u01ad\u01ae\5\36\20\2\u01ae"+
		"\u01af\7(\2\2\u01af\u01b0\b\24\1\2\u01b0\u01c0\3\2\2\2\u01b1\u01b2\t\6"+
		"\2\2\u01b2\u01b3\7\23\2\2\u01b3\u01b4\5\36\20\2\u01b4\u01b5\7(\2\2\u01b5"+
		"\u01b6\b\24\1\2\u01b6\u01c0\3\2\2\2\u01b7\u01b8\t\7\2\2\u01b8\u01b9\7"+
		"\23\2\2\u01b9\u01ba\5\36\20\2\u01ba\u01bb\7\25\2\2\u01bb\u01bc\5\36\20"+
		"\2\u01bc\u01bd\7(\2\2\u01bd\u01be\b\24\1\2\u01be\u01c0\3\2\2\2\u01bf\u01a5"+
		"\3\2\2\2\u01bf\u01ab\3\2\2\2\u01bf\u01b1\3\2\2\2\u01bf\u01b7\3\2\2\2\u01c0"+
		"\'\3\2\2\2\u01c1\u01c2\7<\2\2\u01c2\u01c3\b\25\1\2\u01c3)\3\2\2\2\u01c4"+
		"\u01c6\7*\2\2\u01c5\u01c4\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c6\u01c7\3\2"+
		"\2\2\u01c7\u01c8\t\b\2\2\u01c8\u01cd\b\26\1\2\u01c9\u01ca\7,\2\2\u01ca"+
		"\u01cb\t\b\2\2\u01cb\u01cd\b\26\1\2\u01cc\u01c5\3\2\2\2\u01cc\u01c9\3"+
		"\2\2\2\u01cd+\3\2\2\2\u01ce\u01d0\7!\2\2\u01cf\u01d1\7*\2\2\u01d0\u01cf"+
		"\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1\u01d2\3\2\2\2\u01d2\u01d3\t\b\2\2\u01d3"+
		"\u01d5\7\25\2\2\u01d4\u01d6\7*\2\2\u01d5\u01d4\3\2\2\2\u01d5\u01d6\3\2"+
		"\2\2\u01d6\u01d7\3\2\2\2\u01d7\u01d8\t\b\2\2\u01d8\u01d9\7#\2\2\u01d9"+
		"\u0220\b\27\1\2\u01da\u01dc\7!\2\2\u01db\u01dd\7*\2\2\u01dc\u01db\3\2"+
		"\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01de\3\2\2\2\u01de\u01df\t\b\2\2\u01df"+
		"\u01e0\7\25\2\2\u01e0\u01e1\7,\2\2\u01e1\u01e2\t\b\2\2\u01e2\u01e3\7#"+
		"\2\2\u01e3\u0220\b\27\1\2\u01e4\u01e5\7!\2\2\u01e5\u01e6\7,\2\2\u01e6"+
		"\u01e7\t\b\2\2\u01e7\u01e9\7\25\2\2\u01e8\u01ea\7*\2\2\u01e9\u01e8\3\2"+
		"\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01eb\3\2\2\2\u01eb\u01ec\t\b\2\2\u01ec"+
		"\u01ed\7#\2\2\u01ed\u0220\b\27\1\2\u01ee\u01ef\7!\2\2\u01ef\u01f0\7,\2"+
		"\2\u01f0\u01f1\t\b\2\2\u01f1\u01f2\7\25\2\2\u01f2\u01f3\7,\2\2\u01f3\u01f4"+
		"\t\b\2\2\u01f4\u01f5\7#\2\2\u01f5\u0220\b\27\1\2\u01f6\u01f8\7*\2\2\u01f7"+
		"\u01f6\3\2\2\2\u01f7\u01f8\3\2\2\2\u01f8\u01f9\3\2\2\2\u01f9\u01fa\t\b"+
		"\2\2\u01fa\u01fb\7*\2\2\u01fb\u01fc\t\b\2\2\u01fc\u01fd\7\'\2\2\u01fd"+
		"\u0220\b\27\1\2\u01fe\u0200\7*\2\2\u01ff\u01fe\3\2\2\2\u01ff\u0200\3\2"+
		"\2\2\u0200\u0201\3\2\2\2\u0201\u0202\t\b\2\2\u0202\u0203\7,\2\2\u0203"+
		"\u0204\t\b\2\2\u0204\u0205\7\'\2\2\u0205\u0220\b\27\1\2\u0206\u0208\7"+
		"*\2\2\u0207\u0206\3\2\2\2\u0207\u0208\3\2\2\2\u0208\u0209\3\2\2\2\u0209"+
		"\u020a\t\b\2\2\u020a\u020b\7\'\2\2\u020b\u0220\b\27\1\2\u020c\u020d\7"+
		",\2\2\u020d\u020e\t\b\2\2\u020e\u020f\7*\2\2\u020f\u0210\t\b\2\2\u0210"+
		"\u0211\7\'\2\2\u0211\u0220\b\27\1\2\u0212\u0213\7,\2\2\u0213\u0214\t\b"+
		"\2\2\u0214\u0215\7,\2\2\u0215\u0216\t\b\2\2\u0216\u0217\7\'\2\2\u0217"+
		"\u0220\b\27\1\2\u0218\u0219\7,\2\2\u0219\u021a\t\b\2\2\u021a\u021b\7\'"+
		"\2\2\u021b\u0220\b\27\1\2\u021c\u021d\5*\26\2\u021d\u021e\b\27\1\2\u021e"+
		"\u0220\3\2\2\2\u021f\u01ce\3\2\2\2\u021f\u01da\3\2\2\2\u021f\u01e4\3\2"+
		"\2\2\u021f\u01ee\3\2\2\2\u021f\u01f7\3\2\2\2\u021f\u01ff\3\2\2\2\u021f"+
		"\u0207\3\2\2\2\u021f\u020c\3\2\2\2\u021f\u0212\3\2\2\2\u021f\u0218\3\2"+
		"\2\2\u021f\u021c\3\2\2\2\u0220-\3\2\2\2\u0221\u0222\7\65\2\2\u0222\u0223"+
		"\7<\2\2\u0223\u0224\b\30\1\2\u0224\u0226\7\6\2\2\u0225\u0227\5\60\31\2"+
		"\u0226\u0225\3\2\2\2\u0227\u0228\3\2\2\2\u0228\u0226\3\2\2\2\u0228\u0229"+
		"\3\2\2\2\u0229\u022a\3\2\2\2\u022a\u022b\7\b\2\2\u022b/\3\2\2\2\u022c"+
		"\u022d\7\36\2\2\u022d\u022e\58\35\2\u022e\u022f\7#\2\2\u022f\u0230\58"+
		"\35\2\u0230\u0231\7\25\2\2\u0231\u0232\79\2\2\u0232\u0233\7\25\2\2\u0233"+
		"\u0234\5\36\20\2\u0234\u0235\7\"\2\2\u0235\u0236\7\5\2\2\u0236\u0237\b"+
		"\31\1\2\u0237\u0243\3\2\2\2\u0238\u0239\7\36\2\2\u0239\u023a\58\35\2\u023a"+
		"\u023b\7#\2\2\u023b\u023c\58\35\2\u023c\u023d\7\25\2\2\u023d\u023e\79"+
		"\2\2\u023e\u023f\7\"\2\2\u023f\u0240\7\5\2\2\u0240\u0241\b\31\1\2\u0241"+
		"\u0243\3\2\2\2\u0242\u022c\3\2\2\2\u0242\u0238\3\2\2\2\u0243\61\3\2\2"+
		"\2\u0244\u0245\7\66\2\2\u0245\u0246\7\23\2\2\u0246\u0247\5\64\33\2\u0247"+
		"\u0248\7(\2\2\u0248\u0249\7\36\2\2\u0249\u024a\t\b\2\2\u024a\u024b\7\""+
		"\2\2\u024b\u024c\7\6\2\2\u024c\u024d\5\66\34\2\u024d\u024e\7\b\2\2\u024e"+
		"\u024f\b\32\1\2\u024f\63\3\2\2\2\u0250\u0251\b\33\1\2\u0251\u0252\5\36"+
		"\20\2\u0252\u0253\t\2\2\2\u0253\u0254\5\36\20\2\u0254\u0255\b\33\1\2\u0255"+
		"\u025d\3\2\2\2\u0256\u0257\f\3\2\2\u0257\u0258\t\3\2\2\u0258\u0259\5\64"+
		"\33\4\u0259\u025a\b\33\1\2\u025a\u025c\3\2\2\2\u025b\u0256\3\2\2\2\u025c"+
		"\u025f\3\2\2\2\u025d\u025b\3\2\2\2\u025d\u025e\3\2\2\2\u025e\65\3\2\2"+
		"\2\u025f\u025d\3\2\2\2\u0260\u0261\5\36\20\2\u0261\u0262\b\34\1\2\u0262"+
		"\u027a\3\2\2\2\u0263\u0264\5\36\20\2\u0264\u0265\7\25\2\2\u0265\u0266"+
		"\5\36\20\2\u0266\u0267\7\25\2\2\u0267\u0268\5\36\20\2\u0268\u0269\b\34"+
		"\1\2\u0269\u027a\3\2\2\2\u026a\u026b\5\36\20\2\u026b\u026c\7\25\2\2\u026c"+
		"\u026d\5\36\20\2\u026d\u026e\7\25\2\2\u026e\u026f\5\36\20\2\u026f\u0270"+
		"\7\25\2\2\u0270\u0271\5\36\20\2\u0271\u0272\b\34\1\2\u0272\u027a\3\2\2"+
		"\2\u0273\u0274\7<\2\2\u0274\u0275\7\36\2\2\u0275\u0276\5\36\20\2\u0276"+
		"\u0277\7\"\2\2\u0277\u0278\b\34\1\2\u0278\u027a\3\2\2\2\u0279\u0260\3"+
		"\2\2\2\u0279\u0263\3\2\2\2\u0279\u026a\3\2\2\2\u0279\u0273\3\2\2\2\u027a"+
		"\67\3\2\2\2\u027b\u027c\7\23\2\2\u027c\u027d\t\b\2\2\u027d\u027e\7\25"+
		"\2\2\u027e\u027f\t\b\2\2\u027f\u0280\7\25\2\2\u0280\u0281\t\b\2\2\u0281"+
		"\u0282\7\25\2\2\u0282\u0283\t\b\2\2\u0283\u0284\7(\2\2\u0284\u0289\b\35"+
		"\1\2\u0285\u0286\7%\2\2\u0286\u0287\7\67\2\2\u0287\u0289\b\35\1\2\u0288"+
		"\u027b\3\2\2\2\u0288\u0285\3\2\2\2\u02899\3\2\2\2\u028a\u028b\7\2\2\3"+
		"\u028b;\3\2\2\2)RVZgmx\u008b\u0096\u00a5\u00ba\u00d5\u00e8\u00f1\u0118"+
		"\u0124\u0126\u0149\u0153\u0155\u0174\u017d\u0199\u01a2\u01bf\u01c5\u01cc"+
		"\u01d0\u01d5\u01dc\u01e9\u01f7\u01ff\u0207\u021f\u0228\u0242\u025d\u0279"+
		"\u0288";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}