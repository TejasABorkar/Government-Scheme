package com.govermentscheme.backend.scheme.dto;

import com.govermentscheme.backend.scheme.entity.SchemeLevel;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data @Builder
public class SchemeSummaryResponse {
    private Long id;
    private String name;
    private String shortDescription;  // description cha first 150 chars
    private SchemeLevel level;
    private String categoryName;
    private String department;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private Boolean isExpired;
}
