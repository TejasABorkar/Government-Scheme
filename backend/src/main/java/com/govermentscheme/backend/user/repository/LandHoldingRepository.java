package com.govermentscheme.backend.user.repository;

import com.govermentscheme.backend.user.entity.LandHolding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LandHoldingRepository extends JpaRepository<LandHolding, Long> {

    List<LandHolding> findByFarmerProfileId(Long farmerProfileId);

    Optional<LandHolding> findByIdAndFarmerProfileUserId(Long id, Long userId);
}