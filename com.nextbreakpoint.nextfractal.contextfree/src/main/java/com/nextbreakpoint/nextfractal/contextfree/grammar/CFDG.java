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

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.*;

public class CFDG {
	private static final double SQRT2 = Math.sqrt(2.0);
	private double[] backgroundColor;
	private Shape initialShape;
	private ASTRule needle;
	private ASTReplacement initShape;
	private ASTRepContainer cfdgContents;
	private List<ShapeType> shapeTypes = new ArrayList<ShapeType>();
	private Stack<ASTRule> rules = new Stack<ASTRule>();
	private Map<Integer, ASTDefine> functions = new HashMap<Integer, ASTDefine>();
	private Map<ECFGParam, Integer> paramDepth = new HashMap<ECFGParam, Integer>();
	private Map<ECFGParam, ASTExpression> paramExp = new HashMap<ECFGParam, ASTExpression>();
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

	public double[] getBackgroundColor(RTI rti) {
		return backgroundColor;
	}

	public void setBackgroundColor(RTI rti) {
		Modification white = new Modification();
		white.setColor(new HSBColor(0.0, 0.0, 1.0, 1.0));
		if (hasParameter(ECFGParam.Background, white, rti)) {
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
			error("Cannot find a rule for a shape (very helpful I know)");
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
		if (type.getShapeType() == EShapeType.NewShape) {
			type.setShapeType(rule.isPath() ? EShapeType.PathType : EShapeType.RuleType);
		}
		if (type.getParameters() != null && !type.getParameters().isEmpty()) {
			rule.getRuleBody().getParameters().clear();
			rule.getRuleBody().getParameters().addAll(type.getParameters());
		}
		type.setHasRules(true);
		return type.isShape();
	}

	public void addParameter(EParam param) {
		parameters |= param.getType();
		usesColor = (parameters & EParam.Color.getType()) != 0;
		usesTime = (parameters & EParam.Time.getType()) != 0;
		usesFrameTime = (parameters & EParam.FrameTime.getType()) != 0;
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
		if (!hasParameter(ECFGParam.Tile, EExpType.ModType)) {
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
				error("Tile must be aligned with the X or Y axis");
			}

			if (Math.abs(u_x - o_x) < 0.0 || Math.abs(v_y - o_y) < 0.0) {
				error("Tile must be in the positive X/Y quadrant");
			}

			point[0] = u_x - o_x;
			point[1] = u_y - o_y;
		}

		return true;
	}

	public EFriezeType isFrieze(AffineTransform transform, double[] point) {
		//TODO completare con location
		if (!hasParameter(ECFGParam.Tile, EExpType.ModType)) {
			return EFriezeType.NoFrieze;
		}

		if (tileMod.getTransform().getScaleX() != 0 && tileMod.getTransform().getScaleY() != 0) {
			return EFriezeType.NoFrieze;
		}

		if (tileMod.getTransform().getScaleX() == 0 && tileMod.getTransform().getScaleY() == 0) {
			return EFriezeType.NoFrieze;
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
				error("Frieze must be aligned with the X and Y axis");
			}

			if (Math.abs(u_x - o_x) < 0.0 || Math.abs(v_y - o_y) < 0.0) {
				error("Frieze must be in the positive X/Y quadrant");
			}

			point[0] = u_x - o_x;
			point[1] = u_y - o_y;
		}

