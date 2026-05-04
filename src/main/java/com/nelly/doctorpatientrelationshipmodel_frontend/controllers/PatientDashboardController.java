package com.nelly.doctorpatientrelationshipmodel_frontend.controllers;

import com.nelly.doctorpatientrelationshipmodel_frontend.models.*;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.DataManager;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.NavigationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PatientDashboardController implements Initializable {

    @FXML private Label sidebarInitials;
    @FXML private Label sidebarName;
    @FXML private Label welcomeLabel;
    @FXML private Label dateLabel;

    // Stat labels
    @FXML private Label apptCountLabel;
    @FXML private Label doctorNameLabel;
    @FXML private Label recordCountLabel;
    @FXML private Label prescCountLabel;

    // Appointments table
    @FXML private TableView<Appointment>         appointmentTable;
    @FXML private TableColumn<Appointment, String> apptDoctorCol;
    @FXML private TableColumn<Appointment, String> apptDateCol;
    @FXML private TableColumn<Appointment, String> apptStatusCol;
    @FXML private TableColumn<Appointment, String> apptActionCol;

    // Records table
    @FXML private TableView<MedicalRecord>         recordTable;
    @FXML private TableColumn<MedicalRecord, String> recDateCol;
    @FXML private TableColumn<MedicalRecord, String> recDoctorCol;
    @FXML private TableColumn<MedicalRecord, String> recDiagCol;
    @FXML private TableColumn<MedicalRecord, String> recMedCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Patient p = DataManager.getInstance().getCurrentPatient();
        if (p == null) { NavigationManager.getInstance().navigateTo("fxml/login.fxml"); return; }

        setupHeader(p);
        setupStats(p);
        setupAppointmentsTable(p);
        setupRecordsTable(p);
    }

    private void setupHeader(Patient p) {
        String initials = initials(p.getName());
        sidebarInitials.setText(initials);
        sidebarName.setText(p.getName());
        welcomeLabel.setText("Welcome back, " + p.getName() + "!");
        dateLabel.setText("Today is " + LocalDate.now() + " — here's your health overview.");
    }

    private void setupStats(Patient p) {
        List<Appointment> appts = DataManager.getInstance().getAppointmentsForPatient(p);
        apptCountLabel.setText(String.valueOf(appts.size()));

        Doctor doc = p.getAssignedDoctor();
        doctorNameLabel.setText(doc != null ? "Dr. " + doc.getName() : "None assigned");

        recordCountLabel.setText(String.valueOf(p.getMedicalHistory().size()));

        long prescCount = p.getMedicalHistory().stream()
                .filter(r -> r.getPrescription() != null)
                .count();
        prescCountLabel.setText(String.valueOf(prescCount));
    }

    private void setupAppointmentsTable(Patient p) {
        apptDoctorCol.setCellValueFactory(c ->
                new SimpleStringProperty("Dr. " + c.getValue().getDoctor().getName()));
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
                if ("Cancelled".equals(status)) { setText("Cancelled"); setGraphic(null); return; }
                Button btn = new Button("Cancel");
                btn.getStyleClass().add("btn-danger");
                btn.setStyle("-fx-font-size: 11px; -fx-padding: 4 10 4 10;");
                btn.setOnAction(e -> {
                    Appointment appt = getTableView().getItems().get(getIndex());
                    DataManager.getInstance().cancelAppointment(appt);
                    getTableView().refresh();
                    setupStats(DataManager.getInstance().getCurrentPatient());
                });
                setGraphic(btn); setText(null);
            }
        });

        List<Appointment> appts = DataManager.getInstance().getAppointmentsForPatient(p);
        appointmentTable.getItems().setAll(appts);
    }

    private void setupRecordsTable(Patient p) {
        recDateCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDate()));
        recDoctorCol.setCellValueFactory(c ->
                new SimpleStringProperty("Dr. " + c.getValue().getDoctorName()));
        recDiagCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDiagnosis()));
        recMedCol.setCellValueFactory(c -> {
            Prescription pr = c.getValue().getPrescription();
            return new SimpleStringProperty(pr != null ? pr.getMedicine() : "—");
        });
        recordTable.getItems().setAll(p.getMedicalHistory());
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

    @FXML private void showOverview()      { /* already on overview */ }
    @FXML private void showAppointments()  { appointmentTable.scrollTo(0); }
    @FXML private void showRecords()       { recordTable.scrollTo(0); }
    @FXML private void showDoctor()        { /* show doctor info dialog */ }
    @FXML private void goBookAppointment() { NavigationManager.getInstance().navigateTo("fxml/appointment.fxml"); }
    @FXML private void logout() {
        DataManager.getInstance().logout();
        NavigationManager.getInstance().navigateTo("fxml/home.fxml");
    }
}
