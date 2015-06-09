package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public class CompiledTrapOpMoveTo implements CompiledTrapOp {
	private Number c1;
	
	public CompiledTrapOpMoveTo(Number c1) {
		this.c1 = c1;
	}

	@Override
	public void evaluate(Trap trap) {
		trap.moveTo(c1);
	}
}
