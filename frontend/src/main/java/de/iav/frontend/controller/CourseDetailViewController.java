package de.iav.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.CourseDTO_RequestBody;
import de.iav.frontend.model.Teacher;
import de.iav.frontend.security.AuthenticationService;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TeacherViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CourseDetailViewController {
    @FXML
    private TextField courseNameTextField;

    @FXML
    private TextField courseContentTextField;

    @FXML
    private ListView studentsOfCourseListView;

    @FXML
    private Button cancelButton;

    @FXML
    private Button submitButton;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String STUDENTS_URL_BACKEND = "http://localhost:8080";

    @FXML
    public void onCancelButtonClickSwitchToTeacherScene(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToTeacherView(event);
    }

    @FXML
    public void onSubmitButtonClickSwitchToTeacherSceneAndSaveCourse(ActionEvent event) throws IOException{

        String courseName = courseNameTextField.getText();
        String courseContent = courseContentTextField.getText();

        Teacher loginTeacher = TeacherViewService.getInstance().getLoginTeacher();

        CourseDTO_RequestBody courseRequest = new CourseDTO_RequestBody(courseName, courseContent);

        int statusCode = -1;

        try {
            String requestBody = objectMapper.writeValueAsString(courseRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STUDENTS_URL_BACKEND + "/api/teachers/" + loginTeacher.teacherId() + "/course"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Cookie", "JSESSIONID=" + AuthenticationService.getInstance().getSessionId())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            var response =  AuthenticationService.getInstance().getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.join().statusCode();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (statusCode == 202){
            SceneSwitchService.getInstance().switchToTeacherView(event);
        }
        else {
            System.out.println("ADDING COURSE FAILED. ");
        }

    }
}
