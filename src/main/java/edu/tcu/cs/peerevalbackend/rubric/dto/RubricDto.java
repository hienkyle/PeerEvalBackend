package edu.tcu.cs.peerevalbackend.rubric.dto;

import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.dto.RubricCriteriaDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RubricDto (
        @NotEmpty(message = "name is required.")
        String rubricName,

        @NotEmpty(message = "Criteria are required.")
        List<RubricCriteriaDto> rubricCriteriaDtos) {

        @Override
        public List<RubricCriteriaDto> rubricCriteriaDtos() {
                return rubricCriteriaDtos;
        }
}
