package com.nelly.doctorpatientrelationshipmodel_frontend.utils;

import com.nelly.doctorpatientrelationshipmodel_frontend.fileio.*;
import com.nelly.doctorpatientrelationshipmodel_frontend.models.*;

import java.util.*;

public class DataManager {

    private static DataManager instance;

    private final Map<String, Doctor> doctors = new LinkedHashMap<>();
    private final Map<String, Patient> patients = new LinkedHashMap<>();
    private final List<Appointment> appointments = new ArrayList<>();

    private Doctor currentDoctor;
    private Patient currentPatient;

    private DataManager() {
        loadData();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void loadData() {
        try {
            List<Doctor> docList = DoctorFileManager.load();
            for (Doctor d : docList) doctors.put(d.getDoctorId(), d);

            List<Patient> patList = PatientFileManager.load(doctors);
            for (Patient p : patList) patients.put(p.getPatientId(), p);

            List<Appointment> apptList = AppointmentFileManager.load(doctors, patients);
            appointments.addAll(apptList);

            MedicalRecordFileManager.loadAll(doctors, patients);
        } catch (Exception e) {
            System.out.println("No existing data — starting fresh.");
        }
    }

    public void saveAll() {
        try {
            DoctorFileManager.save(new ArrayList<>(doctors.values()));
            PatientFileManager.save(new ArrayList<>(patients.values()));
            AppointmentFileManager.save(appointments);
            MedicalRecordFileManager.save(new ArrayList<>(patients.values()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Data Access ──────────────────────────────────────────────────────────
    public Map<String, Doctor> getDoctors() { return doctors; }
    public Map<String, Patient> getPatients() { return patients; }
    public List<Appointment> getAppointments() { return appointments; }

    // ── Session ──────────────────────────────────────────────────────────────
    public Doctor getCurrentDoctor() { return currentDoctor; }
    public Patient getCurrentPatient() { return currentPatient; }
    public void setCurrentDoctor(Doctor d) { currentDoctor = d; }
    public void setCurrentPatient(Patient p) { currentPatient = p; }
    public void logout() { currentDoctor = null; currentPatient = null; }

    // ── CRUD ─────────────────────────────────────────────────────────────────
    public void addDoctor(Doctor d) { doctors.put(d.getDoctorId(), d); saveAll(); }
    public void addPatient(Patient p) { patients.put(p.getPatientId(), p); saveAll(); }
    public void addAppointment(Appointment a) { appointments.add(a); saveAll(); }

    public void cancelAppointment(Appointment a) {
        a.cancelAppointment();
        saveAll();
    }

    // ── Lookup ───────────────────────────────────────────────────────────────
    public Doctor findDoctorByEmail(String email) {
        return doctors.values().stream()
                .filter(d -> d.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
    }

    public Patient findPatientByEmail(String email) {
        return patients.values().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
    }

    public List<Appointment> getAppointmentsForPatient(Patient patient) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getPatient().getPatientId().equals(patient.getPatientId())) {
                result.add(a);
            }
        }
        return result;
    }

    public List<Appointment> getAppointmentsForDoctor(Doctor doctor) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointments) {
            if (a.getDoctor().getDoctorId().equals(doctor.getDoctorId())) {
                result.add(a);
            }
        }
        return result;
    }

    public boolean isDoctorIdTaken(String id) { return doctors.containsKey(id); }
    public boolean isPatientIdTaken(String id) { return patients.containsKey(id); }
    public boolean isEmailTaken(String email) {
        return findDoctorByEmail(email) != null || findPatientByEmail(email) != null;
    }

    public String generateDoctorId() {
        return "D" + String.format("%04d", doctors.size() + 1);
    }

    public String generatePatientId() {
        return "P" + String.format("%04d", patients.size() + 1);
    }
}
