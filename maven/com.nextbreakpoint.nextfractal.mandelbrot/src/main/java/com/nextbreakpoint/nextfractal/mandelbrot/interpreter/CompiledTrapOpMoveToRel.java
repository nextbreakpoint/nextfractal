package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class CompiledTrapOpMoveToRel implements CompiledTrapOp {
	private Number c1;
	
	public CompiledTrapOpMoveToRel(Number c1) {
		this.c1 = c1;
	}
}
