package com.govermentscheme.backend.user.controller;

import com.govermentscheme.backend.user.dto.*;
import com.govermentscheme.backend.user.service.LandHoldingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me/land")
@RequiredArgsConstructor
public class LandHoldingController {

    private final LandHoldingService landService;

    @GetMapping
    public ResponseEntity<List<LandHoldingResponse>> list(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(landService.list(userId));
    }

    @PostMapping
    public ResponseEntity<LandHoldingResponse> add(@AuthenticationPrincipal Long userId,
                                                   @Valid @RequestBody LandHoldingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(landService.add(userId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LandHoldingResponse> update(@AuthenticationPrincipal Long userId,
                                                      @PathVariable Long id,
                                                      @Valid @RequestBody LandHoldingRequest request) {
        return ResponseEntity.ok(landService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long userId,
                                       @PathVariable Long id) {
        landService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}