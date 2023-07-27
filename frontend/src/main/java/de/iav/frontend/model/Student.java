package de.iav.frontend.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public record Student(
        String studentId,
        String loginName,
        String firstName,
        String lastName,
        String email,
        List<Course> courses

) {

}
