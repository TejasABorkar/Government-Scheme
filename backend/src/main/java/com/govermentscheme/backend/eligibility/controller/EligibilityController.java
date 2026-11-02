package com.govermentscheme.backend.eligibility.controller;

import com.govermentscheme.backend.eligibility.dto.EligibilityResultResponse;
import com.govermentscheme.backend.eligibility.service.EligibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schemes")
@RequiredArgsConstructor
public class EligibilityController {

    private final EligibilityService eligibilityService;

    @PostMapping("/{schemeId}/eligibility")
    public ResponseEntity<EligibilityResultResponse> checkEligibility(
            @PathVariable Long schemeId,
            @RequestParam(defaultValue = "mr") String lang,
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(eligibilityService.evaluate(userId, schemeId, lang));
    }
}