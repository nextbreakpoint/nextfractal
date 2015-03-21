package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import java.util.concurrent.locks.ReentrantLock;

public class DummyRendererLock implements RendererLock {
	private ReentrantLock lock = new ReentrantLock();
	
	@Override
	public void lock() {
		lock.lock();
	}

	@Override
	public void unlock() {
		lock.unlock();
	}
}
