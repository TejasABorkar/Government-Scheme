-- ============================================
-- 1. DOCUMENT TYPES (Master list)
-- ============================================
CREATE TABLE document_types (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                code VARCHAR(50) NOT NULL UNIQUE,
                                is_active BOOLEAN NOT NULL DEFAULT TRUE,
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE document_type_translations (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            document_type_id BIGINT NOT NULL,
                                            lang_code VARCHAR(5) NOT NULL,
                                            name VARCHAR(150) NOT NULL,
                                            where_to_get TEXT NULL,
                                            CONSTRAINT fk_doctrans_type FOREIGN KEY (document_type_id) REFERENCES document_types(id) ON DELETE CASCADE,
                                            CONSTRAINT uq_doctrans UNIQUE (document_type_id, lang_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 2. SCHEME REQUIRED DOCUMENTS (Link table)
-- ============================================
CREATE TABLE scheme_required_documents (
                                           scheme_id BIGINT NOT NULL,
                                           document_type_id BIGINT NOT NULL,
                                           is_mandatory BOOLEAN NOT NULL DEFAULT TRUE,
                                           PRIMARY KEY (scheme_id, document_type_id),
                                           CONSTRAINT fk_srd_scheme FOREIGN KEY (scheme_id) REFERENCES schemes(id) ON DELETE CASCADE,
                                           CONSTRAINT fk_srd_doctype FOREIGN KEY (document_type_id) REFERENCES document_types(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 3. SCHEME STEPS
-- ============================================
CREATE TABLE scheme_steps (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              scheme_id BIGINT NOT NULL,
                              step_no INT NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_step_scheme FOREIGN KEY (scheme_id) REFERENCES schemes(id) ON DELETE CASCADE,
                              INDEX idx_steps_scheme (scheme_id, step_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE scheme_step_translations (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          step_id BIGINT NOT NULL,
                                          lang_code VARCHAR(5) NOT NULL,
                                          instruction TEXT NOT NULL,
                                          CONSTRAINT fk_steptrans_step FOREIGN KEY (step_id) REFERENCES scheme_steps(id) ON DELETE CASCADE,
                                          CONSTRAINT uq_steptrans UNIQUE (step_id, lang_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 4. SCHEME FAQs
-- ============================================
CREATE TABLE scheme_faqs (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             scheme_id BIGINT NOT NULL,
                             display_order INT NOT NULL DEFAULT 0,
                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             CONSTRAINT fk_faq_scheme FOREIGN KEY (scheme_id) REFERENCES schemes(id) ON DELETE CASCADE,
                             INDEX idx_faqs_scheme (scheme_id, display_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE scheme_faq_translations (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         faq_id BIGINT NOT NULL,
                                         lang_code VARCHAR(5) NOT NULL,
                                         question TEXT NOT NULL,
                                         answer TEXT NOT NULL,
                                         CONSTRAINT fk_faqtrans_faq FOREIGN KEY (faq_id) REFERENCES scheme_faqs(id) ON DELETE CASCADE,
                                         CONSTRAINT uq_faqtrans UNIQUE (faq_id, lang_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 5. SCHEME VIDEOS
-- ============================================
CREATE TABLE scheme_videos (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               scheme_id BIGINT NOT NULL,
                               lang_code VARCHAR(5) NOT NULL,
                               youtube_video_id VARCHAR(20) NOT NULL,
                               is_active BOOLEAN NOT NULL DEFAULT TRUE,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               CONSTRAINT fk_video_scheme FOREIGN KEY (scheme_id) REFERENCES schemes(id) ON DELETE CASCADE,
                               CONSTRAINT uq_video_scheme_lang UNIQUE (scheme_id, lang_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 6. BOOKMARKS & NOTES (Personalisation)
-- ============================================
CREATE TABLE bookmarks (
                           user_id BIGINT NOT NULL,
                           scheme_id BIGINT NOT NULL,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (user_id, scheme_id),
                           CONSTRAINT fk_bm_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                           CONSTRAINT fk_bm_scheme FOREIGN KEY (scheme_id) REFERENCES schemes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE user_notes (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            user_id BIGINT NOT NULL,
                            scheme_id BIGINT NOT NULL,
                            note TEXT NOT NULL,
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            CONSTRAINT fk_note_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            CONSTRAINT fk_note_scheme FOREIGN KEY (scheme_id) REFERENCES schemes(id) ON DELETE CASCADE,
                            INDEX idx_notes_user_scheme (user_id, scheme_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 7. SEED MASTER DATA (Document Types + Translations)
-- ============================================
INSERT INTO document_types (code) VALUES
                                      ('AADHAAR'), ('BANK_PASSBOOK'), ('LAND_RECORD_7_12'), ('FARMER_ID'), ('INCOME_CERTIFICATE');

INSERT INTO document_type_translations (document_type_id, lang_code, name, where_to_get) VALUES
-- Aadhaar
(1, 'en', 'Aadhaar Card', 'Nearest Aadhaar Enrolment Centre'),
(1, 'mr', 'आधार कार्ड', 'जवळचे आधार नोंदणी केंद्र'),
(1, 'hi', 'आधार कार्ड', 'निकटतम आधार नामांकन केंद्र'),
-- Bank Passbook
(2, 'en', 'Bank Passbook', 'Any bank branch'),
(2, 'mr', 'बँक पासबुक', 'कोणतीही बँक शाखा'),
(2, 'hi', 'बैंक पासबुक', 'कोई भी बैंक शाखा'),
-- Land Record 7/12
(3, 'en', 'Land Record (7/12)', 'Mahabhulekh / Talathi office'),
(3, 'mr', 'जमीन रेकॉर्ड (७/१२)', 'महाभूलेख / तलाठी कार्यालय'),
(3, 'hi', 'भूमि रिकॉर्ड (7/12)', 'महाभूलेख / तलाठी कार्यालय'),
-- Farmer ID
(4, 'en', 'Farmer ID', 'Agriculture Dept / CSC'),
(4, 'mr', 'शेतकरी ओळखपत्र', 'कृषी विभाग / CSC'),
(4, 'hi', 'किसान आईडी', 'कृषि विभाग / CSC'),
-- Income Certificate
(5, 'en', 'Income Certificate', 'Tehsil office'),
(5, 'mr', 'उत्पन्न प्रमाणपत्र', 'तहसील कार्यालय'),
(5, 'hi', 'आय प्रमाण पत्र', 'तहसील कार्यालय');