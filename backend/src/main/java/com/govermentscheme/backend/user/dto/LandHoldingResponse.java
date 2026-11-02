package com.govermentscheme.backend.user.dto;

import com.govermentscheme.backend.user.entity.*;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder
public class LandHoldingResponse {
    private Long id;
    private BigDecimal areaHectares;
    private OwnershipType ownershipType;
    private IrrigationType irrigationType;
    private String cropType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}