package com.govermentscheme.backend.eligibility.repository;

import com.govermentscheme.backend.eligibility.entity.EligibilityRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EligibilityRuleRepository extends JpaRepository<EligibilityRule, Long> {

    @Query("""
        SELECT r FROM EligibilityRule r
        LEFT JOIN FETCH r.translations
        WHERE r.scheme.id = :schemeId
        ORDER BY r.displayOrder ASC
        """)
    List<EligibilityRule> findBySchemeIdWithTranslations(@Param("schemeId") Long schemeId);

    void deleteBySchemeId(Long schemeId);
}