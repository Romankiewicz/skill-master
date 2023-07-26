package de.iav.backend.security;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "users")
public record AppUser(
        String id,

        // TODO: Make this unique
        String loginName,
        String password,
        @Indexed(unique = true)
        String email,
        AppUserRole role
) {
}
