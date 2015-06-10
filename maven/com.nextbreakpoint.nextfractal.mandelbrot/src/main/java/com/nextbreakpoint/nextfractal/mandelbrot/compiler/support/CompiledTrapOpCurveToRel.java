package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public class CompiledTrapOpCurveToRel implements CompiledTrapOp {
	private Number c1;
	private Number c2;
	private Number c3;
	
	public CompiledTrapOpCurveToRel(Number c1, Number c2, Number c3) {
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}

	@Override
	public void evaluate(Trap trap) {
		trap.curveToRel(c1, c2, c3);
	}
}
