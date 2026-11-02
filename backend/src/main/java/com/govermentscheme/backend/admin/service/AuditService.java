package com.govermentscheme.backend.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.govermentscheme.backend.admin.dto.AuditLogResponse;
import com.govermentscheme.backend.admin.entity.AuditLog;
import com.govermentscheme.backend.admin.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository repo;
    private final ObjectMapper objectMapper;

    public void log(Long userId, String action, String entity, Long entityId,
                    Object oldValue, Object newValue) {
        try {
            repo.save(AuditLog.builder()
                    .userId(userId)
                    .action(action)
                    .entity(entity)
                    .entityId(entityId)
                    .oldValue(oldValue != null ? objectMapper.writeValueAsString(oldValue) : null)
                    .newValue(newValue != null ? objectMapper.writeValueAsString(newValue) : null)
                    .build());
        } catch (Exception ignored) {}
    }

    public Page<AuditLogResponse> search(String entity, Long entityId, int page, int size) {
        return repo.search(entity, entityId, PageRequest.of(page, size))
                .map(a -> AuditLogResponse.builder()
                        .id(a.getId())
                        .userId(a.getUserId())
                        .action(a.getAction())
                        .entity(a.getEntity())
                        .entityId(a.getEntityId())
                        .oldValue(a.getOldValue())
                        .newValue(a.getNewValue())
                        .createdAt(a.getCreatedAt())
                        .build());
    }
}