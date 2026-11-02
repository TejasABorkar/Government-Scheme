package com.govermentscheme.backend.user.service;

import com.govermentscheme.backend.auth.entity.User;
import com.govermentscheme.backend.auth.repository.UserRepository;
import com.govermentscheme.backend.common.exception.ApiException;
import com.govermentscheme.backend.common.exception.ErrorCode;
import com.govermentscheme.backend.user.dto.*;
import com.govermentscheme.backend.user.entity.FarmerProfile;
import com.govermentscheme.backend.user.repository.FarmerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmerProfileService {

    private final FarmerProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProfileResponse saveProfile(Long userId, ProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.UNAUTHORIZED));

        FarmerProfile profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> FarmerProfile.builder().user(user).build());

        if (!Boolean.TRUE.equals(request.getConsentGiven())) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR, "Consent is required");
        }
        if (profile.getConsentGivenAt() == null) {
            profile.setConsentGivenAt(LocalDateTime.now());
        }
        profile.setConsentGiven(true);
        profile.setFarmerIdAvailable(Boolean.TRUE.equals(request.getFarmerIdAvailable()));
        profile.setFarmerIdValue(request.getFarmerIdValue());
        profile.setSocialCategory(request.getSocialCategory());
        profile.setGender(request.getGender());
        profile.setAgeGroup(request.getAgeGroup());
        profile.setIncomeRange(request.getIncomeRange());
        profile.setDistrict(request.getDistrict());
        profile.setTaluka(request.getTaluka());
        profile.setVillage(request.getVillage());

        FarmerProfile saved = profileRepository.save(profile);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(Long userId) {
        FarmerProfile profile = profileRepository.findByUserIdWithLandHoldings(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Profile not found"));
        return toResponse(profile);
    }

    @Transactional(readOnly = true)
    public ProfileCompletenessResponse getCompleteness(Long userId) {
        FarmerProfile profile = profileRepository.findByUserIdWithLandHoldings(userId)
                .orElse(null);
        if (profile == null) return new ProfileCompletenessResponse(0, List.of("profile"));
        List<String> missing = missingFields(profile);
        int total = 9;
        int filled = total - missing.size();
        return new ProfileCompletenessResponse((int) Math.round(filled * 100.0 / total), missing);
    }

    @Transactional
    public void deleteAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.UNAUTHORIZED));
        userRepository.delete(user);  // ON DELETE CASCADE handles profile + land
    }

    // ---------- helpers ----------
    private ProfileResponse toResponse(FarmerProfile p) {
        List<LandHoldingResponse> lands = p.getLandHoldings() == null ? List.of()
                : p.getLandHoldings().stream()
                .map(l -> LandHoldingResponse.builder()
                        .id(l.getId())
                        .areaHectares(l.getAreaHectares())
                        .ownershipType(l.getOwnershipType())
                        .irrigationType(l.getIrrigationType())
                        .cropType(l.getCropType())
                        .createdAt(l.getCreatedAt())
                        .updatedAt(l.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        List<String> missing = missingFields(p);
        int completeness = (int) Math.round((9 - missing.size()) * 100.0 / 9);

        return ProfileResponse.builder()
                .id(p.getId())
                .userId(p.getUser().getId())
                .farmerIdAvailable(p.getFarmerIdAvailable())
                .farmerIdValue(p.getFarmerIdValue())
                .socialCategory(p.getSocialCategory())
                .gender(p.getGender())
                .ageGroup(p.getAgeGroup())
                .incomeRange(p.getIncomeRange())
                .district(p.getDistrict())
                .taluka(p.getTaluka())
                .village(p.getVillage())
                .consentGiven(p.getConsentGiven())
                .consentGivenAt(p.getConsentGivenAt())
                .landHoldings(lands)
                .completenessPercentage(completeness)
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    private List<String> missingFields(FarmerProfile p) {
        List<String> missing = new ArrayList<>();
        if (p.getSocialCategory() == null) missing.add("socialCategory");
        if (p.getGender() == null) missing.add("gender");
        if (p.getAgeGroup() == null) missing.add("ageGroup");
        if (p.getIncomeRange() == null) missing.add("incomeRange");
        if (p.getDistrict() == null || p.getDistrict().isBlank()) missing.add("district");
        if (p.getTaluka() == null || p.getTaluka().isBlank()) missing.add("taluka");
        if (p.getVillage() == null || p.getVillage().isBlank()) missing.add("village");
        if (p.getLandHoldings() == null || p.getLandHoldings().isEmpty()) missing.add("landHoldings");
        if (!Boolean.TRUE.equals(p.getConsentGiven())) missing.add("consent");
        return missing;
    }
}