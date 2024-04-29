package edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.converter;

import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.RubricCriteria;
import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.dto.RubricCriteriaDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RubricCriteriaToRubricCriteriaDtoConverter implements Converter<RubricCriteria, RubricCriteriaDto> {

    @Override
    public RubricCriteriaDto convert(RubricCriteria source) {
        RubricCriteriaDto rubricCriteriaDto = new RubricCriteriaDto(
                source.getCriteriaNum(),
                source.getCriteriaName(),
                source.getCriteriaDesc(),
                source.getCriteriaMaxScore()
        );

        return rubricCriteriaDto;
    }
}
