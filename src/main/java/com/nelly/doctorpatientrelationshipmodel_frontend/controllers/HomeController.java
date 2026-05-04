package com.nelly.doctorpatientrelationshipmodel_frontend.controllers;

import com.nelly.doctorpatientrelationshipmodel_frontend.models.Doctor;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.DataManager;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {

    @FXML private ScrollPane mainScroll;
    @FXML private HBox doctorsRow;

    private static final String[] AVATAR_COLORS = {
        "#7C3AED", "#9333EA", "#EC4899", "#3B82F6", "#10B981", "#F59E0B"
    };
    private static final String[] DEMO_NAMES = {
        "Dr. Martin Silva", "Dr. Kulok Dash", "Dr. Shelly Grace", "Dr. Andrew Cole"
    };
    private static final String[] DEMO_SPECIALTIES = {
        "Cardiologist", "Neurologist", "Pediatrician", "Orthopedist"
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateDoctors();
    }

    private void populateDoctors() {
        doctorsRow.getChildren().clear();
        List<Doctor> list = new ArrayList<>(DataManager.getInstance().getDoctors().values());

        if (list.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                doctorsRow.getChildren().add(
                    buildDoctorCard(DEMO_NAMES[i], DEMO_SPECIALTIES[i], AVATAR_COLORS[i], i == 1));
            }
        } else {
            for (int i = 0; i < Math.min(list.size(), 4); i++) {
                Doctor d = list.get(i);
                String specialty = d.getSpecialty().isEmpty()
                        ? "General Practitioner"
                        : d.getSpecialty().iterator().next();
                doctorsRow.getChildren().add(
                    buildDoctorCard("Dr. " + d.getName(), specialty,
                                    AVATAR_COLORS[i % AVATAR_COLORS.length], i == 1));
            }
        }
    }

    private VBox buildDoctorCard(String name, String specialty, String color, boolean featured) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add(featured ? "doctor-card-featured" : "doctor-card");

        // Avatar
        StackPane avatar = new StackPane();
        avatar.getStyleClass().add("doctor-avatar-circle");
        avatar.setStyle("-fx-background-color: " + color + "BB; -fx-background-radius: 50;");
        Label initials = new Label(initials(name));
        initials.getStyleClass().add("doctor-avatar-text");
        avatar.getChildren().add(initials);

        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add(featured ? "doctor-name-white" : "doctor-name");

        Label specLabel = new Label(specialty);
        specLabel.getStyleClass().add(featured ? "doctor-specialty-white" : "doctor-specialty");

        HBox dots = new HBox(6);
        dots.setAlignment(Pos.CENTER);
        String dotColor = featured ? "#FFFFFF55" : "#7C3AED44";
        for (int i = 0; i < 3; i++) {
            Region dot = new Region();
            dot.setMinSize(8, 8);
            dot.setMaxSize(8, 8);
            dot.setStyle("-fx-background-radius: 50; -fx-background-color: " + dotColor + ";");
            dots.getChildren().add(dot);
        }

        card.getChildren().addAll(avatar, nameLabel, specLabel, dots);
        return card;
    }

    private String initials(String name) {
        String clean = name.replace("Dr. ", "").trim();
        String[] parts = clean.split(" ");
        if (parts.length >= 2) {
            return ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
        }
        return clean.substring(0, Math.min(2, clean.length())).toUpperCase();
    }

    // ── Navigation ────────────────────────────────────────────────────────────
    @FXML private void scrollToTop()      { mainScroll.setVvalue(0); }
    @FXML private void scrollToAbout()    { mainScroll.setVvalue(0.18); }
    @FXML private void scrollToServices() { mainScroll.setVvalue(0.38); }
    @FXML private void scrollToDoctors()  { mainScroll.setVvalue(0.72); }
    @FXML private void goToLogin()        { NavigationManager.getInstance().navigateTo("fxml/login.fxml"); }
}
