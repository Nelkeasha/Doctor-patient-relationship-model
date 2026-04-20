import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person{

    private String specialty;
    private List<Patient> patients;

    public Doctor(String name, int age, String email, String gender, String address, String phone, String specialty) {
        super(name, age, email, gender, address, phone);
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

    @Override
    void describe() {
        System.out.println("Doctor: " + getName() + " | specialty: " + getSpecialty() + " | email: " + getEmail() + " | Address: " + getAddress() + " | Phone: " + getPhone());
    }
}
