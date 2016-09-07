package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTExpression;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;

public class CFStackModification implements CFStackItem {
    private Modification modification;

    public CFStackModification(Modification modification) {
        this.modification = modification;
    }

    public Modification getModification() {
        return modification;
    }

    @Override
    public void evalArgs(CFDGRenderer renderer, CFStackRule parent, CFStackItem[] dest, ASTExpression arguments, boolean onStack) {

    }

    @Override
    public ExpType getType() {
        return ExpType.ModType;
    }

    @Override
    public int getTupleSize() {
        return 1;
    }
}
