package com.govermentscheme.backend.admin.controller;

import com.govermentscheme.backend.eligibility.dto.RuleResponse;
import com.govermentscheme.backend.eligibility.service.EligibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/schemes/{schemeId}/rules")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminRuleController {

    private final EligibilityService eligibilityService;

    @GetMapping
    public ResponseEntity<List<RuleResponse>> list(@PathVariable Long schemeId) {
        return ResponseEntity.ok(eligibilityService.listRules(schemeId));
    }
}