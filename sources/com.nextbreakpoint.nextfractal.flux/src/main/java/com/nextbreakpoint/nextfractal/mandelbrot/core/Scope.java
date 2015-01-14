package com.nextbreakpoint.nextfractal.mandelbrot.core;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author amedeghini
 */
public class Scope {
	private final Map<String, Variable> vars = new HashMap<>();
	private final Set<String> stateVars = new LinkedHashSet<>();

	/**
	 * @param name
	 * @param var
	 */
	public void registerVariable(String name, Variable var) {
		vars.put(name, var);
	}

	/**
	 * @param name
	 * @return
	 */
	public Number getVariable(String name) {
		return vars.get(name).get();
	}
	
	/**
	 * @param name
	 */
	public void addStateVariable(String name) {
		stateVars.add(name);
	}

	/**
	 * @param name
	 */
	public void removeStateVariable(String name) {
		stateVars.remove(name);
	}
	
	/**
	 * @return
	 */
	public int stateSize() {
		return stateVars.size();
	}

	/**
	 * @param state
	 */
	public void getState(MutableNumber[] state) {
		int i = 0;
		for (String varName : stateVars) {
			Variable var = vars.get(varName);
			if (var != null) {
				state[i++].set(var.get());
			}
		}
	}

	/**
	 * @param state
	 */
	public void setState(Number[] state) {
		int i = 0;
		for (String varName : stateVars) {
			Variable var = vars.get(varName);
			if (var != null) {
				var.get().set(state[i++]);
			}
		}
	}

	/**
	 * 
	 */
	public void clear() {
		stateVars.clear();
		vars.clear();
	}
}
