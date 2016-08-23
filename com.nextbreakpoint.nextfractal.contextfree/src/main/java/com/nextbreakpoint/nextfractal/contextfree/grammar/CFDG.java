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
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.*;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.*;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.*;

public class CFDG {
	private double[] backgroundColor;
	private Shape initialShape;
	private ASTRule needle;
	private ASTReplacement initShape;
	private ASTRepContainer cfdgContents;
	private List<ShapeType> shapeTypes = new ArrayList<ShapeType>();
	private Stack<ASTRule> rules = new Stack<ASTRule>();
	private Map<Integer, ASTDefine> functions = new HashMap<Integer, ASTDefine>();
	private Map<CFG, Integer> paramDepth = new HashMap<CFG, Integer>();
	private Map<CFG, ASTExpression> paramExp = new HashMap<CFG, ASTExpression>();
	private Modification tileMod;
	private Modification sizeMod;
	private Modification timeMod;
	private Point2D tileOffset;
	private int parameters;
	private int stackSize;
	private boolean useAlpha;
	private boolean usesColor;
	private boolean usesTime;
	private boolean usesFrameTime;
	private boolean uses16bitColor;
	private CFDGDriver cfdgDriver;

	public CFDG(CFDGDriver cfdgDriver) {
		this.cfdgDriver = cfdgDriver;
	}

	public CFDGDriver getDriver() {
		return cfdgDriver;
	}

	public Shape getInitialShape(RTI rti) {
		Shape shape = new Shape();
		shape.worldState.setColor(new HSBColor(0,0,0,1));
		shape.worldState.setColorTarget(new HSBColor(0,0,0,1));
		shape.worldState.getTransformTime().setEnd(1);
		initShape.replace(shape, rti);
		shape.worldState.getTransform().translate(tileOffset.getX(), tileOffset.getY());
		initialShape = shape;
		return initialShape;
	}

	public ASTRepContainer getContents() {
		return cfdgContents;
	}

	public double[] getBackgroundColor(RTI rti) {
		return backgroundColor;
	}

	public void setBackgroundColor(RTI rti) {
		Modification white = new Modification();
		white.setColor(new HSBColor(0.0, 0.0, 1.0, 1.0));
		if (hasParameter(CFG.Background, white, rti)) {
			white.color().getRGBA(backgroundColor);
			if (!useAlpha) {
				backgroundColor[3] = 1.0;
			}
		}
	}

	public ASTRule findRule(int nameIndex) {
		for (ASTRule rule: rules) {
			if (rule.getNameIndex() == nameIndex) {
				return rule;
			}
		}
		return null;
	}

	public ASTRule findRule(int nameIndex, double weight) {
		needle.setNameIndex(nameIndex);
		needle.setWeight(weight);
		int first = lowerBound(rules, 0, rules.size() - 1, needle);
		if (first == rules.size() || rules.get(first).getNameIndex() != nameIndex) {
			Logger.fail("Cannot find a rule for a shape (very helpful I know)", null);
		}
		return rules.get(first);
	}

	private int lowerBound(List<ASTRule> rules, int first, int last, ASTRule val) {
		// TODO controllare
		int count = last - first;
		while (count > 0) {
			int step = count / 2;
			int offset = first + step;
			if (rules.get(offset).compareTo(val) < 0) {
				first = first + 1;
				count -= step + 1;
			} else {
				count = step;
			}
		}
		return first;
	}

	public boolean addRule(ASTRule rule) {
		rules.push(rule);
		ShapeType type = shapeTypes.get(rule.getNameIndex());
		if (type.getShapeType() == ShapeClass.NewShape) {
			type.setShapeType(rule.isPath() ? ShapeClass.PathType : ShapeClass.RuleType);
		}
		if (type.getParameters() != null && !type.getParameters().isEmpty()) {
			rule.getRuleBody().getParameters().clear();
			rule.getRuleBody().getParameters().addAll(type.getParameters());
		}
		type.setHasRules(true);
		return type.isShape();
	}

