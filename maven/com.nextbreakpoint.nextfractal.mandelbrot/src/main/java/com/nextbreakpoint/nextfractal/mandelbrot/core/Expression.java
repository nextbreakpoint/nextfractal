/*
 * NextFractal 1.0
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
package com.nextbreakpoint.nextfractal.mandelbrot.core;

public class Expression {
	private Expression() {
	}
	
	public static Number number(int n) {
		return new Number(n, 0);
	}

	public static Number number(double r) {
		return new Number(r, 0);
	}

	public static Number number(double r, double i) {
		return new Number(r, i);
	}

	public static Number opAdd(Number a, Number b) {
		return new Number(a.r() + b.r(), a.i() + b.i());
	}

	public static Number opSub(Number a, Number b) {
		return new Number(a.r() - b.r(), a.i() - b.i());
	}

	public static Number opMul(Number a, Number b) {
		return new Number(a.r() * b.r() - a.i() * b.i(), a.r() * b.i() + a.i() * b.r());
	}

	public static Number opAdd(Number a, double b) {
		return new Number(a.r() + b, a.i());
	}

	public static Number opSub(Number a, double b) {
		return new Number(a.r() - b, a.i());
	}

	public static Number opMul(Number a, double b) {
		return new Number(a.r() * b, a.i() * b);
	}

	public static Number opDiv(Number a, double b) {
		return new Number(a.r() / b, a.i() / b);
	}

	public static Number opAdd(double a, Number b) {
		return new Number(a + b.r(), +b.i());
	}

	public static Number opSub(double a, Number b) {
		return new Number(a - b.r(), -b.i());
	}

	public static Number opMul(double a, Number b) {
		return new Number(a * b.r(), a * b.i());
	}

	public static Number opAdd(double a, double b) {
		return new Number(a + b, 0);
	}

	public static Number opSub(double a, double b) {
		return new Number(a - b, 0);
	}

	public static Number opMul(double a, double b) {
		return new Number(a * b, 0);
	}

	public static Number opPow(double a, double b) {
		return new Number(Math.pow(a, b), 0);
	}

	public static Number opNeg(Number a) {
		return new Number(-a.r(), -a.i());
	}

	public static Number opPos(Number a) {
		return new Number(+a.r(), +a.i());
	}

	public static double funcMod(double x) {
		return Math.abs(x);
	}

	public static double funcPha(double x) {
		return 0;
	}

	public static double funcAcos(double x) {
		return Math.acos(x);
	}

	public static double funcAsin(double x) {
		return Math.asin(x);
	}

	public static double funcAtan(double x) {
		return Math.atan(x);
	}

	public static double funcCos(double x) {
		return Math.cos(x);
	}

	public static double funcSin(double x) {
		return Math.sin(x);
	}

	public static double funcTan(double x) {
		return Math.tan(x);
	}

	public static double funcExp(double x) {
		return Math.exp(x);
	}

	public static double funcLog(double x) {
		return Math.log(x);
	}

	public static double funcSqrt(double x) {
		return Math.sqrt(x);
	}

	public static double funcPow(double x, double y) {
		return Math.pow(x, y);
	}

	public static double funcHypot(double x, double y) {
		return Math.hypot(x, y);
	}

	public static double funcAtan2(double x, double y) {
		return Math.atan2(x, y);
	}

	public static double funcMod(Number x) {
		return Math.hypot(x.r(), x.i());
	}

	public static double funcMod2(Number x) {
		return Math.pow(x.r(), 2) + Math.pow(x.i(), 2);
	}

	public static double funcPha(Number x) {
		return Math.atan2(x.i(), x.r());
	}

	public static Number funcSin(Number x) {
		return new Number(Math.sin(x.r()) * Math.cosh(x.i()), +Math.cos(x.r()) * Math.sinh(x.i()));
	}

	public static Number funcCos(Number x) {
		return new Number(Math.cos(x.r()) * Math.cosh(x.i()), -Math.sin(x.r()) * Math.sinh(x.i()));
	}

	public static Number funcTan(Number x) {
		double d = Math.pow(Math.cos(x.r()), 2) + Math.pow(Math.sinh(x.i()), 2);
		return new Number((Math.sin(x.r()) * Math.cos(x.r())) / d, (Math.sinh(x.i()) * Math.cosh(x.i())) / d);
	}

	public static Number funcExp(Number x) {
		double d = Math.exp(x.r());
		return new Number(d * Math.cos(x.i()), d * Math.sin(x.i()));
	}

	public static Number funcPow(Number x, double y) {
		double d = Math.pow(Math.hypot(x.r(), x.i()), y);
		return new Number(d * Math.cos(x.r() * y), d * Math.sin(x.i() * y));
	}

	public static Number funcSqrt(Number x) {
		double d = Math.sqrt(Math.hypot(x.r(), x.i()));
		return new Number(d * Math.cos(x.r() * 0.5), d * Math.sin(x.i() * 0.5));
	}

	public static double funcRe(Number n) {
		return n.r();
	}

	public static double funcIm(Number n) {
		return n.i();
	}
}
