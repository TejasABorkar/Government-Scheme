package com.govermentscheme.backend.guidance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scheme_step_translations", uniqueConstraints = @UniqueConstraint(columnNames = {"step_id", "lang_code"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SchemeStepTranslation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id", nullable = false)
    private SchemeStep step;

    @Column(name = "lang_code", nullable = false, length = 5)
    private String langCode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String instruction;
}