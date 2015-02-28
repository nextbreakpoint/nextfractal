package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.Random;

class Rand64 {
	private static long RAND64_SEED  = 0x3DF41234;

    private long seed;
    private Random random = new Random();
//    private static final Rand64 common = new Rand64();

	public Rand64() {
		this(RAND64_SEED);
	}

	public Rand64(long seed) {
		this.seed = seed;
		random.setSeed(seed);
	}

	public double getDouble() {
		return random.nextDouble();
	}
	
	public long getLong() {
		return random.nextLong();
	}
	
	public long getPositive() {
		return random.nextLong();
	}
	
	public long getUnsigned() {
		return random.nextLong();
	}
    
	public void setSeed(long seed) {
		this.seed = seed;
		random.setSeed(this.seed);
	}
	
	public void init() {
		setSeed(RAND64_SEED);
	}
    
	public void xorString(String t, int[] i) {
	    for (int j = 0; j < t.length(); j++) {
	        xorChar(t.charAt(j), j);
	        bump();
	        i[0] = (i[0] + 1) & 7;
	    }
	}
    
    public void xorChar(char c, long i) {
        seed ^= ((long)c) << (i * 8);
	}
    
    public void bump() {
	}

	public void add(Rand64 oldEntropy) {
		// TODO Auto-generated method stub
		
	}
}