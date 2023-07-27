package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Course;
import de.iav.frontend.model.Student;
import de.iav.frontend.model.Teacher;
import de.iav.frontend.security.AuthenticationService;
import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TeacherViewService {

    private static TeacherViewService instance;
    private final HttpClient studentClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String STUDENTS_URL_BACKEND = "http://localhost:8080";

    public TeacherViewService() {
    }

    public static synchronized TeacherViewService getInstance(){
        if(instance == null){
            instance = new TeacherViewService();
        }
        return instance;
    }

    public Teacher getLoginTeacher() {

        String loginName = AuthenticationService.getInstance().getUsername();

        System.out.println("StudentViewService.getLoginTeacher()÷::::::" + loginName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STUDENTS_URL_BACKEND + "/api/teachers/search?loginName=" + loginName))
                .header("Accept", "application/json")
                .header("Cookie", "JSESSIONID=" + AuthenticationService.getInstance().getSessionId())
                .build();

        Teacher result = studentClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(responseBody -> mapToTeacher(responseBody))
                .join();

        return result;
    }

    public void deleteCourse(String courseIdToDelete, ListView<Course> coursesOfTeacherListView){

        System.out.println("zu löschender Kurs: " + courseIdToDelete);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STUDENTS_URL_BACKEND + "/api/courses/" + courseIdToDelete))
                .header("Cookie", "JSESSIONID=" + AuthenticationService.getInstance().getSessionId())
                .DELETE()
                .build();

        studentClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 204) {
                        Platform.runLater(() -> {
                            coursesOfTeacherListView
                                    .getItems()
                                    .removeIf(course -> course.courseId().equals(courseIdToDelete));
                            coursesOfTeacherListView.refresh();
                        });
                    }else {
                        System.out.println(response.statusCode());
                        throw new RuntimeException("Löschen des Kurses nicht möglich bitte eingaben überprüfen.");

                    }
                })
                .join();

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
