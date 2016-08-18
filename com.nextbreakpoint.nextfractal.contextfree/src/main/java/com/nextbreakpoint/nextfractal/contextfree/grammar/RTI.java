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

import com.nextbreakpoint.nextfractal.contextfree.core.AffineTransformTime;
import com.nextbreakpoint.nextfractal.contextfree.core.Bounds;
import com.nextbreakpoint.nextfractal.contextfree.core.Rand64;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.PrimShape;
import org.antlr.v4.runtime.Token;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.*;

public class RTI {
	private static final double FIXED_BORDER = 1;
	private static final double SHAPE_BORDER = 1;

	private int width;
	private int height;

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

	private CFDG cfdg;
	private Canvas canvas;
	private PathIterator iterator;
	private boolean colorConflict;
	private int maxShapes;
	private boolean tiled;
	private boolean sized;
	private boolean timed;
	private FriezeType frieze;
	private double friezeSize;
	private boolean drawingMode;
	private boolean finalStep;

	private int variation;
	private double border;

	private double scaleArea;
	private double scale;
	private double fixedBoderX;
	private double fixedBoderY;
	private double shapeBorder;
	private double totalArea;
	private double currentArea;
	private double currScale;
	private double currArea;
	private double minArea;
	private double minSize;
	private Bounds pathBounds;
	private Bounds bounds;
	private AffineTransformTime timeBounds;
	private AffineTransformTime frameTimeBounds;
	private AffineTransform currTransform;
	private int outputSoFar;
	private List<AffineTransform> symetryOps;

	private List<FinishedShape> finishedShapes = new ArrayList<>();
	private List<Shape> unfinishedShapes = new ArrayList<>();

	private Map<CommandInfo, PrimShape> shapeMap = new HashMap<>();

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

		currentSeed.setSeed(variation);
		currentSeed.bump();

		cfLogicalStackTop = 0;
		cfStackSize = 0;

		for (ASTReplacement rep : cfdg.getContents().getBody()) {
			if (rep instanceof ASTDefine) {
				ASTDefine def = (ASTDefine) rep;
				def.traverse(new Shape(), false, this);
			}
		}

		fixedBoderX = 0;
		fixedBoderY = 0;
		shapeBorder = 0;
		totalArea = 0;
		minArea = 0.3;
		outputSoFar = 0;
		double[] value = new double[1];
		cfdg.hasParameter(CFG.MinimumSize, value, this);
		value[0] = (value[0] <= 0.0) ? 0.3 : value[0];
		minArea = value[0] * value[0];
		fixedBoderX = FIXED_BORDER * ((border <= 1.0) ? border : 1.0);
		shapeBorder = SHAPE_BORDER * ((border <= 1.0) ? 1.0 : border);

		cfdg.hasParameter(CFG.BorderFixed, value, this);
		fixedBoderX = value[0];

		cfdg.hasParameter(CFG.BorderDynamic, value, this);
		shapeBorder = value[0];

		if (2 * (int)Math.abs(fixedBoderX) >= Math.min(width, height)) {
			fixedBoderX = 0;
		}

		if (shapeBorder <= 0.0) {
			shapeBorder = 1.0;
		}

		if (cfdg.hasParameter(CFG.MaxNatural, value, this) && (value[0] < 1.0 || (value[0] - 1.0) == value[0]))
		{
        	ASTExpression max = cfdg.hasParameter(CFG.MaxNatural);
			//TODO rivedere
			throw new RuntimeException((value[0] < 1.0) ? "CF::MaxNatural must be >= 1" : "CF::MaxNatural must be < 9007199254740992");
		}

		currentPath = new ASTCompiledPath(cfdg.getDriver(), null);

		cfdg.getSymmetry(symmetryOps, this);
		cfdg.setBackgroundColor(this);
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
			if (parameter.getType() == ExpType.RuleType) {
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
		if (colorConflict) {
			return;
		}
		colorConflict = true;
		warning(location, "Conflicting color change");
	}

	public void processShape(Shape shape) {
		// TODO Auto-generated method stub
	}

	public void processSubpath(Shape shape, boolean tr, RepElemType repType) {
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
