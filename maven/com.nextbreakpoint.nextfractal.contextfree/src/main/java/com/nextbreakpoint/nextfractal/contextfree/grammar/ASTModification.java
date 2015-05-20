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
package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.antlr.v4.runtime.Token;

class ASTModification extends ASTExpression {
	public static final int SIZE = 9;
	private EModClass modClass;
	private Modification modData = new Modification();
	private List<ASTModTerm> modExp = new ArrayList<ASTModTerm>();
	private int entropyIndex;
	private boolean canonical;
	
	public ASTModification(Token location) {
		super(true, false, EExpType.ModType, location);
		this.modClass = EModClass.NotAClass;
		this.entropyIndex = 0;
		this.canonical = true;
	}
	
	public ASTModification(ASTModification mod, Token location) {
		super(true, false, EExpType.ModType, location);
		if (mod != null) {
			modData.getRand64Seed().setSeed(0);
			grab(mod);
		} else {
			this.modClass = EModClass.NotAClass;
		}
	}

	public ASTModification(ASTModification mod) {
		super(true, false, EExpType.ModType, mod.getLocation());
		this.modClass = mod.modClass;
		this.entropyIndex = mod.entropyIndex;
		this.canonical = mod.canonical;
	}

	public Modification getModData() {
		return modData;
	}

	public EModClass getModClass() {
		return modClass;
	}

	public List<ASTModTerm> getModExp() {
		return modExp;
	}

	public void setIsConstant(boolean isConstant) {
		this.isConstant = isConstant;
	}

	public void setCanonical(boolean canonical) {
		this.canonical = canonical;
	}

	public int getEntropyIndex() {
		return entropyIndex;
	}
	
	public void setEntropyIndex(int entropyIndex) {
		this.entropyIndex = entropyIndex;
	}

	public boolean isCanonial() {
		return canonical;
	}
	
	public void grab(ASTModification mod) {
		Rand64 oldEntropy = modData.getRand64Seed();
		modData = mod.getModData();
		modData.getRand64Seed().add(oldEntropy);
		modExp.clear();
		modExp.addAll(mod.getModExp());
		modClass = mod.getModClass();
		entropyIndex = (entropyIndex + mod.getEntropyIndex()) & 7;
		isConstant = modExp.isEmpty();
		canonical = mod.isCanonial();
	}

	public void makeCanonial() {
	    // Receive a vector of modification terms and return an ASTexpression with
	    // those terms rearranged into TRSSF canonical order. Duplicate terms are
	    // deleted with a warning.
		List<ASTModTerm> temp = new ArrayList<ASTModTerm>(modExp);
		modExp.clear();
		
		ASTModTerm x = null;
		ASTModTerm y = null;
		ASTModTerm z = null;
		ASTModTerm rot = null;
		ASTModTerm skew = null;
		ASTModTerm size = null;
		ASTModTerm zsize = null;
		ASTModTerm flip = null;
		ASTModTerm xform = null;
		
		for (ASTModTerm term : temp) {
			switch (term.getModType()) {
				case x:
					x = term;
					break;
	
				case y:
					y = term;
					break;
	
				case z:
					z = term;
					break;
	
				case modification:
				case transform:
					xform = term;
					break;
	
				case rot:
					rot = term;
					break;
	
				case size:
					size = term;
					break;
	
				case zsize:
					zsize = term;
					break;
	
				case skew:
					skew = term;
					break;
	
				case flip:
					flip = term;
					break;
	
				default:
					modExp.add(term);
					break;
			}
		}
		
		if (x != null) modExp.add(x); 
		if (y != null) modExp.add(y); 
		if (z != null) modExp.add(z); 
		if (rot != null) modExp.add(rot); 
		if (size != null) modExp.add(size); 
		if (zsize != null) modExp.add(zsize); 
		if (skew != null) modExp.add(skew); 
		if (flip != null) modExp.add(flip); 
		if (xform != null) modExp.add(xform); 
	}

	public void addEntropy(String name) {
		int[] index = new int[1];
		modData.getRand64Seed().xorString(name, index);
		entropyIndex = index[0];
	}

	public void setVal(Modification[] mod, RTI rti) {
        mod[0] = modData;
		for (ASTModTerm term : modExp) {
			term.evaluate(mod, false, rti);
		}
	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) {
		error("Improper evaluation of an adjustment expression");
		return -1;
	}