	public void addParameter(Param param) {
		parameters |= param.getType();
		usesColor = (parameters & Param.Color.getType()) != 0;
		usesTime = (parameters & Param.Time.getType()) != 0;
		usesFrameTime = (parameters & Param.FrameTime.getType()) != 0;
	}

	public double[] getColor(HSBColor hsb) {
		double[] c = new double[4];
		hsb.getRGBA(c);
		if (uses16bitColor) {
			return c; //TODO completare
		} else {
			return c;
		}
	}

	public boolean isTiled(AffineTransform transform, double[] point) {
		//TODO completare con location
		if (!hasParameter(CFG.Tile, ExpType.ModType)) {
			return false;
		}

		if (tileMod.getTransform().getScaleX() == 0 || tileMod.getTransform().getScaleY() == 0) {
			return false;
		}

		if (transform != null) {
			transform.setTransform(new AffineTransform(tileMod.getTransform().getScaleX(), tileMod.getTransform().getShearY(), tileMod.getTransform().getShearX(), tileMod.getTransform().getScaleY(), 0, 0));
		}

		if (point != null && point.length == 2) {
			double o_x = 0.0;
			double o_y = 0.0;
			double u_x = 1.0;
			double u_y = 0.0;
			double v_x = 0.0;
			double v_y = 1.0;

			Point2D o = new Point2D.Double(o_x, o_y);
			Point2D u = new Point2D.Double(u_x, u_y);
			Point2D v = new Point2D.Double(v_x, v_y);

			tileMod.getTransform().transform(o, o);
			tileMod.getTransform().transform(u, u);
			tileMod.getTransform().transform(v, v);

			if (Math.abs(u_y - o_y) >= 0.0001 && Math.abs(v_x - o_x) >= 0.0001) {
				Logger.fail("Tile must be aligned with the X or Y axis", null);
			}

			if (Math.abs(u_x - o_x) < 0.0 || Math.abs(v_y - o_y) < 0.0) {
				Logger.fail("Tile must be in the positive X/Y quadrant", null);
			}

			point[0] = u_x - o_x;
			point[1] = u_y - o_y;
		}

		return true;
	}

	public FriezeType isFrieze(AffineTransform transform, double[] point) {
		//TODO completare con location
		if (!hasParameter(CFG.Tile, ExpType.ModType)) {
			return FriezeType.NoFrieze;
		}

		if (tileMod.getTransform().getScaleX() != 0 && tileMod.getTransform().getScaleY() != 0) {
			return FriezeType.NoFrieze;
		}

		if (tileMod.getTransform().getScaleX() == 0 && tileMod.getTransform().getScaleY() == 0) {
			return FriezeType.NoFrieze;
		}

		if (transform != null) {
			transform.setTransform(new AffineTransform(tileMod.getTransform().getScaleX(), tileMod.getTransform().getShearY(), tileMod.getTransform().getShearX(), tileMod.getTransform().getScaleY(), 0, 0));
		}

		if (point != null && point.length == 2) {
			double o_x = 0.0;
			double o_y = 0.0;
			double u_x = 1.0;
			double u_y = 0.0;
			double v_x = 0.0;
			double v_y = 1.0;

			Point2D o = new Point2D.Double(o_x, o_y);
			Point2D u = new Point2D.Double(u_x, u_y);
			Point2D v = new Point2D.Double(v_x, v_y);

			tileMod.getTransform().transform(o, o);
			tileMod.getTransform().transform(u, u);
			tileMod.getTransform().transform(v, v);

			if (Math.abs(u_y - o_y) >= 0.0001 || Math.abs(v_x - o_x) >= 0.0001) {
				Logger.fail("Frieze must be aligned with the X and Y axis", null);
			}

			if (Math.abs(u_x - o_x) < 0.0 || Math.abs(v_y - o_y) < 0.0) {
				Logger.fail("Frieze must be in the positive X/Y quadrant", null);
			}

			point[0] = u_x - o_x;
			point[1] = u_y - o_y;
		}

		return tileMod.getTransform().getScaleX() == 0.0 ? FriezeType.FriezeY : FriezeType.FriezeX;
	}

