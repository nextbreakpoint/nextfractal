package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public class CompiledTrapOpArcToRel implements CompiledTrapOp {
	private Number c1;
	private Number c2;

	public CompiledTrapOpArcToRel(Number c1, Number c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	@Override
	public void evaluate(Trap trap) {
		trap.arcToRel(c1, c2);
	}
}
