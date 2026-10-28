package com.govermentscheme.backend.guidance.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DocumentItemResponse {
    private Long documentTypeId;
    private String code;
    private String name;
    private String whereToGet;
    private Boolean isMandatory;
}