	public boolean isSized(double[] point) {
		//TODO completare con location
		if (!hasParameter(CFG.Size, ExpType.ModType)) {
			return false;
		}

		//TODO da rivedere
		if (point != null) {
			point[0] = sizeMod.getTransform().getScaleX();
			point[1] = sizeMod.getTransform().getScaleY();
		}

		if (sizeMod.getTransform().getShearX() != 0.0 || sizeMod.getTransform().getShearY() != 0.0) {
			Logger.fail("Size specification must not be rotated or skewed", null);
		}

		return false;
	}

	public boolean isTimed(AffineTransformTime transform) {
		//TODO completare con location
		if (!hasParameter(CFG.Time, ExpType.ModType)) {
			return false;
		}

		//TODO da rivedere
		if (transform != null) {
			transform.setBegin(timeMod.getTransformTime().getBegin());
			transform.setEnd(timeMod.getTransformTime().getEnd());
			transform.setStep(timeMod.getTransformTime().getStep());
		}

		if (sizeMod.getTransformTime().getBegin() >= sizeMod.getTransformTime().getEnd()) {
			Logger.fail("Time specification must have positive duration", null);
		}

		return false;
	}

	public void getSummetry(SymmList syms, RTI rti) {
		// TODO controllare
		syms.clear();
		ASTExpression exp = hasParameter(CFG.Symmetry);
		List<ASTModification> left = AST.getTransforms(exp, syms, rti, isTiled(null, null), tileMod.getTransform());
		if (!left.isEmpty()) {
			Logger.fail("At least one term was invalid", exp.getLocation());
		}
	}

	public boolean hasParameter(CFG p, double[] value, RTI rti) {
		ASTExpression exp = hasParameter(p);
		if (exp == null || exp.getType() != ExpType.NumericType) {
			return false;
		}
		if (!exp.isConstant() && rti != null) {
			Logger.fail("This expression must be constant", exp.getLocation());
			return false;
		} else {
			exp.evaluate(value, 1, rti);
		}
		return true;
	}

	public boolean hasParameter(CFG p, Modification value, RTI rti) {
		ASTExpression exp = hasParameter(p);
		if (exp == null || exp.getType() != ExpType.ModType) {
			return false;
		}
		if (!exp.isConstant() && rti != null) {
			Logger.fail("This expression must be constant", exp.getLocation());
			return false;
		} else {
			exp.evaluate(value, true, rti);//TODO controllare
		}
		return true;
	}

	public boolean hasParameter(CFG p, ExpType type) {
		ASTExpression exp = hasParameter(p);
		if (exp == null || exp.getType() != type) {
			return false;
		}
		return true;
	}

	public ASTExpression hasParameter(CFG p) {
		if (paramDepth.get(p).intValue() == -1) {
			return null;
		}
		return paramExp.get(p);
	}

	public boolean addParameter(String name, ASTExpression exp, int depth) {
		CFG p = CFG.valueOf(name);
		if (p == null) {
			return false;
		}
		if (depth < paramDepth.get(p)) {
			paramDepth.put(p, depth);
			paramExp.put(p, exp);
		}
		return true;
	}

