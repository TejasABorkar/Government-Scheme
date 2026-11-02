package com.govermentscheme.backend.guidance.repository;

import com.govermentscheme.backend.guidance.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {}