package edu.tcu.cs.peerevalbackend.rubric.rubricCriteria;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class RubricCriteriaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RubricCriteriaService rubricCriteriaService;

    @Autowired
    ObjectMapper objectMapper;

    List<RubricCriteria> rubricCriteria;

    @BeforeEach
    void setUp() {

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
    void testFindRubricCriteriaByIdSuccess() throws Exception {
        //given
        given(this.rubricCriteriaService.findById(1)).willReturn(this.rubricCriteria.get(0));

        //when & then
        this.mockMvc.perform(get("/peerEval/rubricCriteria/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.criteriaNum").value(1));
    }

    @Test
    void testFindRubricCriteriaByIdNotFound() throws Exception {
        //given
        given(this.rubricCriteriaService.findById(1)).willThrow(new ObjectNotFoundException("rubricCriteria", 1));

        //when & then
        this.mockMvc.perform(get("/peerEval/rubricCriteria/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find rubricCriteria with Id 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddRubricCriteriaSuccess() throws Exception {
        //given
        RubricCriteriaDto rubricCriteriaDto = new RubricCriteriaDto(
                1,
                "Criteria 1",
                "Criteria 1 is to grade blah blah blah.",
                5);

        String json = this.objectMapper.writeValueAsString(rubricCriteriaDto);
        RubricCriteria savedRubricCriteria = new RubricCriteria();
        savedRubricCriteria.setCriteriaNum(1);
        savedRubricCriteria.setCriteriaName("Criteria 1");
        savedRubricCriteria.setCriteriaDesc("Criteria 1 is to grade blah blah blah.");
        savedRubricCriteria.setCriteriaMaxScore(5);

        given(this.rubricCriteriaService.save(Mockito.any(RubricCriteria.class))).willReturn(savedRubricCriteria);

        //when & then
        this.mockMvc.perform(post("/peerEval/rubricCriteria").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.criteriaNum").isNotEmpty())
                .andExpect(jsonPath("$.data.criteriaName").value(savedRubricCriteria.getCriteriaName()))
                .andExpect(jsonPath("$.data.criteriaDesc").value(savedRubricCriteria.getCriteriaDesc()))
                .andExpect(jsonPath("$.data.criteriaMaxScore").value(savedRubricCriteria.getCriteriaMaxScore()));

    }

}
