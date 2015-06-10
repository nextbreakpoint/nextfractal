package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public interface CompiledTrapOp {
	public void evaluate(Trap trap);
}
