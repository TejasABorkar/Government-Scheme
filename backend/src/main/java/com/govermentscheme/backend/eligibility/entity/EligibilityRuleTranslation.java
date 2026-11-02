package com.govermentscheme.backend.eligibility.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eligibility_rule_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"rule_id", "lang_code"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EligibilityRuleTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id", nullable = false)
    private EligibilityRule rule;

    @Column(name = "lang_code", nullable = false, length = 5)
    private String langCode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
}