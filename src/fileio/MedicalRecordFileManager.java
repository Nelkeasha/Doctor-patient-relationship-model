package fileio;

import models.*;

import java.io.*;
import java.util.*;

public class MedicalRecordFileManager {

    private static final String FILE_PATH = "data/medical_records.txt";

    // Writes all patients' medical records as pipe-separated lines:
    // patientId|date|diagnosis|doctorName|medicine|dosage|instructions|prescribingDoctorId
    public static void save(List<Patient> patients) throws IOException {
        new File("data").mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Patient patient : patients) {
                for (MedicalRecord record : patient.getMedicalHistory()) {
                    Prescription p = record.getPrescription();
                    String medicine     = p != null ? p.getMedicine()     : "";
                    String dosage       = p != null ? p.getDosage()       : "";
                    String instructions = p != null ? p.getInstructions() : "";
                    String docId        = p != null ? p.getDoctor().getDoctorId() : "";

                    writer.write(patient.getPatientId() + "|" +
                            record.getDate() + "|" + record.getDiagnosis() + "|" +
                            record.getDoctorName() + "|" + medicine + "|" +
                            dosage + "|" + instructions + "|" + docId);
                    writer.newLine();
                }
            }
        }
        System.out.println("Medical records saved to " + FILE_PATH);
    }

    // Reads medical_records.txt and returns records grouped by patientId.
    // Reconstructs Prescription objects using the provided lookup maps.
    public static Map<String, List<MedicalRecord>> loadAll(
            Map<String, Doctor> doctorsById,
            Map<String, Patient> patientsById) throws IOException {

        Map<String, List<MedicalRecord>> result = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No medical records file found at " + FILE_PATH);
            return result;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;

                String patientId  = parts[0];
                String date       = parts[1];
                String diagnosis  = parts[2];
                String doctorName = parts[3];

                Prescription prescription = null;
                if (parts.length >= 8 && !parts[4].isEmpty()) {
                    Doctor prescribingDoc = doctorsById.get(parts[7]);
                    Patient patient = patientsById.get(patientId);
                    if (prescribingDoc != null && patient != null) {
                        prescription = new Prescription(
                                prescribingDoc, patient, parts[4], parts[5], parts[6]);
                    }
                }

                MedicalRecord record = new MedicalRecord(date, diagnosis, prescription, doctorName);
                result.computeIfAbsent(patientId, k -> new ArrayList<>()).add(record);
            }
        }
        System.out.println("Medical records loaded from " + FILE_PATH);
        return result;
    }
}
