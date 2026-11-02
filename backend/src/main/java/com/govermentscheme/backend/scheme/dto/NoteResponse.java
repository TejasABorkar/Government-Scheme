package com.govermentscheme.backend.scheme.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class NoteResponse {
    private Long id;
    private Long schemeId;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}