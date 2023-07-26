package de.iav.backend.security;

public record AppUserResponse(
        String id,
        String username,
        String email,
        String role
) {
}
