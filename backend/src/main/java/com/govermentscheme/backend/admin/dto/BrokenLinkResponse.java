package com.govermentscheme.backend.admin.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class BrokenLinkResponse {
    private Long schemeId;
    private String schemeName;
    private String url;
    private Integer statusCode;
    private LocalDateTime checkedAt;
}