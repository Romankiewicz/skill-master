package de.iav.frontend.controller;

import de.iav.frontend.security.AppUserRole;
import de.iav.frontend.security.AuthenticationService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class RegistrationViewController {
    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox selectRoleComboBox;

    @FXML
    private CheckBox gdprCheckbox;

    private AppUserRole selectedRole;
    public void initialize() {

        System.out.println(selectRoleComboBox.getValue());

        selectedRole = AppUserRole.STUDENT;

        selectRoleComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (newValue.equals("Register as student")){
                        this.selectedRole = AppUserRole.STUDENT;
                    }
                    else if (newValue.equals("Register as teacher")){
                        this.selectedRole = AppUserRole.TEACHER;
                    }

                }
        );
    }

    @FXML
    public void onRegisterButtonClick_switchToNextView (ActionEvent event) throws IOException {
        if(isEveryTextFieldValid()){
            String email = emailTextField.getText();
            String userName = userNameTextField.getText();
            String password = passwordTextField.getText();

            if(selectedRole.equals(AppUserRole.TEACHER)){
                if (AuthenticationService.getInstance().createNewTeacher(email, userName, password)) {
                    errorLabel.setText("Registration successful. Welcome " + AuthenticationService.getInstance().getUsername() + "!");

                    System.out.println("RegistrationViewController: " + AuthenticationService.getInstance().getSessionId());

                    SceneSwitchService.getInstance().switchToLoginView(event);
                } else {
                    errorLabel.setText(AuthenticationService.getInstance().getErrorMessage());
                }
            } else {
                if (AuthenticationService.getInstance().createNewStudent(email, userName, password)) {
                    errorLabel.setText("Registration successful. Welcome " + AuthenticationService.getInstance().getUsername() + "!");

                    System.out.println("RegistrationViewController: " + AuthenticationService.getInstance().getSessionId());

                    SceneSwitchService.getInstance().switchToLoginView(event);
                } else {
                    errorLabel.setText(AuthenticationService.getInstance().getErrorMessage());
                }
            }

        }
    }

    private boolean isEveryTextFieldValid() {
        if (userNameTextField.getText() == null || userNameTextField.getText().isEmpty()) {
            errorLabel.setText("Bitte gib einen Username ein");
            return false;
        } else if (passwordTextField.getText() == null || passwordTextField.getText().isEmpty()) {
            errorLabel.setText("Bitte gib einen Passwort ein");
            return false;
        } else if (firstNameTextField.getText() == null || firstNameTextField.getText().isEmpty()) {
            errorLabel.setText("Bitte gib einen Vornamen ein");
            return false;
        } else if (lastNameTextField.getText() == null || lastNameTextField.getText().isEmpty()) {
            errorLabel.setText("Bitte gib einen Nachnamen ein");
            return false;
        } else if (emailTextField.getText() == null || emailTextField.getText().isEmpty()) {
            errorLabel.setText("Bitte gib eine E-Mail-Adresse ein");
            return false;
        } else if (!gdprCheckbox.isSelected()) {
            errorLabel.setText("Bitte akzeptiere die Datenschutzerklärung");
            return false;
        }else {
            errorLabel.setText("");
            return true;
        }
    }

    @FXML
    public void onCancelButtonClick_switchToLoginView(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToLoginView(event);
    }

}
