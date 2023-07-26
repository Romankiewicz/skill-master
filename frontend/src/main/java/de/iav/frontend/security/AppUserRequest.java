package de.iav.frontend.security;

public record AppUserRequest(
        String loginName,
        String password,
        String email
) {
}
