/*
 * NextFractal 1.0.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

public interface ASTExpressionCompiler {
	public void compile(ASTNumber number);

	public void compile(ASTFunction function);

	public void compile(ASTOperator operator);

	public void compile(ASTParen paren);

	public void compile(ASTVariable variable);

	public void compile(ASTConditionCompareOp compareOp);

	public void compile(ASTConditionLogicOp logicOp);

	public void compile(ASTConditionTrap trap);

	public void compile(ASTRuleLogicOp logicOp);

	public void compile(ASTRuleCompareOp compareOp);

	public void compile(ASTColorPalette palette);

	public void compile(ASTColorComponent component);

	public void compile(ASTConditionalStatement statement);

	public void compile(ASTAssignStatement statement);
}