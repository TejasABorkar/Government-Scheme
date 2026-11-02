package com.govermentscheme.backend.guidance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_type_translations", uniqueConstraints = @UniqueConstraint(columnNames = {"document_type_id", "lang_code"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DocumentTypeTranslation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;

    @Column(name = "lang_code", nullable = false, length = 5)
    private String langCode;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "where_to_get", columnDefinition = "TEXT")
    private String whereToGet;
}