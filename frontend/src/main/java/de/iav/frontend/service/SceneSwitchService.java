package de.iav.frontend.service;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class SceneSwitchService {

    private static SceneSwitchService instance;

    public static synchronized SceneSwitchService getInstance() {
        if (instance == null) {
            instance = new SceneSwitchService();
        }
        return instance;
    }

    public void switchToRegistrationView(ActionEvent actionEvent) throws IOException {
        // load layout of scene
        FXMLLoader loaderRegView = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/registration-view.fxml"));

        // set scene object which should display the content in scene2
        Scene sceneRegView = new Scene(loaderRegView.load());

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(sceneRegView);

        // set stage which should be shown (newly) on click
        Stage stage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());

        // set scene2 to stage and show it
        stage.setScene(sceneRegView);
        stage.show();
    }


    public void switchToLoginView(ActionEvent actionEvent) throws IOException {
        // load layout of scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/login-view.fxml"));

        // set scene object which should display the content in scene2
        Scene scene = new Scene(loader.load());

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);

        // set stage which should be shown (newly) on click
        Stage stage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());

        // set scene2 to stage and show it
        stage.setScene(scene);
        stage.show();
    }

}