	public void rulesLoaded(CFDGDriver driver) {
		//TODO rivedere

		double[] weightsums = new double[shapeTypes.size()];
		double[] percentweightsums = new double[shapeTypes.size()];
		double[] unitweightsums = new double[shapeTypes.size()];
		int[] rulecounts = new int[shapeTypes.size()];
		int[] weightTypes = new int[shapeTypes.size()];

		for (ASTRule rule : rules) {
			if (rule.getWeightType() == WeightType.PercentWeight) {
				percentweightsums[rule.getNameIndex()] += rule.getWeight();
				if (percentweightsums[rule.getNameIndex()] > 1.0001) {
					Logger.fail("Percentages exceed 100%", rule.getLocation());
				}
			} else {
				weightsums[rule.getNameIndex()] += rule.getWeight();
			}
			rulecounts[rule.getNameIndex()] += 1;
			weightTypes[rule.getNameIndex()] |= rule.getWeightType().getType();
		}

		for (ASTRule rule : rules) {
			double weight = rule.getWeight() / weightsums[rule.getNameIndex()];
			if ((weightTypes[rule.getNameIndex()] & WeightType.PercentWeight.getType()) != 0) {
				if (rule.getWeightType() == WeightType.PercentWeight) {
					weight = rule.getWeight();
				} else {
					weight *= 1.0 - percentweightsums[rule.getNameIndex()];
					if (percentweightsums[rule.getNameIndex()] > 0.9999) {
						Logger.warning("Percentages sum to 100%, this rule has no weight", rule.getLocation());
					}
				}
			}
			if (weightTypes[rule.getNameIndex()] == WeightType.PercentWeight.getType() && Math.abs(percentweightsums[rule.getNameIndex()] - 1.0) > 0.0001) {
				Logger.warning("Percentages do not sum to 100%", rule.getLocation());
			}
			if (!Double.isFinite(weight)) {
				weight = 0;
			}
			unitweightsums[rule.getNameIndex()] += weight;
			if (rulecounts[rule.getNameIndex()] - 1 > 0) {
				rule.setWeight(unitweightsums[rule.getNameIndex()]);
			} else {
				rule.setWeight(1.1);
			}
		}

		Collections.sort(rules);

		driver.setLocalStackDepth(0);

		cfdgContents.compile(CompilePhase.TypeCheck, null, null);

		if (driver.errorOccured()) {
			cfdgContents.compile(CompilePhase.Simplify, null, null);
		}

		double[] value = new double[1];
		uses16bitColor = hasParameter(CFG.ColorDepth, value, null) && Math.floor(value[0]) == 16;

		if (hasParameter(CFG.Color, value, null)) {
			usesColor = value[0] != 0;
		}

		if (hasParameter(CFG.Alpha, value, null)) {
			usesColor = value[0] != 0;
		}

		ASTExpression e = hasParameter(CFG.Background);
		if (e != null && e instanceof ASTModification) {
			ASTModification m = (ASTModification) e;
			useAlpha = m.getModData().color().alpha() != 1.0;
			for (ASTModTerm term : m.getModExp()) {
				if (term.getModType() == ModType.alpha || term.getModType() == ModType.alphaTarg) {
					useAlpha = true;
				}
			}
		}
	}

	public int numRules() {
		return rules.size();
	}

	public String decodeShapeName(int shape) {
		if (shape < shapeTypes.size()) {
			return shapeTypes.get(shape).getName();
		} else {
			return "**unnamed shape**";
		}
	}

	public int tryEncodeShapeName(String s) {
	    for (int i = 0; i < shapeTypes.size(); i++) {
	        if (s.equals(shapeTypes.get(i).getName())) {
	            return i;
	        }
	    }
	    return -1;
	}

	public int encodeShapeName(String s) {
		int i = tryEncodeShapeName(s);
		if (i >= 0) return i;
		shapeTypes.add(new ShapeType(s));
		return shapeTypes.size() - 1;
	}

	public ShapeClass getShapeType(int nameIndex) {
		return shapeTypes.get(nameIndex).getShapeType();
	}

	public boolean shapeHasRules(int nameIndex) {
		if (nameIndex < shapeTypes.size()) {
			return shapeTypes.get(nameIndex).hasRules();
		}
		return false;
	}

	public void setShapeHasNoParam(int nameIndex, ASTExpression args) {
		if (nameIndex < shapeTypes.size() && args != null) {
			shapeTypes.get(nameIndex).setShouldHaveNoParams(true);
		}
	}

	public boolean getShapeHasNoParam(int nameIndex) {
		if (nameIndex < shapeTypes.size()) {
			return shapeTypes.get(nameIndex).isShouldHaveNoParams();
		}
		return false;
	}

	public String setShapeParams(int nameIndex, ASTRepContainer p, int argSize, boolean isPath) {
		ShapeType type = shapeTypes.get(nameIndex);
		if (type.isShape()) {
			if (p.getParameters().isEmpty()) {
				return "Shape has already been declared. Parameter declaration must be on the first shape declaration only";
			}
			if (type.getShapeType() == ShapeClass.PathType && !isPath) {
				return "Shape name already in use by another rule or path";
			}
			if (isPath) {
				return "Path name already in use by another rule or path";
			}
			return null;
		}
		if (type.getShapeType() != ShapeClass.NewShape) {
			return "Shape name already in use by another rule or path";
		}
		type.getParameters().clear();
		type.getParameters().addAll(p.getParameters());
		type.setIsShape(true);
		type.setArgSize(argSize);
		type.setShapeType(isPath ? ShapeClass.PathType : ShapeClass.NewShape);
		return null;
	}

