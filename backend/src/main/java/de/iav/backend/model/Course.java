package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;


@Document(collection = "courses")
public record Course(
        @MongoId
        String courseId,
        String courseName,
        @DBRef
        List<Student> students,
        @DBRef
        List<Teacher> teachers
) {

}
