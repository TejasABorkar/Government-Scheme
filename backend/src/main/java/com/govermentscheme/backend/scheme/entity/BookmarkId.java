package com.govermentscheme.backend.scheme.entity;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class BookmarkId implements Serializable {
    private Long userId;
    private Long schemeId;
}