package com.govermentscheme.backend.guidance.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class StepRequest {

    @NotNull
    @Min(1)
    private Integer stepNo;

    @NotEmpty
    private List<TranslationReq> translations;

    @Data
    public static class TranslationReq {
        @NotBlank
        @Pattern(regexp = "^(mr|hi|en)$")
        private String langCode;

        @NotBlank
        private String instruction;
    }
}