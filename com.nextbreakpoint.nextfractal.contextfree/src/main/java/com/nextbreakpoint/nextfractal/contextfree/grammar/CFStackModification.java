package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;

public class CFStackModification extends CFStackItem {
    private Modification modification;

    public CFStackModification(CFStack stack, Modification modification) {
        super(stack);
        this.modification = modification;
    }

    public Modification getModification() {
        return modification;
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
