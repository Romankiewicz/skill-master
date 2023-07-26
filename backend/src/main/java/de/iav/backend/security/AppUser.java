package de.iav.backend.security;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public record AppUser(
        String loginName,
        String password,
        @Indexed(unique = true)
        String email,
        AppUserRole role
) {
}
