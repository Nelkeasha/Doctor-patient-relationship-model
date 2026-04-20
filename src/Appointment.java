public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String date;
    private String status;

    public Appointment(Doctor doctor, Patient patient, String date, String status) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void confirmAppointment() {
        System.out.println("Appointment confirmed for " + patient.getName() +
                " with Dr. " + doctor.getName() +
                " on " + date);
    }
    public void cancelAppointment() {
        System.out.println("Appointment for " + patient.getName() +
                " with Dr. " + doctor.getName() +
                " has been cancelled.");
    }

    public void display() {
        System.out.println("--- Appointment Details ---");
        System.out.println("Patient  : " + patient.getName());
        System.out.println("Doctor   : Dr. " + doctor.getName());
        System.out.println("Date     : " + date);
        System.out.println("Status   : " + status);
    }
}
