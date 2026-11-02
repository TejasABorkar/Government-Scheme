package com.govermentscheme.backend.admin.controller;

import com.govermentscheme.backend.video.dto.VideoRequest;
import com.govermentscheme.backend.video.dto.VideoResponse;
import com.govermentscheme.backend.video.service.VideoAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/schemes/{schemeId}/videos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminVideoController {

    private final VideoAdminService videoAdminService;

    @GetMapping
    public ResponseEntity<List<VideoResponse>> list(@PathVariable Long schemeId) {
        return ResponseEntity.ok(videoAdminService.listByScheme(schemeId));
    }

    @PostMapping
    public ResponseEntity<VideoResponse> addOrUpdate(
            @PathVariable Long schemeId,
            @Valid @RequestBody VideoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(videoAdminService.addOrUpdate(schemeId, req));
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> delete(@PathVariable Long schemeId,
                                       @PathVariable Long videoId) {
        videoAdminService.delete(videoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{videoId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long schemeId,
                                           @PathVariable Long videoId) {
        videoAdminService.deactivate(videoId);
        return ResponseEntity.noContent().build();
    }
}