package de.iav.backend.repository;

import de.iav.backend.model.Course;
import de.iav.backend.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    Course findCourseByCourseNameEquals(String courseName);
    List<Course> findAll();

    List<Course> findCoursesByTeacher(Teacher t);
}
