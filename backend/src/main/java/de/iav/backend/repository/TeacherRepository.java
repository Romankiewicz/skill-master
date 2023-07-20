package de.iav.backend.repository;

import de.iav.backend.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TeacherRepository extends MongoRepository<Teacher, String> {

    Teacher findByLoginNameAndTeacherId(String loginName, String teacherId);

    List<Teacher> findAll();
}
