package com.govermentscheme.backend.scheme.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SchemeTranslationRequest {

    @NotBlank(message = "Language code is required")
    @Pattern(regexp = "^(mr|hi|en)$", message = "Language must be mr, hi, or en")
    private String langCode;

    @NotBlank(message = "Scheme name is required")
    @Size(max = 255)
    private String name;

    private String description;
    private String benefits;
    private String eligibilitySummary;
}