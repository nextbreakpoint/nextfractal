package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public class CompiledTrapOpArcTo implements CompiledTrapOp {
	private Number c1;
	private Number c2;
	
	public CompiledTrapOpArcTo(Number c1, Number c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	@Override
	public void evaluate(Trap trap) {
		trap.arcTo(c1, c2);
	}
}
