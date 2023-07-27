package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Student;
import de.iav.frontend.model.StudentDTO_RequestBody;
import de.iav.frontend.model.Teacher;
import de.iav.frontend.model.TeacherDTO_RequestBody;
import de.iav.frontend.security.AuthenticationService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegistrationViewService {
    private static RegistrationViewService instance;
    private final HttpClient registrationClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String STUDENTS_URL_BACKEND = System.getenv("BACKEND_STUDENT_URI");

    public RegistrationViewService() {
    }

    public static synchronized RegistrationViewService getInstance(){
        if(instance == null){
            instance = new RegistrationViewService();
        }
        return instance;
    }

    public Teacher addTeacher(TeacherDTO_RequestBody newTeacher, String sessionId){
        try {
            String requestBody = objectMapper.writeValueAsString(newTeacher);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STUDENTS_URL_BACKEND + "/api/teachers"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Cookie", "JSESSIONID=" + sessionId)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return registrationClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(responseBody -> mapToTeacher(responseBody))
                    .join();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Student addStudent(StudentDTO_RequestBody newStudent, String sessionId){
        try {
            String requestBody = objectMapper.writeValueAsString(newStudent);



            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STUDENTS_URL_BACKEND + "/api/students"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Cookie", "JSESSIONID=" + sessionId)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return registrationClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(responseBody -> mapToStudent(responseBody))
                    .join();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Student mapToStudent(String responseBody) {
        System.out.println(responseBody);
        try {
            return objectMapper.readValue(responseBody, Student.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Teacher mapToTeacher(String responseBody) {
        System.out.println(responseBody);
        try {
            return objectMapper.readValue(responseBody, Teacher.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

