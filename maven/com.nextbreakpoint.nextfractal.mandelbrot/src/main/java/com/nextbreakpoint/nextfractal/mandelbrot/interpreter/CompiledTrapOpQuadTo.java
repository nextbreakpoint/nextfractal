package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class CompiledTrapOpQuadTo implements CompiledTrapOp {
	private Number c1;
	private Number c2;
	
	public CompiledTrapOpQuadTo(Number c1, Number c2) {
		this.c1 = c1;
		this.c2 = c2;
	}
}
