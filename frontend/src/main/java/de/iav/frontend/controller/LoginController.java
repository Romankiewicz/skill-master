package de.iav.frontend.controller;

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

public class LoginController {

    @FXML
    private TextField userName_TF;

    @FXML
    private PasswordField password_TF;

    @FXML
    private ComboBox<String> userTypeSelector_CB;

    private AppUserRole selectedRole;

    public void initialize() {

        System.out.println(userTypeSelector_CB.getValue());

        userTypeSelector_CB.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println(newValue);

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
        }
        else {
            System.out.println("LOGIN FAILED! USER NAME OR LOGIN INCORRECT");
        }
    }



}
