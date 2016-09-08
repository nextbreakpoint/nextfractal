package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;

import java.util.ArrayList;
import java.util.List;

public class CFStackIterator {
    private int stackPos;
    private int paramPos;
    private CFStack stack;
    private List<ASTParameter> params;

    public CFStackIterator(CFStack stack, List<ASTParameter> parameters) {
        this.stack = stack;
        this.params = new ArrayList<>(parameters);
        this.paramPos = 0;
        this.stackPos = 0;
    }

    public CFStackIterator(CFStack stack) {
        this.stack = stack;
        this.params = new ArrayList<>();
        this.paramPos = 0;
        this.stackPos = 0;
    }

    public CFStackIterator(CFStackIterator iterator) {
        this.stack = iterator.stack;
        this.params = iterator.params;
        this.stackPos = iterator.stackPos;
        this.paramPos = iterator.paramPos;
    }

    public ASTParameter getType() {
        return params.get(paramPos);
    }

    public CFStackItem getItem() {
        return stack.getStackItem(stackPos);
    }

    public void setItem(int index, CFStackItem item) {
        stack.setStackItem(stackPos + index, item);
    }

    public CFStackIterator next() {
        if (paramPos >= params.size()) {
            return null;
        }
        ASTParameter next = params.get(paramPos);
        stackPos += next.getTupleSize();
        paramPos += 1;
        return this;
    }
}
