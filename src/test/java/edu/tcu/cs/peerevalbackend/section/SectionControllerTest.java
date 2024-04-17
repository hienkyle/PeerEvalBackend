package edu.tcu.cs.peerevalbackend.section;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevalbackend.section.dto.SectionDto;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    void setUp() {
        this.sections = new ArrayList<>();

        Section s1 = new Section();
        s1.setSectionName("Section 1");
        s1.setAcademicYear("2023-2024");
        s1.setFirstAndLastDate("8/21/23 and 5/01/24");
        this.sections.add(s1);

        Section s2 = new Section();
        s2.setSectionName("Section 2");
        s2.setAcademicYear("2023-2024");
        s2.setFirstAndLastDate("8/21/23 and 5/01/24");
        this.sections.add(s2);

        Section s3 = new Section();
        s3.setSectionName("Section 3");
        s3.setAcademicYear("2023-2024");
        s3.setFirstAndLastDate("8/21/23 and 5/01/24");
        this.sections.add(s3);
    }

    @AfterEach
    void tearDown() {
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
                                            "8/21/23 and 5/01/24");

        String json = this.objectMapper.writeValueAsString(sectionDto);

        Section savedSection = new Section();
        savedSection.setSectionName("Section New-Section");
        savedSection.setAcademicYear("2023-2024");
        savedSection.setFirstAndLastDate("8/21/23 and 5/01/24");

        given(this.sectionService.save(Mockito.any(Section.class))).willReturn(savedSection);

        //when & then
        this.mockMvc.perform(post("/peerEval/section").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.academicYear").value(savedSection.getAcademicYear()))
                .andExpect(jsonPath("$.data.firstAndLastDate").value(savedSection.getFirstAndLastDate()));
    }

}