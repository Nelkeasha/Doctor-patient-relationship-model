# Doctor-Patient Relationship System 🏥

A simple Java-based system that models the relationship between doctors and patients using the core principles of Object-Oriented Programming (OOP) and robust Exception Handling.

---

##  Project Overview

This project simulates how a small clinic operates — patients register, doctors are assigned, appointments are booked, diagnoses are made, and prescriptions are issued. Everything is modeled using Java classes and objects, with proper error handling to ensure the system never crashes unexpectedly.

---

##  Purpose

The goal of this project is to demonstrate the four core OOP principles and Java Exception Handling:

| Principle | How It Is Applied |
|---|---|
| **Encapsulation** | Patient medical records and personal data are private, accessed only through getters |
| **Inheritance** | `Doctor` and `Patient` both inherit from the `Person` base class |
| **Polymorphism** | The `describe()` method behaves differently for `Doctor` vs `Patient` |
| **Abstraction** | `Person` is an abstract class — it cannot be instantiated directly |
| **Exception Handling** | Custom and unchecked exceptions handle all real-world error scenarios |

---

## 🗂️ Project Structure

```
Doctor-patient-relationship-model/
│
└── src/
      ├── models/
      │     ├── Person.java                  # Abstract base class
      │     ├── Doctor.java                  # Inherits from Person
      │     ├── Patient.java                 # Inherits from Person
      │     ├── Appointment.java             # Links a Doctor and a Patient
      │     ├── Prescription.java            # Medicine issued by a Doctor
      │     ├── MedicalRecord.java           # Patient's health history
      │     ├── InvalidDoctorException.java  # Custom exception for Doctor errors
      │     ├── InvalidPatientException.java # Custom exception for Patient errors
      │     ├── AppointmentException.java    # Custom exception for Appointment errors
      │     └── PrescriptionException.java   # Custom exception for Prescription errors
      └── Main.java                          # Entry point / demo
```

---

##  Classes and Responsibilities

### `Person` (Abstract)
- Base class for all humans in the system
- Fields: `name`, `age`, `email`, `gender`
- Abstract method: `describe()`

### `Doctor` (extends Person)
- Fields: `specialty`, `list of patients`
- Methods: `addPatient()`, `diagnose()`, `prescribe()`

### `Patient` (extends Person)
- Fields: `patientId`, `address`, `phoneNumber`, `medicalHistory`, `assignedDoctor`
- Methods: `setAssignedDoctor()`, `addMedicalRecord()`, `viewHistory()`

### `Appointment`
- Links a `Doctor` and a `Patient`
- Fields: `date`, `status`
- Methods: `confirmAppointment()`, `cancelAppointment()`, `display()`

### `Prescription`
- Created by a `Doctor` for a `Patient`
- Fields: `medicine`, `dosage`, `instructions`
- Methods: `display()`

### `MedicalRecord`
- Stores a patient's health history
- Fields: `date`, `diagnosis`, `prescription`, `doctorName`
- Methods: `display()`

---

##  Exception Handling

### Custom Exception Hierarchy

```
RuntimeException
    ├── InvalidPatientException   → invalid patient data or business rule violations
    ├── InvalidDoctorException    → invalid doctor data
    ├── AppointmentException      → appointment booking or cancellation issues
    └── PrescriptionException     → invalid prescription data
```

### Real-World Error Scenarios Handled

| Scenario | Exception Thrown |
|---|---|
| Doctor created with empty specialty | `InvalidDoctorException` |
| Patient created with negative age | `InvalidPatientException` |
| Patient created with empty ID | `InvalidPatientException` |
| Assigning a doctor to a patient who already has one | `InvalidPatientException` |
| Booking appointment with no assigned doctor | `AppointmentException` |
| Cancelling an already cancelled appointment | `AppointmentException` |
| Prescription with empty medicine name | `PrescriptionException` |
| Adding a null medical record | `InvalidPatientException` |

---

##  System Flow

```
1. Create a Doctor
2. Create a Patient
3. Doctor adds Patient to their list
4. Patient gets Doctor assigned
5. Appointment is booked between Doctor and Patient
6. Doctor diagnoses the Patient
7. Doctor writes a Prescription
8. Prescription is saved to Patient's MedicalRecord
9. Patient views their MedicalRecord
```

---

##  How to Run

1. Make sure you have **Java JDK** installed
2. Clone or download the project:
   ```bash
   git clone <your-repo-link>
   ```
3. Navigate to the project folder:
   ```bash
   cd Doctor-patient-relationship-model
   ```
4. Compile all files:
   ```bash
   javac src/models/*.java src/Main.java
   ```
5. Run the main class:
   ```bash
   java -cp src Main
   ```

---

##  Concepts Demonstrated

- Abstract classes and methods
- Inheritance with `extends` and `super()`
- Method overriding with `@Override`
- Encapsulation with `private` fields and getters
- Object relationships (one-to-many: Doctor → Patients)
- Use of `ArrayList` for managing collections of objects
- Custom exceptions extending `RuntimeException`
- `try-catch` blocks with multiple catch handlers
- Real-world business rule validation
- Meaningful error messages without system crashes

---

##  Author

Built as part of an Object-Oriented Programming (OOP) and Exception Handling assignment using Java.

