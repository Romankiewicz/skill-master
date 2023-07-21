package de.iav.backend.controller;

import de.iav.backend.model.CourseDTO_RequestBody;
import de.iav.backend.model.Teacher;
import de.iav.backend.model.TeacherDTO_RequestBody;
import de.iav.backend.service.TeacherService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public List<Teacher> getAllTeachers(){
        return teacherService.listAllTeachers();
    }

    @GetMapping("/search")
    public Teacher getTeacherByIdAndLoginName(@RequestParam(name = "teacherId", required = false) String teacherId, @RequestParam(name = "loginName", required = false) String loginName){
        return teacherService.findByLoginNameAndTeacherId(loginName, teacherId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher addTeacher(@RequestBody TeacherDTO_RequestBody teacher) {
        return teacherService.addTeacher(teacher);
    }

    @PutMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Teacher updateTeacher(@PathVariable String teacherId, @RequestBody Teacher updateTeacher){
        return teacherService.updateTeacherById(teacherId, updateTeacher);
    }

    @PostMapping("/{teacherId}/course")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Teacher addCourseToCourseListOfTeacher(@PathVariable String teacherId, @RequestBody CourseDTO_RequestBody courseToAdd){
        return teacherService.addCourseToCourseListOfTeacher(teacherId, courseToAdd);
    }

    @DeleteMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacherById(@PathVariable String teacherId){
        teacherService.deleteTeacherById(teacherId);
    }

    @DeleteMapping("/{teacherId}/course/{courseId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Teacher deleteCourseFromCourseListOfTeacher(@PathVariable("teacherId") String teacherId, @PathVariable("courseId") String courseId){
        return teacherService.deleteCourseFromCourseListOfTeacher(teacherId,courseId);
    }

}
