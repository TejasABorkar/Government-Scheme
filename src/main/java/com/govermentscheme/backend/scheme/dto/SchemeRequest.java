package com.govermentscheme.backend.scheme.dto;

import com.govermentscheme.backend.scheme.entity.SchemeLevel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SchemeRequest {

    @NotNull(message = "Level is required")
    private SchemeLevel level;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @Size(max = 150)
    private String department;

    @NotBlank(message = "Official URL is required")
    @Pattern(regexp = "^https?://.+", message = "Must be a valid HTTP/HTTPS URL")
    private String officialUrl;

    @Size(max = 255)
    private String grReference;

    private LocalDate startDate;
    private LocalDate endDate;

    @NotEmpty(message = "At least one translation is required")
    @Valid
    private List<SchemeTranslationRequest> translations;
}