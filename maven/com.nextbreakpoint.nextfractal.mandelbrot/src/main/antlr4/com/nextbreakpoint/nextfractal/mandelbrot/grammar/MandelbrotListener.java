// Generated from Mandelbrot.g4 by ANTLR 4.2.2
package com.nextbreakpoint.nextfractal.mandelbrot.grammar;


import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MandelbrotParser}.
 */
public interface MandelbrotListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#beginstatement}.
	 * @param ctx the parse tree
	 */
	void enterBeginstatement(@NotNull MandelbrotParser.BeginstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#beginstatement}.
	 * @param ctx the parse tree
	 */
	void exitBeginstatement(@NotNull MandelbrotParser.BeginstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(@NotNull MandelbrotParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(@NotNull MandelbrotParser.ConstantContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#color}.
	 * @param ctx the parse tree
	 */
	void enterColor(@NotNull MandelbrotParser.ColorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#color}.
	 * @param ctx the parse tree
	 */
	void exitColor(@NotNull MandelbrotParser.ColorContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#loopstatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopstatement(@NotNull MandelbrotParser.LoopstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#loopstatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopstatement(@NotNull MandelbrotParser.LoopstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#conditionexp4}.
	 * @param ctx the parse tree
	 */
	void enterConditionexp4(@NotNull MandelbrotParser.Conditionexp4Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#conditionexp4}.
	 * @param ctx the parse tree
	 */
	void exitConditionexp4(@NotNull MandelbrotParser.Conditionexp4Context ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#conditionexp3}.
	 * @param ctx the parse tree
	 */
	void enterConditionexp3(@NotNull MandelbrotParser.Conditionexp3Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#conditionexp3}.
	 * @param ctx the parse tree
	 */
	void exitConditionexp3(@NotNull MandelbrotParser.Conditionexp3Context ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#conditionexp2}.
	 * @param ctx the parse tree
	 */
	void enterConditionexp2(@NotNull MandelbrotParser.Conditionexp2Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#conditionexp2}.
	 * @param ctx the parse tree
	 */
	void exitConditionexp2(@NotNull MandelbrotParser.Conditionexp2Context ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#variablelist}.
	 * @param ctx the parse tree
	 */
	void enterVariablelist(@NotNull MandelbrotParser.VariablelistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#variablelist}.
	 * @param ctx the parse tree
	 */
	void exitVariablelist(@NotNull MandelbrotParser.VariablelistContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(@NotNull MandelbrotParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(@NotNull MandelbrotParser.LoopContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(@NotNull MandelbrotParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(@NotNull MandelbrotParser.FunctionContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#complex}.
	 * @param ctx the parse tree
	 */
	void enterComplex(@NotNull MandelbrotParser.ComplexContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#complex}.
	 * @param ctx the parse tree
	 */
	void exitComplex(@NotNull MandelbrotParser.ComplexContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull MandelbrotParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull MandelbrotParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#end}.
	 * @param ctx the parse tree
	 */
	void enterEnd(@NotNull MandelbrotParser.EndContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#end}.
	 * @param ctx the parse tree
	 */
	void exitEnd(@NotNull MandelbrotParser.EndContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#palette}.
	 * @param ctx the parse tree
	 */
	void enterPalette(@NotNull MandelbrotParser.PaletteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#palette}.
	 * @param ctx the parse tree
	 */
	void exitPalette(@NotNull MandelbrotParser.PaletteContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#orbit}.
	 * @param ctx the parse tree
	 */
	void enterOrbit(@NotNull MandelbrotParser.OrbitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#orbit}.
	 * @param ctx the parse tree
	 */
	void exitOrbit(@NotNull MandelbrotParser.OrbitContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#trap}.
	 * @param ctx the parse tree
	 */
	void enterTrap(@NotNull MandelbrotParser.TrapContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#trap}.
	 * @param ctx the parse tree
	 */
	void exitTrap(@NotNull MandelbrotParser.TrapContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorexp}.
	 * @param ctx the parse tree
	 */
	void enterColorexp(@NotNull MandelbrotParser.ColorexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorexp}.
	 * @param ctx the parse tree
	 */
	void exitColorexp(@NotNull MandelbrotParser.ColorexpContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull MandelbrotParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull MandelbrotParser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#simpleexpression}.
	 * @param ctx the parse tree
	 */
	void enterSimpleexpression(@NotNull MandelbrotParser.SimpleexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#simpleexpression}.
	 * @param ctx the parse tree
	 */
	void exitSimpleexpression(@NotNull MandelbrotParser.SimpleexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#expression4}.
	 * @param ctx the parse tree
	 */
	void enterExpression4(@NotNull MandelbrotParser.Expression4Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#expression4}.
	 * @param ctx the parse tree
	 */
	void exitExpression4(@NotNull MandelbrotParser.Expression4Context ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#real}.
	 * @param ctx the parse tree
	 */
	void enterReal(@NotNull MandelbrotParser.RealContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#real}.
	 * @param ctx the parse tree
	 */
	void exitReal(@NotNull MandelbrotParser.RealContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#expression2}.
	 * @param ctx the parse tree
	 */
	void enterExpression2(@NotNull MandelbrotParser.Expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#expression2}.
	 * @param ctx the parse tree
	 */
	void exitExpression2(@NotNull MandelbrotParser.Expression2Context ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#pathop}.
	 * @param ctx the parse tree
	 */
	void enterPathop(@NotNull MandelbrotParser.PathopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#pathop}.
	 * @param ctx the parse tree
	 */
	void exitPathop(@NotNull MandelbrotParser.PathopContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#expression3}.
	 * @param ctx the parse tree
	 */
	void enterExpression3(@NotNull MandelbrotParser.Expression3Context ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#expression3}.
	 * @param ctx the parse tree
	 */
	void exitExpression3(@NotNull MandelbrotParser.Expression3Context ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorinit}.
	 * @param ctx the parse tree
	 */
	void enterColorinit(@NotNull MandelbrotParser.ColorinitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorinit}.
	 * @param ctx the parse tree
	 */
	void exitColorinit(@NotNull MandelbrotParser.ColorinitContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#ruleexp}.
	 * @param ctx the parse tree
	 */
	void enterRuleexp(@NotNull MandelbrotParser.RuleexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#ruleexp}.
	 * @param ctx the parse tree
	 */
	void exitRuleexp(@NotNull MandelbrotParser.RuleexpContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#conditionexp}.
	 * @param ctx the parse tree
	 */
	void enterConditionexp(@NotNull MandelbrotParser.ConditionexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#conditionexp}.
	 * @param ctx the parse tree
	 */
	void exitConditionexp(@NotNull MandelbrotParser.ConditionexpContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#endstatement}.
	 * @param ctx the parse tree
	 */
	void enterEndstatement(@NotNull MandelbrotParser.EndstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#endstatement}.
	 * @param ctx the parse tree
	 */
	void exitEndstatement(@NotNull MandelbrotParser.EndstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#simpleconditionexp}.
	 * @param ctx the parse tree
	 */
	void enterSimpleconditionexp(@NotNull MandelbrotParser.SimpleconditionexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#simpleconditionexp}.
	 * @param ctx the parse tree
	 */
	void exitSimpleconditionexp(@NotNull MandelbrotParser.SimpleconditionexpContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorstatement}.
	 * @param ctx the parse tree
	 */
	void enterColorstatement(@NotNull MandelbrotParser.ColorstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorstatement}.
	 * @param ctx the parse tree
	 */
	void exitColorstatement(@NotNull MandelbrotParser.ColorstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorargb}.
	 * @param ctx the parse tree
	 */
	void enterColorargb(@NotNull MandelbrotParser.ColorargbContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorargb}.
	 * @param ctx the parse tree
	 */
	void exitColorargb(@NotNull MandelbrotParser.ColorargbContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#fractal}.
	 * @param ctx the parse tree
	 */
	void enterFractal(@NotNull MandelbrotParser.FractalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#fractal}.
	 * @param ctx the parse tree
	 */
	void exitFractal(@NotNull MandelbrotParser.FractalContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(@NotNull MandelbrotParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(@NotNull MandelbrotParser.VariableContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#paletteelement}.
	 * @param ctx the parse tree
	 */
	void enterPaletteelement(@NotNull MandelbrotParser.PaletteelementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#paletteelement}.
	 * @param ctx the parse tree
	 */
	void exitPaletteelement(@NotNull MandelbrotParser.PaletteelementContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#begin}.
	 * @param ctx the parse tree
	 */
	void enterBegin(@NotNull MandelbrotParser.BeginContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#begin}.
	 * @param ctx the parse tree
	 */
	void exitBegin(@NotNull MandelbrotParser.BeginContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#eof}.
	 * @param ctx the parse tree
	 */
	void enterEof(@NotNull MandelbrotParser.EofContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#eof}.
	 * @param ctx the parse tree
	 */
	void exitEof(@NotNull MandelbrotParser.EofContext ctx);

	/**
	 * Enter a parse tree produced by {@link MandelbrotParser#colorrule}.
	 * @param ctx the parse tree
	 */
	void enterColorrule(@NotNull MandelbrotParser.ColorruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link MandelbrotParser#colorrule}.
	 * @param ctx the parse tree
	 */
	void exitColorrule(@NotNull MandelbrotParser.ColorruleContext ctx);
}