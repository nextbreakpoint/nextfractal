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

    public void copyTo(CFStackItem[] dest, int destOffset) {
        int destIndex = destOffset;
        for (int srcIndex = 0; srcIndex < paramCount; srcIndex++) {
            switch (stack.getStackItem(srcIndex).getType()) {
                case NumericType:
                case FlagType:
                case ModType:
                    System.arraycopy(stack.getStackItems(), srcIndex, dest, destIndex, stack.getStackItem(srcIndex).getTupleSize());
                    break;
                case RuleType:
                    dest[destIndex] = stack.getStackItem(srcIndex);
                    break;
                default:
                    break;
            }
            destIndex += stack.getStackItem(srcIndex).getTupleSize();
        }
    }
}
