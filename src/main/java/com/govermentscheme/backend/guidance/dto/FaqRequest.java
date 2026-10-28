package com.govermentscheme.backend.guidance.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class FaqRequest {

    private Integer displayOrder = 0;

    @NotEmpty
    private List<TranslationReq> translations;

    @Data
    public static class TranslationReq {
        @NotBlank
        @Pattern(regexp = "^(mr|hi|en)$")
        private String langCode;

        @NotBlank
        private String question;

        @NotBlank
        private String answer;
    }
}