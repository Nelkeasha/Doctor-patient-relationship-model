package fileio;

import exceptions.InvalidDoctorException;
import models.Doctor;

import java.io.*;
import java.util.*;

public class DoctorFileManager {

    private static final String FILE_PATH = "data/doctors.txt";

    // Writes each doctor as one pipe-separated line:
    // doctorId|name|age|email|gender|specialty1,specialty2,...
    public static void save(List<Doctor> doctors) throws IOException {
        new File("data").mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Doctor d : doctors) {
                String specialties = String.join(",", d.getSpecialty());
                writer.write(d.getDoctorId() + "|" + d.getName() + "|" +
                        d.getAge() + "|" + d.getEmail() + "|" +
                        d.getGender() + "|" + specialties);
                writer.newLine();
            }
        }
        System.out.println("Doctors saved to " + FILE_PATH);
    }

    // Reads doctors.txt and reconstructs Doctor objects with their specialties.
    public static List<Doctor> load() throws IOException {
        List<Doctor> doctors = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No doctors file found at " + FILE_PATH);
            return doctors;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 5) continue;

                Doctor doctor = new Doctor(parts[0], parts[1],
                        Integer.parseInt(parts[2]), parts[3], parts[4]);

                if (parts.length >= 6 && !parts[5].isEmpty()) {
                    for (String spec : parts[5].split(",")) {
                        if (!spec.trim().isEmpty()) {
                            doctor.addSpecialty(spec.trim());
                        }
                    }
                }
                doctors.add(doctor);
            }
        }
        System.out.println("Doctors loaded from " + FILE_PATH);
        return doctors;
    }
}
