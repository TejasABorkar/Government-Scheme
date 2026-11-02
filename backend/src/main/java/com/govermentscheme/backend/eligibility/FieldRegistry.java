package com.govermentscheme.backend.eligibility;

import com.govermentscheme.backend.user.entity.FarmerProfile;
import com.govermentscheme.backend.user.entity.IncomeRange;
import com.govermentscheme.backend.user.entity.LandHolding;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FieldRegistry {

    public static final List<String> SUPPORTED_FIELDS = List.of(
            "total_land_hectares",
            "social_category",
            "age_group",
            "gender",
            "income_range",
            "district",
            "has_farmer_id",
            "crop_type",
            "irrigation_type",
            "ownership_type"
    );

    public Object extract(String field, FarmerProfile profile) {
        if (profile == null) return null;
        return switch (field) {
            case "total_land_hectares" -> totalLandHectares(profile);
            case "social_category" -> profile.getSocialCategory() != null ? profile.getSocialCategory().name() : null;
            case "age_group" -> profile.getAgeGroup() != null ? profile.getAgeGroup().name() : null;
            case "gender" -> profile.getGender() != null ? profile.getGender().name() : null;
            case "income_range" -> profile.getIncomeRange() != null ? profile.getIncomeRange().name() : null;
            case "district" -> profile.getDistrict();
            case "has_farmer_id" -> Boolean.TRUE.equals(profile.getFarmerIdAvailable());
            case "crop_type" -> cropSet(profile);
            case "irrigation_type" -> irrigationSet(profile);
            case "ownership_type" -> ownershipSet(profile);
            default -> null;
        };
    }

    /** For income_range, we compare by rank, so expose rank */
    public Integer extractRank(String field, FarmerProfile profile) {
        if ("income_range".equals(field) && profile.getIncomeRange() != null) {
            return profile.getIncomeRange().getRank();
        }
        return null;
    }

    private BigDecimal totalLandHectares(FarmerProfile p) {
        if (p.getLandHoldings() == null || p.getLandHoldings().isEmpty()) return null;
        return p.getLandHoldings().stream()
                .map(LandHolding::getAreaHectares)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Object cropSet(FarmerProfile p) {
        if (p.getLandHoldings() == null) return null;
        return p.getLandHoldings().stream()
                .map(LandHolding::getCropType)
                .filter(Objects::nonNull)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
    }

    private Object irrigationSet(FarmerProfile p) {
        if (p.getLandHoldings() == null) return null;
        return p.getLandHoldings().stream()
                .map(LandHolding::getIrrigationType)
                .filter(Objects::nonNull)
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    private Object ownershipSet(FarmerProfile p) {
        if (p.getLandHoldings() == null) return null;
        return p.getLandHoldings().stream()
                .map(LandHolding::getOwnershipType)
                .filter(Objects::nonNull)
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}