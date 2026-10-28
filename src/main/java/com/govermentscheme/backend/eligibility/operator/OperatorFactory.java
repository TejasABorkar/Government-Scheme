package com.govermentscheme.backend.eligibility.operator;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class OperatorFactory {

    private final Map<RuleOperator, Operator> operators = new EnumMap<>(RuleOperator.class);

    public OperatorFactory(List<Operator> operatorList) {
        for (Operator op : operatorList) {
            operators.put(op.getType(), op);
        }
    }

    public Operator get(RuleOperator type) {
        return operators.get(type);
    }
}