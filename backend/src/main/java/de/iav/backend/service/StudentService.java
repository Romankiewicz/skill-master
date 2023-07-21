package de.iav.backend.service;

import de.iav.backend.model.Course;
import de.iav.backend.model.Student;
import de.iav.backend.model.StudentDTO_RequestBody;
import de.iav.backend.repository.CourseRepository;
import de.iav.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByIdAndLoginName(String studentId, String loginName) {
        return studentRepository.findStudentByStudentIdAndLoginName(studentId, loginName);
    }

    public Student addStudent(StudentDTO_RequestBody studentToAdd) {
        return studentRepository.save(
                new Student(null,
                        studentToAdd.loginName(),
                        studentToAdd.firstName(),
                        studentToAdd.lastName(),
                        studentToAdd.email(),
                        new ArrayList<>()
                )
        );
    }

    public Student updateStudentById(String studentId, Student updateStudent) {
        return studentRepository.save(
                new Student(
                        studentId,
                        updateStudent.loginName(),
                        updateStudent.firstName(),
                        updateStudent.lastName(),
                        updateStudent.email(),
                        updateStudent.courses()
                )
        );
    }

    public void addCourseToCourseListOfStudent(String studentId, String courseId) {
        Student studentToModify = studentRepository
                .findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student with ID:\n"
                        + studentId
                        + "\ndon´t exist."));
        Course courseToAdd = courseRepository
                .findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course with ID:\n"
                        + courseId
                        + "\ndon´t exist."));
        courseToAdd.students().add(studentToModify);
        courseRepository.save(courseToAdd);
        studentToModify.courses().add(courseToAdd);
        studentRepository.save(studentToModify);
    }

    public void deleteStudentById(String studentId) {
        studentRepository.deleteById(studentId);
    }

    public void deleteCourseFromCourseListOfStudent(String studentId, String courseId) {
        Student studentToModify = studentRepository
                .findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student with ID:\n"
                        + studentId
                        + "\ndon´t exist."));
        Course courseToDelete = courseRepository
                .findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course with ID:\n"
                        + courseId
                        + "\ndon´t exist."));
        studentToModify.courses().remove(courseToDelete);
    }

}
