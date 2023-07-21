package de.iav.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
        @DBRef
        @JsonIgnoreProperties("courses")
        List<Student> students,
        Teacher teacher
) {

}
