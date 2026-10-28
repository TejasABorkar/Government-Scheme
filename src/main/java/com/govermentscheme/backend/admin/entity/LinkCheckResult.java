package com.govermentscheme.backend.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "link_check_results")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LinkCheckResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scheme_id", nullable = false)
    private Long schemeId;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "is_working", nullable = false)
    private Boolean isWorking;

    @Column(name = "checked_at", nullable = false, updatable = false)
    private LocalDateTime checkedAt;

    @PrePersist protected void onCreate() { checkedAt = LocalDateTime.now(); }
}