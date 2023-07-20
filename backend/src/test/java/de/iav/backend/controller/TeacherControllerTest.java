package de.iav.backend.controller;

import de.iav.backend.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeacherControllerTest {


    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllTeachers_whenNoTeacherExists_thenRenternEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}