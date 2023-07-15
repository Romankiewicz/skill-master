package de.iav.frontend.controller;

import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private CheckBox gdprCheckbox;

    @FXML
    public void onRegisterButtonClick_switchToNextView () throws IOException {
        if(isEveryTextFieldValid()){
            errorLabel.setText("Alles palletti");
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
