package com.nextbreakpoint.nextfractal.mandelbrot;

import java.nio.IntBuffer;
import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.Condition;
import com.nextbreakpoint.nextfractal.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.IntegerVector4D;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.Renderer;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;
import com.nextbreakpoint.nextfractal.render.RenderFactory;

public class MandelbrotImageGenerator implements ImageGenerator {
	private Renderer renderer;
	private Condition condition;

	public MandelbrotImageGenerator(ThreadFactory threadFactory, RenderFactory renderFactory, RendererTile tile) {
		renderer = new Renderer(threadFactory, renderFactory, tile);
	}

	public IntBuffer renderImage(Object data) {
		MandelbrotData generatorData = (MandelbrotData)data;
		IntBuffer pixels = IntBuffer.allocate(renderer.getSize().getWidth() * renderer.getSize().getHeight());
		try {
			Compiler compiler = new Compiler(getClass().getPackage().getName(), getClass().getSimpleName());
			CompilerReport report = compiler.generateJavaSource(generatorData.getSource());
			//TODO report errors
			CompilerBuilder<Orbit> orbitBuilder = compiler.compileOrbit(report);
			CompilerBuilder<Color> colorBuilder = compiler.compileColor(report);
			renderer.setCondition(condition);
			renderer.abortTasks();
			renderer.waitForTasks();
			double[] traslation = generatorData.getTraslation();
			double[] rotation = generatorData.getRotation();
			double[] scale = generatorData.getScale();
			double[] constant = generatorData.getConstant();
			boolean julia = generatorData.isJulia();
			renderer.setOrbit(orbitBuilder.build());
			renderer.setColor(colorBuilder.build());
			renderer.init();
			RendererView view = new RendererView();
			view .setTraslation(new DoubleVector4D(traslation));
			view.setRotation(new DoubleVector4D(rotation));
			view.setScale(new DoubleVector4D(scale));
			view.setState(new IntegerVector4D(0, 0, 0, 0));
			view.setJulia(julia);
			view.setConstant(new Number(constant));
			renderer.setView(view);
			renderer.runTask();
			renderer.waitForTasks();
			renderer.getPixels(pixels);
		} catch (Exception e) {
			e.printStackTrace();//TODO display errors
		}
		return pixels;
	}

	public RendererSize getSize() {
		return renderer.getSize();
	}
	
	@Override
	public void setStopCondition(Condition condition) {
		this.condition = condition;
	}
}
