package com.govermentscheme.backend.scheme.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookmarks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@IdClass(BookmarkId.class)
public class Bookmark {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "scheme_id")
    private Long schemeId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }
}