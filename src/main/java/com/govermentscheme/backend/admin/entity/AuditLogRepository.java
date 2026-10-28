package com.govermentscheme.backend.admin.repository;

import com.govermentscheme.backend.admin.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    @Query("SELECT a FROM AuditLog a WHERE " +
            "(:entity IS NULL OR a.entity = :entity) AND " +
            "(:entityId IS NULL OR a.entityId = :entityId) " +
            "ORDER BY a.createdAt DESC")
    Page<AuditLog> search(@Param("entity") String entity,
                          @Param("entityId") Long entityId,
                          Pageable pageable);
}