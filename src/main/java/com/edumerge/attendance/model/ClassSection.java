package com.edumerge.attendance.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "class_sections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassSection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name; // e.g., CS-A, IT-B
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}




