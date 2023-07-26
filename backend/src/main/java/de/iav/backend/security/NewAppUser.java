package de.iav.backend.security;

public record NewAppUser(
        String username,
        String password,
        String email
) {
}
