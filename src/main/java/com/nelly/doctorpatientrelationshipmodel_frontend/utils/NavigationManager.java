package com.nelly.doctorpatientrelationshipmodel_frontend.utils;

import com.nelly.doctorpatientrelationshipmodel_frontend.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationManager {

    private static NavigationManager instance;
    private Stage stage;

    private NavigationManager() {}

    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void navigateTo(String fxmlRelativePath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(fxmlRelativePath));
            Parent root = loader.load();
            applyScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T navigateToAndGetController(String fxmlRelativePath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource(fxmlRelativePath));
            Parent root = loader.load();
            applyScene(root);
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void applyScene(Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                HelloApplication.class.getResource("css/medicare.css").toExternalForm());
        stage.setScene(scene);
    }
}
