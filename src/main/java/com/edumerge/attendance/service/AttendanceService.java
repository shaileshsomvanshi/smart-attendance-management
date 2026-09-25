package com.edumerge.attendance.service;

import com.edumerge.attendance.dto.RecordAttendanceRequest;
import com.edumerge.attendance.model.AttendanceRecord;
import com.edumerge.attendance.model.AttendanceSession;
import com.edumerge.attendance.model.AttendanceStatus;

import java.util.List;
import java.util.Map;

public interface AttendanceService {
    
    AttendanceSession recordAttendance(RecordAttendanceRequest request);
    
    AttendanceRecord updateAttendanceRecord(Long recordId, AttendanceStatus newStatus, String remarks);
    
    List<AttendanceRecord> getAttendanceForSession(Long sessionId);
    
    List<AttendanceRecord> getAttendanceForStudent(Long studentId);
    
    Map<String, Object> getStudentAttendanceSummary(Long studentId);
    
    List<Map<String, Object>> getStudentsWithLowAttendance(Long classSectionId, double thresholdPercentage);
}



