package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledTrapOp;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public class CompiledTrapOpMoveToRel implements CompiledTrapOp {
	private Number c1;
	
	public CompiledTrapOpMoveToRel(Number c1) {
		this.c1 = c1;
	}

	@Override
	public void evaluate(Trap trap) {
		trap.moveToRel(c1);
	}
}
