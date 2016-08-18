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

import java.awt.geom.AffineTransform;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.core.Rand64;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGDriver;
import com.nextbreakpoint.nextfractal.contextfree.grammar.RTI;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Shape;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.RepElemType;
import org.antlr.v4.runtime.Token;

public class ASTTransform extends ASTReplacement {
	private ASTRepContainer body;
	private ASTExpression expHolder;
	private boolean clone;
	
	public ASTTransform(CFDGDriver driver, ASTExpression exp, Token location) {
		super(driver, null, RepElemType.empty, location);
		body = new ASTRepContainer(driver);
		this.expHolder = exp;
		this.clone = false;
	}
	
	public ASTRepContainer getBody() {
		return body;
	}
	
	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}

	public ASTExpression getExpHolder() {
		return expHolder;
	}

	public void setExpHolder(ASTExpression expHolder) {
		this.expHolder = expHolder;
	}

	@Override
	public void compile(CompilePhase ph) {
		super.compile(ph);
		ASTExpression ret = null;
		if (expHolder != null) {
			ret = expHolder.compile(ph);
		}
		if (ret != null) {
			error("Error analyzing transform list");
		}
		body.compile(ph, null, null);
		
		switch (ph) {
			case TypeCheck: 
				if (clone && !ASTParameter.Impure) {
					error("Shape cloning only permitted in impure mode");
				}
				break;
	
			case Simplify:
				if (expHolder != null) {
					expHolder.simplify();
				}
				break;
	
			default:
				break;
		}
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		AffineTransform[] dummy = new AffineTransform[1];
		@SuppressWarnings("unchecked")
		List<AffineTransform>[] transforms = new List[1];
		List<ASTModification> mods = getTransforms(expHolder, transforms, rti, false, dummy);
		Rand64 cloneSeed = rti.getCurrentSeed();
		Shape transChild = parent;
		boolean opsOnly = body.getRepType() == RepElemType.op.getType();
		if (opsOnly && !tr) {
			transChild.getWorldState().setTransform(null);
		}
		int modsLength = mods.size();
		int totalLength = modsLength + transforms[0].size();
		for (int i = 0; i < totalLength; i++) {
			Shape child = transChild;
			if (i < modsLength) {
				mods.get(i).evaluate(child.getWorldState(), true, rti);
			} else {
				child.getWorldState().getTransform().preConcatenate(transforms[0].get(i - modsLength));
			}
			rti.getCurrentSeed().bump();
			int size = rti.getStackSize();
			for (ASTReplacement rep : body.getBody()) {
				if (clone) {
					rti.setCurrentSeed(cloneSeed);
				}
				rep.traverse(child, tr || opsOnly, rti);
			}
			rti.unwindStack(size, body.getParameters());
		}
	}

	private List<ASTModification> getTransforms(ASTExpression exp, List<AffineTransform>[] syms, RTI rti, boolean tiled, AffineTransform[] tile) {
		// TODO Auto-generated method stub
		return null;
	}
}
