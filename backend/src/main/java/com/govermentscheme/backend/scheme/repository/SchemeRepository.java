package com.govermentscheme.backend.scheme.repository;

import com.govermentscheme.backend.scheme.entity.Scheme;
import com.govermentscheme.backend.scheme.entity.SchemeLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {

    @Query(value = """
    SELECT s FROM Scheme s
    JOIN FETCH s.category c
    WHERE s.isActive = true
      AND (s.endDate IS NULL OR s.endDate >= CURRENT_DATE)
      AND (:level IS NULL OR s.level = :level)
      AND (:categoryId IS NULL OR c.id = :categoryId)
      AND (:q IS NULL OR EXISTS (
            SELECT 1 FROM SchemeTranslation t
            WHERE t.scheme = s
              AND LOWER(t.name) LIKE LOWER(CONCAT('%', :q, '%'))))
    """,
            countQuery = """
    SELECT COUNT(s) FROM Scheme s
    WHERE s.isActive = true
      AND (s.endDate IS NULL OR s.endDate >= CURRENT_DATE)
      AND (:level IS NULL OR s.level = :level)
      AND (:categoryId IS NULL OR s.category.id = :categoryId)
      AND (:q IS NULL OR EXISTS (
            SELECT 1 FROM SchemeTranslation t
            WHERE t.scheme = s
              AND LOWER(t.name) LIKE LOWER(CONCAT('%', :q, '%'))))
    """)
    Page<Scheme> searchPublic(@Param("q") String q,
                              @Param("level") SchemeLevel level,
                              @Param("categoryId") Long categoryId,
                              Pageable pageable);

    @Query("SELECT s FROM Scheme s JOIN FETCH s.category c LEFT JOIN FETCH s.translations WHERE s.id = :id")
    Optional<Scheme> findByIdWithTranslations(@Param("id") Long id);
}