package de.iav.backend.repository;

import de.iav.backend.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
    Course findCourseByCourseNameEquals(String courseName);
}
