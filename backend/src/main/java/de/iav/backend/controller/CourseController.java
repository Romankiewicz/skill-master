package de.iav.backend.controller;

import de.iav.backend.model.Course;
import de.iav.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/search")
    public Course getCourseByCourseName(@RequestParam("courseName") String courseName){
        return courseService.getCourseByCourseName(courseName);
    }

    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable String courseId){
        return courseService.getCourseById(courseId);
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable String courseId){
        courseService.deleteCourse(courseId);
    }
}
