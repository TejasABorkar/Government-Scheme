package com.govermentscheme.backend.eligibility.repository;

import com.govermentscheme.backend.eligibility.entity.EligibilityCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EligibilityHistoryRepository extends JpaRepository<EligibilityCheckHistory, Long> {

    List<EligibilityCheckHistory> findByUserIdOrderByCheckedAtDesc(Long userId);
}