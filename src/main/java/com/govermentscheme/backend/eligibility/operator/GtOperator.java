package com.govermentscheme.backend.eligibility.operator;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GtOperator implements Operator {
    @Override
    public RuleOperator getType() { return RuleOperator.GT; }

    @Override
    public boolean evaluate(Object actual, String expected) {
        if (actual == null) return false;
        BigDecimal a = new BigDecimal(actual.toString());
        BigDecimal e = new BigDecimal(expected.trim());
        return a.compareTo(e) > 0;
    }
}