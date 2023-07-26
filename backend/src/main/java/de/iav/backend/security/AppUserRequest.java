package de.iav.backend.security;

public record AppUserRequest(
        String loginName,
        String email,
        AppUserRole role
) {
}
