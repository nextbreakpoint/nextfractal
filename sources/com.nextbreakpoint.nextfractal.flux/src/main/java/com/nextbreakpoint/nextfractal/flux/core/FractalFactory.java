package com.nextbreakpoint.nextfractal.flux.core;

public interface FractalFactory {
	public String getId();
	
	public FractalParser createParser();

	public FractalSession createSession();
}
