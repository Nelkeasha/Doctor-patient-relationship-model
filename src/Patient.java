import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {

    private String patientId;
    private String phone;
    private String address;
    private Doctor assignedDoctor;
    private List<MedicalRecord> medicalHistory;

    public Patient(String patientId, String name, int age, String email, String gender, String address, String phone) {
        super(name, age, email, gender);
        this.phone = phone;
        this.address = address;
        this.patientId =  patientId;
        this.assignedDoctor = null;
        this.medicalHistory = new ArrayList<>();

    }

    public String getPatientId() {
        return patientId;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public  void setAssignedDoctor(Doctor doctor) {
        this.assignedDoctor = doctor;
        System.out.println("Dr. " + doctor.getName() + " has been assigned to " + getName());
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalHistory.add(record);
    }

    public void viewHistory() {
        if(medicalHistory.isEmpty()) {
            System.out.println("No medical records found");
        }  else {
            for(MedicalRecord record : medicalHistory) {
                record.display();
            }
        }
    }

    @Override
    void describe() {
        System.out.println("Patient: " + getName() + " | ID: " + getPatientId() + "email:" + getEmail() + "gender:" + getGender() + "address:" + getAddress() + "phone:" + getPhone());
    }
}
