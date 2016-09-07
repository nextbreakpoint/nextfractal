package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTExpression;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;

public interface CFStackItem {
    public void evalArgs(CFDGRenderer renderer, CFStackRule parent, CFStackItem[] dest, ASTExpression arguments, boolean onStack);

    public ExpType getType();

    public int getTupleSize();
}
