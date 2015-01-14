package com.nextbreakpoint.nextfractal.mandelbrot.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author amedeghini
 */
public class Scope {
	private final Map<String, MutableNumber> vars = new HashMap<>();
	private final List<String> variables = new ArrayList<>();

	/**
	 * @param name
	 * @param value
	 */
	public void createVariable(String name, Number value) {
		variables.add(name);
		setVariable(name, value);
	}

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
		return variables.size();
	}

	/**
	 * @param state
	 */
	public void getState(MutableNumber[] state) {
		int i = 0;
		for (String variable : variables) {
			state[i++].set(vars.get(variable));
		}
	}

	/**
	 * @param state
	 */
	public void setState(Number[] state) {
		int i = 0;
		for (String variable : variables) {
			vars.get(variable).set(state[i++]);
		}
	}

	/**
	 * 
	 */
	public void clear() {
		vars.clear();
		variables.clear();
	}
}
