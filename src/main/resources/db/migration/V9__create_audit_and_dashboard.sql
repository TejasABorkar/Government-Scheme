-- ============================================
-- AUDIT LOG
-- ============================================
CREATE TABLE audit_logs (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            user_id BIGINT NULL,
                            action VARCHAR(50) NOT NULL,          -- CREATE, UPDATE, DELETE, ACTIVATE, DEACTIVATE
                            entity VARCHAR(50) NOT NULL,          -- SCHEME, RULE, USER, VIDEO, etc.
                            entity_id BIGINT NULL,
                            old_value JSON NULL,
                            new_value JSON NULL,
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
                            INDEX idx_audit_entity (entity, entity_id),
                            INDEX idx_audit_user_created (user_id, created_at DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- LINK CHECK HISTORY (for ADM-06)
-- ============================================
CREATE TABLE link_check_results (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    scheme_id BIGINT NOT NULL,
                                    url VARCHAR(500) NOT NULL,
                                    status_code INT NULL,
                                    is_working BOOLEAN NOT NULL,
                                    checked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    CONSTRAINT fk_link_scheme FOREIGN KEY (scheme_id) REFERENCES schemes(id) ON DELETE CASCADE,
                                    INDEX idx_link_scheme_checked (scheme_id, checked_at DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;