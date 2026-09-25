# Smart Attendance Management System


This is a Spring Boot REST API application built for the Pre-Drive Product Engineering Assignment (Option 1). It provides a complete backend solution for tracking attendance, correcting records, reviewing history, and identifying students with low attendance.

## Prerequisites
- **Java 17** (or higher)
- **Maven**
- An IDE (IntelliJ IDEA, Eclipse, etc.)

## Setup & Running the Application
1. **Import Project**: Open the folder in your IDE as a Maven project. Let the IDE download the dependencies automatically.
2. **Database Configuration**:
   - By default, the application runs on an **in-memory H2 database**. No setup is required. 
   - To use PostgreSQL or MySQL, uncomment the relevant blocks in `src/main/resources/application.properties` and add your credentials.
3. **Run**: Execute the `SmartAttendanceApplication.java` main class.
4. **Sample Data**: Upon startup, `DataInitializer.java` automatically seeds the database with Departments, Faculty, Courses, Class Sections, and Students so you can begin testing immediately.

---

## Interactive API Documentation (Swagger UI)
You can visually test all the APIs without Postman using the built-in Swagger interface.
1. Run the application.
2. Visit **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)** in your browser.

---

## API Endpoints Documentation

### 1. Record Attendance (Batch)
Records attendance for an entire class session at once.

- **URL**: `/api/attendance/record`
- **Method**: `POST`
- **Request Body**:
```json
{
  "facultyId": 1,
  "courseId": 1,
  "classSectionId": 1,
  "sessionDate": "2026-09-25",
  "startTime": "09:00:00",
  "endTime": "10:00:00",
  "attendances": [
    {
      "studentId": 1,
      "status": "PRESENT",
      "remarks": "On time"
    },
    {
      "studentId": 2,
      "status": "ABSENT",
      "remarks": "Medical leave"
    }
  ]
}
```
- **Success Response (200 OK)**:
Returns the created `AttendanceSession` object with its generated ID.

---

### 2. Update/Correct Attendance Record
Allows faculty or admins to correct a specific student's attendance record (e.g., changing from ABSENT to PRESENT).

- **URL**: `/api/attendance/records/{recordId}?status=PRESENT&remarks=Corrected`
- **Method**: `PATCH`
- **Parameters**: 
  - `recordId` (Path Variable): The ID of the specific attendance record.
  - `status` (Query Param): `PRESENT`, `ABSENT`, `LATE`, or `EXCUSED`.
  - `remarks` (Query Param, Optional): Reason for correction.
- **Success Response (200 OK)**:
```json
{
  "id": 1,
  "session": { ... },
  "student": { ... },
  "status": "PRESENT",
  "remarks": "Corrected"
}
```

---

### 3. Review Attendance by Session
Fetch all student attendance records for a specific class session.

- **URL**: `/api/attendance/sessions/{sessionId}/records`
- **Method**: `GET`
- **Success Response (200 OK)**:
```json
[
  {
    "id": 1,
    "status": "PRESENT",
    "remarks": "On time",
    "student": {
      "id": 1,
      "name": "Alice Johnson",
      "rollNumber": "ROLL001"
    }
  }
]
```

---

### 4. Student Attendance History
Fetch the complete class attendance history across all subjects for a specific student.

- **URL**: `/api/attendance/students/{studentId}/history`
- **Method**: `GET`
- **Success Response (200 OK)**: Array of all `AttendanceRecord` objects belonging to the student.

---

### 5. Single Student Attendance Summary
Calculates a specific student's total attendance percentage.

- **URL**: `/api/attendance/students/{studentId}/summary`
- **Method**: `GET`
- **Success Response (200 OK)**:
```json
{
  "studentId": 1,
  "presentClasses": 9,
  "totalClasses": 10,
  "attendancePercentage": 90.0
}
```

---

### 6. Identify Students with Low Attendance
Dynamically flags students in a specific class section who fall below a certain attendance percentage threshold (default is 75%).

- **URL**: `/api/attendance/low-attendance?classSectionId=1&threshold=75.0`
- **Method**: `GET`
- **Success Response (200 OK)**:
```json
[
  {
    "studentId": 2,
    "studentName": "Bob Williams",
    "rollNumber": "ROLL002",
    "presentClasses": 2,
    "totalClasses": 5,
    "attendancePercentage": 40.0
  }
]
```

---
## Tooling & Verification
* Built using Java 17, Spring Boot 3.2.4.
* Dependencies: Spring Data JPA, Spring Web, PostgreSQL/MySQL Drivers, Lombok, Springdoc OpenAPI.
* Verified via internal database unit modeling and manual endpoint testing.
