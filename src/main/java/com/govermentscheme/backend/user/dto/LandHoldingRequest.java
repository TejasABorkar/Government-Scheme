package com.govermentscheme.backend.user.dto;

import com.govermentscheme.backend.user.entity.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class LandHoldingRequest {

    @NotNull(message = "Area is required")
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "10000.00")
    private BigDecimal areaHectares;

    private OwnershipType ownershipType;
    private IrrigationType irrigationType;

    @Size(max = 100) private String cropType;
}