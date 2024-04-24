package edu.tcu.cs.peerevalbackend.section;

import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SectionServiceTest {

    @Mock //wanna simulate this, don't actually call it
    SectionRepository sectionRepository;

    @InjectMocks //inject the mock into this object
    SectionService sectionService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //given. arrange inputs and targets. define the behavior of Mock object sectionRepository

//        {
//        "name": "Section 1",
//        “academicYear”: "2023-2024",
//        “firstAndLastDate: "8/21/23 and 5/01/24"
//        }

        Section s = new Section();
        s.setSectionName("Section 1");
        s.setAcademicYear("2023-2024");
        s.setFirstAndLastDate("8/21/23 and 5/01/24");

        //know what it returns before u mock
        given(sectionRepository.findById("Section 1")).willReturn(Optional.of(s));

        //when. act on the target behavior. when steps should cover the method to be tested
        Section returnedSection = sectionService.findById("Section 1");

        //then. assert expected outcomes.
        assertThat(returnedSection.getSectionName()).isEqualTo(s.getSectionName());
        assertThat(returnedSection.getAcademicYear()).isEqualTo(s.getAcademicYear());
        assertThat(returnedSection.getFirstAndLastDate()).isEqualTo(s.getFirstAndLastDate());
        verify(sectionRepository, times(1)).findById("Section 1");
    }

    @Test
    void testFindByIdNotFound(){
        //given
        given(sectionRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> {
            Section returnedSection = sectionService.findById("Section 1");
        });

        //then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find section with Id Section 1 :(");
        verify(sectionRepository, times(1)).findById("Section 1");
    }

    @Test
    void testSaveSuccess() {
        //given
        Section newSection = new Section();
        newSection.setSectionName("Section New-Section");
        newSection.setAcademicYear("2023-2024");
        newSection.setFirstAndLastDate("8/21/23 and 5/01/24");

        given(sectionRepository.save(newSection)).willReturn(newSection);

        //when
        Section savedSection = sectionService.save(newSection);

        //then
        assertThat(savedSection.getSectionName()).isEqualTo(newSection.getSectionName());
        assertThat(savedSection.getAcademicYear()).isEqualTo(newSection.getAcademicYear());
        assertThat(savedSection.getFirstAndLastDate()).isEqualTo(newSection.getFirstAndLastDate());
        verify(sectionRepository, times(1)).save(newSection);
    }

    @Test
    void testUpdateSuccess() {
        //given
        Section oldSection = new Section();
        oldSection.setSectionName("Section 1");
        oldSection.setAcademicYear("2023-2024");
        oldSection.setFirstAndLastDate("8/21/23 and 5/01/24");

        Section update  = new Section();
        update.setSectionName("Section 1");
        update.setAcademicYear("2023-2024");
        update.setFirstAndLastDate("8/21/23 and 5/02/24"); //changed day from 01 to 02

        //first find then update
        given(sectionRepository.findById("Section 1")).willReturn(Optional.of(oldSection));
        given(sectionRepository.save(oldSection)).willReturn(oldSection);

        //when
        Section updatedSection = sectionService.update("Section 1", update);

        //then
        assertThat(updatedSection.getSectionName()).isEqualTo(update.getSectionName());
        assertThat(updatedSection.getAcademicYear()).isEqualTo(updatedSection.getAcademicYear());
        verify(sectionRepository, times(1)).findById("Section 1");
        verify(sectionRepository, times(1)).save(oldSection);
    }

    @Test
    void testUpdateNotFound() {
        //given
        Section update  = new Section();
        update.setSectionName("Section 1");
        update.setAcademicYear("2023-2024");
        update.setFirstAndLastDate("8/21/23 and 5/02/24"); //changed day from 01 to 02

        given(sectionRepository.findById("Section 1")).willReturn(Optional.empty());

        //when
        assertThrows(ObjectNotFoundException.class, () -> {
            sectionService.update("Section 1", update);
        });

        //then
        verify(sectionRepository, times(1)).findById("Section 1");
    }

    @Test
    void testDeleteSuccess() {
        //given
        Section section = new Section();
        section.setSectionName("Section 1");
        section.setAcademicYear("2023-2024");
        section.setFirstAndLastDate("8/21/23 and 5/01/24");

        given(sectionRepository.findById("Section 1")).willReturn(Optional.of(section));
        doNothing().when(sectionRepository).deleteById("Section 1");

        //when
        sectionService.delete("Section 1");

        //then
        verify(sectionRepository, times(1)).deleteById("Section 1");

    }

    @Test
    void testDeleteNotFound() {
        //given
        given(sectionRepository.findById("Section 1")).willReturn(Optional.empty());
        doNothing().when(sectionRepository).deleteById("Section 1");

        //when
        assertThrows(ObjectNotFoundException.class, () -> {
            sectionService.delete("Section 1");
        });

        //then
        verify(sectionRepository, times(1)).findById("Section 1");

    }

}