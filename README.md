# Medicare — Doctor-Patient Relationship Platform

A full-stack Java desktop application that models a modern healthcare clinic. The backend covers OOP fundamentals, Java Collections, and File I/O for data persistence. The frontend is a complete JavaFX GUI — the **Medicare** healthcare platform — with a landing page, login/register flow, patient dashboard, doctor dashboard, and appointment booking.

---

## Project Overview

Patients register and get assigned a doctor. Doctors manage their patient list. Appointments are booked, diagnoses are made, prescriptions are issued, and all data is saved to flat files and fully reconstructed on restart. The JavaFX UI is themed to match a modern healthcare design with gradient hero sections, service cards, doctor profile cards, and responsive dashboards.

---

## Progress

| Phase | Topic | Status |
|---|---|---|
| Phase 1 | OOP + Exception Handling | Done |
| Phase 2 | Java Collections (List, Set, Map) | Done |
| Phase 3 | File I/O (data persistence) | Done |
| Phase 4 | JavaFX UI — Medicare Platform | Done |

---

## How to Run

### Prerequisites
- Java JDK 21
- Maven (`mvn`) or Maven Daemon (`mvnd`) on your PATH
- Ensure `JAVA_HOME` points to the JDK root (e.g. `C:\Program Files\Java\jdk-21`), **not** to the `bin` subfolder

### Run the JavaFX app
```bash
mvn clean javafx:run
```

The `data/` directory is created automatically on first run and persists all clinic data between sessions.

---

## Phase 4 — JavaFX UI (Medicare Platform)

A complete multi-screen desktop application built with JavaFX 21, styled with a custom CSS theme.

### Screens

| Screen | Description |
|---|---|
| **Landing Page** | Hero section, 8 service cards, doctor profile cards, stats, CTA |
| **Login / Register** | Split layout with role toggle (Patient / Doctor) and mode toggle (Sign In / Register) |
| **Patient Dashboard** | Stat cards, appointment table with cancel action, medical records table |
| **Doctor Dashboard** | Stat cards, patient table, appointment table with confirm action, add-patient dialog |
| **Appointment Booking** | Date picker, appointment type, notes, confirmation alert |

### UI Architecture

```
HelloApplication.java
    └── NavigationManager (singleton)   — switches scenes, injects CSS
    └── DataManager (singleton)         — wraps FileManagers, holds session state

Screens:
    fxml/home.fxml              ← HomeController.java
    fxml/login.fxml             ← LoginController.java
    fxml/patient-dashboard.fxml ← PatientDashboardController.java
    fxml/doctor-dashboard.fxml  ← DoctorDashboardController.java
    fxml/appointment.fxml       ← AppointmentController.java

Styles:
    css/medicare.css            — purple gradient theme (#7C3AED primary)
```

### App Flow

```
Landing Page
    └── "Get Started" / "Contact Us"
            └── Login / Register (Patient or Doctor)
                    ├── Patient → Patient Dashboard → Book Appointment
                    └── Doctor  → Doctor Dashboard  → Add Patient (dialog)
```

### Design Highlights
- **Hero gradient**: `#5B21B6 → #7C3AED → #A855F7 → #EC4899`
- **Service cards**: 4×2 grid — Wellness, Mental Health, Diagnostics, Pediatrics, Telehealth, etc.
- **Doctor cards**: circular avatar with initials; featured card in purple gradient
- **Stat row**: 100% Certified · 25M+ Users · 99% Satisfaction
- **Dashboards**: sidebar navigation + stat cards + styled `TableView` with badge-coloured status cells
- **Appointment constraint**: a patient must have an assigned doctor before booking — enforced in both UI and model layer

---

## Project Structure

