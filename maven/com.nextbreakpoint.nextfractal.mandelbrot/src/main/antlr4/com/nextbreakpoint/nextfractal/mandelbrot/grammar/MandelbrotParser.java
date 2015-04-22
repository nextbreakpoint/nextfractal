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
		T__47=1, T__46=2, T__45=3, T__44=4, T__43=5, T__42=6, T__41=7, T__40=8, 
		T__39=9, T__38=10, T__37=11, T__36=12, T__35=13, T__34=14, T__33=15, T__32=16, 
		T__31=17, T__30=18, T__29=19, T__28=20, T__27=21, T__26=22, T__25=23, 
		T__24=24, T__23=25, T__22=26, T__21=27, T__20=28, T__19=29, T__18=30, 
		T__17=31, T__16=32, T__15=33, T__14=34, T__13=35, T__12=36, T__11=37, 
		T__10=38, T__9=39, T__8=40, T__7=41, T__6=42, T__5=43, T__4=44, T__3=45, 
		T__2=46, T__1=47, T__0=48, FRACTAL=49, ORBIT=50, TRAP=51, BEGIN=52, LOOP=53, 
		END=54, IF=55, COLOR=56, PALETTE=57, RULE=58, ARGB=59, RATIONAL=60, INTEGER=61, 
		PATHOP_1POINTS=62, PATHOP_2POINTS=63, VARIABLE=64, COMMENT=65, WHITESPACE=66;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'2pi'", "'cos'", "';'", "'{'", "'max'", "'='", "'}'", 
		"'asin'", "'mod2'", "'^'", "'im'", "'?'", "'sin'", "'<='", "'pow'", "'~?'", 
		"'&'", "'('", "'min'", "'*'", "','", "'pha'", "'re'", "'atan'", "'sqrt'", 
		"'tan'", "'atan2'", "'pi'", "'mod'", "'>='", "'['", "'log'", "'|'", "'<'", 
		"']'", "'>'", "'abs'", "'<>'", "'#'", "'e'", "'acos'", "'i'", "')'", "'exp'", 
		"'+'", "'hypot'", "'-'", "'fractal'", "'orbit'", "'trap'", "'begin'", 
		"'loop'", "'end'", "'if'", "'color'", "'palette'", "'rule'", "ARGB", "RATIONAL", 
		"INTEGER", "PATHOP_1POINTS", "PATHOP_2POINTS", "VARIABLE", "COMMENT", 
		"WHITESPACE"
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
			setState(65); match(8);
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
			setState(69); match(32);
			setState(70); ((OrbitContext)_localctx).ra = complex();
			setState(71); match(22);
			setState(72); ((OrbitContext)_localctx).rb = complex();
			setState(73); match(36);

					builder.setOrbit(new ASTOrbit(((OrbitContext)_localctx).o, new ASTRegion(((OrbitContext)_localctx).ra.result, ((OrbitContext)_localctx).rb.result)));
				
			setState(75); match(32);
			setState(76); ((OrbitContext)_localctx).v = variablelist(0);
			setState(77); match(36);
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

			setState(92); match(8);
			}
		}
		catch (RecognitionException re) {
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
			setState(95); match(32);
			setState(96); ((ColorContext)_localctx).argb = colorargb();
			setState(97); match(36);
			 
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
			setState(112); match(8);
			}
		}
		catch (RecognitionException re) {
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
			while (_la==IF || _la==VARIABLE) {
				{
				{
				setState(117); beginstatements();
				}
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(123); match(8);
			}
		}
		catch (RecognitionException re) {
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
			setState(126); match(32);
			setState(127); ((LoopContext)_localctx).lb = match(INTEGER);
			setState(128); match(22);
			setState(129); ((LoopContext)_localctx).le = match(INTEGER);
			setState(130); match(36);
			setState(131); match(19);
			setState(132); ((LoopContext)_localctx).e = conditionexp(0);
			setState(133); match(44);

					builder.setOrbitLoop(new ASTOrbitLoop(((LoopContext)_localctx).l, builder.parseInt((((LoopContext)_localctx).lb!=null?((LoopContext)_localctx).lb.getText():null)), builder.parseInt((((LoopContext)_localctx).le!=null?((LoopContext)_localctx).le.getText():null)), ((LoopContext)_localctx).e.result));
				
			setState(135); match(5);
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IF || _la==VARIABLE) {
				{
				{
				setState(136); loopstatements();
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(142); match(8);
			}
		}
		catch (RecognitionException re) {
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
			while (_la==IF || _la==VARIABLE) {
				{
				{
				setState(147); endstatements();
				}
				}
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(153); match(8);
			}
		}
		catch (RecognitionException re) {
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
			setState(157); match(32);
			setState(158); ((TrapContext)_localctx).c = complex();
			setState(159); match(36);

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
			setState(168); match(8);
			}
		}
		catch (RecognitionException re) {
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
				setState(171); match(19);
				setState(172); ((PathopContext)_localctx).c = complex();
				setState(173); match(44);
				setState(174); match(4);

						builder.addOrbitTrapOp(new ASTOrbitTrapOp(((PathopContext)_localctx).o, (((PathopContext)_localctx).o!=null?((PathopContext)_localctx).o.getText():null), ((PathopContext)_localctx).c.result));
					
				}
				break;
			case PATHOP_2POINTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(177); ((PathopContext)_localctx).o = match(PATHOP_2POINTS);
				setState(178); match(19);
				setState(179); ((PathopContext)_localctx).c1 = complex();
				setState(180); match(22);
				setState(181); ((PathopContext)_localctx).c2 = complex();
				setState(182); match(44);
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
			setState(212);
			switch (_input.LA(1)) {
			case VARIABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(197); ((StatementContext)_localctx).v = match(VARIABLE);
				setState(198); match(7);
				setState(199); ((StatementContext)_localctx).e = expression(0);
				setState(200); match(4);

						((StatementContext)_localctx).result =  new ASTAssignStatement(((StatementContext)_localctx).v, (((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result);
						builder.registerVariable((((StatementContext)_localctx).v!=null?((StatementContext)_localctx).v.getText():null), ((StatementContext)_localctx).e.result.isReal(), ((StatementContext)_localctx).v);
					
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(203); ((StatementContext)_localctx).f = match(IF);
				setState(204); match(19);
				setState(205); ((StatementContext)_localctx).c = conditionexp(0);
				setState(206); match(44);
				setState(207); match(5);
				setState(208); ((StatementContext)_localctx).s = statement();
				setState(209); match(8);

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
			setState(215); ((VariablelistContext)_localctx).v = match(VARIABLE);

					builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(224);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VariablelistContext(_parentctx, _parentState);
					_localctx.vl = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_variablelist);
					setState(218);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(219); match(22);
					setState(220); ((VariablelistContext)_localctx).v = match(VARIABLE);

					          		builder.registerStateVariable((((VariablelistContext)_localctx).v!=null?((VariablelistContext)_localctx).v.getText():null), ((VariablelistContext)_localctx).v);
					          	
					}
					} 
				}
				setState(226);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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
			setState(243);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(228); ((ConditionexpContext)_localctx).e1 = expression(0);
				setState(229);
				((ConditionexpContext)_localctx).o = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 7) | (1L << 15) | (1L << 31) | (1L << 35) | (1L << 37) | (1L << 39))) != 0)) ) {
					((ConditionexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(230); ((ConditionexpContext)_localctx).e2 = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionCompareOp(((ConditionexpContext)_localctx).e1.result.getLocation(), (((ConditionexpContext)_localctx).o!=null?((ConditionexpContext)_localctx).o.getText():null), ((ConditionexpContext)_localctx).e1.result, ((ConditionexpContext)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(233); ((ConditionexpContext)_localctx).v = match(VARIABLE);
				setState(234); match(13);
				setState(235); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, true);
					
				}
				break;

			case 3:
				{
				setState(238); ((ConditionexpContext)_localctx).v = match(VARIABLE);
				setState(239); match(17);
				setState(240); ((ConditionexpContext)_localctx).e = expression(0);

						((ConditionexpContext)_localctx).result =  new ASTConditionTrap(((ConditionexpContext)_localctx).v, (((ConditionexpContext)_localctx).v!=null?((ConditionexpContext)_localctx).v.getText():null), ((ConditionexpContext)_localctx).e.result, false);
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(252);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConditionexpContext(_parentctx, _parentState);
					_localctx.c1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_conditionexp);
					setState(245);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(246);
					((ConditionexpContext)_localctx).l = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 11) | (1L << 18) | (1L << 34))) != 0)) ) {
						((ConditionexpContext)_localctx).l = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(247); ((ConditionexpContext)_localctx).c2 = conditionexp(2);

					          		((ConditionexpContext)_localctx).result =  new ASTConditionLogicOp(((ConditionexpContext)_localctx).c1.result.getLocation(), (((ConditionexpContext)_localctx).l!=null?((ConditionexpContext)_localctx).l.getText():null), ((ConditionexpContext)_localctx).c1.result, ((ConditionexpContext)_localctx).c2.result);
					          	
					}
					} 
				}
				setState(254);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
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
			setState(294);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(256); ((ExpressionContext)_localctx).s = match(48);
				setState(257); ((ExpressionContext)_localctx).e = expression(5);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 2:
				{
				setState(260); ((ExpressionContext)_localctx).s = match(46);
				setState(261); ((ExpressionContext)_localctx).e = expression(4);

						((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 3:
				{
				setState(264); ((ExpressionContext)_localctx).p = constant();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).p.result;
					
				}
				break;

			case 4:
				{
				setState(267); ((ExpressionContext)_localctx).v = variable();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).v.result;
					
				}
				break;

			case 5:
				{
				setState(270); ((ExpressionContext)_localctx).c = complex();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).c.result;
					
				}
				break;

			case 6:
				{
				setState(273); ((ExpressionContext)_localctx).f = function();

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).f.result;
					
				}
				break;

			case 7:
				{
				setState(276); ((ExpressionContext)_localctx).t = match(19);
				setState(277); ((ExpressionContext)_localctx).e = expression(0);
				setState(278); match(44);

						((ExpressionContext)_localctx).result =  new ASTParen(((ExpressionContext)_localctx).t, ((ExpressionContext)_localctx).e.result);
					
				}
				break;

			case 8:
				{
				setState(281); ((ExpressionContext)_localctx).m = match(34);
				setState(282); ((ExpressionContext)_localctx).e = expression(0);
				setState(283); match(34);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).m, "mod", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 9:
				{
				setState(286); ((ExpressionContext)_localctx).a = match(35);
				setState(287); ((ExpressionContext)_localctx).e = expression(0);
				setState(288); match(37);

						((ExpressionContext)_localctx).result =  new ASTFunction(((ExpressionContext)_localctx).a, "pha", ((ExpressionContext)_localctx).e.result);	
					
				}
				break;

			case 10:
				{
				setState(291); ((ExpressionContext)_localctx).e3 = expression2(0);

						((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).e3.result;	
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(308);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(306);
					switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(296);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(297); ((ExpressionContext)_localctx).s = match(46);
						setState(298); ((ExpressionContext)_localctx).e2 = expression(4);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "+", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;

					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(301);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(302); ((ExpressionContext)_localctx).s = match(48);
						setState(303); ((ExpressionContext)_localctx).e2 = expression(3);

						          		((ExpressionContext)_localctx).result =  new ASTOperator(((ExpressionContext)_localctx).s, "-", ((ExpressionContext)_localctx).e1.result, ((ExpressionContext)_localctx).e2.result);		
						          	
						}
						break;
					}
					} 
				}
				setState(310);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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
			setState(346);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(312); ((Expression2Context)_localctx).i = match(43);
				setState(313); ((Expression2Context)_localctx).e2 = expression2(3);

						((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
					
				}
				break;

			case 2:
				{
				setState(316); ((Expression2Context)_localctx).p = constant();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).p.result;
					
				}
				break;

			case 3:
				{
				setState(319); ((Expression2Context)_localctx).v = variable();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).v.result;
					
				}
				break;

			case 4:
				{
				setState(322); ((Expression2Context)_localctx).r = real();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).r.result;
					
				}
				break;

			case 5:
				{
				setState(325); ((Expression2Context)_localctx).f = function();

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).f.result;
					
				}
				break;

			case 6:
				{
				setState(328); ((Expression2Context)_localctx).t = match(19);
				setState(329); ((Expression2Context)_localctx).e = expression(0);
				setState(330); match(44);

						((Expression2Context)_localctx).result =  new ASTParen(((Expression2Context)_localctx).t, ((Expression2Context)_localctx).e.result);
					
				}
				break;

			case 7:
				{
				setState(333); ((Expression2Context)_localctx).m = match(34);
				setState(334); ((Expression2Context)_localctx).e = expression(0);
				setState(335); match(34);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).m, "mod", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(338); ((Expression2Context)_localctx).a = match(35);
				setState(339); ((Expression2Context)_localctx).e = expression(0);
				setState(340); match(37);

						((Expression2Context)_localctx).result =  new ASTFunction(((Expression2Context)_localctx).a, "pha", ((Expression2Context)_localctx).e.result);	
					
				}
				break;

			case 9:
				{
				setState(343); ((Expression2Context)_localctx).e3 = expression3(0);

						((Expression2Context)_localctx).result =  ((Expression2Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(358);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(356);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(348);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(349); ((Expression2Context)_localctx).s = match(21);
						setState(350); ((Expression2Context)_localctx).e2 = expression2(5);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).s, "*", ((Expression2Context)_localctx).e1.result, ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;

					case 2:
						{
						_localctx = new Expression2Context(_parentctx, _parentState);
						_localctx.e2 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression2);
						setState(353);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(354); ((Expression2Context)_localctx).i = match(43);

						          		((Expression2Context)_localctx).result =  new ASTOperator(((Expression2Context)_localctx).i, "*", new ASTNumber(((Expression2Context)_localctx).i, 0.0, 1.0), ((Expression2Context)_localctx).e2.result);
						          	
						}
						break;
					}
					} 
				}
				setState(360);
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
			setState(392);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(362); ((Expression3Context)_localctx).p = constant();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).p.result;
					
				}
				break;

			case 2:
				{
				setState(365); ((Expression3Context)_localctx).v = variable();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).v.result;
					
				}
				break;

			case 3:
				{
				setState(368); ((Expression3Context)_localctx).r = real();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).r.result;
					
				}
				break;

			case 4:
				{
				setState(371); ((Expression3Context)_localctx).f = function();

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).f.result;
					
				}
				break;

			case 5:
				{
				setState(374); ((Expression3Context)_localctx).t = match(19);
				setState(375); ((Expression3Context)_localctx).e = expression(0);
				setState(376); match(44);

						((Expression3Context)_localctx).result =  new ASTParen(((Expression3Context)_localctx).t, ((Expression3Context)_localctx).e.result);
					
				}
				break;

			case 6:
				{
				setState(379); ((Expression3Context)_localctx).m = match(34);
				setState(380); ((Expression3Context)_localctx).e = expression(0);
				setState(381); match(34);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).m, "mod", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 7:
				{
				setState(384); ((Expression3Context)_localctx).a = match(35);
				setState(385); ((Expression3Context)_localctx).e = expression(0);
				setState(386); match(37);

						((Expression3Context)_localctx).result =  new ASTFunction(((Expression3Context)_localctx).a, "pha", ((Expression3Context)_localctx).e.result);	
					
				}
				break;

			case 8:
				{
				setState(389); ((Expression3Context)_localctx).e3 = expression4(0);

						((Expression3Context)_localctx).result =  ((Expression3Context)_localctx).e3.result;
					
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(401);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression3Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression3);
					setState(394);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(395); ((Expression3Context)_localctx).s = match(1);
					setState(396); ((Expression3Context)_localctx).e2 = expression3(3);

					          		((Expression3Context)_localctx).result =  new ASTOperator(((Expression3Context)_localctx).s, "/", ((Expression3Context)_localctx).e1.result, ((Expression3Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(403);
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
			setState(432);
			switch (_input.LA(1)) {
			case 2:
			case 29:
			case 41:
				{
				setState(405); ((Expression4Context)_localctx).p = constant();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).p.result;
					
				}
				break;
			case VARIABLE:
				{
				setState(408); ((Expression4Context)_localctx).v = variable();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).v.result;
					
				}
				break;
			case 46:
			case 48:
			case RATIONAL:
			case INTEGER:
				{
				setState(411); ((Expression4Context)_localctx).r = real();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).r.result;
					
				}
				break;
			case 3:
			case 6:
			case 9:
			case 10:
			case 12:
			case 14:
			case 16:
			case 20:
			case 23:
			case 24:
			case 25:
			case 26:
			case 27:
			case 28:
			case 30:
			case 33:
			case 38:
			case 42:
			case 45:
			case 47:
				{
				setState(414); ((Expression4Context)_localctx).f = function();

						((Expression4Context)_localctx).result =  ((Expression4Context)_localctx).f.result;
					
				}
				break;
			case 19:
				{
				setState(417); ((Expression4Context)_localctx).t = match(19);
				setState(418); ((Expression4Context)_localctx).e = expression(0);
				setState(419); match(44);

						((Expression4Context)_localctx).result =  new ASTParen(((Expression4Context)_localctx).t, ((Expression4Context)_localctx).e.result);
					
				}
				break;
			case 34:
				{
				setState(422); ((Expression4Context)_localctx).m = match(34);
				setState(423); ((Expression4Context)_localctx).e = expression(0);
				setState(424); match(34);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).m, "mod", ((Expression4Context)_localctx).e.result);	
					
				}
				break;
			case 35:
				{
				setState(427); ((Expression4Context)_localctx).a = match(35);
				setState(428); ((Expression4Context)_localctx).e = expression(0);
				setState(429); match(37);

						((Expression4Context)_localctx).result =  new ASTFunction(((Expression4Context)_localctx).a, "pha", ((Expression4Context)_localctx).e.result);	
					
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(441);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expression4Context(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression4);
					setState(434);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(435); ((Expression4Context)_localctx).s = match(11);
					setState(436); ((Expression4Context)_localctx).e2 = expression4(2);

					          		((Expression4Context)_localctx).result =  new ASTOperator(((Expression4Context)_localctx).s, "^", ((Expression4Context)_localctx).e1.result, ((Expression4Context)_localctx).e2.result);
					          	
					}
					} 
				}
				setState(443);
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
			setState(470);
			switch (_input.LA(1)) {
			case 10:
			case 12:
			case 23:
			case 24:
			case 30:
				enterOuterAlt(_localctx, 1);
				{
				setState(444);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 10) | (1L << 12) | (1L << 23) | (1L << 24) | (1L << 30))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(445); match(19);
				setState(446); ((FunctionContext)_localctx).e = expression(0);
				setState(447); match(44);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), ((FunctionContext)_localctx).e.result);		
					
				}
				break;
			case 3:
			case 9:
			case 14:
			case 25:
			case 27:
			case 42:
				enterOuterAlt(_localctx, 2);
				{
				setState(450);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 9) | (1L << 14) | (1L << 25) | (1L << 27) | (1L << 42))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(451); match(19);
				setState(452); ((FunctionContext)_localctx).e = expression(0);
				setState(453); match(44);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 26:
			case 33:
			case 38:
			case 45:
				enterOuterAlt(_localctx, 3);
				{
				setState(456);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 26) | (1L << 33) | (1L << 38) | (1L << 45))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(457); match(19);
				setState(458); ((FunctionContext)_localctx).e = expression(0);
				setState(459); match(44);

						((FunctionContext)_localctx).result =  new ASTFunction(((FunctionContext)_localctx).f, (((FunctionContext)_localctx).f!=null?((FunctionContext)_localctx).f.getText():null), new ASTExpression[] { ((FunctionContext)_localctx).e.result });		
					
				}
				break;
			case 6:
			case 16:
			case 20:
			case 28:
			case 47:
				enterOuterAlt(_localctx, 4);
				{
				setState(462);
				((FunctionContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 16) | (1L << 20) | (1L << 28) | (1L << 47))) != 0)) ) {
					((FunctionContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(463); match(19);
				setState(464); ((FunctionContext)_localctx).e1 = expression(0);
				setState(465); match(22);
				setState(466); ((FunctionContext)_localctx).e2 = expression(0);
				setState(467); match(44);

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
			setState(478);
			switch (_input.LA(1)) {
			case 41:
				enterOuterAlt(_localctx, 1);
				{
				setState(472); ((ConstantContext)_localctx).p = match(41);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.E);
					
				}
				break;
			case 29:
				enterOuterAlt(_localctx, 2);
				{
				setState(474); ((ConstantContext)_localctx).p = match(29);

						((ConstantContext)_localctx).result =  new ASTNumber(((ConstantContext)_localctx).p, Math.PI);
					
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 3);
				{
				setState(476); ((ConstantContext)_localctx).p = match(2);

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
			setState(480); ((VariableContext)_localctx).v = match(VARIABLE);

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
			setState(491);
			switch (_input.LA(1)) {
			case 46:
			case RATIONAL:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(484);
				_la = _input.LA(1);
				if (_la==46) {
					{
					setState(483); match(46);
					}
				}

				setState(486);
				((RealContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((RealContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();

						((RealContext)_localctx).result =  new ASTNumber(((RealContext)_localctx).r, builder.parseDouble((((RealContext)_localctx).r!=null?((RealContext)_localctx).r.getText():null)));
					
				}
				break;
			case 48:
				enterOuterAlt(_localctx, 2);
				{
				setState(488); match(48);
				setState(489);
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
			setState(574);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(493); match(35);
				setState(495);
				_la = _input.LA(1);
				if (_la==46) {
					{
					setState(494); match(46);
					}
				}

				setState(497);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(498); match(22);
				setState(500);
				_la = _input.LA(1);
				if (_la==46) {
					{
					setState(499); match(46);
					}
				}

				setState(502);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(503); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(505); match(35);
				setState(507);
				_la = _input.LA(1);
				if (_la==46) {
					{
					setState(506); match(46);
					}
				}

				setState(509);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(510); match(22);
				setState(511); match(48);
				setState(512);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(513); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(515); match(35);
				setState(516); match(48);
				setState(517);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(518); match(22);
				setState(520);
				_la = _input.LA(1);
				if (_la==46) {
					{
					setState(519); match(46);
					}
				}

				setState(522);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(523); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(525); match(35);
				setState(526); match(48);
				setState(527);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(528); match(22);
				setState(529); match(48);
				setState(530);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(531); match(37);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(534);
				_la = _input.LA(1);
				if (_la==46) {
					{
					setState(533); match(46);
					}
				}

				setState(536);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(537); match(46);
				setState(538);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(539); match(43);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(542);
				_la = _input.LA(1);
				if (_la==46) {
					{
					setState(541); match(46);
					}
				}

				setState(544);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(545); match(48);
				setState(546);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(547); match(43);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble((((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(550);
				_la = _input.LA(1);
				if (_la==46) {
					{
					setState(549); match(46);
					}
				}

				setState(552);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(553); match(43);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble((((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(555); match(48);
				setState(556);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(557); match(46);
				setState(558);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(559); match(43);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("+" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(561); match(48);
				setState(562);
				((ComplexContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(563); match(48);
				setState(564);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(565); match(43);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).r, builder.parseDouble("-" + (((ComplexContext)_localctx).r!=null?((ComplexContext)_localctx).r.getText():null)), builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(567); match(48);
				setState(568);
				((ComplexContext)_localctx).i = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ComplexContext)_localctx).i = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(569); match(43);

						((ComplexContext)_localctx).result =  new ASTNumber(((ComplexContext)_localctx).i, 0.0, builder.parseDouble("-" + (((ComplexContext)_localctx).i!=null?((ComplexContext)_localctx).i.getText():null)));
					
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(571); ((ComplexContext)_localctx).rn = real();

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
			setState(576); ((PaletteContext)_localctx).p = match(PALETTE);
			setState(577); ((PaletteContext)_localctx).v = match(VARIABLE);

					builder.addPalette(new ASTPalette(((PaletteContext)_localctx).p, (((PaletteContext)_localctx).v!=null?((PaletteContext)_localctx).v.getText():null))); 
				
			setState(579); match(5);
			setState(581); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(580); paletteelement();
				}
				}
				setState(583); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==32 );
			setState(585); match(8);
			}
		}
		catch (RecognitionException re) {
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
			setState(609);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(587); ((PaletteelementContext)_localctx).t = match(32);
				setState(588); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(589); match(37);
				setState(590); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(591); match(22);
				setState(592); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(593); match(22);
				setState(594); ((PaletteelementContext)_localctx).e = expression(0);
				setState(595); match(36);
				setState(596); match(4);

						builder.addPaletteElement(new ASTPaletteElement(((PaletteelementContext)_localctx).t, ((PaletteelementContext)_localctx).bc.result, ((PaletteelementContext)_localctx).ec.result, builder.parseInt((((PaletteelementContext)_localctx).s!=null?((PaletteelementContext)_localctx).s.getText():null)), ((PaletteelementContext)_localctx).e.result));
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(599); ((PaletteelementContext)_localctx).t = match(32);
				setState(600); ((PaletteelementContext)_localctx).bc = colorargb();
				setState(601); match(37);
				setState(602); ((PaletteelementContext)_localctx).ec = colorargb();
				setState(603); match(22);
				setState(604); ((PaletteelementContext)_localctx).s = match(INTEGER);
				setState(605); match(36);
				setState(606); match(4);

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
			setState(611); ((ColorruleContext)_localctx).t = match(RULE);
			setState(612); match(19);
			setState(613); ((ColorruleContext)_localctx).r = ruleexp(0);
			setState(614); match(44);
			setState(615); match(32);
			setState(616);
			((ColorruleContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==RATIONAL || _la==INTEGER) ) {
				((ColorruleContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(617); match(36);
			setState(618); match(5);
			setState(619); ((ColorruleContext)_localctx).c = colorexp();
			setState(620); match(8);

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
			setState(624); ((RuleexpContext)_localctx).e1 = expression(0);
			setState(625);
			((RuleexpContext)_localctx).o = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 7) | (1L << 15) | (1L << 31) | (1L << 35) | (1L << 37) | (1L << 39))) != 0)) ) {
				((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
			}
			consume();
			setState(626); ((RuleexpContext)_localctx).e2 = expression(0);

					((RuleexpContext)_localctx).result =  new ASTRuleCompareOpExpression(((RuleexpContext)_localctx).e1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).e1.result, ((RuleexpContext)_localctx).e2.result);
				
			}
			_ctx.stop = _input.LT(-1);
			setState(636);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,38,_ctx);
			while ( _alt!=2 && _alt!=ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RuleexpContext(_parentctx, _parentState);
					_localctx.r1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_ruleexp);
					setState(629);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(630);
					((RuleexpContext)_localctx).o = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 11) | (1L << 18) | (1L << 34))) != 0)) ) {
						((RuleexpContext)_localctx).o = (Token)_errHandler.recoverInline(this);
					}
					consume();
					setState(631); ((RuleexpContext)_localctx).r2 = ruleexp(2);

					          		((RuleexpContext)_localctx).result =  new ASTRuleLogicOpExpression(((RuleexpContext)_localctx).r1.result.getLocation(), (((RuleexpContext)_localctx).o!=null?((RuleexpContext)_localctx).o.getText():null), ((RuleexpContext)_localctx).r1.result, ((RuleexpContext)_localctx).r2.result);
					          	
					}
					} 
				}
				setState(638);
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
			setState(664);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(639); ((ColorexpContext)_localctx).e1 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result);
					
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(642); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(643); match(22);
				setState(644); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(645); match(22);
				setState(646); ((ColorexpContext)_localctx).e3 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result);
					
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(649); ((ColorexpContext)_localctx).e1 = expression(0);
				setState(650); match(22);
				setState(651); ((ColorexpContext)_localctx).e2 = expression(0);
				setState(652); match(22);
				setState(653); ((ColorexpContext)_localctx).e3 = expression(0);
				setState(654); match(22);
				setState(655); ((ColorexpContext)_localctx).e4 = expression(0);

						((ColorexpContext)_localctx).result =  new ASTColorComponent(((ColorexpContext)_localctx).e1.result.getLocation(), ((ColorexpContext)_localctx).e1.result, ((ColorexpContext)_localctx).e2.result, ((ColorexpContext)_localctx).e3.result, ((ColorexpContext)_localctx).e4.result);
					
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(658); ((ColorexpContext)_localctx).v = match(VARIABLE);
				setState(659); match(32);
				setState(660); ((ColorexpContext)_localctx).e = expression(0);
				setState(661); match(36);

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
			setState(679);
			switch (_input.LA(1)) {
			case 19:
				enterOuterAlt(_localctx, 1);
				{
				setState(666); match(19);
				setState(667);
				((ColorargbContext)_localctx).a = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).a = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(668); match(22);
				setState(669);
				((ColorargbContext)_localctx).r = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).r = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(670); match(22);
				setState(671);
				((ColorargbContext)_localctx).g = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).g = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(672); match(22);
				setState(673);
				((ColorargbContext)_localctx).b = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==RATIONAL || _la==INTEGER) ) {
					((ColorargbContext)_localctx).b = (Token)_errHandler.recoverInline(this);
				}
				consume();
				setState(674); match(44);

						((ColorargbContext)_localctx).result =  new ASTColorARGB(builder.parseFloat((((ColorargbContext)_localctx).a!=null?((ColorargbContext)_localctx).a.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).r!=null?((ColorargbContext)_localctx).r.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).g!=null?((ColorargbContext)_localctx).g.getText():null)), builder.parseFloat((((ColorargbContext)_localctx).b!=null?((ColorargbContext)_localctx).b.getText():null)));
					
				}
				break;
			case 40:
				enterOuterAlt(_localctx, 2);
				{
				setState(676); match(40);
				setState(677); ((ColorargbContext)_localctx).argb = match(ARGB);

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
			setState(681); match(EOF);
			}
		}
		catch (RecognitionException re) {
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3D\u02ae\4\2\t\2\4"+
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
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00d7\n\r\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\7\16\u00e1\n\16\f\16\16\16\u00e4\13\16\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5"+
		"\17\u00f6\n\17\3\17\3\17\3\17\3\17\3\17\7\17\u00fd\n\17\f\17\16\17\u0100"+
		"\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0129"+
		"\n\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u0135\n\20"+
		"\f\20\16\20\u0138\13\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u015d"+
		"\n\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\7\21\u0167\n\21\f\21\16"+
		"\21\u016a\13\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u018b\n\22\3\22\3\22\3\22\3\22\3\22"+
		"\7\22\u0192\n\22\f\22\16\22\u0195\13\22\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u01b3\n\23\3\23\3\23\3\23"+
		"\3\23\3\23\7\23\u01ba\n\23\f\23\16\23\u01bd\13\23\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u01d9\n\24\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\5\25\u01e1\n\25\3\26\3\26\3\26\3\27\5\27\u01e7\n\27\3"+
		"\27\3\27\3\27\3\27\3\27\5\27\u01ee\n\27\3\30\3\30\5\30\u01f2\n\30\3\30"+
		"\3\30\3\30\5\30\u01f7\n\30\3\30\3\30\3\30\3\30\3\30\5\30\u01fe\n\30\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u020b\n\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0219"+
		"\n\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0221\n\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\5\30\u0229\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30"+
		"\u0241\n\30\3\31\3\31\3\31\3\31\3\31\6\31\u0248\n\31\r\31\16\31\u0249"+
		"\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0264\n\32\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\7\34\u027d\n\34\f\34\16\34\u0280"+
		"\13\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u029b"+
		"\n\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\5\36\u02aa\n\36\3\37\3\37\3\37\2\t\32\34\36 \"$\66 \2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<\2\t\b\2\t\t\21\21!!%%\'"+
		"\'))\5\2\r\r\24\24$$\6\2\f\f\16\16\31\32  \b\2\5\5\13\13\20\20\33\33\35"+
		"\35,,\6\2\34\34##((//\7\2\b\b\22\22\26\26\36\36\61\61\3\2>?\u02e1\2>\3"+
		"\2\2\2\4F\3\2\2\2\6`\3\2\2\2\bt\3\2\2\2\n\177\3\2\2\2\f\u0092\3\2\2\2"+
		"\16\u009d\3\2\2\2\20\u00bc\3\2\2\2\22\u00be\3\2\2\2\24\u00c1\3\2\2\2\26"+
		"\u00c4\3\2\2\2\30\u00d6\3\2\2\2\32\u00d8\3\2\2\2\34\u00f5\3\2\2\2\36\u0128"+
		"\3\2\2\2 \u015c\3\2\2\2\"\u018a\3\2\2\2$\u01b2\3\2\2\2&\u01d8\3\2\2\2"+
		"(\u01e0\3\2\2\2*\u01e2\3\2\2\2,\u01ed\3\2\2\2.\u0240\3\2\2\2\60\u0242"+
		"\3\2\2\2\62\u0263\3\2\2\2\64\u0265\3\2\2\2\66\u0271\3\2\2\28\u029a\3\2"+
		"\2\2:\u02a9\3\2\2\2<\u02ab\3\2\2\2>?\7\63\2\2?@\b\2\1\2@A\7\7\2\2AB\5"+
		"\4\3\2BC\5\6\4\2CD\7\n\2\2DE\5<\37\2E\3\3\2\2\2FG\7\64\2\2GH\7\"\2\2H"+
		"I\5.\30\2IJ\7\30\2\2JK\5.\30\2KL\7&\2\2LM\b\3\1\2MN\7\"\2\2NO\5\32\16"+
		"\2OP\7&\2\2PT\7\7\2\2QS\5\16\b\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2"+
		"\2UX\3\2\2\2VT\3\2\2\2WY\5\b\5\2XW\3\2\2\2XY\3\2\2\2YZ\3\2\2\2Z\\\5\n"+
		"\6\2[]\5\f\7\2\\[\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^_\7\n\2\2_\5\3\2\2\2`a"+
		"\7:\2\2ab\7\"\2\2bc\5:\36\2cd\7&\2\2de\b\4\1\2ei\7\7\2\2fh\5\60\31\2g"+
		"f\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2jo\3\2\2\2ki\3\2\2\2ln\5\64\33"+
		"\2ml\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2pr\3\2\2\2qo\3\2\2\2rs\7\n\2"+
		"\2s\7\3\2\2\2tu\7\66\2\2uv\b\5\1\2vz\7\7\2\2wy\5\22\n\2xw\3\2\2\2y|\3"+
		"\2\2\2zx\3\2\2\2z{\3\2\2\2{}\3\2\2\2|z\3\2\2\2}~\7\n\2\2~\t\3\2\2\2\177"+
		"\u0080\7\67\2\2\u0080\u0081\7\"\2\2\u0081\u0082\7?\2\2\u0082\u0083\7\30"+
		"\2\2\u0083\u0084\7?\2\2\u0084\u0085\7&\2\2\u0085\u0086\7\25\2\2\u0086"+
		"\u0087\5\34\17\2\u0087\u0088\7.\2\2\u0088\u0089\b\6\1\2\u0089\u008d\7"+
		"\7\2\2\u008a\u008c\5\24\13\2\u008b\u008a\3\2\2\2\u008c\u008f\3\2\2\2\u008d"+
		"\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u0090\3\2\2\2\u008f\u008d\3\2"+
		"\2\2\u0090\u0091\7\n\2\2\u0091\13\3\2\2\2\u0092\u0093\78\2\2\u0093\u0094"+
		"\b\7\1\2\u0094\u0098\7\7\2\2\u0095\u0097\5\26\f\2\u0096\u0095\3\2\2\2"+
		"\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009b"+
		"\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c\7\n\2\2\u009c\r\3\2\2\2\u009d"+
		"\u009e\7\65\2\2\u009e\u009f\7B\2\2\u009f\u00a0\7\"\2\2\u00a0\u00a1\5."+
		"\30\2\u00a1\u00a2\7&\2\2\u00a2\u00a3\b\b\1\2\u00a3\u00a7\7\7\2\2\u00a4"+
		"\u00a6\5\20\t\2\u00a5\u00a4\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3"+
		"\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa"+
		"\u00ab\7\n\2\2\u00ab\17\3\2\2\2\u00ac\u00ad\7@\2\2\u00ad\u00ae\7\25\2"+
		"\2\u00ae\u00af\5.\30\2\u00af\u00b0\7.\2\2\u00b0\u00b1\7\6\2\2\u00b1\u00b2"+
		"\b\t\1\2\u00b2\u00bd\3\2\2\2\u00b3\u00b4\7A\2\2\u00b4\u00b5\7\25\2\2\u00b5"+
		"\u00b6\5.\30\2\u00b6\u00b7\7\30\2\2\u00b7\u00b8\5.\30\2\u00b8\u00b9\7"+
		".\2\2\u00b9\u00ba\7\6\2\2\u00ba\u00bb\b\t\1\2\u00bb\u00bd\3\2\2\2\u00bc"+
		"\u00ac\3\2\2\2\u00bc\u00b3\3\2\2\2\u00bd\21\3\2\2\2\u00be\u00bf\5\30\r"+
		"\2\u00bf\u00c0\b\n\1\2\u00c0\23\3\2\2\2\u00c1\u00c2\5\30\r\2\u00c2\u00c3"+
		"\b\13\1\2\u00c3\25\3\2\2\2\u00c4\u00c5\5\30\r\2\u00c5\u00c6\b\f\1\2\u00c6"+
		"\27\3\2\2\2\u00c7\u00c8\7B\2\2\u00c8\u00c9\7\t\2\2\u00c9\u00ca\5\36\20"+
		"\2\u00ca\u00cb\7\6\2\2\u00cb\u00cc\b\r\1\2\u00cc\u00d7\3\2\2\2\u00cd\u00ce"+
		"\79\2\2\u00ce\u00cf\7\25\2\2\u00cf\u00d0\5\34\17\2\u00d0\u00d1\7.\2\2"+
		"\u00d1\u00d2\7\7\2\2\u00d2\u00d3\5\30\r\2\u00d3\u00d4\7\n\2\2\u00d4\u00d5"+
		"\b\r\1\2\u00d5\u00d7\3\2\2\2\u00d6\u00c7\3\2\2\2\u00d6\u00cd\3\2\2\2\u00d7"+
		"\31\3\2\2\2\u00d8\u00d9\b\16\1\2\u00d9\u00da\7B\2\2\u00da\u00db\b\16\1"+
		"\2\u00db\u00e2\3\2\2\2\u00dc\u00dd\f\3\2\2\u00dd\u00de\7\30\2\2\u00de"+
		"\u00df\7B\2\2\u00df\u00e1\b\16\1\2\u00e0\u00dc\3\2\2\2\u00e1\u00e4\3\2"+
		"\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\33\3\2\2\2\u00e4\u00e2"+
		"\3\2\2\2\u00e5\u00e6\b\17\1\2\u00e6\u00e7\5\36\20\2\u00e7\u00e8\t\2\2"+
		"\2\u00e8\u00e9\5\36\20\2\u00e9\u00ea\b\17\1\2\u00ea\u00f6\3\2\2\2\u00eb"+
		"\u00ec\7B\2\2\u00ec\u00ed\7\17\2\2\u00ed\u00ee\5\36\20\2\u00ee\u00ef\b"+
		"\17\1\2\u00ef\u00f6\3\2\2\2\u00f0\u00f1\7B\2\2\u00f1\u00f2\7\23\2\2\u00f2"+
		"\u00f3\5\36\20\2\u00f3\u00f4\b\17\1\2\u00f4\u00f6\3\2\2\2\u00f5\u00e5"+
		"\3\2\2\2\u00f5\u00eb\3\2\2\2\u00f5\u00f0\3\2\2\2\u00f6\u00fe\3\2\2\2\u00f7"+
		"\u00f8\f\3\2\2\u00f8\u00f9\t\3\2\2\u00f9\u00fa\5\34\17\4\u00fa\u00fb\b"+
		"\17\1\2\u00fb\u00fd\3\2\2\2\u00fc\u00f7\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe"+
		"\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\35\3\2\2\2\u0100\u00fe\3\2\2"+
		"\2\u0101\u0102\b\20\1\2\u0102\u0103\7\62\2\2\u0103\u0104\5\36\20\7\u0104"+
		"\u0105\b\20\1\2\u0105\u0129\3\2\2\2\u0106\u0107\7\60\2\2\u0107\u0108\5"+
		"\36\20\6\u0108\u0109\b\20\1\2\u0109\u0129\3\2\2\2\u010a\u010b\5(\25\2"+
		"\u010b\u010c\b\20\1\2\u010c\u0129\3\2\2\2\u010d\u010e\5*\26\2\u010e\u010f"+
		"\b\20\1\2\u010f\u0129\3\2\2\2\u0110\u0111\5.\30\2\u0111\u0112\b\20\1\2"+
		"\u0112\u0129\3\2\2\2\u0113\u0114\5&\24\2\u0114\u0115\b\20\1\2\u0115\u0129"+
		"\3\2\2\2\u0116\u0117\7\25\2\2\u0117\u0118\5\36\20\2\u0118\u0119\7.\2\2"+
		"\u0119\u011a\b\20\1\2\u011a\u0129\3\2\2\2\u011b\u011c\7$\2\2\u011c\u011d"+
		"\5\36\20\2\u011d\u011e\7$\2\2\u011e\u011f\b\20\1\2\u011f\u0129\3\2\2\2"+
		"\u0120\u0121\7%\2\2\u0121\u0122\5\36\20\2\u0122\u0123\7\'\2\2\u0123\u0124"+
		"\b\20\1\2\u0124\u0129\3\2\2\2\u0125\u0126\5 \21\2\u0126\u0127\b\20\1\2"+
		"\u0127\u0129\3\2\2\2\u0128\u0101\3\2\2\2\u0128\u0106\3\2\2\2\u0128\u010a"+
		"\3\2\2\2\u0128\u010d\3\2\2\2\u0128\u0110\3\2\2\2\u0128\u0113\3\2\2\2\u0128"+
		"\u0116\3\2\2\2\u0128\u011b\3\2\2\2\u0128\u0120\3\2\2\2\u0128\u0125\3\2"+
		"\2\2\u0129\u0136\3\2\2\2\u012a\u012b\f\5\2\2\u012b\u012c\7\60\2\2\u012c"+
		"\u012d\5\36\20\6\u012d\u012e\b\20\1\2\u012e\u0135\3\2\2\2\u012f\u0130"+
		"\f\4\2\2\u0130\u0131\7\62\2\2\u0131\u0132\5\36\20\5\u0132\u0133\b\20\1"+
		"\2\u0133\u0135\3\2\2\2\u0134\u012a\3\2\2\2\u0134\u012f\3\2\2\2\u0135\u0138"+
		"\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137\3\2\2\2\u0137\37\3\2\2\2\u0138"+
		"\u0136\3\2\2\2\u0139\u013a\b\21\1\2\u013a\u013b\7-\2\2\u013b\u013c\5 "+
		"\21\5\u013c\u013d\b\21\1\2\u013d\u015d\3\2\2\2\u013e\u013f\5(\25\2\u013f"+
		"\u0140\b\21\1\2\u0140\u015d\3\2\2\2\u0141\u0142\5*\26\2\u0142\u0143\b"+
		"\21\1\2\u0143\u015d\3\2\2\2\u0144\u0145\5,\27\2\u0145\u0146\b\21\1\2\u0146"+
		"\u015d\3\2\2\2\u0147\u0148\5&\24\2\u0148\u0149\b\21\1\2\u0149\u015d\3"+
		"\2\2\2\u014a\u014b\7\25\2\2\u014b\u014c\5\36\20\2\u014c\u014d\7.\2\2\u014d"+
		"\u014e\b\21\1\2\u014e\u015d\3\2\2\2\u014f\u0150\7$\2\2\u0150\u0151\5\36"+
		"\20\2\u0151\u0152\7$\2\2\u0152\u0153\b\21\1\2\u0153\u015d\3\2\2\2\u0154"+
		"\u0155\7%\2\2\u0155\u0156\5\36\20\2\u0156\u0157\7\'\2\2\u0157\u0158\b"+
		"\21\1\2\u0158\u015d\3\2\2\2\u0159\u015a\5\"\22\2\u015a\u015b\b\21\1\2"+
		"\u015b\u015d\3\2\2\2\u015c\u0139\3\2\2\2\u015c\u013e\3\2\2\2\u015c\u0141"+
		"\3\2\2\2\u015c\u0144\3\2\2\2\u015c\u0147\3\2\2\2\u015c\u014a\3\2\2\2\u015c"+
		"\u014f\3\2\2\2\u015c\u0154\3\2\2\2\u015c\u0159\3\2\2\2\u015d\u0168\3\2"+
		"\2\2\u015e\u015f\f\6\2\2\u015f\u0160\7\27\2\2\u0160\u0161\5 \21\7\u0161"+
		"\u0162\b\21\1\2\u0162\u0167\3\2\2\2\u0163\u0164\f\4\2\2\u0164\u0165\7"+
		"-\2\2\u0165\u0167\b\21\1\2\u0166\u015e\3\2\2\2\u0166\u0163\3\2\2\2\u0167"+
		"\u016a\3\2\2\2\u0168\u0166\3\2\2\2\u0168\u0169\3\2\2\2\u0169!\3\2\2\2"+
		"\u016a\u0168\3\2\2\2\u016b\u016c\b\22\1\2\u016c\u016d\5(\25\2\u016d\u016e"+
		"\b\22\1\2\u016e\u018b\3\2\2\2\u016f\u0170\5*\26\2\u0170\u0171\b\22\1\2"+
		"\u0171\u018b\3\2\2\2\u0172\u0173\5,\27\2\u0173\u0174\b\22\1\2\u0174\u018b"+
		"\3\2\2\2\u0175\u0176\5&\24\2\u0176\u0177\b\22\1\2\u0177\u018b\3\2\2\2"+
		"\u0178\u0179\7\25\2\2\u0179\u017a\5\36\20\2\u017a\u017b\7.\2\2\u017b\u017c"+
		"\b\22\1\2\u017c\u018b\3\2\2\2\u017d\u017e\7$\2\2\u017e\u017f\5\36\20\2"+
		"\u017f\u0180\7$\2\2\u0180\u0181\b\22\1\2\u0181\u018b\3\2\2\2\u0182\u0183"+
		"\7%\2\2\u0183\u0184\5\36\20\2\u0184\u0185\7\'\2\2\u0185\u0186\b\22\1\2"+
		"\u0186\u018b\3\2\2\2\u0187\u0188\5$\23\2\u0188\u0189\b\22\1\2\u0189\u018b"+
		"\3\2\2\2\u018a\u016b\3\2\2\2\u018a\u016f\3\2\2\2\u018a\u0172\3\2\2\2\u018a"+
		"\u0175\3\2\2\2\u018a\u0178\3\2\2\2\u018a\u017d\3\2\2\2\u018a\u0182\3\2"+
		"\2\2\u018a\u0187\3\2\2\2\u018b\u0193\3\2\2\2\u018c\u018d\f\4\2\2\u018d"+
		"\u018e\7\3\2\2\u018e\u018f\5\"\22\5\u018f\u0190\b\22\1\2\u0190\u0192\3"+
		"\2\2\2\u0191\u018c\3\2\2\2\u0192\u0195\3\2\2\2\u0193\u0191\3\2\2\2\u0193"+
		"\u0194\3\2\2\2\u0194#\3\2\2\2\u0195\u0193\3\2\2\2\u0196\u0197\b\23\1\2"+
		"\u0197\u0198\5(\25\2\u0198\u0199\b\23\1\2\u0199\u01b3\3\2\2\2\u019a\u019b"+
		"\5*\26\2\u019b\u019c\b\23\1\2\u019c\u01b3\3\2\2\2\u019d\u019e\5,\27\2"+
		"\u019e\u019f\b\23\1\2\u019f\u01b3\3\2\2\2\u01a0\u01a1\5&\24\2\u01a1\u01a2"+
		"\b\23\1\2\u01a2\u01b3\3\2\2\2\u01a3\u01a4\7\25\2\2\u01a4\u01a5\5\36\20"+
		"\2\u01a5\u01a6\7.\2\2\u01a6\u01a7\b\23\1\2\u01a7\u01b3\3\2\2\2\u01a8\u01a9"+
		"\7$\2\2\u01a9\u01aa\5\36\20\2\u01aa\u01ab\7$\2\2\u01ab\u01ac\b\23\1\2"+
		"\u01ac\u01b3\3\2\2\2\u01ad\u01ae\7%\2\2\u01ae\u01af\5\36\20\2\u01af\u01b0"+
		"\7\'\2\2\u01b0\u01b1\b\23\1\2\u01b1\u01b3\3\2\2\2\u01b2\u0196\3\2\2\2"+
		"\u01b2\u019a\3\2\2\2\u01b2\u019d\3\2\2\2\u01b2\u01a0\3\2\2\2\u01b2\u01a3"+
		"\3\2\2\2\u01b2\u01a8\3\2\2\2\u01b2\u01ad\3\2\2\2\u01b3\u01bb\3\2\2\2\u01b4"+
		"\u01b5\f\3\2\2\u01b5\u01b6\7\r\2\2\u01b6\u01b7\5$\23\4\u01b7\u01b8\b\23"+
		"\1\2\u01b8\u01ba\3\2\2\2\u01b9\u01b4\3\2\2\2\u01ba\u01bd\3\2\2\2\u01bb"+
		"\u01b9\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc%\3\2\2\2\u01bd\u01bb\3\2\2\2"+
		"\u01be\u01bf\t\4\2\2\u01bf\u01c0\7\25\2\2\u01c0\u01c1\5\36\20\2\u01c1"+
		"\u01c2\7.\2\2\u01c2\u01c3\b\24\1\2\u01c3\u01d9\3\2\2\2\u01c4\u01c5\t\5"+
		"\2\2\u01c5\u01c6\7\25\2\2\u01c6\u01c7\5\36\20\2\u01c7\u01c8\7.\2\2\u01c8"+
		"\u01c9\b\24\1\2\u01c9\u01d9\3\2\2\2\u01ca\u01cb\t\6\2\2\u01cb\u01cc\7"+
		"\25\2\2\u01cc\u01cd\5\36\20\2\u01cd\u01ce\7.\2\2\u01ce\u01cf\b\24\1\2"+
		"\u01cf\u01d9\3\2\2\2\u01d0\u01d1\t\7\2\2\u01d1\u01d2\7\25\2\2\u01d2\u01d3"+
		"\5\36\20\2\u01d3\u01d4\7\30\2\2\u01d4\u01d5\5\36\20\2\u01d5\u01d6\7.\2"+
		"\2\u01d6\u01d7\b\24\1\2\u01d7\u01d9\3\2\2\2\u01d8\u01be\3\2\2\2\u01d8"+
		"\u01c4\3\2\2\2\u01d8\u01ca\3\2\2\2\u01d8\u01d0\3\2\2\2\u01d9\'\3\2\2\2"+
		"\u01da\u01db\7+\2\2\u01db\u01e1\b\25\1\2\u01dc\u01dd\7\37\2\2\u01dd\u01e1"+
		"\b\25\1\2\u01de\u01df\7\4\2\2\u01df\u01e1\b\25\1\2\u01e0\u01da\3\2\2\2"+
		"\u01e0\u01dc\3\2\2\2\u01e0\u01de\3\2\2\2\u01e1)\3\2\2\2\u01e2\u01e3\7"+
		"B\2\2\u01e3\u01e4\b\26\1\2\u01e4+\3\2\2\2\u01e5\u01e7\7\60\2\2\u01e6\u01e5"+
		"\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8\u01e9\t\b\2\2\u01e9"+
		"\u01ee\b\27\1\2\u01ea\u01eb\7\62\2\2\u01eb\u01ec\t\b\2\2\u01ec\u01ee\b"+
		"\27\1\2\u01ed\u01e6\3\2\2\2\u01ed\u01ea\3\2\2\2\u01ee-\3\2\2\2\u01ef\u01f1"+
		"\7%\2\2\u01f0\u01f2\7\60\2\2\u01f1\u01f0\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2"+
		"\u01f3\3\2\2\2\u01f3\u01f4\t\b\2\2\u01f4\u01f6\7\30\2\2\u01f5\u01f7\7"+
		"\60\2\2\u01f6\u01f5\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7\u01f8\3\2\2\2\u01f8"+
		"\u01f9\t\b\2\2\u01f9\u01fa\7\'\2\2\u01fa\u0241\b\30\1\2\u01fb\u01fd\7"+
		"%\2\2\u01fc\u01fe\7\60\2\2\u01fd\u01fc\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe"+
		"\u01ff\3\2\2\2\u01ff\u0200\t\b\2\2\u0200\u0201\7\30\2\2\u0201\u0202\7"+
		"\62\2\2\u0202\u0203\t\b\2\2\u0203\u0204\7\'\2\2\u0204\u0241\b\30\1\2\u0205"+
		"\u0206\7%\2\2\u0206\u0207\7\62\2\2\u0207\u0208\t\b\2\2\u0208\u020a\7\30"+
		"\2\2\u0209\u020b\7\60\2\2\u020a\u0209\3\2\2\2\u020a\u020b\3\2\2\2\u020b"+
		"\u020c\3\2\2\2\u020c\u020d\t\b\2\2\u020d\u020e\7\'\2\2\u020e\u0241\b\30"+
		"\1\2\u020f\u0210\7%\2\2\u0210\u0211\7\62\2\2\u0211\u0212\t\b\2\2\u0212"+
		"\u0213\7\30\2\2\u0213\u0214\7\62\2\2\u0214\u0215\t\b\2\2\u0215\u0216\7"+
		"\'\2\2\u0216\u0241\b\30\1\2\u0217\u0219\7\60\2\2\u0218\u0217\3\2\2\2\u0218"+
		"\u0219\3\2\2\2\u0219\u021a\3\2\2\2\u021a\u021b\t\b\2\2\u021b\u021c\7\60"+
		"\2\2\u021c\u021d\t\b\2\2\u021d\u021e\7-\2\2\u021e\u0241\b\30\1\2\u021f"+
		"\u0221\7\60\2\2\u0220\u021f\3\2\2\2\u0220\u0221\3\2\2\2\u0221\u0222\3"+
		"\2\2\2\u0222\u0223\t\b\2\2\u0223\u0224\7\62\2\2\u0224\u0225\t\b\2\2\u0225"+
		"\u0226\7-\2\2\u0226\u0241\b\30\1\2\u0227\u0229\7\60\2\2\u0228\u0227\3"+
		"\2\2\2\u0228\u0229\3\2\2\2\u0229\u022a\3\2\2\2\u022a\u022b\t\b\2\2\u022b"+
		"\u022c\7-\2\2\u022c\u0241\b\30\1\2\u022d\u022e\7\62\2\2\u022e\u022f\t"+
		"\b\2\2\u022f\u0230\7\60\2\2\u0230\u0231\t\b\2\2\u0231\u0232\7-\2\2\u0232"+
		"\u0241\b\30\1\2\u0233\u0234\7\62\2\2\u0234\u0235\t\b\2\2\u0235\u0236\7"+
		"\62\2\2\u0236\u0237\t\b\2\2\u0237\u0238\7-\2\2\u0238\u0241\b\30\1\2\u0239"+
		"\u023a\7\62\2\2\u023a\u023b\t\b\2\2\u023b\u023c\7-\2\2\u023c\u0241\b\30"+
		"\1\2\u023d\u023e\5,\27\2\u023e\u023f\b\30\1\2\u023f\u0241\3\2\2\2\u0240"+
		"\u01ef\3\2\2\2\u0240\u01fb\3\2\2\2\u0240\u0205\3\2\2\2\u0240\u020f\3\2"+
		"\2\2\u0240\u0218\3\2\2\2\u0240\u0220\3\2\2\2\u0240\u0228\3\2\2\2\u0240"+
		"\u022d\3\2\2\2\u0240\u0233\3\2\2\2\u0240\u0239\3\2\2\2\u0240\u023d\3\2"+
		"\2\2\u0241/\3\2\2\2\u0242\u0243\7;\2\2\u0243\u0244\7B\2\2\u0244\u0245"+
		"\b\31\1\2\u0245\u0247\7\7\2\2\u0246\u0248\5\62\32\2\u0247\u0246\3\2\2"+
		"\2\u0248\u0249\3\2\2\2\u0249\u0247\3\2\2\2\u0249\u024a\3\2\2\2\u024a\u024b"+
		"\3\2\2\2\u024b\u024c\7\n\2\2\u024c\61\3\2\2\2\u024d\u024e\7\"\2\2\u024e"+
		"\u024f\5:\36\2\u024f\u0250\7\'\2\2\u0250\u0251\5:\36\2\u0251\u0252\7\30"+
		"\2\2\u0252\u0253\7?\2\2\u0253\u0254\7\30\2\2\u0254\u0255\5\36\20\2\u0255"+
		"\u0256\7&\2\2\u0256\u0257\7\6\2\2\u0257\u0258\b\32\1\2\u0258\u0264\3\2"+
		"\2\2\u0259\u025a\7\"\2\2\u025a\u025b\5:\36\2\u025b\u025c\7\'\2\2\u025c"+
		"\u025d\5:\36\2\u025d\u025e\7\30\2\2\u025e\u025f\7?\2\2\u025f\u0260\7&"+
		"\2\2\u0260\u0261\7\6\2\2\u0261\u0262\b\32\1\2\u0262\u0264\3\2\2\2\u0263"+
		"\u024d\3\2\2\2\u0263\u0259\3\2\2\2\u0264\63\3\2\2\2\u0265\u0266\7<\2\2"+
		"\u0266\u0267\7\25\2\2\u0267\u0268\5\66\34\2\u0268\u0269\7.\2\2\u0269\u026a"+
		"\7\"\2\2\u026a\u026b\t\b\2\2\u026b\u026c\7&\2\2\u026c\u026d\7\7\2\2\u026d"+
		"\u026e\58\35\2\u026e\u026f\7\n\2\2\u026f\u0270\b\33\1\2\u0270\65\3\2\2"+
		"\2\u0271\u0272\b\34\1\2\u0272\u0273\5\36\20\2\u0273\u0274\t\2\2\2\u0274"+
		"\u0275\5\36\20\2\u0275\u0276\b\34\1\2\u0276\u027e\3\2\2\2\u0277\u0278"+
		"\f\3\2\2\u0278\u0279\t\3\2\2\u0279\u027a\5\66\34\4\u027a\u027b\b\34\1"+
		"\2\u027b\u027d\3\2\2\2\u027c\u0277\3\2\2\2\u027d\u0280\3\2\2\2\u027e\u027c"+
		"\3\2\2\2\u027e\u027f\3\2\2\2\u027f\67\3\2\2\2\u0280\u027e\3\2\2\2\u0281"+
		"\u0282\5\36\20\2\u0282\u0283\b\35\1\2\u0283\u029b\3\2\2\2\u0284\u0285"+
		"\5\36\20\2\u0285\u0286\7\30\2\2\u0286\u0287\5\36\20\2\u0287\u0288\7\30"+
		"\2\2\u0288\u0289\5\36\20\2\u0289\u028a\b\35\1\2\u028a\u029b\3\2\2\2\u028b"+
		"\u028c\5\36\20\2\u028c\u028d\7\30\2\2\u028d\u028e\5\36\20\2\u028e\u028f"+
		"\7\30\2\2\u028f\u0290\5\36\20\2\u0290\u0291\7\30\2\2\u0291\u0292\5\36"+
		"\20\2\u0292\u0293\b\35\1\2\u0293\u029b\3\2\2\2\u0294\u0295\7B\2\2\u0295"+
		"\u0296\7\"\2\2\u0296\u0297\5\36\20\2\u0297\u0298\7&\2\2\u0298\u0299\b"+
		"\35\1\2\u0299\u029b\3\2\2\2\u029a\u0281\3\2\2\2\u029a\u0284\3\2\2\2\u029a"+
		"\u028b\3\2\2\2\u029a\u0294\3\2\2\2\u029b9\3\2\2\2\u029c\u029d\7\25\2\2"+
		"\u029d\u029e\t\b\2\2\u029e\u029f\7\30\2\2\u029f\u02a0\t\b\2\2\u02a0\u02a1"+
		"\7\30\2\2\u02a1\u02a2\t\b\2\2\u02a2\u02a3\7\30\2\2\u02a3\u02a4\t\b\2\2"+
		"\u02a4\u02a5\7.\2\2\u02a5\u02aa\b\36\1\2\u02a6\u02a7\7*\2\2\u02a7\u02a8"+
		"\7=\2\2\u02a8\u02aa\b\36\1\2\u02a9\u029c\3\2\2\2\u02a9\u02a6\3\2\2\2\u02aa"+
		";\3\2\2\2\u02ab\u02ac\7\2\2\3\u02ac=\3\2\2\2+TX\\ioz\u008d\u0098\u00a7"+
		"\u00bc\u00d6\u00e2\u00f5\u00fe\u0128\u0134\u0136\u015c\u0166\u0168\u018a"+
		"\u0193\u01b2\u01bb\u01d8\u01e0\u01e6\u01ed\u01f1\u01f6\u01fd\u020a\u0218"+
		"\u0220\u0228\u0240\u0249\u0263\u027e\u029a\u02a9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}