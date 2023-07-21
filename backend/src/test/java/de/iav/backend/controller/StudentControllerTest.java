package de.iav.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Course;
import de.iav.backend.model.Student;
import de.iav.backend.model.Teacher;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @DirtiesContext
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
    @DirtiesContext
    void addStudent() throws Exception {

        Student currentStudent = new Student("1", "MasterChief",
                "John",
                "117",
                "master.chief@gmail.com",
                new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentStudent)))
                .andExpect(status().isCreated());


    }

    @Test
    void updateStudentById() throws Exception {

        Student currentStudent = new Student("1",
                "MasterChief",
                "John",
                "117",
                "master.chief@gmail.com",
                new ArrayList<>());

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentStudent)))
                .andExpect(status().isCreated()).andReturn();

        Student responseStudent = (objectMapper.readValue(response.getResponse().getContentAsString(), Student.class));

        Student updateStudent = new Student(responseStudent.studentId(),
                responseStudent.loginName(),
                responseStudent.firstName(),
                responseStudent.lastName(),
                "master.chief.waffentester@gmail.com",
                responseStudent.courses());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/students/" + responseStudent.studentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateStudent)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(objectMapper.writeValueAsString(updateStudent)));
    }

    @Test
    void addCourseToCourseListOfStudent()  throws Exception {

        Student currentStudent = new Student("1",
                "MasterChief",
                "John",
                "117",
                "master.chief@gmail.com",
                new ArrayList<>());

        Teacher currentTeacher = new Teacher("1",
                "SeriousSam",
                "Samuel",
                "Stone",
                "serios.sam@gmail.com",
                new ArrayList<>());


        Course currentCourse = new Course("1",
                "DynamiteforDummies",
                "Explosives and other stuff",
                new ArrayList<>(),
                new Teacher("1",
                        "SeriousSam",
                        "Samuel",
                        "Stone",
                        "serios.sam@gmail.com",
                        new ArrayList<>()));

        MvcResult student = mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentStudent)))
                .andExpect(status().isCreated()).andReturn();

        Student responseStudent = (objectMapper.readValue(student.getResponse().getContentAsString(), Student.class));

        MvcResult teacher = mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentTeacher)))
                .andExpect(status().isCreated()).andReturn();

        Teacher responseTeacher = (objectMapper.readValue(teacher.getResponse().getContentAsString(), Teacher.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/teachers/"
                + responseTeacher.teacherId() + "/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(currentCourse)))
                .andExpect(status().isAccepted());

        MvcResult course = mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/search?courseName=DynamiteforDummies"))
                .andExpect(status().isOk()).andReturn();

        Course responseCourse = objectMapper.readValue(course.getResponse().getContentAsString(), Course.class);
        List<Course> responseCourseList = new ArrayList<>();
        responseCourseList.add(responseCourse);

        Student updateStudent = new Student(responseStudent.studentId(),
                responseStudent.loginName(),
                responseStudent.firstName(),
                responseStudent.lastName(),
                responseStudent.email(),
                responseCourseList);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/students/"
                                + responseStudent.studentId()
                                + "/course/"
                                + responseCourse.courseId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateStudent)))
                .andExpect(status().isAccepted());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudentById() throws Exception{

        Student currentStudent = new Student("1",
                "MasterChief",
                "John",
                "117",
                "master.chief@gmail.com",
                new ArrayList<>());

        MvcResult student = mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentStudent)))
                .andExpect(status().isCreated()).andReturn();

        Student responseStudent = (objectMapper.readValue(student.getResponse().getContentAsString(), Student.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/"
                                + responseStudent.studentId()))
                .andExpect(status().isNoContent());


    }


    @Test
    void deleteCourseFromCourseListOfStudent() {
    }
}