package com.govermentscheme.backend.admin.controller;

import com.govermentscheme.backend.guidance.dto.FaqRequest;
import com.govermentscheme.backend.guidance.dto.StepRequest;
import com.govermentscheme.backend.guidance.service.GuidanceAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/schemes/{schemeId}")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminGuidanceController {

    private final GuidanceAdminService guidanceAdminService;

    @PostMapping("/steps")
    public ResponseEntity<Void> addStep(@PathVariable Long schemeId,
                                        @Valid @RequestBody StepRequest req) {
        guidanceAdminService.addStep(schemeId, req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/steps/{stepId}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long schemeId,
                                           @PathVariable Long stepId) {
        guidanceAdminService.deleteStep(stepId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/faqs")
    public ResponseEntity<Void> addFaq(@PathVariable Long schemeId,
                                       @Valid @RequestBody FaqRequest req) {
        guidanceAdminService.addFaq(schemeId, req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/faqs/{faqId}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long schemeId,
                                          @PathVariable Long faqId) {
        guidanceAdminService.deleteFaq(faqId);
        return ResponseEntity.noContent().build();
    }
}