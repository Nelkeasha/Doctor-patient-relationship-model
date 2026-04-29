package fileio;

import models.Appointment;
import models.Doctor;
import models.Patient;

import java.io.*;
import java.util.*;

public class AppointmentFileManager {

    private static final String FILE_PATH = "data/appointments.txt";

    // Writes each appointment as one pipe-separated line:
    // doctorId|patientId|date|status
    public static void save(List<Appointment> appointments) throws IOException {
        new File("data").mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Appointment a : appointments) {
                writer.write(a.getDoctor().getDoctorId() + "|" +
                        a.getPatient().getPatientId() + "|" +
                        a.getDate() + "|" + a.getStatus());
                writer.newLine();
            }
        }
        System.out.println("Appointments saved to " + FILE_PATH);
    }

    // Reads appointments.txt and reconstructs Appointment objects.
    // Requires doctorsById and patientsById maps — patients must already have
    // their assigned doctor set before this method is called.
    public static List<Appointment> load(Map<String, Doctor> doctorsById,
                                          Map<String, Patient> patientsById) throws IOException {
        List<Appointment> appointments = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No appointments file found at " + FILE_PATH);
            return appointments;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;

                Doctor doctor = doctorsById.get(parts[0]);
                Patient patient = patientsById.get(parts[1]);

                if (doctor != null && patient != null) {
                    Appointment appointment = new Appointment(doctor, patient, parts[2], parts[3]);
                    appointments.add(appointment);
                }
            }
        }
        System.out.println("Appointments loaded from " + FILE_PATH);
        return appointments;
    }
}
