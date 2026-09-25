package com.edumerge.attendance.controller;

import com.edumerge.attendance.dto.RecordAttendanceRequest;
import com.edumerge.attendance.model.AttendanceRecord;
import com.edumerge.attendance.model.AttendanceSession;
import com.edumerge.attendance.model.AttendanceStatus;
import com.edumerge.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // 1. Attendance Recording
    @PostMapping("/record")
    public ResponseEntity<AttendanceSession> recordAttendance(@RequestBody RecordAttendanceRequest request) {
        AttendanceSession session = attendanceService.recordAttendance(request);
        return ResponseEntity.ok(session);
    }

    // 2. Attendance Corrections
    @PatchMapping("/records/{recordId}")
    public ResponseEntity<AttendanceRecord> updateRecord(
            @PathVariable Long recordId,
            @RequestParam AttendanceStatus status,
            @RequestParam(required = false) String remarks) {
        AttendanceRecord updated = attendanceService.updateAttendanceRecord(recordId, status, remarks);
        return ResponseEntity.ok(updated);
    }

    // 3. Attendance Review (for a specific session)
    @GetMapping("/sessions/{sessionId}/records")
    public ResponseEntity<List<AttendanceRecord>> getSessionRecords(@PathVariable Long sessionId) {
        return ResponseEntity.ok(attendanceService.getAttendanceForSession(sessionId));
    }

    // 4. Attendance History (for a specific student)
    @GetMapping("/students/{studentId}/history")
    public ResponseEntity<List<AttendanceRecord>> getStudentHistory(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceForStudent(studentId));
    }
    
    // Summary for a single student
    @GetMapping("/students/{studentId}/summary")
    public ResponseEntity<Map<String, Object>> getStudentSummary(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getStudentAttendanceSummary(studentId));
    }

    // 5. Identification of students with low attendance
    @GetMapping("/low-attendance")
    public ResponseEntity<List<Map<String, Object>>> getLowAttendanceStudents(
            @RequestParam Long classSectionId,
            @RequestParam(defaultValue = "75.0") double threshold) {
        return ResponseEntity.ok(attendanceService.getStudentsWithLowAttendance(classSectionId, threshold));
    }
}
