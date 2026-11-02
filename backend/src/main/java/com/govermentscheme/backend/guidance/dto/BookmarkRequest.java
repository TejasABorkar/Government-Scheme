package com.govermentscheme.backend.scheme.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookmarkRequest {
    @NotNull
    private Long schemeId;
}