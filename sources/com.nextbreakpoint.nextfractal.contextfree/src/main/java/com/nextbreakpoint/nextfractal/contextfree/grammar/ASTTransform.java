package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.awt.geom.AffineTransform;
import java.util.List;

import org.antlr.v4.runtime.Token;

class ASTTransform extends ASTReplacement {
	private ASTRepContainer body = new ASTRepContainer();
	private ASTExpression expHolder;
	private boolean clone;
	
	public ASTTransform(ASTExpression exp, Token location) {
		super(null, ERepElemType.empty, location);
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
	public void compile(ECompilePhase ph) {
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
		boolean opsOnly = body.getRepType() == ERepElemType.op.getType();
		if (opsOnly && !tr) {
			transChild.getWorldState().setTransform(null);
		}
		int modsLength = mods.size();
		int totalLength = modsLength + transforms[0].size();
		for (int i = 0; i < totalLength; i++) {
			Shape child = transChild;
			if (i < modsLength) {
				Modification[] mod = new Modification[1];
				mods.get(i).evaluate(mod, true, rti);
				child.setWorldState(mod[0]);
			} else {
				child.getWorldState().getTransform().preConcatenate(transforms[0].get(i - modsLength));
			}
			rti.getCurrentSeed().bump();int s = rti.getCFStack().size();
			for (ASTReplacement rep : body.getBody()) {
				if (clone) {
					rti.setCurrentSeed(cloneSeed);
				}
				rep.traverse(child, tr || opsOnly, rti);
			}
			rti.unwindStack(s, body.getParameters());
		}
	}

	private List<ASTModification> getTransforms(ASTExpression exp, List<AffineTransform>[] syms, RTI rti, boolean tiled, AffineTransform[] tile) {
		// TODO Auto-generated method stub
		return null;
	}
}