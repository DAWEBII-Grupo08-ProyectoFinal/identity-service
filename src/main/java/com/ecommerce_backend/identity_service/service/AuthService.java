package com.ecommerce_backend.identity_service.service;

import com.ecommerce_backend.identity_service.dto.request.LoginRequest;
import com.ecommerce_backend.identity_service.dto.request.RegisterRequest;
import com.ecommerce_backend.identity_service.dto.response.AuthResponse;
import com.ecommerce_backend.identity_service.entity.Role;
import com.ecommerce_backend.identity_service.entity.User;
import com.ecommerce_backend.identity_service.exception.IdentityException;
import com.ecommerce_backend.identity_service.repository.UserRepository;
import com.ecommerce_backend.identity_service.security.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.email());

        if (userRepository.existsByEmail(email)) {
            throw new IdentityException(
                    HttpStatus.CONFLICT,
                    "El correo ya se encuentra registrado"
            );
        }

        User user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getId(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(normalizeEmail(request.email()))
                .orElseThrow(() -> new IdentityException(
                        HttpStatus.UNAUTHORIZED,
                        "Credenciales incorrectas"
                ));

        if (!user.getEnabled()) {
            throw new IdentityException(HttpStatus.FORBIDDEN, "Usuario deshabilitado");
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IdentityException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales incorrectas"
            );
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getId(), user.getEmail());
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

}
