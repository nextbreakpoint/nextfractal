package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class CompiledTrapOpMoveTo implements CompiledTrapOp {
	private Number c1;
	
	public CompiledTrapOpMoveTo(Number c1) {
		this.c1 = c1;
	}
}
