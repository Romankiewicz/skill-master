package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "students")
public record Student(
        String loginName,
        String firstName,
        String lastName,
        String email,
        @DBRef
        List<Course> courseList

) {

}
