package com.govermentscheme.backend.scheme.controller;

import com.govermentscheme.backend.scheme.dto.*;
import com.govermentscheme.backend.scheme.entity.SchemeLevel;
import com.govermentscheme.backend.scheme.service.SchemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schemes")
@RequiredArgsConstructor
public class SchemeController {

    private final SchemeService schemeService;

    @GetMapping
    public ResponseEntity<PagedResponse<SchemeSummaryResponse>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) SchemeLevel level,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "mr") String lang) {
        return ResponseEntity.ok(schemeService.searchPublic(q, level, categoryId, page, size, lang));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchemeDetailResponse> detail(@PathVariable Long id,
                                                       @RequestParam(defaultValue = "mr") String lang) {
        return ResponseEntity.ok(schemeService.getDetail(id, lang));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<SchemeCategoryResponse>> categories(
            @RequestParam(defaultValue = "mr") String lang) {
        return ResponseEntity.ok(schemeService.listCategories(lang));
    }
}