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
    void getAllCourses_whenCoursesIsNotEmpty_thenReturnListWittElementsAsJson() throws Exception {
        Teacher currentTeacher = new Teacher("1234", "user", "Dirk", "Stadge", "123@gmx.de", new ArrayList<>());
        teacherRepository.save(currentTeacher);

        Course courseOne = new Course("12356", "Mathe", "1235", new ArrayList<>(), currentTeacher);
        Course courseTwo = new Course("123456", "Physik", "abcd", new ArrayList<>(), currentTeacher);

        courseRepository.save(courseOne);
        courseRepository.save(courseTwo);

        List<Course> expectedList = new ArrayList<>();
        expectedList.add(courseOne);
        expectedList.add(courseTwo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedList)));
    }

    @Test
    void getCourseByCourseName() {
    }

    @Test
    void getCourseById() {
    }

    @Test
    void deleteCourse() {
    }
}