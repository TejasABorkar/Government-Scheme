package com.govermentscheme.backend.scheme.service;

import com.govermentscheme.backend.scheme.entity.Bookmark;
import com.govermentscheme.backend.scheme.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository repo;

    @Transactional
    public void add(Long userId, Long schemeId) {
        if (!repo.existsByUserIdAndSchemeId(userId, schemeId)) {
            repo.save(Bookmark.builder().userId(userId).schemeId(schemeId).build());
        }
    }

    @Transactional
    public void remove(Long userId, Long schemeId) {
        repo.deleteByUserIdAndSchemeId(userId, schemeId);
    }

    public List<Long> listSchemeIds(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(Bookmark::getSchemeId).collect(Collectors.toList());
    }
}