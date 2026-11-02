package com.govermentscheme.backend.eligibility.operator;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NotInOperator implements Operator {
    @Override
    public RuleOperator getType() { return RuleOperator.NOT_IN; }

    @Override
    public boolean evaluate(Object actual, String expected) {
        if (actual == null) return false;
        Set<String> excluded = Arrays.stream(expected.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
        return !excluded.contains(actual.toString().toUpperCase());
    }
}