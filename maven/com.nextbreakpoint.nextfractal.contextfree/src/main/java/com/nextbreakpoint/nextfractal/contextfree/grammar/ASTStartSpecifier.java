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

class ASTStartSpecifier extends ASTRuleSpecifier {
	private ASTModification modification;
	
	public ASTStartSpecifier(int nameIndex, String name, ASTExpression args, ASTModification mod, Token location) {
		super(nameIndex, name, args, null, location);
		this.modification = mod;
	}
	
	public ASTStartSpecifier(int nameIndex, String name, ASTModification mod, Token location) {
		super(nameIndex, name, null, null, location);
		this.modification = mod;
	}
	
	public ASTStartSpecifier(ASTRuleSpecifier rule, ASTModification mod, Token location) {
		super(rule, location);
		this.modification = mod;
	}

	public ASTStartSpecifier(ASTExpression exp, ASTModification mod, Token location) {
		super(exp, location);
		this.modification = mod;
	}

	public ASTModification getModification() {
		return modification;
	}

	@Override
	public ASTExpression simplify() {
		super.simplify();
		if (modification != null) {
			ASTExpression m = modification.simplify();
			assert(m == modification);
		}
		return this;
	}

	@Override
	public ASTExpression compile(ECompilePhase ph) {
		super.compile(ph);
		if (modification != null) {
			modification.compile(ph);
		}
		return null;
	}

	@Override
	public void entropy(StringBuilder e) {
		e.append(getEntropy());
		if (modification != null) {
			modification.entropy(e);
		}
	}
}
