package de.iav.frontend.model;

import java.util.List;


public record Teacher(

        String teacherId,
        String loginName,
        String firstName,
        String lastName,
        String email,
        List<Course> courses
) {

}
