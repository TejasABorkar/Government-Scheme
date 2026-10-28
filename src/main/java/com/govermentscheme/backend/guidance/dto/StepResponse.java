package com.govermentscheme.backend.guidance.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class StepResponse {
    private Integer stepNo;
    private String instruction;
}