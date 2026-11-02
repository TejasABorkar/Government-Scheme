package com.govermentscheme.backend.user.repository;

import com.govermentscheme.backend.user.entity.FarmerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FarmerProfileRepository extends JpaRepository<FarmerProfile, Long> {

    Optional<FarmerProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    @Query("SELECT p FROM FarmerProfile p LEFT JOIN FETCH p.landHoldings WHERE p.user.id = :userId")
    Optional<FarmerProfile> findByUserIdWithLandHoldings(@Param("userId") Long userId);
}
