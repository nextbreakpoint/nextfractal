package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTNumber;

public class JavaScriptOrbit extends Orbit {
	private ASTFractal astFractal;
	private ExpressionContext context;
	private String source;
	private ScriptEngine engine;
	private List<Number[]> states;

	public JavaScriptOrbit(ASTFractal astFractal, ExpressionContext context, String source) {
		this.astFractal = astFractal;
		this.context = context;
		this.source = source;
		initializeStack();
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("JavaScript");
		Map<String, Object> map = new HashMap<>();
		map.put("orbit", this);
		map.put("exp", new JavaScriptMath());
		Bindings bindings = new SimpleBindings(map);
		engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
	}

	@Override
	public void init() {
		ASTNumber a = astFractal.getOrbit().getRegion().getA();
		ASTNumber b = astFractal.getOrbit().getRegion().getB();
		setInitialRegion(new Number(a.r(), a.i()), new Number(b.r(), b.i()));
		Collection<CompilerVariable> vars = astFractal.getStateVariables();
		for (CompilerVariable var : vars) {
			if (var.isReal()) {
				addVariable(var.getRealValue());
			} else {
				addVariable(var.getValue());
			}
		}
	}

	@Override
	public void render(List<Number[]> states) {
		this.states = states;
		try {
		     engine.eval(source);
		} catch (ScriptException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected MutableNumber[] createNumbers() {
		if (context == null) {
			return null;
		}
		return new MutableNumber[context.getNumberCount()];
	}

	public List<Number[]> getStates() {
		return states;
	}
}
