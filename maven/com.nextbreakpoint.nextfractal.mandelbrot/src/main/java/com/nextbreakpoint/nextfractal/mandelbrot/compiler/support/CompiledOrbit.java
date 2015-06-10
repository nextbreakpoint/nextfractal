package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.Collection;
import java.util.List;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class CompiledOrbit {
	private Number[] region;
	private Collection<CompilerVariable> orbitVariables; 
	private Collection<CompilerVariable> stateVariables;
	private List<CompiledStatement> beginStatements;
	private List<CompiledStatement> loopStatements;
	private List<CompiledStatement> endStatements;
	private List<CompiledTrap> traps;
	private CompiledCondition loopCondition;
	private int loopBegin;
	private int loopEnd;
	
	public CompiledOrbit(Collection<CompilerVariable> orbitVariables, Collection<CompilerVariable> stateVariables) {
		this.orbitVariables = orbitVariables;
		this.stateVariables = stateVariables;
	}

	public Collection<CompilerVariable> getOrbitVariables() {
		return orbitVariables;
	}

	public Collection<CompilerVariable> getStateVariables() {
		return stateVariables;
	}

	public Number[] getRegion() {
		return region;
	}

	public void setRegion(Number[] region) {
		this.region = region;
	}

	public List<CompiledStatement> getBeginStatements() {
		return beginStatements;
	}

	public void setBeginStatements(List<CompiledStatement> beginStatements) {
		this.beginStatements = beginStatements;
	}

	public List<CompiledStatement> getLoopStatements() {
		return loopStatements;
	}

	public void setLoopStatements(List<CompiledStatement> loopStatements) {
		this.loopStatements = loopStatements;
	}

	public List<CompiledStatement> getEndStatements() {
		return endStatements;
	}

	public void setEndStatements(List<CompiledStatement> endStatements) {
		this.endStatements = endStatements;
	}

	public List<CompiledTrap> getTraps() {
		return traps;
	}

	public void setTraps(List<CompiledTrap> traps) {
		this.traps = traps;
	}

	public CompiledCondition getLoopCondition() {
		return loopCondition;
	}

	public void setLoopCondition(CompiledCondition loopCondition) {
		this.loopCondition = loopCondition;
	}

	public int getLoopBegin() {
		return loopBegin;
	}

	public void setLoopBegin(int loopBegin) {
		this.loopBegin = loopBegin;
	}

	public int getLoopEnd() {
		return loopEnd;
	}

	public void setLoopEnd(int loopEnd) {
		this.loopEnd = loopEnd;
	}
}
