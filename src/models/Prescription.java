package models;

import exceptions.InvalidDoctorException;
import exceptions.InvalidPatientException;
import exceptions.PrescriptionException;

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

    public void display() {
        System.out.println("--- models.Prescription ---");
        System.out.println("models.Patient      : " + patient.getName());
        System.out.println("models.Doctor       : Dr. " + doctor.getName());
        System.out.println("Medicine     : " + medicine);
        System.out.println("Dosage       : " + dosage);
        System.out.println("Instructions : " + instructions);
    }
}