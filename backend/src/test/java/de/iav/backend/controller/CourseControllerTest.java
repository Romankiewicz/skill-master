package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Course;
import de.iav.backend.model.Teacher;
import de.iav.backend.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
        Teacher currentTeacher = new Teacher(null, "user", "Dirk", "Stadge", "123@gmx.de", new ArrayList<>());

        MvcResult resultTeacher = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currentTeacher)))
                .andExpect(status().isCreated())
                .andReturn();

        Teacher savedTeacher= objectMapper.readValue(resultTeacher.getResponse().getContentAsString(), Teacher.class);

        Course courseOne = new Course(null, "Mathe", "1235", new ArrayList<>(), savedTeacher);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers/" + savedTeacher.teacherId() + "/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseOne)))
                .andExpect(status().isAccepted())
                .andReturn();

        Teacher updatedTeacher = objectMapper.readValue(result.getResponse().getContentAsString(), Teacher.class);
        Course expectedCourse = courseRepository.findById(updatedTeacher.courseList().get(0).courseId()).orElseThrow();

        List<Course> expectedList = new ArrayList<>();
        expectedList.add(expectedCourse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedList)));
    }

    @Test
    @DirtiesContext
    void getCourseByCourseName_whenCourseWithGivenNameExist_thenExpectStatusOkAndReturnCourseAsJson() throws Exception {
        Teacher currentTeacher = new Teacher(null, "user", "Dirk", "Stadge", "123@gmx.de", new ArrayList<>());

        MvcResult resultTeacher = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentTeacher)))
                        .andExpect(status().isCreated())
                        .andReturn();

        Teacher savedTeacher= objectMapper.readValue(resultTeacher.getResponse().getContentAsString(), Teacher.class);
        Course courseOne = new Course(null, "Mathe", "1235", new ArrayList<>(), savedTeacher);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers/" + savedTeacher.teacherId() + "/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseOne)))
                .andExpect(status().isAccepted())
                .andReturn();

        Teacher updatedTeacher = objectMapper.readValue(result.getResponse().getContentAsString(), Teacher.class);
        Course expectedCourse = courseRepository.findById(updatedTeacher.courseList().get(0).courseId()).orElseThrow();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/search?courseName=Mathe"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCourse)));

    }

    @Test
    @DirtiesContext
    void getCourseById_whenCourseWithGivenIdExist_thenExpectStatusOkAndReturnCourseAsJson() throws Exception {
        Teacher currentTeacher = new Teacher(null, "user", "Dirk", "Stadge", "123@gmx.de", new ArrayList<>());

        MvcResult resultTeacher = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentTeacher)))
                .andExpect(status().isCreated())
                .andReturn();

        Teacher savedTeacher= objectMapper.readValue(resultTeacher.getResponse().getContentAsString(), Teacher.class);
        Course courseOne = new Course(null, "Mathe", "1235", new ArrayList<>(), savedTeacher);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers/" + savedTeacher.teacherId() + "/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseOne)))
                .andExpect(status().isAccepted())
                .andReturn();

        Teacher updatedTeacher = objectMapper.readValue(result.getResponse().getContentAsString(), Teacher.class);
        Course expectedCourse = courseRepository.findCourseByCourseNameEquals(updatedTeacher.courseList().get(0).courseName());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/"+ expectedCourse.courseId() ))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCourse)));
    }
    @Test
    @DirtiesContext
    void deleteCourse_whenCourseWithIdExists_thenDeleteCourseAndReturnNothing()throws Exception {
        Teacher currentTeacher = new Teacher(null, "user", "Dirk", "Stadge", "123@gmx.de", new ArrayList<>());

        MvcResult resultTeacher = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentTeacher)))
                .andExpect(status().isCreated())
                .andReturn();

        Teacher savedTeacher = objectMapper.readValue(resultTeacher.getResponse().getContentAsString(), Teacher.class);
        Course courseOne = new Course(null, "Mathe", "1235", new ArrayList<>(), savedTeacher);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers/" + savedTeacher.teacherId() + "/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseOne)))
                .andExpect(status().isAccepted())
                .andReturn();

        Teacher updatedTeacher = objectMapper.readValue(result.getResponse().getContentAsString(), Teacher.class);
        Course expectedCourse = courseRepository.findCourseByCourseNameEquals(updatedTeacher.courseList().get(0).courseName());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/courses/" + expectedCourse.courseId()))
                .andExpect(status().isOk());

    }
}