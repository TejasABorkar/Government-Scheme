package com.govermentscheme.backend.admin.service;

import com.govermentscheme.backend.admin.dto.BrokenLinkResponse;
import com.govermentscheme.backend.admin.entity.LinkCheckResult;
import com.govermentscheme.backend.admin.repository.LinkCheckResultRepository;
import com.govermentscheme.backend.scheme.entity.Scheme;
import com.govermentscheme.backend.scheme.repository.SchemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LinkCheckService {

    private final SchemeRepository schemeRepository;
    private final LinkCheckResultRepository linkRepo;

    @Transactional
    public List<BrokenLinkResponse> checkAllLinks() {
        List<Scheme> schemes = schemeRepository.findAll();
        for (Scheme s : schemes) {
            int status = 0;
            boolean working = false;
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(s.getOfficialUrl()).openConnection();
                conn.setRequestMethod("HEAD");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setInstanceFollowRedirects(true);
                status = conn.getResponseCode();
                working = status >= 200 && status < 400;
                conn.disconnect();
            } catch (Exception ignored) {}

            linkRepo.save(LinkCheckResult.builder()
                    .schemeId(s.getId())
                    .url(s.getOfficialUrl())
                    .statusCode(status)
                    .isWorking(working)
                    .build());
        }
        return getBrokenLinks();
    }

    public List<BrokenLinkResponse> getBrokenLinks() {
        return linkRepo.findByIsWorkingFalse().stream()
                .map(l -> {
                    String name = schemeRepository.findById(l.getSchemeId())
                            .map(s -> s.getTranslations().isEmpty() ? "Scheme" :
                                    s.getTranslations().get(0).getName())
                            .orElse("Unknown");
                    return BrokenLinkResponse.builder()
                            .schemeId(l.getSchemeId())
                            .schemeName(name)
                            .url(l.getUrl())
                            .statusCode(l.getStatusCode())
                            .checkedAt(l.getCheckedAt())
                            .build();
                }).collect(Collectors.toList());
    }
}