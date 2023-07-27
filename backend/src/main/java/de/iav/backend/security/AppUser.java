package de.iav.backend.security;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "users")
public record AppUser(
        String id,
        @Indexed(unique = true, name = "_login_name_")
        String loginName,
        String password,
        @Indexed(unique = true, name =  "_email_")
        String email,
        AppUserRole role
) {
}
