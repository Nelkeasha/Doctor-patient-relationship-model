package com.nelly.doctorpatientrelationshipmodel_frontend;

import com.nelly.doctorpatientrelationshipmodel_frontend.utils.NavigationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        NavigationManager.getInstance().setStage(stage);

        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("fxml/home.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 820);
        scene.getStylesheets().add(
                HelloApplication.class.getResource("css/medicare.css").toExternalForm());

        stage.setTitle("Medicare — Patient-Centered Healthcare");
        stage.setScene(scene);
        stage.setMinWidth(1024);
        stage.setMinHeight(700);
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}
