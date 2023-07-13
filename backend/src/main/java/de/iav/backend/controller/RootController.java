package de.iav.backend.controller;

import de.iav.backend.model.Teacher;
import de.iav.backend.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RootController {

    private final TeacherService teacherService;

    @GetMapping
    public String getWelccome() {
        return "Welcome";
    }

}
