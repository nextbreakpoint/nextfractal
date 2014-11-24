package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTOrbit extends ASTObject {
	private ASTRegion region; 
	private List<ASTOrbitTrap> traps; 
	private ASTOrbitProjection projection; 
	private ASTOrbitBegin begin; 
	private ASTOrbitLoop loop; 
	private ASTOrbitCondition condition; 
	private ASTOrbitEnd end; 

	public ASTOrbit(Token location, ASTRegion region) {
		super(location);
		this.region = region;
	}

	public List<ASTOrbitTrap> getTraps() {
		return traps;
	}

	public void setTraps(List<ASTOrbitTrap> traps) {
		this.traps = traps;
	}

	public ASTOrbitProjection getProjection() {
		return projection;
	}

	public void setProjection(ASTOrbitProjection projection) {
		this.projection = projection;
	}

	public ASTOrbitBegin getBegin() {
		return begin;
	}

	public void setBegin(ASTOrbitBegin begin) {
		this.begin = begin;
	}

	public ASTOrbitLoop getLoop() {
		return loop;
	}

	public void setLoop(ASTOrbitLoop loop) {
		this.loop = loop;
	}

	public ASTOrbitCondition getCondition() {
		return condition;
	}

	public void setCondition(ASTOrbitCondition condition) {
		this.condition = condition;
	}

	public ASTOrbitEnd getEnd() {
		return end;
	}

	public void setEnd(ASTOrbitEnd end) {
		this.end = end;
	}

	public ASTRegion getRegion() {
		return region;
	}

	public void addTrap(ASTOrbitTrap trap) {
		if (traps == null) {
			traps = new ArrayList<ASTOrbitTrap>();
		}
		traps.add(trap);
	}
}