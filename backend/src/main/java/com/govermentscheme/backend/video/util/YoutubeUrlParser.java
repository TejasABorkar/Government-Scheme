package com.govermentscheme.backend.video.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class YoutubeUrlParser {

    private static final Pattern[] PATTERNS = new Pattern[]{
            Pattern.compile("(?:youtube\\.com/watch\\?v=)([a-zA-Z0-9_-]{11})"),
            Pattern.compile("(?:youtu\\.be/)([a-zA-Z0-9_-]{11})"),
            Pattern.compile("(?:youtube\\.com/embed/)([a-zA-Z0-9_-]{11})"),
            Pattern.compile("^([a-zA-Z0-9_-]{11})$")
    };

    public String extractVideoId(String urlOrId) {
        if (urlOrId == null || urlOrId.isBlank()) return null;
        String input = urlOrId.trim();
        for (Pattern p : PATTERNS) {
            Matcher m = p.matcher(input);
            if (m.find()) return m.group(1);
        }
        return null;
    }
}