package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTFractal;

public class JavaScriptColor extends Color {
	private ASTFractal astFractal;
	private ExpressionContext context;
	private String source;
	private ScriptEngine engine;

	public JavaScriptColor(ASTFractal astFractal, ExpressionContext context, String source) {
		this.astFractal = astFractal;
		this.context = context;
		this.source = source;
		initializeStack();
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("JavaScript");
		Map<String, Object> map = new HashMap<>();
		map.put("color", this);
		map.put("exp", new JavaScriptMath());
		Bindings bindings = new SimpleBindings(map);
		engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
	}

	@Override
	public void init() {
	}

	@Override
	public void render() {
		try {
		     engine.eval(source);
		} catch (ScriptException ex) {
		}
	}

	@Override
	protected MutableNumber[] createNumbers() {
		if (context == null) {
			return null;
		}
		return new MutableNumber[context.getNumberCount()];
	}
}
