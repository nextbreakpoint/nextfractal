package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.AST;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTExpression;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;

import java.util.List;

public class CFStackRule extends CFStackItem {
    private int ruleName;
    private int paramCount;

    public CFStackRule(CFStack stack, int ruleName, int paramCount) {
        super(stack);
        this.ruleName = ruleName;
        this.paramCount = paramCount;
    }

    public CFStackRule(CFStackRule rule) {
        super(rule.getStack());
        this.ruleName = rule.ruleName;
        this.paramCount = rule.paramCount;
    }

    @Override
    public ExpType getType() {
        return ExpType.RuleType;
    }

    @Override
    public int getTupleSize() {
        return 1;
    }

    public int getRuleName() {
        return ruleName;
    }

    public void setRuleName(int ruleName) {
        this.ruleName = ruleName;
    }

    public int getParamCount() {
        return paramCount;
    }

    public void setParamCount(int paramCount) {
        this.paramCount = paramCount;
    }

    @Override
    public void evalArgs(CFDGRenderer renderer, ASTExpression arguments, List<ASTParameter> parameters, boolean sequential) {
        AST.evalArgs(renderer, (CFStackRule)stack.getStackItem(stack.getStackTop()), iterator(), arguments, false);
    }

    @Override
    protected CFStackIterator iterator() {
        if (paramCount > 0) {
            return iterator(((CFStackParams)stack.getStackItem(stack.getStackTop() + 1)).getParams());
        }
        return super.iterator();
    }

    public void copyItems(CFStackItem[] items, int headerSize) {
        //TODO completare copyItems
        int destIndex = 0;
        for (int srcIndex = headerSize; srcIndex < items.length;) {
            switch (items[srcIndex].getType()) {
                case NumericType:
                case FlagType:
                case ModType:
                    System.arraycopy(items, srcIndex, stack.getStackItems(), destIndex, items[srcIndex].getTupleSize());
                    break;
                case RuleType:
                    stack.getStackItems()[destIndex] = items[srcIndex];
                    break;
                default:
                    break;
            }
            destIndex += items[srcIndex].getTupleSize();
        }
    }
}
