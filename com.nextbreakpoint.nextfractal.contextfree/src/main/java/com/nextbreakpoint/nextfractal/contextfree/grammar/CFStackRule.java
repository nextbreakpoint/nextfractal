package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTExpression;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;

import java.util.List;

public class CFStackRule implements CFStackItem {
    private int ruleName;
    private int paramCount;
    private List<ASTParameter> params;

    public CFStackRule(int ruleName, int paramCount, List<ASTParameter> params) {
        this.ruleName = ruleName;
        this.paramCount = paramCount;
        this.params = params;
    }

    public CFStackRule(CFStackRule rule) {
        this.ruleName = rule.ruleName;
        this.paramCount = rule.paramCount;
        this.params = rule.params;
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

    public List<ASTParameter> getParams() {
        return params;
    }

    public void setParams(List<ASTParameter> params) {
        this.params = params;
    }

    @Override
    public void evalArgs(CFDGRenderer renderer, CFStackRule parent, CFStackItem[] dest, ASTExpression arguments, boolean onStack) {

    }

    @Override
    public ExpType getType() {
        return ExpType.RuleType;
    }

    @Override
    public int getTupleSize() {
        return 1;
    }
}
