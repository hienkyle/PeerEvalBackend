package edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.converter;

import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.RubricCriteria;
import edu.tcu.cs.peerevalbackend.rubric.rubricCriteria.dto.RubricCriteriaDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RubricCriteriaDtoToRubricCriteriaConverter implements Converter<RubricCriteriaDto, RubricCriteria> {

    @Override
    public RubricCriteria convert(RubricCriteriaDto source) {

        if (source == null) {
            return null; // Handle null input gracefully
        }

        RubricCriteria rubricCriteria = new RubricCriteria();
        rubricCriteria.setCriteriaNum(source.criteriaNum());
        rubricCriteria.setCriteriaName(source.criteriaName());
        rubricCriteria.setCriteriaDesc(source.criteriaDesc());
        rubricCriteria.setCriteriaMaxScore(source.criteriaMaxScore());

        return rubricCriteria;
    }


}
