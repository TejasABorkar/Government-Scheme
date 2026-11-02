package com.govermentscheme.backend.guidance.repository;

import com.govermentscheme.backend.guidance.entity.SchemeFaq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchemeFaqRepository extends JpaRepository<SchemeFaq, Long> {
    @Query("SELECT DISTINCT f FROM SchemeFaq f LEFT JOIN FETCH f.translations WHERE f.scheme.id = :schemeId ORDER BY f.displayOrder")
    List<SchemeFaq> findBySchemeIdWithTranslations(@Param("schemeId") Long schemeId);
}