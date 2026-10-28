package com.govermentscheme.backend.scheme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteRequest {
    @NotNull
    private Long schemeId;

    @NotBlank
    @Size(max = 2000)
    private String note;
}