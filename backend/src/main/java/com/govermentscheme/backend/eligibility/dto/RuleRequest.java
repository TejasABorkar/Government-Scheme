package com.govermentscheme.backend.eligibility.dto;

import com.govermentscheme.backend.eligibility.entity.RuleOperator;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class RuleRequest {

    @NotBlank(message = "Field is required")
    private String field;

    @NotNull(message = "Operator is required")
    private RuleOperator operator;

    @NotBlank(message = "Value is required")
    private String value;

    private Boolean isMandatory = true;

    private Integer displayOrder = 0;

    @NotEmpty(message = "At least one translation required")
    private List<RuleTranslationRequest> translations;

    @Data
    public static class RuleTranslationRequest {
        @NotBlank
        @Pattern(regexp = "^(mr|hi|en)$")
        private String langCode;

        @NotBlank
        private String message;
    }
}