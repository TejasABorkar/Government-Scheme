-- Admin user (password: Admin@123)
-- BCrypt hash (cost 10) for "Admin@123" — tu swatah generate karu shakto
INSERT INTO users (name, email, phone, password_hash, role, preferred_language, is_active)
VALUES (
           'Admin User',
           'admin@krushisetu.com',
           '9000000000',
           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZ');


DELETE FROM flyway_schema_history WHERE success = 0;
DELETE FROM users WHERE email = 'admin@krushisetu.com';  -- only if a partial row exists