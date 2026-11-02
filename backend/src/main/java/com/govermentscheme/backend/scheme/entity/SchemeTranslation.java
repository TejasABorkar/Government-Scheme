package com.govermentscheme.backend.scheme.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scheme_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"scheme_id", "lang_code"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SchemeTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheme_id", nullable = false)
    private Scheme scheme;

    @Column(name = "lang_code", nullable = false, length = 5)
    private String langCode;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    @Column(name = "eligibility_summary", columnDefinition = "TEXT")
    private String eligibilitySummary;
}