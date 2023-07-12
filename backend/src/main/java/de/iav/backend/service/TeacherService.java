package de.iav.backend.service;

import de.iav.backend.model.Course;
import de.iav.backend.model.CourseDTO_RequestBody;
import de.iav.backend.model.Teacher;
import de.iav.backend.model.TeacherDTO_RequestBody;
import de.iav.backend.repository.CourseRepository;
import de.iav.backend.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class TeacherService {
    
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    
    public List<Teacher> listAllTeachers(){
        return teacherRepository.findAll();
    }
    
    public Teacher findById(String teacherId){
        return teacherRepository.findById(teacherId)
                                .orElseThrow(() -> new NoSuchElementException("Teacher with ID:\n"
                                                                                      + teacherId
                                                                                      + "\ndon´t exist."));
    }
    
    public Teacher findByLoginNameAndTeacherId(String loginName, String teacherId){
        return teacherRepository.findByLoginNameAndTeacherId(loginName, teacherId);
    }
    
    public Teacher addTeacher (TeacherDTO_RequestBody teacher){
        return teacherRepository.save(
                new Teacher(null,
                        teacher.loginName(),
                        teacher.firstName(),
                        teacher.lastName(),
                        teacher.email(),
                        new ArrayList<>()
                )
        );
    }

    public Teacher updateTeacherById(String teacherId, Teacher updateTeacher){
        return teacherRepository.save(new Teacher(teacherId,
                updateTeacher.loginName(),
                updateTeacher.firstName(),
                updateTeacher.lastName(),
                updateTeacher.email(),
                updateTeacher.courseList()));
    }

    /*public Course addCourseToCourseListOfTeacher(String teacherId, CourseDTO_RequestBody courseToAdd){
        Course addCourse = courseRepository.save(new Course(null,
                courseToAdd.courseName(),
                courseToAdd.content(),
                teacherId, new ArrayList<>()));
    }*/
    
}
