package com.nextbreakpoint.nextfractal.flux.grammar;


class ASTRegion {
	private ASTComplex a; 
	private ASTComplex b; 

	public ASTRegion(ASTComplex a, ASTComplex b) {
		this.a = a;
		this.b = b;
	}

	public ASTComplex getA() {
		return a;
	}

	public ASTComplex getB() {
		return b;
	}
}