package com.nextbreakpoint.nextfractal.contextfree.parser;

import org.antlr.v4.runtime.Token;

class ASTLet extends ASTUserFunction {
	private ASTRepContainer definitions;
	
	public ASTLet(ASTRepContainer args, ASTDefine func, Token location) {
		super(-1, null, func, location);
		this.definitions = args;
		isLet = true;
	}

	@Override
	public ASTExpression simplify() {
		getDefinition().compile(ECompilePhase.Simplify);
		if (isConstant()) {
			StringBuilder e = new StringBuilder();
			entropy(e);
			ASTParameter p = new ASTParameter(-1, getDefinition(), location);
			p.setDefinition(getDefinition());
			ASTExpression ret = p.constCopy(e.toString());
			if (ret != null) {
				return ret;
			}
		} else if (getArguments() == null) {
			ASTExpression ret = getDefinition().getExp();
			return ret;
		}
		return super.simplify();
	}

	@Override
	public ASTExpression compile(ECompilePhase ph) {
		switch (ph) {
			case TypeCheck: 
				{
					definitions.compile(ph, null, getDefinition());
					ASTExpression args = null;
					for (ASTReplacement rep : definitions.getBody()) {
						if (rep instanceof ASTDefine) {
							ASTDefine def = (ASTDefine)rep;
							if (def.getDefineType() == EDefineType.StackDefine) {
								getDefinition().incStackCount(def.getTupleSize());
								args = ASTExpression.append(args, def.getExp());
							}
						}
					}
					definitions.getParameters().remove(definitions.getParameters().size() - 1);
					for (ASTParameter param : definitions.getParameters()) {
						if (param.getDefinition() != null) {
							getDefinition().getParameters().add(param);
						}
					}
					definitions = null;//TODO controllare
					setArguments(args);
					isConstant = getArguments() == null && getDefinition().getExp().isConstant();
					isNatural = getDefinition().isNatural();
					locality = getDefinition().getExp() != null ? getDefinition().getExp().getLocality() : getDefinition().getChildChange().getLocality();
					type = getDefinition().getExpType();
				}
				break;
	
			case Simplify: 
				break;
	
			default:
				break;
		}
		return null;
	}
}