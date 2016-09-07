package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTExpression;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import java.util.List;

public class CFStackParams implements CFStackItem {
    private List<ASTParameter> params;

    public CFStackParams(List<ASTParameter> params) {
        this.params = params;
    }

    public List<ASTParameter> getParams() {
        return params;
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
        return params.size();
    }
}
