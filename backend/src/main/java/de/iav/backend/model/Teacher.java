package de.iav.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;


@Document(collection = "teachers")
public record Teacher(
        @MongoId
        String teacherId,
        String loginName,
        String firstName,
        String lastName,
        String email,
        @DBRef
        @JsonIgnoreProperties("teacher")
        List<Course> courses
) {

}
