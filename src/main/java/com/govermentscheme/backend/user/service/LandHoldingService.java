package com.govermentscheme.backend.user.service;

import com.govermentscheme.backend.common.exception.ApiException;
import com.govermentscheme.backend.common.exception.ErrorCode;
import com.govermentscheme.backend.user.dto.*;
import com.govermentscheme.backend.user.entity.FarmerProfile;
import com.govermentscheme.backend.user.entity.LandHolding;
import com.govermentscheme.backend.user.repository.FarmerProfileRepository;
import com.govermentscheme.backend.user.repository.LandHoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LandHoldingService {

    private final LandHoldingRepository landRepository;
    private final FarmerProfileRepository profileRepository;

    @Transactional
    public LandHoldingResponse add(Long userId, LandHoldingRequest request) {
        FarmerProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR,
                        "Complete your profile first"));
        LandHolding holding = LandHolding.builder()
                .farmerProfile(profile)
                .areaHectares(request.getAreaHectares())
                .ownershipType(request.getOwnershipType())
                .irrigationType(request.getIrrigationType())
                .cropType(request.getCropType())
                .build();
        return toResponse(landRepository.save(holding));
    }

    @Transactional(readOnly = true)
    public List<LandHoldingResponse> list(Long userId) {
        FarmerProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Profile not found"));
        return landRepository.findByFarmerProfileId(profile.getId()).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public LandHoldingResponse update(Long userId, Long holdingId, LandHoldingRequest request) {
        LandHolding holding = landRepository
                .findByIdAndFarmerProfileUserId(holdingId, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.FORBIDDEN,
                        "Land holding not found or not yours"));
        holding.setAreaHectares(request.getAreaHectares());
        holding.setOwnershipType(request.getOwnershipType());
        holding.setIrrigationType(request.getIrrigationType());
        holding.setCropType(request.getCropType());
        return toResponse(landRepository.save(holding));
    }

    @Transactional
    public void delete(Long userId, Long holdingId) {
        LandHolding holding = landRepository
                .findByIdAndFarmerProfileUserId(holdingId, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.FORBIDDEN,
                        "Land holding not found or not yours"));
        landRepository.delete(holding);
    }

    private LandHoldingResponse toResponse(LandHolding h) {
        return LandHoldingResponse.builder()
                .id(h.getId())
                .areaHectares(h.getAreaHectares())
                .ownershipType(h.getOwnershipType())
                .irrigationType(h.getIrrigationType())
                .cropType(h.getCropType())
                .createdAt(h.getCreatedAt())
                .updatedAt(h.getUpdatedAt())
                .build();
    }
}