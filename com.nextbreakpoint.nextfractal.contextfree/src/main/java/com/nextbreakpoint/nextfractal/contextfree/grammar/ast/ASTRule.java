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
package com.nextbreakpoint.nextfractal.contextfree.grammar.ast;

import java.awt.geom.PathIterator;

import com.nextbreakpoint.nextfractal.contextfree.grammar.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.PrimShapeType;
import org.antlr.v4.runtime.Token;

public class ASTRule extends ASTReplacement implements Comparable<ASTRule> {
	private ASTRepContainer ruleBody;
	private ASTCompiledPath cachedPath;
	private double weight;
	private boolean isPath;
	private int nameIndex;
	private WeightType weightType;
	
	public ASTRule(CFDGDriver driver, int nameIndex, float weight, boolean percent, Token location) {
		super(driver, null, RepElemType.rule, location);
		ruleBody = new ASTRepContainer(driver);
		this.nameIndex = nameIndex;
		this.isPath = false;
		this.weight = weight <= 0.0 ? 1.0f : weight;
		this.weightType = percent ? WeightType.PercentWeight : WeightType.ExplicitWeight;
		this.cachedPath = null;
	}

	public ASTRule(CFDGDriver driver, int nameIndex, Token location) {
		super(driver, null, RepElemType.rule, location);
		ruleBody = new ASTRepContainer(driver);
		this.nameIndex = nameIndex;
		this.isPath = false;
		this.weight = 1.0f;
		this.weightType = WeightType.NoWeight;
		this.cachedPath = null;
	}

	protected ASTRule(CFDGDriver driver, int nameIndex, Token location, boolean dummy) {
		super(driver, null, RepElemType.rule, location);
		ruleBody = new ASTRepContainer(driver);
		this.nameIndex = nameIndex;
		this.isPath = true;
		this.weight = 1.0f;
		this.weightType = WeightType.NoWeight;
		this.cachedPath = null;
		if (nameIndex != PrimShapeType.circleType.getType()) {
			com.nextbreakpoint.nextfractal.contextfree.grammar.PrimShape shape = com.nextbreakpoint.nextfractal.contextfree.grammar.PrimShape.getShapeMap().get(nameIndex);
			double[] coords = new double[6];
			int cmd;
			PathIterator iterator = shape.getPathIterator();
			while (!isStop(cmd = iterator.currentSegment(coords))) {
				if (isVertex(cmd)) {
					ASTExpression a = new ASTCons(location, new ASTReal(coords[0], location), new ASTReal(coords[1], location));
					ASTPathOp op = new ASTPathOp(driver, isMoveTo(cmd) ? PathOp.MOVETO.name() : PathOp.LINETO.name(), a, location);
					getRuleBody().getBody().add(op);
				}
			}
		} else {
			ASTExpression a = new ASTCons(location, new ASTReal(0.5, location), new ASTReal(0.0, location));
			ASTPathOp op = new ASTPathOp(driver, PathOp.MOVETO.name(), a, location);
			getRuleBody().getBody().add(op);
			a = new ASTCons(location, new ASTReal(-0.5, location), new ASTReal(0.0, location), new ASTReal(0.5, location));
			op = new ASTPathOp(driver, PathOp.ARCTO.name(), a, location);
			getRuleBody().getBody().add(op);
			a = new ASTCons(location, new ASTReal(0.5, location), new ASTReal(0.0, location), new ASTReal(0.5, location));
			op = new ASTPathOp(driver, PathOp.ARCTO.name(), a, location);
			getRuleBody().getBody().add(op);
		}
		getRuleBody().getBody().add(new ASTPathOp(driver, PathOp.CLOSEPOLY.name(), null, location));
		getRuleBody().setRepType(RepElemType.op.getType());
		getRuleBody().setPathOp(PathOp.MOVETO);
	}
	
	private boolean isMoveTo(int cmd) {
		return cmd == PathIterator.SEG_MOVETO;
	}

	private boolean isVertex(int cmd) {
		return cmd >= PathIterator.SEG_MOVETO && cmd < PathIterator.SEG_CLOSE;
	}

	private boolean isStop(int cmd) {
		return cmd == PathIterator.SEG_CLOSE;
	}

	public ASTRepContainer getRuleBody() {
		return ruleBody;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public WeightType getWeightType() {
		return weightType;
	}
	
	public boolean isPath() {
		return isPath;
	}
	
	public void setPath(boolean isPath) {
		this.isPath = isPath;
	}

	public int getNameIndex() {
		return nameIndex;
	}

	public void setNameIndex(int nameIndex) {
		this.nameIndex = nameIndex;
	}

	@Override
	public void compile(CompilePhase ph) {
		driver.setInPathContainer(isPath);
		super.compile(ph);
		ruleBody.compile(ph, null, null);
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		rti.setCurrentSeed(parent.getWorldState().getRand64Seed());
		if (isPath) {
			rti.processPrimShape(parent, this);
		} else {
			ruleBody.traverse(parent, tr, rti, true);
			parent.releaseParams();
		}
	}
	
	public void traversePath(Shape parent, RTI rti) {
		rti.init();
		rti.setCurrentSeed(parent.getWorldState().getRand64Seed());
		rti.setRandUsed(false);
		
		ASTCompiledPath savedPath = null;
		
		if (cachedPath != null && cachedPath.getParameters().equals(parent.getParameters())) {
			savedPath = rti.getCurrentPath();
			rti.setCurrentPath(cachedPath);
			rti.setCurrentCommand(cachedPath.getCommandInfo().iterator());
		}
		
		ruleBody.traverse(parent, false, rti, true);
		if (!rti.getCurrentPath().isComplete()) {
			rti.getCurrentPath().finish(true, rti);
		}
		if (rti.getCurrentPath().useTerminal()) {
			rti.getCurrentPath().getTerminalCommand().traverse(parent, false, rti);
		}
		
		if (savedPath != null) {
			rti.setCurrentPath(savedPath);
		} else {
			if (rti.isRandUsed() && cachedPath == null) {
				cachedPath = rti.getCurrentPath();
				cachedPath.setIsComplete(true);
				cachedPath.setParameters(new StackRule(parent.getParameters()));
				rti.setCurrentPath(new ASTCompiledPath(driver, getLocation()));
			} else {
				rti.getCurrentPath().getPath().clear();
				rti.getCurrentPath().getCommandInfo().clear();
				rti.getCurrentPath().setUseTerminal(false);
				rti.getCurrentPath().setPathUID(ASTCompiledPath.nextPathUID());
				if (rti.getCurrentPath().getParameters() != null) {
					rti.getCurrentPath().setParameters(null);
				}
			}
		}
	}

	@Override
	public int compareTo(ASTRule o) {
		return nameIndex == o.nameIndex ? (weight < o.weight ? -1 : weight == o.weight ? 0 : 1) : nameIndex - o.nameIndex;
	}

	public ASTCompiledPath getCachedPath() {
		return cachedPath;
	}

	public void setCachedPath(ASTCompiledPath cachedPath) {
		this.cachedPath = cachedPath;
	}

	public void resetCachedPath() {
		this.cachedPath = null;
	}
}
