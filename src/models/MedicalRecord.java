package models;

public class MedicalRecord {
    private String date;
    private String diagnosis;
    private Prescription prescription;
    private String doctorName;


    public MedicalRecord(String date, String diagnosis,
                         Prescription prescription, String doctorName) {
        this.date = date;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.doctorName = doctorName;
    }

    public String getDate() { return date; }
    public String getDiagnosis() { return diagnosis; }
    public Prescription getPrescription() { return prescription; }


    public void display() {
        System.out.println("--- Medical Record ---");
        System.out.println("Date      : " + date);
        System.out.println("models.Doctor    : Dr. " + doctorName);
        System.out.println("Diagnosis : " + diagnosis);
        if (prescription != null) {
            prescription.display();
        } else {
            System.out.println("No prescription issued.");
        }
    }
}