package com.edumerge.attendance.service;

import com.edumerge.attendance.dto.RecordAttendanceRequest;
import com.edumerge.attendance.dto.StudentAttendanceDto;
import com.edumerge.attendance.model.*;
import com.edumerge.attendance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceSessionRepository sessionRepository;
    
    @Autowired
    private AttendanceRecordRepository recordRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private FacultyRepository facultyRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private ClassSectionRepository classSectionRepository;

    @Override
    @Transactional
    public AttendanceSession recordAttendance(RecordAttendanceRequest request) {
        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        ClassSection classSection = classSectionRepository.findById(request.getClassSectionId())
                .orElseThrow(() -> new RuntimeException("Class Section not found"));
                
        AttendanceSession session = new AttendanceSession();
        session.setFaculty(faculty);
        session.setCourse(course);
        session.setClassSection(classSection);
        session.setSessionDate(request.getSessionDate());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        
        session = sessionRepository.save(session);
        
        for (StudentAttendanceDto dto : request.getAttendances()) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found: " + dto.getStudentId()));
                    
            AttendanceRecord record = new AttendanceRecord();
            record.setSession(session);
            record.setStudent(student);
            record.setStatus(dto.getStatus());
            record.setRemarks(dto.getRemarks());
            
            recordRepository.save(record);
        }
        
        return session;
    }

    @Override
    @Transactional
    public AttendanceRecord updateAttendanceRecord(Long recordId, AttendanceStatus newStatus, String remarks) {
        AttendanceRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        record.setStatus(newStatus);
        if (remarks != null) {
            record.setRemarks(remarks);
        }
        return recordRepository.save(record);
    }

    @Override
    public List<AttendanceRecord> getAttendanceForSession(Long sessionId) {
        return recordRepository.findBySessionId(sessionId);
    }

    @Override
    public List<AttendanceRecord> getAttendanceForStudent(Long studentId) {
        return recordRepository.findByStudentId(studentId);
    }

    @Override
    public Map<String, Object> getStudentAttendanceSummary(Long studentId) {
        List<AttendanceRecord> records = recordRepository.findByStudentId(studentId);
        long totalClasses = records.size();
        long presentClasses = records.stream()
                .filter(r -> r.getStatus() == AttendanceStatus.PRESENT || r.getStatus() == AttendanceStatus.LATE)
                .count();
                
        double percentage = totalClasses == 0 ? 0.0 : ((double) presentClasses / totalClasses) * 100.0;
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("studentId", studentId);
        summary.put("totalClasses", totalClasses);
        summary.put("presentClasses", presentClasses);
        summary.put("attendancePercentage", percentage);
        
        return summary;
    }

    @Override
    public List<Map<String, Object>> getStudentsWithLowAttendance(Long classSectionId, double thresholdPercentage) {
        List<Student> students = studentRepository.findByClassSectionId(classSectionId);
        List<Map<String, Object>> lowAttendanceStudents = new ArrayList<>();
        
        for (Student student : students) {
            Map<String, Object> summary = getStudentAttendanceSummary(student.getId());
            double percentage = (Double) summary.get("attendancePercentage");
            if (percentage < thresholdPercentage) {
                summary.put("studentName", student.getName());
                summary.put("rollNumber", student.getRollNumber());
                lowAttendanceStudents.add(summary);
            }
        }
        
        return lowAttendanceStudents;
    }
}




