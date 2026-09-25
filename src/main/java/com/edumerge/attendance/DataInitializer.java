package com.edumerge.attendance;

import com.edumerge.attendance.model.*;
import com.edumerge.attendance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private DepartmentRepository deptRepo;
    @Autowired private CourseRepository courseRepo;
    @Autowired private ClassSectionRepository classSectionRepo;
    @Autowired private FacultyRepository facultyRepo;
    @Autowired private StudentRepository studentRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (deptRepo.count() > 0) return;

        Department cse = new Department(null, "Computer Science");
        deptRepo.save(cse);

        Course java = new Course(null, "Java Programming", "CS101", cse);
        courseRepo.save(java);

        ClassSection cseA = new ClassSection(null, "CSE-A", cse);
        classSectionRepo.save(cseA);

        Faculty profSmith = new Faculty(null, "Dr. Smith", "smith@edumerge.com", cse);
        facultyRepo.save(profSmith);

        Student s1 = new Student(null, "Alice Johnson", "ROLL001", "alice@edumerge.com", cseA);
        Student s2 = new Student(null, "Bob Williams", "ROLL002", "bob@edumerge.com", cseA);
        studentRepo.save(s1);
        studentRepo.save(s2);

        System.out.println("Sample Data Initialized!");
    }
}



