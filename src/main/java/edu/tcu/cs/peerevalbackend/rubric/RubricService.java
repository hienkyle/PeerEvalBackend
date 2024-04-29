package edu.tcu.cs.peerevalbackend.rubric;

import edu.tcu.cs.peerevalbackend.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RubricService {

    private final RubricRepository rubricRepository;


    public RubricService(RubricRepository rubricRepository) {
        this.rubricRepository = rubricRepository;
    }

    public Rubric findById(String rubricName) {
        return this.rubricRepository.findById(rubricName)
                .orElseThrow(() -> new ObjectNotFoundException("rubric", rubricName));
    }

    public Rubric save(Rubric newRubric) {
        return this.rubricRepository.save(newRubric);
    }

}
