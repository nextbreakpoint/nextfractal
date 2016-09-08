package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import java.util.List;

public class CFStackParams extends CFStackItem {
    private List<ASTParameter> params;

    public CFStackParams(CFStack stack, List<ASTParameter> params) {
        super(stack);
        this.params = params;
    }

    public List<ASTParameter> getParams() {
        return params;
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
