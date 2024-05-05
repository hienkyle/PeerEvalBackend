package edu.tcu.cs.peerevalbackend.rubric.converter;

import edu.tcu.cs.peerevalbackend.rubric.Rubric;
import edu.tcu.cs.peerevalbackend.rubric.dto.RubricDto;
import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.converter.RubricCriteriaToRubricCriteriaDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RubricToRubricDtoConverter implements Converter<Rubric, RubricDto> {
    private final RubricCriteriaToRubricCriteriaDtoConverter rubricCriteriaToRubricCriteriaDtoConverter;

    public RubricToRubricDtoConverter(RubricCriteriaToRubricCriteriaDtoConverter rubricCriteriaToRubricCriteriaDtoConverter) {
        this.rubricCriteriaToRubricCriteriaDtoConverter = rubricCriteriaToRubricCriteriaDtoConverter;
    }

    @Override
    public RubricDto convert(Rubric source) {
        RubricDto rubricDto = new RubricDto(
                source.getRubricName(),
                source.getRubricCriteria() != null ? source.getRubricCriteria().stream()
                        .map(rubricCriteria -> rubricCriteriaToRubricCriteriaDtoConverter.convert(rubricCriteria)).collect(Collectors.toList()) : null
        );

        return rubricDto;
    }
}
