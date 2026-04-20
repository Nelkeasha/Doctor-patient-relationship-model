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

    @Override
    void describe() {

    }
}
