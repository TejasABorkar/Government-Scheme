package com.govermentscheme.backend.guidance.entity;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class SchemeRequiredDocumentId implements Serializable {
    private Long scheme;
    private Long documentType;
}
