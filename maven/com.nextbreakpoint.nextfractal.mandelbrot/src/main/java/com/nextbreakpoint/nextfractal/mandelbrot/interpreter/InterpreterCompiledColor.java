package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Collection;
import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledRule;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;

public class InterpreterCompiledColor {
	private Collection<CompilerVariable> colorVariables; 
	private Collection<CompilerVariable> stateVariables;
	private float[] backgroundColor;
	private boolean julia;
	private List<CompiledRule> rules;
	private List<CompiledPalette> palettes;
	private List<CompiledStatement> initStatements;
	
	public InterpreterCompiledColor(Collection<CompilerVariable> colorVariables, Collection<CompilerVariable> stateVariables) {
		this.colorVariables = colorVariables;
		this.stateVariables = stateVariables;
	}

	public Collection<CompilerVariable> getColorVariables() {
		return colorVariables;
	}

	public Collection<CompilerVariable> getStateVariables() {
		return stateVariables;
	}

	public boolean isJulia() {
		return julia;
	}

	public void setJulia(boolean julia) {
		this.julia = julia;
	}

	public float[] getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(float[] backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public List<CompiledRule> getRules() {
		return rules;
	}

	public void setRules(List<CompiledRule> rules) {
		this.rules = rules;
	}

	public List<CompiledPalette> getPalettes() {
		return palettes;
	}

	public void setPalettes(List<CompiledPalette> palettes) {
		this.palettes = palettes;
	}

	public List<CompiledStatement> getInitStatements() {
		return initStatements;
	}

	public void setInitStatements(List<CompiledStatement> initStatements) {
		this.initStatements = initStatements;
	}
}