package com.nelly.doctorpatientrelationshipmodel_frontend.models;

import com.nelly.doctorpatientrelationshipmodel_frontend.exceptions.InvalidDoctorException;
import com.nelly.doctorpatientrelationshipmodel_frontend.exceptions.InvalidPatientException;
import com.nelly.doctorpatientrelationshipmodel_frontend.exceptions.PrescriptionException;

public class Prescription {
    private Doctor doctor;
    private Patient patient;
    private String medicine;
    private String dosage;
    private String instructions;

    public Prescription(Doctor doctor, Patient patient,
                        String medicine, String dosage, String instructions) {

        // Validation
        if (doctor == null) {
            throw new InvalidDoctorException("Doctor cannot be null in a prescription.");
        }
        if (patient == null) {
            throw new InvalidPatientException("Patient cannot be null in a prescription.");
        }
        if (medicine == null || medicine.trim().isEmpty()) {
            throw new PrescriptionException("Medicine name cannot be empty.");
        }
        if (dosage == null || dosage.trim().isEmpty()) {
            throw new PrescriptionException("Dosage cannot be empty.");
        }
        if (instructions == null || instructions.trim().isEmpty()) {
            throw new PrescriptionException("Instructions cannot be empty.");
        }

        this.doctor = doctor;
        this.patient = patient;
        this.medicine = medicine;
        this.dosage = dosage;
        this.instructions = instructions;
    }


    public String getMedicine() { return medicine; }
    public String getDosage() { return dosage; }
    public String getInstructions() { return instructions; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }

    public void display() {
        System.out.println("--- Prescription ---");
        System.out.println("Patient      : " + patient.getName());
        System.out.println("Doctor       : Dr. " + doctor.getName());
        System.out.println("Medicine     : " + medicine);
        System.out.println("Dosage       : " + dosage);
        System.out.println("Instructions : " + instructions);
    }
}