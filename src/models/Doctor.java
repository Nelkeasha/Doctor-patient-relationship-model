package models;

import exceptions.InvalidDoctorException;
import exceptions.InvalidPatientException;
import exceptions.PrescriptionException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Doctor extends Person {

    private String doctorId;

    private Set<String> specialties;
    private List<Patient> patients;

    public Doctor(String doctorId, String name, int age, String email, String gender) {
        super(name, age, email, gender);

        if (doctorId == null || doctorId.trim().isEmpty()) {
            throw new InvalidDoctorException("Doctor ID cannot be empty.");
        }
        this.doctorId = doctorId;
        this.specialties = new HashSet<>();
        this.patients = new ArrayList<>();
    }

    public String getDoctorId() { return doctorId; }
    public List<Patient> getPatients() { return patients; }
    public Set<String> getSpecialty() { return specialties; }



    public void addPatient (Patient patient){
        // Validation
        if (patient == null) {
            throw new InvalidPatientException("Cannot add a null patient.");
        }
        patients.add(patient);
        System.out.println(patient.getName() + "added to Dr. " + getName() + "'s patient list");
    }

    public Patient getPatient(int index) {
        if (index < 0 || index >= patients.size()) {
            throw new InvalidPatientException("Patient index out of range.");
        }
        return patients.get(index);
    }

    public void removePatient(Patient patient) {
        if (!patients.contains(patient)) {
            throw new InvalidPatientException(patient.getName() + " is not in this doctor's list.");
        }
        patients.remove(patient);
        System.out.println(patient.getName() + " removed from Dr. " + getName() + "'s list.");
    }

    public void addSpecialty(String specialty) {
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new InvalidDoctorException("Specialty cannot be empty.");
        }
        boolean added = specialties.add(specialty);
        if (!added) {
            System.out.println("Dr. " + getName() + " already has specialty: " + specialty);
        } else {
            System.out.println("Specialty '" + specialty + "' added to Dr. " + getName());
        }
    }

    public void removeSpecialty(String specialty) {
        if (!specialties.contains(specialty)) {
            throw new InvalidDoctorException("Specialty '" + specialty + "' not found.");
        }
        specialties.remove(specialty);
        System.out.println("Specialty '" + specialty + "' removed from Dr. " + getName());
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
