package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Student;
import de.iav.backend.repository.StudentRepository;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllStudents_whenStudendsIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getAllStudents_whenStudentExist() throws Exception {

        //        1. Objekt vom Typ Student erzeugen:
        Student currentStudent = new Student("1", "MasterChief",
                "John",
                "117",
                "master.chief@gmail.com",
                new ArrayList<>());

//        2. Student an "Backend" senden und den Response speichern:
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentStudent)))
                .andExpect(status().isCreated()).andReturn();

//        3. Liste von Studenten erzeugen und unseren DummieStudenten aus der response darin speichern:
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(objectMapper.readValue(response.getResponse().getContentAsString(), Student.class));

//        4. Get Anfrage senden und mit der Liste abgleichen:
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(expectedStudents)));
    }

    @Test
//    @DirtiesContext
    void getStudentByIdAndLoginName_whenStudentExists() throws Exception {
        Student currentStudent = new Student("1", "MasterChief",
                "John",
                "117",
                "master.chief@gmail.com",
                new ArrayList<>());

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentStudent)))
                .andExpect(status().isCreated()).andReturn();

        Student expectedStudent= (objectMapper.readValue(response.getResponse().getContentAsString(), Student.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/students/search?studentId="
                        + expectedStudent.studentId()
                        + "&loginName=MasterChief"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(expectedStudent)));

    }

    @Test
    void addStudent() {
    }

    @Test
    void updateStudentById() {
    }

    @Test
    void addCourseToCourseListOfStudent() {
    }

    @Test
    void deleteStudentById() {
    }

    @Test
    void deleteCourseFromCourseListOfStudent() {
    }
}