package com.nextbreakpoint.nextfractal.contextfree.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class CFDG {
	private List<ShapeType> shapeTypes = new ArrayList<ShapeType>();
	private Map<Integer, ASTDefine> functions = new HashMap<Integer, ASTDefine>();
	private Stack<ASTRule> rules = new Stack<ASTRule>();
	private Map<ECFGParam, Integer> paramDepth = new HashMap<ECFGParam, Integer>();
	private Map<ECFGParam, ASTExpression> paramExp = new HashMap<ECFGParam, ASTExpression>();
	private int parameters;
	private boolean usesColor;
	private boolean usesAlpha;
	private boolean uses16bitColor;
	private boolean usesTime;
	private boolean usesFrameTime;
	
	public int encodeShapeName(String s) {
		int i = tryEncodeShapeName(s);
		if (i >= 0) return i;
		shapeTypes.add(new ShapeType(s));
		return shapeTypes.size() - 1;
	}
	
	public int tryEncodeShapeName(String s) {
	    for (int i = 0; i < shapeTypes.size(); i++) {
	        if (s.equals(shapeTypes.get(i).getName())) {
	            return i;
	        }
	    }
	    return -1;
	}

	public String decodeShapeName(int shape) {
	    if (shape < shapeTypes.size()) {
	        return shapeTypes.get(shape).getName();
	    } else {
	        return "**unnamed shape**";
	    }
	}

	public ASTDefine findFunction(int index) {
		ASTDefine def = functions.get(index);
		if (def != null) {
			return def;
		}
		return null;
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
	
	public boolean addRuleShape(ASTRule rule) {
		rules.push(rule);
		ShapeType type = shapeTypes.get(rule.getNameIndex());
		if (type.getShapeType() == EShapeType.NewShape) {
			type.setShapeType(rule.isPath() ? EShapeType.PathType : EShapeType.RuleType);
		}
		if (!type.getParameters().isEmpty()) {
			rule.getRuleBody().getParameters().clear();
			rule.getRuleBody().getParameters().addAll(type.getParameters());
		}
		type.setHasRules(true);
		return type.isShape();
	}

	public EShapeType getShapeType(int nameIndex) {
		return shapeTypes.get(nameIndex).getShapeType();
	}
	
	public EShapeType getShapeType(String name) {
		for (int i = 0; i < shapeTypes.size(); i++) {
			if (shapeTypes.get(i).getName().equals(name)) {
				return shapeTypes.get(i).getShapeType();
			}
		}
		return EShapeType.NewShape;
	}
	
	public ASTDefine declareFunction(int nameIndex, ASTDefine def) {
		ASTDefine prev = findFunction(nameIndex);
		if (prev != null) {
			return prev;
		}
		functions.put(nameIndex, def);
		return def;
	}
	
	public List<ASTParameter> getShapeParams(int nameIndex) {
	    if (nameIndex < 0 || nameIndex >= shapeTypes.size() || !shapeTypes.get(nameIndex).isShape()) {
	        return null;
	    }
	    return shapeTypes.get(nameIndex).getParameters();
	}
	
	public ASTRule findRule(int nameIndex) {
	    for (ASTRule rule: rules) {
	        if (rule.getNameIndex() == nameIndex) {
	            return rule;
	        }
	    }
	    return null;
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

	public boolean hasParameter(ECFGParam p, Modification[] value, RTI rti) {
		ASTExpression exp = hasParameter(p);
		if (exp == null || exp.getType() != EExpType.ModType) {
			return false;
		}
		if (!exp.isConstant() && rti != null) {
			error("This expression must be constant");
			return false;
		} else {
			exp.evaluate(value, false, rti);//TODO controllare
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

	public void setShapeHasNoParam(int nameIndex, ASTExpression args) {
		if (nameIndex < shapeTypes.size() && args != null) {
			shapeTypes.get(nameIndex).setShouldHaveNoParams(true);
		}
	}

	public void addParameter(EParam param) {
		parameters |= param.getType();
	    usesColor = (parameters & EParam.Color.getType()) != 0;
	    usesTime = (parameters & EParam.Time.getType()) != 0;
	    usesFrameTime = (parameters & EParam.FrameTime.getType()) != 0;
	}

	protected void error(String message) {
		System.err.println(message);
	}
}
