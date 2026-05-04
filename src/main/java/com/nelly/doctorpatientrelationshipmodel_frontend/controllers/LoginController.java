package com.nelly.doctorpatientrelationshipmodel_frontend.controllers;

import com.nelly.doctorpatientrelationshipmodel_frontend.models.Doctor;
import com.nelly.doctorpatientrelationshipmodel_frontend.models.Patient;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.DataManager;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class LoginController implements Initializable {

    // Role toggles
    @FXML private Button patientToggle;
    @FXML private Button doctorToggle;

    // Mode toggles
    @FXML private Button loginModeBtn;
    @FXML private Button registerModeBtn;

    // Shared fields
    @FXML private TextField emailField;
    @FXML private Label     formSubtitle;
    @FXML private Button    submitBtn;
    @FXML private Label     switchLabel;
    @FXML private Button    switchBtn;
    @FXML private Label     errorLabel;

    // Register-only sections
    @FXML private VBox      nameBox;
    @FXML private VBox      ageBox;
    @FXML private VBox      genderBox;
    @FXML private VBox      patientExtraBox;
    @FXML private VBox      doctorExtraBox;

    // Register-only fields
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> genderCombo;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private ComboBox<String> doctorCombo;
    @FXML private TextField specialtyField;

    private boolean isPatientRole = true;
    private boolean isLoginMode   = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderCombo.getItems().addAll("Male", "Female", "Other");
        refreshDoctorCombo();
        applyMode();
    }

    private void refreshDoctorCombo() {
        doctorCombo.getItems().clear();
        doctorCombo.getItems().add("None");
        for (Doctor d : DataManager.getInstance().getDoctors().values()) {
            String spec = d.getSpecialty().isEmpty() ? "" : " — " + d.getSpecialty().iterator().next();
            doctorCombo.getItems().add("Dr. " + d.getName() + spec + " [" + d.getDoctorId() + "]");
        }
        doctorCombo.getSelectionModel().selectFirst();
    }

    // ── Role selection ────────────────────────────────────────────────────────
    @FXML private void selectPatient() {
        isPatientRole = true;
        patientToggle.getStyleClass().setAll("toggle-active");
        doctorToggle.getStyleClass().setAll("toggle-inactive");
        applyMode();
    }

    @FXML private void selectDoctor() {
        isPatientRole = false;
        doctorToggle.getStyleClass().setAll("toggle-active");
        patientToggle.getStyleClass().setAll("toggle-inactive");
        applyMode();
    }

    // ── Mode switching ────────────────────────────────────────────────────────
    @FXML private void switchToLogin() {
        isLoginMode = true;
        loginModeBtn.getStyleClass().setAll("toggle-active");
        registerModeBtn.getStyleClass().setAll("toggle-inactive");
        applyMode();
    }

    @FXML private void switchToRegister() {
        isLoginMode = false;
        registerModeBtn.getStyleClass().setAll("toggle-active");
        loginModeBtn.getStyleClass().setAll("toggle-inactive");
        applyMode();
    }

    private void applyMode() {
        clearError();
        boolean showRegister = !isLoginMode;

        // Show/hide register-only widgets
        nameBox.setVisible(showRegister);
        nameBox.setManaged(showRegister);
        ageBox.setVisible(showRegister);
        ageBox.setManaged(showRegister);
        genderBox.setVisible(showRegister);
        genderBox.setManaged(showRegister);

        boolean showPatientExtra = showRegister && isPatientRole;
        patientExtraBox.setVisible(showPatientExtra);
        patientExtraBox.setManaged(showPatientExtra);

        boolean showDoctorExtra = showRegister && !isPatientRole;
        doctorExtraBox.setVisible(showDoctorExtra);
        doctorExtraBox.setManaged(showDoctorExtra);

        // Labels
        String role = isPatientRole ? "Patient" : "Doctor";
        if (isLoginMode) {
            formSubtitle.setText("Sign in as a " + role);
            submitBtn.setText("Sign In");
            switchLabel.setText("Don't have an account?");
            switchBtn.setText("Register here");
            switchBtn.setOnAction(e -> switchToRegister());
        } else {
            formSubtitle.setText("Create a new " + role + " account");
            submitBtn.setText("Create Account");
            switchLabel.setText("Already have an account?");
            switchBtn.setText("Sign in here");
            switchBtn.setOnAction(e -> switchToLogin());
        }
        if (showRegister && isPatientRole) refreshDoctorCombo();
    }

    // ── Submit ────────────────────────────────────────────────────────────────
    @FXML private void handleSubmit() {
        clearError();
        if (isLoginMode) handleLogin();
        else             handleRegister();
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) { showError("Please enter your email address."); return; }

        DataManager dm = DataManager.getInstance();
        if (isPatientRole) {
            Patient p = dm.findPatientByEmail(email);
            if (p == null) { showError("No patient found with that email. Please register first."); return; }
            dm.setCurrentPatient(p);
            NavigationManager.getInstance().navigateTo("fxml/patient-dashboard.fxml");
        } else {
            Doctor d = dm.findDoctorByEmail(email);
            if (d == null) { showError("No doctor found with that email. Please register first."); return; }
            dm.setCurrentDoctor(d);
            NavigationManager.getInstance().navigateTo("fxml/doctor-dashboard.fxml");
        }
    }

    private void handleRegister() {
        String name   = nameField.getText().trim();
        String email  = emailField.getText().trim();
        String ageStr = ageField.getText().trim();
        String gender = genderCombo.getValue();

        if (name.isEmpty() || email.isEmpty() || ageStr.isEmpty() || gender == null) {
            showError("Please fill in all required fields."); return;
        }
        int age;
        try { age = Integer.parseInt(ageStr); } catch (NumberFormatException e) {
            showError("Age must be a valid number."); return;
        }
        if (age <= 0 || age > 150) { showError("Please enter a valid age."); return; }

        DataManager dm = DataManager.getInstance();
        if (dm.isEmailTaken(email)) { showError("That email is already registered."); return; }

        try {
            if (isPatientRole) registerPatient(dm, name, age, email, gender);
            else               registerDoctor(dm, name, age, email, gender);
        } catch (Exception ex) {
            showError("Registration failed: " + ex.getMessage());
        }
    }

    private void registerPatient(DataManager dm, String name, int age, String email, String gender) {
        String phone   = phoneField.getText().trim();
        String address = addressField.getText().trim();
        if (phone.isEmpty() || address.isEmpty()) {
            showError("Phone and address are required for patients."); return;
        }

        String id = dm.generatePatientId();
        Patient p = new Patient(id, name, age, email, gender, address, phone);
        dm.addPatient(p);

        // Assign selected doctor (optional)
        String selected = doctorCombo.getValue();
        if (selected != null && !selected.equals("None")) {
            String docId = selected.substring(selected.lastIndexOf('[') + 1, selected.lastIndexOf(']'));
            Doctor d = dm.getDoctors().get(docId);
            if (d != null) {
                p.setAssignedDoctor(d);
                d.addPatient(p);
                dm.saveAll();
            }
        }

        dm.setCurrentPatient(p);
        NavigationManager.getInstance().navigateTo("fxml/patient-dashboard.fxml");
    }

    private void registerDoctor(DataManager dm, String name, int age, String email, String gender) {
        String specialty = specialtyField.getText().trim();
        String id = dm.generateDoctorId();
        Doctor d = new Doctor(id, name, age, email, gender);
        if (!specialty.isEmpty()) d.addSpecialty(specialty);
        dm.addDoctor(d);
        dm.setCurrentDoctor(d);
        NavigationManager.getInstance().navigateTo("fxml/doctor-dashboard.fxml");
    }

    @FXML private void goHome() { NavigationManager.getInstance().navigateTo("fxml/home.fxml"); }

    private void showError(String msg) { errorLabel.setText(msg); }
    private void clearError()          { errorLabel.setText(""); }
}
