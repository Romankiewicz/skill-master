package de.iav.backend.security;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public record NewAppUser(
        String username,
        String password,
        String email
) {
}
