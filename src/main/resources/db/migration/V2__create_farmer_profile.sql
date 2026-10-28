CREATE TABLE farmer_profiles (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 user_id BIGINT NOT NULL UNIQUE,
                                 farmer_id_available BOOLEAN NOT NULL DEFAULT FALSE,
                                 farmer_id_value VARCHAR(20) NULL,
                                 social_category VARCHAR(20) NULL,
                                 gender VARCHAR(20) NULL,
                                 age_group VARCHAR(20) NULL,
                                 income_range VARCHAR(30) NULL,
                                 district VARCHAR(100) NULL,
                                 taluka VARCHAR(100) NULL,
                                 village VARCHAR(100) NULL,
                                 consent_given BOOLEAN NOT NULL DEFAULT FALSE,
                                 consent_given_at TIMESTAMP NULL,
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 CONSTRAINT fk_profile_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE land_holdings (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               farmer_profile_id BIGINT NOT NULL,
                               area_hectares DECIMAL(8,2) NOT NULL,
                               ownership_type VARCHAR(30) NULL,
                               irrigation_type VARCHAR(30) NULL,
                               crop_type VARCHAR(100) NULL,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               CONSTRAINT fk_land_profile FOREIGN KEY (farmer_profile_id) REFERENCES farmer_profiles(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_land_profile ON land_holdings(farmer_profile_id);

