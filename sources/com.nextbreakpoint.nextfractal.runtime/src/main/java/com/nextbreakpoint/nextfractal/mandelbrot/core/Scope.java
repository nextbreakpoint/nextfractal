package com.nextbreakpoint.nextfractal.mandelbrot.core;

/**
 * @author amedeghini
 */
public class Scope {
	private MutableNumber[] vars = new MutableNumber[0]; 

	/**
	 * @param value
	 */
	public void createVariable(Number value) {
		MutableNumber[] tmpVars = vars;
		vars = new MutableNumber[vars.length + 1];
		System.arraycopy(tmpVars, 0, vars, 0, tmpVars.length);
		vars[tmpVars.length] = new MutableNumber(value);
	}

	/**
	 * @param index
	 * @param value
	 */
	public void setVariable(int index, Number value) {
		vars[index].set(value);
	}

	/**
	 * @param index
	 * @return
	 */
	public Number getVariable(int index) {
		return vars[index];
	}
	
	/**
	 * @return
	 */
	public int stateSize() {
		return vars.length;
	}

	/**
	 * @param state
	 */
	public void getState(MutableNumber[] state) {
		for (int i = 0; i < vars.length; i++) {
			state[i].set(vars[i]);
		}
	}

	/**
	 * @param state
	 */
	public void setState(Number[] state) {
		for (int i = 0; i < vars.length; i++) {
			vars[i].set(state[i]);
		}
	}

	/**
	 * 
	 */
	public void clear() {
		for (int i = 0; i < vars.length; i++) {
			vars[i].set(0, 0);
		}
	}

	/**
	 * 
	 */
	public void empty() {
		vars = new MutableNumber[0];
	}
}
