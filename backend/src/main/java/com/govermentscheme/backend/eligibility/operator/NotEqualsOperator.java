package com.govermentscheme.backend.eligibility.operator;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;
import org.springframework.stereotype.Component;

@Component
public class NotEqualsOperator implements Operator {
    @Override
    public RuleOperator getType() { return RuleOperator.NE; }

    @Override
    public boolean evaluate(Object actual, String expected) {
        if (actual == null) return true;
        return !actual.toString().equalsIgnoreCase(expected.trim());
    }
}