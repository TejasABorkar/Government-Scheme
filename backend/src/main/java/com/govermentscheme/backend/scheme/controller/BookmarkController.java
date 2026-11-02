package com.govermentscheme.backend.scheme.controller;

import com.govermentscheme.backend.scheme.dto.BookmarkRequest;
import com.govermentscheme.backend.scheme.service.BookmarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    public ResponseEntity<List<Long>> list(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(bookmarkService.listSchemeIds(userId));
    }

    @PostMapping
    public ResponseEntity<Void> add(@AuthenticationPrincipal Long userId,
                                    @Valid @RequestBody BookmarkRequest req) {
        bookmarkService.add(userId, req.getSchemeId());
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{schemeId}")
    public ResponseEntity<Void> remove(@AuthenticationPrincipal Long userId,
                                       @PathVariable Long schemeId) {
        bookmarkService.remove(userId, schemeId);
        return ResponseEntity.noContent().build();
    }
}