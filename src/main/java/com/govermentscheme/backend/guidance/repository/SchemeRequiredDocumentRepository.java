package com.govermentscheme.backend.guidance.repository;

import com.govermentscheme.backend.guidance.entity.SchemeRequiredDocument;
import com.govermentscheme.backend.guidance.entity.SchemeRequiredDocumentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchemeRequiredDocumentRepository extends JpaRepository<SchemeRequiredDocument, SchemeRequiredDocumentId> {

    @Query("SELECT srd FROM SchemeRequiredDocument srd JOIN FETCH srd.documentType dt LEFT JOIN FETCH dt.translations WHERE srd.scheme.id = :schemeId")
    List<SchemeRequiredDocument> findBySchemeIdWithTranslations(@Param("schemeId") Long schemeId);
}