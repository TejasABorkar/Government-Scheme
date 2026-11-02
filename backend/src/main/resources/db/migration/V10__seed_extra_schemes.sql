-- =========================================================
-- V10: SEED 20 REAL GOVERNMENT SCHEMES (From DAFW Report 2025-26)
-- No hardcoded IDs (Uses sub-queries for category_id)
-- Includes English, Marathi, and Hindi translations
-- =========================================================

-- 1. PM Fasal Bima Yojana (PMFBY)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='INSURANCE'), 'Ministry of Agriculture', 'https://pmfby.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'PM Fasal Bima Yojana', 'Crop insurance scheme for farmers.', 'Comprehensive risk insurance from pre-sowing to post-harvest.'),
                                                                                        (@s, 'mr', 'पंतप्रधान पीक विमा योजना', 'शेतकऱ्यांसाठी पीक विमा योजना.', 'पेरणीपूर्व ते काढणीपश्चात नुकसानीसाठी विमा संरक्षण.'),
                                                                                        (@s, 'hi', 'प्रधानमंत्री फसल बीमा योजना', 'किसानों के लिए फसल बीमा योजना।', 'बुवाई से कटाई के बाद तक सुरक्षा।');

-- 2. Kisan Credit Card (KCC)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='LOAN'), 'Department of Agriculture', 'https://www.myscheme.gov.in/schemes/kcc', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Kisan Credit Card', 'Credit facility for farmers for short-term needs.', 'Short-term credit up to ₹3 lakh at concessional interest rate (4% effective).'),
                                                                                        (@s, 'mr', 'किसान क्रेडिट कार्ड', 'शेतकऱ्यांसाठी अल्पमुदतीच्या गरजांसाठी कर्ज सुविधा.', '₹३ लाखांपर्यंत कमी व्याजदरात (४% प्रभावी) कर्ज.'),
                                                                                        (@s, 'hi', 'किसान क्रेडिट कार्ड', 'किसानों के लिए अल्पकालिक ऋण सुविधा।', '₹3 लाख तक रियायती ब्याज दर (4% प्रभावी) पर ऋण।');

-- 3. Agriculture Infrastructure Fund (AIF)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='LOAN'), 'Ministry of Agriculture', 'https://agrinfra.dac.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Agriculture Infrastructure Fund', 'Financing facility for post-harvest management infrastructure.', 'Interest subvention of 3% and credit guarantee for loans up to ₹2 Crores.'),
                                                                                        (@s, 'mr', 'कृषी पायाभूत सुविधा निधी', 'काढणीपश्चात व्यवस्थापन पायाभूत सुविधांसाठी वित्तपुरवठा.', '३% व्याज सवलत आणि ₹२ कोटींपर्यंत कर्ज हमी.'),
                                                                                        (@s, 'hi', 'कृषि अवसंरचना कोष', 'फसल कटाई के बाद प्रबंधन के लिए वित्तपोषण।', '3% ब्याज छूट और ₹2 करोड़ तक ऋण गारंटी।');

-- 4. National Beekeeping and Honey Mission (NBHM)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://nbb.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'National Beekeeping and Honey Mission', 'Promotion and development of scientific beekeeping.', 'Financial assistance for beekeeping, honey testing labs, and training.'),
                                                                                        (@s, 'mr', 'राष्ट्रीय मधुमक्षिका पालन आणि मध मिशन', 'वैज्ञानिक मधुमक्षिका पालनाचा प्रचार आणि विकास.', 'मधुमक्षिका पालन, मध चाचणी प्रयोगशाळा आणि प्रशिक्षणासाठी आर्थिक सहाय्य.'),
                                                                                        (@s, 'hi', 'राष्ट्रीय मधुमक्खी पालन और शहद मिशन', 'वैज्ञानिक मधुमक्खी पालन का प्रचार और विकास।', 'मधुमक्खी पालन, शहद परीक्षण प्रयोगशाळा और प्रशिक्षण के लिए वित्तीय सहायता।');

-- 5. National Food Security Mission (NFSM)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://nfsm.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'National Food Security Mission', 'Increase production of rice, wheat, and pulses.', 'Subsidies for seeds, fertilizers, and modern farming equipment.'),
                                                                                        (@s, 'mr', 'राष्ट्रीय अन्न सुरक्षा मिशन', 'तांदूळ, गहू आणि कडधान्यांचे उत्पादन वाढवणे.', 'बियाणे, खते आणि आधुनिक शेती अवजारांसाठी अनुदान.'),
                                                                                        (@s, 'hi', 'राष्ट्रीय खाद्य सुरक्षा मिशन', 'चावल, गेहूं और दालों का उत्पादन बढ़ाना।', 'बीज, उर्वरक और आधुनिक कृषि उपकरणों पर सब्सिडी।');

