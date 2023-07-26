package de.iav.frontend.security;

public record AppUserRequest(
        String username,
        String password,
        String email
) {
}
