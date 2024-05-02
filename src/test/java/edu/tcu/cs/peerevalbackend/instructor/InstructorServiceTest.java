package edu.tcu.cs.peerevalbackend.instructor;

import edu.tcu.cs.peerevalbackend.system.ActiveStatus;
import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
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
import static org.assertj.core.api.Assertions.catchThrowable;
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
    void testInviteSuccess(){
        // Given
        List<String> emails = new ArrayList<>();
        emails.add("alvie@gmail.com");
        emails.add("maribel@gmail.com");

        // When
        List<String> sentEmails = instructorService.invite(emails);

        // Then
        assertThat(sentEmails).isEqualTo(emails);
    }

    @Test
    void testFindByIdSuccess() {
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

    @Test
    void testFindByIdNotFound() {
        // Given
        given(instructorRepository.findById("1")).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Instructor returnedInstructor = instructorService.findById("1");
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find instructor with Id 1 :(");
        verify(instructorRepository,times(1)).findById("1");
    }

    @Test
    void testSaveSuccess() {
        // Given
        Instructor newInstructor = new Instructor();
        newInstructor.setName("hien");
        newInstructor.setStatus(ActiveStatus.IS_ACTIVE);

        given(this.instructorRepository.save(newInstructor)).willReturn(newInstructor);

        // When
        Instructor savedIns = this.instructorService.save(newInstructor);

        // Then
        assertThat(savedIns.getName()).isEqualTo("hien");
        assertThat(savedIns.getStatus()).isEqualTo(ActiveStatus.IS_ACTIVE);
        verify(this.instructorRepository, times(1)).save(newInstructor);
    }

    @Test
    void testDeactivateSuccess() {
        // Given
        Instructor oldInstructor = new Instructor();
        oldInstructor.setInstructorId("1");
        oldInstructor.setName("alvie");
        oldInstructor.setStatus(ActiveStatus.IS_ACTIVE);

        Instructor update = new Instructor();
        update.setName("alvie");
        update.setStatus(ActiveStatus.IS_DEACTIVATED);

        given(this.instructorRepository.findById("1")).willReturn(Optional.of(oldInstructor));
        given(this.instructorRepository.save(oldInstructor)).willReturn(oldInstructor);

        // When
        Instructor updatedIns = this.instructorService.deactivate("1", "this is a test");

        // Then
        assertThat(updatedIns.getInstructorId()).isEqualTo("1");
        assertThat(updatedIns.getName()).isEqualTo("alvie");
        assertThat(updatedIns.getStatus()).isEqualTo(ActiveStatus.IS_DEACTIVATED);
        verify(this.instructorRepository, times(1)).findById("1");
        verify(this.instructorRepository, times(1)).save(oldInstructor);
    }

    @Test
    void testDeactivatedNotFound() {
        // Given
        given(instructorRepository.findById("1")).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Instructor returnedInstructor = instructorService.deactivate("1", "this is a test");
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find instructor with Id 1 :(");
        verify(instructorRepository,times(1)).findById("1");
    }

    @Test
    void testReactivateSuccess() {
        // Given
        Instructor oldInstructor = new Instructor();
        oldInstructor.setInstructorId("1");
        oldInstructor.setName("alvie");
        oldInstructor.setStatus(ActiveStatus.IS_DEACTIVATED);

        Instructor update = new Instructor();
        update.setName("alvie");
        update.setStatus(ActiveStatus.IS_ACTIVE);

        given(this.instructorRepository.findById("1")).willReturn(Optional.of(oldInstructor));
        given(this.instructorRepository.save(oldInstructor)).willReturn(oldInstructor);

        // When
        Instructor updatedIns = this.instructorService.reactivate("1");

        // Then
        assertThat(updatedIns.getInstructorId()).isEqualTo("1");
        assertThat(updatedIns.getName()).isEqualTo("alvie");
        assertThat(updatedIns.getStatus()).isEqualTo(ActiveStatus.IS_ACTIVE);
        verify(this.instructorRepository, times(1)).findById("1");
        verify(this.instructorRepository, times(1)).save(oldInstructor);
    }

    @Test
    void testReactivatedNotFound() {
        // Given
        given(instructorRepository.findById("1")).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Instructor returnedInstructor = instructorService.reactivate("1");
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find instructor with Id 1 :(");
        verify(instructorRepository,times(1)).findById("1");
    }
}