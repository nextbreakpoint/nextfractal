package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.ArrayList;
import java.util.List;

class ShapeType {
	private String name;
	private boolean hasRules;
	private boolean isShape;
	private EShapeType shapeType;
	private List<ASTParameter> parameters = new ArrayList<ASTParameter>();
	private int argSize;
	private boolean shouldHaveNoParams;

	public ShapeType(String name) {
		this.name = name;
		this.hasRules = false;
		this.isShape = false;
		this.shapeType = EShapeType.NewShape;
		this.argSize = 0;
		this.shouldHaveNoParams = false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasRules() {
		return hasRules;
	}

	public void setHasRules(boolean hasRules) {
		this.hasRules = hasRules;
	}

	public boolean isShape() {
		return isShape;
	}

	public void setIsShape(boolean isShape) {
		this.isShape = isShape;
	}

	public EShapeType getShapeType() {
		return shapeType;
	}

	public void setShapeType(EShapeType shapeType) {
		this.shapeType = shapeType;
	}

	public List<ASTParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ASTParameter> parameters) {
		this.parameters = parameters;
	}

	public int getArgSize() {
		return argSize;
	}

	public void setArgSize(int argSize) {
		this.argSize = argSize;
	}

	public boolean isShouldHaveNoParams() {
		return shouldHaveNoParams;
	}

	public void setShouldHaveNoParams(boolean shouldHaveNoParams) {
		this.shouldHaveNoParams = shouldHaveNoParams;
	}
}