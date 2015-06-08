package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class CompiledTrapOpCurveTo implements CompiledTrapOp {
	private Number c1;
	private Number c2;
	private Number c3;
	
	public CompiledTrapOpCurveTo(Number c1, Number c2, Number c3) {
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}
}
