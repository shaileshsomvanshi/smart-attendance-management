package com.edumerge.attendance.dto;

import com.edumerge.attendance.model.AttendanceStatus;
import lombok.Data;

@Data
public class StudentAttendanceDto {
    private Long studentId;
    private AttendanceStatus status;
    private String remarks;
}
