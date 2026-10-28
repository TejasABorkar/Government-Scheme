package com.govermentscheme.backend.guidance.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class FaqResponse {
    private Long id;
    private Integer displayOrder;
    private String question;
    private String answer;
}