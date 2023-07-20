package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Course;
import de.iav.backend.model.Teacher;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeacherControllerTest {


    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DirtiesContext
    void getAllTeachers_whenNoTeacherExists_thenRenternEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getAllTeachers_whenTeacherWithoutCourseExists_thenRenternListWithTheTeacher() throws Exception {

        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());

        teacherRepository.save(teacherToAdd);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherToAdd)))
                .andExpect(status().isCreated());


        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teacherId").value("1"))
                .andExpect(jsonPath("$[0].loginName").value("FordProbe"))
                .andExpect(jsonPath("$[0].firstName").value("Dirk"))
                .andExpect(jsonPath("$[0].lastName").value("Stadge"))
                .andExpect(jsonPath("$[0].email").value("dirk@gmx.de"))
                .andExpect(jsonPath("$[0].courseList.length()").value(0));
    }

    @Test
    @DirtiesContext
    void getAllTeachers_whenTeacherWithCourseExists_thenRenternListWithTheTeacher() throws Exception {

        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());

        /*mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherToAdd)))
                .andExpect(status().isCreated());*/

        teacherRepository.save(teacherToAdd);

        Course courseOne = new Course("1", "Mathe", "1235", new ArrayList<>(), teacherToAdd);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers/1/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseOne)))
                .andExpect(status().isAccepted());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$[0].teacherId").value("1"))
                .andExpect(jsonPath("$[0].loginName").value("FordProbe"))
                .andExpect(jsonPath("$[0].firstName").value("Dirk"))
                .andExpect(jsonPath("$[0].lastName").value("Stadge"))
                .andExpect(jsonPath("$[0].email").value("dirk@gmx.de"))
                .andExpect(jsonPath("$[0].courseList.length()").value(1))
                .andExpect(jsonPath("$[0].courseList[0].courseId").value("1235"))
                .andExpect(jsonPath("$[0].courseList[0].courseName").value("Mathe"))
                .andExpect(jsonPath("$[0].courseList[0].students.length()").value(0))
                .andExpect(jsonPath("$[0].courseList[0].teacher").doesNotExist());
    }


}