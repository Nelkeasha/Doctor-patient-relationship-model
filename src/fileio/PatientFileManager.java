package fileio;

import models.Doctor;
import models.Patient;

import java.io.*;
import java.util.*;

public class PatientFileManager {

    private static final String FILE_PATH = "data/patients.txt";

    // Writes each patient as one pipe-separated line:
    // patientId|name|age|email|gender|address|phone|assignedDoctorId
    public static void save(List<Patient> patients) throws IOException {
        new File("data").mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Patient p : patients) {
                String assignedDocId = p.getAssignedDoctor() != null
                        ? p.getAssignedDoctor().getDoctorId() : "";
                writer.write(p.getPatientId() + "|" + p.getName() + "|" +
                        p.getAge() + "|" + p.getEmail() + "|" +
                        p.getGender() + "|" + p.getAddress() + "|" +
                        p.getPhone() + "|" + assignedDocId);
                writer.newLine();
            }
        }
        System.out.println("Patients saved to " + FILE_PATH);
    }

    // Reads patients.txt and reconstructs Patient objects.
    // Requires a doctorsById map to restore assigned doctor relationships.
    public static List<Patient> load(Map<String, Doctor> doctorsById) throws IOException {
        List<Patient> patients = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No patients file found at " + FILE_PATH);
            return patients;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 7) continue;

                Patient patient = new Patient(parts[0], parts[1],
                        Integer.parseInt(parts[2]), parts[3], parts[4], parts[5], parts[6]);

                if (parts.length >= 8 && !parts[7].isEmpty()) {
                    Doctor assigned = doctorsById.get(parts[7]);
                    if (assigned != null) {
                        patient.setAssignedDoctor(assigned);
                    }
                }
                patients.add(patient);
            }
        }
        System.out.println("Patients loaded from " + FILE_PATH);
        return patients;
    }
}
