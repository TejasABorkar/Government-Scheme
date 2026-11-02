package com.govermentscheme.backend.scheme.repository;

import com.govermentscheme.backend.scheme.entity.Bookmark;
import com.govermentscheme.backend.scheme.entity.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {
    List<Bookmark> findByUserId(Long userId);
    boolean existsByUserIdAndSchemeId(Long userId, Long schemeId);
    void deleteByUserIdAndSchemeId(Long userId, Long schemeId);
}