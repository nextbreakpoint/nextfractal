package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTExpression;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;

public class CFStackNumber implements CFStackItem {
    private double number;

    public CFStackNumber(double number) {
        this.number = number;
    }

    public double getNumber() {
        return number;
    }

    @Override
    public void evalArgs(CFDGRenderer renderer, CFStackRule parent, CFStackItem[] dest, ASTExpression arguments, boolean onStack) {
//            AST.evalArgs();
    }

    @Override
    public ExpType getType() {
        return ExpType.NumericType;
    }

    @Override
    public int getTupleSize() {
        return 1;
    }
}
