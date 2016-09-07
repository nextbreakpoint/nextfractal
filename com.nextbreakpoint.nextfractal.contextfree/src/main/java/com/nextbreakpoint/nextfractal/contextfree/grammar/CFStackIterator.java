package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CFStackIterator {
    private int stackPos;
    private int paramPos;
    private List<CFStackItem> stack;
    private List<ASTParameter> params;

    public CFStackIterator() {
        this.stack = new ArrayList<>();
        this.stackPos = 0;
        this.params = new ArrayList<>();
        this.paramPos = 0;
    }

    public CFStackIterator(CFStackItem[] stack, List<ASTParameter> params) {
        this.stack = Arrays.asList(stack);
        this.stackPos = 0;
        this.params = params;
        this.paramPos = 0;
    }

    public CFStackIterator(CFStackIterator iterator) {
        this.stack = iterator.stack;
        this.stackPos = iterator.stackPos;
        this.params = iterator.params;
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

    public ASTParameter getType() {
        return params.get(paramPos);
    }

    public CFStackItem getItem() {
        return stack.get(stackPos);
    }
}
