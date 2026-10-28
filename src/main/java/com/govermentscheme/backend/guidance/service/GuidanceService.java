package com.govermentscheme.backend.guidance.service;

import com.govermentscheme.backend.common.exception.ApiException;
import com.govermentscheme.backend.common.exception.ErrorCode;
import com.govermentscheme.backend.guidance.dto.*;
import com.govermentscheme.backend.guidance.entity.*;
import com.govermentscheme.backend.guidance.repository.*;
import com.govermentscheme.backend.scheme.entity.Scheme;
import com.govermentscheme.backend.scheme.entity.SchemeTranslation;
import com.govermentscheme.backend.scheme.repository.SchemeRepository;
import com.govermentscheme.backend.video.dto.VideoResponse;
import com.govermentscheme.backend.video.service.SchemeVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuidanceService {

    private final SchemeRepository schemeRepository;
    private final SchemeRequiredDocumentRepository documentRepository;
    private final SchemeStepRepository stepRepository;
    private final SchemeFaqRepository faqRepository;
    private final SchemeVideoService videoService;

    private static final String DISCLAIMER =
            "This information is indicative only. The official government portal is the authoritative source.";

    @Transactional(readOnly = true)
    public SchemeGuidanceResponse getGuidance(Long schemeId, String lang) {
        Scheme scheme = schemeRepository.findByIdWithTranslations(schemeId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));

        String schemeName = resolveName(scheme, lang);

        List<DocumentItemResponse> docs = documentRepository.findBySchemeIdWithTranslations(schemeId)
                .stream().map(srd -> toDocItem(srd, lang)).collect(Collectors.toList());

        List<StepResponse> steps = stepRepository.findBySchemeIdWithTranslations(schemeId)
                .stream().map(s -> toStep(s, lang)).collect(Collectors.toList());

        List<FaqResponse> faqs = faqRepository.findBySchemeIdWithTranslations(schemeId)
                .stream().map(f -> toFaq(f, lang)).collect(Collectors.toList());

        VideoResponse video = videoService.resolveVideo(schemeId, lang).orElse(null);

        return SchemeGuidanceResponse.builder()
                .schemeId(schemeId)
                .schemeName(schemeName)
                .officialUrl(scheme.getOfficialUrl())
                .disclaimer(DISCLAIMER)
                .documents(docs)
                .steps(steps)
                .faqs(faqs)
                .video(video)
                .build();
    }

    private String resolveName(Scheme s, String lang) {
        if (s.getTranslations() == null || s.getTranslations().isEmpty()) return "Scheme";
        String target = (lang == null ? "en" : lang).toLowerCase();
        return s.getTranslations().stream()
                .filter(t -> target.equals(t.getLangCode())).findFirst()
                .or(() -> s.getTranslations().stream().filter(t -> "en".equals(t.getLangCode())).findFirst())
                .map(SchemeTranslation::getName).orElse("Scheme");
    }

    private DocumentItemResponse toDocItem(SchemeRequiredDocument srd, String lang) {
        String target = (lang == null ? "en" : lang).toLowerCase();
        DocumentTypeTranslation tr = srd.getDocumentType().getTranslations().stream()
                .filter(t -> target.equals(t.getLangCode())).findFirst()
                .or(() -> srd.getDocumentType().getTranslations().stream()
                        .filter(t -> "en".equals(t.getLangCode())).findFirst())
                .orElse(null);

        return DocumentItemResponse.builder()
                .documentTypeId(srd.getDocumentType().getId())
                .code(srd.getDocumentType().getCode())
                .name(tr != null ? tr.getName() : srd.getDocumentType().getCode())
                .whereToGet(tr != null ? tr.getWhereToGet() : null)
                .isMandatory(srd.getIsMandatory())
                .build();
    }

    private StepResponse toStep(SchemeStep step, String lang) {
        String target = (lang == null ? "en" : lang).toLowerCase();
        String instr = step.getTranslations().stream()
                .filter(t -> target.equals(t.getLangCode())).findFirst()
                .or(() -> step.getTranslations().stream()
                        .filter(t -> "en".equals(t.getLangCode())).findFirst())
                .map(SchemeStepTranslation::getInstruction)
                .orElse("");
        return StepResponse.builder().stepNo(step.getStepNo()).instruction(instr).build();
    }

    private FaqResponse toFaq(SchemeFaq faq, String lang) {
        String target = (lang == null ? "en" : lang).toLowerCase();
        SchemeFaqTranslation tr = faq.getTranslations().stream()
                .filter(t -> target.equals(t.getLangCode())).findFirst()
                .or(() -> faq.getTranslations().stream()
                        .filter(t -> "en".equals(t.getLangCode())).findFirst())
                .orElse(null);
        return FaqResponse.builder()
                .id(faq.getId())
                .displayOrder(faq.getDisplayOrder())
                .question(tr != null ? tr.getQuestion() : "")
                .answer(tr != null ? tr.getAnswer() : "")
                .build();
    }
}