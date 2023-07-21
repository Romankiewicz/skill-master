package de.iav.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;


@Document(collection = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course{
        @MongoId
        String courseId;

        String courseName;

        String content;

        @DBRef(db = "students")
        List<Student> students;

        Teacher teacher;
}
