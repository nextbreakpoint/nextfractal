/*
 * NextFractal 1.0.5
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class JavaScriptMath {
	public JavaScriptMath() {
	}

	public MutableNumber variable(Number x) {
		return new MutableNumber(x);
	}
	
	public MutableNumber variable(double x) {
		return new MutableNumber(x, 0);
	}
	
	public MutableNumber variable(double r, double i) {
		return new MutableNumber(r, i);
	}

	public Number number(Number x) {
		return new Number(x);
	}

	public Number number(double r) {
		return new Number(r, 0);
	}

	public Number number(double r, double i) {
		return new Number(r, i);
	}

	public double real(Number x) {
		return x.r();
	}

	public double real(double r) {
		return r;
	}

	public Number opAdd(MutableNumber out, Number a, Number b) {
		return out.set(a.r() + b.r(), a.i() + b.i());
	}

	public Number opSub(MutableNumber out, Number a, Number b) {
		return out.set(a.r() - b.r(), a.i() - b.i());
	}

	public Number opMul(MutableNumber out, Number a, Number b) {
		return out.set(a.r() * b.r() - a.i() * b.i(), a.r() * b.i() + a.i() * b.r());
	}

	public Number opDiv(MutableNumber out, Number a, Number b) {
		double m = b.r() * b.r() + b.i() * b.i();
		return out.set((a.r() * b.r() + a.i() * b.i()) / m, (-a.r() * b.i() + a.i() * b.r()) / m);
	}

	public Number opAdd(MutableNumber out, Number a, double b) {
		return out.set(a.r() + b, a.i());
	}

	public Number opSub(MutableNumber out, Number a, double b) {
		return out.set(a.r() - b, a.i());
	}

	public Number opMul(MutableNumber out, Number a, double b) {
		return out.set(a.r() * b, a.i() * b);
	}

	public Number opDiv(MutableNumber out, Number a, double b) {
		return out.set(a.r() / b, a.i() / b);
	}

	public Number opAdd(MutableNumber out, double a, Number b) {
		return out.set(a + b.r(), +b.i());
	}

	public Number opSub(MutableNumber out, double a, Number b) {
		return out.set(a - b.r(), -b.i());
	}

	public Number opMul(MutableNumber out, double a, Number b) {
		return out.set(a * b.r(), a * b.i());
	}
	
	public Number opPow(MutableNumber out, Number a, double b) {
		double m = Math.pow(Math.hypot(a.r(), a.i()), b);
		double f = Math.atan2(a.i(), a.r()) * b;
		return out.set(m * Math.cos(f), m * Math.sin(f));
	}
	
	public Number opNeg(MutableNumber out, Number a) {
		return out.set(-a.r(), -a.i());
	}
	
	public Number opPos(MutableNumber out, Number a) {
		return out.set(+a.r(), +a.i());
	}

	public double opAdd(double a, double b) {
		return a + b;
	}

	public double opSub(double a, double b) {
		return a - b;
	}

	public double opMul(double a, double b) {
		return a * b;
	}

	public double opDiv(double a, double b) {
		return a / b;
	}

	public double opPow(double a, double b) {
		return Math.pow(a, b);
	}

	public double funcMod(double x) {
		return Math.abs(x);
	}

	public double funcPha(double x) {
		return 0;
	}

	public double funcAcos(double x) {
		return Math.acos(x);
	}

	public double funcAsin(double x) {
		return Math.asin(x);
	}

	public double funcAtan(double x) {
		return Math.atan(x);
	}

	public double funcCos(double x) {
		return Math.cos(x);
	}

	public double funcSin(double x) {
		return Math.sin(x);
	}

	public double funcTan(double x) {
		return Math.tan(x);
	}

	public double funcExp(double x) {
		return Math.exp(x);
	}

	public double funcLog(double x) {
		return Math.log(x);
	}

	public double funcSqrt(double x) {
		return Math.sqrt(x);
	}

	public double funcAbs(double x) {
		return Math.abs(x);
	}

	public double funcRe(double x) {
		return x;
	}

	public double funcIm(double x) {
		return x;
	}

	public double funcCeil(double x) {
		return Math.ceil(x);
	}

	public double funcFloor(double x) {
		return Math.floor(x);
	}

	public double funcPow(double x, double y) {
		return Math.pow(x, y);
	}

	public double funcHypot(double x, double y) {
		return Math.hypot(x, y);
	}

	public double funcAtan2(double x, double y) {
		return Math.atan2(y, x);
	}

	public double funcMax(double x, double y) {
		return Math.max(y, x);
	}

	public double funcMin(double x, double y) {
		return Math.min(y, x);
	}

	public double funcMod(Number x) {
		return Math.hypot(x.r(), x.i());
	}

	public double funcMod2(Number x) {
		return Math.pow(x.r(), 2) + Math.pow(x.i(), 2);
	}

	public double funcPha(Number x) {
		return Math.atan2(x.i(), x.r());
	}

	public double funcRe(Number n) {
		return n.r();
	}

	public double funcIm(Number n) {
		return n.i();
	}

	public Number funcSin(MutableNumber out, Number x) {
		return out.set(Math.sin(x.r()) * Math.cosh(x.i()), +Math.cos(x.r()) * Math.sinh(x.i()));
	}

	public Number funcCos(MutableNumber out, Number x) {
		return out.set(Math.cos(x.r()) * Math.cosh(x.i()), -Math.sin(x.r()) * Math.sinh(x.i()));
	}

	public Number funcTan(MutableNumber out, Number x) {
		double d = Math.pow(Math.cos(x.r()), 2) + Math.pow(Math.sinh(x.i()), 2);
		return out.set((Math.sin(x.r()) * Math.cos(x.r())) / d, (Math.sinh(x.i()) * Math.cosh(x.i())) / d);
	}

	public Number funcExp(MutableNumber out, Number x) {
		double d = Math.exp(x.r());
		return out.set(d * Math.cos(x.i()), d * Math.sin(x.i()));
	}

	public Number funcPow(MutableNumber out, Number x, double e) {
		double d = Math.pow(Math.hypot(x.r(), x.i()), e);
		double f = Math.atan2(x.i(), x.r()) * e;
		return out.set(d * Math.cos(f), d * Math.sin(f));
	}

	public Number funcSqrt(MutableNumber out, Number x) {
		double d = Math.sqrt(Math.hypot(x.r(), x.i()));
		double f = Math.atan2(x.i(), x.r()) * 0.5;
		return out.set(d * Math.cos(f), d * Math.sin(f));
	}
	
	public Number[] numberArray(Number[] array) {
		return array;
	}
}
