package com.govermentscheme.backend.eligibility.dto;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data @Builder
public class RuleResponse {
    private Long id;
    private String field;
    private RuleOperator operator;
    private String value;
    private Boolean isMandatory;
    private Integer displayOrder;
    private List<TranslationInfo> translations;

    @Data @Builder
    public static class TranslationInfo {
        private String langCode;
        private String message;
    }
}