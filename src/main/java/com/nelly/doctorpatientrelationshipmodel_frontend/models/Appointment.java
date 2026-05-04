package com.nelly.doctorpatientrelationshipmodel_frontend.models;

import com.nelly.doctorpatientrelationshipmodel_frontend.exceptions.AppointmentException;
import com.nelly.doctorpatientrelationshipmodel_frontend.exceptions.InvalidDoctorException;
import com.nelly.doctorpatientrelationshipmodel_frontend.exceptions.InvalidPatientException;

public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String date;
    private String status;

    public Appointment(Doctor doctor, Patient patient, String date, String status) {

        // Validation
        if (doctor == null) {
            throw new InvalidDoctorException("Doctor cannot be null for an appointment.");
        }
        if (patient == null) {
            throw new InvalidPatientException("Patient cannot be null for an appointment.");
        }
        if (date == null || date.trim().isEmpty()) {
            throw new AppointmentException("Appointment date cannot be empty.");
        }
        if (patient.getAssignedDoctor() == null) {
            throw new AppointmentException(patient.getName() +
                    " has no assigned doctor. Please assign a doctor first.");
        }

        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.status = (status == null || status.trim().isEmpty()) ? "Scheduled" : status;
    }


    public String getDate() { return date; }
    public String getStatus() { return status; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }

    public void confirmAppointment() {
        if (this.status.equals("Cancelled")) {
            throw new AppointmentException("Cannot confirm a cancelled appointment.");
        }
        System.out.println("Appointment confirmed for " + patient.getName() +
                " with Dr. " + doctor.getName() +
                " on " + date);
    }
    public void cancelAppointment() {
        if (this.status.equals("Cancelled")) {
            throw new AppointmentException("Appointment is already cancelled.");
        }
        this.status = "Cancelled";
        System.out.println("Appointment for " + patient.getName() +
                " with Dr. " + doctor.getName() +
                " has been cancelled.");
    }

    public void display() {
        System.out.println("--- Appointment Details ---");
        System.out.println("Patient  : " + patient.getName());
        System.out.println("Doctor   : Dr. " + doctor.getName());
        System.out.println("Date     : " + date);
        System.out.println("Status   : " + status);
    }
}
