package com.govermentscheme.backend.video.repository;

import com.govermentscheme.backend.video.entity.SchemeVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchemeVideoRepository extends JpaRepository<SchemeVideo, Long> {
    Optional<SchemeVideo> findBySchemeIdAndLangCodeAndIsActiveTrue(Long schemeId, String langCode);
}