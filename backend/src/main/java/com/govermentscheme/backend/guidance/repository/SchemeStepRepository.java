package com.govermentscheme.backend.guidance.repository;

import com.govermentscheme.backend.guidance.entity.SchemeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchemeStepRepository extends JpaRepository<SchemeStep, Long> {
    @Query("SELECT DISTINCT s FROM SchemeStep s LEFT JOIN FETCH s.translations WHERE s.scheme.id = :schemeId ORDER BY s.stepNo")
    List<SchemeStep> findBySchemeIdWithTranslations(@Param("schemeId") Long schemeId);
}