package com.govermentscheme.backend.video.entity;

import com.govermentscheme.backend.scheme.entity.Scheme;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheme_videos", uniqueConstraints = @UniqueConstraint(columnNames = {"scheme_id", "lang_code"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SchemeVideo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheme_id", nullable = false)
    private Scheme scheme;

    @Column(name = "lang_code", nullable = false, length = 5)
    private String langCode;

    @Column(name = "youtube_video_id", nullable = false, length = 20)
    private String youtubeVideoId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }
}