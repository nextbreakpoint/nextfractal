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

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.*;

public class CFDG {
	private double[] backgroundColor;
	private Shape initialShape;
	private ASTRule needle;
	private ASTReplacement initShape;
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
		//TODO completare
		return false;
	}

	public boolean isFrieze(AffineTransform transform, double[] point) {
		//TODO completare
		return false;
	}

	public boolean isSized(double[] point) {
		//TODO completare
		return false;
	}

	public boolean isTimed(AffineTransform transform) {
		//TODO completare
		return false;
	}

	public void getSummetry(SymmList syms, RTI rti) {
		// TODO controllare
		syms.clear();
		ASTExpression e = hasParameter(ECFGParam.Symmetry);
//		List<ASTModification> left = getTransforms(e, syms, rti, isTiled(), tileMod.getTransform());
//		if (!left.isEmpty()) {
//			error("At least one term was invalid");
//		}
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

	public void rulesLoaded() {
		//TODO implementare
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

//	public EShapeType getShapeType(String name) {
//		for (int i = 0; i < shapeTypes.size(); i++) {
//			if (shapeTypes.get(i).getName().equals(name)) {
//				return shapeTypes.get(i).getShapeType();
//			}
//		}
//		return EShapeType.NewShape;
//	}

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

	public RTI renderer(int width, int height, double minSize, int variation, double border) {
		RTI rti = new RTI();
		//TODO completare
		return rti;
	}

	// TODO da rivedere

	protected void error(String message) {
		System.err.println(message);
		throw new RuntimeException(message);
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
