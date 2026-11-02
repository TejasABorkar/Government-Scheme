-- ============================================
-- 1. SCHEME CATEGORIES
-- ============================================
CREATE TABLE scheme_categories (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   code VARCHAR(50) NOT NULL UNIQUE,
                                   is_active BOOLEAN NOT NULL DEFAULT TRUE,
                                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE scheme_category_translations (
                                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              category_id BIGINT NOT NULL,
                                              lang_code VARCHAR(5) NOT NULL,
                                              name VARCHAR(150) NOT NULL,
                                              CONSTRAINT fk_cat_trans_category FOREIGN KEY (category_id)
                                                  REFERENCES scheme_categories(id) ON DELETE CASCADE,
                                              CONSTRAINT uq_cat_trans UNIQUE (category_id, lang_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 2. SCHEMES
-- ============================================
CREATE TABLE schemes (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         level VARCHAR(20) NOT NULL,                -- CENTRAL / STATE
                         category_id BIGINT NOT NULL,
                         department VARCHAR(150) NULL,
                         official_url VARCHAR(500) NOT NULL,
                         gr_reference VARCHAR(255) NULL,
                         start_date DATE NULL,
                         end_date DATE NULL,
                         is_active BOOLEAN NOT NULL DEFAULT TRUE,
                         last_verified_at DATE NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         CONSTRAINT fk_scheme_category FOREIGN KEY (category_id)
                             REFERENCES scheme_categories(id),
                         INDEX idx_schemes_active_level_cat (is_active, level, category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 3. SCHEME TRANSLATIONS
-- ============================================
CREATE TABLE scheme_translations (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     scheme_id BIGINT NOT NULL,
                                     lang_code VARCHAR(5) NOT NULL,
                                     name VARCHAR(255) NOT NULL,
                                     description TEXT NULL,
                                     benefits TEXT NULL,
                                     eligibility_summary TEXT NULL,
                                     CONSTRAINT fk_scheme_trans FOREIGN KEY (scheme_id)
                                         REFERENCES schemes(id) ON DELETE CASCADE,
                                     CONSTRAINT uq_scheme_trans UNIQUE (scheme_id, lang_code),
                                     INDEX idx_scheme_trans_name (name(100)),
                                     FULLTEXT INDEX ft_scheme_trans (name, description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 4. SEED CATEGORIES
-- ============================================
INSERT INTO scheme_categories (code, is_active) VALUES
                                                    ('AGRICULTURE', TRUE),
                                                    ('SUBSIDY', TRUE),
                                                    ('INSURANCE', TRUE),
                                                    ('LOAN', TRUE),
                                                    ('EQUIPMENT', TRUE),
                                                    ('IRRIGATION', TRUE);

-- Category translations (mr, hi, en)
INSERT INTO scheme_category_translations (category_id, lang_code, name) VALUES
                                                                            (1, 'en', 'Agriculture'),
                                                                            (1, 'mr', 'कृषी'),
                                                                            (1, 'hi', 'कृषि'),
                                                                            (2, 'en', 'Subsidy'),
                                                                            (2, 'mr', 'अनुदान'),
                                                                            (2, 'hi', 'सब्सिडी'),
                                                                            (3, 'en', 'Insurance'),
                                                                            (3, 'mr', 'विमा'),
                                                                            (3, 'hi', 'बीमा'),
                                                                            (4, 'en', 'Loan'),
                                                                            (4, 'mr', 'कर्ज'),
                                                                            (4, 'hi', 'ऋण'),
                                                                            (5, 'en', 'Equipment'),
                                                                            (5, 'mr', 'अवजारे'),
                                                                            (5, 'hi', 'उपकरण'),
                                                                            (6, 'en', 'Irrigation'),
                                                                            (6, 'mr', 'सिंचन'),
                                                                            (6, 'hi', 'सिंचाई');