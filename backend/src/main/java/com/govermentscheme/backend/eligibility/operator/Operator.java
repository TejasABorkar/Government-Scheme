package com.govermentscheme.backend.eligibility.operator;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;

public interface Operator {
    RuleOperator getType();
    boolean evaluate(Object actual, String expectedValue);
}