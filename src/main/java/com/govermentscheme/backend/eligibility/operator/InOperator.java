package com.govermentscheme.backend.eligibility.operator;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InOperator implements Operator {
    @Override
    public RuleOperator getType() { return RuleOperator.IN; }

    @Override
    public boolean evaluate(Object actual, String expected) {
        if (actual == null) return false;
        // expected format: "SC,ST,OBC"
        Set<String> allowed = Arrays.stream(expected.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
        return allowed.contains(actual.toString().toUpperCase());
    }
}