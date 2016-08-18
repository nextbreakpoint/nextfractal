/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.grammar.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.nextbreakpoint.nextfractal.contextfree.core.Rand64;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGDriver;
import com.nextbreakpoint.nextfractal.contextfree.grammar.DeferUntilRuntimeException;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Modification;
import com.nextbreakpoint.nextfractal.contextfree.grammar.RTI;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.*;
import org.antlr.v4.runtime.Token;

public class ASTModification extends ASTExpression {
	public static final int SIZE = 9;
	private ModClass modClass;
	private Modification modData = new Modification();
	private List<ASTModTerm> modExp = new ArrayList<ASTModTerm>();
	private int entropyIndex;
	private boolean canonical;
	private CFDGDriver driver;

	public ASTModification(CFDGDriver driver, Token location) {
		super(true, false, ExpType.ModType, location);
		this.driver = driver;
		this.modClass = ModClass.NotAClass;
		this.entropyIndex = 0;
		this.canonical = true;
	}
	
	public ASTModification(CFDGDriver driver, ASTModification mod, Token location) {
		super(true, false, ExpType.ModType, location);
		this.driver = driver;
		if (mod != null) {
			modData.getRand64Seed().setSeed(0);
			grab(mod);
		} else {
			this.modClass = ModClass.NotAClass;
		}
	}

	public ASTModification(ASTModification mod) {
		super(true, false, ExpType.ModType, mod.getLocation());
		this.modClass = mod.modClass;
		this.entropyIndex = mod.entropyIndex;
		this.canonical = mod.canonical;
	}

	public Modification getModData() {
		return modData;
	}

	public ModClass getModClass() {
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
			term.evaluate(modData, false, rti);
		}
	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) {
		error("Improper evaluation of an adjustment expression");
		return -1;
	}

	@Override
	public void evaluate(Modification result, boolean shapeDest, RTI rti) {
		if (shapeDest) {
			result.concat(modData);
		} else {
			if (result.merge(modData)) {
				if (rti != null) rti.colorConflict(getLocation());
			}
		}
		for (ASTModTerm term : modExp) {
			term.evaluate(result, shapeDest, rti);
		}
	}

	protected void evalConst() {
		Map<ModType, ModClass> map = new HashMap<ModType, ModClass>();
		map.put(ModType.unknown, ModClass.classByOrdinal(ModClass.NotAClass.getType()));
		map.put(ModType.x, ModClass.classByOrdinal(ModClass.GeomClass.getType() | ModClass.PathOpClass.getType()));
		map.put(ModType.y, ModClass.classByOrdinal(ModClass.GeomClass.getType() | ModClass.PathOpClass.getType()));
		map.put(ModType.z, ModClass.ZClass);
		map.put(ModType.xyz, ModClass.classByOrdinal(ModClass.NotAClass.getType()));
		map.put(ModType.transform, ModClass.GeomClass);
		map.put(ModType.size, ModClass.GeomClass);
		map.put(ModType.sizexyz, ModClass.classByOrdinal(ModClass.GeomClass.getType() | ModClass.ZClass.getType()));
		map.put(ModType.rot, ModClass.classByOrdinal(ModClass.GeomClass.getType() | ModClass.PathOpClass.getType()));
		map.put(ModType.skew, ModClass.GeomClass);
		map.put(ModType.flip, ModClass.GeomClass);
		map.put(ModType.zsize, ModClass.ZClass);
		map.put(ModType.hue, ModClass.HueClass);
		map.put(ModType.sat, ModClass.SatClass);
		map.put(ModType.bright, ModClass.BrightClass);
		map.put(ModType.alpha, ModClass.AlphaClass);
		map.put(ModType.hueTarg, ModClass.HueClass);
		map.put(ModType.satTarg, ModClass.SatClass);
		map.put(ModType.brightTarg, ModClass.BrightClass);
		map.put(ModType.alphaTarg, ModClass.AlphaClass);
		map.put(ModType.targHue, ModClass.HueTargetClass);
		map.put(ModType.targSat, ModClass.SatTargetClass);
		map.put(ModType.targBright, ModClass.BrightTargetClass);
		map.put(ModType.targAlpha, ModClass.AlphaTargetClass);
		map.put(ModType.time, ModClass.TimeClass);
		map.put(ModType.timescale, ModClass.TimeClass);
		map.put(ModType.param, ModClass.ParamClass);
		map.put(ModType.x1, ModClass.PathOpClass);
		map.put(ModType.y1, ModClass.PathOpClass);
		map.put(ModType.x2, ModClass.PathOpClass);
		map.put(ModType.y2, ModClass.PathOpClass);
		map.put(ModType.xrad, ModClass.PathOpClass);
		map.put(ModType.yrad, ModClass.PathOpClass);
		map.put(ModType.modification, ModClass.InvalidClass);
		
		int nonConstant = 0;
		
		List<ASTModTerm> temp = new ArrayList<ASTModTerm>(modExp);
		modExp.clear();

		for (ASTModTerm term : temp) {
			ModClass mc = map.get(term.getModType());
			modClass = ModClass.classByOrdinal(modClass.getType() | mc.getType());
            if (!term.isConstant())
                nonConstant |= mc.getType();
			boolean keepThisOne = (mc.getType() & nonConstant) != 0;
			if (driver.isInPathContainer() && (mc.getType() & ModClass.ZClass.getType()) != 0) {
				error("Z changes are not supported within paths");
			}
			if (driver.isInPathContainer() && (mc.getType() & ModClass.TimeClass.getType()) != 0) {
				error("Time changes are not supported within paths");
			}
			try {
				if (!keepThisOne) {
					term.evaluate(modData, false, null); //TODO controllare
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
	public ASTExpression compile(CompilePhase ph) {
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
						if (term.getArguments() == null || term.getArguments().getType() != ExpType.NumericType) {
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
									if (term.getModType() == ModType.x && next.getModType() == ModType.y && argcount == 1) {
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
										term.setModType(term.getModType() == ModType.xyz ? ModType.x : ModType.size);
										term.setArgumentsCount(2);
										
		                                // Check if xy part is the identity transform and only save it if it is not
										ModType ztype = term.getModType() == ModType.size ? ModType.zsize : ModType.z;
										ASTModTerm zmod = new ASTModTerm(ztype, new ASTReal(d[2], location), location);
										zmod.setArgumentsCount(1);
										
										if (d[0] == 1.0 && d[1] == 1.0 && term.getModType() == ModType.size) {
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
										term.setModType(term.getModType() == ModType.xyz ? ModType.x : ModType.size);
										term.setArgumentsCount(2);
										
										ModType ztype = term.getModType() == ModType.size ? ModType.zsize : ModType.z;
										ASTModTerm zmod = new ASTModTerm(ztype, new ASTReal(d[2], location), location);
										zmod.setArgumentsCount(1);
										
										if (term.getModType() == ModType.size && xyargs.isConstant() && xyargs.evaluate(d, 2) == 2 && d[0] == 1.0 && d[1] == 1.0) {
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
					locality = Locality.PureLocal;
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
