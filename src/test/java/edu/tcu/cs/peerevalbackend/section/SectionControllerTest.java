package edu.tcu.cs.peerevalbackend.section;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevalbackend.instructor.Instructor;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.student.Student;
import edu.tcu.cs.peerevalbackend.system.ActiveStatus;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevalbackend.team.Team;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class SectionControllerTest {

    @Autowired
    MockMvc mockMvc; //for mocking http requests (since this is the controller)

    @MockBean
    SectionService sectionService;

    @Autowired
    ObjectMapper objectMapper;

    List<Section> sections;

    List<Team> teams;

    List<Instructor> instructors;

    List<Student> students;

    @BeforeEach
    void setUp() {
        //section set up
        this.sections = new ArrayList<>();

        Section s1 = new Section();
        s1.setSectionName("Section 1");
        s1.setAcademicYear("2023-2024");
        s1.setFirstAndLastDate("8/21/23 and 5/01/24");
        s1.setTeams(null);
        s1.setInstructors(null);
        s1.setStudents(null);
        this.sections.add(s1);

        Section s2 = new Section();
        s2.setSectionName("Section 2");
        s2.setAcademicYear("2023-2024");
        s2.setFirstAndLastDate("8/21/23 and 5/01/24");
        s2.setTeams(null);
        s2.setInstructors(null);
        s2.setStudents(null);
        this.sections.add(s2);

        Section s3 = new Section();
        s3.setSectionName("Section 3");
        s3.setAcademicYear("2023-2024");
        s3.setFirstAndLastDate("8/21/23 and 5/01/24");
        s3.setTeams(null);
        s3.setInstructors(null);
        s3.setStudents(null);
        this.sections.add(s3);

        //team set up
        Team team1 = new Team();
        team1.setTeamName("Team 1");
        team1.setAcademicYear("2023-2024");
        team1.setSectionName(null);
        team1.setInstructors(null);
        this.teams.add(team1);

        Team team2 = new Team();
        team2.setTeamName("Team 2");
        team2.setAcademicYear("2023-2024");
        team2.setSectionName(null);
        team2.setInstructors(null);
        this.teams.add(team2);

        Team team3 = new Team();
        team3.setTeamName("Team 3");
        team3.setAcademicYear("2023-2024");
        team3.setSectionName(null);
        team3.setInstructors(null);
        this.teams.add(team3);

        //instructor set up
        Instructor i1 = new Instructor();
        i1.setInstructorId("1");
        i1.setName("alvie");
        i1.setPassword("123456");
        i1.setSections(null);
        i1.setStatus(ActiveStatus.IS_ACTIVE);
        i1.setDeactivateReason(null);
        i1.setTeams(null);
        instructors.add(i1);

        Instructor i2 = new Instructor();
        i2.setInstructorId("2");
        i2.setName("ana");
        i2.setPassword("123456");
        i2.setSections(null);
        i2.setStatus(ActiveStatus.IS_ACTIVE);
        i2.setDeactivateReason(null);
        i2.setTeams(null);
        instructors.add(i2);

        Instructor i3 = new Instructor();
        i3.setInstructorId("3");
        i3.setName("maribel");
        i3.setPassword("123456");
        i3.setSections(null);
        i3.setStatus(ActiveStatus.IS_DEACTIVATED);
        i3.setDeactivateReason(null);
        i3.setTeams(null);
        instructors.add(i3);

        //student set up
        this.students = new ArrayList<>();
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
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllSectionsSuccess() throws Exception {
        //given
        Sort sort = Sort.by(
                Sort.Order.asc("sectionName"),  // Sort section name in ascending order
                Sort.Order.desc("academicYear") // Sort academic year in descending order
        );

        Pageable pageable = PageRequest.of(0, 10, sort); //10 sections per page
        PageImpl<Section> sectionPage = new PageImpl<>(this.sections, pageable, this.sections.size());

        given(this.sectionService.findAll(Mockito.any(Pageable.class))).willReturn(sectionPage);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page", "0");
        requestParams.add("size", "10");
        requestParams.add("sort", "sectionName,academicYear,desc");
        //can add sorting

        //when & then
        this.mockMvc.perform(get("/sections").accept(MediaType.APPLICATION_JSON).params(requestParams))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data.content", Matchers.hasSize(this.sections.size())))
                .andExpect(jsonPath("$.data.content[0].id").value("Section 1"))
                .andExpect(jsonPath("$.data.content[0].academicYear").value("2023-2024"))
                .andExpect(jsonPath("$.data.content[0].firstAndLastDate").value("8/21/23 and 5/01/24"))
                .andExpect(jsonPath("$.data.content[0].teams").exists())
                .andExpect(jsonPath("$.data.content[0].instructors").exists())
                .andExpect(jsonPath("$.data.content[0].students").exists());
    }

    @Test
    void testFindSectionByIdSuccess() throws Exception {
        //given
        given(this.sectionService.findById("Section 1")).willReturn(this.sections.get(0));

        //when and then
        this.mockMvc.perform(get("/peerEval/section/Section 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("Section 1"));
    } //will the last '.andExpect' be $.data.id or $.data.sectionName

    @Test
    void testFindSectionByIdNotFound() throws Exception {
        //given
        given(this.sectionService.findById("Section 1")).willThrow(new ObjectNotFoundException("section", "Section 1"));

        //when and then
        this.mockMvc.perform(get("/peerEval/section/Section 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find section with Id Section 1"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddSectionSuccess() throws Exception {
        //given
        SectionDto sectionDto = new SectionDto("Section New-Section",
                                              "2023-2024",
                                            "8/21/23 and 5/01/24",
                                            null,
                                            null,
                                            null);

        String json = this.objectMapper.writeValueAsString(sectionDto);

        Section savedSection = new Section();
        savedSection.setSectionName("Section New-Section");
        savedSection.setAcademicYear("2023-2024");
        savedSection.setFirstAndLastDate("8/21/23 and 5/01/24");
        savedSection.setTeams(null);
        savedSection.setInstructors(null);
        savedSection.setStudents(null);

        given(this.sectionService.save(Mockito.any(Section.class))).willReturn(savedSection);

        //when & then
        this.mockMvc.perform(post("/peerEval/section").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.academicYear").value(savedSection.getAcademicYear()))
                .andExpect(jsonPath("$.data.firstAndLastDate").value(savedSection.getFirstAndLastDate()))
                .andExpect(jsonPath("$.data.teams").value(savedSection.getTeams()))
                .andExpect(jsonPath("$.data.instructors").value(savedSection.getInstructors()))
                .andExpect(jsonPath("$.data.students").value(savedSection.getStudents()));
    }

    @Test
    void testUpdateSectionSuccess() throws Exception {
        //given
        SectionDto sectionDto = new SectionDto("Section 2.5",
                "2023-2024",
                "8/21/23 and 5/01/24",
                null,
                null,
                null);

        String json = this.objectMapper.writeValueAsString(sectionDto);

        Section updatedSection = new Section();
        updatedSection.setSectionName("Section 2.5");
        updatedSection.setAcademicYear("2023-2024");
        updatedSection.setFirstAndLastDate("8/21/23 and 5/01/24");
        updatedSection.setTeams(null);
        updatedSection.setInstructors(null);
        updatedSection.setStudents(null);

        given(this.sectionService.update(eq("Section 2.5"), Mockito.any(Section.class))).willReturn(updatedSection);

        //when & then
        this.mockMvc.perform(put("/peerEval/section/Section 2.5").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.academicYear").value(updatedSection.getAcademicYear()))
                .andExpect(jsonPath("$.data.firstAndLastDate").value(updatedSection.getFirstAndLastDate()))
                .andExpect(jsonPath("$.data.teams").value(updatedSection.getTeams()))
                .andExpect(jsonPath("$.data.instructors").value(updatedSection.getInstructors()))
                .andExpect(jsonPath("$.data.students").value(updatedSection.getStudents()));
    }

    @Test
    void testUpdateSectionErrorWithNonExistingId() throws Exception {
        //given
        SectionDto sectionDto = new SectionDto("Section 2.5",
                "2023-2024",
                "8/21/23 and 5/01/24",
                null,
                null,
                null);

        String json = this.objectMapper.writeValueAsString(sectionDto);

        given(this.sectionService.update(eq("Section 2.5"), Mockito.any(Section.class))).willThrow(new ObjectNotFoundException("section", "Section 2.5"));

        //when & then
        this.mockMvc.perform(put("/peerEval/section/Section 2.5").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find section with Id Section 2.5 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteSectionSuccess() throws Exception {
        //given
        doNothing().when(this.sectionService).delete("Section 1");

        //when & then
        this.mockMvc.perform(delete("/peerEval/section/Section 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteSectionErrorWithNonExistentId() throws Exception {
        //given
        doThrow(new ObjectNotFoundException("section", "Section 1")).when(this.sectionService).delete("Section 1");

        //when & then
        this.mockMvc.perform(delete("/peerEval/section/Section 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find section with Id Section 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}