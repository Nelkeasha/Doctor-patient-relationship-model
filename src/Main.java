import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        Doctor doctor = new Doctor("Alice", 35, "alice@doc.com", "female", "cardiology");


        Patient patient = new Patient("P001", "Bob", 25, "bob@patient.com", "male", "kigali", "0788831221");

        doctor.addPatient(patient);

        patient.setAssignedDoctor(doctor);


        doctor.describe();
        patient.describe();


        Appointment appointment = new Appointment(doctor, patient, "2026-04-25", "scheduled");
        appointment.confirmAppointment();
        appointment.display();

        doctor.diagnose(patient, "Hypertension");


        Prescription prescription = doctor.prescribe(
                patient,
                "Amlodipine",
                "5mg",
                "Once a day after breakfast"
        );

        // 9. Create a Medical Record and add to patient history
        MedicalRecord record = new MedicalRecord(
                "2026-04-25",
                "Hypertension",
                prescription,
                doctor.getName()
        );
        patient.addMedicalRecord(record);

        // 10. Patient views their medical history
        patient.viewHistory();
    }
}