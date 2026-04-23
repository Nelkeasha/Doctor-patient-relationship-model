import exceptions.AppointmentException;
import exceptions.InvalidDoctorException;
import exceptions.InvalidPatientException;
import exceptions.PrescriptionException;
import models.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   DOCTOR-PATIENT MANAGEMENT SYSTEM     ");
        System.out.println("========================================\n");

        Map<String, Doctor> hospitalRegistry = new HashMap<>();

        Map<String, List<Patient>> doctorPatientRegistry = new HashMap<>();

        System.out.println("--- TEST 1: Creating Valid Doctor ---");
        Doctor doctor = null;
        try {
            doctor = new Doctor("Alice", 35, "alice@doc.com", "female");
            System.out.println("Doctor created successfully!");
            doctor.describe();
        } catch (InvalidDoctorException e) {
            System.out.println("Doctor Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 2: Doctor With Empty Specialty ---");
        try {
            Doctor badDoctor = new Doctor("John", 35, "john@doc.com", "male");
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

        System.out.println("\n--- TEST 8: Set Operations (Specialties) ---");
        try {

            doctor.addSpecialty("Cardiology");
            doctor.addSpecialty("Neurology");

            // Try adding duplicate — Set will reject it
            doctor.addSpecialty("Cardiology");


            doctor.removeSpecialty("Neurology");

            System.out.println("Dr. Alice's specialties: " + doctor.getSpecialty());
        } catch (InvalidDoctorException e) {
            System.out.println("Doctor Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 9: Map Operations (Hospital Registry) ---");

        hospitalRegistry.put("D001", doctor);
        System.out.println("Doctors registered: " + hospitalRegistry.keySet());


        Doctor foundDoctor = hospitalRegistry.get("D001");
        System.out.println("Looking up D001: " + foundDoctor.getName());


        hospitalRegistry.remove("D001");
        System.out.println("After removing D001: " + hospitalRegistry.keySet());


        hospitalRegistry.put("D001", doctor);


        System.out.println("\n--- TEST 10: Map + List (Doctor-Patient Registry) ---");


        doctorPatientRegistry.put("D001", doctor.getPatients());

        List<Patient> alicesPatients = doctorPatientRegistry.get("D001");
        System.out.println("Dr. Alice's patients from registry:");
        for (Patient p : alicesPatients) {
            System.out.println("  - " + p.getName());
        }



        System.out.println("\n--- TEST 11: List Operations (Doctor's Patients) ---");
        try {
            Patient patient2 = new Patient("P002", "Diana", 30, "diana@patient.com", "female", "Musanze", "0788000002");

            doctor.addPatient(patient2);
            System.out.println("Total patients: " + doctor.getPatients().size());

            Patient retrieved = doctor.getPatient(0);
            System.out.println("Retrieved patient at index 0: " + retrieved.getName());

            doctor.removePatient(patient2);
            System.out.println("After removal, total patients: " + doctor.getPatients().size());

        } catch (InvalidPatientException e) {
            System.out.println("Patient Error: " + e.getMessage());
        }

        System.out.println("\n--- TEST 12: List Operations (Appointments) ---");
        Appointment appointment = null;
        try {
            appointment = new Appointment(doctor, patient, "2026-04-25", "scheduled");
            patient.addAppointment(appointment);

            Appointment retrieved = patient.getAppointment(0);
            System.out.println("Retrieved appointment date: " + retrieved.getDate());

            appointment.confirmAppointment();
            appointment.display();

        } catch (AppointmentException e) {
            System.out.println("Appointment Error: " + e.getMessage());
        }

        System.out.println("\n--- TEST 13: Cancelling Appointment Twice ---");
        try {
            appointment.cancelAppointment();
            appointment.cancelAppointment();
        } catch (AppointmentException e) {
            System.out.println("Appointment Error: " + e.getMessage());
        }

        System.out.println("\n--- TEST 14: Remove Appointment from List ---");
        try {
            patient.removeAppointment(appointment);
            System.out.println("Appointments after removal: " + patient.getAppointments().size());
        } catch (AppointmentException e) {
            System.out.println("Appointment Error: " + e.getMessage());
        }

        System.out.println("\n--- TEST 15: Writing Valid Prescription ---");
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

        System.out.println("\n--- TEST 16: Prescription With Empty Medicine ---");
        try {
            doctor.prescribe(patient, "", "5mg", "Once a day");
        } catch (PrescriptionException e) {
            System.out.println("Prescription Error: " + e.getMessage());
        }


        System.out.println("\n--- TEST 17: List Operations (Medical Records) ---");
        try {
            MedicalRecord record = new MedicalRecord(
                    "2026-04-25",
                    "Hypertension",
                    prescription,
                    doctor.getName()
            );


            patient.addMedicalRecord(record);

            MedicalRecord retrieved = patient.getMedicalRecord(0);
            System.out.println("Retrieved record diagnosis: " + retrieved.getDiagnosis());


            patient.viewHistory();

            patient.removeMedicalRecord(record);
            System.out.println("Records after removal: " + patient.getMedicalHistory().size());

        } catch (InvalidPatientException e) {
            System.out.println("Record Error: " + e.getMessage());
        }

        System.out.println("\n========================================");
        System.out.println("         ALL TESTS COMPLETED            ");
        System.out.println("========================================");
    }
}