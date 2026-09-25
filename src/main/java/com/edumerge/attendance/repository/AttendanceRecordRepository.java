package com.edumerge.attendance.repository;

import com.edumerge.attendance.model.AttendanceRecord;
import com.edumerge.attendance.model.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findBySessionId(Long sessionId);
    List<AttendanceRecord> findByStudentId(Long studentId);
    List<AttendanceRecord> findByStudentIdAndSessionCourseId(Long studentId, Long courseId);
    long countByStudentIdAndStatus(Long studentId, AttendanceStatus status);
}
