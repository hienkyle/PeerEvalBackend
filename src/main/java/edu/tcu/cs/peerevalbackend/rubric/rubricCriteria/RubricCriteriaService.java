package edu.tcu.cs.peerevalbackend.rubric.rubricCriteria;

import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RubricCriteriaService {

    private final RubricCriteriaRepository rubricCriteriaRepository;

    public RubricCriteriaService(RubricCriteriaRepository rubricCriteriaRepository) {
        this.rubricCriteriaRepository = rubricCriteriaRepository;
    }

    public RubricCriteria findById(Integer criteriaNum) {
        return this.rubricCriteriaRepository.findById(criteriaNum)
                .orElseThrow(() -> new ObjectNotFoundException("rubricCriteria", criteriaNum));
    }

    public RubricCriteria save(RubricCriteria newRubricCriteria) {
        return this.rubricCriteriaRepository.save(newRubricCriteria);
    }
    /*

     */

}
