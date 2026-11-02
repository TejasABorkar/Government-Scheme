package com.govermentscheme.backend.user.dto;

import com.govermentscheme.backend.user.entity.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder
public class ProfileResponse {
    private Long id;
    private Long userId;
    private Boolean farmerIdAvailable;
    private String farmerIdValue;
    private SocialCategory socialCategory;
    private Gender gender;
    private AgeGroup ageGroup;
    private IncomeRange incomeRange;
    private String district;
    private String taluka;
    private String village;
    private Boolean consentGiven;
    private LocalDateTime consentGivenAt;
    private List<LandHoldingResponse> landHoldings;
    private Integer completenessPercentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
