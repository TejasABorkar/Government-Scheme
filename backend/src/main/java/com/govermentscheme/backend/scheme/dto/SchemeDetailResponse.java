package com.govermentscheme.backend.scheme.dto;

import com.govermentscheme.backend.scheme.entity.SchemeLevel;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data @Builder
public class SchemeDetailResponse {
    private Long id;
    private String name;
    private String description;
    private String benefits;
    private String eligibilitySummary;
    private SchemeLevel level;
    private String categoryName;
    private String department;
    private String officialUrl;
    private String grReference;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private Boolean isExpired;
    private LocalDate lastVerifiedAt;
    private String disclaimer;             // SRS SCH-07 — indicative notice
    private List<TranslationStatus> translations;  // admin sathi

    @Data @Builder
    public static class TranslationStatus {
        private String langCode;
        private boolean present;
    }
}