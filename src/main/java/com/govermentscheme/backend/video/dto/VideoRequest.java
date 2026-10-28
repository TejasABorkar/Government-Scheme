package com.govermentscheme.backend.video.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VideoRequest {

    @NotBlank(message = "YouTube URL or ID is required")
    private String youtubeUrlOrId;

    @NotBlank(message = "Language is required")
    @Pattern(regexp = "^(mr|hi|en)$", message = "Language must be mr, hi, or en")
    private String langCode;

    private Boolean isActive = true;
}