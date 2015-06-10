/*
 * NextFractal 1.1.0
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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

class ASTRepContainer {
	private EPathOp pathOp;
	private int repType;
	private List<ASTReplacement> body = new ArrayList<ASTReplacement>();
	private List<ASTParameter> parameters = new ArrayList<ASTParameter>();
	private boolean isGlobal;
	private int stackCount;

	public ASTRepContainer() {
		pathOp = EPathOp.UNKNOWN;
		repType = ERepElemType.empty.getType();
		isGlobal = false;
		stackCount = 0;
	}
	
	public ASTRepContainer(ASTRepContainer repCont) {
		pathOp = repCont.pathOp;
		repType = repCont.repType;
		isGlobal = repCont.isGlobal;
		stackCount = repCont.stackCount;
	}
	
	public void setStackCount(int stackCount) {
		this.stackCount = stackCount;
	}

	public EPathOp getPathOp() {
		return pathOp;
	}

	public void setPathOp(EPathOp pathOp) {
		this.pathOp = pathOp;
	}
	
	public int getRepType() {
		return repType;
	}

	public void setRepType(int repType) {
		this.repType = repType;
	}
	
	public List<ASTReplacement> getBody() {
		return body;
	}

	public List<ASTParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ASTParameter> parameters) {
		this.parameters = parameters;
	}	

	public boolean isGlobal() {
		return isGlobal;
	}

	public int getStackCount() {
		return stackCount;
	}

	public void addParameter(String type, int nameIndex, Token location) {
		parameters.add(new ASTParameter(type, nameIndex, location));
		ASTParameter param = parameters.get(parameters.size() - 1);
		param.setIsParameter(true);
		param.check();
	}

	public ASTParameter addDefParameter(int nameIndex, ASTDefine def, Token location) {
		parameters.add(new ASTParameter(nameIndex, def, location));
		ASTParameter param = parameters.get(parameters.size() - 1);
		param.check();
		return param;
	}

	public void addLoopParameter(int nameIndex, boolean natural, boolean local, Token location) {
		parameters.add(new ASTParameter(nameIndex, natural, local, location));
		ASTParameter param = parameters.get(parameters.size() - 1);
		param.check();
		stackCount += param.getTupleSize();
	}

	public void compile(ECompilePhase ph, ASTLoop loop, ASTDefine def) {
		if (ph == ECompilePhase.TypeCheck) {
			stackCount = 0;
			for (int i = 0; i < parameters.size(); i++) {
				if (parameters.get(i).isParameter() || parameters.get(i).isLoopIndex()) {
					stackCount += parameters.get(i).getTupleSize();
				} else {
					parameters = parameters.subList(0, i);
					break;
				}
			}
			
			Builder.currentBuilder().pushRepContainer(this);
			if (loop != null) {
				loop.compileLoopMod();
			}
			for (ASTReplacement rep : body) {
				rep.compile(ph);
			}
			if (def != null) {
				def.compile(ph);
			}
			Builder.currentBuilder().popRepContainer(null);
		}
	}
	
	public void traverse(Shape parent, boolean tr, RTI rti, boolean getParams) {
		int s = rti.getCFStack().size();
		if (getParams && parent.getParameters() != null) {
			rti.initStack(parent.getParameters());
		}
		for (ASTReplacement rep : body) {
			rep.traverse(parent, tr, rti);
		}
		rti.unwindStack(s, getParameters());
	}
}
