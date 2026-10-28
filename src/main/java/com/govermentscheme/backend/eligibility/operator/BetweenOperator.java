package com.govermentscheme.backend.eligibility.operator;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BetweenOperator implements Operator {
    @Override
    public RuleOperator getType() { return RuleOperator.BETWEEN; }

    @Override
    public boolean evaluate(Object actual, String expected) {
        if (actual == null) return false;
        // expected format: "min,max" e.g. "1,5"
        String[] parts = expected.split(",");
        if (parts.length != 2) throw new IllegalArgumentException("BETWEEN expects 'min,max'");
        BigDecimal a = new BigDecimal(actual.toString());
        BigDecimal min = new BigDecimal(parts[0].trim());
        BigDecimal max = new BigDecimal(parts[1].trim());
        return a.compareTo(min) >= 0 && a.compareTo(max) <= 0;
    }
}