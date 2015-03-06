package com.nextbreakpoint.nextfractal.core.utils;

import java.io.IOException;
import java.util.logging.LogManager;

public class LogConfig {
	public LogConfig() {
		try {
			LogManager.getLogManager().readConfiguration(getClass().getResourceAsStream("/logging.properties"));
		} catch (SecurityException | IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
