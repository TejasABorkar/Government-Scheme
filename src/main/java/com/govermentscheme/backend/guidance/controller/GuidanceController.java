package com.govermentscheme.backend.guidance.controller;

import com.govermentscheme.backend.guidance.dto.SchemeGuidanceResponse;
import com.govermentscheme.backend.guidance.service.GuidanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schemes")
@RequiredArgsConstructor
public class GuidanceController {

    private final GuidanceService guidanceService;

    @GetMapping("/{schemeId}/guidance")
    public ResponseEntity<SchemeGuidanceResponse> getGuidance(
            @PathVariable Long schemeId,
            @RequestParam(defaultValue = "mr") String lang) {
        return ResponseEntity.ok(guidanceService.getGuidance(schemeId, lang));
    }
}