package de.iav.backend.model;

import java.util.List;


public record Student(
        String loginName,
        String firstName,
        String lastName,
        String email,
        List<Course> courseList
        
) {

}
