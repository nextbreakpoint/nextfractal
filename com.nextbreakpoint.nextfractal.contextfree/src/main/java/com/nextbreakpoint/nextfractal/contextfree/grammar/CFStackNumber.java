package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;

public class CFStackNumber extends CFStackItem {
    private double number;

    public CFStackNumber(CFStack stack, double number) {
        super(stack);
        this.number = number;
    }

    public double getNumber() {
        return number;
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