-- 6. Sub-Mission on Agricultural Mechanization (SMAM)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='EQUIPMENT'), 'Ministry of Agriculture', 'https://agrimachinery.nic.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Sub-Mission on Agricultural Mechanization', 'Promote agricultural mechanization and farm equipment.', 'Subsidies for tractors, power tillers, and custom hiring centers.'),
                                                                                        (@s, 'mr', 'कृषी यांत्रिकीकरण उप-अभियान', 'कृषी यांत्रिकीकरण आणि शेती अवजारांना प्रोत्साहन.', 'ट्रॅक्टर, पॉवर टिलर आणि कस्टम हायरिंग केंद्रांसाठी अनुदान.'),
                                                                                        (@s, 'hi', 'कृषि यंत्रीकरण उप-मिशन', 'कृषि यंत्रीकरण और कृषि उपकरणों को बढ़ावा।', 'ट्रैक्टर, पावर टिलर और कस्टम हायरिंग सेंटर के लिए सब्सिडी।');

-- 7. Per Drop More Crop (PDMC)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='IRRIGATION'), 'Ministry of Agriculture', 'https://pmksy.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Per Drop More Crop', 'Enhance water use efficiency through micro-irrigation.', 'Financial assistance for drip and sprinkler irrigation systems.'),
                                                                                        (@s, 'mr', 'प्रति थेंब अधिक पीक', 'सूक्ष्म सिंचनाद्वारे पाण्याचा वापर कार्यक्षमता वाढवणे.', 'ठिबक आणि तुषार सिंचन प्रणालीसाठी आर्थिक सहाय्य.'),
                                                                                        (@s, 'hi', 'प्रति बूंद अधिक फसल', 'सूक्ष्म सिंचाई के माध्यम से जल उपयोग दक्षता बढ़ाना।', 'ड्रिप और स्प्रिंकलर सिंचाई प्रणाली के लिए वित्तीय सहायता।');

-- 8. Rainfed Area Development (RAD)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://rkvy.nic.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Rainfed Area Development', 'Promote integrated farming systems in rainfed areas.', 'Support for crop diversification, livestock, and fisheries.'),
                                                                                        (@s, 'mr', 'कोरडवाहू क्षेत्र विकास', 'कोरडवाहू भागात एकात्मिक शेती प्रणालीला प्रोत्साहन.', 'पीक विविधीकरण, पशुधन आणि मत्स्यपालनासाठी आधार.'),
                                                                                        (@s, 'hi', 'वर्षा सिंचित क्षेत्र विकास', 'वर्षा आधारित क्षेत्रों में एकीकृत कृषि प्रणाली को बढ़ावा।', 'फसल विविधीकरण, पशुधन और मत्स्य पालन के लिए समर्थन।');

-- 9. Soil Health & Fertility
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://soilhealth.dac.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Soil Health & Fertility', 'Promote integrated nutrient management.', 'Soil testing and issuance of Soil Health Cards to farmers.'),
                                                                                        (@s, 'mr', 'माती आरोग्य आणि सुपीकता', 'एकात्मिक पोषक तत्व व्यवस्थापनाला प्रोत्साहन.', 'माती परीक्षण आणि शेतकऱ्यांना माती आरोग्य कार्ड.'),
                                                                                        (@s, 'hi', 'मृदा स्वास्थ्य और उर्वरता', 'एकीकृत पोषक तत्व प्रबंधन को बढ़ावा।', 'मृदा परीक्षण और किसानों को मृदा स्वास्थ्य कार्ड।');

-- 10. Paramparagat Krishi Vikas Yojana (PKVY)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://pgsindia-ncof.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Paramparagat Krishi Vikas Yojana', 'Promote organic farming in clusters.', 'Financial assistance for organic inputs and certification.'),
                                                                                        (@s, 'mr', 'परंपरागत कृषी विकास योजना', 'क्लस्टरमध्ये सेंद्रिय शेतीला प्रोत्साहन.', 'सेंद्रिय निविष्ठा आणि प्रमाणपत्रासाठी आर्थिक सहाय्य.'),
                                                                                        (@s, 'hi', 'परंपरागत कृषि विकास योजना', 'क्लस्टर में जैविक खेती को बढ़ावा।', 'जैविक इनपुट और प्रमाणन के लिए वित्तीय सहायता।');

