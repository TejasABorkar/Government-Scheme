package com.govermentscheme.backend.user.controller;

import com.govermentscheme.backend.user.dto.*;
import com.govermentscheme.backend.user.service.FarmerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final FarmerProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> get(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(profileService.getProfile(userId));
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> save(@AuthenticationPrincipal Long userId,
                                                @Valid @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(profileService.saveProfile(userId, request));
    }

    @GetMapping("/completeness")
    public ResponseEntity<ProfileCompletenessResponse> completeness(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(profileService.getCompleteness(userId));
    }

    @DeleteMapping("/account")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal Long userId) {
        profileService.deleteAccount(userId);
        return ResponseEntity.noContent().build();
    }
}