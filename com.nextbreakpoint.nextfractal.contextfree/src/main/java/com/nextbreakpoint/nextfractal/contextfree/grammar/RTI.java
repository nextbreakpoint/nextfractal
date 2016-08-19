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
import org.antlr.v4.runtime.Token;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.Function;

public class RTI {
	private static final double FIXED_BORDER = 8.0;
	private static final double SHAPE_BORDER = 1.0;

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
	private TiledCanvas tiledCanvas;
	private PathIterator iterator;
	private boolean colorConflict;
	private int maxShapes = 500000000;
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
	private double fixedBorderX;
	private double fixedBorderY;
	private double shapeBorder;
	private double totalArea;
	private double currentArea;
	private double currScale;
	private double currArea;
	private double minArea;
	private double minSize;
	private Bounds bounds;
	private Bounds pathBounds;
	private AffineTransform currTransform;
	private AffineTransformTime timeBounds;
	private AffineTransformTime frameTimeBounds;
	private int outputSoFar;

	private List<CommandInfo> shapeMap = new ArrayList<>();
	private List<AffineTransform> symmetryOps = new ArrayList<>();
	private List<Shape> unfinishedShapes = new ArrayList<>();
	private List<FinishedShape> finishedShapes = new ArrayList<>();

	public RTI(CFDG cfdg, int width, int height, double minSize, int variation, double border) {
		this.cfdg = cfdg;
		this.width = width;
		this.height = height;
		this.minSize = minSize;
		this.variation = variation;
		this.border = border;
		cfStack = new StackType[8192];
		cfStackSize = 0;
		frameTimeBounds = AffineTransformTime.getTranslateInstance(Double.MIN_VALUE, Double.MAX_VALUE);

		//TODO rivedere

		for (PrimShape primShape : PrimShape.getShapeMap().values()) {
			shapeMap.add(new CommandInfo(primShape));
		}

		double[] value = new double[1];
		value[0] = 0;
		cfdg.hasParameter(CFG.FrameTime, value, null);
		currentTime = value[0];
		value[0] = 0;
		cfdg.hasParameter(CFG.Frame, value, null);
		currentFrame = value[0];
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

	public int getMaxShapes() {
		return maxShapes;
	}

	public void setMaxShapes(int maxShapes) {
		this.maxShapes = (maxShapes != 0) ? maxShapes : 400000000;
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

		fixedBorderX = 0;
		fixedBorderY = 0;
		shapeBorder = 0;
		totalArea = 0;
		minArea = 0.3;
		outputSoFar = 0;
		double[] value = new double[1];
		cfdg.hasParameter(CFG.MinimumSize, value, this);
		value[0] = (value[0] <= 0.0) ? 0.3 : value[0];
		minArea = value[0] * value[0];
		fixedBorderX = FIXED_BORDER * ((border <= 1.0) ? border : 1.0);
		shapeBorder = SHAPE_BORDER * ((border <= 1.0) ? 1.0 : border);

		cfdg.hasParameter(CFG.BorderFixed, value, this);
		fixedBorderX = value[0];

		cfdg.hasParameter(CFG.BorderDynamic, value, this);
		shapeBorder = value[0];

		if (2 * (int)Math.abs(fixedBorderX) >= Math.min(width, height)) {
			fixedBorderX = 0;
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
		init();

		double[] vector = new double[2];

		tiled = cfdg.isTiled(null, vector);
		frieze = cfdg.isFrieze(null, vector);
		sized = cfdg.isSized(vector);
		timed = cfdg.isTimed(timeBounds);

		double tileX = vector[0];
		double tileY = vector[1];

		if (tiled || sized) {
			fixedBorderX = shapeBorder = 0.0;
			bounds.setMinX(-tileX / 2.0);
			bounds.setMinY(-tileY / 2.0);
			bounds.setMaxX(tileX / 2.0);
			bounds.setMaxY(tileY / 2.0);
			rescaleOutput(width, height, true);
			scaleArea = currArea;
		}

		if (frieze == FriezeType.FriezeX)
			friezeSize = tileX / 2.0;
		if (frieze == FriezeType.FriezeY)
			friezeSize = tileY / 2.0;
		if (frieze != FriezeType.FriezeY)
			fixedBorderY = fixedBorderX;
		if (frieze == FriezeType.FriezeX)
			fixedBorderX = 0;
	}

	public void resetSize(int x, int y) {
		this.width = x;
		this.height = y;
		if (tiled || sized) {
			currScale = currArea = 0.0;
			rescaleOutput(width, height, true);
			scaleArea = currArea;
		}
	}

	public void cleanup() {
		// TODO rivedere

//		for (FinishedShape shape : finishedShapes) {
//			if (shape.isAbortEverything()) {
//				break;
//			}
//		}

		finishedShapes.clear();
		unfinishedShapes.clear();

		unwindStack(0, cfdg.getContents().getParameters());

		currentPath = null;

		cfdg.resetCachedPaths();
	}

	public void resetBounds() {
		bounds = new Bounds();
	}

	public void run(Canvas canvas) {
		// TODO Auto-generated method stub



	}

	public void draw(Canvas canvas) {
		frameTimeBounds = AffineTransformTime.getTranslateInstance(Double.MIN_VALUE, Double.MAX_VALUE);
		outputPrep(canvas);
		outputFinal();
		outputStats();
	}

	public void animate(Canvas canvas) {
		// TODO Auto-generated method stub
	}

	private void outputPrep(Canvas canvas) {
		this.canvas = canvas;

		if (canvas != null) {
			width = canvas.getWidth();
			height = canvas.getHeight();

			if (tiled || frieze != FriezeType.NoFrieze) {
				AffineTransform transform = new AffineTransform();
				cfdg.isTiled(transform, null);
				cfdg.isFrieze(transform, null);
				tiledCanvas = new TiledCanvas(canvas, transform, frieze);
				tiledCanvas.setScale(currScale);
				canvas = tiledCanvas;
			}

			frameTimeBounds = AffineTransformTime.getTranslateInstance(Double.MIN_VALUE, Double.MAX_VALUE);
		}

		requestStop = false;
		requestFinishUp = false;
		requestUpdate = false;

		// TODO aggiungere stats
	}

	private void outputFinal() {
		output(true);
	}

	private void outputPartial() {
		output(false);
	}

	private void outputStats() {
		// TODO rivedere
		requestUpdate = false;
	}

	private void rescaleOutput(int width, int height, boolean finalStep) {
		if (!bounds.valid()) {
			return;
		}

		int[] currWidth = new int[1];
		int[] currHeight = new int[1];

		AffineTransform transform = new AffineTransform();

		double scale = bounds.computeScale(currWidth, currHeight, fixedBorderX, fixedBorderY, true, transform, tiled || sized || frieze != FriezeType.NoFrieze);

		//TODO rivedere

		if (finalStep || currScale == 0.0 || currScale * 0.9 > scale) {
			currScale = scale;
			currArea = scale * scale;
			if (tiledCanvas != null) {
				tiledCanvas.setScale(scale);
			}
			currTransform = transform;
			outputSoFar = 0;
		}
	}

	private void output(boolean finalStep) {
		if (canvas == null) {
			return;
		}

		if (!finalStep) {
			return;
		}

		this.finalStep = finalStep;

		int currWidth = width;
		int currHeight = height;

		rescaleOutput(currWidth, currHeight, finalStep);

		if (finalStep) {
			if (finishedShapes.size() > 10000) {
				info("Sorting shapes...");
			}
			Collections.sort(finishedShapes);
		}

		//TODO rivedere

		canvas.start(outputSoFar == 0, cfdg.getBackgroundColor(null), currWidth, currHeight);

		try {
			forEachShape(finalStep, shape -> {
				drawShape(shape);
				return null;
			});
		} catch (StopException e) {
		} catch (Exception e) {
			error(e.getMessage());
		}

		canvas.end();
	}

	private Object forEachShape(boolean finalStep, Function<FinishedShape, Object> shapeFunction) {
		if (!finalStep) {
			finishedShapes.stream().forEach(shape -> shapeFunction.apply(shape));
			outputSoFar = finishedShapes.size();
		} else {
			//TODO rivedere
			OutputMerge merger = new OutputMerge();
			merger.addShapes(finishedShapes);
			merger.merge(shapeFunction);
		}
		return null;
	}

	private void drawShape(FinishedShape shape) {
		if (requestStop) {
			throw new StopException();
		}

		if (!finalStep && requestFinishUp) {
			throw new StopException();
		}

		if (requestUpdate) {
			outputStats();
		}

		if (!shape.getWorldState().getTransformTime().overlaps(frameTimeBounds)) {
			return;
		}

		AffineTransform transform = shape.getWorldState().getTransform();

		transform.preConcatenate(currTransform);

		double a = shape.getWorldState().getTransformZ().sz() + currArea;

		if (!Double.isFinite(a) && shape.getShapeType() != PrimShapeType.fillType.getType()) {
			Bounds b = shape.bounds();
			Point2D.Double p1 = new Point2D.Double(b.getMinX(), b.getMinY());
			currTransform.transform(p1, p1);
			b.setMinX(p1.getX());
			b.setMinY(p1.getY());
			Point2D.Double p2 = new Point2D.Double(b.getMaxX(), b.getMaxY());
			currTransform.transform(p2, p2);
			b.setMaxX(p2.getX());
			b.setMaxY(p2.getY());
			tiledCanvas.tileTransform(b);
		}

		if (cfdg.getShapeType(shape.getShapeType()) == ShapeClass.PathType) {
			ASTRule rule = cfdg.findRule(shape.getShapeType());
			rule.traversePath(shape, this);
		} else {
			double[] color = cfdg.getColor(shape.getWorldState().color());
			if (PrimShape.isPrimShape(shape.getShapeType())) {
				canvas.primitive(shape.getShapeType(), color, transform);
			} else {
				error("Non drawable shape with no rules: " + cfdg.decodeShapeName(shape.getShapeType()));
				requestStop = true;
				throw new StopException();
			}
		}
	}

	private void info(String message) {
		//TODO completare
		System.out.println(message);
	}

	private void error(String message) {
		//TODO completare
		System.out.println(message);
	}

	private void warning(Token location, String message) {
		//TODO completare
		System.out.println(message);
	}
}
