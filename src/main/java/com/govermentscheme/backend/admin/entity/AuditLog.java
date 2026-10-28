package com.govermentscheme.backend.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 50)
    private String action;

    @Column(nullable = false, length = 50)
    private String entity;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "old_value", columnDefinition = "JSON")
    private String oldValue;

    @Column(name = "new_value", columnDefinition = "JSON")
    private String newValue;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }
}