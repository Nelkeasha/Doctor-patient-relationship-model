class Prescription {
    private Doctor doctor;
    private Patient patient;
    private String medicine;
    private String dosage;
    private String instructions;

    public Prescription(Doctor doctor, Patient patient,
                        String medicine, String dosage, String instructions) {
        this.doctor = doctor;
        this.patient = patient;
        this.medicine = medicine;
        this.dosage = dosage;
        this.instructions = instructions;
    }


    public String getMedicine() { return medicine; }
    public String getDosage() { return dosage; }
    public String getInstructions() { return instructions; }

    public void display() {
        System.out.println("--- Prescription ---");
        System.out.println("Patient      : " + patient.getName());
        System.out.println("Doctor       : Dr. " + doctor.getName());
        System.out.println("Medicine     : " + medicine);
        System.out.println("Dosage       : " + dosage);
        System.out.println("Instructions : " + instructions);
    }
}