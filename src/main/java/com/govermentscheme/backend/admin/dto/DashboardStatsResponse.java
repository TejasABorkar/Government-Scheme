package com.govermentscheme.backend.admin.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data @Builder
public class DashboardStatsResponse {
    private Long totalFarmers;
    private Long totalSchemes;
    private Long activeSchemes;
    private Long totalEligibilityChecks;
    private Long totalBookmarks;
    private List<SchemeCheckCount> checksPerScheme;
    private List<SchemeBookmarkCount> topBookmarkedSchemes;

    @Data @Builder
    public static class SchemeCheckCount {
        private Long schemeId;
        private String schemeName;
        private Long count;
    }

    @Data @Builder
    public static class SchemeBookmarkCount {
        private Long schemeId;
        private String schemeName;
        private Long count;
    }
}