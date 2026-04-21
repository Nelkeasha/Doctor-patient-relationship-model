import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person{

    private String specialty;
    private List<Patient> patients;

    public Doctor(String name, int age, String email, String gender,  String specialty) {
        super(name, age, email, gender);
        this.specialty = specialty;
        this.patients = new ArrayList<>();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void addPatient (Patient patient){
        patients.add(patient);
        System.out.println(patient.getName() + "added to Dr. " + getName() + "'s patient list");
    }

    public void diagnose(Patient patient, String diagnosis){
        System.out.println("Dr. " + getName() + " diagnosed " + patient.getName() + " with: " + diagnosis);
    }

    public Prescription prescribe(Patient patient, String medicine, String dosage, String instructions) {
        Prescription prescription = new Prescription(this, patient, medicine, dosage, instructions);
        System.out.println("Dr. " + getName() + " prescribed " + medicine + " to " + patient.getName());
        return prescription;
    }

    @Override
    void describe() {
        System.out.println("Doctor: " + getName() + " | specialty: " + getSpecialty() + " | email: " + getEmail() );
    }
}
