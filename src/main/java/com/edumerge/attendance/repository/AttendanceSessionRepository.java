package com.edumerge.attendance.repository;

import com.edumerge.attendance.model.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {
    List<AttendanceSession> findByClassSectionIdAndCourseId(Long classSectionId, Long courseId);
    List<AttendanceSession> findByFacultyIdAndSessionDate(Long facultyId, LocalDate date);
}





