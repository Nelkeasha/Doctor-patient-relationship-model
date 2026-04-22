package models;

import exceptions.InvalidDoctorException;
import exceptions.InvalidPatientException;
import exceptions.PrescriptionException;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {

    private String specialty;
    private List<Patient> patients;

    public Doctor(String name, int age, String email, String gender,  String specialty) {
        super(name, age, email, gender);

        //validation
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new InvalidDoctorException("Doctor specialty cannot be empty.");
        }
        this.specialty = specialty;
        this.patients = new ArrayList<>();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void addPatient (Patient patient){
        // Validation
        if (patient == null) {
            throw new InvalidPatientException("Cannot add a null patient.");
        }
        patients.add(patient);
        System.out.println(patient.getName() + "added to Dr. " + getName() + "'s patient list");
    }

    public void diagnose(Patient patient, String diagnosis){
        if (patient == null) {
            throw new InvalidPatientException("Patient cannot be null.");
        }
        if (diagnosis == null || diagnosis.trim().isEmpty()) {
            throw new InvalidDoctorException("Diagnosis cannot be empty.");
        }
        System.out.println("Dr. " + getName() + " diagnosed " + patient.getName() + " with: " + diagnosis);
    }

    public Prescription prescribe(Patient patient, String medicine, String dosage, String instructions) {
        if (medicine == null || medicine.trim().isEmpty()) {
            throw new PrescriptionException("Medicine name cannot be empty.");
        }
        if (dosage == null || dosage.trim().isEmpty()) {
            throw new PrescriptionException("Dosage cannot be empty.");
        }

        Prescription prescription = new Prescription(this, patient, medicine, dosage, instructions);
        System.out.println("Dr. " + getName() + " prescribed " + medicine + " to " + patient.getName());
        return prescription;
    }

    @Override
    public void describe() {
        System.out.println("models.Doctor: " + getName() + " | specialty: " + getSpecialty() + " | email: " + getEmail() );
    }
}
