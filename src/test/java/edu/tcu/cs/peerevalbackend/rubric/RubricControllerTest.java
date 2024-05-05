package edu.tcu.cs.peerevalbackend.rubric;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevalbackend.rubric.dto.RubricDto;
import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.RubricCriteria;
import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.dto.RubricCriteriaDto;
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
@AutoConfigureMockMvc(addFilters = false) // Turn off Spring Security
public class RubricControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RubricService rubricService;

    @Autowired
    ObjectMapper objectMapper;

    List<Rubric> rubrics;

    List<RubricCriteria> rubricCriteria;

    @BeforeEach
    void setUp() {

        this.rubrics = new ArrayList<>();
        this.rubricCriteria = new ArrayList<>();

        Rubric r1 = new Rubric();
        r1.setRubricName("Rubric 1");
        r1.setRubricCriteria(null);
        this.rubrics.add(r1);

        Rubric r2 = new Rubric();
        r2.setRubricName("Rubric 2");
        r2.setRubricCriteria(null);
        this.rubrics.add(r2);

        //=================================================================
        this.rubricCriteria = new ArrayList<>();

        RubricCriteria rc1 = new RubricCriteria();
        rc1.setCriteriaNum(1);
        rc1.setCriteriaName("Criteria 1");
        rc1.setCriteriaDesc("Criteria 1 is to grade blah blah blah.");
        rc1.setCriteriaMaxScore(5);
        this.rubricCriteria.add(rc1);

        RubricCriteria rc2 = new RubricCriteria();
        rc2.setCriteriaNum(2);
        rc2.setCriteriaName("Criteria 2");
        rc2.setCriteriaDesc("Criteria 2 is to grade blah blah blah.");
        rc2.setCriteriaMaxScore(5);
        this.rubricCriteria.add(rc2);

        RubricCriteria rc3 = new RubricCriteria();
        rc3.setCriteriaNum(3);
        rc3.setCriteriaName("Criteria 3");
        rc3.setCriteriaDesc("Criteria 3 is to grade blah blah blah.");
        rc3.setCriteriaMaxScore(5);
        this.rubricCriteria.add(rc3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindRubricByIdSuccess() throws Exception {
        //given
        given(this.rubricService.findById("Rubric 1")).willReturn(this.rubrics.get(0));

        //when & then
        this.mockMvc.perform(get("/peerEval/rubric/Rubric 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.rubricName").value("Rubric 1"));
    }

    @Test
    void testFindRubricByIdNotFound() throws Exception {
        //given
        given(this.rubricService.findById("Rubric 1")).willThrow(new ObjectNotFoundException("rubric", "Rubric 1"));

        //when & then
        this.mockMvc.perform(get("/peerEval/rubric/Rubric 1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find rubric with Id Rubric 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddRubricSuccess() throws Exception {
        List<RubricCriteriaDto> criteriaDtos = new ArrayList<>();
        RubricCriteriaDto criteriaDto1 = new RubricCriteriaDto(1, "Criteria 1", "Criteria 1 is to grade blah blah blah.", 5);
        RubricCriteriaDto criteriaDto2 = new RubricCriteriaDto(2, "Criteria 2", "Criteria 2 is to grade blah blah blah.", 5);
        RubricCriteriaDto criteriaDto3 = new RubricCriteriaDto(3, "Criteria 3", "Criteria 3 is to grade blah blah blah.", 5);
        criteriaDtos.add(criteriaDto1);
        criteriaDtos.add(criteriaDto2);
        //given
        RubricDto rubricDto = new RubricDto(
                "Rubric 1",
                criteriaDtos

        );

        String json = this.objectMapper.writeValueAsString(rubricDto);

        Rubric savedRubric = new Rubric();
        savedRubric.setRubricName("Rubric 1");
        savedRubric.setRubricCriteria(this.rubricCriteria);

        given(this.rubricService.save(Mockito.any(Rubric.class))).willReturn(savedRubric);

        //when & then
        this.mockMvc.perform(post("/peerEval/rubric").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.rubricName").isNotEmpty())
                .andExpect(jsonPath("$.data.rubricCriteriaDtos").isNotEmpty());
    }

}
