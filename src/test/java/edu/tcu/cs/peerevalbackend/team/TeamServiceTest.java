package edu.tcu.cs.peerevalbackend.team;

import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.student.StudentRepository;
import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    TeamService teamService;

    @Mock
    StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByNameSuccess() {
        //Set up a team
        Team team = new Team();
        team.setTeamName("Team 1");
        team.setAcademicYear("2023-2024");
        team.setInstructors(null);
        team.setStudents(null);
        team.setSectionName(null);

        //Given
        /*
        * Remember that findById is the same as findByName for me
        * Recall that team name acts as the ID (unique identifier)
        */
        given(teamRepository.findById("Team 1")).willReturn(Optional.of(team));

        //When
        /*
        * Call the findByName method from teamService
        */
        Team returnedTeam = teamService.findByName("Team 1");

        //Then
        /*
        * Verify that the returned team matches the expected attributes
        */
        assertThat(returnedTeam.getTeamName()).isEqualTo(team.getTeamName());
        assertThat(returnedTeam.getAcademicYear()).isEqualTo(team.getAcademicYear());
        assertThat(returnedTeam.getInstructors()).isEqualTo(team.getInstructors());
        assertThat(returnedTeam.getStudents()).isEqualTo(team.getStudents());
        assertThat(returnedTeam.getSectionName()).isEqualTo(team.getSectionName());

        /*
        * Verify that the findById method was called once with the correct parameter
        */
        verify(teamRepository, times(1)).findById("Team 1");
    }

    @Test
    void testFindByNameNotFound() {
        //Given
        given(teamRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        //When
        Throwable thrown = catchThrowable(() -> {
            Team returnedTeam = teamService.findByName("Team 1");
        });

        //Then
        /*
        * I don't have the freedom to change what is in hasMessage()
        * Be consistent with message
        */
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find team with Id Team 1 :(");
        verify(teamRepository, times(1)).findById("Team 1");
    }

    @Test
    void testSaveSuccess() {
        //Set up a new team
        Team newTeam = new Team();
        newTeam.setTeamName("Team 2");
        newTeam.setAcademicYear("2023-2024");
        newTeam.setInstructors(null);
        newTeam.setStudents(null);
        newTeam.setSectionName(null);

        //Given
        given(teamRepository.save(newTeam)).willReturn(newTeam);

        //When
        Team savedTeam = teamService.save(newTeam);

        //Then
        assertThat(savedTeam.getTeamName()).isEqualTo(newTeam.getTeamName());
        assertThat(savedTeam.getAcademicYear()).isEqualTo(newTeam.getAcademicYear());
        assertThat(savedTeam.getInstructors()).isEqualTo(newTeam.getInstructors());
        assertThat(savedTeam.getStudents()).isEqualTo(newTeam.getStudents());
        assertThat(savedTeam.getSectionName()).isEqualTo(newTeam.getSectionName());
        verify(teamRepository, times(1)).save(newTeam);
    }

    @Test
    void testUpdateSuccess() {
        //Set up old team
        Team oldTeam = new Team();
        oldTeam.setTeamName("Team 1");
        oldTeam.setAcademicYear("2023-2024");
        oldTeam.setInstructors(null);
        oldTeam.setStudents(null);
        oldTeam.setSectionName(null);

        //Set up update team
        Team update = new Team();
        update.setTeamName("Team 1");
        update.setAcademicYear("2024-2025"); //Change academic year
        update.setInstructors(null);
        update.setStudents(null);
        update.setSectionName(null);

        //Given
        /*
        * Find team then update
        */
        given(teamRepository.findById("Team 1")).willReturn(Optional.of(oldTeam));
        given(teamRepository.save(oldTeam)).willReturn(oldTeam);

        //when
        Team updatedTeam = teamService.update("Team 1", update);

        //then
        assertThat(updatedTeam.getSectionName()).isEqualTo(update.getSectionName());
        assertThat(updatedTeam.getAcademicYear()).isEqualTo(updatedTeam.getAcademicYear());
        verify(teamRepository, times(1)).findById("Team 1");
        verify(teamRepository, times(1)).save(oldTeam);
    }

    @Test
    void testUpdateNotFound() {
        //Set up update team
        Team update = new Team();
        update.setTeamName("Team 1");
        update.setAcademicYear("2024-2025"); //Change academic year
        update.setInstructors(null);
        update.setStudents(null);
        update.setSectionName(null);

        //Given
        given(teamRepository.findById("Team 1")).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class, () -> {
            teamService.update("Team 1", update);
        });

        //Then
        verify(teamRepository, times(1)).findById("Team 1");
    }

    @Test
    void testDeleteSuccess() {
        //Set up team
        Team team = new Team();
        team.setTeamName("Team 1");
        team.setAcademicYear("2023-2024");
        team.setInstructors(null);
        team.setStudents(null);
        team.setSectionName(null);

        //Given
        given(teamRepository.findById("Team 1")).willReturn(Optional.of(team));
        doNothing().when(teamRepository).deleteById("Team 1");

        //When
        teamService.delete("Team 1");

        //Then
        verify(teamRepository, times(1)).deleteById("Team 1");
    }

    @Test
    void testDeleteNotFound() {
        //Given
        given(teamRepository.findById("Team 1")).willReturn(Optional.empty());

        //When
        assertThrows(ObjectNotFoundException.class, () -> {
            teamService.delete("Team 1");
        });

        //Then
        verify(teamRepository, times(1)).findById("Team 1");
    }

    @Test
    void testAssignStudentSuccess() {
        //Set up student
        Student stu1 = new Student();
        stu1.setStudentId(1);
        stu1.setFirstName("Mason");
        stu1.setMiddleInitial("D");
        stu1.setLastName("OConnor");
        stu1.setAcademicYear("2024");
        stu1.setPassword("12345");

        //Create a list of students for team 1
        List<Student> team1Students = new ArrayList<>();
        team1Students.add(stu1);

        /*
        * Set up team 1 where I will assign student
        * Needed this to test whether I properly remove student from a team before assigning to new team
        */
        Team team1 = new Team();
        team1.setTeamName("Team 1");
        team1.setAcademicYear("2023-2024");
        team1.setInstructors(null);
        team1.setStudents(team1Students);
        team1.setSectionName(null);

        //Create a list of students for team 2
        List<Student> team2Students = new ArrayList<>();

        //Set up a new team where I will assign student
        Team team2 = new Team();
        team2.setTeamName("Team 2");
        team2.setAcademicYear("2023-2024");
        team2.setInstructors(null);
        team2.setStudents(team2Students);
        team2.setSectionName(null);

        //Given
        given(this.studentRepository.findById(1)).willReturn(Optional.of(stu1));
        given(this.teamRepository.findById("Team 2")).willReturn(Optional.of(team2));

        //When
        this.teamService.assignStudent("Team 2", 1);

        //Then
        assertThat(stu1.getTeamName()).isEqualTo(team2.getTeamName());
        assertThat(team2.getStudents()).contains(stu1);
    }
}
/*
    @Test
    void testAssignArtifactErrorWithNonExistentWizardId() {
        //given
        Artifact a = new Artifact();
        a.setId("1250808601744904192");
        a.setName("Invisibility Cloak");
        a.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a.setImageUrl("ImageUrl");

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");
        w2.addArtifact(a);

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a));
        given(this.wizardRepository.findById(3)).willReturn(Optional.empty());

        //when
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3, "1250808601744904192");
        });

        //then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find wizard with Id 3 :(");
        assertThat(a.getOwner().getId()).isEqualTo(2);
    }

    @Test
    void testAssignArtifactErrorWithNonExistentArtifactId() {
        //given
        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        //when
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3, "1250808601744904192");
        });

        //then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find artifact with Id 1250808601744904192 :(");
    }
*/