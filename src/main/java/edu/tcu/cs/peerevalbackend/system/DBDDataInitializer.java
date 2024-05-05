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
    private final StudentRepository studentRepository;

    private TeamRepository teamRepository;

    private SectionRepository sectionRepository;

    private InstructorRepository instructorRepository;

    public DBDDataInitializer(TeamRepository teamRepository, SectionRepository sectionRepository, InstructorRepository instructorRepository, StudentRepository studentRepository) {
        this.teamRepository = teamRepository;
        this.sectionRepository = sectionRepository;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Instructors setup
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

    }
}
