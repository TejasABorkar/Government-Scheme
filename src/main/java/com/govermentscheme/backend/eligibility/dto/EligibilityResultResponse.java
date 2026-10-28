package com.govermentscheme.backend.eligibility.dto;

import com.govermentscheme.backend.eligibility.entity.EligibilityResult;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder
public class EligibilityResultResponse {
    private Long schemeId;
    private String schemeName;
    private EligibilityResult result;
    private List<FailedRule> failedRules;
    private List<String> missingFields;
    private String notice;
    private LocalDateTime checkedAt;

    @Data @Builder
    public static class FailedRule {
        private String field;
        private String operator;
        private String expected;
        private Boolean isMandatory;
        private String message;
    }
}