package com.govermentscheme.backend.admin.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class AuditLogResponse {
    private Long id;
    private Long userId;
    private String action;
    private String entity;
    private Long entityId;
    private String oldValue;
    private String newValue;
    private LocalDateTime createdAt;
}