package com.nelly.doctorpatientrelationshipmodel_frontend.controllers;

import com.nelly.doctorpatientrelationshipmodel_frontend.models.*;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.DataManager;
import com.nelly.doctorpatientrelationshipmodel_frontend.utils.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    @FXML private Label     sidebarInitials;
    @FXML private Label     sidebarName;

    @FXML private Label     doctorInitials;
    @FXML private Label     doctorNameLabel;
    @FXML private Label     doctorSpecLabel;

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> typeCombo;
    @FXML private TextArea  notesArea;
    @FXML private Label     errorLabel;

    @FXML private VBox      noDoctorWarning;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Patient p = DataManager.getInstance().getCurrentPatient();
        if (p == null) { NavigationManager.getInstance().navigateTo("fxml/login.fxml"); return; }

        sidebarName.setText(p.getName());
        sidebarInitials.setText(initials(p.getName()));

        typeCombo.getItems().addAll(
            "General Consultation",
            "Follow-up Visit",
            "Specialist Referral",
            "Lab / Test Review",
            "Emergency Consultation",
            "Mental Health Session"
        );
        typeCombo.getSelectionModel().selectFirst();
        datePicker.setValue(LocalDate.now().plusDays(1));

        Doctor doc = p.getAssignedDoctor();
        boolean hasDoctor = doc != null;

        noDoctorWarning.setVisible(!hasDoctor);
        noDoctorWarning.setManaged(!hasDoctor);

        if (hasDoctor) {
            doctorNameLabel.setText("Dr. " + doc.getName());
            String spec = doc.getSpecialty().isEmpty() ? "General Practitioner"
                                                       : String.join(", ", doc.getSpecialty());
            doctorSpecLabel.setText(spec);
            doctorInitials.setText(initials(doc.getName()));
        } else {
            doctorNameLabel.setText("No doctor assigned");
            doctorSpecLabel.setText("");
        }
    }

    @FXML private void bookAppointment() {
        clearError();
        Patient p = DataManager.getInstance().getCurrentPatient();
        if (p == null || p.getAssignedDoctor() == null) {
            showError("You need an assigned doctor to book an appointment."); return;
        }

        LocalDate date = datePicker.getValue();
        if (date == null) { showError("Please select a date."); return; }
        if (date.isBefore(LocalDate.now())) { showError("Appointment date must be in the future."); return; }

        String type  = typeCombo.getValue();
        String notes = notesArea.getText().trim();
        String dateStr = date.format(FMT) + (notes.isEmpty() ? "" : " — " + type);

        try {
            Doctor doctor = p.getAssignedDoctor();
            Appointment appt = new Appointment(doctor, p, dateStr, "Scheduled");
            p.addAppointment(appt);
            DataManager.getInstance().addAppointment(appt);

            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Appointment booked for " + date.format(FMT) + " with Dr. " + doctor.getName() + "!",
                ButtonType.OK);
            alert.setTitle("Booking Confirmed");
            alert.setHeaderText("Your appointment has been scheduled.");
            alert.showAndWait();

            NavigationManager.getInstance().navigateTo("fxml/patient-dashboard.fxml");
        } catch (Exception e) {
            showError("Could not book appointment: " + e.getMessage());
        }
    }

    @FXML private void goBack() {
        NavigationManager.getInstance().navigateTo("fxml/patient-dashboard.fxml");
    }

    @FXML private void logout() {
        DataManager.getInstance().logout();
        NavigationManager.getInstance().navigateTo("fxml/home.fxml");
    }

    private String initials(String name) {
        String[] p = name.trim().split(" ");
        if (p.length >= 2) return ("" + p[0].charAt(0) + p[1].charAt(0)).toUpperCase();
        return name.substring(0, Math.min(2, name.length())).toUpperCase();
    }

    private void showError(String msg) { errorLabel.setText(msg); }
    private void clearError()          { errorLabel.setText(""); }
}
