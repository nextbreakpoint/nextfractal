package com.nextbreakpoint.nextfractal.flux.mandelbrot.parser;

import com.nextbreakpoint.nextfractal.flux.FractalParser;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.fractal.RendererFractalAdapter;

public class MandelbrotFractalParser implements FractalParser {
	@Override
	public RendererFractal parse(String packageName, String className, String source) throws Exception {
		Compiler compiler = new Compiler(packageName, className, source);
		CompilerReport report = compiler.compile();
		//TODO report errors
		return new RendererFractalAdapter(report.getFractal()); 
	}
}
