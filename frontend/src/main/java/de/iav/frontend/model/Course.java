package de.iav.frontend.model;

import java.util.List;



public record Course(

        String courseId,
        String courseName,
        String content,
        List<Student> students,
        Teacher teacher
) {

}
