# Doctor-Patient Relationship System

A Java-based system that models the relationship between doctors and patients, built progressively across three phases: OOP fundamentals, Java Collections, and File I/O for data persistence.

---

## Project Overview

This project simulates how a small clinic operates — patients register, doctors are assigned, appointments are booked, diagnoses are made, prescriptions are issued, and all data is saved to and restored from files. Each phase builds on the previous one without breaking existing functionality.

---

## Progress

| Phase | Topic | Status |
|---|---|---|
| Phase 1 | OOP + Exception Handling | Done |
| Phase 2 | Java Collections (List, Set, Map) | Done |
| Phase 3 | File I/O (data persistence) | Done |

---

## Phase 1 — OOP & Exception Handling

Established the class hierarchy and all business rules using the four OOP pillars.

| Principle | How It Is Applied |
|---|---|
| **Encapsulation** | All fields are private, accessed only through getters/setters |
| **Inheritance** | `Doctor` and `Patient` both extend the abstract `Person` class |
| **Polymorphism** | `describe()` behaves differently for `Doctor` vs `Patient` |
| **Abstraction** | `Person` is abstract and cannot be instantiated directly |
| **Exception Handling** | Four custom unchecked exceptions cover all error scenarios |

### Custom Exception Hierarchy

```
RuntimeException
    ├── InvalidPatientException   → invalid patient data or business rule violations
    ├── InvalidDoctorException    → invalid doctor data or ID
    ├── AppointmentException      → appointment booking or cancellation issues
    └── PrescriptionException     → invalid prescription data
```

### Error Scenarios Handled

| Scenario | Exception |
|---|---|
| Doctor created with empty ID | `InvalidDoctorException` |
| Patient created with negative age | `InvalidPatientException` |
| Patient created with empty ID | `InvalidPatientException` |
| Assigning a doctor to a patient who already has one | `InvalidPatientException` |
| Booking appointment with no assigned doctor | `AppointmentException` |
| Cancelling an already cancelled appointment | `AppointmentException` |
| Prescription with empty medicine name | `PrescriptionException` |
| Adding a null medical record | `InvalidPatientException` |

---

## Phase 2 — Java Collections

Replaced bare arrays with proper Java collection types throughout the models.

| Collection | Where Used | Purpose |
|---|---|---|
| `List<Patient>` | `Doctor` | Ordered list of a doctor's patients (add, get by index, remove) |
| `Set<String>` | `Doctor` | Unique specialties — duplicates automatically rejected |
| `List<MedicalRecord>` | `Patient` | Patient's health history (add, retrieve, remove) |
| `List<Appointment>` | `Patient` | Patient's appointments (add, retrieve, remove) |
| `Map<String, Doctor>` | `Main` | Hospital registry keyed by doctor ID |
| `Map<String, List<Patient>>` | `Main` | Doctor-to-patient registry |

---

## Phase 3 — File I/O

Added a `fileio` package that persists all data to plain text files using pipe-separated (`|`) values and reads it back to fully reconstruct the object graph.

### File Format

| File | Fields |
|---|---|
| `data/doctors.txt` | `doctorId\|name\|age\|email\|gender\|specialty1,specialty2,...` |
| `data/patients.txt` | `patientId\|name\|age\|email\|gender\|address\|phone\|assignedDoctorId` |
| `data/appointments.txt` | `doctorId\|patientId\|date\|status` |
| `data/medical_records.txt` | `patientId\|date\|diagnosis\|doctorName\|medicine\|dosage\|instructions\|docId` |

### Load Order

Because patients reference doctors and appointments reference patients, data must be loaded in dependency order:

```
1. Load doctors       → build Map<doctorId, Doctor>
2. Load patients      → restore assigned-doctor links using the doctors map
3. Load appointments  → reconstruct Doctor + Patient references
4. Load medical records → reconstruct Prescription objects with Doctor + Patient
```

---

## Project Structure