```
doctor-patient-relationship-model_frontend/
│
├── data/                                      # Generated at runtime
│     ├── doctors.txt
│     ├── patients.txt
│     ├── appointments.txt
│     └── medical_records.txt
│
└── src/main/
      ├── java/com/nelly/doctorpatientrelationshipmodel_frontend/
      │     │
      │     ├── HelloApplication.java          # JavaFX entry point (1280×820)
      │     │
      │     ├── controllers/
      │     │     ├── HomeController.java
      │     │     ├── LoginController.java
      │     │     ├── PatientDashboardController.java
      │     │     ├── DoctorDashboardController.java
      │     │     └── AppointmentController.java
      │     │
      │     ├── utils/
      │     │     ├── NavigationManager.java   # Singleton — scene switching + CSS
      │     │     └── DataManager.java         # Singleton — data access + session
      │     │
      │     ├── models/
      │     │     ├── Person.java              # Abstract base class
      │     │     ├── Doctor.java              # Set<specialties>, List<patients>
      │     │     ├── Patient.java             # List<records>, List<appointments>
      │     │     ├── Appointment.java
      │     │     ├── Prescription.java
      │     │     └── MedicalRecord.java
      │     │
      │     ├── fileio/
      │     │     ├── DoctorFileManager.java
      │     │     ├── PatientFileManager.java
      │     │     ├── AppointmentFileManager.java
      │     │     └── MedicalRecordFileManager.java
      │     │
      │     └── exceptions/
      │           ├── InvalidDoctorException.java
      │           ├── InvalidPatientException.java
      │           ├── AppointmentException.java
      │           └── PrescriptionException.java
      │
      └── resources/com/nelly/doctorpatientrelationshipmodel_frontend/
            ├── css/
            │     └── medicare.css
            └── fxml/
                  ├── home.fxml
                  ├── login.fxml
                  ├── patient-dashboard.fxml
                  ├── doctor-dashboard.fxml
                  └── appointment.fxml
```

---

## Backend — Phase 1: OOP & Exception Handling

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
| Assigning a second doctor to a patient | `InvalidPatientException` |
| Booking appointment with no assigned doctor | `AppointmentException` |
| Cancelling an already-cancelled appointment | `AppointmentException` |
| Prescription with empty medicine name | `PrescriptionException` |
| Adding a null medical record | `InvalidPatientException` |

---

## Backend — Phase 2: Java Collections

| Collection | Where Used | Purpose |
|---|---|---|
| `List<Patient>` | `Doctor` | Ordered list of a doctor's patients |
| `Set<String>` | `Doctor` | Unique specialties — duplicates auto-rejected |
| `List<MedicalRecord>` | `Patient` | Patient's health history |
| `List<Appointment>` | `Patient` | Patient's appointments |
| `Map<String, Doctor>` | `DataManager` | Hospital registry keyed by doctor ID |
| `Map<String, Patient>` | `DataManager` | Patient registry keyed by patient ID |

---

## Backend — Phase 3: File I/O

### File Format

| File | Fields |
|---|---|
| `data/doctors.txt` | `doctorId\|name\|age\|email\|gender\|specialty1,specialty2,...` |
| `data/patients.txt` | `patientId\|name\|age\|email\|gender\|address\|phone\|assignedDoctorId` |
| `data/appointments.txt` | `doctorId\|patientId\|date\|status` |
| `data/medical_records.txt` | `patientId\|date\|diagnosis\|doctorName\|medicine\|dosage\|instructions\|docId` |

### Load Order

Data is loaded in dependency order so all object references can be restored:

```
1. Load doctors        → build Map<doctorId, Doctor>
2. Load patients       → restore assigned-doctor links
3. Load appointments   → reconstruct Doctor + Patient references
4. Load medical records → reconstruct Prescription objects
```

---

## System Flow (Backend Logic)

```
1. Create a Doctor (with ID and specialties)
2. Create a Patient
3. Doctor adds Patient to their list
4. Patient gets Doctor assigned (one-time only)
5. Appointment is booked between Doctor and Patient
6. Doctor diagnoses the Patient
7. Doctor writes a Prescription
8. Prescription is saved into the Patient's MedicalRecord
9. Patient views their MedicalRecord
10. All data is written to files and reconstructed on next launch
```

---

## Key Dependencies (pom.xml)

| Dependency | Purpose |
|---|---|
| `javafx-controls` 21 | UI controls |
| `javafx-fxml` 21 | FXML scene loading |
| `controlsfx` 11.2.1 | Extended UI controls |
| `bootstrapfx-core` 0.4.0 | Bootstrap-style CSS utilities |

---

## Concepts Demonstrated

**OOP:** Abstract classes, inheritance, polymorphism, encapsulation, custom exceptions

**Collections:** `List`, `Set`, `Map`, streams with `.filter()` and `.findFirst()`

**File I/O:** `BufferedWriter`, `BufferedReader`, pipe-delimited persistence, dependency-ordered reconstruction

**JavaFX:** FXML layouts, CSS styling, `TableView` with custom cell factories, `DatePicker`, `ComboBox`, `Dialog`, `Alert`, scene switching, singleton navigation manager

---

## Author

Built as part of an Object-Oriented Programming course using Java 21, covering OOP, Exception Handling, Java Collections, File I/O, and JavaFX GUI development.
