package com.govermentscheme.backend.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS("AUTH_001", "Email already registered"),
    PHONE_ALREADY_EXISTS("AUTH_002", "Phone number already registered"),
    INVALID_CREDENTIALS("AUTH_003", "Invalid email/phone or password"),
    ACCOUNT_DISABLED("AUTH_004", "Account is disabled"),
    UNAUTHORIZED("AUTH_005", "Authentication required"),
    FORBIDDEN("AUTH_006", "Access denied"),
    VALIDATION_ERROR("COMMON_001", "Validation failed");

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
