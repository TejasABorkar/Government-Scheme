package com.govermentscheme.backend.scheme.repository;

import com.govermentscheme.backend.scheme.entity.UserNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserNoteRepository extends JpaRepository<UserNote, Long> {
    List<UserNote> findByUserId(Long userId);
    Optional<UserNote> findByIdAndUserId(Long id, Long userId);
    List<UserNote> findByUserIdAndSchemeId(Long userId, Long schemeId);
}