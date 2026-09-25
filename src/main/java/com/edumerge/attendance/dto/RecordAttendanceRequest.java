package com.edumerge.attendance.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class RecordAttendanceRequest {
    private Long facultyId;
    private Long courseId;
    private Long classSectionId;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    
    private List<StudentAttendanceDto> attendances;
}







