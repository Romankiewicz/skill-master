package de.iav.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;


@Document(collection = "teachers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Teacher{

        @MongoId
        String teacherId;

        String loginName;

        String firstName;

        String lastName;

        String email;

        @DBRef
        @JsonIgnoreProperties("teachers")
        List<Course> courseList;
}
