package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

public class ASTOrbit extends ASTObject {
	private ASTRegion region; 
	private List<ASTOrbitTrap> traps = new ArrayList<>(); 
	private ASTOrbitBegin begin; 
	private ASTOrbitLoop loop; 
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String suffix = "";
		if (region != null) {
			builder.append("region = ");
			builder.append(region);
			suffix = ",";
		}
		if (begin != null) {
			if (suffix.length() != 0) {
				builder.append(suffix);
			} else {
				suffix = ",";
			}
			builder.append("begin = {");
			builder.append(begin);
			builder.append("}");
		}
		if (loop != null) {
			if (suffix.length() != 0) {
				builder.append(suffix);
			} else {
				suffix = ",";
			}
			builder.append("loop = {");
			builder.append(loop);
			builder.append("}");
		}
		if (end != null) {
			if (suffix.length() != 0) {
				builder.append(suffix);
			} else {
				suffix = ",";
			}
			builder.append("end = {");
			builder.append(end);
			builder.append("}");
		}
		if (suffix.length() != 0) {
			builder.append(suffix);
		} else {
			suffix = ",";
		}
		builder.append("traps = [");
		for (int i = 0; i < traps.size(); i++) {
			ASTOrbitTrap trap = traps.get(i);
			builder.append("{");
			builder.append(trap);
			builder.append("}");
			if (i < traps.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}
}