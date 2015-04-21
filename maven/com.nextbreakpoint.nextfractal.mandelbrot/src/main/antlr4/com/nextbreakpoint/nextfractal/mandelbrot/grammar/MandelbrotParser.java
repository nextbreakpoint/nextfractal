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
		T__44=1, T__43=2, T__42=3, T__41=4, T__40=5, T__39=6, T__38=7, T__37=8, 
		T__36=9, T__35=10, T__34=11, T__33=12, T__32=13, T__31=14, T__30=15, T__29=16, 
		T__28=17, T__27=18, T__26=19, T__25=20, T__24=21, T__23=22, T__22=23, 
		T__21=24, T__20=25, T__19=26, T__18=27, T__17=28, T__16=29, T__15=30, 
		T__14=31, T__13=32, T__12=33, T__11=34, T__10=35, T__9=36, T__8=37, T__7=38, 
		T__6=39, T__5=40, T__4=41, T__3=42, T__2=43, T__1=44, T__0=45, FRACTAL=46, 
		ORBIT=47, TRAP=48, CONDITION=49, BEGIN=50, LOOP=51, END=52, COLOR=53, 
		PALETTE=54, RULE=55, ARGB=56, RATIONAL=57, INTEGER=58, PATHOP_1POINTS=59, 
		PATHOP_2POINTS=60, VARIABLE=61, COMMENT=62, WHITESPACE=63;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'2pi'", "'cos'", "';'", "'{'", "'='", "'}'", "'asin'", 
		"'mod2'", "'^'", "'im'", "'?'", "'sin'", "'<='", "'pow'", "'~?'", "'&'", 
		"'('", "'*'", "','", "'pha'", "'re'", "'atan'", "'sqrt'", "'tan'", "'atan2'", 
		"'pi'", "'mod'", "'>='", "'['", "'log'", "'|'", "'<'", "']'", "'>'", "'<>'", 
		"'#'", "'e'", "'acos'", "'i'", "')'", "'exp'", "'+'", "'hypot'", "'-'", 
		"'fractal'", "'orbit'", "'trap'", "'condition'", "'begin'", "'loop'", 
		"'end'", "'color'", "'palette'", "'rule'", "ARGB", "RATIONAL", "INTEGER", 
		"PATHOP_1POINTS", "PATHOP_2POINTS", "VARIABLE", "COMMENT", "WHITESPACE"
	};
	public static final int
		RULE_fractal = 0, RULE_orbit = 1, RULE_color = 2, RULE_begin = 3, RULE_loop = 4, 
		RULE_end = 5, RULE_trap = 6, RULE_pathop = 7, RULE_beginstatements = 8, 
		RULE_loopstatements = 9, RULE_endstatements = 10, RULE_statement = 11, 
		RULE_variablelist = 12, RULE_conditionexp = 13, RULE_expression = 14, 
		RULE_expression2 = 15, RULE_expression3 = 16, RULE_expression4 = 17, RULE_function = 18, 
		RULE_constant = 19, RULE_variable = 20, RULE_real = 21, RULE_complex = 22, 
		RULE_palette = 23, RULE_paletteelement = 24, RULE_colorrule = 25, RULE_ruleexp = 26, 
		RULE_colorexp = 27, RULE_colorargb = 28, RULE_eof = 29;
	public static final String[] ruleNames = {
		"fractal", "orbit", "color", "begin", "loop", "end", "trap", "pathop", 
		"beginstatements", "loopstatements", "endstatements", "statement", "variablelist", 
		"conditionexp", "expression", "expression2", "expression3", "expression4", 
		"function", "constant", "variable", "real", "complex", "palette", "paletteelement", 
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
			setState(60); ((FractalContext)_localctx).f = match(FRACTAL);

					builder.setFractal(new ASTFractal(((FractalContext)_localctx).f));
				
			setState(62); match(5);
			setState(63); orbit();
			setState(64); color();
			setState(65); match(7);
			setState(66); eof();
			}
		}
		catch (RecognitionException re) {
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
			setState(68); ((OrbitContext)_localctx).o = match(ORBIT);
			setState(69); match(30);
			setState(70); ((OrbitContext)_localctx).ra = complex();
			setState(71); match(20);
			setState(72); ((OrbitContext)_localctx).rb = complex();
			setState(73); match(34);

					builder.setOrbit(new ASTOrbit(((OrbitContext)_localctx).o, new ASTRegion(((OrbitContext)_localctx).ra.result, ((OrbitContext)_localctx).rb.result)));
				
			setState(75); match(30);
			setState(76); ((OrbitContext)_localctx).v = variablelist(0);
			setState(77); match(34);
			setState(78); match(5);
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TRAP) {
				{
				{
				setState(79); trap();
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(86);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(85); begin();
				}
			}

			setState(88); loop();
			setState(90);
			_la = _input.LA(1);
			if (_la==END) {
				{
				setState(89); end();
				}
			}

			setState(92); match(7);
			}
		}
		catch (RecognitionException re) {
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
			setState(94); ((ColorContext)_localctx).c = match(COLOR);
			setState(95); match(30);
			setState(96); ((ColorContext)_localctx).argb = colorargb();
			setState(97); match(34);
			 
					builder.setColor(new ASTColor(((ColorContext)_localctx).c, ((ColorContext)_localctx).argb.result));
				
			setState(99); match(5);
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PALETTE) {
				{
				{
				setState(100); palette();
				}
				}
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RULE) {
				{
				{
				setState(106); colorrule();
				}
				}
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(112); match(7);
			}
		}
		catch (RecognitionException re) {
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
			setState(114); ((BeginContext)_localctx).b = match(BEGIN);
			 
					builder.setOrbitBegin(new ASTOrbitBegin(((BeginContext)_localctx).b));
				
			setState(116); match(5);
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VARIABLE) {
				{
				{
				setState(117); beginstatements();
				}
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(123); match(7);
			}
		}
		catch (RecognitionException re) {
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
		public List<TerminalNode> INTEGER() { return getTokens(MandelbrotParser.INTEGER); }
		public TerminalNode LOOP() { return getToken(MandelbrotParser.LOOP, 0); }
		public ConditionexpContext conditionexp() {
			return getRuleContext(ConditionexpContext.class,0);
		}
		public List<LoopstatementsContext> loopstatements() {
			return getRuleContexts(LoopstatementsContext.class);
		}
		public TerminalNode INTEGER(int i) {
			return getToken(MandelbrotParser.INTEGER, i);
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
			setState(125); ((LoopContext)_localctx).l = match(LOOP);
			setState(126); match(30);
			setState(127); ((LoopContext)_localctx).lb = match(INTEGER);
			setState(128); match(20);
			setState(129); ((LoopContext)_localctx).le = match(INTEGER);
			setState(130); match(34);
			setState(131); match(18);
			setState(132); ((LoopContext)_localctx).e = conditionexp(0);
			setState(133); match(41);

					builder.setOrbitLoop(new ASTOrbitLoop(((LoopContext)_localctx).l, builder.parseInt((((LoopContext)_localctx).lb!=null?((LoopContext)_localctx).lb.getText():null)), builder.parseInt((((LoopContext)_localctx).le!=null?((LoopContext)_localctx).le.getText():null)), ((LoopContext)_localctx).e.result));
				
			setState(135); match(5);
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VARIABLE) {
				{
				{
				setState(136); loopstatements();
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(142); match(7);
			}
		}
		catch (RecognitionException re) {
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
			setState(144); ((EndContext)_localctx).e = match(END);

					builder.setOrbitEnd(new ASTOrbitEnd(((EndContext)_localctx).e));		
				
			setState(146); match(5);
			setState(150);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VARIABLE) {
				{
				{
				setState(147); endstatements();
				}
				}
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(153); match(7);
			}
		}
		catch (RecognitionException re) {
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
			setState(155); ((TrapContext)_localctx).t = match(TRAP);
			setState(156); ((TrapContext)_localctx).n = match(VARIABLE);
			setState(157); match(30);
			setState(158); ((TrapContext)_localctx).c = complex();
			setState(159); match(34);

					builder.addOrbitTrap(new ASTOrbitTrap(((TrapContext)_localctx).t, (((TrapContext)_localctx).n!=null?((TrapContext)_localctx).n.getText():null), ((TrapContext)_localctx).c.result));
				
			setState(161); match(5);
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PATHOP_1POINTS || _la==PATHOP_2POINTS) {
				{
				{
				setState(162); pathop();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(168); match(7);
			}
		}
		catch (RecognitionException re) {
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
			setState(186);
			switch (_input.LA(1)) {
			case PATHOP_1POINTS:
				enterOuterAlt(_localctx, 1);
				{
				setState(170); ((PathopContext)_localctx).o = match(PATHOP_1POINTS);
				setState(171); match(18);
				setState(172); ((PathopContext)_localctx).c = complex();
				setState(173); match(41);
				setState(174); match(4);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c.result));
					
				}
				break;
			case PATHOP_2POINTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(177); ((PathopContext)_localctx).o = match(PATHOP_2POINTS);
				setState(178); match(18);
				setState(179); ((PathopContext)_localctx).c1 = complex();
				setState(180); match(20);
				setState(181); ((PathopContext)_localctx).c2 = complex();
				setState(182); match(41);
				setState(183); match(4);

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
			setState(188); ((BeginstatementsContext)_localctx).s = statement();

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
			setState(191); ((LoopstatementsContext)_localctx).s = statement();

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
			setState(194); ((EndstatementsContext)_localctx).s = statement();

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
		public TerminalNode VARIABLE() { return getToken(MandelbrotParser.VARIABLE, 0); }
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
			setState(197); ((StatementContext)_localctx).v = match(VARIABLE);
			setState(198); match(6);
			setState(199); ((StatementContext)_localctx).e = expression(0);
			setState(200); match(4);

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
			setState(204); ((VariablelistContext)_localctx).v = match(VARIABLE);

					builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(213);
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
					setState(207);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(208); match(20);
					setState(209); ((VariablelistContext)_localctx).v = match(VARIABLE);

					          		builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
					          	
					}
					} 
				}
				setState(215);
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
			setState(232);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(217); ((ConditionexpContext)_localctx).e1 = expression(0);
				setState(218);
				((ConditionexpContext)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 14) | (1L << 29) | (1L << 33) | (1L << 35) | (1L << 36))) != 0)) ) {
					((ConditionexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(219); ((ConditionexpContext)_localctx).e2 = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionCompareOp(((ConditionexpContext)_localctx).e1.result.getLocation(), (((ConditionexpContext)_localctx).o!=null?((ConditionexpContext)_localctx).o.getText():null), ((ConditionexpContext)_localctx).e1.result, ((ConditionexpContext)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(222); ((ConditionexpContext)_localctx).v = match(VARIABLE);
				setState(223); match(12);
				setState(224); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, true);
					
				}
				break;

			case 3:
				{
				setState(227); ((ConditionexpContext)_localctx).v = match(VARIABLE);
				setState(228); match(16);
				setState(229); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, false);
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(241);
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
					setState(234);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(235);
					((ConditionexpContext)_localctx).l = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 10) | (1L << 17) | (1L << 32))) != 0)) ) {
						((ConditionexpContext)_localctx).l = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(236); ((ConditionexpContext)_localctx).c2 = conditionexp(2);

					          		((ConditionexpContext)_localctx).result =  new ASTConditionLogicOp(((ConditionexpContext)_localctx).c1.result.getLocation(), (((ConditionexpContext)_localctx).l!=null?((ConditionexpContext)_localctx).l.getText():null), ((ConditionexpContext)_localctx).c1.result, ((ConditionexpContext)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(243);
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
			setState(283);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(245); ((ExpressionContext)_localctx).s = match(45);
				setState(246); ((ExpressionContext)_localctx).e = expression(5);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 2:
				{
				setState(249); ((ExpressionContext)_localctx).s = match(43);
				setState(250); ((ExpressionContext)_localctx).e = expression(4);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 3:
				{
				setState(253); ((ExpressionContext)_localctx).p = constant();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).p.result;
					
				}
				break;

			case 4:
				{
				setState(256); ((ExpressionContext)_localctx).v = variable();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).v.result;
					
				}
				break;

			case 5:
				{
				setState(259); ((ExpressionContext)_localctx).c = complex();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).c.result;
					
				}
				break;

			case 6:
				{
				setState(262); ((ExpressionContext)_localctx).f = function();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).f.result;
					
				}
				break;

			case 7:
				{
				setState(265); ((ExpressionContext)_localctx).t = match(18);
				setState(266); ((ExpressionContext)_localctx).e = expression(0);
				setState(267); match(41);

						((ExpressionContext)_localctx).result =  new ASTParen(((ExpressionContext)_localctx).t, ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 8:
				{
				setState(270); ((ExpressionContext)_localctx).m = match(32);
				setState(271); ((ExpressionContext)_localctx).e = expression(0);
				setState(272); match(32);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).m, "mod", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 9:
				{
				setState(275); ((ExpressionContext)_localctx).a = match(33);
				setState(276); ((ExpressionContext)_localctx).e = expression(0);
				setState(277); match(35);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).a, "pha", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 10:
				{
				setState(280); ((ExpressionContext)_localctx).e3 = expression2(0);

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e3.result;	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(297);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(295);
					switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(285);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(286); ((ExpressionContext)_localctx).s = match(43);
						setState(287); ((ExpressionContext)_localctx).e2 = expression(4);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;

					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(290);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(291); ((ExpressionContext)_localctx).s = match(45);
						setState(292); ((ExpressionContext)_localctx).e2 = expression(3);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					}
					} 
				}
				setState(299);
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
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(301); ((Expression2Context)_localctx).i = match(40);
				setState(302); ((Expression2Context)_localctx).e2 = expression2(3);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(305); ((Expression2Context)_localctx).p = constant();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).p.result;
					
				}
				break;

			case 3:
				{
				setState(308); ((Expression2Context)_localctx).v = variable();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).v.result;
					
				}
				break;

			case 4:
				{
				setState(311); ((Expression2Context)_localctx).r = real();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).r.result;
					
				}
				break;

			case 5:
				{
				setState(314); ((Expression2Context)_localctx).f = function();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).f.result;
					
				}
				break;

			case 6:
				{
				setState(317); ((Expression2Context)_localctx).t = match(18);
				setState(318); ((Expression2Context)_localctx).e = expression(0);
				setState(319); match(41);

						((Expression2Context)_localctx).result =  new ASTParen(((Expression2Context)_localctx).t, ((Expression2Context)_localctx).e.result);
					
				}
				break;

			case 7:
				{
				setState(322); ((Expression2Context)_localctx).m = match(32);
				setState(323); ((Expression2Context)_localctx).e = expression(0);
				setState(324); match(32);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).m, "mod", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(327); ((Expression2Context)_localctx).a = match(33);
				setState(328); ((Expression2Context)_localctx).e = expression(0);
				setState(329); match(35);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).a, "pha", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 9:
				{
				setState(332); ((Expression2Context)_localctx).e3 = expression3(0);

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(347);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(345);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(337);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(338); ((Expression2Context)_localctx).s = match(19);
						setState(339); ((Expression2Context)_localctx).e2 = expression2(5);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "*", ((Expression2Context)_localctx).e1.result, ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;

					case 2:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e2 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(342);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(343); ((Expression2Context)_localctx).i = match(40);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					}
					} 
				}
				setState(349);
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
			setState(381);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(351); ((Expression3Context)_localctx).p = constant();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).p.result;
					
				}
				break;

			case 2:
				{
				setState(354); ((Expression3Context)_localctx).v = variable();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).v.result;
					
				}
				break;

			case 3:
				{
				setState(357); ((Expression3Context)_localctx).r = real();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).r.result;
					
				}
				break;

			case 4:
				{
				setState(360); ((Expression3Context)_localctx).f = function();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).f.result;
					
				}
				break;

			case 5:
				{
				setState(363); ((Expression3Context)_localctx).t = match(18);
				setState(364); ((Expression3Context)_localctx).e = expression(0);
				setState(365); match(41);

						((Expression3Context)_localctx).result =  new ASTParen(((Expression3Context)_localctx).t, ((Expression3Context)_localctx).e.result);
					
				}
				break;

			case 6:
				{
				setState(368); ((Expression3Context)_localctx).m = match(32);
				setState(369); ((Expression3Context)_localctx).e = expression(0);
				setState(370); match(32);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).m, "mod", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 7:
				{
				setState(373); ((Expression3Context)_localctx).a = match(33);
				setState(374); ((Expression3Context)_localctx).e = expression(0);
				setState(375); match(35);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).a, "pha", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(378); ((Expression3Context)_localctx).e3 = expression4(0);

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(390);
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
					setState(383);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(384); ((Expression3Context)_localctx).s = match(1);
					setState(385); ((Expression3Context)_localctx).e2 = expression3(3);

					          		((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).s, "/", ((Expression3Context)_localctx).e1.result, ((Expression3Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(392);
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
			setState(421);
			switch (_input.LA(1)) {
			case 2:
			case 27:
			case 38:
				{
				setState(394); ((Expression4Context)_localctx).p = constant();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).p.result;
					
				}
				break;
			case VARIABLE:
				{
				setState(397); ((Expression4Context)_localctx).v = variable();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).v.result;
					
				}
				break;
			case 43:
			case 45:
			case RATIONAL:
			case INTEGER:
				{
				setState(400); ((Expression4Context)_localctx).r = real();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).r.result;
					
				}
				break;
			case 3:
			case 8:
			case 9:
			case 11:
			case 13:
			case 15:
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
			case 26:
			case 28:
			case 31:
			case 39:
			case 42:
			case 44:
				{
				setState(403); ((Expression4Context)_localctx).f = function();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).f.result;
					
				}
				break;
			case 18:
				{
				setState(406); ((Expression4Context)_localctx).t = match(18);
				setState(407); ((Expression4Context)_localctx).e = expression(0);
				setState(408); match(41);

						((Expression4Context)_localctx).result =  new ASTParen(((Expression4Context)_localctx).t, ((Expression4Context)_localctx).e.result);
					
				}
				break;
			case 32:
				{
				setState(411); ((Expression4Context)_localctx).m = match(32);
				setState(412); ((Expression4Context)_localctx).e = expression(0);
				setState(413); match(32);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).m, "mod", ((Expression4Context)_localctx).e.result);	
					
				}
				break;
			case 33:
				{
				setState(416); ((Expression4Context)_localctx).a = match(33);
				setState(417); ((Expression4Context)_localctx).e = expression(0);
				setState(418); match(35);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).a, "pha", ((Expression4Context)_localctx).e.result);	
					
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(430);
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
					setState(423);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(424); ((Expression4Context)_localctx).s = match(10);
					setState(425); ((Expression4Context)_localctx).e2 = expression4(2);

					          		((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).s, "^", ((Expression4Context)_localctx).e1.result, ((Expression4Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(432);
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
			setState(459);
			switch (_input.LA(1)) {
			case 9:
			case 11:
			case 21:
			case 22:
			case 28:
				enterOuterAlt(_localctx, 1);
				{
				setState(433);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 9) | (1L << 11) | (1L << 21) | (1L << 22) | (1L << 28))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(434); match(18);
				setState(435); ((FunctionContext)_localctx).e = expression(0);
				setState(436); match(41);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), ((FunctionContext)_localctx).e.result);		
					
				}
				break;
			case 3:
			case 8:
			case 13:
			case 23:
			case 25:
			case 39:
				enterOuterAlt(_localctx, 2);
				{
				setState(439);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 8) | (1L << 13) | (1L << 23) | (1L << 25) | (1L << 39))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(440); match(18);
				setState(441); ((FunctionContext)_localctx).e = expression(0);
				setState(442); match(41);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 24:
			case 31:
			case 42:
				enterOuterAlt(_localctx, 3);
				{
				setState(445);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 24) | (1L << 31) | (1L << 42))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(446); match(18);
				setState(447); ((FunctionContext)_localctx).e = expression(0);
				setState(448); match(41);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 15:
			case 26:
			case 44:
				enterOuterAlt(_localctx, 4);
				{
				setState(451);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 15) | (1L << 26) | (1L << 44))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(452); match(18);
				setState(453); ((FunctionContext)_localctx).e1 = expression(0);
				setState(454); match(20);
				setState(455); ((FunctionContext)_localctx).e2 = expression(0);
				setState(456); match(41);

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
			setState(467);
			switch (_input.LA(1)) {
			case 38:
				enterOuterAlt(_localctx, 1);
				{
				setState(461); ((ConstantContext)_localctx).p = match(38);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.E);
					
				}
				break;
			case 27:
				enterOuterAlt(_localctx, 2);
				{
				setState(463); ((ConstantContext)_localctx).p = match(27);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.PI);
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 3);
				{
				setState(465); ((ConstantContext)_localctx).p = match(2);

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
			setState(469); ((VariableContext)_localctx).v = match(VARIABLE);

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
			setState(480);
			switch (_input.LA(1)) {
			case 43:
			case RATIONAL:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(473);
				_la = _input.LA(1);
				if (_la==43) {
					{
					setState(472); match(43);
					}
				}

				setState(475);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((RealContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();

						((RealContext)_localctx).result =  new ASTNumber(((RealContext)_localctx).r, builder.parseDouble((((RealContext)_localctx).r!=null?((RealContext)_localctx).r.getText():null)));
					
				}
				break;
			case 45:
				enterOuterAlt(_localctx, 2);
				{
				setState(477); match(45);
				setState(478);
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
			setState(563);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(482); match(33);
				setState(484);
				_la = _input.LA(1);
				if (_la==43) {
					{
					setState(483); match(43);
					}
				}

				setState(486);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(487); match(20);
				setState(489);
				_la = _input.LA(1);
				if (_la==43) {
					{
					setState(488); match(43);
					}
				}

				setState(491);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(492); match(35);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(494); match(33);
				setState(496);
				_la = _input.LA(1);
				if (_la==43) {
					{
					setState(495); match(43);
					}
				}

				setState(498);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(499); match(20);
				setState(500); match(45);
				setState(501);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(502); match(35);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(504); match(33);
				setState(505); match(45);
				setState(506);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(507); match(20);
				setState(509);
				_la = _input.LA(1);
				if (_la==43) {
					{
					setState(508); match(43);
					}
				}

				setState(511);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(512); match(35);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(514); match(33);
				setState(515); match(45);
				setState(516);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(517); match(20);
				setState(518); match(45);
				setState(519);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(520); match(35);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(523);
				_la = _input.LA(1);
				if (_la==43) {
					{
					setState(522); match(43);
					}
				}

				setState(525);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(526); match(43);
				setState(527);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(528); match(40);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(531);
				_la = _input.LA(1);
				if (_la==43) {
					{
					setState(530); match(43);
					}
				}

				setState(533);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(534); match(45);
				setState(535);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(536); match(40);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(539);
				_la = _input.LA(1);
				if (_la==43) {
					{
					setState(538); match(43);
					}
				}

				setState(541);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(542); match(40);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble((((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(544); match(45);
				setState(545);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(546); match(43);
				setState(547);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(548); match(40);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(550); match(45);
				setState(551);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(552); match(45);
				setState(553);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(554); match(40);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(556); match(45);
				setState(557);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(558); match(40);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(560); ((ComplexContext)_localctx).rn = real();

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
			setState(565); ((PaletteContext)_localctx).p = match(PALETTE);
			setState(566); ((PaletteContext)_localctx).v = match(VARIABLE);

					builder.addPalette(new ASTPalette(((PaletteContext)_localctx).p, (((PaletteContext)_localctx).v!=null?((PaletteContext)_localctx).v.getText():null))); 
				
			setState(568); match(5);
			setState(570); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(569); paletteelement();
				}
				}
				setState(572); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==30 );
			setState(574); match(7);
			}
		}
		catch (RecognitionException re) {
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
			setState(598);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(576); ((PaletteelementContext)_localctx).t = match(30);
				setState(577); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(578); match(35);
				setState(579); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(580); match(20);
				setState(581); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(582); match(20);
				setState(583); ((PaletteelementContext)_localctx).e = expression(0);
				setState(584); match(34);
				setState(585); match(4);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), ((PaletteelementContext)_localctx).e.result));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(588); ((PaletteelementContext)_localctx).t = match(30);
				setState(589); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(590); match(35);
				setState(591); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(592); match(20);
				setState(593); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(594); match(34);
				setState(595); match(4);

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
		enterRule(_localctx, 50, RULE_colorrule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(600); ((ColorruleContext)_localctx).t = match(RULE);
			setState(601); match(18);
			setState(602); ((ColorruleContext)_localctx).r = ruleexp(0);
			setState(603); match(41);
			setState(604); match(30);
			setState(605);
			((ColorruleContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==RATIONAL || _la==INTEGER) ) {
				((ColorruleContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(606); match(34);
			setState(607); match(5);
			setState(608); ((ColorruleContext)_localctx).c = colorexp();
			setState(609); match(7);

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
		int _startState = 52;
		enterRecursionRule(_localctx, 52, RULE_ruleexp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(613); ((RuleexpContext)_localctx).e1 = expression(0);
			setState(614);
			((RuleexpContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 14) | (1L << 29) | (1L << 33) | (1L << 35) | (1L << 36))) != 0)) ) {
				((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(615); ((RuleexpContext)_localctx).e2 = expression(0);

					((RuleexpContext)_localctx).result =  new ASTRuleCompareOpExpression(((RuleexpContext)_localctx).e1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).e1.result, ((RuleexpContext)_localctx).e2.result);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(625);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,37,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RuleexpContext(_parentctx, _parentState);
					_localctx.r1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_ruleexp);
					setState(618);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(619);
					((RuleexpContext)_localctx).o = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 10) | (1L << 17) | (1L << 32))) != 0)) ) {
						((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(620); ((RuleexpContext)_localctx).r2 = ruleexp(2);

					          		((RuleexpContext)_localctx).result =  new ASTRuleLogicOpExpression(((RuleexpContext)_localctx).r1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).r1.result, ((RuleexpContext)_localctx).r2.result);
					          	
					}
					} 
				}
				setState(627);
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
		enterRule(_localctx, 54, RULE_colorexp);
		try {
			setState(653);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(628); ((ColorexpContext)_localctx).e1 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(631); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(632); match(20);
				setState(633); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(634); match(20);
				setState(635); ((ColorexpContext)_localctx).e3 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(638); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(639); match(20);
				setState(640); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(641); match(20);
				setState(642); ((ColorexpContext)_localctx).e3 = expression(0);
				setState(643); match(20);
				setState(644); ((ColorexpContext)_localctx).e4 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result, ((ColorexpContext)_localctx).e4.result);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(647); ((ColorexpContext)_localctx).v = match(VARIABLE);
				setState(648); match(30);
				setState(649); ((ColorexpContext)_localctx).e = expression(0);
				setState(650); match(34);

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
		enterRule(_localctx, 56, RULE_colorargb);
		int _la;
		try {
			setState(668);
			switch (_input.LA(1)) {
			case 18:
				enterOuterAlt(_localctx, 1);
				{
				setState(655); match(18);
				setState(656);
				((ColorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(657); match(20);
				setState(658);
				((ColorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(659); match(20);
				setState(660);
				((ColorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(661); match(20);
				setState(662);
				((ColorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(663); match(41);

						((ColorargbContext)_localctx).result =  new ASTColorARGB(builder.parseFloat((((ColorargbContext)_localctx).a!=null?((ColorargbContext)_localctx).a.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).r!=null?((ColorargbContext)_localctx).r.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).g!=null?((ColorargbContext)_localctx).g.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).b!=null?((ColorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case 37:
				enterOuterAlt(_localctx, 2);
				{
				setState(665); match(37);
				setState(666); ((ColorargbContext)_localctx).argb = match(ARGB);

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
		enterRule(_localctx, 58, RULE_eof);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(670); match(EOF);
			}
		}
		catch (RecognitionException re) {
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

		case 26: return ruleexp_sempred((RuleexpContext)_localctx, predIndex);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3A\u02a3\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\7\3S\n\3\f\3\16\3V\13\3\3\3\5\3Y\n\3\3\3\3\3\5\3]\n\3\3\3\3\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\7\4h\n\4\f\4\16\4k\13\4\3\4\7\4n\n\4\f\4\16\4q\13"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\7\5y\n\5\f\5\16\5|\13\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u008c\n\6\f\6\16\6\u008f\13\6"+
		"\3\6\3\6\3\7\3\7\3\7\3\7\7\7\u0097\n\7\f\7\16\7\u009a\13\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00a6\n\b\f\b\16\b\u00a9\13\b\3\b\3\b"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00bd"+
		"\n\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u00d6\n\16\f\16\16\16\u00d9"+
		"\13\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\5\17\u00eb\n\17\3\17\3\17\3\17\3\17\3\17\7\17\u00f2\n"+
		"\17\f\17\16\17\u00f5\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\5\20\u011e\n\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\7\20\u012a\n\20\f\20\16\20\u012d\13\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\5\21\u0152\n\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\7\21"+
		"\u015c\n\21\f\21\16\21\u015f\13\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u0180\n\22\3\22"+
		"\3\22\3\22\3\22\3\22\7\22\u0187\n\22\f\22\16\22\u018a\13\22\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u01a8"+
		"\n\23\3\23\3\23\3\23\3\23\3\23\7\23\u01af\n\23\f\23\16\23\u01b2\13\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u01ce"+
		"\n\24\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u01d6\n\25\3\26\3\26\3\26\3\27"+
		"\5\27\u01dc\n\27\3\27\3\27\3\27\3\27\3\27\5\27\u01e3\n\27\3\30\3\30\5"+
		"\30\u01e7\n\30\3\30\3\30\3\30\5\30\u01ec\n\30\3\30\3\30\3\30\3\30\3\30"+
		"\5\30\u01f3\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\5\30\u0200\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\5\30\u020e\n\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0216\n\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\5\30\u021e\n\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\5\30\u0236\n\30\3\31\3\31\3\31\3\31\3\31\6\31\u023d\n"+
		"\31\r\31\16\31\u023e\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\5\32\u0259\n\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\7\34\u0272"+
		"\n\34\f\34\16\34\u0275\13\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3"+
		"\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3"+
		"\35\3\35\3\35\5\35\u0290\n\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\5\36\u029f\n\36\3\37\3\37\3\37\2\t\32\34\36"+
		" \"$\66 \2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668"+
		":<\2\t\7\2\b\b\20\20\37\37##%&\5\2\f\f\23\23\"\"\6\2\13\13\r\r\27\30\36"+
		"\36\b\2\5\5\n\n\17\17\31\31\33\33))\5\2\32\32!!,,\5\2\21\21\34\34..\3"+
		"\2;<\u02d5\2>\3\2\2\2\4F\3\2\2\2\6`\3\2\2\2\bt\3\2\2\2\n\177\3\2\2\2\f"+
		"\u0092\3\2\2\2\16\u009d\3\2\2\2\20\u00bc\3\2\2\2\22\u00be\3\2\2\2\24\u00c1"+
		"\3\2\2\2\26\u00c4\3\2\2\2\30\u00c7\3\2\2\2\32\u00cd\3\2\2\2\34\u00ea\3"+
		"\2\2\2\36\u011d\3\2\2\2 \u0151\3\2\2\2\"\u017f\3\2\2\2$\u01a7\3\2\2\2"+
		"&\u01cd\3\2\2\2(\u01d5\3\2\2\2*\u01d7\3\2\2\2,\u01e2\3\2\2\2.\u0235\3"+
		"\2\2\2\60\u0237\3\2\2\2\62\u0258\3\2\2\2\64\u025a\3\2\2\2\66\u0266\3\2"+
		"\2\28\u028f\3\2\2\2:\u029e\3\2\2\2<\u02a0\3\2\2\2>?\7\60\2\2?@\b\2\1\2"+
		"@A\7\7\2\2AB\5\4\3\2BC\5\6\4\2CD\7\t\2\2DE\5<\37\2E\3\3\2\2\2FG\7\61\2"+
		"\2GH\7 \2\2HI\5.\30\2IJ\7\26\2\2JK\5.\30\2KL\7$\2\2LM\b\3\1\2MN\7 \2\2"+
		"NO\5\32\16\2OP\7$\2\2PT\7\7\2\2QS\5\16\b\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2"+
		"\2TU\3\2\2\2UX\3\2\2\2VT\3\2\2\2WY\5\b\5\2XW\3\2\2\2XY\3\2\2\2YZ\3\2\2"+
		"\2Z\\\5\n\6\2[]\5\f\7\2\\[\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^_\7\t\2\2_\5\3"+
		"\2\2\2`a\7\67\2\2ab\7 \2\2bc\5:\36\2cd\7$\2\2de\b\4\1\2ei\7\7\2\2fh\5"+
		"\60\31\2gf\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2jo\3\2\2\2ki\3\2\2\2l"+
		"n\5\64\33\2ml\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2pr\3\2\2\2qo\3\2\2"+
		"\2rs\7\t\2\2s\7\3\2\2\2tu\7\64\2\2uv\b\5\1\2vz\7\7\2\2wy\5\22\n\2xw\3"+
		"\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{}\3\2\2\2|z\3\2\2\2}~\7\t\2\2~\t"+
		"\3\2\2\2\177\u0080\7\65\2\2\u0080\u0081\7 \2\2\u0081\u0082\7<\2\2\u0082"+
		"\u0083\7\26\2\2\u0083\u0084\7<\2\2\u0084\u0085\7$\2\2\u0085\u0086\7\24"+
		"\2\2\u0086\u0087\5\34\17\2\u0087\u0088\7+\2\2\u0088\u0089\b\6\1\2\u0089"+
		"\u008d\7\7\2\2\u008a\u008c\5\24\13\2\u008b\u008a\3\2\2\2\u008c\u008f\3"+
		"\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u0090\3\2\2\2\u008f"+
		"\u008d\3\2\2\2\u0090\u0091\7\t\2\2\u0091\13\3\2\2\2\u0092\u0093\7\66\2"+
		"\2\u0093\u0094\b\7\1\2\u0094\u0098\7\7\2\2\u0095\u0097\5\26\f\2\u0096"+
		"\u0095\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2"+
		"\2\2\u0099\u009b\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c\7\t\2\2\u009c"+
		"\r\3\2\2\2\u009d\u009e\7\62\2\2\u009e\u009f\7?\2\2\u009f\u00a0\7 \2\2"+
		"\u00a0\u00a1\5.\30\2\u00a1\u00a2\7$\2\2\u00a2\u00a3\b\b\1\2\u00a3\u00a7"+
		"\7\7\2\2\u00a4\u00a6\5\20\t\2\u00a5\u00a4\3\2\2\2\u00a6\u00a9\3\2\2\2"+
		"\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a7"+
		"\3\2\2\2\u00aa\u00ab\7\t\2\2\u00ab\17\3\2\2\2\u00ac\u00ad\7=\2\2\u00ad"+
		"\u00ae\7\24\2\2\u00ae\u00af\5.\30\2\u00af\u00b0\7+\2\2\u00b0\u00b1\7\6"+
		"\2\2\u00b1\u00b2\b\t\1\2\u00b2\u00bd\3\2\2\2\u00b3\u00b4\7>\2\2\u00b4"+
		"\u00b5\7\24\2\2\u00b5\u00b6\5.\30\2\u00b6\u00b7\7\26\2\2\u00b7\u00b8\5"+
		".\30\2\u00b8\u00b9\7+\2\2\u00b9\u00ba\7\6\2\2\u00ba\u00bb\b\t\1\2\u00bb"+
		"\u00bd\3\2\2\2\u00bc\u00ac\3\2\2\2\u00bc\u00b3\3\2\2\2\u00bd\21\3\2\2"+
		"\2\u00be\u00bf\5\30\r\2\u00bf\u00c0\b\n\1\2\u00c0\23\3\2\2\2\u00c1\u00c2"+
		"\5\30\r\2\u00c2\u00c3\b\13\1\2\u00c3\25\3\2\2\2\u00c4\u00c5\5\30\r\2\u00c5"+
		"\u00c6\b\f\1\2\u00c6\27\3\2\2\2\u00c7\u00c8\7?\2\2\u00c8\u00c9\7\b\2\2"+
		"\u00c9\u00ca\5\36\20\2\u00ca\u00cb\7\6\2\2\u00cb\u00cc\b\r\1\2\u00cc\31"+
		"\3\2\2\2\u00cd\u00ce\b\16\1\2\u00ce\u00cf\7?\2\2\u00cf\u00d0\b\16\1\2"+
		"\u00d0\u00d7\3\2\2\2\u00d1\u00d2\f\3\2\2\u00d2\u00d3\7\26\2\2\u00d3\u00d4"+
		"\7?\2\2\u00d4\u00d6\b\16\1\2\u00d5\u00d1\3\2\2\2\u00d6\u00d9\3\2\2\2\u00d7"+
		"\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\33\3\2\2\2\u00d9\u00d7\3\2\2"+
		"\2\u00da\u00db\b\17\1\2\u00db\u00dc\5\36\20\2\u00dc\u00dd\t\2\2\2\u00dd"+
		"\u00de\5\36\20\2\u00de\u00df\b\17\1\2\u00df\u00eb\3\2\2\2\u00e0\u00e1"+
		"\7?\2\2\u00e1\u00e2\7\16\2\2\u00e2\u00e3\5\36\20\2\u00e3\u00e4\b\17\1"+
		"\2\u00e4\u00eb\3\2\2\2\u00e5\u00e6\7?\2\2\u00e6\u00e7\7\22\2\2\u00e7\u00e8"+
		"\5\36\20\2\u00e8\u00e9\b\17\1\2\u00e9\u00eb\3\2\2\2\u00ea\u00da\3\2\2"+
		"\2\u00ea\u00e0\3\2\2\2\u00ea\u00e5\3\2\2\2\u00eb\u00f3\3\2\2\2\u00ec\u00ed"+
		"\f\3\2\2\u00ed\u00ee\t\3\2\2\u00ee\u00ef\5\34\17\4\u00ef\u00f0\b\17\1"+
		"\2\u00f0\u00f2\3\2\2\2\u00f1\u00ec\3\2\2\2\u00f2\u00f5\3\2\2\2\u00f3\u00f1"+
		"\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\35\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f6"+
		"\u00f7\b\20\1\2\u00f7\u00f8\7/\2\2\u00f8\u00f9\5\36\20\7\u00f9\u00fa\b"+
		"\20\1\2\u00fa\u011e\3\2\2\2\u00fb\u00fc\7-\2\2\u00fc\u00fd\5\36\20\6\u00fd"+
		"\u00fe\b\20\1\2\u00fe\u011e\3\2\2\2\u00ff\u0100\5(\25\2\u0100\u0101\b"+
		"\20\1\2\u0101\u011e\3\2\2\2\u0102\u0103\5*\26\2\u0103\u0104\b\20\1\2\u0104"+
		"\u011e\3\2\2\2\u0105\u0106\5.\30\2\u0106\u0107\b\20\1\2\u0107\u011e\3"+
		"\2\2\2\u0108\u0109\5&\24\2\u0109\u010a\b\20\1\2\u010a\u011e\3\2\2\2\u010b"+
		"\u010c\7\24\2\2\u010c\u010d\5\36\20\2\u010d\u010e\7+\2\2\u010e\u010f\b"+
		"\20\1\2\u010f\u011e\3\2\2\2\u0110\u0111\7\"\2\2\u0111\u0112\5\36\20\2"+
		"\u0112\u0113\7\"\2\2\u0113\u0114\b\20\1\2\u0114\u011e\3\2\2\2\u0115\u0116"+
		"\7#\2\2\u0116\u0117\5\36\20\2\u0117\u0118\7%\2\2\u0118\u0119\b\20\1\2"+
		"\u0119\u011e\3\2\2\2\u011a\u011b\5 \21\2\u011b\u011c\b\20\1\2\u011c\u011e"+
		"\3\2\2\2\u011d\u00f6\3\2\2\2\u011d\u00fb\3\2\2\2\u011d\u00ff\3\2\2\2\u011d"+
		"\u0102\3\2\2\2\u011d\u0105\3\2\2\2\u011d\u0108\3\2\2\2\u011d\u010b\3\2"+
		"\2\2\u011d\u0110\3\2\2\2\u011d\u0115\3\2\2\2\u011d\u011a\3\2\2\2\u011e"+
		"\u012b\3\2\2\2\u011f\u0120\f\5\2\2\u0120\u0121\7-\2\2\u0121\u0122\5\36"+
		"\20\6\u0122\u0123\b\20\1\2\u0123\u012a\3\2\2\2\u0124\u0125\f\4\2\2\u0125"+
		"\u0126\7/\2\2\u0126\u0127\5\36\20\5\u0127\u0128\b\20\1\2\u0128\u012a\3"+
		"\2\2\2\u0129\u011f\3\2\2\2\u0129\u0124\3\2\2\2\u012a\u012d\3\2\2\2\u012b"+
		"\u0129\3\2\2\2\u012b\u012c\3\2\2\2\u012c\37\3\2\2\2\u012d\u012b\3\2\2"+
		"\2\u012e\u012f\b\21\1\2\u012f\u0130\7*\2\2\u0130\u0131\5 \21\5\u0131\u0132"+
		"\b\21\1\2\u0132\u0152\3\2\2\2\u0133\u0134\5(\25\2\u0134\u0135\b\21\1\2"+
		"\u0135\u0152\3\2\2\2\u0136\u0137\5*\26\2\u0137\u0138\b\21\1\2\u0138\u0152"+
		"\3\2\2\2\u0139\u013a\5,\27\2\u013a\u013b\b\21\1\2\u013b\u0152\3\2\2\2"+
		"\u013c\u013d\5&\24\2\u013d\u013e\b\21\1\2\u013e\u0152\3\2\2\2\u013f\u0140"+
		"\7\24\2\2\u0140\u0141\5\36\20\2\u0141\u0142\7+\2\2\u0142\u0143\b\21\1"+
		"\2\u0143\u0152\3\2\2\2\u0144\u0145\7\"\2\2\u0145\u0146\5\36\20\2\u0146"+
		"\u0147\7\"\2\2\u0147\u0148\b\21\1\2\u0148\u0152\3\2\2\2\u0149\u014a\7"+
		"#\2\2\u014a\u014b\5\36\20\2\u014b\u014c\7%\2\2\u014c\u014d\b\21\1\2\u014d"+
		"\u0152\3\2\2\2\u014e\u014f\5\"\22\2\u014f\u0150\b\21\1\2\u0150\u0152\3"+
		"\2\2\2\u0151\u012e\3\2\2\2\u0151\u0133\3\2\2\2\u0151\u0136\3\2\2\2\u0151"+
		"\u0139\3\2\2\2\u0151\u013c\3\2\2\2\u0151\u013f\3\2\2\2\u0151\u0144\3\2"+
		"\2\2\u0151\u0149\3\2\2\2\u0151\u014e\3\2\2\2\u0152\u015d\3\2\2\2\u0153"+
		"\u0154\f\6\2\2\u0154\u0155\7\25\2\2\u0155\u0156\5 \21\7\u0156\u0157\b"+
		"\21\1\2\u0157\u015c\3\2\2\2\u0158\u0159\f\4\2\2\u0159\u015a\7*\2\2\u015a"+
		"\u015c\b\21\1\2\u015b\u0153\3\2\2\2\u015b\u0158\3\2\2\2\u015c\u015f\3"+
		"\2\2\2\u015d\u015b\3\2\2\2\u015d\u015e\3\2\2\2\u015e!\3\2\2\2\u015f\u015d"+
		"\3\2\2\2\u0160\u0161\b\22\1\2\u0161\u0162\5(\25\2\u0162\u0163\b\22\1\2"+
		"\u0163\u0180\3\2\2\2\u0164\u0165\5*\26\2\u0165\u0166\b\22\1\2\u0166\u0180"+
		"\3\2\2\2\u0167\u0168\5,\27\2\u0168\u0169\b\22\1\2\u0169\u0180\3\2\2\2"+
		"\u016a\u016b\5&\24\2\u016b\u016c\b\22\1\2\u016c\u0180\3\2\2\2\u016d\u016e"+
		"\7\24\2\2\u016e\u016f\5\36\20\2\u016f\u0170\7+\2\2\u0170\u0171\b\22\1"+
		"\2\u0171\u0180\3\2\2\2\u0172\u0173\7\"\2\2\u0173\u0174\5\36\20\2\u0174"+
		"\u0175\7\"\2\2\u0175\u0176\b\22\1\2\u0176\u0180\3\2\2\2\u0177\u0178\7"+
		"#\2\2\u0178\u0179\5\36\20\2\u0179\u017a\7%\2\2\u017a\u017b\b\22\1\2\u017b"+
		"\u0180\3\2\2\2\u017c\u017d\5$\23\2\u017d\u017e\b\22\1\2\u017e\u0180\3"+
		"\2\2\2\u017f\u0160\3\2\2\2\u017f\u0164\3\2\2\2\u017f\u0167\3\2\2\2\u017f"+
		"\u016a\3\2\2\2\u017f\u016d\3\2\2\2\u017f\u0172\3\2\2\2\u017f\u0177\3\2"+
		"\2\2\u017f\u017c\3\2\2\2\u0180\u0188\3\2\2\2\u0181\u0182\f\4\2\2\u0182"+
		"\u0183\7\3\2\2\u0183\u0184\5\"\22\5\u0184\u0185\b\22\1\2\u0185\u0187\3"+
		"\2\2\2\u0186\u0181\3\2\2\2\u0187\u018a\3\2\2\2\u0188\u0186\3\2\2\2\u0188"+
		"\u0189\3\2\2\2\u0189#\3\2\2\2\u018a\u0188\3\2\2\2\u018b\u018c\b\23\1\2"+
		"\u018c\u018d\5(\25\2\u018d\u018e\b\23\1\2\u018e\u01a8\3\2\2\2\u018f\u0190"+
		"\5*\26\2\u0190\u0191\b\23\1\2\u0191\u01a8\3\2\2\2\u0192\u0193\5,\27\2"+
		"\u0193\u0194\b\23\1\2\u0194\u01a8\3\2\2\2\u0195\u0196\5&\24\2\u0196\u0197"+
		"\b\23\1\2\u0197\u01a8\3\2\2\2\u0198\u0199\7\24\2\2\u0199\u019a\5\36\20"+
		"\2\u019a\u019b\7+\2\2\u019b\u019c\b\23\1\2\u019c\u01a8\3\2\2\2\u019d\u019e"+
		"\7\"\2\2\u019e\u019f\5\36\20\2\u019f\u01a0\7\"\2\2\u01a0\u01a1\b\23\1"+
		"\2\u01a1\u01a8\3\2\2\2\u01a2\u01a3\7#\2\2\u01a3\u01a4\5\36\20\2\u01a4"+
		"\u01a5\7%\2\2\u01a5\u01a6\b\23\1\2\u01a6\u01a8\3\2\2\2\u01a7\u018b\3\2"+
		"\2\2\u01a7\u018f\3\2\2\2\u01a7\u0192\3\2\2\2\u01a7\u0195\3\2\2\2\u01a7"+
		"\u0198\3\2\2\2\u01a7\u019d\3\2\2\2\u01a7\u01a2\3\2\2\2\u01a8\u01b0\3\2"+
		"\2\2\u01a9\u01aa\f\3\2\2\u01aa\u01ab\7\f\2\2\u01ab\u01ac\5$\23\4\u01ac"+
		"\u01ad\b\23\1\2\u01ad\u01af\3\2\2\2\u01ae\u01a9\3\2\2\2\u01af\u01b2\3"+
		"\2\2\2\u01b0\u01ae\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1%\3\2\2\2\u01b2\u01b0"+
		"\3\2\2\2\u01b3\u01b4\t\4\2\2\u01b4\u01b5\7\24\2\2\u01b5\u01b6\5\36\20"+
		"\2\u01b6\u01b7\7+\2\2\u01b7\u01b8\b\24\1\2\u01b8\u01ce\3\2\2\2\u01b9\u01ba"+
		"\t\5\2\2\u01ba\u01bb\7\24\2\2\u01bb\u01bc\5\36\20\2\u01bc\u01bd\7+\2\2"+
		"\u01bd\u01be\b\24\1\2\u01be\u01ce\3\2\2\2\u01bf\u01c0\t\6\2\2\u01c0\u01c1"+
		"\7\24\2\2\u01c1\u01c2\5\36\20\2\u01c2\u01c3\7+\2\2\u01c3\u01c4\b\24\1"+
		"\2\u01c4\u01ce\3\2\2\2\u01c5\u01c6\t\7\2\2\u01c6\u01c7\7\24\2\2\u01c7"+
		"\u01c8\5\36\20\2\u01c8\u01c9\7\26\2\2\u01c9\u01ca\5\36\20\2\u01ca\u01cb"+
		"\7+\2\2\u01cb\u01cc\b\24\1\2\u01cc\u01ce\3\2\2\2\u01cd\u01b3\3\2\2\2\u01cd"+
		"\u01b9\3\2\2\2\u01cd\u01bf\3\2\2\2\u01cd\u01c5\3\2\2\2\u01ce\'\3\2\2\2"+
		"\u01cf\u01d0\7(\2\2\u01d0\u01d6\b\25\1\2\u01d1\u01d2\7\35\2\2\u01d2\u01d6"+
		"\b\25\1\2\u01d3\u01d4\7\4\2\2\u01d4\u01d6\b\25\1\2\u01d5\u01cf\3\2\2\2"+
		"\u01d5\u01d1\3\2\2\2\u01d5\u01d3\3\2\2\2\u01d6)\3\2\2\2\u01d7\u01d8\7"+
		"?\2\2\u01d8\u01d9\b\26\1\2\u01d9+\3\2\2\2\u01da\u01dc\7-\2\2\u01db\u01da"+
		"\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01de\t\b\2\2\u01de"+
		"\u01e3\b\27\1\2\u01df\u01e0\7/\2\2\u01e0\u01e1\t\b\2\2\u01e1\u01e3\b\27"+
		"\1\2\u01e2\u01db\3\2\2\2\u01e2\u01df\3\2\2\2\u01e3-\3\2\2\2\u01e4\u01e6"+
		"\7#\2\2\u01e5\u01e7\7-\2\2\u01e6\u01e5\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7"+
		"\u01e8\3\2\2\2\u01e8\u01e9\t\b\2\2\u01e9\u01eb\7\26\2\2\u01ea\u01ec\7"+
		"-\2\2\u01eb\u01ea\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec\u01ed\3\2\2\2\u01ed"+
		"\u01ee\t\b\2\2\u01ee\u01ef\7%\2\2\u01ef\u0236\b\30\1\2\u01f0\u01f2\7#"+
		"\2\2\u01f1\u01f3\7-\2\2\u01f2\u01f1\3\2\2\2\u01f2\u01f3\3\2\2\2\u01f3"+
		"\u01f4\3\2\2\2\u01f4\u01f5\t\b\2\2\u01f5\u01f6\7\26\2\2\u01f6\u01f7\7"+
		"/\2\2\u01f7\u01f8\t\b\2\2\u01f8\u01f9\7%\2\2\u01f9\u0236\b\30\1\2\u01fa"+
		"\u01fb\7#\2\2\u01fb\u01fc\7/\2\2\u01fc\u01fd\t\b\2\2\u01fd\u01ff\7\26"+
		"\2\2\u01fe\u0200\7-\2\2\u01ff\u01fe\3\2\2\2\u01ff\u0200\3\2\2\2\u0200"+
		"\u0201\3\2\2\2\u0201\u0202\t\b\2\2\u0202\u0203\7%\2\2\u0203\u0236\b\30"+
		"\1\2\u0204\u0205\7#\2\2\u0205\u0206\7/\2\2\u0206\u0207\t\b\2\2\u0207\u0208"+
		"\7\26\2\2\u0208\u0209\7/\2\2\u0209\u020a\t\b\2\2\u020a\u020b\7%\2\2\u020b"+
		"\u0236\b\30\1\2\u020c\u020e\7-\2\2\u020d\u020c\3\2\2\2\u020d\u020e\3\2"+
		"\2\2\u020e\u020f\3\2\2\2\u020f\u0210\t\b\2\2\u0210\u0211\7-\2\2\u0211"+
		"\u0212\t\b\2\2\u0212\u0213\7*\2\2\u0213\u0236\b\30\1\2\u0214\u0216\7-"+
		"\2\2\u0215\u0214\3\2\2\2\u0215\u0216\3\2\2\2\u0216\u0217\3\2\2\2\u0217"+
		"\u0218\t\b\2\2\u0218\u0219\7/\2\2\u0219\u021a\t\b\2\2\u021a\u021b\7*\2"+
		"\2\u021b\u0236\b\30\1\2\u021c\u021e\7-\2\2\u021d\u021c\3\2\2\2\u021d\u021e"+
		"\3\2\2\2\u021e\u021f\3\2\2\2\u021f\u0220\t\b\2\2\u0220\u0221\7*\2\2\u0221"+
		"\u0236\b\30\1\2\u0222\u0223\7/\2\2\u0223\u0224\t\b\2\2\u0224\u0225\7-"+
		"\2\2\u0225\u0226\t\b\2\2\u0226\u0227\7*\2\2\u0227\u0236\b\30\1\2\u0228"+
		"\u0229\7/\2\2\u0229\u022a\t\b\2\2\u022a\u022b\7/\2\2\u022b\u022c\t\b\2"+
		"\2\u022c\u022d\7*\2\2\u022d\u0236\b\30\1\2\u022e\u022f\7/\2\2\u022f\u0230"+
		"\t\b\2\2\u0230\u0231\7*\2\2\u0231\u0236\b\30\1\2\u0232\u0233\5,\27\2\u0233"+
		"\u0234\b\30\1\2\u0234\u0236\3\2\2\2\u0235\u01e4\3\2\2\2\u0235\u01f0\3"+
		"\2\2\2\u0235\u01fa\3\2\2\2\u0235\u0204\3\2\2\2\u0235\u020d\3\2\2\2\u0235"+
		"\u0215\3\2\2\2\u0235\u021d\3\2\2\2\u0235\u0222\3\2\2\2\u0235\u0228\3\2"+
		"\2\2\u0235\u022e\3\2\2\2\u0235\u0232\3\2\2\2\u0236/\3\2\2\2\u0237\u0238"+
		"\78\2\2\u0238\u0239\7?\2\2\u0239\u023a\b\31\1\2\u023a\u023c\7\7\2\2\u023b"+
		"\u023d\5\62\32\2\u023c\u023b\3\2\2\2\u023d\u023e\3\2\2\2\u023e\u023c\3"+
		"\2\2\2\u023e\u023f\3\2\2\2\u023f\u0240\3\2\2\2\u0240\u0241\7\t\2\2\u0241"+
		"\61\3\2\2\2\u0242\u0243\7 \2\2\u0243\u0244\5:\36\2\u0244\u0245\7%\2\2"+
		"\u0245\u0246\5:\36\2\u0246\u0247\7\26\2\2\u0247\u0248\7<\2\2\u0248\u0249"+
		"\7\26\2\2\u0249\u024a\5\36\20\2\u024a\u024b\7$\2\2\u024b\u024c\7\6\2\2"+
		"\u024c\u024d\b\32\1\2\u024d\u0259\3\2\2\2\u024e\u024f\7 \2\2\u024f\u0250"+
		"\5:\36\2\u0250\u0251\7%\2\2\u0251\u0252\5:\36\2\u0252\u0253\7\26\2\2\u0253"+
		"\u0254\7<\2\2\u0254\u0255\7$\2\2\u0255\u0256\7\6\2\2\u0256\u0257\b\32"+
		"\1\2\u0257\u0259\3\2\2\2\u0258\u0242\3\2\2\2\u0258\u024e\3\2\2\2\u0259"+
		"\63\3\2\2\2\u025a\u025b\79\2\2\u025b\u025c\7\24\2\2\u025c\u025d\5\66\34"+
		"\2\u025d\u025e\7+\2\2\u025e\u025f\7 \2\2\u025f\u0260\t\b\2\2\u0260\u0261"+
		"\7$\2\2\u0261\u0262\7\7\2\2\u0262\u0263\58\35\2\u0263\u0264\7\t\2\2\u0264"+
		"\u0265\b\33\1\2\u0265\65\3\2\2\2\u0266\u0267\b\34\1\2\u0267\u0268\5\36"+
		"\20\2\u0268\u0269\t\2\2\2\u0269\u026a\5\36\20\2\u026a\u026b\b\34\1\2\u026b"+
		"\u0273\3\2\2\2\u026c\u026d\f\3\2\2\u026d\u026e\t\3\2\2\u026e\u026f\5\66"+
		"\34\4\u026f\u0270\b\34\1\2\u0270\u0272\3\2\2\2\u0271\u026c\3\2\2\2\u0272"+
		"\u0275\3\2\2\2\u0273\u0271\3\2\2\2\u0273\u0274\3\2\2\2\u0274\67\3\2\2"+
		"\2\u0275\u0273\3\2\2\2\u0276\u0277\5\36\20\2\u0277\u0278\b\35\1\2\u0278"+
		"\u0290\3\2\2\2\u0279\u027a\5\36\20\2\u027a\u027b\7\26\2\2\u027b\u027c"+
		"\5\36\20\2\u027c\u027d\7\26\2\2\u027d\u027e\5\36\20\2\u027e\u027f\b\35"+
		"\1\2\u027f\u0290\3\2\2\2\u0280\u0281\5\36\20\2\u0281\u0282\7\26\2\2\u0282"+
		"\u0283\5\36\20\2\u0283\u0284\7\26\2\2\u0284\u0285\5\36\20\2\u0285\u0286"+
		"\7\26\2\2\u0286\u0287\5\36\20\2\u0287\u0288\b\35\1\2\u0288\u0290\3\2\2"+
		"\2\u0289\u028a\7?\2\2\u028a\u028b\7 \2\2\u028b\u028c\5\36\20\2\u028c\u028d"+
		"\7$\2\2\u028d\u028e\b\35\1\2\u028e\u0290\3\2\2\2\u028f\u0276\3\2\2\2\u028f"+
		"\u0279\3\2\2\2\u028f\u0280\3\2\2\2\u028f\u0289\3\2\2\2\u02909\3\2\2\2"+
		"\u0291\u0292\7\24\2\2\u0292\u0293\t\b\2\2\u0293\u0294\7\26\2\2\u0294\u0295"+
		"\t\b\2\2\u0295\u0296\7\26\2\2\u0296\u0297\t\b\2\2\u0297\u0298\7\26\2\2"+
		"\u0298\u0299\t\b\2\2\u0299\u029a\7+\2\2\u029a\u029f\b\36\1\2\u029b\u029c"+
		"\7\'\2\2\u029c\u029d\7:\2\2\u029d\u029f\b\36\1\2\u029e\u0291\3\2\2\2\u029e"+
		"\u029b\3\2\2\2\u029f;\3\2\2\2\u02a0\u02a1\7\2\2\3\u02a1=\3\2\2\2*TX\\"+
		"ioz\u008d\u0098\u00a7\u00bc\u00d7\u00ea\u00f3\u011d\u0129\u012b\u0151"+
		"\u015b\u015d\u017f\u0188\u01a7\u01b0\u01cd\u01d5\u01db\u01e2\u01e6\u01eb"+
		"\u01f2\u01ff\u020d\u0215\u021d\u0235\u023e\u0258\u0273\u028f\u029e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}