package de.iav.frontend.controller;

import de.iav.frontend.model.Student;
import de.iav.frontend.security.AppUserRole;
import de.iav.frontend.security.AuthenticationService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginController {

    @FXML
    private TextField userName_TF;

    @FXML
    private PasswordField password_TF;

    @FXML
    private ComboBox<String> userTypeSelector_CB;

    private AppUserRole selectedRole;

    private String STUDENTS_URL_BACKEND = System.getenv("BACKEND_STUDENT_URI");

    public void initialize() {

        System.out.println(userTypeSelector_CB.getValue());

        selectedRole = AppUserRole.STUDENT;

        userTypeSelector_CB.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (newValue.equals("Log in as student")){
                        this.selectedRole = AppUserRole.STUDENT;
                    }
                    else if (newValue.equals("Log in as teacher")){
                        this.selectedRole = AppUserRole.TEACHER;
                    }

                }
        );
    }

    @FXML
    public void onClick_switchToRegisterView(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToRegistrationView(event);
    }

    @FXML
    public void onClick_login(ActionEvent event) throws IOException {
        String loginName = userName_TF.getText();
        String password = password_TF.getText();
        boolean res = AuthenticationService.getInstance().login(loginName, password);

        if (res &&
                ! AuthenticationService.getInstance().getUsername().equals("anonymousUser")
        )
        {
            System.out.println("LOGIN NAME IS _______________ " + AuthenticationService.getInstance().getUsername());
            if (selectedRole == AppUserRole.STUDENT){

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(STUDENTS_URL_BACKEND + "/api/students/search?loginName=" + loginName))
                        .header("Accept", "application/json")
                        .header("Cookie", "JSESSIONID=" + AuthenticationService.getInstance().getSessionId())
                        .build();

                var response = AuthenticationService.getInstance().getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
                int statusCode = response.join().statusCode();
                String body = response.join().body();

                if (statusCode==200 && response.join().body().length() > 0) {
                    SceneSwitchService.getInstance().switchToStudentView(event);
                } else {
                    // TODO show in label (red)
                    System.out.println("LOGIN FAILED! PROBABLY ROLE INCORRECT OR INTERNAL SERVER ERROR");
                    System.out.println("Status Code: " + statusCode);
                    System.out.println("Response: " + body);
                }


            }
            else if (selectedRole == AppUserRole.TEACHER){

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(STUDENTS_URL_BACKEND + "/api/teachers/search?loginName=" + loginName))
                        .header("Accept", "application/json")
                        .header("Cookie", "JSESSIONID=" + AuthenticationService.getInstance().getSessionId())
                        .build();

                var response = AuthenticationService.getInstance().getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());

                int statusCode = response.join().statusCode();
                String body = response.join().body();

                if (statusCode==200 && body.length() > 0) {
                    SceneSwitchService.getInstance().switchToTeacherView(event);
                } else {
                    // TODO show in label (red)
                    System.out.println("LOGIN FAILED! PROBABLY ROLE INCORRECT OR INTERNAL SERVER ERROR");
                    System.out.println("Status Code: " + statusCode);
                    System.out.println("Response: " + body);
                }

            }

        }
        else {
            // TODO show in label (red)
            System.out.println("LOGIN NAME IS _______________ " + AuthenticationService.getInstance().getUsername());
            System.out.println("LOGIN FAILED! USER NAME OR LOGIN INCORRECT OR INTERNAL SERVER ERROR");
        }
    }



}
