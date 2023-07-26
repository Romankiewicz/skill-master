package de.iav.backend.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/me")
    public String getMe(Principal principal){
        if (principal != null) {
            return principal.getName();
        }
        return "anonymousUser";
    }

    @PostMapping("/login")
    public String login(Principal principal){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/register/student")
    public AppUserResponse registerStudent(@RequestBody AppUserRequest appUserRequest){
        return appUserService.createUser(appUserRequest, AppUserRole.STUDENT);
    }

    @PostMapping("/register/teacher")
    public AppUserResponse registerTeacher(@RequestBody AppUserRequest appUserRequest){
        return appUserService.createUser(appUserRequest, AppUserRole.TEACHER);
    }

    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        SecurityContextHolder.clearContext();
        return "anonymousUser";
    }
}