-- 11. National Mission on Natural Farming (NMNF)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://naturalfarming.dac.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'National Mission on Natural Farming', 'Promote chemical-free natural farming.', 'Training, bio-input resource centers, and certification support.'),
                                                                                        (@s, 'mr', 'राष्ट्रीय नैसर्गिक शेती मिशन', 'रासायनिक खतमुक्त नैसर्गिक शेतीला प्रोत्साहन.', 'प्रशिक्षण, जैव-इनपुट संसाधन केंद्रे आणि प्रमाणनासाठी आधार.'),
                                                                                        (@s, 'hi', 'राष्ट्रीय प्राकृतिक खेती मिशन', 'रसायन मुक्त प्राकृतिक खेती को बढ़ावा।', 'प्रशिक्षण, जैव-इनपुट संसाधन केंद्र और प्रमाणन के लिए समर्थन।');

-- 12. National Bamboo Mission (NBM)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://nbm.nic.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'National Bamboo Mission', 'Develop the complete bamboo value chain.', 'Support for bamboo plantation and product development.'),
                                                                                        (@s, 'mr', 'राष्ट्रीय बांबू मिशन', 'संपूर्ण बांबू मूल्य साखळी विकसित करणे.', 'बांबू लागवड आणि उत्पादन विकासासाठी आधार.'),
                                                                                        (@s, 'hi', 'राष्ट्रीय बांस मिशन', 'संपूर्ण बांस मूल्य श्रृंखला विकसित करना।', 'बांस रोपण और उत्पाद विकास के लिए समर्थन।');

-- 13. Agriculture Marketing Infrastructure (AMI)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://agmarknet.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Agriculture Marketing Infrastructure', 'Develop marketing infrastructure and storage.', 'Capital investment subsidy for storage and marketing projects.'),
                                                                                        (@s, 'mr', 'कृषी विपणन पायाभूत सुविधा', 'विपणन पायाभूत सुविधा आणि साठवणूक विकसित करणे.', 'साठवणूक आणि विपणन प्रकल्पांसाठी भांडवली अनुदान.'),
                                                                                        (@s, 'hi', 'कृषि विपणन अवसंरचना', 'विपणन अवसंरचना और भंडारण विकसित करना।', 'भंडारण और विपणन परियोजनाओं के लिए पूंजी निवेश सब्सिडी।');

-- 14. Formation and Promotion of 10,000 FPOs
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://sfacfpo.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Formation and Promotion of 10,000 FPOs', 'Form and promote Farmer Producer Organizations.', 'Equity grant and credit guarantee for FPOs.'),
                                                                                        (@s, 'mr', '१०,००० एफपीओंची स्थापना आणि प्रचार', 'शेतकरी उत्पादक संस्थांची स्थापना आणि प्रचार.', 'एफपीओंसाठी इक्विटी अनुदान आणि कर्ज हमी.'),
                                                                                        (@s, 'hi', '10,000 एफपीओ का गठन और प्रचार', 'किसान उत्पादक संगठनों का गठन और प्रचार।', 'एफपीओ के लिए इक्विटी अनुदान और ऋण गारंटी।');

-- 15. PM Kisan Maan Dhan Yojana
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://maandhan.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'PM Kisan Maan Dhan Yojana', 'Old age pension scheme for farmers.', 'Minimum fixed pension of ₹3000 after 60 years.'),
                                                                                        (@s, 'mr', 'पीएम किसान मानधन योजना', 'शेतकऱ्यांसाठी वृद्धापकाळ निवृत्तीवेतन योजना.', '६० वर्षानंतर ₹३००० किमान निश्चित पेन्शन.'),
                                                                                        (@s, 'hi', 'पीएम किसान मानधन योजना', 'किसानों के लिए वृद्धावस्था पेंशन योजना।', '60 वर्ष के बाद ₹3000 न्यूनतम निश्चित पेंशन।');

-- 16. Modified Interest Subvention Scheme (MISS)
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='LOAN'), 'Ministry of Agriculture', 'https://www.myscheme.gov.in/schemes/miss', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Modified Interest Subvention Scheme', 'Concessional interest rates on short-term agri-loans.', 'Effective interest rate of 4% with prompt repayment.'),
                                                                                        (@s, 'mr', 'सुधारित व्याज सवलत योजना', 'अल्पमुदतीच्या कृषी कर्जांवर सवलतीचे व्याजदर.', 'वेळेवर परतफेडीसह ४% प्रभावी व्याजदर.'),
                                                                                        (@s, 'hi', 'संशोधित ब्याज सब्सिडी योजना', 'अल्पकालिक कृषि ऋणों पर रियायती ब्याज दर।', 'समय पर पुनर्भुगतान के साथ 4% प्रभावी ब्याज दर।');

