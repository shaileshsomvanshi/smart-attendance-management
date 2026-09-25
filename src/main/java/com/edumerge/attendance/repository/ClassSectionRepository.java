package com.edumerge.attendance.repository;

import com.edumerge.attendance.model.ClassSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassSectionRepository extends JpaRepository<ClassSection, Long> {
}