		return tileMod.getTransform().getScaleX() == 0.0 ? EFriezeType.FriezeY : EFriezeType.FriezeX;
	}

	public boolean isSized(double[] point) {
		//TODO completare con location
		if (!hasParameter(ECFGParam.Size, EExpType.ModType)) {
			return false;
		}

		//TODO da rivedere
		if (point != null) {
			point[0] = sizeMod.getTransform().getScaleX();
			point[1] = sizeMod.getTransform().getScaleY();
		}

		if (sizeMod.getTransform().getShearX() != 0.0 || sizeMod.getTransform().getShearY() != 0.0) {
			error("Size specification must not be rotated or skewed");
		}

		return false;
	}

	public boolean isTimed(AffineTransformTime transform) {
		//TODO completare con location
		if (!hasParameter(ECFGParam.Time, EExpType.ModType)) {
			return false;
		}

		//TODO da rivedere
		if (transform != null) {
			transform.setBegin(timeMod.getTransformTime().getBegin());
			transform.setEnd(timeMod.getTransformTime().getEnd());
			transform.setStep(timeMod.getTransformTime().getStep());
		}

		if (sizeMod.getTransformTime().getBegin() >= sizeMod.getTransformTime().getEnd()) {
			error("Time specification must have positive duration");
		}

		return false;
	}

	public void getSummetry(SymmList syms, RTI rti) {
		// TODO controllare
		syms.clear();
		ASTExpression e = hasParameter(ECFGParam.Symmetry);
		List<ASTModification> left = getTransforms(e, syms, rti, isTiled(null, null), tileMod.getTransform());
		if (!left.isEmpty()) {
			error("At least one term was invalid");
		}
	}

	public boolean hasParameter(ECFGParam p, double[] value, RTI rti) {
		ASTExpression exp = hasParameter(p);
		if (exp == null || exp.getType() != EExpType.NumericType) {
			return false;
		}
		if (!exp.isConstant() && rti != null) {
			error("This expression must be constant");
			return false;
		} else {
			exp.evaluate(value, 1, rti);
		}
		return true;
	}

	public boolean hasParameter(ECFGParam p, Modification value, RTI rti) {
		ASTExpression exp = hasParameter(p);
		if (exp == null || exp.getType() != EExpType.ModType) {
			return false;
		}
		if (!exp.isConstant() && rti != null) {
			error("This expression must be constant");
			return false;
		} else {
			exp.evaluate(value, true, rti);//TODO controllare
		}
		return true;
	}

	public boolean hasParameter(ECFGParam p, EExpType type) {
		ASTExpression exp = hasParameter(p);
		if (exp == null || exp.getType() != type) {
			return false;
		}
		return true;
	}

	public ASTExpression hasParameter(ECFGParam p) {
		if (paramDepth.get(p).intValue() == -1) {
			return null;
		}
		return paramExp.get(p);
	}

	public boolean addParameter(String name, ASTExpression exp, int depth) {
		ECFGParam p = ECFGParam.valueOf(name);
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
			if (rule.getWeightType() == EWeightType.PercentWeight) {
				percentweightsums[rule.getNameIndex()] += rule.getWeight();
				if (percentweightsums[rule.getNameIndex()] > 1.0001) {
					error("Percentages exceed 100%");
				}
			} else {
				weightsums[rule.getNameIndex()] += rule.getWeight();
			}
			rulecounts[rule.getNameIndex()] += 1;
			weightTypes[rule.getNameIndex()] |= rule.getWeightType().getType();
		}

		for (ASTRule rule : rules) {
			double weight = rule.getWeight() / weightsums[rule.getNameIndex()];
			if ((weightTypes[rule.getNameIndex()] & EWeightType.PercentWeight.getType()) != 0) {
				if (rule.getWeightType() == EWeightType.PercentWeight) {
					weight = rule.getWeight();
				} else {
					weight *= 1.0 - percentweightsums[rule.getNameIndex()];
					if (percentweightsums[rule.getNameIndex()] > 0.9999) {
						warning("Percentages sum to 100%, this rule has no weight");
					}
				}
			}
			if (weightTypes[rule.getNameIndex()] == EWeightType.PercentWeight.getType() && Math.abs(percentweightsums[rule.getNameIndex()] - 1.0) > 0.0001) {
				warning("Percentages do not sum to 100%");
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

		cfdgContents.compile(ECompilePhase.TypeCheck, null, null);

		if (driver.errorOccured()) {
			cfdgContents.compile(ECompilePhase.Simplify, null, null);
		}

		double[] value = new double[1];
		uses16bitColor = hasParameter(ECFGParam.ColorDepth, value, null) && Math.floor(value[0]) == 16;

		if (hasParameter(ECFGParam.Color, value, null)) {
			usesColor = value[0] != 0;
		}

		if (hasParameter(ECFGParam.Alpha, value, null)) {
			usesColor = value[0] != 0;
		}

		ASTExpression e = hasParameter(ECFGParam.Background);
		if (e != null && e instanceof  ASTModification) {
			ASTModification m = (ASTModification) e;
			useAlpha = m.getModData().color().alpha() != 1.0;
			for (ASTModTerm term : m.getModExp()) {
				if (term.getModType() == EModType.alpha || term.getModType() == EModType.alphaTarg) {
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

	public EShapeType getShapeType(int nameIndex) {
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
			if (type.getShapeType() == EShapeType.PathType && !isPath) {
				return "Shape name already in use by another rule or path";
			}
			if (isPath) {
				return "Path name already in use by another rule or path";
			}
			return null;
		}
		if (type.getShapeType() != EShapeType.NewShape) {
			return "Shape name already in use by another rule or path";
		}
		type.getParameters().clear();
		type.getParameters().addAll(p.getParameters());
		type.setIsShape(true);
		type.setArgSize(argSize);
		type.setShapeType(isPath ? EShapeType.PathType : EShapeType.NewShape);
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
			ASTExpression startExp = paramExp.get(ECFGParam.StartShape);

			if (startExp == null) {
				error("No startshape found");
				return null;
			}

			if (startExp instanceof ASTStartSpecifier) {
				ASTStartSpecifier specStart = (ASTStartSpecifier)startExp;
				initShape = new ASTReplacement(driver, specStart, specStart.getModification(), startExp.getLocation());
				initShape.getChildChange().addEntropy(initShape.getShapeSpecifier().getEntropy());
			} else {
				error("Type error in startshape");
				return null;
			}

			RTI rti = new RTI(this, width, height, minSize, variation, border);

			Modification tiled = null;
			Modification sized = null;
			Modification timed = null;

			//TODO rivedere

			double[] maxShape = new double[0];

			if (hasParameter(ECFGParam.Tile, tiled, null)) {
				tileMod = tiled;
				AffineTransform transform = tileMod.getTransform();
				tileOffset.setLocation(transform.getTranslateX(), transform.getTranslateY());
				AffineTransform t = new AffineTransform(transform.getScaleX(), transform.getShearY(), transform.getShearX(), transform.getScaleY(), 0, 0);
				tileMod.setTransform(t);
			}

			if (hasParameter(ECFGParam.Size, sized, null)) {
				sizeMod = sized;
				AffineTransform transform = sizeMod.getTransform();
				tileOffset.setLocation(transform.getTranslateX(), transform.getTranslateY());
				AffineTransform t = new AffineTransform(transform.getScaleX(), transform.getShearY(), transform.getShearX(), transform.getScaleY(), 0, 0);
				tileMod.setTransform(t);
			}

			if (hasParameter(ECFGParam.Time, timed, null)) {
				timeMod = timed;
			}

			if (hasParameter(ECFGParam.MaxShapes, maxShape, null)) {
				if (maxShape[0] > 1) {
					rti.setMaxShapes(maxShape[0]);
				}
			}

			rti.initBounds();

			return rti;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private List<ASTModification> getTransforms(ASTExpression e, SymmList syms, RTI rti, boolean tiled, AffineTransform transform) {
		List<ASTModification> result = new ArrayList<>();

		syms.clear();

		if (e == null) {
			return result;
		}

		// TODO da rivedere

		List<Double> symmSpec = new ArrayList<>();

		for (int i = 0; i < e.size(); i++) {
			ASTExpression cit = e.getChild(i);
			switch (cit.getType()) {
				case FlagType:
					processSymmSpec(syms, transform, tiled, symmSpec, cit.getLocation());
					break;
				case NumericType:
					if (symmSpec.isEmpty() && cit.getType() != EExpType.FlagType) {
						error("Symmetry flag expected here");
					}
					int sz = cit.evaluate(null, 0);
					if (sz < 1) {
						error("Could not evaluate this");
					} else {
						double[] values = new double[sz];
						if (cit.evaluate(values, values.length) != sz) {
							error("Could not evaluate this");
						} else {
							for (double value : values) {
								symmSpec.add(value);
							}
						}
					}
					break;
				case ModType:
					processSymmSpec(syms, transform, tiled, symmSpec, cit.getLocation());
					if (cit instanceof ASTModification) {
						ASTModification m = (ASTModification)cit;
						if (m.getModClass() != null && m.getModClass().getType() == (EModClass.GeomClass.getType() | EModClass.PathOpClass.getType()) && (rti != null || m.isConstant())) {
							Modification mod = new Modification();
							cit.evaluate(mod, false, rti);
							addUnique(syms, sizeMod.getTransform());
						} else {
							result.add(m);
						}
					} else {
						error("Wrong type");
					}
					break;
				default:
					error("Wrong type");
					break;
			}
		}

		processSymmSpec(syms, transform, tiled, symmSpec, e.getLocation());

		return result;
	}

	private void addUnique(SymmList syms, AffineTransform transform) {
		if (syms.contains(transform)) {
			syms.add(transform);
		}
	}

	private void processSymmSpec(SymmList syms, AffineTransform transform, boolean tiled, List<Double> data, Token location) {
		if (data == null) {
			return;
		}

		int type = data.get(0).intValue();
		EFlagType flag = EFlagType.fromType(type);

		boolean frieze = (transform.getScaleX() != 0.0 || transform.getScaleY() != 0.0) && transform.getScaleX() * transform.getScaleY() == 0.0;
		boolean rhombic = tiled && ((Math.abs(transform.getShearY()) <= 0.0000001 && Math.abs(transform.getShearX() / transform.getScaleX() - 0.5) < 0.0000001) || (Math.abs(transform.getShearX()) <= 0.0000001 && Math.abs(transform.getShearY() / transform.getScaleY() - 0.5) < 0.0000001));
		boolean rectangular = tiled && transform.getShearX() == 0.0 && transform.getShearY() == 0.0;
		boolean square = rectangular && transform.getScaleX() == transform.getScaleY();
		boolean hexagonal = false;
		boolean square45 = false;
		double size45 = transform.getScaleX();

		if (rhombic) {
			double x1 = 1;
			double y1 = 0;
			Point2D.Double p1 = new Point2D.Double(x1, y1);
			transform.transform(p1, p1);
			x1 = p1.getX();
			y1 = p1.getY();
			double dist10 = Math.hypot(x1, y1);
			double x2 = 0;
			double y2 = 1;
			Point2D.Double p2 = new Point2D.Double(x1, y1);
			transform.transform(p2, p2);
			x2 = p2.getX();
			y2 = p2.getY();
			double dist01 = Math.hypot(x2, y2);
			hexagonal = Math.abs(dist10 / dist01 - 1.0) < 0.0000001;
			square45 = Math.abs(dist10 / dist01 - SQRT2) < 0.0000001 || Math.abs(dist01 / dist10 - SQRT2) < 0.0000001;
			size45 = Math.min(dist01, dist10);
		}

		if (type >= EFlagType.CF_P11G.getType() && type <= EFlagType.CF_P2MM.getType() && !frieze) {
			error("Frieze symmetry only works in frieze designs");
		}

		if (type >= EFlagType.CF_PM.getType() && type <= EFlagType.CF_P6M.getType() && !tiled) {
			error("Wallpaper symmetry only works in tiled designs");
		}

		if (type >= EFlagType.CF_P2.getType() && !frieze && !tiled) {
			error("p2 symmetry only works in frieze or tiled designs");
		}

		//TODO completare

		switch (flag) {
			case CF_CYCLIC: {
				break;
			}
			default: {
				break;
			}
		}
	}

	// TODO da rivedere

	protected void error(String message) {
		System.err.println(message);
		throw new RuntimeException(message);
	}

	protected void warning(String message) {
		System.err.println(message);
	}

	public void compile(ECompilePhase ph) {
		for (ASTRule rule : rules) {
			rule.compile(ph);
		}
	}

	public void traverse(Shape shape, boolean tr, RTI rti) {
		ASTRule rule = rules.iterator().next();
		rule.traverse(shape, tr, rti);
	}
}
