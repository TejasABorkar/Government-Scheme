package com.govermentscheme.backend.user.entity;

import com.govermentscheme.backend.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farmer_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FarmerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "farmer_id_available", nullable = false)
    private Boolean farmerIdAvailable = false;

    @Column(name = "farmer_id_value", length = 20)
    private String farmerIdValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_category", length = 20)
    private SocialCategory socialCategory;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", length = 20)
    private AgeGroup ageGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_range", length = 30)
    private IncomeRange incomeRange;

    @Column(length = 100)
    private String district;

    @Column(length = 100)
    private String taluka;

    @Column(length = 100)
    private String village;

    @Column(name = "consent_given", nullable = false)
    private Boolean consentGiven = false;

    @Column(name = "consent_given_at")
    private LocalDateTime consentGivenAt;

    @OneToMany(mappedBy = "farmerProfile", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<LandHolding> landHoldings = new ArrayList<>();

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