package com.nelly.doctorpatientrelationshipmodel_frontend.controllers;

import com.nelly.doctorpatientrelationshipmodel_frontend.models.*;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.DataManager;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.NavigationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class DoctorDashboardController implements Initializable {

    @FXML private Label sidebarInitials;
    @FXML private Label sidebarName;
    @FXML private Label sidebarSpec;
    @FXML private Label welcomeLabel;
    @FXML private Label specLabel;
    @FXML private Label dateLabel;

    // Stat labels
    @FXML private Label patientCountLabel;
    @FXML private Label apptCountLabel;
    @FXML private Label specCountLabel;
    @FXML private Label activeApptLabel;

    // Patients table
    @FXML private TableView<Patient>             patientTable;
    @FXML private TableColumn<Patient, String>   patNameCol;
    @FXML private TableColumn<Patient, String>   patEmailCol;
    @FXML private TableColumn<Patient, String>   patPhoneCol;
    @FXML private TableColumn<Patient, String>   patAddrCol;

    // Appointments table
    @FXML private TableView<Appointment>             appointmentTable;
    @FXML private TableColumn<Appointment, String>   apptPatCol;
    @FXML private TableColumn<Appointment, String>   apptDateCol;
    @FXML private TableColumn<Appointment, String>   apptStatusCol;
    @FXML private TableColumn<Appointment, String>   apptActionCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Doctor d = DataManager.getInstance().getCurrentDoctor();
        if (d == null) { NavigationManager.getInstance().navigateTo("fxml/login.fxml"); return; }

        setupHeader(d);
        setupStats(d);
        setupPatientsTable(d);
        setupAppointmentsTable(d);
    }

    private void setupHeader(Doctor d) {
        String initials = initials(d.getName());
        sidebarInitials.setText(initials);
        sidebarName.setText("Dr. " + d.getName());
        String spec = d.getSpecialty().isEmpty() ? "General Practitioner"
                                                 : String.join(", ", d.getSpecialty());
        sidebarSpec.setText(spec);
        welcomeLabel.setText("Welcome, Dr. " + d.getName() + "!");
        specLabel.setText(spec);
        dateLabel.setText("Today — " + LocalDate.now());
    }

    private void setupStats(Doctor d) {
        DataManager dm = DataManager.getInstance();
        List<Appointment> appts = dm.getAppointmentsForDoctor(d);

        patientCountLabel.setText(String.valueOf(d.getPatients().size()));
        apptCountLabel.setText(String.valueOf(appts.size()));
        specCountLabel.setText(String.valueOf(d.getSpecialty().size()));

        long active = appts.stream()
                .filter(a -> !"Cancelled".equals(a.getStatus()))
                .count();
        activeApptLabel.setText(String.valueOf(active));
    }

    private void setupPatientsTable(Doctor d) {
        patNameCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getName()));
        patEmailCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getEmail()));
        patPhoneCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getPhone()));
        patAddrCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getAddress()));

        patientTable.getItems().setAll(d.getPatients());
    }

    private void setupAppointmentsTable(Doctor d) {
        apptPatCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getPatient().getName()));
        apptDateCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDate()));
        apptStatusCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getStatus()));
        apptStatusCol.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                if (empty || s == null) { setText(null); setGraphic(null); return; }
                Label badge = new Label(s);
                badge.getStyleClass().add(statusBadge(s));
                setGraphic(badge); setText(null);
            }
        });

        apptActionCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getStatus()));
        apptActionCol.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) { setGraphic(null); return; }
                if ("Cancelled".equals(status)) { setText("—"); setGraphic(null); return; }
                Button btn = new Button("Confirm");
                btn.getStyleClass().add("btn-primary");
                btn.setStyle("-fx-font-size: 11px; -fx-padding: 4 10 4 10;");
                btn.setOnAction(e -> {
                    Appointment appt = getTableView().getItems().get(getIndex());
                    appt.confirmAppointment();
                    DataManager.getInstance().saveAll();
                    getTableView().refresh();
                    setupStats(DataManager.getInstance().getCurrentDoctor());
                });
                setGraphic(btn); setText(null);
            }
        });

        List<Appointment> appts = DataManager.getInstance().getAppointmentsForDoctor(d);
        appointmentTable.getItems().setAll(appts);
    }

    private String statusBadge(String status) {
        return switch (status) {
            case "Confirmed"  -> "badge-green";
            case "Scheduled"  -> "badge-blue";
            case "Cancelled"  -> "badge-red";
            default           -> "badge-yellow";
        };
    }

    private String initials(String name) {
        String[] p = name.trim().split(" ");
        if (p.length >= 2) return ("" + p[0].charAt(0) + p[1].charAt(0)).toUpperCase();
        return name.substring(0, Math.min(2, name.length())).toUpperCase();
    }

    @FXML private void showOverview()       { /* already on overview */ }
    @FXML private void showPatients()       { patientTable.scrollTo(0); }
    @FXML private void showAppointments()   { appointmentTable.scrollTo(0); }
    @FXML private void showPrescriptions()  { /* extend later */ }

    @FXML private void addPatient() {
        showAddPatientDialog();
    }

    private void showAddPatientDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Patient to Your List");
        dialog.setHeaderText("Register a new patient");

        DataManager dm = DataManager.getInstance();
        Doctor doctor = dm.getCurrentDoctor();

        // Fields
        TextField nameF    = new TextField(); nameF.setPromptText("Full Name");
        TextField emailF   = new TextField(); emailF.setPromptText("Email");
        TextField ageF     = new TextField(); ageF.setPromptText("Age");
        TextField phoneF   = new TextField(); phoneF.setPromptText("Phone");
        TextField addrF    = new TextField(); addrF.setPromptText("Address");
        ComboBox<String> genderC = new ComboBox<>();
        genderC.getItems().addAll("Male", "Female", "Other");
        genderC.setPromptText("Gender");

        VBox form = new VBox(10,
            new Label("Name:"), nameF,
            new Label("Email:"), emailF,
            new Label("Age:"), ageF,
            new Label("Gender:"), genderC,
            new Label("Phone:"), phoneF,
            new Label("Address:"), addrF
        );
        form.setStyle("-fx-padding: 10;");
        dialog.getDialogPane().setContent(form);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                String name    = nameF.getText().trim();
                String email   = emailF.getText().trim();
                int    age     = Integer.parseInt(ageF.getText().trim());
                String gender  = genderC.getValue();
                String phone   = phoneF.getText().trim();
                String address = addrF.getText().trim();

                if (name.isEmpty() || email.isEmpty() || gender == null || phone.isEmpty() || address.isEmpty()) {
                    showAlert("All fields are required."); return;
                }
                if (dm.isEmailTaken(email)) { showAlert("Email already registered."); return; }

                String id = dm.generatePatientId();
                Patient p = new Patient(id, name, age, email, gender, address, phone);
                p.setAssignedDoctor(doctor);
                doctor.addPatient(p);
                dm.addPatient(p);

                patientTable.getItems().setAll(doctor.getPatients());
                setupStats(doctor);
            } catch (NumberFormatException ex) {
                showAlert("Age must be a valid number.");
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        }
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        a.showAndWait();
    }

    @FXML private void logout() {
        DataManager.getInstance().logout();
        NavigationManager.getInstance().navigateTo("fxml/home.fxml");
    }
}
