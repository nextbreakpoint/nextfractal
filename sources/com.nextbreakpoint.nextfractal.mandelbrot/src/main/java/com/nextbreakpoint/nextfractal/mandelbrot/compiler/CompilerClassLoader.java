package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class CompilerClassLoader extends ClassLoader {
	private static final Logger logger = Logger.getLogger(CompilerClassLoader.class.getName());
	private static final AtomicInteger count = new AtomicInteger();
	
	public CompilerClassLoader() {
		logger.fine("Create classloader (" + count.addAndGet(1) + ")");
	}
	
	public void defineClassFromData(String name, byte[] data) {
		Class<?> clazz = defineClass(name, data, 0, data.length);
		super.resolveClass(clazz);
	}

	@Override
	protected void finalize() throws Throwable {
		logger.fine("Finalize classloader (" + count.addAndGet(-1) + ")");
		super.finalize();
	}
}