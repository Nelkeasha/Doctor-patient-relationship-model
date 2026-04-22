package models;

import exceptions.InvalidDoctorException;
import exceptions.InvalidPatientException;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {

    private String patientId;
    private String phone;
    private String address;
    private Doctor assignedDoctor;
    private List<MedicalRecord> medicalHistory;

    public Patient(String patientId, String name, int age, String email, String gender, String address, String phone) {
        super(name, age, email, gender);

        if (patientId == null || patientId.trim().isEmpty()) {
            throw new InvalidPatientException("Patient ID cannot be empty.");
        }

        this.phone = phone;
        this.address = address;
        this.patientId =  patientId;
        this.assignedDoctor = null;
        this.medicalHistory = new ArrayList<>();

    }

    public String getPatientId() {
        return patientId;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public  void setAssignedDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new InvalidDoctorException("Cannot assign a null doctor.");
        }
        if (this.assignedDoctor != null) {
            throw new InvalidPatientException(getName() +
                    " already has Dr. " + assignedDoctor.getName() + " assigned.");
        }

        this.assignedDoctor = doctor;
        System.out.println("Dr. " + doctor.getName() + " has been assigned to " + getName());
    }

    public void addMedicalRecord(MedicalRecord record) {
        if (record == null) {
            throw new InvalidPatientException("Medical record cannot be null.");
        }
        medicalHistory.add(record);
    }
    public void viewHistory() {
        if (medicalHistory.isEmpty()) {
            System.out.println(getName() + " has no medical records yet.");
        } else {
            System.out.println("--- Medical History for " + getName() + " ---");
            for (MedicalRecord record : medicalHistory) {
                record.display();
            }
        }
    }

    @Override
    public void describe() {
        System.out.println("models.Patient: " + getName() + " | ID: " + getPatientId() + "email:" + getEmail() + "gender:" + getGender() + "address:" + getAddress() + "phone:" + getPhone());
    }
}
