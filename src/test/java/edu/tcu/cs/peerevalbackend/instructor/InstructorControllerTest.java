package edu.tcu.cs.peerevalbackend.instructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevalbackend.system.ActiveStatus;
import edu.tcu.cs.peerevalbackend.system.StatusCode;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class InstructorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    InstructorService instructorService;

    @Autowired
    ObjectMapper objectMapper;

    List<Instructor> instructors;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @BeforeEach
    void setUp() {
        instructors = new ArrayList<>();
        Instructor i1 = new Instructor();
        i1.setInstructorId("1");
        i1.setName("alvie");
        i1.setPassword("123456");
        i1.setSections(null);
        i1.setStatus(ActiveStatus.IS_ACTIVE);
        i1.setDeactivateReason(null);
        i1.setTeams(null);

        Instructor i2 = new Instructor();
        i2.setInstructorId("2");
        i2.setName("ana");
        i2.setPassword("123456");
        i2.setSections(null);
        i2.setStatus(ActiveStatus.IS_ACTIVE);
        i2.setDeactivateReason(null);
        i2.setTeams(null);

        Instructor i3 = new Instructor();
        i3.setInstructorId("3");
        i3.setName("maribel");
        i3.setPassword("123456");
        i3.setSections(null);
        i3.setStatus(ActiveStatus.IS_DEACTIVATED);
        i3.setDeactivateReason(null);
        i3.setTeams(null);

        instructors.add(i1);
        instructors.add(i2);
        instructors.add(i3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findInstructorsByCriteria() {
    }

    @Test
    void testViewInstructorSuccess() throws Exception {
        // Given
        given(this.instructorService.findById("1")).willReturn(this.instructors.get(0));

        // When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(this.baseUrl + "/instructors/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.instructorId").value("1"))
                .andExpect(jsonPath("$.data.name").value("alvie"))
                .andExpect(jsonPath("$.data.status").value("IS_ACTIVE"));
    }

    @Test
    void deactivateInstructor() {
    }

    @Test
    void reactivateInstructor() {
    }
}