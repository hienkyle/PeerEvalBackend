package edu.tcu.cs.peerevalbackend.system;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.instructor.InstructorRepository;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.SectionRepository;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.StudentRepository;
import edu.tcu.cs.peerevalbackend.team.Team;
import edu.tcu.cs.peerevalbackend.team.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBDDataInitializer implements CommandLineRunner {
    private TeamRepository teamRepository;

    private SectionRepository sectionRepository;

    private InstructorRepository instructorRepository;

    private StudentRepository studentRepository;

    public DBDDataInitializer(TeamRepository teamRepository, SectionRepository sectionRepository, InstructorRepository instructorRepository, StudentRepository studentRepository) {
        this.teamRepository = teamRepository;
        this.sectionRepository = sectionRepository;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // instructors setup
        Instructor instructor1 = new Instructor();
        instructor1.setInstructorId("1");
        instructor1.setName("alvie");
        instructor1.setAcademicYear(2024);
        instructor1.setStatus(ActiveStatus.IS_ACTIVE);
        instructor1.setTeams(null);

        Instructor instructor2 = new Instructor();
        instructor2.setInstructorId("2");
        instructor2.setName("ana");
        instructor2.setAcademicYear(2024);
        instructor2.setStatus(ActiveStatus.IS_ACTIVE);
        instructor2.setTeams(null);

        instructorRepository.save(instructor1);
        instructorRepository.save(instructor2);

        Student student1 = new Student();
        student1.setStudentId(1);
        student1.setFirstName("maribel");
        student1.setLastName("vargas");
        student1.setPassword("123456");

        studentRepository.save(student1);
    }
}
