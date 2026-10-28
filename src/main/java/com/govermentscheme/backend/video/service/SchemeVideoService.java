package com.govermentscheme.backend.video.service;

import com.govermentscheme.backend.video.dto.VideoResponse;
import com.govermentscheme.backend.video.entity.SchemeVideo;
import com.govermentscheme.backend.video.repository.SchemeVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchemeVideoService {

    private final SchemeVideoRepository repo;

    private static final String NOTICE =
            "This video is for guidance only. The official site is the authoritative source.";

    public Optional<VideoResponse> resolveVideo(Long schemeId, String lang) {
        List<String> fallback = switch (lang == null ? "en" : lang.toLowerCase()) {
            case "mr" -> List.of("mr", "hi", "en");
            case "hi" -> List.of("hi", "mr", "en");
            default -> List.of("en", "mr", "hi");
        };

        for (String lc : fallback) {
            Optional<SchemeVideo> v = repo.findBySchemeIdAndLangCodeAndIsActiveTrue(schemeId, lc);
            if (v.isPresent()) {
                SchemeVideo video = v.get();
                return Optional.of(VideoResponse.builder()
                        .id(video.getId())
                        .langCode(video.getLangCode())
                        .youtubeVideoId(video.getYoutubeVideoId())
                        .embedUrl("https://www.youtube.com/embed/" + video.getYoutubeVideoId())
                        .watchUrl("https://www.youtube.com/watch?v=" + video.getYoutubeVideoId())
                        .isActive(true)
                        .notice(NOTICE)
                        .build());
            }
        }
        return Optional.empty();
    }
}