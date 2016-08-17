/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

public class RTI {
	private CFDG cfdg;
	private int width;
	private int height;
	private double minSize;
	private int variation;
	private double border;
	private Point2D.Double lastPoint;
	private boolean stop;
	private boolean closed;
	private boolean wantMoveTo;
	private boolean wantCommand;
	private boolean opsOnly;
	private int index;
	private int nextIndex;
	private int cfStackSize;
	private int cfLogicalStackTop;
	private StackType[] cfStack;
	private ASTCompiledPath currentPath;
	private Iterator<CommandInfo> currentCommand;
	private double currentTime;
	private double currentFrame;
	private Rand64 currentSeed;
	private boolean randUsed;
	private double maxNatural;
	private double maxSteps;
	private boolean requestStop;
	private boolean requestFinishUp;
	private boolean requestUpdate;

	public RTI(CFDG cfdg, int width, int height, double minSize, int variation, double border) {
		this.cfdg = cfdg;
		this.width = width;
		this.height = height;
		this.minSize = minSize;
		this.variation = variation;
		this.border = border;
		cfStack = new StackType[8192];
		cfStackSize = 0;
	}

	public ASTCompiledPath getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(ASTCompiledPath currentPath) {
		this.currentPath = currentPath;
	}

	public Iterator<CommandInfo> getCurrentCommand() {
		return currentCommand;
	}

	public void setCurrentCommand(Iterator<CommandInfo> iterator) {
		this.currentCommand = iterator;
	}

	public double getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(double currentTime) {
		this.currentTime = currentTime;
	}

	public double getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(double currentFrame) {
		this.currentFrame = currentFrame;
	}

	public Rand64 getCurrentSeed() {
		return currentSeed;
	}

	public void setCurrentSeed(Rand64 currentSeed) {
		this.currentSeed = currentSeed;
	}

	public boolean isRandUsed() {
		return randUsed;
	}

	public void setRandUsed(boolean randUsed) {
		this.randUsed = randUsed;
	}

	public double getMaxNatural() {
		return maxNatural;
	}

	public void setMaxNatural(double maxNatural) {
		this.maxNatural = maxNatural;
	}

	public boolean isRequestStop() {
		return requestStop;
	}

	public void setRequestStop(boolean requestStop) {
		this.requestStop = requestStop;
	}

	public double getMaxShapes() {
		return maxSteps;
	}

	public void setMaxShapes(double maxSteps) {
		this.maxSteps = maxSteps;
	}

	public Point2D.Double getLastPoint() {
		return lastPoint;
	}

	public void setLastPoint(Point2D.Double lastPoint) {
		this.lastPoint = lastPoint;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean isWantMoveTo() {
		return wantMoveTo;
	}

	public void setWantMoveTo(boolean wantMoveTo) {
		this.wantMoveTo = wantMoveTo;
	}

	public boolean isWantCommand() {
		return wantCommand;
	}

	public void setWantCommand(boolean wantCommand) {
		this.wantCommand = wantCommand;
	}

	public boolean isOpsOnly() {
		return opsOnly;
	}

	public void setOpsOnly(boolean opsOnly) {
		this.opsOnly = opsOnly;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getNextIndex() {
		return nextIndex;
	}

	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}

	public double getMaxSteps() {
		return maxSteps;
	}

	public void setMaxSteps(double maxSteps) {
		this.maxSteps = maxSteps;
	}

	public boolean isRequestFinishUp() {
		return requestFinishUp;
	}

	public void setRequestFinishUp(boolean requestFinishUp) {
		this.requestFinishUp = requestFinishUp;
	}

	public boolean isRequestUpdate() {
		return requestUpdate;
	}

	public void setRequestUpdate(boolean requestUpdate) {
		this.requestUpdate = requestUpdate;
	}

	public void init() {
		lastPoint = new Point2D.Double(0, 0);
		stop = false;
		closed = false;
		wantMoveTo = true;
		wantCommand = true;
		opsOnly = false;
		index = 0;
		nextIndex = 0;
	}

	public boolean isNatual(double n) {
		//TODO rivedere
		return n >= 0 && n <= getMaxNatural() && n == Math.floor(n);
	}

	public void initStack(StackRule parameters) {
		if (parameters != null && parameters.getParamCount() > 0) {
			if (cfStackSize + parameters.getParamCount() > cfStack.length) {
				throw new RuntimeException("Maximum stack size exceeded");
			}
			int oldSize = cfStackSize;
			cfStackSize += parameters.getParamCount();
			parameters.copyParams(cfStack, oldSize);
		}
		setLogicalStackTop(cfStackSize);
	}

	public void unwindStack(int oldSize, List<ASTParameter> parameters) {
		if (oldSize == cfStackSize) {
			return;
		}
		int pos = oldSize;
		for (ASTParameter parameter : parameters) {
			if (pos >= cfStackSize) {
				break;
			}
			if (parameter.isLoopIndex() || parameter.getStackIndex() < 0) {
				continue;
			}
			if (parameter.getType() == EExpType.RuleType) {
				//TODO rivedere
				cfStack[pos].getRule().setParamCount(0);
			}
			pos += parameter.getTupleSize();
		}
		cfStackSize = oldSize;
		cfLogicalStackTop = cfStackSize;
	}

	public void colorConflict(Token location) {
		// TODO rivedere
		warning(location, "Conflicting color change");
	}

	public void processShape(Shape shape) {
		// TODO Auto-generated method stub
	}

	public void processSubpath(Shape shape, boolean tr, ERepElemType repType) {
		// TODO Auto-generated method stub
	}

	public void processPrimShape(Shape shape, ASTRule rule) {
		// TODO Auto-generated method stub
	}

	public void processPathCommand(Shape shape, CommandInfo attr) {
		// TODO Auto-generated method stub
	}

	public StackType stackItem(int stackIndex) {
		return cfStack[stackIndex];
	}

	public void addStackItem(StackType stackType) {
		//TODO rivedere
		cfStack[cfLogicalStackTop] = stackType;
	}

	public void removeStackItem(int index) {
		//TODO rivedere
		cfStack[cfLogicalStackTop] = null;
	}

	public int getStackSize() {
		return cfStackSize;
	}

	public int getLogicalStackTop() {
		return cfLogicalStackTop;
	}

	public void setLogicalStackTop(int cfLogicalStackTop) {
		this.cfLogicalStackTop = cfLogicalStackTop;
	}

	public void initBounds() {
		// TODO Auto-generated method stub
	}

	public void resetBounds() {
		// TODO Auto-generated method stub
	}

	public void resetSize(int width, int height) {
		// TODO Auto-generated method stub
	}

	public void run(Canvas canvas) {
		// TODO Auto-generated method stub
	}

	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
	}

	public void animate(Canvas canvas) {
		// TODO Auto-generated method stub
	}

	private void warning(Token location, String message) {
		//TODO completare
		System.out.println(message);
	}
}
