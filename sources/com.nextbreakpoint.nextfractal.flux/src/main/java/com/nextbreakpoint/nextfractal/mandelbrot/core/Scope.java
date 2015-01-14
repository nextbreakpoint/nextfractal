package com.nextbreakpoint.nextfractal.mandelbrot.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author amedeghini
 */
public class Scope {
	private final Map<String, MutableNumber> vars = new LinkedHashMap<>();

	/**
	 * @param name
	 * @param value
	 */
	public void setVariable(String name, Number value) {
		vars.put(name, new MutableNumber(value));
	}

	/**
	 * @param name
	 * @return
	 */
	public Number getVariable(String name) {
		return vars.get(name);
	}
	
	/**
	 * @return
	 */
	public int stateSize() {
		return vars.size();
	}

	/**
	 * @param state
	 */
	public void getState(MutableNumber[] state) {
		int i = 0;
		for (Entry<String, MutableNumber> entry : vars.entrySet()) {
			state[i++].set(entry.getValue());
		}
	}

	/**
	 * @param state
	 */
	public void setState(Number[] state) {
		int i = 0;
		for (Entry<String, MutableNumber> entry : vars.entrySet()) {
			entry.getValue().set(state[i++]);
		}
	}

	/**
	 * 
	 */
	public void clear() {
		vars.clear();
	}
}
