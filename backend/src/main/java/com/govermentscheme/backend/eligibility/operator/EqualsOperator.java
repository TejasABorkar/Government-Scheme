package com.govermentscheme.backend.eligibility.operator;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;
import org.springframework.stereotype.Component;

@Component
public class EqualsOperator implements Operator {
    @Override
    public RuleOperator getType() { return RuleOperator.EQ; }

    @Override
    public boolean evaluate(Object actual, String expected) {
        if (actual == null) return false;
        return actual.toString().equalsIgnoreCase(expected.trim());
    }
}