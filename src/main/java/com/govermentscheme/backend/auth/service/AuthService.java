package com.govermentscheme.backend.auth.service;


import com.govermentscheme.backend.auth.dto.AuthResponse;
import com.govermentscheme.backend.auth.entity.User;
import com.govermentscheme.backend.auth.repository.UserRepository;
import com.govermentscheme.backend.auth.dto.*;
import com.govermentscheme.backend.auth.entity.Role;
import com.govermentscheme.backend.common.exception.*;
import com.govermentscheme.backend.common.exception.ApiException;
import com.govermentscheme.backend.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new ApiException(ErrorCode.PHONE_ALREADY_EXISTS);
        }

        User user = User.builder()
                .name(request.getName().trim())
                .email(request.getEmail().trim().toLowerCase())
                .phone(request.getPhone().trim())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.FARMER)
                .preferredLanguage(request.getPreferredLanguage())
                .isActive(true)
                .build();

        User saved = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(saved);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpirySeconds())
                .userId(saved.getId())
                .name(saved.getName())
                .role(saved.getRole().name())
                .preferredLanguage(saved.getPreferredLanguage())
                .build();
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String input = request.getEmailOrPhone().trim();
        User user = userRepository
                .findByEmailOrPhone(input.toLowerCase(), input)
                .orElseThrow(() -> new ApiException(ErrorCode.INVALID_CREDENTIALS));

        if (!user.getIsActive()) {
            throw new ApiException(ErrorCode.ACCOUNT_DISABLED);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtService.generateAccessToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpirySeconds())
                .userId(user.getId())
                .name(user.getName())
                .role(user.getRole().name())
                .preferredLanguage(user.getPreferredLanguage())
                .build();
    }
}