package edu.tcu.cs.peerevalbackend.rubric.rubricCriteria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubricCriteriaRepository extends JpaRepository<RubricCriteria, Integer> {
}
