package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.AST;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTExpression;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import java.util.List;

public abstract class CFStackItem {
    protected CFStack stack;

    protected CFStackItem(CFStack stack) {
        this.stack = stack;
    }

    public void evalArgs(CFDGRenderer renderer, ASTExpression arguments, List<ASTParameter> parameters, boolean sequential) {
        AST.evalArgs(renderer, null, iterator(parameters), arguments, sequential);
    }

    public CFStack getStack() {
        return stack;
    }

    public abstract ExpType getType();

    public abstract int getTupleSize();

    protected CFStackIterator iterator(List<ASTParameter> parameters) {
        return new CFStackIterator(stack, parameters);
    }

    protected CFStackIterator iterator() {
        return new CFStackIterator(stack);
    }
}
