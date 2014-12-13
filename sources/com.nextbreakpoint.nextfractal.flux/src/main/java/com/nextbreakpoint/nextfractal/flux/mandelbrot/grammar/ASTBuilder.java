package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler.CompilerVariable;

public class ASTBuilder {
	private ASTFractal fractal;

	public ASTBuilder() {
	}
	
	protected void warning(String message, Token location) {
		System.out.println("[" + location.getLine() + ":" + location.getCharPositionInLine() + "] : " + message);
	}
	
	protected void error(String message, Token location) {
		System.err.println("[" + location.getLine() + ":" + location.getCharPositionInLine() + "] : " + message);
	}
	
	public void setOrbit(ASTOrbit orbit) {
		fractal.setOrbit(orbit);
	}

	public void setOrbitBegin(ASTOrbitBegin orbitBegin) {
		fractal.getOrbit().setBegin(orbitBegin);
	}

	public void setOrbitEnd(ASTOrbitEnd orbitEnd) {
		fractal.getOrbit().setEnd(orbitEnd);
	}

	public void setOrbitLoop(ASTOrbitLoop orbitLoop) {
		fractal.getOrbit().setLoop(orbitLoop);
	}
	
	public void addOrbitTrap(ASTOrbitTrap orbitTrap) {
		fractal.getOrbit().addTrap(orbitTrap);
	}

	public void addBeginStatement(ASTStatement statement) {
		fractal.getOrbit().getBegin().addStatement(statement);
	}

	public void addEndStatement(ASTStatement statement) {
		fractal.getOrbit().getEnd().addStatement(statement);
	}

	public void addLoopStatement(ASTStatement statement) {
		fractal.getOrbit().getLoop().addStatement(statement);
	}

	public void addOrbitTrapOp(ASTOrbitTrapOp orbitTrapOp) {
		if (fractal.getOrbit().getTraps().size() > 0) {
			ASTOrbitTrap trap = fractal.getOrbit().getTraps().get(fractal.getOrbit().getTraps().size() - 1);
			trap.addOperator(orbitTrapOp);
		}
	}

	public void setColor(ASTColor color) {
		fractal.setColor(color);;
	}

	public void addPalette(ASTPalette palette) {
		fractal.getColor().addPalette(palette);
	}
	
	public void addRule(ASTRule rule) {
		fractal.getColor().addRule(rule);
	}

	public void addPaletteElement(ASTPaletteElement element) {
		if (fractal.getColor().getPalettes().size() > 0) {
			ASTPalette palette = fractal.getColor().getPalettes().get(fractal.getColor().getPalettes().size() - 1);
			palette.addElements(element);
		}
	}

	public ASTFractal getFractal() {
		return fractal;
	}
	
	public void setFractal(ASTFractal fractal) {
		this.fractal = fractal;
	}

	public double parseDouble(String text) {
		return Double.parseDouble(text);
	}

	public float parseFloat(String text) {
		return Float.parseFloat(text);
	}

	public int parseInt(String text) {
		return Integer.parseInt(text);
	}

	public int parseInt(String text, int base) {
		return Integer.parseInt(text, base);
	}

	public long parseLong(String text) {
		return Long.parseLong(text);
	}

	public long parseLong(String text, int base) {
		return Long.parseLong(text, base);
	}

	public void registerVariable(String name, boolean real, boolean create, Token location) {
		fractal.registerVariable(name, real, create, location);
	}

	public void registerVariable(String name, boolean real, Token location) {
		fractal.registerVariable(name, real, true, location);
	}

	public CompilerVariable getVariable(String name, Token location) {
		return fractal.getVariable(name, location);
	}

	public void registerStateVariable(String variable, Token location) {
		fractal.registerStateVariable(variable, location);
	}
}	
