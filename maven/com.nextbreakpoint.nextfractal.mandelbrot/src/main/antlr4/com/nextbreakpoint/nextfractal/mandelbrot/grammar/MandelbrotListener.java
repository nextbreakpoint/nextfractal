// Generated from com/nextbreakpoint/nextfractal/mandelbrot/grammar/Mandelbrot.g4 by ANTLR 4.5.1
package com.nextbreakpoint.nextfractal.mandelbrot.grammar;


import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MandelbrotParser}.
 */
public interface MandelbrotListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#fractal}.
	 * @param ctx the parse tree
	 */
	void enterFractal(MandelbrotParser.FractalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#fractal}.
	 * @param ctx the parse tree
	 */
	void exitFractal(MandelbrotParser.FractalContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#orbit}.
	 * @param ctx the parse tree
	 */
	void enterOrbit(MandelbrotParser.OrbitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#orbit}.
	 * @param ctx the parse tree
	 */
	void exitOrbit(MandelbrotParser.OrbitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#color}.
	 * @param ctx the parse tree
	 */
	void enterColor(MandelbrotParser.ColorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#color}.
	 * @param ctx the parse tree
	 */
	void exitColor(MandelbrotParser.ColorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#begin}.
	 * @param ctx the parse tree
	 */
	void enterBegin(MandelbrotParser.BeginContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#begin}.
	 * @param ctx the parse tree
	 */
	void exitBegin(MandelbrotParser.BeginContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(MandelbrotParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(MandelbrotParser.LoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#end}.
	 * @param ctx the parse tree
	 */
	void enterEnd(MandelbrotParser.EndContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#end}.
	 * @param ctx the parse tree
	 */
	void exitEnd(MandelbrotParser.EndContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#trap}.
	 * @param ctx the parse tree
	 */
	void enterTrap(MandelbrotParser.TrapContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#trap}.
	 * @param ctx the parse tree
	 */
	void exitTrap(MandelbrotParser.TrapContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#pathop}.
	 * @param ctx the parse tree
	 */
	void enterPathop(MandelbrotParser.PathopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#pathop}.
	 * @param ctx the parse tree
	 */
	void exitPathop(MandelbrotParser.PathopContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#beginstatement}.
	 * @param ctx the parse tree
	 */
	void enterBeginstatement(MandelbrotParser.BeginstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#beginstatement}.
	 * @param ctx the parse tree
	 */
	void exitBeginstatement(MandelbrotParser.BeginstatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#loopstatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopstatement(MandelbrotParser.LoopstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#loopstatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopstatement(MandelbrotParser.LoopstatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#endstatement}.
	 * @param ctx the parse tree
	 */
	void enterEndstatement(MandelbrotParser.EndstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#endstatement}.
	 * @param ctx the parse tree
	 */
	void exitEndstatement(MandelbrotParser.EndstatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MandelbrotParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MandelbrotParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#statevariable}.
	 * @param ctx the parse tree
	 */
	void enterStatevariable(MandelbrotParser.StatevariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#statevariable}.
	 * @param ctx the parse tree
	 */
	void exitStatevariable(MandelbrotParser.StatevariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#statevariablelist}.
	 * @param ctx the parse tree
	 */
	void enterStatevariablelist(MandelbrotParser.StatevariablelistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#statevariablelist}.
	 * @param ctx the parse tree
	 */
	void exitStatevariablelist(MandelbrotParser.StatevariablelistContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#simpleconditionexp}.
	 * @param ctx the parse tree
	 */
	void enterSimpleconditionexp(MandelbrotParser.SimpleconditionexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#simpleconditionexp}.
	 * @param ctx the parse tree
	 */
	void exitSimpleconditionexp(MandelbrotParser.SimpleconditionexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#conditionexp}.
	 * @param ctx the parse tree
	 */
	void enterConditionexp(MandelbrotParser.ConditionexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#conditionexp}.
	 * @param ctx the parse tree
	 */
	void exitConditionexp(MandelbrotParser.ConditionexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#conditionexp2}.
	 * @param ctx the parse tree
	 */
	void enterConditionexp2(MandelbrotParser.Conditionexp2Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#conditionexp2}.
	 * @param ctx the parse tree
	 */
	void exitConditionexp2(MandelbrotParser.Conditionexp2Context ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#conditionexp3}.
	 * @param ctx the parse tree
	 */
	void enterConditionexp3(MandelbrotParser.Conditionexp3Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#conditionexp3}.
	 * @param ctx the parse tree
	 */
	void exitConditionexp3(MandelbrotParser.Conditionexp3Context ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#conditionexp4}.
	 * @param ctx the parse tree
	 */
	void enterConditionexp4(MandelbrotParser.Conditionexp4Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#conditionexp4}.
	 * @param ctx the parse tree
	 */
	void exitConditionexp4(MandelbrotParser.Conditionexp4Context ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#simpleexpression}.
	 * @param ctx the parse tree
	 */
	void enterSimpleexpression(MandelbrotParser.SimpleexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#simpleexpression}.
	 * @param ctx the parse tree
	 */
	void exitSimpleexpression(MandelbrotParser.SimpleexpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MandelbrotParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MandelbrotParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#expression2}.
	 * @param ctx the parse tree
	 */
	void enterExpression2(MandelbrotParser.Expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#expression2}.
	 * @param ctx the parse tree
	 */
	void exitExpression2(MandelbrotParser.Expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#expression3}.
	 * @param ctx the parse tree
	 */
	void enterExpression3(MandelbrotParser.Expression3Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#expression3}.
	 * @param ctx the parse tree
	 */
	void exitExpression3(MandelbrotParser.Expression3Context ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#expression4}.
	 * @param ctx the parse tree
	 */
	void enterExpression4(MandelbrotParser.Expression4Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#expression4}.
	 * @param ctx the parse tree
	 */
	void exitExpression4(MandelbrotParser.Expression4Context ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(MandelbrotParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(MandelbrotParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(MandelbrotParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(MandelbrotParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(MandelbrotParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(MandelbrotParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#real}.
	 * @param ctx the parse tree
	 */
	void enterReal(MandelbrotParser.RealContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#real}.
	 * @param ctx the parse tree
	 */
	void exitReal(MandelbrotParser.RealContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#complex}.
	 * @param ctx the parse tree
	 */
	void enterComplex(MandelbrotParser.ComplexContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#complex}.
	 * @param ctx the parse tree
	 */
	void exitComplex(MandelbrotParser.ComplexContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#palette}.
	 * @param ctx the parse tree
	 */
	void enterPalette(MandelbrotParser.PaletteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#palette}.
	 * @param ctx the parse tree
	 */
	void exitPalette(MandelbrotParser.PaletteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#paletteelement}.
	 * @param ctx the parse tree
	 */
	void enterPaletteelement(MandelbrotParser.PaletteelementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#paletteelement}.
	 * @param ctx the parse tree
	 */
	void exitPaletteelement(MandelbrotParser.PaletteelementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorinit}.
	 * @param ctx the parse tree
	 */
	void enterColorinit(MandelbrotParser.ColorinitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorinit}.
	 * @param ctx the parse tree
	 */
	void exitColorinit(MandelbrotParser.ColorinitContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorstatement}.
	 * @param ctx the parse tree
	 */
	void enterColorstatement(MandelbrotParser.ColorstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorstatement}.
	 * @param ctx the parse tree
	 */
	void exitColorstatement(MandelbrotParser.ColorstatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorrule}.
	 * @param ctx the parse tree
	 */
	void enterColorrule(MandelbrotParser.ColorruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorrule}.
	 * @param ctx the parse tree
	 */
	void exitColorrule(MandelbrotParser.ColorruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#ruleexp}.
	 * @param ctx the parse tree
	 */
	void enterRuleexp(MandelbrotParser.RuleexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#ruleexp}.
	 * @param ctx the parse tree
	 */
	void exitRuleexp(MandelbrotParser.RuleexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorexp}.
	 * @param ctx the parse tree
	 */
	void enterColorexp(MandelbrotParser.ColorexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorexp}.
	 * @param ctx the parse tree
	 */
	void exitColorexp(MandelbrotParser.ColorexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorargb}.
	 * @param ctx the parse tree
	 */
	void enterColorargb(MandelbrotParser.ColorargbContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorargb}.
	 * @param ctx the parse tree
	 */
	void exitColorargb(MandelbrotParser.ColorargbContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#backgroundcolorargb}.
	 * @param ctx the parse tree
	 */
	void enterBackgroundcolorargb(MandelbrotParser.BackgroundcolorargbContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#backgroundcolorargb}.
	 * @param ctx the parse tree
	 */
	void exitBackgroundcolorargb(MandelbrotParser.BackgroundcolorargbContext ctx);
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#eof}.
	 * @param ctx the parse tree
	 */
	void enterEof(MandelbrotParser.EofContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#eof}.
	 * @param ctx the parse tree
	 */
	void exitEof(MandelbrotParser.EofContext ctx);
}