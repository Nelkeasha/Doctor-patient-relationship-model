import exceptions.AppointmentException;
import exceptions.InvalidDoctorException;
import exceptions.InvalidPatientException;
import exceptions.PrescriptionException;
import models.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   DOCTOR-PATIENT MANAGEMENT SYSTEM     ");
        System.out.println("========================================\n");

        System.out.println("--- TEST 1: Creating Valid Doctor ---");
        Doctor doctor = null;
        try {
            doctor = new Doctor("Alice", 35, "alice@doc.com", "female", "cardiology");
            System.out.println("Doctor created successfully!");
            doctor.describe();
        } catch (InvalidDoctorException e) {
            System.out.println("Doctor Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 2: Doctor With Empty Specialty ---");
        try {
            Doctor badDoctor = new Doctor("John", 35, "john@doc.com", "male", "");
        } catch (InvalidDoctorException e) {
            System.out.println("Doctor Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 3: Creating Valid Patient ---");
        Patient patient = null;
        try {
            patient = new Patient("P001", "Bob", 25, "bob@patient.com", "male", "kigali", "0788831221");
            System.out.println("Patient created successfully!");
            patient.describe();
        } catch (InvalidPatientException e) {
            System.out.println("Patient Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 4: Patient With Negative Age ---");
        try {
            Patient badPatient = new Patient("P002", "Jane", -5, "jane@patient.com", "female", "kigali", "0788000004");
        } catch (InvalidPatientException e) {
            System.out.println("Patient Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 5: Patient With Empty ID ---");
        try {
            Patient badPatient = new Patient("", "Mike", 30, "mike@patient.com", "male", "kigali", "0788000005");
        } catch (InvalidPatientException e) {
            System.out.println("Patient Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 6: Assigning Doctor to Patient ---");
        try {
            doctor.addPatient(patient);
            patient.setAssignedDoctor(doctor);
        } catch (InvalidDoctorException | InvalidPatientException e) {
            System.out.println("Assignment Error: " + e.getMessage());
        }

        System.out.println("\n--- TEST 7: Assigning Same Doctor Again ---");
        try {
            patient.setAssignedDoctor(doctor);
        } catch (InvalidPatientException e) {
            System.out.println("Patient Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 8: Booking Valid Appointment ---");
        Appointment appointment = null;
        try {
            appointment = new Appointment(doctor, patient, "2026-04-25", "scheduled");
            appointment.confirmAppointment();
            appointment.display();
        } catch (AppointmentException e) {
            System.out.println("Appointment Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 9: Cancelling Appointment Twice ---");
        try {
            appointment.cancelAppointment();
            appointment.cancelAppointment();
        } catch (AppointmentException e) {
            System.out.println("Appointment Error: " + e.getMessage());
        }

        System.out.println("\n--- TEST 10: Writing Valid Prescription ---");
        Prescription prescription = null;
        try {
            doctor.diagnose(patient, "Hypertension");
            prescription = doctor.prescribe(
                    patient,
                    "Amlodipine",
                    "5mg",
                    "Once a day after breakfast"
            );
            prescription.display();
        } catch (PrescriptionException | InvalidDoctorException e) {
            System.out.println("Prescription Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 11: Prescription With Empty Medicine ---");
        try {
            doctor.prescribe(patient, "", "5mg", "Once a day");
        } catch (PrescriptionException e) {
            System.out.println("Prescription Error: " + e.getMessage());
        }

        System.out.println("\n--- TEST 12: Medical Record and History ---");
        try {
            MedicalRecord record = new MedicalRecord(
                    "2026-04-25",
                    "Hypertension",
                    prescription,
                    doctor.getName()
            );
            patient.addMedicalRecord(record);
            patient.viewHistory();
        } catch (InvalidPatientException e) {
            System.out.println("Record Error: " + e.getMessage());
        }

        System.out.println("\n========================================");
        System.out.println("         ALL TESTS COMPLETED            ");
        System.out.println("========================================");
    }
}