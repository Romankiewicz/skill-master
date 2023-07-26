package de.iav.backend.security;

public record AppUserResponse(
        String loginName,
        String username,
        String email,
        AppUserRole role
) {
}