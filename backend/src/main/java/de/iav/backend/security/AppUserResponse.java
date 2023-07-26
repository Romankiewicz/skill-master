package de.iav.backend.security;

public record AppUserResponse(
        String loginName,
        String email,
        AppUserRole role
) {
}
