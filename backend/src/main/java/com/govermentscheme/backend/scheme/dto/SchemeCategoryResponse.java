package com.govermentscheme.backend.scheme.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class SchemeCategoryResponse {
    private Long id;
    private String code;
    private String name;
    private Boolean isActive;
}