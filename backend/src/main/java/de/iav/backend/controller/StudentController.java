package de.iav.backend.controller;

import de.iav.backend.model.Student;
import de.iav.backend.model.StudentDTO_RequestBody;
import de.iav.backend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping
    public Student getStudentByIdAndLoginName(@RequestParam(name = "studentId", required = false) String studentId, @RequestParam(name = "loginName", required = false)String loginName){
        return studentService.getStudentByIdAndLoginName(studentId, loginName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody StudentDTO_RequestBody studentToAdd){
        return studentService.addStudent(studentToAdd);
    }

    @PutMapping("/{studentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Student updateStudentById(@PathVariable String studentId, @RequestBody Student updateStudent){
        return studentService.updateStudentById(studentId, updateStudent);
    }

    @PutMapping("/{studentId}/course/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCourseToCourseListOfStudent(@PathVariable(name = "studentId") String studentId, @PathVariable(name = "courseId") String courseId){
        studentService.addCourseToCourseListOfStudent(studentId, courseId);
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentById(@PathVariable String studentId){
        studentService.deleteStudentById(studentId);
    }

    @DeleteMapping("/{studentId}/course/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseFromCourseListOfStudent(@PathVariable(name = "studentId") String studentId, @PathVariable(name = "courseId") String courseId){
        studentService.deleteCourseFromCourseListOfStudent(studentId, courseId);
    }
}