	@Override
	public void evaluate(Modification[] result, boolean shapeDest, RTI rti) {
		if (shapeDest) {
			result[0].concat(modData);
		} else {
			if (result[0].merge(modData)) {
				if (rti != null) rti.colorConflict();
			}
		}
		for (ASTModTerm term : modExp) {
			term.evaluate(result, shapeDest, rti);
		}
	}

	protected void evalConst() {
		Map<EModType, EModClass> map = new HashMap<EModType, EModClass>();
		map.put(EModType.unknown, EModClass.classByOrdinal(EModClass.NotAClass.getType()));
		map.put(EModType.x, EModClass.classByOrdinal(EModClass.GeomClass.getType() | EModClass.PathOpClass.getType()));
		map.put(EModType.y, EModClass.classByOrdinal(EModClass.GeomClass.getType() | EModClass.PathOpClass.getType()));
		map.put(EModType.z, EModClass.ZClass);
		map.put(EModType.xyz, EModClass.classByOrdinal(EModClass.NotAClass.getType()));
		map.put(EModType.transform, EModClass.GeomClass);
		map.put(EModType.size, EModClass.GeomClass);
		map.put(EModType.sizexyz, EModClass.classByOrdinal(EModClass.GeomClass.getType() | EModClass.ZClass.getType()));
		map.put(EModType.rot, EModClass.classByOrdinal(EModClass.GeomClass.getType() | EModClass.PathOpClass.getType()));
		map.put(EModType.skew, EModClass.GeomClass);
		map.put(EModType.flip, EModClass.GeomClass);
		map.put(EModType.zsize, EModClass.ZClass);
		map.put(EModType.hue, EModClass.HueClass);
		map.put(EModType.sat, EModClass.SatClass);
		map.put(EModType.bright, EModClass.BrightClass);
		map.put(EModType.alpha, EModClass.AlphaClass);
		map.put(EModType.hueTarg, EModClass.HueClass);
		map.put(EModType.satTarg, EModClass.SatClass);
		map.put(EModType.brightTarg, EModClass.BrightClass);
		map.put(EModType.alphaTarg, EModClass.AlphaClass);
		map.put(EModType.targHue, EModClass.HueTargetClass);
		map.put(EModType.targSat, EModClass.SatTargetClass);
		map.put(EModType.targBright, EModClass.BrightTargetClass);
		map.put(EModType.targAlpha, EModClass.AlphaTargetClass);
		map.put(EModType.time, EModClass.TimeClass);
		map.put(EModType.timescale, EModClass.TimeClass);
		map.put(EModType.param, EModClass.ParamClass);
		map.put(EModType.x1, EModClass.PathOpClass);
		map.put(EModType.y1, EModClass.PathOpClass);
		map.put(EModType.x2, EModClass.PathOpClass);
		map.put(EModType.y2, EModClass.PathOpClass);
		map.put(EModType.xrad, EModClass.PathOpClass);
		map.put(EModType.yrad, EModClass.PathOpClass);
		map.put(EModType.modification, EModClass.InvalidClass);
		
		int nonConstant = 0;
		
		List<ASTModTerm> temp = new ArrayList<ASTModTerm>(modExp);
		modExp.clear();

		for (ASTModTerm term : temp) {
			EModClass mc = map.get(term.getModType());
			modClass = EModClass.classByOrdinal(modClass.getType() | mc.getType());
            if (!term.isConstant())
                nonConstant |= mc.getType();
			boolean keepThisOne = (mc.getType() & nonConstant) != 0;
			if (Builder.currentBuilder().isInPathContainer() && (mc.getType() & EModClass.ZClass.getType()) != 0) {
				error("Z changes are not supported within paths");
			}
			if (Builder.currentBuilder().isInPathContainer() && (mc.getType() & EModClass.TimeClass.getType()) != 0) {
				error("Time changes are not supported within paths");
			}
			try {
				if (!keepThisOne) {
					Modification[] mod = new Modification[1];
					term.evaluate(mod, false, null);
					modData = mod[0];
				}
			} catch (DeferUntilRuntimeException e) {
				keepThisOne = true;
			}
			if (keepThisOne) {
				if (term.getArguments() != null) {
					term.getArguments().simplify();
				}
				modExp.add(term);
			}
		}
	}

	@Override
	public ASTExpression simplify() {
		evalConst();
		return this;
	}

