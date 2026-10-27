package com.govermentscheme.backend.scheme.repository;

import com.govermentscheme.backend.scheme.entity.SchemeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchemeCategoryRepository extends JpaRepository<SchemeCategory, Long> {

    List<SchemeCategory> findByIsActiveTrueOrderByIdAsc();

    Optional<SchemeCategory> findByCode(String code);

    boolean existsByCode(String code);
}