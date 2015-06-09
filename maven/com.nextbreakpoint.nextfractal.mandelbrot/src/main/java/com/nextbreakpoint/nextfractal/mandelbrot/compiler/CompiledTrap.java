package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class CompiledTrap {
	private String name;
	private Number center;
	private List<CompiledTrapOp> operators;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Number getCenter() {
		return center;
	}

	public void setCenter(Number center) {
		this.center = center;
	}

	public List<CompiledTrapOp> getOperators() {
		return operators;
	}

	public void setOperators(List<CompiledTrapOp> operators) {
		this.operators = operators;
	}
}
