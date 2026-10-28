-- TEST DATA ONLY: scheme + eligibility rules

-- 1. Test scheme
INSERT INTO schemes (level, category_id, department, official_url, is_active)
VALUES ('CENTRAL',
        (SELECT id FROM scheme_categories WHERE code = 'AGRICULTURE'),
        'Ministry of Agriculture & Farmers Welfare',
        'https://pmkisan.gov.in/',
        TRUE);
SET @scheme_id = LAST_INSERT_ID();

INSERT INTO scheme_translations (scheme_id, lang_code, name) VALUES
                                                                 (@scheme_id, 'en', 'PM-KISAN'),
                                                                 (@scheme_id, 'mr', 'पीएम-किसान सन्मान निधी'),
                                                                 (@scheme_id, 'hi', 'पीएम-किसान सम्मान निधि');

-- 2. Rule 1: land <= 2 hectares
INSERT INTO eligibility_rules (scheme_id, field, operator, value, is_mandatory, display_order)
VALUES (@scheme_id, 'total_land_hectares', 'LTE', '2', TRUE, 1);
SET @rule1 = LAST_INSERT_ID();

INSERT INTO eligibility_rule_translations (rule_id, lang_code, message) VALUES
                                                                            (@rule1, 'en', 'Land area exceeds the 2 hectare limit'),
                                                                            (@rule1, 'mr', 'जमीन क्षेत्र २ हेक्टरपेक्षा जास्त आहे'),
                                                                            (@rule1, 'hi', 'भूमि क्षेत्र 2 हेक्टेयर से अधिक है');

-- 3. Rule 2: social category SC, ST, OBC
INSERT INTO eligibility_rules (scheme_id, field, operator, value, is_mandatory, display_order)
VALUES (@scheme_id, 'social_category', 'IN', 'SC,ST,OBC', TRUE, 2);
SET @rule2 = LAST_INSERT_ID();

INSERT INTO eligibility_rule_translations (rule_id, lang_code, message) VALUES
                                                                            (@rule2, 'en', 'Only SC, ST, or OBC category farmers are eligible'),
                                                                            (@rule2, 'mr', 'फक्त SC, ST, किंवा OBC श्रेणीचे शेतकरी पात्र'),
                                                                            (@rule2, 'hi', 'केवल SC, ST, या OBC श्रेणी के किसान पात्र हैं');