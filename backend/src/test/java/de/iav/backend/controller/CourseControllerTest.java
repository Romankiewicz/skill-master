package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Course;
import de.iav.backend.model.Teacher;
import de.iav.backend.repository.CourseRepository;
import de.iav.backend.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DirtiesContext
    void getAllCourses_whenNoCoursesExists_thenReturnEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getAllCourses_whenCoursesIsNotEmpty_thenReturnListWithElementsAsJson() throws Exception {
        Teacher currentTeacher = new Teacher("1234", "user", "Dirk", "Stadge", "123@gmx.de", new ArrayList<>());
        teacherRepository.save(currentTeacher);
        //1. Teacher Post

        mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currentTeacher)))
                .andExpect(status().isCreated());

        //2. Post addCourseToCourseListOfTeacher
        Course courseOne = new Course("12356", "Mathe", "1235", new ArrayList<>(), currentTeacher);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers/1234/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseOne)))
                .andExpect(status().isAccepted());

        System.out.println(courseRepository.findById("12356"));

        List<Course> expectedList = new ArrayList<>();
        expectedList.add(courseOne);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teacher.firstName").value("Dirk"));
    }

    @Test
    @DirtiesContext
    void getCourseByCourseName() throws Exception {

    }

    @Test
    void getCourseById() {
    }

    @Test
    void deleteCourse() {
    }
}