package com.govermentscheme.backend.admin.controller;

import com.govermentscheme.backend.scheme.dto.*;
import com.govermentscheme.backend.scheme.entity.SchemeLevel;
import com.govermentscheme.backend.scheme.service.SchemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/schemes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminSchemeController {

    private final SchemeService schemeService;

    @GetMapping
    public ResponseEntity<PagedResponse<SchemeSummaryResponse>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) SchemeLevel level,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(schemeService.listAdmin(q, level, categoryId, page, size, lang));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchemeDetailResponse> get(@PathVariable Long id,
                                                    @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(schemeService.getAdminDetail(id, lang));
    }

    @PostMapping
    public ResponseEntity<SchemeDetailResponse> create(@Valid @RequestBody SchemeRequest request,
                                                       @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(schemeService.createScheme(request, lang));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchemeDetailResponse> update(@PathVariable Long id,
                                                       @Valid @RequestBody SchemeRequest request,
                                                       @RequestParam(defaultValue = "en") String lang) {
        return ResponseEntity.ok(schemeService.updateScheme(id, request, lang));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<SchemeDetailResponse> activate(@PathVariable Long id) {
        return ResponseEntity.ok(schemeService.activate(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<SchemeDetailResponse> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(schemeService.deactivate(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        schemeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}