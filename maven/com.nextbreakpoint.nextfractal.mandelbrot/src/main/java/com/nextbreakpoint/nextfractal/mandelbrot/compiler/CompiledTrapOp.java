package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public interface CompiledTrapOp {
	public void evaluate(Trap trap);
}
