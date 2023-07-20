package de.iav.backend.repository;

import de.iav.backend.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends MongoRepository< Teacher, String> {
    
    Teacher findByLoginNameAndTeacherId(String loginName, String teacherId);

    Optional<Teacher> findById(String s);

    List<Teacher> findAll();


}