-- 17. Namo Drone Didi Scheme
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='EQUIPMENT'), 'Ministry of Agriculture', 'https://www.myscheme.gov.in/schemes/namo-drone-didi', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Namo Drone Didi Scheme', 'Provide drones to Women Self Help Groups.', 'Financial assistance for drone purchase and training.'),
                                                                                        (@s, 'mr', 'नमो ड्रोन दीदी योजना', 'महिला स्वयंसहाय्यता गटांना ड्रोन पुरवणे.', 'ड्रोन खरेदी आणि प्रशिक्षणासाठी आर्थिक सहाय्य.'),
                                                                                        (@s, 'hi', 'नमो ड्रोन दीदी योजना', 'महिला स्वयं सहायता समूहों को ड्रोन प्रदान करना।', 'ड्रोन खरीद और प्रशिक्षण के लिए वित्तीय सहायता।');

-- 18. Mission for Aatmanirbharta in Pulses
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://www.myscheme.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'Mission for Aatmanirbharta in Pulses', 'Self-sufficiency in pulses production.', 'Assured procurement of Tur, Urad, and Masoor.'),
                                                                                        (@s, 'mr', 'कडधान्यात आत्मनिर्भरता मिशन', 'कडधान्य उत्पादनात स्वयंपूर्णता.', 'तूर, उडीद आणि मसूरची खात्रीशीर खरेदी.'),
                                                                                        (@s, 'hi', 'दलहन में आत्मनिर्भरता मिशन', 'दलहन उत्पादन में आत्मनिर्भरता।', 'तुअर, उड़द और मसूर की सुनिश्चित खरीद।');

-- 19. National Mission on Edible Oils - Oil Palm
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://nmeo.dac.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'National Mission on Edible Oils - Oil Palm', 'Enhance edible oil production through oil palm.', 'Financial assistance for oil palm cultivation and processing.'),
                                                                                        (@s, 'mr', 'राष्ट्रीय खाद्यतेल मिशन - ऑइल पाम', 'ऑइल पामद्वारे खाद्यतेल उत्पादन वाढवणे.', 'ऑइल पाम लागवड आणि प्रक्रियेसाठी आर्थिक सहाय्य.'),
                                                                                        (@s, 'hi', 'राष्ट्रीय खाद्य तेल मिशन - ऑयल पाम', 'ऑयल पाम के माध्यम से खाद्य तेल उत्पादन बढ़ाना।', 'ऑयल पाम खेती और प्रसंस्करण के लिए वित्तीय सहायता।');

-- 20. National Mission on Edible Oils - Oilseeds
INSERT INTO schemes (level, category_id, department, official_url, is_active, last_verified_at)
VALUES ('CENTRAL', (SELECT id FROM scheme_categories WHERE code='AGRICULTURE'), 'Ministry of Agriculture', 'https://nmeo.dac.gov.in/', TRUE, '2026-10-28');
SET @s = LAST_INSERT_ID();
INSERT INTO scheme_translations (scheme_id, lang_code, name, description, benefits) VALUES
                                                                                        (@s, 'en', 'National Mission on Edible Oils - Oilseeds', 'Boost oilseed production for self-reliance.', 'Support for seed hubs, value chain clusters, and oil extraction units.'),
                                                                                        (@s, 'mr', 'राष्ट्रीय खाद्यतेल मिशन - तेलबिया', 'स्वावलंबनासाठी तेलबिया उत्पादन वाढवणे.', 'बियाणे हब, व्हॅल्यू चेन क्लस्टर आणि तेल काढण्याच्या युनिट्ससाठी आधार.'),
                                                                                        (@s, 'hi', 'राष्ट्रीय खाद्य तेल मिशन - तिलहन', 'आत्मनिर्भरता के लिए तिलहन उत्पादन बढ़ाना।', 'बीज हब, वैल्यू चेन क्लस्टर और तेल निष्कर्षण इकाइयों के लिए समर्थन।');

-- =========================================================
-- BONUS: PM-KISAN (Update Description for ID 1)
-- Ha aadhi ch asel tar fakt description update karto
-- =========================================================
UPDATE scheme_translations SET description = 'Income support scheme for all landholding farmers.', benefits = '₹6000 per year in three installments.' WHERE scheme_id = 1 AND lang_code = 'en';
UPDATE scheme_translations SET description = 'अल्प व अत्यल्प भूधारक शेतकऱ्यांसाठी उत्पन्न सहाय्य योजना.', benefits = 'वर्षाला ₹६००० तीन हप्त्यांमध्ये.' WHERE scheme_id = 1 AND lang_code = 'mr';
UPDATE scheme_translations SET description = 'सभी भूमिधारक किसानों के लिए आय सहायता योजना।', benefits = 'प्रति वर्ष ₹6000 तीन किस्तों में।' WHERE scheme_id = 1 AND lang_code = 'hi';