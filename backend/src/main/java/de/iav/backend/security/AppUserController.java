package de.iav.backend.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/*
@RestController
@RequestMapping("/api/users")
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
    public String login(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/register")
    public AppUserResponse register(@RequestBody NewAppUser newAppUser){
        return appUserService.register(newAppUser);
    }

    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        SecurityContextHolder.clearContext();
        return "anonymousUser";
    }
}*/
