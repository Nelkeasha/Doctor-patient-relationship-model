# Doctor-Patient Relationship System 🏥

A simple Java-based system that models the relationship between doctors and patients using the core principles of Object-Oriented Programming (OOP).

---

## Project Overview

This project simulates how a small clinic operates — patients register, doctors are assigned, appointments are booked, diagnoses are made, and prescriptions are issued. Everything is modeled using Java classes and objects.

---

##  Purpose

The goal of this project is to demonstrate the four core OOP principles:

| Principle | How It Is Applied |
|---|---|
| **Encapsulation** | Patient medical records and personal data are private, accessed only through getters |
| **Inheritance** | `Doctor` and `Patient` both inherit from the `Person` base class |
| **Polymorphism** | The `describe()` method behaves differently for `Doctor` vs `Patient` |
| **Abstraction** | `Person` is an abstract class — it cannot be instantiated directly |

---

## 🗂️ Project Structure

```
DoctorPatientSystem/
│
├── Person.java          # Abstract base class
├── Doctor.java          # Inherits from Person
├── Patient.java         # Inherits from Person
├── Appointment.java     # Links a Doctor and a Patient
├── Prescription.java    # Medicine issued by a Doctor
├── MedicalRecord.java   # Patient's health history
└── Main.java            # Entry point / demo
```

---

## 🧩Classes and Responsibilities

### `Person` (Abstract)
- Base class for all humans in the system
- Fields: `name`, `age`, `phoneNumber`
- Abstract method: `describe()`

### `Doctor` (extends Person)
- Fields: `specialty`, `list of patients`
- Methods: `addPatient()`, `diagnose()`, `prescribe()`

### `Patient` (extends Person)
- Fields: `patientId`, `medicalHistory`, `assignedDoctor`
- Methods: `bookAppointment()`, `viewHistory()`

### `Appointment`
- Links a `Doctor` and a `Patient`
- Fields: `date`, `status`
- Methods: `confirm()`, `cancel()`

### `Prescription`
- Created by a `Doctor` for a `Patient`
- Fields: `medicine`, `dosage`, `instructions`
- Methods: `display()`

### `MedicalRecord`
- Stores a patient's health history
- Fields: `date`, `diagnosis`, `prescription`, `doctorName`
- Methods: `display()`

---

##  System Flow

```
1. Create a Doctor
2. Create a Patient
3. Doctor adds Patient to their list
4. Patient books an Appointment with the Doctor
5. Doctor diagnoses the Patient
6. Doctor writes a Prescription
7. Prescription is saved to Patient's MedicalRecord
8. Patient views their MedicalRecord
```

---

## 🚀 How to Run

1. Make sure you have **Java JDK** installed
2. Clone or download the project
3. Compile all files:
   ```bash
   javac *.java
   ```
4. Run the main class:
   ```bash
   java Main
   ```

---

## Concepts Demonstrated

- Abstract classes and methods
- Inheritance with `extends` and `super()`
- Method overriding with `@Override`
- Encapsulation with `private` fields and getters
- Object relationships (one-to-many: Doctor → Patients)
- Use of `ArrayList` for managing collections of objects

---

## Author

Built as part of an Object-Oriented Programming (OOP) assignment using Java.