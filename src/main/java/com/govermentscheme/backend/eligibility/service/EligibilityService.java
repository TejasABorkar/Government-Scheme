package com.govermentscheme.backend.eligibility.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.govermentscheme.backend.common.exception.ApiException;
import com.govermentscheme.backend.common.exception.ErrorCode;
import com.govermentscheme.backend.eligibility.FieldRegistry;
import com.govermentscheme.backend.eligibility.dto.EligibilityResultResponse;
import com.govermentscheme.backend.eligibility.entity.*;
import com.govermentscheme.backend.eligibility.operator.Operator;
import com.govermentscheme.backend.eligibility.operator.OperatorFactory;
import com.govermentscheme.backend.eligibility.repository.EligibilityHistoryRepository;
import com.govermentscheme.backend.eligibility.repository.EligibilityRuleRepository;
import com.govermentscheme.backend.scheme.entity.Scheme;
import com.govermentscheme.backend.scheme.entity.SchemeTranslation;
import com.govermentscheme.backend.scheme.repository.SchemeRepository;
import com.govermentscheme.backend.user.entity.FarmerProfile;
import com.govermentscheme.backend.user.repository.FarmerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EligibilityService {

    private final SchemeRepository schemeRepository;
    private final FarmerProfileRepository profileRepository;
    private final EligibilityRuleRepository ruleRepository;
    private final EligibilityHistoryRepository historyRepository;
    private final FieldRegistry fieldRegistry;
    private final OperatorFactory operatorFactory;
    private final ObjectMapper objectMapper;

    private static final String NOTICE =
            "This result is indicative only. The final decision rests with the government.";
    private static final String NOTICE_MR =
            "हा निकाल सूचक आहे. अंतिम निर्णय सरकारचा असेल.";
    private static final String NOTICE_HI =
            "यह परिणाम सूचक है। अंतिम निर्णय सरकार का होगा।";

    @Transactional
    public EligibilityResultResponse evaluate(Long userId, Long schemeId, String lang) {
        Scheme scheme = schemeRepository.findByIdWithTranslations(schemeId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));

        if (!Boolean.TRUE.equals(scheme.getIsActive())) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not active");
        }

        FarmerProfile profile = profileRepository.findByUserIdWithLandHoldings(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR,
                        "Please complete your profile first"));

        List<EligibilityRule> rules = ruleRepository.findBySchemeIdWithTranslations(schemeId);

        List<EligibilityResultResponse.FailedRule> failed = new ArrayList<>();
        List<String> missingFields = new ArrayList<>();

        if (rules.isEmpty()) {
            // No rules → Eligible with notice
            EligibilityResultResponse response = buildResponse(
                    scheme, EligibilityResult.ELIGIBLE,
                    failed, missingFields, lang, schemeId, userId);
            return response;
        }

        for (EligibilityRule rule : rules) {
            Object actual = fieldRegistry.extract(rule.getField(), profile);

            if (actual == null) {
                missingFields.add(rule.getField());
                continue;
            }

            Operator op = operatorFactory.get(rule.getOperator());
            if (op == null) {
                throw new ApiException(ErrorCode.VALIDATION_ERROR,
                        "Unknown operator: " + rule.getOperator());
            }

            boolean pass = op.evaluate(actual, rule.getValue());
            if (!pass) {
                String msg = resolveMessage(rule, lang);
                failed.add(EligibilityResultResponse.FailedRule.builder()
                        .field(rule.getField())
                        .operator(rule.getOperator().name())
                        .expected(rule.getValue())
                        .isMandatory(rule.getIsMandatory())
                        .message(msg)
                        .build());
            }
        }

        EligibilityResult result = decide(failed, missingFields);
        return buildResponse(scheme, result, failed, missingFields, lang, schemeId, userId);
    }

    private EligibilityResult decide(
            List<EligibilityResultResponse.FailedRule> failed,
            List<String> missingFields) {
        if (failed.isEmpty() && missingFields.isEmpty()) {
            return EligibilityResult.ELIGIBLE;
        }
        boolean anyMandatoryFailed = failed.stream()
                .anyMatch(f -> Boolean.TRUE.equals(f.getIsMandatory()));
        if (anyMandatoryFailed) {
            return EligibilityResult.NOT_ELIGIBLE;
        }
        return EligibilityResult.PARTIALLY_ELIGIBLE;
    }

    private String resolveMessage(EligibilityRule rule, String lang) {
        if (rule.getTranslations() == null || rule.getTranslations().isEmpty()) return "Rule failed";
        String target = lang == null ? "en" : lang.toLowerCase();
        return rule.getTranslations().stream()
                .filter(t -> target.equals(t.getLangCode()))
                .findFirst()
                .or(() -> rule.getTranslations().stream()
                        .filter(t -> "en".equals(t.getLangCode()))
                        .findFirst())
                .map(EligibilityRuleTranslation::getMessage)
                .orElse("Rule failed");
    }

    private EligibilityResultResponse buildResponse(
            Scheme scheme, EligibilityResult result,
            List<EligibilityResultResponse.FailedRule> failed,
            List<String> missingFields, String lang,
            Long schemeId, Long userId) {

        String schemeName = scheme.getTranslations().stream()
                .filter(t -> (lang != null ? lang : "en").equalsIgnoreCase(t.getLangCode()))
                .findFirst()
                .or(() -> scheme.getTranslations().stream()
                        .filter(t -> "en".equals(t.getLangCode())).findFirst())
                .map(SchemeTranslation::getName)
                .orElse("Scheme");

        String notice = switch (lang == null ? "en" : lang.toLowerCase()) {
            case "mr" -> NOTICE_MR;
            case "hi" -> NOTICE_HI;
            default -> NOTICE;
        };

        LocalDateTime checkedAt = LocalDateTime.now();

        // Save history
        try {
            historyRepository.save(EligibilityCheckHistory.builder()
                    .userId(userId)
                    .schemeId(schemeId)
                    .result(result)
                    .reasons(objectMapper.writeValueAsString(failed))
                    .missingFields(objectMapper.writeValueAsString(missingFields))
                    .build());
        } catch (Exception ignored) {}

        return EligibilityResultResponse.builder()
                .schemeId(schemeId)
                .schemeName(schemeName)
                .result(result)
                .failedRules(failed)
                .missingFields(missingFields)
                .notice(notice)
                .checkedAt(checkedAt)
                .build();
    }

    // ============================================
    // ADMIN — Rules CRUD
    // ============================================

    @Transactional(readOnly = true)
    public List<com.govermentscheme.backend.eligibility.dto.RuleResponse> listRules(Long schemeId) {
        return ruleRepository.findBySchemeIdWithTranslations(schemeId).stream()
                .map(this::toRuleResponse)
                .collect(Collectors.toList());
    }

    private com.govermentscheme.backend.eligibility.dto.RuleResponse toRuleResponse(EligibilityRule r) {
        return com.govermentscheme.backend.eligibility.dto.RuleResponse.builder()
                .id(r.getId())
                .field(r.getField())
                .operator(r.getOperator())
                .value(r.getValue())
                .isMandatory(r.getIsMandatory())
                .displayOrder(r.getDisplayOrder())
                .translations(r.getTranslations().stream()
                        .map(t -> com.govermentscheme.backend.eligibility.dto.RuleResponse.TranslationInfo.builder()
                                .langCode(t.getLangCode())
                                .message(t.getMessage())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}