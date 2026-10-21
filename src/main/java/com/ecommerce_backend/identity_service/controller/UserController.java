package com.ecommerce_backend.identity_service.controller;

import com.ecommerce_backend.identity_service.dto.response.UserNotificationInfoResponse;
import com.ecommerce_backend.identity_service.dto.response.UserResponse;
import com.ecommerce_backend.identity_service.entity.User;
import com.ecommerce_backend.identity_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return ResponseEntity.ok(
                new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }

    @GetMapping("internal/{userId}/notification-info")
    public ResponseEntity<UserNotificationInfoResponse> findNotificationInfo(
            @PathVariable Long userId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Usuario no encontrado")
                );

        return ResponseEntity.ok(
                new UserNotificationInfoResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getEmail()
                )
        );
    }
}
