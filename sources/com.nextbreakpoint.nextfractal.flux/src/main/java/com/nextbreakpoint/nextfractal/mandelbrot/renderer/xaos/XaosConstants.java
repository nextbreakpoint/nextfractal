package com.nextbreakpoint.nextfractal.mandelbrot.renderer.xaos;

class XaosConstants {
	public static final boolean DUMP = false;
	public static final boolean DUMP_XAOS = false;
	public static final boolean SHOW_REFRESH = false;
	public static final boolean SHOW_SYMETRY = false;
	public static final boolean SHOW_CALCULATE = false;
	public static final boolean SHOW_SOLIDGUESS = false;
	public static final boolean PRINT_REALLOCTABLE = false;
	public static final boolean PRINT_CALCULATE = false;
	public static final boolean PRINT_POSITIONS = false;
	public static final boolean PRINT_MOVETABLE = false;
	public static final boolean PRINT_FILLTABLE = false;
	public static final boolean PRINT_MULTABLE = false;
	public static final boolean PRINT_REGION = true;
	public static final boolean PRINT_ONLYNEW = true;
	public static final boolean USE_XAOS = true; 
	public static final boolean USE_SYMETRY = true;
	public static final boolean USE_SOLIDGUESS = true;
	public static final boolean USE_MULTITHREAD = true;
	public static final int GUESS_RANGE = 4;
	public static final int RANGES = 2;
	public static final int RANGE = 4;
	public static final int STEPS = 8;
	public static final int MASK = 0x7;
	public static final int DSIZE = (RANGES + 1);
	public static final int FPMUL = 64;
	public static final int FPRANGE = FPMUL * RANGE;
	public static final int MAX_PRICE = Integer.MAX_VALUE;
	public static final int NEW_PRICE = FPRANGE * FPRANGE;
	public static final int[] MULTABLE = new int[FPRANGE * 2];
	static {
		for (int i = -FPRANGE; i < FPRANGE; i++) {
			MULTABLE[FPRANGE + i] = i * i;
		}
	}
}

