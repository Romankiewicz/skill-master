package de.iav.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class SkillMasterApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SkillMasterApplication.class.getResource("/de/iav/frontend/fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 615, 337);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}