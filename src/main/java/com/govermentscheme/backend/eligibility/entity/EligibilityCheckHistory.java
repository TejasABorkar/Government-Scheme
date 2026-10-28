package com.govermentscheme.backend.eligibility.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eligibility_check_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EligibilityCheckHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "scheme_id", nullable = false)
    private Long schemeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EligibilityResult result;

    @Column(columnDefinition = "JSON")
    private String reasons;

    @Column(name = "missing_fields", columnDefinition = "JSON")
    private String missingFields;

    @Column(name = "checked_at", nullable = false, updatable = false)
    private LocalDateTime checkedAt;

    @PrePersist
    protected void onCreate() { checkedAt = LocalDateTime.now(); }
}