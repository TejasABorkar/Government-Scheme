package com.govermentscheme.backend.video.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VideoResponse {
    private Long id;
    private String langCode;
    private String youtubeVideoId;
    private String embedUrl;
    private String watchUrl;
    private Boolean isActive;
    private String notice;
}