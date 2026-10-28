-- Scheme 1 documents
INSERT INTO scheme_required_documents (scheme_id, document_type_id, is_mandatory) VALUES
                                                                                      (1, 1, TRUE),  -- Aadhaar (mandatory)
                                                                                      (1, 2, TRUE),  -- Bank Passbook (mandatory)
                                                                                      (1, 3, TRUE),  -- 7/12 (mandatory)
                                                                                      (1, 4, FALSE); -- Farmer ID (optional)

-- Steps
INSERT INTO scheme_steps (scheme_id, step_no) VALUES (1, 1), (1, 2), (1, 3);
INSERT INTO scheme_step_translations (step_id, lang_code, instruction) VALUES
                                                                           (1, 'en', 'Visit the official PM-KISAN portal'),
                                                                           (1, 'mr', 'अधिकृत PM-KISAN पोर्टलला भेट द्या'),
                                                                           (1, 'hi', 'आधिकारिक PM-KISAN पोर्टल पर जाएं'),
                                                                           (2, 'en', 'Register with your Aadhaar number'),
                                                                           (2, 'mr', 'आधार क्रमांकाने नोंदणी करा'),
                                                                           (2, 'hi', 'आधार संख्या से पंजीकरण करें'),
                                                                           (3, 'en', 'Submit land details and bank account'),
                                                                           (3, 'mr', 'जमीन तपशील आणि बँक खाते सबमिट करा'),
                                                                           (3, 'hi', 'भूमि विवरण और बैंक खाता जमा करें');

-- FAQs
INSERT INTO scheme_faqs (scheme_id, display_order) VALUES (1, 1), (1, 2);
INSERT INTO scheme_faq_translations (faq_id, lang_code, question, answer) VALUES
                                                                              (1, 'en', 'Who is eligible?', 'Small and marginal farmers with land up to 2 hectares.'),
                                                                              (1, 'mr', 'कोण पात्र आहे?', '२ हेक्टरपर्यंत जमीन असलेले अल्प भूधारक शेतकरी.'),
                                                                              (1, 'hi', 'कौन पात्र है?', '2 हेक्टेयर तक भूमि वाले छोटे किसान।'),
                                                                              (2, 'en', 'How much benefit?', '₹6000 per year in three installments.'),
                                                                              (2, 'mr', 'किती लाभ?', 'वर्षाला ₹६००० तीन हप्त्यांमध्ये.'),
                                                                              (2, 'hi', 'कितना लाभ?', 'प्रति वर्ष ₹6000 तीन किस्तों में।');

-- Video (English only, test fallback)
INSERT INTO scheme_videos (scheme_id, lang_code, youtube_video_id, is_active) VALUES
    (1, 'en', 'dQw4w9WgXcQ', TRUE);