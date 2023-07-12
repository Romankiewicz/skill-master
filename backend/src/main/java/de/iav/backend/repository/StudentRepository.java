package de.iav.backend.repository;

import de.iav.backend.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    public Student findStudentByStudentIdAndLoginName(String studentId, String loginName);

}
