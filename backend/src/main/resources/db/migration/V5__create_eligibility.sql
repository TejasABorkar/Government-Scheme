-- ============================================
-- 1. ELIGIBILITY RULES
-- ============================================
CREATE TABLE eligibility_rules (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   scheme_id BIGINT NOT NULL,
                                   field VARCHAR(50) NOT NULL,
                                   operator VARCHAR(20) NOT NULL,
                                   value VARCHAR(500) NOT NULL,
                                   is_mandatory BOOLEAN NOT NULL DEFAULT TRUE,
                                   display_order INT NOT NULL DEFAULT 0,
                                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   CONSTRAINT fk_rule_scheme FOREIGN KEY (scheme_id) REFERENCES schemes(id) ON DELETE CASCADE,
                                   INDEX idx_rules_scheme (scheme_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE eligibility_rule_translations (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               rule_id BIGINT NOT NULL,
                                               lang_code VARCHAR(5) NOT NULL,
                                               message TEXT NOT NULL,
                                               CONSTRAINT fk_rule_trans_rule FOREIGN KEY (rule_id) REFERENCES eligibility_rules(id) ON DELETE CASCADE,
                                               CONSTRAINT uq_rule_trans UNIQUE (rule_id, lang_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 2. ELIGIBILITY CHECK HISTORY
-- ============================================
CREATE TABLE eligibility_check_history (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           user_id BIGINT NOT NULL,
                                           scheme_id BIGINT NOT NULL,
                                           result VARCHAR(30) NOT NULL,
                                           reasons JSON NULL,
                                           missing_fields JSON NULL,
                                           checked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                           CONSTRAINT fk_history_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                           CONSTRAINT fk_history_scheme FOREIGN KEY (scheme_id) REFERENCES schemes(id) ON DELETE CASCADE,
                                           INDEX idx_history_user_checked (user_id, checked_at DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;