	public List<ASTParameter> getShapeParams(int nameIndex) {
		if (nameIndex < 0 || nameIndex >= shapeTypes.size() || !shapeTypes.get(nameIndex).isShape()) {
			return null;
		}
		return shapeTypes.get(nameIndex).getParameters();
	}

	public int getShapeParamsSize(int nameIndex) {
		if (nameIndex < 0 || nameIndex >= shapeTypes.size()) {
			return 0;
		}
		return shapeTypes.get(nameIndex).getArgSize();
	}

	public int reportStackDepth(int size) {
		if (size > stackSize) {
			stackSize = size;
		}
		return stackSize;
	}

	public void resetCachedPaths() {
		for (ASTRule rule : rules) {
			rule.resetCachedPath();
		}
	}

	public ASTDefine declareFunction(int nameIndex, ASTDefine def) {
		ASTDefine prev = findFunction(nameIndex);
		if (prev != null) {
			return prev;
		}
		functions.put(nameIndex, def);
		return def;
	}

	public ASTDefine findFunction(int index) {
		ASTDefine def = functions.get(index);
		if (def != null) {
			return def;
		}
		return null;
	}

	public RTI renderer(CFDGDriver driver, int width, int height, double minSize, int variation, double border) {
		try {
			ASTExpression startExp = paramExp.get(CFG.StartShape);

			if (startExp == null) {
				Logger.fail("No startshape found", null);
				return null;
			}

			if (startExp instanceof ASTStartSpecifier) {
				ASTStartSpecifier specStart = (ASTStartSpecifier)startExp;
				initShape = new ASTReplacement(driver, specStart, specStart.getModification(), startExp.getLocation());
				initShape.getChildChange().addEntropy(initShape.getShapeSpecifier().getEntropy());
			} else {
				Logger.fail("Type error in startshape", startExp.getLocation());
				return null;
			}

			RTI rti = new RTI(this, width, height, minSize, variation, border);

			Modification tiled = null;
			Modification sized = null;
			Modification timed = null;

			//TODO rivedere

			double[] maxShape = new double[0];

			if (hasParameter(CFG.Tile, tiled, null)) {
				tileMod = tiled;
				AffineTransform transform = tileMod.getTransform();
				tileOffset.setLocation(transform.getTranslateX(), transform.getTranslateY());
				AffineTransform t = new AffineTransform(transform.getScaleX(), transform.getShearY(), transform.getShearX(), transform.getScaleY(), 0, 0);
				tileMod.setTransform(t);
			}

			if (hasParameter(CFG.Size, sized, null)) {
				sizeMod = sized;
				AffineTransform transform = sizeMod.getTransform();
				tileOffset.setLocation(transform.getTranslateX(), transform.getTranslateY());
				AffineTransform t = new AffineTransform(transform.getScaleX(), transform.getShearY(), transform.getShearX(), transform.getScaleY(), 0, 0);
				tileMod.setTransform(t);
			}

			if (hasParameter(CFG.Time, timed, null)) {
				timeMod = timed;
			}

			if (hasParameter(CFG.MaxShapes, maxShape, null)) {
				if (maxShape[0] > 1) {
					rti.setMaxShapes((int)maxShape[0]);
				}
			}

			rti.initBounds();

			return rti;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// TODO da rivedere

	public void compile(CompilePhase ph) {
		for (ASTRule rule : rules) {
			rule.compile(ph);
		}
	}

	public void traverse(Shape shape, boolean tr, RTI rti) {
		ASTRule rule = rules.iterator().next();
		rule.traverse(shape, tr, rti);
	}

	public boolean usesTime() {
		return usesTime;
	}

	public boolean usesFrameTime() {
		return usesFrameTime;
	}
}