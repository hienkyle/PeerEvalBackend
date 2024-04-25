package edu.tcu.cs.peerevalbackend.instructor;

import edu.tcu.cs.peerevalbackend.system.ActiveStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {
    @Mock
    InstructorRepository instructorRepository;

    @InjectMocks
    InstructorService instructorService;

    List<Instructor> instructors;

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
    void findByIdSuccess() {
        // Given
        Instructor i= new Instructor();
        i.setInstructorId("1");
        i.setName("alvie");
        i.setPassword("123456");
        i.setSections(null);
        i.setStatus(ActiveStatus.IS_ACTIVE);
        i.setDeactivateReason(null);
        i.setTeams(null);

        given(instructorRepository.findById("1")).willReturn(Optional.of(i));

        // When
        Instructor returnedInstructor = instructorService.findById("1");

        // Then
        assertThat(returnedInstructor.getInstructorId()).isEqualTo(i.getInstructorId());
        assertThat(returnedInstructor.getName()).isEqualTo(i.getName());
        assertThat(returnedInstructor.getSections()).isEqualTo(i.getSections());
        assertThat(returnedInstructor.getStatus()).isEqualTo(i.getStatus());
        assertThat(returnedInstructor.getDeactivateReason()).isEqualTo(i.getDeactivateReason());
        assertThat(returnedInstructor.getTeams()).isEqualTo(i.getTeams());
        verify(instructorRepository, times(1)).findById("1");
    }
}