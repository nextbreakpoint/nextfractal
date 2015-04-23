/*
 * NextFractal 1.0.1
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

import org.antlr.v4.runtime.Token;

class ASTReplacement {
	private ASTRuleSpecifier shapeSpec;
	private ASTModification childChange;
	private ERepElemType repType;
	private EPathOp pathOp;
	private Token location;
	
	public ASTReplacement(ASTRuleSpecifier shapeSpec, ASTModification childChange, ERepElemType repType, Token location) {
		this.repType = repType;
		this.childChange = childChange;
		this.shapeSpec = shapeSpec;
		this.pathOp = EPathOp.UNKNOWN;
		this.location = location;
		childChange = new ASTModification(location);
	}

	public ASTReplacement(ASTRuleSpecifier shapeSpec, ASTModification childChange, Token location) {
		this(shapeSpec, childChange, ERepElemType.empty, location);
	}

	public ASTReplacement(ASTModification childChange, ERepElemType repType, Token location) {
		this(new ASTRuleSpecifier(location), childChange, repType, location);
	}

	public ASTReplacement(String name, Token location) {
		this(new ASTRuleSpecifier(location), new ASTModification(location), ERepElemType.op, location);
		this.pathOp = EPathOp.pathOpTypeByName(name);
		if (this.pathOp == EPathOp.UNKNOWN) {
			error("Unknown path operation type");
		}
	}

	public ASTRuleSpecifier getShapeSpecifier() {
		return shapeSpec;
	}

	public ERepElemType getRepType() {
		return repType;
	}
	
	public void setRepType(ERepElemType repType) {
		this.repType = repType;
	}
	
	public EPathOp getPathOp() {
		return pathOp;
	}

	public void setPathOp(EPathOp pathOp) {
		this.pathOp = pathOp;
	}

	public ASTModification getChildChange() {
		return childChange;
	}

	public void compile(ECompilePhase ph) {
		ASTExpression r = shapeSpec.compile(ph);
		assert(r == null);
		r = childChange.compile(ph);
		assert(r == null);
		
		switch (ph) {
			case TypeCheck: 
				childChange.addEntropy(shapeSpec.getEntropy());
				if (Builder.currentBuilder().isInPathContainer()) {
					// This is a subpath
					if (shapeSpec.getArgSource() == EArgSource.ShapeArgs || shapeSpec.getArgSource() == EArgSource.StackArgs || PrimShape.isPrimShape(shapeSpec.getShapeType())) {
						if (repType != ERepElemType.op) {
							error("Error in subpath specification");
						} else {
							ASTRule rule = Builder.currentBuilder().getRule(shapeSpec.getShapeType());
							if (rule == null || rule.isPath()) {
								error("Subpath can only refer to a path");
							} else if (rule.getRuleBody().getRepType() != repType.getType()) {
								error("Subpath type mismatch error");
							}
						}
					}
				}
				break;
	
			case Simplify: 
				r = shapeSpec.simplify();
				assert(r == shapeSpec);
				r = childChange.simplify();
				assert(r == childChange);
				break;
	
			default:
				break;
		}
	}

	public void replace(Shape s, RTI rti) {
		if (shapeSpec.getArgSource() == EArgSource.NoArgs) {
			s.setShapeType(shapeSpec.getShapeType());
			s.setParameters(null);
		} else {
			s.setParameters(shapeSpec.evalArgs(rti, s.getParameters()));
			if (shapeSpec.getArgSource() == EArgSource.SimpleParentArgs) {
				s.setShapeType(shapeSpec.getShapeType());
			} else {
				s.setShapeType(s.getParameters().getRuleName());
			}
			if (s.getParameters().getParamCount() == 0) {
				s.setParameters(null);
			}
		}
		rti.getCurrentSeed().add(childChange.getModData().getRand64Seed());
		rti.getCurrentSeed().bump();
		Modification[] mod = new Modification[1];
		childChange.evaluate(mod, true, rti);
		s.setWorldState(mod[0]);
		s.setAreaCache(s.getWorldState().area());
	}

	public void traverse(Shape parent, boolean tr, RTI rti) {
		Shape child = parent;
		switch (repType) {
		case replacement:
			replace(child,  rti);
			child.getWorldState().setRand64Seed(rti.getCurrentSeed());
			child.getWorldState().getRand64Seed().bump();
			rti.processShape(child);
			break;

		case op:
			if (!tr) child.getWorldState().setTransform(null);
		case mixed:
		case command:
			replace(child, rti);
			rti.processSubpath(child, tr || repType == ERepElemType.op, repType);
			break;

		default:
			throw new RuntimeException("Subpaths must be all path operation or all path command");
		}
	}

	protected void error(String message) {
		System.out.println(message);
	}

	public Token getLocation() {
		return location;
	}
}
