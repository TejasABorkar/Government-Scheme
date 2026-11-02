package com.govermentscheme.backend.guidance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scheme_faq_translations", uniqueConstraints = @UniqueConstraint(columnNames = {"faq_id", "lang_code"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SchemeFaqTranslation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faq_id", nullable = false)
    private SchemeFaq faq;

    @Column(name = "lang_code", nullable = false, length = 5)
    private String langCode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;
}