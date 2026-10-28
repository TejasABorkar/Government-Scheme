package com.govermentscheme.backend.scheme.service;

import com.govermentscheme.backend.common.exception.ApiException;
import com.govermentscheme.backend.common.exception.ErrorCode;
import com.govermentscheme.backend.scheme.dto.NoteRequest;
import com.govermentscheme.backend.scheme.dto.NoteResponse;
import com.govermentscheme.backend.scheme.entity.UserNote;
import com.govermentscheme.backend.scheme.repository.UserNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final UserNoteRepository repo;

    @Transactional
    public NoteResponse create(Long userId, NoteRequest req) {
        UserNote note = UserNote.builder()
                .userId(userId).schemeId(req.getSchemeId()).note(req.getNote()).build();
        return toResponse(repo.save(note));
    }

    @Transactional
    public NoteResponse update(Long userId, Long id, NoteRequest req) {
        UserNote note = repo.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.FORBIDDEN, "Note not found"));
        note.setNote(req.getNote());
        return toResponse(repo.save(note));
    }

    @Transactional
    public void delete(Long userId, Long id) {
        UserNote note = repo.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.FORBIDDEN, "Note not found"));
        repo.delete(note);
    }

    public List<NoteResponse> list(Long userId) {
        return repo.findByUserId(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    private NoteResponse toResponse(UserNote n) {
        return NoteResponse.builder()
                .id(n.getId()).schemeId(n.getSchemeId()).note(n.getNote())
                .createdAt(n.getCreatedAt()).updatedAt(n.getUpdatedAt()).build();
    }
}