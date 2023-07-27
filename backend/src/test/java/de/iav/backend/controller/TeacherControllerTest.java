package de.iav.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Course;
import de.iav.backend.model.Teacher;
import de.iav.backend.model.TeacherDTO_RequestBody;
import de.iav.backend.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
    @WithMockUser
    void getAllTeachers_whenNoTeacherExistsLoggedIn_thenRenternEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getAllTeachers_whenTeacherWithoutCourseExistsLoggedIn_thenRenternListWithTheTeacher() throws Exception {

        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());

        teacherRepository.save(teacherToAdd);

        /*
        mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherToAdd)))
                .andExpect(status().isCreated());*/


        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teacherId").value("1"))
                .andExpect(jsonPath("$[0].loginName").value("FordProbe"))
                .andExpect(jsonPath("$[0].firstName").value("Dirk"))
                .andExpect(jsonPath("$[0].lastName").value("Stadge"))
                .andExpect(jsonPath("$[0].email").value("dirk@gmx.de"))
                .andExpect(jsonPath("$[0].courses.length()").value(0));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getAllTeachers_whenTeacherWithCourseExistsLoggedIn_thenRenternListWithTheTeacher() throws Exception {

        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());

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
                .andExpect(jsonPath("$[0].courses.length()").value(1))
                .andExpect(jsonPath("$[0].courses[0].courseId").exists())
                .andExpect(jsonPath("$[0].courses[0].courseName").value("Mathe"))
                .andExpect(jsonPath("$[0].courses[0].students.length()").value(0))
                .andExpect(jsonPath("$[0].courses[0].teacher").doesNotExist());
    }


    @Test
    @DirtiesContext
    @WithMockUser
    void getTeacherByIdAndLoginName_whenTeacherAndIdGivenLoggedIn_thenReturnThatTeacher() throws Exception {
        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());


        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherToAdd)))
            .andExpect(status().isCreated())
            .andReturn();

        Teacher expectedTeacher = objectMapper.readValue(response.getResponse().getContentAsString(), Teacher.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers/search?teacherId="
                +expectedTeacher.teacherId()
                + "&loginName="+expectedTeacher.loginName()))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$[0].teacherId").value("1"))
                .andExpect(jsonPath("$.loginName").value("FordProbe"))
                .andExpect(jsonPath("$.firstName").value("Dirk"))
                .andExpect(jsonPath("$.lastName").value("Stadge"))
                .andExpect(jsonPath("$.email").value("dirk@gmx.de"))
                .andExpect(jsonPath("$.courses.length()").value(0));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void addTeacher_whenTeacherAddedLoggedIn_thenTeacherCouldBeFoundByGetRequest() throws Exception {

        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherToAdd)))
                .andExpect(status().isCreated());


        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teacherId").exists())
                .andExpect(jsonPath("$[0].loginName").value("FordProbe"))
                .andExpect(jsonPath("$[0].firstName").value("Dirk"))
                .andExpect(jsonPath("$[0].lastName").value("Stadge"))
                .andExpect(jsonPath("$[0].email").value("dirk@gmx.de"))
                .andExpect(jsonPath("$[0].courses.length()").value(0));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void updateTeacher_whenTeacherUpdatedLoggedIn_thenReturnTheUpdatedTeacherOnGetRequest() throws Exception {

        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());


        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherToAdd)))
                .andExpect(status().isCreated())
                .andReturn();

        Teacher expectedTeacher = objectMapper.readValue(response.getResponse().getContentAsString(), Teacher.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherToAdd)))
                .andExpect(status().isCreated());

        Teacher teacherToChange = new Teacher("???", "FordProbee",
                "Dirke", "Stadgee",
                "dirke@gmx.de", new ArrayList<Course>());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/teachers/" + expectedTeacher.teacherId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherToChange)))
                .andExpect(status().isAccepted());


        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teacherId").exists())
                .andExpect(jsonPath("$[0].loginName").value("FordProbee"))
                .andExpect(jsonPath("$[0].firstName").value("Dirke"))
                .andExpect(jsonPath("$[0].lastName").value("Stadgee"))
                .andExpect(jsonPath("$[0].email").value("dirke@gmx.de"))
                .andExpect(jsonPath("$[0].courses.length()").value(0));

    }

    @Test
    @DirtiesContext
    @WithMockUser
    void addCourseToCourseListOfTeacher_whenCourseAddedLoggedIn_thenReturnTheTeacherWithAssignedCourse() throws Exception {
        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());

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
                .andExpect(jsonPath("$[0].courses.length()").value(1))
                .andExpect(jsonPath("$[0].courses[0].courseId").exists())
                .andExpect(jsonPath("$[0].courses[0].courseName").value("Mathe"))
                .andExpect(jsonPath("$[0].courses[0].students.length()").value(0))
                .andExpect(jsonPath("$[0].courses[0].teacher").doesNotExist());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteTeacherById_whenListWithOneTeacherDeletedLoggedIn_thenReturnEmptyList() throws Exception {

        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());

        teacherRepository.save(teacherToAdd);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/teachers/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));



    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteTeacherById_whenListWithMultipleTeacherDeletedLoggedIn_thenReturnListWithoutDeletedTeacher() throws Exception {

        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());

        Teacher teacherToAdd_2 = new Teacher("2", "FordProbee",
                "Dirke", "Stadgee",
                "dirke@gmx.de", new ArrayList<Course>());

        teacherRepository.save(teacherToAdd);
        teacherRepository.save(teacherToAdd_2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/teachers/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].loginName").value("FordProbee"))
                .andExpect(jsonPath("$[0].firstName").value("Dirke"))
                .andExpect(jsonPath("$[0].lastName").value("Stadgee"))
                .andExpect(jsonPath("$[0].email").value("dirke@gmx.de"));

    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteCourseFromCourseListOfTeacher_whenTeacherWithOneCourseDeletedLoggedIn_thenReturnTeacherWithoutCourse() throws Exception {
        Teacher teacherToAdd = new Teacher("1", "FordProbe",
                "Dirk", "Stadge",
                "dirk@gmx.de", new ArrayList<Course>());

        teacherRepository.save(teacherToAdd);

        Course courseOne = new Course("1", "Mathe", "1235", new ArrayList<>(), teacherToAdd);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers/1/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseOne)))
                .andExpect(status().isAccepted()).andReturn();

        Teacher expectedTeacher = objectMapper.readValue(response.getResponse().getContentAsString(), Teacher.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].loginName").value("FordProbe"))
                .andExpect(jsonPath("$[0].courses.length()").value(1));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/teachers/1/course/"+expectedTeacher.courses().get(0).courseId()))
                .andExpect(status().isAccepted());


        mockMvc.perform(MockMvcRequestBuilders.get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].loginName").value("FordProbe"))
                .andExpect(jsonPath("$[0].courses.length()").value(0));
    }
}