```
Doctor-patient-relationship-model/
│
├── data/                              # Generated at runtime
│     ├── doctors.txt
│     ├── patients.txt
│     ├── appointments.txt
│     └── medical_records.txt
│
└── src/
      ├── exceptions/
      │     ├── InvalidDoctorException.java
      │     ├── InvalidPatientException.java
      │     ├── AppointmentException.java
      │     └── PrescriptionException.java
      │
      ├── models/
      │     ├── Person.java            # Abstract base class
      │     ├── Doctor.java            # Extends Person; holds Set<specialties>, List<patients>
      │     ├── Patient.java           # Extends Person; holds List<records>, List<appointments>
      │     ├── Appointment.java       # Links Doctor and Patient with date/status
      │     ├── Prescription.java      # Medicine issued by a Doctor
      │     └── MedicalRecord.java     # Single health history entry for a Patient
      │
      ├── fileio/
      │     ├── DoctorFileManager.java         # save/load doctors.txt
      │     ├── PatientFileManager.java        # save/load patients.txt
      │     ├── AppointmentFileManager.java    # save/load appointments.txt
      │     └── MedicalRecordFileManager.java  # save/load medical_records.txt
      │
      └── Main.java                    # Entry point; runs all phase demos
```

---

## Classes and Responsibilities

### `Person` (Abstract)
- Base class for all persons in the system
- Fields: `name`, `age`, `email`, `gender`
- Abstract method: `describe()`

### `Doctor` (extends Person)
- Fields: `doctorId`, `Set<String> specialties`, `List<Patient> patients`
- Methods: `addPatient()`, `removePatient()`, `addSpecialty()`, `removeSpecialty()`, `diagnose()`, `prescribe()`

### `Patient` (extends Person)
- Fields: `patientId`, `address`, `phone`, `assignedDoctor`, `List<MedicalRecord>`, `List<Appointment>`
- Methods: `setAssignedDoctor()`, `addMedicalRecord()`, `viewHistory()`, `addAppointment()`, `viewAppointments()`

### `Appointment`
- Links a `Doctor` and a `Patient`
- Fields: `date`, `status`
- Methods: `confirmAppointment()`, `cancelAppointment()`, `display()`

### `Prescription`
- Created by a `Doctor` for a `Patient`
- Fields: `medicine`, `dosage`, `instructions`
- Methods: `display()`

### `MedicalRecord`
- Stores a single health history entry for a patient
- Fields: `date`, `diagnosis`, `prescription`, `doctorName`
- Methods: `display()`

---

## System Flow

```
1. Create a Doctor (with ID and specialties)
2. Create a Patient
3. Doctor adds Patient to their list
4. Patient gets Doctor assigned
5. Appointment is booked between Doctor and Patient
6. Doctor diagnoses the Patient
7. Doctor writes a Prescription
8. Prescription is saved into the Patient's MedicalRecord
9. Patient views their MedicalRecord
10. All data is written to files
11. Data is read back from files and fully reconstructed
```

---

## How to Run

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
   javac -cp src src/exceptions/*.java src/models/*.java src/fileio/*.java src/Main.java -d out
   ```
5. Run the main class:
   ```bash
   java -cp out Main
   ```

The `data/` directory is created automatically on first run.

---

## Concepts Demonstrated

- Abstract classes and the `abstract` keyword
- Inheritance with `extends` and `super()`
- Method overriding with `@Override`
- Encapsulation with `private` fields and public getters
- Custom exceptions extending `RuntimeException`
- `try-catch` with multiple exception types
- `List` — ordered, index-based collection with duplicates
- `Set` — unordered collection that rejects duplicates automatically
- `Map` — key-value lookup structure (registry pattern)
- File writing with `FileWriter` and `BufferedWriter`
- File reading with `FileReader` and `BufferedReader`
- Object graph reconstruction from flat file data
- Dependency-ordered loading to restore object relationships

---

## Author

Built as part of an Object-Oriented Programming assignment using Java, covering OOP, Exception Handling, Java Collections, and File I/O.
