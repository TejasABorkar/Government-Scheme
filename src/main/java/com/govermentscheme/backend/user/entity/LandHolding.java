package com.govermentscheme.backend.user.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "land_holdings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LandHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_profile_id", nullable = false)
    private FarmerProfile farmerProfile;

    @Column(name = "area_hectares", nullable = false, precision = 8, scale = 2)
    private BigDecimal areaHectares;

    @Enumerated(EnumType.STRING)
    @Column(name = "ownership_type", length = 30)
    private OwnershipType ownershipType;

    @Enumerated(EnumType.STRING)
    @Column(name = "irrigation_type", length = 30)
    private IrrigationType irrigationType;

    @Column(name = "crop_type", length = 100)
    private String cropType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}