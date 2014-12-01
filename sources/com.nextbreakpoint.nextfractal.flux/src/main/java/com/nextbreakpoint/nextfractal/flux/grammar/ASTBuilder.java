package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.flux.Variable;

public class ASTBuilder {
	private ASTFractal fractal;
	private Map<String, Variable> variables = new HashMap<>();

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
	
	public void setOrbitProjection(ASTOrbitProjection orbitProjection) {
		fractal.getOrbit().setProjection(orbitProjection);
	}

	public void setOrbitCondition(ASTOrbitCondition orbitCondition) {
		fractal.getOrbit().setCondition(orbitCondition);
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

	public void registerVariable(ASTStatement statement) {
		Variable var = variables.get(statement.getName());
		if (var == null) {
			var = new Variable(statement.getName(), statement.getExp().isReal());
			variables.put(var.getName(), var);
		} else if (!statement.getExp().isReal() && var.isReal()) {
			throw new RuntimeException("Variable type is not compatible with expression type: " + statement.getLocation().getText() + " [" + statement.getLocation().getLine() + ":" + statement.getLocation().getCharPositionInLine() + "]");
		}
	}
}	
