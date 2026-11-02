package com.govermentscheme.backend.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private Long userId;
    private String name;
    private String role;
    private String preferredLanguage;

    public AuthResponse() {}

    public AuthResponse(String accessToken, String tokenType, Long expiresIn,
                        Long userId, String name, String role, String preferredLanguage) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.preferredLanguage = preferredLanguage;
    }
}