	@Override
	public ASTExpression compile(ECompilePhase ph) {
		for (ASTModTerm term : modExp) {
			term.compile(ph);
		}
		
		switch (ph) {
			case TypeCheck:
				{
					List<ASTModTerm> temp = new ArrayList<ASTModTerm>(modExp);
					modExp.clear();

					for (ListIterator<ASTModTerm> iterm = temp.listIterator(); iterm.hasNext();) {
						ASTModTerm term = iterm.next();
						if (term.getArguments() == null || term.getArguments().getType() != EExpType.NumericType) {
							modExp.add(term);
						}
						int argcount = term.getArguments().evaluate(null, 0);
						switch (term.getModType()) {
							case x:
							case y:
								{
									if (!iterm.hasNext()) {
										break;
									}
									ASTModTerm next = iterm.next();
									if (term.getModType() == EModType.x && next.getModType() == EModType.y && argcount == 1) {
										term.setArguments(term.getArguments().append(next));
										term.setArgumentsCount(2);
										modExp.add(term);
										continue;
									}
								}
								break;

	                            // Try to split the XYZ term into an XY term and a Z term. Drop the XY term
	                            // if it is the identity. First try an all-constant route, then try to tease
	                            // apart the arguments.
							case xyz:
							case sizexyz:
								{
									double[] d = new double[3];
									if (term.getArguments().isConstant() && term.getArguments().evaluate(d, 3) != 3) {
										term.setArguments(new ASTCons(location, new ASTReal(d[0], location), new ASTReal(d[1], location)));
										term.setModType(term.getModType() == EModType.xyz ? EModType.x : EModType.size);
										term.setArgumentsCount(2);
										
		                                // Check if xy part is the identity transform and only save it if it is not
										EModType ztype = term.getModType() == EModType.size ? EModType.zsize : EModType.z;
										ASTModTerm zmod = new ASTModTerm(ztype, new ASTReal(d[2], location), location);
										zmod.setArgumentsCount(1);
										
										if (d[0] == 1.0 && d[1] == 1.0 && term.getModType() == EModType.size) {
		                                    // Drop xy term and just save z term if xy term
		                                    // is the identity transform
											term.setArguments(zmod);
										} else {
											modExp.add(zmod);
										}
										modExp.add(term);
										continue;
									}
									
									List<ASTExpression> xyzargs = extract(term.getArguments());
									ASTExpression xyargs = null;
									ASTExpression zargs = null;
									
									for (ASTExpression arg : xyzargs) {
										if (xyargs == null || xyargs.evaluate(null, 0) < 2) {
											xyargs = append(xyargs, arg);
										} else {
											zargs = append(zargs, arg);
										}
									}
									
									if (xyargs != null && zargs != null && xyargs.evaluate(null, 0) == 2) {
		                                // We have successfully split the 3-tuple into a 2-tuple and a scalar
										term.setArguments(xyargs);
										term.setModType(term.getModType() == EModType.xyz ? EModType.x : EModType.size);
										term.setArgumentsCount(2);
										
										EModType ztype = term.getModType() == EModType.size ? EModType.zsize : EModType.z;
										ASTModTerm zmod = new ASTModTerm(ztype, new ASTReal(d[2], location), location);
										zmod.setArgumentsCount(1);
										
										if (term.getModType() == EModType.size && xyargs.isConstant() && xyargs.evaluate(d, 2) == 2 && d[0] == 1.0 && d[1] == 1.0) {
		                                    // Drop xy term and just save z term if xy term
		                                    // is the identity transform
											term.setArguments(zmod);
										} else {
											modExp.add(zmod);
										}
									} else {
										// No dice, put it all back
										xyargs = append(xyargs, zargs);
										term.setArguments(xyargs);
									}
									modExp.add(term);
								}
								continue;
								
							default:
								break;
						}
						modExp.add(term);
					}
					
					isConstant = true;
					locality = ELocality.PureLocal;
					for (ASTModTerm term : modExp) {
						isConstant = isConstant && term.isConstant();
						locality = combineLocality(locality, term.getLocality());
						StringBuilder ent = new StringBuilder();
						term.entropy(ent);
						addEntropy(ent.toString());
					}
					
					if (canonical) {
						makeCanonial();
					}
				}
				break;
	
			case Simplify: 
				break;

			default:
				break;
		}
		return null;
	}

	public void concat(ASTModTerm t) {
		// TODO Auto-generated method stub
		
	}
}
