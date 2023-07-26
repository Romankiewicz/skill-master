package de.iav.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    // "Username" == How will the app being authenticated (loginName/email)
    // parameter always "username"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByLoginName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(user.loginName())
                .password(user.password())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.role().name())))
                .build();
    }

    public AppUserResponse createUser(AppUserRequest appUserRequest){

        AppUser userToSave = new AppUser(
                null,
                appUserRequest.loginName(),
                passwordEncoder.encode(appUserRequest.password()),
                appUserRequest.email(),
                appUserRequest.role()
        );

        AppUser savedUser = appUserRepository.save(userToSave);

        return new AppUserResponse(userToSave.loginName(), userToSave.email(), userToSave.role());

    }
}
