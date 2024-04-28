package edu.tcu.cs.peerevalbackend.rubric;

import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.RubricCriteria;
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
public class RubricServiceTest {

    @Mock
    RubricRepository rubricRepository;

    @InjectMocks
    RubricService rubricService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //given
        Rubric r1 = new Rubric();
        r1.setRubricName("Rubric 1");
        r1.setRubricCriteria(null);

        given(rubricRepository.findById("Rubric 1")).willReturn(Optional.of(r1));

        //when
        Rubric returnedRubric = rubricService.findById("Rubric 1");

        //then
        assertThat(returnedRubric.getRubricName()).isEqualTo(r1.getRubricName());
        assertThat(returnedRubric.getRubricCriteria()).isEqualTo(r1.getRubricCriteria());
        verify(rubricRepository, times(1)).findById("Rubric 1");
    }

    @Test
    void testFindByIdNotFound() {
        //given
        given(rubricRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> {
            Rubric returnedRubric = rubricService.findById("Rubric 1");
        });

        //then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find rubric with Id Rubric 1 :(");
        verify(rubricRepository, times(1)).findById("Rubric 1");
    }


    @Test
    void testSaveSuccess() {
        //given
        Rubric newRubric = new Rubric();
        newRubric.setRubricName("Rubric 1");
        newRubric.setRubricCriteria(null);

        given(rubricRepository.save(newRubric)).willReturn(newRubric);

        //when
        Rubric savedRubric = rubricService.save(newRubric);

        //then
        assertThat(savedRubric.getRubricName()).isEqualTo(newRubric.getRubricName());
        assertThat(savedRubric.getRubricCriteria()).isEqualTo(newRubric.getRubricCriteria());
        verify(rubricRepository, times(1)).save(newRubric);
    }

}
