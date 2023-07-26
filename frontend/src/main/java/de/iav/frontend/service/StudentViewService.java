package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Course;
import de.iav.frontend.model.Student;
import de.iav.frontend.security.AuthenticationService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class StudentViewService {

    private static StudentViewService instance;
    private final HttpClient studentClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String STUDENTS_URL_BACKEND = "http://localhost:8080";

    public StudentViewService() {
    }

    public static synchronized StudentViewService getInstance(){
        if(instance == null){
            instance = new StudentViewService();
        }
        return instance;
    }

    public Student getLoginStudent() {

        String loginName = AuthenticationService.getInstance().getUsername();

        System.out.println("StudentViewService.getLoginStudent()÷::::::" + loginName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STUDENTS_URL_BACKEND + "/api/students/search?loginName=" + loginName))
                .header("Accept", "application/json")
                .header("Cookie", "JSESSIONID=" + AuthenticationService.getInstance().getSessionId())
                .build();

        Student result = studentClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(responseBody -> mapToStudent(responseBody))
                .join();

        return result;
    }

    public List<Course> getAllCourses() {

        String loginName = AuthenticationService.getInstance().getUsername();

        System.out.println("StudentViewService.getAllCourses()÷::::::" + loginName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STUDENTS_URL_BACKEND + "/api/courses"))
                .header("Accept", "application/json")
                .header("Cookie", "JSESSIONID=" + AuthenticationService.getInstance().getSessionId())
                .build();

        List<Course> result = studentClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(responseBody -> mapToCourseList(responseBody))
                .join();

        return result;
    }

    private Student mapToStudent(String responseBody) {

        System.out.println(responseBody);

        try {
            return objectMapper.readValue(responseBody, Student.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Course> mapToCourseList(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
