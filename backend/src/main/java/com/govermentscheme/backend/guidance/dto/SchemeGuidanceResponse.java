package com.govermentscheme.backend.guidance.dto;

import com.govermentscheme.backend.video.dto.VideoResponse;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data @Builder
public class SchemeGuidanceResponse {
    private Long schemeId;
    private String schemeName;
    private String officialUrl;
    private String disclaimer;
    private List<DocumentItemResponse> documents;
    private List<StepResponse> steps;
    private List<FaqResponse> faqs;
    private VideoResponse video;
}