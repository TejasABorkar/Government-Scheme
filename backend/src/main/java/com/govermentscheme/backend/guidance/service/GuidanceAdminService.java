package com.govermentscheme.backend.guidance.service;

import com.govermentscheme.backend.common.exception.ApiException;
import com.govermentscheme.backend.common.exception.ErrorCode;
import com.govermentscheme.backend.guidance.dto.FaqRequest;
import com.govermentscheme.backend.guidance.dto.StepRequest;
import com.govermentscheme.backend.guidance.entity.*;
import com.govermentscheme.backend.guidance.repository.SchemeFaqRepository;
import com.govermentscheme.backend.guidance.repository.SchemeStepRepository;
import com.govermentscheme.backend.scheme.entity.Scheme;
import com.govermentscheme.backend.scheme.repository.SchemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuidanceAdminService {

    private final SchemeStepRepository stepRepository;
    private final SchemeFaqRepository faqRepository;
    private final SchemeRepository schemeRepository;

    @Transactional
    public void addStep(Long schemeId, StepRequest req) {
        Scheme scheme = schemeRepository.findById(schemeId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));

        SchemeStep step = SchemeStep.builder()
                .scheme(scheme)
                .stepNo(req.getStepNo())
                .build();

        for (StepRequest.TranslationReq tr : req.getTranslations()) {
            step.getTranslations().add(SchemeStepTranslation.builder()
                    .step(step)
                    .langCode(tr.getLangCode())
                    .instruction(tr.getInstruction())
                    .build());
        }
        stepRepository.save(step);
    }

    @Transactional
    public void deleteStep(Long stepId) {
        SchemeStep step = stepRepository.findById(stepId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Step not found"));
        stepRepository.delete(step);
    }

    @Transactional
    public void addFaq(Long schemeId, FaqRequest req) {
        Scheme scheme = schemeRepository.findById(schemeId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));

        SchemeFaq faq = SchemeFaq.builder()
                .scheme(scheme)
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();

        for (FaqRequest.TranslationReq tr : req.getTranslations()) {
            faq.getTranslations().add(SchemeFaqTranslation.builder()
                    .faq(faq)
                    .langCode(tr.getLangCode())
                    .question(tr.getQuestion())
                    .answer(tr.getAnswer())
                    .build());
        }
        faqRepository.save(faq);
    }

    @Transactional
    public void deleteFaq(Long faqId) {
        SchemeFaq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "FAQ not found"));
        faqRepository.delete(faq);
    }
}