/*
 * NextFractal 1.0.4
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

import java.util.Iterator;
import java.util.List;

class RTI {

	public double getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getCurrentFrame() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setRandUsed(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public Rand64 getCurrentSeed() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxNatural() {
		// TODO Auto-generated method stub
		return 0;
	}

	public StackType stackItem(int stackIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public void colorConflict() {
		// TODO Auto-generated method stub
		
	}

	public StackType getLogicalStackTop() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StackType> getCFStack() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLogicalStackTop(StackType stackType) {
		// TODO Auto-generated method stub
		
	}

	public boolean getRequestStop() {
		// TODO Auto-generated method stub
		return false;
	}

	public void processShape(Shape shape) {
		// TODO Auto-generated method stub
		
	}

	public void processSubpath(Shape shape, boolean tr, ERepElemType repType) {
		// TODO Auto-generated method stub
		
	}

	public void initStack(StackRule parameters) {
		// TODO Auto-generated method stub
		
	}

	public void unwindStack(int size, List<ASTParameter> parameters) {
		// TODO Auto-generated method stub
		
	}

	public void setCurrentSeed(Rand64 seed) {
		// TODO Auto-generated method stub
		
	}

	public void processPrimShape(Shape parent, ASTRule astRule) {
		// TODO Auto-generated method stub
		
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public ASTCompiledPath getCurrentPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCurrentPath(ASTCompiledPath path) {
		// TODO Auto-generated method stub
		
	}

	public void setCurrentCommand(Iterator<CommandInfo> iterator) {
		// TODO Auto-generated method stub
		
	}

	public boolean isRandUsed() {
		// TODO Auto-generated method stub
		return false;
	}
}
