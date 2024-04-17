package edu.tcu.cs.peerevalbackend.system;

import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.instructor.InstructorRepository;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.section.SectionRepository;
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

    public DBDDataInitializer(TeamRepository teamRepository, SectionRepository sectionRepository, InstructorRepository instructorRepository) {
        this.teamRepository = teamRepository;
        this.sectionRepository = sectionRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // section setup
        Section section = new Section();
        section.setSectionName("Section name");

        // teams setup
        Team team1 = new Team();
        team1.setTeamName("team 1");

        Team team2 = new Team();
        team2.setTeamName("team 2");

        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);

        // set teams for section
        section.setTeams(teams);

        // instructors setup
        Instructor instructor1 = new Instructor();
        instructor1.setInstructorId("1");

        Instructor instructor2 = new Instructor();
        instructor2.setInstructorId("2");

        List<Instructor> instructors = new ArrayList<>();
        instructors.add(instructor1);
        instructors.add(instructor2);

        // set instructors for section
        section.setInstructors(instructors);

    }
}
