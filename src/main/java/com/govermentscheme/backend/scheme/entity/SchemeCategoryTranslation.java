package com.govermentscheme.backend.scheme.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scheme_category_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "lang_code"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SchemeCategoryTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private SchemeCategory category;

    @Column(name = "lang_code", nullable = false, length = 5)
    private String langCode;

    @Column(nullable = false, length = 150)
    private String name;
}