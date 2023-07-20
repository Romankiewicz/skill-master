package de.iav.backend.controller;

import de.iav.backend.model.Student;
import de.iav.backend.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllStudents_whenStudendsIsEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getAllStudents_whenStudentExist() throws Exception {
        studentRepository.save(new Student("1", "MasterChief",
                "John",
                "117",
                "master.chief@gmail.com",
                new ArrayList<>()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                "studentId": "1",
                                "firstName": "John",
                                "lastName": "117",
                                "email": ""
                                
                                """
                ));
    }

//    @Test
//    void getStudentByIdAndLoginName() {
//        studentRepository.save(new Student("1", "MasterChief",
//                "John",
//                "117",
//                "master.chief@gmail.com",
//                new ArrayList<>()));
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/students/search"))
//                .andExpect(status().isOk());
//    }

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