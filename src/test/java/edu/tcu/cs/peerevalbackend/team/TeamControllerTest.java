package edu.tcu.cs.peerevalbackend.team;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.section.Section;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.system.ActiveStatus;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevalbackend.team.dto.TeamDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Turn off Spring Security
public class TeamControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TeamService teamService;

    @Autowired
    ObjectMapper objectMapper;

    List<Section> sections;

    List<Team> teams;

    List<Instructor> instructors;

    List<Student> students;

    @BeforeEach
    void setUp() {
        this.sections = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.students = new ArrayList<>();

        //Student set up
        Student stu1 = new Student();
        stu1.setStudentId(1);
        stu1.setFirstName("Mason");
        stu1.setMiddleInitial("D");
        stu1.setLastName("OConnor");
        stu1.setAcademicYear("2024");
        stu1.setPassword("12345");
        this.students.add(stu1);

        Student stu2 = new Student();
        stu2.setStudentId(2);
        stu2.setFirstName("Jake");
        stu2.setMiddleInitial("B");
        stu2.setLastName("OConnor");
        stu2.setAcademicYear("2024");
        stu2.setPassword("54321");
        this.students.add(stu2);

        Student stu3 = new Student();
        stu3.setStudentId(3);
        stu1.setFirstName("Asher");
        stu1.setMiddleInitial("D");
        stu1.setLastName("OConnor");
        stu1.setAcademicYear("2024");
        stu1.setPassword("1518123");
        this.students.add(stu3);

        //Instructor set up
        Instructor i1 = new Instructor();
        i1.setInstructorId("1");
        i1.setName("alvie");
        i1.setPassword("123456");
        i1.setStatus(ActiveStatus.IS_ACTIVE);
        i1.setDeactivateReason(null);
        instructors.add(i1);

        Instructor i2 = new Instructor();
        i2.setInstructorId("2");
        i2.setName("ana");
        i2.setPassword("123456");
        i2.setStatus(ActiveStatus.IS_ACTIVE);
        i2.setDeactivateReason(null);
        instructors.add(i2);

        Instructor i3 = new Instructor();
        i3.setInstructorId("3");
        i3.setName("maribel");
        i3.setPassword("123456");
        i3.setStatus(ActiveStatus.IS_ACTIVE);
        i3.setDeactivateReason(null);
        instructors.add(i3);

        //Team set up
        Team team1 = new Team();
        team1.setTeamName("Team 1");
        team1.setAcademicYear("2023-2024");
        this.teams.add(team1);

        Team team2 = new Team();
        team2.setTeamName("Team 2");
        team2.setAcademicYear("2023-2024");
        this.teams.add(team2);

        //Section set up
        Section s1 = new Section();
        s1.setSectionName("Section 1");
        s1.setAcademicYear("2023-2024");
        s1.setFirstAndLastDate("8/21/23 and 5/01/24");
        this.sections.add(s1);

        /*
        * Set section & team for students
        */
        stu1.setTeam(team1);
        stu2.setTeam(team1);
        stu3.setTeam(team2);
        stu1.setSection(s1);
        stu2.setSection(s1);
        stu3.setSection(s1);

        /*
        * Set section & teams for instructor
        * Instructors will be in the section
        * Instructors will share the same teams, remember that teams have the same instructors
        */
        i1.setTeams(this.teams);
        i2.setTeams(this.teams);
        i3.setTeams(this.teams);
        i1.setSections(this.sections);
        i2.setSections(this.sections);
        i3.setSections(this.sections);

        //Set section, instructors, & students for team
        team1.addStudent(stu1);
        team1.addStudent(stu2);
        team2.addStudent(stu3);
        team1.setInstructors(this.instructors);
        team2.setInstructors(this.instructors);
        team1.setSection(s1);
        team2.setSection(s1);

        //Set teams, instructors, & students for section
        s1.setStudents(this.students);
        s1.setInstructors(this.instructors);
        s1.setTeams(this.teams);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllSectionsSuccess() throws Exception {
        /*
        * First, academic year in reverse chronological order
        * Then, section name in ascending order
        * Then, team name in ascending order
        */
        //Given
        Sort sort = Sort.by(
                Sort.Order.desc("academicYear"),
                Sort.Order.asc("sectionName"),
                Sort.Order.asc("teamName")
        );

        Pageable pageable = PageRequest.of(0, 10, sort);
        PageImpl<Team> teamPage = new PageImpl<>(this.teams, pageable, this.teams.size());

        given(this.teamService.findAll(Mockito.any(Pageable.class))).willReturn(teamPage);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("size", "10");
        requestParams.add("sort", "academicYear desc, sectionName, teamName");

        //When and then
        this.mockMvc.perform(get("/peerEval/teams").accept(MediaType.APPLICATION_JSON).params(requestParams))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data.content", Matchers.hasSize(this.teams.size())))

                /* The code below is to make sure that the first item is as we expect it */
                .andExpect(jsonPath("$.data.content[0].teamName").value("Team 1"))
                .andExpect(jsonPath("$.data.content[0].academicYear").value("2023-2024"))
                .andExpect(jsonPath("$.data.content[0].instructorDtos").isNotEmpty())
                .andExpect(jsonPath("$.data.content[0].studentDtos").isNotEmpty())
                .andExpect(jsonPath("$.data.content[0].sectionName").value("Section 1"));
    }

    @Test
    void testFindTeamByNameSuccess() throws Exception {
        //Given
        given(this.teamService.findByName("Team 1")).willReturn(this.teams.get(0));

        //When and then
        this.mockMvc.perform(get("/peerEval/teams/Team 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.teamName").value("Team 1"));

    }

    @Test
    void testFindTeamByNameNotFound() throws Exception {
        //Given
        given(this.teamService.findByName("Team 1")).willThrow(new ObjectNotFoundException("team", "Team 1"));

        //When and then
        this.mockMvc.perform(get("/peerEval/teams/Team 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find team with Id Team 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddTeamSuccess() throws Exception {
        //Given
        TeamDto teamDto = new TeamDto("Team New-Team",
                                            "2023-2024",
                                            null,
                                            null,
                                            sections.getFirst().getSectionName());

        String json = this.objectMapper.writeValueAsString(teamDto);

        Team savedTeam = new Team();
        savedTeam.setTeamName("Team New-Team");
        savedTeam.setAcademicYear("2023-2024");
        savedTeam.setInstructors(null);
        savedTeam.setStudents(null);
        savedTeam.setSection(sections.getFirst());

        given(this.teamService.save(Mockito.any(Team.class))).willReturn(savedTeam);

        //When and then
        this.mockMvc.perform(post("/peerEval/teams").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.teamName").isNotEmpty())
                .andExpect(jsonPath("$.data.academicYear").value(savedTeam.getAcademicYear()))
                .andExpect(jsonPath("$.data.instructorDtos").value(savedTeam.getInstructors()))
                .andExpect(jsonPath("$.data.studentDtos").value(savedTeam.getStudents()))
                .andExpect(jsonPath("$.data.sectionName").value(savedTeam.getSection().getSectionName()));

                /*
                * If section was null, then I would be unable to do savedTeam.getSection().getSectionName() because I can't get teh section name of a null section
                * //.andExpect(jsonPath("$.data.sectionName").value(savedTeam.getSection().getSectionName()))
                */
    }

    @Test
    void testUpdateTeamSuccess() throws Exception {
        //Given
        TeamDto teamDto = new TeamDto("Team 2.5",
                "2023-2024",
                null,
                null,
                null);

        String json = this.objectMapper.writeValueAsString(teamDto);

        Team updatedTeam = new Team();
        updatedTeam.setTeamName("Team 2.5");
        updatedTeam.setAcademicYear("2023-2024");
        updatedTeam.setInstructors(null);
        updatedTeam.setStudents(null);
        updatedTeam.setSection(null);

        given(this.teamService.update(eq("Team 2.5"), Mockito.any(Team.class))).willReturn(updatedTeam);

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 2.5").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.teamName").isNotEmpty())
                .andExpect(jsonPath("$.data.academicYear").value(updatedTeam.getAcademicYear()))
                .andExpect(jsonPath("$.data.instructorDtos").value(updatedTeam.getInstructors()))
                .andExpect(jsonPath("$.data.studentDtos").value(updatedTeam.getStudents()))
                .andExpect(jsonPath("$.data.sectionName").value(updatedTeam.getSection()));
    }

    @Test
    void testUpdateTeamErrorWithNonExistingName() throws Exception {
        TeamDto teamDto = new TeamDto("Team 2.5",
                "2023-2024",
                null,
                null,
                null);

        String json = this.objectMapper.writeValueAsString(teamDto);

        given(this.teamService.update(eq("Team 2.5"), Mockito.any(Team.class))).willThrow(new ObjectNotFoundException("team", "Team 2.5"));

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 2.5").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find team with Id Team 2.5 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteTeamSuccess() throws Exception {
        //Given
        doNothing().when(this.teamService).delete("Team 1");

        //When and then
        this.mockMvc.perform(delete("/peerEval/teams/Team 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteTeamErrorWithNonExistentName() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("team", "Team 1")).when(this.teamService).delete("Team 1");

        //When and then
        this.mockMvc.perform(delete("/peerEval/teams/Team 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find team with Id Team 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /*
    * Below are the cases for assigning student
    * Ensure that the URL matches that defined in TeamController class
    */

    @Test
    void testAssignStudentSuccess() throws Exception {
        //Given
        doNothing().when(this.teamService).assignStudent("Team 1", 1);

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/assign/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Student Assignment Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAssignStudentErrorWithNonExistentTeamName() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("team", "Team 1")).when(this.teamService).assignStudent("Team 1", 1);

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/assign/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find team with Id Team 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAssignStudentErrorWithNonExistentStudentId() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("student", 1)).when(this.teamService).assignStudent("Team 1", 1);

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/assign/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find student with Id 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /*
    * Below are the cases for assigning instructor
    * Again, ensure that the URL matches that defined in TeamController class
    */

    @Test
    void testAssignInstructorSuccess() throws Exception {
        //Given
        doNothing().when(this.teamService).assignInstructor("Team 1", "1");

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/assign/instructors/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Instructor Assignment Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAssignInstructorErrorWithNonExistentTeamName() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("team", "Team 1")).when(this.teamService).assignInstructor("Team 1", "1");

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/assign/instructors/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find team with Id Team 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAssignInstructorErrorWithNonExistentInstructorId() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("instructor", "1")).when(this.teamService).assignInstructor("Team 1", "1");

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/assign/instructors/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find instructor with Id 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /*
    * Below are the cases for removing student
    * Again, ensure that the URL matches that defined in TeamController class
    */

    @Test
    void testRemoveStudentSuccess() throws Exception {
        //Given
        doNothing().when(this.teamService).removeStudent("Team 1", 1);

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/remove/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Remove Student Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testRemoveStudentErrorWithNonExistentTeamName() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("team", "Team 1")).when(this.teamService).removeStudent("Team 1", 1);

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/remove/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find team with Id Team 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testRemoveStudentErrorWithNonExistentStudentId() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("student", 1)).when(this.teamService).removeStudent("Team 1", 1);

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/remove/students/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find student with Id 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /*
    * Below are the cases for removing instructor
    * Again, ensure that the URL matches that defined in TeamController class
    */

    @Test
    void testRemoveInstructorSuccess() throws Exception {
        //Given
        doNothing().when(this.teamService).removeInstructor("Team 1", "1");

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/remove/instructors/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Remove Instructor Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testRemoveInstructorErrorWithNonExistentTeamName() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("team", "Team 1")).when(this.teamService).removeInstructor("Team 1", "1");

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/remove/instructors/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find team with Id Team 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testRemoveInstructorErrorWithNonExistentInstructorId() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("instructor", "1")).when(this.teamService).removeInstructor("Team 1", "1");

        //When and then
        this.mockMvc.perform(put("/peerEval/teams/Team 1/remove/instructors/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find instructor with Id 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}