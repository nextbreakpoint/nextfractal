package com.nextbreakpoint.nextfractal.core;

public interface Application {
	public static final int EXIT_RESTART = 1;
	public static final int EXIT_OK = 0;
	public Object start(ApplicationContext context) throws Exception;
	public void stop();
}
