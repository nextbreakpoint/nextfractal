package com.nextbreakpoint.nextfractal.swing;

import com.nextbreakpoint.nextfractal.core.ApplicationContext;
import com.nextbreakpoint.nextfractal.twister.swing.NextFractal;

public class Main implements ApplicationContext {
	public static void main(String[] args) {
		NextFractal application = new NextFractal();
		try {
			application.start(new Main());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	@Override
	public void applicationRunning() {
	}
}
