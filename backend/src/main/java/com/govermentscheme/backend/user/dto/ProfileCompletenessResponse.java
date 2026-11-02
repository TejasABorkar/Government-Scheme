package com.govermentscheme.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data @AllArgsConstructor
public class ProfileCompletenessResponse {
    private int percentage;
    private List<String> missingFields;
}