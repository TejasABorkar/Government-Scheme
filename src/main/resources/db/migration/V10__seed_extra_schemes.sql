-- Additional schemes (5 total, tu 10+ karu shakto)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at) VALUES
                                                                                                    ('CENTRAL', 3, 'Ministry of Agriculture', 'https://pmfby.gov.in/', TRUE, '2026-10-28'),
                                                                                                    ('CENTRAL', 5, 'Ministry of Agriculture', 'https://agrimachinery.nic.in/', TRUE, '2026-10-28'),
                                                                                                    ('STATE', 2, 'Maharashtra Agri Dept', 'https://krishi.maharashtra.gov.in/', TRUE, '2026-10-28'),
                                                                                                    ('STATE', 6, 'Maharashtra Irrigation Dept', 'https://sjsa.maharashtra.gov.in/', TRUE, '2026-10-28'),
                                                                                                    ('CENTRAL', 4, 'NABARD', 'https://www.nabard.org/', TRUE, '2026-10-28');

-- Translations for scheme 2-6
INSERT INTO scheme_translations (scheme_id, lang_code, name, description) VALUES
                                                                              (2, 'en', 'PM Fasal Bima Yojana', 'Crop insurance scheme for farmers.'),
                                                                              (2, 'mr', 'पंतप्रधान पीक विमा योजना', 'शेतकऱ्यांसाठी पीक विमा योजना.'),
                                                                              (3, 'en', 'Sub-Mission on Agricultural Mechanization', 'Subsidy for farm equipment.'),
                                                                              (3, 'mr', 'कृषी यांत्रिकीकरण उप-अभियान', 'कृषी अवजारांसाठी अनुदान.'),
                                                                              (4, 'en', 'Maharashtra Krishi Yojana', 'State agriculture scheme.'),
                                                                              (4, 'mr', 'महाराष्ट्र कृषी योजना', 'राज्य कृषी योजना.'),
                                                                              (5, 'en', 'Jalyukt Shivar Abhiyan', 'Water conservation scheme.'),
                                                                              (5, 'mr', 'जलयुक्त शिवार अभियान', 'जलसंधारण योजना.'),
                                                                              (6, 'en', 'Kisan Credit Card', 'Credit facility for farmers.'),
                                                                              (6, 'mr', 'किसान क्रेडिट कार्ड', 'शेतकऱ्यांसाठी कर्ज सुविधा.');