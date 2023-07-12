package de.iav.backend.service;


import de.iav.backend.model.Course;
import de.iav.backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseByCourseName(String courseName){
        return courseRepository.findCourseByCourseNameEquals(courseName);
    }

    public Course getCourseById(String courseId){
        return courseRepository
                .findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course with ID:\n"
                + courseId
                + "\ndon´t exist."));
    }

    public void deleteCourse(String courseId){
        courseRepository.deleteById(courseId);
    }
}
