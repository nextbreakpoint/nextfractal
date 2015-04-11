/*
 * NextFractal 1.0
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
package com.nextbreakpoint.nextfractal.contextfree.grammar;

import org.antlr.v4.runtime.Token;

class ASTPathCommand extends ASTReplacement {
	private double miterLimit;
	private double strokeWidth;
	private ASTExpression parameters;
	private int flags;
	
	public ASTPathCommand(Token location) {
		super(null, ERepElemType.command, location);//TODO da controllare
		this.miterLimit = 4.0;
		this.strokeWidth = 0.1;
		this.parameters = null;
		this.flags = EFlagType.CF_MITER_JOIN.getType() + EFlagType.CF_BUTT_CAP.getType();
	}

	public ASTPathCommand(String s, ASTModification mods, ASTExpression params, Token location) {
		super(mods, ERepElemType.command, location);
		this.miterLimit = 4.0;
		this.strokeWidth = 0.1;
		this.parameters = params;
		this.flags = EFlagType.CF_MITER_JOIN.getType() + EFlagType.CF_BUTT_CAP.getType();
		if (s.equals("FILL")) {
			this.flags |= EFlagType.CF_FILL.getType();
		} else if (!s.equals("STROKE")) {
			error("Unknown path command/operation");
		}
	}

	public double getMiterLimit() {
		return miterLimit;
	}

	public double getStrokeWidth() {
		return strokeWidth;
	}

	public ASTExpression getParameters() {
		return parameters;
	}

	public int getFlags() {
		return flags;
	}

	@Override
	public void compile(ECompilePhase ph) {
		//TODO da completare
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		//TODO da completare
	}
}
