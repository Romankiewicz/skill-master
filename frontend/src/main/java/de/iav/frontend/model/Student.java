package de.iav.frontend.model;


import java.util.List;

public record Student(
        String studentId,
        String loginName,
        String firstName,
        String lastName,
        String email,
        List<Course> courseList

) {

}
