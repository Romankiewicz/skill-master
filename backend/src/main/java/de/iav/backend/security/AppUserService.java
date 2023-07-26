package de.iav.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(
                appUser.username(),
                appUser.password(),
                List.of(new SimpleGrantedAuthority("ROLE_" + appUser.role()))
        );
    }

    public AppUserResponse register(NewAppUser newAppUser){

        Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

        AppUser appUser = new AppUser(
                null,
                newAppUser.username(),
                passwordEncoder.encode(newAppUser.password()),
                newAppUser.email(),
                "USER"
        );
        AppUser savedAppUser = appUserRepository.save(appUser);
        return new AppUserResponse(savedAppUser.id(), savedAppUser.username(), savedAppUser.email(), savedAppUser.role());
    }
}
