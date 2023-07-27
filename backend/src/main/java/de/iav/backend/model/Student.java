package de.iav.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;


@Document(collection = "students")
public record Student(
        @MongoId
        String studentId,
        String loginName,
        String firstName,
        String lastName,
        String email,
        @DBRef(lazy = true)
        @JsonIgnoreProperties("students")
        List<Course> courses

) {

}
