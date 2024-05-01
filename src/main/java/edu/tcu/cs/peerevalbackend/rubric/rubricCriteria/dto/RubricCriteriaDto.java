package edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.dto;

import jakarta.validation.constraints.NotEmpty;

public record RubricCriteriaDto(

        Integer criteriaNum,

        @NotEmpty(message = "name is required.")
        String criteriaName,

        @NotEmpty(message = "description is required.")
        String criteriaDesc,

        Integer criteriaMaxScore) {
}
