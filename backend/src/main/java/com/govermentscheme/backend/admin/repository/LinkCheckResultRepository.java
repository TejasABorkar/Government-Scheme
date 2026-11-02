package com.govermentscheme.backend.admin.repository;

import com.govermentscheme.backend.admin.entity.LinkCheckResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkCheckResultRepository extends JpaRepository<LinkCheckResult, Long> {
    List<LinkCheckResult> findBySchemeIdOrderByCheckedAtDesc(Long schemeId);
    List<LinkCheckResult> findByIsWorkingFalse();
}