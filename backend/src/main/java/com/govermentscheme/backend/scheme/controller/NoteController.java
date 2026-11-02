package com.govermentscheme.backend.scheme.controller;

import com.govermentscheme.backend.scheme.dto.NoteRequest;
import com.govermentscheme.backend.scheme.dto.NoteResponse;
import com.govermentscheme.backend.scheme.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponse>> list(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(noteService.list(userId));
    }

    @PostMapping
    public ResponseEntity<NoteResponse> create(@AuthenticationPrincipal Long userId,
                                               @Valid @RequestBody NoteRequest req) {
        return ResponseEntity.status(201).body(noteService.create(userId, req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> update(@AuthenticationPrincipal Long userId,
                                               @PathVariable Long id,
                                               @Valid @RequestBody NoteRequest req) {
        return ResponseEntity.ok(noteService.update(userId, id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Long userId,
                                       @PathVariable Long id) {
        noteService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}