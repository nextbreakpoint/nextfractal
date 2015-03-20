package com.nextbreakpoint.nextfractal.mandelbrot;

import java.nio.IntBuffer;
import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.utils.Condition;
import com.nextbreakpoint.nextfractal.core.utils.Double4D;
import com.nextbreakpoint.nextfractal.core.utils.Integer4D;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.Renderer;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;

public class MandelbrotImageGenerator implements ImageGenerator {
	private Renderer renderer;
	private Condition condition;

	public MandelbrotImageGenerator(ThreadFactory threadFactory, RendererFactory renderFactory, RendererTile tile) {
		renderer = new Renderer(threadFactory, renderFactory, tile);
	}

	public IntBuffer renderImage(Object data) {
		MandelbrotData generatorData = (MandelbrotData)data;
		IntBuffer pixels = IntBuffer.allocate(renderer.getSize().getWidth() * renderer.getSize().getHeight());
		try {
			Compiler compiler = new Compiler();
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
			double[] constant = generatorData.getPoint();
			boolean julia = generatorData.isJulia();
			renderer.setOrbit(orbitBuilder.build());
			renderer.setColor(colorBuilder.build());
			renderer.init();
			RendererView view = new RendererView();
			view .setTraslation(new Double4D(traslation));
			view.setRotation(new Double4D(rotation));
			view.setScale(new Double4D(scale));
			view.setState(new Integer4D(0, 0, 0, 0));
			view.setJulia(julia);
			view.setPoint(new Number(constant));
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
	public boolean isInterrupted() {
		return renderer.isInterrupted();
	}
}
