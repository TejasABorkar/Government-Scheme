package com.govermentscheme.backend.video.service;

import com.govermentscheme.backend.common.exception.ApiException;
import com.govermentscheme.backend.common.exception.ErrorCode;
import com.govermentscheme.backend.scheme.entity.Scheme;
import com.govermentscheme.backend.scheme.repository.SchemeRepository;
import com.govermentscheme.backend.video.dto.VideoRequest;
import com.govermentscheme.backend.video.dto.VideoResponse;
import com.govermentscheme.backend.video.entity.SchemeVideo;
import com.govermentscheme.backend.video.repository.SchemeVideoRepository;
import com.govermentscheme.backend.video.util.YoutubeUrlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoAdminService {

    private final SchemeVideoRepository repo;
    private final SchemeRepository schemeRepository;
    private final YoutubeUrlParser youtubeUrlParser;

    @Transactional
    public VideoResponse addOrUpdate(Long schemeId, VideoRequest req) {
        Scheme scheme = schemeRepository.findById(schemeId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Scheme not found"));

        String videoId = youtubeUrlParser.extractVideoId(req.getYoutubeUrlOrId());
        if (videoId == null) {
            throw new ApiException(ErrorCode.VALIDATION_ERROR, "Invalid YouTube URL or ID");
        }

        SchemeVideo video = repo.findBySchemeIdAndLangCodeAndIsActiveTrue(schemeId, req.getLangCode())
                .orElseGet(() -> SchemeVideo.builder()
                        .scheme(scheme)
                        .langCode(req.getLangCode())
                        .build());

        video.setYoutubeVideoId(videoId);
        video.setIsActive(req.getIsActive() != null ? req.getIsActive() : true);

        return toResponse(repo.save(video));
    }

    @Transactional
    public void delete(Long videoId) {
        SchemeVideo video = repo.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Video not found"));
        repo.delete(video);
    }

    @Transactional
    public void deactivate(Long videoId) {
        SchemeVideo video = repo.findById(videoId)
                .orElseThrow(() -> new ApiException(ErrorCode.VALIDATION_ERROR, "Video not found"));
        video.setIsActive(false);
        repo.save(video);
    }

    public List<VideoResponse> listByScheme(Long schemeId) {
        return repo.findAll().stream()
                .filter(v -> v.getScheme().getId().equals(schemeId))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private VideoResponse toResponse(SchemeVideo v) {
        return VideoResponse.builder()
                .id(v.getId())
                .langCode(v.getLangCode())
                .youtubeVideoId(v.getYoutubeVideoId())
                .embedUrl("https://www.youtube.com/embed/" + v.getYoutubeVideoId())
                .watchUrl("https://www.youtube.com/watch?v=" + v.getYoutubeVideoId())
                .isActive(v.getIsActive())
                .notice("This video is for guidance only. The official site is the authoritative source.")
                .build();
    }
}