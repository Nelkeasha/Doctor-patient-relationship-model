package models;

import exceptions.AppointmentException;
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
    private List<Appointment> appointments;

    public Patient(String patientId, String name, int age, String email, String gender, String address, String phone) {
        super(name, age, email, gender);

        if (patientId == null || patientId.trim().isEmpty()) {
            throw new InvalidPatientException("Patient ID cannot be empty.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidPatientException("Address cannot be empty.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidPatientException("Phone number cannot be empty.");
        }

        this.phone = phone;
        this.address = address;
        this.patientId =  patientId;
        this.assignedDoctor = null;
        this.medicalHistory = new ArrayList<>();
        this.appointments = new ArrayList<>();

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

    public List<MedicalRecord> getMedicalHistory() { return medicalHistory; }

    public List<Appointment> getAppointments() { return appointments; }

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
    public MedicalRecord getMedicalRecord(int index) {
        if (index < 0 || index >= medicalHistory.size()) {
            throw new InvalidPatientException("Medical record index out of range.");
        }
        return medicalHistory.get(index);
    }

    public void removeMedicalRecord(MedicalRecord record) {
        if (!medicalHistory.contains(record)) {
            throw new InvalidPatientException("Medical record not found.");
        }
        medicalHistory.remove(record);
        System.out.println("Medical record removed for " + getName());
    }

    public void addAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new AppointmentException("Appointment cannot be null.");
        }
        appointments.add(appointment);
        System.out.println("Appointment added for " + getName());
    }

    public Appointment getAppointment(int index) {
        if (index < 0 || index >= appointments.size()) {
            throw new AppointmentException("Appointment index out of range.");
        }
        return appointments.get(index);
    }

    public void removeAppointment(Appointment appointment) {
        if (!appointments.contains(appointment)) {
            throw new AppointmentException("Appointment not found.");
        }
        appointments.remove(appointment);
        System.out.println("Appointment removed for " + getName());
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

    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println(getName() + " has no appointments yet.");
        } else {
            System.out.println("--- Appointments for " + getName() + " ---");
            for (Appointment appointment : appointments) {
                appointment.display();
            }
        }
    }

    @Override
    public void describe() {
        System.out.println("models.Patient: " + getName() + " | ID: " + getPatientId() + "email:" + getEmail() + "gender:" + getGender() + "address:" + getAddress() + "phone:" + getPhone());
    }
}
