package com.govermentscheme.backend.scheme.service;

import com.govermentscheme.backend.admin.service.AuditService;
import com.govermentscheme.backend.common.exception.ApiException;
import com.govermentscheme.backend.common.exception.ErrorCode;
import com.govermentscheme.backend.i18n.TranslationResolver;
import com.govermentscheme.backend.scheme.dto.*;
import com.govermentscheme.backend.scheme.entity.*;
import com.govermentscheme.backend.scheme.repository.SchemeCategoryRepository;
import com.govermentscheme.backend.scheme.repository.SchemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchemeService {

    private final SchemeRepository schemeRepository;
    private final SchemeCategoryRepository categoryRepository;
    private final TranslationResolver translationResolver;
    private final AuditService auditService;

    private static final String DISCLAIMER =
            "This information is indicative only. The official government portal is the authoritative source.";

    // ============================================
    // PUBLIC API
    // ============================================

    @Transactional(readOnly = true)
    public PagedResponse<SchemeSummaryResponse> searchPublic(
            String q, SchemeLevel level, Long categoryId,
            int page, int size, String lang) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Scheme> result = schemeRepository.searchPublic(q, level, categoryId, pageable);

        List<SchemeSummaryResponse> content = result.getContent().stream()
                .map(s -> toSummary(s, lang))
                .collect(Collectors.toList());

        return PagedResponse.<SchemeSummaryResponse>builder()
                .content(content)
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .last(result.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public SchemeDetailResponse getDetail(Long id, String lang) {
        Scheme scheme = schemeRepository.findByIdWithTranslations(id)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));
        if (!Boolean.TRUE.equals(scheme.getIsActive())) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not available");
        }
        return toDetail(scheme, lang, false);
    }

    // ============================================
    // ADMIN API
    // ============================================

    @Transactional
    public SchemeDetailResponse createScheme(SchemeRequest request, String lang) {
        SchemeCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Category not found"));

        Scheme scheme = Scheme.builder()
                .level(request.getLevel())
                .category(category)
                .department(request.getDepartment())
                .officialUrl(request.getOfficialUrl())
                .grReference(request.getGrReference())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isActive(false)
                .build();

        for (SchemeTranslationRequest tr : request.getTranslations()) {
            SchemeTranslation translation = SchemeTranslation.builder()
                    .scheme(scheme)
                    .langCode(tr.getLangCode())
                    .name(tr.getName())
                    .description(tr.getDescription())
                    .benefits(tr.getBenefits())
                    .eligibilitySummary(tr.getEligibilitySummary())
                    .build();
            scheme.getTranslations().add(translation);
        }

        Scheme saved = schemeRepository.save(scheme);

        // AUDIT LOG
        auditService.log(getCurrentUserId(), "CREATE", "SCHEME", saved.getId(), null,
                Map.of("name", saved.getTranslations().isEmpty() ? "" :
                        saved.getTranslations().get(0).getName()));

        return toDetail(saved, lang, true);
    }

    @Transactional
    public SchemeDetailResponse updateScheme(Long id, SchemeRequest request, String lang) {
        Scheme scheme = schemeRepository.findByIdWithTranslations(id)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));

        SchemeCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Category not found"));

        // Snapshot old values for audit
        Map<String, Object> oldValues = Map.of(
                "department", scheme.getDepartment() != null ? scheme.getDepartment() : "",
                "officialUrl", scheme.getOfficialUrl()
        );

        scheme.setLevel(request.getLevel());
        scheme.setCategory(category);
        scheme.setDepartment(request.getDepartment());
        scheme.setOfficialUrl(request.getOfficialUrl());
        scheme.setGrReference(request.getGrReference());
        scheme.setStartDate(request.getStartDate());
        scheme.setEndDate(request.getEndDate());

        scheme.getTranslations().clear();
        for (SchemeTranslationRequest tr : request.getTranslations()) {
            scheme.getTranslations().add(SchemeTranslation.builder()
                    .scheme(scheme)
                    .langCode(tr.getLangCode())
                    .name(tr.getName())
                    .description(tr.getDescription())
                    .benefits(tr.getBenefits())
                    .eligibilitySummary(tr.getEligibilitySummary())
                    .build());
        }

        Scheme saved = schemeRepository.save(scheme);

        // AUDIT LOG
        auditService.log(getCurrentUserId(), "UPDATE", "SCHEME", id, oldValues,
                Map.of("department", saved.getDepartment() != null ? saved.getDepartment() : "",
                        "officialUrl", saved.getOfficialUrl()));

        return toDetail(saved, lang, true);
    }

    @Transactional
    public SchemeDetailResponse activate(Long id) {
        Scheme s = schemeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));
        s.setIsActive(true);
        Scheme saved = schemeRepository.save(s);

        auditService.log(getCurrentUserId(), "ACTIVATE", "SCHEME", id,
                Map.of("isActive", false), Map.of("isActive", true));

        return toDetail(saved, "en", true);
    }

    @Transactional
    public SchemeDetailResponse deactivate(Long id) {
        Scheme s = schemeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));
        s.setIsActive(false);
        Scheme saved = schemeRepository.save(s);

        auditService.log(getCurrentUserId(), "DEACTIVATE", "SCHEME", id,
                Map.of("isActive", true), Map.of("isActive", false));

        return toDetail(saved, "en", true);
    }

    @Transactional
    public void delete(Long id) {
        Scheme s = schemeRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));
        schemeRepository.delete(s);

        auditService.log(getCurrentUserId(), "DELETE", "SCHEME", id,
                Map.of("id", id), null);
    }

    @Transactional(readOnly = true)
    public PagedResponse<SchemeSummaryResponse> listAdmin(
            String q, SchemeLevel level, Long categoryId,
            int page, int size, String lang) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Scheme> result = schemeRepository.searchPublic(q, level, categoryId, pageable);

        List<SchemeSummaryResponse> content = result.getContent().stream()
                .map(s -> toSummary(s, lang))
                .collect(Collectors.toList());

        return PagedResponse.<SchemeSummaryResponse>builder()
                .content(content)
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .last(result.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public SchemeDetailResponse getAdminDetail(Long id, String lang) {
        Scheme scheme = schemeRepository.findByIdWithTranslations(id)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));
        return toDetail(scheme, lang, true);
    }

    @Transactional(readOnly = true)
    public List<SchemeCategoryResponse> listCategories(String lang) {
        return categoryRepository.findByIsActiveTrueOrderByIdAsc().stream()
                .map(c -> {
                    String name = translationResolver.resolveCategory(c.getTranslations(), lang)
                            .map(SchemeCategoryTranslation::getName)
                            .orElse(c.getCode());
                    return SchemeCategoryResponse.builder()
                            .id(c.getId())
                            .code(c.getCode())
                            .name(name)
                            .isActive(c.getIsActive())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // ============================================
    // MAPPERS
    // ============================================

    private SchemeSummaryResponse toSummary(Scheme s, String lang) {
        String name = translationResolver.resolveScheme(s.getTranslations(), lang)
                .map(SchemeTranslation::getName)
                .orElse("Untitled");
        String desc = translationResolver.resolveScheme(s.getTranslations(), lang)
                .map(SchemeTranslation::getDescription)
                .orElse(null);
        String shortDesc = desc != null && desc.length() > 150
                ? desc.substring(0, 150) + "..." : desc;

        // Category name from code (safe, no lazy load)
        String categoryName = s.getCategory() != null ? s.getCategory().getCode() : "General";

        return SchemeSummaryResponse.builder()
                .id(s.getId())
                .name(name)
                .shortDescription(shortDesc)
                .level(s.getLevel())
                .categoryName(categoryName)
                .department(s.getDepartment())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .isActive(s.getIsActive())
                .isExpired(s.isExpired())
                .build();
    }

    private SchemeDetailResponse toDetail(Scheme s, String lang, boolean includeTranslationStatus) {
        SchemeTranslation tr = translationResolver.resolveScheme(s.getTranslations(), lang).orElse(null);

        // Ethe category translations lazy load hotil, pan he method @Transactional madhe ahe tyamule safe ahe.
        String categoryName = translationResolver.resolveCategory(s.getCategory().getTranslations(), lang)
                .map(SchemeCategoryTranslation::getName)
                .orElse(s.getCategory().getCode());

        List<SchemeDetailResponse.TranslationStatus> statuses = null;
        if (includeTranslationStatus) {
            statuses = Language.SUPPORTED.stream()
                    .map(lc -> SchemeDetailResponse.TranslationStatus.builder()
                            .langCode(lc)
                            .present(s.getTranslations().stream()
                                    .anyMatch(t -> t.getLangCode().equals(lc)))
                            .build())
                    .collect(Collectors.toList());
        }

        return SchemeDetailResponse.builder()
                .id(s.getId())
                .name(tr != null ? tr.getName() : "Untitled")
                .description(tr != null ? tr.getDescription() : null)
                .benefits(tr != null ? tr.getBenefits() : null)
                .eligibilitySummary(tr != null ? tr.getEligibilitySummary() : null)
                .level(s.getLevel())
                .categoryName(categoryName)
                .department(s.getDepartment())
                .officialUrl(s.getOfficialUrl())
                .grReference(s.getGrReference())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .isActive(s.getIsActive())
                .isExpired(s.isExpired())
                .lastVerifiedAt(s.getLastVerifiedAt())
                .disclaimer(DISCLAIMER)
                .translations(statuses)
                .build();
    }

    // ============================================
    // HELPER
    // ============================================

    private Long getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) return null;
        try {
            return Long.valueOf(auth.getPrincipal().toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}