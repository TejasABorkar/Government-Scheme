package com.govermentscheme.backend.user.dto;

import com.govermentscheme.backend.user.entity.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProfileRequest {

    @Pattern(regexp = "^[A-Z]{0,5}[0-9]{0,15}$", message = "Invalid Farmer ID format")
    private String farmerIdValue;

    private Boolean farmerIdAvailable;

    private SocialCategory socialCategory;
    private Gender gender;
    private AgeGroup ageGroup;
    private IncomeRange incomeRange;

    @Size(max = 100) private String district;
    @Size(max = 100) private String taluka;
    @Size(max = 100) private String village;

    @NotNull(message = "Consent is required before saving profile")
    @AssertTrue(message = "You must give consent")
    private Boolean consentGiven;
}
