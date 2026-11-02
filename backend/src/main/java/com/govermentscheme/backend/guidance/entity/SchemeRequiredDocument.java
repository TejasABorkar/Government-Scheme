package com.govermentscheme.backend.guidance.entity;

import com.govermentscheme.backend.scheme.entity.Scheme;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scheme_required_documents")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(SchemeRequiredDocumentId.class)
public class SchemeRequiredDocument {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheme_id")
    private Scheme scheme;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = true;
}