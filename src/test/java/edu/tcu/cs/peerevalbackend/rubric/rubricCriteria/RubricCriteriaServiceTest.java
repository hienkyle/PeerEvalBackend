package edu.tcu.cs.peerevalbackend.rubric.rubricCriteria;

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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RubricCriteriaServiceTest {

    @Mock
    RubricCriteriaRepository rubricCriteriaRepository;

    @InjectMocks
    RubricCriteriaService rubricCriteriaService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //given
        RubricCriteria rc1 = new RubricCriteria();
        rc1.setCriteriaNum(1);
        rc1.setCriteriaName("Criteria 1");
        rc1.setCriteriaDesc("Criteria 1 is to grade blah blah blah.");
        rc1.setCriteriaMaxScore(5);

        given(rubricCriteriaRepository.findById(1)).willReturn(Optional.of(rc1));

        //when
        RubricCriteria returnedRubricCriteria = rubricCriteriaService.findById(1);

        //then
        assertThat(returnedRubricCriteria.getCriteriaNum()).isEqualTo(rc1.getCriteriaNum());
        assertThat(returnedRubricCriteria.getCriteriaName()).isEqualTo(rc1.getCriteriaName());
        assertThat(returnedRubricCriteria.getCriteriaDesc()).isEqualTo(rc1.getCriteriaDesc());
        assertThat(returnedRubricCriteria.getCriteriaMaxScore()).isEqualTo(rc1.getCriteriaMaxScore());
        verify(rubricCriteriaRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        //given
        given(rubricCriteriaRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> {
            RubricCriteria returnedRubricCriteria = rubricCriteriaService.findById(1);
        });

        //then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find rubricCriteria with Id 1 :(");
        verify(rubricCriteriaRepository, times(1)).findById(1);
    }

    @Test
    void testSaveSuccess() {
        //given
        RubricCriteria newRubricCriteria = new RubricCriteria();
        newRubricCriteria.setCriteriaNum(1);
        newRubricCriteria.setCriteriaName("Criteria 1");
        newRubricCriteria.setCriteriaDesc("Criteria 1 is to grade blah blah.");
        newRubricCriteria.setCriteriaMaxScore(5);

        given(rubricCriteriaRepository.save(newRubricCriteria)).willReturn(newRubricCriteria);

        //when
        RubricCriteria savedRubricCriteria = rubricCriteriaService.save(newRubricCriteria);

        //then
        assertThat(savedRubricCriteria.getCriteriaNum()).isEqualTo(newRubricCriteria.getCriteriaNum());
        assertThat(savedRubricCriteria.getCriteriaName()).isEqualTo(newRubricCriteria.getCriteriaName());
        assertThat(savedRubricCriteria.getCriteriaDesc()).isEqualTo(newRubricCriteria.getCriteriaDesc());
        assertThat(savedRubricCriteria.getCriteriaMaxScore()).isEqualTo(newRubricCriteria.getCriteriaMaxScore());
        verify(rubricCriteriaRepository, times(1)).save(newRubricCriteria);
    }

}
