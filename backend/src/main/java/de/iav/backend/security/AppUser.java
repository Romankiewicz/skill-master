package de.iav.backend.security;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appUsers")
public record AppUser(
        String id,
        String username,
        String password,
        @Indexed(unique = true)
        String email,
        String role
) {
}
