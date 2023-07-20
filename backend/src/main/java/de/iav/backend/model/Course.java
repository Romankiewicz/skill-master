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
        String content,
        @DBRef(db = "students")
        List<Student> students,
        //@DBRef(db = "teachers")
        Teacher teacher
) {

}
