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

import java.util.ArrayList;
import java.util.List;
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

    List<Section> sections;

    @BeforeEach
    void setUp() {

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

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllSuccess() {
        //given
        given(this.sectionRepository.findAll()).willReturn(this.sections);

        //when
        List<Section> actualSections = this.sectionService.findAll();

        //then
        assertThat(actualSections.size()).isEqualTo(this.sections.size());

        //verify
        verify(this.sectionRepository, times(1)).findAll();

    }

    @Test
    void testFindByIdSuccess() {
        //given. arrange inputs and targets. define the behavior of Mock object sectionRepository
        Section s = new Section();
        s.setSectionName("Section 1");
        s.setAcademicYear("2023-2024");
        s.setFirstAndLastDate("8/21/23 and 5/01/24");
        s.setTeams(null);
        s.setInstructors(null);
        s.setStudents(null);

        //know what it returns before u mock
        given(sectionRepository.findById("Section 1")).willReturn(Optional.of(s));

        //when. act on the target behavior. when steps should cover the method to be tested
        Section returnedSection = sectionService.findById("Section 1");

        //then. assert expected outcomes.
        assertThat(returnedSection.getSectionName()).isEqualTo(s.getSectionName());
        assertThat(returnedSection.getAcademicYear()).isEqualTo(s.getAcademicYear());
        assertThat(returnedSection.getFirstAndLastDate()).isEqualTo(s.getFirstAndLastDate());
        assertThat(returnedSection.getTeams()).isEqualTo(s.getTeams());
        assertThat(returnedSection.getInstructors()).isEqualTo(s.getInstructors());
        assertThat(returnedSection.getStudents()).isEqualTo(s.getStudents());
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
        newSection.setTeams(null);
        newSection.setInstructors(null);
        newSection.setStudents(null);

        given(sectionRepository.save(newSection)).willReturn(newSection);

        //when
        Section savedSection = sectionService.save(newSection);

        //then
        assertThat(savedSection.getSectionName()).isEqualTo(newSection.getSectionName());
        assertThat(savedSection.getAcademicYear()).isEqualTo(newSection.getAcademicYear());
        assertThat(savedSection.getFirstAndLastDate()).isEqualTo(newSection.getFirstAndLastDate());
        assertThat(savedSection.getTeams()).isEqualTo(newSection.getTeams());
        assertThat(savedSection.getInstructors()).isEqualTo(newSection.getInstructors());
        assertThat(savedSection.getStudents()).isEqualTo(newSection.getStudents());
        verify(sectionRepository, times(1)).save(newSection);
    }

    @Test
    void testUpdateSuccess() {
        //given
        Section oldSection = new Section();
        oldSection.setSectionName("Section 1");
        oldSection.setAcademicYear("2023-2024");
        oldSection.setFirstAndLastDate("8/21/23 and 5/01/24");
        oldSection.setTeams(null);
        oldSection.setInstructors(null);
        oldSection.setStudents(null);

        Section update  = new Section();
        update.setSectionName("Section 1");
        update.setAcademicYear("2023-2024");
        update.setFirstAndLastDate("8/21/23 and 5/02/24"); //changed day from 01 to 02
        update.setTeams(null);
        update.setInstructors(null);
        update.setStudents(null);

        //first find then update
        given(sectionRepository.findById("Section 1")).willReturn(Optional.of(oldSection));
        given(sectionRepository.save(oldSection)).willReturn(oldSection);

        //when
        Section updatedSection = sectionService.update("Section 1", update);

        //then
        assertThat(updatedSection.getSectionName()).isEqualTo(update.getSectionName());
        assertThat(updatedSection.getAcademicYear()).isEqualTo(updatedSection.getAcademicYear());
        assertThat(updatedSection.getFirstAndLastDate()).isEqualTo(updatedSection.getFirstAndLastDate());
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
        update.setTeams(null);
        update.setInstructors(null);
        update.setStudents(null);

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
        section.setTeams(null);
        section.setInstructors(null);
        section.setStudents(null);

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
        //doNothing().when(sectionRepository).deleteById("Section 1");

        //when
        assertThrows(ObjectNotFoundException.class, () -> {
            sectionService.delete("Section 1");
        });

        //then
        verify(sectionRepository, times(1)).findById("Section 1");

    }

}